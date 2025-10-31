package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerCore;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityCore;
import com.leafia.transformer.LeafiaGls;
import com.llib.math.SIPfx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GUICore extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/dfc/gui_core.png");
	private TileEntityCore core;
	
	public GUICore(InventoryPlayer invPlayer, TileEntityCore tedf) {
		super(new ContainerCore(invPlayer, tedf));
		core = tedf;
		
		this.xSize = 176;
		this.ySize = 204;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 7, 16, 78, core.tanks[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 7, 16, 78, core.tanks[1]);
/*
		String[] heat = new String[] { "Heat Saturation: " + core.heat + "%" };
		String[] field = new String[] { "Restriction Field: " + core.field + "%" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 53, guiTop + 97, 70, 4, mouseX, mouseY, heat);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 53, guiTop + 101, 70, 4, mouseX, mouseY, field);*/

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 7, guiTop + 98, 70, 4, mouseX, mouseY,
				new String[]{"Temperature: "+((core.temperature >= TileEntityCore.failsafeLevel) ? "ERROR" : String.format("%01.1f",core.temperature)+"°C"+"§8 / "+core.meltingPoint)});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 7, guiTop + 102, 70, 4, mouseX, mouseY,
				new String[]{"Stabilization: "+Math.round(core.stabilization*100)+"%"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 84, guiTop + 98, 70, 4, mouseX, mouseY,
				new String[]{"Contained Energy: "+((core.containedEnergy >= TileEntityCore.failsafeLevel) ? "ERROR" : SIPfx.formatNoSpace("%01.3f",core.containedEnergy*1000,false)+"SPK")});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 84, guiTop + 102, 70, 4, mouseX, mouseY,
				new String[]{"Expelling Energy: "+String.format("%01.3f",core.expellingEnergy/10000)+"SPK/s"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 161, guiTop + 98, 8, 8, mouseX, mouseY,
				new String[]{"Potential: "+Math.round(core.potentialGain*100)+"%"});

		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		
		String name = this.core.hasCustomInventoryName() ? this.core.getInventoryName() : I18n.format(this.core.getInventoryName()).trim();
		this.fontRenderer.drawString(name, this.xSize - 8 - this.fontRenderer.getStringWidth(name), this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		/*
		int i = core.getHeatScaled(70);
		if(i > 70)
			i = 70;
		drawTexturedModalRect(guiLeft + 53, guiTop + 98, 0, 204, i, 4);
		
		int j = core.getFieldScaled(70);
		if(j > 70)
			j = 70;
		drawTexturedModalRect(guiLeft + 53, guiTop + 102, 0, 208, j, 4);
		 */
		if (core.temperature < core.meltingPoint)
			drawTexturedModalRect(guiLeft + 7, guiTop + 98, 0, 204,
					(int)MathHelper.clampedLerp(0,70,core.temperature/core.meltingPoint), 4);
		else
			drawTexturedModalRect(guiLeft+7,guiTop+98,0,224+4*Math.floorDiv(Math.floorMod(core.ticks,8),4),70,4);
		drawTexturedModalRect(guiLeft + 7, guiTop + 102, 0, 208,
				(int)MathHelper.clampedLerp(0,70,(core.getStabilizationDiv()-1)/10), 4);
		drawTexturedModalRect(guiLeft + 84, guiTop + 98, 0, 216,
				(int)MathHelper.clampedLerp(0,70,core.containedEnergy/1_000_000), 4); // 1MSPK ~ 1PSPK (= 5EHE)
		drawTexturedModalRect(guiLeft + 84, guiTop + 102, 0, 220,
				(int)MathHelper.clampedLerp(0,70,core.expellingEnergy/(1_000_000)), 4);
		LeafiaGls.inLocalSpace(()->{
			LeafiaGls.translate(guiLeft+165,guiTop+102,0);
			LeafiaGls.scale(2/5f);
			LeafiaGls.rotate((float)Math.min((core.potentialGain-1)/9,core.client_maxDial)*360,0,0,1);
			LeafiaGls.pushMatrix();
			LeafiaGls.translate(-2.5,-9.5,0);
			drawTexturedModalRect(0,0,176,0,5,12);
			LeafiaGls.popMatrix();
		});

		if(core.hasCore)
			drawTexturedModalRect(guiLeft + 70, guiTop + 29, 220, 0, 36, 36);

		FFUtils.drawLiquid(core.tanks[0], guiLeft, guiTop, zLevel, 16, 78, 8, 114);
		FFUtils.drawLiquid(core.tanks[1], guiLeft, guiTop, zLevel, 16, 78, 152, 114);
	}
}