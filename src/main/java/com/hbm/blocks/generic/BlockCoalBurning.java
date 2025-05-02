package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCoalBurning extends BlockOre {

	public BlockCoalBurning(String s) {
		super();
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		super.randomDisplayTick(state, world, pos, rand);
		for(EnumFacing dir : EnumFacing.VALUES) {

        	if(dir == EnumFacing.DOWN)
        		continue;

        	if(world.getBlockState(pos.offset(dir)).getMaterial() == Material.AIR) {

        		double ix = pos.getX() + 0.5F + dir.getXOffset() + rand.nextDouble() - 0.5D;
        		double iy = pos.getY() + 0.5F + dir.getYOffset() + rand.nextDouble() - 0.5D;
        		double iz = pos.getZ() + 0.5F + dir.getZOffset() + rand.nextDouble() - 0.5D;

        		if(dir.getXOffset() != 0)
        			ix = pos.getX() + 0.5F + dir.getXOffset() * 0.5 + rand.nextDouble() * 0.125 * dir.getXOffset();
        		if(dir.getYOffset() != 0)
        			iy = pos.getY() + 0.5F + dir.getYOffset() * 0.5 + rand.nextDouble() * 0.125 * dir.getYOffset();
        		if(dir.getZOffset() != 0)
        			iz = pos.getZ() + 0.5F + dir.getZOffset() * 0.5 + rand.nextDouble() * 0.125 * dir.getZOffset();

        		world.spawnParticle(EnumParticleTypes.FLAME, ix, iy, iz, 0.0, 0.0, 0.0);
        		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, ix, iy, iz, 0.0, 0.0, 0.0);
        		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, ix, iy, iz, 0.0, 0.1, 0.0);
        	}
        }
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		entityIn.setFire(3);
	}
}
