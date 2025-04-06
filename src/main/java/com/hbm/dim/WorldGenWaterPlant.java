package com.hbm.dim;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterPlant extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
	    boolean flag = false;

	    for (int l = 0; l < 64; ++l) {
	        int px = pos.getX() + rand.nextInt(8) - rand.nextInt(8);
	        int py = pos.getY() + rand.nextInt(4) - rand.nextInt(4);
	        int pz = pos.getZ() + rand.nextInt(8) - rand.nextInt(8);
			BlockPos pPos = new BlockPos(px, py, pz);
	        boolean submerged = true;
	        for (int i = 0; i < 3; i++) {
	            if (world.getBlockState(pPos.add(0, i, 0)).getMaterial() != Material.WATER) {
	                submerged = false;
	            }
	        }

	        if (submerged && (!world.provider.hasSkyLight() || py < 254) && world.getBlockState(pPos.add(0, -1, 0)).getBlock() == ModBlocks.laythe_silt) {
	            
	            if (rand.nextBoolean()) {
					//world.setBlockState(pos, ModBlocks.plant_tall_laythe, 2);
	                //world.setBlockState(pos.add(0, 1, 0), ModBlocks.plant_tall_laythe, 2);
	            } else {
	                int height = 2 + rand.nextInt(4); 
	                if(py <= 57) {
		                for (int h = 0; h < height; ++h) {
		                    //world.setBlockState(pos.add(0, h, 0), ModBlocks.laythe_kelp, 1);
		                }	                	
	                }

	            }

	            int px2 = pos.getX() + rand.nextInt(8) - rand.nextInt(8);
	            int py2 = pos.getY() + rand.nextInt(4) - rand.nextInt(4);
	            int pz2 = pos.getZ() + rand.nextInt(8) - rand.nextInt(8);

	            if (world.getBlockState(new BlockPos(px2, py2 - 1, pz2)).getBlock() == ModBlocks.laythe_silt) {
	                //world.setBlockState(new BlockPos(px2, py2, pz2), ModBlocks.laythe_short, 2);
	            }

	            flag = true;
	        }
	    }

	    return flag;
	}

}
