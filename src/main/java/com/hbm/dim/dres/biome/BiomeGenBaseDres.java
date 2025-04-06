/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.dres.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.Biome;

public abstract class BiomeGenBaseDres extends BiomeGenBaseCelestial {

    public static final Biome dresPlains = new BiomeGenDresPlains(new BiomeProperties("Dresian Plains").setBaseHeight(0.625F).setHeightVariation(0.04F).setTemperature(-1.0F).setRainfall(0.0F));
    public static final Biome dresCanyon = new BiomeGenDresCanyon(new BiomeProperties("Dres Large Basins").setBaseHeight(-1F).setHeightVariation(0.34F).setTemperature(-1.0F).setRainfall(0.0F));
    
    public BiomeGenBaseDres(BiomeProperties properties) {
        super(properties);
        
        this.decorator = new BiomeDecoratorCelestial(ModBlocks.dres_rock);
        this.decorator.generateFalls = false;
        
        this.topBlock = ModBlocks.sellafield_slaked.getDefaultState();
        this.fillerBlock = ModBlocks.sellafield_slaked.getDefaultState();
    }

}