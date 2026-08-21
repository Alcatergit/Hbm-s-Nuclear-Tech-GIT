package com.hbm.render.entity.effect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.List;

import com.hbm.main.ModEventHandlerClient;
import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityNukeTorex.Cloudlet;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.hbm.handler.HbmShaderManager2.Shader;
import com.hbm.render.GLCompat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;

import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;

public class RenderTorex extends Render<EntityNukeTorex> {

	public static final IRenderFactory<EntityNukeTorex> FACTORY = RenderTorex::new;
	
	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private static final ResourceLocation flare = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");

	public static final int flashBaseDuration = 30;
	public static final int flareBaseDuration = 100;

	private static final List<Shockwave> warpActive = new ArrayList<>();
	private static Framebuffer warpSceneCopy;
	private static Shader warpShader;

	protected RenderTorex(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(EntityNukeTorex cloud, double x, double y, double z, float entityYaw, float partialTicks){
		float scale = (float)cloud.getScale();
		float flashDuration = scale * flashBaseDuration;
		float flareDuration = scale * flareBaseDuration;
		spawnWarp(cloud);

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog)
			GL11.glDisable(GL11.GL_FOG);

		cloudletWrapper(cloud, partialTicks);

		if(cloud.ticksExisted < flareDuration+1)
			flareWrapper(cloud, partialTicks, flareDuration);

		if(cloud.ticksExisted < flashDuration+1)
			flashWrapper(cloud, partialTicks, flashDuration);
		if(cloud.ticksExisted < (flashDuration / 10) && System.currentTimeMillis() - ModEventHandlerClient.flashTimestamp > 1_000) ModEventHandlerClient.flashTimestamp = System.currentTimeMillis();
		if(cloud.didPlaySound && !cloud.didShake && System.currentTimeMillis() - ModEventHandlerClient.shakeTimestamp > 1_000) {
			EntityPlayer player = MainRegistry.proxy.me();
			float dist = player.getDistance(cloud);
			ModEventHandlerClient.shakeTimestamp = System.currentTimeMillis();
			ModEventHandlerClient.shakeMultiplier = Math.max(((scale * 200D) - (double) dist) / (scale * 200D), 0D);
			player.hurtTime = Math.max((int) (((((scale * 200F) - dist)) / (scale * 200F)) * 150F), 0);
			player.maxHurtTime = Math.max((int) (((((scale * 200F) - dist)) / (scale * 200F)) * 100F), 0);
			player.attackedAtYaw = 0F;
			cloud.didShake = true;
		}

		if(fog)
			GL11.glEnable(GL11.GL_FOG);

		GL11.glPopMatrix();
	}

	private final Comparator cloudSorter = (arg0, arg1) -> {
        Cloudlet first = (Cloudlet) arg0;
        Cloudlet second = (Cloudlet) arg1;
        EntityPlayer player = MainRegistry.proxy.me();
        double dist1 = player.getDistanceSq(first.posX, first.posY, first.posZ);
        double dist2 = player.getDistanceSq(second.posX, second.posY, second.posZ);

        return Double.compare(dist2, dist1);
    };

	private void cloudletWrapper(EntityNukeTorex cloud, float partialTicks) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		// To prevent particles cutting off before fully fading out
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();
		
		bindTexture(cloudlet);

		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		
		ArrayList<Cloudlet> cloudlets = new ArrayList<>(cloud.cloudlets);
		cloudlets.sort(cloudSorter);
		
		for(Cloudlet cloudlet : cloudlets) {
			Vec3 vec = cloudlet.getInterpPos(partialTicks);
			tessellateCloudlet(buf, vec.xCoord - cloud.posX, vec.yCoord - cloud.posY, vec.zCoord - cloud.posZ, cloudlet, partialTicks);
		}

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private void flareWrapper(EntityNukeTorex cloud, float partialTicks, float flareDuration) {

		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();
			
		bindTexture(flare);

		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		
		double age = Math.min(cloud.ticksExisted + partialTicks, flareDuration);
		float alpha = (float) Math.min(1, (flareDuration - age) / flareDuration);
		
		Random rand = new Random(cloud.getEntityId());
		
		for(int i = 0; i < 3; i++) {
			float x = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float y = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float z = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			tessellateFlare(buf, x, y + cloud.coreHeight, z, (float) (10 * cloud.rollerSize), alpha, partialTicks);
		}

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}

	private void tessellateCloudlet(BufferBuilder buf, double posX, double posY, double posZ, Cloudlet cloud, float partialTicks) {

		float a = cloud.getAlpha();
		float scale = cloud.getScale();

		float f1 = ActiveRenderInfo.getRotationX();
		float f2 = ActiveRenderInfo.getRotationZ();
		float f3 = ActiveRenderInfo.getRotationYZ();
		float f4 = ActiveRenderInfo.getRotationXY();
		float f5 = ActiveRenderInfo.getRotationXZ();

		float brightness = cloud.type == EntityNukeTorex.TorexType.CONDENSATION ? 0.9F : 0.75F * cloud.colorMod;
		Vec3 color = cloud.getInterpColor(partialTicks);
		float r, g, b;
		r =  Math.max(0.15F, (float)color.xCoord * brightness);
		g =  Math.max(0.15F, (float)color.yCoord * brightness);
		b =  Math.max(0.15F, (float)color.zCoord * brightness);

		int br = (int)Math.max(48, (Math.min((r+g+b) / 3D, 1) * 240));
		r = Math.min(1F, r);
		g = Math.min(1F, g);
		b = Math.min(1F, b);

		buf.pos((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale)).tex(1, 1).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale)).tex(1, 0).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale)).tex(0, 0).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale)).tex(0, 1).color(r, g, b, a).lightmap(br, br).endVertex();
	}

	private void tessellateFlare(BufferBuilder buf, double posX, double posY, double posZ, float scale, float a, float partialTicks) {

		float f1 = ActiveRenderInfo.getRotationX();
		float f2 = ActiveRenderInfo.getRotationZ();
		float f3 = ActiveRenderInfo.getRotationYZ();
		float f4 = ActiveRenderInfo.getRotationXY();
		float f5 = ActiveRenderInfo.getRotationXZ();
		int br = (int)(a * 240);
		buf.pos((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale)).tex(1, 1).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale)).tex(1, 0).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale)).tex(0, 0).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale)).tex(0, 1).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();

	}

	private void flashWrapper(EntityNukeTorex cloud, float interp, float flashDuration) {

        if(cloud.ticksExisted < flashDuration) {

    		GL11.glPushMatrix();
    		//Function [0, 1] that determines the scale and intensity (inverse!) of the flash
        	double intensity = (cloud.ticksExisted + interp) / flashDuration;
        	GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);

        	//Euler function to slow down the scale as it progresses
        	//Makes it start fast and the fade-out is nice and smooth
        	intensity = intensity * Math.pow(Math.E, -intensity) * 2.717391304D;

        	renderFlash(50F * (float)flashDuration/(float)flashBaseDuration, intensity, cloud.coreHeight);
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
    		GL11.glPopMatrix();
        }
	}

	private void renderFlash(float scale, double intensity, double height) {

    	GL11.glScalef(0.2F, 0.2F, 0.2F);
    	GL11.glTranslated(0, height * 4, 0);

    	double inverse = 1.0D - intensity;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
        GL11.glPushMatrix();

        for(int i = 0; i < 300; i++) {

            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);

            float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float)(intensity * scale);
            float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float)(intensity * scale);

            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            buf.pos(0D, 0D, 0D).color(1.0F, 1.0F, 1.0F, (float) inverse).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.0D, vert1, 1.0D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }

        GL11.glPopMatrix();

        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNukeTorex entity) {
		return null;
	}

	public static void spawnWarp(EntityNukeTorex entity) {
		if ((entity == null || entity.isDead) || (entity.isReloaded && !entity.isScaled)) return;
		for (Shockwave sw : warpActive) {
			if (sw.entity == entity) return;
		}

		double s = entity.getScale();
		int localTicks = Math.max((int) (entity.ticksExisted - (s * 2.5)), 0);

		int explosionRadius = (int) (s * 100.0);
		int blastDuration = (int) Math.ceil(80 * Math.cbrt(explosionRadius / 100.0));
		double shockSpeed = Math.max(2.0, 2.0 * explosionRadius / (double) blastDuration);
		float maxRadius = (float) (2.0 * explosionRadius);
		float lifetime = (float) (maxRadius / shockSpeed);
		if (localTicks < 1) return;

		float spawnTick = 0;
		if (localTicks == 1) {
			spawnTick = entity.ticksExisted;
		} else if (entity.isReloaded && entity.isScaled) {
			spawnTick = (int) Math.ceil(s * 2.5 + 1);
			if (entity.ticksExisted - spawnTick >= lifetime) return;
		}

		warpActive.add(new Shockwave(entity.posX, entity.posY, entity.posZ, maxRadius, lifetime, (float) s, entity, spawnTick));
	}

	public static void renderWarp(float partialTicks) {
		if (warpActive.isEmpty()) return;

		Minecraft mc = Minecraft.getMinecraft();
		if (mc.world == null || mc.getRenderViewEntity() == null) return;

		warpActive.removeIf(sw -> sw.entity == null || sw.entity.world != mc.world || sw.entity.isDead || sw.getAge(partialTicks) >= sw.lifetime);
		if (warpActive.isEmpty()) return;

		if (warpShader == null) {
			warpShader = loadWarpShader();
		}
		int shader = warpShader.getShaderId();
		if (shader == 0) {
			return;
		}

		Framebuffer mainFbo = mc.getFramebuffer();
		int w = mainFbo.framebufferWidth;
		int h = mainFbo.framebufferHeight;

		ensureWarpSceneCopy(w, h);

		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, mainFbo.framebufferObject);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, warpSceneCopy.framebufferObject);
		GL30.glBlitFramebuffer(0, 0, w, h, 0, 0, w, h, GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, mainFbo.framebufferObject);

		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();

		GlStateManager.depthMask(false);
		GlStateManager.enableDepth();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableCull();
		GlStateManager.disableLighting();
		GlStateManager.enableTexture2D();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, warpSceneCopy.framebufferTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

		OpenGlHelper.glUseProgram(shader);
		GL20.glUniform2f(GL20.glGetUniformLocation(shader, "screenSize"), w, h);

		Entity viewer = mc.getRenderViewEntity();
		double viewX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * partialTicks;
		double viewY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * partialTicks;
		double viewZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * partialTicks;

		for (Shockwave sw : warpActive) {
			float age = sw.getAge(partialTicks);
			float radius = sw.getRadius(age);
			float alpha = sw.getAlpha(age);
			if (alpha <= 0.01F) continue;

			GL20.glUniform1f(GL20.glGetUniformLocation(shader, "intensity"), alpha * sw.intensityScale);
			GL20.glUniform1f(GL20.glGetUniformLocation(shader, "time"), sw.entity.ticksExisted + partialTicks);

			GlStateManager.pushMatrix();
			GlStateManager.translate(sw.x - viewX, sw.y - viewY, sw.z - viewZ);
			GlStateManager.scale(radius, radius, radius);

			renderWarpSphere(16, 32);

			GlStateManager.popMatrix();
		}

		OpenGlHelper.glUseProgram(0);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.enableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);

		GlStateManager.popAttrib();
		GlStateManager.popMatrix();

		mc.getFramebuffer().bindFramebuffer(true);
	}

	private static void ensureWarpSceneCopy(int width, int height) {
		if (warpSceneCopy == null || warpSceneCopy.framebufferWidth != width
				|| warpSceneCopy.framebufferHeight != height) {
			if (warpSceneCopy != null) warpSceneCopy.deleteFramebuffer();
			warpSceneCopy = new Framebuffer(width, height, false);
			warpSceneCopy.setFramebufferColor(0, 0, 0, 0);
		}
	}

	private static void renderWarpSphere(int stacks, int slices) {
		for (int i = 0; i < stacks; i++) {
			float lat0 = (float) (Math.PI * (-0.5 + (double) i / stacks));
			float z0 = (float) Math.sin(lat0);
			float zr0 = (float) Math.cos(lat0);

			float lat1 = (float) (Math.PI * (-0.5 + (double) (i + 1) / stacks));
			float z1 = (float) Math.sin(lat1);
			float zr1 = (float) Math.cos(lat1);

			GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
			for (int j = 0; j <= slices; j++) {
				float lng = (float) (2 * Math.PI * (double) j / slices);
				float x = (float) Math.cos(lng);
				float y = (float) Math.sin(lng);

				float nx0 = x * zr0;
				float ny0 = y * zr0;
				float nz0 = z0;
				GL11.glNormal3f(nx0, ny0, nz0);
				GL11.glVertex3f(nx0, ny0, nz0);

				float nx1 = x * zr1;
				float ny1 = y * zr1;
				float nz1 = z1;
				GL11.glNormal3f(nx1, ny1, nz1);
				GL11.glVertex3f(nx1, ny1, nz1);
			}
			GL11.glEnd();
		}
	}

	private static Shader loadWarpShader() {
		try {
			ResourceLocation file = new ResourceLocation(RefStrings.MODID, "shaders/warp_shockwave");
			int program = GLCompat.createProgram();

			int vertexShader = GLCompat.createShader(GLCompat.GL_VERTEX_SHADER);
			GLCompat.shaderSource(vertexShader, readShaderFile(new ResourceLocation(file.getNamespace(), file.getPath() + ".vert")));
			GLCompat.compileShader(vertexShader);
			if (GLCompat.getShaderi(vertexShader, GLCompat.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GLCompat.getShaderInfoLog(vertexShader, GLCompat.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating warp vertex shader");
			}

			int fragmentShader = GLCompat.createShader(GLCompat.GL_FRAGMENT_SHADER);
			GLCompat.shaderSource(fragmentShader, readShaderFile(new ResourceLocation(file.getNamespace(), file.getPath() + ".frag")));
			GLCompat.compileShader(fragmentShader);
			if (GLCompat.getShaderi(fragmentShader, GLCompat.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GLCompat.getShaderInfoLog(fragmentShader, GLCompat.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating warp fragment shader");
			}

			GLCompat.attachShader(program, vertexShader);
			GLCompat.attachShader(program, fragmentShader);
			GLCompat.linkProgram(program);
			if (GLCompat.getProgrami(program, GLCompat.GL_LINK_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GLCompat.getProgramInfoLog(program, GLCompat.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error linking warp shader");
			}

			GLCompat.deleteShader(vertexShader);
			GLCompat.deleteShader(fragmentShader);

			return new Shader(program).withUniforms(s -> s.uniform1i("sceneTex", 0));
		} catch (Exception x) {
			MainRegistry.logger.error("Failed to load warp shader, warp effect disabled");
			x.printStackTrace();
			return new Shader(0);
		}
	}

	private static ByteBuffer readShaderFile(ResourceLocation file) throws IOException {
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(file).getInputStream();
		byte[] bytes = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		ByteBuffer buf = BufferUtils.createByteBuffer(bytes.length);
		buf.put(bytes);
		buf.rewind();
		return buf;
	}

	private static class Shockwave {
		final double x, y, z;
		final float maxRadius, lifetime, intensityScale;
		final EntityNukeTorex entity;
		final float spawnTick;

		Shockwave(double x, double y, double z, float maxRadius, float lifetime, float scale, EntityNukeTorex entity, float spawnTick) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.maxRadius = maxRadius;
			this.lifetime = lifetime;
			this.intensityScale = Math.min(scale * 0.5F, 1.5F);
			this.entity = entity;
			this.spawnTick = spawnTick;
		}

		float getAge(float partialTicks) {
			return entity.ticksExisted + partialTicks - spawnTick;
		}

		float getRadius(float age) {
			float t = Math.min(age / lifetime, 1.0F);
			return maxRadius * t + 1.0F;
		}

		float getAlpha(float age) {
			float fadeStart = lifetime * 0.72F;
			if (age < fadeStart) return 1.0F;
			float fadeT = (age - fadeStart) / (lifetime - fadeStart);
			return (float) (1.0 - Math.pow(fadeT, 3.0));
		}
	}
}
