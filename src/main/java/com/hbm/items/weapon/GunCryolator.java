package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityLN2;
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

public class GunCryolator extends Item {

	Random rand = new Random();
	
	public GunCryolator(String s) {
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
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_cryolator){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_cryolator_ammo))) {
			EntityLN2 entityarrow = new EntityLN2(world, player, 3.0F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				if (count % 10 == 0)
					Library.consumeInventoryItem(player.inventory, ModItems.gun_cryolator_ammo);
			}

			if (count % 5 == 0)
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.cryolatorShoot, SoundCategory.PLAYERS, 1.0F, 1F);

			if (!world.isRemote) {
				world.spawnEntity(entityarrow);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Ice to meet you!");
		list.add("");
		list.add("Ammo: §bCryolator Ammo");
		list.add("Damage: 5");
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
