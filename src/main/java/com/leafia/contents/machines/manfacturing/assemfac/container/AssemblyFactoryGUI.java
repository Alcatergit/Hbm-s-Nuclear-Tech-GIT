package com.leafia.contents.machines.manfacturing.assemfac.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.leafia.contents.machines.manfacturing.assemfac.AssemblyFactoryTE;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class AssemblyFactoryGUI extends GuiInfoContainer {
	static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID+":textures/gui/processing/gui_assemfac.png");
	static final ResourceLocation tex2 = new ResourceLocation(RefStrings.MODID+":textures/gui/processing/gui_chemfac.png");
	AssemblyFactoryTE te;
	public AssemblyFactoryGUI(InventoryPlayer playerInv,AssemblyFactoryTE te) {
		super(new AssemblyFactoryContainer(playerInv,te));
		this.xSize = 256;
		this.ySize = 256;
		this.te = te;
	}

	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		super.drawScreen(mouseX,mouseY,partialTicks);
		super.renderHoveredToolTip(mouseX,mouseY);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 234, guiTop + 164, 16, 52, te.power, te.getMaxPower());
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 233-24, guiTop + 108+73, 9, 54, te.water.getTank(), ModForgeFluids.COOLANT);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 242-24, guiTop + 108+73, 9, 54, te.steam.getTank(), ModForgeFluids.HOTCOOLANT);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY) {
		drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		Minecraft.getMinecraft().getTextureManager().bindTexture(tex2);
		int p = (int) (te.power * 52 / te.getMaxPower());
		drawTexturedModalRect(guiLeft + 234, guiTop + 216 - p, 0, 219 - p, 16, p);

		if (te.power > 0)
			drawTexturedModalRect(guiLeft + 238, guiTop + 150, 0, 219, 9, 12);

		FFUtils.drawLiquid(te.water.getTank(), guiLeft, guiTop, this.zLevel, 7, 52, 234-24, 189+73);
		FFUtils.drawLiquid(te.steam.getTank(), guiLeft, guiTop, this.zLevel, 7, 52, 243-24, 189+73);

		if (Keyboard.isKeyDown(Keyboard.KEY_LMENU))
			for (int i = 0; i < this.inventorySlots.inventorySlots.size(); i++) {
				Slot s = this.inventorySlots.getSlot(i);

				this.fontRenderer.drawStringWithShadow(i + "", guiLeft + s.xPos + 2, guiTop + s.yPos, 0xffffff);
				this.fontRenderer.drawStringWithShadow(s.getSlotIndex() + "", guiLeft + s.xPos + 2, guiTop + s.yPos + 8, 0xff8080);
			}
	}
}
