package com.hbm.tileentity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.generic.BlockDoorGeneric;
import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDoorAnimationPacket;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDoorGeneric extends TileEntityLockableBase implements ITickable, IAnimatedDoor, IControllable {

	//0: closed, 1: open, 2: closing, 3: opening
	public byte state = 0;
	public DoorDecl doorType;
	public int openTicks = 0;
	public long animStartTime = 0;
	public int redstonePower;
	public boolean shouldUseBB = false;
	public Set<BlockPos> activatedBlocks = new HashSet<>(4);

	private AudioWrapper audio;
	private AudioWrapper audio2;
	
	@Override
	public void update(){
		if(state == 3) {
			openTicks++;
			if(openTicks >= doorType.timeToOpen()) {
				openTicks = doorType.timeToOpen();
			}
		} else if(state == 2) {
			openTicks--;
			if(openTicks <= 0) {
				openTicks = 0;
			}
		}

		if(world.isRemote) {

		} else {
			int[][] ranges = doorType.getDoorOpenRanges();
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			if(state == 3) {
				for(int i = 0; i < ranges.length; i++) {
					int[] range = ranges[i];
					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = doorType.getDoorRangeOpenTime(openTicks, i);
					for(int j = 0; j < Math.abs(range[3]); j++) {
						if((float)j / (Math.abs(range[3] - 1)) > time)
							break;
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(EnumFacing.Axis.values()[range[5]]){
							case X:
								add = new BlockPos(0, k, Math.signum(range[3])*j);
								break;
							case Y:
								add = new BlockPos(k, Math.signum(range[3])*j, 0);
								break;
							case Z:
								add = new BlockPos(Math.signum(range[3])*j, k, 0);
								break;
							}
							Rotation r = dir.getBlockRotation();
							if(dir.toEnumFacing().getAxis() == EnumFacing.Axis.X)
								r = r.add(Rotation.CLOCKWISE_180);
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							if(finalPos.equals(this.pos)) {
								this.shouldUseBB = true;
							} else {
								((BlockDummyable)getBlockType()).makeExtra(world, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
			} else if(state == 2){
				for(int i = 0; i < ranges.length; i++) {
					int[] range = ranges[i];
					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = doorType.getDoorRangeOpenTime(openTicks, i);
					for(int j = Math.abs(range[3])-1; j >= 0; j--) {
						if((float)j / (Math.abs(range[3] - 1)) < time)
							break;
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(EnumFacing.Axis.values()[range[5]]){
							case X:
								add = new BlockPos(0, k, Math.signum(range[3])*j);
								break;
							case Y:
								add = new BlockPos(k, Math.signum(range[3])*j, 0);
								break;
							case Z:
								add = new BlockPos(Math.signum(range[3])*j, k, 0);
								break;
							}
							Rotation r = dir.getBlockRotation();
							if(dir.toEnumFacing().getAxis() == EnumFacing.Axis.X)
								r = r.add(Rotation.CLOCKWISE_180);
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							if(finalPos.equals(this.pos)) {
								this.shouldUseBB = false;
							} else {
								((BlockDummyable)getBlockType()).removeExtra(world, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
			}
			if(state == 3 && openTicks == doorType.timeToOpen()) {
				state = 1;
				broadcastControlEvt();
			}
			if(state == 2 && openTicks == 0) {
				state = 0;
				broadcastControlEvt();
			}
			PacketDispatcher.wrapper.sendToAllAround(new TEDoorAnimationPacket(pos, state, (byte)(shouldUseBB ? 1 : 0)), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
			
			if(redstonePower == -1 && state == 0){
				tryToggle(-1);
			} else if(redstonePower > 0 && state == 1){
				tryToggle(-1);
			}
			if(redstonePower == -1){
				redstonePower = 0;
			}
		}
	}

	@Override
	public void onChunkUnload() {
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
    	}
	}
	
	@Override
	public void onLoad(){
		doorType = ((BlockDoorGeneric)this.getBlockType()).type;
	}

	public boolean canLock(EntityPlayer player, EnumHand hand, EnumFacing facing) {
		if (this.redstonePower > 0) {
			player.sendMessage(new TextComponentString("Cannot lock while redstone powered"));
			return false;
		} else {
			return true;
		}
	}

	public boolean tryToggle(EntityPlayer player){
		if(state == 0 && redstonePower > 0) {
			//Redstone "power locks" doors, just like minecraft iron doors
			return false;
		}
		if(this.state == 0) {
			if(!world.isRemote && canAccess(player)) {
				this.state = 3;
				broadcastControlEvt();
			}
			return true;
		} else if(this.state == 1) {
			if(!world.isRemote && canAccess(player)) {
				this.state = 2;
				broadcastControlEvt();
			}
			return true;
		}
		return false;
	}
	
	public boolean tryToggle(int passcode){
		if(this.isLocked() && passcode != this.lock)
			return false;
		if(this.state == 0) {
			if(!world.isRemote) {
				this.state = 3;
				broadcastControlEvt();
			}
			return true;
		} else if(this.state == 1) {
			if(!world.isRemote) {
				this.state = 2;
				broadcastControlEvt();
			}
			return true;
		}
		return false;
	}

	@Override
	public void open(){
		if(state == 0)
			toggle();
	}

	@Override
	public void close(){
		if(state == 1)
			toggle();
	}

	@Override
	public DoorState getState(){
		return DoorState.values()[state];
	}

	@Override
	public void toggle(){
		if(state == 0) {
			state = 3;
		} else if(state == 1) {
			state = 2;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleNewState(byte state){
		if(this.state != state) {
			if(this.state == 0 && state == 3){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getOpenSoundLoop(), doorType.getOpenSoundStart(), doorType.getOpenSoundEnd(), SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio.startSound();
				}
				if(audio2 == null && doorType.getSoundLoop2() != null){
					audio2 = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getSoundLoop2(), null, null, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio2.startSound();
				}
			}
			if(this.state == 1 && state == 2){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getCloseSoundLoop(), doorType.getCloseSoundStart(), doorType.getCloseSoundEnd(), SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio.startSound();
				}
				if(audio2 == null && doorType.getSoundLoop2() != null){
					audio2 = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getSoundLoop2(), null, null, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio2.startSound();
				}
			}
			if((this.state == 3 && state == 1) || (this.state == 2 && state == 0)){
				if(audio != null){
					audio.stopSound();
					audio = null;
				}
				if(audio2 != null){
					audio2.stopSound();
					audio2 = null;
				}
			}
			
			
			this.state = state;
			if(state > 1)
				animStartTime = System.currentTimeMillis();
		}
	}

	//Ah yes piggy backing on this packet
	@Override
	public void setTextureState(byte tex){
		if(tex > 0)
			shouldUseBB = true;
		else
			shouldUseBB = false;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public double getMaxRenderDistanceSquared(){
		return 65536D;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag){
		this.state = tag.getByte("state");
		this.openTicks = tag.getInteger("openTicks");
		this.animStartTime = tag.getInteger("animStartTime");
		this.redstonePower = tag.getInteger("redstoned");
		this.shouldUseBB = tag.getBoolean("shouldUseBB");
		NBTTagCompound activatedBlocks = tag.getCompoundTag("activatedBlocks");
		this.activatedBlocks.clear();
		for(int i = 0; i < activatedBlocks.getKeySet().size()/3; i ++){
			this.activatedBlocks.add(new BlockPos(activatedBlocks.getInteger("x"+i), activatedBlocks.getInteger("y"+i), activatedBlocks.getInteger("z"+i)));
		}
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		tag.setByte("state", state);
		tag.setInteger("openTicks", openTicks);
		tag.setLong("animStartTime", animStartTime);
		tag.setInteger("redstoned", redstonePower);
		tag.setBoolean("shouldUseBB", shouldUseBB);
		NBTTagCompound activatedBlocks = new NBTTagCompound();
		int i = 0;
		for(BlockPos p : this.activatedBlocks){
			activatedBlocks.setInteger("x"+i, p.getX());
			activatedBlocks.setInteger("y"+i, p.getY());
			activatedBlocks.setInteger("z"+i, p.getZ());
			i++;
		}
		tag.setTag("activatedBlocks", activatedBlocks);
		return super.writeToNBT(tag);
	}

	public void broadcastControlEvt(){
		ControlEventSystem.get(world).broadcastToSubscribed(this, ControlEvent.newEvent("door_open_state").setVar("state", new DataValueFloat(state)));
	}
	
	@Override
	public void receiveEvent(BlockPos from, ControlEvent e){
		if(e.name.equals("door_toggle")){
			tryToggle((int)e.vars.get("passcode").getNumber());
		}
	}
	
	@Override
	public List<String> getInEvents(){
		return Arrays.asList("door_toggle");
	}
	
	@Override
	public List<String> getOutEvents(){
		return Arrays.asList("door_open_state");
	}
	
	@Override
	public void validate(){
		super.validate();
		ControlEventSystem.get(world).addControllable(this);
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
    	}
		ControlEventSystem.get(world).removeControllable(this);
	}

	@Override
	public BlockPos getControlPos(){
		return getPos();
	}

	@Override
	public World getControlWorld(){
		return getWorld();
	}

	public void updateRedstonePower(BlockPos pos){
		//Drillgon200: Best I could come up with without having to use dummy tile entities
		boolean powered = world.isBlockIndirectlyGettingPowered(pos) > 0;
		boolean contained = activatedBlocks.contains(pos);
		if(!contained && powered){
			activatedBlocks.add(pos);
			if(redstonePower == -1){
				redstonePower = 0;
			}
			redstonePower++;
		} else if(contained && !powered){
			activatedBlocks.remove(pos);
			redstonePower--;
			if(redstonePower == 0){
				redstonePower = -1;
			}
		}
	}

}
