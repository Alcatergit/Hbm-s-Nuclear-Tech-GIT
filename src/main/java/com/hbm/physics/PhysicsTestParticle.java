package com.hbm.physics;

import org.lwjgl.opengl.GL11;

import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class PhysicsTestParticle extends Particle {

	public RigidBody body;
	public AxisAlignedBB[] boxes;
	boolean collided = false;

	public PhysicsTestParticle(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		body = new RigidBody(worldIn, posXIn, posYIn, posZIn);
		boxes = new AxisAlignedBB[1];
		float size = 0.3F;
		//boxes[0] = new AxisAlignedBB(-size, -size*0.5, -size, size, size*0.5, size);
		//boxes[1] = new AxisAlignedBB(-size*0.5, -size*4-size, -size*0.5, size*0.5, size*0.5-size, size*0.5);
		boxes[0] = new AxisAlignedBB(0, 0, 0, 0.0625F*2, 0.0625F*16, 0.0625F*2);
		body.addColliders(new AABBCollider(boxes[0], 4));
		body.impulseVelocity(new Vec3(0, 0, 0.01), body.globalCentroid);
		body.friction = 0.3F;
		particleMaxAge = 200;
	}
	
	@Override
	public void onUpdate() {
		body.minecraftTimestep();
		this.particleAge ++;
		if(particleAge >= particleMaxAge){
			setExpired();
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX)*partialTicks;
        double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY)*partialTicks;
        double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ)*partialTicks;
        
        interpPosX = entPosX;
        interpPosY = entPosY;
        interpPosZ = entPosZ;
        GL11.glPushMatrix();
        if(collided)
        	partialTicks = 1;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        
        GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(4);
		RenderGlobal.drawSelectionBoundingBox(body.boundingBox.offset(-interpPosX, -interpPosY, -interpPosZ), 0, 1, 0, 1);
		GlStateManager.enableTexture2D();
        GL11.glPopMatrix();
        for(Contact c : body.contacts.contacts){
			if(c != null){
				Tessellator tes = Tessellator.getInstance();
				BufferBuilder buf = tes.getBuffer();
				buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
				Vec3 normal = c.normal.mult(0.5F);
				Vec3 globalA = c.globalA.subtract(interpPosX, interpPosY, interpPosZ);
				Vec3 globalB = c.globalB.subtract(interpPosX, interpPosY, interpPosZ);
				buf.pos(globalA.xCoord, globalA.yCoord, globalA.zCoord).color(0F, 0F, 1F, 1F).endVertex();
				buf.pos(globalA.xCoord-normal.xCoord, globalA.yCoord-normal.yCoord, globalA.zCoord-normal.xCoord).color(0F, 0F, 1F, 1F).endVertex();
				
				buf.pos(globalB.xCoord, globalB.yCoord, globalB.zCoord).color(0F, 0F, 1F, 1F).endVertex();
				buf.pos(globalB.xCoord+normal.xCoord, globalB.yCoord+normal.yCoord, globalB.zCoord+normal.xCoord).color(0F, 0F, 1F, 1F).endVertex();
				tes.draw();
				
				GL11.glPointSize(16);
				buf.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION_COLOR);
				buf.pos(globalA.xCoord, globalA.yCoord, globalA.zCoord).color(0F, 0F, 1F, 1F).endVertex();
				buf.pos(globalB.xCoord, globalB.yCoord, globalB.zCoord).color(0F, 0F, 1F, 1F).endVertex();
				tes.draw();
			}
		}
		body.doGlTransform(new Vec3(interpPosX, interpPosY, interpPosZ), partialTicks);
		
		GlStateManager.disableTexture2D();
		GlStateManager.glLineWidth(4);
		for(AxisAlignedBB box : boxes){
			RenderGlobal.drawSelectionBoundingBox(box, 1, collided ? 0 : 1, collided ? 0 : 1, 1);
		}
		GlStateManager.enableTexture2D();
		
		GL11.glPopMatrix();
	}
	
}
