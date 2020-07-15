package com.hbm.particle;

import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleLightning extends Particle {

	public int divisions = 7;
	public Vec3 direction = Vec3.createVectorHelper(0, -40, 0);
	private float[] positions;
	
	public ParticleLightning(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.canCollide = false;
		this.particleMaxAge = 60;
		this.particleScale = 20F;
		regenerateLightning();
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
	}
	
	public void regenerateLightning(){
		positions = new float[(divisions+2)*3];
		for(int i = 0; i < positions.length; i += 3){
			float magnitude = (i/3)/(divisions+1F);
			Vec3 pos = direction.mult(magnitude);
			positions[i] = (float) pos.xCoord;
			positions[i+1] = (float) pos.yCoord;
			positions[i+2] = (float) pos.zCoord;
		}
		
		for(int i = 3; i < positions.length-3; i += 3){
			Vec3 randPos = Vec3.createVectorHelper((world.rand.nextDouble()-0.5)*4, (rand.nextDouble()-0.5)*2, (rand.nextDouble()-0.5)*4);
			positions[i] += randPos.xCoord;
			positions[i+1] += randPos.yCoord;
			positions[i+2] += randPos.zCoord;
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		
		double d0 = this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks;
		double d1 = this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks;
		double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks;
		
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

		GL11.glTranslated(d0 - d3, d1 - d4, d2 - d5);
		
		float[] vertices = new float[positions.length*2];
		
		Vec3d look = entity.getPositionEyes(partialTicks).subtract(d0, d1, d2);
		for(int i = 0; i < positions.length-3; i += 3){
			//Vec3 toNextSegment = Vec3.createVectorHelper(positions[i+3], positions[i+4], positions[i+5]).subtract(Vec3.createVectorHelper(positions[i], positions[i+1], positions[i+2]));
			Vec3 point1 = Vec3.createVectorHelper(look.x, look.y, look.z).crossProduct(direction).normalize().mult((float) (0.2*particleScale));
		    Vec3 point2 = point1.mult(-1);
		    
		    vertices[i*2] = (float) point1.xCoord + positions[i];
		    vertices[i*2+1] = (float) point1.yCoord + positions[i+1];
		    vertices[i*2+2] = (float) point1.zCoord + positions[i+2];
		    vertices[i*2+3] = (float) point2.xCoord + positions[i];
		    vertices[i*2+4] = (float) point2.yCoord + positions[i+1];
		    vertices[i*2+5] = (float) point2.zCoord + positions[i+2];
		    
		    if(i == positions.length - 6){
		    	int i2 = i + 3;
		    	vertices[i2*2] = (float) point1.xCoord + positions[i2];
			    vertices[i2*2+1] = (float) point1.yCoord + positions[i2+1];
			    vertices[i2*2+2] = (float) point1.zCoord + positions[i2+2];
			    vertices[i2*2+3] = (float) point2.xCoord + positions[i2];
			    vertices[i2*2+4] = (float) point2.yCoord + positions[i2+1];
			    vertices[i2*2+5] = (float) point2.zCoord + positions[i2+2];
		    }
		}
		
		RenderHelper.bindTexture(ResourceManager.bfg_core_lightning);
		
		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		
		float[] prevPositions = ArrayUtils.subarray(vertices, 0, 6);
		
		float uStep = 1/(divisions + 1F);
		for(int i = 6; i < vertices.length; i += 6){
			float u = (i/6-1)*uStep;
			float u2 = u + uStep;
			buf.pos(prevPositions[0], prevPositions[1], prevPositions[2]).tex(u, 0).endVertex();
			buf.pos(prevPositions[3], prevPositions[4], prevPositions[5]).tex(u, 1).endVertex();
			buf.pos(vertices[i+3], vertices[i+4], vertices[i+5]).tex(u2, 1).endVertex();
			buf.pos(vertices[i], vertices[i+1], vertices[i+2]).tex(u2, 0).endVertex();

			prevPositions = ArrayUtils.subarray(vertices, i, i+6);
		}
		
		tes.draw();
		
		GlStateManager.enableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		
		GL11.glPopMatrix();
	}
	
	
}
