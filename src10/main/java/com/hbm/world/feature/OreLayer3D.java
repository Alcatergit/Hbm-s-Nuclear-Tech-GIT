package com.hbm.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreLayer3D {
	
	public static int counter = 0;
	public int id;

	NoiseGeneratorPerlin noiseX;
	NoiseGeneratorPerlin noiseY;
	NoiseGeneratorPerlin noiseZ;
	
	double scaleH;
	double scaleV;
	double threshold;
	
	Block block;
	int dim = 0;
	
	public OreLayer3D(Block block) {
		this.block = block;
		this.id = counter;
		counter++;
	}
	
	public OreLayer3D setDimension(int dim) {
		this.dim = dim;
		return this;
	}
	
	public OreLayer3D setScaleH(double scale) {
		this.scaleH = scale;
		return this;
	}
	
	public OreLayer3D setScaleV(double scale) {
		this.scaleV = scale;
		return this;
	}
	
	public OreLayer3D setThreshold(double threshold) {
		this.threshold = threshold;
		return this;
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {

		World world = event.getWorld();
		
		if(world.provider.getDimension() != this.dim) return;

		if(this.noiseX == null) this.noiseX = new NoiseGeneratorPerlin(new Random(world.getSeed() + 101 + id), 4);
		if(this.noiseY == null) this.noiseY = new NoiseGeneratorPerlin(new Random(world.getSeed() + 102 + id), 4);
		if(this.noiseZ == null) this.noiseZ = new NoiseGeneratorPerlin(new Random(world.getSeed() + 103 + id), 4);

		int cX = event.getChunkPos().x * 16;
		int cZ = event.getChunkPos().z * 16;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				for(int y = 64; y > 5; y--) {
					double nX = this.noiseX.getValue(y * scaleV, z * scaleH);
					double nY = this.noiseY.getValue(x * scaleH, z * scaleH);
					double nZ = this.noiseZ.getValue(x * scaleH, y * scaleV);
					
					if(nX * nY * nZ > threshold) {
						IBlockState target = world.getBlockState(new BlockPos(x, y, z));

						if(target.isNormalCube() && target.getMaterial() == Material.ROCK) {
							world.setBlockState(new BlockPos(x, y, z), block.getDefaultState());
						}
					}
				}
			}
		}
	}
}
