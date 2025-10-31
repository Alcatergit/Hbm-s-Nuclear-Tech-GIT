package com.leafia.contents.control.fuel.nuclearfuel;

import com.hbm.items.ModItems.LeafiaRods;
import com.hbm.render.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

public class LeafiaRodRender extends TileEntityItemStackRenderer {

	public static final LeafiaRodRender INSTANCE = new LeafiaRodRender();

	public TransformType type;
	public IBakedModel itemModel;

	enum Face {
		NORMAL,
		BEHIND,
		LEFT,
		TOP,
		RIGHT,
		BOTTOM
	}
	class Brush {
		final double PIX = 0.0625;
		BufferBuilder buf;
		TextureAtlasSprite texture;
		int pL = 0;
		int pD = 0;
		int pR = 0;
		int pU = 0;
		int bL = 0;
		int bD = 0;
		int bR = 0;
		int bU = 0;
		boolean drawingRod = true;
		Brush(BufferBuilder buf, TextureAtlasSprite texture) {
			this.buf = buf;
			this.texture = texture;
		}
		void set(int x, int y, int w, int h) {
			if (!drawingRod)
				y--;
			pL = x;
			pU = y;
			pR = x+w;
			pD = y+h;
			if (drawingRod) {
				if (pU > 10) pU--; else pU++;
				if (pD > 10) pD--; else pD++;
			}
		}
		void setBrush(int x, int y, int w, int h) {
			bL = x;
			bU = y;
			bR = x+w;
			bD = y+h;
		}
		void updateBrush() {
			bL = pL;
			bU = pU;
			bR = pR;
			bD = pD;
			if (!drawingRod) {
				bU++;
				bD++;
			}
		}
		double pix(int p) {
			return p*PIX;
		}
		void addVertex(int vx, int vy, float tx, float ty, float z) {
			buf.pos(vx * PIX, (16-vy) * PIX, (z/2-0.5)*PIX).tex(tx,ty).endVertex();
		}
		void paint(Face face) {
			float texL = 0;
			float texR = 0;
			float texD = 0;
			float texU = 0;
			switch(face) {
				case NORMAL:
					texL = texture.getInterpolatedU(bL);
					texR = texture.getInterpolatedU(bR);
					texU = texture.getInterpolatedV(bU);
					texD = texture.getInterpolatedV(bD);

					addVertex(pL,pD,texL,texD,1);
					addVertex(pR,pD,texR,texD,1);
					addVertex(pR,pU,texR,texU,1);
					addVertex(pL,pU,texL,texU, 1);
					break;
				case BEHIND:
					texL = texture.getInterpolatedU(bL);
					texR = texture.getInterpolatedU(bR);
					texU = texture.getInterpolatedV(bU);
					texD = texture.getInterpolatedV(bD);

					addVertex(pR,pD,texR,texD,-1);
					addVertex(pL,pD,texL,texD,-1);
					addVertex(pL,pU,texL,texU,-1);
					addVertex(pR,pU,texR,texU, -1);
					break;
				case TOP:
					texL = texture.getInterpolatedU(bL);
					texR = texture.getInterpolatedU(bR);
					texU = texture.getInterpolatedV(bU);
					texD = texture.getInterpolatedV(bU+1);

					addVertex(pL,pU,texL,texD,1);
					addVertex(pR,pU,texR,texD,1);
					addVertex(pR,pU,texR,texU,-1);
					addVertex(pL,pU,texL,texU, -1);
					break;
				case BOTTOM:
					texL = texture.getInterpolatedU(bL);
					texR = texture.getInterpolatedU(bR);
					texU = texture.getInterpolatedV(bD-1);
					texD = texture.getInterpolatedV(bD);

					addVertex(pL,pD,texL,texD,-1);
					addVertex(pR,pD,texR,texD,-1);
					addVertex(pR,pD,texR,texU,1);
					addVertex(pL,pD,texL,texU, 1);
					break;
				case LEFT:
					texL = texture.getInterpolatedU(bL);
					texR = texture.getInterpolatedU(bL+1);
					texU = texture.getInterpolatedV(bU);
					texD = texture.getInterpolatedV(bD);

					addVertex(pL,pD,texL,texD,-1);
					addVertex(pL,pD,texR,texD,1);
					addVertex(pL,pU,texR,texU,1);
					addVertex(pL,pU,texL,texU, -1);
					break;
				case RIGHT:
					texL = texture.getInterpolatedU(bR-1);
					texR = texture.getInterpolatedU(bR);
					texU = texture.getInterpolatedV(bU);
					texD = texture.getInterpolatedV(bD);

					addVertex(pR,pD,texL,texD,1);
					addVertex(pR,pD,texR,texD,-1);
					addVertex(pR,pU,texR,texU,-1);
					addVertex(pR,pU,texL,texU, 1);
					break;
			}
		}
	}
	
	@Override
	public void renderByItem(ItemStack stack) {

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		RenderHelper.bindBlockTexture();

		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = Tessellator.getInstance().getBuffer();

		LeafiaRodItem rod = LeafiaRodItem.fromResourceMap.get(stack.getItem().getRegistryName().getPath());

		ItemStack renderStack = new ItemStack(rod.baseItem, 1, 0);
		IBakedModel submodel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(renderStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
		//Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, submodel);

		TextureAtlasSprite submodelTex = submodel.getParticleTexture();



		final double HALF_A_PIXEL = 0.03125;
		LeafiaRodItem.ItemType type = rod.baseItemType;

		if (rod.specialRodModel == null) {
			GlStateManager.color(1F, 1F, 1F, 1F);
			//GlStateManager.disableLighting();
			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			Brush brush = new Brush(buf,submodelTex);
			// oekaki time?
			if (true) /* Fuel drawing! */ {
				/// If you're wondering why the fuck I have so many setBrush calls on
				/// literally same colored pixels on billets, that's for Bismuth billets.

				// Outlines
				//(1) Top
				switch (type) {
					case BILLET:
						brush.setBrush(7, 3, 2, 1);
						break;
					case BONEMEAL:
						brush.setBrush(9, 7, 1, 1);
						break;
				}
				brush.set(7, 0, 2, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.TOP);
				brush.paint(Face.LEFT);
				brush.paint(Face.RIGHT);
				//(1) Top Left
				switch (type) {
					case BILLET:
						brush.setBrush(4, 4, 1, 1);
						break;
				}
				brush.set(6, 1, 1, 3);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.TOP);
				brush.pD -= 1;
				brush.paint(Face.LEFT);
				//(1) Top Right
				switch (type) {
					case BILLET:
						brush.setBrush(11, 4, 1, 1);
						break;
				}
				brush.set(9, 1, 1, 3);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.TOP);
				brush.pD -= 1;
				brush.paint(Face.RIGHT);
				//(1) Mid Left
				switch (type) {
					case BILLET:
						brush.setBrush(2, 7, 1, 1);
						break;
				}
				brush.set(6, 7, 1, 5);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.pD -= 1;
				brush.paint(Face.LEFT);
				//(1) Mid Right
				switch (type) {
					case BILLET:
						brush.setBrush(13, 7, 1, 1);
						break;
				}
				brush.set(9, 8, 1, 4);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.pD -= 1;
				brush.paint(Face.RIGHT);
				//(2) Mid Left Shadow
				switch (type) {
					case BILLET:
						brush.setBrush(3, 10, 1, 1);
						break;
					case BONEMEAL:
						brush.setBrush(10, 7, 1, 1);
						break;
				}
				brush.set(6, 6, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.LEFT);
				//(2) Mid Right Shadow
				switch (type) {
					case BILLET:
						brush.setBrush(12, 10, 1, 1);
						break;
				}
				brush.set(9, 6, 1, 2);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.RIGHT);
				//(2) Bottom Shadow
				switch (type) {
					case BILLET:
						brush.setBrush(7, 12, 2, 1);
						break;
				}
				brush.set(7, 15, 2, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.BOTTOM);
				brush.paint(Face.LEFT);
				brush.paint(Face.RIGHT);
				//(2) Bottom Left Shadow
				switch (type) {
					case BILLET:
						brush.setBrush(4, 11, 1, 1);
						break;
				}
				brush.set(6, 14, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.BOTTOM);
				brush.paint(Face.LEFT);
				//(2) Bottom Right Shadow
				switch (type) {
					case BILLET:
						brush.setBrush(11, 11, 1, 1);
						break;
				}
				brush.set(9, 14, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.paint(Face.BOTTOM);
				brush.paint(Face.RIGHT);


				// Inner cladding
				//(3) Top Upper Highlight
				switch (type) {
					case BILLET:
						brush.setBrush(3, 8, 1, 1);
						break;
					case BONEMEAL:
						brush.setBrush(7, 6, 1, 1);
						break;
				}
				brush.set(7, 1, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(3) Top Lower Highlight
				switch (type) {
					case BILLET:
						brush.setBrush(4, 9, 1, 1);
						break;
				}
				brush.set(7, 3, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(3) Mid Upper Highlight
				switch (type) {
					case BILLET:
						brush.setBrush(6, 10, 1, 1);
						break;
				}
				brush.set(7, 7, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(3) Mid Lower Highlight
				switch (type) {
					case BILLET:
						brush.setBrush(7, 10, 1, 1);
						break;
				}
				brush.set(7, 11, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);


				//(4) Top J-shaped Surface
				switch (type) {
					case BILLET:
						brush.setBrush(4, 7, 1, 1);
						break;
					case BONEMEAL:
						brush.setBrush(7, 7, 1, 1);
						break;
				}
				brush.set(8, 1, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				brush.set(7, 2, 2, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(4) Mid L Surface
				switch (type) {
					case BILLET:
						brush.setBrush(4, 8, 1, 1);
						break;
				}
				brush.set(7, 6, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(4) Mid R Surface
				switch (type) {
					case BILLET:
						brush.setBrush(8, 6, 1, 1);
						break;
				}
				brush.set(8, 7, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(4) Mid LLL Surface
				brush.set(7, 8, 1, 3);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(4) Bottom Left Surface
				brush.set(7, 14, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);

				//(4) Mid R Surface
				switch (type) {
					case BILLET:
						brush.setBrush(9, 6, 1, 1);
						break;
				}
				brush.set(8, 11, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);


				//(5) Top Dark
				switch (type) {
					case BILLET:
						brush.setBrush(5, 10, 1, 1);
						break;
					case BONEMEAL:
						brush.setBrush(8, 7, 1, 1);
						break;
				}
				brush.set(8, 3, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(5) Mid Upper Dark
				switch (type) {
					case BILLET:
						brush.setBrush(6, 11, 1, 1);
						break;
				}
				brush.set(8, 6, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(5) Mid Middle Dark
				switch (type) {
					case BILLET:
						brush.setBrush(11, 5, 1, 1);
						break;
				}
				brush.set(8, 8, 1, 3);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
				//(5) Bottom Right Dark
				switch (type) {
					case BILLET:
						brush.setBrush(6, 7, 1, 1);
						break;
				}
				brush.set(8, 14, 1, 1);
				brush.paint(Face.NORMAL);
				brush.paint(Face.BEHIND);
			}
			brush.drawingRod = false;
			switch(rod.purity) {
				case RAW: {
					break;
				}
				case ISOTOPE: {
					brush.set(1,12,3,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.LEFT);
					brush.pR-=2; brush.updateBrush();
					brush.paint(Face.BOTTOM);
					brush.pR+=2; brush.pL+=2; brush.updateBrush();
					brush.paint(Face.BOTTOM);

					brush.set(1,15,3,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.RIGHT);
					brush.paint(Face.LEFT);
					brush.pR-=2; brush.updateBrush();
					brush.paint(Face.TOP);
					brush.pR+=2; brush.pL+=2; brush.updateBrush();
					brush.paint(Face.TOP);

					brush.set(2,13,1,2); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.LEFT);
					brush.paint(Face.RIGHT);
					break;
				}
				case FUEL: {
					brush.set(1,12,3,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.pL+=1; brush.updateBrush();
					brush.paint(Face.BOTTOM);

					brush.set(1,12,1,4); brush.updateBrush();
					brush.paint(Face.LEFT);
					brush.paint(Face.BOTTOM);
					brush.pU+=1; brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.pD-=2; brush.updateBrush();
					brush.paint(Face.RIGHT);
					brush.pU+=2; brush.pD+=2; brush.updateBrush();
					brush.paint(Face.RIGHT);

					brush.set(2,14,1,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.RIGHT);
					brush.paint(Face.TOP);
					brush.paint(Face.BOTTOM);
					break;
				}
				case SOURCE: {
					brush.set(1,12,3,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.pL+=2; brush.updateBrush();
					brush.paint(Face.BOTTOM);
					brush.set(1,12,1,2); brush.updateBrush();
					brush.paint(Face.LEFT);
					brush.paint(Face.BOTTOM);
					brush.set(1,13,2,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.RIGHT);

					brush.set(1,15,3,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.RIGHT);
					brush.pR-=2; brush.updateBrush();
					brush.paint(Face.TOP);
					brush.set(3,14,1,2); brush.updateBrush();
					brush.paint(Face.RIGHT);
					brush.paint(Face.TOP);
					brush.set(2,14,2,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.LEFT);
					break;
				}
				case BREEDER: {
					brush.set(1,12,2,2); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.pL+=1; brush.updateBrush();
					brush.paint(Face.BOTTOM);

					brush.set(1,12,1,4); brush.updateBrush();
					brush.paint(Face.LEFT);
					brush.pU+=2; brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.pD-=1; brush.updateBrush();
					brush.paint(Face.RIGHT);

					brush.set(1,15,3,1); brush.updateBrush();
					brush.paint(Face.BOTTOM);
					brush.pL+=1; brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.pR-=1; brush.updateBrush();
					brush.paint(Face.TOP);

					brush.set(3,14,1,2); brush.updateBrush();
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.pD-=1; brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.LEFT);
					break;
				}
				case UNSTABLE: {
					brush.set(2,13,2,2); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.LEFT);
					brush.set(1,12,1,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.LEFT);
					brush.set(4,12,1,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.LEFT);
					brush.set(1,15,1,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.LEFT);
					brush.set(4,15,1,1); brush.updateBrush();
					brush.paint(Face.NORMAL);
					brush.paint(Face.BEHIND);
					brush.paint(Face.TOP);
					brush.paint(Face.RIGHT);
					brush.paint(Face.BOTTOM);
					brush.paint(Face.LEFT);
					break;
				}
			}

			tes.draw();
			GlStateManager.enableLighting();
		}

		GL11.glPushMatrix();
		GL11.glTranslated(0.5, 0.5, -HALF_A_PIXEL);
		if (rod.specialRodModel == null)
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
		else
			Minecraft.getMinecraft().getRenderItem().renderItem(stack, rod.bakedSpecialRod);
		GL11.glPopMatrix();

		NBTTagCompound data = stack.getTagCompound();
		if (data != null) {
			double meltingPoint = LeafiaRodItem.fromResourceMap.get(stack.getItem().getRegistryName().getPath()).meltingPoint;
			if (meltingPoint != 0) {
				double heat = data.getDouble("heat");
				double opacity = (heat-meltingPoint/2)/(meltingPoint/2);
				if (opacity > 0) {
					GL11.glPushMatrix();
					GL11.glTranslated(0.5, 0.5, -HALF_A_PIXEL * 0.45);

					GlStateManager.enableBlend();
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
					GlStateManager.blendFunc(SourceFactor.DST_COLOR, GlStateManager.DestFactor.ONE);
					GlStateManager.disableLighting();
					float col = (float) Math.min(opacity, 1F);
					GlStateManager.color(col,col,col,col);
					IBakedModel overlay = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(LeafiaRods.leafRod, 1, 15), Minecraft.getMinecraft().world, null);
					TextureAtlasSprite icon = overlay.getParticleTexture();
					float up = icon.getInterpolatedV(16);
					float down = icon.getInterpolatedV(0);
					float left = icon.getInterpolatedU(0);
					float right = icon.getInterpolatedU(16);
					float posX = -0.5F;
					float posY = 0.5F;
					float sizeY = -1;
					float sizeX = 1;
					RenderHelper.startDrawingTexturedQuads();
					RenderHelper.addVertexWithUV(posX, posY + sizeY, 0.065F, left, up);
					RenderHelper.addVertexWithUV(posX + sizeX, posY + sizeY, 0.065F, right, up);
					RenderHelper.addVertexWithUV(posX + sizeX, posY, 0.065F, right, down);
					RenderHelper.addVertexWithUV(posX, posY, 0.065F, left, down);
					RenderHelper.draw();

					GlStateManager.enableLighting();
					GlStateManager.color(1, 1, 1, 1);
					//GlStateManager.disableBlend();
					GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);

					GL11.glPopMatrix();
				}
			}
		}
		/*
		FluidStack f = FluidUtil.getFluidContained(stack);
		TextureAtlasSprite lava = null;
		if (f != null)
			lava = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getFluid().getStill().toString());

		
		if (lava != null) {
			RenderHelper.setColor(f.getFluid().getColor(f));
			GlStateManager.disableLighting();
			float maxU = lava.getInterpolatedU(9);
			float minU = lava.getInterpolatedU(7);
			float maxV = lava.getInterpolatedV(9);
			float minV = lava.getInterpolatedV(3);

			GL11.glTranslated(0, 0, 0.5 + HALF_A_PIXEL);
			buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buf.pos(7 * PIX, 3 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 3 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 9 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(7 * PIX, 9 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * PIX, 3 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(7 * PIX, 3 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(7 * PIX, 9 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(9 * PIX, 9 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			maxU = lava.getInterpolatedU(10);
			minU = lava.getInterpolatedU(9);
			maxV = lava.getInterpolatedV(12);
			minV = lava.getInterpolatedV(11);
			
			buf.pos(9 * PIX, 11 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(10 * PIX, 11 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(10 * PIX, 12 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(10 * PIX, 11 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 11 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(10 * PIX, 12 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			maxU = lava.getInterpolatedU(9);
			minU = lava.getInterpolatedU(8);
			maxV = lava.getInterpolatedV(13);
			minV = lava.getInterpolatedV(12);
			
			buf.pos(8 * PIX, 12 * PIX, 0).tex(minU, minV).endVertex();
			buf.pos(9 * PIX, 12 * PIX, 0).tex(maxU, minV).endVertex();
			buf.pos(9 * PIX, 13 * PIX, 0).tex(maxU, maxV).endVertex();
			buf.pos(8 * PIX, 13 * PIX, 0).tex(minU, maxV).endVertex();

			buf.pos(9 * PIX, 12 * PIX, -PIX).tex(maxU, minV).endVertex();
			buf.pos(8 * PIX, 12 * PIX, -PIX).tex(minU, minV).endVertex();
			buf.pos(8 * PIX, 13 * PIX, -PIX).tex(minU, maxV).endVertex();
			buf.pos(9 * PIX, 13 * PIX, -PIX).tex(maxU, maxV).endVertex();
			
			
			tes.draw();
			GlStateManager.enableLighting();
			
		}*/

		GL11.glPopAttrib();
		GL11.glPopMatrix();
		super.renderByItem(stack);
	}
}
