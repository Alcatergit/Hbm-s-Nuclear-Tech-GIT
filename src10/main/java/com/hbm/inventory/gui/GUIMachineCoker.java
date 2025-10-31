package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineCoker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Locale;

public class GUIMachineCoker extends GuiInfoContainer {

    private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_coker.png");
    private TileEntityMachineCoker refinery;

    public GUIMachineCoker(InventoryPlayer invPlayer, TileEntityMachineCoker tedf) {
        super(new ContainerMachineCoker(invPlayer, tedf));
        refinery = tedf;

        this.xSize = 176;
        this.ySize = 204;
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        FFUtils.renderTankInfo(this, x, y, guiLeft + 35, guiTop + 17, 16, 52, refinery.tanks[0]);
        FFUtils.renderTankInfo(this, x, y, guiLeft + 125, guiTop + 17, 16, 52, refinery.tanks[1]);

        this.drawCustomInfoStat(x, y, guiLeft + 60, guiTop + 45, 54, 7, x, y, new String[] { String.format(Locale.US, "%,d", refinery.progress) + " / " + String.format(Locale.US, "%,d", TileEntityMachineCoker.processTime) + "TU" });
        this.drawCustomInfoStat(x, y, guiLeft + 60, guiTop + 54, 54, 7, x, y, new String[] { String.format(Locale.US, "%,d", refinery.heat) + " / " + String.format(Locale.US, "%,d", TileEntityMachineCoker.maxHeat) + "TU" });

        super.renderHoveredToolTip(x, y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String name = this.refinery.hasCustomInventoryName() ? this.refinery.getInventoryName() : I18n.format(this.refinery.getInventoryName());

        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 0xC7C1A3);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int p = refinery.progress * 53 / TileEntityMachineCoker.processTime;
        drawTexturedModalRect(guiLeft + 61, guiTop + 46, 176, 0, p, 5);

        int h = refinery.heat * 52 / TileEntityMachineCoker.maxHeat;
        drawTexturedModalRect(guiLeft + 61, guiTop + 55, 176, 5, h, 5);

        FFUtils.drawLiquid(refinery.tanks[0], guiLeft, guiTop, this.zLevel, 16, 52, 35, 98);
        FFUtils.drawLiquid(refinery.tanks[1], guiLeft, guiTop, this.zLevel, 16, 52, 125, 98);
    }
}
