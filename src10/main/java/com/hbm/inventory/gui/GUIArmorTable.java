package com.hbm.inventory.gui;

import com.hbm.handler.ArmorModHandler;
import com.hbm.inventory.container.ContainerArmorTable;
import com.hbm.lib.RefStrings;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIArmorTable extends GuiContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_armor_modifier.png");
	public int left;
	public int top;

	public GUIArmorTable(InventoryPlayer player) {
		super(new ContainerArmorTable(player));

		this.xSize = 176 + 22;
		this.ySize = 222;

		guiLeft = (this.width - this.xSize) / 2;
		guiTop = (this.height - this.ySize) / 2;
	}

	protected void drawGuiContainerForegroundLayer(int mX, int mY) {
		String name = I18n.format("container.armorTable");
		this.fontRenderer.drawString(name, (this.xSize - 22) / 2 - this.fontRenderer.getStringWidth(name) / 2 + 22, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8 + 22, this.ySize - 96 + 2, 4210752);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	protected void drawGuiContainerBackgroundLayer(float inter, int mX, int mY) {
		super.drawDefaultBackground();
		GlStateManager.color(1, 1, 1, 1);
		this.mc.getTextureManager().bindTexture(texture);

		this.drawTexturedModalRect(guiLeft + 22, guiTop, 0, 0, this.xSize - 22, this.ySize);
		this.drawTexturedModalRect(guiLeft, guiTop + 31, 176, 96, 22, 100);

		ItemStack armor = this.inventorySlots.getSlot(ArmorModHandler.MOD_SLOTS).getStack();

		if(armor != ItemStack.EMPTY) {

			if(armor.getItem() instanceof ItemArmor)
				this.drawTexturedModalRect(guiLeft + 41 + 22, guiTop + 60, 176, 74, 22, 22);
			else
				this.drawTexturedModalRect(guiLeft + 41 + 22, guiTop + 60, 176, 52, 22, 22);
		} else {

			if(System.currentTimeMillis() % 1000 < 500)
				this.drawTexturedModalRect(guiLeft + 41 + 22, guiTop + 60, 176, 52, 22, 22);
		}
		
		for(int i = 0; i < ArmorModHandler.MOD_SLOTS; i++) {
			Slot slot = this.inventorySlots.getSlot(i);
			drawIndicator(i, slot.xPos - 1, slot.yPos - 1);
		}
	}

	private void drawIndicator(int index, int x, int y) {
		ItemStack mod = this.inventorySlots.getSlot(index).getStack();
		ItemStack armor = this.inventorySlots.getSlot(ArmorModHandler.MOD_SLOTS).getStack();

		if(mod.isEmpty())
			return;

		if(ArmorModHandler.isApplicable(armor, mod)) {
			this.drawTexturedModalRect(guiLeft + x, guiTop + y, 176, 34, 18, 18);
		} else {
			this.drawTexturedModalRect(guiLeft + x, guiTop + y, 176, 16, 18, 18);
		}
	}
}