package com.hbm.world.biome;


import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeGenCraterBase extends Biome {

	public static final Biome craterBiome = new BiomeGenCrater(new Biome.BiomeProperties("Crater").setRainDisabled());
	public static final Biome craterInnerBiome = new BiomeGenCraterInner(new Biome.BiomeProperties("Inner Crater").setRainDisabled());
	public static final Biome craterOuterBiome = new BiomeGenCraterOuter(new Biome.BiomeProperties("Outer Crater").setRainDisabled());

	public static void initDictionary() {
		BiomeDictionary.addTypes(craterBiome, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(craterInnerBiome, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND);
		BiomeDictionary.addTypes(craterOuterBiome, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND);
	}

	public BiomeGenCraterBase(Biome.BiomeProperties properties) {
		super(properties);
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	@Override
	public int getWaterColorMultiplier() {
		return 0xE0FFAE;
	}

	public static class BiomeGenCrater extends BiomeGenCraterBase {

		public BiomeGenCrater(Biome.BiomeProperties properties) {
			super(properties);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getGrassColorAtPos(BlockPos pos) {
			double noise = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.225D, pos.getZ() * 0.225D);
			return noise < -0.1D ? 0x606060 : 0x505050;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getFoliageColorAtPos(BlockPos pos) {
			return 0x6A7039;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getSkyColorByTemp(float temp) {
			return 0x525A52;
		}
	}

	public static class BiomeGenCraterOuter extends BiomeGenCraterBase {

		public BiomeGenCraterOuter(Biome.BiomeProperties properties) {
			super(properties);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getGrassColorAtPos(BlockPos pos) {
			double noise = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.225D, pos.getZ() * 0.225D);
			return noise < -0.1D ? 0x776F59 : 0x6F6752;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getFoliageColorAtPos(BlockPos pos) {
			return 0x6A7039;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getSkyColorByTemp(float temp) {
			return 0x6B9189;
		}
	}

	public static class BiomeGenCraterInner extends BiomeGenCraterBase {

		public BiomeGenCraterInner(Biome.BiomeProperties properties) {
			super(properties);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getGrassColorAtPos(BlockPos pos) {
			double noise = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.225D, pos.getZ() * 0.225D);
			return noise < -0.1D ? 0x404040 : 0x303030;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getFoliageColorAtPos(BlockPos pos) {
			return 0x6A7039;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public int getSkyColorByTemp(float temp) {
			return 0x424A42;
		}
	}
}
