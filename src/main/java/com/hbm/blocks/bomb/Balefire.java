package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Balefire extends BlockFire {

	public Balefire(String s) {
		super();
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override 
	protected boolean canDie(World worldIn, BlockPos pos){
		Block b = worldIn.getBlockState(pos.down()).getBlock();

		return (b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core);
	}

	@Override 
	public int getFlammability(Block b){
		if(b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core){
			return 20000;
		}
		return super.getEncouragement(b);
	}

	@Override 
	public int getEncouragement(Block b){
		if(b != ModBlocks.baleonitite_slaked && 
			b != ModBlocks.baleonitite_1 && 
			b != ModBlocks.baleonitite_2 && 
			b != ModBlocks.baleonitite_3 && 
			b != ModBlocks.baleonitite_4 && 
			b != ModBlocks.baleonitite_core){
			return 20000;
		}
		return super.getEncouragement(b);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setFire(10);

		if (entityIn instanceof EntityLivingBase)
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(HbmPotion.radiation, 5 * 20, 9));
	}
}
