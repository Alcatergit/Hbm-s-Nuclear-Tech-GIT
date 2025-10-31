package com.hbm.potion;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.capability.HbmLivingCapability;
import com.hbm.config.CompatibilityConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.leafia.dev.optimization.LeafiaParticlePacket;
import com.leafia.dev.optimization.LeafiaParticlePacket.Sweat;
import com.leafia.passive.LeafiaPassiveServer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HbmPotion extends Potion {
	
	public static HbmPotion taint;
	public static HbmPotion radiation;
	public static HbmPotion bang;
	public static HbmPotion mutation;
	public static HbmPotion radx;
	public static HbmPotion lead;
	public static HbmPotion radaway;
	public static HbmPotion telekinesis;
	public static HbmPotion phosphorus;
	public static HbmPotion stability;
	public static HbmPotion potionsickness;
	public static HbmPotion skindamage;
	public static HbmPotion frigid;
	
	public HbmPotion(boolean isBad, int color, String name, int x, int y){
		super(isBad, color);
		this.setPotionName(name);
		this.setRegistryName(RefStrings.MODID, name);
		this.setIconIndex(x, y);
	}

	/**
	 * Add Damaged Skin status effect to an entity. Starting from Damaged Skin I,
	 * multiple calls will increase its amplifier until it hits the specified maximum.
	 * @param entity The entity to be affected
	 * @param maxLevel A number ranging from 1 to 3
	 */
	public static void hurtSkin(EntityLivingBase entity,int maxLevel) {
		maxLevel -= 1;
		int level = 0;
		PotionEffect effect = entity.getActivePotionEffect(skindamage);
		if (effect != null) {
			if (effect.getAmplifier() > maxLevel) return;
			level = Math.min(effect.getAmplifier() + 1,maxLevel);
		}
		entity.addPotionEffect(new PotionEffect(skindamage,450*20,level,false,false));
	}

	/**
	 * Retrieves the amplifier of Damaged Skin status effect added by 1,
	 * or 0 if entity does not have the effect.
	 * @param entity Target entity
	 * @return A number ranging from 0 to 3
	 */
	public static int getSkinDamage(EntityLivingBase entity) {
		PotionEffect effect = entity.getActivePotionEffect(skindamage);
		if (effect != null)
			return effect.getAmplifier()+1;
		return 0;
	}

	public static void init() {
		taint = registerPotion(true, 8388736, "potion.hbm_taint", 0, 0);
		radiation = registerPotion(true, 8700200, "potion.hbm_radiation", 1, 0);
		bang = registerPotion(true, 1118481, "potion.hbm_bang", 3, 0);
		mutation = registerPotion(false, 0xFF8132, "potion.hbm_mutation", 2, 0);
		radx = registerPotion(false, 0x225900, "potion.hbm_radx", 5, 0);
		lead = registerPotion(true, 0x767682, "potion.hbm_lead", 6, 0);
		radaway = registerPotion(false, 0xFFE400, "potion.hbm_radaway", 7, 0);
		telekinesis = registerPotion(true, 0x00F3FF, "potion.hbm_telekinesis", 0, 1);
		phosphorus = registerPotion(true, 0xFF3A00, "potion.hbm_phosphorus", 1, 1);
		stability = registerPotion(false, 0xD0D0D0, "potion.hbm_stability", 2, 1);
		potionsickness = registerPotion(false, 0xFF8080, "potion.hbm_potionsickness", 3, 1);
		skindamage = registerPotion(true, 0xe11313, "potion.hbm_skindamage", 4, 1);
		frigid = registerPotion(true, /*0x65d3ff*/0xFFFFFF, "potion.hbm_frigid", 0, 2);
	}

	public static HbmPotion registerPotion(boolean isBad, int color, String name, int x, int y) {
		
		HbmPotion effect = new HbmPotion(isBad, color, name, x, y);
		ForgeRegistries.POTIONS.register(effect);
		
		return effect;
	}

	@Override
	public String getName() {
		if (this == frigid)
			return MobEffects.SLOWNESS.getName();
		return super.getName();
	}

	PotionEffect lastEffect = null;
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID, "textures/gui/potions.png");
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
		if (this == skindamage) {
			if (lastEffect != null) {
				if (lastEffect.getPotion() == this)
					return 8+5+Math.min(lastEffect.getAmplifier(),2);
			}
		}
		return super.getStatusIconIndex();
	}
	@Override
	public boolean shouldRender(PotionEffect effect) {
		lastEffect = effect;
		return super.shouldRender(effect);
	}
	@Override
	public boolean shouldRenderHUD(PotionEffect effect) {
		return this.shouldRender(effect);
	}
	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entity,AbstractAttributeMap attr,int amplifier) {
		super.removeAttributesModifiersFromEntity(entity,attr,amplifier);
		if (this == skindamage && !entity.world.isRemote && amplifier > 0)
			LeafiaPassiveServer.queueFunction(()->{
				if (entity.isEntityAlive())
					entity.addPotionEffect(new PotionEffect(skindamage,300*20,amplifier-1,false,false));
			});
	}

	public void performEffect(EntityLivingBase entity,int level) {

		if(this == taint) {
			if(!(entity instanceof EntityTaintedCreeper) && entity.world.rand.nextInt(80) == 0)
				entity.attackEntityFrom(ModDamageSource.taint, (level + 1));
			
			if(GeneralConfig.enableHardcoreTaint && !entity.world.isRemote && CompatibilityConfig.isWarDim(entity.world)) {
				
				int x = (int)(entity.posX - 1);
				int y = (int)entity.posY;
				int z = (int)(entity.posZ);
				BlockPos pos = new BlockPos(x, y, z);
				
				if(entity.world.getBlockState(pos).getBlock()
						.isReplaceable(entity.world, pos) && 
						BlockTaint.hasPosNeightbour(entity.world, pos)) {
					
					entity.world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, 14), 2);
				}
			} 
		}
		if(this == radiation) {
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, (float)(level + 1F) * 0.05F);
		}
		if(this == radaway) {
			if(entity.hasCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null))
				entity.getCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).decreaseRads((level+1)*0.05F);
		}
		if(this == bang) {
			if(CompatibilityConfig.isWarDim(entity.world)){
				entity.attackEntityFrom(ModDamageSource.bang, 10000*(level+1));

				if (!(entity instanceof EntityPlayer)){
					entity.onDeath(ModDamageSource.bang);
					entity.setHealth(0);
				}
			}
			entity.world.playSound(null, new BlockPos(entity), HBMSoundEvents.laserBang, SoundCategory.AMBIENT, 100.0F, 1.0F);
			ExplosionLarge.spawnParticles(entity.world, entity.posX, entity.posY, entity.posZ, 10);
		}
		if(this == lead) {
			
			entity.attackEntityFrom(ModDamageSource.lead, (level + 1));
		}
		if(this == telekinesis) {
			
			int remaining = entity.getActivePotionEffect(this).getDuration();
			
			if(remaining > 1) {
				entity.motionX = entity.motionX+(entity.getRNG().nextFloat()-0.5)*(level+1)*0.5;
				entity.motionY = entity.motionY+(entity.getRNG().nextFloat()-0.5)*(level+1)*0.5;
				entity.motionZ = entity.motionZ+(entity.getRNG().nextFloat()-0.5)*(level+1)*0.5;
			}
		}
		if(this == phosphorus && !entity.world.isRemote && CompatibilityConfig.isWarDim(entity.world)) {
			
			entity.setFire(level+1);
		}

		if(this == potionsickness && !entity.world.isRemote) {
			
			if(entity.world.rand.nextInt(128) == 0){
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 8*20, 0));
			}
		}

		if (this == skindamage && !entity.world.isRemote) {
			if (entity.getRNG().nextInt(50-level*20) == 0) {
				LeafiaParticlePacket.Sweat particle = new Sweat(entity,Blocks.REDSTONE_BLOCK.getDefaultState(),1);
				particle.emit(new Vec3d(entity.posX,entity.posY,entity.posZ),Vec3d.ZERO,entity.dimension);
			}
		}
	}

	public boolean isReady(int par1, int par2) {

		if(this == taint || this == potionsickness) {

	        return par1 % 2 == 0;
		}
		if(this == radiation || this == radaway || this == telekinesis || this == phosphorus) {
			
			return true;
		}
		if(this == bang) {

			return par1 <= 10;
		}
		if(this == lead) {

			int k = 60;
	        return k > 0 ? par1 % k == 0 : true;
		}
		if (this == skindamage)
			return true;

		return false;
	}
}
