package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

public class TileEntityRBMKModerator extends TileEntityRBMKBase implements SimpleComponent {
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 2 + world.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.GRAPHITE);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.MODERATOR;
	}

	// opencomputers interface

	@Override
	public String getComponentName() {
		return "rbmk_moderator";
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
