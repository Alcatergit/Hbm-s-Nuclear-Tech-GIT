package com.hbm.flashlight;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import com.hbm.handler.HbmShaderManager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;

@Deprecated
public class FlashlightFramebuffer extends Framebuffer {

	public int positionTexture;
	public int normalTexture;
	
	public FlashlightFramebuffer(int width, int height, boolean useDepthIn) {
		super(width, height, useDepthIn);
	}

	@Override
	public void createFramebuffer(int width, int height) {
		this.framebufferWidth = width;
		this.framebufferHeight = height;
		this.framebufferTextureWidth = width;
		this.framebufferTextureHeight = height;

		if(!OpenGlHelper.isFramebufferEnabled()) {
			this.framebufferClear();
		} else {
			this.framebufferObject = OpenGlHelper.glGenFramebuffers();
			this.framebufferTexture = TextureUtil.glGenTextures();

			if(this.useDepth) {
				this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
			}

			this.setFramebufferFilter(9728);
			GlStateManager.bindTexture(this.framebufferTexture);
			GlStateManager.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (IntBuffer) null);
			OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
			OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);

			
			//Position and normal buffers begin
			positionTexture = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, positionTexture);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB16, framebufferTextureWidth, framebufferTextureHeight, 0, GL11.GL_RGB, GL11.GL_FLOAT, (IntBuffer) null);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, GL11.GL_TEXTURE_2D, positionTexture, 0);
			
			normalTexture = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, positionTexture);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB16, framebufferTextureWidth, framebufferTextureHeight, 0, GL11.GL_RGB, GL11.GL_FLOAT, (IntBuffer) null);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT2, GL11.GL_TEXTURE_2D, normalTexture, 0);
			
			//Position and normal buffers end
			
			IntBuffer attachments = BufferUtils.createIntBuffer(3);
			attachments.put(new int[]{GL30.GL_COLOR_ATTACHMENT0, GL30.GL_COLOR_ATTACHMENT1, GL30.GL_COLOR_ATTACHMENT2});
			GL20.glDrawBuffers(attachments);
			
			if(this.useDepth) {
				OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
				OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
				OpenGlHelper.glFramebufferRenderbuffer(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBuffer);
			}

			this.framebufferClear();
			this.unbindFramebufferTexture();
		}
	}

	@Override
	public void deleteFramebuffer() {
		if(OpenGlHelper.isFramebufferEnabled()) {
			this.unbindFramebufferTexture();
			this.unbindFramebuffer();

			if(this.depthBuffer > -1) {
				OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
				this.depthBuffer = -1;
			}

			if(this.framebufferTexture > -1) {
				TextureUtil.deleteTexture(this.framebufferTexture);
				this.framebufferTexture = -1;
			}

			if(this.framebufferObject > -1) {
				OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
				OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
				this.framebufferObject = -1;
			}
		}
	}
	
	public void postProcess(){
		int prevTexture0;
		int prevTexture6;
		int prevTexture7;
		this.bindFramebuffer(false);
		GlStateManager.disableAlpha();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		prevTexture0 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		this.bindFramebufferTexture();
		GL13.glActiveTexture(GL13.GL_TEXTURE6);
		prevTexture6 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, positionTexture);
		GL13.glActiveTexture(GL13.GL_TEXTURE7);
		prevTexture7 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, normalTexture);
		
		HbmShaderManager.useShader(HbmShaderManager.deferredFlashlight);
		Flashlight.setUniforms();
		GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.deferredFlashlight, "colorTexture"), 0);
		GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.deferredFlashlight, "positionTexture"), 6);
		GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.deferredFlashlight, "normalTexture"), 7);
		this.framebufferRender(this.framebufferWidth, this.framebufferHeight);
		HbmShaderManager.releaseShader();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE7);
		prevTexture7 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevTexture7);
		GL13.glActiveTexture(GL13.GL_TEXTURE6);
		prevTexture6 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevTexture6);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		prevTexture0 = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevTexture0);
	}
}
