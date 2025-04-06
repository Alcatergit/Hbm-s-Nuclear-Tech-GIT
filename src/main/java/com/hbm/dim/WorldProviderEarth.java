package com.hbm.dim;

import net.minecraft.world.DimensionType;

public class WorldProviderEarth extends WorldProviderCelestial {

    @Override
    public void init() {
        this.biomeProvider = this.world.provider.getBiomeProvider();
        this.hasSkyLight = true;
    }

    @Override
    public boolean hasLife() {
        return true;
    }

    @Override
	public boolean canRespawnHere() {
		return true;
	}

    @Override
    public DimensionType getDimensionType(){return DimensionType.OVERWORLD;}
    
}
