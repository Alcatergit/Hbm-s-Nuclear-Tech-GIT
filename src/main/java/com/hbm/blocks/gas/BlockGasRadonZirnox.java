package com.hbm.blocks.gas;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorUtil;
import com.hbm.lib.ForgeDirection;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGasRadonZirnox extends BlockGasBase {

	public BlockGasRadonZirnox(String s) {
		super(0.1F, 0.4F, 0.1F, s);
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity){
		if(!(entity instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entityLiving = (EntityLivingBase) entity;
		ContaminationUtil.contaminate(entityLiving, HazardType.RADIATION, ContaminationType.CREATIVE, 0.5F);
		entityLiving.addPotionEffect(new PotionEffect(HbmPotion.radiation, 120, 2));
		
		if(ArmorRegistry.hasProtection(entityLiving, EntityEquipmentSlot.HEAD, HazardClass.RAD_GAS)) {
			ArmorUtil.damageGasMaskFilter(entityLiving, 1);
			if(ArmorRegistry.hasProtection(entityLiving, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_FINE))
				ArmorUtil.damageGasMaskFilter(entityLiving, 1);
			else {
				ContaminationUtil.contaminate(entityLiving, HazardType.RADIATION, ContaminationType.CREATIVE, 0.2F);
				ContaminationUtil.applyAsbestos(entity, 3, 1);
			}
		} else {
			ContaminationUtil.contaminate(entityLiving, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 1.5F);
			ContaminationUtil.applyAsbestos(entityLiving,5,0); // Mesothelioma can be developed as a result of exposure to radiation in the lungs
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}
	
	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));

			if(rand.nextInt(7) == 0 && world.getBlockState(pos.add(dir.offsetX,dir.offsetY,dir.offsetZ)).getBlock() == Blocks.AIR) {
				world.setBlockState(pos,ModBlocks.gas_radon_dense.getDefaultState());
			}

			RadiationSavedData.incrementRad(world, pos, 5, 5);
			
			if(rand.nextInt(10) == 0) {
				if(world.getBlockState(pos.down()).getBlock() == Blocks.GRASS)
					world.setBlockState(pos.down(), ModBlocks.waste_earth.getDefaultState());
			}
	
			if(rand.nextInt(350) == 0) {
				world.setBlockToAir(pos);
				return;
			}
		}
		
		super.updateTick(world, pos, state, rand);
		world.scheduleUpdate(pos, this, this.tickRate(world));
	}
}