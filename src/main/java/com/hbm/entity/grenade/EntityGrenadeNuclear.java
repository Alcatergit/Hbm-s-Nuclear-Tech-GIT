package com.hbm.entity.grenade;

import java.util.Random;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityGrenadeNuclear extends EntityGrenadeBouncyBase {
    private static Random rand = new Random();

    public EntityGrenadeNuclear(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeNuclear(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand)
    {
        super(p_i1774_1_, p_i1774_2_, hand);
    }

    public EntityGrenadeNuclear(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.world.isRemote)
        {
            this.setDead();

            world.spawnEntity(EntityNukeExplosionMK4.statFac(world, (int)BombConfig.fatmanRadius/2, posX + 0.5, posY + 0.5, posZ + 0.5));
            if(rand.nextInt(100) == 0){
                ExplosionParticleB.spawnMush(world, posX + 0.5, posY - 3, posZ + 0.5);
            } else {
                ExplosionParticle.spawnMush(world, posX + 0.5, posY - 3, posZ + 0.5);
            }
        }
    }

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_nuclear);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
