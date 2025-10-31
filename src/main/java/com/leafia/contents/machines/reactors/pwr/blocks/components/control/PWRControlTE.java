package com.leafia.contents.machines.reactors.pwr.blocks.components.control;

import com.hbm.lib.HBMSoundEvents;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class PWRControlTE extends TileEntity implements PWRComponentEntity, ITickable, LeafiaPacketReceiver {
    BlockPos corePos = null;
    PWRData data = null;

    public PWRControlTE() {
        super();
    }
    public int height = 1;
    public double position = 0;
    public String name = defaultName;
    public static final String defaultName = "00.Unsorted";
    AudioWrapper sound = null;

    public static final double speed = 0.1/20;
    public double targetPosition = 0;

    public void updateHeight() {
        if (!this.isInvalid() && world.isBlockLoaded(pos)) {
            Chunk chunk = world.getChunk(pos);
            BlockPos downPos = pos.down();
            height = 1;
            while (world.isValid(downPos)) {
                if (world.getBlockState(downPos).getBlock() instanceof PWRControlBlock) {
                    height++;
                    if (world.isRemote) { // manually kill TEs below
                        TileEntity entity = chunk.getTileEntity(downPos,Chunk.EnumCreateEntityType.CHECK);
                        if (entity != null) {
                            if (entity instanceof PWRControlTE) {
                                ((PWRControlTE)entity).connectUpper();
                            }
                        }
                    }
                } else
                    break;
                downPos = downPos.down();
            }
        }
    }

    public void connectUpper() { // For clients, called only on validate()
        if (!this.isInvalid() && world.isBlockLoaded(pos)) {
            if (world.getBlockState(pos.up()).getBlock() instanceof PWRControlBlock)
                this.invalidate();
        }
    }
    @Override
    public void setCoreLink(@Nullable BlockPos pos) {
        corePos = pos;
    }

    @Override
    public PWRData getLinkedCore() {
        return PWRComponentEntity.getCoreFromPos(world,corePos);
    }

    @Override
    public void assignCore(@Nullable PWRData data) {
        if (this.data != data) {
            PWRData.addDataToPacket(LeafiaPacket._start(this),data).__sendToAffectedClients();
        }
        this.data = data;
    }
    @Override
    public PWRData getCore() {
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("corePosX"))
            corePos = new BlockPos(
                    compound.getInteger("corePosX"),
                    compound.getInteger("corePosY"),
                    compound.getInteger("corePosZ")
            );
        if (compound.hasKey("rodP"))
            position = compound.getDouble("rodP");
        if (compound.hasKey("rodD"))
            targetPosition = compound.getDouble("rodD");
        if (compound.hasKey("name"))
            name = compound.getString("name");
        super.readFromNBT(compound);
        if (compound.hasKey("data")) { // DO NOT MOVE THIS ABOVE SUPER CALL! super.readFromNBT() is where this.pos gets initialized!!
            data = new PWRData(this);
            data.readFromNBT(compound);
            //new PWRDiagnosis(world).addPosition(pos);
        }
    }

    public NBTTagCompound writeControlDateToNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("rodP", position);
        nbt.setDouble("rodD", targetPosition);
        nbt.setString("name", name);
        nbt.setInteger("x", pos.getX());
        nbt.setInteger("y", pos.getY());
        nbt.setInteger("z", pos.getZ());
        return nbt;
    }

    public void readControlDataFromNBT(NBTTagCompound nbt)
    {
        position = nbt.getDouble("rodP");
        targetPosition = nbt.getDouble("rodD");
        name = nbt.getString("name");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (corePos != null) {
            compound.setInteger("corePosX",corePos.getX());
            compound.setInteger("corePosY",corePos.getY());
            compound.setInteger("corePosZ",corePos.getZ());
        }
        compound.setDouble("rodP",position);
        compound.setDouble("rodD",targetPosition);
        compound.setString("name",name);
        if (data != null) {
            data.writeToNBT(compound);
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void invalidate() {
        if (sound != null)
            sound.stopSound();
        sound = null;
        super.invalidate();
        if (this.data != null)
            this.data.invalidate(world);
    }

    @Override
    public void validate() {
        super.validate();
        //if (world.isRemote) {
            //LeafiaPacket._validate(this); //LeafiaPacket._start(this).__write((byte)0,true).__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK).__sendToServer();
        //}
        connectUpper();
        updateHeight();
    }

    @Override
    public void onChunkUnload() {
        if (sound != null && playing) {
            sound.stopSound();
        }
        sound = null;
        super.onChunkUnload();
    }

    boolean playing = false;
    int stupidworkaroundcooldown = 0;
    @Override
    public void update() {
        if (this.data != null)
            this.data.update();
        if (world.isRemote) {
            if (stupidworkaroundcooldown > 0) {
                stupidworkaroundcooldown--;
                return;
            }
            if (!(world.getBlockState(pos.up()).getBlock() instanceof PWRControlBlock)) {
                if (sound == null)
                    sound = MainRegistry.proxy.getLoopedSoundStartStop(world,HBMSoundEvents.pwrRodLoop,HBMSoundEvents.pwrRodStart,HBMSoundEvents.pwrRodStop,SoundCategory.BLOCKS,pos.getX(),pos.getY(),pos.getZ(),0.0175f,0.75f);
                if (playing) {
                    if (targetPosition == position) {
                        sound.stopSound();
                        stupidworkaroundcooldown = 5;
                        playing = false;
                    }
                } else {
                    if (targetPosition != position) {
                        sound.startSound();
                        playing = true;
                    }
                }
            }
        } else {
            if (targetPosition != position) {
                if (Math.abs(targetPosition - position) < speed/height)
                    position = targetPosition;
                else {
                    position += Math.signum(targetPosition - position) * speed/height;
                }
                syncLocals();
                this.markDirty();
            }
        }
    }
    public LeafiaPacket generateSyncPacket() {
        return LeafiaPacket._start(this).__write(0,position).__write(1,targetPosition);
    }
    public void syncLocals() {
        generateSyncPacket().__sendToAffectedClients();//.__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK).__sendToAllInDimension();
    }
    @Override
    public String getPacketIdentifier() {
        return "PWRControl";
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void onReceivePacketLocal(byte key,Object value) {
        switch(key) {
            case 0:
                position = (double)value;
                break;
            case 1:
                targetPosition = (double)value;
                break;
            case 2:
                name = (String)value;
                break;
            case 31:
                data = PWRData.tryLoadFromPacket(this,value);
                break;
        }
        if (this.data != null)
            this.data.onReceivePacketLocal(key,value);
    }
    @Override
    public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {/*
        if (key == 0) {
            generateSyncPacket().__sendToClient(plr);
        }*/
        if (this.data != null)
            this.data.onReceivePacketServer(key,value,plr);
    }

    @Override
    public void onPlayerValidate(EntityPlayer plr) {
        LeafiaPacket packet = generateSyncPacket().__write(!name.equals(defaultName) ? 2 : -1,name);
        if (this.data != null) {
            PWRData.addDataToPacket(packet,this.data);
        }
        packet.__sendToClient(plr);
    }
}
