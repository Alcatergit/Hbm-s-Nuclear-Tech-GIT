package com.hbm.particle_instanced;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import com.google.common.collect.Queues;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.HbmShaderManager2;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.Vbo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RefStrings.MODID)
public class InstancedParticleRenderer {

	//Position, scale, tex (texcoord offset and texcoord size), color (1 byte per channel), lightmap (two bytes).
	private static final int BYTES_PER_PARTICLE = 3*4 + 4 + 4*4 + 4 + 2;

	//Stupid hack
	public static float partialTicks;
	
	private static int faceCount = 0;
	private static int vao;
	private static int particleDataVbo;
	
	private static ByteBuffer particleBuffer = GLAllocation.createDirectByteBuffer(0);
	
	protected static ArrayDeque<ParticleInstanced> particles = Queues.newArrayDeque();
	private static final Queue<ParticleInstanced> queue = Queues.<ParticleInstanced> newArrayDeque();
	
	public static void addParticle(ParticleInstanced p) {
		if(p != null)
			queue.add(p);
	}
	
	public static void updateParticles() {
		Iterator<ParticleInstanced> itr = particles.iterator();
		while(itr.hasNext()) {
			ParticleInstanced p = itr.next();
			p.onUpdate();
			if(!p.isAlive()) {
				faceCount -= p.getFaceCount();
				itr.remove();
			}
		}
		if(!queue.isEmpty()) {
			for(ParticleInstanced particle = queue.poll(); particle != null; particle = queue.poll()) {
				if(particles.size() > 16384){
					ParticleInstanced p = particles.removeFirst();
					faceCount -= p.getFaceCount();
				}
				faceCount += particle.getFaceCount();
				particles.add(particle);
			}
			
		}
	}
	
	public static void renderParticles(Entity entityIn, float partialTicks) {
		Particle.interpPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
		Particle.interpPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
		Particle.interpPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
		Particle.cameraViewDir = entityIn.getLook(partialTicks);
		
		RenderHelper.bindBlockTexture();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
		GlStateManager.depthMask(false);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		int bufferSize = faceCount*BYTES_PER_PARTICLE;
		if(particleBuffer.capacity() < bufferSize)
			particleBuffer = GLAllocation.createDirectByteBuffer(bufferSize);
		particleBuffer.limit(bufferSize);
		
		for(ParticleInstanced p : particles){
			p.addDataToBuffer(particleBuffer, partialTicks);
		}
		particleBuffer.rewind();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, particleDataVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, particleBuffer, GL15.GL_DYNAMIC_DRAW);
		
		GL30.glBindVertexArray(vao);
		ResourceManager.lit_particles.use();
		GL31.glDrawArraysInstanced(GL11.GL_QUADS, 0, 4, faceCount);
		HbmShaderManager2.releaseShader();
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GlStateManager.depthMask(true);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	}
	
	public static void setup(){
		int particleQuadVbo = GL15.glGenBuffers();
		int particleDataVbo = GL15.glGenBuffers();
		float[] vertexData = {
				-0.5F, -0.5F, 0,
				0.5F, -0.5F, 0,
				0.5F, 0.5F, 0,
				-0.5F, 0.5F, 0};
		ByteBuffer data = GLAllocation.createDirectByteBuffer(4*vertexData.length);
		for(float f : vertexData)
			data.putFloat(f);
		data.rewind();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, particleQuadVbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 12, 0);
		GL20.glEnableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, particleDataVbo);
		//Position, scale, tex (texcoord offset and a texcoord size), color (1 byte per channel), lightmap (two shorts).
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, BYTES_PER_PARTICLE, 0);
		GL20.glEnableVertexAttribArray(1);
		//Scale
		GL20.glVertexAttribPointer(2, 1, GL11.GL_FLOAT, false, BYTES_PER_PARTICLE, 12);
		GL20.glEnableVertexAttribArray(2);
		//tex
		GL20.glVertexAttribPointer(3, 4, GL11.GL_FLOAT, false, BYTES_PER_PARTICLE, 16);
		GL20.glEnableVertexAttribArray(3);
		//color
		GL20.glVertexAttribPointer(4, 4, GL11.GL_UNSIGNED_BYTE, true, BYTES_PER_PARTICLE, 32);
		GL20.glEnableVertexAttribArray(4);
		//lightmap
		GL20.glVertexAttribPointer(5, 2, GL11.GL_UNSIGNED_BYTE, true, BYTES_PER_PARTICLE, 36);
		GL20.glEnableVertexAttribArray(5);
		
		GL33.glVertexAttribDivisor(1, 1);
		GL33.glVertexAttribDivisor(2, 1);
		GL33.glVertexAttribDivisor(3, 1);
		GL33.glVertexAttribDivisor(4, 1);
		GL33.glVertexAttribDivisor(5, 1);
		
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		InstancedParticleRenderer.vao = vao;
		InstancedParticleRenderer.particleDataVbo = particleDataVbo;
	}
	
	@SubscribeEvent
	public static void renderLast(RenderWorldLastEvent event) {
		if(GeneralConfig.instancedParticles){
			partialTicks = event.getPartialTicks();
			renderParticles(Minecraft.getMinecraft().getRenderViewEntity(), event.getPartialTicks());
		}
	}

	private static boolean init = true;
	
	@SubscribeEvent
	public static void clientTick(ClientTickEvent event) {
		if(GeneralConfig.instancedParticles && event.phase == Phase.START) {
			if(init){
				setup();
				init = false;
			}
			if(!Minecraft.getMinecraft().isGamePaused())
				updateParticles();
		}
	}
}
