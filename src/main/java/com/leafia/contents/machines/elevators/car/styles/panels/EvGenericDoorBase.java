package com.leafia.contents.machines.elevators.car.styles.panels;

import com.leafia.contents.machines.elevators.car.ElevatorEntity.HitSrf;
import com.leafia.dev.math.FiaMatrix;

import java.util.ArrayList;
import java.util.List;

public abstract class EvGenericDoorBase extends ElevatorPanelBase {
	public EvGenericDoorBase(int rotation) {
		super(rotation);
	}
	@Override
	public List<HitSrf> getHitSurfaces() {
		List<HitSrf> surfaces = new ArrayList<>();
		/*
		surfaces.add(new HitSrf(new FiaMatrix().translate(0,0,-23/16d),-7/16d,0,7/16d,32/16d,1d/16d).setType(0));

		surfaces.add(new HitSrf(new FiaMatrix().translate(0,0,-19/16d),-15/16d,0,-7/16d,36/16d,4d/16d).setType(0));
		surfaces.add(new HitSrf(new FiaMatrix().translate(0,0,-19/16d),7/16d,0,15/16d,36/16d,4d/16d).setType(0));
		surfaces.add(new HitSrf(new FiaMatrix().translate(-7/16d,0,-19/16d).rotateY(90),0,0,4/16d,32/16d,4d/16d).setType(0));
		surfaces.add(new HitSrf(new FiaMatrix().translate(7/16d,0,-19/16d).rotateY(-90),-4/16d,0,0,32/16d,4d/16d).setType(0));

		surfaces.add(new HitSrf(new FiaMatrix().translate(0,0,-19/16d),-7/16d,32/16d,7/16d,36/16d,4d/16d).setType(1));
		surfaces.add(new HitSrf(new FiaMatrix().translate(0,32/16d,-19/16d).rotateX(-90),-7/16d,0,7/16d,4d/16d,4d/16d).setType(1));
		 */
		FiaMatrix mat = new FiaMatrix().translate(0,0,-15/16d).rotateY(180);
		surfaces.add(new HitSrf(mat.translate(0,0,4/16d),-7/16d,0,7/16d,32/16d,1/16d));
		//door

		surfaces.add(new HitSrf(mat.translate(-7/16d,0,0),-8/16d,0,0,36/16d,4/16d));
		surfaces.add(new HitSrf(mat.translate(-7/16d,0,0).rotateY(-90),0,0,4/16d,32/16d,8/16d));
		// left wall

		surfaces.add(new HitSrf(mat.translate(7/16d,0,0),0,0,8/16d,36/16d,4/16d));
		surfaces.add(new HitSrf(mat.translate(7/16d,0,0).rotateY(90),-4/16d,0,0,32/16d,8/16d));
		// right wall

		surfaces.add(new HitSrf(mat.translate(0,32/16d,0),-7/16d,0,7/16d,4/16d,4/16d));
		// ceiling
		return surfaces;
	}
}
