package com.leafia.contents.worldgen;

import com.leafia.contents.worldgen.biomes.Barrens;
import com.leafia.contents.worldgen.biomes.Ruins;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.event.terraingen.WorldTypeEvent.InitBiomeGens;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraftforge.fml.common.eventhandler.Event.Result.DENY;

public class ModBiomesGenerator {
	@SubscribeEvent
	public void initLayers(InitBiomeGens evt) {
		/*NoiseGeneratorPerlin pawlin = new NoiseGeneratorPerlin(new Random(evt.getSeed()),2);
		NoiseGeneratorPerlin fract = new NoiseGeneratorPerlin(new Random(evt.getSeed()),4);
		double sc0 = 0.001;
		double div0 = 10;
		double div1 = 5;
		BiFunction<Long,Long,Double> getNz = (x,y)->{
			double nz0 = Math.max(pawlin.getValue(x/div0*sc0,y/div0*sc0)*2-0.5,0);
			double nz1 = fract.getValue(x/div1*5.53*sc0,y/div1*5.53*sc0)*0.1*nz0;
			return nz0+nz1;
		};
		GenLayer shaper = new GenLayer(1000) {
			final GenLayer base = layers[0];
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				int[] original = base.getInts(areaX,areaY,areaWidth,areaHeight);
				int[] biomes = IntCache.getIntCache(areaWidth*areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						this.initChunkSeed((long)areaX+x,(long)areaY+y);
						int og = original[x+y*areaWidth];
						if (Math.floorMod((x+areaX)*4,320) >= 320/2) //if (getNz.apply((long)areaX+x,(long)areaY+y) > 0)
							biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.barrens);
						else
							biomes[x+y*areaWidth] = og;
					}
				}
				return biomes;
			}
		};
		GenLayer decorator = new GenLayer(1000) {
			final GenLayer base = layers[1];
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				int[] original = base.getInts(areaX,areaY,areaWidth,areaHeight);
				int[] biomes = IntCache.getIntCache(areaWidth*areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						this.initChunkSeed((long)areaX+x,(long)areaY+y);
						int og = original[x+y*areaWidth];
						if (Math.floorMod(x+areaX,320) >= 320/2) //if (getNz.apply((long)areaX+x,(long)areaY+y) > 0)
							biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.barrens); // :troll:
						else
							biomes[x+y*areaWidth] = og;
					}
				}
				return biomes;
			}
		};
		layers[0] = shaper;
		layers[1] = decorator;
		*/
		GenLayer[] layers = evt.getOriginalBiomeGens();
		for (ModBiome biome : ModBiomes.ALL_BIOMES) {
			GenLayer[] copy = new GenLayer[layers.length];
			System.arraycopy(layers,0,copy,0,layers.length);
			GenLayer[] newLayer = biome.overrideGenLayers(evt.getSeed(),copy,0,1,4);
			if (newLayer != null)
				layers = newLayer;
		}
		evt.setNewBiomeGens(layers);
		for (GenLayer layer : layers) {
			System.out.println("######### LAYER: "+layer.getClass().toString());
		}
	}
	@SubscribeEvent
	public void onPopulateChunk(Populate evt) {
		Biome biome = evt.getWorld().getBiome(new BlockPos(evt.getChunkX()*16+16,0,evt.getChunkZ()*16+16));
		switch(evt.getType()) {
			case LAKE: case LAVA:
				if (biome instanceof Barrens || biome instanceof Ruins)
					evt.setResult(DENY);
				break;
		}
	}
	@SubscribeEvent
	public void onInitMapGen(InitMapGenEvent evt) {

	}
}