package com.hbm.items.weapon;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityLaser;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import com.hbm.util.I18nUtil;
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
import net.minecraft.world.World;

public class GunBrimstone extends Item {

	public GunBrimstone(String s) {
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
	public void onUsingTick(ItemStack stack, EntityLivingBase ent, int count) {
		if(ent.getHeldItemMainhand() == stack && ent.getHeldItemOffhand().getItem() == ModItems.gun_brimstone){
			ent.getHeldItemOffhand().getItem().onUsingTick(ent.getHeldItemOffhand(), ent, count);
		}
		if(!(ent instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer)ent;
		World world = player.world;

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.ammo_566_gold)) && count % 1 == 0) {
			
			
			EntityLaser laser = new EntityLaser(world, player, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

			//world.playSoundAtEntity(player, "hbm:weapon.rifleShoot", 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

			if (!flag) {
				Library.consumeInventoryItem(player.inventory, ModItems.gun_dash_ammo);
			}

			if (!world.isRemote) {
				world.spawnEntity(laser);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 5, 0));
		}
		return map;
	}
	
}
