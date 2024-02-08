package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityModBeam extends Entity implements IProjectile {

	public static final DataParameter<Boolean> CRITICAL = EntityDataManager.createKey(EntityModBeam.class, DataSerializers.BOOLEAN);
	
	private int field_145791_d = -1;
    private int field_145792_e = -1;
    private int field_145789_f = -1;
    public double gravity = 0.0D;
    private Block field_145790_g;
    private int inData;
    private boolean inGround;
    /** 1 if the player can pick up the arrow */
    public int canBePickedUp;
    /** Seems to be some sort of timer for animating an arrow. */
    public int arrowShake;
    /** The owner of this arrow. */
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0D;
    /** The amount of knockback an arrow applies when it hits a mob. */
    public int mode = 0;

    public EntityModBeam(World p_i1753_1_)
    {
        super(p_i1753_1_);
        this.setSize(0.5F, 0.5F);
    }

    public EntityModBeam(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_)
    {
        super(p_i1754_1_);
        this.setSize(0.5F, 0.5F);
        this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
    }

    public EntityModBeam(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_)
    {
        super(p_i1755_1_);
        this.shootingEntity = p_i1755_2_;

        if (p_i1755_2_ instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.posY = p_i1755_2_.posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
        double d0 = p_i1755_3_.posX - p_i1755_2_.posX;
        double d1 = p_i1755_3_.getEntityBoundingBox().minY + p_i1755_3_.height / 3.0F - this.posY;
        double d2 = p_i1755_3_.posZ - p_i1755_2_.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(p_i1755_2_.posX + d4, this.posY, p_i1755_2_.posZ + d5, f2, f3);
            float f4 = (float)d3 * 0.2F;
            this.shoot(d0, d1 + f4, d2, p_i1755_4_, p_i1755_5_);
        }
    }

    public EntityModBeam(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, EnumHand hand)
    {
        super(p_i1756_1_);
        this.shootingEntity = p_i1756_2_;

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        if(hand == EnumHand.MAIN_HAND){
        	this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
            this.posY -= 0.10000000149011612D;
            this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        } else {
        	this.posX += MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
            this.posY -= 0.10000000149011612D;
            this.posZ += MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        }
        
        this.setPosition(this.posX, this.posY, this.posZ);
        
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.shoot(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
    }

    public EntityModBeam(World world, int x, int y, int z, double mx, double my, double mz, double grav) {
        super(world);
    	this.posX = x + 0.5F;
    	this.posY = y + 0.5F;
    	this.posZ = z + 0.5F;
    	
    	this.motionX = mx;
    	this.motionY = my;
    	this.motionZ = mz;
    	
    	this.gravity = grav;
    }

    @Override
	protected void entityInit()
    {
        this.getDataManager().register(CRITICAL, false);
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    @Override
	public void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        float f2 = MathHelper.sqrt(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= f2;
        p_70186_3_ /= f2;
        p_70186_5_ /= f2;
        p_70186_1_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * p_70186_8_;
        p_70186_1_ *= p_70186_7_;
        p_70186_3_ *= p_70186_7_;
        p_70186_5_ *= p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    	this.setPosition(x, y, z);
    	this.setRotation(yaw, pitch);
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
    {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70016_3_, f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    //@Override
    @Override
	public void onUpdate()
    {
        super.onUpdate();
        if(world.isRemote)
        	return;
        
        if(this.ticksExisted > 100)
        	this.setDead();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            //this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        BlockPos pos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
        IBlockState blockstate = world.getBlockState(pos);

        if (blockstate.getMaterial() != Material.AIR)
        {
    		this.setDead();
    		explode();
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }
        else
        {
            ++this.ticksInAir;
            Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec31, vec3, false, true, false);
            vec31 = new Vec3d(this.posX, this.posY, this.posZ);
            vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vec3 = new Vec3d(movingobjectposition.hitVec.x, movingobjectposition.hitVec.y, movingobjectposition.hitVec.z);
            }

            Entity entity = null;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(this.motionX, this.motionY, this.motionZ).grow(1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expand(f1, f1, f1);
                    RayTraceResult movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new RayTraceResult(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;
                
                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;

            
            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null && movingobjectposition.entityHit != this.shootingEntity)
                {
                    f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int k = MathHelper.ceil(f2 * this.damage);

                    if (this.getIsCritical())
                    {
                        k += this.rand.nextInt(k / 2 + 2);
                    }
                    
                    if(movingobjectposition.entityHit instanceof EntityLivingBase) {
                    	((EntityLivingBase)movingobjectposition.entityHit).addPotionEffect(new PotionEffect(HbmPotion.bang, 60, 0));
                    } else {
                    	explode();
                    }
                    
                	this.setDead();
                }
                else if(movingobjectposition.typeOfHit == Type.BLOCK)
                {
                	IBlockState newState = world.getBlockState(movingobjectposition.getBlockPos());
                    this.field_145791_d = movingobjectposition.getBlockPos().getX();
                    this.field_145792_e = movingobjectposition.getBlockPos().getY();
                    this.field_145789_f = movingobjectposition.getBlockPos().getZ();
                    this.field_145790_g = newState.getBlock();
                    this.inData = newState.getBlock().getMetaFromState(newState);
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            f1 = 0.05F;

            if (this.isInWater())
            {
            	this.setDead();
            	explode();
            }

            if (this.isWet())
            {
                this.extinguish();
            }
            
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.setShort("xTile", (short)this.field_145791_d);
        p_70014_1_.setShort("yTile", (short)this.field_145792_e);
        p_70014_1_.setShort("zTile", (short)this.field_145789_f);
        p_70014_1_.setShort("life", (short)this.ticksInGround);
        p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
        p_70014_1_.setByte("inData", (byte)this.inData);
        p_70014_1_.setByte("shake", (byte)this.arrowShake);
        p_70014_1_.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        p_70014_1_.setByte("pickup", (byte)this.canBePickedUp);
        p_70014_1_.setDouble("damage", this.damage);
        p_70014_1_.setInteger("mode", this.mode);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        this.field_145791_d = p_70037_1_.getShort("xTile");
        this.field_145792_e = p_70037_1_.getShort("yTile");
        this.field_145789_f = p_70037_1_.getShort("zTile");
        this.ticksInGround = p_70037_1_.getShort("life");
        this.field_145790_g = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
        this.inData = p_70037_1_.getByte("inData") & 255;
        this.arrowShake = p_70037_1_.getByte("shake") & 255;
        this.inGround = p_70037_1_.getByte("inGround") == 1;
        this.mode = p_70037_1_.getInteger("mode");

        if (p_70037_1_.hasKey("damage", 99))
        {
            this.damage = p_70037_1_.getDouble("damage");
        }

        if (p_70037_1_.hasKey("pickup", 99))
        {
            this.canBePickedUp = p_70037_1_.getByte("pickup");
        }
        else if (p_70037_1_.hasKey("player", 99))
        {
            this.canBePickedUp = p_70037_1_.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
	protected boolean canTriggerWalking()
    {
        return false;
    }

    public void setDamage(double p_70239_1_)
    {
        this.damage = p_70239_1_;
    }

    public double getDamage()
    {
        return this.damage;
    }


    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    @Override
    public boolean canBeAttackedWithItem() {
    	return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setIsCritical(boolean crit)
    {
        this.getDataManager().set(CRITICAL, crit);
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        return this.getDataManager().get(CRITICAL);
    }
    
    private void explode() {
    	if(!world.isRemote) {
    		
    		if(mode == 0) {
    			ExplosionLarge.explode(world, posX, posY, posZ, 5, true, false, false);
    		} else if( mode == 1) {
    			ExplosionLarge.explodeFire(world, posX, posY, posZ, 10, true, false, false);
    		} else if(mode == 2) {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	
				EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.world);
				entity.posX = this.posX;
				entity.posY = this.posY;
				entity.posZ = this.posZ;
				entity.destructionRange = 10;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;
	
				this.world.spawnEntity(entity);
	    		
	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.world, 10);
	    		cloud.posX = this.posX;
	    		cloud.posY = this.posY;
	    		cloud.posZ = this.posZ;
	    		this.world.spawnEntity(cloud);
    		} else if(mode == 3) {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	
				EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.world);
				entity.posX = this.posX;
				entity.posY = this.posY;
				entity.posZ = this.posZ;
				entity.destructionRange = 20;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;
	
				this.world.spawnEntity(entity);
	    		
	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.world, 20);
	    		cloud.posX = this.posX;
	    		cloud.posY = this.posY;
	    		cloud.posZ = this.posZ;
	    		this.world.spawnEntity(cloud);
    		} else if(mode == 4) {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		EntityVortex vortex = new EntityVortex(this.world, 1F);
	    		vortex.posX = this.posX;
	    		vortex.posY = this.posY;
	    		vortex.posZ = this.posZ;
	    		this.world.spawnEntity(vortex);
    		} else if(mode == 5) {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		EntityVortex vortex = new EntityVortex(this.world, 2.5F);
	    		vortex.posX = this.posX;
	    		vortex.posY = this.posY;
	    		vortex.posZ = this.posZ;
	    		this.world.spawnEntity(vortex);
    		} else if(mode == 6) {
    			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		EntityRagingVortex vortex = new EntityRagingVortex(this.world, 2.5F);
	    		vortex.posX = this.posX;
	    		vortex.posY = this.posY;
	    		vortex.posZ = this.posZ;
	    		this.world.spawnEntity(vortex);
    		} else if(mode == 7) {
    			this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		EntityRagingVortex vortex = new EntityRagingVortex(this.world, 5F);
	    		vortex.posX = this.posX;
	    		vortex.posY = this.posY;
	    		vortex.posZ = this.posZ;
	    		this.world.spawnEntity(vortex);
    		} else if(mode == 8) {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		EntityBlackHole vortex = new EntityBlackHole(this.world, 2F);
	    		vortex.posX = this.posX;
	    		vortex.posY = this.posY;
	    		vortex.posZ = this.posZ;
	    		this.world.spawnEntity(vortex);
    		} else {
				this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 100.0f, this.world.rand.nextFloat() * 0.1F + 0.9F);
	    		
	    		this.world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.gadgetRadius, posX, posY, posZ));
                if(BombConfig.enableNukeClouds) {
                    EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.gadgetRadius);
                }
    		}
    	}
    }
}
