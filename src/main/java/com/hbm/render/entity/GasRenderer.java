package com.hbm.render.entity;

import com.hbm.entity.particle.EntityGasFX;
import com.hbm.items.ModItems.DummyTexs;
import com.hbm.render.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.HashMap;
import java.util.Map;

public class GasRenderer extends Render<EntityGasFX> {

	public static final IRenderFactory<EntityGasFX> FACTORY = (RenderManager man) -> {return new GasRenderer(man);};
	
	private Item renderItem;
	private static final Map<Item, TextureAtlasSprite> textures = new HashMap<Item, TextureAtlasSprite>();
	
	protected GasRenderer(RenderManager renderManager) {
		super(renderManager);
		renderItem = DummyTexs.gas1;
	}
	
	@Override
	public void doRender(EntityGasFX fx, double x, double y, double z, float entityYaw, float partialTicks) {
		if(textures.isEmpty()){
			textures.put(DummyTexs.gas1, RenderHelper.getItemTexture(DummyTexs.gas1));
			textures.put(DummyTexs.gas2, RenderHelper.getItemTexture(DummyTexs.gas2));
			textures.put(DummyTexs.gas3, RenderHelper.getItemTexture(DummyTexs.gas3));
			textures.put(DummyTexs.gas4, RenderHelper.getItemTexture(DummyTexs.gas4));
			textures.put(DummyTexs.gas5, RenderHelper.getItemTexture(DummyTexs.gas5));
			textures.put(DummyTexs.gas6, RenderHelper.getItemTexture(DummyTexs.gas6));
			textures.put(DummyTexs.gas7, RenderHelper.getItemTexture(DummyTexs.gas7));
			textures.put(DummyTexs.gas8, RenderHelper.getItemTexture(DummyTexs.gas8));
		}
		if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
			renderItem = DummyTexs.gas8;
		}

		if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
			renderItem = DummyTexs.gas7;
		}

		if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
			renderItem = DummyTexs.gas6;
		}

		if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
			renderItem = DummyTexs.gas5;
		}

		if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
			renderItem = DummyTexs.gas4;
		}

		if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
			renderItem = DummyTexs.gas3;
		}

		if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
			renderItem = DummyTexs.gas2;
		}

		if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
			renderItem = DummyTexs.gas1;
		}

		TextureAtlasSprite iicon = textures.get(renderItem);

		if (iicon != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GlStateManager.disableLighting();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(7.5F, 7.5F, 7.5F);
			//
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			//
			this.bindEntityTexture(fx);

			this.func_77026_a(iicon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GlStateManager.enableLighting();
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGasFX entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
	
	private void func_77026_a(TextureAtlasSprite p_77026_2_) {
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		RenderHelper.startDrawingTexturedQuads();
		RenderHelper.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		RenderHelper.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		RenderHelper.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		RenderHelper.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		RenderHelper.draw();
	}

}
