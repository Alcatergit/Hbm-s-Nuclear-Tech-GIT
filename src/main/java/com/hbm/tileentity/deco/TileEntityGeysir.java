package com.hbm.tileentity.deco;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockGeysir;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntityWaterSplash;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityGeysir extends TileEntity implements ITickable {

	int timer;

	public static final byte range = 32;
	
	@Override
	public void update() {
		if (!this.world.isRemote && world.isAirBlock(pos.up())) {
			timer--;
			
			IBlockState state = world.getBlockState(pos);
			boolean active = state.getValue(BlockGeysir.ACTIVE);
			
			if(timer <= 0) {
				timer = getDelay();

				if(!active)
					BlockGeysir.setState(state.withProperty(BlockGeysir.ACTIVE, true), world, pos);
				else
					BlockGeysir.setState(state.withProperty(BlockGeysir.ACTIVE, false), world, pos);
			}
			
			if(active) {
				perform();
			}
		}
	}

	private void water() {
		
		int particleCount = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(range, range, range)).size();
		if(particleCount < 25){
			EntityWaterSplash fx = new EntityWaterSplash(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);

			fx.motionX = world.rand.nextGaussian() * 0.35;
			fx.motionZ = world.rand.nextGaussian() * 0.35;
			fx.motionY = 2;
			
			world.spawnEntity(fx);
		}
	}
	
	private void chlorine() {
		
		int particleCount = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(range, range, range)).size();
		if(particleCount < 25){
			for(int i = 0; i < 3; i++) {
				EntityOrangeFX fx = new EntityOrangeFX(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0.0, 0.0, 0.0);
		
				fx.motionX = world.rand.nextGaussian() * 0.45;
				fx.motionZ = world.rand.nextGaussian() * 0.45;
				fx.motionY = timer * 0.3;
				
				world.spawnEntity(fx);
			}
		}
	}
	
	private void vapor() {

		List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5, pos.getY() + 0.5, pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 2, pos.getZ() + 1.5));
		
		if (!entities.isEmpty()) {
			for (Entity e : entities) {

				if(e instanceof EntityLivingBase)
				((EntityLivingBase)e).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 0));
			}
		}
	}
	
	private void fire() {

		int range = 32;
		if(world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(range, range, range)).isEmpty())
			return;

		int particleCount = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(range, range, range)).size();
		
		if(particleCount < 25){
			if(world.rand.nextInt(3) == 0) {
				EntityShrapnel fx = new EntityShrapnel(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
				fx.motionX = world.rand.nextGaussian() * 0.05;
				fx.motionZ = world.rand.nextGaussian() * 0.05;
				fx.motionY = 0.5 + world.rand.nextDouble() * timer * 0.01;

				world.spawnEntity(fx);
			}

			if(timer % 2 == 0) //TODO: replace with actual particle
				world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5F, pos.getY() + 1.1F, pos.getZ() + 0.5F, world.rand.nextGaussian() * 0.05, 0.2, world.rand.nextGaussian() * 0.05));
		}
	}

	private int getDelay() {
		
		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();
		boolean active = state.getValue(BlockGeysir.ACTIVE);
		Random rand = world.rand;
		
		if(b == ModBlocks.geysir_water) {
			
			return (!active ? 30 : 100 + rand.nextInt(40));
			
		} else if(b == ModBlocks.geysir_chlorine) {
			
			return (!active ? 20 : 400 + rand.nextInt(100));
			
		} else if(b == ModBlocks.geysir_vapor) {
			
			return (!active ? 20 : 30 + rand.nextInt(20));
			
		} else if(b == ModBlocks.geysir_nether) {

			return (!active ? (rand.nextBoolean() ? 300 : 450) : 80 + rand.nextInt(60));

		}
		
		return 0;
	}
	
	private void perform() {
		Block b = world.getBlockState(pos).getBlock();
		
		if(b == ModBlocks.geysir_water) {
			
			water();
			
		} else if(b == ModBlocks.geysir_chlorine) {
			
			chlorine();
			
		} else if(b == ModBlocks.geysir_vapor) {
			
			vapor();
			
		}
	}
	
}
