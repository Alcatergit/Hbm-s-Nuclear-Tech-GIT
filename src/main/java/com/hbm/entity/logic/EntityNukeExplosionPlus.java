package com.hbm.entity.logic;

import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionFleija;
import com.hbm.explosion.ExplosionNukeAdvanced;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityNukeExplosionPlus extends Entity {

	public int age = 0;
	public int destructionRange = 0;
	public int wasteRange = 0;
	public ExplosionNukeAdvanced exp;
	public ExplosionNukeAdvanced wst;
	public ExplosionNukeAdvanced vap;
	public ExplosionFleija expl;
	public int speed = 1;
	public float coefficient = 1;
	public float coefficient2 = 1;
	public boolean did = false;
	public boolean did2 = false;
	public boolean waste = true;
	
	public EntityNukeExplosionPlus(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
    	if(!CompatibilityConfig.isWarDim(world)){
			this.setDead();
			return;
		}
        if(!this.did)
        {
        	if(this.waste)
        	{
            	exp = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.world, this.destructionRange, this.coefficient, 0);
        		wst = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.world, (this.wasteRange), this.coefficient, 2);
        		vap = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.world, (int)(this.wasteRange * 1.5), this.coefficient, 1);
        	} else {
            	expl = new ExplosionFleija((int)this.posX, (int)this.posY, (int)this.posZ, this.world, this.destructionRange, this.coefficient, this.coefficient2);
        	}
        	
        	this.did = true;
        }
        
        speed = 160;
        
        boolean flag = false;
        boolean flag3 = false;
        
        for(int i = 0; i < this.speed; i++)
        {
        	if(waste) {
        		flag = exp.update();
        		flag3 = vap.update();
        		
        		if(flag3) {
        			this.setDead();
        		}
        	} else {
        		if(expl.update()) {
        			this.setDead();
        		}
        	}
        }
        	
        if(!flag)
        {
        	this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.HOSTILE, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	ContaminationUtil.radiate(this.world, this.posX, this.posY, this.posZ, this.destructionRange * 2, this.destructionRange * 10, 0, this.destructionRange * 2, this.destructionRange * 3);
        } else {
			if (!did2 && waste) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.world, (this.wasteRange) * 10);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((this.wasteRange), 16);

				this.world.spawnEntity(fallout);
				
				did2 = true;
        	}
        }
        
        age++;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

}
