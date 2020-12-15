package com.hbm.render.entity;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.items.ModItems;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SpillRenderer extends Render<EntityOilSpillFX> {

	public static final IRenderFactory<EntityOilSpillFX> FACTORY = (RenderManager man) -> {return new SpillRenderer(man);};
	
	private Item renderItem;
	private static final Map<Item, TextureAtlasSprite> textures = new HashMap<Item, TextureAtlasSprite>();
	
	protected SpillRenderer(RenderManager renderManager) {
		super(renderManager);
		renderItem = ModItems.spill8;
	}
	
	@Override
	public void doRender(EntityOilSpillFX fx, double x, double y, double z, float entityYaw, float partialTicks) {
		if(textures.isEmpty()){
			textures.put(ModItems.spill1, RenderHelper.getItemTexture(ModItems.spill1));
			textures.put(ModItems.spill2, RenderHelper.getItemTexture(ModItems.spill2));
			textures.put(ModItems.spill3, RenderHelper.getItemTexture(ModItems.spill3));
			textures.put(ModItems.spill4, RenderHelper.getItemTexture(ModItems.spill4));
			textures.put(ModItems.spill5, RenderHelper.getItemTexture(ModItems.spill5));
			textures.put(ModItems.spill6, RenderHelper.getItemTexture(ModItems.spill6));
			textures.put(ModItems.spill7, RenderHelper.getItemTexture(ModItems.spill7));
			textures.put(ModItems.spill8, RenderHelper.getItemTexture(ModItems.spill8));
		}
		if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
			renderItem = ModItems.spill8;
		}

		if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
			renderItem = ModItems.spill7;
		}

		if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
			renderItem = ModItems.spill6;
		}

		if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
			renderItem = ModItems.spill5;
		}

		if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
			renderItem = ModItems.spill4;
		}

		if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
			renderItem = ModItems.spill3;
		}

		if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
			renderItem = ModItems.spill2;
		}

		if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
			renderItem = ModItems.spill1;
		}

		TextureAtlasSprite iicon = textures.get(renderItem);

		if (iicon != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(7.5F, 7.5F, 7.5F);
			//
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			//
			this.bindEntityTexture(fx);

			this.func_77026_a(iicon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityOilSpillFX entity) {
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
