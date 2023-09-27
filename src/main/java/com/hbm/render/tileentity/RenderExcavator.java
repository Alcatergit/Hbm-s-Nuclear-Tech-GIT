package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.render.RenderHelper;
import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderExcavator extends TileEntitySpecialRenderer<TileEntityMachineExcavator> {
	
	public static final ResourceLocation cobble = new ResourceLocation("minecraft:textures/blocks/cobblestone.png");
	public static final ResourceLocation gravel = new ResourceLocation("minecraft:textures/blocks/gravel.png");

	@Override
	public boolean isGlobalRenderer(TileEntityMachineExcavator te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineExcavator drill, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.disableCull();
		
		switch(drill.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		GL11.glTranslated(0, -3, 0);
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.excavator_tex);
		ResourceManager.excavator.renderPart("Main");
		
		float crusher = drill.prevCrusherRotation + (drill.crusherRotation - drill.prevCrusherRotation) * partialTicks;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 2.0F, 2.8125F);
		GL11.glRotatef(-crusher, 1, 0, 0);
		GL11.glTranslatef(0.0F, -2.0F, -2.8125F);
		ResourceManager.excavator.renderPart("Crusher1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 2.0F, 2.1875F);
		GL11.glRotatef(crusher, 1, 0, 0);
		GL11.glTranslatef(0.0F, -2.0F, -2.1875F);
		ResourceManager.excavator.renderPart("Crusher2");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef(drill.prevDrillRotation + (drill.drillRotation - drill.prevDrillRotation) * partialTicks, 0F, -1F, 0F);
		float ext = drill.prevDrillExtension + (drill.drillExtension - drill.prevDrillExtension) * partialTicks;
		GL11.glTranslatef(0.0F, -ext, 0.0F);
		ResourceManager.excavator.renderPart("Drillbit");
		
		while(ext >= -1.5) {
			ResourceManager.excavator.renderPart("Shaft");
			GL11.glTranslated(0.0D, 2.0D, 0.0D);
			ext -= 2;
		}
		GL11.glPopMatrix();
		
		GlStateManager.shadeModel(GL11.GL_FLAT);
		
		if(drill.chuteTimer > 0) {
			bindTexture(cobble);
			double widthX = 0.125;
			double widthZ = 0.125;
			double speed = 250D;
			double dropU = -System.currentTimeMillis() % speed / speed;
			double dropL = dropU + 4;
			RenderHelper.startDrawingTexturedQuads();
			RenderHelper.addVertexWithUV(widthX, 3, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(-widthX, 3, 2.5 + widthZ, 1, dropU);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 1, dropL);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropL);

			RenderHelper.addVertexWithUV(-widthX, 3, 2.5 - widthZ, 1, dropU);
			RenderHelper.addVertexWithUV(widthX, 3, 2.5 - widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 - widthZ, 0, dropL);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 - widthZ, 1, dropL);

			RenderHelper.addVertexWithUV(-widthX, 3, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(-widthX, 3, 2.5 - widthZ, 1, dropU);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 - widthZ, 1, dropL);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 0, dropL);

			RenderHelper.addVertexWithUV(widthX, 3, 2.5 - widthZ, 1, dropU);
			RenderHelper.addVertexWithUV(widthX, 3, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropL);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 - widthZ, 1, dropL);
			RenderHelper.draw();

			boolean smoosh = drill.enableCrusher;
			widthX = smoosh ? 0.5 : 0.25;
			widthZ = 0.0625;
			double uU = smoosh ? 4 : 2;
			double uL = 0.5;
			bindTexture(smoosh ? gravel : cobble);
			RenderHelper.startDrawingTexturedQuads();
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 + widthZ, uU, dropU);
			RenderHelper.addVertexWithUV(-widthX, 1, 2.5 + widthZ, uU, dropL);
			RenderHelper.addVertexWithUV(widthX, 1, 2.5 + widthZ, 0, dropL);

			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 - widthZ, uU, dropU);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 - widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(widthX, 1, 2.5 - widthZ, 0, dropL);
			RenderHelper.addVertexWithUV(-widthX, 1, 2.5 - widthZ, uU, dropL);

			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(-widthX, 2, 2.5 - widthZ, uL, dropU);
			RenderHelper.addVertexWithUV(-widthX, 1, 2.5 - widthZ, uL, dropL);
			RenderHelper.addVertexWithUV(-widthX, 1, 2.5 + widthZ, 0, dropL);

			RenderHelper.addVertexWithUV(widthX, 2, 2.5 - widthZ, uL, dropU);
			RenderHelper.addVertexWithUV(widthX, 2, 2.5 + widthZ, 0, dropU);
			RenderHelper.addVertexWithUV(widthX, 1, 2.5 + widthZ, 0, dropL);
			RenderHelper.addVertexWithUV(widthX, 1, 2.5 - widthZ, uL, dropL);
			RenderHelper.draw();
		}
		
		GL11.glPopMatrix();
	}
}
