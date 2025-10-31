package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionChaos;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityGrenadePC extends EntityGrenadeBase {

	public EntityGrenadePC(World p_i1773_1_) {
		super(p_i1773_1_);
	}

	public EntityGrenadePC(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand) {
		super(p_i1774_1_, p_i1774_2_, hand);
	}

	public EntityGrenadePC(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void explode() {

		if (!this.world.isRemote) {
			this.setDead();

			//Drillgon200: 2002 is the code for the sound of glass breaking when a potion hits. See EntityPotion#onImpact
            this.world.playEvent(2002, new BlockPos((int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ)), 0);
			ExplosionChaos.spawnChlorine(world, posX, posY, posZ, 500, 2, 2);
		}
	}
}
