package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.ModDamageSource;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.LoopedEntitySoundPacket;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMissileAntiBallistic extends EntityMissileBaseAdvanced {

    private static final int explosionRange = 25;
    private static final int dmg = 50;

    public Entity tracking;
    public static final int activationTimer = 40;
    public static final double baseSpeed = 1.5D;
    private static final int maxSpeed = 10;

	public EntityMissileAntiBallistic(World world) {
		super(world);
		this.setSize(1F, 8F);
	}

    @Override
    public void doMovement(){
        if(this.velocity < maxSpeed) this.velocity += 0.1;
        if (this.ticksExisted < activationTimer) {
            this.motionY = baseSpeed;
        } else {
            Entity prevTracking = this.tracking;
            if (this.tracking == null || this.tracking.isDead) this.targetMissile();
            if (world.isRemote && prevTracking == null && this.tracking != null) {
                ExplosionLarge.spawnShock(world, posX, posY, posZ, 24, 3F);
            }
            if (this.ticksExisted > 600 || this.posY > 5000) {
                this.setDead();
            } else if (this.tracking != null && !this.tracking.isDead) {
                this.aimAtTarget();
            }
        }
        this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI), (float)(Math.atan2(this.motionY, MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / Math.PI) - 90);

        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
        for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
        while(this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
        while(this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
        while(this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
    }

    private void targetMissile() {
    	//Targeting missiles
		List<Entity> listOfMissiles = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(posX - WeaponConfig.radarRange, 0, posZ - WeaponConfig.radarRange, posX + WeaponConfig.radarRange, 5000, posZ + WeaponConfig.radarRange));
		
		Entity target = null;
		double closest = Double.MAX_VALUE;
		
		for(Entity e : listOfMissiles) {
			if(!(e instanceof EntityMissileAntiBallistic) && (e instanceof EntityMissileBaseAdvanced || e instanceof EntityMissileCustom || e instanceof EntityMIRV)) {
				double dis = Math.pow(e.posX - posX, 2) + Math.pow(e.posY - posY, 2) + Math.pow(e.posZ - posZ, 2);
				
				if(dis < closest) {
					closest = dis;
					target = e;
				}
			}
		}
        this.tracking = target;
    }

    /** Predictive targeting system */
    protected void aimAtTarget() {

        Vec3 delta = Vec3.createVectorHelper(tracking.posX - posX, tracking.posY - posY, tracking.posZ - posZ);
        double intercept = delta.length() / (baseSpeed * this.velocity);
        Vec3 predicted = Vec3.createVectorHelper(tracking.posX + (tracking.posX - tracking.lastTickPosX) * intercept, tracking.posY + (tracking.posY - tracking.lastTickPosY) * intercept, tracking.posZ + (tracking.posZ - tracking.lastTickPosZ) * intercept);
        Vec3 motion = Vec3.createVectorHelper(predicted.xCoord - posX, predicted.yCoord - posY, predicted.zCoord - posZ).normalize();

        if(delta.length() < explosionRange && activationTimer < ticksExisted) {
            this.setDead();
            ExplosionLarge.explodeArea(world, posX, posY, posZ, explosionRange, dmg, true, false, false);
        }

        this.motionX = motion.xCoord * baseSpeed;
        this.motionY = motion.yCoord * baseSpeed;
        this.motionZ = motion.zCoord * baseSpeed;
    }

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.thruster_small, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.thruster_small);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.explode(world, posX, posY, posZ, 10.0F, true, true, true);
	}
}
