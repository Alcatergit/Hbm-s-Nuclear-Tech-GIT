package com.hbm.inventory.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerReactorMultiblock;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GUIReactorMultiblock extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_large_experimental.png");
	private TileEntityMachineReactorLarge diFurnace;
	private boolean barGrabbed = false;

	public GUIReactorMultiblock(InventoryPlayer invPlayer, TileEntityMachineReactorLarge tedf) {
		super(new ContainerReactorMultiblock(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 6, guiTop + 5, 16, 52, diFurnace.tanks[0], diFurnace.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 24, guiTop + 5, 16, 52, diFurnace.tanks[1], diFurnace.tankTypes[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 22, guiTop + 107, 10, 16, diFurnace.tanks[2], diFurnace.tankTypes[2]);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 42, guiTop + 5, 4, 88, new String[] { "Core Temperature:", "   " + Math.round((diFurnace.coreHeat) * 0.00002 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 48, guiTop + 5, 4, 88, new String[] { "Hull Temperature:", "   " + Math.round((diFurnace.hullHeat) * 0.00001 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 115, guiTop + 17, 18, 90, new String[] { "Control Rods: " + diFurnace.rods + "%" });
		
		String[] text = new String[] { "Coolant will move heat from the core to",
				"the hull. Water will use that heat and",
				"generate steam.",
				"Water consumption rate:",
				" 100 mB/t",
				" 2000 mB/s",
				"Coolant consumption rate:",
				" 10 mB/t",
				" 200 mB/s",
				"Water next to the reactor's open",
				"sides will pour into the tank." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);

		String fuel = "";
		
		switch(diFurnace.type) {
		case URANIUM:
			fuel = "Uranium";
			break;
		case MOX:
			fuel = "MOX";
			break;
		case PLUTONIUM:
			fuel = "Plutonium";
			break;
		case SCHRABIDIUM:
			fuel = "Schrabidium";
			break;
		case THORIUM:
			fuel = "Thorium";
			break;
		default:
			fuel = "ERROR";
			break;
		}

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 100, guiTop + 16, 10, 88, new String[] {fuel+" Rod", "Fuel: "+( Math.round(100F * diFurnace.fuel / diFurnace.maxFuel))+"%"});
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 128, guiTop + 16, 10, 88, new String[] {"Depleted "+fuel+" Rod", "Depleted Fuel: "+( Math.round(100F * diFurnace.waste/ diFurnace.maxWaste))+"%"});
		
		String[] text0 = new String[] { diFurnace.rods > 0 ? "Reactor is ON" : "Reactor is OFF"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 35, guiTop + 107, 18, 18, mouseX, mouseY, text0);
		
		if(diFurnace.tanks[0].getFluidAmount() <= 0) {
			String[] text2 = new String[] { "Error: Water is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text2);
		}

		if(diFurnace.tanks[1].getFluidAmount() <= 0) {
			String[] text3 = new String[] { "Error: Coolant is required for",
					"the reactor to function properly!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text3);
		}

		String s = "0";
		
		if(diFurnace.tankTypes[2] == ModForgeFluids.steam){
			s = "1x";
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.hotsteam){
			s = "10x";
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.superhotsteam){
			s = "100x";
		}
		
		String[] text4 = new String[] { "Steam compression switch",
				"Current compression level: " + s};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 4, guiTop + 105, 16, 20, mouseX, mouseY, text4);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
    	
    	if(guiLeft + 114 <= x && guiLeft + 114 + 10 > x && guiTop + 16 < y && guiTop + 16 + 88 >= y) {
    		barGrabbed = true;
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			
			int rods = (y - (guiTop + 23)) * 100 / 77;
			
			if(rods < 0)
				rods = 0;
			
			if(rods > 100)
				rods = 100;
			
			rods = 100 - rods;
			
			//diFurnace.rods = rods;
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos(), rods, 0));
    	}
		
    	if(guiLeft + 5 <= x && guiLeft + 5 + 14 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			int c = 0;
			
			if(diFurnace.tankTypes[2] == ModForgeFluids.steam){
				diFurnace.tankTypes[2] = ModForgeFluids.hotsteam;
				c = 1;
			} else if(diFurnace.tankTypes[2] == ModForgeFluids.hotsteam){
				diFurnace.tankTypes[2] = ModForgeFluids.superhotsteam;
				c = 2;
			} else if(diFurnace.tankTypes[2] == ModForgeFluids.superhotsteam){
				diFurnace.tankTypes[2] = ModForgeFluids.steam;
				c = 0;
			}
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos(), c, 1));
    	}
    }
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if(barGrabbed){
			int rods = MathHelper.clamp((mouseY - (guiTop + 23)) * 100 / 77, 0, 100);
			rods = 100 - rods;
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos(), rods, 0));
		}
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		barGrabbed = false;
		super.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		this.fontRenderer.drawString("Size", 68, 10, 0x7F7F7F);
		this.fontRenderer.drawString(I18n.format("container.inventory"), this.xSize-this.fontRenderer.getStringWidth(I18n.format("container.inventory"))-6, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int k = diFurnace.rods;
		drawTexturedModalRect(guiLeft + 115, guiTop + 107 - 14 - (k * 76 / 100), 208, 36, 18, 14);

		int f = diFurnace.getRodsScaled(88);
		drawTexturedModalRect(guiLeft + 115, guiTop + 16, 200, 36+f, 8, 88-f);
		
		if(diFurnace.rods > 0)
			drawTexturedModalRect(guiLeft + 35, guiTop + 107, 176, 0, 18, 18);
		
		int q = diFurnace.getFuelScaled(88);
		drawTexturedModalRect(guiLeft + 101, guiTop + 16 + 88 - q, 184, 36, 8, q);
		
		int j = diFurnace.getWasteScaled(88);
		drawTexturedModalRect(guiLeft + 129, guiTop + 16 + 88 - j, 192, 36, 8, j);
		
		int s = diFurnace.size;
		
		if(s < 8)
			drawTexturedModalRect(guiLeft + 67, guiTop + 18, 208, 50 + s * 18, 22, 18);
		else
			drawTexturedModalRect(guiLeft + 67, guiTop + 18, 230, 50 + (s - 8) * 18, 22, 18);
		
		if(diFurnace.tankTypes[2] == ModForgeFluids.steam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 176, 18, 14, 18);
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.hotsteam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 190, 18, 14, 18);
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.superhotsteam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 204, 18, 14, 18);
		}
		
		if(diFurnace.hasCoreHeat()) {
			int i = diFurnace.getCoreHeatScaled(88);
			
			i = (int) Math.min(i, 88);
			
			drawTexturedModalRect(guiLeft + 42, guiTop + 94 - i, 176, 124-i, 4, i);
		}

		if(diFurnace.hasHullHeat()) {
			int i = diFurnace.getHullHeatScaled(88);
			
			i = (int) Math.min(i, 88);
			
			drawTexturedModalRect(guiLeft + 48, guiTop + 94 - i, 180, 124-i, 4, i);
		}

		if(diFurnace.tanks[0].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(diFurnace.tanks[1].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 7);


		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 6, 86);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 24, 86);
		FFUtils.drawLiquid(diFurnace.tanks[2], guiLeft, guiTop, zLevel, 10, 16, 22, 152);
	}
}
