package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityCombineBall;
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

public class GunOSIPR extends Item {
	
	Random rand = new Random();
	
	public GunOSIPR(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		this.setMaxDamage(2500);
		
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
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_osipr){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;
		
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_osipr_ammo)) && count % 3 == 0) {
					EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 5, 15, false, "chopper", player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				entityarrow.setDamage(5 + rand.nextInt(10));

				//world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.osiprShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_osipr_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntity(entityarrow);
				}
			}
		} else {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_osipr_ammo2)) && count % 30 == 0 && (this.getMaxItemUseDuration(stack) - count) != 0) {
				EntityCombineBall entityarrow = new EntityCombineBall(player.world, player, 3.0F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				entityarrow.setDamage(35 + rand.nextInt(45 - 35));

				//world.playSoundAtEntity(player, "tile.piston.in", 1.0F, 0.75F);
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.singFlyby, SoundCategory.PLAYERS, 1.0F, 1F);

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_osipr_ammo2);
				}
				
				if (!world.isRemote) {
					world.spawnEntity(entityarrow);
				}
			}
			
			if((this.getMaxItemUseDuration(stack) - count) % 30 == 15 && (player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_osipr_ammo2)))
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.osiprCharging, SoundCategory.PLAYERS, 1.0F, 1F);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Hold right mouse button");
		list.add("to shoot,");
		list.add("sneak to shoot a");
		list.add("dark energy ball!");
		list.add("");
		list.add("Ammo: Dark Energy Plugs");
		list.add("Secondary Ammo: Combine Ball");
		list.add("Damage: 5 - 15");
		list.add("Secondary Damage: 1000");
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
