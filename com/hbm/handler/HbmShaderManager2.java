package com.hbm.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.hbm.handler.HbmShaderManager2.Shader.Uniform;
import com.hbm.main.MainRegistry;
import com.hbm.particle_instanced.InstancedParticleRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

//Same as the other class except with less junk (hopefully)
public class HbmShaderManager2 {

public static final FloatBuffer AUX_GL_BUFFER = GLAllocation.createDirectFloatBuffer(16);
	
	public static final Uniform MODELVIEW_PROJECTION_MATRIX = shader -> {
		//No idea if all these rewind calls are necessary. I'll have to check that later.
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		Matrix4f mvMatrix = new Matrix4f();
		mvMatrix.load(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		Matrix4f pMatrix = new Matrix4f();
		pMatrix.load(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		
		Matrix4f.mul(pMatrix, mvMatrix, mvMatrix).store(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		
		GL20.glUniformMatrix4(GL20.glGetUniformLocation(shader, "modelViewProjectionMatrix"), false, AUX_GL_BUFFER);
	};
	
	public static final Uniform MODELVIEW_MATRIX = shader -> {
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		GL20.glUniformMatrix4(GL20.glGetUniformLocation(shader, "modelview"), false, AUX_GL_BUFFER);
	};
	
	public static final Uniform PROJECTION_MATRIX = shader -> {
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		GL20.glUniformMatrix4(GL20.glGetUniformLocation(shader, "projection"), false, AUX_GL_BUFFER);
	};
	
	public static final Uniform INV_PLAYER_ROT_MATRIX = shader -> {
		Entity entityIn = Minecraft.getMinecraft().getRenderViewEntity();
		//Stupid hack to get partial ticks.
		float partialTicks = InstancedParticleRenderer.partialTicks;
		float yaw = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw)*partialTicks;
		float pitch = entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch)*partialTicks;
		Matrix4f mat = new Matrix4f();
		mat.rotate((float) Math.toRadians(-yaw+180), new Vector3f(0, 1, 0));
		mat.rotate((float) Math.toRadians(-pitch), new Vector3f(1, 0, 0));
		mat.store(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		GL20.glUniformMatrix4(GL20.glGetUniformLocation(shader, "invPlayerRot"), false, AUX_GL_BUFFER);
	};
	
	public static final Uniform LIGHTMAP = shader -> {
		GL20.glUniform1i(GL20.glGetUniformLocation(shader, "lightmap"), 1);
	};
	
	public static Shader loadShader(ResourceLocation file) {
		int vertexShader = 0;
		int fragmentShader = 0;
		try {
			int program = GL20.glCreateProgram();
			
			vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			GL20.glShaderSource(vertexShader, readFileToBuf(new ResourceLocation(file.getResourceDomain(), file.getResourcePath() + ".vert")));
			GL20.glCompileShader(vertexShader);
			if(GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetShaderInfoLog(vertexShader, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating vertex shader: " + file);
			}
			
			fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			GL20.glShaderSource(fragmentShader, readFileToBuf(new ResourceLocation(file.getResourceDomain(), file.getResourcePath() + ".frag")));
			GL20.glCompileShader(fragmentShader);
			if(GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetShaderInfoLog(fragmentShader, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating fragment shader: " + file);
			}
			
			GL20.glAttachShader(program, vertexShader);
			GL20.glAttachShader(program, fragmentShader);
			GL20.glLinkProgram(program);
			if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
				MainRegistry.logger.error(GL20.glGetProgramInfoLog(program, GL20.GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Error creating fragment shader: " + file);
			}
			
			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);
			
			return new Shader(program);
		} catch(Exception x) {
			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);
			x.printStackTrace();
		}
		return new Shader(0);
	}

	private static ByteBuffer readFileToBuf(ResourceLocation file) throws IOException {
		InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(file).getInputStream();
		byte[] bytes = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		ByteBuffer buf = BufferUtils.createByteBuffer(bytes.length);
		buf.put(bytes);
		buf.rewind();
		return buf;
	}
	
	public static void releaseShader(){
		GL20.glUseProgram(0);
	}
	
	public static class Shader {

		private int shader;
		private List<Uniform> uniforms = new ArrayList<>(2);
		
		public Shader(int shader) {
			this.shader = shader;
		}
		
		public Shader withUniforms(Uniform... uniforms){
			for(Uniform u : uniforms){
				this.uniforms.add(u);
			}
			return this;
		}
		
		public void use(){
			GL20.glUseProgram(shader);
			for(Uniform u : uniforms){
				u.apply(shader);
			}
		}
		
		public int getShaderId(){
			return shader;
		}
		
		public static interface Uniform {
			public void apply(int shader);
		}
	}
}
