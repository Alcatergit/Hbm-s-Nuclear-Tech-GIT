package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
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

public class GunSpark extends Item {

	Random rand = new Random();

	public int dmgMin = 12;
	public int dmgMax = 24;

	public GunSpark(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.setMaxStackSize(1);

		ModItems.ALL_ITEMS.add(this);
	}

	/**
	 * called when the player releases the use item button. Args: itemstack,
	 * world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
		int j = this.getMaxItemUseDuration(stack) - timeLeft;
		if (entityLiving instanceof EntityPlayer) {
			ArrowLooseEvent event = new ArrowLooseEvent((EntityPlayer) entityLiving, stack, world, j, true);
			MinecraftForge.EVENT_BUS.post(event);
			j = event.getCharge();
		}
		boolean creative = false;
		boolean hasAmmo = false;
		if(entityLiving instanceof EntityPlayer){
			if(((EntityPlayer)entityLiving).capabilities.isCreativeMode){
				creative = true;
			}
			hasAmmo = Library.hasInventoryItem(((EntityPlayer) entityLiving).inventory, ModItems.gun_spark_ammo);
		}
		boolean flag = creative || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
		if (flag || hasAmmo) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}

			stack.damageItem(1, entityLiving);

			world.playSound(entityLiving.posX, entityLiving.posY, entityLiving.posZ, HBMSoundHandler.sparkShoot, SoundCategory.PLAYERS, 1.0F, 1.0F, true);

			if (flag) {
			} else {
				if(entityLiving instanceof EntityPlayer)
					Library.consumeInventoryItem(((EntityPlayer) entityLiving).inventory, ModItems.gun_spark_ammo);
			}

			EntitySparkBeam beam = new EntitySparkBeam(world, entityLiving, 3F, entityLiving.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			beam.setDamage(dmgMin + rand.nextInt(dmgMax - dmgMin));
			if (!world.isRemote)
				world.spawnEntity(beam);
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.BOW;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ArrowNockEvent event = new ArrowNockEvent(playerIn, playerIn.getHeldItem(handIn), handIn, worldIn, true);
		MinecraftForge.EVENT_BUS.post(event);

		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}

	@Override
	public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag bool) {

		list.add("'magic does not compute'");
		list.add("'aeiou'");
		list.add("");
		list.add("Ammo: §bElectromagnetic Cartridge");
		list.add("Projectiles explode on impact.");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
}
