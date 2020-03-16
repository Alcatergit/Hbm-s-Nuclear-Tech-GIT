package com.hbm.world;

import com.hbm.blocks.ModBlocks;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class OilBubble {

	public static void spawnOil(World world, int x, int y, int z, int radius) {
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;

		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for(int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for(int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy * 3;
				for(int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if(ZZ < r22) {
						pos.setPos(X, Y, Z);
						if(world.getBlockState(pos).getBlock() == Blocks.STONE)
							world.setBlockState(pos, ModBlocks.ore_oil.getDefaultState());
					}
				}
			}
		}
	}

}
