package com.hbm.items.weapon;

import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class GunFolly extends Item implements IHoldableWeapon {

	@Override
	public Crosshair getCrosshair() {
		return Crosshair.L_SPLIT;
	}
	
	public GunFolly(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		int state = getState(stack);
		
		if(state == 0) {
			
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.follyOpen, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setState(stack, 1);
			
		} else if(state == 1) {
			
			if(Library.hasInventoryItem(player.inventory, ModItems.ammo_folly)) {

				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.follyReload, SoundCategory.PLAYERS, 1.0F, 1.0F);
				Library.consumeInventoryItem(player.inventory, ModItems.ammo_folly);
				setState(stack, 2);
			} else {
				
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.follyClose, SoundCategory.PLAYERS, 1.0F, 1.0F);
				setState(stack, 0);
			}
			
		} else if(state == 2) {

			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.follyClose, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setState(stack, 3);
			setTimer(stack, 100);
		} else if(state == 3) {
			
			if(getTimer(stack) == 0) {
				
				setState(stack, 0);
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.follyFire, SoundCategory.PLAYERS, 1.0F, 1.0F);

				double mult = 1.75D;
				
				player.motionX -= player.getLookVec().x * mult;
				player.motionY -= player.getLookVec().y * mult;
				player.motionZ -= player.getLookVec().z * mult;

				if (!world.isRemote) {
					EntityBulletBase bullet = new EntityBulletBase(world, BulletConfigSyncingUtil.TEST_CONFIG, player, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
					world.spawnEntity(bullet);
					
					for(int i = 0; i < 25; i++) {
						EntitySSmokeFX flame = new EntitySSmokeFX(world);
						
						flame.motionX = player.getLookVec().x;
						flame.motionY = player.getLookVec().y;
						flame.motionZ = player.getLookVec().z;
						
						flame.posX = player.posX + flame.motionX + world.rand.nextGaussian() * 0.35;
						flame.posY = player.posY + flame.motionY + world.rand.nextGaussian() * 0.35 + player.eyeHeight;
						flame.posZ = player.posZ + flame.motionZ + world.rand.nextGaussian() * 0.35;
						
						world.spawnEntity(flame);
					}
				}
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(getState(stack) == 3) {
			
			if(isSelected) {
				int timer = getTimer(stack);
				
				if(timer > 0) {
					timer--;
	
					if(timer % 20 == 0 && timer != 0)
						world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.follyBuzzer, SoundCategory.PLAYERS, 1.0F, 1.0F);
					
					if(timer == 0)
						world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.follyAquired, SoundCategory.PLAYERS, 1.0F, 1.0F);
					
					setTimer(stack, timer);
				}
			} else {
				setTimer(stack, 100);
			}
		}
	}
	
	public static void setState(ItemStack stack, int i) {
		writeNBT(stack, "state", i);
	}
	
	public static int getState(ItemStack stack) {
		return readNBT(stack, "state");
	}
	
	public static void setTimer(ItemStack stack, int i) {
		writeNBT(stack, "timer", i);
	}
	
	public static int getTimer(ItemStack stack) {
		return readNBT(stack, "timer");
	}
	
	private static void writeNBT(ItemStack stack, String key, int value) {
		
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setInteger(key, value);
	}
	
	private static int readNBT(ItemStack stack, String key) {
		
		if(!stack.hasTagCompound())
			return 0;
		
		return stack.getTagCompound().getInteger(key);
	}

}
