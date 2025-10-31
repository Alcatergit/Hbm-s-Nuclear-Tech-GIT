package com.leafia.contents.machines.elevators.floors;

import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.gui.GuiScreenLeafia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class EvFloorGUI extends GuiScreenLeafia {
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/elevators/floor.png");
	final EvFloorTE te;
	private GuiTextField field;
	public EvFloorGUI(EvFloorTE te) {
		super();
		this.te = te;
		this.xSize = 66;
		this.ySize = 36;
	}
	public void initGui() {

		super.initGui();

		Keyboard.enableRepeatEvents(true);
		this.field = new GuiTextField(0, this.fontRenderer, guiLeft + 7+12, guiTop + 15+4, 24-4, 10);
		this.field.setTextColor(0x5BBC00);
		this.field.setDisabledTextColour(0x499500);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(2);
		this.field.setText(Integer.toString(te.floor));
	}
	int click = 0;
	@Override
	protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException {
		super.mouseClicked(mouseX,mouseY,mouseButton);
		field.mouseClicked(mouseX,mouseY,mouseButton);
		if (mouseX-guiLeft >= 43 && mouseY-guiTop >= 13 && mouseX-guiLeft <= 43+18 && mouseY-guiTop <= 13+18) {
			if (mouseButton == 0 && click <= 0) {
				try {
					int floor = Integer.parseInt(field.getText());
					click = 20;
					mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
					LeafiaPacket._start(te).__write(0,floor).__sendToServer();
					field.setText(Integer.toString(floor));
				} catch (NumberFormatException ignored) {
					mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundEvents.UI_BUTTON_INVALID, 1.0F));
				}
			}
		}
	}

	@Override
	protected void drawGuiScreenForegroundLayer(int i, int j) {
		String name = I18nUtil.resolveKey("gui.evfloor",te.floor);
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 4, 4210752);
	}
	@Override
	protected void drawGuiScreenBackgroundLayer(float v,int i,int i1) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if(field.isFocused())
			drawTexturedModalRect(guiLeft + 7, guiTop + 15, 84, 0, 32, 14);
		if (click > 0) {
			click--;
			drawTexturedModalRect(guiLeft+43,guiTop+13,66,0,18,18);
		}
		this.field.drawTextBox();
	}
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
	{
		if (this.field.textboxKeyTyped(p_73869_1_, p_73869_2_)) { }
		else {
			super.keyTyped(p_73869_1_, p_73869_2_);
		}
	}
}
