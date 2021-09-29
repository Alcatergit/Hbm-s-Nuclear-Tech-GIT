package com.hbm.handler;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ArmorUtil {

	public static boolean checkForFaraday(EntityPlayer player) {
		
		NonNullList<ItemStack> armor = player.inventory.armorInventory;
		
		if(armor.get(0).isEmpty() || armor.get(1).isEmpty() || armor.get(2).isEmpty() || armor.get(3).isEmpty()) return false;
		
		if(ArmorUtil.isFaradayArmor(armor.get(0)) &&
				ArmorUtil.isFaradayArmor(armor.get(1)) &&
				ArmorUtil.isFaradayArmor(armor.get(2)) &&
				ArmorUtil.isFaradayArmor(armor.get(3)))
			return true;
		
		return false;
	}

	public static boolean isFaradayArmor(ItemStack item) {
		
		String name = item.getUnlocalizedName();
		
		if(HazmatRegistry.getCladding(item) > 0)
			return true;
		
		for(String metal : metals) {
			
			if(name.toLowerCase().contains(metal))
				return true;
		}
		
		return false;
	}

	public static final String[] metals = new String[] {
			"chainmail",
			"iron",
			"silver",
			"gold",
			"platinum",
			"tin",
			"lead",
			"schrabidium",
			"euphemium",
			"steel",
			"cmb",
			"titanium",
			"alloy",
			"copper",
			"bronze",
			"electrum",
			"t45",
			"bj",
			"starmetal",
			"hazmat", //also count because rubber is insulating
			"rubber",
			"spacesuit"
	};

	public static void damageSuit(EntityPlayer player, int slot, int amount) {
	
		if(player.inventory.armorInventory.get(slot) == ItemStack.EMPTY)
			return;
	
		int j = player.inventory.armorInventory.get(slot).getItemDamage();
		player.inventory.armorInventory.get(slot).setItemDamage(j += amount);
	
		if(player.inventory.armorInventory.get(slot).getItemDamage() >= player.inventory.armorInventory.get(slot).getMaxDamage()) {
			System.out.println(player.inventory.armorInventory.get(slot).getMaxDamage());
			player.inventory.armorInventory.set(slot, ItemStack.EMPTY);
		}
	}

	public static boolean checkForFiend2(EntityPlayer player) {
		
		return ArmorUtil.checkArmorPiece(player, ModItems.jackt2, 2) && Library.checkForHeld(player, ModItems.shimmer_axe);
	}

	public static boolean checkForFiend(EntityPlayer player) {
		
		return ArmorUtil.checkArmorPiece(player, ModItems.jackt, 2) && Library.checkForHeld(player, ModItems.shimmer_sledge);
	}

	// Drillgon200: Is there a reason for this method? I don't know and I don't
	// care to find out.
	public static boolean checkForHaz2(EntityPlayer player) {
	
		if(checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) ||
				checkArmor(player, ModItems.liquidator_helmet, ModItems.liquidator_plate, ModItems.liquidator_legs, ModItems.liquidator_boots) || 
				checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
			return true;
		}
	
		return false;
	}

	public static boolean checkForHazmat(EntityPlayer player) {
		if(ArmorUtil.checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || ArmorUtil.checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) || ArmorUtil.checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) || ArmorUtil.checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || ArmorUtil.checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) || checkForHaz2(player)) {
	
			return true;
		}
	
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
	
		return false;
	}

	public static boolean checkForAsbestos(EntityPlayer player) {
	
		if(ArmorUtil.checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots)) {
			return true;
		}
	
		return false;
	}

	public static boolean checkArmor(EntityPlayer player, Item helm, Item chest, Item leg, Item shoe) {
		if(player.inventory.armorInventory.get(0).getItem() == shoe && player.inventory.armorInventory.get(1).getItem() == leg && player.inventory.armorInventory.get(2).getItem() == chest && player.inventory.armorInventory.get(3).getItem() == helm) {
			return true;
		}
	
		return false;
	}

	public static boolean checkArmorPiece(EntityPlayer player, Item armor, int slot) {
		if(player.inventory.armorInventory.get(slot) != null && player.inventory.armorInventory.get(slot).getItem() == armor) {
			return true;
		}
	
		return false;
	}

	public static boolean checkForGasMask(EntityPlayer player) {
	
		if(checkArmorPiece(player, ModItems.hazmat_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_red, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_grey, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_paa_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask_m65, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.schrabidium_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.euphemium_helmet, 3)) {
			return true;
		}
	
		if(player.isPotionActive(HbmPotion.mutation))
			return true;
	
		return false;
	}
	
	public static boolean checkForGoggles(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.goggles, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.ajr_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.bj_helmet, 3))
		{
			return true;
		}
		if(checkArmorPiece(player, ModItems.hev_helmet, 3))
		{
			return true;
		}
		
		return false;
	}
}