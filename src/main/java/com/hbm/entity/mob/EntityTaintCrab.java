package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityTaintCrab extends EntityCyberCrab {

	public List<double[]> targets = new ArrayList<double[]>();
	
	public EntityTaintCrab(World worldIn) {
		super(worldIn);
		this.setSize(1.25F, 1.25F);
        this.ignoreFrustumCheck = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5F);
	}
	
	@Override
	protected EntityAIAttackRanged arrowAttack() {
		return new EntityAIAttackRanged(this, 0.5D, 5, 5, 50.0F);
	}
	
	@Override
	public void onLivingUpdate() {
		targets = TileEntityTesla.zap(world, posX, posY + 1.25, posZ, 10, this);

		List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));
		
		for(EntityLivingBase e : targets) {
			if(!(e instanceof EntityCyberCrab))
				e.addPotionEffect(new PotionEffect(HbmPotion.taint, 30));
		}
    	
        super.onLivingUpdate();
	}
	
	@Override
	protected Item getDropItem() {
		return ModItems.coil_advanced_alloy;
	}
	
	@Override
	protected void dropRareDrop(int p_70600_1_) {
		this.dropItem(ModItems.coil_magnetized_tungsten, 1);
	}
	
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		EntityBulletBase bullet = new EntityBulletBase(world, BulletConfigSyncingUtil.BMG50_STAR, this);
		//Vec3 motion = Vec3.createVectorHelper(posX - entity.posX, posY - entity.posZ - entity.height / 2, posZ - entity.posZ);
		//motion = motion.normalize();
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "vanilla");
		data.setString("mode", "flame");
		data.setDouble("mX", bullet.motionX * 0.3);
		data.setDouble("mY", bullet.motionY * 0.3);
		data.setDouble("mZ", bullet.motionZ * 0.3);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, bullet.posX, bullet.posY, bullet.posZ), new TargetPoint(this.dimension, posX, posY, posZ, 50));
        this.world.spawnEntity(bullet);
        this.playSound(HBMSoundHandler.sawShoot, 1.0F, 0.5F);
	}
	
}
