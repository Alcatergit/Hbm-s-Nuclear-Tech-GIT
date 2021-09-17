package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.render.item.weapon.ItemRenderCrucible;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleCrucibleSpark extends ParticleFirstPerson {

	public float stretch;
	public int timeUntilChange = 0;
	//Actual motion without the randomness
	public float amx, amy, amz;
	
	public ParticleCrucibleSpark(World worldIn, float s, float scale, double posXIn, double posYIn, double posZIn, float mx, float my, float mz) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.stretch = s;
		this.particleScale = scale;
		Vec3d am = new Vec3d(amx, amy, amz);
		if(am.lengthSquared() != 0){
			Vec3d rand = BobMathUtil.randVecInCone(am.normalize(), 30).scale(am.lengthVector());
			motionX = rand.x;
			motionY = rand.y;
			motionZ = rand.z;
		} else {
			this.motionX = (rand.nextFloat()-0.5)*0.015;
			this.motionY = (rand.nextFloat()-0.5)*0.015;
			this.motionZ = (rand.nextFloat()-0.5)*0.015;
		}
		timeUntilChange = rand.nextInt(6)+1;
		this.amx = mx;
		this.amy = my;
		this.amz = mz;
	}
	
	public ParticleCrucibleSpark color(float r, float g, float b, float a){
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.particleAlpha = a;
		return this;
	}
	
	public ParticleCrucibleSpark lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		timeUntilChange --;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		this.posX += this.motionX + amx;
		this.posY += this.motionY + amy;
		this.posZ += this.motionZ + amz;
		if(timeUntilChange == 0){
			timeUntilChange = rand.nextInt(6)+1;
			Vec3d am = new Vec3d(motionX, motionY, motionZ);
			if(am.lengthSquared() != 0){
				Vec3d rand = BobMathUtil.randVecInCone(am.normalize(), 30).scale(am.lengthVector());
				motionX = rand.x;
				motionY = rand.y;
				motionZ = rand.z;
			} else {
				this.motionX = (rand.nextFloat()-0.5)*0.015;
				this.motionY = (rand.nextFloat()-0.5)*0.015;
				this.motionZ = (rand.nextFloat()-0.5)*0.015;
			}
		}
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return true;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		
		
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
        float mX = (float)(this.posX + (this.posX+this.motionX - this.posX) * (double)partialTicks);
        float mY = (float)(this.posY + (this.posY+this.motionY - this.posY) * (double)partialTicks);
        float mZ = (float)(this.posZ + (this.posZ+this.motionZ - this.posZ) * (double)partialTicks);
        
        Vec3d particleAxis = new Vec3d(mX, mY, mZ).subtract(f5, f6, f7);
        Vec3d toPlayer = new Vec3d(mX, mY, mZ).subtract(ItemRenderCrucible.playerPos);
        Vec3d point1 = particleAxis.crossProduct(toPlayer).normalize().scale(0.5*particleScale);
        Vec3d point2 = point1.scale(-1);
        point1 = point1.addVector(f5, f6, f7);
        point2 = point2.addVector(f5, f6, f7);
        particleAxis = particleAxis.scale(stretch);
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        float alpha = this.particleAlpha;
        while(alpha > 0){
        	buffer.pos(point2.x, point2.y, point2.z).tex(1, 0).endVertex();
        	buffer.pos(point1.x, point1.y, point1.z).tex(1, 1).endVertex();
        	
        	buffer.pos(point1.x+particleAxis.x, point1.y+particleAxis.y, point1.z+particleAxis.z).tex(0, 1).endVertex();
        	buffer.pos(point2.x+particleAxis.x, point2.y+particleAxis.y, point2.z+particleAxis.z).tex(0, 0).endVertex();
        	alpha -= 1;
        }
        Tessellator.getInstance().draw();
       
	}
	
	@Override
	public ParticleType getType() {
		return ParticleType.CRUCIBLE;
	}

	
}
