package com.hbm.tileentity.machine.oil;

import api.hbm.tile.IHeatSource;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.CokerRecipes;
import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.lib.DirPos;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class TileEntityMachineCoker extends TileEntityMachineBase implements ITickable, IGUIProvider, IFluidHandler, ITankPacketAcceptor {

    public boolean wasOn;
    public int progress;
    public static int processTime = 20_000;

    public int heat;
    public static int maxHeat = 100_000;
    public static double diffusion = 0.25D;

    public FluidTank[] tanks;

    public TileEntityMachineCoker() {
        super(1);
        tanks = new FluidTank[2];
        tanks[0] = new FluidTank(16_000); //Input
        tanks[1] = new FluidTank(8_000); //Output
    }

    @Override
    public String getName() {
        return "container.machineCoker";
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            this.tryPullHeat();

            this.wasOn = false;

            if(canProcess()) {
                int burn = heat / 100;

                if(burn > 0) {
                    this.wasOn = true;
                    this.progress += burn;
                    this.heat -= burn;

                    if(progress >= processTime) {
                        this.markDirty();
                        progress -= processTime;

                        if (tanks[0].getFluid() != null){
                            Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getFluid().getFluid());
                            int fillReq = recipe.getX();
                            ItemStack output = recipe.getY();
                            FluidStack byproduct = recipe.getZ();

                            if (output != null) {
                                if (inventory.getStackInSlot(0).isEmpty()) {
                                    inventory.setStackInSlot(0, output.copy());
                                } else {
                                    inventory.getStackInSlot(0).grow(output.getCount());
                                }
                            }

                            if (byproduct != null) {
                                tanks[1].fill(byproduct, true);
                            }

                            tanks[0].drain(fillReq, true);
                        }
                    }
                }
            }

            for(DirPos pos : getConPos()) {
                if(tanks[1].getFluidAmount() > 0)
                    FFUtils.fillFluid(this, tanks[1], world, pos.getPos(), 4000);
            }

            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("wasOn", this.wasOn);
            data.setInteger("heat", this.heat);
            data.setInteger("progress", this.progress);
            data.setTag("tanks", FFUtils.serializeTankArray(tanks));
            this.networkPack(data, 25);
        } else {

            if(this.wasOn) {

                if(world.getTotalWorldTime() % 2 == 0) {
                    NBTTagCompound fx = new NBTTagCompound();
                    fx.setString("type", "tower");
                    fx.setFloat("lift", 10F);
                    fx.setFloat("base", 0.75F);
                    fx.setFloat("max", 3F);
                    fx.setInteger("life", 200 + world.rand.nextInt(50));
                    fx.setInteger("color",0x404040);
                    fx.setDouble("posX", pos.getX() + 0.5);
                    fx.setDouble("posY", pos.getY() + 22);
                    fx.setDouble("posZ", pos.getZ() + 0.5);
                    MainRegistry.proxy.effectNT(fx);
                }
            }
        }
    }

    public DirPos[] getConPos() {

        return new DirPos[] {
                new DirPos(pos.getX() + 2, pos.getY(), pos.getZ() + 1, Library.POS_X),
                new DirPos(pos.getX() + 2, pos.getY(), pos.getZ() - 1, Library.POS_X),
                new DirPos(pos.getX() - 2, pos.getY(), pos.getZ() + 1, Library.NEG_X),
                new DirPos(pos.getX() - 2, pos.getY(), pos.getZ() - 1, Library.NEG_X),
                new DirPos(pos.getX() + 1, pos.getY(), pos.getZ() + 2, Library.POS_Z),
                new DirPos(pos.getX() - 1, pos.getY(), pos.getZ() + 2, Library.POS_Z),
                new DirPos(pos.getX() + 1, pos.getY(), pos.getZ() - 2, Library.NEG_Z),
                new DirPos(pos.getX() - 1, pos.getY(), pos.getZ() - 2, Library.NEG_Z)
        };
    }

    public boolean canProcess() {
        if(tanks[0].getFluid() == null) return false;
        Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getFluid().getFluid());

        if(recipe == null) return false;

        int fillReq = recipe.getX();
        ItemStack output = recipe.getY();
        FluidStack byproduct = recipe.getZ();

        if(tanks[0].getFluidAmount() < fillReq) return false;
        if(byproduct != null && tanks[1].getFluid() != null) {
            if(byproduct.getFluid() != tanks[1].getFluid().getFluid()) return false;
            if(byproduct.amount + tanks[1].getFluidAmount() > tanks[1].getCapacity()) return false;
        }
        if(output != null && !inventory.getStackInSlot(0).isEmpty()) {
            if(output.getItem() != inventory.getStackInSlot(0).getItem()) return false;
            if(output.getItemDamage() != inventory.getStackInSlot(0).getItemDamage()) return false;
            return output.getCount() + inventory.getStackInSlot(0).getCount() <= output.getMaxStackSize();
        }

        return true;
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        super.networkUnpack(nbt);

        this.wasOn = nbt.getBoolean("wasOn");
        this.heat = nbt.getInteger("heat");
        this.progress = nbt.getInteger("progress");
        if(nbt.hasKey("tanks")){
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    protected void tryPullHeat() {

        if(this.heat >= maxHeat) return;

        TileEntity con = world.getTileEntity(pos.add(0, -1, 0));

        if(con instanceof IHeatSource source) {
            int diff = source.getHeatStored() - this.heat;

            if(diff == 0) {
                return;
            }

            if(diff > 0) {
                diff = (int) Math.ceil(diff * diffusion);
                source.useUpHeat(diff);
                this.heat += diff;
                if(this.heat > maxHeat)
                    this.heat = maxHeat;
                return;
            }
        }

        this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return new int[] { 1 };
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("tanks")){
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
        this.progress = nbt.getInteger("prog");
        this.heat = nbt.getInteger("heat");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
        nbt.setInteger("prog", progress);
        nbt.setInteger("heat", heat);
        return super.writeToNBT(nbt);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineCoker(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineCoker(player.inventory, this);
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if(tags.length == 2) {
            tanks[0].readFromNBT(tags[0]);
            tanks[1].readFromNBT(tags[1]);
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0] };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource == null) return 0;

        if(CokerRecipes.getOutput(resource.getFluid()) != null && (tanks[0].getFluid() == null || resource.isFluidEqual(tanks[0].getFluid()))) {
            return tanks[0].fill(resource, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(resource == null || tanks[1].getFluid() == null || resource.isFluidEqual(tanks[1].getFluid())) {
            return null;
        }
        return tanks[1].drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tanks[1].drain(maxDrain, doDrain);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        } else {
            return super.hasCapability(capability, facing);
        }
    }
}
