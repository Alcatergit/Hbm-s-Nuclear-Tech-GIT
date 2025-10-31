package com.leafia.contents.worldgen.biomes;

import com.hbm.blocks.ModBlocks;
import com.leafia.contents.worldgen.ModBiome;
import com.leafia.contents.worldgen.ModBiomes;
import com.leafia.contents.worldgen.biomes.effects.HasAcidicRain;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;

public class Ruins extends ModBiome implements HasAcidicRain {
	@Nullable
	@Override
	public GenLayer[] overrideGenLayers(long seed,GenLayer[] layers,int shaperIndex,int decoratorIndex,int shaperScale) {
		return ModBiomes.barrens.overrideGenLayers(seed,layers,shaperIndex,decoratorIndex,shaperScale);
	}
	public Ruins(String resource) {
		super(resource,
				new BiomeProperties("Ruins")
						.setBaseHeight(0.1f)
						.setHeightVariation(0f)
						.setRainfall(0.85f)
						.setTemperature(1.52f)
						.setWaterColor(0x2f2c17)//0x737163)
		);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();

		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 65, 3, 6));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieVillager.class, 88, 8, 14));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 1, 2));
		//this.spawnableMonsterList.add(new SpawnListEntry(EntityNuclearCreeper.class, 40, 1, 1)); hmmmm no not yet that makes him no longer so special
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();

		this.decorator.treesPerChunk = -999;

		this.topBlock = ModBlocks.waste_earth.getStateFromMeta(5);
		this.fillerBlock = ModBlocks.waste_dirt.getStateFromMeta(5);

		this.postInit = ()->{
			//BiomeManager.addBiome(BiomeType.DESERT,new BiomeEntry(this,15));
			BiomeDictionary.addTypes(this,Type.DEAD,Type.DRY,Type.WASTELAND);
		};
	}
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0x242318;
	}
	@Override
	public int getFogColor() {
		return getSkyColorByTemp(0);
	}
	@Override
	public float getFogDensity(float original) { return Math.max(original,0.75f); }
	@Override
	public float getFogStart(float original) { return original*-0.01f; }
	@Override
	public float getFogEnd(float original) { return original*0.35f; }
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x0a0909;
	}
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x0a0909;
	}
}
