package com.hbm.tileentity.turret;

public class TileEntityTurretHeavy extends TileEntityTurretBase {

	public double recoil;
	
	@Override
	public void update() {
		
		super.update();

		if(recoil > 0.001){
			recoil *= 0.9;
		}else{
			recoil = 0;
		}
	}
}
