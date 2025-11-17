package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.container.ContainerMachineStrandCaster;
import com.hbm.inventory.gui.GUIMachineStrandCaster;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TileEntityMachineStrandCaster extends TileEntityFoundryCastingBase implements IGUIProvider, IFluidHandler, ITankPacketAcceptor, INBTPacketReceiver {

    public static final int duration = 200;
    public static final int moldCount = 10;
    public FluidTank[] tanks;
    public long lastProgressTick = 0;
    public boolean isOn = false;
    private static final int invSize = 7;

    public @NotNull String getName() {
        return "container.machineStrandCaster";
    }

    public TileEntityMachineStrandCaster() {
        super(invSize);
        this.tanks = new FluidTank[2];
        this.tanks[0] = new FluidTank(FluidRegistry.WATER, 0, 64_000);
        this.tanks[1] = new FluidTank(ModForgeFluids.SPENTSTEAM, 0, 64_000);
    }

    @Override
    public void update() {

        if (!world.isRemote) {

            if (this.lastType != this.type || this.lastAmount != this.amount) {
                IBlockState state = world.getBlockState(pos);
                world.markAndNotifyBlock(pos, world.getChunk(pos), state, state, 3);
                this.lastType = this.type;
                this.lastAmount = this.amount;
            }

            // In case of overfill problems, spit out the excess as scrap
            if (amount > getCapacity()) {
                ItemStack scrap = ItemScraps.create(new Mats.MaterialStack(type, Math.max(amount - getCapacity(), 0)));
                EntityItem item = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 2, pos.getZ() + 0.5, scrap);
                world.spawnEntity(item);
                this.amount = this.getCapacity();
            }

            if (this.amount == 0) {
                this.type = null;
            }


            int moldsToCast = maxProcessable();
            // Makes it flush the buffers after 10 seconds of inactivity, or when they're full
            isOn = moldsToCast > 0;
            if (moldsToCast > 0 && world.getTotalWorldTime() > lastProgressTick + duration) {

                ItemMold.Mold mold = this.getInstalledMold();

                this.amount -= moldsToCast * mold.getCost();

                ItemStack out = mold.getOutput(type);
                final int itemsPerCast = out.getCount();
                final int initialRemaining = itemsPerCast * moldsToCast;
                int remaining = initialRemaining;

                final int itemMaxStackSize = out.getMaxStackSize();

                for (int i = 1; i < 7 && remaining > 0; i++) {
                    ItemStack slotStack = inventory.getStackInSlot(i);
                    int slotLimit = Math.min(inventory.getSlotLimit(i), itemMaxStackSize);

                    if (slotStack.isEmpty()) {
                        int toDeposit = Math.min(remaining, slotLimit);
                        if (toDeposit > 0) {
                            ItemStack put = out.copy();
                            put.setCount(toDeposit);
                            inventory.setStackInSlot(i, put);
                            remaining -= toDeposit;
                        }
                        continue;
                    }

                    if (ItemHandlerHelper.canItemStacksStack(slotStack, out)) {
                        int toDeposit = Math.min(remaining, slotLimit - slotStack.getCount());
                        if (toDeposit > 0) {
                            slotStack.grow(toDeposit);
                            inventory.setStackInSlot(i, slotStack);
                            remaining -= toDeposit;
                        }
                    }
                }

                int producedItems = initialRemaining - remaining;
                int castsMade = producedItems / itemsPerCast;

                if (castsMade > 0) {
                    tanks[0].drain(getWaterRequired() * castsMade, true);
                    tanks[1].fill(new FluidStack(ModForgeFluids.SPENTSTEAM, getWaterRequired() * castsMade), true);

                    markDirty();
                    lastProgressTick = world.getTotalWorldTime();
                }
            }
            fillFluidInit();
            PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[]{tanks[0], tanks[1]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("o", isOn);
            data.setLong("p", lastProgressTick);
            data.setTag("tanks", FFUtils.serializeTankArray(tanks));
            INBTPacketReceiver.networkPack(this, data, 25);
        }
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        lastProgressTick = nbt.getLong("p");
        isOn = nbt.getBoolean("o");
        if(nbt.hasKey("tanks")) {
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    public double getProgress(){
        if(!isOn) return 0;
        return  Math.max(0, Math.min(1, (world.getTotalWorldTime() - lastProgressTick) / (double)(duration-1)));
    }

    private int maxProcessable() {
        ItemMold.Mold mold = this.getInstalledMold();
        if (type == null || mold == null) {
            return 0;
        } else {
            ItemStack out = mold.getOutput(type);
            if(out == null || out.isEmpty()) return 0;
        }


        int freeSlots = 0;
        final int stackLimit = mold.getOutput(type).getMaxStackSize();

        for (int i = 1; i < 7; i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                freeSlots += stackLimit;
            } else if (itemStack.isItemEqual(mold.getOutput(type))) {
                freeSlots += stackLimit - itemStack.getCount();
            }
        }

        int moldsToCast = Math.min(moldCount, amount / mold.getCost());
        moldsToCast = Math.min(moldsToCast, freeSlots / mold.getOutput(type).getCount());
        moldsToCast = Math.min(moldsToCast, tanks[0].getFluidAmount() / getWaterRequired());
        moldsToCast = Math.min(moldsToCast, (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / getWaterRequired());

        return moldsToCast;
    }

    public DirPos[] getFluidConPos() {

        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        return new DirPos[]{
                new DirPos(pos.getX() + rot.offsetX * 2 - dir.offsetX, pos.getY(), pos.getZ() + rot.offsetZ * 2 - dir.offsetZ, rot),
                new DirPos(pos.getX() - rot.offsetX - dir.offsetX, pos.getY(), pos.getZ() - rot.offsetZ - dir.offsetZ, rot.getOpposite()),
                new DirPos(pos.getX() + rot.offsetX * 2 - dir.offsetX * 5, pos.getY(), pos.getZ() + rot.offsetZ * 2 - dir.offsetZ * 5, rot),
                new DirPos(pos.getX() - rot.offsetX - dir.offsetX * 5, pos.getY(), pos.getZ() - rot.offsetZ - dir.offsetZ * 5, rot.getOpposite())
        };
    }

    public int[][] getMetalPourPos() {

        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        return new int[][]{
                new int[]{pos.getX() + rot.offsetX - dir.offsetX, pos.getY() + 2, pos.getZ() + rot.offsetZ - dir.offsetZ},
                new int[]{pos.getX() - dir.offsetX, pos.getY() + 2, pos.getZ() - dir.offsetZ},
                new int[]{pos.getX() + rot.offsetX, pos.getY() + 2, pos.getZ() + rot.offsetZ},
                new int[]{pos.getX(), pos.getY() + 2, pos.getZ()}
        };
    }

    @Override
    public ItemMold.Mold getInstalledMold() {
        ItemStack itemStack = inventory.getStackInSlot(0);
        if (itemStack.isEmpty()) return null;

        if (itemStack.getItem() instanceof ItemMold mold) {
            return mold.getMold(itemStack);
        }

        return null;
    }

    @Override
    public int getMoldSize() {
        return getInstalledMold().size;
    }

    @Override
    public boolean canAcceptPartialPour(World world, BlockPos pos, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {
        if (side != ForgeDirection.UP) return false;
        for (int[] pourPos : getMetalPourPos()) {
            if (pourPos[0] == pos.getX() && pourPos[1] == pos.getY() && pourPos[2] == pos.getZ()) {
                return this.standardCheck(world, pos, side, stack);
            }
        }
        return false;
    }

    @Override
    public boolean standardCheck(World world, BlockPos pos, ForgeDirection side, Mats.MaterialStack stack) {
        if (this.type != null && this.type != stack.material) return false;
        return !(this.amount >= this.getCapacity() || getInstalledMold() == null);
    }

    @Override
    public int getCapacity() {
        ItemMold.Mold mold = this.getInstalledMold();
        return mold == null ? 50000 : mold.getCost() * moldCount * 2;
    }

    private int getWaterRequired() {
        return getInstalledMold() != null ? 5 * getInstalledMold().getCost() : 50;
    }

    private void fillFluidInit() {
        for(DirPos dirPos : getFluidConPos()) {
            FFUtils.fillFluid(this, tanks[1], world, dirPos.getPos(), 32000);
        }
    }

    @Override
    public Mats.MaterialStack standardAdd(World world, BlockPos pos, ForgeDirection side, Mats.MaterialStack stack) {
        this.type = stack.material;
        int limit = this.getCapacity();
        if (stack.amount + this.amount <= limit) {
            this.amount += stack.amount;
            return null;
        }

        int required = limit - this.amount;
        this.amount = limit;

        stack.amount -= required;

        lastProgressTick = world.getTotalWorldTime();

        return stack;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineStrandCaster(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiInfoContainer provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineStrandCaster(player.inventory, this);
    }


    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
        nbt.setLong("t", lastProgressTick);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("tanks")) {
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
        lastProgressTick = nbt.getLong("t");
    }

    @Override
    public boolean isItemValidForSlot(int i, @NotNull ItemStack stack) {
        if (i == 0) return stack.getItem() == ModItems.mold;
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing side) {
        return new int[]{1, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
        return this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return !this.isItemValidForSlot(slot, itemStack);
    }

    AxisAlignedBB bb = null;

    @Override
    public @NotNull AxisAlignedBB getRenderBoundingBox() {

        if (bb == null) {
            bb = new AxisAlignedBB(
                    pos.getX() - 7,
                    pos.getY(),
                    pos.getZ() - 7,
                    pos.getX() + 7,
                    pos.getY() + 3,
                    pos.getZ() + 7);
        }
        return bb;
    }


    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if (tags.length == 2) {
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
        if (resource != null && resource.getFluid() == FluidRegistry.WATER) {
            return tanks[0].fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource != null && resource.getFluid() == ModForgeFluids.SPENTSTEAM) {
            return tanks[1].drain(resource, doDrain);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tanks[1].drain(maxDrain, doDrain);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }

        return super.getCapability(capability, facing);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        if(world.getTileEntity(pos) != this)
        {
            return false;
        }else{
            return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=128;
        }
    }
}