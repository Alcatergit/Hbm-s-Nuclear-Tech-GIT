package com.hbm.items.special;

import java.util.List;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

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

public class ItemCustomLore extends Item {

	public ItemCustomLore(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {

		if(this == ModItems.powder_poison)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Warning: Poisonous!");
		}
		if(this == ModItems.pellet_cluster)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Adds some extra boom!");
		}

		if(this == ModItems.powder_fire)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Incendiary bombs are fun!");
		}
		if(this == ModItems.pellet_gas)
		{
			list.add("Used in multi purpose bombs:");
			list.add("*cough cough* Halp pls!");
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
				if(Library.checkArmorPiece(player, ModItems.jackt, 2) || Library.checkArmorPiece(player, ModItems.jackt2, 2))
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
			list.add("This item is needed for every large");
			list.add("nuclear reactor, as it allows the");
			list.add("reactor to generate electricity and");
			list.add("use up it's fuel. Removing the fuse");
			list.add("from a reactor will instantly shut");
			list.add("it down.");
		}
		if(this == ModItems.screwdriver) {
			list.add("Could be used instead of a fuse...");
		}
		if(this == ModItems.gun_super_shotgun) {
			list.add("It's super broken!");
		}

		if(this == ModItems.burnt_bark) {
			list.add("A piece of bark from an exploded golden oak tree.");
		}

		if(this == ModItems.flame_pony) {
			// list.add("Blue horse beats yellow horse, look it up!");
			list.add("Yellow horse beats blue horse, that's a proven fact!");
		}

		if(this == ModItems.ingot_neptunium) {
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

		if(this == ModItems.pellet_rtg_weak) {
			if(MainRegistry.polaroidID == 11)
				list.add("Meh.");
			else
				list.add("Cheaper and weaker pellet, now with more U238!");
		}

		if(this == ModItems.rod_uranium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Plutonium Rod");
		}

		if(this == ModItems.rod_u235) {
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Neptunium Rod");
		}

		if(this == ModItems.rod_u238) {
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}

		if(this == ModItems.rod_neptunium) {
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 238 Rod");
		}

		if(this == ModItems.rod_plutonium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}

		if(this == ModItems.rod_pu238) {
			list.add("Worth 5 operations in breeding reactor");
			list.add("Worth 25 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}

		if(this == ModItems.rod_pu239) {
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 240 Rod");
		}

		if(this == ModItems.rod_pu240) {
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}

		if(this == ModItems.rod_schrabidium) {
			list.add("Worth 15 operations in breeding reactor");
			list.add("Worth 75 operations in nuclear powered furnace");
			list.add("Turns into Solinium Rod");
		}

		if(this == ModItems.rod_solinium) {
			list.add("Worth 20 operations in breeding reactor");
			list.add("Worth 100 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_dual_uranium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium Rod");
		}

		if(this == ModItems.rod_dual_u235) {
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Neptunium Rod");
		}

		if(this == ModItems.rod_dual_u238) {
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}

		if(this == ModItems.rod_dual_neptunium) {
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 238 Rod");
		}

		if(this == ModItems.rod_dual_plutonium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}

		if(this == ModItems.rod_dual_pu238) {
			list.add("Worth 10 operations in breeding reactor");
			list.add("Worth 50 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}

		if(this == ModItems.rod_dual_pu239) {
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 240 Rod");
		}

		if(this == ModItems.rod_dual_pu240) {
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}

		if(this == ModItems.rod_dual_schrabidium) {
			list.add("Worth 30 operations in breeding reactor");
			list.add("Worth 150 operations in nuclear powered furnace");
			list.add("Turns into Dual Solinium Rod");
		}

		if(this == ModItems.rod_dual_solinium) {
			list.add("Worth 40 operations in breeding reactor");
			list.add("Worth 200 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_quad_uranium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium Rod");
		}

		if(this == ModItems.rod_quad_u235) {
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Neptunium Rod");
		}

		if(this == ModItems.rod_quad_u238) {
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}

		if(this == ModItems.rod_quad_neptunium) {
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 238 Rod");
		}

		if(this == ModItems.rod_quad_plutonium) {
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}

		if(this == ModItems.rod_quad_pu238) {
			list.add("Worth 20 operations in breeding reactor");
			list.add("Worth 100 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}

		if(this == ModItems.rod_quad_pu239) {
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 240 Rod");
		}

		if(this == ModItems.rod_quad_pu240) {
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}

		if(this == ModItems.rod_quad_schrabidium) {
			list.add("Worth 60 operations in breeding reactor");
			list.add("Worth 300 operations in nuclear powered furnace");
			list.add("Turns into Quad Solinium Rod");
		}

		if(this == ModItems.rod_quad_solinium) {
			list.add("Worth 80 operations in breeding reactor");
			list.add("Worth 400 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_lithium) {
			list.add("Turns into Tritium Rod");
		}

		if(this == ModItems.rod_dual_lithium) {
			list.add("Turns into Dual Tritium Rod");
		}

		if(this == ModItems.rod_quad_lithium) {
			list.add("Turns into Quad Tritium Rod");
		}
		if(this == ModItems.upgrade_speed_1) {
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -15 / Consumption +300");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -25 / Consumption +300");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -25 / Consumption +300");
		}

		if(this == ModItems.upgrade_speed_2) {
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -30 / Consumption +600");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -50 / Consumption +600");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -50 / Consumption +600");
		}

		if(this == ModItems.upgrade_speed_3) {
			list.add("Speed Upgrade");
			list.add("Mining Drill:");
			list.add("Delay -45 / Consumption +900");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Delay -75 / Consumption +900");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Delay -75 / Consumption +900");
		}

		if(this == ModItems.upgrade_effect_1) {
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +1 / Consumption +80");
		}

		if(this == ModItems.upgrade_effect_2) {
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +2 / Consumption +160");
		}

		if(this == ModItems.upgrade_effect_3) {
			list.add("Effectiveness Upgrade");
			list.add("Mining Drill:");
			list.add("Radius +3 / Consumption +240");
		}

		if(this == ModItems.upgrade_power_1) {
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -30 / Delay +5");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -30 / Delay +5");
		}

		if(this == ModItems.upgrade_power_2) {
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -60 / Delay +10");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -60 / Delay +10");
		}

		if(this == ModItems.upgrade_power_3) {
			list.add("Power Saving Upgrade");
			list.add("Mining Drill:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Assembly Machine:");
			list.add("Consumption -90 / Delay +15");
			list.add("");
			list.add("Chemical Plant:");
			list.add("Consumption -90 / Delay +15");
		}

		if(this == ModItems.upgrade_fortune_1) {
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +1 / Delay +15");
		}

		if(this == ModItems.upgrade_fortune_2) {
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +2 / Delay +30");
		}

		if(this == ModItems.upgrade_fortune_3) {
			list.add("Fortune Upgrade");
			list.add("Mining Drill:");
			list.add("Fortune +3 / Delay +45");
		}

		if(this == ModItems.upgrade_afterburn_1) {
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x2 / Consumption x2.5");
		}

		if(this == ModItems.upgrade_afterburn_2) {
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x3 / Consumption x5");
		}

		if(this == ModItems.upgrade_afterburn_3) {
			list.add("Afterburner Upgrade");
			list.add("Turbofan:");
			list.add("Production x4 / Consumption x7.5");
		}
		if(this == ModItems.upgrade_radius) {
			list.add("Forcefield Range Upgrade");
			list.add("Radius +16 / Consumption +500");
			list.add("");
			list.add("Stacks to 16");
		}

		if(this == ModItems.upgrade_health) {
			list.add("Forcefield Health Upgrade");
			list.add("Max. Health +50 / Consumption +250");
			list.add("");
			list.add("Stacks to 16");
		}
		if(this == ModItems.ingot_combine_steel) {
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
		if(this == ModItems.ingot_euphemium) {
			list.add("A very special and yet strange element.");
		}
		if(this == ModItems.powder_euphemium) {
			list.add("Pulverized pink.");
			list.add("Tastes like strawberries.");
		}
		if(this == ModItems.nugget_euphemium) {
			list.add("A small piece of a pink metal.");
			list.add("It's properties are still unknown,");
			list.add("DEAL WITH IT carefully.");
		}
		if(this == ModItems.rod_quad_euphemium) {
			list.add("A quad fuel rod which contains a");
			list.add("very small ammount of a strange new element.");
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this == ModItems.plate_euphemium || this == ModItems.ingot_euphemium || this == ModItems.powder_neptunium || this == ModItems.powder_euphemium || this == ModItems.powder_iodine || this == ModItems.powder_astatine || this == ModItems.powder_neodymium || this == ModItems.powder_caesium || this == ModItems.powder_strontium || this == ModItems.powder_cobalt || this == ModItems.powder_bromine || this == ModItems.powder_niobium || this == ModItems.powder_tennessine || this == ModItems.powder_cerium || this == ModItems.nugget_euphemium || this == ModItems.rod_quad_euphemium) {
			return EnumRarity.EPIC;
		}

		if(this == ModItems.rod_schrabidium || this == ModItems.rod_dual_schrabidium || this == ModItems.rod_quad_schrabidium || this == ModItems.ingot_schrabidium || this == ModItems.nugget_schrabidium || this == ModItems.plate_schrabidium || ItemCell.hasFluid(stack, ModForgeFluids.sas3) || this == ModItems.powder_schrabidium || this == ModItems.wire_schrabidium || this == ModItems.ingot_saturnite || this == ModItems.plate_saturnite || this == ModItems.powder_thorium || this == ModItems.circuit_schrabidium || this == ModItems.gun_revolver_schrabidium_ammo) {
			return EnumRarity.RARE;
		}

		if(this == ModItems.plate_paa || this == ModItems.gun_mp_ammo || this == ModItems.gun_revolver_cursed_ammo) {
			return EnumRarity.UNCOMMON;
		}

		return EnumRarity.COMMON;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return super.hasEffect(stack);
	}

}
