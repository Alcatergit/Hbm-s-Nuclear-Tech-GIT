package com.leafia.contents.machines.processing.assemtable.container;

import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.processing.assemtable.AssemTableTE;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AssemTableGUI extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID+":textures/gui/processing/gui_assemtable.png");
	private AssemTableTE te;

	public AssemTableGUI(InventoryPlayer invPlayer,AssemTableTE te) {
		super(new AssemTableContainer(invPlayer,te));
		this.te = te;
		
		this.xSize = 252;
		this.ySize = 192;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18nUtil.resolveKey(te.getName());
		
		this.fontRenderer.drawString(name, 68 - this.fontRenderer.getStringWidth(name) / 2, 15, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8 + 37, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(guiLeft+205,guiTop+48,0);
		LeafiaGls.rotate(te.getWorld().rand.nextFloat()*360,0,0,1);
		LeafiaGls.translate(-59,-2,0);
		drawTexturedModalRect(0,0,62,192,98,4);
		LeafiaGls.popMatrix();
		drawTexturedModalRect(guiLeft+201,guiTop+44,54,192,8,8);
	}
}
