package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

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
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class GunXVL1456 extends Item {

	Random rand = new Random();
	
	public GunXVL1456(String s) {
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
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer)entityLiving;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_xvl1456){
			player.getHeldItemOffhand().onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
		}
		int j = this.getMaxItemUseDuration(stack) - timeLeft;
		
		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, j, false);
		MinecraftForge.EVENT_BUS.post(event);
		// if (event.isCanceled()) {
		// return;
		// }
        j = event.getCharge() * 2;

		if (player.isSneaking() && j >= 20) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

			if (flag || Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
				EntityBullet entitybullet = new EntityBullet(worldIn, player, 3.0F, j, j + 5, false, "tauDay", player.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

				entitybullet.setDamage(j + rand.nextInt(6));

				worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauShoot, SoundCategory.PLAYERS, 1.0F, 0.5F);

				if (flag) {
					entitybullet.canBePickedUp = 2;
				} else {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
				}

				entitybullet.setIsCritical(true);

				if (!worldIn.isRemote) {
					worldIn.spawnEntity(entitybullet);
				}
				stack.damageItem((int)(j * 0.05F), player);
				
				player.rotationPitch -= (j * 0.1F);
			}
		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ArrowNockEvent event = new ArrowNockEvent(playerIn, playerIn.getHeldItem(handIn), handIn, worldIn, false);
		MinecraftForge.EVENT_BUS.post(event);
		playerIn.setActiveHand(handIn);
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player1, int count) {
		if(!(player1 instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer)player1;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_xvl1456){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		World world = player.world;
		if (!player.isSneaking()) {
			boolean flag = player.capabilities.isCreativeMode
					|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) && count % 4 == 0) {

				EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 25, 65, false, "eyyOk", player.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				entityarrow.setDamage(25 + rand.nextInt(65 - 25));

				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

				if (flag) {
					entityarrow.canBePickedUp = 2;
				} else {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
				}
				
				if (!world.isRemote) {
					world.spawnEntity(entityarrow);
				}
			}
		} else {
			if (count % 20 == 0 && this.getMaxItemUseDuration(stack) - count != 0) {
				boolean flag = player.capabilities.isCreativeMode
						|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
				if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo))) {
					if (!flag) {
						Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
					}
				}
			}
			
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.nullTau, SoundCategory.PLAYERS, 0.1F, 1.0F);
		}
		
		if(player instanceof EntityPlayer)
		{
			if(count < getMaxItemUseDuration(stack) - 200 && player.isSneaking() && count != 0)
			{
				if(!world.isRemote)
				{
					stack.damageItem(1250, player);
					
					world.createExplosion(player, player.posX, player.posY, player.posZ, 10.0F, true);
					player.attackEntityFrom(ModDamageSource.tauBlast, 1000F);
					((EntityPlayer) player).dropItem(false);
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Hold right mouse button");
		list.add("to shoot tauons,");
		list.add("sneak to charge up for");
		list.add("stronger shots!");
		list.add("");
		list.add("Ammo: Depleted Uranium");
		list.add("Damage: 25 - 65");
		list.add("Charged Damage: 40 - 400");
		list.add("Projectiles penetrate walls.");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6, 0));
		}
		return map;
	}
}
