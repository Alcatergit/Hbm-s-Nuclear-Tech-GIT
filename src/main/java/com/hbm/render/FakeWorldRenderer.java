package com.hbm.render;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class FakeWorldRenderer {

	private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private float fogColorRed;
	private float fogColorGreen;
	private float fogColorBlue;
	private float bossColorModifier;
	private float bossColorModifierPrev;
	// Wow, real helpful comments forge
	/** Fog color 2 */
	private float fogColor2;
	/** Fog color 1 */
	private float fogColor1;
	private boolean cloudFog;
	private float farPlaneDistance;
	public int rendererUpdateCount;
	private final Random random = new Random();
	private final float[] rainXCoords = new float[1024];
	private final float[] rainYCoords = new float[1024];
	private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
	private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");

	public static final FakeWorldRenderer INSTANCE = new FakeWorldRenderer();

	private Minecraft mc = null;

	private FakeWorldRenderer() {
		for(int i = 0; i < 32; ++i) {
			for(int j = 0; j < 32; ++j) {
				float f = (float) (j - 16);
				float f1 = (float) (i - 16);
				float f2 = MathHelper.sqrt(f * f + f1 * f1);
				this.rainXCoords[i << 5 | j] = -f1 / f2;
				this.rainYCoords[i << 5 | j] = f / f2;
			}
		}
	}

	public void renderWorld(float partialTicks, long finishTimeNano) {
		if(mc == null)
			mc = Minecraft.getMinecraft();

		if(this.mc.getRenderViewEntity() == null) {
			this.mc.setRenderViewEntity(this.mc.player);
		}

		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.alphaFunc(516, 0.5F);

		this.renderWorldPass(2, partialTicks, finishTimeNano);
	}

	private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {

		RenderGlobal renderglobal = this.mc.renderGlobal;
		ParticleManager particlemanager = this.mc.effectRenderer;
		GlStateManager.enableCull();
		this.mc.mcProfiler.endStartSection("clear");
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		this.updateFogColor(partialTicks);
		GlStateManager.clear(16640);
		this.mc.mcProfiler.endStartSection("camera");
		this.setupCameraTransform(partialTicks, pass);
		//ActiveRenderInfo.updateRenderInfo(this.mc.getRenderViewEntity(), this.mc.gameSettings.thirdPersonView == 2); // Forge:
																														// MC-46445
																														// Spectator
																														// mode
																														// particles
																														// and
																														// sounds
																														// computed
																														// from
																														// where
																														// you
																														// have
																														// been
																														// before
		this.mc.mcProfiler.endStartSection("frustum");
		ClippingHelperImpl.getInstance();
		this.mc.mcProfiler.endStartSection("culling");
		ICamera icamera = new Frustum();
		Entity entity = this.mc.getRenderViewEntity();
		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		icamera.setPosition(d0, d1, d2);

		if(this.mc.gameSettings.renderDistanceChunks >= 4) {
			this.setupFog(-1, partialTicks);
			this.mc.mcProfiler.endStartSection("sky");
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
			GlStateManager.matrixMode(5888);
			renderglobal.renderSky(partialTicks, pass);
			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.farPlaneDistance * MathHelper.SQRT_2);
			GlStateManager.matrixMode(5888);
		}

		this.setupFog(0, partialTicks);
		GlStateManager.shadeModel(7425);

		this.mc.mcProfiler.endStartSection("prepareterrain");
		this.setupFog(0, partialTicks);
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		this.mc.mcProfiler.endStartSection("terrain_setup");
		renderglobal.setupTerrain(entity, (double) partialTicks, icamera, 0, this.mc.player.isSpectator());

		if(pass == 0 || pass == 2) {
			this.mc.mcProfiler.endStartSection("updatechunks");
			this.mc.renderGlobal.updateChunks(finishTimeNano);
		}

		this.mc.mcProfiler.endStartSection("terrain");
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.disableAlpha();
		renderglobal.renderBlockLayer(BlockRenderLayer.SOLID, (double) partialTicks, pass, entity);
		GlStateManager.enableAlpha();
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, this.mc.gameSettings.mipmapLevels > 0); // FORGE:
																																				// fix
																																				// flickering
																																				// leaves
																																				// when
																																				// mods
																																				// mess
																																				// up
																																				// the
																																				// blurMipmap
																																				// settings
		renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, (double) partialTicks, pass, entity);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT, (double) partialTicks, pass, entity);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
		GlStateManager.shadeModel(7424);
		GlStateManager.alphaFunc(516, 0.1F);

		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		this.mc.mcProfiler.endStartSection("entities");
		net.minecraftforge.client.ForgeHooksClient.setRenderPass(0);
		renderglobal.renderEntities(entity, icamera, partialTicks);
		net.minecraftforge.client.ForgeHooksClient.setRenderPass(0);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		this.disableLightmap();

		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();

		if(this.mc.debugRenderer.shouldRender()) {
			this.mc.debugRenderer.renderDebug(partialTicks, finishTimeNano);
		}

		this.mc.mcProfiler.endStartSection("destroyProgress");
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
		renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), entity, partialTicks);
		this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
		GlStateManager.disableBlend();

		this.enableLightmap();
		this.mc.mcProfiler.endStartSection("litParticles");
		particlemanager.renderLitParticles(entity, partialTicks);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		this.setupFog(0, partialTicks);
		this.mc.mcProfiler.endStartSection("particles");
		particlemanager.renderParticles(entity, partialTicks);
		this.disableLightmap();

		GlStateManager.depthMask(false);
		GlStateManager.enableCull();
		this.mc.mcProfiler.endStartSection("weather");
		this.renderRainSnow(partialTicks);
		GlStateManager.depthMask(true);
		renderglobal.renderWorldBorder(entity, partialTicks);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.alphaFunc(516, 0.1F);
		this.setupFog(0, partialTicks);
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		GlStateManager.shadeModel(7425);
		this.mc.mcProfiler.endStartSection("translucent");
		renderglobal.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, (double) partialTicks, pass, entity);

		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		this.mc.mcProfiler.endStartSection("entities");
		net.minecraftforge.client.ForgeHooksClient.setRenderPass(1);
		renderglobal.renderEntities(entity, icamera, partialTicks);
		// restore blending function changed by
		// RenderGlobal.preRenderDamagedBlocks
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		net.minecraftforge.client.ForgeHooksClient.setRenderPass(-1);
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

		GlStateManager.shadeModel(7424);
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.disableFog();

		this.mc.mcProfiler.endStartSection("forge_render_last");
		net.minecraftforge.client.ForgeHooksClient.dispatchRenderLast(renderglobal, partialTicks);

		this.mc.mcProfiler.endStartSection("hand");

	}

	private void setupFog(int startCoords, float partialTicks) {
		Entity entity = this.mc.getRenderViewEntity();
		this.setupFogColor(false);
		GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint(this.mc.world, entity, partialTicks);
		if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(MobEffects.BLINDNESS)) {
			float f1 = 5.0F;
			int i = ((EntityLivingBase) entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();

			if(i < 20) {
				f1 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - (float) i / 20.0F);
			}

			GlStateManager.setFog(GlStateManager.FogMode.LINEAR);

			if(startCoords == -1) {
				GlStateManager.setFogStart(0.0F);
				GlStateManager.setFogEnd(f1 * 0.8F);
			} else {
				GlStateManager.setFogStart(f1 * 0.25F);
				GlStateManager.setFogEnd(f1);
			}

			if(GLContext.getCapabilities().GL_NV_fog_distance) {
				GlStateManager.glFogi(34138, 34139);
			}
		} else if(this.cloudFog) {
			GlStateManager.setFog(GlStateManager.FogMode.EXP);
			GlStateManager.setFogDensity(0.1F);
		} else if(iblockstate.getMaterial() == Material.WATER) {
			GlStateManager.setFog(GlStateManager.FogMode.EXP);

			if(entity instanceof EntityLivingBase) {
				if(((EntityLivingBase) entity).isPotionActive(MobEffects.WATER_BREATHING)) {
					GlStateManager.setFogDensity(0.01F);
				} else {
					GlStateManager.setFogDensity(0.1F - (float) EnchantmentHelper.getRespirationModifier((EntityLivingBase) entity) * 0.03F);
				}
			} else {
				GlStateManager.setFogDensity(0.1F);
			}
		} else if(iblockstate.getMaterial() == Material.LAVA) {
			GlStateManager.setFog(GlStateManager.FogMode.EXP);
			GlStateManager.setFogDensity(2.0F);
		} else {
			float f = this.farPlaneDistance;
			GlStateManager.setFog(GlStateManager.FogMode.LINEAR);

			if(startCoords == -1) {
				GlStateManager.setFogStart(0.0F);
				GlStateManager.setFogEnd(f);
			} else {
				GlStateManager.setFogStart(f * 0.75F);
				GlStateManager.setFogEnd(f);
			}

			if(GLContext.getCapabilities().GL_NV_fog_distance) {
				GlStateManager.glFogi(34138, 34139);
			}

			if(this.mc.world.provider.doesXZShowFog((int) entity.posX, (int) entity.posZ) || this.mc.ingameGUI.getBossOverlay().shouldCreateFog()) {
				GlStateManager.setFogStart(f * 0.05F);
				GlStateManager.setFogEnd(Math.min(f, 192.0F) * 0.5F);
			}
		}

		GlStateManager.enableColorMaterial();
		GlStateManager.enableFog();
		GlStateManager.colorMaterial(1028, 4608);
	}

	public void setupFogColor(boolean black) {
		if(black) {
			GlStateManager.glFog(2918, this.setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
		} else {
			GlStateManager.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
		}
	}

	private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
		this.fogColorBuffer.clear();
		this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
		this.fogColorBuffer.flip();
		return this.fogColorBuffer;
	}

	private void updateFogColor(float partialTicks) {
		World world = this.mc.world;
		Entity entity = this.mc.getRenderViewEntity();
		float f = 0.25F + 0.75F * (float) this.mc.gameSettings.renderDistanceChunks / 32.0F;
		f = 1.0F - (float) Math.pow((double) f, 0.25D);
		Vec3d vec3d = world.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
		float f1 = (float) vec3d.x;
		float f2 = (float) vec3d.y;
		float f3 = (float) vec3d.z;
		Vec3d vec3d1 = world.getFogColor(partialTicks);
		this.fogColorRed = (float) vec3d1.x;
		this.fogColorGreen = (float) vec3d1.y;
		this.fogColorBlue = (float) vec3d1.z;

		if(this.mc.gameSettings.renderDistanceChunks >= 4) {
			double d0 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) > 0.0F ? -1.0D : 1.0D;
			Vec3d vec3d2 = new Vec3d(d0, 0.0D, 0.0D);
			float f5 = (float) entity.getLook(partialTicks).dotProduct(vec3d2);

			if(f5 < 0.0F) {
				f5 = 0.0F;
			}

			if(f5 > 0.0F) {
				float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

				if(afloat != null) {
					f5 = f5 * afloat[3];
					this.fogColorRed = this.fogColorRed * (1.0F - f5) + afloat[0] * f5;
					this.fogColorGreen = this.fogColorGreen * (1.0F - f5) + afloat[1] * f5;
					this.fogColorBlue = this.fogColorBlue * (1.0F - f5) + afloat[2] * f5;
				}
			}
		}

		this.fogColorRed += (f1 - this.fogColorRed) * f;
		this.fogColorGreen += (f2 - this.fogColorGreen) * f;
		this.fogColorBlue += (f3 - this.fogColorBlue) * f;
		float f8 = world.getRainStrength(partialTicks);

		if(f8 > 0.0F) {
			float f4 = 1.0F - f8 * 0.5F;
			float f10 = 1.0F - f8 * 0.4F;
			this.fogColorRed *= f4;
			this.fogColorGreen *= f4;
			this.fogColorBlue *= f10;
		}

		float f9 = world.getThunderStrength(partialTicks);

		if(f9 > 0.0F) {
			float f11 = 1.0F - f9 * 0.5F;
			this.fogColorRed *= f11;
			this.fogColorGreen *= f11;
			this.fogColorBlue *= f11;
		}

		float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
		this.fogColorRed *= f13;
		this.fogColorGreen *= f13;
		this.fogColorBlue *= f13;
		double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks) * world.provider.getVoidFogYFactor();

		if(d1 < 1.0D) {
			if(d1 < 0.0D) {
				d1 = 0.0D;
			}

			d1 = d1 * d1;
			this.fogColorRed = (float) ((double) this.fogColorRed * d1);
			this.fogColorGreen = (float) ((double) this.fogColorGreen * d1);
			this.fogColorBlue = (float) ((double) this.fogColorBlue * d1);
		}

		if(this.bossColorModifier > 0.0F) {
			float f14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
			this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
			this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
			this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
		}

		if(entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isPotionActive(MobEffects.NIGHT_VISION)) {
			float f15 = this.getNightVisionBrightness((EntityLivingBase) entity, partialTicks);
			float f6 = 1.0F / this.fogColorRed;

			if(f6 > 1.0F / this.fogColorGreen) {
				f6 = 1.0F / this.fogColorGreen;
			}

			if(f6 > 1.0F / this.fogColorBlue) {
				f6 = 1.0F / this.fogColorBlue;
			}

			// Forge: fix MC-4647 and MC-10480
			if(Float.isInfinite(f6))
				f6 = Math.nextAfter(f6, 0.0);

			this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
			this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
			this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
		}

		if(this.mc.gameSettings.anaglyph) {
			float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
			float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
			float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
			this.fogColorRed = f16;
			this.fogColorGreen = f17;
			this.fogColorBlue = f7;
		}

		GlStateManager.clearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
	}

	private float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
		int i = entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
		return i > 200 ? 1.0F : 0.7F + MathHelper.sin(((float) i - partialTicks) * (float) Math.PI * 0.2F) * 0.3F;
	}

	private void orientCamera(float partialTicks) {
		Entity entity = this.mc.getRenderViewEntity();
		float f = entity.getEyeHeight();
		double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
		double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
		double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;

		GlStateManager.translate(0.0F, 0.0F, 0.05F);

		GlStateManager.translate(0.0F, -f, 0.0F);
		d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks;
		d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks + (double) f;
		d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks;
		this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
	}

	private void setupCameraTransform(float partialTicks, int pass) {
		this.farPlaneDistance = (float) (this.mc.gameSettings.renderDistanceChunks * 16);
		GlStateManager.matrixMode(5889);
		GlStateManager.loadIdentity();

		if(this.mc.gameSettings.anaglyph) {
			GlStateManager.translate((float) (-(pass * 2 - 1)) * 0.07F, 0.0F, 0.0F);
		}

		Project.gluPerspective(this.getFOVModifier(partialTicks, true), (float) this.mc.displayWidth / (float) this.mc.displayHeight, 0.05F, this.farPlaneDistance * MathHelper.SQRT_2);
		GlStateManager.matrixMode(5888);
		GlStateManager.loadIdentity();

		if(this.mc.gameSettings.anaglyph) {
			GlStateManager.translate((float) (pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		this.orientCamera(partialTicks);
	}

	public void disableLightmap() {
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public void enableLightmap() {
		/* GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.matrixMode(5890);
		GlStateManager.loadIdentity();
		float f = 0.00390625F;
		GlStateManager.scale(0.00390625F, 0.00390625F, 0.00390625F);
		GlStateManager.translate(8.0F, 8.0F, 8.0F);
		GlStateManager.matrixMode(5888);
		this.mc.getTextureManager().bindTexture(this.locationLightMap);
		GlStateManager.glTexParameteri(3553, 10241, 9729);
		GlStateManager.glTexParameteri(3553, 10240, 9729);
		GlStateManager.glTexParameteri(3553, 10242, 10496);
		GlStateManager.glTexParameteri(3553, 10243, 10496);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);*/
	}

	private float getFOVModifier(float partialTicks, boolean useFOVSetting) {

		return 70.0F;

	}

	protected void renderRainSnow(float partialTicks) {
		net.minecraftforge.client.IRenderHandler renderer = this.mc.world.provider.getWeatherRenderer();
		if(renderer != null) {
			renderer.render(partialTicks, this.mc.world, mc);
			return;
		}

		float f = this.mc.world.getRainStrength(partialTicks);

		if(f > 0.0F) {
			this.enableLightmap();
			Entity entity = this.mc.getRenderViewEntity();
			World world = this.mc.world;
			int i = MathHelper.floor(entity.posX);
			int j = MathHelper.floor(entity.posY);
			int k = MathHelper.floor(entity.posZ);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			GlStateManager.disableCull();
			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.alphaFunc(516, 0.1F);
			double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
			double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
			double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
			int l = MathHelper.floor(d1);
			int i1 = 5;

			if(this.mc.gameSettings.fancyGraphics) {
				i1 = 10;
			}

			int j1 = -1;
			float f1 = (float) this.rendererUpdateCount + partialTicks;
			bufferbuilder.setTranslation(-d0, -d1, -d2);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for(int k1 = k - i1; k1 <= k + i1; ++k1) {
				for(int l1 = i - i1; l1 <= i + i1; ++l1) {
					int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
					double d3 = (double) this.rainXCoords[i2] * 0.5D;
					double d4 = (double) this.rainYCoords[i2] * 0.5D;
					blockpos$mutableblockpos.setPos(l1, 0, k1);
					Biome biome = world.getBiome(blockpos$mutableblockpos);

					if(biome.canRain() || biome.getEnableSnow()) {
						int j2 = world.getPrecipitationHeight(blockpos$mutableblockpos).getY();
						int k2 = j - i1;
						int l2 = j + i1;

						if(k2 < j2) {
							k2 = j2;
						}

						if(l2 < j2) {
							l2 = j2;
						}

						int i3 = j2;

						if(j2 < l) {
							i3 = l;
						}

						if(k2 != l2) {
							this.random.setSeed((long) (l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
							blockpos$mutableblockpos.setPos(l1, k2, k1);
							float f2 = biome.getTemperature(blockpos$mutableblockpos);

							if(world.getBiomeProvider().getTemperatureAtHeight(f2, j2) >= 0.15F) {
								if(j1 != 0) {
									if(j1 >= 0) {
										tessellator.draw();
									}

									j1 = 0;
									this.mc.getTextureManager().bindTexture(RAIN_TEXTURES);
									bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
								}

								double d5 = -((double) (this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 31) + (double) partialTicks) / 32.0D * (3.0D + this.random.nextDouble());
								double d6 = (double) ((float) l1 + 0.5F) - entity.posX;
								double d7 = (double) ((float) k1 + 0.5F) - entity.posZ;
								float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / (float) i1;
								float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * f;
								blockpos$mutableblockpos.setPos(l1, i3, k1);
								int j3 = world.getCombinedLight(blockpos$mutableblockpos, 0);
								int k3 = j3 >> 16 & 65535;
								int l3 = j3 & 65535;
								bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) l2, (double) k1 - d4 + 0.5D).tex(0.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
								bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) l2, (double) k1 + d4 + 0.5D).tex(1.0D, (double) k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
								bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) k2, (double) k1 + d4 + 0.5D).tex(1.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
								bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) k2, (double) k1 - d4 + 0.5D).tex(0.0D, (double) l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
							} else {
								if(j1 != 1) {
									if(j1 >= 0) {
										tessellator.draw();
									}

									j1 = 1;
									this.mc.getTextureManager().bindTexture(SNOW_TEXTURES);
									bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
								}

								double d8 = (double) (-((float) (this.rendererUpdateCount & 511) + partialTicks) / 512.0F);
								double d9 = this.random.nextDouble() + (double) f1 * 0.01D * (double) ((float) this.random.nextGaussian());
								double d10 = this.random.nextDouble() + (double) (f1 * (float) this.random.nextGaussian()) * 0.001D;
								double d11 = (double) ((float) l1 + 0.5F) - entity.posX;
								double d12 = (double) ((float) k1 + 0.5F) - entity.posZ;
								float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / (float) i1;
								float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f;
								blockpos$mutableblockpos.setPos(l1, i3, k1);
								int i4 = (world.getCombinedLight(blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
								int j4 = i4 >> 16 & 65535;
								int k4 = i4 & 65535;
								bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) l2, (double) k1 - d4 + 0.5D).tex(0.0D + d9, (double) k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
								bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) l2, (double) k1 + d4 + 0.5D).tex(1.0D + d9, (double) k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
								bufferbuilder.pos((double) l1 + d3 + 0.5D, (double) k2, (double) k1 + d4 + 0.5D).tex(1.0D + d9, (double) l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
								bufferbuilder.pos((double) l1 - d3 + 0.5D, (double) k2, (double) k1 - d4 + 0.5D).tex(0.0D + d9, (double) l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
							}
						}
					}
				}
			}

			if(j1 >= 0) {
				tessellator.draw();
			}

			bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.alphaFunc(516, 0.1F);
			this.disableLightmap();
		}
	}

}
