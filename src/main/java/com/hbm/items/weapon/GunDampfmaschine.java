package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GunDampfmaschine extends Item {

	Random rand = new Random();
	
	public GunDampfmaschine(String s) {
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
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		World world = player.world;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_dampfmaschine){
			player.getHeldItemOffhand().getItem().onUsingTick(player.getHeldItemOffhand(), player, count);
		}
		if (true) {
			
			if(!player.isSneaking()) {
				EntityRocket entitybullet = new EntityRocket(world, player, 3.0F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
				
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.crateBreak, SoundCategory.PLAYERS, 10.0F, 0.9F + (rand.nextFloat() * 0.2F));
				if(count == this.getMaxItemUseDuration(stack))
					world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.alarmAutopilot, SoundCategory.PLAYERS, 100.0F, 1.0F);
				
				if (!world.isRemote) {
					world.spawnEntity(entitybullet);
				}
			} else {
				
				world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PIG_AMBIENT, SoundCategory.PLAYERS, 10.0F, 0.9F + (rand.nextFloat() * 0.2F));
				
				if(count % 10 == 0) {
					EntityBombletSelena bomb = new EntityBombletSelena(world);
					bomb.posX = player.posX;
					bomb.posY = player.posY + player.getEyeHeight();
					bomb.posZ = player.posZ;
					bomb.motionX = player.getLookVec().x * 5;
					bomb.motionY = player.getLookVec().y * 5;
					bomb.motionZ = player.getLookVec().z * 5;
					if(count == this.getMaxItemUseDuration(stack))
						world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.chopperDrop, SoundCategory.PLAYERS, 10.0F, 1.0F);
					
					if (!world.isRemote) {
						world.spawnEntity(bomb);
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Sometimes, to do what’s right,");
		list.add("you have to become the villain of");
		list.add("the pi-I mean me too, thanks.");
		list.add("");
		list.add("oh sorry how did this get here i'm not good with computer can somebody tell me how i can get out of here oh fiddlesticks this is not good oh no please can anybody hear me i am afraid please for the love of god somebody get me out of here");
		list.add("");
		list.add("Ammo: §6orang");
		list.add("Damage: aaaaaaaaa");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", -2, 0));
		}
		return map;
	}
}
