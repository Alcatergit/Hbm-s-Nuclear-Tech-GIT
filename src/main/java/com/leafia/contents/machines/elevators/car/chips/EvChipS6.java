package com.leafia.contents.machines.elevators.car.chips;

import com.hbm.lib.HBMSoundEvents;
import com.leafia.contents.machines.elevators.car.ElevatorEntity;
import com.leafia.contents.machines.elevators.car.ElevatorEntity.EvButtonEnablePacket;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.llib.technical.LeafiaEase;
import com.llib.technical.LeafiaEase.Direction;
import com.llib.technical.LeafiaEase.Ease;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class EvChipS6 extends EvChipBase {
	public EvChipS6(ElevatorEntity entity) {
		super(entity);
		if (entity.doorOpen) {
			doorLevelIn = 1;
			doorLevelOut = 1;
		}
	}
	@Override
	public String getType() { return "s6"; }
	@Override
	public void onButtonServer(String id,EntityPlayer player,EnumHand hand) {
		if (id.startsWith("floor") && !entity.enabledButtons.contains(id)) {
			int floor = Integer.parseInt(id.substring(5));
			//if (entity.doorOpen && floor == entity.getDataInteger(ElevatorEntity.FLOOR)) return;
			if (entity.parkFloor == floor) return;

			entity.enabledButtons.add(id);
			entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.s6beep,SoundCategory.BLOCKS,0.35f,1);
			entity.targetFloors = entity.getTargetFloorsFromEnabledButtons();
		} else if (id.equals("close")) {
			if (entity.doorOpen && !entity.targetFloors.isEmpty()) {
				closeTimer = closeTime;
				cooldown = 0;
			}
		} else if (id.equals("open") && closing) {
			closing = false;
			closeTimer = 0;
			cooldown = 20;
		}
	}
	int doorOpenTimer = 0;
	final int doorOpenTime = 10;
	float doorLevelIn = 0;
	float doorLevelOut = 0;
	int pingpongTimer = 0;
	int cooldown = 0;
	boolean closing = false;
	boolean moving = false;
	int closeTimer = 0;
	final int closeTime = 60;
	int moveCooldown = 0;
	void openingDoor() {
		if (entity.isEnd(entity.down))
			entity.down = !entity.down;
		entity.doorOpen = true;
		if (entity.targetFloors.contains(entity.parkFloor)) {
			entity.targetFloors.removeElement(entity.parkFloor);
			entity.enabledButtons.remove("floor"+entity.parkFloor);
			LeafiaCustomPacket.__start(new EvButtonEnablePacket(entity)).__sendToAll();
		}
		if (doorOpenTimer < doorOpenTime)
			doorOpenTimer++;
		else {
			entity.getDataManager().set(ElevatorEntity.ARROW,entity.down ? -1 : 1);
			LeafiaEase ease = new LeafiaEase(Ease.EXPO,Direction.O);
			if (doorLevelIn < 1) {
				doorLevelIn = Math.min(1,doorLevelIn+1/20f);
				entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevelIn));
			}
			if (doorLevelOut < 1) {
				doorLevelOut = Math.min(1,doorLevelOut+1/20f);
				entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevelOut));
				cooldown = 3*20;
			}
			if (pingpongTimer < 5) {
				if (pingpongTimer == 0)
					entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.electronicpingshort,SoundCategory.BLOCKS,0.35f,1.7f);
				pingpongTimer++;
				if (entity.down && pingpongTimer == 5)
					entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.electronicpingshort,SoundCategory.BLOCKS,0.35f,1.45f);
			}
		}
	}
	void closingDoor() {
		LeafiaEase ease = new LeafiaEase(Ease.SINE,Direction.IO);
		if (doorLevelOut > 0) {
			doorLevelOut = Math.max(0,doorLevelOut-1/20f);
			entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevelOut));
		} else if (doorLevelIn > 0) {
			doorLevelIn = Math.max(0,doorLevelIn-1/20f);
			entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevelIn));
		} else {
			moveCooldown = 10;
			entity.timeSinceStart = 0;
			entity.braking = false;
			entity.doorOpen = false;
			entity.getDataManager().set(ElevatorEntity.ARROW,0);
			closing = false;
			doorOpenTimer = 0;
			pingpongTimer = 0;
		}
	}
	@Override
	public void onUpdate() {
		Integer floor = entity.getFloorAtPos(new BlockPos(entity.posX,entity.posY+0.5,entity.posZ));
		Integer nextFloor = entity.getNextFloor();
		if (nextFloor == null && entity.isFloorOnReverseDirection()) {
			nextFloor = entity.getNextFloor(!entity.down);
			if (!entity.doorOpen) entity.down = !entity.down;
		}
		//if (entity.targetFloors.size() > 0 && entity.getNextFloor() == null) entity.down = !entity.down;
		if (nextFloor != null && !entity.doorOpen) {
			if (nextFloor.equals(floor)) {}
			else entity.parkFloor = nextFloor;
		}
		if (cooldown > 0) cooldown--;

		double ratio = getSpeedRatio();
		if (entity.pulley != null)
			entity.setMotion(0,entity.doorOpen ? 0 : ratio*0.08,0);

		// MAIN CONTROL
		if (nextFloor != null && !entity.doorOpen) {
			if (moveCooldown > 0) moveCooldown--;
			else
				entity.timeSinceStart++;
		}
		if (nextFloor != null && entity.doorOpen && !closing && cooldown <= 0) {
			if (closeTimer < closeTime)
				closeTimer++;
			else {
				closing = true;
				closeTimer = 0;
			}
		}

		// DOOR CONTROL
		if (floor != null) {
			if (floor.equals(entity.parkFloor) && ratio == 0 && !closing) {
				openingDoor();
				entity.timeSinceStart = 0;
			}
		}
		if (entity.doorOpen && closing)
			closingDoor();

		// FLOOR PASS CONTROL
		if (floor != null) {
			if (!floor.equals(entity.getDataInteger(ElevatorEntity.FLOOR))) {
				entity.getDataManager().set(ElevatorEntity.FLOOR,floor);
				entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.s6beep,SoundCategory.BLOCKS,0.35f,1);
				//entity.enabledButtons.remove("floor"+floor);
				//LeafiaCustomPacket.__start(new EvButtonEnablePacket(entity)).__sendToAll();
			}
		}

		/*
		LeafiaDebug.debugLog(entity.getWorld(),new BlockPos(entity.posX,entity.posY+0.5,entity.posZ));
		if (cooldown > 0) cooldown--;
		if (floor != null && floor.equals(entity.parkFloor)) {
			if (Math.abs(ratio) <= 0.01 && entity.getDataInteger(ElevatorEntity.FLOOR).equals(floor) && !shouldClose) {
				if (doorOpenTimer < doorOpenTime)
					doorOpenTimer++;
				else {
					if (doorLevelIn < 1) {
						doorLevelIn = Math.min(1,doorLevelIn+1/20f);
						doorLevelOut = doorLevelIn;
						LeafiaEase ease = new LeafiaEase(Ease.EXPO,Direction.O);
						entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevelIn));
						entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevelOut));
						entity.doorOpen = true;
						cooldown = 2*20;
						entity.getDataManager().set(ElevatorEntity.ARROW,entity.down ? -1 : 1);
					}
					if (pingpongTimer < 5) {
						if (pingpongTimer == 0)
							entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.electronicpingshort,SoundCategory.BLOCKS,0.35f,1.7f);
						pingpongTimer++;
						if (entity.down && pingpongTimer == 5)
							entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.electronicpingshort,SoundCategory.BLOCKS,0.35f,1.45f);
					}
				}
			}
		} else {
			if (nextFloor != null && !entity.doorOpen)
				entity.parkFloor = nextFloor;
		}
		if (floor != null) {
			if (!floor.equals(entity.getDataInteger(ElevatorEntity.FLOOR))) {
				entity.getDataManager().set(ElevatorEntity.FLOOR,floor);
				entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.s6beep,SoundCategory.BLOCKS,0.35f,1);
			}
		}
		LeafiaDebug.debugLog(entity.getWorld(),"closeTimer: "+closeTimer);
		if (cooldown <= 0 && !shouldClose && !moving) {
			if (!entity.targetFloors.isEmpty()) {
				if (closeTimer < closeTime)
					closeTimer++;
				else
					shouldClose = true;
			}
		} else if (shouldClose) {
			LeafiaEase ease = new LeafiaEase(Ease.SINE,Direction.IO);
			if (doorLevelOut > 0) {
				doorLevelOut = Math.max(0,doorLevelOut-1/20f);
				entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevelOut));
			} else if (doorLevelIn > 0) {
				doorLevelIn = Math.max(0,doorLevelIn-1/20f);
				entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevelIn));
			} else {
				shouldClose = false;
				moveCooldown = 10;
				entity.timeSinceStart = 0;
				entity.braking = false;
				entity.getDataManager().set(ElevatorEntity.ARROW,0);
			}
		}
		if (!entity.doorOpen) {
			if (moveCooldown > 0) moveCooldown--;
			else {
				entity.timeSinceStart++;
				if (entity.getNextFloor() == null) entity.down = !entity.down;
				LeafiaDebug.debugLog(entity.getWorld(),"RATIO: "+ratio);
			}
		}*/
	}
}
