package com.leafia.contents.machines.elevators.car.styles;

import com.leafia.contents.machines.elevators.car.ElevatorEntity.HitSrf;
import com.leafia.dev.math.FiaMatrix;

import java.util.ArrayList;
import java.util.List;

public class EvWallBase {
	public final int rotation;
	public EvWallBase(int rotation) {
		this.rotation = rotation;
	}
	public List<HitSrf> getHitSurfaces() {
		List<HitSrf> surfaces = new ArrayList<>();
		surfaces.add(new HitSrf(new FiaMatrix().translate(0,0,-15/16d).rotateY(180),-15/16d,0,15/16d,36/16d,4d/16d).setType(0));
		return surfaces;
	}
}
