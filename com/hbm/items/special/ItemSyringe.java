package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.gear.JetpackBooster;
import com.hbm.items.gear.JetpackBreak;
import com.hbm.items.gear.JetpackRegular;
import com.hbm.items.gear.JetpackVectorized;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSyringe extends Item {

	public ItemSyringe(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (this == ModItems.syringe_awesome) {
			if (!world.isRemote) {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 50 * 20, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 50 * 20, 24));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 * 20, 6));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 50 * 20, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 4));

				player.getHeldItem(hand).shrink(1);
				player.playSound(HBMSoundHandler.syringeUse, 1.0F, 1.0F);

				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}
			}
		}
		if (this == ModItems.syringe_metal_stimpak) {
			if (!world.isRemote) {
				player.heal(5);

				player.getHeldItem(hand).shrink(1);
				player.playSound(HBMSoundHandler.syringeUse, 1.0F, 1.0F);
				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_metal_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}
			}
		}
		if (this == ModItems.jetpack_tank && (player.inventory.armorInventory.get(2).getItem() == ModItems.jetpack_boost || player.inventory.armorInventory.get(2).getItem() == ModItems.jetpack_break || player.inventory.armorInventory.get(2).getItem() == ModItems.jetpack_fly || player.inventory.armorInventory.get(2).getItem() == ModItems.jetpack_vector)) {
			if (!world.isRemote) {
				ItemStack jetpack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				int fill = JetpackRegular.getFuel(jetpack) + 1000;

				if (jetpack.getItem() == ModItems.jetpack_boost && fill > JetpackBooster.maxFuel)
					fill = JetpackBooster.maxFuel;
				if (jetpack.getItem() == ModItems.jetpack_break && fill > JetpackBreak.maxFuel)
					fill = JetpackBreak.maxFuel;
				if (jetpack.getItem() == ModItems.jetpack_fly && fill > JetpackRegular.maxFuel)
					fill = JetpackRegular.maxFuel;
				if (jetpack.getItem() == ModItems.jetpack_vector && fill > JetpackVectorized.maxFuel)
					fill = JetpackVectorized.maxFuel;

				if (JetpackRegular.getFuel(jetpack) == fill)
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));

				JetpackRegular.setFuel(jetpack, fill);
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.jetpackTank, SoundCategory.PLAYERS, 1.0F, 1.0F);
				ItemStack stack = player.getHeldItem(hand);
				stack.shrink(1);
				if (stack.isEmpty())
					player.setHeldItem(hand, ItemStack.EMPTY);
				else {
					ItemStack newStack = new ItemStack(stack.getItem(), stack.getCount(), stack.getMetadata());
					newStack.setTagCompound(stack.getTagCompound());
					player.setHeldItem(hand, newStack);
				}
			}
		}
		return ActionResult.<ItemStack> newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if (this == ModItems.syringe_awesome) {
			return true;
		}
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if (this == ModItems.syringe_awesome) {
			return EnumRarity.UNCOMMON;
		}
		return EnumRarity.COMMON;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker) {
		if (this == ModItems.syringe_awesome) {
			if (!entity.world.isRemote) {
				entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 50 * 20, 0));
				entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 50 * 20, 24));
				entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 * 20, 6));
				entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 50 * 20, 4));
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 4));

				stack.shrink(1);
				;
				entity.playSound(HBMSoundHandler.syringeUse, 1.0F, 1.0F);

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
						player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
					}
				}
			}
		}
		if (this == ModItems.syringe_metal_stimpak) {
			if (!entity.world.isRemote) {
				entity.heal(5);

				stack.shrink(1);
				;
				entity.playSound(HBMSoundHandler.syringeUse, 1.0F, 1.0F);

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this == ModItems.syringe_awesome) {
			tooltip.add("Every good effect for 50 seconds");
		}
		if (this == ModItems.syringe_metal_stimpak) {
			tooltip.add("Heals 2.5 hearts");
		}
	}
}
