package com.hbm.entity.logic;

import java.util.List;

import com.hbm.config.CompatibilityConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.Library;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBlast extends Entity {
	
	int life = RadiationConfig.fireDuration;
	int size;
	int damage = 100;
	int exCount;
	float exSize;
	boolean exFire;

	public EntityBlast(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!world.isRemote) {
			if(!CompatibilityConfig.isWarDim(world)){
				this.setDead();
				return;
			}
			fire();
			
			if(this.ticksExisted < exCount) {
				EntityTNTPrimed scapegoat = new EntityTNTPrimed(world);
		    	world.newExplosion(scapegoat, posX + rand.nextGaussian(), posY + rand.nextGaussian(), posZ + rand.nextGaussian(), exSize, false, true);
			}
			
			if(this.ticksExisted > life)
				this.setDead();
		}
	}
	
	private void fire() {
		
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(posX - size, posY - size, posZ - size, posX + size, posY + size, posZ + size));
		
		for(Entity e : list) {
			
			//if(e instanceof EntityPlayer || !(e instanceof EntityLivingBase)) {
				
				double dist = Math.sqrt(
						Math.pow(e.posX - posX, 2) +
						Math.pow(e.posY - posY, 2) +
						Math.pow(e.posZ - posZ, 2));
				
				if(dist <= size && canHurt(e)) {
					e.setFire(5);
					e.attackEntityFrom(ModDamageSource.blast, damage);
				}
			//}
		}
	}
	
	private boolean canHurt(Entity e) {
		return !Library.isObstructed(world, posX, posY, posZ, e.posX, e.posY + e.getEyeHeight(), e.posZ);
	}
	
	public static EntityBlast statFac(World world, double posX, double posY, double posZ, int size, int damage, int exCount, float exSize, boolean exFire) {
		
		EntityBlast blast = new EntityBlast(world);
		blast.posX = posX;
		blast.posY = posY;
		blast.posZ = posZ;
		blast.size = size;
		blast.damage = damage;
		blast.exCount = exCount;
		blast.exSize = exSize;
		blast.exFire = exFire;
		return blast;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("age");
		this.size = nbt.getInteger("size");
		this.damage = nbt.getInteger("damage");
		this.exCount = nbt.getInteger("exCount");
		this.exSize = nbt.getFloat("exSize");
		this.exFire = nbt.getBoolean("exFire");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", this.ticksExisted);
		nbt.setInteger("size", this.size);
		nbt.setInteger("damage", this.damage);
		nbt.setInteger("exCount", this.exCount);
		nbt.setFloat("exSize", this.exSize);
		nbt.setBoolean("exFire", this.exFire);
	}
}