package com.hbm.blocks.fluid;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class RadWaterBlock extends BlockFluidClassic {

	private DamageSource damageSource;
	public int color;
	
	public RadWaterBlock(Fluid fluid, DamageSource source, String s, int color) {
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
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial().isLiquid())
			return true;
		return super.canDisplace(world, pos);
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
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(entityIn instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entityIn, HazardType.RADIATION, ContaminationType.CREATIVE, 1.0F);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
		super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
		reactToBlocks(world, pos.east(), 2);
		reactToBlocks(world, pos.west(), 2);
		reactToBlocks(world, pos.up(), 1);
		reactToBlocks(world, pos.down(), 3);
		reactToBlocks(world, pos.south(), 2);
		reactToBlocks(world, pos.north(), 2);
	}
	
	public void reactToBlocks(World world, BlockPos pos, int flag) {
		if(world.getBlockState(pos).getMaterial() != Material.WATER) {
			IBlockState block = world.getBlockState(pos);
			
			if(block.getMaterial() == Material.LAVA) {
				//flag 1 == pos is on top, flag 2 == pos is on side, flag 3 == pos is on bottom
				if(flag == 1){
					world.setBlockState(pos, ModBlocks.sellafield_slaked.getDefaultState());
				}else if(flag == 2){
					world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
				}else if(flag == 3){
					world.setBlockState(pos, ModBlocks.gravel_obsidian.getDefaultState());
				}
			}
		}
	}
	
	@Override
	public int tickRate(World world) {
		return 15;
	}
	
}
