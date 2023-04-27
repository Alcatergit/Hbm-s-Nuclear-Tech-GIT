package com.hbm.handler;

import java.util.HashMap;
import java.util.Map;

import com.hbm.items.ModItems;
import com.hbm.items.armor.ItemModCladding;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HazmatRegistry {
	private static Map<Item, Double> entries = new HashMap<>();

	public static void registerHazmat(Item item, double resistance) {

		entries.put(item, resistance);
	}

	public static double getResistance(ItemStack stack) {

		if(stack == null)
			return 0;

		float cladding = getCladding(stack);

		Double d = entries.get(stack.getItem());

		if(d != null)
			return d + cladding;

		return cladding;
	}

	public static float getCladding(ItemStack stack) {

		if (stack.hasTagCompound() && stack.getTagCompound().getFloat("hfr_cladding") > 0.0F) {
			return stack.getTagCompound().getFloat("hfr_cladding");
		} else {
			if (ArmorModHandler.hasMods(stack)) {
				ItemStack[] mods = ArmorModHandler.pryMods(stack);
				ItemStack cladding = mods[5];
				if (cladding != null && cladding.getItem() instanceof ItemModCladding) {
					return (float)((ItemModCladding)cladding.getItem()).rad;
				}
			}

			return 0.0F;
		}
	}

	public static float getResistance(EntityLivingBase player) {
		float res = 0.0F;

		if (player.getUniqueID().toString().equals(Library.HbMinecraft) || player.getUniqueID().toString().equals(Library.Drillgon) || player.getUniqueID().toString().equals(Library.Alcater)) {
			res += 1.0F;
		}

		for(ItemStack stack : player.getArmorInventoryList()) {
			if(!stack.isEmpty()) {
				res += getResistance(stack);
			}
		}
		PotionEffect radx = player.getActivePotionEffect(HbmPotion.radx);
		if(radx != null)
			res += 0.1F * radx.getAmplifier();

		return res;

	}

	public static void registerHazmats() {
		//assuming coefficient of 10
		//real coefficient turned out to be 5
		//oops

		double helmet = 0.2D;
		double chest = 0.4D;
		double legs = 0.3D;
		double boots = 0.1D;

		double iron = 0.0225D; // 5%
		double gold = 0.0225D; // 5%
		double steel = 0.045D; // 10%
		double titanium = 0.045D; // 10%
		double alloy = 0.07D; // 15%
		double cobalt = 0.125D; // 25%

		double hazYellow = 0.6D; // 75%
		double hazRed = 1.0D; // 90%
		double hazGray = 2D; // 99%
		double liquidator = 2.4D; // 99.6%
		double paa = 3.0D; // 99.9%
		

		double t45 = 1D; // 90%
		double ajr = 1.3D; // 95%
		double hev = 1.6D; // 97.5%
		double bj = 1D; // 90%
		double rpa = 2D; // 99%
		double fau = 4D; // 99.99%
		double dns = 6D; // 99.9999%
		double security = 0.01D; // 2.3%
		double star = 0.25D; // 44%
		double cmb = 0.5D; // 68%
		double schrab = 1D; // 90.0%
		double euph = 10D; // 99.99999999%

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet, hazYellow * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate, hazYellow * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs, hazYellow * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots, hazYellow * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_red, hazRed * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_red, hazRed * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_red, hazRed * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_red, hazRed * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_grey, hazGray * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_grey, hazGray * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_grey, hazGray * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_grey, hazGray * boots);

		HazmatRegistry.registerHazmat(ModItems.liquidator_helmet, liquidator * helmet);
		HazmatRegistry.registerHazmat(ModItems.liquidator_plate, liquidator * chest);
		HazmatRegistry.registerHazmat(ModItems.liquidator_legs, liquidator * legs);
		HazmatRegistry.registerHazmat(ModItems.liquidator_boots, liquidator * boots);

		HazmatRegistry.registerHazmat(ModItems.t45_helmet, t45 * helmet);
		HazmatRegistry.registerHazmat(ModItems.t45_plate, t45 * chest);
		HazmatRegistry.registerHazmat(ModItems.t45_legs, t45 * legs);
		HazmatRegistry.registerHazmat(ModItems.t45_boots, t45 * boots);

		HazmatRegistry.registerHazmat(ModItems.ajr_helmet, ajr * helmet);
		HazmatRegistry.registerHazmat(ModItems.ajr_plate, ajr * chest);
		HazmatRegistry.registerHazmat(ModItems.ajr_legs, ajr * legs);
		HazmatRegistry.registerHazmat(ModItems.ajr_boots, ajr * boots);

		HazmatRegistry.registerHazmat(ModItems.ajro_helmet, ajr * helmet);
		HazmatRegistry.registerHazmat(ModItems.ajro_plate, ajr * chest);
		HazmatRegistry.registerHazmat(ModItems.ajro_legs, ajr * legs);
		HazmatRegistry.registerHazmat(ModItems.ajro_boots, ajr * boots);

		HazmatRegistry.registerHazmat(ModItems.rpa_helmet, rpa * helmet);
		HazmatRegistry.registerHazmat(ModItems.rpa_plate, rpa * chest);
		HazmatRegistry.registerHazmat(ModItems.rpa_legs, rpa * legs);
		HazmatRegistry.registerHazmat(ModItems.rpa_boots, rpa * boots);

		HazmatRegistry.registerHazmat(ModItems.bj_helmet, bj * helmet);
		HazmatRegistry.registerHazmat(ModItems.bj_plate, bj * chest);
		HazmatRegistry.registerHazmat(ModItems.bj_plate_jetpack, bj * chest);
		HazmatRegistry.registerHazmat(ModItems.bj_legs, bj * legs);
		HazmatRegistry.registerHazmat(ModItems.bj_boots, bj * boots);

		HazmatRegistry.registerHazmat(ModItems.hev_helmet, hev * helmet);
		HazmatRegistry.registerHazmat(ModItems.hev_plate, hev * chest);
		HazmatRegistry.registerHazmat(ModItems.hev_legs, hev * legs);
		HazmatRegistry.registerHazmat(ModItems.hev_boots, hev * boots);
		
		HazmatRegistry.registerHazmat(ModItems.fau_helmet, fau * helmet);
		HazmatRegistry.registerHazmat(ModItems.fau_plate, fau * chest);
		HazmatRegistry.registerHazmat(ModItems.fau_legs, fau * legs);
		HazmatRegistry.registerHazmat(ModItems.fau_boots, fau * boots);

		HazmatRegistry.registerHazmat(ModItems.dns_helmet, dns * helmet);
		HazmatRegistry.registerHazmat(ModItems.dns_plate, dns * chest);
		HazmatRegistry.registerHazmat(ModItems.dns_legs, dns * legs);
		HazmatRegistry.registerHazmat(ModItems.dns_boots, dns * boots);

		HazmatRegistry.registerHazmat(ModItems.paa_helmet, paa * helmet);
		HazmatRegistry.registerHazmat(ModItems.paa_plate, paa * chest);
		HazmatRegistry.registerHazmat(ModItems.paa_legs, paa * legs);
		HazmatRegistry.registerHazmat(ModItems.paa_boots, paa * boots);

		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_helmet, paa * helmet);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_plate, paa * chest);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_legs, paa * legs);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_boots, paa * boots);

		HazmatRegistry.registerHazmat(ModItems.security_helmet, security * helmet);
		HazmatRegistry.registerHazmat(ModItems.security_plate, security * chest);
		HazmatRegistry.registerHazmat(ModItems.security_legs, security * legs);
		HazmatRegistry.registerHazmat(ModItems.security_boots, security * boots);

		HazmatRegistry.registerHazmat(ModItems.starmetal_helmet, star * helmet);
		HazmatRegistry.registerHazmat(ModItems.starmetal_plate, star * chest);
		HazmatRegistry.registerHazmat(ModItems.starmetal_legs, star * legs);
		HazmatRegistry.registerHazmat(ModItems.starmetal_boots, star * boots);

		HazmatRegistry.registerHazmat(ModItems.jackt, 0.1);
		HazmatRegistry.registerHazmat(ModItems.jackt2, 0.1);

		HazmatRegistry.registerHazmat(ModItems.gas_mask, 0.07);
		HazmatRegistry.registerHazmat(ModItems.gas_mask_m65, 0.095);

		HazmatRegistry.registerHazmat(ModItems.steel_helmet, steel * helmet);
		HazmatRegistry.registerHazmat(ModItems.steel_plate, steel * chest);
		HazmatRegistry.registerHazmat(ModItems.steel_legs, steel * legs);
		HazmatRegistry.registerHazmat(ModItems.steel_boots, steel * boots);

		HazmatRegistry.registerHazmat(ModItems.titanium_helmet, titanium * helmet);
		HazmatRegistry.registerHazmat(ModItems.titanium_plate, titanium * chest);
		HazmatRegistry.registerHazmat(ModItems.titanium_legs, titanium * legs);
		HazmatRegistry.registerHazmat(ModItems.titanium_boots, titanium * boots);

		HazmatRegistry.registerHazmat(ModItems.cobalt_helmet, cobalt * helmet);
		HazmatRegistry.registerHazmat(ModItems.cobalt_plate, cobalt * chest);
		HazmatRegistry.registerHazmat(ModItems.cobalt_legs, cobalt * legs);
		HazmatRegistry.registerHazmat(ModItems.cobalt_boots, cobalt * boots);

		HazmatRegistry.registerHazmat(Items.IRON_HELMET, iron * helmet);
		HazmatRegistry.registerHazmat(Items.IRON_CHESTPLATE, iron * chest);
		HazmatRegistry.registerHazmat(Items.IRON_LEGGINGS, iron * legs);
		HazmatRegistry.registerHazmat(Items.IRON_BOOTS, iron * boots);

		HazmatRegistry.registerHazmat(Items.GOLDEN_HELMET, gold * helmet);
		HazmatRegistry.registerHazmat(Items.GOLDEN_CHESTPLATE, gold * chest);
		HazmatRegistry.registerHazmat(Items.GOLDEN_LEGGINGS, gold * legs);
		HazmatRegistry.registerHazmat(Items.GOLDEN_BOOTS, gold * boots);

		HazmatRegistry.registerHazmat(ModItems.alloy_helmet, alloy * helmet);
		HazmatRegistry.registerHazmat(ModItems.alloy_plate, alloy * chest);
		HazmatRegistry.registerHazmat(ModItems.alloy_legs, alloy * legs);
		HazmatRegistry.registerHazmat(ModItems.alloy_boots, alloy * boots);

		HazmatRegistry.registerHazmat(ModItems.cmb_helmet, cmb * helmet);
		HazmatRegistry.registerHazmat(ModItems.cmb_plate, cmb * chest);
		HazmatRegistry.registerHazmat(ModItems.cmb_legs, cmb * legs);
		HazmatRegistry.registerHazmat(ModItems.cmb_boots, cmb * boots);

		HazmatRegistry.registerHazmat(ModItems.schrabidium_helmet, schrab * helmet);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_plate, schrab * chest);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_legs, schrab * legs);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_boots, schrab * boots);

		HazmatRegistry.registerHazmat(ModItems.euphemium_helmet, euph * helmet);
		HazmatRegistry.registerHazmat(ModItems.euphemium_plate, euph * chest);
		HazmatRegistry.registerHazmat(ModItems.euphemium_legs, euph * legs);
		HazmatRegistry.registerHazmat(ModItems.euphemium_boots, euph * boots);
	}
}
