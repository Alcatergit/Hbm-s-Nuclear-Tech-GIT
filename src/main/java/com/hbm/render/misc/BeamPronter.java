package com.hbm.render.misc;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.config.GeneralConfig;
import com.hbm.handler.HbmShaderManager2;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Tessellator;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BeamPronter {
	
	public static enum EnumWaveType {
		RANDOM,
		SPIRAL,
		STRAIGHT
	}
	
	public static enum EnumBeamType {
		SOLID,
		LINE
	}
	
	public static void prontBeam(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, int outerColor, int innerColor, int start, int segments, float spinRadius, int layers, float thickness) {

		GL11.glPushMatrix();
		
		float sYaw = (float)(Math.atan2(skeleton.xCoord, skeleton.zCoord) * 180F / Math.PI);
        float sqrt = MathHelper.sqrt(skeleton.xCoord * skeleton.xCoord + skeleton.zCoord * skeleton.zCoord);
		float sPitch = (float)(Math.atan2(skeleton.yCoord, (double)sqrt) * 180F / Math.PI);

		GL11.glRotatef(180, 0, 1F, 0);
		GL11.glRotatef(sYaw, 0, 1F, 0);
		GL11.glRotatef(sPitch - 90, 1F, 0, 0);
		
		GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(true);

        
		if(beam == EnumBeamType.SOLID) {
			GlStateManager.depthMask(false);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
			GlStateManager.disableCull();
		}
        
		Tessellator tessellator = Tessellator.instance;
		
		if(beam == EnumBeamType.LINE) {
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
		} else if (beam == EnumBeamType.SOLID){
			net.minecraft.client.renderer.Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		}
		
		Vec3 unit = Vec3.createVectorHelper(0, 1, 0);
		Random rand = new Random(start);
		double length = skeleton.lengthVector();
		double segLength = length / segments;
		double lastX = 0;
		double lastY = 0;
		double lastZ = 0;
		
		for(int i = 0; i <= segments; i++) {

			double pX = unit.xCoord * segLength * i;
			double pY = unit.yCoord * segLength * i;
			double pZ = unit.zCoord * segLength * i;
			
			if(wave != EnumWaveType.STRAIGHT) {
				Vec3 spinner = Vec3.createVectorHelper(spinRadius, 0, 0);
				if(wave == EnumWaveType.SPIRAL) {
					spinner.rotateAroundY((float)Math.PI * (float)start / 180F);
					spinner.rotateAroundY((float)Math.PI * 45F / 180F * i);
				} else if(wave == EnumWaveType.RANDOM) {
					spinner.rotateAroundY((float)Math.PI * 2 * rand.nextFloat());
				}
				pX += spinner.xCoord;
				pY += spinner.yCoord;
				pZ += spinner.zCoord;
			}
			
			if(beam == EnumBeamType.LINE && i > 0) {
	            tessellator.setColorOpaque_I(outerColor);
	            tessellator.addVertex(pX, pY, pZ);
	            tessellator.addVertex(lastX, lastY, lastZ);
			}
			
			if(beam == EnumBeamType.SOLID && i > 0) {
				
				float radius = thickness / (float)layers;

				for(int j = 1; j <= layers; j++) {
					int color = 0;
					if(layers == 1) {
						color = outerColor;
					} else {
						float inter = (float)(j - 1) / (float)(layers - 1);
						color = BobMathUtil.interpolateColor(innerColor, outerColor, inter);
					}
					tessellator.setColorOpaque_I(color);
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ + (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ + (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ + (radius * j));
					
					tessellator.addVertex(lastX + (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(lastX - (radius * j), lastY, lastZ - (radius * j));
					tessellator.addVertex(pX - (radius * j), pY, pZ - (radius * j));
					tessellator.addVertex(pX + (radius * j), pY, pZ - (radius * j));
				}
			}
			
			lastX = pX;
			lastY = pY;
			lastZ = pZ;
		}
		
		if(beam == EnumBeamType.LINE) {
			tessellator.setColorOpaque_I(innerColor);
            tessellator.addVertex(0, 0, 0);
            tessellator.addVertex(0, skeleton.lengthVector(), 0);
		}

		
		
		tessellator.draw();
		
		if(beam == EnumBeamType.SOLID) {
			GlStateManager.disableBlend();
			GlStateManager.enableCull();
			GlStateManager.depthMask(true);
		}
		
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
		GL11.glPopMatrix();

		GL11.glPopMatrix();
	}
	
	//Drillgon200: Yeah, I don't know what to do about fluid colors so I'm just going butcher it and try my best to use the middle pixel of the icon
	//Alcater: I figured out a way to extract the fluid colors from the texture and save them in a HashMap at loadup. This function wont be needed anymore.
	//public static void prontBeamWithIcon(Vec3 skeleton, EnumWaveType wave, EnumBeamType beam, TextureAtlasSprite icon, int innerColor, int start, int segments, float spinRadius, int layers, float thickness) {

	
	public static void gluonBeam(Vec3 pos1, Vec3 pos2, float size){
		//long l = System.nanoTime();
		GL11.glPushMatrix();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		GlStateManager.disableCull();
		if(!GeneralConfig.useShaders2){
			GlStateManager.color(0.4F, 0.7F, 1, 1);
		}
		
		Vec3 diff = pos1.subtract(pos2);
		float len = (float) diff.lengthVector();
		Vec3 angles = BobMathUtil.getEulerAngles(diff);
		GL11.glTranslated(pos1.xCoord, pos1.yCoord, pos1.zCoord);
		
		GL11.glRotated(angles.xCoord+90, 0, 1, 0);
		GL11.glRotated(-angles.yCoord, 0, 0, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.noise_1);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.noise_2);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.bfg_core_lightning);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		net.minecraft.client.renderer.Tessellator tes = net.minecraft.client.renderer.Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		Matrix4f mvMatrix = new Matrix4f();
		mvMatrix.load(HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		Matrix4f.invert(mvMatrix, mvMatrix);
		Vector4f billboardPos = Matrix4f.transform(mvMatrix, new Vector4f(0, 0, 0, 1), null);
		//System.out.println(billboardPos);
		//GL20.glUniform3f(GL20.glGetUniformLocation(ResourceManager.gluon_beam.getShaderId(), "playerPos"), billboardPos.x, billboardPos.y, billboardPos.z);
		
		//GL20.glUniform3f(GL20.glGetUniformLocation(ResourceManager.gluon_beam.getShaderId(), "playerPos"), 0.0F, 0.1F, 0F);
		int SUBDIVISIONS_PER_BLOCK = 16;
		int subdivisions = (int)Math.ceil(len*SUBDIVISIONS_PER_BLOCK);
		
		//System.out.println(billboardPos);
		ResourceManager.gluon_spiral.use();
		ResourceManager.gluon_spiral.uniform3f("playerPos", billboardPos.x, billboardPos.y, billboardPos.z);
		ResourceManager.gluon_spiral.uniform1f("subdivXAmount", 1/(float)SUBDIVISIONS_PER_BLOCK);
		ResourceManager.gluon_spiral.uniform1f("subdivUAmount", 1/(float)(subdivisions+1));
		ResourceManager.gluon_spiral.uniform1f("len", len);
		
		buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);
		for(int i = 0; i <= subdivisions; i ++){
			float iN = ((float)i/(float)subdivisions);
			float pos = iN*len;
			buf.pos(pos, 0, -size*0.025).tex(iN, 0.45).endVertex();
			buf.pos(pos, 0, size*0.025).tex(iN, 0.55).endVertex();
		}
		tes.draw();
		
		SUBDIVISIONS_PER_BLOCK *= 0.5;
		subdivisions = (int)Math.ceil(len*SUBDIVISIONS_PER_BLOCK);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.gluon_beam_tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		//GlStateManager.depthMask(true);
		ResourceManager.gluon_beam.use();
		ResourceManager.gluon_beam.uniform1f("beam_length", len);
		
		buf.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_TEX);
		//GL20.glUniform1f(GL20.glGetUniformLocation(ResourceManager.gluon_beam.getShaderId(), "subdivXAmount"), 1/(float)SUBDIVISIONS_PER_BLOCK);
		//GL20.glUniform1f(GL20.glGetUniformLocation(ResourceManager.gluon_beam.getShaderId(), "subdivUAmount"), 1/(float)(subdivisions+1));
		
		Vec3d vec = new Vec3d(billboardPos.x, billboardPos.y, billboardPos.z).crossProduct(new Vec3d(1, 0, 0)).normalize();
		for(int i = 0; i <= subdivisions; i ++){
			float iN = ((float)i/(float)subdivisions);
			float pos = iN*len;
			buf.pos(pos, -vec.y, -vec.z).tex(iN, 0).endVertex();
			buf.pos(pos, vec.y, vec.z).tex(iN, 1).endVertex();
		}
		tes.draw();
		
		HbmShaderManager2.releaseShader();
		
		//System.out.println(System.nanoTime() - l);
		if(!GeneralConfig.useShaders2){
			GlStateManager.color(1, 1, 1, 1);
		}
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GL11.glPopMatrix();
	}
}