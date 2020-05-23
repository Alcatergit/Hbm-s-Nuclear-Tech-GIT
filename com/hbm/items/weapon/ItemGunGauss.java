package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemGunGauss extends ItemGunBase {
	
	private AudioWrapper chargeLoop;

	public ItemGunGauss(GunConfiguration config, GunConfiguration alt, String s) {
		super(config, alt, s);
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {

		if(getHasShot(stack)) {
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.sparkShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setHasShot(stack, false);
		}
		
		if(!main && getStored(stack) > 0) {
			EntityBulletBase bullet = new EntityBulletBase(world, altConfig.config.get(0), player);
			bullet.overrideDamage = Math.min(getStored(stack), 13) * 3.5F;
			world.spawnEntity(bullet);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauShoot, SoundCategory.PLAYERS, 1.0F, 0.75F);
			setItemWear(stack, getItemWear(stack) + (getCharge(stack)) * 2);
			setCharge(stack, 0);
		}
	}
	
	@Override
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {

		if(chargeLoop != null) {
			chargeLoop.stopSound();
			chargeLoop = null;
		}
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player) {
		setCharge(stack, 1);
	}
	
	@Override
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {

		if(!main && getItemWear(stack) < mainConfig.durability && Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
			chargeLoop = MainRegistry.proxy.getLoopedSound(HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, (float)player.posX, (float)player.posY, (float)player.posZ, 1.0F, 0.75F);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, 1.0F, 0.75F);
			
			if(chargeLoop != null) {
				chargeLoop.startSound();
			}
		}
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand isCurrentItem) {
		
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if(getIsAltDown(stack) && getItemWear(stack) < mainConfig.durability) {
			
			int c = getCharge(stack);
			
			if(c > 200) {
				setCharge(stack, 0);
				setItemWear(stack, mainConfig.durability);
				player.attackEntityFrom(ModDamageSource.tauBlast, 1000);
				world.newExplosion(player, player.posX, player.posY + player.eyeHeight, player.posZ, 5.0F, true, true);
				return;
			}
			
			if(c > 0) {
				setCharge(stack, c + 1);
				
				if(c % 10 == 1 && c < 140) {
					
					if(Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
						Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
						setStored(stack, getStored(stack) + 1);
					} else {
						setCharge(stack, 0);
						setStored(stack, 0);
					}
				}
			} else {
				setStored(stack, 0);
			}
		} else {
			setCharge(stack, 0);
			setStored(stack, 0);
		}
	}
	
	@Override
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand isCurrentItem) {
		super.updateClient(stack, world, player, slot, isCurrentItem);

		if(chargeLoop != null) {
			chargeLoop.updatePosition((float)player.posX, (float)player.posY, (float)player.posZ);
			chargeLoop.updatePitch(chargeLoop.getPitch() + 0.01F);
		}
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand) {
		
		super.spawnProjectile(world, player, stack, config, hand);
		setHasShot(stack, true);
	}
	
	public static void setHasShot(ItemStack stack, boolean b) {
		writeNBT(stack, "hasShot", b ? 1 : 0);
	}
	
	public static boolean getHasShot(ItemStack stack) {
		return readNBT(stack, "hasShot") == 1;
	}
	
	/// gauss charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "gauss_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "gauss_charge");
	}
	
	public static void setStored(ItemStack stack, int i) {
		writeNBT(stack, "gauss_stored", i);
	}
	
	public static int getStored(ItemStack stack) {
		return readNBT(stack, "gauss_stored");
	}
}