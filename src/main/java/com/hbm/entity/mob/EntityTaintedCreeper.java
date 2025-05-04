package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.ai.EntityAITaintedCreeperSwell;
import com.hbm.interfaces.IRadiationImmune;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityTaintedCreeper extends EntityMob implements IRadiationImmune {
	
	private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(EntityTaintedCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityTaintedCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.<Boolean>createKey(EntityTaintedCreeper.class, DataSerializers.BOOLEAN);
	 /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;
    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;
    private int fuseTime = 30;
    /** Explosion radius for this creeper. */
    private int explosionRadius = 20;
	public EntityTaintedCreeper(World world) {
		super(world);
			this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(2, new EntityAITaintedCreeperSwell(this));
	        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
	        this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
	        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
	        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityOcelot>(this, EntityOcelot.class, true));
	}
	
	 @Override
		protected void applyEntityAttributes()
	    {
	        super.applyEntityAttributes();
	        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
	        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	    }
	 
	 @Override
	public boolean isAIDisabled() {
		return false;
	}

	 /**
	     * The maximum height from where the entity is allowed to jump (used in pathfinder)
	     */
	    public int getMaxFallHeight()
	    {
	        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
	    }

	    public void fall(float distance, float damageMultiplier)
	    {
	        super.fall(distance, damageMultiplier);
	        this.timeSinceIgnited = (int)((float)this.timeSinceIgnited + distance * 1.5F);

	        if (this.timeSinceIgnited > this.fuseTime - 5)
	        {
	            this.timeSinceIgnited = this.fuseTime - 5;
	        }
	    }
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATE, -1);
		this.dataManager.register(POWERED, Boolean.FALSE);
        this.dataManager.register(IGNITED, Boolean.FALSE);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if ((Boolean) this.dataManager.get(POWERED))
        {
            compound.setBoolean("powered", true);
        }

        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.setBoolean("ignited", this.hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
	@Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, compound.getBoolean("powered"));

        if (compound.hasKey("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.hasKey("ExplosionRadius", 99))
        {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited"))
        {
            this.ignite();
        }
    }
	
	/**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F * 30 / 75, 0.5F);
                
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.onUpdate();
        
        if(this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0)
        {
        	this.heal(1.0F);
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */   
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return SoundEvents.ENTITY_CREEPER_HURT;
    }

    /**
     * Returns the sound this mob makes on death.
     */
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
	public void onDeath(DamageSource p_70645_1_)
    {
        super.onDeath(p_70645_1_);
    }

    @Override
	public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return this.dataManager.get(POWERED);
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }

    @Override
	protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.TNT);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return this.dataManager.get(STATE);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int i)
    {
        this.dataManager.set(STATE, i);
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    @Override
	public void onStruckByLightning(EntityLightningBolt p_70077_1_)
    {
        super.onStruckByLightning(p_70077_1_);
        this.dataManager.set(POWERED, Boolean.TRUE);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (itemstack != null && itemstack.getItem() == Items.FLINT_AND_STEEL)
        {
        	this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);

            if (!this.world.isRemote)
            {
                this.ignite();
                itemstack.damageItem(1, player);
                return true;
            }
        }

        return super.processInteract(player, hand);
    }
   

    private void explode()
    {
        if (!this.world.isRemote)
        {

            if (this.getPowered())
            {
            	this.explosionRadius *= 3;
            }
            
            world.newExplosion(this, posX, posY, posZ, 5.0F, false, false);
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            if(this.getPowered())
            {
	            
			    for(int i = 0; i < 255; i++) {
			    	int a = rand.nextInt(15) + (int)posX - 7;
			    	int b = rand.nextInt(15) + (int)posY - 7;
			    	int c = rand.nextInt(15) + (int)posZ - 7;
			    	pos.setPos(a, b, c);
			           if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockTaint.hasPosNeightbour(world, pos)) {
			        	   
			        	   if(GeneralConfig.enableHardcoreTaint)
			        		   world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, rand.nextInt(3) + 5), 2);
			        	   else
			        		   world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, rand.nextInt(3)), 2);
			           }
			    }
			    
            } else {
	            
			    for(int i = 0; i < 85; i++) {
			    	int a = rand.nextInt(7) + (int)posX - 3;
			    	int b = rand.nextInt(7) + (int)posY - 3;
			    	int c = rand.nextInt(7) + (int)posZ - 3;
			    	pos.setPos(a, b, c);
			           if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockTaint.hasPosNeightbour(world, pos)) {
			        	   
			        	   if(GeneralConfig.enableHardcoreTaint)
			        		   world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, rand.nextInt(6) + 10), 2);
			        	   else
			        		   world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, rand.nextInt(3) + 4), 2);
			           }
			    }
            }

            this.setDead();
        }
    }
    
    public void ignite() {
        this.dataManager.set(IGNITED, Boolean.TRUE);
    }
    public boolean hasIgnited(){
    	return this.dataManager.get(IGNITED);
    }
}
