package com.hbm.dim.Ike;


import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;

import net.minecraft.block.Block;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderIke extends WorldProviderCelestial {
	
	@Override
	public void init() {
		this.biomeProvider = new BiomeProviderSingle(new BiomeGenIke(new Biome.BiomeProperties("Ike").setBaseHeight(0.325F).setHeightVariation(0.05F).setRainDisabled()));
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderIke(this.world, this.getSeed(), false);
	}

	@Override
	public Block getStone() {
		return ModBlocks.ike_stone;
	}

	@Override
	public boolean updateLightmap(int[] lightmap) {
		for(int i = 0; i < 256; i++) {
			float sun = getSunBrightness(1.0F) - 0.1F;
			float sky = lightBrightnessTable[i / 16];
			float duna = Math.max(sky - sun, 0);

			int[] color = unpackColor(lightmap[i]);

			color[0] += duna * 20;
			if(color[0] > 255) color[0] = 255;

			lightmap[i] = packColor(color);
		}
		return true;
	}

	//private static ArrayList<WeightedRandomFishable> plushie;

	//private ArrayList<WeightedRandomFishable> getPlushie() {
		//if(plushie == null) {
			//plushie = new ArrayList<>();
			//plushie.add(new WeightedRandomFishable(new ItemStack(ModBlocks.plushie, 1, 1), 100));
		//}

		//return plushie;
	//}

	/// FISH ///
	//public ArrayList<WeightedRandomFishable> getFish() {
		//return getPlushie();
	//}

	//public ArrayList<WeightedRandomFishable> getJunk() {
		//return getPlushie();
	//}

	//public ArrayList<WeightedRandomFishable> getTreasure() {
		//return getPlushie();
	//}
	/// FISH ///

	@Override
	public DimensionType getDimensionType(){return DimensionType.getById(SpaceConfig.ikeDimension);}

}