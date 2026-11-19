package com.hbm.render.entity.effect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import com.hbm.main.ModEventHandlerClient;
import org.lwjgl.opengl.*;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityNukeTorex.Cloudlet;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class RenderTorex extends Render<EntityNukeTorex> {

	public static final IRenderFactory<EntityNukeTorex> FACTORY = RenderTorex::new;
	
	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private static final ResourceLocation flare = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");

	public static final int flashBaseDuration = 30;
	public static final int flareBaseDuration = 100;

	protected RenderTorex(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(EntityNukeTorex cloud, double x, double y, double z, float entityYaw, float partialTicks){
		float scale = (float)cloud.getScale();
		float flashDuration = (float)Math.sqrt(scale) * flashBaseDuration;
		float flareDuration = scale * flareBaseDuration;

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog) GL11.glDisable(GL11.GL_FOG);

		cloudletWrapper(cloud, partialTicks);

		if(cloud.ticksExisted < flareDuration + 1)
			flareWrapper(cloud, partialTicks, flareDuration);

		if(cloud.ticksExisted < flashDuration + 1)
			flashWrapper(cloud, partialTicks, flashDuration);

		updateFlashState(cloud, partialTicks);

		if(cloud.ticksExisted < (flashDuration / 10) && System.currentTimeMillis() - ModEventHandlerClient.flashTimestamp > 1_000) {
			ModEventHandlerClient.flashTimestamp = System.currentTimeMillis();
			ModEventHandlerClient.flashDuration = (int) (5_000 * Math.sqrt(scale));
		}

		if(cloud.didPlaySound && !cloud.didShake && System.currentTimeMillis() - ModEventHandlerClient.shakeTimestamp > 1_000) {
			EntityPlayer player = MainRegistry.proxy.me();
			float dist = player.getDistance(cloud);
			ModEventHandlerClient.shakeTimestamp = System.currentTimeMillis();
			ModEventHandlerClient.shakeDuration = (int) (1_500 * Math.sqrt(scale));
			ModEventHandlerClient.shakeMultiplier = Math.max(((scale * 200D) - (double) dist) / (scale * 200D), 0D) * Math.sqrt(scale);
			player.hurtTime = (int) (Math.max((((((scale * 200F) - dist)) / (scale * 200F)) * 150F), 0) * Math.sqrt(scale));
			player.maxHurtTime = (int) (Math.max((((((scale * 200F) - dist)) / (scale * 200F)) * 100F), 0) * Math.sqrt(scale));
			player.attackedAtYaw = 0F;
			cloud.didShake = true;
		}

		if(fog) GL11.glEnable(GL11.GL_FOG);

		GL11.glPopMatrix();
	}

	private static void updateFlashState(EntityNukeTorex cloud, float partialTicks) {
		EntityNukeTorex.flashLightTable = cloud.world.provider.getLightBrightnessTable();
		EntityNukeTorex.flashStamp = System.currentTimeMillis();

		double scale = cloud.getScale();
		double u = (cloud.ticksExisted + partialTicks) / (flareBaseDuration * scale);
		float intensity = 0F;

		if(u < 1.0D) {
			double r = Math.log(0.3472D) / Math.log(0.3D / Math.sqrt(scale));
			double x = Math.pow(u, r);
			double i = x * Math.pow(Math.E, -x) * 2.717391304D;
			intensity = (float) ((1.0D - i) * i * i * 6.75D);
		}

		if(intensity <= 0F) {
			EntityNukeTorex.flashI = 0F;
			EntityNukeTorex.flashW = 0F;
			return;
		}

		Vec3 hue = cloud.getInterpColor(intensity, cloud.getType());
		double max = Math.max(hue.xCoord, Math.max(hue.yCoord, hue.zCoord));

		if(max <= 0.0D) {
			EntityNukeTorex.flashI = 0F;
			EntityNukeTorex.flashW = 0F;
			return;
		}

		EntityNukeTorex.flashR = (float) (hue.xCoord / max);
		EntityNukeTorex.flashG = (float) (hue.yCoord / max);
		EntityNukeTorex.flashB = (float) (hue.zCoord / max);
		EntityNukeTorex.flashI = intensity;
		EntityNukeTorex.flashW = 0.45F * intensity;
	}

	/** Tints the fog with the running flash color, mirroring the sky tint. */
	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onTorexFogColors(EntityViewRenderEvent.FogColors event) {
		float weight = EntityNukeTorex.getFlashWeight();

		if(weight <= 0F)
			return;

		Entity view = Minecraft.getMinecraft().getRenderViewEntity();

		if(view == null)
			return;

		IBlockState state = ActiveRenderInfo.getBlockStateAtEntityViewpoint(view.world, view, (float) event.getRenderPartialTicks());

		if(state.getMaterial().isLiquid())
			return;

		event.setRed(event.getRed() * (1F - weight) + EntityNukeTorex.flashR * weight);
		event.setGreen(event.getGreen() * (1F - weight) + EntityNukeTorex.flashG * weight);
		event.setBlue(event.getBlue() * (1F - weight) + EntityNukeTorex.flashB * weight);
	}

	private final Comparator cloudSorter = (arg0, arg1) -> {
        Cloudlet first = (Cloudlet) arg0;
        Cloudlet second = (Cloudlet) arg1;
        EntityPlayer player = MainRegistry.proxy.me();
        double dist1 = player.getDistanceSq(first.posX, first.posY, first.posZ);
        double dist2 = player.getDistanceSq(second.posX, second.posY, second.posZ);

        return Double.compare(dist2, dist1);
    };

	private void cloudletWrapper(EntityNukeTorex cloud, float partialTicks) {

		boolean wasLighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
		boolean wasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		boolean wasAlphaTest = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
		boolean wasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int prevBlendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
		int prevBlendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);
		int prevAlphaFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
		float prevAlphaRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		// To prevent particles cutting off before fully fading out
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();

		bindTexture(cloudlet);

		Tessellator tess = Tessellator.getInstance();
        BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);

		ArrayList<Cloudlet> cloudlets = new ArrayList<>(cloud.cloudlets);
		cloudlets.sort(cloudSorter);

		for(Cloudlet cloudlet : cloudlets) {
			Vec3 vec = cloudlet.getInterpPos(partialTicks);
			tessellateCloudlet(buf, vec.xCoord - cloud.posX, vec.yCoord - cloud.posY, vec.zCoord - cloud.posZ, cloudlet, partialTicks);
		}

		tess.draw();

		GL11.glBlendFunc(prevBlendSrc, prevBlendDst);
		if(wasBlend) GL11.glEnable(GL11.GL_BLEND); else GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(prevAlphaFunc, prevAlphaRef);
		if(wasAlphaTest) GL11.glEnable(GL11.GL_ALPHA_TEST); else GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(wasDepthMask);
		if(wasLighting) RenderHelper.enableStandardItemLighting(); else RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();
	}

	private void flareWrapper(EntityNukeTorex cloud, float partialTicks, float flareDuration) {

		boolean wasLighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
		boolean wasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		boolean wasAlphaTest = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
		boolean wasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int prevBlendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
		int prevBlendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);
		int prevAlphaFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
		float prevAlphaRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);

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

		GL11.glBlendFunc(prevBlendSrc, prevBlendDst);
		if(wasBlend) GL11.glEnable(GL11.GL_BLEND); else GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(prevAlphaFunc, prevAlphaRef);
		if(wasAlphaTest) GL11.glEnable(GL11.GL_ALPHA_TEST); else GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(wasDepthMask);
		if(wasLighting) RenderHelper.enableStandardItemLighting(); else RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();
	}

	private void tessellateCloudlet(BufferBuilder buf, double posX, double posY, double posZ, Cloudlet cloud, float partialTicks) {

		float a = cloud.getAlpha();
		float scale = cloud.getScale();

		float f1 = ActiveRenderInfo.getRotationX();
		float f2 = ActiveRenderInfo.getRotationZ();
		float f3 = ActiveRenderInfo.getRotationYZ();
		float f4 = ActiveRenderInfo.getRotationXY();
		float f5 = ActiveRenderInfo.getRotationXZ();

		float brightness = cloud.type == EntityNukeTorex.TorexType.CONDENSATION ? 0.9F : 0.75F * cloud.colorMod;
		Vec3 color = cloud.getInterpColor(partialTicks);
		float r, g, b;
		r =  Math.max(0.15F, (float)color.xCoord * brightness);
		g =  Math.max(0.15F, (float)color.yCoord * brightness);
		b =  Math.max(0.15F, (float)color.zCoord * brightness);

		int br = (int)Math.max(48, (Math.min((r+g+b) / 3D, 1) * 240));
		r = Math.min(1F, r);
		g = Math.min(1F, g);
		b = Math.min(1F, b);

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

        if(cloud.ticksExisted < flashDuration) {

    		int prevAlphaFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
    		float prevAlphaRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);

    		GL11.glPushMatrix();
    		//Function [0, 1] that determines the scale and intensity (inverse!) of the flash
        	double intensity = (cloud.ticksExisted + interp) / flashDuration;
        	GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);

        	//Euler function to slow down the scale as it progresses
        	//Makes it start fast and the fade-out is nice and smooth
        	intensity = intensity * Math.pow(Math.E, -intensity) * 2.717391304D;

        	renderFlash(0.58F * (float)cloud.torusWidth, intensity, cloud.coreHeight);
            GL11.glAlphaFunc(prevAlphaFunc, prevAlphaRef);
    		GL11.glPopMatrix();
        }
	}

	private void renderFlash(float scale, double intensity, double height) {

		boolean wasLighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
		boolean wasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		boolean wasAlphaTest = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
		boolean wasTexture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
		boolean wasCull = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		boolean wasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int prevShadeModel = GL11.glGetInteger(GL11.GL_SHADE_MODEL);
		int prevBlendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
		int prevBlendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);

    	GL11.glScalef(0.2F, 0.2F, 0.2F);
    	GL11.glTranslated(0, height * 5, 0);

    	double inverse = 1.0D - intensity;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

        int count = MathHelper.clamp((int) Math.round(300.0D * (scale / 40.0D) * (scale / 40.0D)), 300, 3000);
        double golden = Math.PI * (3.0D - Math.sqrt(5.0D));

        buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

        for(int i = 0; i < count; i++) {

            double y = 1.0D - 2.0D * (i + 0.5D) / count;
            double ring = Math.sqrt(Math.max(0.0D, 1.0D - y * y));
            double angle = golden * i;
            double dirX = ring * Math.cos(angle);
            double dirZ = ring * Math.sin(angle);

            float vert1 = 25.0F * (float)(intensity * scale);
            float vert2 = vert1 / 2.0F;

            double ux, uy, uz;

            if(Math.abs(y) < 0.9D) {
                double inv = 1.0D / Math.max(ring, 1.0E-4D);
                ux = -dirZ * inv; uy = 0.0D; uz = dirX * inv;
            } else {
                double inv = 1.0D / Math.sqrt(dirX * dirX + y * y);
                ux = y * inv; uy = -dirX * inv; uz = 0.0D;
            }

            double vx = y * uz - dirZ * uy;
            double vy = dirZ * ux - dirX * uz;
            double vz = dirX * uy - y * ux;

            double ax = dirX * vert1;
            double ay = y * vert1;
            double az = dirZ * vert1;

            for(int k = 0; k < 8; k++) {

                double a0 = k * 0.7853981633974483D;
                double a1 = (k + 1) * 0.7853981633974483D;
                double c0 = Math.cos(a0), s0 = Math.sin(a0);
                double c1 = Math.cos(a1), s1 = Math.sin(a1);

                buf.pos(0.0D, 0.0D, 0.0D).color(1.0F, 1.0F, 1.0F, (float) inverse).endVertex();
                buf.pos(ax + (ux * c0 + vx * s0) * vert2, ay + (uy * c0 + vy * s0) * vert2, az + (uz * c0 + vz * s0) * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
                buf.pos(ax + (ux * c1 + vx * s1) * vert2, ay + (uy * c1 + vy * s1) * vert2, az + (uz * c1 + vz * s1) * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            }
        }

        tessellator.draw();

        GL11.glDepthMask(wasDepthMask);
        if(wasCull) GL11.glEnable(GL11.GL_CULL_FACE); else GL11.glDisable(GL11.GL_CULL_FACE);
        if(wasAlphaTest) GL11.glEnable(GL11.GL_ALPHA_TEST); else GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(prevBlendSrc, prevBlendDst);
        if(wasBlend) GL11.glEnable(GL11.GL_BLEND); else GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(prevShadeModel);
        if(wasTexture) GL11.glEnable(GL11.GL_TEXTURE_2D); else GL11.glDisable(GL11.GL_TEXTURE_2D);
        if(wasLighting) RenderHelper.enableStandardItemLighting(); else RenderHelper.disableStandardItemLighting();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityNukeTorex entity) {
		return null;
	}
}
