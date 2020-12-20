package com.hbm.inventory.gui;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;

import com.hbm.inventory.container.ContainerCoreStabilizer;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GUICoreStabilizer extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_stabilizer.png");
	private TileEntityCoreStabilizer stabilizer;
    private GuiTextField field;
	
	public GUICoreStabilizer(EntityPlayer invPlayer, TileEntityCoreStabilizer tedf) {
		super(new ContainerCoreStabilizer(invPlayer, tedf));
		stabilizer = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	public void initGui() {

		super.initGui();

        Keyboard.enableRepeatEvents(true);
        this.field = new GuiTextField(0, this.fontRenderer, guiLeft + 75, guiTop + 57, 29, 12);
        this.field.setTextColor(-1);
        this.field.setDisabledTextColour(-1);
        this.field.setEnableBackgroundDrawing(false);
        this.field.setMaxStringLength(3);
        this.field.setText(String.valueOf(stabilizer.watts));
	}
	
	public void syncTextField(int watts){
		this.field.setText(String.valueOf(watts));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 17, 16, 52, stabilizer.power, TileEntityCoreStabilizer.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
        this.field.mouseClicked(x, y, i);

    	if(guiLeft + 124 <= x && guiLeft + 124 + 18 > x && guiTop + 52 < y && guiTop + 52 + 18 >= y) {
    		
    		if(NumberUtils.isCreatable(field.getText())) {
    			int j = MathHelper.clamp(Integer.parseInt(field.getText()), 1, 100);
    			field.setText(j + "");
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(stabilizer.getPos(), j, 0));
    		}
    	}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.stabilizer.hasCustomInventoryName() ? this.stabilizer.getInventoryName() : I18n.format(this.stabilizer.getInventoryName());
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(field.isFocused())
			drawTexturedModalRect(guiLeft + 71, guiTop + 53, 192, 4, 34, 16);

		drawTexturedModalRect(guiLeft + 71, guiTop + 45, 192, 0, stabilizer.watts * 34 / 100, 4);
		
		int i = (int) stabilizer.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 35, guiTop + 69 - i, 176, 52 - i, 16, i);
		
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
