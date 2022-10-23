package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineBoilerRTG;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineBoilerRTG;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineBoilerRTG extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_boiler_rtg.png");
	private TileEntityMachineBoilerRTG rtgBoiler;
	
	public GUIMachineBoilerRTG(InventoryPlayer invPlayer, TileEntityMachineBoilerRTG tedf) {
		super(new ContainerMachineBoilerRTG(invPlayer, tedf));
		rtgBoiler = tedf;

		this.xSize = 176;
		this.ySize = 168;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		TileEntityMachineBoilerRTG dud = rtgBoiler;

		if(rtgBoiler.isInvalid() && rtgBoiler.getWorld().getTileEntity(rtgBoiler.getPos()) instanceof TileEntityMachineBoilerRTG)
			dud = (TileEntityMachineBoilerRTG) rtgBoiler.getWorld().getTileEntity(rtgBoiler.getPos());

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 69 - 52, 16, 52, dud.tanks[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 69 - 52, 16, 52, dud.tanks[1]);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 84, guiTop + 16, 8, 18, mouseX, mouseY, new String[] { String.valueOf((int)((double)dud.heat / 100D)) + "°C"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 34, 18, 18, mouseX, mouseY, new String[] { "RTG Heat: "+dud.rtgPower });

		
		String[] text = new String[] { "Heat produced:",
				"  1.5°C/t",
				"  or 30°C/s",
				"Heat consumed:",
				"  0.15°C/t",
				"  or 3.0°C/s (base)",
				"  0.25°C/t",
				"  or 5.0°C/t (once boiling point is reached)",
				"  0.4°C/t",
				"  or 8.0°C/t (for every subsequent multiple of boiling point)" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = new String[] { "Boiling rate:",
				"  Base rate * amount of full multiples",
				"  of boiling points reached" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.rtgBoiler.hasCustomInventoryName() ? this.rtgBoiler.getInventoryName() : I18n.format(this.rtgBoiler.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		//<insert witty line here>
		TileEntityMachineBoilerRTG dud = rtgBoiler;

		if(rtgBoiler.getWorld().getTileEntity(rtgBoiler.getPos()) instanceof TileEntityMachineBoilerRTG)
			dud = (TileEntityMachineBoilerRTG) rtgBoiler.getWorld().getTileEntity(rtgBoiler.getPos());
		
		if(dud.rtgPower > 0)
			drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 0, 18, 18);

		int j = (int)dud.getHeatScaled(17);
		drawTexturedModalRect(guiLeft + 85, guiTop + 33 - j, 194, 16 - j, 6, j);

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		
		FFUtils.drawLiquid(dud.tanks[0], guiLeft, guiTop, this.zLevel, 16, 52, 44, 97);
		FFUtils.drawLiquid(dud.tanks[1], guiLeft, guiTop, this.zLevel, 16, 52, 116, 97);
	}
}
