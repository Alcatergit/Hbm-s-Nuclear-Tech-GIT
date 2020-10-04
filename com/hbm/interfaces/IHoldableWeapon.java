package com.hbm.interfaces;

import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IHoldableWeapon {

	public Crosshair getCrosshair();
	
	@SideOnly(Side.CLIENT)
	public default boolean hasCustomHudElement(){return false;};
	
	@SideOnly(Side.CLIENT)
	public default void renderHud(ScaledResolution res, GuiIngame gui, ItemStack stack, float partialTicks){};
}
