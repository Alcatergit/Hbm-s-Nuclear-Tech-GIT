package com.hbm.entity.grenade;

import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityGrenadeIFImpact extends EntityGrenadeBase {

    public EntityGrenadeIFImpact(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityGrenadeIFImpact(World p_i1774_1_, EntityLivingBase p_i1774_2_, EnumHand hand)
    {
        super(p_i1774_1_, p_i1774_2_, hand);
    }

    public EntityGrenadeIFImpact(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
    public void explode() {
    	
        if (!this.world.isRemote)
        {
            this.setDead();
    		
    		ExplosionLarge.jolt(world, posX, posY, posZ, 5, 200, 0.25);
    		ExplosionLarge.explode(world, posX, posY, posZ, 5, true, true, true);
        }
    }
}
