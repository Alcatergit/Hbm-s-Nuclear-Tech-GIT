package com.hbm.main;

import org.apache.logging.log4j.Level;

import com.hbm.lib.RefStrings;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class AdvancementManager {

	public static Advancement root;
	
	public static Advancement achCircuit0;
	public static Advancement achCircuit1;
	public static Advancement achCircuit2;
	public static Advancement achCircuit3;
	public static Advancement achCircuit4;
	public static Advancement achCircuit5;
	public static Advancement achJack;
	public static Advancement achDalekanium;
	public static Advancement achRefinery;
	public static Advancement achBattery;
	public static Advancement achOil;
	public static Advancement achCatapult1;
	public static Advancement achCatapult2;
	public static Advancement achCatapult3;
	public static Advancement achU235;
	public static Advancement achPu238;
	public static Advancement achPu239;
	public static Advancement achNeptunium;
	public static Advancement achDesh;
	public static Advancement achMeteor;
	public static Advancement achGeiger;
	public static Advancement achDesignator;
	public static Advancement achRemote;
	public static Advancement achOverpowered;
	public static Advancement achShimSham;
	public static Advancement achMatchstick;
	public static Advancement achRails;
	public static Advancement achFolder;
	public static Advancement achPress;
	public static Advancement achFWatz;
	public static Advancement achTurbofan;
	public static Advancement achGadget;
	public static Advancement achBoy;
	public static Advancement achMan;
	public static Advancement achMike;
	public static Advancement achTsar;
	public static Advancement achFLEIJA;
	public static Advancement achPrototype;
	public static Advancement achCustom;
	public static Advancement achTurret;
	public static Advancement achMeteorDeath;
	public static Advancement achXenium;
	public static Advancement achRadiation;
	public static Advancement achSchrabidium;
	public static Advancement achEuphemium;
	public static Advancement horizonsStart;
	public static Advancement horizonsEnd;
	public static Advancement horizonsBonus;
	public static Advancement soyuz;
	public static Advancement achRadPoison;
	public static Advancement achRadDeath;
	
	public static Advancement achSacrifice;
	public static Advancement achImpossible;
	public static Advancement achTOB;
	public static Advancement achFreytag;
	public static Advancement achSelenium;
	public static Advancement achPotato;
	public static Advancement achC44;
	public static Advancement achC20_5;
	public static Advancement achSpace;
	public static Advancement achFOEQ;
	public static Advancement achFiend;
	public static Advancement achFiend2;
	public static Advancement bobMetalworks;
	public static Advancement bobAssembly;
	public static Advancement bobChemistry;
	public static Advancement bobOil;
	public static Advancement bobNuclear;
	public static Advancement bobHidden;
	public static Advancement achStratum;
	public static Advancement achMeltdown;
	public static Advancement achOmega12;
	
	public static Advancement bossCreeper;
	public static Advancement bossMeltdown;
	public static Advancement bossMaskman;
	public static Advancement bossWorm;

	public static void init(MinecraftServer serv){
		net.minecraft.advancements.AdvancementManager adv = serv.getAdvancementManager();
		
		root = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "root"));
		achSpace = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "space"));
		bobMetalworks = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bobmetalworks"));
		bobAssembly = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bobassembly"));
		bobChemistry = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bobchemistry"));
		bobOil = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "boboil"));
		bobNuclear = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bobnuclear"));
		bobHidden = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bobhidden"));
		achSacrifice = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achsacrifice"));
		achFOEQ = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achfoeq"));
		achFiend = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achfiend"));
		achFiend2 = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achfiend2"));
		horizonsStart = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "horizonsstart"));
		horizonsEnd = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "horizonsend"));
		horizonsBonus = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "horizonsbonus"));
		soyuz = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "soyuz"));
		achRadPoison = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achradpoison"));
		achRadDeath = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achraddeath"));
		achStratum = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achstratum"));
		achMeltdown = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achmeltdown"));
		achOmega12 = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "achomega12"));
		
		bossCreeper = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bosscreeper"));
		bossMeltdown = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bossmeltdown"));
		bossMaskman = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bossmaskman"));
		bossWorm = adv.getAdvancement(new ResourceLocation(RefStrings.MODID, "bossworm"));
	}
	
	public static void grantAchievement(EntityPlayerMP player, Advancement a){
		if(a == null){
			MainRegistry.logger.log(Level.ERROR, "Failed to grant null advancement! This should never happen.");
			return;
		}
		for(String s : player.getAdvancements().getProgress(a).getRemaningCriteria()){
			player.getAdvancements().grantCriterion(a, s);
		}
	}
	
	public static void grantAchievement(EntityPlayer player, Advancement a){
		if(player instanceof EntityPlayerMP)
			grantAchievement((EntityPlayerMP)player, a);
	}
	
	public static boolean hasAdvancement(EntityPlayer player, Advancement a){
		if(a == null){
			MainRegistry.logger.log(Level.ERROR, "Failed to test null advancement! This should never happen.");
			return false;
		}
		if(player instanceof EntityPlayerMP){
			return ((EntityPlayerMP)player).getAdvancements().getProgress(a).isDone();
		}
		return false;
	}
	
}
