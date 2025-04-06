package com.hbm.dim.moho.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.Biome;

public abstract class BiomeGenBaseMoho extends BiomeGenBaseCelestial {

	public static final Biome mohoCrag = new BiomeGenMohoCrag(new BiomeProperties("Moho Crag").setBaseHeight(0.275F).setHeightVariation(0.666F));
	public static final Biome mohoBasalt = new BiomeGenMohoBasalt(new BiomeProperties("Moho Basalt Deltas").setBaseHeight(0).setHeightVariation(0.224F));

	public BiomeGenBaseMoho(BiomeProperties properties) {
		super(properties);
		properties.setRainDisabled();

		BiomeDecoratorCelestial decorator = new BiomeDecoratorCelestial(ModBlocks.moho_stone);
		decorator.lavaCount = 50;
		this.decorator = decorator;

		properties.setTemperature(1.0F);
		properties.setRainfall(0.0F);
	}

}
