package com.hbm.entity.logic;



import com.hbm.config.CompatibilityConfig;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.util.ContaminationUtil;
import com.hbm.explosion.ExplosionBalefire;
import com.hbm.main.MainRegistry;

import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class EntityBalefire extends EntityChunky {

	public int age = 0;
	public int destructionRange = 0;
	public ExplosionBalefire exp;
	public int speed = 1;
	public boolean did = false;
	public boolean mute = false;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
		speed = nbt.getInteger("speed");
		did = nbt.getBoolean("did");
		mute = nbt.getBoolean("mute");
    	
		exp = new ExplosionBalefire((int)this.posX, (int)this.posY, (int)this.posZ, this.world, this.destructionRange);
		exp.readFromNbt(nbt, "exp_");
    	
    	this.did = true;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", age);
		nbt.setInteger("destructionRange", destructionRange);
		nbt.setInteger("speed", speed);
		nbt.setBoolean("did", did);
		nbt.setBoolean("mute", mute);
		
		if(exp != null) exp.saveToNbt(nbt, "exp_");
	}

	public EntityBalefire(World world) {
		super(world);
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
    		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
    			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized BF explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");
    		
        	exp = new ExplosionBalefire((int)this.posX, (int)this.posY, (int)this.posZ, this.world, this.destructionRange);
        	
        	this.did = true;
        }
        
        speed += 1;	//increase speed to keep up with expansion
        
        boolean flag = false;
        
        for(int i = 0; i < this.speed; i++)
        {
        	flag = exp.update();
        	
        	if(flag) {
        		this.setDead();
        	}
        }
        
        if(!flag)
        {
        	if(this.destructionRange > 15){
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, this.destructionRange * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}else{
				if(rand.nextInt(5) == 0)
					this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, this.destructionRange * 0.05F, 0.8F + this.rand.nextFloat() * 0.2F);
			}
        	ContaminationUtil.radiate(this.world, this.posX, this.posY, this.posZ, this.destructionRange*2D, this.destructionRange*2000F, 0F, this.destructionRange*100F, this.destructionRange*500F);
        }
        
        age++;
    }
	
	public EntityBalefire mute() {
		this.mute = true;
		return this;
	}
}
