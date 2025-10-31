package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.items.armor.ArmorFSBPowered;
import com.hbm.render.model.ModelT45Boots;
import com.hbm.render.model.ModelT45Chest;
import com.hbm.render.model.ModelT45Helmet;
import com.hbm.render.model.ModelT45Legs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorT45 extends ArmorFSBPowered {

	@SideOnly(Side.CLIENT)
	private ModelT45Helmet helmet;
	@SideOnly(Side.CLIENT)
	private ModelT45Chest plate;
	@SideOnly(Side.CLIENT)
	private ModelT45Legs legs;
	@SideOnly(Side.CLIENT)
	private ModelT45Boots boots;
	
	public ArmorT45(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, long maxPower, long chargeRate, long consumption, long drain, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn, "", maxPower, chargeRate, consumption, drain, s);
	}

	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		if (stack.getItem() == ModItems.t45_helmet)
			return armorType == EntityEquipmentSlot.HEAD;
		if (stack.getItem() == ModItems.t45_plate)
			return armorType == EntityEquipmentSlot.CHEST;
		if (stack.getItem() == ModItems.t45_legs)
			return armorType == EntityEquipmentSlot.LEGS;
		if (stack.getItem() == ModItems.t45_boots)
			return armorType == EntityEquipmentSlot.FEET;
		return super.isValidArmor(stack, armorType, entity);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (this == ModItems.t45_helmet) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.helmet == null) {
					this.helmet = new ModelT45Helmet();
				}
				return this.helmet;
			}
		}
		if (this == ModItems.t45_plate) {
			if (armorSlot == EntityEquipmentSlot.CHEST) {
				if (this.plate == null) {
					this.plate = new ModelT45Chest();
				}
				return this.plate;
			}
		}
		if (this == ModItems.t45_legs) {
			if (armorSlot == EntityEquipmentSlot.LEGS) {
				if (this.legs == null) {
					this.legs = new ModelT45Legs();
				}
				return this.legs;
			}
		}
		if (this == ModItems.t45_boots) {
			if (armorSlot == EntityEquipmentSlot.FEET) {
				if (this.boots == null) {
					this.boots = new ModelT45Boots();
				}
				return this.boots;
			}
		}
		return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ModItems.t45_helmet) {
			return "hbm:textures/armor/T45Helmet.png";
		}
		if (stack.getItem() == ModItems.t45_plate) {
			return "hbm:textures/armor/T45Chest.png";
		}
		if (stack.getItem() == ModItems.t45_legs) {
			return "hbm:textures/armor/T45Legs.png";
		}
		if (stack.getItem() == ModItems.t45_boots) {
			return "hbm:textures/armor/T45Boots.png";
		}
		return super.getArmorTexture(stack, entity, slot, type);
	}
}
