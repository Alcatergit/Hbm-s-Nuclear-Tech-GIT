package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DigammaMatter extends Block {
	
	public static final PropertyInteger META = BlockDummyable.META;
	
	private static Random rand = new Random(); 

	public DigammaMatter(String s) {
		super(Material.PORTAL);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
		return NULL_AABB;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		float pixel = 0.0625F;
		return new AxisAlignedBB(rand.nextInt(9) * pixel, rand.nextInt(9) * pixel, rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel, 1.0F - rand.nextInt(9) * pixel);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		if(!world.isRemote)
			world.scheduleUpdate(pos, this, 10 + rand.nextInt(40));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote) {
			
			int meta = state.getValue(META);
			
			if(meta >= 7) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
			} else {

				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				//world.setBlock(x, y, z, this, meta + 1, 3);
				
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						for(int k = -1; k <= 1; k++) {
							
							int dist = Math.abs(i) + Math.abs(j) + Math.abs(k);
							
							if(dist > 0 && dist < 3) {
								if(world.getBlockState(new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k)).getBlock() != this)
									world.setBlockState(new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k), state.withProperty(META, meta+1), 3);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(META);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(META, meta);
	}
	
}