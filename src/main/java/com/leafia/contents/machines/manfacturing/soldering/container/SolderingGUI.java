package com.leafia.contents.machines.manfacturing.soldering.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.manfacturing.soldering.SolderingTE;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.gui.FiaUIRect;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SolderingGUI extends GuiInfoContainer {
	SolderingTE te;
	static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID+":textures/gui/processing/gui_soldering_station.png");
	public SolderingGUI(InventoryPlayer invPlayer,SolderingTE te) {
		super(new SolderingContainer(invPlayer,te));
		this.te = te;
		this.xSize = 176;
		this.ySize = 204;
	}

	FiaUIRect empty;
	FiaUIRect collision;
	@Override
	public void initGui() {
		super.initGui();
		empty = new FiaUIRect(this,6,67,8,8);
		collision = new FiaUIRect(this,74,66,10,10);
	}

	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		super.drawScreen(mouseX,mouseY,partialTicks);
		super.renderHoveredToolTip(mouseX,mouseY);
		drawElectricityInfo(this,mouseX,mouseY,guiLeft+152,guiTop+18,16,52,te.power,te.maxPower);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 17, guiTop + 63, 52, 16, te.tank);
		if (empty.isMouseIn(mouseX,mouseY))
			drawHoveringText(I18nUtil.resolveKey("gui.desc.voidcontents"),mouseX,mouseY);
		else if (collision.isMouseIn(mouseX,mouseY)) {
			List<String> list = new ArrayList<>();
			list.add(I18nUtil.resolveKey("gui.soldering.collision",te.collisionPrevention ? TextFormatting.GREEN+"ON" : TextFormatting.RED+"OFF"));
			list.add(TextFormatting.GRAY+I18nUtil.resolveKey("gui.soldering.collision.desc"));
			drawHoveringText(list,mouseX,mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException {
		super.mouseClicked(mouseX,mouseY,mouseButton);
		if (mouseButton == 0) {
			if (collision.isMouseIn(mouseX,mouseY)) {
				LeafiaPacket._start(te).__write(0,0).__sendToServer();
				playClick(1);
			} else if (empty.isMouseIn(mouseX,mouseY)) {
				LeafiaPacket._start(te).__write(0,1).__sendToServer();
				playClick(1);
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,int mouseY) {
		String name = I18nUtil.resolveKey("tile.machine_soldering.name");
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2 - 18, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY) {
		drawDefaultBackground();
		LeafiaGls.color(1,1,1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
		if (te.collisionPrevention)
			drawTexturedModalRect(collision.absX(),collision.absY(),192,14,collision.w,collision.h);

		// thanks alcater
		int p = (int) (te.power * 52 / Math.max(te.getMaxPower(), 1));
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - p, 176, 52 - p, 16, p);

		int i = te.progress * 33 / Math.max(te.processTime, 1);
		drawTexturedModalRect(guiLeft + 72, guiTop + 28, 192, 0, i, 14);

		if(te.power >= te.consumption)
			drawTexturedModalRect(guiLeft + 156, guiTop + 4, 176, 52, 9, 12);

		FFUtils.drawLiquid(te.tank, guiLeft, guiTop, this.zLevel, 52, 16, 17, 107);
	}
}
