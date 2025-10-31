package com.hbm.items.armor;

import com.hbm.items.gear.ArmorModel;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ArmorHat extends ArmorModel {

	public ArmorHat(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn, s);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		entityItem.setDead();
		return true;
	}

}
