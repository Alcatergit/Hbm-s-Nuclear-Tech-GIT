package com.hbm.items.armor;

import com.hbm.items.ModItems;
import com.hbm.render.model.ModelGlasses;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorAshGlasses extends ItemArmor {

	@SideOnly(Side.CLIENT)
	private ModelGlasses modelGoggles;

	public ArmorAshGlasses(ArmorMaterial armorMaterial, int renderIndex, EntityEquipmentSlot armorType, String s) {
		super(armorMaterial, renderIndex, armorType);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@SideOnly(Side.CLIENT)
	ModelGlasses model;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default){
		if(model == null) {
			model = new ModelGlasses(0);
		}
		return model;
	}
}
