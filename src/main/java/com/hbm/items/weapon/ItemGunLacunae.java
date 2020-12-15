package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemGunLacunae extends ItemGunBase {
	
	public ItemGunLacunae(GunConfiguration config, String s) {
		super(config, s);
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		
		if(main) {
			setDelay(stack, 20);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lacunaeSpinup, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		
		if(main)
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lacunaeSpindown, SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		
		super.updateServer(stack, world, player, slot, hand);
		
		if(getIsMouseDown(stack)) {
			
			int rot = readNBT(stack, "rot") % 360;
			rot += 25;
			writeNBT(stack, "rot", rot);
		}
	}
}