package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CoriumBlock extends BlockFluidClassic {

	public Random rand = new Random();
	public int color;

	public CoriumBlock(Fluid fluid, String s, int color) {
		super(fluid, Material.LAVA);
		this.color = color;
		this.setTranslationKey(s);
		this.setRegistryName(s);
		setQuantaPerBlock(5);
		setCreativeTab(null);
		displacements.put(this, false);
		this.tickRate = 30;
		
		this.setTickRandomly(true);
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
	public boolean canDisplace(IBlockAccess world, BlockPos pos){
		IBlockState b = world.getBlockState(pos);
		@SuppressWarnings("deprecation")
		float res = (float) (Math.sqrt(b.getBlock().getExplosionResistance(null)) * 2);
		
		if(res < 1)
			return true;
		Random rand = new Random();
		
		return b.getMaterial().isLiquid() || rand.nextInt((int) res) == 0;
	}
	
	@Override
	public boolean displaceIfPossible(World world, BlockPos pos){
		if(world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return canDisplace(world, pos);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		entity.setInWeb();
		entity.setFire(3);
		entity.attackEntityFrom(ModDamageSource.radiation, 200F);
		
		if(entity instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, 500F);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		super.updateTick(world, pos, state, rand);
		
		if(!world.isRemote && rand.nextInt(10) == 0) {
			
			if(this.isSourceBlock(world, pos))
				world.setBlockState(pos, ModBlocks.block_corium.getDefaultState());
			else
				world.setBlockState(pos, ModBlocks.block_corium_cobble.getDefaultState());
		}
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
		return false;
	}
}