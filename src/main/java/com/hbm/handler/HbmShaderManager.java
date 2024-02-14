package com.hbm.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Spaghetti("Oh god, 85% of this class is useless unused trash")
@Deprecated
public class HbmShaderManager {

	private static float targetWidth;
	private static float targetHeight;

	private static final boolean arbShaders = !GLContext.getCapabilities().OpenGL21;

	private static final int VERT = arbShaders ? ARBVertexShader.GL_VERTEX_SHADER_ARB : GL20.GL_VERTEX_SHADER;
	private static final int FRAG = arbShaders ? ARBFragmentShader.GL_FRAGMENT_SHADER_ARB : GL20.GL_FRAGMENT_SHADER;
	private static final int VALIDATE_STATUS = arbShaders ? ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB : GL20.GL_VALIDATE_STATUS;
	private static final int INFO_LOG_LENGTH = arbShaders ? ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB : GL20.GL_INFO_LOG_LENGTH;

	public static final Uniform WORLD_TIME = Uniform.createUniform("worldTime", () -> {
		return Minecraft.getMinecraft().world.getTotalWorldTime() + Minecraft.getMinecraft().getRenderPartialTicks();
	});
	public static final Uniform TIME = Uniform.createUniform("time", () -> {
		return System.currentTimeMillis()/1000F;
	});
	public static final Uniform TARGET_WIDTH = Uniform.createUniform("targetWidth", () -> {
		return targetWidth;
	});
	public static final Uniform TARGET_HEIGHT = Uniform.createUniform("targetHeight", () -> {
		return targetHeight;
	});

	public static Framebuffer gaussFbo;

	public static Framebuffer fbo6;
	public static Framebuffer fbo7;
	public static Framebuffer fbo8;

	public static List<Runnable> gaussRenderers = new ArrayList<Runnable>();
	private static boolean firstRun = false;
	private static int displayWidth;
	private static int displayHeight;

	public static int gauss;
	// Horizontal gaussian for two pass blur
	public static int hGauss;
	// Vertical gaussian for two pass blur
	public static int vGauss;
	public static int combine;

	// Flashlight stuff for making sure the beam doesn't go past any geometry
	public static int flashlightBeam;
	public static int flashlightWorld;
	public static int deferredFlashlight;
	
	public static int dissolve;
	public static int bfg_worm;
	public static int bfg_beam;
	
	public static int noise1;
	public static int noise2;

	// public static FloatBuffer testBuf1;
	// public static FloatBuffer testBuf2;

	public static void addRenderForGauss(Runnable r) {
		gaussRenderers.add(r);
	}

	public static void renderGauss() {
		if(!GeneralConfig.useShaders)
			return;
		// Drillgon200: I don't know why I have to do this but shaders only work
		// if I do.
		GlStateManager.disableBlend();

		if(firstRun || Minecraft.getMinecraft().displayWidth != displayWidth || Minecraft.getMinecraft().displayHeight != displayHeight) {
			displayWidth = Minecraft.getMinecraft().displayWidth;
			displayHeight = Minecraft.getMinecraft().displayHeight;
			firstRun = false;

			gaussFbo.createBindFramebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			gaussFbo.setFramebufferColor(0.0F, 0.0F, 0.0F, 1.0F);

			fbo6.createBindFramebuffer(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2);
			fbo6.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
			fbo7.createBindFramebuffer(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2);
			fbo7.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);

			fbo8.createBindFramebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
			fbo8.setFramebufferColor(0.0F, 0.0F, 0.0F, 1.0F);
		}
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.pushMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();

		gaussFbo.framebufferClear();
		gaussFbo.bindFramebuffer(true);

		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, Minecraft.getMinecraft().getFramebuffer().framebufferObject);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, gaussFbo.framebufferObject);
		GL30.glBlitFramebuffer(0, 0, gaussFbo.framebufferWidth, gaussFbo.framebufferHeight, 0, 0, gaussFbo.framebufferWidth, gaussFbo.framebufferHeight, GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);

		for(Runnable r : gaussRenderers)
			r.run();
		gaussRenderers.clear();

		fbo6.framebufferClear();
		fbo6.bindFramebuffer(true);

		useShader(vGauss);
		targetHeight = fbo6.framebufferHeight;
		TARGET_HEIGHT.assign(vGauss);
		renderFBOAlpha(gaussFbo, fbo6.framebufferWidth, fbo6.framebufferHeight);
		releaseShader();

		fbo7.framebufferClear();
		fbo7.bindFramebuffer(true);
		useShader(hGauss);
		targetWidth = fbo7.framebufferWidth;
		TARGET_WIDTH.assign(hGauss);
		fbo6.framebufferRender(fbo7.framebufferWidth, fbo7.framebufferHeight);
		releaseShader();

		fbo8.framebufferClear();
		fbo8.bindFramebuffer(true);
		useShader(vGauss);
		targetHeight = fbo8.framebufferHeight;
		TARGET_HEIGHT.assign(vGauss);
		fbo7.framebufferRender(fbo8.framebufferWidth, fbo8.framebufferHeight);
		releaseShader();

		gaussFbo.framebufferClear();
		gaussFbo.bindFramebuffer(true);
		useShader(hGauss);
		targetWidth = gaussFbo.framebufferWidth;
		TARGET_WIDTH.assign(hGauss);
		fbo8.framebufferRender(gaussFbo.framebufferWidth, gaussFbo.framebufferHeight);
		releaseShader();

		Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
		useShader(combine);
		GL20.glUniform1i(GL20.glGetUniformLocation(combine, "mcImage"), 2);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		int tex = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().getFramebuffer().framebufferTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		gaussFbo.framebufferRender(Minecraft.getMinecraft().getFramebuffer().framebufferWidth, Minecraft.getMinecraft().displayHeight);

		releaseShader();

		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		GL11.glPopMatrix();
		GlStateManager.enableDepth();
		GlStateManager.enableLighting();
		GlStateManager.enableAlpha();
	}

	public static void stealDepthBuffer() {
		Framebuffer mainBuf = Minecraft.getMinecraft().getFramebuffer();
		gaussFbo.bindFramebuffer(false);
		OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, mainBuf.depthBuffer);
		mainBuf.bindFramebuffer(false);
	}

	public static void renderFBOAlpha(Framebuffer buf, int width, int height) {
		if(OpenGlHelper.isFramebufferEnabled()) {

			GlStateManager.colorMask(true, true, true, false);
			GlStateManager.disableDepth();
			GlStateManager.depthMask(false);

			GlStateManager.matrixMode(5889);
			GlStateManager.loadIdentity();
			GlStateManager.ortho(0.0D, (double) width, (double) height, 0.0D, 1000.0D, 3000.0D);
			GlStateManager.matrixMode(5888);

			GlStateManager.loadIdentity();

			GlStateManager.translate(0.0F, 0.0F, -2000.0F);
			GlStateManager.viewport(0, 0, width, height);
			GlStateManager.enableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.disableAlpha();

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			buf.bindFramebufferTexture();
			float f = (float) width;
			float f1 = (float) height;
			float f2 = (float) buf.framebufferWidth / (float) buf.framebufferTextureWidth;
			float f3 = (float) buf.framebufferHeight / (float) buf.framebufferTextureHeight;
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.pos(0.0D, (double) f1, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
			bufferbuilder.pos((double) f, (double) f1, 0.0D).tex((double) f2, 0.0D).color(255, 255, 255, 255).endVertex();
			bufferbuilder.pos((double) f, 0.0D, 0.0D).tex((double) f2, (double) f3).color(255, 255, 255, 255).endVertex();
			bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, (double) f3).color(255, 255, 255, 255).endVertex();
			tessellator.draw();
			buf.unbindFramebufferTexture();
			GlStateManager.depthMask(true);
			GlStateManager.enableDepth();
			GlStateManager.colorMask(true, true, true, true);
		}
	}

	public static void loadShaders() {
		if(GeneralConfig.useShaders) {
			gaussFbo = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, true);
			fbo6 = new Framebuffer(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2, true);
			fbo7 = new Framebuffer(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2, true);
			fbo8 = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, true);
			gauss = createShader("gauss.frag", "gauss.vert");
			hGauss = createShader("vGauss.frag", "vGauss.vert");
			vGauss = createShader("hGauss.frag", "hGauss.vert");
			combine = createShader("combine.frag", "combine.vert");
			flashlightBeam = createShader("flashlightbeam.frag", "flashlightbeam.vert");
			flashlightWorld = createShader("flashlightworld.frag", "flashlightworld.vert");
			deferredFlashlight = createShader("deferredflashlight.frag", "deferredflashlight.vert");
		}
		if(GeneralConfig.useShaders2){
			dissolve = createShader("dissolve.frag", "dissolve.vert");
			bfg_worm = createShader("bfg_worm.frag", "bfg_worm.vert");
			bfg_beam = createShader("bfg_beam.frag", "bfg_worm.vert");
			SimpleTexture tex = new SimpleTexture(new ResourceLocation(RefStrings.MODID, "textures/misc/perlin1.png"));
			try {
				tex.loadTexture(Minecraft.getMinecraft().getResourceManager());
			} catch(IOException e) {
				e.printStackTrace();
			}
			noise1 = tex.getGlTextureId();
			GlStateManager.bindTexture(noise1);
			GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
			GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
			tex = new SimpleTexture(new ResourceLocation(RefStrings.MODID, "textures/misc/perlin2.png"));
			try {
				tex.loadTexture(Minecraft.getMinecraft().getResourceManager());
			} catch(IOException e) {
				e.printStackTrace();
			}
			noise2 = tex.getGlTextureId();
			GlStateManager.bindTexture(noise2);
			GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
			GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL14.GL_MIRRORED_REPEAT);
		}
	}

	private static int createShader(String frag, String vert) {
		int prog = OpenGlHelper.glCreateProgram();
		if(prog == 0)
			return 0;
		int vertexShader = createVertexShader("/assets/" + RefStrings.MODID + "/shaders/" + vert);
		int fragShader = createFragShader("/assets/" + RefStrings.MODID + "/shaders/" + frag);
		OpenGlHelper.glAttachShader(prog, vertexShader);
		OpenGlHelper.glAttachShader(prog, fragShader);

		OpenGlHelper.glLinkProgram(prog);
		if(OpenGlHelper.glGetProgrami(prog, OpenGlHelper.GL_LINK_STATUS) == GL11.GL_FALSE) {
			MainRegistry.logger.log(Level.ERROR, "Error creating shader " + frag + " " + vert);
			MainRegistry.logger.error(OpenGlHelper.glGetProgramInfoLog(prog, 32768));
			return 0;
		}

		glValidateProgram(prog);

		if(OpenGlHelper.glGetProgrami(prog, VALIDATE_STATUS) == GL11.GL_FALSE) {
			MainRegistry.logger.log(Level.ERROR, "Error validating shader " + frag + " " + vert);
			return 0;
		}
		return prog;
	}

	private static int createFragShader(String shaderSource) {
		int shader = 0;
		try {
			shader = OpenGlHelper.glCreateShader(FRAG);
			if(shader == 0)
				return 0;
			OpenGlHelper.glShaderSource(shader, readFileToBuf(shaderSource));
			OpenGlHelper.glCompileShader(shader);

			if(OpenGlHelper.glGetShaderi(shader, OpenGlHelper.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(OpenGlHelper.glGetShaderInfoLog(shader, INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating shader: " + shaderSource);
			}
			return shader;
		} catch(Exception x) {
			OpenGlHelper.glDeleteShader(shader);
			x.printStackTrace();
			return -1;
		}
	}

	private static int createVertexShader(String shaderSource) {
		int shader = 0;
		try {
			shader = OpenGlHelper.glCreateShader(VERT);
			if(shader == 0)
				return 0;
			OpenGlHelper.glShaderSource(shader, readFileToBuf(shaderSource));
			OpenGlHelper.glCompileShader(shader);

			if(OpenGlHelper.glGetShaderi(shader, OpenGlHelper.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(OpenGlHelper.glGetShaderInfoLog(shader, INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating shader: " + shaderSource);
			}
			return shader;
		} catch(Exception x) {
			OpenGlHelper.glDeleteShader(shader);
			x.printStackTrace();
			return -1;
		}
	}

	public static ByteBuffer readFileToBuf(String file) throws IOException {

		InputStream in = ShaderManager.class.getResourceAsStream(file);

		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
		return (ByteBuffer) BufferUtils.createByteBuffer(bytes.length).put(bytes).position(0);
	}

	public static void glValidateProgram(int prog) {
		if(arbShaders) {
			ARBShaderObjects.glValidateProgramARB(prog);
		} else {
			GL20.glValidateProgram(prog);
		}
	}

	public static boolean shouldUseShader(int shader) {
		return OpenGlHelper.shadersSupported && GeneralConfig.useShaders;
	}
	
	public static boolean shouldUseShader2(int shader) {
		return OpenGlHelper.shadersSupported && GeneralConfig.useShaders2;
	}

	public static void useShader(int shader) {
		if(!shouldUseShader(shader))
			return;
		OpenGlHelper.glUseProgram(shader);
	}
	
	public static void useShader2(int shader) {
		if(!shouldUseShader2(shader))
			return;
		OpenGlHelper.glUseProgram(shader);
	}
	
	public static void useDissolveShader(float amount){
		useShader2(dissolve);
		amount = MathHelper.clamp(amount, 0.0F, 1.0F);
		amount = BobMathUtil.remap(amount, 0.0F, 1.0F, 0.34F, 0.7F);
		GL20.glUniform1f(GL20.glGetUniformLocation(dissolve, "amount"), amount);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, noise1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL20.glUniform1i(GL20.glGetUniformLocation(dissolve, "lightmap"), 1);
		GL20.glUniform1i(GL20.glGetUniformLocation(dissolve, "noise"), 2);
		WORLD_TIME.assign(dissolve);
		GL20.glUniform2f(GL20.glGetUniformLocation(dissolve, "noiseScroll"), 0.01F, 0.01F);
	}
	
	public static void useWormShader(float offset){
		useShader2(bfg_worm);
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, noise1);
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, noise2);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL20.glUniform1i(GL20.glGetUniformLocation(bfg_worm, "noise"), 2);
		GL20.glUniform1i(GL20.glGetUniformLocation(bfg_worm, "bigNoise"), 3);
		float worldTime = Minecraft.getMinecraft().world.getTotalWorldTime() + Minecraft.getMinecraft().getRenderPartialTicks() + offset;
		GL20.glUniform1f(GL20.glGetUniformLocation(bfg_worm, "worldTime"), worldTime/4);
	}

	public static void releaseShader() {
		useShader(0);
	}
	
	public static void releaseShader2() {
		useShader2(0);
	}

	public static class Uniform {

		FloatSupplier ints;
		String name;

		public Uniform(String name, FloatSupplier ints) {
			this.name = name;
			this.ints = ints;
		}

		public static Uniform createUniform(String name, FloatSupplier ints) {
			return new Uniform(name, ints);
		}

		public void assign(int shader) {
			if(arbShaders) {
				ARBShaderObjects.glUniform1fARB(OpenGlHelper.glGetUniformLocation(shader, name), ints.getAsFloat());
			} else {
				GL20.glUniform1f(OpenGlHelper.glGetUniformLocation(shader, name), ints.getAsFloat());
			}
		}

	}

	public static interface FloatSupplier {
		public float getAsFloat();
	}

	public static boolean isActiveShader(int prog) {
		if(GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM) == prog)
			return true;
		return false;
	}

}
