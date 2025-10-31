package com.hbm.particle;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.HbmShaderManager2;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ParticleHeatDistortion extends Particle {

	public float heatAmount;
	public float timeOffset;
	public boolean local;
	
	public ParticleHeatDistortion(World worldIn, double posXIn, double posYIn, double posZIn, float scale, float heatAmount, int lifetime, float timeOffset) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleMaxAge = lifetime;
		this.particleScale = scale;
		this.heatAmount = heatAmount;
		this.timeOffset = timeOffset;
	}
	
	public ParticleHeatDistortion motion(float mX, float mY, float mZ){
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		return this;
	}
	
	public ParticleHeatDistortion enableLocalSpaceCorrection(){
		local = true;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.particleAge >= this.particleMaxAge){
			setExpired();
			return;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		
		float f4 = (float) (0.1F * this.particleScale);
		GlStateManager.depthMask(false);
        
		if(local){
			float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
	        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
	        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
	        GL11.glTranslated(f5, f6, f7);
	        if(BobMathUtil.r_viewMat == null){
				BobMathUtil.r_viewMat = ReflectionHelper.findField(ActiveRenderInfo.class, "MODELVIEW", "field_178812_b");
			}
			try {
				FloatBuffer view_mat = (FloatBuffer) BobMathUtil.r_viewMat.get(null);
				view_mat.rewind();
				GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
				for(int i = 0; i < 12; i ++){
					ClientProxy.AUX_GL_BUFFER.put(i, view_mat.get(i));
				}
				ClientProxy.AUX_GL_BUFFER.rewind();
				GL11.glLoadMatrix(ClientProxy.AUX_GL_BUFFER);
			} catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        } else {
        	double entPosX = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX)*partialTicks;
            double entPosY = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY)*partialTicks;
            double entPosZ = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ)*partialTicks;
            
            interpPosX = entPosX;
            interpPosY = entPosY;
            interpPosZ = entPosZ;
        	float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
            float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
            float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
            GL11.glTranslated(f5, f6, f7);
        }
        
        
        
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fresnel_ms);
        float heat_fade = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0.8, 1), 0.8F, 1F, 0F, 1.1F), 0, 1);
		heat_fade *= MathHelper.clamp(BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 0.2), 0F, 0.2F, 0F, 1.1F), 0, 1);
        ResourceManager.heat_distortion.use();
        ResourceManager.heat_distortion.uniform1f("amount", heatAmount*heat_fade*0.15F);
        float time = (System.currentTimeMillis()%10000000)/1000F;
        ResourceManager.heat_distortion.uniform1f("time", time+timeOffset);
        
		//TODO calling glFlush here is probably a *terrible* idea for performance. I should find a better solution.
		GL11.glFlush();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(avec3d[0].x, avec3d[0].y, avec3d[0].z).tex(1, 1).endVertex();
        buffer.pos(avec3d[1].x, avec3d[1].y, avec3d[1].z).tex(1, 0).endVertex();
        buffer.pos(avec3d[2].x, avec3d[2].y, avec3d[2].z).tex(0, 0).endVertex();
        buffer.pos(avec3d[3].x, avec3d[3].y, avec3d[3].z).tex(0, 1).endVertex();
        Tessellator.getInstance().draw();
        
        HbmShaderManager2.releaseShader();
        
        GlStateManager.depthMask(true);
        GL11.glPopMatrix();
	}

}
