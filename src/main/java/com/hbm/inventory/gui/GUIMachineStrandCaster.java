package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerMachineStrandCaster;
import com.hbm.inventory.material.Mats;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import com.hbm.util.I18nUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GUIMachineStrandCaster extends GuiInfoContainer {

    private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_strand_caster.png");
    private final TileEntityMachineStrandCaster caster;

    public GUIMachineStrandCaster(InventoryPlayer invPlayer, TileEntityMachineStrandCaster tile) {
        super(new ContainerMachineStrandCaster(invPlayer, tile));
        caster = tile;

        this.xSize = 176;
        this.ySize = 214;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawStackInfo(mouseX, mouseY, 16, 12);

        FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 82, guiTop + 13, 16, 24, caster.tanks[0], FluidRegistry.WATER);
        FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 82, guiTop + 64, 16, 24, caster.tanks[1], ModForgeFluids.SPENTSTEAM);

        super.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(
        I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawDefaultBackground();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(caster.isOn){
            int width = (int) (caster.getProgress() * 73);
            drawTexturedModalRect(guiLeft+51, guiTop+47, 176, 100, width, 9);
        }

        if (caster.amount != 0) {
            int targetHeight = Math.min((caster.amount) * 79 / caster.getCapacity(), 92);

            int hex = caster.type.moltenColor;
            Color color = new Color(hex);
            GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
            drawTexturedModalRect(guiLeft + 17, guiTop + 93 - targetHeight, 176, 89 - targetHeight, 34, targetHeight);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        FFUtils.drawLiquid(caster.tanks[0], guiLeft, guiTop, this.zLevel, 16, 24, 82, 66);
        FFUtils.drawLiquid(caster.tanks[1], guiLeft, guiTop, this.zLevel, 16, 24, 82, 117);
    }

    protected void drawStackInfo(int mouseX, int mouseY, int x, int y) {
        String[] list = new String[1];
        if (caster.type == null)
          list[0] = ChatFormatting.RED + "Empty";
        else
          list[0] = ChatFormatting.YELLOW + I18nUtil.resolveKey(caster.type.getTranslationKey()) + ": " + Mats.formatAmount(caster.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT));

        this.drawCustomInfoStat(mouseX, mouseY, guiLeft + x, guiTop + y, 36, 81, mouseX, mouseY, list);
    }
}
