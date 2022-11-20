package com.hbm.inventory.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerMachineReactorSmall;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUIMachineReactorSmall extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_experimental.png");
	private static ResourceLocation overlay = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_reactor_overlay_experimental.png");
	private TileEntityMachineReactorSmall diFurnace;
	private boolean toggleOverlay = false;

	public GUIMachineReactorSmall(InventoryPlayer invPlayer, TileEntityMachineReactorSmall tedf) {
		super(new ContainerMachineReactorSmall(invPlayer, tedf));
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
		
		String[] text1 = new String[] { "Raise/lower the control rods",
				"using the button next to the",
				"fluid gauges." };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);

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
		
		String[] text5 = new String[] { diFurnace.retracting ? "Raise control rods" : "Lower control rods"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 35, guiTop + 106, 18, 18, mouseX, mouseY, text5);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.diFurnace.hasCustomInventoryName() ? this.diFurnace.getInventoryName() : I18n.format(this.diFurnace.getInventoryName());
		
		this.fontRenderer.drawString(name, 121 - this.fontRenderer.getStringWidth(name) / 2, 6, 15066597);
		this.fontRenderer.drawString(I18n.format("container.inventory"), this.xSize-this.fontRenderer.getStringWidth(I18n.format("container.inventory"))-6, this.ySize - 96 + 2, 4210752);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 35 <= x && guiLeft + 35 + 16 > x && guiTop + 107 < y && guiTop + 107 + 16 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), diFurnace.retracting ? 0 : 1, 0));
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
			
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), c, 1));
    	}
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		if(toggleOverlay)
			Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
		else
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

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

		if(!diFurnace.retracting)
			drawTexturedModalRect(guiLeft + 35, guiTop + 107, 176, 0, 18, 18);
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);
		
		if(diFurnace.tanks[0].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(diFurnace.tanks[1].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 7);

		
		FFUtils.drawLiquid(diFurnace.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 6, 86);
		FFUtils.drawLiquid(diFurnace.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 24, 86);
		FFUtils.drawLiquid(diFurnace.tanks[2], guiLeft, guiTop, zLevel, 10, 16, 22, 152);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
	
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
    {
        super.keyTyped(p_73869_1_, p_73869_2_);
        
        if (p_73869_2_ == 56)
        {
            this.toggleOverlay = !this.toggleOverlay;
        }
        
    }
}
