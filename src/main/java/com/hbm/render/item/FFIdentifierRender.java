package com.hbm.render.item;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModFluidProperties;
import com.hbm.forgefluid.ModFluidProperties.FluidProperties;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.render.RenderHelper;
import com.hbm.render.misc.EnumSymbol;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class FFIdentifierRender extends TileEntityItemStackRenderer {

	public static final FFIdentifierRender INSTANCE = new FFIdentifierRender();
	
	public TransformType type;
	public IBakedModel itemModel;
	public IBakedModel itemModelFuzzy;

	float getDisplayAlpha(long offset) {
		return 1-(float)Math.pow(Math.floorMod(System.currentTimeMillis()-offset,100)/200f,2)*2;
	}

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		if(itemStackIn.getItem() != ModItems.forge_fluid_identifier && itemStackIn.getItem() != ModItems.fuzzy_identifier)
			return;
		final double HALF_A_PIXEL = 0.03125;
		final double PIX = 0.0625;
		Fluid fluid = ItemForgeFluidIdentifier.getType(itemStackIn);
		TextureAtlasSprite fluidIcon = FFUtils.getTextureFromFluid(fluid);
		RenderHelper.bindBlockTexture();
		boolean fuzzy = itemStackIn.getItem() == ModItems.fuzzy_identifier;
		{
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GlStateManager.disableLighting();
			GL11.glTranslated(0, 0, 0.5+HALF_A_PIXEL);

			if(fluidIcon != null){
				FFUtils.setColorFromFluid(fluid);
				if (fuzzy)
					RenderHelper.startDrawingColoredTexturedQuads();
				else
					RenderHelper.startDrawingTexturedQuads();

				if (fuzzy) {
					float minU = fluidIcon.getInterpolatedU(9*1.5-8);
					float minV = fluidIcon.getInterpolatedV(14*1.5-8);
					float maxU = fluidIcon.getInterpolatedU(13*1.5-8);
					float maxV = fluidIcon.getInterpolatedV(15*1.5-8);

					GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);

					float alpha = getDisplayAlpha(0);
					RenderHelper.addVertexColorWithUV(9*PIX, 14*PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 14 * PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 15 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(9 * PIX, 15 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13*PIX, 14*PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(9 * PIX, 14 * PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(9 * PIX, 15 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 15 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);

					alpha = getDisplayAlpha(15);
					minU = fluidIcon.getInterpolatedU(5*1.5-8);
					minV = fluidIcon.getInterpolatedV(13*1.5-8);
					maxU = fluidIcon.getInterpolatedU(13*1.5-8);
					maxV = fluidIcon.getInterpolatedV(14*1.5-8);
					RenderHelper.addVertexColorWithUV(5*PIX, 13*PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 13 * PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 14 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(5 * PIX, 14 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13*PIX, 13*PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(5 * PIX, 13 * PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(5 * PIX, 14 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 14 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);

					alpha = getDisplayAlpha(30);
					minU = fluidIcon.getInterpolatedU(7*1.5-8);
					minV = fluidIcon.getInterpolatedV(12*1.5-8);
					maxU = fluidIcon.getInterpolatedU(13*1.5-8);
					maxV = fluidIcon.getInterpolatedV(13*1.5-8);
					RenderHelper.addVertexColorWithUV(7*PIX, 12*PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 12 * PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 13 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(7 * PIX, 13 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13*PIX, 12*PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(7 * PIX, 12 * PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(7 * PIX, 13 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(13 * PIX, 13 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);

					alpha = getDisplayAlpha(45);
					minU = fluidIcon.getInterpolatedU(8*1.5-8);
					minV = fluidIcon.getInterpolatedV(11*1.5-8);
					maxU = fluidIcon.getInterpolatedU(10*1.5-8);
					maxV = fluidIcon.getInterpolatedV(12*1.5-8);
					RenderHelper.addVertexColorWithUV(8*PIX, 11*PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(10 * PIX, 11 * PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(10 * PIX, 12 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(8 * PIX, 12 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(10*PIX, 11*PIX, -HALF_A_PIXEL, maxU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(8 * PIX, 11 * PIX, -HALF_A_PIXEL, minU, minV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(8 * PIX, 12 * PIX, -HALF_A_PIXEL, minU, maxV, 1, 1, 1, alpha);
					RenderHelper.addVertexColorWithUV(10 * PIX, 12 * PIX, -HALF_A_PIXEL, maxU, maxV, 1, 1, 1, alpha);
				} else {
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
				}


				RenderHelper.draw();
			}
			if (fuzzy) {
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
				GlStateManager.color(1.0F, 1.0F,1.0F,1.0F);
				GlStateManager.disableTexture2D();
				FluidProperties prop = ModFluidProperties.getProperties(fluid);
				RenderHelper.startDrawingColoredQuads();
				for (int type = 0; type < 4; type++) {
					int x = 3;
					int y = 10;
					float r = 0;
					float g = 0;
					float b = 0;
					switch(type) {
						case 0:
							x++;
							r = prop.flammability/4f;
							break;
						case 1:
							y--;
							b = prop.poison/4f;
							break;
						case 2:
							x+=2;
							y--;
							r = prop.reactivity/4f;
							g = prop.reactivity/4f;
							break;
						case 3:
							x++;
							y-=2;
							if (prop.symbol != EnumSymbol.NONE) {
								r = 1;
								g = 1;
								b = 1;
							}
							break;
					}
					r = (float)Math.pow(r,0.65);
					g = (float)Math.pow(g,0.65);
					b = (float)Math.pow(b,0.65);
					RenderHelper.addVertexColor(x*PIX,y*PIX,0,r,g,b,1);
					RenderHelper.addVertexColor((x+1)*PIX,y*PIX,0,r,g,b,1);
					RenderHelper.addVertexColor((x+1)*PIX,(y+1)*PIX,0,r,g,b,1);
					RenderHelper.addVertexColor(x*PIX,(y+1)*PIX,0,r,g,b,1);
					RenderHelper.addVertexColor((x+1)*PIX,y*PIX,-PIX,r,g,b,1);
					RenderHelper.addVertexColor(x*PIX,y*PIX,-PIX,r,g,b,1);
					RenderHelper.addVertexColor(x*PIX,(y+1)*PIX,-PIX,r,g,b,1);
					RenderHelper.addVertexColor((x+1)*PIX,(y+1)*PIX,-PIX,r,g,b,1);
				}
				RenderHelper.draw();
				GlStateManager.enableTexture2D();
			}
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableLighting();
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		Minecraft.getMinecraft().getRenderItem().renderItem(itemStackIn,fuzzy ? itemModelFuzzy : itemModel);
	}
}
