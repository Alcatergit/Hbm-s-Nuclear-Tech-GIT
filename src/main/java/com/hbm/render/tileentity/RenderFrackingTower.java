package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.machine.oil.TileEntityMachineFrackingTower;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

import net.minecraft.util.ResourceLocation;

public class RenderFrackingTower extends TileEntitySpecialRenderer<TileEntityMachineFrackingTower> {

        @Override
    public boolean isGlobalRenderer(TileEntityMachineFrackingTower te)
    {
        return true;
    }

    @Override
    public void render(TileEntityMachineFrackingTower te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GlStateManager.rotate(180, 0F, 1F, 0F);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.fracking_tower_tex);
        ResourceManager.fracking_tower.renderAll();
        GlStateManager.shadeModel(GL11.GL_FLAT);

        GlStateManager.translate(0, 0.5, 0);

        bindTexture(ResourceManager.pipe_neo_tex);
        ResourceManager.pipe_neo.renderPart("pX");
        ResourceManager.pipe_neo.renderPart("nX");
        ResourceManager.pipe_neo.renderPart("pZ");
        ResourceManager.pipe_neo.renderPart("nZ");

        GlStateManager.enableCull();
        GlStateManager.popMatrix();


    }
}