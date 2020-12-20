package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class FFIdentifierRender extends TileEntityItemStackRenderer {

	public static final FFIdentifierRender INSTANCE = new FFIdentifierRender();
	
	public TransformType type;
	public IBakedModel itemModel;
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		if(itemStackIn.getItem() != ModItems.forge_fluid_identifier)
			return;
		final double HALF_A_PIXEL = 0.03125;
		final double PIX = 0.0625;
		Fluid fluid = ItemForgeFluidIdentifier.getType(itemStackIn);
		TextureAtlasSprite fluidIcon = FFUtils.getTextureFromFluid(fluid);
		RenderHelper.bindBlockTexture();
		if(fluidIcon != null){
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GlStateManager.disableLighting();
			GL11.glTranslated(0, 0, 0.5+HALF_A_PIXEL);
			
			FFUtils.setColorFromFluid(fluid);
			
			RenderHelper.startDrawingTexturedQuads();
			
			float maxU = fluidIcon.getInterpolatedU(9);
			float minU = fluidIcon.getInterpolatedU(7);
			float maxV = fluidIcon.getInterpolatedV(12);
			float minV = fluidIcon.getInterpolatedV(4);
			
			RenderHelper.addVertexWithUV(7*PIX, 4*PIX, 0, minU, minV);
			RenderHelper.addVertexWithUV(9 * PIX, 4 * PIX, 0, maxU, minV);
			RenderHelper.addVertexWithUV(9 * PIX, 12 * PIX, 0, maxU, maxV);
			RenderHelper.addVertexWithUV(7 * PIX, 12 * PIX, 0, minU, maxV);
			RenderHelper.addVertexWithUV(9 * PIX, 4 * PIX, -PIX, maxU, minV);
			RenderHelper.addVertexWithUV(7*PIX, 4*PIX, -PIX, minU, minV);
			RenderHelper.addVertexWithUV(7 * PIX, 12 * PIX, -PIX, minU, maxV);
			RenderHelper.addVertexWithUV(9 * PIX, 12 * PIX, -PIX, maxU, maxV);
			
			
			maxU = fluidIcon.getInterpolatedU(10);
			minU = fluidIcon.getInterpolatedU(9);
			maxV = fluidIcon.getInterpolatedV(10);
			minV = fluidIcon.getInterpolatedV(5);
			
			RenderHelper.addVertexWithUV(9*PIX, 5*PIX, 0, minU, minV);
			RenderHelper.addVertexWithUV(10 * PIX, 5 * PIX, 0, maxU, minV);
			RenderHelper.addVertexWithUV(10 * PIX, 10 * PIX, 0, maxU, maxV);
			RenderHelper.addVertexWithUV(9 * PIX, 10 * PIX, 0, minU, maxV);
			RenderHelper.addVertexWithUV(10 * PIX, 5 * PIX, -PIX, maxU, minV);
			RenderHelper.addVertexWithUV(9*PIX, 5*PIX, -PIX, minU, minV);
			RenderHelper.addVertexWithUV(9 * PIX, 10 * PIX, -PIX, minU, maxV);
			RenderHelper.addVertexWithUV(10 * PIX, 10 * PIX, -PIX, maxU, maxV);
			
			maxU = fluidIcon.getInterpolatedU(7);
			minU = fluidIcon.getInterpolatedU(6);
			maxV = fluidIcon.getInterpolatedV(10);
			minV = fluidIcon.getInterpolatedV(5);
			
			RenderHelper.addVertexWithUV(6*PIX, 5*PIX, 0, minU, minV);
			RenderHelper.addVertexWithUV(7 * PIX, 5 * PIX, 0, maxU, minV);
			RenderHelper.addVertexWithUV(7 * PIX, 10 * PIX, 0, maxU, maxV);
			RenderHelper.addVertexWithUV(6 * PIX, 10 * PIX, 0, minU, maxV);
			RenderHelper.addVertexWithUV(7 * PIX, 5 * PIX, -PIX, maxU, minV);
			RenderHelper.addVertexWithUV(6*PIX, 5*PIX, -PIX, minU, minV);
			RenderHelper.addVertexWithUV(6 * PIX, 10 * PIX, -PIX, minU, maxV);
			RenderHelper.addVertexWithUV(7 * PIX, 10 * PIX, -PIX, maxU, maxV);
			
			
			maxU = fluidIcon.getInterpolatedU(11);
			minU = fluidIcon.getInterpolatedU(10);
			maxV = fluidIcon.getInterpolatedV(8);
			minV = fluidIcon.getInterpolatedV(6);
			
			RenderHelper.addVertexWithUV(10*PIX, 6*PIX, 0, minU, minV);
			RenderHelper.addVertexWithUV(11 * PIX, 6 * PIX, 0, maxU, minV);
			RenderHelper.addVertexWithUV(11 * PIX, 8 * PIX, 0, maxU, maxV);
			RenderHelper.addVertexWithUV(10 * PIX, 8 * PIX, 0, minU, maxV);
			RenderHelper.addVertexWithUV(11 * PIX, 6 * PIX, -PIX, maxU, minV);
			RenderHelper.addVertexWithUV(10*PIX, 6*PIX, -PIX, minU, minV);
			RenderHelper.addVertexWithUV(10 * PIX, 8 * PIX, -PIX, minU, maxV);
			RenderHelper.addVertexWithUV(11 * PIX, 8 * PIX, -PIX, maxU, maxV);
			
			
			maxU = fluidIcon.getInterpolatedU(6);
			minU = fluidIcon.getInterpolatedU(5);
			maxV = fluidIcon.getInterpolatedV(8);
			minV = fluidIcon.getInterpolatedV(6);
			
			RenderHelper.addVertexWithUV(5*PIX, 6*PIX, 0, minU, minV);
			RenderHelper.addVertexWithUV(6 * PIX, 6 * PIX, 0, maxU, minV);
			RenderHelper.addVertexWithUV(6 * PIX, 8 * PIX, 0, maxU, maxV);
			RenderHelper.addVertexWithUV(5 * PIX, 8 * PIX, 0, minU, maxV);
			RenderHelper.addVertexWithUV(6 * PIX, 6 * PIX, -PIX, maxU, minV);
			RenderHelper.addVertexWithUV(5*PIX, 6*PIX, -PIX, minU, minV);
			RenderHelper.addVertexWithUV(5 * PIX, 8 * PIX, -PIX, minU, maxV);
			RenderHelper.addVertexWithUV(6 * PIX, 8 * PIX, -PIX, maxU, maxV);
			
			
			RenderHelper.draw();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableLighting();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		Minecraft.getMinecraft().getRenderItem().renderItem(itemStackIn, itemModel);
		
	}
}
