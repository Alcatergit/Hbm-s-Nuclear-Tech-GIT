package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GunHP extends Item {

	Random rand = new Random();
	
	public GunHP(String s) {
		this.setTranslationKey(s);
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
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_hp){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;
		
		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_hp_ammo))) {

			EnumHand hand = player.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
			EntityPlasmaBeam plasma = new EntityPlasmaBeam(world, player, 1F, hand);
			EntityPlasmaBeam plasma1 = new EntityPlasmaBeam(world, player, 1F, hand);
			EntityPlasmaBeam plasma2 = new EntityPlasmaBeam(world, player, 1F, hand);
			EntityPlasmaBeam plasma3 = new EntityPlasmaBeam(world, player, 1F, hand);
			EntityPlasmaBeam plasma4 = new EntityPlasmaBeam(world, player, 1F, hand);
			plasma1.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma1.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma1.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma2.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma3.motionZ *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionX *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionY *= (0.75 + (rand.nextDouble() * 0.5));
			plasma4.motionZ *= (0.75 + (rand.nextDouble() * 0.5));

			if (flag) {
				plasma.canBePickedUp = 2;
			} else {
				if (count % 20 == 0)
					Library.consumeInventoryItem(player.inventory, ModItems.gun_hp_ammo);
			}

			if (count == this.getMaxItemUseDuration(stack))
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorIgnite, SoundCategory.PLAYERS, 1.0F, 1F);
			if (count % 10 == 0)
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.immolatorShoot, SoundCategory.PLAYERS, 1.0F, 1F);

			if (!world.isRemote) {
				world.spawnEntity(plasma);
				world.spawnEntity(plasma1);
				world.spawnEntity(plasma2);
				world.spawnEntity(plasma3);
				world.spawnEntity(plasma4);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Rrrrt - rrrrt - rrrrt, weeee!");
		list.add("");
		list.add("Ammo: §aInk Cartridge");
		list.add("Damage: 25 - 45");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
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
