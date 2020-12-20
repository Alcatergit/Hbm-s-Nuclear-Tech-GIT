package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.particle.EntityDSmokeFX;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class DSmokeRenderer extends Render<EntityDSmokeFX> {
	public static TextureAtlasSprite[] sprites = new TextureAtlasSprite[8];
	private TextureAtlasSprite sprite;

	public DSmokeRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityDSmokeFX p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		if (p_76986_1_ instanceof EntityDSmokeFX) {
			EntityDSmokeFX fx = (EntityDSmokeFX) p_76986_1_;
			sprite = null;
			if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
				sprite = sprites[7];
			}

			if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
				sprite = sprites[6];
			}

			if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
				sprite = sprites[5];
			}

			if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
				sprite = sprites[4];
			}

			if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
				sprite = sprites[3];
			}

			if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
				sprite = sprites[2];
			}

			if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
				sprite = sprites[1];
			}

			if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
				sprite = sprites[0];
			}

			if (sprite != null) {
				GL11.glPushMatrix();
				GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
				GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glScalef(7.5F, 7.5F, 7.5F);
				//
				//GL11.glScalef(0.5F, 0.5F, 0.5F);
				//
				this.bindEntityTexture(p_76986_1_);
				Tessellator tessellator = Tessellator.getInstance();

				this.func_77026_a(tessellator, sprite);
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityDSmokeFX p_110775_1_) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
	

	private void func_77026_a(Tessellator tes, TextureAtlasSprite tex) {
		float f = tex.getMinU();
		float f1 = tex.getMaxU();
		float f2 = tex.getMinV();
		float f3 = tex.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		BufferBuilder buf = tes.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		//buf.putNormal(0.0F, 1.0F, 0.0F);
		buf.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(f, f3).endVertex();
		buf.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(f, f3).endVertex();
		buf.pos(f4 - f5, f4 - f6, 0.0D).tex(f1, f2).endVertex();
		buf.pos(0.0F - f5, f4 - f6, 0.0D).tex(f, f2);
		tes.draw();
	}
}
