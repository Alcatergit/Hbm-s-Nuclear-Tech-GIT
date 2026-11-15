package com.hbm.render.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityRubble;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRubble;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

public class RenderRubble extends Render<EntityRubble> {

	ModelRubble mine;
	Random rand;

	private static final Map<String, CachedTexture> DYNAMIC_TEXTURES = new HashMap<>();

	private static class CachedTexture {
		final TextureAtlasSprite sprite;
		final DynamicTexture texture;
		final int glId;

		CachedTexture(TextureAtlasSprite sprite, DynamicTexture texture) {
			this.sprite = sprite;
			this.texture = texture;
			this.glId = texture.getGlTextureId();
		}
	}

	public RenderRubble(RenderManager renderManager) {
		super(renderManager);
		mine = new ModelRubble();
		rand = new Random();
	}

	@Override
	public void doRender(EntityRubble rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef((rocket.ticksExisted % 360) * 10, 1, 1, 1);

		try {
			int block = rocket.getDataManager().get(EntityRubble.BLOCKID);
			int meta = rocket.getDataManager().get(EntityRubble.BLOCKMETA);
			@SuppressWarnings("deprecation")
			IBlockState state = Block.getBlockById(block).getStateFromMeta(meta);
			TextureAtlasSprite sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state).getParticleTexture();
			bindParticleTexture(sprite);
			mine.renderAll(0.0625F);
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		GL11.glPopMatrix();
	}

	private static void bindParticleTexture(TextureAtlasSprite sprite) {
		String iconName = sprite.getIconName();
		String[] split = iconName.split(":", 2);
		String namespace = split.length == 2 ? split[0] : "minecraft";
		String path = split.length == 2 ? split[1] : iconName;
		ResourceLocation png = new ResourceLocation(namespace, "textures/" + path + ".png");

		if(resourceExists(png)) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(png);
			return;
		}

		String key = iconName + ":" + sprite.getIconWidth() + "x" + sprite.getIconHeight();
		CachedTexture cached = DYNAMIC_TEXTURES.get(key);
		if(cached != null && cached.sprite == sprite) {
			GlStateManager.bindTexture(cached.glId);
			return;
		}
		if(cached != null) {
			TextureUtil.deleteTexture(cached.glId);
		}

		DynamicTexture texture = new DynamicTexture(sprite.getIconWidth(), sprite.getIconHeight());
		int[][] frames = sprite.getFrameTextureData(0);
		if(frames != null && frames.length > 0 && frames[0] != null) {
			int[] src = frames[0];
			int[] dst = texture.getTextureData();
			System.arraycopy(src, 0, dst, 0, Math.min(src.length, dst.length));
		}
		texture.updateDynamicTexture();
		DYNAMIC_TEXTURES.put(key, new CachedTexture(sprite, texture));
		GlStateManager.bindTexture(texture.getGlTextureId());
	}

	private static boolean resourceExists(ResourceLocation loc) {
		try {
			Minecraft.getMinecraft().getResourceManager().getResource(loc);
			return true;
		} catch(Exception ex) {
			return false;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRubble entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleScrap.png");
	}
}