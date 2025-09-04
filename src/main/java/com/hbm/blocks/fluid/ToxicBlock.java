package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class ToxicBlock extends BlockFluidClassic {

	private DamageSource damageSource;
	public int color;
	
	public ToxicBlock(Fluid fluid, DamageSource source, String s, int color) {
		super(fluid, Material.WATER);
		this.color = color;
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		this.setQuantaPerBlock(4);
		this.damageSource = source;
		this.displacements.put(this, false);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
		if (!isWithinFluid(world, pos, ActiveRenderInfo.projectViewFromEntity(entity, partialTicks)))
		{
			BlockPos otherPos = pos.down(densityDir);
			IBlockState otherState = world.getBlockState(otherPos);
			return otherState.getBlock().getFogColor(world, otherPos, otherState, entity, originalColor, partialTicks);
		}
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		return new Vec3d(red, green, blue);
	}

	private boolean isWithinFluid(IBlockAccess world, BlockPos pos, Vec3d vec) {
		float filled = getFilledPercentage(world, pos);
		return filled < 0 ? vec.y > pos.getY() + filled + 1
				: vec.y < pos.getY() + filled;
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
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.setInWeb();
		
		if(entityIn instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entityIn, HazardType.RADIATION, ContaminationType.CREATIVE, 50.0F);
		else if(entityIn instanceof EntityFallingBlock)
			entityIn.setDead();
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(reactToBlocks(world, pos.east()))
			world.setBlockState(pos.east(), getRandomSellafite(world));
		if(reactToBlocks(world, pos.west()))
			world.setBlockState(pos.west(), getRandomSellafite(world));
		if(reactToBlocks(world, pos.up()))
			world.setBlockState(pos, getRandomSellafite(world));
		if(reactToBlocks(world, pos.down()))
			world.setBlockState(pos.down(), getRandomSellafite(world));
		if(reactToBlocks(world, pos.south()))
			world.setBlockState(pos.south(), getRandomSellafite(world));
		if(reactToBlocks(world, pos.north()))
			world.setBlockState(pos.north(), getRandomSellafite(world));

		if(world.rand.nextInt(15) == 0) RadiationSavedData.incrementRad(world, pos, 300F, 3000F);

		super.updateTick(world, pos, state, rand);
	}

	private IBlockState getRandomSellafite(World world){
		int n = world.rand.nextInt(100);
		if(n < 20) return ModBlocks.sellafield_4.getStateFromMeta(world.rand.nextInt(4));
		if(n < 60) return ModBlocks.sellafield_3.getStateFromMeta(world.rand.nextInt(4));
		return ModBlocks.sellafield_2.getStateFromMeta(world.rand.nextInt(4));
	}
	
	public boolean reactToBlocks(World world, BlockPos pos) {
		if(!world.isBlockLoaded(pos)) return false;
		if(world.getBlockState(pos).getMaterial() != Material.WATER) {
			IBlockState state = world.getBlockState(pos);
			if(state.getMaterial().isLiquid()) return true;
			if(state.getBlock() instanceof BlockStone) return true;
			if(state.getBlock() == ModBlocks.sellafield_slaked || state.getBlock() == ModBlocks.sellafield_0 || state.getBlock() == ModBlocks.sellafield_1) return true;
		}
		return false;
	}
	
	@Override
	public int tickRate(World world) {
		return 15;
	}
}
