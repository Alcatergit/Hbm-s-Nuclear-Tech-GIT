package com.hbm.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.particle.lightning_test.TrailRenderer2;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleJetpackTrail extends Particle {

	public List<Vec3d> points = new ArrayList<Vec3d>();
	public List<Integer> ages = new ArrayList<>();
	
	public ParticleJetpackTrail(World worldIn) {
		super(worldIn, 0, 0, 0);
	}
	
	public void tryAddNewPos(Vec3d pos){
		if(points.size() == 0){
			points.add(pos);
			ages.add(0);
		} else {
			if(points.get(points.size()-1).distanceTo(pos) > 0.1){
				points.add(pos);
				ages.add(0);
			} else {
				points.set(points.size()-1, pos);
				ages.set(ages.size()-1, 0);
			}
		}
	}
	
	@Override
	public void onUpdate() {
		Iterator<Vec3d> itr = points.iterator();
		int i = 0;
		while(itr.hasNext()){
			itr.next();
			int age = ages.get(i);
			age ++;
			if(age > 20){
				itr.remove();
				ages.remove(i);
				continue;
			}
			ages.set(i, age);
			i++;
		}
		if(points.isEmpty()){
			this.setExpired();
		}
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return false;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		if(points.size() < 2)
			return;
		GL11.glPushMatrix();
		GL11.glTranslated(-interpPosX, -interpPosY, -interpPosZ);
		GlStateManager.disableLighting();
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		int i = this.getBrightnessForRender(partialTicks);
	    int j = i >> 16 & 65535;
	    int k = i & 65535;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
		final float end = 0.15F;
		TrailRenderer2.draw(new Vec3d(interpPosX, interpPosY+entityIn.getEyeHeight(), interpPosZ), points, 0.025F, false, false, t -> {
			float a = BobMathUtil.remap01_clamp(t, 0, end) * BobMathUtil.remap01_clamp(t, 1, 1-end);
			return new float[]{0.7F, 0.7F, 0.7F, a*0.7F};
		});
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}

}
