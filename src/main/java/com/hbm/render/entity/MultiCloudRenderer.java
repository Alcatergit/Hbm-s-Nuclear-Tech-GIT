package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import com.hbm.entity.particle.EntityModFX;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MultiCloudRenderer extends Render<EntityModFX> {

	private TextureAtlasSprite tex;
	private Item[] textureItems;
	private int meta;
	
	public MultiCloudRenderer(Item[] items, RenderManager renderManager) {
		super(renderManager);
		textureItems = items;
		meta = 0;
	}
	public MultiCloudRenderer(Item[] items, int m, RenderManager renderManager) {
		super(renderManager);
		textureItems = items;
		meta = m;
	}
	//Trash code, should probably fix later
	@Override
	public void doRender(EntityModFX fx, double x, double y, double z, float entityYaw, float partialTicks) {
		this.bindEntityTexture(fx);
		if (tex != null) {
			GL11.glPushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.disableLighting();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GlStateManager.enableRescaleNormal();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(7.5F, 7.5F, 7.5F);
			
			////
			Random randy = new Random(fx.hashCode());
			////
			
			Random rand = new Random(100);
			
			for(int i = 0; i < 5; i++) {
				
				float d = randy.nextInt(10) * 0.05F;
				GlStateManager.color(1 - d, 1 - d, 1 - d);

				double dX = (rand.nextGaussian() - 1D) * 0.15D;
				double dY = (rand.nextGaussian() - 1D) * 0.15D;
				double dZ = (rand.nextGaussian() - 1D) * 0.15D;
				double size = rand.nextDouble() * 0.5D + 0.25D;
				
				GL11.glTranslatef((float) dX, (float) dY, (float) dZ);
				GL11.glScaled(size, size, size);

				GL11.glPushMatrix();
				Tessellator tessellator = Tessellator.getInstance();
				this.func_77026_a(tessellator, tex);
				GL11.glPopMatrix();

				GL11.glScaled(1/size, 1/size, 1/size);
				GL11.glTranslatef((float) -dX, (float) -dY, (float) -dZ);
			}
			
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableLighting();
			GlStateManager.disableBlend();
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityModFX fx) {
		Item item = textureItems[0];
		
		if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
			item = textureItems[7];
		}

		if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
			item = textureItems[6];
		}

		if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
			item = textureItems[5];
		}

		if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
			item = textureItems[4];
		}

		if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
			item = textureItems[3];
		}

		if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
			item = textureItems[2];
		}

		if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
			item = textureItems[1];
		}

		if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
			item = textureItems[0];
		}
		tex = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(item, 1, meta), null, null).getParticleTexture();
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	private void func_77026_a(Tessellator tes, TextureAtlasSprite tas) {
		BufferBuilder buf = tes.getBuffer();
		float f = tas.getMinU();
		float f1 = tas.getMaxU();
		float f2 = tas.getMinV();
		float f3 = tas.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(f, f3).endVertex();
		buf.pos(f4 - f5, 0.0F - f6, 0.0D).tex(f1, f3).endVertex();
		buf.pos(f4 - f5, f4 - f6, 0.0D).tex(f1, f2).endVertex();
		buf.pos(0.0F - f5, f4 - f6, 0.0D).tex(f, f2).endVertex();
		tes.draw();
	}
}
