package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineFluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineFluidTank extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_tank.png");
	private TileEntityMachineFluidTank tank;

	public GUIMachineFluidTank(InventoryPlayer invPlayer, TileEntityMachineFluidTank tedf) {
		super(new ContainerMachineFluidTank(invPlayer, tedf));
		tank = tedf;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		// tank.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 71, guiTop +
		// 69 - 52, 34, 52);
		this.renderTankInfo(mouseX, mouseY, guiLeft + 71, guiTop + 69 - 52, 34, 52);

		String[] text = new String[] { "Inserting a fuse into the marked", "slot will set the tank to output mode" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tank.hasCustomInventoryName() ? this.tank.getInventoryName() : I18n.format(this.tank.getInventoryName());

		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(tank.dna())
			drawTexturedModalRect(guiLeft + 152, guiTop + 53, 176, 0, 16, 16);

		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		FFUtils.drawLiquid(tank.tank, this.guiLeft, this.guiTop, this.zLevel, 34, 52, 71, 97);
		this.mc.getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft + 71, guiTop + 17, 0, 204, 34, 52);

		// Minecraft.getMinecraft().getTextureManager().bindTexture(tank.tank.getSheet());
		// tank.tank.renderTank(this, guiLeft + 71, guiTop + 69,
		// tank.tank.getTankType().textureX() * FluidTank.x,
		// tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		// tank.tank.renderTank(this, guiLeft + 71 + 16, guiTop + 69,
		// tank.tank.getTankType().textureX() * FluidTank.x,
		// tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		// tank.tank.renderTank(this, guiLeft + 71 + 32, guiTop + 69,
		// tank.tank.getTankType().textureX() * FluidTank.x,
		// tank.tank.getTankType().textureY() * FluidTank.y, 2, 52);
	}


	public void renderTankInfo(int mouseX, int mouseY, int x, int y, int width, int height) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			/*if(tank.tank.getFluid() != null) {
				this.drawFluidInfo(new String[] { I18n.format(tank.tank.getInfo().fluid.getUnlocalizedName()), tank.tank.getFluidAmount() + "/" + tank.tank.getCapacity() + "mB" }, mouseX, mouseY);
			} else {
				this.drawFluidInfo(new String[] { I18n.format("None"), "0/" + tank.tank.getCapacity() + "mB" }, mouseX, mouseY);
			}*/
			FFUtils.renderTankInfo(this, mouseX, mouseY, x, y, width, height, tank.tank);

		}
	}
}
