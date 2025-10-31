package com.leafia.contents.blockfluids.fluoride;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockLiquidFluoride extends BlockFluidClassic {

	private DamageSource damageSource;
	
	public BlockLiquidFluoride(Fluid fluid,Material material,String s) {
		super(fluid, material);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		//this.setQuantaPerBlock(4);
		this.damageSource = DamageSource.ON_FIRE;
		this.displacements.put(this, false);
		//this.tickRate = 30;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Vec3d getFogColor(World world,BlockPos pos,IBlockState state,Entity entity,Vec3d originalColor,float partialTicks) {
		return new Vec3d(0.851,0.867,0.765);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		//if(world.getBlockState(pos).getMaterial().isLiquid())
		//	return true;
		return super.canDisplace(world, pos);
	}
	
	// @Override
	// public boolean displaceIfPossible(World world, BlockPos pos) {
	// 	return super.displaceIfPossible(world, pos);
	// }

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
		//entity.setInWeb();
		entity.setFire(3);
	}

	@Override
	public int tickRate(World world) {
		return 30;
	}
}
