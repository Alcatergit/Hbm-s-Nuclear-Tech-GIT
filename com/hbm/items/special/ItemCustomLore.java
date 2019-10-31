package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCustomLore extends Item {
	
	public ItemCustomLore(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {

		if(this == ModItems.ingot_neptunium)
		{
			if(MainRegistry.polaroidID == 11) {
				list.add("Woo, scary!");
			} else
				list.add("That one's my favourite!");
		}

		if(this == ModItems.pellet_rtg)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Contains ~100% Pu238 oxide.");
			else
				list.add("RTG fuel pellet for infinite energy! (almost)");
		}

		if(this == ModItems.pellet_rtg_weak)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Meh.");
			else
				list.add("Cheaper and weaker pellet, now with more U238!");
		}

		if(this == ModItems.rod_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Plutonium Rod");
		}
		
		if(this == ModItems.rod_u235)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Neptunium Rod");
		}
		
		if(this == ModItems.rod_u238)
		{
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}

		if(this == ModItems.rod_neptunium)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 238 Rod");
		}

		if(this == ModItems.rod_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}

		if(this == ModItems.rod_pu238)
		{
			list.add("Worth 5 operations in breeding reactor");
			list.add("Worth 25 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_pu239)
		{
			list.add("Worth 3 operations in breeding reactor");
			list.add("Worth 15 operations in nuclear powered furnace");
			list.add("Turns into Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_pu240)
		{
			list.add("Worth 1 operation in breeding reactor");
			list.add("Worth 5 operations in nuclear powered furnace");
			list.add("Turns into Lead Rod");
		}
		
		if(this == ModItems.rod_schrabidium)
		{
			list.add("Worth 15 operations in breeding reactor");
			list.add("Worth 75 operations in nuclear powered furnace");
			list.add("Turns into Solinium Rod");
		}
		
		if(this == ModItems.rod_solinium)
		{
			list.add("Worth 20 operations in breeding reactor");
			list.add("Worth 100 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_dual_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium Rod");
		}
		
		if(this == ModItems.rod_dual_u235)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Neptunium Rod");
		}
		
		if(this == ModItems.rod_dual_u238)
		{
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}

		if(this == ModItems.rod_dual_neptunium)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 238 Rod");
		}

		if(this == ModItems.rod_dual_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}

		if(this == ModItems.rod_dual_pu238)
		{
			list.add("Worth 10 operations in breeding reactor");
			list.add("Worth 50 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_dual_pu239)
		{
			list.add("Worth 6 operations in breeding reactor");
			list.add("Worth 30 operations in nuclear powered furnace");
			list.add("Turns into Dual Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_dual_pu240)
		{
			list.add("Worth 2 operations in breeding reactor");
			list.add("Worth 10 operations in nuclear powered furnace");
			list.add("Turns into Dual Lead Rod");
		}
		
		if(this == ModItems.rod_dual_schrabidium)
		{
			list.add("Worth 30 operations in breeding reactor");
			list.add("Worth 150 operations in nuclear powered furnace");
			list.add("Turns into Dual Solinium Rod");
		}
		
		if(this == ModItems.rod_dual_solinium)
		{
			list.add("Worth 40 operations in breeding reactor");
			list.add("Worth 200 operations in nuclear powered furnace");
		}

		if(this == ModItems.rod_quad_uranium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium Rod");
		}
		
		if(this == ModItems.rod_quad_u235)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Neptunium Rod");
		}
		
		if(this == ModItems.rod_quad_u238)
		{
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}

		if(this == ModItems.rod_quad_neptunium)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 238 Rod");
		}

		if(this == ModItems.rod_quad_plutonium)
		{
			list.add("Worth 0 operations in breeding reactor");
			list.add("Worth 0 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}

		if(this == ModItems.rod_quad_pu238)
		{
			list.add("Worth 20 operations in breeding reactor");
			list.add("Worth 100 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 239 Rod");
		}
		
		if(this == ModItems.rod_quad_pu239)
		{
			list.add("Worth 12 operations in breeding reactor");
			list.add("Worth 60 operations in nuclear powered furnace");
			list.add("Turns into Quad Plutonium 240 Rod");
		}
		
		if(this == ModItems.rod_quad_pu240)
		{
			list.add("Worth 4 operations in breeding reactor");
			list.add("Worth 20 operations in nuclear powered furnace");
			list.add("Turns into Quad Lead Rod");
		}
		
		if(this == ModItems.rod_quad_schrabidium)
		{
			list.add("Worth 60 operations in breeding reactor");
			list.add("Worth 300 operations in nuclear powered furnace");
			list.add("Turns into Quad Solinium Rod");
		}
		
		if(this == ModItems.rod_quad_solinium)
		{
			list.add("Worth 80 operations in breeding reactor");
			list.add("Worth 400 operations in nuclear powered furnace");
		}
		
		if(this == ModItems.rod_lithium)
		{
			list.add("Turns into Tritium Rod");
		}
		
		if(this == ModItems.rod_dual_lithium)
		{
			list.add("Turns into Dual Tritium Rod");
		}
		
		if(this == ModItems.rod_quad_lithium)
		{
			list.add("Turns into Quad Tritium Rod");
		}
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this == ModItems.plate_euphemium || 
    			this == ModItems.powder_neptunium)
    	{
    		return EnumRarity.EPIC;
    	}
    	
    	if(this == ModItems.rod_schrabidium || this == ModItems.rod_dual_schrabidium || 
    			this == ModItems.rod_quad_schrabidium || this == ModItems.ingot_schrabidium || 
    			this == ModItems.nugget_schrabidium || this == ModItems.plate_schrabidium || 
    			this == ModItems.cell_sas3 || this == ModItems.powder_schrabidium || 
    			this == ModItems.wire_schrabidium ||
    			this == ModItems.plate_saturnite)
    	{
    		return EnumRarity.RARE;
    	}
    	
    	if(this == ModItems.plate_paa)
    	{
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
