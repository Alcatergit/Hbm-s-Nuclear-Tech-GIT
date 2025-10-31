package com.leafia.contents.worldgen.biomes;

import com.hbm.blocks.ModBlocks;
import com.leafia.contents.worldgen.ModBiome;
import com.leafia.contents.worldgen.ModBiomes;
import com.leafia.contents.worldgen.biomes.effects.HasAcidicRain;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.BiFunction;

public class Barrens extends ModBiome implements HasAcidicRain {
	@Nullable
	@Override
	public GenLayer[] overrideGenLayers(long seed,GenLayer[] layers,int shaperIndex,int decoratorIndex,int shaperScale) {
		NoiseGeneratorPerlin pawlin = new NoiseGeneratorPerlin(new Random(seed),2);
		NoiseGeneratorPerlin fract = new NoiseGeneratorPerlin(new Random(seed),4);
		int innerExponent = 3;
		int exponent = 7;
		int exponentP = 1;
		int radius = 1<<exponent-1;
		double sc0 = 0.001*(1L<<exponentP);
		double div0 = 10;
		double div1 = 5;
		BiFunction<Long,Long,Double> getNz = (x,y)->{
			double nz0 = Math.max(pawlin.getValue(x/div0*sc0,y/div0*sc0)*2-0.5,0);
			double nz1 = fract.getValue(x/div1*5.53*sc0,y/div1*5.53*sc0)*0.1*nz0;
			return nz0+nz1;
		};
		GenLayer coreLayer = new GenLayer(seed) {
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				// Generate the center of the sellacity biome
				int rd = 0;
				int ds = 4;
				int ax = areaX-rd;
				int ay = areaY-rd;
				int aw = areaWidth+rd*2;
				int ah = areaHeight+rd*2;
				int[] map = IntCache.getIntCache(aw*ah);
				for (int x = 0; x < aw; x++) {
					for (int y = 0; y < ah; y++) {
						this.initChunkSeed(((long)ax+x)/ds,((long)ay+y)/ds);
						map[x+y*aw] = (this.nextInt(1) == 0
								&& (this.nextInt(ds) == Math.floorMod((long)ax+x,ds) && this.nextInt(ds) == Math.floorMod((long)ay+y,ds))
								&& getNz.apply(
										(((long)ax+x)<<exponent)+(1<<exponent)/2,(((long)ay+y)<<exponent)+(1<<exponent)/2) > 0
						) ? 1 : 0;
					}
				}
				if (rd == 0) return map;
				int[] inbound = IntCache.getIntCache(areaWidth*areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						int mx = x+rd;
						int my = (y+rd)*aw;
						boolean nope = true;
						if (map[mx+my] > 0) {
							nope = false;
							for (int ox = -rd; ox <= rd && !nope; ox++) {
								for (int oy = -rd; oy <= rd && !nope; oy++) {
									if (ox == 0 && oy == 0) continue;
									if (map[mx+my+ox+oy*aw] > 0) nope = true;
								}
							}
						}
						inbound[x+y*areaWidth] = nope ? 0 : 1;
					}
				}
				return inbound;
			}
		};
		GenLayer zoomInner = GenLayerZoom.magnify(seed-3350,coreLayer,innerExponent);
		GenLayer sepaLayer = new GenLayer(seed) {
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				// Separate outer areas and middle areas of sellacity biome using a method similar to RemoveTooMuchOcean
				int rd = 1;
				int ax = areaX-rd;
				int ay = areaY-rd;
				int aw = areaWidth+rd*2;
				int ah = areaHeight+rd*2;
				int[] map = zoomInner.getInts(ax,ay,aw,ah);
				int[] inbound = IntCache.getIntCache(areaWidth*areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						int mx = x+rd;
						int my = (y+rd)*aw;
						if (map[mx+my] > 0) {
							inbound[x+y*areaWidth] = (map[mx-1+my]>0 && map[mx+1+my]>0 && map[mx+my-aw]>0 && map[mx+my+aw]>0) ? 2 : 1;
						} else
							inbound[x+y*areaWidth] = 0;
					}
				}
				return inbound;
			}
		};
		GenLayer zoom = GenLayerZoom.magnify(seed-3350+innerExponent,sepaLayer,exponent-innerExponent);
		GenLayer pawLayer = new GenLayer(-1) {
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				// Expand areas from coreLayer by using perlin noise function.
				// This means it will iterate over areas not originally chosen by coreLayer,
				//   which mean the iterated area will *NOT always be greater than 0.
				int cw = areaWidth+radius*2;
				int[] core = zoom.getInts(areaX-radius,areaY-radius,areaWidth+radius*2,areaHeight+radius*2);
				int[] map = IntCache.getIntCache(areaWidth*areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						this.initChunkSeed((long)areaX + x,(long)areaY + y);
						int cx = x+radius;
						int cy = (y+radius)*cw;
						int n = 0; int mx = 0;
						for (int ox = -radius; ox <= radius; ox++) {
							for (int oy = -radius; oy <= radius; oy++) {
								mx++;
								if (core[cx+cy + ox+oy*cw] > 0) n++;
							}
						}
						map[x+y*areaWidth] = n/(mx/20d) - Math.pow(-getNz.apply((long)areaX+x,(long)areaY+y)+1.5,2)/4 > 0 ? (
								core[cx+cy] > 0 ? core[cx+cy] : 3
						) : 0;
					}
				}
				return map;
			}
		};
		GenLayer zoom2 = GenLayerZoom.magnify(2250,pawLayer,exponentP);
		// assuming that we will NOT get any number besides 4 for shaperScale! Needs a total rewrite if proved wrong :/
		GenLayer zoom3 = GenLayerZoom.magnify(1952,zoom2,Integer.numberOfTrailingZeros(shaperScale));
		layers[1] = new GenLayer(-1) {
			final GenLayer base = layers[decoratorIndex];
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				int[] biomes = base.getInts(areaX,areaY,areaWidth,areaHeight);
				int[] map = zoom3.getInts(areaX,areaY,areaWidth,areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						switch(map[x+y*areaWidth]) {
							case 1: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.barrens); break;
							case 2: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.ruins); break;
							case 3: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.desolation); break;
						}
						//int og = original[x+y*areaWidth];
						//if (map[x+y*areaWidth] > 0) //(Math.floorMod(x+areaX,320) >= 320/2) //if (getNz.apply((long)areaX+x,(long)areaY+y) > 0)
						//	biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.barrens); // :troll:
						//else
						//	biomes[x+y*areaWidth] = og;
					}
				}
				return biomes;
			}
		};
		layers[0] = new GenLayer(-1) {
			final GenLayer base = layers[shaperIndex];
			@Override
			public int[] getInts(int areaX,int areaY,int areaWidth,int areaHeight) {
				int[] biomes = base.getInts(areaX,areaY,areaWidth,areaHeight);
				int[] map = zoom2.getInts(areaX,areaY,areaWidth,areaHeight);
				for (int x = 0; x < areaWidth; x++) {
					for (int y = 0; y < areaHeight; y++) {
						switch(map[x+y*areaWidth]) {
							case 1: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.barrens); break;
							case 2: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.ruins); break;
							case 3: biomes[x+y*areaWidth] = Biome.getIdForBiome(ModBiomes.desolation); break;
						}
					}
				}
				return biomes;
			}
		};

		return layers;
	}
	public Barrens(String resource) {
		super(resource,
				new BiomeProperties("Barrens")
						.setBaseHeight(0.126f)
						.setHeightVariation(0.004f)
						.setRainfall(0.7f)
						.setTemperature(1.5f)
						.setWaterColor(0x2f2c17)//0x737163)
		);
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();

		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 65, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieVillager.class, 88, 3, 7));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
		//this.spawnableMonsterList.add(new SpawnListEntry(EntityNuclearCreeper.class, 40, 1, 1)); hmmmm no not yet that makes him no longer so special
		this.spawnableCaveCreatureList.clear();
		this.spawnableWaterCreatureList.clear();

		this.decorator.treesPerChunk = -999;

		this.topBlock = ModBlocks.waste_earth.getStateFromMeta(4);
		this.fillerBlock = ModBlocks.waste_dirt.getStateFromMeta(4);

		this.postInit = ()->{
			//BiomeManager.addBiome(BiomeType.DESERT,new BiomeEntry(this,15));
			BiomeDictionary.addTypes(this,Type.DEAD,Type.DRY,Type.WASTELAND);
		};
	}
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 0xc05a39;
	}
	@Override
	public int getFogColor() {
		return getSkyColorByTemp(0);
	}
	@Override
	public float getFogStart(float original) { return original*0.2f; }
	@Override
	public float getFogEnd(float original) { return original*0.6f; }
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x3b3c34;
	}
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x56362a;
	}
}
