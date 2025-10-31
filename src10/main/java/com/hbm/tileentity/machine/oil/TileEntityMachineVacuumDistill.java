package com.hbm.tileentity.machine.oil;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.VacuumDistillRecipes;
import com.hbm.inventory.container.ContainerMachineVacuumDistill;
import com.hbm.inventory.gui.GUIMachineVacuumDistill;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Quartet;

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
import org.jetbrains.annotations.NotNull;

public class TileEntityMachineVacuumDistill extends TileEntityMachineBase implements ITickable, IGUIProvider, IFluidHandler, IEnergyUser, ITankPacketAcceptor {

    public long power;
    public static final long maxPower = 1_000_000;

    public FluidTank[] tanks;

    private AudioWrapper audio;
    private int audioTime;
    public boolean isOn;

    public TileEntityMachineVacuumDistill() {
        super(12);

        this.tanks = new FluidTank[5];
        this.tanks[0] = new FluidTank(64_000);
        this.tanks[1] = new FluidTank(24_000);
        this.tanks[2] = new FluidTank(24_000);
        this.tanks[3] = new FluidTank(24_000);
        this.tanks[4] = new FluidTank(24_000);
    }

    @Override
    public String getName() {
        return "container.vacuumDistill";
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            this.isOn = false;

            this.updateConnections();
            power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
            tryMoveBattery();

            if(this.inputValidForRecipe()) //checking if the containers fluid has a recipe
                FFUtils.fillFromFluidContainer(inventory, tanks[0],2, 3);

            refine();

            FFUtils.fillFluidContainer(inventory, tanks[1], 4, 5);
            FFUtils.fillFluidContainer(inventory, tanks[2], 6, 7);
            FFUtils.fillFluidContainer(inventory, tanks[3], 8, 9);
            FFUtils.fillFluidContainer(inventory, tanks[4], 10, 11);

            for(DirPos pos : getConPos()) {
                for(int i = 1; i < 5; i++) {
                    if(tanks[i].getFluidAmount() > 0) {
                        FFUtils.fillFluid(this, tanks[i], world, pos.getPos(), 6000);
                    }
                }
            }

            NBTTagCompound data = new NBTTagCompound();
            data.setLong("power", this.power);
            data.setBoolean("isOn", this.isOn);
            data.setTag("tanks", FFUtils.serializeTankArray(tanks));
            this.networkPack(data, 150);
        } else {

            if(this.isOn) audioTime = 20;

//            if(audioTime > 0) {
//
//                audioTime--;
//
//                if(audio == null) {
//                    audio = createAudioLoop();
//                    audio.startSound();
//                } else if(!audio.isPlaying()) {
//                    audio = rebootAudio(audio);
//                }
//
//                audio.updateVolume(getVolume(1F));
//                audio.keepAlive();
//
//            } else {
//
//                if(audio != null) {
//                    audio.stopSound();
//                    audio = null;
//                }
//            }
        }
    }

//    @Override
//    public AudioWrapper createAudioLoop() {
//        return MainRegistry.proxy.getLoopedSound(HBMSoundHandler.boiler, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), 0.25F, 15F);
//    }

//    @Override
//    public void onChunkUnload() {
//
//        if(audio != null) {
//            audio.stopSound();
//            audio = null;
//        }
//    }
//
//    @Override
//    public void invalidate() {
//
//        super.invalidate();
//
//        if(audio != null) {
//            audio.stopSound();
//            audio = null;
//        }
//    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {

        this.power = nbt.getLong("power");
        this.isOn = nbt.getBoolean("isOn");
        if(nbt.hasKey("tanks")){
            FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        }
    }

    protected boolean inputValidForRecipe(){
        if(!inventory.getStackInSlot(2).isEmpty()){
            FluidStack containerFluid = FluidUtil.getFluidContained(inventory.getStackInSlot(2));
            if(containerFluid != null){
                return VacuumDistillRecipes.getVacuum(containerFluid.getFluid()) != null;
            }
        }
        return false;
    }

    private void refine() {
        if(tanks[0].getFluid() == null) return;
        Quartet<FluidStack, FluidStack, FluidStack, FluidStack> refinery = VacuumDistillRecipes.getVacuum(tanks[0].getFluid().getFluid());
        if(refinery == null) return;

        FluidStack[] stacks = new FluidStack[] {refinery.getW(), refinery.getX(), refinery.getY(), refinery.getZ()};

        if(power < 10_000) return;
        if(tanks[0].getFluidAmount() < 100) return;
        for(int i = 0; i < stacks.length; i++) {
            if(tanks[i + 1].getFluid() == null) continue;
            if(tanks[i + 1].getFluid().getFluid() != stacks[i].getFluid()) return;
            if(tanks[i + 1].getFluidAmount() + stacks[i].amount > tanks[i + 1].getCapacity())
                return;
        }
        this.isOn = true;
        power -= 10_000;
        tanks[0].drain(100, true);

        for(int i = 0; i < stacks.length; i++) {
            tanks[i + 1].fill(stacks[i], true);
        }
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
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
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
                    pos.getY() + 9,
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
    public long getPower() {
        return power;
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public boolean canConnect(ForgeDirection dir) {
        return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerMachineVacuumDistill(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIMachineVacuumDistill(player.inventory, this);
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if(tags.length == 5){
            tanks[0].readFromNBT(tags[0]);
            tanks[1].readFromNBT(tags[1]);
            tanks[2].readFromNBT(tags[2]);
            tanks[3].readFromNBT(tags[3]);
            tanks[4].readFromNBT(tags[4]);
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0], tanks[3].getTankProperties()[0], tanks[4].getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource == null) return 0;

        if(VacuumDistillRecipes.getVacuum(resource.getFluid()) != null && (tanks[0].getFluid() == null || resource.isFluidEqual(tanks[0].getFluid()))) {
            return tanks[0].fill(resource, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(resource == null)
            return null;
        if (resource.isFluidEqual(tanks[1].getFluid())) {
            return tanks[1].drain(resource.amount, doDrain);
        }
        if (resource.isFluidEqual(tanks[2].getFluid())) {
            return tanks[2].drain(resource.amount, doDrain);
        }
        if (resource.isFluidEqual(tanks[3].getFluid())) {
            return tanks[3].drain(resource.amount, doDrain);
        }
        if (resource.isFluidEqual(tanks[4].getFluid())) {
            return tanks[4].drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (tanks[1].getFluid() != null) {
            return tanks[1].drain(maxDrain, doDrain);
        } else if(tanks[2].getFluid() != null){
            return tanks[2].drain(maxDrain, doDrain);
        } else if(tanks[3].getFluid() != null){
            return tanks[3].drain(maxDrain, doDrain);
        } else if(tanks[4].getFluid() != null){
            return tanks[4].drain(maxDrain, doDrain);
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
