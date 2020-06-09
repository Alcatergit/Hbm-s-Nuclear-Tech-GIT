package com.hbm.render.misc;

import org.lwjgl.opengl.GL11;
import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderScreenOverlay {

	private static final ResourceLocation misc = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_misc.png");
	private static final ResourceLocation hud = new ResourceLocation("textures/gui/widgets.png");
	private static final RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
	
	private static long lastSurvey;
	private static float prevResult;
	private static float lastResult;
	
	public static void renderRadCounter(ScaledResolution resolution, float in, Gui gui) {
		GL11.glPushMatrix();

		GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        
        float radiation = 0;
        
        radiation = lastResult - prevResult;
        
        if(System.currentTimeMillis() >= lastSurvey + 1000) {
        	lastSurvey = System.currentTimeMillis();
        	prevResult = lastResult;
        	lastResult = in;
        }
		
		int length = 74;
		int maxRad = 1000;
		
		int bar = getScaled(in, maxRad, 74);
		
		//if(radiation >= 1 && radiation <= 999)
		//	bar -= (1 + Minecraft.getMinecraft().theWorld.rand.nextInt(3));
		
		int posX = 16;
		int posY = resolution.getScaledHeight() - 18 - 2;

		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
        gui.drawTexturedModalRect(posX, posY, 0, 0, 94, 18);
        gui.drawTexturedModalRect(posX + 1, posY + 1, 1, 19, bar, 16);
        
        if(radiation >= 25) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 36, 36, 18, 18);
        	
        } else if(radiation >= 10) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 18, 36, 18, 18);
        	
        } else if(radiation >= 2.5) {
            gui.drawTexturedModalRect(posX + length + 2, posY - 18, 0, 36, 18, 18);
        	
        }
		
		if(radiation > 1000) {
			Minecraft.getMinecraft().fontRenderer.drawString(">1000 RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation >= 1) {
			Minecraft.getMinecraft().fontRenderer.drawString(((int)Math.round(radiation)) + " RAD/s", posX, posY - 8, 0xFF0000);
		} else if(radiation > 0) {
			Minecraft.getMinecraft().fontRenderer.drawString("<1 RAD/s", posX, posY - 8, 0xFF0000);
		}

        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(hud);
	}
	
	private static int getScaled(double cur, double max, double scale) {
		
		return (int) Math.min(cur / max * scale, scale);
	}

	
	public static void renderCustomCrosshairs(ScaledResolution resolution, Gui gui, Crosshair cross) {
		if(cross == Crosshair.NONE)
			return;
		
		int size = cross.size;

		GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(misc);
	        GlStateManager.enableBlend();
	        GlStateManager.tryBlendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
	        gui.drawTexturedModalRect(resolution.getScaledWidth() / 2 - (size / 2), resolution.getScaledHeight() / 2 - (size / 2), cross.x, cross.y, size, size);
	        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
	        GlStateManager.disableBlend();
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(hud);
	}
	
	public static void renderAmmo(ScaledResolution resolution, Gui gui, Item ammo, int count, int max, int dura, EnumHand hand) {
		
		GL11.glPushMatrix();
        
		int pX = resolution.getScaledWidth() / 2 + 62 + 36;
		int pZ = resolution.getScaledHeight() - 21;
		if(hand == EnumHand.OFF_HAND){
			pX -= 277;
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);
        gui.drawTexturedModalRect(pX, pZ + 16, 94, 0, 52, 3);
        gui.drawTexturedModalRect(pX + 1, pZ + 16, 95, 3, 50 - dura, 3);
		
		String cap = max == -1 ? ("∞") : ("" + max);
		
		Minecraft.getMinecraft().fontRenderer.drawString(count + " / " + cap, pX + 16, pZ + 6, 0xFFFFFF);

        GlStateManager.disableBlend();
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.renderItemAndEffectIntoGUI(null, new ItemStack(ammo), pX, pZ);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableBlend();
        
        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(hud);
	}
	
	public static void renderAmmoAlt(ScaledResolution resolution, Gui gui, Item ammo, int count, EnumHand hand) {

		GL11.glPushMatrix();

		int pX = resolution.getScaledWidth() / 2 + 62 + 36 + 18;
		int pZ = resolution.getScaledHeight() - 21 - 16;
		if(hand == EnumHand.OFF_HAND){
			pX -= 296;
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(misc);

		Minecraft.getMinecraft().fontRenderer.drawString(count + "x", pX + 16, pZ + 6, 0xFFFFFF);

        GlStateManager.disableBlend();
        GlStateManager.enableRescaleNormal();
        RenderHelper.enableGUIStandardItemLighting();
        	itemRenderer.renderItemAndEffectIntoGUI(null, new ItemStack(ammo), pX, pZ);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();

        GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(hud);
	}
	
	public enum Crosshair {
		NONE(0, 0, 0),
		CROSS(1, 55, 16),
		CIRCLE(19, 55, 16),
		SEMI(37, 55, 16),
		KRUCK(55, 55, 16),
		DUAL(1, 73, 16),
		SPLIT(19, 73, 16),
		CLASSIC(37, 73, 16),
		BOX(55, 73, 16),
		L_CROSS(0, 90, 32),
		L_KRUCK(32, 90, 32),
		L_CLASSIC(64, 90, 32),
		L_CIRCLE(96, 90, 32),
		L_SPLIT(0, 122, 32),
		L_ARROWS(32, 122, 32),
		L_BOX(64, 122, 32),
		L_CIRCUMFLEX(96, 122, 32),
		L_RAD(0, 154, 32);
		
		public int x;
		public int y;
		public int size;
		
		private Crosshair(int x, int y, int size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
	}
}
