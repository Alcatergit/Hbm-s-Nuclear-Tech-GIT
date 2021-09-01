package com.hbm.particle.gluon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleGluonDisintegration extends Particle {

	ModelBox box;
	int dl = -1;
	float[] matrix;
	//float[] invRot = new float[16];
	List<ParticleGluonParticle> subParticles = new ArrayList<>();
	Vec3d rotation;
	float rotX, rotY, rotZ, prevRotX, prevRotY, prevRotZ  = 0;
	ResourceLocation tex;
	float cubeMidX, cubeMidY, cubeMidZ;
	int numParticles;
	float boxScale;
	
	public ParticleGluonDisintegration(World worldIn, double posXIn, double posYIn, double posZIn, ModelBox box, float[] matrix, ResourceLocation tex, float cubeMidX, float cubeMidY, float cubeMidZ, float scale) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.box = box;
		this.matrix = matrix;
		/*Matrix4f mat = new Matrix4f();
		AUX_GL_BUFFER.put(matrix);
		AUX_GL_BUFFER.rewind();
		mat.load(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		mat.invert();
		mat.store(AUX_GL_BUFFER);
		AUX_GL_BUFFER.rewind();
		AUX_GL_BUFFER.get(invRot);
		AUX_GL_BUFFER.rewind();
		invRot[12] = 0;
		invRot[13] = 0;
		invRot[14] = 0;
		invRot[15] = 1;*/
		rotation = new Vec3d((worldIn.rand.nextFloat()-0.5)*50, (worldIn.rand.nextFloat()-0.5)*50, (worldIn.rand.nextFloat()-0.5)*50);
		this.tex = tex;
		this.particleMaxAge = 90;
		this.cubeMidX = cubeMidX;
		this.cubeMidY = cubeMidY;
		this.cubeMidZ = cubeMidZ;
		boxScale = scale;
		numParticles = (int) Math.cbrt((Math.abs(box.posX1-box.posX2)*Math.abs(box.posY1-box.posY2)*Math.abs(box.posZ1-box.posZ2)*0.6))+1;
		if(numParticles > 15)
			numParticles = 15;
	}
	
	public void motion(Vec3d motion){
		this.motionX = motion.x;
		this.motionY = motion.y;
		this.motionZ = motion.z;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge++;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		prevRotX = rotX;
		prevRotY = rotY;
		prevRotZ = rotZ;
		move(motionX, motionY, motionZ);
		this.motionX*=0.9;
		this.motionY*=0.9;
		this.motionZ*=0.9;
		if(particleAge >= particleMaxAge){
			this.setExpired();
			GL11.glDeleteLists(dl, 1);
			return;
		}
		Iterator<ParticleGluonParticle> itr = subParticles.iterator();
		while(itr.hasNext()){
			Particle p = itr.next();
			p.onUpdate();
			if(!p.isAlive())
				itr.remove();
		}
		if(particleAge < 65){
			for(int i = 0; i < numParticles; i ++){
				subParticles.add(new ParticleGluonParticle(world, (box.posX1 + (box.posX2-box.posX1)*rand.nextFloat())*0.0625F-cubeMidX, 
					(box.posY1 + (box.posY2-box.posY1)*rand.nextFloat())*0.0625F-cubeMidY, 
					(box.posZ1 + (box.posZ2-box.posZ1)*rand.nextFloat())*0.0625F-cubeMidZ, 0.9F, rand.nextInt(4) + 2));
			}
			for(int i = 0; i < 2; i ++)
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleGluonParticleSmol(world, posX, posY, posZ, rand.nextFloat()*0.25F-0.125F, rand.nextFloat()*0.25F-0.125F, rand.nextFloat()*0.25F-0.125F));
		} else if(particleAge == 65){
			for(int i = 0; i < numParticles*3; i ++){
				subParticles.add(new ParticleGluonParticle(world, (box.posX1 + (box.posX2-box.posX1)*rand.nextFloat()*1.3)*0.0625F-cubeMidX, 
					(box.posY1 + (box.posY2-box.posY1)*rand.nextFloat()*1.3)*0.0625F-cubeMidY, 
					(box.posZ1 + (box.posZ2-box.posZ1)*rand.nextFloat()*1.3)*0.0625F-cubeMidZ, 1.2F, rand.nextInt(12) + 8));
			}
		}
		rotX += rotation.x;
		rotY += rotation.y;
		rotZ += rotation.z;
		rotation = rotation.scale(0.95);
	}
	
	@Override
	public void move(double x, double y, double z) {
		double d0 = y;
        double origX = x;
        double origZ = z;

        if (this.canCollide)
        {
            List<AxisAlignedBB> list = this.world.getCollisionBoxes((Entity)null, this.getBoundingBox().expand(x, y, z));

            for (AxisAlignedBB axisalignedbb : list)
            {
                y = axisalignedbb.calculateYOffset(this.getBoundingBox(), y);
            }

            this.setBoundingBox(this.getBoundingBox().offset(0.0D, y, 0.0D));

            for (AxisAlignedBB axisalignedbb1 : list)
            {
                x = axisalignedbb1.calculateXOffset(this.getBoundingBox(), x);
            }

            this.setBoundingBox(this.getBoundingBox().offset(x, 0.0D, 0.0D));

            for (AxisAlignedBB axisalignedbb2 : list)
            {
                z = axisalignedbb2.calculateZOffset(this.getBoundingBox(), z);
            }

            this.setBoundingBox(this.getBoundingBox().offset(0.0D, 0.0D, z));
        }
        else
        {
            this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        }

        this.resetPositionToBB();
        this.onGround = d0 != y && d0 < 0.0D;
        
        if(d0 != y){
        	this.motionY = -motionY*0.75*(rand.nextFloat()*0.8+0.25);
        }

        if (origX != x)
        {
            this.motionX = -motionX*0.75*(rand.nextFloat()*1.12+0.25);
        }

        if (origZ != z)
        {
            this.motionZ = -motionY*0.75*(rand.nextFloat()*1.12+0.25);
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
		GL11.glPushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableRescaleNormal();
		float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        float rotateX = (float)(this.prevRotX + (this.rotX - this.prevRotX) * (double)partialTicks);
        float rotateY = (float)(this.prevRotY + (this.rotY - this.prevRotY) * (double)partialTicks);
        float rotateZ = (float)(this.prevRotZ + (this.rotZ - this.prevRotZ) * (double)partialTicks);
        
        GL11.glTranslated(f5, f6, f7);
        Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		if(dl == -1){
			dl = GL11.glGenLists(1);
			GL11.glNewList(dl, GL11.GL_COMPILE);
			//Moves it so the origin is in the middle. I hope this makes for slightly better rotations.
			buffer.setTranslation(-cubeMidX, -cubeMidY, -cubeMidZ);
			box.render(buffer, 0.0625F);
			buffer.setTranslation(0, 0, 0);
			GL11.glEndList();
		}
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
		//Ah yes, spaghetti code.
		ClientProxy.AUX_GL_BUFFER2.put(matrix);
		ClientProxy.AUX_GL_BUFFER2.rewind();
		GL11.glMultMatrix(ClientProxy.AUX_GL_BUFFER2);
		GL11.glRotated(rotateX, 1, 0, 0);
		GL11.glRotated(rotateY, 0, 1, 0);
		GL11.glRotated(rotateZ, 0, 0, 1);
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.001F);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		float a = 1-BobMathUtil.remap01_clamp(particleAge+partialTicks, 65, 67);
		GlStateManager.color(0.1F, 0.1F, 0.1F, a);
		
		GL11.glCallList(dl);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();
		//AUX_GL_BUFFER.put(invRot);
		//AUX_GL_BUFFER.rewind();
		//GL11.glMultMatrix(AUX_GL_BUFFER);
		//GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, AUX_GL_BUFFER);
		//AUX_GL_BUFFER.rewind();
		//AUX_GL_BUFFER.put(matrix, 0, 12);
		//AUX_GL_BUFFER.rewind();
		//GL11.glLoadMatrix(AUX_GL_BUFFER);
		
		
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fresnel_ms);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		for(ParticleGluonParticle p : subParticles){
			p.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ, ClientProxy.AUX_GL_BUFFER);
		}
		GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
		
		GlStateManager.disableRescaleNormal();
		GL11.glPopMatrix();
	}
	
}
