package com.hbm.render.entity;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.items.ModItems;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class GasFlameRenderer extends Render<EntityGasFlameFX> {

	protected Item renderItem = ModItems.nuclear_waste;
	protected Map<Item, TextureAtlasSprite> textures = new HashMap<Item, TextureAtlasSprite>();
	
	public static final IRenderFactory<EntityGasFlameFX> FACTORY = (RenderManager manage) -> {return new GasFlameRenderer(manage);};
	
	protected GasFlameRenderer(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityGasFlameFX fx, double x, double y, double z, float entityYaw, float partialTicks) {

		if(textures.size() == 0){
			textures.put(ModItems.gasflame1, RenderHelper.getItemTexture(ModItems.gasflame1));
			textures.put(ModItems.gasflame2, RenderHelper.getItemTexture(ModItems.gasflame2));
			textures.put(ModItems.gasflame3, RenderHelper.getItemTexture(ModItems.gasflame3));
			textures.put(ModItems.gasflame4, RenderHelper.getItemTexture(ModItems.gasflame4));
			textures.put(ModItems.gasflame5, RenderHelper.getItemTexture(ModItems.gasflame5));
			textures.put(ModItems.gasflame6, RenderHelper.getItemTexture(ModItems.gasflame6));
			textures.put(ModItems.gasflame7, RenderHelper.getItemTexture(ModItems.gasflame7));
			textures.put(ModItems.gasflame8, RenderHelper.getItemTexture(ModItems.gasflame8));
		}
		if (fx.particleAge <= fx.maxAge && fx.particleAge >= fx.maxAge / 8 * 7) {
			renderItem = ModItems.gasflame8;
		}

		if (fx.particleAge < fx.maxAge / 8 * 7 && fx.particleAge >= fx.maxAge / 8 * 6) {
			renderItem = ModItems.gasflame7;
		}

		if (fx.particleAge < fx.maxAge / 8 * 6 && fx.particleAge >= fx.maxAge / 8 * 5) {
			renderItem = ModItems.gasflame6;
		}

		if (fx.particleAge < fx.maxAge / 8 * 5 && fx.particleAge >= fx.maxAge / 8 * 4) {
			renderItem = ModItems.gasflame5;
		}

		if (fx.particleAge < fx.maxAge / 8 * 4 && fx.particleAge >= fx.maxAge / 8 * 3) {
			renderItem = ModItems.gasflame4;
		}

		if (fx.particleAge < fx.maxAge / 8 * 3 && fx.particleAge >= fx.maxAge / 8 * 2) {
			renderItem = ModItems.gasflame3;
		}

		if (fx.particleAge < fx.maxAge / 8 * 2 && fx.particleAge >= fx.maxAge / 8 * 1) {
			renderItem = ModItems.gasflame2;
		}

		if (fx.particleAge < fx.maxAge / 8 && fx.particleAge >= 0) {
			renderItem = ModItems.gasflame1;
		}

		TextureAtlasSprite icon = textures.get(renderItem);

		if (icon != null) {
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GlStateManager.disableLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glTranslatef((float) x, (float) y, (float) z);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glScalef(7.5F, 7.5F, 7.5F);
			//
			GL11.glScalef(0.35F, 0.35F, 0.35F);
			//
			this.bindEntityTexture(fx);
			Tessellator tessellator = Tessellator.getInstance();

			this.func_77026_a(tessellator, icon);
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopAttrib();
			GL11.glPopMatrix();
		}
	}
	
	private void func_77026_a(Tessellator tes, TextureAtlasSprite p_77026_2_) {
		float f = p_77026_2_.getMinU();
		float f1 = p_77026_2_.getMaxU();
		float f2 = p_77026_2_.getMinV();
		float f3 = p_77026_2_.getMaxV();
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		RenderHelper.startDrawingTexturedQuads(tes);
		//RenderHelper.setNormal(0.0F, 1.0F, 0.0F);
		RenderHelper.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		RenderHelper.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		RenderHelper.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		RenderHelper.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		tes.draw();
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityGasFlameFX entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
