package com.hbm.tileentity.machine.oil;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.HydrotreatingRecipes;
import com.hbm.inventory.container.ContainerMachineHydrotreater;
import com.hbm.inventory.gui.GUIMachineHydrotreater;
import com.hbm.items.ModItems;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;

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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineHydrotreater extends TileEntityMachineBase implements ITickable, IGUIProvider, IFluidHandler, IEnergyUser, ITankPacketAcceptor {

    public long power;
    public static final long maxPower = 1_000_000;

    public FluidTank[] tanks;

    public TileEntityMachineHydrotreater() {
        super(11);

        this.tanks = new FluidTank[4];
        this.tanks[0] = new FluidTank(64_000);
        this.tanks[1] = new FluidTank(64_000);
        this.tanks[2] = new FluidTank(24_000);
        this.tanks[3] = new FluidTank(24_000);
    }

    @Override
    public String getName() {
        return "container.hydrotreater";
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            if(this.world.getTotalWorldTime() % 20 == 0) this.updateConnections();
            power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
            tryMoveBattery();

            if(this.inputValidForRecipe()) //checking if the containers fluid has a recipe
                FFUtils.fillFromFluidContainer(inventory, tanks[0],2, 3);
            if(this.inputValidForTank()) //checking if the containers fluid has a recipe
                FFUtils.fillFromFluidContainer(inventory, tanks[1],4, 5);

            if(world.getTotalWorldTime() % 2 == 0) reform();

            FFUtils.fillFluidContainer(inventory, tanks[2], 6, 7);
            FFUtils.fillFluidContainer(inventory, tanks[3], 8, 9);

            for(DirPos pos : getConPos()) {
                for(int i = 2; i < 4; i++) {
                    if(tanks[i].getFluidAmount() > 0) {
                        FFUtils.fillFluid(this, tanks[i], world, pos.getPos(), 8000);
                    }
                }
            }

            NBTTagCompound data = new NBTTagCompound();
            data.setLong("power", this.power);
            data.setTag("tanks", FFUtils.serializeTankArray(tanks));
            this.networkPack(data, 150);
        }
    }

    protected boolean inputValidForRecipe(){
        if(!inventory.getStackInSlot(2).isEmpty()){
            FluidStack containerFluid = FluidUtil.getFluidContained(inventory.getStackInSlot(2));
            if(containerFluid != null){
                return HydrotreatingRecipes.getOutput(containerFluid.getFluid()) != null;
            }
        }
        return false;
    }

    protected boolean inputValidForTank(){
        if(!inventory.getStackInSlot(4).isEmpty()){
            FluidStack containerFluid = FluidUtil.getFluidContained(inventory.getStackInSlot(4));
            if(containerFluid != null && tanks[0].getFluid() != null){
                Triplet<FluidStack, FluidStack, FluidStack> out = HydrotreatingRecipes.getOutput(tanks[0].getFluid().getFluid());
                return out != null && containerFluid.getFluid() == out.getX().getFluid();
            }
        }
        return false;
    }

    public void tryMoveBattery() {
        ItemStack itemStackDrain = inventory.getStackInSlot(0);
        if (itemStackDrain.getItem() instanceof IBatteryItem itemDrain) {
            if (itemDrain.getCharge(itemStackDrain) == 0) {
                inventory.getStackInSlot(1);
                if (inventory.getStackInSlot(1).isEmpty()) {
                    inventory.setStackInSlot(1, itemStackDrain);
                    inventory.setStackInSlot(0, ItemStack.EMPTY);
                }
            }
        }
    }

    private void reform() {
        if(power < 20_000) return;
        if(tanks[0].getFluidAmount() < 100 || tanks[0].getFluid() == null) return;

        Triplet<FluidStack, FluidStack, FluidStack> out = HydrotreatingRecipes.getOutput(tanks[0].getFluid().getFluid());
        if(out == null) {
            return;
        }

        if(tanks[1].getFluidAmount() < out.getX().amount || !out.getX().isFluidEqual(tanks[1].getFluid())) return;
        if(inventory.getStackInSlot(10).isEmpty() || inventory.getStackInSlot(10).getItem() != ModItems.catalytic_converter) return;

        if(tanks[2].getFluidAmount() + out.getY().amount > tanks[2].getCapacity() || (tanks[2].getFluid() != null && !out.getY().isFluidEqual(tanks[2].getFluid()))) return;
        if(tanks[3].getFluidAmount() + out.getZ().amount > tanks[3].getCapacity() || (tanks[3].getFluid() != null && !out.getZ().isFluidEqual(tanks[3].getFluid()))) return;

        tanks[0].drain(100, true);
        tanks[1].drain(out.getX().amount, true);
        tanks[2].fill(out.getY(), true);
        tanks[3].fill(out.getZ(), true);

        power -= 20_000;
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {

        this.power = nbt.getLong("power");
        if(nbt.hasKey("tanks")){
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    private void updateConnections() {
        for(DirPos pos : getConPos()) {
            this.trySubscribe(world, pos.getPos(), pos.getDir());
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

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        power = nbt.getLong("power");
        if(nbt.hasKey("tanks")){
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("power", power);
        nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
        return super.writeToNBT(nbt);
    }

    AxisAlignedBB bb = null;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        if(bb == null) {
            bb = new AxisAlignedBB(
                    pos.getX() - 1,
                    pos.getY(),
                    pos.getZ() - 1,
                    pos.getX() + 2,
                    pos.getY() + 7,
                    pos.getZ() + 2
            );
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override public long getPower() { return power; }
    @Override public void setPower(long power) { this.power = power; }
    @Override public long getMaxPower() { return maxPower; }
    @Override public boolean canConnect(ForgeDirection dir) { return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN; }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineHydrotreater(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineHydrotreater(player.inventory, this);
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if(tags.length == 4){
            tanks[0].readFromNBT(tags[0]);
            tanks[1].readFromNBT(tags[1]);
            tanks[2].readFromNBT(tags[2]);
            tanks[3].readFromNBT(tags[3]);
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0], tanks[3].getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource == null) return 0;

        if(HydrotreatingRecipes.getOutput(resource.getFluid()) != null && (tanks[0].getFluid() == null || resource.isFluidEqual(tanks[0].getFluid()))) {
            return tanks[0].fill(resource, doFill);
        }
        if(tanks[0].getFluid() != null){
            Triplet<FluidStack, FluidStack, FluidStack> out = HydrotreatingRecipes.getOutput(tanks[0].getFluid().getFluid());
            if(out != null && out.getX().isFluidEqual(resource)){
                return tanks[1].fill(resource, doFill);
            }
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(resource == null)
            return null;
        if (resource.isFluidEqual(tanks[2].getFluid())) {
            return tanks[2].drain(resource.amount, doDrain);
        }
        if (resource.isFluidEqual(tanks[3].getFluid())) {
            return tanks[3].drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tanks[2].getFluid() != null){
            return tanks[2].drain(maxDrain, doDrain);
        } else if(tanks[3].getFluid() != null){
            return tanks[3].drain(maxDrain, doDrain);
        }
        return null;
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
