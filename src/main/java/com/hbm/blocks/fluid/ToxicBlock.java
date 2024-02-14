package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class ToxicBlock extends BlockFluidClassic {

	private DamageSource damageSource;
	
	public ToxicBlock(Fluid fluid, Material material, DamageSource source, String s) {
		super(fluid, material);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		this.setQuantaPerBlock(4);
		this.damageSource = source;
		this.displacements.put(this, false);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial().isLiquid())
			return false;
		return super.canDisplace(world, pos);
	}
	
	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial().isLiquid())
			return false;
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setInWeb();
		
		if(entityIn instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entityIn, HazardType.RADIATION, ContaminationType.CREATIVE, 50.0F);
		else if(entityIn instanceof EntityFallingBlock)
			entityIn.setDead();
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(reactToBlocks(world, pos.east()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.west()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.up()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.down()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.south()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.north()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		super.updateTick(world, pos, state, rand);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
		if(reactToBlocks(world, pos.east()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.west()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.up()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.down()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.south()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		if(reactToBlocks(world, pos.north()))
			world.setBlockState(pos, ModBlocks.sellafield_core.getStateFromMeta(world.rand.nextInt(4)));
		super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
	}
	
	public boolean reactToBlocks(World world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial() != ModBlocks.fluidtoxic) {
			if(world.getBlockState(pos).getMaterial().isLiquid()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int tickRate(World world) {
		return 15;
	}
}
