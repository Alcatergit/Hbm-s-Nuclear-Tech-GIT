package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BombThermo extends Block implements IBomb {

	public BombThermo(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote && worldIn.isBlockIndirectlyGettingPowered(pos) > 0)
        {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        	if(this == ModBlocks.therm_endo)
        	{
        		ExplosionThermo.freeze(worldIn, pos.getX(), pos.getY(), pos.getZ(), 15);
        		ExplosionThermo.freezer(worldIn, pos.getX(), pos.getY(), pos.getZ(), 20);
        	}

        	if(this == ModBlocks.therm_exo)
        	{
        		ExplosionThermo.scorch(worldIn, pos.getX(), pos.getY(), pos.getZ(), 15);
        		ExplosionThermo.setEntitiesOnFire(worldIn, pos.getX(), pos.getY(), pos.getZ(), 20);
        	}
        	
        	worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5.0F, true);
        }
	}
	
	

	@Override
	public void explode(World world, BlockPos pos) {
		if(world.isRemote)
			return;
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
    	if(this == ModBlocks.therm_endo)
    	{
    		ExplosionThermo.freeze(world, pos.getX(), pos.getY(), pos.getZ(), 15);
    		ExplosionThermo.freezer(world, pos.getX(), pos.getY(), pos.getZ(), 20);
    	}

    	if(this == ModBlocks.therm_exo)
    	{
    		ExplosionThermo.scorch(world, pos.getX(), pos.getY(), pos.getZ(), 15);
    		ExplosionThermo.setEntitiesOnFire(world, pos.getX(), pos.getY(), pos.getZ(), 20);
    	}
    	
    	world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 5.0F, true);
	}

}
