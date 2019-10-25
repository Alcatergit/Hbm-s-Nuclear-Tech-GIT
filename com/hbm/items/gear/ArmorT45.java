package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;

public class ArmorT45 extends ItemArmor implements ISpecialArmor {

	public ArmorT45(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.tabTest);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return super.isValidArmor(stack, armorType, entity);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage,
			int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		// TODO Auto-generated method stub
		
	}

}
