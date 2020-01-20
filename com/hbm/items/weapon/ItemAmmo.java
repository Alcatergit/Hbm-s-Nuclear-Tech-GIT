package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemAmmo extends Item {

	public ItemAmmo(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		//TODO uncomment this when done with bullets
		//12 GAUGE
				/*if(this == ModItems.ammo_12gauge_incendiary) {
					list.add(TextFormatting.BLUE + "+ Incendiary");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_20gauge_slug) {
					list.add(TextFormatting.BLUE + "+ Near-perfect accuracy");
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.BLUE + "+ Decreased wear");
					list.add(TextFormatting.RED + "- Single projectile");
				}
				
				//20 GAUGE
				if(this == ModItems.ammo_20gauge_flechette) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.YELLOW + "* Less bouncy");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_20gauge_incendiary) {
					list.add(TextFormatting.BLUE + "+ Incendiary");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_20gauge_explosive) {
					list.add(TextFormatting.BLUE + "+ Explosive");
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_20gauge_caustic) {
					list.add(TextFormatting.BLUE + "+ Toxic");
					list.add(TextFormatting.BLUE + "+ Caustic");
					list.add(TextFormatting.YELLOW + "* Not bouncy");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_20gauge_shock) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.BLUE + "+ Stunning");
					list.add(TextFormatting.BLUE + "+ EMP");
					list.add(TextFormatting.YELLOW + "* Not bouncy");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_20gauge_wither) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.BLUE + "+ Withering");
				}*/
				
				//.357 MAGNUM
				if(this == ModItems.ammo_357_desh) {
					list.add(TextFormatting.BLUE + "+ Fits every .357 model");
					list.add(TextFormatting.BLUE + "+ Above-average damage");
				}

				//.44 MAGNUM
				/*if(this == ModItems.ammo_44_ap) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_44_du) {
					list.add(TextFormatting.BLUE + "+ Highly increased damage");
					list.add(TextFormatting.YELLOW + "* Heavy metal");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_44_pip) {
					list.add(TextFormatting.BLUE + "+ Boxcar");
					list.add(TextFormatting.RED + "- Highly decreased damage");
				}
				if(this == ModItems.ammo_44_bj) {
					list.add(TextFormatting.BLUE + "+ Boat");
					list.add(TextFormatting.RED + "- Highly decreased damage");
				}
				
				//5mm
				if(this == ModItems.ammo_5mm_explosive) {
					list.add(TextFormatting.BLUE + "+ Explosive");
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_5mm_du) {
					list.add(TextFormatting.BLUE + "+ Highly increased damage");
					list.add(TextFormatting.YELLOW + "* Heavy metal");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				
				//9mm
				if(this == ModItems.ammo_9mm_ap) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_9mm_du) {
					list.add(TextFormatting.BLUE + "+ Highly increased damage");
					list.add(TextFormatting.YELLOW + "* Heavy metal");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				
				//.22LR
				if(this == ModItems.ammo_22lr_ap) {
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				
				//.50 BMG
				if(this == ModItems.ammo_50bmg_incendiary) {
					list.add(TextFormatting.BLUE + "+ Incendiary");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_50bmg_explosive) {
					list.add(TextFormatting.BLUE + "+ Explosive");
					list.add(TextFormatting.BLUE + "+ Increased damage");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				if(this == ModItems.ammo_50bmg_du) {
					list.add(TextFormatting.BLUE + "+ Highly increased damage");
					list.add(TextFormatting.YELLOW + "* Heavy metal");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				
				//84mm ROCKETS
				if(this == ModItems.ammo_rocket_he) {
					list.add(TextFormatting.BLUE + "+ Increased blast radius");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_rocket_incendiary) {
					list.add(TextFormatting.BLUE + "+ Incendiary explosion");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_rocket_shrapnel) {
					list.add(TextFormatting.BLUE + "+ Shrapnel");
				}
				if(this == ModItems.ammo_rocket_emp) {
					list.add(TextFormatting.BLUE + "+ EMP");
					list.add(TextFormatting.RED + "- Decreased blast radius");
				}
				if(this == ModItems.ammo_rocket_glare) {
					list.add(TextFormatting.BLUE + "+ Increased projectile speed");
					list.add(TextFormatting.BLUE + "+ Incendiary explosion");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_rocket_toxic) {
					list.add(TextFormatting.BLUE + "+ Chlorine gas");
					list.add(TextFormatting.RED + "- No explosion");
					list.add(TextFormatting.RED + "- Decreased projectile speed");
				}
				if(this == ModItems.ammo_rocket_sleek) {
					list.add(TextFormatting.BLUE + "+ Highly increased blast radius");
					list.add(TextFormatting.BLUE + "+ Not affected by gravity");
					list.add(TextFormatting.YELLOW + "* Jolt");
				}
				if(this == ModItems.ammo_rocket_nuclear) {
					list.add(TextFormatting.BLUE + "+ Nuclear");
					list.add(TextFormatting.RED + "- Very highly increased wear");
					list.add(TextFormatting.RED + "- Decreased projectile speed");
				}
				if(this == ModItems.ammo_rocket_rpc) {
					list.add(TextFormatting.BLUE + "+ Chainsaw");
					list.add(TextFormatting.BLUE + "+ Penetrating");
					list.add(TextFormatting.BLUE + "+ Not affected by gravity");
					list.add(TextFormatting.RED + "- Increased wear");
					list.add(TextFormatting.RED + "- Non-explosive");
					list.add(TextFormatting.YELLOW + "* Uhhhh");
				}
				
				//40mm GRENADES
				if(this == ModItems.ammo_grenade_he) {
					list.add(TextFormatting.BLUE + "+ Increased blast radius");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_grenade_incendiary) {
					list.add(TextFormatting.BLUE + "+ Incendiary explosion");
					list.add(TextFormatting.RED + "- Increased wear");
				}
				if(this == ModItems.ammo_grenade_toxic) {
					list.add(TextFormatting.BLUE + "+ Chlorine gas");
					list.add(TextFormatting.RED + "- No explosion");
				}
				if(this == ModItems.ammo_grenade_concussion) {
					list.add(TextFormatting.BLUE + "+ Increased blast radius");
					list.add(TextFormatting.RED + "- No block damage");
				}
				if(this == ModItems.ammo_grenade_finned) {
					list.add(TextFormatting.BLUE + "+ Decreased gravity");
					list.add(TextFormatting.RED + "- Decreased blast radius");
				}
				if(this == ModItems.ammo_grenade_sleek) {
					list.add(TextFormatting.BLUE + "+ Increased blast radius");
					list.add(TextFormatting.YELLOW + "* Jolt");
				}
				if(this == ModItems.ammo_grenade_nuclear) {
					list.add(TextFormatting.BLUE + "+ Nuclear");
					list.add(TextFormatting.BLUE + "+ Increased range");
					list.add(TextFormatting.RED + "- Highly increased wear");
				}
				
				//FOLLY
				if(this == ModItems.ammo_folly) {
					list.add(TextFormatting.BLUE + "+ Focused starmetal reaction blast");
				}
				if(this == ModItems.ammo_folly_nuclear) {
					list.add(TextFormatting.BLUE + "+ Howitzer mini nuke shell");
				}
				if(this == ModItems.ammo_folly_du) {
					list.add(TextFormatting.BLUE + "+ Howitzer 17kg U238 shell");
				}*/
	}
}
