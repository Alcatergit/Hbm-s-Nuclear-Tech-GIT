package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.modules.ItemHazardModule;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModInsert extends ItemArmorMod implements IItemHazard {

	private float damageMod;
	private float projectileMod;
	private float explosionMod;
	private float speed;
	
	public ItemModInsert(int durability, float damageMod, float projectileMod, float explosionMod, float speed, String s){
		super(ArmorModHandler.kevlar, false, true, false, false, s);
		this.damageMod = damageMod;
		this.projectileMod = projectileMod;
		this.explosionMod = explosionMod;
		this.speed = speed;
		this.setMaxDamage(durability);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		if(damageMod != 1F)
			list.add(TextFormatting.RED + (damageMod < 1 ? "-" : "+") + Math.abs(Math.round((1F - damageMod) * 100)) + "% Damage");
		if(projectileMod != 1F)
			list.add(TextFormatting.YELLOW + "-" + Math.round((1F - projectileMod) * 100) + "% Projectile Damage");
		if(explosionMod != 1F)
			list.add(TextFormatting.YELLOW + "-" + Math.round((1F - explosionMod) * 100) + "% Explosion Damage");
		if(speed != 1F)
			list.add(TextFormatting.BLUE + "-" + Math.round((1F - speed) * 100) + "% Speed");
		
		if(this == ModItems.insert_polonium)
			list.add(TextFormatting.DARK_RED + "+100 RAD/s");
		
		list.add("Durability: "+(stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage());
		
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
		
		module.addInformation(stack, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor){
		List<String> desc = new ArrayList<>();

		if(damageMod != 1F)
			desc.add((damageMod < 1 ? "-" : "+") + Math.abs(Math.round((1F - damageMod) * 100)) + "% dmg");
		if(projectileMod != 1F)
			desc.add("-" + Math.round((1F - projectileMod) * 100) + "% proj");
		if(explosionMod != 1F)
			desc.add("-" + Math.round((1F - explosionMod) * 100) + "% exp");
		if(explosionMod != 1F)
			desc.add("-" + Math.round((1F - speed) * 100) + "% speed");

		if(this == ModItems.insert_polonium)
			desc.add("+100 RAD/s");
		
		String join = String.join(" / ", desc);
		
		list.add(TextFormatting.DARK_PURPLE + "  " + stack.getDisplayName() + " (" + join + " / " + (stack.getMaxDamage() - stack.getItemDamage()) + "HP)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor){
		event.setAmount(event.getAmount()*damageMod);
		
		if(event.getSource().isProjectile())
			event.setAmount(event.getAmount()*projectileMod);
		
		if(event.getSource().isExplosion())
			event.setAmount(event.getAmount()*explosionMod);
		
		ItemStack insert = ArmorModHandler.pryMods(armor)[ArmorModHandler.kevlar];
		
		if(insert == null)
			return;
		
		insert.setItemDamage(insert.getItemDamage() + 1);
		
		if(!event.getEntity().world.isRemote && this == ModItems.insert_era) {
			event.getEntity().world.newExplosion(event.getEntity(), event.getEntity().posX, event.getEntity().posY - event.getEntity().getYOffset() + event.getEntity().height * 0.5, event.getEntity().posZ, 0.05F, false, false);
		}
		
		if(insert.getItemDamage() >= insert.getMaxDamage()) {
			ArmorModHandler.removeMod(armor, ArmorModHandler.kevlar);
		} else {
			ArmorModHandler.applyMod(armor, insert);
		}
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor){
		if(!entity.world.isRemote && this == ModItems.insert_polonium) {
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 5.0F);
		}
	}
	
	@Override
	public Multimap<String, AttributeModifier> getModifiers(EntityEquipmentSlot slot, ItemStack armor){
		if(speed == 1)
			return null;
		
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, armor);
		
		multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
				new AttributeModifier(ArmorModHandler.UUIDs[((ItemArmor)armor.getItem()).armorType.getIndex()], "NTM Armor Mod Speed", -1F + speed, 2));
		
		return multimap;
	}
	
	ItemHazardModule module = new ItemHazardModule();
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected){
		if(entityIn instanceof EntityLivingBase)
			this.module.applyEffects((EntityLivingBase) entityIn, stack.getCount(), itemSlot, isSelected, ((EntityLivingBase)entityIn).getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
	}

}
