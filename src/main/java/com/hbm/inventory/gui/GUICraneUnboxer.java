package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerCraneUnboxer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GUICraneUnboxer extends GuiInfoContainer {

    private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crane_unboxer.png");
    private TileEntityCraneUnboxer unboxer;

    public GUICraneUnboxer(InventoryPlayer invPlayer, TileEntityCraneUnboxer tedf) {
        super(new ContainerCraneUnboxer(invPlayer, tedf));
        unboxer = tedf;

        this.xSize = 176;
        this.ySize = 185;
    }

    @Override
    public void drawScreen(int x, int y, float interp) {
        super.drawScreen(x, y, interp);
        super.renderHoveredToolTip(x, y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String name = this.unboxer.hasCustomInventoryName() ? this.unboxer.getInventoryName() : I18n.format(this.unboxer.getInventoryName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float interp, int i, int j) {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
