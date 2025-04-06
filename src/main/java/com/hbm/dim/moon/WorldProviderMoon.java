package com.hbm.dim.moon;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderMoon extends WorldProviderCelestial {

	@Override
	public void init() {
		this.biomeProvider = new BiomeProviderSingle(new BiomeGenMoon(new Biome.BiomeProperties("Mun").setBaseHeight(0.125F).setHeightVariation(0.05F).setRainDisabled()));
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderMoon(this.world, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.moon_rock;
	}

	@Override
	public DimensionType getDimensionType(){return DimensionType.getById(SpaceConfig.moonDimension);}

}
