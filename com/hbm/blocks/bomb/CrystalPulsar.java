package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class CrystalPulsar extends Block {

	public CrystalPulsar(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos1, Block blockIn, BlockPos fromPos) {
		int x = pos1.getX();
		int y = pos1.getY();
		int z = pos1.getZ();
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		if(world.getBlockState(pos.setPos(x + 1, y, z)).getBlock() == ModBlocks.crystal_virus || 
    			world.getBlockState(pos.setPos(x - 1, y, z)).getBlock() == ModBlocks.crystal_virus || 
    			world.getBlockState(pos.setPos(x, y + 1, z)).getBlock() == ModBlocks.crystal_virus || 
    			world.getBlockState(pos.setPos(x, y - 1, z)).getBlock() == ModBlocks.crystal_virus || 
    			world.getBlockState(pos.setPos(x, y, z + 1)).getBlock() == ModBlocks.crystal_virus || 
    			world.getBlockState(pos.setPos(x, y, z - 1)).getBlock() == ModBlocks.crystal_virus || 
    			!world.isRemote) {
			ExplosionChaos.hardenVirus(world, x, y, z, 10);
    	}
	}

}
