

package com.hbm.dim.eve.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseEve extends BiomeGenBaseCelestial {

	public static final Biome evePlains = new BiomeGenEvePlains(new BiomeProperties("Eve Plains").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(1.0F).setRainfall(0.5F));
	public static final Biome eveOcean = new BiomeGenEveOcean(new BiomeProperties("Explodium Ocean").setBaseHeight(-0.6F).setHeightVariation(0.01F).setTemperature(1.0F).setRainfall(0.5F));
	public static final Biome eveMountains = new BiomeGenEveMountains(new BiomeProperties("Eve Mountains").setBaseHeight(0.525F).setHeightVariation(0.4F).setTemperature(1.0F).setRainfall(0.5F));
	public static final Biome eveSeismicPlains = new BiomeGenEveSeismicPlains(new BiomeProperties("Eve Seismic Plains").setBaseHeight(0.270F).setHeightVariation(0.3F).setTemperature(1.0F).setRainfall(0.5F));
	public static final Biome eveRiver = new BiomeGenEveRiver(new BiomeProperties("Explodium River").setBaseHeight(-0.7F).setHeightVariation(0.0F).setTemperature(1.0F).setRainfall(0.5F));

	public BiomeGenBaseEve(BiomeProperties properties) {
		super(properties);
		properties.setWaterColor(0x5b009a);

		this.decorator = new BiomeDecoratorCelestial(ModBlocks.eve_rock);
		
		this.topBlock = ModBlocks.eve_silt.getDefaultState();
		this.fillerBlock = ModBlocks.eve_rock.getDefaultState();
	}
}