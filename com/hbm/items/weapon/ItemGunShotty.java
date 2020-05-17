package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.lib.Library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGunShotty extends ItemGunBase {

	public ItemGunShotty(GunConfiguration config, String s) {
		super(config, s);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateServer(stack, world, player, slot, hand);
		if(player.getUniqueID().toString().equals(Library.Dr_Nostalgia) && getDelay(stack) < this.mainConfig.rateOfFire * 0.9){
			setDelay(stack, 0);
		}
		
	}
	
}