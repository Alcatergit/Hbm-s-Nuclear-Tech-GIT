package com.hbm.blocks.generic;

import com.hbm.blocks.BlockFallingBase;
import com.hbm.blocks.ModBlocks;
import com.hbm.capability.HbmLivingProps;
import com.hbm.capability.HbmLivingProps.ContaminationEffect;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGoldSand extends BlockFallingBase {

	public BlockGoldSand(Material m, String s, SoundType type){
		super(m, s, type);
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn){
		if(entityIn instanceof EntityLivingBase) {
			entityIn.attackEntityFrom(DamageSource.IN_FIRE, 2F);
			
			if(this == ModBlocks.sand_gold198) {
				HbmLivingProps.addCont((EntityLivingBase)entityIn, new ContaminationEffect(5F, 300, false));
			}
		}
	}

}
