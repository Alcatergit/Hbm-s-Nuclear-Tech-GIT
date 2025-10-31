package com.hbm.blocks.fluid;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockLiquidCorium extends BlockFluidClassic {

	public Random rand = new Random();

	public BlockLiquidCorium(Fluid fluid,Material material,String s) {
		super(fluid, material);
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
	public boolean canDisplace(IBlockAccess world, BlockPos pos){
		IBlockState b = world.getBlockState(pos);
		@SuppressWarnings("deprecation")
		float res = (float) (Math.sqrt(b.getBlock().getExplosionResistance(null)) * 2)/(1+rand.nextInt(2));
		
		if(res < 1)
			return true;
		
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn,World world,BlockPos pos,Random rand) {
		super.randomDisplayTick(stateIn,world,pos,rand);
		if (rand.nextInt(5) == 0) {
			if (rand.nextInt(20) == 0)
				world.playSound(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.7F + rand.nextFloat() * 0.25F, false);
			{
				int amt = rand.nextInt(2)+1;
				for (int i = 0; i < amt; i++)
					world.spawnParticle(
							EnumParticleTypes.LAVA,
							pos.getX()+rand.nextFloat(),pos.getY()+rand.nextFloat(),pos.getZ()+rand.nextFloat(),
							0,0,0
					);
			}
			{
				int amt = rand.nextInt(3);
				for (int i = 0; i < amt; i++)
					world.spawnParticle(
							EnumParticleTypes.SMOKE_NORMAL,
							pos.getX()+rand.nextFloat(),pos.getY()+rand.nextFloat(),pos.getZ()+rand.nextFloat(),
							rand.nextDouble()/2-0.25,rand.nextDouble()/2-0.25,rand.nextDouble()/2-0.25
					);
			}
			{
				int amt = rand.nextInt(3);
				for (int i = 0; i < amt; i++)
					world.spawnParticle(
							EnumParticleTypes.SMOKE_LARGE,
							pos.getX()+rand.nextFloat(),pos.getY()+rand.nextFloat(),pos.getZ()+rand.nextFloat(),
							rand.nextDouble()/2-0.25,rand.nextDouble()/2-0.25,rand.nextDouble()/2-0.25
					);
			}
		}
		if (rand.nextInt(100) == 0)
			world.playSound(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.7F + rand.nextFloat() * 0.25F, false);
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
		return false;
	}
}