package com.hbm.items.special;

import com.hbm.config.GeneralConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.ArmorSets;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.Inserts;
import com.hbm.items.ModItems.Materials.*;
import com.hbm.items.ModItems.RetroRods;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import com.leafia.dev.MachineTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemCustomLore extends Item {

	EnumRarity rarity;
	
	public ItemCustomLore(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		String unloc = this.getTranslationKey() + ".desc";
		String loc = I18nUtil.resolveKey(unloc);

		if(!unloc.equals(loc)) {

			String[] locs = loc.split("\\$");

			for(String s : locs) {
				list.add(s);
			}
		}
		if(this == Powders.powder_asbestos)
		{
			list.add(TextFormatting.ITALIC + "\"Sniffffffff- MHHHHHHMHHHHHHHHH\"");
		}
		if(this == ModItems.bismuth_tool){
			list.add("§eRight-click a Dud while having empty cells in inventory gives you antimatter cells.");
			list.add("§8§oMight set off the Dud tho");
		}
		if(this == ModItems.iv_empty) {
			list.add(I18nUtil.resolveKey("desc.ivempty"));
		}
		if(this == ModItems.radaway) {
			list.add(I18nUtil.resolveKey("desc.radamount", -250));
			list.add(I18nUtil.resolveKey("desc.radspeed", -25));
			list.add(I18nUtil.resolveKey("desc.duration", 10));
		}
		if(this == ModItems.radaway_strong) {
			list.add(I18nUtil.resolveKey("desc.radamount", -500));
			list.add(I18nUtil.resolveKey("desc.radspeed", -100));
			list.add(I18nUtil.resolveKey("desc.duration", 5));
		}
		if(this == ModItems.radaway_flush) {
			list.add(I18nUtil.resolveKey("desc.radamount", -1000));
			list.add(I18nUtil.resolveKey("desc.radspeed", -400));
			list.add(I18nUtil.resolveKey("desc.duration", 2.5));
		}
		if(this == Ingots.ingot_schraranium)
		{
			if(GeneralConfig.enableBabyMode)
				list.add("Peer can go die, I'm not putting any retarded niko stuff in the mod.");
			else
				list.add("Made from uranium in a nuclear transmutator");
		}
		if(this == Ingots.ingot_fiberglass)
		{
			list.add("High in fiber, high in glass. Everything the body needs.");
		}
		if(this == ModItems.missile_soyuz_lander)
		{
			list.add("Doubles as a crappy lander!");
		}
		if(this == ModItems.book_of_)
		{
			list.add("Denn wer den Walzer richtig tritt,");
			list.add("der ist auch für den Abgang fit.");
		}
		if(this == ModItems.watch)
		{
			list.add("A small blue pocket watch.");
			list.add("It's glass has a few cracks in it,");
			list.add("and some shards are missing.");
			list.add("It stopped ticking at 2:34.");
		}
		if(this == ModItems.reacher)
		{
			list.add("Holding this in main hand or off hand reduces radiation coming from items to its square-root.");
			list.add("It also is useful to handle very hot or cold items.");
		}
		if(this == ModItems.crystal_horn)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("An actual horn.");
			else
				list.add("Not an actual horn.");
		}
		
		if(this == ModItems.crystal_charred)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Also a real horn. Weird, right?");
			else
				list.add("High quality silicate, slightly burned.");
		}
		if(this == Ingots.ingot_asbestos)
		{
			list.add(TextFormatting.ITALIC + "\"Filled with life, self-doubt and asbestos. That comes with the air.\"");
		}
		if(this == ModItems.entanglement_kit)
		{
			list.add("Teleporter crafting item.");
			list.add("Enables dimension-shifting via");
			list.add("beryllium-enhanced resource scanner.");
		}
		if(this == ModItems.ams_focus_limiter)
		{
			list.add("Maximum performance for restriction field:");
			list.add("Standard cooling, no energy bonus.");
		}
		
		if(this == ModItems.ams_focus_booster)
		{
			list.add("Weaker restriction field and core energy injection:");
			list.add("More heat generation, extra energy.");
		}
		
		if(this == ModItems.ams_muzzle)
		{
			list.add("...it emits an energy-beam thingy.");
		}
		if(this == Powders.powder_poison)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Warning: Poisonous!");
		}
		if(this == ModItems.pellet_cluster)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Adds some extra boom!");
		}

		if(this == Powders.powder_fire)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Incendiary bombs are fun!");
		}
		if(this == ModItems.pellet_gas)
		{
			list.add("Used in multi purpose bombs:");
			list.add("*cough cough* Halp pls!");
		}
		if(this == Powders.powder_tektite)
		{
			list.add("Collected via Geralds Miningfleet from §3outer space");
		}
		if(this == ModItems.igniter)
		{
			list.add("(Used by right-clicking the Prototype)");
			list.add("It's a green metal handle with a");
			list.add("bright red button and a small lid.");
			list.add("At the bottom, the initials N.E. are");
			list.add("engraved. Whoever N.E. was, he had");
			list.add("a great taste in shades of green.");
		}
		if(this == ModItems.overfuse)
		{
			list.add("Say what?");
		}
		if(this == ModItems.tritium_deuterium_cake)
		{
			list.add("Not actual cake, but great");
			list.add("universal fusion fuel!");
		}
		if(this == ModItems.pin) {
			list.add("Can be used with a screwdriver to pick locks.");
			if(Minecraft.getMinecraft().player != null) {
				EntityPlayer player = Minecraft.getMinecraft().player;
				if(ArmorUtil.checkArmorPiece(player, ArmorSets.jackt, 2) || ArmorUtil.checkArmorPiece(player, ArmorSets.jackt2, 2))
					list.add("Success rate of picking standard lock is 100%!");
				else
					list.add("Success rate of picking standard lock is ~10%");
			}
		}
		if(this == ModItems.key_red) {
			if(MainRegistry.polaroidID == 11) {
				list.add(TextFormatting.DARK_RED + "" + TextFormatting.BOLD + "e");
			} else {
				list.add("Explore the other side.");
			}
		}
		if(this == ModItems.crystal_energy) {
			list.add("Densely packed energy powder.");
			list.add("Not edible.");
		}
		if(this == ModItems.pellet_coolant) {
			list.add("Required for cyclotron operation.");
			list.add("Do NOT operate cyclotron without it!");
		}
		if(this == ModItems.fuse) {
			/*
			list.add("This item is needed for every large");
			list.add("nuclear reactor, as it allows the");
			list.add("reactor to generate electricity and");
			list.add("use up it's fuel. Removing the fuse");
			list.add("from a reactor will instantly shut");
			list.add("it down.");*/
		}
		if(this == Armory.gun_super_shotgun) {
			list.add("It's super broken!");
		}

		if(this == ModItems.burnt_bark) {
			list.add("A piece of bark from an exploded golden oak tree.");
		}

		if(this == ModItems.flame_pony) {
			// list.add("Blue horse beats yellow horse, look it up!");
			list.add(I18nUtil.resolveKey("desc.flamepony"));
		}
		
		if(this == ModItems.flame_conspiracy)
		{
			list.add(I18nUtil.resolveKey("desc.flameconspiracy"));
		}
		if(this == ModItems.flame_politics)
		{
			list.add(I18nUtil.resolveKey("desc.flamepolitics"));
		}
		if(this == ModItems.flame_opinion)
		{
			list.add(I18nUtil.resolveKey("desc.flameopinion"));
		}

		if(this == Ingots.ingot_neptunium) {
			if(MainRegistry.polaroidID == 11) {
				list.add("Woo, scary!");
			} else
				list.add("That one's my favourite!");
		}

		if(this == ModItems.pellet_rtg) {
			if(MainRegistry.polaroidID == 11)
				list.add("Contains ~100% Pu238 oxide.");
			else
				list.add("RTG fuel pellet for infinite energy! (almost)");
		}

		if(this == RetroRods.rod_lithium) {
			list.add("Turns into Tritium Rod");
		}

		if(this == RetroRods.rod_dual_lithium) {
			list.add("Turns into Dual Tritium Rod");
		}

		if(this == RetroRods.rod_quad_lithium) {
			list.add("Turns into Quad Tritium Rod");
		}
		if(this == Ingots.ingot_combine_steel) {
			/*list.add("\"I mean, it's a verb for crying out loud.");
			list.add("The aliens aren't verbs. They're nouns!\"");
			list.add("\"Actually, I think it's also the name");
			list.add("of some kind of farm equipment, like a");
			list.add("thresher or something.\"");
			list.add("\"That's even worse. Now we have a word");
			list.add("that could mean 'to mix things together',");
			list.add("a piece of farm equipment, and let's see...");
			list.add("oh yea, it can also mean 'the most advanced");
			list.add("form of life in the known universe'.\"");
			list.add("\"So?\"");
			list.add("\"'So?' C'mon man, they're ALIENS!\"");*/
			list.add("*insert Civil Protection reference here*");
		}
		if(this == Ingots.ingot_euphemium) {
			list.add("A very special and yet strange element.");
		}
		if(this == Powders.powder_euphemium) {
			list.add("Pulverized pink.");
			list.add("Tastes like strawberries.");
		}
		if(this == Nuggies.nugget_euphemium) {
			list.add("A small piece of a pink metal.");
			list.add("It's properties are still unknown,");
			list.add("DEAL WITH IT carefully.");
		}
		if(this == RetroRods.rod_quad_euphemium) {
			list.add("A quad fuel rod which contains a");
			list.add("very small ammount of a strange new element.");
		}
		if(this == ModItems.pellet_rtg_polonium)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Polonium 4 U and me.");
			else
				list.add("Tastes nice in Tea");
		}
		if(this == ModItems.mech_key)
		{
			list.add("It pulses with power.");
		}
		if(this == Nuggies.nugget_mox_fuel) {
			list.add("Moxie says: " + TextFormatting.BOLD + "TAX EVASION.");
		}
		if(this == Billets.billet_mox_fuel) {
			list.add(TextFormatting.ITALIC + "Pocket-Moxie!");
		}
		
		if(this == Ingots.ingot_lanthanium)
		{
			list.add("'Lanthanum'");
		}

		if(this == Ingots.ingot_gh336 || this == Billets.billet_gh336 || this == Nuggies.nugget_gh336)
		{
			list.add("Seaborgium's colleague");
		}

		if(this == Billets.billet_flashlead)
		{
			list.add("The lattice decays, causing antimatter-matter annihilation reactions, causing the release of pions, decaying into muons, catalyzing fusion of the nuclei, creating the new element. Please try to keep up.");
		}
		
		if(this == Ingots.ingot_tantalium || this == Nuggies.nugget_tantalium || this == ModItems.gem_tantalium || this == Powders.powder_tantalium)
		{
			list.add("'Tantalum'");
		}
		if(this == ModItems.euphemium_capacitor)
		{
			list.add("Permits passive dispersion of accumulated positive energy.");
		}

		if(this == ModItems.factory_core_titanium || this == ModItems.factory_core_advanced)
		{
			list.add("Used in factories to make the speed change");
		}
		if(this == ModItems.undefined && world != null) {
			
			if(world.rand.nextInt(10) == 0) {
				list.add(TextFormatting.DARK_RED + "UNDEFINED");
			} else {
				Random rand = new Random(System.currentTimeMillis() / 500);
				
				if(setSize == 0)
					setSize = Item.REGISTRY.getKeys().size();
				
				int r = rand.nextInt(setSize);
				
				Item item = Item.getItemById(r);
				
				if(item != null) {
					list.add(new ItemStack(item).getDisplayName());
				} else {
					list.add(TextFormatting.RED + "ERROR #" + r);
				}
			}
		}
	}
	
	static int setSize = 0;

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this == ModItems.plate_euphemium || 
			this == Ingots.ingot_euphemium ||
			this == Ingots.ingot_osmiridium ||
			this == Ingots.ingot_astatine ||
			this == Ingots.ingot_iodine ||
			this == Ingots.ingot_i131 ||
			this == Ingots.ingot_strontium ||
			this == Ingots.ingot_sr90 ||
			this == Ingots.ingot_cobalt ||
			this == Ingots.ingot_co60 ||
			this == Ingots.ingot_bromine ||
			this == Ingots.ingot_tennessine ||
			this == Ingots.ingot_cerium ||
			this == Ingots.ingot_caesium ||
			this == Ingots.ingot_niobium ||
			this == Ingots.ingot_neodymium ||
			this == Ingots.ingot_gh336 ||
			this == ModItems.euphemium_capacitor ||

			this == Nuggies.nugget_euphemium ||
			this == Nuggies.nugget_osmiridium ||
			this == Nuggies.nugget_strontium ||
			this == Nuggies.nugget_sr90 ||
			this == Nuggies.nugget_cobalt ||
			this == Nuggies.nugget_co60 ||
			this == Nuggies.nugget_gh336 ||

			this == Billets.billet_gh336 ||
			this == Billets.billet_co60 ||
			this == Billets.billet_sr90 ||
			
			this == Powders.powder_neptunium ||
			this == Powders.powder_euphemium ||
			this == Powders.powder_osmiridium ||
			this == Powders.powder_iodine ||
			this == Powders.powder_i131 ||
			this == Powders.powder_strontium ||
			this == Powders.powder_sr90 ||
			this == Powders.powder_astatine ||
			this == Powders.powder_at209 ||
			this == Powders.powder_cobalt ||
			this == Powders.powder_co60 ||
			this == Powders.powder_bromine ||
			this == Powders.powder_niobium ||
			this == Powders.powder_cerium ||
			this == Powders.powder_neodymium ||
			this == Powders.powder_tennessine ||
			this == Powders.powder_xe135 ||
			this == Powders.powder_caesium ||
			this == Powders.powder_cs137 ||
			this == Powders.powder_cs137 ||
			this == Powders.powder_nitan_mix ||
			this == Powders.powder_spark_mix ||
			this == Powders.powder_magic ||


			this == Powders.powder_sr90_tiny ||
			this == Powders.powder_iodine_tiny ||
			this == Powders.powder_i131_tiny ||
			this == Powders.powder_co60_tiny ||
			this == Powders.powder_cobalt_tiny ||
			this == Powders.powder_niobium_tiny ||
			this == Powders.powder_cerium_tiny ||
			this == Powders.powder_neodymium_tiny ||
			this == Powders.powder_xe135_tiny ||
			this == Powders.powder_cs137_tiny ||
			this == Nuggies.nugget_daffergon ||
			this == Powders.powder_daffergon ||
			this == Ingots.ingot_daffergon ||
			
			this == Inserts.bathwater_mk3 ||
			this == ModItems.plate_euphemium ||  
			this == RetroRods.rod_euphemium ||
			this == RetroRods.rod_quad_euphemium ||
			this == RetroRods.rod_daffergon ||
			this == ModItems.watch || 
			this == ModItems.undefined) {
			return EnumRarity.EPIC;
		}

		if(this == Ingots.ingot_schrabidium ||
			this == Ingots.ingot_schraranium ||
			this == Ingots.ingot_schrabidate ||
			this == Ingots.ingot_saturnite ||
			this == Ingots.ingot_solinium ||
			this == Nuggies.nugget_schrabidium ||
			this == Nuggies.nugget_solinium ||
			this == Ingots.ingot_electronium ||
			this == Billets.billet_solinium ||
			this == Billets.billet_schrabidium ||
			
			this == Powders.powder_schrabidate ||
			this == Powders.powder_schrabidium ||

			this == ModItems.wire_schrabidium || 

			this == ModItems.plate_schrabidium || 
			this == ModItems.plate_saturnite || 
			
			this == ModItems.circuit_schrabidium || 
			this == Armory.gun_revolver_schrabidium_ammo ||
			this == Powders.powder_unobtainium ||
			this == Nuggies.nugget_unobtainium ||
			this == Ingots.ingot_unobtainium ||
			this == Nuggies.nugget_unobtainium_greater ||
			this == Nuggies.nugget_unobtainium_lesser ||
			this == Billets.billet_unobtainium ||
			
			this == ModItems.solinium_core ||
			this == Powders.powder_impure_osmiridium ||
			this == Crystals.crystal_osmiridium ||
			this == Crystals.crystal_schrabidium ||
    		this == Crystals.crystal_schraranium ||
    		this == Crystals.crystal_trixite ||
    		ItemCell.hasFluid(stack, ModForgeFluids.SAS3) ||
    		this == RetroRods.rod_unobtainium ||
    		this == RetroRods.rod_schrabidium ||
			this == RetroRods.rod_dual_schrabidium ||
			this == RetroRods.rod_quad_schrabidium ||
			this == RetroRods.rod_dual_solinium ||
			this == RetroRods.rod_quad_solinium) {
			return EnumRarity.RARE;
		}

		if(this == Inserts.bathwater_mk2 ||
			this == ModItems.plate_paa || 
			this == Inserts.cladding_paa ||
			this == Armory.ammo_566_gold ||
			this == Armory.gun_revolver_cursed_ammo ||
			this == Powders.powder_power ||
			this == Powders.powder_yellowcake ||
			this == Billets.billet_australium ||
			this == Billets.billet_australium_greater ||
			this == Billets.billet_australium_lesser ||

			this == Ingots.ingot_australium ||
			this == Ingots.ingot_weidanium ||
			this == Ingots.ingot_reiium ||
			this == Ingots.ingot_verticium ||
			this == Powders.powder_paleogenite ||
			this == Powders.powder_paleogenite_tiny ||

			this == Nuggies.nugget_australium ||
			this == Nuggies.nugget_australium_greater ||
			this == Nuggies.nugget_australium_lesser ||
			this == Nuggies.nugget_weidanium ||
			this == Nuggies.nugget_reiium ||
			this == Nuggies.nugget_verticium ||

			this == Powders.powder_australium ||
			this == Powders.powder_weidanium ||
			this == Powders.powder_reiium ||
			this == Powders.powder_verticium) {
			return EnumRarity.UNCOMMON;
		}

		return this.rarity != null ? rarity : EnumRarity.COMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if(this == ModItems.rune_isa ||
    			this == ModItems.rune_dagaz ||
    			this == ModItems.rune_hagalaz ||
    			this == ModItems.rune_jera ||
    			this == ModItems.rune_thurisaz ||
    			this == ModItems.egg_balefire_shard ||
    			this == ModItems.egg_balefire ||
    			this == ModItems.coin_maskman || 
    			this == ModItems.coin_radiation || 
    			this == ModItems.coin_worm || 
    			this == ModItems.coin_ufo || 
    			this == ModItems.coin_creeper) 
		{
    		return true;
    	}
		return super.hasEffect(stack);
	}
	
	public ItemCustomLore setRarity(EnumRarity rarity) {
    	this.rarity = rarity;
		return this;
    }

}
