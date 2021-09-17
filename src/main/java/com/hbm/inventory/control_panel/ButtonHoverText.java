package com.hbm.inventory.control_panel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonHoverText extends GuiButton {

	public String hoverText;
	
	public ButtonHoverText(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, String hoverText){
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		this.hoverText = hoverText;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
		String prev = this.displayString;
		if(this.isMouseOver())
			this.displayString = this.hoverText;
		super.drawButton(mc, mouseX, mouseY, partialTicks);
		this.displayString = prev;
	}

}
