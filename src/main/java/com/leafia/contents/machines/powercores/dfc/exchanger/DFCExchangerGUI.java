package com.leafia.contents.machines.powercores.dfc.exchanger;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.gui.FiaUIRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class DFCExchangerGUI extends GuiInfoContainer {
	final DFCExchangerTE te;
	static final ResourceLocation rsc = new ResourceLocation(RefStrings.MODID,"textures/gui/dfc/gui_exchanger.png");
	public DFCExchangerGUI(EntityPlayer player,DFCExchangerTE te) {
		super(new DFCExchangerContainer(player,te));
		xSize = 176;
		ySize = 184;
		this.te = te;
	}

	GuiTextField[] fields = new GuiTextField[3];
	FiaUIRect amountHover;
	FiaUIRect cycleHover;
	FiaUIRect saveBtn;
	int amount = 0;
	int delay = 0;
	int compression = 0;
	void updateTextBoxes() {
		if (te.amountToHeat != amount) {
			amount = te.amountToHeat;
			fields[0].setText(Integer.toString(amount));
		}
		if (te.tickDelay != delay) {
			delay = te.tickDelay;
			fields[1].setText(Integer.toString(delay));
		}
		if (te.compression != compression) {
			compression = te.compression;
			fields[2].setText(Integer.toString(compression));
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		saveBtn = new FiaUIRect(this,115,66,18,18);
		amountHover = new FiaUIRect(this,70,22,36,18);
		cycleHover = new FiaUIRect(this,70,40,36,18);
		fields[0] = new GuiTextField(0, this.fontRenderer, guiLeft + 72 + 2, guiTop + 24 + 4, 30, 10);
		fields[1] = new GuiTextField(0, this.fontRenderer, guiLeft + 72 + 2, guiTop + 42 + 4, 24, 10);
		fields[2] = new GuiTextField(0, this.fontRenderer, guiLeft + 81 + 9, guiTop + 68 + 4, 24, 10);
		for (GuiTextField field : fields) {
			field.setTextColor(0x5BBC00);
			field.setDisabledTextColour(0x499500);
			field.setEnableBackgroundDrawing(false);
			field.setText("0");
		}
		fields[0].setMaxStringLength(5);
		fields[1].setMaxStringLength(4);
		fields[2].setMaxStringLength(2);
		updateTextBoxes();
	}

	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		super.drawScreen(mouseX,mouseY,partialTicks);
		super.renderHoveredToolTip(mouseX,mouseY);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 24, guiTop + 30, 16, 52, te.input, te.inputFluid);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 136, guiTop + 30, 16, 52, te.output, te.outputFluid);
		if (amountHover.isMouseIn(mouseX,mouseY))
			super.drawHoveringText(I18nUtil.resolveKey("gui.heatex.amount"),mouseX,mouseY);
		if (cycleHover.isMouseIn(mouseX,mouseY))
			super.drawHoveringText(I18nUtil.resolveKey("gui.heatex.cycle"),mouseX,mouseY);
	}
	int saveButtonCoolDown = 0;
	@Override
	protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException {
		super.mouseClicked(mouseX,mouseY,mouseButton);
		for (GuiTextField field : fields)
			field.mouseClicked(mouseX,mouseY,mouseButton);
		if (mouseButton == 0 && saveBtn.isMouseIn(mouseX,mouseY) && saveButtonCoolDown == 0) {
			try {
				int value = Integer.parseInt(fields[2].getText());
				if (value <= 0) {
					playDenied();
					return;
				}
				LeafiaPacket._start(te).__write(2,value).__sendToServer();
				playClick(1);
				saveButtonCoolDown = 20;
			} catch (NumberFormatException ignored) {
				playDenied();
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.te.hasCustomInventoryName() ? this.te.getInventoryName() : I18n.format(this.te.getInventoryName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY) {
		drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(rsc);
		drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);
		if (saveButtonCoolDown > 0) {
			saveButtonCoolDown--;
			drawTexturedModalRect(guiLeft+115,guiTop+66,176,14,18,18);
		}
		if (fields[0].isFocused())
			drawTexturedModalRect(guiLeft+72,guiTop+24,176,0,32,14);
		if (fields[1].isFocused())
			drawTexturedModalRect(guiLeft+72,guiTop+42,176,0,32,14);
		if (fields[2].isFocused())
			drawTexturedModalRect(guiLeft+81,guiTop+68,176,0,32,14);
		for (GuiTextField field : fields)
			field.drawTextBox();
		FFUtils.drawLiquid(te.input, guiLeft, guiTop, zLevel, 16, 52, 24, 31+80);
		FFUtils.drawLiquid(te.output, guiLeft, guiTop, zLevel, 16, 52, 136, 31+80);
		updateTextBoxes();
	}
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		for (GuiTextField field : fields)
			field.textboxKeyTyped(typedChar,keyCode);
		super.keyTyped(typedChar,keyCode);
		if (fields[0].isFocused()) {
			try {
				int value = Integer.parseInt(fields[0].getText());
				LeafiaPacket._start(te).__write(0,value).__sendToServer();
			} catch (NumberFormatException ignored) {}
		}
		if (fields[1].isFocused()) {
			try {
				int value = Integer.parseInt(fields[1].getText());
				LeafiaPacket._start(te).__write(1,value).__sendToServer();
			} catch (NumberFormatException ignored) {}
		}
	}
}
