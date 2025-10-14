package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.lib.RefStrings;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.machine.TileEntityCrucible;

import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderCrucible extends TileEntitySpecialRenderer<TileEntityCrucible> {
    
    public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava.png");

    @Override
    public boolean isGlobalRenderer(TileEntityCrucible te) {
        return true;
    }

    @Override
    public void render(TileEntityCrucible crucible, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        switch(crucible.getBlockMetadata() - BlockDummyable.offset) {
        case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
        case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
        case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
        case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
        }

        bindTexture(ResourceManager.crucible_tex);
        ResourceManager.crucible_heat.renderAll();
        
        if(!crucible.recipeStack.isEmpty() || !crucible.wasteStack.isEmpty()) {
            int totalCap = TileEntityCrucible.recipeZCapacity + TileEntityCrucible.wasteZCapacity;
            int totalMass = 0;

            for(MaterialStack stack : crucible.recipeStack) totalMass += stack.amount;
            for(MaterialStack stack : crucible.wasteStack) totalMass += stack.amount;

            double level = ((double) totalMass / (double) totalCap) * 0.875D;
    
            GL11.glPushMatrix();
            GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

            bindTexture(lava);
            Tessellator tess = new Tessellator();
            tess.setNormal(0F, 1F, 0F);
            tess.setBrightness(240);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-1, 0.5 + level, -1, 0, 0);
            tess.addVertexWithUV(-1, 0.5 + level, 1, 0, 1);
            tess.addVertexWithUV(1, 0.5 + level, 1, 1, 1);
            tess.addVertexWithUV(1, 0.5 + level, -1, 1, 0);
            tess.draw();
            
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        
        GL11.glPopMatrix();
    }
}
