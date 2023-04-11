package com.hbm.util;

import com.hbm.capability.HbmLivingCapability.EntityHbmProps;
import com.hbm.capability.HbmLivingCapability;
import com.hbm.capability.HbmLivingProps;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.handler.ArmorUtil;
import com.hbm.handler.HazmatRegistry;
import com.hbm.interfaces.IRadiationImmune;
import com.hbm.interfaces.IItemHazard;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ContaminationUtil {

	/**
	 * Calculates how much radiation can be applied to this entity by calculating resistance
	 * @param entity
	 * @return
	 */
	public static float calculateRadiationMod(EntityLivingBase entity) {

		if(entity.isPotionActive(HbmPotion.mutation))
			return 0;
		float mult = 1;
		if(entity.getEntityData().hasKey("hbmradmultiplier", 99))
			mult = entity.getEntityData().getFloat("hbmradmultiplier");

		float koeff = 10.0F;
		return (float) Math.pow(koeff, -(getConfigEntityRadResistance(entity) + HazmatRegistry.getResistance(entity))) * mult;
	}

	private static void applyRadData(Entity e, float f) {

		if(e instanceof IRadiationImmune)
			return;
		
		if(!(e instanceof EntityLivingBase))
			return;

		if(e instanceof EntityPlayer && (((EntityPlayer) e).capabilities.isCreativeMode || ((EntityPlayer) e).isSpectator()))
			return;
		
		if(e instanceof EntityPlayer && e.ticksExisted < 200)
			return;
		
		EntityLivingBase entity = (EntityLivingBase)e;

		f *= calculateRadiationMod(entity);

		if(entity.hasCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null)) {
			HbmLivingCapability.IEntityHbmProps ent = entity.getCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null);
			ent.increaseRads(f);
		}
	}

	private static void applyRadDirect(Entity entity, float f) {

		if(entity instanceof IRadiationImmune)
			return;
		
		if(entity.getEntityData().hasKey("hbmradmultiplier", 99))
			f *= entity.getEntityData().getFloat("hbmradmultiplier");
		
		if(entity instanceof EntityPlayer && (((EntityPlayer) entity).capabilities.isCreativeMode || ((EntityPlayer) entity).isSpectator()))
			return;
		
		if(!(entity instanceof EntityLivingBase))
			return;

		if(((EntityLivingBase) entity).isPotionActive(HbmPotion.mutation))
			return;

		if(entity.hasCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null)) {
			HbmLivingCapability.IEntityHbmProps ent = entity.getCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static void printGeigerData(EntityPlayer player) {

		double eRad = ((long)(HbmLivingProps.getRadiation(player) * 1000)) / 1000D;

		RadiationSavedData data = RadiationSavedData.getData(player.world);
		double rads = ((long)(data.getRadNumFromCoord(player.getPosition()) * 1000D)) / 1000D;
		double env = ((long)(getPlayerRads(player) * 1000D)) / 1000D;

		double res = ((long)(100000000D - ContaminationUtil.calculateRadiationMod(player) * 100000000D)) / 1000000D;
		double resKoeff = ((long)(HazmatRegistry.getResistance(player) * 100D)) / 100D;

		double rec = ((long)(env* (100-res)/100D * 1000D))/ 1000D;

		String chunkPrefix = getPreffixFromRad(rads);
		String envPrefix = getPreffixFromRad(env);
		String recPrefix = getPreffixFromRad(rec);
		String radPrefix = "";
		String resPrefix = "" + TextFormatting.WHITE;

		if(eRad < 200)
			radPrefix += TextFormatting.GREEN;
		else if(eRad < 400)
			radPrefix += TextFormatting.YELLOW;
		else if(eRad < 600)
			radPrefix += TextFormatting.GOLD;
		else if(eRad < 800)
			radPrefix += TextFormatting.RED;
		else if(eRad < 1000)
			radPrefix += TextFormatting.DARK_RED;
		else
			radPrefix += TextFormatting.DARK_GRAY;

		if(resKoeff > 0)
			resPrefix += TextFormatting.GREEN;

		//localization and server-side restrictions have turned this into a painful mess
		//a *functioning* painful mess, nonetheless
		player.sendMessage(new TextComponentString("===== ☢ ").appendSibling(new TextComponentTranslation("geiger.title")).appendSibling(new TextComponentString(" ☢ =====")).setStyle(new Style().setColor(TextFormatting.GOLD)));
		player.sendMessage(new TextComponentTranslation("geiger.chunkRad").appendSibling(new TextComponentString(" " + chunkPrefix + rads + " RAD/s")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.envRad").appendSibling(new TextComponentString(" " + envPrefix + env + " RAD/s")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.recievedRad").appendSibling(new TextComponentString(" " + recPrefix + rec + " RAD/s")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.playerRad").appendSibling(new TextComponentString(" " + radPrefix + eRad + " RAD")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
		player.sendMessage(new TextComponentTranslation("geiger.playerRes").appendSibling(new TextComponentString(" " + resPrefix + res + "% (" + resKoeff + ")")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}

	public static void printDosimeterData(EntityPlayer player) {

		double rads = ContaminationUtil.getActualPlayerRads(player);
		boolean limit = false;
		
		if(rads > 3.6D) {
			rads = 3.6D;
			limit = true;
		}
		rads = ((int)(1000D * rads))/ 1000D;
		String radsPrefix = getPreffixFromRad(rads);
		
		player.sendMessage(new TextComponentString("===== ☢ ").appendSibling(new TextComponentTranslation("dosimeter.title")).appendSibling(new TextComponentString(" ☢ =====")).setStyle(new Style().setColor(TextFormatting.GOLD)));
		player.sendMessage(new TextComponentTranslation("geiger.recievedRad").appendSibling(new TextComponentString(" " + radsPrefix + (limit ? ">" : "") + rads + " RAD/s")).setStyle(new Style().setColor(TextFormatting.YELLOW)));
	}

	public static String getTextColorFromPercent(double percent){
		if(percent < 0.5)
			return ""+TextFormatting.GREEN;
		else if(percent < 0.6)
			return ""+TextFormatting.YELLOW;
		else if(percent < 0.7)
			return ""+TextFormatting.GOLD;
		else if(percent < 0.8)
			return ""+TextFormatting.RED;
		else if(percent < 0.9)
			return ""+TextFormatting.DARK_RED;
		else
			return ""+TextFormatting.DARK_GRAY;
	}

	public static String getTextColorLung(double percent){
		if(percent > 0.9)
			return ""+TextFormatting.GREEN;
		else if(percent > 0.75)
			return ""+TextFormatting.YELLOW;
		else if(percent > 0.5)
			return ""+TextFormatting.GOLD;
		else if(percent > 0.25)
			return ""+TextFormatting.RED;
		else if(percent > 0.1)
			return ""+TextFormatting.DARK_RED;
		else
			return ""+TextFormatting.DARK_GRAY;
	}

	public static void printDiagnosticData(EntityPlayer player) {

		double digamma = ((int)(HbmLivingProps.getDigamma(player) * 1000)) / 1000D;
		double halflife = ((int)((1D - Math.pow(0.5, digamma)) * 10000)) / 100D;
		
		player.sendMessage(new TextComponentString("===== Ϝ ").appendSibling(new TextComponentTranslation("digamma.title")).appendSibling(new TextComponentString(" Ϝ =====")).setStyle(new Style().setColor(TextFormatting.DARK_PURPLE)));
		player.sendMessage(new TextComponentTranslation("digamma.playerDigamma").appendSibling(new TextComponentString(TextFormatting.RED + " " + digamma + " DRX")).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
		player.sendMessage(new TextComponentTranslation("digamma.playerHealth").appendSibling(new TextComponentString(getTextColorFromPercent(halflife/100D) + String.format(" %6.2f", halflife) + "%")).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
	}

	public static void printLungDiagnosticData(EntityPlayer player) {

		float playerAsbestos = 100F-((int)(10000F * HbmLivingProps.getAsbestos(player) / EntityHbmProps.maxAsbestos))/100F;
		float playerBlacklung = 100F-((int)(10000F * HbmLivingProps.getBlackLung(player) / EntityHbmProps.maxBlacklung))/100F;
		float playerTotal = (playerAsbestos * playerBlacklung/100F);
		

		player.sendMessage(new TextComponentString("===== L ").appendSibling(new TextComponentTranslation("lung_scanner.title")).appendSibling(new TextComponentString(" L =====")).setStyle(new Style().setColor(TextFormatting.WHITE)));
		player.sendMessage(new TextComponentTranslation("lung_scanner.player_asbestos_health").setStyle(new Style().setColor(TextFormatting.WHITE)).appendSibling(new TextComponentString(String.format(getTextColorLung(playerAsbestos/100D)+" %6.2f", playerAsbestos)+" %")));
		player.sendMessage(new TextComponentTranslation("lung_scanner.player_coal_health").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)).appendSibling(new TextComponentString(String.format(getTextColorLung(playerBlacklung/100D)+" %6.2f", playerBlacklung)+" %")));
		player.sendMessage(new TextComponentTranslation("lung_scanner.player_total_health").setStyle(new Style().setColor(TextFormatting.GRAY)).appendSibling(new TextComponentString(String.format(getTextColorLung(playerTotal/100D)+" %6.2f", playerTotal)+" %")));
	
	}

	public static double getActualPlayerRads(EntityLivingBase entity) {
		return getPlayerRads(entity) * (double)(ContaminationUtil.calculateRadiationMod(entity));
	}

	public static double getPlayerRads(EntityLivingBase entity) {
		float rads = HbmLivingProps.getRadBuf(entity);
		if(entity instanceof EntityPlayer)
			 rads = rads + HbmLivingProps.getNeutron((EntityPlayer)entity)*20F;
		return (double)rads;
	}

	public static double getNoNeutronPlayerRads(EntityLivingBase entity) {
		return (double)(HbmLivingProps.getRadBuf(entity)) * (double)(ContaminationUtil.calculateRadiationMod(entity));
	}

	public static float getPlayerNeutronRads(EntityPlayer player){
		float radBuffer = 0F;
		for(ItemStack slotI : player.inventory.mainInventory){
			radBuffer = radBuffer + getNeutronRads(slotI);
		}
		for(ItemStack slotA : player.inventory.armorInventory){
			radBuffer = radBuffer + getNeutronRads(slotA);
		}
		return radBuffer;
	}

	public static boolean isRadItem(ItemStack stack){
		if(stack == null)
			return false;

		if(stack.getItem() instanceof IItemHazard && ((IItemHazard)stack.getItem()).isRadioactive()){
			return true;
		}
		return false;
	}

	public static float getNeutronRads(ItemStack stack){
		if(stack != null && !isRadItem(stack)){
			if(stack.hasTagCompound()){
				NBTTagCompound nbt = stack.getTagCompound();
				if(nbt.hasKey("ntmNeutron")){
					return nbt.getFloat("ntmNeutron") * stack.getCount();
				}
			}
		}
		return 0F;
	}

	public static void neutronActivateInventory(EntityPlayer player, float rad, float decay){
		ItemStack mainHandItem = player.getHeldItemMainhand().copy();
		for(ItemStack slotI : player.inventory.mainInventory){
			neutronActivateItem(slotI, rad, decay);
		}
		for(ItemStack slotA : player.inventory.armorInventory){
			neutronActivateItem(slotA, rad, decay);
		}
		player.setHeldItem(EnumHand.MAIN_HAND , mainHandItem);
	}

	public static void neutronActivateItem(ItemStack stack, float rad, float decay){
		if(stack != null && !isRadItem(stack)){
			NBTTagCompound nbt;
			if(stack.hasTagCompound()){
				nbt = stack.getTagCompound();
			} else{
				nbt = new NBTTagCompound();
			}
			float prevActivation = 0;
			if(nbt.hasKey("ntmNeutron")){
				prevActivation = nbt.getFloat("ntmNeutron");
			}

			float newActivation = prevActivation * decay + (rad / stack.getCount());
			if(prevActivation * decay + rad < 0.0001F){
				nbt.removeTag("ntmNeutron");
			} else {
				nbt.setFloat("ntmNeutron", newActivation);
			}
			if(nbt.hasNoTags()){
				stack.setTagCompound(null);
			} else {
				stack.setTagCompound(nbt);
			}
		}
	}
	
	public static String getPreffixFromRad(double rads) {

		String chunkPrefix = "";
		
		if(rads == 0)
			chunkPrefix += TextFormatting.GREEN;
		else if(rads < 1)
			chunkPrefix += TextFormatting.YELLOW;
		else if(rads < 10)
			chunkPrefix += TextFormatting.GOLD;
		else if(rads < 100)
			chunkPrefix += TextFormatting.RED;
		else if(rads < 1000)
			chunkPrefix += TextFormatting.DARK_RED;
		else
			chunkPrefix += TextFormatting.DARK_GRAY;
		
		return chunkPrefix;
	}
	
	public static float getRads(Entity e) {
		if(e instanceof IRadiationImmune)
			return 0.0F;
		if(e instanceof EntityLivingBase)
			HbmLivingProps.getRadiation((EntityLivingBase)e);
		return 0.0F;
	}

	public static float getConfigEntityRadResistance(Entity e){
		float totalResistanceValue = 0.0F;
		if(!(e instanceof EntityPlayer)){
			ResourceLocation entity_path = EntityList.getKey(e);
			Object resistanceMod = CompatibilityConfig.mobModRadresistance.get(entity_path.getResourceDomain());
			Object resistanceMob = CompatibilityConfig.mobRadresistance.get(entity_path.toString());
			if(resistanceMod != null){
				totalResistanceValue = totalResistanceValue + (float)resistanceMod;
			}
			if(resistanceMob != null){
				totalResistanceValue = totalResistanceValue + (float)resistanceMob;
			}	
		}
		return totalResistanceValue;
	}

	public static boolean checkConfigEntityImmunity(Entity e){
		if(!(e instanceof EntityPlayer)){
			ResourceLocation entity_path = EntityList.getKey(e);
			if(entity_path != null){
				if(CompatibilityConfig.mobModRadimmune.contains(entity_path.getResourceDomain())){
					return true;
				}else{
					return CompatibilityConfig.mobRadimmune.contains(entity_path.toString());
				}
			}
		}
		return false;
	}
	
	public static boolean isRadImmune(Entity e) {
		if(e instanceof EntityLivingBase && ((EntityLivingBase)e).isPotionActive(HbmPotion.mutation))
			return true;
		
		return e instanceof EntityNuclearCreeper ||
				e instanceof EntityMooshroom ||
				e instanceof EntityZombie ||
				e instanceof EntitySkeleton ||
				e instanceof EntityQuackos ||
				e instanceof EntityOcelot ||
				e instanceof IRadiationImmune || checkConfigEntityImmunity(e);
	}
	
	/// ASBESTOS ///
		public static void applyAsbestos(Entity e, int i) {

			if(!(e instanceof EntityLivingBase))
				return;
			
			if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
				return;
			
			if(e instanceof EntityPlayer && e.ticksExisted < 200)
				return;
			
			EntityLivingBase entity = (EntityLivingBase)e;
			
			if(ArmorRegistry.hasAllProtection(entity, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_FINE))
				ArmorUtil.damageGasMaskFilter(entity, i);
			else
				HbmLivingProps.incrementAsbestos(entity, i);
		}
		
		/// DIGAMMA ///
		public static void applyDigammaData(Entity e, float f) {

			if(!(e instanceof EntityLivingBase))
				return;

			if(e instanceof EntityQuackos || e instanceof EntityOcelot)
				return;
			
			if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
				return;
			
			if(e instanceof EntityPlayer && e.ticksExisted < 200)
				return;
			
			EntityLivingBase entity = (EntityLivingBase)e;
			
			if(entity.isPotionActive(HbmPotion.stability))
				return;
			
			if(!(entity instanceof EntityPlayer && ArmorUtil.checkForDigamma((EntityPlayer) entity)))
				HbmLivingProps.incrementDigamma(entity, f);
		}
		
		public static void applyDigammaDirect(Entity e, float f) {

			if(!(e instanceof EntityLivingBase))
				return;

			if(e instanceof IRadiationImmune)
				return;
			
			if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
				return;
			
			EntityLivingBase entity = (EntityLivingBase)e;
			HbmLivingProps.incrementDigamma(entity, f);
		}
		
		public static float getDigamma(Entity e) {

			if(!(e instanceof EntityLivingBase))
				return 0.0F;
			
			EntityLivingBase entity = (EntityLivingBase)e;
			return HbmLivingProps.getDigamma(entity);
		}
	
	public static enum HazardType {
		MONOXIDE,
		RADIATION,
		NEUTRON,
		ASBESTOS,
		DIGAMMA
	}
	
	public static enum ContaminationType {
		GAS,				//filterable by gas mask
		GAS_NON_REACTIVE,	//not filterable by gas mask
		GOGGLES,			//preventable by goggles
		FARADAY,			//preventable by metal armor
		HAZMAT,				//preventable by hazmat
		HAZMAT2,			//preventable by heavy hazmat
		DIGAMMA,			//preventable by fau armor or stability
		DIGAMMA2,			//preventable by robes
		CREATIVE,			//preventable by creative mode, for rad calculation armor piece bonuses still apply
		RAD_BYPASS,			//same as creaative but fill not apply radiation resistance calculation
		NONE				//not preventable
	}
	
	/*
	 * This system is nice but the cont types are a bit confusing. Cont types should have much better names and multiple cont types should be applicable.
	 */
	@SuppressWarnings("incomplete-switch") //just shut up
	public static boolean contaminate(EntityLivingBase entity, HazardType hazard, ContaminationType cont, float amount) {
		
		if(hazard == HazardType.RADIATION) {
			float radEnv = HbmLivingProps.getRadEnv(entity);
			HbmLivingProps.setRadEnv(entity, radEnv + amount);
		}
		
		if(entity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer)entity;
			
			switch(cont) {
			case GOGGLES:			if(ArmorUtil.checkForGoggles(player))	return false; break;
			case FARADAY:			if(ArmorUtil.checkForFaraday(player))	return false; break;
			case HAZMAT:			if(ArmorUtil.checkForHazmat(player))	return false; break;
			case HAZMAT2:			if(ArmorUtil.checkForHaz2(player))		return false; break;
			case DIGAMMA:			if(ArmorUtil.checkForDigamma(player))	return false; break;
			case DIGAMMA2: break;
			}
			
			if(player.capabilities.isCreativeMode && cont != ContaminationType.NONE){
				if(hazard == HazardType.NEUTRON)
					HbmLivingProps.setNeutron(entity, amount);
				return false;
			}
			
			if(player.ticksExisted < 200)
				return false;
		}
		
		if((hazard == HazardType.RADIATION || hazard == HazardType.NEUTRON) && isRadImmune(entity)){
			return false;
		}
		
		switch(hazard) {
		case MONOXIDE: entity.attackEntityFrom(ModDamageSource.monoxide, amount); break;
		case RADIATION: HbmLivingProps.incrementRadiation(entity, amount * (cont == ContaminationType.RAD_BYPASS ? 1 : calculateRadiationMod(entity))); break;
		case NEUTRON: HbmLivingProps.incrementRadiation(entity, amount * (cont == ContaminationType.RAD_BYPASS ? 1 : calculateRadiationMod(entity))); HbmLivingProps.setNeutron(entity, amount); break;
		case ASBESTOS: HbmLivingProps.incrementAsbestos(entity, (int)amount); break;
		case DIGAMMA: HbmLivingProps.incrementDigamma(entity, amount); break;
		}
		
		return true;
	}
}