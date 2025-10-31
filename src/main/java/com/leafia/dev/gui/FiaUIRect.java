package com.leafia.dev.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public class FiaUIRect {
	public int guiLeft;
	public int guiTop;
	public int x;
	public int y;
	public int w;
	public int h;
	public FiaUIRect(int x,int y,int w,int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	public FiaUIRect(GuiContainer gui,int x,int y,int w,int h) {
		this(x,y,w,h);
		updateGuiPosition(gui);
	}
	public FiaUIRect updateGuiPosition(GuiContainer gui) {
		guiLeft = gui.guiLeft;
		guiTop = gui.guiTop;
		return this;
	}
	public FiaUIRect(GuiScreenLeafia gui,int x,int y,int w,int h) {
		this(x,y,w,h);
		updateGuiPosition(gui);
	}
	public FiaUIRect updateGuiPosition(GuiScreenLeafia gui) {
		guiLeft = gui.guiLeft;
		guiTop = gui.guiTop;
		return this;
	}
	public FiaUIRect updateGuiPosition(int guiLeft,int guiTop) {
		this.guiLeft = guiLeft;
		this.guiTop = guiTop;
		return this;
	}
	public boolean isMouseIn(int mouseX,int mouseY) {
		return guiLeft+x <= mouseX && mouseX <= guiLeft+x+w && guiTop+y <= mouseY && mouseY <= guiTop+y+h;
	}
	public int absX() {
		return guiLeft+x;
	}
	public int absY() {
		return guiTop+y;
	}
}
