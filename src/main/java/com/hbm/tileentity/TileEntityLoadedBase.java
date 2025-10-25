package com.hbm.tileentity;

import api.hbm.energy.ILoadedTile;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile {
	
	public boolean isLoaded = false;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

    @Override
    public void onLoad() {
        super.onLoad();
        this.isLoaded = true;
    }
}
