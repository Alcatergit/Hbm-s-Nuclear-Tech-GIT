package com.hbm.blocks.fluid;

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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RadWaterBlock extends BlockFluidClassic {

	private DamageSource damageSource;
	
	public RadWaterBlock(Fluid fluid, Material material, DamageSource source, String s) {
		super(fluid, material);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(null);
		this.damageSource = source;
		this.displacements.put(this, false);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos) == Blocks.WATER || world.getBlockState(pos) == Blocks.FLOWING_WATER) {
			if (world.getBlockState(pos.up()).getBlock() instanceof RadWaterBlock)
				return true;
			int count = 0;
			for (EnumFacing horizon : EnumFacing.HORIZONTALS) {
				BlockPos offset = pos.offset(horizon);
				Block block = world.getBlockState(offset).getBlock();
				if (block == ModBlocks.radwater_block || block == Blocks.WATER) {
					if (isSourceBlock(world,offset)) {
						count++;
						if (count >= 3)
							return true;
					}
				}
			}
		}
		return super.canDisplace(world, pos);
	}

	@Override
	public Vec3d getFogColor(World world,BlockPos pos,IBlockState state,Entity entity,Vec3d originalColor,float partialTicks) {
		return Blocks.WATER.getFogColor(world,pos,state,entity,originalColor,partialTicks);
	}
	// @Override
	// public boolean displaceIfPossible(World world, BlockPos pos) {
	// 	return super.displaceIfPossible(world, pos);
	// }


	@Override
	public void updateTick(@NotNull World world,@NotNull BlockPos pos,@NotNull IBlockState state,@NotNull Random rand) {
		super.updateTick(world,pos,state,rand);
		if (!this.isSourceBlock(world,pos)) {
			boolean count = false;
			for (EnumFacing horizon : EnumFacing.HORIZONTALS) {
				BlockPos offset = pos.offset(horizon);
				Block block = world.getBlockState(offset).getBlock();
				if (block == ModBlocks.radwater_block || block == Blocks.WATER) {
					if (isSourceBlock(world,offset)) {
						if (!count)
							count = true;
						else {
							world.setBlockState(pos,ModBlocks.radwater_block.getDefaultState());
							break;
						}
					}
				}
			}
		}
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
		if(world.getBlockState(pos).getMaterial() != ModBlocks.fluidradwater) {
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
