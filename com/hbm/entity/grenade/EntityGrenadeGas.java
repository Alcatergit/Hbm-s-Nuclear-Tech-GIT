package com.hbm.entity.grenade;

import java.util.Random;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityGrenadeGas extends EntityGrenadeBouncyBase {
	Random rand = new Random();

	public EntityGrenadeGas(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeGas(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand) {
		super(p_i1774_1_, p_i1774_2_, hand);
	}

	public EntityGrenadeGas(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.world.isRemote) {
			this.setDead();
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, 0.0F, true);
			// ExplosionChaos.poison(this.worldObj, (int)this.posX,
			// (int)this.posY, (int)this.posZ, 5);
			// for(int i = 0; 0 < 15; i++) {

			/*
			 * ExplosionLarge.spawnParticlesRadial(worldObj, posX, posY, posZ,
			 * 50); ExplosionLarge.spawnParticlesRadial(worldObj, posX, posY,
			 * posZ, 50); ExplosionLarge.spawnParticlesRadial(worldObj, posX,
			 * posY, posZ, 50); ExplosionLarge.spawnParticlesRadial(worldObj,
			 * posX, posY, posZ, 50);
			 */

			ExplosionChaos.spawnChlorine(world, posX, posY, posZ, 50, 1.25, 0);

			// }
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_gas);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}

}
