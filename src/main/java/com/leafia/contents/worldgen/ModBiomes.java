package com.leafia.contents.worldgen;

import com.leafia.contents.worldgen.biomes.Barrens;
import com.leafia.contents.worldgen.biomes.Desolation;
import com.leafia.contents.worldgen.biomes.Ruins;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModBiomes {
	public static final List<ModBiome> ALL_BIOMES = new ArrayList<>();

	public static final ModBiome barrens = new Barrens("barrens");
	public static final ModBiome desolation = new Desolation("outer_barrens");
	public static final ModBiome ruins = new Ruins("iturnedmcto7dtd");

	public static void init(){
		for (ModBiome biome : ALL_BIOMES) {
			ForgeRegistries.BIOMES.register(biome);
			biome.postInit.run();
		}
	}
}
