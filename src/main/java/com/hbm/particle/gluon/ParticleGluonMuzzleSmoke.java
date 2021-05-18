package com.hbm.particle.gluon;

import com.hbm.particle.ParticleFirstPerson;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleGluonMuzzleSmoke extends ParticleFirstPerson {

	public int type;
	public ResourceLocation tex;
	public float workingAlpha;
	public float rotationOverLife;
	public float scaleOverLife;
	
	public ParticleGluonMuzzleSmoke(World worldIn, double posXIn, double posYIn, double posZIn, int type, ResourceLocation tex, float scale, float rot, float scaleOverLife) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
		this.rotationOverLife = rot;
		this.particleAngle = rand.nextFloat()*360;
		this.scaleOverLife = scaleOverLife;
		this.tex = tex;
		this.type = type;
	}
	
	public ParticleGluonMuzzleSmoke color(float colR, float colG, float colB, float colA){
		this.particleRed = colR;
		this.particleGreen = colG;
		this.particleBlue = colB;
		this.particleAlpha = colA;
		workingAlpha = colA;
		return this;
	}
	
	public ParticleGluonMuzzleSmoke lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.type == 1){
			this.prevPosZ = this.posZ;
			this.posZ-=0.075F;
		}
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return true;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		//GL11.glPushMatrix();
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		this.workingAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 1), 0.6F, 1F, 0.6F, 1F), 0, 1)*particleAlpha;
		workingAlpha *= MathHelper.clamp(BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 0.2), 0F, 0.2F, 0F, 1F), 0, 1);
		
		float f4 = 0.1F * (this.particleScale+timeScale*5F*scaleOverLife);
		
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
        //GL11.glTranslated(f5, f6, f7);
        //GL11.glScalef(f4, f4, f4);
        Vec3[] vecs = new Vec3[]{new Vec3(0.5, 0.5, 0), new Vec3(-0.5, 0.5, 0), new Vec3(-0.5, -0.5, 0), new Vec3(0.5, -0.5, 0)};
        for(int i = 0; i < vecs.length; i ++){
        	vecs[i] = vecs[i].mult(f4);
        	vecs[i].rotateAroundZ((float) Math.toRadians(this.particleAngle+timeScale*rotationOverLife));
        	vecs[i].xCoord += f5;
        	vecs[i].yCoord += f6;
        	vecs[i].zCoord += f7;
        }
		//GL11.glRotated(this.particleAngle+timeScale*rotationOverLife, 0, 0, 1);
        
        float a = workingAlpha;
        for(int i = 0; i < workingAlpha; i ++){
        	 buffer.pos(vecs[0].xCoord, vecs[0].yCoord, vecs[0].zCoord).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
             buffer.pos(vecs[1].xCoord, vecs[1].yCoord, vecs[1].zCoord).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
             buffer.pos(vecs[2].xCoord, vecs[2].yCoord, vecs[2].zCoord).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
             buffer.pos(vecs[3].xCoord, vecs[3].yCoord, vecs[3].zCoord).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
             a-=1;
        }
	
      //  GL11.glPopMatrix();
	}
	
	@Override
	public ParticleType getType() {
		return ParticleType.GLUON;
	}

}
