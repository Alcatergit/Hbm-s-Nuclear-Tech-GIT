package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTaint extends Block {
	public BlockTaint(Material m, String s) {
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.tabTest);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
    	if(!world.isRemote && meta < 15) {
    		
	    	for(int i = 0; i < 15; i++) {
	    		int a = rand.nextInt(11) + x - 5;
	    		int b = rand.nextInt(11) + y - 5;
	    		int c = rand.nextInt(11) + z - 5;
	    		BlockPos pos = pos1.add(a, b, c);
	            if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && hasPosNeightbour(world, pos))
	            	world.setBlockState(pos, ModBlocks.taint.getBlockState(), meta + 1, 2);
	            world.setbl
	    	}
	            
		    for(int i = 0; i < 85; i++) {
		    	int a = rand.nextInt(7) + x - 3;
		    	int b = rand.nextInt(7) + y - 3;
		    	int c = rand.nextInt(7) + z - 3;
		           if(world.getBlock(a, b, c).isReplaceable(world, a, b, c) && hasPosNeightbour(world, a, b, c))
		           	world.setBlock(a, b, c, ModBlocks.taint, meta + 1, 2);
		    }
    	}
	}
	
	 public static boolean hasPosNeightbour(World world, BlockPos pos) {
	    	Block b0 = world.getBlockState(pos.add(1, 0, 0)).getBlock();
	    	Block b1 = world.getBlockState(pos.add(0, 1, 0)).getBlock();
	    	Block b2 = world.getBlockState(pos.add(0, 0, 1)).getBlock();
	    	Block b3 = world.getBlockState(pos.add(-1, 0, 0)).getBlock();
	    	Block b4 = world.getBlockState(pos.add(0, -1, 0)).getBlock();
	    	Block b5 = world.getBlockState(pos.add(0, 0, -1)).getBlock();
	    	boolean b = b0.isNormalCube(world.getBlockState(pos.add(1, 0, 0)), world, pos) ||
	    			b1.isNormalCube(world.getBlockState(pos.add(0, 1, 0)), world, pos) ||
	    			b2.isNormalCube(world.getBlockState(pos.add(0, 0, 1)), world, pos) ||
	    			b3.isNormalCube(world.getBlockState(pos.add(-1, 0, 0)), world, pos) ||
	    			b4.isNormalCube(world.getBlockState(pos.add(0, -1, 0)), world, pos) ||
	    			b5.isNormalCube(world.getBlockState(pos.add(0, 0, -1)), world, pos);
	    	return b;
	    }
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return new AxisAlignedBB(pos, pos);
	}
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
		int level = 15 - meta;
		
    	List<ItemStack> list = new ArrayList<ItemStack>();
    	PotionEffect effect = new PotionEffect(HbmPotion.taint, 15 * 20, level);
    	effect.setCurativeItems(list);
    	
    	if(entity instanceof EntityLivingBase) {
    		if(world.rand.nextInt(50) == 0) {
    			((EntityLivingBase)entity).addPotionEffect(effect);
    		}
    	}
    	
    	if(entity instanceof EntityCreeper) {
    		EntityTaintedCreeper creep = new EntityTaintedCreeper(world);
    		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

    		if(!world.isRemote) {
    			entity.setDead();
    			world.spawnEntityInWorld(creep);
    		}
    	}
	}
}
