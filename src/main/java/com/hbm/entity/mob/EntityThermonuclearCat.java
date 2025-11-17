package com.hbm.entity.mob;

import java.util.List;
import java.util.Random;

import com.hbm.config.CompatibilityConfig;
import com.hbm.items.ModItems;
import com.hbm.interfaces.IRadiationImmune;
import com.hbm.util.ContaminationUtil;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EntityThermonuclearCat extends EntityOcelot implements IRadiationImmune {

    public static final int catCount = 16;
    public static final int effectRadius = 16;
    public EntityAITempt aiTemptThermo;

	public EntityThermonuclearCat(World worldIn) {
		super(worldIn);
        this.isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
	}

    public @NotNull Item getTemptItem(){
        return ModItems.billet_nuclear_waste;
    }

    @Override
    protected void initEntityAI() {
        this.aiSit = new EntityAISit(this);
        this.aiTemptThermo = new EntityAITempt(this, 0.6D, getTemptItem(), true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTemptThermo);
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 20.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8D));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(10, new EntityAIWanderAvoidWater(this, 0.8D, 1.0000001E-5F));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMob.class, 10, false, false, null));
    }

    @Override
    public EntityThermonuclearCat createChild(EntityAgeable ageable) {
        EntityThermonuclearCat entitycat = new EntityThermonuclearCat(this.world);
        if (this.isTamed()) {
            entitycat.setOwnerId(this.getOwnerId());
            entitycat.setTamed(true);
            entitycat.setTameSkin(this.getTameSkin(), true);
        }
        return entitycat;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if(otherAnimal == this) {
            return false;
        } else if(!this.isTamed()) {
            return false;
        } else if(!(otherAnimal instanceof EntityThermonuclearCat entityocelot)) {
            return false;
        } else {
            if (!entityocelot.isTamed()) {
                return false;
            } else {
                return this.isInLove() && entityocelot.isInLove();
            }
        }
    }

    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        } else {
            return this.isTamed() ? I18n.translateToLocal("entity.entity_thermo_cat_tamed.name") : super.getName();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), getCatDmg(isChild(), isLegendary()));
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!this.world.isRemote && this.isOwner(player) && !this.isBreedingItem(itemstack)) {
                this.aiSit.setSitting(!this.isSitting());
            }
        } else if ((this.aiTemptThermo == null || this.aiTemptThermo.isRunning()) && itemstack.getItem() == getTemptItem() && player.getDistanceSq(this) < 9.0D) {
            if (!player.capabilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.setTameSkin(rollTameSkin(world.rand));
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }
        if(itemstack.getItem() == Items.FISH) return false;

        return super.processInteract(player, hand);
    }

    public static int rollTameSkin(Random rand){
        float chance = rand.nextFloat();
        if(chance > 0.2) return 1 + rand.nextInt(10);//  80%
        return 11 + rand.nextInt(5);
    }

    @Override
    public void setTameSkin(int skinId) {
        setTameSkin(skinId, isChild());
    }

    public void setTameSkin(int skinId, boolean isChild) {
        super.setTameSkin(skinId);
        int catHP = this.getCatHP(isChild, skinId > 10);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(catHP);
        this.setHealth(catHP);
        if(skinId > 10){
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
        }
    }

    public float getCatRad(boolean isChild, boolean isLegendary){
        return (isChild ? 2 : 20) * (isLegendary ? 2 : 1);
    }

    public int getCatHP(boolean isChild, boolean isLegendary){
        return (isChild ? 10 : 100) * (isLegendary ? 2 : 1);
    }

    public float getCatDmg(boolean isChild, boolean isLegendary){
        return (isChild ? 4 : 10) * (isLegendary ? 2 : 1);
    }

    public boolean isLegendary(){
        return this.getTameSkin() > 10;
    }

	@Override
    public void onLivingUpdate() {
        if(CompatibilityConfig.isWarDim(world)) ContaminationUtil.radiate(world, posX, posY, posZ, effectRadius, getCatRad(isChild(), isLegendary()));
        super.onLivingUpdate();
    }

    public static void convertInRadiusToThermo(World world, double x, double y, double z, double radius){
    	List<EntityOcelot> entities = world.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(x, y, z, x, y, z).grow(radius, radius, radius));
		
		for(EntityOcelot e : entities) {
			if(e instanceof EntityThermonuclearCat)
    			continue;
			Vec3 vec = Vec3.createVectorHelper(e.posX - x, (e.posY + e.getEyeHeight()) - y, e.posZ - z);
			double len = vec.length();
			if(len < radius){
                convertToThermo(world, e);
			}
		}
    }

    public static void convertToThermo(World world, EntityOcelot ocel){
    	if(ocel instanceof EntityThermonuclearCat || ocel.isDead)
    		return;
        EntityThermonuclearCat cat = new EntityThermonuclearCat(world);
        cat.setLocationAndAngles(ocel.posX, ocel.posY, ocel.posZ, ocel.rotationYaw, ocel.rotationPitch);
        cat.setCustomNameTag(ocel.getCustomNameTag());
        cat.setGrowingAge(ocel.getGrowingAge());
        cat.setScaleForAge(ocel.isChild());
        cat.setTamed(ocel.isTamed());
        if(cat.isTamed()) {
            cat.setTameSkin(rollTameSkin(world.rand));
        } else {
            cat.setTameSkin(0);
        }
        cat.setOwnerId(ocel.getOwnerId());
        cat.setSitting(ocel.isSitting());
        cat.aiSit.setSitting(ocel.isSitting());
        if(!world.isRemote)
            world.spawnEntity(cat);
        ocel.setDead();
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == ModItems.powder_balefire;
    }

    @Override
    protected boolean canDespawn() {
    	return !this.isTamed() && this.ticksExisted > 24000;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player){
    	return 200;
    }

	@Override
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
		super.dropLoot(wasRecentlyHit, lootingModifier, source);
		this.dropItem(ModItems.nuclear_waste, Math.max(1, lootingModifier));
	}
}
