package com.hbm.blocks.generic;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;
import com.leafia.shit.BlockFallingBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGoldSand extends BlockFallingBase implements IItemHazard {

	ItemHazardModule module;

	public BlockGoldSand(Material m, String s, SoundType type){
		super(m, s, type);
		this.module = new ItemHazardModule();
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase)entity, 0.5F, 0, false, EnumHand.MAIN_HAND);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		if(entity instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase)entity, 0.5F, 0, false, EnumHand.MAIN_HAND);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}
}
