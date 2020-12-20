package com.hbm.tileentity.deco;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityPinkCloudFX;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityVent extends TileEntity implements ITickable {

	Random rand = new Random();
	
	@Override
	public void update() {
		if(!world.isRemote && world.isBlockIndirectlyGettingPowered(pos) > 0) {
			Block b = world.getBlockState(pos).getBlock();

			if(b == ModBlocks.vent_chlorine) {
				//if(rand.nextInt(1) == 0) {
					double x = rand.nextGaussian() * 1.5;
					double y = rand.nextGaussian() * 1.5;
					double z = rand.nextGaussian() * 1.5;
					
					if(!world.getBlockState(new BlockPos(pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z)).isNormalCube()) {
						world.spawnEntity(new EntityChlorineFX(world, pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z, x/2, y/2, z/2));
					}
				//}
			}
			if(b == ModBlocks.vent_cloud) {
				//if(rand.nextInt(50) == 0) {
				double x = rand.nextGaussian() * 1.75;
				double y = rand.nextGaussian() * 1.75;
				double z = rand.nextGaussian() * 1.75;
				
				if(!world.getBlockState(new BlockPos(pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z)).isNormalCube()) {
					world.spawnEntity(new EntityCloudFX(world, pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z, x/2, y/2, z/2));
					}
				//}
			}
			if(b == ModBlocks.vent_pink_cloud) {
				//if(rand.nextInt(65) == 0) {
				double x = rand.nextGaussian() * 2;
				double y = rand.nextGaussian() * 2;
				double z = rand.nextGaussian() * 2;
				
				if(!world.getBlockState(new BlockPos(pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z)).isNormalCube()) {
					world.spawnEntity(new EntityPinkCloudFX(world, pos.getX() + (int)x, pos.getY() + (int)y, pos.getZ() + (int)z, x/2, y/2, z/2));
					}
				//}
			}
		}
	}

}
