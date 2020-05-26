package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemAssemblyTemplate.AssemblerRecipe;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
@Spaghetti("everything")
public class MachineRecipes {
	public static List<Item> stamps_flat = new ArrayList<Item>() {
		/**
		 * I don't even know what this serial version thing is.
		 */
		private static final long serialVersionUID = 4758678372533583790L;

		{
			add(ModItems.stamp_stone_flat);
			add(ModItems.stamp_iron_flat);
			add(ModItems.stamp_steel_flat);
			add(ModItems.stamp_titanium_flat);
			add(ModItems.stamp_obsidian_flat);
			add(ModItems.stamp_schrabidium_flat);
		}
	};

	public static List<Item> stamps_plate = new ArrayList<Item>() {
		/**
		* 
		*/
		private static final long serialVersionUID = -6373696756798212258L;

		{
			add(ModItems.stamp_stone_plate);
			add(ModItems.stamp_iron_plate);
			add(ModItems.stamp_steel_plate);
			add(ModItems.stamp_titanium_plate);
			add(ModItems.stamp_obsidian_plate);
			add(ModItems.stamp_schrabidium_plate);
		}
	};

	public static List<Item> stamps_wire = new ArrayList<Item>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1446284270063893048L;

		{
			add(ModItems.stamp_stone_wire);
			add(ModItems.stamp_iron_wire);
			add(ModItems.stamp_steel_wire);
			add(ModItems.stamp_titanium_wire);
			add(ModItems.stamp_obsidian_wire);
			add(ModItems.stamp_schrabidium_wire);
		}
	};

	public static List<Item> stamps_circuit = new ArrayList<Item>() {
		/**
		* 
		*/
		private static final long serialVersionUID = -149968111089313972L;

		{
			add(ModItems.stamp_stone_circuit);
			add(ModItems.stamp_iron_circuit);
			add(ModItems.stamp_steel_circuit);
			add(ModItems.stamp_titanium_circuit);
			add(ModItems.stamp_obsidian_circuit);
			add(ModItems.stamp_schrabidium_circuit);
		}
	};
	
	public static ItemStack getPressResult(ItemStack input, ItemStack stamp) {
		if (input == null || stamp == null)
			return null;

		if (stamps_flat.contains(stamp.getItem())) {

			if (mODE(input, "dustCoal"))
				return new ItemStack(Items.COAL);
			if (mODE(input, "dustQuartz"))
				return new ItemStack(Items.QUARTZ);
			if (mODE(input, "dustNetherQuartz"))
				return new ItemStack(Items.QUARTZ);
			if (mODE(input, "dustLapis"))
				return new ItemStack(Items.DYE, 1, 4);
			if (mODE(input, "dustDiamond"))
				return new ItemStack(Items.DIAMOND);
			if (mODE(input, "dustEmerald"))
				return new ItemStack(Items.EMERALD);
			if (input.getItem() == ModItems.pellet_coal)
				return new ItemStack(Items.DIAMOND);
			if (input.getItem() == ModItems.biomass)
				return new ItemStack(ModItems.biomass_compressed);
			if (input.getItem() == ModItems.powder_lignite)
				return new ItemStack(ModItems.briquette_lignite);
		}

		if (stamps_plate.contains(stamp.getItem())) {

			if (mODE(input, "ingotIron"))
				return new ItemStack(ModItems.plate_iron);
			if (mODE(input, "ingotGold"))
				return new ItemStack(ModItems.plate_gold);
			if (mODE(input, "ingotTitanium"))
				return new ItemStack(ModItems.plate_titanium);
			if (mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.plate_aluminium);
			if (mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.plate_steel);
			if (mODE(input, "ingotLead"))
				return new ItemStack(ModItems.plate_lead);
			if (mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.plate_copper);
			if (mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if (mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if (mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.plate_schrabidium);
			if (mODE(input, "ingotCMBSteel"))
				return new ItemStack(ModItems.plate_combine_steel);
			if (mODE(input, "ingotSaturnite"))
				return new ItemStack(ModItems.plate_saturnite);

		}

		if (stamps_wire.contains(stamp.getItem())) {

			if (mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.wire_aluminium, 8);
			if (mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.wire_copper, 8);
			if (mODE(input, "ingotTungsten"))
				return new ItemStack(ModItems.wire_tungsten, 8);
			if (mODE(input, "ingotRedAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if (mODE(input, "ingotRedstoneAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if (mODE(input, "ingotGold"))
				return new ItemStack(ModItems.wire_gold, 8);
			if (mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.wire_schrabidium, 8);
			if (mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if (mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if (mODE(input, "ingotMagnetizedTungsten"))
				return new ItemStack(ModItems.wire_magnetized_tungsten, 8);
		}

		if (stamps_circuit.contains(stamp.getItem())) {

			if (input.getItem() == ModItems.circuit_raw)
				return new ItemStack(ModItems.circuit_aluminium);
		}

		if (stamp.getItem() == ModItems.stamp_357) {

			if (input.getItem() == ModItems.assembly_iron)
				return new ItemStack(ModItems.gun_revolver_iron_ammo);
			if (input.getItem() == ModItems.assembly_steel)
				return new ItemStack(ModItems.gun_revolver_ammo);
			if (input.getItem() == ModItems.assembly_lead)
				return new ItemStack(ModItems.gun_revolver_lead_ammo);
			if (input.getItem() == ModItems.assembly_gold)
				return new ItemStack(ModItems.gun_revolver_gold_ammo);
			if (input.getItem() == ModItems.assembly_schrabidium)
				return new ItemStack(ModItems.gun_revolver_schrabidium_ammo);
			if (input.getItem() == ModItems.assembly_nightmare)
				return new ItemStack(ModItems.gun_revolver_nightmare_ammo);
			if (input.getItem() == ModItems.assembly_desh)
				return new ItemStack(ModItems.ammo_357_desh);

			if (mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.gun_revolver_cursed_ammo);
		}

		if (stamp.getItem() == ModItems.stamp_44) {

			if (input.getItem() == ModItems.assembly_nopip)
				return new ItemStack(ModItems.ammo_44);
		}

		if (stamp.getItem() == ModItems.stamp_9) {

			if (input.getItem() == ModItems.assembly_smg)
				return new ItemStack(ModItems.ammo_9mm);
			if (input.getItem() == ModItems.assembly_uzi)
				return new ItemStack(ModItems.ammo_22lr);
			if (mODE(input, "ingotGold"))
				return new ItemStack(ModItems.gun_mp_ammo);
			if (input.getItem() == ModItems.assembly_lacunae)
				return new ItemStack(ModItems.ammo_5mm);
		}

		if (stamp.getItem() == ModItems.stamp_50) {

			if (input.getItem() == ModItems.assembly_calamity)
				return new ItemStack(ModItems.ammo_50bmg);
			if (input.getItem() == ModItems.assembly_actionexpress)
				return new ItemStack(ModItems.ammo_50ae);
		}

		return null;
	}

	public static ItemStack getFurnaceProcessingResult(ItemStack item, ItemStack item2) {
		return getFurnaceOutput(item, item2);
	}

	public static ItemStack getFurnaceOutput(ItemStack item, ItemStack item2) {

		if (item == null || item2 == null)
			return null;

		if (MainRegistry.enableDebugMode) {
			if (item.getItem() == Items.IRON_INGOT && item2.getItem() == Items.QUARTZ
					|| item.getItem() == Items.QUARTZ && item2.getItem() == Items.IRON_INGOT) {
				return new ItemStack(ModBlocks.test_render, 1);
			}
		}

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "gemCoal")
				|| mODE(item, "gemCoal") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.neutron_reflector, 2);
		}

		if (mODE(item, new String[] {"ingotLead", "dustLead"}) && mODE(item2, new String[] {"ingotCopper", "dustCopper"})
				|| mODE(item, new String[] {"ingotCopper", "dustCopper"}) && mODE(item2, new String[] {"ingotLead", "dustLead"})) {
			return new ItemStack(ModItems.neutron_reflector, 4);
		}

		if (mODE(item, "plateLead") && mODE(item2, "plateCopper")
				|| mODE(item, "plateCopper") && mODE(item2, "plateLead")) {
			return new ItemStack(ModItems.neutron_reflector, 1);
		}

		if (mODE(item, new String[] {"ingotIron", "dustIron"}) && mODE(item2, new String[] {"gemCoal", "dustCoal"})
				|| mODE(item, new String[] {"gemCoal", "dustCoal"}) && mODE(item2, new String[] {"ingotIron", "dustIron"})) {
			return new ItemStack(ModItems.ingot_steel, 2);
		}

		if (mODE(item, new String[] {"ingotCopper", "dustCopper"}) && item2.getItem() == Items.REDSTONE
				|| item.getItem() == Items.REDSTONE && mODE(item2, new String[] {"ingotCopper", "dustCopper"})) {
			return new ItemStack(ModItems.ingot_red_copper, 2);
		}

		if (ItemFluidCanister.isFullCanister(item, ModForgeFluids.diesel) && item2.getItem() == Items.SLIME_BALL
				|| item.getItem() == Items.SLIME_BALL && ItemFluidCanister.isFullCanister(item2, ModForgeFluids.diesel)) {
			return new ItemStack(ModItems.canister_napalm, 1);
		}

		if (mODE(item, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})
				|| mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"})) {
			return new ItemStack(ModItems.ingot_advanced_alloy, 2);
		}

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "nuggetSchrabidium")
				|| mODE(item, "nuggetSchrabidium") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.ingot_magnetized_tungsten, 1);
		}

		if (item.getItem() == ModItems.plate_mixed && mODE(item2, "plateGold")
				|| mODE(item, "plateGold") && item2.getItem() == ModItems.plate_mixed) {
			return new ItemStack(ModItems.plate_paa, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})
				|| mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotCobalt", "dustCobalt"})
				|| mODE(item, new String[] {"ingotCobalt", "dustCobalt"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSaturnite", "dustSaturnite"}) && item2.getItem() == ModItems.powder_meteorite
				|| item.getItem() == ModItems.powder_meteorite && mODE(item2, new String[] {"ingotSaturnite", "dustSaturnite"})) {
			return new ItemStack(ModItems.ingot_starmetal, 2);
		}

		return null;
	}

	// Matches Ore Dict Entry
	public static boolean mODE(ItemStack stack, String name) {
		if (stack.isEmpty())
			return false;
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));

		for (int i = 0; i < ids.length; i++) {

			String s = OreDictionary.getOreName(ids[i]);

			if (s.length() > 0 && s.equals(name))
				return true;
		}

		return false;
	}

	public static boolean mODE(Item item, String[] names) {
		return mODE(new ItemStack(item), names);
	}

	public static boolean mODE(ItemStack item, String[] names) {
		boolean flag = false;
		if (names.length > 0) {
			for (int i = 0; i < names.length; i++) {
				if (mODE(item, names[i]))
					flag = true;
			}
		}

		return flag;
	}

	public static ItemStack getOutputFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return ItemStack.EMPTY;

		int type = ItemAssemblyTemplate.getTagWithRecipeNumber(stack).getInteger("type");
		if (type >= ItemAssemblyTemplate.recipes.size())
			return ItemStack.EMPTY;
		AssemblerRecipe template = ItemAssemblyTemplate.recipes.get(type);
		if (template == null)
			return ItemStack.EMPTY;
		ItemStack output = ItemStack.EMPTY;
		if (template.getOutput() != null) {
			return template.getOutput().copy();
		}
		return output;
	}

	public static List<ItemStack> getRecipeFromTempate(ItemStack stack) {
		if (stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;

		int type = ItemAssemblyTemplate.getTagWithRecipeNumber(stack).getInteger("type");
		List<ItemStack> list = new ArrayList<ItemStack>();
		if (type >= ItemAssemblyTemplate.recipes.size())
			return null;
		AssemblerRecipe template = ItemAssemblyTemplate.recipes.get(type);
		if (template == null)
			return null;
		if (template.getInputs() != null) {
			return copyItemStackList(template.getInputs());
		}

		if (list.isEmpty()) {
			return null;
		} else {
			return list;
		}
	}

	public static List<ItemStack> copyItemStackList(List<ItemStack> list) {
		List<ItemStack> newList = new ArrayList<ItemStack>();
		if (list == null || list.isEmpty())
			return newList;
		for (ItemStack stack : list) {
			newList.add(stack.copy());
		}
		return newList;
	}

	public static List<ItemStack> getChemInputFromTempate(ItemStack stack) {

		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;

		List<ItemStack> list = new ArrayList<ItemStack>();

		switch (ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case CC_OIL:
			list.add(new ItemStack(Items.COAL, 10));
			break;
		case CC_I:
			list.add(new ItemStack(Items.COAL, 8));
			break;
		case CC_HEATING:
			list.add(new ItemStack(Items.COAL, 8));
			break;
		case CC_HEAVY:
			list.add(new ItemStack(Items.COAL, 10));
			break;
		case CC_NAPHTHA:
			list.add(new ItemStack(Items.COAL, 10));
			break;
		case ASPHALT:
			list.add(new ItemStack(Blocks.GRAVEL, 2));
			list.add(new ItemStack(Blocks.SAND, 6));
			break;
		case CONCRETE:
			list.add(new ItemStack(Blocks.GRAVEL, 8));
			list.add(new ItemStack(Blocks.SAND, 8));
			break;
		case COOLANT:
			list.add(new ItemStack(ModItems.niter, 1));
			break;
		case CRYOGEL:
			list.add(new ItemStack(ModItems.powder_ice, 1));
			break;
		case DESH:
			list.add(new ItemStack(ModItems.powder_desh_mix, 1));
			break;
		case CIRCUIT_4:
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 1));
			break;
		case CIRCUIT_5:
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_schrabidium, 6));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.ingot_desh, 1));
			break;
		case POLYMER:
			list.add(new ItemStack(Items.COAL, 2));
			list.add(new ItemStack(ModItems.fluorite, 1));
			break;
		case DEUTERIUM:
			list.add(new ItemStack(ModItems.sulfur, 1));
			break;
		case BP_BIOGAS:
			list.add(new ItemStack(ModItems.biomass, 16));
			break;
		case YELLOWCAKE:
			list.add(new ItemStack(ModItems.powder_uranium, 1));
			list.add(new ItemStack(ModItems.sulfur, 2));
			break;
		case UF6:
			list.add(new ItemStack(ModItems.powder_yellowcake, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
		case PUF6:
			list.add(new ItemStack(ModItems.powder_plutonium, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
		case SAS3:
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.sulfur, 2));
			break;
		case NITAN:
			list.add(new ItemStack(ModItems.powder_nitan_mix, 2));
			break;
		case OIL_SAND:
			list.add(new ItemStack(ModBlocks.ore_oil_sand, 16));
			break;
		case DYN_SCHRAB:
			list.add(new ItemStack(ModItems.dynosphere_desh_charged, 3));
			list.add(new ItemStack(ModItems.ingot_uranium, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 8));
			break;
		case DYN_EUPH:
			list.add(new ItemStack(ModItems.dynosphere_schrabidium_charged, 1));
			list.add(new ItemStack(ModItems.ingot_plutonium, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 16));
			break;
		case DYN_DNT:
			list.add(new ItemStack(ModItems.dynosphere_euphemium_charged, 2));
			list.add(new ItemStack(ModItems.powder_spark_mix, 1));
			list.add(new ItemStack(ModItems.ingot_starmetal, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 32));
			break;
		case CORDITE:
			list.add(new ItemStack(ModItems.niter, 2));
			list.add(new ItemStack(Blocks.PLANKS, 1));
			list.add(new ItemStack(Items.SUGAR, 1));
			break;
		case KEVLAR:
			list.add(new ItemStack(ModItems.niter, 2));
			list.add(new ItemStack(Items.BRICK, 1));
			list.add(new ItemStack(Items.COAL, 1));
			break;
		case SOLID_FUEL:
			list.add(new ItemStack(ModItems.solid_fuel, 2));
			list.add(new ItemStack(ModItems.niter, 1));
			list.add(new ItemStack(Items.REDSTONE, 1));
			break;
		case SATURN:
			list.add(new ItemStack(ModItems.powder_dura_steel, 1));
			list.add(new ItemStack(ModItems.powder_fire, 1));
			break;
		default:
			break;
		}

		if (list.isEmpty())
			return null;
		else
			return list;
	}

	public static FluidStack[] getFluidInputFromTempate(ItemStack stack) {

		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;

		FluidStack[] input = new FluidStack[2];

		switch (ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case FP_HEAVYOIL:
			input[0] = new FluidStack(ModForgeFluids.heavyoil, 1000);
			break;
		case FP_SMEAR:
			input[0] = new FluidStack(ModForgeFluids.smear, 1000);
			break;
		case FP_NAPHTHA:
			input[0] = new FluidStack(ModForgeFluids.naphtha, 1000);
			break;
		case FP_LIGHTOIL:
			input[0] = new FluidStack(ModForgeFluids.lightoil, 1000);
			break;
		case FR_REOIL:
			input[0] = new FluidStack(ModForgeFluids.smear, 1000);
			break;
		case FR_PETROIL:
			input[0] = new FluidStack(ModForgeFluids.reclaimed, 800);
			input[1] = new FluidStack(ModForgeFluids.lubricant, 200);
			break;
		case FC_BITUMEN:
			input[0] = new FluidStack(ModForgeFluids.bitumen, 1200);
			input[1] = new FluidStack(ModForgeFluids.steam, 2400);
			break;
		case FC_I_NAPHTHA:
			input[0] = new FluidStack(ModForgeFluids.smear, 1400);
			input[1] = new FluidStack(FluidRegistry.WATER, 800);
			break;
		case FC_GAS_PETROLEUM:
			input[0] = new FluidStack(ModForgeFluids.gas, 1800);
			input[1] = new FluidStack(FluidRegistry.WATER, 1200);
			break;
		case FC_DIESEL_KEROSENE:
			input[0] = new FluidStack(ModForgeFluids.diesel, 1200);
			input[1] = new FluidStack(ModForgeFluids.steam, 2000);
			break;
		case FC_KEROSENE_PETROLEUM:
			input[0] = new FluidStack(ModForgeFluids.kerosene, 1400);
			input[1] = new FluidStack(ModForgeFluids.steam, 2000);
			break;
		case CC_I:
			input[0] = new FluidStack(ModForgeFluids.smear, 800);
			input[1] = new FluidStack(FluidRegistry.WATER, 1800);
			break;
		case CC_OIL:
			input[0] = new FluidStack(ModForgeFluids.oil, 600);
			input[1] = new FluidStack(ModForgeFluids.steam, 1400);
			break;
		case CC_HEATING:
			input[0] = new FluidStack(ModForgeFluids.heatingoil, 800);
			input[1] = new FluidStack(ModForgeFluids.steam, 2000);
			break;
		case CC_HEAVY:
			input[0] = new FluidStack(ModForgeFluids.heavyoil, 600);
			input[1] = new FluidStack(FluidRegistry.WATER, 1400);
			break;
		case CC_NAPHTHA:
			input[0] = new FluidStack(ModForgeFluids.naphtha, 1200);
			input[1] = new FluidStack(ModForgeFluids.steam, 2400);
			break;
		case ASPHALT:
			input[0] = new FluidStack(ModForgeFluids.bitumen, 8000);
			break;
		case CONCRETE:
			input[0] = new FluidStack(FluidRegistry.WATER, 2000);
			break;
		case COOLANT:
			input[0] = new FluidStack(FluidRegistry.WATER, 1800);
			break;
		case CRYOGEL:
			input[0] = new FluidStack(ModForgeFluids.coolant, 1800);
			break;
		case DESH:
			input[0] = new FluidStack(ModForgeFluids.acid, 800);
			input[1] = new FluidStack(ModForgeFluids.lightoil, 200);
			break;
		case PEROXIDE:
			input[0] = new FluidStack(FluidRegistry.WATER, 1000);
			break;
		case CIRCUIT_4:
			input[0] = new FluidStack(ModForgeFluids.acid, 400);
			break;
		case CIRCUIT_5:
			input[0] = new FluidStack(ModForgeFluids.acid, 800);
			input[1] = new FluidStack(ModForgeFluids.petroleum, 400);
			break;
		case SF_OIL:
			input[0] = new FluidStack(ModForgeFluids.oil, 350);
			break;
		case SF_HEAVYOIL:
			input[0] = new FluidStack(ModForgeFluids.heavyoil, 250);
			break;
		case SF_SMEAR:
			input[0] = new FluidStack(ModForgeFluids.smear, 200);
			break;
		case SF_HEATINGOIL:
			input[0] = new FluidStack(ModForgeFluids.heatingoil, 100);
			break;
		case SF_RECLAIMED:
			input[0] = new FluidStack(ModForgeFluids.reclaimed, 200);
			break;
		case SF_PETROIL:
			input[0] = new FluidStack(ModForgeFluids.petroil, 250);
			break;
		case SF_LUBRICANT:
			input[0] = new FluidStack(ModForgeFluids.lubricant, 250);
			break;
		case SF_NAPHTHA:
			input[0] = new FluidStack(ModForgeFluids.naphtha, 300);
			break;
		case SF_DIESEL:
			input[0] = new FluidStack(ModForgeFluids.diesel, 400);
			break;
		case SF_LIGHTOIL:
			input[0] = new FluidStack(ModForgeFluids.lightoil, 450);
			break;
		case SF_KEROSENE:
			input[0] = new FluidStack(ModForgeFluids.kerosene, 550);
			break;
		case SF_GAS:
			input[0] = new FluidStack(ModForgeFluids.gas, 750);
			break;
		case SF_PETROLEUM:
			input[0] = new FluidStack(ModForgeFluids.petroleum, 600);
			break;
		case SF_BIOGAS:
			input[0] = new FluidStack(ModForgeFluids.biogas, 400);
			break;
		case SF_BIOFUEL:
			input[0] = new FluidStack(ModForgeFluids.biofuel, 300);
			break;
		case POLYMER:
			input[0] = new FluidStack(ModForgeFluids.petroleum, 600);
			break;
		case DEUTERIUM:
			input[0] = new FluidStack(FluidRegistry.WATER, 4000);
			break;
		case STEAM:
			input[0] = new FluidStack(FluidRegistry.WATER, 1000);
			break;
		case BP_BIOFUEL:
			input[0] = new FluidStack(ModForgeFluids.biogas, 2000);
			break;
		case YELLOWCAKE:
			input[0] = new FluidStack(ModForgeFluids.acid, 500);
			break;
		case UF6:
			input[0] = new FluidStack(FluidRegistry.WATER, 1000);
			break;
		case PUF6:
			input[0] = new FluidStack(FluidRegistry.WATER, 1000);
			break;
		case SAS3:
			input[0] = new FluidStack(ModForgeFluids.acid, 2000);
			break;
		case NITAN:
			input[0] = new FluidStack(ModForgeFluids.kerosene, 600);
			break;
		case OIL_SAND:
			input[0] = new FluidStack(ModForgeFluids.bitumen, 400);
			break;
		case CORDITE:
			input[0] = new FluidStack(ModForgeFluids.heatingoil, 200);
			break;
		case KEVLAR:
			input[0] = new FluidStack(ModForgeFluids.petroleum, 100);
			break;
		case SOLID_FUEL:
			input[0] = new FluidStack(ModForgeFluids.petroleum, 200);
			break;
		case ELECTROLYSIS:
			input[0] = new FluidStack(FluidRegistry.WATER, 8000);
			break;
		case XENON:
			// input[0] = null;
			break;
		case SATURN:
			input[0] = new FluidStack(ModForgeFluids.acid, 100);
			input[1] = new FluidStack(ModForgeFluids.mercury, 200);
        	break;
		default:
			break;
		}

		return input;
	}

	public static ItemStack[] getChemOutputFromTempate(ItemStack stack) {

		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;

		ItemStack[] output = new ItemStack[4];

		switch (ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case ASPHALT:
			output[0] = new ItemStack(ModBlocks.asphalt, 4);
			output[1] = new ItemStack(ModBlocks.asphalt, 4);
			output[2] = new ItemStack(ModBlocks.asphalt, 4);
			output[3] = new ItemStack(ModBlocks.asphalt, 4);
			break;
		case CONCRETE:
			output[0] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[1] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[2] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[3] = new ItemStack(ModBlocks.concrete_smooth, 4);
			break;
		case DESH:
			output[0] = new ItemStack(ModItems.ingot_desh, 1);
			break;
		case CIRCUIT_4:
			output[0] = new ItemStack(ModItems.circuit_gold, 1);
			break;
		case CIRCUIT_5:
			output[0] = new ItemStack(ModItems.circuit_schrabidium, 1);
			break;
		case SF_OIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_HEAVYOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_SMEAR:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_HEATINGOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_RECLAIMED:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_PETROIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_LUBRICANT:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_NAPHTHA:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_DIESEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_LIGHTOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_KEROSENE:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_GAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_PETROLEUM:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_BIOGAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case SF_BIOFUEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
		case POLYMER:
			output[0] = new ItemStack(ModItems.ingot_polymer, 1);
			break;
		case YELLOWCAKE:
			output[0] = new ItemStack(ModItems.powder_yellowcake, 1);
			break;
		case DYN_SCHRAB:
			output[0] = new ItemStack(ModItems.ingot_schrabidium, 1);
			output[1] = new ItemStack(ModItems.powder_desh, 12);
			output[2] = new ItemStack(ModItems.powder_desh_mix, 12);
			break;
		case DYN_EUPH:
			output[0] = new ItemStack(ModItems.ingot_euphemium, 1);
			output[1] = new ItemStack(ModItems.powder_schrabidium, 4);
			output[2] = new ItemStack(ModItems.powder_power, 4);
			break;
		case DYN_DNT:
			output[0] = new ItemStack(ModItems.ingot_dineutronium, 1);
			output[1] = new ItemStack(ModItems.powder_euphemium, 8);
			output[2] = new ItemStack(ModItems.powder_nitan_mix, 8);
			break;
		case CORDITE:
			output[0] = new ItemStack(ModItems.cordite, 4);
			break;
		case KEVLAR:
			output[0] = new ItemStack(ModItems.plate_kevlar, 4);
			break;
		case SOLID_FUEL:
			output[0] = new ItemStack(ModItems.rocket_fuel, 1);
			break;
		case SATURN:
			output[0] = new ItemStack(ModItems.ingot_saturnite, 1);
        	break;
		default:
			break;
		}

		return output;
	}

	public static FluidStack[] getFluidOutputFromTempate(ItemStack stack) {

		if (stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;

		FluidStack[] output = new FluidStack[2];

		switch (ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case FP_HEAVYOIL:
			output[0] = new FluidStack(ModForgeFluids.bitumen, 300);
			output[1] = new FluidStack(ModForgeFluids.smear, 700);
			break;
		case FP_SMEAR:
			output[0] = new FluidStack(ModForgeFluids.heatingoil, 600);
			output[1] = new FluidStack(ModForgeFluids.lubricant, 400);
			break;
		case FP_NAPHTHA:
			output[0] = new FluidStack(ModForgeFluids.heatingoil, 400);
			output[1] = new FluidStack(ModForgeFluids.diesel, 600);
			break;
		case FP_LIGHTOIL:
			output[0] = new FluidStack(ModForgeFluids.diesel, 400);
			output[1] = new FluidStack(ModForgeFluids.kerosene, 600);
			break;
		case FR_REOIL:
			output[0] = new FluidStack(ModForgeFluids.reclaimed, 800);
			break;
		case FR_PETROIL:
			output[0] = new FluidStack(ModForgeFluids.petroil, 1000);
			break;
		case FC_BITUMEN:
			output[0] = new FluidStack(ModForgeFluids.oil, 1000);
			output[1] = new FluidStack(ModForgeFluids.petroleum, 200);
			break;
		case FC_I_NAPHTHA:
			output[0] = new FluidStack(ModForgeFluids.naphtha, 800);
			break;
		case FC_GAS_PETROLEUM:
			output[0] = new FluidStack(ModForgeFluids.petroleum, 800);
			break;
		case FC_DIESEL_KEROSENE:
			output[0] = new FluidStack(ModForgeFluids.kerosene, 400);
			break;
		case FC_KEROSENE_PETROLEUM:
			output[0] = new FluidStack(ModForgeFluids.petroleum, 800);
			break;
		case CC_OIL:
			output[0] = new FluidStack(ModForgeFluids.oil, 2000);
			break;
		case CC_I:
			output[0] = new FluidStack(ModForgeFluids.smear, 1600);
			break;
		case CC_HEATING:
			output[0] = new FluidStack(ModForgeFluids.heatingoil, 1800);
			break;
		case CC_HEAVY:
			output[0] = new FluidStack(ModForgeFluids.heavyoil, 1800);
			break;
		case CC_NAPHTHA:
			output[0] = new FluidStack(ModForgeFluids.naphtha, 2000);
			break;
		case COOLANT:
			output[0] = new FluidStack(ModForgeFluids.coolant, 2000);
			break;
		case CRYOGEL:
			output[0] = new FluidStack(ModForgeFluids.cryogel, 2000);
			break;
		case PEROXIDE:
			output[0] = new FluidStack(ModForgeFluids.acid, 800);
			break;
		case DEUTERIUM:
			output[0] = new FluidStack(ModForgeFluids.deuterium, 500);
			break;
		case STEAM:
			output[0] = new FluidStack(ModForgeFluids.steam, 1000);
			break;
		case BP_BIOGAS:
			output[0] = new FluidStack(ModForgeFluids.biogas, 4000);
			break;
		case BP_BIOFUEL:
			output[0] = new FluidStack(ModForgeFluids.biofuel, 1000);
			break;
		case UF6:
			output[0] = new FluidStack(ModForgeFluids.uf6, 1000);
			break;
		case PUF6:
			output[0] = new FluidStack(ModForgeFluids.puf6, 1000);
			break;
		case SAS3:
			output[0] = new FluidStack(ModForgeFluids.sas3, 1000);
			break;
		case NITAN:
			output[0] = new FluidStack(ModForgeFluids.nitan, 1000);
			break;
		case OIL_SAND:
			output[0] = new FluidStack(ModForgeFluids.bitumen, 1000);
			break;
		case DYN_SCHRAB:
			output[0] = new FluidStack(ModForgeFluids.watz, 50);
			break;
		case DYN_EUPH:
			output[0] = new FluidStack(ModForgeFluids.watz, 100);
			break;
		case DYN_DNT:
			output[0] = new FluidStack(ModForgeFluids.watz, 150);
			break;
		case ELECTROLYSIS:
			output[0] = new FluidStack(ModForgeFluids.hydrogen, 400);
			output[1] = new FluidStack(ModForgeFluids.oxygen, 400);
			break;
		case XENON:
			output[0] = new FluidStack(ModForgeFluids.xenon, 50);
			break;
		default:
			break;
		}

		return output;
	}

	// return: Fluid, amount produced, amount required, HE produced
	public static Object[] getTurbineOutput(Fluid type) {

		if (type == ModForgeFluids.steam) {
			return new Object[] { FluidRegistry.WATER, 5, 500, 50 };
		} else if (type == ModForgeFluids.hotsteam) {
			return new Object[] { ModForgeFluids.steam, 50, 5, 100 };
		} else if (type == ModForgeFluids.superhotsteam) {
			return new Object[] { ModForgeFluids.hotsteam, 50, 5, 150 };
		}

		return null;
	}

	// return: FluidType, amount produced, amount required, heat required (°C * 100)
	public static Object[] getBoilerOutput(Fluid type) {

		if (type == FluidRegistry.WATER) {
			return new Object[] { ModForgeFluids.steam, 500, 5, 10000 };
		} else if (type == ModForgeFluids.steam) {
			return new Object[] { ModForgeFluids.hotsteam, 5, 50, 30000 };
		} else if (type == ModForgeFluids.hotsteam) {
			return new Object[] { ModForgeFluids.superhotsteam, 5, 50, 45000 };
		} else if (type == ModForgeFluids.oil) {
			return new Object[] { ModForgeFluids.hotoil, 5, 5, 35000 };
		} else {
			return null;
		}

	}

	public static ItemStack[] getCentrifugeProcessingResult(ItemStack item) {
		return getCentrifugeOutput(item);
	}

	public static ItemStack[] getCentrifugeOutput(ItemStack item) {
		if(item == null || item.isEmpty())
			return null;
		ItemStack[] test = new ItemStack[] { new ItemStack(Items.APPLE, 3), new ItemStack(Items.LEATHER, 1),
				new ItemStack(Items.SUGAR, 3), new ItemStack(Items.BLAZE_POWDER, 2) };

		ItemStack[] uranF = new ItemStack[] { new ItemStack(ModItems.nugget_u235, 1),
				new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) };
		ItemStack[] plutoniumF = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nugget_pu240, 1), new ItemStack(ModItems.nugget_lead, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) };
		ItemStack[] moxF = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nugget_neptunium, 1), new ItemStack(ModItems.nugget_u238, 2),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) };
		ItemStack[] schrabidiumF = new ItemStack[] { new ItemStack(ModItems.nugget_beryllium, 1),
				new ItemStack(ModItems.nugget_lead, 1), new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) };
		ItemStack[] thoriumF = new ItemStack[] { new ItemStack(ModItems.nugget_u238, 1),
				new ItemStack(ModItems.nugget_th232, 1), new ItemStack(ModItems.nugget_u233, 3),
				new ItemStack(ModItems.nuclear_waste_tiny, 1) };
		
		ItemStack[] cloud = new ItemStack[] { new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.dust, 1),
				new ItemStack(ModItems.dust, 1) };

		ItemStack[] coal = new ItemStack[] { new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(ModItems.powder_coal, 2), new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] lignite = new ItemStack[] { new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(ModItems.powder_lignite, 2), new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] iron = new ItemStack[] { new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] gold = new ItemStack[] { new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(ModItems.powder_gold, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] diamond = new ItemStack[] { new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(ModItems.powder_diamond, 1), new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] emerald = new ItemStack[] { new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(ModItems.powder_emerald, 1), new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] titanium = new ItemStack[] { new ItemStack(ModItems.powder_titanium, 1),
				new ItemStack(ModItems.powder_titanium, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] quartz = new ItemStack[] { new ItemStack(ModItems.powder_quartz, 1),
				new ItemStack(ModItems.powder_quartz, 1), new ItemStack(ModItems.powder_lithium_tiny, 1),
				new ItemStack(Blocks.NETHERRACK, 1) };
		ItemStack[] tungsten = new ItemStack[] { new ItemStack(ModItems.powder_tungsten, 1),
				new ItemStack(ModItems.powder_tungsten, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] copper = new ItemStack[] { new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.powder_copper, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] aluminium = new ItemStack[] { new ItemStack(ModItems.powder_aluminium, 1),
				new ItemStack(ModItems.powder_aluminium, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] lead = new ItemStack[] { new ItemStack(ModItems.powder_lead, 1),
				new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] schrabidium = new ItemStack[] { new ItemStack(ModItems.powder_schrabidium, 1),
				new ItemStack(ModItems.powder_schrabidium, 1), new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] rare = new ItemStack[] { new ItemStack(ModItems.powder_desh_mix, 1),
				new ItemStack(ModItems.powder_actinium_tiny, 1), new ItemStack(ModItems.powder_lanthanium_tiny, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] plutonium = new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 1),
				new ItemStack(ModItems.powder_plutonium, 1), new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] uranium = new ItemStack[] { new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(ModItems.powder_uranium, 1), new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] thorium = new ItemStack[] { new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(ModItems.powder_thorium, 1), new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] beryllium = new ItemStack[] { new ItemStack(ModItems.powder_beryllium, 1),
				new ItemStack(ModItems.powder_beryllium, 1), new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] redstone = new ItemStack[] { new ItemStack(Items.REDSTONE, 3),
				new ItemStack(Items.REDSTONE, 3), new ItemStack(ModItems.nugget_mercury, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] tikite = new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 2),
				new ItemStack(ModItems.powder_cobalt, 2), new ItemStack(ModItems.powder_nitan_mix, 1),
				new ItemStack(Blocks.END_STONE, 1) };
		ItemStack[] lapis = new ItemStack[] { new ItemStack(ModItems.powder_lapis, 3),
				new ItemStack(ModItems.powder_lapis, 3), new ItemStack(ModItems.powder_cobalt_tiny, 1),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] starmetal = new ItemStack[] { new ItemStack(ModItems.powder_dura_steel, 3),
				new ItemStack(ModItems.powder_astatine, 1), new ItemStack(ModItems.powder_cobalt, 2),
				new ItemStack(Blocks.GRAVEL, 1) };
		ItemStack[] euphCluster = new ItemStack[] { new ItemStack(ModItems.nugget_euphemium, 7),
				new ItemStack(ModItems.powder_schrabidium, 4), new ItemStack(ModItems.ingot_starmetal, 2),
				new ItemStack(ModItems.nugget_solinium, 2) };

		if (MainRegistry.enableDebugMode) {
			if (item.getItem() == Item.getItemFromBlock(ModBlocks.test_render)) {
				return test;
			}
		}

		if (item.getItem() == ModItems.waste_uranium) {
			return uranF;
		}

		if (item.getItem() == ModItems.waste_thorium) {
			return thoriumF;
		}

		if (item.getItem() == ModItems.waste_plutonium) {
			return plutoniumF;
		}

		if (item.getItem() == ModItems.waste_mox) {
			return moxF;
		}

		if (item.getItem() == ModItems.waste_schrabidium) {
			return schrabidiumF;
		}

		if (item.getItem() == ModItems.powder_cloud) {
			return cloud;
		}

		if (mODE(item, "oreCoal")) {
			return coal;
		}

		if (mODE(item, "oreLignite")) {
			return lignite;
		}

		if (mODE(item, "oreIron")) {
			return iron;
		}

		if (mODE(item, "oreGold")) {
			return gold;
		}

		if (mODE(item, "oreDiamond")) {
			return diamond;
		}

		if (mODE(item, "oreEmerald")) {
			return emerald;
		}

		if (mODE(item, "oreUranium")) {
			return uranium;
		}

		if (mODE(item, "oreThorium")) {
			return thorium;
		}

		if (mODE(item, "orePlutonium")) {
			return plutonium;
		}

		if (mODE(item, "oreTitanium")) {
			return titanium;
		}

		if (mODE(item, "oreTungsten")) {
			return tungsten;
		}

		if (mODE(item, "oreLead")) {
			return lead;
		}

		if (mODE(item, "oreBeryllium")) {
			return beryllium;
		}

		if (mODE(item, "oreAluminum")) {
			return aluminium;
		}

		if (mODE(item, "oreSchrabidium")) {
			return schrabidium;
		}

		if (mODE(item, "oreQuartz") || mODE(item, "oreNetherQuartz")) {
			return quartz;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_rare)) {
			return rare;
		}

		if (mODE(item, "oreCopper")) {
			return copper;
		}

		if (mODE(item, "oreRedstone") || item.getItem() == Item.getItemFromBlock(Blocks.LIT_REDSTONE_ORE)) {
			return redstone;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_tikite)) {
			return tikite;
		}

		if (mODE(item, "oreLapis")) {
			return lapis;
		}

		if (mODE(item, "oreStarmetal")) {
			return starmetal;
		}
		
		if (item.getItem() == Item.getItemFromBlock(ModBlocks.block_euphemium_cluster)) {
			return euphCluster;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_fire)) {
			return new ItemStack[] {
					new ItemStack(Items.BLAZE_POWDER, 2),
					new ItemStack(ModItems.powder_fire, 2),
					new ItemStack(ModItems.ingot_phosphorus),
					new ItemStack(Blocks.NETHERRACK)
			};
		}

		if (item.getItem() == Items.BLAZE_ROD) {
			return new ItemStack[] {
					new ItemStack(Items.BLAZE_POWDER, 1),
					new ItemStack(Items.BLAZE_POWDER, 1),
					new ItemStack(ModItems.powder_fire, 1),
					new ItemStack(ModItems.powder_fire, 1)
			};
		}

		return null;
	}
	
	public static List<GasCentOutput> getGasCentOutput(Fluid fluid) {
		
		List<GasCentOutput> list = new ArrayList<GasCentOutput>();
		if(fluid == null){
			return null;
		} else if(fluid == FluidRegistry.LAVA){
			list.add(new GasCentOutput(1, new ItemStack(ModBlocks.gravel_obsidian), 1));
			list.add(new GasCentOutput(2, new ItemStack(Blocks.GRAVEL), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_lithium), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_iron, 2), 4));
			return list;
		} else if(fluid == ModForgeFluids.uf6){
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_u238), 1));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_u238), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_u235), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.fluorite), 4));
			return list;
		} else if(fluid == ModForgeFluids.puf6){
			list.add(new GasCentOutput(3, new ItemStack(ModItems.nugget_pu238), 1));
			list.add(new GasCentOutput(2, new ItemStack(ModItems.nugget_pu239), 2));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_pu240), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.fluorite), 4));
			return list;
		} else if(fluid == ModForgeFluids.watz){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_schrabidium), 1));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.nugget_uranium), 2));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.powder_iron), 3));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.powder_copper), 4));
			return list;
		} else if(fluid == ModForgeFluids.sas3){
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_schrabidium), 1));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_schrabidium), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.sulfur), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.sulfur), 4));
			return list;
		} else if(fluid == ModForgeFluids.coolant){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 4));
			return list;
		} else if(fluid == ModForgeFluids.cryogel){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_ice), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_ice), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 4));
			return list;
		} else if(fluid == ModForgeFluids.nitan){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 4));
			return list;
		}
		
		return null;
	}
	
	public static class GasCentOutput {
		public int weight;
		public ItemStack output;
		public int slot;
		
		public GasCentOutput(int w, ItemStack s, int i) {
			weight = w;
			output = s;
			slot = i;
		}
	}


	public static int getFluidConsumedGasCent(Fluid fluid) {
		if(fluid == null)
			return 0;
		else if(fluid == FluidRegistry.LAVA)
			return 1000;
		else if(fluid == ModForgeFluids.uf6)
			return 100;
		else if(fluid == ModForgeFluids.puf6)
			return 100;
		else if(fluid == ModForgeFluids.watz)
			return 100;
		else if(fluid == ModForgeFluids.sas3)
			return 100;
		else if(fluid == ModForgeFluids.coolant)
			return 2000;
		else if(fluid == ModForgeFluids.cryogel)
			return 1000;
		else if(fluid == ModForgeFluids.nitan)
			return 500;
		else
			return 100;
	}
	
	public static ItemStack getReactorProcessingResult(Item item) {
		return getReactorOutput(item);
	}

	public static ItemStack getReactorOutput(Item item) {

		if (item == ModItems.rod_th232) {
			return new ItemStack(ModItems.rod_u233, 1);
		}

		if (item == ModItems.rod_uranium) {
			return new ItemStack(ModItems.rod_plutonium, 1);
		}

		if (item == ModItems.rod_u233) {
			return new ItemStack(ModItems.rod_u235, 1);
		}

		if (item == ModItems.rod_u235) {
			return new ItemStack(ModItems.rod_neptunium, 1);
		}

		if (item == ModItems.rod_u238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_neptunium) {
			return new ItemStack(ModItems.rod_pu238, 1);
		}

		if (item == ModItems.rod_plutonium) {
			return new ItemStack(ModItems.rod_waste, 1);
		}

		if (item == ModItems.rod_pu238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_pu239) {
			return new ItemStack(ModItems.rod_pu240, 1);
		}

		if (item == ModItems.rod_pu240) {
			return new ItemStack(ModItems.rod_waste, 1);
		}

		if (item == ModItems.rod_schrabidium) {
			return new ItemStack(ModItems.rod_solinium, 1);
		}

		if (item == ModItems.rod_dual_th232) {
			return new ItemStack(ModItems.rod_dual_u233, 1);
		}

		if (item == ModItems.rod_dual_uranium) {
			return new ItemStack(ModItems.rod_dual_plutonium, 1);
		}

		if (item == ModItems.rod_dual_u233) {
			return new ItemStack(ModItems.rod_dual_u235, 1);
		}

		if (item == ModItems.rod_dual_u235) {
			return new ItemStack(ModItems.rod_dual_neptunium, 1);
		}

		if (item == ModItems.rod_dual_u238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_neptunium) {
			return new ItemStack(ModItems.rod_dual_pu238, 1);
		}

		if (item == ModItems.rod_dual_plutonium) {
			return new ItemStack(ModItems.rod_dual_waste, 1);
		}

		if (item == ModItems.rod_dual_pu238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_pu239) {
			return new ItemStack(ModItems.rod_dual_pu240, 1);
		}

		if (item == ModItems.rod_dual_pu240) {
			return new ItemStack(ModItems.rod_dual_waste, 1);
		}

		if (item == ModItems.rod_dual_schrabidium) {
			return new ItemStack(ModItems.rod_dual_solinium, 1);
		}

		if (item == ModItems.rod_quad_th232) {
			return new ItemStack(ModItems.rod_quad_u233, 1);
		}

		if (item == ModItems.rod_quad_uranium) {
			return new ItemStack(ModItems.rod_quad_plutonium, 1);
		}

		if (item == ModItems.rod_quad_u233) {
			return new ItemStack(ModItems.rod_quad_u235, 1);
		}

		if (item == ModItems.rod_quad_u235) {
			return new ItemStack(ModItems.rod_quad_neptunium, 1);
		}

		if (item == ModItems.rod_quad_u238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_neptunium) {
			return new ItemStack(ModItems.rod_quad_pu238, 1);
		}

		if (item == ModItems.rod_quad_plutonium) {
			return new ItemStack(ModItems.rod_quad_waste, 1);
		}

		if (item == ModItems.rod_quad_pu238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_pu239) {
			return new ItemStack(ModItems.rod_quad_pu240, 1);
		}

		if (item == ModItems.rod_quad_pu240) {
			return new ItemStack(ModItems.rod_quad_waste, 1);
		}

		if (item == ModItems.rod_quad_schrabidium) {
			return new ItemStack(ModItems.rod_quad_solinium, 1);
		}

		if (item == ModItems.rod_lithium) {
			return new ItemStack(ModItems.rod_tritium, 1);
		}

		if (item == ModItems.rod_dual_lithium) {
			return new ItemStack(ModItems.rod_dual_tritium, 1);
		}

		if (item == ModItems.rod_quad_lithium) {
			return new ItemStack(ModItems.rod_quad_tritium, 1);
		}

		if (item == ModItems.rod_quad_solinium) {
			return new ItemStack(ModItems.rod_quad_euphemium, 1);
		}

		if (item == Item.getItemFromBlock(Blocks.STONE)) {
			return new ItemStack(ModBlocks.sellafield_slaked, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_slaked)) {
			return new ItemStack(ModBlocks.sellafield_0, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_0)) {
			return new ItemStack(ModBlocks.sellafield_1, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_1)) {
			return new ItemStack(ModBlocks.sellafield_2, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_2)) {
			return new ItemStack(ModBlocks.sellafield_3, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_3)) {
			return new ItemStack(ModBlocks.sellafield_4, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_4)) {
			return new ItemStack(ModBlocks.sellafield_core, 1);
		}

		//Drillgon200: No more easy cheats
		/*if (item == ModItems.bobmazon_materials) {
			return new ItemStack(ModItems.bobmazon_hidden);
		}*/

		return null;
	}
	
	public static List<ShredderRecipe> recipesShredder = new ArrayList<ShredderRecipe>();
	public static List<DictCouple> theWholeThing = new ArrayList<DictCouple>();
	public static int dustCount = 0;
	
	//Drillgon200: I should really update this to the much better new shredder recipe system
	public static ItemStack getShredderResult(ItemStack stack) {
		if(stack == null || stack.isEmpty())
			return new ItemStack(ModItems.scrap);
		for(ShredderRecipe rec : recipesShredder)
		{
			if(stack != null && 
					rec.input.getItem() == stack.getItem() && 
					rec.input.getItemDamage() == stack.getItemDamage())
				return rec.output.copy();
		}
		
		return new ItemStack(ModItems.scrap);
	}
	
	public Map<Object, Object> getShredderRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < MachineRecipes.recipesShredder.size(); i++) {
			if(MachineRecipes.recipesShredder.get(i) != null && MachineRecipes.recipesShredder.get(i).output.getItem() != ModItems.scrap)
				recipes.put(MachineRecipes.recipesShredder.get(i).input, getShredderResult(MachineRecipes.recipesShredder.get(i).input));
		}
		
		return recipes;
	}
	
	public class ShredderRecipe {

		public ItemStack input;
		public ItemStack output;

		public void registerEverythingImSrs() {
			
			//Makes the OreDict easily accessible. Neat.
			
			//You see that guy up there? He's a liar. "easily accessible" may be true, but the detection is bullshit.

			/*System.out.println("Loading all items and blocks, please wait...");
			System.out.println("This process normally takes very long due to the incompetence of other modders I have to compensate for. Sorry for the inconvenience.");

			for (Object item : GameData.getItemRegistry()) {

				List<String> list = new ArrayList<String>();
				int[] array;

				if (item instanceof Item) {
					
					int x = 1;
					//if(((Item)item).getHasSubtypes())
					//	x = 126;

					for(int j = 0; j < x; j++)
					{
						ItemStack stack = new ItemStack((Item) item, 1, j);
						array = OreDictionary.getOreIDs(stack);

						for (int i = 0; i < array.length; i++) {
							// if
							// (!OreDictionary.getOreName(array[i]).equals("Unknown"))
							// {
							list.add(OreDictionary.getOreName(array[i]));
							// }
						}
						// if(list.size() > 0)
						theWholeThing.add(new DictCouple(stack, list));
					}
				}
			}

			for (Object block : GameData.getBlockRegistry()) {

				List<String> list = new ArrayList<String>();
				int[] array;

				if (block instanceof Block) {
					Item item = Item.getItemFromBlock((Block)block);
					
					int x = 1;
					//if(item != null && item.getHasSubtypes())
					//	x = 16;
					
					for(int j = 0; j < x; j++)
					{
						ItemStack stack = new ItemStack((Block) block, 1, j);
						array = OreDictionary.getOreIDs(stack);

						for (int i = 0; i < array.length; i++) {
							// if
							// (!OreDictionary.getOreName(array[i]).equals("Unknown"))
							// {
							list.add(OreDictionary.getOreName(array[i]));
							// }
						}

						// if(list.size() > 0)
						if(!doesExist(stack))
							theWholeThing.add(new DictCouple(stack, list));
					}
				}
			}
			
			System.out.println("Added " + theWholeThing.size() + " elements from the Ore Dict!");*/
			
			String[] names = OreDictionary.getOreNames();
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < names.length; i++) {
				stacks.addAll(OreDictionary.getOres(names[i]));
			}
			
			for(int i = 0; i < stacks.size(); i++) {
				
				int[] ids = OreDictionary.getOreIDs(stacks.get(i));

				List<String> oreNames = new ArrayList<String>();
				
				for(int j = 0; j < ids.length; j++) {
					oreNames.add(OreDictionary.getOreName(ids[j]));
				}
				
				theWholeThing.add(new DictCouple(stacks.get(i), oreNames));
			}
			
			MainRegistry.logger.info("Added " + theWholeThing.size() + " elements from the Ore Dict!");
		}
		
		public boolean doesExist(ItemStack stack) {
			
			for(DictCouple dic : theWholeThing) {
				if(dic.item.getItem() == stack.getItem() && dic.item.getItemDamage() == stack.getItemDamage())
					return true;
			}
			
			return false;
		}

		public void addRecipes() {

			// Not very efficient, I know, but at least it works AND it's
			// somewhat smart!
			
			for(int i = 0; i < theWholeThing.size(); i++)
			{
				for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					String s = theWholeThing.get(i).list.get(j);
					
					if (s.length() > 5 && s.substring(0, 5).equals("ingot")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, stack);
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("ore")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					/*} else if (s.length() > 3 && s.substring(0, 3).equals("rod")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}*/
					} else if (s.length() > 5 && s.substring(0, 5).equals("block")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 9, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("gem")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 4 && s.substring(0, 4).equals("dust")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else if (s.length() > 6 && s.substring(0, 6).equals("powder")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
					}
				}

				if(theWholeThing.get(i).list.isEmpty())
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
				if(!theWholeThing.get(i).list.isEmpty() && theWholeThing.get(i).list.get(0).equals("Unknown"))
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
			}

			MainRegistry.logger.info("Added " + recipesShredder.size() + " in total.");
			MainRegistry.logger.info("Added " + dustCount + " ore dust recipes.");
		}
		
		public ItemStack canFindDustByName(String s) {
			
			for(DictCouple d : theWholeThing)
			{
				for(String s1 : d.list)
				{
					if(s1.length() > 4 && s1.substring(0, 4).equals("dust") && s1.substring(4).equals(s))
					{
						dustCount++;
						return d.item;
					}
				}
			}
			
			return null;
		}
		
		public void setRecipe(ItemStack inp, ItemStack outp) {
			ShredderRecipe recipe = new ShredderRecipe();
			
			recipe.input = inp;
			recipe.output = outp;
			
			recipesShredder.add(recipe);
		}
		
		public void overridePreSetRecipe(ItemStack inp, ItemStack outp) {
			
			boolean flag = false;
			
			for(int i = 0; i < recipesShredder.size(); i++)
			{
				if(recipesShredder.get(i) != null && 
						recipesShredder.get(i).input != null && 
						recipesShredder.get(i).output != null && 
						inp != null && 
						outp != null && 
						recipesShredder.get(i).input.getItem() == inp.getItem() && 
						recipesShredder.get(i).input.getItemDamage() == inp.getItemDamage()) {
					recipesShredder.get(i).output = outp;
					flag = true;
				}
			}
			
			if(!flag) {
				ShredderRecipe rec = new ShredderRecipe();
				rec.input = inp;
				rec.output = outp;
				recipesShredder.add(rec);
			}
		}
		
		public void removeDuplicates() {
			List<ShredderRecipe> newList = new ArrayList<ShredderRecipe>();
			
			for(ShredderRecipe piv : recipesShredder)
			{
				boolean flag = false;
				
				if(newList.size() == 0)
				{
					newList.add(piv);
				} else {
					for(ShredderRecipe rec : newList) {
						if(piv != null && rec != null && piv.input != null && rec.input != null && rec.input.getItem() != null && piv.input.getItem() != null && rec.input.getItemDamage() == piv.input.getItemDamage() && rec.input.getItem() == piv.input.getItem())
							flag = true;
						if(piv == null || rec == null || piv.input == null || rec.input == null)
							flag = true;
					}
				}
				
				if(!flag)
				{
					newList.add(piv);
				}
			}
		}
		
		public void PrintRecipes() {
			/*for(int i = 0; i < recipes.size(); i++) {
				System.out.println("Recipe #" + i + ", " + recipes.get(i).input + " - " + recipes.get(i).output);
			}*/
			/*for(int i = 0; i < theWholeThing.size(); i++) {
			System.out.println(theWholeThing.get(i).item);
			}*/
			/*for(int i = 0; i < theWholeThing.size(); i++) {
				//for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					//System.out.println(theWholeThing.get(i).item + " | " + getShredderResult(theWholeThing.get(i).item));
				}
				
				
			}*/

			/*for (int j = 0; j < recipes.size(); j++) {
				if (recipes.get(j) != null && recipes.get(j).input != null && recipes.get(j).output != null &&
						recipes.get(j).input.getItem() != null && recipes.get(j).output.getItem() != null)
					System.out.println(recipes.get(j).input + " | " + recipes.get(j).output);
				else
					System.out.println(recipes.get(j));
			}*/

			MainRegistry.logger.debug("TWT: " + theWholeThing.size() + ", REC: " + recipesShredder.size());
		}
	}

	public static class DictCouple {
		
		public ItemStack item;
		public List<String> list;

		public DictCouple(ItemStack item, List<String> list) {
			this.item = item;
			this.list = list;
		}
		
		public static List<String> findWithStack(ItemStack stack) {
			for(DictCouple couple : theWholeThing) {
				if(couple.item == stack);
					return couple.list;
			}
			
			return null;
		}
	}
	
	public static ItemStack getCyclotronOutput(ItemStack part, ItemStack item) {

		if(part == null || item == null)
			return null;
		
		//LITHIUM
		if (part.getItem() == ModItems.part_lithium) {
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_coal)
				return new ItemStack(ModItems.fluorite, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_lead, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_quartz, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_schrabidium)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(item.getItem() == ModItems.powder_reiium)
				return new ItemStack(ModItems.powder_weidanium, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_copper, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_thorium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_cerium, 1);
		}
		
		//BERYLLIUM
		if (part.getItem() == ModItems.part_beryllium) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_copper, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_gold, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_astatine, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.niter, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.niter, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_neptunium, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_actinium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_weidanium)
				return new ItemStack(ModItems.powder_australium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_neodymium, 1);
		}
		
		//CARBON
		if (part.getItem() == ModItems.part_carbon) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustCoal"))
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_astatine, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_lead, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_thorium, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.fluorite, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_australium)
				return new ItemStack(ModItems.powder_verticium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_tungsten, 1);
		}
		
		//COPPER
		if (part.getItem() == ModItems.part_copper) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustCoal"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_niobium, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_niobium, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_actinium, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_astatine, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_verticium)
				return new ItemStack(ModItems.powder_unobtainium, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_astatine, 1);
		}
		
		//PLUTONIUM
		if (part.getItem() == ModItems.part_plutonium) {
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_unobtainium)
				return new ItemStack(ModItems.powder_daffergon, 1);
			if(ItemCell.isFullCell(item, ModForgeFluids.amat))
				return ItemCell.getFullCell(ModForgeFluids.aschrab);
		}

		return null;
	}
	
}
