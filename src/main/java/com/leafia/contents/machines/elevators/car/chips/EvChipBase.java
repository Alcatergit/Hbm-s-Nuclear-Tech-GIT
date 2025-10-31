package com.leafia.contents.machines.elevators.car.chips;

import com.leafia.contents.machines.elevators.car.ElevatorEntity;
import com.leafia.dev.LeafiaDebug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

import java.util.Map;

public abstract class EvChipBase {
	final ElevatorEntity entity;
	double accelerateTime = 1.5;
	int brakeDistance = 2;

	public EvChipBase(ElevatorEntity entity) {
		this.entity = entity;
		readEntityFromNBT(entity.getEntityData());
	}
	public abstract String getType();

	double getSpeedRatio() {
		Integer nextFloor = entity.getNextFloor();
		if (nextFloor == null) return 0;
		if (entity.getDataInteger(ElevatorEntity.FLOOR) != entity.parkFloor) { //entity.targetHeight < 0) {
			Map<Integer,Integer> map = entity.getFloorsInRange(brakeDistance*(entity.down ? -1 : 1));
			if (map.containsKey(nextFloor))
				entity.targetHeight = map.get(nextFloor);
			else
				entity.targetHeight = -1;
		}
		double maxRatio = 1;
		if (entity.targetHeight >= 0)
			maxRatio = Math.min(1,Math.abs(entity.targetHeight-entity.posY)/brakeDistance);
		if (maxRatio < 1)
			entity.braking = true;
		double ratio = Math.min(maxRatio,entity.timeSinceStart/(accelerateTime*20));
		if (entity.targetHeight >= 0 ? entity.targetHeight < entity.posY : nextFloor < entity.getDataInteger(ElevatorEntity.FLOOR)) ratio *= -1;
		if (Math.abs(maxRatio) < 0.05) {
			double offset = entity.targetHeight-entity.posY;
			double dir = Math.signum(offset);
			ratio = 0.05*dir;
			if (Math.abs(offset) < 0.1)
				ratio = 0.02*dir;
			//if (Math.abs(offset) < 0.04)
			//	ratio = 0.005*dir;
			if (Math.abs(offset) < 0.01)
				ratio = 0;
		}
		return ratio;
	}

	public abstract void onButtonServer(String id,EntityPlayer player,EnumHand hand);
	public abstract void onUpdate();

	public void readEntityFromNBT(NBTTagCompound compound) {}
	public void writeEntityToNBT(NBTTagCompound compound) {}
}
