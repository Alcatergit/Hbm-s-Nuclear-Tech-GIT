
package com.hbm.dim.duna.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.BiomeDecoratorCelestial;
import com.hbm.dim.BiomeGenBaseCelestial;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class BiomeGenBaseDuna extends BiomeGenBaseCelestial {
    
    public static final Biome dunaPlains = new BiomeGenDunaPlains(new BiomeProperties("Dunaian Plains").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(-1.0F).setRainfall(0.0F));
    public static final Biome dunaLowlands = new BiomeGenDunaLowlands(new BiomeProperties("Dunaian Lowland Plains").setBaseHeight(-0.6F).setHeightVariation(0.01F).setTemperature(-1.0F).setRainfall(0.0F));
    public static final Biome dunaPolar = new BiomeGenDunaPolar(new BiomeProperties("Dunaian Ice Sheet").setBaseHeight(0.425F).setHeightVariation(0.05F).setTemperature(-1.0F).setRainfall(0.0F));
    public static final Biome dunaHills = new BiomeGenDunaHills(new BiomeProperties("Weathered Dunaian Hills").setBaseHeight(0.525F).setHeightVariation(0.51F).setTemperature(-1.0F).setRainfall(0.0F));
    public static final Biome dunaPolarHills = new BiomeGenDunaPolarHills(new BiomeProperties("Dunaian Polar Mountains").setBaseHeight(0.725F).setHeightVariation(0.8F).setTemperature(-1.0F).setRainfall(0.0F));
    
    public BiomeGenBaseDuna(BiomeProperties properties) {
        super(properties);
        properties.setRainDisabled();

        this.decorator = new BiomeDecoratorCelestial(ModBlocks.duna_rock);
        this.decorator.generateFalls = false;
		this.topBlock = ModBlocks.duna_sands.getDefaultState();
		this.fillerBlock = ModBlocks.duna_rock.getDefaultState();
    }
}