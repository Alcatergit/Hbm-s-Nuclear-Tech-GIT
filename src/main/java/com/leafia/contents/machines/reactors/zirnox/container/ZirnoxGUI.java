package com.leafia.contents.machines.reactors.zirnox.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.dev.container_utility.LeafiaPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class ZirnoxGUI extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/leafia_zirnox.png");
	private static ResourceLocation texture_02 = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/leafia_zirnox_02.png");

	private ZirnoxTE entity;
	private boolean barGrabbed = false;
	private boolean valveOpen = false;

	public ZirnoxGUI(InventoryPlayer invPlayer, ZirnoxTE entity) {
		super(new ZirnoxContainer(invPlayer, entity));
		this.entity = entity;
		this.xSize = 215;
		this.ySize = 243;
	}

	static enum ui {
		tankFeedwater(193,27,16,52),
		tankSteam(179,128,10,16),
		barHullTemp(24,25,4,88),
		barControlRod(181,28,8,90),
		valve(11,123,26,26),
		gaugePressure(5,26,18,18),
		gaugeCoolant(5,65,18,12);
		int x;
		int y;
		int width;
		int height;
		ui(int x,int y,int width,int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
	boolean isHovering(int mouseX, int mouseY, ui element) {
		return ((mouseX >= element.x+guiLeft) && (mouseX <= element.x+element.width+guiLeft) && (mouseY >= element.y+guiTop) && (mouseY <= element.y+element.height+guiTop));
	}
	void drawTankInfo(int mouseX, int mouseY, ui element, FluidTank tank, Fluid type) {
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft+element.x,guiTop+element.y-1,element.width,element.height,tank,type);
	}
	void drawInfo(int mouseX, int mouseY, ui element, String[] desc) {
		this.drawCustomInfo(this,mouseX,mouseY,guiLeft+element.x,guiTop+element.y-1,element.width,element.height,desc);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX,mouseY,f);

		drawTankInfo(mouseX,mouseY,ui.tankFeedwater,entity.tanks[0],entity.tankTypes[0]);
		drawTankInfo(mouseX,mouseY,ui.tankSteam,entity.tanks[2],entity.tankTypes[2]);
		drawInfo(mouseX,mouseY,ui.barHullTemp,I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.temp.hull",entity.hulltemp,(byte)1));
		this.drawCustomInfo(this,mouseX,mouseY,guiLeft+ui.barControlRod.x,guiTop+ui.barControlRod.y-1,ui.barControlRod.width+4,ui.barControlRod.height,I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.controlrods",entity.rods,(byte)0));
		drawInfo(mouseX,mouseY,ui.valve,I18nUtil.resolveKeyArray("desc.leafia.zirnox.vent"));
		drawInfo(mouseX,mouseY,ui.gaugePressure,I18nUtil.leafia.statusDecimals("desc.leafia.zirnox.pressure",entity.pressure,(byte)1));
		drawTankInfo(mouseX,mouseY,ui.gaugeCoolant,entity.tanks[1],entity.tankTypes[1]);

		String[] text = I18nUtil.resolveKeyArray("desc.leafia.zirnox.tips.coolant");
		this.drawCustomInfoStat(mouseX,mouseY,guiLeft-16,guiTop+36,16,16,guiLeft-8,guiTop+36+16,text);

		String[] text1 = I18nUtil.resolveKeyArray("desc.leafia.zirnox.tips.pressure");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);

		String[] text0 = I18nUtil.resolveKeyArray("desc.leafia._repeated."+((entity.rods != 0) ? "reactors.on" : "reactors.off"));
		this.drawCustomInfoStat(mouseX,mouseY,guiLeft+176,guiTop+6-1,18,19,mouseX,mouseY,text0);
		
		if(entity.tanks[0].getFluidAmount() <= 0) {
			String[] text2 = I18nUtil.resolveKeyArray(
					"desc.leafia._repeated.reactors.require.properly",
					I18nUtil.resolveKey("tile.water.name")
			);
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text2);
		}

		if(entity.tanks[1].getFluidAmount() < 4000) {
			String[] text3 = I18nUtil.resolveKeyArray(
					"desc.leafia._repeated.reactors.require.properly",
					I18nUtil.resolveKey("desc.leafia._short.fluid.co2")
			);
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 32 + 16, text3);
		}
		
		String[] text4 = I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.compression",(short)Math.pow(10,entity.compression),(byte)0);
		this.drawCustomInfoStat(mouseX,mouseY,guiLeft+162,guiTop+128-1,16,20,mouseX,mouseY,text4);
		super.renderHoveredToolTip(mouseX,mouseY);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
    	if (barGrabbed || valveOpen) return;
    	if(guiLeft + 180 <= x && guiLeft + 180 + 10 > x && guiTop + 28 < y && guiTop + 28 + 88 >= y) {
    		barGrabbed = true;
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			
			int rods = (y - (guiTop + 28-2)) * 100 / 88;
			
			if(rods < 0)
				rods = 0;
			
			if(rods > 100)
				rods = 100;
			
			rods = 100 - rods;
			
			//diFurnace.rods = rods;
			
    		//PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(entity.getPos(), rods, 0));
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.CONTROL_RODS.key,(byte)rods)
					.__sendToServer();
    	}
		
    	if(guiLeft + 162 <= x && guiLeft + 162 + 14 > x && guiTop + 127 < y && guiTop + 127 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			
    		//PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(entity.getPos(), c, 3));
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.COMPRESSION.key,(byte)((entity.compression+1)%4))
					.__sendToServer();
    	}

		if (isHovering(x,y,ui.valve)) {
			valveOpen = true;
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.OPENVALVE.key,true)
					.__sendToServer();
		}
    }
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		if(barGrabbed){
			int rods = MathHelper.clamp((mouseY - (guiTop + 28-2)) * 100 / 88, 0, 100);
			rods = 100 - rods;
			//PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(entity.getPos(), rods, 0));
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.CONTROL_RODS.key,(byte)rods)
					.__sendToServer();
		}
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		barGrabbed = false;
		if (valveOpen)
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.OPENVALVE.key,false)
					.__sendToServer();
		valveOpen = false;
		super.mouseReleased(mouseX, mouseY, state);
	}
	@Override
	public void onGuiClosed() {
		if (valveOpen)
			LeafiaPacket._start(entity)
					.__write(ZirnoxTE.packetKeys.OPENVALVE.key,false)
					.__sendToServer();
		valveOpen = false;
		super.onGuiClosed();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.entity.hasCustomInventoryName() ? this.entity.getInventoryName() : I18n.format(this.entity.getInventoryName());

		this.fontRenderer.drawString(name, 107 - this.fontRenderer.getStringWidth(name) / 2, 4, 15066597);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 26, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,143);
		drawTexturedModalRect(guiLeft+19,guiTop+143,19,143,176,ySize-143);

		// "Hull Temperature" bar
		int pix = (int)Math.floor(Math.min((entity.hulltemp-20)/(entity.meltingPoint-20),1)*88);
		drawTexturedModalRect(guiLeft+25,guiTop+115-pix,246,172-pix,4,pix);

		// Control Rod
		float col = isHovering(mouseX,mouseY,ui.barControlRod) ? 0.85F : 1F;
		pix = (int)Math.floor((1-entity.rods/100f)*88);
		GL11.glColor4f(col,col,col,1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft+181,guiTop+28,238,172-pix,8,pix);
		pix = (int)Math.floor((1-entity.rodsTarget/100f)*88);
		GL11.glColor4f(col,col,col,0.25F);
		drawTexturedModalRect(guiLeft+181,guiTop+28,238,172-pix,8,pix);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		if (entity.rods > 0) // Indicator Light
			drawTexturedModalRect(guiLeft+175,guiTop+10,233,239,23,17);

		// CO2 Gauge
		drawTexturedModalRect(guiLeft+5,guiTop+65,238,
				(int)Math.floor(entity.tanks[1].getFluidAmount()/(float)entity.tanks[1].getCapacity()*6f+0.5f)*12,
				18,12);

		// Pressure Dial
		if (entity.pressure < entity.maxPressure)
			drawTexturedModalRect(guiLeft+5,guiTop+26,220,18*(int)Math.floor(entity.pressure/entity.maxPressure*12+0.5),18,18);
		else
			drawTexturedModalRect(guiLeft+5,guiTop+26,202+18*entity.dialX,198+18* entity.dialY,18,18);


		// PAGE 2
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture_02);
		// Valve
		col = isHovering(mouseX,mouseY,ui.valve) ? 1F : 0.8F;
		GL11.glColor4f(col,col,col,1.0F);
		drawTexturedModalRect(guiLeft+11,guiTop+123,0,38+26*entity.valveLevel,26,26);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);

		// Compression
		drawTexturedModalRect(guiLeft+162,guiTop+127,entity.compression%2*14,entity.compression/2*19,14,19);

		// Control Rods
		int level = (int)Math.min(Math.floor(entity.rods/20f),4)-1;
		if (level >= 0)
			drawTexturedModalRect(guiLeft+55,guiTop+24,28+level%2*104,level/2*104,104,104);




		/*
		int k = entity.rods;
		drawTexturedModalRect(guiLeft + 115, guiTop + 107 - 14 - (k * 76 / 100), 208, 36, 18, 14);

		int f = entity.getRodsScaled(88);
		drawTexturedModalRect(guiLeft + 115, guiTop + 16, 200, 36+f, 8, 88-f);
		
		if(entity.rods > 0)
			drawTexturedModalRect(guiLeft + 35, guiTop + 107, 176, 0, 18, 18);
		
		int q = entity.getFuelScaled(88);
		drawTexturedModalRect(guiLeft + 101, guiTop + 16 + 88 - q, 184, 36, 8, q);
		
		int j = entity.getWasteScaled(88);
		drawTexturedModalRect(guiLeft + 129, guiTop + 16 + 88 - j, 192, 36, 8, j);
		
		int s = entity.size;
		
		if(s < 8)
			drawTexturedModalRect(guiLeft + 67, guiTop + 18, 208, 50 + s * 18, 22, 18);
		else
			drawTexturedModalRect(guiLeft + 67, guiTop + 18, 230, 50 + (s - 8) * 18, 22, 18);
		
		if(entity.tankTypes[2] == ModForgeFluids.steam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 176, 18, 14, 18);
		} else if(entity.tankTypes[2] == ModForgeFluids.hotsteam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 190, 18, 14, 18);
		} else if(entity.tankTypes[2] == ModForgeFluids.superhotsteam){
			drawTexturedModalRect(guiLeft + 5, guiTop + 107, 204, 18, 14, 18);
		}
		
		if(entity.hasCoreHeat()) {
			int i = entity.getCoreHeatScaled(88);
			
			i = (int) Math.min(i, 88);
			
			drawTexturedModalRect(guiLeft + 42, guiTop + 94 - i, 176, 124-i, 4, i);
		}

		if(entity.hasHullHeat()) {
			int i = entity.getHullHeatScaled(88);
			
			i = (int) Math.min(i, 88);
			
			drawTexturedModalRect(guiLeft + 48, guiTop + 94 - i, 180, 124-i, 4, i);
		}*/
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 3);

		if(entity.tanks[0].getFluidAmount() <= 0)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32, 16, 16, 6);
		
		if(entity.tanks[1].getFluidAmount() < 4000)
			this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 32 + 16, 16, 16, 7);


		FFUtils.drawLiquid(entity.tanks[0], guiLeft, guiTop, zLevel, 16, 52, 6+187, 86+21);
		//FFUtils.drawLiquid(entity.tanks[1], guiLeft, guiTop, zLevel, 16, 52, 24, 86);
		FFUtils.drawLiquid(entity.tanks[2], guiLeft, guiTop, zLevel, 10, 16, 22+157, 152+20);
	}
}
