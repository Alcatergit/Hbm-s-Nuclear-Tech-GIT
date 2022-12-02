package com.hbm.entity.grenade;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityGrenadeASchrab extends EntityGrenadeBase {

	public EntityGrenadeASchrab(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadeASchrab(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand) {
		super(p_i1774_1_, p_i1774_2_, hand);
	}

	public EntityGrenadeASchrab(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.world.isRemote) {
			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);

			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.world);
			entity.posX = this.posX;
			entity.posY = this.posY;
			entity.posZ = this.posZ;
			if(!EntityNukeExplosionMK3.isJammed(this.world, entity)){
				entity.destructionRange = BombConfig.aSchrabRadius;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;

				this.world.spawnEntity(entity);

				EntityCloudFleija cloud = new EntityCloudFleija(this.world, BombConfig.aSchrabRadius);
				cloud.posX = this.posX;
				cloud.posY = this.posY;
				cloud.posZ = this.posZ;
				this.world.spawnEntity(cloud);
			}
				
			this.setDead();
		}
	}
}
