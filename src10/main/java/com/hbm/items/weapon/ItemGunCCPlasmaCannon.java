package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunCCPlasmaCannon extends ItemGunBase {

	public ItemGunCCPlasmaCannon(GunConfiguration config, String s) {
		super(config, s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onFireClient(ItemStack stack, EntityPlayer player, boolean shouldDoThirdPerson) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasCustomHudElement() {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack, EnumHand hand) {
		
	}

}
