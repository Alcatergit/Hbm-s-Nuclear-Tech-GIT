package com.hbm.render.entity.effect;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityNukeTorex.Cloudlet;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.ContaminationUtil;
import com.leafia.passive.effects.LeafiaShakecam;
import com.llib.technical.LeafiaEase;
import com.llib.technical.LeafiaEase.Direction;
import com.llib.technical.LeafiaEase.Ease;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class RenderTorex extends Render<EntityNukeTorex> {

	public static final IRenderFactory<EntityNukeTorex> FACTORY = man -> new RenderTorex(man);
	
	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private static final ResourceLocation flare = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");

	public static final int flashBaseDuration = 15;
	public static final int flareBaseDuration = 100;

	protected RenderTorex(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(EntityNukeTorex cloud, double x, double y, double z, float entityYaw, float partialTicks){
		if (!cloud.isValid()) return;
		float scale = (float)cloud.getScale();
		float flashDuration = scale * flashBaseDuration;
		float flareDuration = scale * flareBaseDuration;
		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

		x = cloud.initPosX-d3;
		y = cloud.initPosY-d4; // you're welcome, you otherwise glitchy piece of sh*t
		z = cloud.initPosZ-d5;

		doScreenShake(cloud, x, y, z, scale * 100);
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog)
			GL11.glDisable(GL11.GL_FOG);

		cloudletWrapper(cloud, partialTicks);

		if(cloud.ticksExisted < flareDuration+1)
			flareWrapper(cloud, partialTicks, flareDuration);
		
		//if(cloud.ticksExisted < flashDuration+1)
			flashWrapper(cloud, partialTicks, flashDuration);

		if(fog)
			GL11.glEnable(GL11.GL_FOG);

		GL11.glPopMatrix();
	}

	private void doScreenShake(EntityNukeTorex cloud, double x, double y, double z, float amplitude){
		if(cloud.ticksExisted > 300) return;
		EntityPlayer player = MainRegistry.proxy.me();

		double dist = player.getDistance(cloud);
		double shockwaveDistance = dist - cloud.ticksExisted * 1.5 * cloud.animationSpeedShk + cloud.getScale()*flashBaseDuration*6*cloud.animationSpeedShk;
		if(shockwaveDistance > 10) return;

		if (!cloud.reachedPlayer && cloud.sound) {
			cloud.reachedPlayer = true;
			if (amplitude > 48) {
				SoundEvent evt = HBMSoundEvents.nuke;
				if (amplitude > 128) {
					if (dist <= 100 + Math.pow(amplitude,0.95))
						evt = HBMSoundEvents.nuke_near;
					else if (dist > 300 + amplitude + Math.pow(amplitude,0.8) * 2)
						evt = HBMSoundEvents.nuke_far;
				} else
					evt = HBMSoundEvents.nuke_smol;
				cloud.world.playSound(player,cloud.initPosX,cloud.initPosY,cloud.initPosZ,evt,SoundCategory.AMBIENT,amplitude * 15F,0.8F + cloud.world.rand.nextFloat() * 0.2F);
				LeafiaShakecam._addShake(cloud.getInitialPosition(),new LeafiaShakecam.shakeSimple(8f * (amplitude / 100),LeafiaEase.Ease.BACK,LeafiaEase.Direction.I).configure(amplitude * 12F,24f,0.5f,null));
				LeafiaShakecam._addShake(cloud.getInitialPosition(),new LeafiaShakecam.shakeSmooth(15f * (amplitude / 100),LeafiaEase.Ease.QUAD,LeafiaEase.Direction.I).configure(amplitude * 5F,12f,1.8f,8f));
				LeafiaShakecam._addShake(cloud.getInitialPosition(),new LeafiaShakecam.shakeSmooth(30f * (amplitude / 100),null,null).configure(amplitude * 4F,2f,1.5f,3.5f));
				LeafiaShakecam._addShake(cloud.getInitialPosition(),new LeafiaShakecam.shakeSmooth(60f * (amplitude / 100),null,null).configure(amplitude * 4F,0.5f,0.5f,2f));
			} else {
				cloud.world.playSound(player,cloud.getInitialPosition(),HBMSoundEvents.mukeExplosion,SoundCategory.BLOCKS,15,1);
			}
			Vec3d force = ContaminationUtil.getKnockback(player.getPositionVector().add(0,player.eyeHeight,0),cloud.getPositionVector(),amplitude);
			player.motionX += force.x;
			player.motionY += force.y;
			player.motionZ += force.z;
		}
		if(shockwaveDistance < 0) return;
		
		int duration = ((int)(amplitude * Math.min(1, (amplitude * amplitude)/(dist * dist))));
		int swingTimer = duration<<1;
		cloud.world.playSound(player, cloud.initPosX, cloud.initPosY, cloud.initPosZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.AMBIENT, amplitude * 10F, 0.8F + cloud.world.rand.nextFloat() * 0.2F);
		
		if(player.getDisplayName().equals("Vic4Games")) { // What's this troll code doing here lmao
			player.hurtTime = swingTimer<<1;
			player.maxHurtTime = duration<<1;
		} else {
			player.hurtTime = swingTimer;
			player.maxHurtTime = duration;
		}
		player.attackedAtYaw = 0F;
	}
	
	private Comparator cloudSorter = new Comparator() {

		@Override
		public int compare(Object arg0, Object arg1) {
			Cloudlet first = (Cloudlet) arg0;
			Cloudlet second = (Cloudlet) arg1;
			EntityPlayer player = MainRegistry.proxy.me();
			double dist1 = player.getDistanceSq(first.initPosX, first.initPosY, first.initPosZ);
			double dist2 = player.getDistanceSq(second.initPosX, second.initPosY, second.initPosZ);
			
			return dist1 > dist2 ? -1 : dist1 == dist2 ? 0 : 1;
		}
	};

	private void cloudletWrapper(EntityNukeTorex cloud, float partialTicks) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA,DestFactor.ONE_MINUS_SRC_ALPHA,SourceFactor.ONE,DestFactor.ZERO);
		// To prevent particles cutting off before fully fading out
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();
		
		bindTexture(cloudlet);

		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
		ArrayList<Cloudlet> cloudlets = new ArrayList(cloud.cloudlets);
		cloudlets.sort(cloudSorter);

		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		for(Cloudlet cloudlet : cloudlets) {
			Vec3 vec = cloudlet.getInterpPos(partialTicks);
			tessellateCloudlet(buf,0.35F,  vec.xCoord - cloud.initPosX, vec.yCoord - cloud.initPosY, vec.zCoord - cloud.initPosZ, cloudlet, partialTicks, false);
		}
		tess.draw();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA,DestFactor.ONE);
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		for(Cloudlet cloudlet : cloudlets) {
			if (cloudlet.type != EntityNukeTorex.TorexType.CONDENSATION) {
				Vec3 vec = cloudlet.getInterpPos(partialTicks);
				tessellateCloudlet(buf,0,vec.xCoord - cloud.initPosX,vec.yCoord - cloud.initPosY,vec.zCoord - cloud.initPosZ,cloudlet,partialTicks,true);
			}
		}
		tess.draw(); // /hbmleaf torex statFac ~ ~ ~100 100 false
		/*
		GlStateManager.blendFunc(SourceFactor.SRC_COLOR,DestFactor.ONE);
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		for(Cloudlet cloudlet : cloudlets) {
			if (cloudlet.type != EntityNukeTorex.TorexType.CONDENSATION) {
				Vec3 vec = cloudlet.getInterpPos(partialTicks);
				tessellateCloudlet(buf,vec.xCoord - cloud.initPosX,vec.yCoord - cloud.initPosY,vec.zCoord - cloud.initPosZ,cloudlet,partialTicks,true);
			}
		}
		tess.draw();*/

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private void flareWrapper(EntityNukeTorex cloud, float partialTicks, float flareDuration) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();
			
		bindTexture(flare);

		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
		
		double age = Math.min(cloud.ticksExisted + partialTicks, flareDuration);
		float alpha = (float) Math.min(1, (flareDuration - age) / flareDuration);
		
		Random rand = new Random(cloud.getEntityId());
		
		for(int i = 0; i < 3; i++) {
			float x = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float y = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float z = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			tessellateFlare(buf, x, y + cloud.coreHeight, z, (float) (10 * cloud.rollerSize), alpha, partialTicks);
		}

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	private void tessellateCloudlet(BufferBuilder buf, float minBrightness, double posX, double posY, double posZ, Cloudlet cloud, float partialTicks, boolean additive) {

		float a = cloud.getAlpha();
		float scale = cloud.getScale();

		float f1 = ActiveRenderInfo.getRotationX();
		float f2 = ActiveRenderInfo.getRotationZ();
		float f3 = ActiveRenderInfo.getRotationYZ();
		float f4 = ActiveRenderInfo.getRotationXY();
		float f5 = ActiveRenderInfo.getRotationXZ();

		float brightness = cloud.type == cloud.type.CONDENSATION ? 0.9F : 0.75F * cloud.colorMod;
		Vec3 color = cloud.getInterpColor(partialTicks);
		float r, g, b;
		r =  Math.max(minBrightness, (float)color.xCoord * brightness);
		g =  Math.max(minBrightness, (float)color.yCoord * brightness);
		b =  Math.max(minBrightness, (float)color.zCoord * brightness);

		int br = (int)Math.max(48, (Math.min((r+g+b) / 3D, 1) * 240));
		r = Math.min(1F, r);
		g = Math.min(1F, g);
		b = Math.min(1F, b);

		if (additive) {
			r = Math.max(r*1.2f-0.2f,0f);
			g = Math.max(g*1.2f-0.2f,0f);
			b = Math.max(b*1.2f-0.2f,0f);
		}

		buf.pos((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale)).tex(1, 1).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale)).tex(1, 0).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale)).tex(0, 0).color(r, g, b, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale)).tex(0, 1).color(r, g, b, a).lightmap(br, br).endVertex();
	}

	private void tessellateFlare(BufferBuilder buf, double posX, double posY, double posZ, float scale, float a, float partialTicks) {

		float f1 = ActiveRenderInfo.getRotationX();
		float f2 = ActiveRenderInfo.getRotationZ();
		float f3 = ActiveRenderInfo.getRotationYZ();
		float f4 = ActiveRenderInfo.getRotationXY();
		float f5 = ActiveRenderInfo.getRotationXZ();
		int br = (int)(a * 240);
		buf.pos((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale)).tex(1, 1).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale)).tex(1, 0).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale)).tex(0, 0).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();
		buf.pos((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale)).tex(0, 1).color(1F, 1F, 1F, a).lightmap(br, br).endVertex();

	}

	private void flashWrapper(EntityNukeTorex cloud, float interp, float flashDuration) {

        //if(cloud.ticksExisted < flashDuration) {

		if (cloud.ticksExisted+interp > flashDuration*12/2) return;
		float scale = (float)cloud.getScale(); // MY SUFFERING. LOOK AT THIS MESSY CODE SJGDAJGDS

		//Function [0, 1] that determines the scale and intensity (inverse!) of the flash
		double intensity = (cloud.ticksExisted + interp) / flashDuration * 2;
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
		GL11.glPushMatrix();
		if (intensity < 1) {
			LeafiaEase ease = new LeafiaEase(Ease.QUAD,Direction.O);
			intensity = ease.get(intensity);
			renderFlash(scale*(float)intensity,50,cloud.coreHeight,1);
		} else {
			//LeafiaEase ease = new LeafiaEase(Ease.QUAD,Direction.IO);
			//double alpha = 1-ease.get((intensity-1)/5);
			//GL11.glColor3d(intensity,intensity,intensity);
			double size = Math.pow(Math.min(intensity,20),0.5);
			renderFlash(scale*(float)size,50,cloud.coreHeight,Math.max(1-Math.pow((intensity-1)/8,0.5),0));
		}

		//Euler function to slow down the scale as it progresses
		//Makes it start fast and the fade-out is nice and smooth
		//intensity = intensity * Math.pow(Math.E, -intensity) * 2.717391304D;

		//renderFlash(scale*(float)intensity  /* *(float)flashDuration/(float)flashBaseDuration,*/ /*intensity*/,50, cloud.coreHeight);
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glPopMatrix();
        //}
	}

	private void renderFlash(float scale, double intensity, double height, double alpha) {

    	GL11.glScalef(0.2F, 0.2F, 0.2F);
    	GL11.glTranslated(0, height * 4, 0);

    	double inverse = 1.0D - intensity;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
        GL11.glPushMatrix();

        for(int i = 0; i < 300; i++) {

            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);

            float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float)(intensity * scale);
            float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float)(intensity * scale);

            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            buf.pos(0D, 0D, 0D).color(1.0F, 1.0F, 1.0F, (float)alpha/*(float) inverse*/).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.0D, vert1, 1.0D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }

        GL11.glPopMatrix();

        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNukeTorex entity) {
		return null;
	}
}
