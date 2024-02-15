package com.hbm.tileentity.machine;

import api.hbm.tile.IHeatSource;
import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.HeatRecipes;
import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.inventory.gui.GUIHeaterHeatex;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.ForgeDirection;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.FluidTypePacketTest;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityHeaterHeatex extends TileEntityMachineBase implements IHeatSource, IControlReceiver, IGUIProvider, ITickable, ITankPacketAcceptor, IFluidHandler {

    public FluidTank[] tanks;

    public Fluid[] tankTypes;

    public int amountToCool = 1;
    public int tickDelay = 1;
    public int heatGen;
    public int heatEnergy;

    public TileEntityHeaterHeatex() {
        super(1);

        this.tanks = new FluidTank[2];
        this.tankTypes = new Fluid[2];

        this.tanks[0] = new FluidTank(ModForgeFluids.hotcoolant, 0, 24_000);
        this.tankTypes[0] = ModForgeFluids.hotcoolant;
        this.tanks[1] = new FluidTank(ModForgeFluids.coolant, 0, 24_000);
        this.tankTypes[1] = ModForgeFluids.coolant;
    }

    @Override
    public String getName() {
        return "container.heaterHeatex";
    }

    @Override
    public void update() {

        if (!world.isRemote) {

            // first, update current tank settings
            setFluidType();

            PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[]{tanks[0], tanks[1]}), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
            PacketDispatcher.wrapper.sendToAllAround(new FluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), tankTypes), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

            this.heatEnergy *= 0.999;

            this.tryConvert();

            NBTTagCompound data = new NBTTagCompound();
            data.setInteger("heatGen", heatGen);
            data.setInteger("heatEnergy", heatEnergy);
            data.setInteger("toCool", amountToCool);
            data.setInteger("delay", tickDelay);
            data.setString("tankTypes0", tankTypes[0].getName());
            data.setString("tankTypes1", tankTypes[1].getName());
            data.setTag("tanks", FFUtils.serializeTankArray(tanks));
            INBTPacketReceiver.networkPack(this, data, 25);

            fillFluidInit(tanks[1]);
            markDirty();
        }
    }

    public void fillFluidInit(FluidTank tank) {
        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        FFUtils.fillFluid(this, tank, world, pos.add(dir.offsetX * 2 + rot.offsetX, 0, dir.offsetZ * 2 + rot.offsetZ), 12000);
        FFUtils.fillFluid(this, tank, world, pos.add(dir.offsetX * 2 - rot.offsetX, 0, dir.offsetZ * 2 - rot.offsetZ), 12000);
        FFUtils.fillFluid(this, tank, world, pos.add(-dir.offsetX * 2 + rot.offsetX, 0, -dir.offsetZ * 2 + rot.offsetZ), 12000);
        FFUtils.fillFluid(this, tank, world, pos.add(-dir.offsetX * 2 - rot.offsetX, 0, -dir.offsetZ * 2 - rot.offsetZ), 12000);
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        this.heatGen = nbt.getInteger("heatGen");
        this.heatEnergy = nbt.getInteger("heatEnergy");
        this.amountToCool = nbt.getInteger("toCool");
        this.tickDelay = nbt.getInteger("delay");

        if(nbt.hasKey("tankTypes0")) tankTypes[0] = FluidRegistry.getFluid(nbt.getString("tankTypes0"));
        if(nbt.hasKey("tankTypes1")) tankTypes[1] = FluidRegistry.getFluid(nbt.getString("tankTypes1"));
        if (nbt.hasKey("tanks")) {
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    public void setFluidType(){
        ItemStack inFluid = this.inventory.getStackInSlot(0);
        if(inFluid.getItem() == ModItems.forge_fluid_identifier) {
            setFluidTypes(ItemForgeFluidIdentifier.getType(inFluid));
        }
        if(tankTypes[0] == null) setFluidTypes(ModForgeFluids.hotcoolant);
    }

    public void setFluidTypes(Fluid f){
        if(HeatRecipes.hasCoolRecipe(f) && tankTypes[0] != f) {
            tankTypes[0] = f;
            tankTypes[1] = HeatRecipes.getCoolFluid(f);
            // clear input tank fluid
            tanks[0].setFluid(new FluidStack(f, 0));
            tanks[1].setFluid(new FluidStack(tankTypes[1], 0));
            this.markDirty();
        }
    }

    protected void tryConvert() {
        if(tickDelay < 1) tickDelay = 1;
        if(world.getTotalWorldTime() % tickDelay != 0) return;

        if(!HeatRecipes.hasCoolRecipe(tankTypes[0])) {
            return;
        }

        int amountReq = HeatRecipes.getInputAmountCold(tankTypes[0]);
        int amountProduced = HeatRecipes.getOutputAmountCold(tankTypes[0]);
        int heat = HeatRecipes.getResultingHeat(tankTypes[0]);

        int inputOps = tanks[0].getFluidAmount() / amountReq;
        int outputOps = (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / amountProduced;
        int opCap = this.amountToCool;

        int ops = Math.min(inputOps, Math.min(outputOps, opCap));
        tanks[0].drain(ops * amountReq, true);
        tanks[1].fill(new FluidStack(tankTypes[1], ops * amountProduced), true);

        this.heatGen = (heat * ops)>>1;
        this.heatEnergy += heatGen;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.heatEnergy = nbt.getInteger("heatEnergy");
        this.amountToCool = nbt.getInteger("toCool");
        this.tickDelay = nbt.getInteger("delay");

        if (nbt.hasKey("tanks")) {
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
        if(nbt.hasKey("tankTypes0")) tankTypes[0] = FluidRegistry.getFluid(nbt.getString("tankTypes0"));
        if(nbt.hasKey("tankTypes1")) tankTypes[1] = FluidRegistry.getFluid(nbt.getString("tankTypes1"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("heatEnergy", heatEnergy);
        nbt.setInteger("toCool", amountToCool);
        nbt.setInteger("delay", tickDelay);

        nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
        nbt.setString("tankTypes0", tankTypes[0].getName());
        nbt.setString("tankTypes1", tankTypes[1].getName());
        return super.writeToNBT(nbt);
    }

    @Override
    public int getHeatStored() {
        return heatEnergy;
    }

    @Override
    public void useUpHeat(int heat) {
        this.heatEnergy = Math.max(0, this.heatEnergy - heat);
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if (tags.length != 2) {
            return;
        } else {
            tanks[0].readFromNBT(tags[0]);
            tanks[1].readFromNBT(tags[1]);
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluid() == tankTypes[0] && resource.amount > 0) {
            return tanks[0].fill(resource, doFill);
        }

        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource != null && resource.getFluid() == tankTypes[1] && resource.amount > 0) {
            return tanks[1].drain(resource, doDrain);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain > 0) {
            return tanks[1].drain(maxDrain, doDrain);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
            if (facing == dir.toEnumFacing().getOpposite() || facing == dir.toEnumFacing() || facing == null) {
                return true;
            }
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
            if (facing == dir.toEnumFacing().getOpposite() || facing == dir.toEnumFacing() || facing == null) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
            }
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerHeaterHeatex(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIHeaterHeatex(player.inventory, this);
    }

    AxisAlignedBB bb = null;

    @Override
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {

        if (bb == null) {
            bb = new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2);
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public boolean hasPermission(EntityPlayer player) {
        return player.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 16;
    }

    @Override
    public void receiveControl(NBTTagCompound data) {
        if (data.hasKey("toCool")) this.amountToCool = Math.max(data.getInteger("toCool"), 1);
        if (data.hasKey("delay")) this.tickDelay = Math.max(data.getInteger("delay"), 1);

        markDirty();
    }
}
