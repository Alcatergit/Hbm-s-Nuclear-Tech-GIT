package com.hbm.tileentity.machine.oil;

import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.LiquefactionRecipes;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerLiquefactor;
import com.hbm.inventory.gui.GUILiquefactor;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.DirPos;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class TileEntityMachineLiquefactor extends TileEntityMachineBase implements ITickable, IGUIProvider, IEnergyUser, IFluidHandler, ITankPacketAcceptor {

    public long power;
    public static final long maxPower = 100000;
    public static final int usageBase = 500;
    public int usage;
    public int progress;
    public static final int processTimeBase = 200;
    public boolean needsUpdate = false;
    public int processTime;

    public Fluid fluidType = null;
    public FluidTank tank;

    private final UpgradeManager upgradeManager = new UpgradeManager();

    public TileEntityMachineLiquefactor() {
        super(4);
        tank = new FluidTank(24000);
    }

    @Override
    public String getName() {
        return "container.machineLiquefactor";
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            this.power = Library.chargeTEFromItems(inventory, 1, power, maxPower);

            this.updateConnections();

            upgradeManager.eval(inventory, 2, 3);
            int speed = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.SPEED), 3);
            int power = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.POWER), 3);

            this.processTime = processTimeBase - (processTimeBase / 4) * speed;
            this.usage = (usageBase + (usageBase * speed)) / (power + 1);

            if(this.canProcess())
                this.process();
            else
                this.progress = 0;

            if(world.getTotalWorldTime() % 10 == 0) {
                sendFluid();
            }

            NBTTagCompound data = new NBTTagCompound();
            if(fluidType != null) data.setString("fluidType", fluidType.getName());
            data.setLong("power", this.power);
            data.setInteger("progress", this.progress);
            data.setInteger("usage", this.usage);
            data.setInteger("processTime", this.processTime);
            tank.writeToNBT(data);
            this.networkPack(data, 50);
        }
    }

    private void updateConnections() {
        for(DirPos pos : getConPos()) {
            this.trySubscribe(world, pos.getPos(), pos.getDir());
        }
    }

    private void sendFluid() {
        for(DirPos pos : getConPos()) {
            if(tank.getFluidAmount() > 0)
                FFUtils.fillFluid(this, tank, world, pos.getPos(), 8000);
        }
    }

    private DirPos[] getConPos() {
        return new DirPos[] {
                new DirPos(pos.getX(), pos.getY() + 4, pos.getZ(), Library.POS_Y),
                new DirPos(pos.getX(), pos.getY() - 1, pos.getZ(), Library.NEG_Y),
                new DirPos(pos.getX() + 2, pos.getY() + 1, pos.getZ(), Library.POS_X),
                new DirPos(pos.getX() - 2, pos.getY() + 1, pos.getZ(), Library.NEG_X),
                new DirPos(pos.getX(), pos.getY() + 1, pos.getZ() + 2, Library.POS_Z),
                new DirPos(pos.getX(), pos.getY() + 1, pos.getZ() - 2, Library.NEG_Z)
        };
    }

    public boolean canProcess() {

        if(this.power < usage)
            return false;

        if(inventory.getStackInSlot(0).isEmpty())
            return false;

        FluidStack out = LiquefactionRecipes.getOutput(inventory.getStackInSlot(0));

        if(out == null)
            return false;

        if(tank.getFluidAmount() > 0 && tank.getFluid() != null && out.getFluid() != tank.getFluid().getFluid())
            return false;
        fluidType = out.getFluid();

        return out.amount + tank.getFluidAmount() <= tank.getCapacity();
    }

    public void process() {

        this.power -= usage;

        progress++;

        if(progress >= processTime) {

            FluidStack out = LiquefactionRecipes.getOutput(inventory.getStackInSlot(0));
            tank.fill(out.copy(), true);
            inventory.getStackInSlot(0).shrink(1);
            if(inventory.getStackInSlot(0).isEmpty())
                inventory.setStackInSlot(0, ItemStack.EMPTY);

            progress = 0;

            this.markDirty();
        }
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if(tags.length != 1) {
            return;
        }
        tank.readFromNBT(tags[0]);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == 0 && LiquefactionRecipes.getOutput(itemStack) != null;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int j) {
        return LiquefactionRecipes.getOutput(itemStack) == null;
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        this.power = nbt.getLong("power");
        this.progress = nbt.getInteger("progress");
        this.usage = nbt.getInteger("usage");
        this.processTime = nbt.getInteger("processTime");
        if(nbt.hasKey("fluidType"))
           this.fluidType  = FluidRegistry.getFluid(nbt.getString("fluidType"));
        tank.readFromNBT(nbt);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] { tank.getTankProperties()[0] };
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.tank.readFromNBT(nbt.getCompoundTag("tank"));
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(nbt);
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public long getPower() {
        return power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
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
                    pos.getY() + 4,
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


    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerLiquefactor(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUILiquefactor(player.inventory, this);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return true;
        } else {
            return super.hasCapability(capability, facing);
        }
    }
}
