package com.hbm.items.gear;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ArmorAntiSchrabidium extends ItemArmor {

	public ArmorAntiSchrabidium(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.setMaxStackSize(1);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(stack.getItem().equals(ModItems.anti_schrabidium_helmet) || stack.getItem().equals(ModItems.anti_schrabidium_plate) || stack.getItem().equals(ModItems.anti_schrabidium_boots)) {
			return (RefStrings.MODID + ":textures/armor/anti_schrabidium_1.png");
		}
		if(stack.getItem().equals(ModItems.anti_schrabidium_legs)) {
			return (RefStrings.MODID + ":textures/armor/anti_schrabidium_2.png");
		}
		return null;
	}

	public static boolean hasFullAntiSchrabidiumArmor(EntityLivingBase entity) {
		if(entity == null)
			return false;
		
		ItemStack helmet = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
		ItemStack chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		ItemStack legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
		ItemStack feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		return !helmet.isEmpty() && helmet.getItem() == ModItems.anti_schrabidium_helmet &&
		       !chest.isEmpty() && chest.getItem() == ModItems.anti_schrabidium_plate &&
		       !legs.isEmpty() && legs.getItem() == ModItems.anti_schrabidium_legs &&
		       !feet.isEmpty() && feet.getItem() == ModItems.anti_schrabidium_boots;
	}

	public static void handleAttack(LivingAttackEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		
		if(hasFullAntiSchrabidiumArmor(e)) {
			// Cancel the attack entirely
			event.setCanceled(true);
			
			// Reflect damage back to attacker with infinite amplification
			if(event.getSource().getTrueSource() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
				float reflectedDamage = event.getAmount() * Float.MAX_VALUE;
				// Use generic damage source to avoid casting issues
				attacker.attackEntityFrom(DamageSource.GENERIC, reflectedDamage);
			}
		}
	}

	public static void handleHurt(LivingHurtEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		
		if(hasFullAntiSchrabidiumArmor(e)) {
			// Store original damage before setting to 0
			float originalDamage = event.getAmount();
			
			// Set damage to 0 - complete invulnerability
			event.setAmount(0);
			
			// Reflect damage back to attacker with infinite amplification
			if(event.getSource().getTrueSource() instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
				float reflectedDamage = originalDamage * Float.MAX_VALUE;
				// Use generic damage source to avoid casting issues
				attacker.attackEntityFrom(DamageSource.GENERIC, reflectedDamage);
			}
		}
	}

	public static void handleDeath(LivingDeathEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		
		if(hasFullAntiSchrabidiumArmor(e)) {
			// Prevent death event entirely
			event.setCanceled(true);
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		// Ensure player is always at maximum health (Float.MAX_VALUE)
		if(hasFullAntiSchrabidiumArmor(player)) {
			player.setHealth(Float.MAX_VALUE);
		}
	}
}
