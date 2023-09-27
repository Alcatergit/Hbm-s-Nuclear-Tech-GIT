package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GunImmolator extends Item {

	Random rand = new Random();
	
	public GunImmolator(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player1, int count) {
		if(!(player1 instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) player1;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_immolator){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;

		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_immolator_ammo))) {
				EntityFire entityarrow = new EntityFire(world, player, 3.0F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				entityarrow.setDamage(6 + rand.nextInt(5));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					if(count % 10 == 0)
						Library.consumeInventoryItem(player.inventory, ModItems.gun_immolator_ammo);
				}

				if(count == this.getMaxItemUseDuration(stack))
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerIgnite, SoundCategory.PLAYERS, 1.0F, 1F);
				if(count % 5 == 0)
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerShoot, SoundCategory.PLAYERS, 1.0F, 1F);

				if (!world.isRemote) {
					world.spawnEntity(entityarrow);
				}
			}
		} else {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_immolator_ammo))) {

				EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, player, 1F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				
				if (flag) {
					plasma.canBePickedUp = 2;
				} else {
					if(count % 4 == 0)
						Library.consumeInventoryItem(player.inventory, ModItems.gun_immolator_ammo);
				}

				if(count == this.getMaxItemUseDuration(stack))
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorIgnite, SoundCategory.PLAYERS, 1.0F, 1F);
				if(count % 10 == 0)
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorShoot, SoundCategory.PLAYERS, 1.0F, 1F);
				
				if (!world.isRemote)
					world.spawnEntity(plasma);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Hold right mouse button");
		list.add("to shoot fire,");
		list.add("sneak to shoot");
		list.add("plasma beams!");
		list.add("");
		list.add("Ammo: §cImmolator Fuel");
		list.add("Damage: 5");
		list.add("Secondary Damage: 25 - 45");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, 0));
		}
		return map;
	}
}
