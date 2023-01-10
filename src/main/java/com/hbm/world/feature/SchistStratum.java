package com.hbm.world.feature;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SchistStratum {

	NoiseGeneratorPerlin noise;

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {

		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(event.getRand(), 4);
		}

		World world = event.getWorld();
		
		if(world.provider.getDimension() != 0)
			return;
		
		int cX = event.getChunkPos().x * 16;
		int cZ = event.getChunkPos().z * 16;
		
		double scale = 0.01D;
		int threshold = 5;

		for(int x = cX; x < cX + 16; x++) {
			
			for(int z = cZ; z < cZ + 16; z++) {
				
				double n = noise.getValue(x * scale, z * scale);
				if(n > threshold) {
					int range = (int)((n - threshold) * 3);
					
					if(range > 4)
						range = 8 - range;
					
					if(range < 0)
						continue;
					
					for(int y = 30 - range; y <= 30 + range; y++) {
						
						IBlockState target = world.getBlockState(new BlockPos(x, y, z));
						
						if(target.isNormalCube() && target.getMaterial() == Material.ROCK) {
							world.setBlockState(new BlockPos(x, y, z), ModBlocks.stone_gneiss.getDefaultState(), 2);
						}
					}
				}
			}
		}
	}
}