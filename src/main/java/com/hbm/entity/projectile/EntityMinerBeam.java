package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.config.CompatibilityConfig;
import com.hbm.lib.ModDamageSource;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMinerBeam extends Entity implements IProjectile {

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
    private int knockbackStrength;
	
	public EntityMinerBeam(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 0.5F);
	}
	
	public EntityMinerBeam(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_)
    {
        super(p_i1756_1_);
        this.shootingEntity = p_i1756_2_;

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.shoot(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
    }

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		float f2 = MathHelper.sqrt(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * inaccuracy;
        y += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * inaccuracy;
        z += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.002499999832361937D * inaccuracy;
        x *= velocity;
        y *= velocity;
        z *= velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f3 = MathHelper.sqrt(x * x + z * z);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
	}
	
	@Override
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
        this.motionY = y;
        this.motionZ = z;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(x * x + z * z);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
        
        if(this.ticksExisted > 100)
        	this.setDead();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            //this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        IBlockState blockstate = world.getBlockState(new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f));

        if (blockstate.getMaterial() != Material.AIR)
        {
        	if(!world.isRemote && CompatibilityConfig.isWarDim(world)) {
        		this.dropMinedItem(this.world, field_145791_d, field_145792_e, field_145789_f);
        	}
    		this.setDead();
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
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D));
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
            float f4;

            if (movingobjectposition != null && CompatibilityConfig.isWarDim(world))
            {
                if (movingobjectposition.entityHit != null)
                {
                    f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int k = MathHelper.ceil(f2 * this.damage);

                    DamageSource damagesource = null;

                    if (this.shootingEntity == null)
                    {
                        damagesource = DamageSource.GENERIC;
                    }
                    else
                    {
                    	damagesource = ModDamageSource.causeLaserDamage(this, this.shootingEntity);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, k))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                            if (this.knockbackStrength > 0)
                            {
                                f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f4 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f4);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, entitylivingbase);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                            }
                        }

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            if (!this.world.isRemote && movingobjectposition.entityHit instanceof EntityLivingBase)
                            {
                            	movingobjectposition.entityHit.attackEntityFrom(damagesource, 25 + rand.nextInt(20));
                            	movingobjectposition.entityHit.setFire(5);
                            	if(!world.isRemote) {
                            		this.dropMinedItem(this.world, field_145791_d, field_145792_e, field_145789_f);
                            	}
                        		this.setDead();
                            }
                        }
                    }
                }
                else
                {
                	this.field_145791_d = movingobjectposition.getBlockPos().getX();
					this.field_145792_e = movingobjectposition.getBlockPos().getY();
					this.field_145789_f = movingobjectposition.getBlockPos().getZ();
					BlockPos pos = new BlockPos(this.field_145791_d, this.field_145792_e, this.field_145789_f);
					IBlockState test_blockstate = this.world.getBlockState(pos);
					this.field_145790_g = test_blockstate.getBlock();
					this.inData = field_145790_g.getMetaFromState(test_blockstate);
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
            }

            if (this.isWet())
            {
                this.extinguish();
            }
            
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
	}
	
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public double getDamage() {
		return damage;
	}
	
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.field_145791_d = compound.getShort("xTile");
        this.field_145792_e = compound.getShort("yTile");
        this.field_145789_f = compound.getShort("zTile");
        this.ticksInGround = compound.getShort("life");
        this.field_145790_g = Block.getBlockById(compound.getByte("inTile") & 255);
        this.inData = compound.getByte("inData") & 255;
        this.arrowShake = compound.getByte("shake") & 255;
        this.inGround = compound.getByte("inGround") == 1;

        if (compound.hasKey("damage", 99))
        {
            this.damage = compound.getDouble("damage");
        }

        if (compound.hasKey("pickup", 99))
        {
            this.canBePickedUp = compound.getByte("pickup");
        }
        else if (compound.hasKey("player", 99))
        {
            this.canBePickedUp = compound.getBoolean("player") ? 1 : 0;
        }
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("xTile", (short)this.field_145791_d);
		compound.setShort("yTile", (short)this.field_145792_e);
		compound.setShort("zTile", (short)this.field_145789_f);
		compound.setShort("life", (short)this.ticksInGround);
		compound.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
		compound.setByte("inData", (byte)this.inData);
		compound.setByte("shake", (byte)this.arrowShake);
		compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
		compound.setByte("pickup", (byte)this.canBePickedUp);
		compound.setDouble("damage", this.damage);
	}
	
	public void dropMinedItem(World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState b = world.getBlockState(pos);
		ItemStack s = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(Item.getItemFromBlock(b.getBlock()), 1, b.getBlock().getMetaFromState(b)));
		if(!s.isEmpty()) {
			ItemStack t = s.copy();
			if(!world.isRemote)
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
			
            float f = rand.nextFloat() * 0.8F + 0.1F;
            float f1 = rand.nextFloat() * 0.8F + 0.1F;
            float f2 = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, t);

            float f3 = 0.05F;
            entityitem.motionX = (float)rand.nextGaussian() * f3;
            entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float)rand.nextGaussian() * f3;
            
            if(!world.isRemote) {
            	world.spawnEntity(entityitem);
            }
		}
    }

}
