package com.hbm.calc;

import com.hbm.interfaces.ISource;

public class UnionOfTileEntitiesAndBooleans {

	public ISource source;
	public boolean ticked = false;
	
	public UnionOfTileEntitiesAndBooleans(ISource tileentity, boolean bool)
	{
		source = tileentity;
		ticked = bool;
	}
}
