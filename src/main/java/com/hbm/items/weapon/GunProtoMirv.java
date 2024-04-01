package com.hbm.items.weapon;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class GunProtoMirv extends Item {

	public GunProtoMirv(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.maxStackSize = 1;
		this.setMaxDamage(2500);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entityLiving;
		if (entityLiving.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) == stack && !entityLiving.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && entityLiving.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == ModItems.gun_proto) {
			entityLiving.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
		}
		int j = this.getMaxItemUseDuration(stack) - timeLeft;
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, j, false);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.getCharge();

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		boolean f1 = false;
				
		for(int i = 0; i < 8; i++) {
			if (flag || Library.hasInventoryItem(player.inventory, ModItems.ammo_nuke)) {
				
				f1 = true;
				
				float f = j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;
	
				if (j < 25.0D) {
					return;
				}
	
				if (j > 25.0F) {
					f = 25.0F;
				}
	
				EntityMiniNuke entityarrow = new EntityMiniNuke(worldIn, player, 3.0F, player.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
	
				entityarrow.setIsCritical(true);
				entityarrow.gravity = 0.3;
				entityarrow.setDamage(1000);
				
				entityarrow.motionX += worldIn.rand.nextGaussian() * 0.4;
				entityarrow.motionY += worldIn.rand.nextGaussian() * 0.4;
				entityarrow.motionZ += worldIn.rand.nextGaussian() * 0.4;
	
				stack.damageItem(1, player);
	
				if (!flag) {
					Library.consumeInventoryItem(player.inventory, ModItems.ammo_nuke);
				}
	
				if (!worldIn.isRemote) {
					worldIn.spawnEntity(entityarrow);
				}
			}
		}
		
		if(f1)
			worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.fatmanShoot, SoundCategory.PLAYERS, 1.0F, 1F);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(UUID.fromString("91AEAA56-376B-4498-935B-2F7F68070635"), "Weapon modifier", -0.3, 1));
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, 0));
		}
		return map;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Excessive, who's being excessive!?");
		list.add("");
		list.add("Ammo: Mini Nukes");
		list.add("Damage: 1000");
		list.add("Shoots up to eight mini nukes at once!");
	}
}
