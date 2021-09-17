package com.hbm.flashlight;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.hbm.handler.HbmShaderManager;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.portals.DummyRenderEntity;

import glmath.glm.mat._3.Mat3;
import glmath.glm.mat._4.Mat4;
import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Deprecated
public class Flashlight {

	public static final int SHADOW_WIDTH = 1024, SHADOW_HEIGHT = 1024;
	
	public static List<Flashlight> ALL_RENDER_FLASHLIGHTS = new ArrayList<Flashlight>();
	public DummyRenderEntity dummy = new DummyRenderEntity(Minecraft.getMinecraft().world);
	
	public float angle;
	public Vec3d position;
	public Vec3d direction;
	
	public static int frameBuffer;
	public static int depthTexture;
	static boolean frameBufferCreated = false;
	
	
	public Flashlight(Vec3d position, Vec3d direction, float angle) {
		this.position = position;
		this.direction = direction;
		angle = MathHelper.clamp(angle, 0F, 90F);
		this.angle = (float) (1F-(Math.cos(Math.toRadians(angle))));
	}
	
	public void renderBeam(){
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_CURRENT_BIT | GL11.GL_COLOR_BUFFER_BIT);
		HbmShaderManager.useShader(HbmShaderManager.flashlightBeam);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		GlStateManager.disableLighting();
		//Ensure it actually is disabled
		GlStateManager.disableLighting();
		
		
		
		HbmShaderManager.releaseShader();
		GlStateManager.enableLighting();
		GL11.glPopMatrix();
		GL11.glPopAttrib();
	}
	
	public void generateShadowMap(){
		if(!frameBufferCreated){
			frameBuffer = OpenGlHelper.glGenFramebuffers();
			depthTexture = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, depthTexture);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (IntBuffer) null);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT); 
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			
			OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, frameBuffer);
			OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, depthTexture, 0);
			GL11.glDrawBuffer(GL11.GL_NONE);
			GL11.glReadBuffer(GL11.GL_NONE);
			OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
			
			frameBufferCreated = true;
		}
		Entity ent = Minecraft.getMinecraft().getRenderViewEntity();
		Minecraft.getMinecraft().setRenderViewEntity(dummy);
		double yaw = (float) MathHelper.atan2(direction.x, direction.z);
		float pitch = (float) Math.asin(direction.y);
		
		dummy.setPositionAndRotationDirect(position.x, position.y, position.z, (float) -Math.toDegrees(yaw), (float) Math.toDegrees(pitch), 0, false);
		dummy.lastTickPosX = position.x;
		dummy.lastTickPosY = position.y;
		dummy.lastTickPosZ = position.z;
		dummy.prevPosX = position.x;
		dummy.prevPosY = position.y;
		dummy.prevPosZ = position.z;
		dummy.prevRotationYaw = dummy.rotationYaw;
		dummy.prevRotationPitch = dummy.rotationPitch;
		
		
		int i2 = Minecraft.getMinecraft().gameSettings.limitFramerate;
		int j = Math.min(Minecraft.getDebugFPS(), i2);
        j = Math.max(j, 60);
        long k = System.nanoTime() - System.nanoTime();
        long l = Math.max((long)(1000000000 / j / 4) - k, 0L);
		
        ModEventHandlerClient.renderingDepthOnly = true;
        
        bindBuffer();
        GL11.glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        
       // FakeWorldRenderer.INSTANCE.renderWorld(Minecraft.getMinecraft().getRenderPartialTicks(), l);
        //Minecraft.getMinecraft().entityRenderer.renderWorld(Minecraft.getMinecraft().getRenderPartialTicks(), System.nanoTime() + l);
        
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
        
        ModEventHandlerClient.renderingDepthOnly = false;
		Minecraft.getMinecraft().setRenderViewEntity(ent);
		
	}
	
	public static void bindBuffer(){
		OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, frameBuffer);
	}
	
	public static void unbindBuffer(){
		OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
	}
	
	public static void setUniforms(){
		//Mat4 is from the glm library, found at https://github.com/java-graphics/glm
		Mat4 modelMatrix = new Mat4(1.0F);
		Mat3 rotMatrix = new Mat3(1.0F);
		GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightCount"), Math.min(100, Math.min(ALL_RENDER_FLASHLIGHTS.size(), 20)));
		for(int i = 0; i < ALL_RENDER_FLASHLIGHTS.size(); i++){
			if(i >= 20)
				break;
			modelMatrix.identity();
			rotMatrix.identity();
			Flashlight f = ALL_RENDER_FLASHLIGHTS.get(i);
			//Transform the flashlight position to view space
			
			Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
			float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
			if(Minecraft.getMinecraft().isGamePaused()){
			}
			if (Minecraft.getMinecraft().gameSettings.viewBobbing)
	        {
	            applyBobbing(partialTicks, modelMatrix, rotMatrix);
	        }
			modelMatrix.rotate((float) Math.toRadians(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks), 1.0F, 0.0F, 0.0F);
			modelMatrix.rotate((float) Math.toRadians(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F), 0.0F, 1.0F, 0.0F);
			
            
            
            Entity e = f.dummy;
			double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
			double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
			double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
			double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
			double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
			double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
			
            modelMatrix.translate((float)(d0 - d3), (float)(d1 - d4 - entity.getEyeHeight()), (float)(d2 - d5));
            
			rotMatrix.rotate((float) Math.toRadians(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks), 1.0F, 0.0F, 0.0F);
			rotMatrix.rotate((float) Math.toRadians(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F), 0.0F, 1.0F, 0.0F);
			
            Vec4 newPos = modelMatrix.mul(new Vec4(0.0F, 0.0F, 0.0F, 1.0F));
            
            Vec3d[] save = new Vec3d[]{f.position, f.direction};
            
            
            
            Vec3 newPos2 = rotMatrix.mul(new Vec3((float)f.direction.x, (float)f.direction.y, (float)f.direction.z));
            
            f.position = new Vec3d(newPos.x, newPos.y, newPos.z);
            
            f.direction = new Vec3d(newPos2.x, newPos2.y, newPos2.z);
            
            //System.out.println(f.position);
            
			GL20.glUniform3f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lights[" + i + "].pos"), (float)f.position.x, (float)f.position.y, (float)f.position.z);
			GL20.glUniform3f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lights[" + i + "].dir"), (float)f.direction.x, (float)f.direction.y, (float)f.direction.z);
			GL20.glUniform1f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lights[" + i + "].angle"), f.angle);
			
			f.position = save[0];
			f.direction = save[1];
			
		}
	}
	
	private static void applyBobbing(float partialTicks, Mat4 matrix, Mat3 rotMatrix)
    {
		if (Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)Minecraft.getMinecraft().getRenderViewEntity();
            float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
            float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
            float f2 = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
            float f3 = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
            matrix.translate(MathHelper.sin(f1 * (float)Math.PI) * f2 * 0.5F, -Math.abs(MathHelper.cos(f1 * (float)Math.PI) * f2), 0.0F);
            matrix.rotate((float) Math.toRadians(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F), 0.0F, 0.0F, 1.0F);
            matrix.rotate((float) Math.toRadians(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F), 1.0F, 0.0F, 0.0F);
            matrix.rotate((float) Math.toRadians(f3), 1.0F, 0.0F, 0.0F);
            
            rotMatrix.rotate((float) Math.toRadians(MathHelper.sin(f1 * (float)Math.PI) * f2 * 3.0F), 0.0F, 0.0F, 1.0F);
            rotMatrix.rotate((float) Math.toRadians(Math.abs(MathHelper.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F), 1.0F, 0.0F, 0.0F);
            rotMatrix.rotate((float) Math.toRadians(f3), 1.0F, 0.0F, 0.0F);
        }
    }
}
