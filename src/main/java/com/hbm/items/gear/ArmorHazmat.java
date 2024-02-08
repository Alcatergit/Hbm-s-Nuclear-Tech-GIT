package com.hbm.items.gear;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorHazmat extends ItemArmor {

	private ResourceLocation hazmatBlur = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_hazmat.png");
	
	public ArmorHazmat(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(CreativeTabs.COMBAT);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if(stack.getItem().equals(ModItems.hazmat_plate) || stack.getItem().equals(ModItems.hazmat_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_plate) || stack.getItem().equals(ModItems.hazmat_paa_boots)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_1.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_paa_legs)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_paa_2.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_plate_red) || stack.getItem().equals(ModItems.hazmat_boots_red)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1_red.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs_red)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2_red.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_plate_grey) || stack.getItem().equals(ModItems.hazmat_boots_grey)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_1_grey.png");
		}
		if(stack.getItem().equals(ModItems.hazmat_legs_grey)) {
			return (RefStrings.MODID + ":textures/armor/hazmat_2_grey.png");
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		if(this != ModItems.hazmat_helmet && this != ModItems.hazmat_paa_helmet)
    		return;
		GlStateManager.disableDepth();
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(hazmatBlur);
        RenderHelper.startDrawingTexturedQuads();
        RenderHelper.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        RenderHelper.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        RenderHelper.draw();
        GL11.glDepthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		super.renderHelmetOverlay(stack, player, resolution, partialTicks);
	}
}
