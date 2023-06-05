package com.hbm.tileentity.turret;

public class TileEntityTurretLight extends TileEntityTurretBase {

	public int spin;
	public int rotation;
	
	@Override
	public void update() {
		
		super.update();

		if(spin > 0)
			spin -= 1;
			
		rotation += spin;
		rotation = rotation % 360;
	}
}
