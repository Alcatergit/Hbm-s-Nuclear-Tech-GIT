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

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 88 - 52, 16, 52, diFurnace.tanks[0], diFurnace.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 88 - 52, 16, 52, diFurnace.tanks[1], diFurnace.tankTypes[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 108, 88, 4, diFurnace.tanks[2], diFurnace.tankTypes[2]);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 114, 88, 4, new String[] { "Hull Temperature:", "   " + Math.round((diFurnace.hullHeat) * 0.00001 * 980 + 20) + "°C" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 120, 88, 4, new String[] { "Core Temperature:", "   " + Math.round((diFurnace.coreHeat) * 0.00002 * 980 + 20) + "°C" });

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 115, guiTop + 17, 18, 90, new String[] { "Operating Level: " + diFurnace.rods + "%" });
		
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

		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 98, guiTop + 18, 16, 88, new String[] { fuel + ": " + (diFurnace.fuel / TileEntityMachineReactorLarge.fuelMult) + "/" + (diFurnace.maxFuel / TileEntityMachineReactorLarge.fuelMult) + "ng" });
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 18, 16, 88, new String[] { "Depleted " + fuel + ": " + (diFurnace.waste / TileEntityMachineReactorLarge.fuelMult) + "/" + (diFurnace.maxWaste / TileEntityMachineReactorLarge.fuelMult) + "ng" });
		
		String[] text0 = new String[] { diFurnace.rods > 0 ? "Reactor is ON" : "Reactor is OFF"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 52, guiTop + 53, 18, 18, mouseX, mouseY, text0);
		
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
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 63, guiTop + 107, 14, 18, mouseX, mouseY, text4);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
    	
    	if(guiLeft + 115 <= x && guiLeft + 115 + 18 > x && guiTop + 17 < y && guiTop + 17 + 90 >= y) {
    		barGrabbed = true;
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			
			int rods = (y - (guiTop + 24)) * 100 / 76;
			
			if(rods < 0)
				rods = 0;
			
			if(rods > 100)
				rods = 100;
			
			rods = 100 - rods;
			
			//diFurnace.rods = rods;
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos(), rods, 0));
    	}
		
    	if(guiLeft + 63 <= x && guiLeft + 63 + 14 > x && guiTop + 107 < y && guiTop + 107 + 18 >= y) {
    		
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
			int rods = MathHelper.clamp((mouseY - (guiTop + 24)) * 100 / 76, 0, 100);
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
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int k = diFurnace.rods;
		drawTexturedModalRect(guiLeft + 115, guiTop + 107 - 14 - (k * 76 / 100), 208, 36, 18, 14);
		
		if(diFurnace.rods > 0)
			drawTexturedModalRect(guiLeft + 52, guiTop + 53, 212, 0, 18, 18);
		
		int q = diFurnace.getFuelScaled(88);
		drawTexturedModalRect(guiLeft + 98, guiTop + 106 - q, 176, 124 - q, 16, q);
		
		int j = diFurnace.getWasteScaled(88);
		drawTexturedModalRect(guiLeft + 134, guiTop + 106 - j, 192, 124 - j, 16, j);
		
		int s = diFurnace.size;
		
		if(s < 8)
			drawTexturedModalRect(guiLeft + 50, guiTop + 17, 208, 50 + s * 18, 22, 18);
		else
			drawTexturedModalRect(guiLeft + 50, guiTop + 17, 230, 50 + (s - 8) * 18, 22, 18);
		
		if(diFurnace.tankTypes[2] == ModForgeFluids.steam){
			drawTexturedModalRect(guiLeft + 63, guiTop + 107, 176, 18, 14, 18);
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.hotsteam){
			drawTexturedModalRect(guiLeft + 63, guiTop + 107, 190, 18, 14, 18);
		} else if(diFurnace.tankTypes[2] == ModForgeFluids.superhotsteam){
			drawTexturedModalRect(guiLeft + 63, guiTop + 107, 204, 18, 14, 18);
		}
		
		if(diFurnace.hasHullHeat()) {
			int i = diFurnace.getHullHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 114, 0, 226, i, 4);
		}
		
		if(diFurnace.hasCoreHeat()) {
			int i = diFurnace.getCoreHeatScaled(88);
			
			i = (int) Math.min(i, 160);
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 120, 0, 230, i, 4);
		}
		
		if(diFurnace.tanks[2].getFluidAmount() > 0) {
			int i = diFurnace.getSteamScaled(88);
			
			//i = (int) Math.min(i, 160);
			
			int offset = 234;
			
			if(diFurnace.tankTypes[2] == ModForgeFluids.steam){
			} else if(diFurnace.tankTypes[2] == ModForgeFluids.hotsteam){
				offset += 4;
			} else if(diFurnace.tankTypes[2] == ModForgeFluids.superhotsteam){
				offset += 8;
			}
			
			drawTexturedModalRect(guiLeft + 80, guiTop + 108, 0, offset, i, 4);
		}

		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 8, 116);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 26, 116);
	}
}
