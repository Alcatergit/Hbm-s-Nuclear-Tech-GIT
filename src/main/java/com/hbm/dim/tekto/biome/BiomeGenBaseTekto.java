

package com.hbm.dim.tekto.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public abstract class BiomeGenBaseTekto extends BiomeGenBaseCelestial {

	public static final Biome polyvinylPlains = Biomes.OCEAN;//new BiomeGenPolyvinylPlains(SpaceConfig.tektoPolyvinyl).setTemperatureRainfall(1.0F, 0.5F);
	public static final Biome halogenHills = Biomes.OCEAN;//new BiomeGenHalogenHills(SpaceConfig.HalogenHill).setTemperatureRainfall(1.0F, 0.5F);
	public static final Biome tetrachloricRiver = Biomes.OCEAN;//new BiomeGenTetrachloricRiver(SpaceConfig.TektoRiver).setTemperatureRainfall(1.0F, 0.5F);

	public BiomeGenBaseTekto(BiomeProperties properties) {
		super(properties);
		properties.setWaterColor(0x5b009a);

		this.decorator = new BiomeDecoratorCelestial(ModBlocks.sand_uranium);
		
		this.topBlock = ModBlocks.sand_boron.getDefaultState();
		this.fillerBlock = ModBlocks.sand_boron_layer.getDefaultState();
	}
}