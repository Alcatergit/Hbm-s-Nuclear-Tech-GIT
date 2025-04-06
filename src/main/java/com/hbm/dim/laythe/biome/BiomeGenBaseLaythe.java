/*******************************************************************************
 * Copyright 2015 SteveKunG - More Planets Mod
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package com.hbm.dim.laythe.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseLaythe extends BiomeGenBaseCelestial {

	public static final Biome laytheIsland = new BiomeGenLaytheIslands(new BiomeProperties("Laythe Islands").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.2F).setRainfall(0.2F).setWaterColor(0x5b209a));
	public static final Biome laytheOcean = new BiomeGenLaytheOcean(new BiomeProperties("Sagan Sea").setBaseHeight(-0.6F).setHeightVariation(0.01F).setTemperature(0.2F).setRainfall(0.2F));
	public static final Biome laythePolar = new BiomeGenLaythePolar(new BiomeProperties("Laythe Poles").setBaseHeight(-0.1F).setHeightVariation(0.05F).setTemperature(0.2F).setRainfall(0.2F).setWaterColor(0xC1F4FF));

	public BiomeGenBaseLaythe(BiomeProperties properties) {
		super(properties);
		properties.setWaterColor(0x5b009a);
		// no-no-no, mister fish! you won't go to your family, you will go in this ebaniy tazik blyat
		//this.waterCreatures.add(new BiomeGenBase.SpawnListEntry(EntityScutterfish.class, 10, 4, 4));

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(Blocks.STONE);
		decorator.waterPlantsPerChunk = 32;
		this.decorator = decorator;
		this.decorator.generateFalls = false;
        
        this.topBlock = ModBlocks.laythe_silt.getDefaultState();
        this.fillerBlock = ModBlocks.laythe_silt.getDefaultState();
	}
}