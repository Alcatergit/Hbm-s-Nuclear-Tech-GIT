package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BombFlameWar extends Block implements IBomb {

	public BombFlameWar(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(worldIn.getRedstonePowerFromNeighbors(pos) > 0){
			explode(worldIn, pos);
		}
	}

	@Override
	public void explode(World world, BlockPos pos) {
		ExplosionChaos.explode(world, pos.getX(), pos.getY(), pos.getZ(), 15);
    	ExplosionChaos.spawnExplosion(world, pos.getX(), pos.getY(), pos.getZ(), 75);
    	ExplosionChaos.flameDeath(world, pos, 100);
	}

}
