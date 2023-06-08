package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemFluidCanister;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
//Alcater: on it
@Spaghetti("everything")
public class MachineRecipes {

	/**
	 * HashMap of fluids suitable for burning in fluid burner, where key is fluid and value is TU per bucket.
	 */
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
			add(ModItems.stamp_desh_flat);
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
			add(ModItems.stamp_desh_plate);
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
			add(ModItems.stamp_desh_wire);
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
			add(ModItems.stamp_desh_circuit);
		}
	};
	
	public static ItemStack getPressResult(ItemStack input, ItemStack stamp) {
		if (input == null || stamp == null)
			return null;

		if (stamps_flat.contains(stamp.getItem())) {

			if (mODE(input, COAL.dust()))
				return new ItemStack(Items.COAL);
			if (mODE(input, "dustQuartz"))
				return new ItemStack(Items.QUARTZ);
			if (mODE(input, NETHERQUARTZ.dust()))
				return new ItemStack(Items.QUARTZ);
			if (mODE(input, LAPIS.dust()))
				return new ItemStack(Items.DYE, 1, 4);
			if (mODE(input, DIAMOND.dust()))
				return new ItemStack(Items.DIAMOND);
			if (mODE(input, EMERALD.dust()))
				return new ItemStack(Items.EMERALD);
			if (input.getItem() == ModItems.pellet_coal)
				return new ItemStack(Items.DIAMOND);
			if (input.getItem() == ModItems.biomass)
				return new ItemStack(ModItems.biomass_compressed);
			if (input.getItem() == ModItems.powder_lignite)
				return new ItemStack(ModItems.briquette_lignite);
			if(input.getItem() == ModItems.meteorite_sword_reforged)
				return new ItemStack(ModItems.meteorite_sword_hardened);
			if(mODE(input, "fuelCoke"))
				return new ItemStack(ModItems.ingot_graphite);
		}

		if (stamps_plate.contains(stamp.getItem())) {

			if (mODE(input, IRON.ingot()))
				return new ItemStack(ModItems.plate_iron);
			if (mODE(input, GOLD.ingot()))
				return new ItemStack(ModItems.plate_gold);
			if (mODE(input, TI.ingot()))
				return new ItemStack(ModItems.plate_titanium);
			if (mODE(input, AL.ingot()))
				return new ItemStack(ModItems.plate_aluminium);
			if (mODE(input, STEEL.ingot()))
				return new ItemStack(ModItems.plate_steel);
			if (mODE(input, PB.ingot()))
				return new ItemStack(ModItems.plate_lead);
			if (mODE(input, CU.ingot()))
				return new ItemStack(ModItems.plate_copper);
			if (mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if (mODE(input, ALLOY.ingot()))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if (mODE(input, SA326.ingot()))
				return new ItemStack(ModItems.plate_schrabidium);
			if (mODE(input, CMB.ingot()))
				return new ItemStack(ModItems.plate_combine_steel);
			if (mODE(input, BIGMT.ingot()))
				return new ItemStack(ModItems.plate_saturnite);

		}

		if (stamps_wire.contains(stamp.getItem())) {

			if (mODE(input, AL.ingot()))
				return new ItemStack(ModItems.wire_aluminium, 8);
			if (mODE(input, CU.ingot()))
				return new ItemStack(ModItems.wire_copper, 8);
			if (mODE(input, W.ingot()))
				return new ItemStack(ModItems.wire_tungsten, 8);
			if (mODE(input, MINGRADE.ingot()))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if (mODE(input, GOLD.ingot()))
				return new ItemStack(ModItems.wire_gold, 8);
			if (mODE(input, SA326.ingot()))
				return new ItemStack(ModItems.wire_schrabidium, 8);
			if (mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if (mODE(input, ALLOY.ingot()))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if (mODE(input, MAGTUNG.ingot()))
				return new ItemStack(ModItems.wire_magnetized_tungsten, 8);
		}

		if (stamps_circuit.contains(stamp.getItem())) {

			if (input.getItem() == ModItems.circuit_raw)
				return new ItemStack(ModItems.circuit_aluminium);
			if(input.getItem() == ModItems.circuit_bismuth_raw)
				return new ItemStack(ModItems.circuit_bismuth);
			if(input.getItem() == ModItems.circuit_arsenic_raw)
				return new ItemStack(ModItems.circuit_arsenic);
			if(input.getItem() == ModItems.circuit_tantalium_raw)
				return new ItemStack(ModItems.circuit_tantalium);
		}

		if (stamp.getItem() == ModItems.stamp_357 || stamp.getItem() == ModItems.stamp_desh_357) {

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

			if (mODE(input, STEEL.ingot()))
				return new ItemStack(ModItems.gun_revolver_cursed_ammo);
		}

		if (stamp.getItem() == ModItems.stamp_44 || stamp.getItem() == ModItems.stamp_desh_44) {

			if (input.getItem() == ModItems.assembly_nopip)
				return new ItemStack(ModItems.ammo_44);
		}

		if (stamp.getItem() == ModItems.stamp_9 || stamp.getItem() == ModItems.stamp_desh_9) {

			if (input.getItem() == ModItems.assembly_smg)
				return new ItemStack(ModItems.ammo_9mm);
			if (input.getItem() == ModItems.assembly_uzi)
				return new ItemStack(ModItems.ammo_22lr);
			if (mODE(input, GOLD.ingot()))
				return new ItemStack(ModItems.ammo_566_gold);
			if (input.getItem() == ModItems.assembly_lacunae)
				return new ItemStack(ModItems.ammo_5mm);
			if(input.getItem() == ModItems.assembly_556)
				return new ItemStack(ModItems.ammo_556);
		}

		if (stamp.getItem() == ModItems.stamp_50 || stamp.getItem() == ModItems.stamp_desh_50) {

			if (input.getItem() == ModItems.assembly_calamity)
				return new ItemStack(ModItems.ammo_50bmg);
			if (input.getItem() == ModItems.assembly_actionexpress)
				return new ItemStack(ModItems.ammo_50ae);
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

	public static List<ItemStack> copyItemStackList(List<ItemStack> list) {
		List<ItemStack> newList = new ArrayList<ItemStack>();
		if (list == null || list.isEmpty())
			return newList;
		for (ItemStack stack : list) {
			newList.add(stack.copy());
		}
		return newList;
	}

	// return: Fluid, amount produced, amount required, HE produced
	public static Object[] getTurbineOutput(Fluid type) {

		if (type == ModForgeFluids.steam) {
			return new Object[] { ModForgeFluids.spentsteam, 5, 500, 50 };
		} else if (type == ModForgeFluids.hotsteam) {
			return new Object[] { ModForgeFluids.steam, 50, 5, 100 };
		} else if (type == ModForgeFluids.superhotsteam) {
			return new Object[] { ModForgeFluids.hotsteam, 50, 5, 150 };
		} else if(type == ModForgeFluids.ultrahotsteam){
			return new Object[] { ModForgeFluids.superhotsteam, 50, 5, 250 };
		}

		return null;
	}

	public static List<GasCentOutput> getGasCentOutput(Fluid fluid) {
		
		List<GasCentOutput> list = new ArrayList<GasCentOutput>();
		if(fluid == null){
			return null;
		} else if(fluid == ModForgeFluids.uf6){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_u235), 1));
			list.add(new GasCentOutput(19, new ItemStack(ModItems.nugget_u238), 2));
			list.add(new GasCentOutput(7, new ItemStack(ModItems.nugget_uranium_fuel), 3));
			list.add(new GasCentOutput(9, new ItemStack(ModItems.fluorite), 4));
			return list;
		} else if(fluid == ModForgeFluids.puf6){
			list.add(new GasCentOutput(3, new ItemStack(ModItems.nugget_pu238), 1));
			list.add(new GasCentOutput(2, new ItemStack(ModItems.nugget_pu239), 2));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_pu240), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.fluorite), 4));
			return list;
		} else if(fluid == ModForgeFluids.watz){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_solinium), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_uranium), 2));
			list.add(new GasCentOutput(5, new ItemStack(ModItems.powder_lead), 3));
			list.add(new GasCentOutput(10, new ItemStack(ModItems.dust), 4));
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
		} else if(fluid == ModForgeFluids.liquid_osmiridium){
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_impure_osmiridium), 1));
			list.add(new GasCentOutput(2, new ItemStack(ModItems.powder_meteorite), 2));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.powder_meteorite_tiny), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_paleogenite_tiny), 4));
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
			return 1000;
		else if(fluid == ModForgeFluids.sas3)
			return 100;
		else if(fluid == ModForgeFluids.coolant)
			return 2000;
		else if(fluid == ModForgeFluids.cryogel)
			return 1000;
		else if(fluid == ModForgeFluids.nitan)
			return 500;
		else if(fluid == ModForgeFluids.liquid_osmiridium)
			return 2000;
		else
			return 0;
	}
}
