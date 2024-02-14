package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GunLeverActionS extends Item {

	Random rand = new Random();
	
	public int dmgMin = 8;
	public int dmgMax = 16;
	
	public GunLeverActionS(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		this.setMaxDamage(500);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer) entityLiving;
		int j = this.getMaxItemUseDuration(stack) - timeLeft;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, j, Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge));
		MinecraftForge.EVENT_BUS.post(event);
		j = event.getCharge();

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if (flag || Library.hasInventoryItem(player.inventory, ModItems.ammo_20gauge)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (j < 10.0D) {
				return;
			}

			if (j > 10.0F) {
				f = 10.0F;
			}

			Vec3d vec = player.getLookVec();
			vec = new Vec3d(vec.x*-1, vec.y*-1, vec.z*-1);
			vec.normalize();

			player.motionX += vec.x * 0.75;
			player.motionY += vec.y * 0.75;
			player.motionZ += vec.z * 0.75;
			
			Library.consumeInventoryItem(player.inventory, ModItems.ammo_12gauge);

			stack.damageItem(1, player);

        	player.attackEntityFrom(ModDamageSource.suicide, 10000);
        	if(!player.capabilities.isCreativeMode)
        		player.setHealth(0.0F);

			worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.revolverShootAlt, SoundCategory.PLAYERS, 5.0F, 0.75F);
			
			setAnim(stack, 1);
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		int j = getAnim(stack);
    	
    	if(j > 0) {
    		if(j < 30)
    			setAnim(stack, j + 1);
    		else
    			setAnim(stack, 0);
    		
        	if(j == 15)
        		worldIn.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.leverActionReload, SoundCategory.PLAYERS, 2F, 0.85F);
    	}
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
		ItemStack stack = playerIn.getHeldItem(handIn);
		ArrowNockEvent event = new ArrowNockEvent(playerIn, stack, handIn, worldIn, Library.hasInventoryItem(playerIn.inventory, ModItems.ammo_12gauge));
		MinecraftForge.EVENT_BUS.post(event);

		if(getAnim(stack) == 0)
			playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		if(MainRegistry.polaroidID == 11)
			return ("" + I18n.format(this.getUnlocalizedName() + "_2.name")).trim();
		else
			return ("" + I18n.format(this.getUnlocalizedName() + ".name")).trim();
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(MainRegistry.polaroidID == 11)
			list.add("Vee guilt-tripped me into this.");
		else
			list.add("I hate your guts, Vee.");
		list.add("");
		list.add("Ammo: §e12x74 Buckshot");
		list.add("Damage: Infinite");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 3.5, 0));
		}
		return map;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
	
	private static int getAnim(ItemStack stack) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}
		
		return stack.getTagCompound().getInteger("animation");
		
	}
	
	private static void setAnim(ItemStack stack, int i) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		stack.getTagCompound().setInteger("animation", i);
		
	}
	
	public static float getRotationFromAnim(ItemStack stack) {
		float rad = 0.0174533F;
		rad *= 7.5F;
		int i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;
		
		if(i < 10)
			return rad * i;
		else
			return (rad * 10) - (rad * (i - 10));
	}
	
	public static float getOffsetFromAnim(ItemStack stack) {
		float i = getAnim(stack);
		
		if(i < 10)
			return 0;
		i -= 10;
		
		if(i < 10)
			return i / 10;
		else
			return 2 - (i / 10);
	}
}
