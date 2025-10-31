package com.leafia.contents.machines.reactors.zirnox.container;

import api.hbm.energy.IEnergyGenerator;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.FluidTypePacketTest;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodItem;
import com.leafia.contents.machines.reactors.zirnox.debris.ZirnoxDebrisEntity;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.leafia.contents.machines.reactors.zirnox.debris.ZirnoxDebrisEntity.DebrisType.*;

public class ZirnoxTE extends TileEntityMachineBase implements LeafiaPacketReceiver, ITickable, IEnergyGenerator, ITankPacketAcceptor, IFluidHandler {
    public FluidTank[] tanks;
    public Fluid[] tankTypes;

    public byte rods = 0;
    public byte rodsTarget = 0;
    public double hulltemp = 20;
    public double meltingPoint = 800;
    public double pressure = 0;
    double lastPressure = 0;
    boolean kill = false;
    public double maxPressure = 30;
    public byte compression = 0;
    public int generosityTimer = 5*20;
    public double stressTimer = 300;
    public boolean valveOpen = false;
    public double avgHeat = 0;
    public enum packetKeys {
        HULL_TEMP(0),
        PRESSURE(1),
        CONTROL_RODS(2),
        COMPRESSION(3),
        CONTROL_RODS_TARGET(4),
        OPENVALVE(5);

        public byte key;
        packetKeys(int key) {
            this.key = (byte)key;
        }
    }
    public int dialX = 0;
    public int dialY = 0;
    private short getCompressionLevel(Fluid fluid) {
        if (fluid == ModForgeFluids.STEAM)
            return 1;
        else if (fluid == ModForgeFluids.HOTSTEAM)
            return 10;
        else if (fluid == ModForgeFluids.SUPERHOTSTEAM)
            return 100;
        else if (fluid == ModForgeFluids.ULTRAHOTSTEAM)
            return 1000;
        return 0;
    }
    public void setCompression(Fluid newType) {
        short curCompression = getCompressionLevel(this.tankTypes[2]);
        short newCompression = getCompressionLevel(newType);
        int amount = this.tanks[2].getFluidAmount();
        this.tanks[2].drain(this.tanks[2].getCapacity(),true);
        this.tankTypes[2] = newType;
        this.tanks[2].fill(new FluidStack(newType,amount*curCompression/newCompression),true);
    }
    private void updateCompression() {
        switch(compression) {
            case 0: setCompression(ModForgeFluids.STEAM); break;
            case 1: setCompression(ModForgeFluids.HOTSTEAM); break;
            case 2: setCompression(ModForgeFluids.SUPERHOTSTEAM); break;
            case 3: setCompression(ModForgeFluids.ULTRAHOTSTEAM); break;
        }
    }
    private void spawnDebris(ZirnoxDebrisEntity.DebrisType type) {
        ZirnoxDebrisEntity debris = new ZirnoxDebrisEntity(world, pos.getX() + 0.5D, pos.getY() + 4D, pos.getZ() + 0.5D, type);
        debris.motionX = world.rand.nextGaussian() * 0.75D;
        debris.motionZ = world.rand.nextGaussian() * 0.75D;
        debris.motionY = 0.01D + world.rand.nextDouble() * 1.25D;

        if(type == CONCRETE) {
            debris.motionX *= 0.25D;
            debris.motionY += world.rand.nextDouble();
            debris.motionZ *= 0.25D;
        }

        if(type == EXCHANGER) {
            debris.motionX += 0.5D;
            debris.motionY *= 0.1D;
            debris.motionZ += 0.5D;
        }

        world.spawnEntity(debris);
    }

    private void zirnoxDebris() {
        for(int i = 0; i < 2; i++) {
            spawnDebris(EXCHANGER);
        }
        for(int i = 0; i < 50; i++) {
            spawnDebris(CONCRETE);
            spawnDebris(BLANK);
        }
        for(int i = 0; i < 30; i++) {
            spawnDebris(SHRAPNEL);
        }
        for(int i = 0; i < 10; i++) {
            spawnDebris(ELEMENT);
            spawnDebris(GRAPHITE);
        }
    }
    public void explode() {
        ItemStack prevStack = null;
        for(int i = 0; i < inventory.getSlots(); i++) {
            prevStack = LeafiaRodItem.comparePriority(inventory.getStackInSlot(i),prevStack);
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }

        NBTTagCompound data = new NBTTagCompound();
        data.setString("type", "rbmkmush");
        data.setFloat("scale", 4);
        PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5, 250));
        MainRegistry.proxy.effectNT(data);

        int meta = this.getBlockMetadata();
        for (int ox = -2; ox <= 2; ox++) {
            for (int oz = -2; oz <= 2; oz++) {
                for (int oy = 2; ox <= 5; ox++) {
                    world.setBlockToAir(pos.add(ox,oy,oz));
                }
            }
        }
        world.playSound(null,pos.getX()+0.5,pos.getY()+2.5,pos.getZ()+0.5,HBMSoundEvents.rbmk_explosion,SoundCategory.BLOCKS,50.0F,1.0F);

        boolean nope = true;
        if (prevStack != null) {
            if (prevStack.getItem() instanceof LeafiaRodItem) {
                nope = false;
                LeafiaRodItem rod = (LeafiaRodItem)(prevStack.getItem());
                rod.resetDetonate();
                rod.detonateRadius = 18;
                rod.detonateVisualsOnly = true;
                rod.detonate(world,pos);
            }
        }
        if (nope) {
            ExplosionNukeGeneric.waste(world, pos.getX(), pos.getY(), pos.getZ(), 35);
            RadiationSavedData.incrementRad(world, pos, 3000F, 4000F);
        }

        int[] dimensions = {1, 0, 2, 2, 2, 2,};
        world.setBlockState(pos,ModBlocks.machine_zirnox_destroyed.getStateFromMeta(meta),3);
        MultiblockHandlerXR.fillSpace(world, pos.getX(),pos.getY(),pos.getZ(), dimensions, ModBlocks.machine_zirnox_destroyed, ForgeDirection.getOrientation(meta - BlockDummyable.offset));

        world.createExplosion(null, pos.getX()+0.5, pos.getY()+2.5, pos.getZ()+0.5, 24.0F, true);
        zirnoxDebris();

        if(MobConfig.enableElementals) {
            List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(100, 100, 100));

            for(EntityPlayer player : players) {
                player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
            }
        }
    }
    public ZirnoxTE() {
        super(28);
        tanks = new FluidTank[3];
        tankTypes = new Fluid[3];
        tanks[0] = new FluidTank(32000);
        tankTypes[0] = FluidRegistry.WATER;
        tanks[1] = new FluidTank(16000);
        tankTypes[1] = ModForgeFluids.CARBONDIOXIDE;
        tanks[2] = new FluidTank(8000);
        tankTypes[2] = ModForgeFluids.STEAM;
    }
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 24)
            return FFUtils.containsFluid(stack,tankTypes[1]);
        else if (slot == 26)
            return FFUtils.containsFluid(stack,tankTypes[0]);
        else if (slot < 24)
            return stack.getItem() instanceof LeafiaRodItem;
        else
            return false;
    }
    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
        return this.isItemValidForSlot(slot, itemStack);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        hulltemp = compound.getDouble("hulltemp");
        pressure = compound.getDouble("pressure");
        rods = compound.getByte("rods");
        rodsTarget = compound.getByte("rodsD");
        if(compound.hasKey("compression"))
            compression = compound.getByte("compression");
        tankTypes[0] = FluidRegistry.WATER;
        tankTypes[1] = ModForgeFluids.CARBONDIOXIDE;
        if(compression == 0){
            tankTypes[2] = ModForgeFluids.STEAM;
        } else if(compression == 1){
            tankTypes[2] = ModForgeFluids.HOTSTEAM;
        } else if(compression == 2){
            tankTypes[2] = ModForgeFluids.SUPERHOTSTEAM;
        } else if(compression == 3){
            tankTypes[2] = ModForgeFluids.ULTRAHOTSTEAM;
        }
        if(compound.hasKey("inventory"))
            inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        if(compound.hasKey("tanks"))
            FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setDouble("hulltemp", hulltemp);
        compound.setDouble("pressure", pressure);
        compound.setByte("rods", rods);
        compound.setByte("rodsD", rodsTarget);
        compound.setByte("compression", compression);
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
        return super.writeToNBT(compound);
    }

    @Override
    public long getPower() {
        return 0;
    }

    @Override
    public long getMaxPower() {
        return 0;
    }

    @Override
    public void setPower(long power) {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void recievePacket(NBTTagCompound[] tags) { // Fluid packets
        if(tags.length != 3) {
            return;
        } else {
            tanks[0].readFromNBT(tags[0]);
            tanks[1].readFromNBT(tags[1]);
            tanks[2].readFromNBT(tags[2]);
        }
    }

    @Override
    public String getName() {
        return "tile.machine_zirnox.name";
    }
    protected Fluid getFluidInSlot(int slot){
        if(!inventory.getStackInSlot(slot).isEmpty()){
            FluidStack stack = FluidUtil.getFluidContained(inventory.getStackInSlot(slot));
            if (stack != null)
                return stack.getFluid();
        }
        return null;
    }
    public int valveLevel = 0;
    int movedelay = 0;
    double getHeatInSlot(int slot,LeafiaRodItem rod) {
        return rod.getFlux(inventory.getStackInSlot(slot));
    }
    double handleLeafiaFuel(int slot,double cool) {
        ItemStack stack = inventory.getStackInSlot(slot);
        LeafiaRodItem rod = (LeafiaRodItem)stack.getItem();
        double detectedHeat = 0;
        for (int offset = 1; slot-offset*7 >= 0; offset += 1) {
            detectedHeat += getHeatInSlot(slot-offset*7,rod)/Math.pow(2,offset-1);
        }
        for (int offset = 1; slot+offset*7 < 24; offset += 1) {
            detectedHeat += getHeatInSlot(slot+offset*7,rod)/Math.pow(2,offset-1);
        }
        int smod = Math.floorMod(slot,7);
        if (smod <= 2) {
            for (int offset = 1; slot-offset >= 0; offset += 1) {
                detectedHeat += getHeatInSlot(slot-offset,rod)/Math.pow(2,offset-1);
            }
            for (int offset = 1; slot+offset <= 2; offset += 1) {
                detectedHeat += getHeatInSlot(slot+offset,rod)/Math.pow(2,offset-1);
            }
            if (slot-4 > 0)
                detectedHeat += getHeatInSlot(slot-4,rod)/2;
            if (slot-3 > 0)
                detectedHeat += getHeatInSlot(slot-3,rod)/2;
            if (slot+4 < 24)
                detectedHeat += getHeatInSlot(slot+4,rod)/2;
            if (slot+3 < 24)
                detectedHeat += getHeatInSlot(slot+3,rod)/2;
        } else {
            for (int offset = 1; slot-offset >= 3; offset += 1) {
                detectedHeat += getHeatInSlot(slot-offset,rod)/Math.pow(2,offset-1);
            }
            for (int offset = 1; slot+offset <= 7; offset += 1) {
                detectedHeat += getHeatInSlot(slot+offset,rod)/Math.pow(2,offset-1);
            }
            if (smod != 3) {
                if (slot - 4 > 0)
                    detectedHeat += getHeatInSlot(slot - 4, rod) / 2;
                if (slot + 3 < 24)
                    detectedHeat += getHeatInSlot(slot + 3, rod) / 2;
            }
            if (smod != 6) {
                if (slot - 3 > 0)
                    detectedHeat += getHeatInSlot(slot - 3, rod) / 2;
                if (slot + 4 < 24)
                    detectedHeat += getHeatInSlot(slot + 4, rod) / 2;
            }
        }
        rod.HeatFunction(stack,true,detectedHeat*(rods/100f),cool,20,Math.pow(pressure/30,2)*1000);
        rod.decay(stack,inventory,slot);
        NBTTagCompound data = stack.getTagCompound();
        if (data != null) {
            avgHeat += (data.getDouble("heat")-20)/24;
            if (data.getInteger("spillage") > 200) {
                this.kill = true;
                explode();
            }
            return data.getDouble("cooled");
        }
        return 0; // failsafe
    }
    @Override
    public void update() {
        if (kill) return;
        if (world.isRemote) {
            if (valveOpen && (valveLevel < 6))
                valveLevel++;
            if (!valveOpen && (valveLevel > 0))
                valveLevel--;
            if (world.rand.nextInt(5) == 0)
                dialX = 1;
            else
                dialX = world.rand.nextInt(3);
            if (world.rand.nextInt(3) == 0)
                dialY = world.rand.nextInt(2);
            else
                dialY = 1;
        } else {
            movedelay = (movedelay+1)%2;
            if (movedelay == 0) {
                if (rods > rodsTarget)
                    rods--;
                else if (rods < rodsTarget)
                    rods++;
            }
            if (getFluidInSlot(24) == tankTypes[1])
                FFUtils.fillFromFluidContainer(inventory, tanks[1], 24, 25);
            if (getFluidInSlot(26) == tankTypes[0])
                FFUtils.fillFromFluidContainer(inventory, tanks[0], 26, 27);
            lastPressure = pressure;
            this.pressure = ((this.tanks[1].getFluidAmount() * 2) + (this.hulltemp-20)*125 * ((float)this.tanks[1].getFluidAmount() / (float)this.tanks[1].getCapacity()))/3333;
            if (valveOpen)
                this.tanks[1].drain((int)Math.ceil(Math.pow(Math.max(this.pressure-5,0),3)/30+10),true);
            stressTimer -= Math.pow(Math.max(pressure-16,0)/14,0.9)*64;
            if (stressTimer <= 0) {
                stressTimer = 300; // the audios were lot longer than i thought sooo
                world.playSound(null,pos.getX()+0.5,pos.getY()+2.5,pos.getZ()+0.5,HBMSoundEvents.stressSounds[world.rand.nextInt(7)],SoundCategory.BLOCKS, (float)MathHelper.clampedLerp(0.25,4,Math.pow((pressure-16)/14,4)),1.0F);
            }
            if ((this.pressure >= this.maxPressure) && (pressure >= lastPressure-0.2)) {
                generosityTimer--;
                if (generosityTimer <= 0) {
                    explode();
                    return;
                }
            } else
                generosityTimer = 5*20;
            if (hulltemp >= 1300) {
                explode();
                return;
            }
            double coolin = (float) Math.pow(this.tanks[1].getFluidAmount()/16000f,0.4);
            double feedwatr = (float) Math.pow(this.tanks[0].getFluidAmount()/32000f,0.4);
            double cooledSum = 0;
            avgHeat = 20;
            for (int i = 0; i < 24; i++) {
                if (kill) return;
                if (inventory.getStackInSlot(i).getItem() instanceof LeafiaRodItem)
                    cooledSum += handleLeafiaFuel(i,coolin*1.5);
            }
            if (kill) return;
            double difference = Math.abs(avgHeat-this.hulltemp);
            byte sign = 1;
            if (this.hulltemp > avgHeat) sign = -1;
            this.hulltemp += Math.pow(difference,0.25)*sign + cooledSum/24;
            double steamtemp = tankTypes[2].getTemperature()-273;
            double boilBase = Math.pow(Math.max(this.hulltemp-(steamtemp),0),0.25)*Math.pow(steamtemp/100,0.75)*feedwatr;
            boilBase/=5;
            int boiling = (int)(boilBase*24);
            this.hulltemp = Math.max(this.hulltemp-boilBase/*-Math.pow(this.hulltemp,0.25)*/,20);
            switch(compression) {
                case 0: tanks[0].drain(boiling/100,true); break;
                case 1: tanks[0].drain(boiling/10,true); break;
                case 2: tanks[0].drain(boiling,true); break;
                case 3: tanks[0].drain(boiling*10,true); break;
            }
            tanks[2].fill(new FluidStack(tankTypes[2],boiling),true);
            PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[]{tanks[0], tanks[1], tanks[2]}), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
            PacketDispatcher.wrapper.sendToAllAround(new FluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), new Fluid[]{tankTypes[0],tankTypes[1],tankTypes[2]}), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
            //PacketDispatcher.wrapper.sendToAllAround(new LeafiaPacket(pos,this.getPacketIdentifier()), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
            LeafiaPacket._start(this)
                    .__write(packetKeys.HULL_TEMP.key,hulltemp)
                    .__write(packetKeys.PRESSURE.key,pressure)
                    .__write(packetKeys.CONTROL_RODS.key,rods)
                    .__write(packetKeys.COMPRESSION.key,compression)
                    .__write(packetKeys.CONTROL_RODS_TARGET.key,rodsTarget)
                    .__write(packetKeys.OPENVALVE.key,valveOpen)
                    .__sendToClients(15);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0] };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill){
        if(resource == null) {
            return 0;
        } else if(resource.getFluid() == tankTypes[0]) {
            return tanks[0].fill(resource, doFill);
        } else if(resource.getFluid() == tankTypes[1]) {
            return tanks[1].fill(resource, doFill);
        } else {
            return 0;
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if(resource != null && resource.getFluid() == tankTypes[2]) {
            return tanks[2].drain(resource.amount, doDrain);
        } else {
            return null;
        }
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if(tanks[2].getFluidAmount() > 0) {
            return tanks[2].drain(maxDrain, doDrain);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public String getPacketIdentifier() {
        return "zirnox";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onReceivePacketLocal(byte key, Object value) {
        if (key == packetKeys.HULL_TEMP.key)
            hulltemp = (double)value;
        if (key == packetKeys.PRESSURE.key)
            pressure = (double)value;
        if (key == packetKeys.CONTROL_RODS.key)
            rods = (byte)value;
        if (key == packetKeys.CONTROL_RODS_TARGET.key)
            rodsTarget = (byte)value;
        if (key == packetKeys.COMPRESSION.key) {
            compression = (byte) value;
            switch(compression) {
                case 0: tankTypes[2] = ModForgeFluids.STEAM; break;
                case 1: tankTypes[2] = ModForgeFluids.HOTSTEAM; break;
                case 2: tankTypes[2] = ModForgeFluids.SUPERHOTSTEAM; break;
                case 3: tankTypes[2] = ModForgeFluids.ULTRAHOTSTEAM; break;
            }
        } if (key == packetKeys.OPENVALVE.key)
            valveOpen = (boolean)value;
    }
    @Override
    public void onReceivePacketServer(byte key, Object value, EntityPlayer plr) {
        if (key == packetKeys.CONTROL_RODS.key)
            rodsTarget = (byte)value;
        if (key == packetKeys.COMPRESSION.key) {
            compression = (byte)value;
            updateCompression();
        }
        if (key == packetKeys.OPENVALVE.key) {
            this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundEvents.rbmk_az5_cover, SoundCategory.BLOCKS, 0.5F, 0.5F);
            valveOpen = (boolean)value;
        }
    }

    @Override
    public void onPlayerValidate(EntityPlayer plr) {

    }
}