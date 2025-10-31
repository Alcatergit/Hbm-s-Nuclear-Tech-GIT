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

public class EvChipSkylift extends EvChipBase {
	public EvChipSkylift(ElevatorEntity entity) {
		super(entity);
		if (entity.doorOpen) doorLevel = 1;
		accelerateTime = 3;
		brakeDistance = 8;
	}
	@Override
	public String getType() { return "skylift"; }
	@Override
	public void onButtonServer(String id,EntityPlayer player,EnumHand hand) {
		if (id.startsWith("floor") && !entity.enabledButtons.contains(id)) {
			int floor = Integer.parseInt(id.substring(5));
			if (entity.parkFloor == floor) { shouldOpen = true; return; }

			entity.enabledButtons.add(id);
			entity.targetFloors = entity.getTargetFloorsFromEnabledButtons();
		} else if (id.equals("close")) {
			if (entity.doorOpen) {
				closeTimer = closeTime;
				shouldOpen = false;
			}
		} else if (id.equals("open") && closing) {
			shouldOpen = true;
			if (closing) {
				closing = false;
				closeTimer = 0;
			}
		}
	}
	int doorOpenTimer = 0;
	int doorOpenTime = 20;
	int closeTimer = 0;
	int closeTime = 100;
	float doorLevel = 0;
	boolean closing = false;
	boolean dinged = false;
	boolean shouldOpen = false;
	void openingDoor() {
		entity.doorOpen = true;
		if (entity.targetFloors.contains(entity.parkFloor)) {
			entity.targetFloors.removeElement(entity.parkFloor);
			entity.enabledButtons.remove("floor"+entity.parkFloor);
			LeafiaCustomPacket.__start(new EvButtonEnablePacket(entity)).__sendToAll();
		}
		if (doorOpenTimer < doorOpenTime)
			doorOpenTimer++;
		else {
			LeafiaEase ease = new LeafiaEase(Ease.SINE,Direction.IO);
			if (doorLevel < 1) {
				doorLevel = Math.min(1,doorLevel+1/20f);
				entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevel));
				entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevel));
			} else shouldOpen = false;
			if (!dinged) {
				entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.skyliftarrive,SoundCategory.BLOCKS,0.35f,1);
				dinged = true;
			}
		}
	}
	void closingDoor() {
		LeafiaEase ease = new LeafiaEase(Ease.SINE,Direction.IO);
		if (doorLevel > 0) {
			doorLevel = Math.max(0,doorLevel-1/20f);
			entity.getDataManager().set(ElevatorEntity.DOOR_OUT,(float)ease.get(doorLevel));
			entity.getDataManager().set(ElevatorEntity.DOOR_IN,(float)ease.get(doorLevel));
		} else {
			entity.timeSinceStart = 0;
			entity.braking = false;
			entity.doorOpen = false;
			closing = false;
			doorOpenTimer = 0;
			dinged = false;
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

		double ratio = getSpeedRatio();
		if (entity.pulley != null)
			entity.setMotion(0,entity.doorOpen ? 0 : ratio,0);
		LeafiaDebug.debugLog(entity.getWorld(),ratio);
		LeafiaDebug.debugLog(entity.getWorld(),"park: "+entity.parkFloor);
		if (!entity.doorOpen && !entity.targetFloors.isEmpty()) {
			if (entity.timeSinceStart == 0) {
				entity.getDataManager().set(ElevatorEntity.ARROW,entity.down ? -1 : 1);
				shouldOpen = true;
			}
			entity.timeSinceStart++;
		}
		if (entity.doorOpen && !closing) {
			if (closeTimer < closeTime)
				closeTimer++;
			else {
				closing = true;
				closeTimer = 0;
			}
		}

		// DOOR CONTROL
		if (floor != null) {
			LeafiaDebug.debugLog(entity.getWorld(),"CONDITION 1: "+floor.equals(entity.parkFloor));
			LeafiaDebug.debugLog(entity.getWorld(),"CONDITION 2: "+(ratio == 0));
			LeafiaDebug.debugLog(entity.getWorld(),"CONDITION 3: "+!closing);
			if (floor.equals(entity.parkFloor) && ratio == 0 && !closing && shouldOpen)
				openingDoor();
		}
		if (entity.doorOpen && closing)
			closingDoor();


		// FLOOR PASS CONTROL
		if (floor != null) {
			if (!floor.equals(entity.getDataInteger(ElevatorEntity.FLOOR))) {
				entity.getDataManager().set(ElevatorEntity.FLOOR,floor);
				entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.electronicpingshort,SoundCategory.BLOCKS,0.35f,2);
			}
		}
	}
}
