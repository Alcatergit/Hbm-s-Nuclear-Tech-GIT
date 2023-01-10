package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

public class TileEntityRBMKAbsorber extends TileEntityRBMKBase implements SimpleComponent {
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + world.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.ABSORBER;
	}

	// opencomputers interface

	@Override
	public String getComponentName() {
		return "rbmk_absorber";
	}
	
	@Callback(doc = "func(): double - returns hull temp")
	public Object[] getHullTemp(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(doc = "func(): double - returns steam quantity")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {steam};
	}

	@Callback(doc = "func(): double - returns water quantity")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {water};
	}
}
