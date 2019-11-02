package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes {
	public static List<Item> stamps_flat = new ArrayList<Item>() {/**
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
	}};
	
	public static List<Item> stamps_plate = new ArrayList<Item>() {/**
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
	}};
	
	public static List<Item> stamps_wire = new ArrayList<Item>() {/**
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
	}};
	
	public static List<Item> stamps_circuit = new ArrayList<Item>() {/**
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
	}};
	
	public static ItemStack getPressResult(ItemStack input, ItemStack stamp) {
		//TODO Finish press results
		if(input == null || stamp == null)
			return null;
		
		if(stamps_flat.contains(stamp.getItem())) {

			if(mODE(input, "dustCoal"))
				return new ItemStack(Items.COAL);
			if(mODE(input, "dustQuartz"))
				return new ItemStack(Items.QUARTZ);
			if(mODE(input, "dustNetherQuartz"))
				return new ItemStack(Items.QUARTZ);
			if(mODE(input, "dustLapis"))
				return new ItemStack(Items.DYE, 1, 4);
			if(mODE(input, "dustDiamond"))
				return new ItemStack(Items.DIAMOND);
			if(mODE(input, "dustEmerald"))
				return new ItemStack(Items.EMERALD);
			if(input.getItem() == ModItems.pellet_coal)
				return new ItemStack(Items.DIAMOND);
			if(input.getItem() == ModItems.biomass)
				return new ItemStack(ModItems.biomass_compressed);
			if(input.getItem() == ModItems.powder_lignite)
				return new ItemStack(ModItems.briquette_lignite);
		}
		
		if(stamps_plate.contains(stamp.getItem())) {

			if(mODE(input, "ingotIron"))
				return new ItemStack(ModItems.plate_iron);
			/*if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.plate_gold);
			if(mODE(input, "ingotTitanium"))
				return new ItemStack(ModItems.plate_titanium);
			if(mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.plate_aluminium);
			if(mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.plate_steel);
			if(mODE(input, "ingotLead"))
				return new ItemStack(ModItems.plate_lead);
			if(mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.plate_copper);
			if(mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if(mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if(mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.plate_schrabidium);
			if(mODE(input, "ingotCMBSteel"))
				return new ItemStack(ModItems.plate_combine_steel);
			if(mODE(input, "ingotSaturnite"))
				return new ItemStack(ModItems.plate_saturnite);
			
		}
		
		if(stamps_wire.contains(stamp.getItem())) {

			if(mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.wire_aluminium, 8);
			if(mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.wire_copper, 8);
			if(mODE(input, "ingotTungsten"))
				return new ItemStack(ModItems.wire_tungsten, 8);
			if(mODE(input, "ingotRedAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if(mODE(input, "ingotRedstoneAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.wire_gold, 8);
			if(mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.wire_schrabidium, 8);
			if(mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if(mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if(mODE(input, "ingotMagnetizedTungsten"))
				return new ItemStack(ModItems.wire_magnetized_tungsten, 8);
		}
		
		if(stamps_circuit.contains(stamp.getItem())) {

			if(input.getItem() == ModItems.circuit_raw)
				return new ItemStack(ModItems.circuit_aluminium);
		}
		
		if(stamp.getItem() == ModItems.stamp_357) {

			if(input.getItem() == ModItems.assembly_iron)
				return new ItemStack(ModItems.gun_revolver_iron_ammo);
			if(input.getItem() == ModItems.assembly_steel)
				return new ItemStack(ModItems.gun_revolver_ammo);
			if(input.getItem() == ModItems.assembly_lead)
				return new ItemStack(ModItems.gun_revolver_lead_ammo);
			if(input.getItem() == ModItems.assembly_gold)
				return new ItemStack(ModItems.gun_revolver_gold_ammo);
			if(input.getItem() == ModItems.assembly_schrabidium)
				return new ItemStack(ModItems.gun_revolver_schrabidium_ammo);
			if(input.getItem() == ModItems.assembly_nightmare)
				return new ItemStack(ModItems.gun_revolver_nightmare_ammo);
			if(input.getItem() == ModItems.assembly_desh)
				return new ItemStack(ModItems.ammo_357_desh);

			if(mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.gun_revolver_cursed_ammo);
		}
		
		if(stamp.getItem() == ModItems.stamp_44) {

			if(input.getItem() == ModItems.assembly_nopip)
				return new ItemStack(ModItems.ammo_44);
		}
		
		if(stamp.getItem() == ModItems.stamp_9) {

			if(input.getItem() == ModItems.assembly_smg)
				return new ItemStack(ModItems.ammo_9mm);
			if(input.getItem() == ModItems.assembly_uzi)
				return new ItemStack(ModItems.ammo_22lr);
			if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.gun_mp_ammo);
			if(input.getItem() == ModItems.assembly_lacunae)
				return new ItemStack(ModItems.ammo_5mm);
		}
		
		if(stamp.getItem() == ModItems.stamp_50) {

			if(input.getItem() == ModItems.assembly_calamity)
				return new ItemStack(ModItems.ammo_50bmg);
			if(input.getItem() == ModItems.assembly_actionexpress)
				return new ItemStack(ModItems.ammo_50ae);
		} */
		}
		return null;
	}
	
	public static ItemStack getFurnaceProcessingResult(ItemStack item, ItemStack item2) {
		return getFurnaceOutput(item, item2);
	}

	public static ItemStack getFurnaceOutput(ItemStack item, ItemStack item2) {
		
		if(item == null || item2 == null)
			return null;
		//TODO all the other items in these recipes.
	/*	if (MainRegistry.enableDebugMode) {
			if (item.getItem() == Items.iron_ingot && item2.getItem() == Items.quartz
					|| item.getItem() == Items.quartz && item2.getItem() == Items.iron_ingot) {
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

		if (mODE(item, new String[] {"ingotCopper", "dustCopper"}) && item2.getItem() == Items.redstone
				|| item.getItem() == Items.redstone && mODE(item2, new String[] {"ingotCopper", "dustCopper"})) {
			return new ItemStack(ModItems.ingot_red_copper, 2);
		}

		if (item.getItem() == ModItems.canister_fuel && item2.getItem() == Items.slime_ball
				|| item.getItem() == Items.slime_ball && item2.getItem() == ModItems.canister_fuel) {
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
*/
		if (item.getItem() == ModItems.plate_mixed && mODE(item2, "plateGold")
				|| mODE(item, "plateGold") && item2.getItem() == ModItems.plate_mixed) {
			return new ItemStack(ModItems.plate_paa, 2);
		}

	/*	if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})
				|| mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && item2.getItem() == ModItems.powder_cobalt
				|| item.getItem() == ModItems.powder_cobalt && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotDuraSteel", "dustDuraSteel"}) && item2.getItem() == ModItems.powder_meteorite
				|| item.getItem() == ModItems.powder_meteorite && mODE(item2, new String[] {"ingotDuraSteel", "dustDuraSteel"})) {
			return new ItemStack(ModItems.ingot_starmetal, 2);
		}

		if (mODE(item, new String[] {"dustCobalt"}) && item2.getItem() == ModItems.ingot_starmetal
				|| item.getItem() == ModItems.ingot_starmetal && mODE(item2, new String[] {"dustCobalt"})) {
			return new ItemStack(ModItems.ingot_saturnite, 2);
		}*/

		return null;
	}
	
	//Matches Ore Dict Entry
	public static boolean mODE(ItemStack stack, String name) {
		if(stack.isEmpty())
			return false;
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 0 && s.equals(name))
				return true;
		}
		
		return false;
	}
	
	public static boolean mODE(Item item, String[] names) {
		return mODE(new ItemStack(item), names);
	}
	
	public static boolean mODE(ItemStack item, String[] names) {
		boolean flag = false;
		if(names.length > 0) {
			for(int i = 0; i < names.length; i++) {
				if(mODE(item, names[i]))
					flag = true;
			}
		}
		
		return flag;
	}

	public static ItemStack getOutputFromTempate(ItemStack stack) {
		//TODO assembler recipes
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return ItemStack.EMPTY;
		EnumAssemblyTemplate template = ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage());
		ItemStack output = ItemStack.EMPTY;
		if(template.getOutput() != null){
			return template.getOutput().copy();
		}
		switch(template){
		case IRON_PLATE:
			output = new ItemStack(ModItems.plate_iron, 2);
			break;
		}
		return output;
	}

	public static List<ItemStack> getRecipeFromTempate(ItemStack stack) {
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		EnumAssemblyTemplate template = ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage());
		if(template.getIngredients() != null){
			return copyItemStackList(template.getIngredients());
		}
		switch(template){
		case IRON_PLATE:
			list.add(new ItemStack(Items.IRON_INGOT, 3));
			break;
		}
		
		if(list.isEmpty()){
			return null;
		} else {
			return list;
		}
	}
	public static List<ItemStack> copyItemStackList(List<ItemStack> list){
		List<ItemStack> newList = new ArrayList<ItemStack>();
		if(list == null || list.isEmpty())
			return newList;
		for(ItemStack stack : list){
			newList.add(stack.copy());
		}
		return newList;
	}
}
