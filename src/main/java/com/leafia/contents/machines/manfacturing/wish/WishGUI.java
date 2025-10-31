package com.leafia.contents.machines.manfacturing.wish;

import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.util.I18nUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WishGUI extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation("minecraft:textures/gui/container/furnace.png");
	private WishTE diFurnace;

	public WishGUI(InventoryPlayer invPlayer,WishTE tedf) {
		super(new WishContainer(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18nUtil.resolveKey("tile.wish_crucible.name");
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int j = diFurnace.getBurnScaled(14);
		drawTexturedModalRect(guiLeft+56,guiTop+36+(14-j),176,14-j,14,j);
		
		int j1 = diFurnace.getDiFurnaceProgressScaled(24);
		drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, j1 + 1, 17);
	}

}
