package com.hbm.render.tileentity;

import com.hbm.items.machine.ItemCatalyst;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderSparks;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityCore;
import com.hbm.tileentity.machine.TileEntityCore.Cores;
import com.hbm.tileentity.machine.TileEntityCore.DFCShock;
import com.leafia.transformer.LeafiaGls;
import com.llib.math.LeafiaColor;
import com.llib.math.MathLeafia;
import com.llib.technical.LeafiaEase;
import com.llib.technical.LeafiaEase.Direction;
import com.llib.technical.LeafiaEase.Ease;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.ConcurrentModificationException;
import java.util.Random;

public class RenderCore extends TileEntitySpecialRenderer<TileEntityCore> {

	public static IModelCustom[] deformSphere = new IModelCustom[10];
	static {
		for (int i = 0; i < 10; i++)
			deformSphere[i] = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/leafia/deformed_sphere/deform"+i+".obj"));
	}
	public static IModelCustom instability_ring = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID,"models/leafia/ecr_instability_ring.obj"));

	@Override
	public boolean isGlobalRenderer(TileEntityCore te) {
		return true;
	}

	@Override
	public void render(TileEntityCore core, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (core.temperature < 100) {
			renderStandby(core, x, y, z);
		} else {

			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			//GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
			//GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX - 90, 1.0F, 0.0F, 0.0F);
			GL11.glTranslated(-0.5, -0.5, -0.5);

			renderOrb(core, 0, 0, 0, partialTicks);
			GL11.glPopMatrix();
		}
		if (core.jammerPos != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			LeafiaColor colorBlast = new LeafiaColor(1, 0.5, 0);
			LeafiaColor colorJammer = new LeafiaColor(0, 0.75, 1);
			Vec3 vec = new Vec3(core.jammerPos.subtract(core.getPos()));
			BeamPronter.prontBeam(
					vec,
					EnumWaveType.RANDOM, EnumBeamType.SOLID,
					colorBlast.toInARGB(), colorBlast.toInARGB(),
					(int) (core.getWorld().getTotalWorldTime() % 1000),
					(int) vec.length(), 0.5f, 1, 0.2f
			);
			BeamPronter.prontBeam(
					vec,
					EnumWaveType.RANDOM, EnumBeamType.SOLID,
					colorJammer.toInARGB(), colorJammer.toInARGB(),
					(int) ((core.getWorld().getTotalWorldTime() + 500) % 1000),
					(int) vec.length(), 0.5f, 1, 0.2f
			);
			GL11.glPopMatrix();
		}
		try {
			for (DFCShock shock : core.dfcShocks) {
				Vec3d lastPos = null;
				LeafiaGls.pushMatrix();
				GL11.glTranslated(x-core.getPos().getX(),y-core.getPos().getY(),z-core.getPos().getZ());
				if (core.getWorld().rand.nextInt(4) >= 1) {
					for (Vec3d pos : shock.poses) {
						if (lastPos != null) {
							if (pos.distanceTo(lastPos) < 0.1) continue;
							LeafiaGls.pushMatrix();
							LeafiaGls.translate(lastPos);
							Vec3 vec3 = new Vec3(pos.subtract(lastPos));
							BeamPronter.prontBeam(
									vec3,
									EnumWaveType.STRAIGHT,EnumBeamType.SOLID,
									0x5B1D00,0x7F7F7F,
									0,1,0,2,0.25f
							);
							LeafiaGls.popMatrix();
						}
						lastPos = pos;
					}
				}
				LeafiaGls.popMatrix();
			}
		} catch (ConcurrentModificationException ignored) {} // fuck you java array iterations
	}

	public void renderStandby(TileEntityCore core, double x, double y, double z) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GlStateManager.disableLighting();
		GlStateManager.enableCull();
		GlStateManager.disableTexture2D();

		GL11.glScalef(0.25F, 0.25F, 0.25F);
		float brightness = (float) Math.pow(core.temperature / 100d, 1.5);
		GlStateManager.color(0.1F + brightness * .9f, 0.1F + brightness * .9f, 0.1F + brightness * .9f);
		ResourceManager.sphere_uv.renderAll();

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		GL11.glScalef(1.25F, 1.25F, 1.25F);
		GlStateManager.color(0.1F + brightness * .9f, 0.2F + (((float) core.temperature / 100f) * 0.25f + brightness * 0.75f) * .8f, 0.4F + ((float) core.temperature / 100f) * .6f);
		ResourceManager.sphere_uv.renderAll();
		GlStateManager.disableBlend();

		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		if (core.getWorld().rand.nextInt(50) == 0) {
			for (int i = 0; i < 3; i++) {
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, 0, 0, 0, 1.5F, 5, 10, 0x00FFFF, 0xFFFFFF);
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, 0, 0, 0, 3F, 5, 10, 0x00FFFF, 0xFFFFFF);
			}
		}
		GlStateManager.color(1F, 1F, 1F);
		GL11.glPopMatrix();
	}

	public void renderOrb(TileEntityCore core, double x, double y, double z, float partialTicks) {

		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5,y+0.5,z+0.5);
		GL11.glPushMatrix();

		int color = core.color;
		float r = (color>>16&255)/255F;
		float g = (color>>8&255)/255F;
		float b = (color&255)/255F;

		int tot = core.tanks[0].getCapacity()+core.tanks[1].getCapacity();
		int fill = core.tanks[0].getFluidAmount()+core.tanks[1].getFluidAmount();

		float scale = (float) Math.log(core.temperature/50+1) /* * ((float) fill / (float) tot)*/+0.5F;
		double rot = 0;
		if (core.collapsing > 0.97) {
			double percent = (core.collapsing-0.97)/0.03;
			LeafiaEase ease = new LeafiaEase(Ease.EXPO,Direction.I);
			scale *= (float) ease.get(ease.get(percent),1,0);
			rot = ease.get(percent)*1000;
		}
		GL11.glScalef(scale,scale,scale);

		GlStateManager.enableCull();
		GlStateManager.disableLighting();
		bindTexture(new ResourceLocation(RefStrings.MODID,"textures/solid_emissive.png")); // shader fix
		GlStateManager.disableTexture2D();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,240F,240F);


		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		if (core.ringAlpha > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			int colorC = core.colorCatalyst;
			float rC = (colorC>>16&255)/255F;
			float gC = (colorC>>8&255)/255F;
			float bC = (colorC&255)/255F;
			GlStateManager.scale(0.025f,0.025f,0.025f);
			GlStateManager.color(rC,gC,bC,core.ringAlpha);
			GlStateManager.pushMatrix();
			GlStateManager.rotate(core.ringAngle+core.ringSpinSpeed*partialTicks,0,1,0);
			instability_ring.renderPart("InstabilityInnerRing");
			GlStateManager.popMatrix();
			GlStateManager.rotate(core.ringAngle+core.ringSpinSpeed*partialTicks,0,-1,0);
			instability_ring.renderPart("InstabilityRing");
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
		GlStateManager.color(r,g,b,1.0F);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		GL11.glRotated(rot,1,1,1);
		GL11.glScalef(0.5F,0.5F,0.5F);
		ResourceManager.sphere_ruv.renderAll();
		GL11.glScalef(2F, 2F, 2F);

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);


		if (core.client_type == Cores.ams_core_eyeofharmony) {
			GL11.glScalef(0.5F,0.5F,0.5F);
			GL11.glPushMatrix();
			GL11.glRotatef(core.getWorld().rand.nextFloat()*360,1,0,0);
			GL11.glRotatef(core.getWorld().rand.nextFloat()*360,0,1,0);
			GL11.glRotatef(core.getWorld().rand.nextFloat()*360,0,0,1);
		}
		for (int i = 6; i <= 10; i++) {
			GL11.glPushMatrix();
			GL11.glScalef(i * 0.1F, i * 0.1F, i * 0.1F);
			if (core.client_type == Cores.ams_core_eyeofharmony)
				deformSphere[core.getWorld().rand.nextInt(10)].renderAll();
			else
				ResourceManager.sphere_ruv.renderAll();
			GL11.glPopMatrix();
		}
		if (core.client_type == Cores.ams_core_eyeofharmony) {
			GL11.glPopMatrix();
			GL11.glScalef(2F,2F,2F);
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		if (core.client_type == Cores.ams_core_sing) {
			GL11.glPushMatrix();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(0.11f, 0.11f, 0.11f);
			GL11.glScalef(-scale * 1.15f, -scale * 1.15f, -scale * 1.15f);
			ResourceManager.sphere_ruv.renderAll();
			GL11.glPopMatrix();

			GlStateManager.blendFunc(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.color(0.529f, 0.722f, 1, 0.5f);
			double tr = MathHelper.positiveModulo(System.currentTimeMillis() / 1000d, 3000) / 2 * Math.PI;
			double add = 0.4; // 0.6
			GL11.glRotated(tr / 3 * (180 / Math.PI), 0, 1, 2);
			for (int d = 0; d < 16; d++) {
				GL11.glPushMatrix();
				double t = tr + add * d;
				GL11.glRotated(-MathLeafia.smoothLinear(Math.abs(MathHelper.positiveModulo(t / 3 / Math.PI, 2) - 1), 0.5) * 180 * 3, 0, 0, 1);
				GL11.glRotated(Math.sin(t / 3) * 135, 0, 1, 0);
				GL11.glTranslated(0, 0, -scale * 1.4 / 2);
				GL11.glScalef(-0.25f, -0.25f, -0.25f);
				ResourceManager.sphere_ruv.renderAll();
				GL11.glPopMatrix();
			}
		} else if (core.client_type == Cores.ams_core_eyeofharmony) {
			LeafiaGls.disableCull();
			LeafiaGls.pushMatrix();
			LeafiaGls.rotate(core.angle+partialTicks*core.lightRotateSpeed,1,1,1);
			renderFlash(0.024f+core.getWorld().rand.nextFloat()*0.001f,20,1,r,g,b);
			LeafiaGls.enableCull();
			LeafiaGls.popMatrix();
		}


		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GL11.glPopMatrix();

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GL11.glPopMatrix();
	}
	private void renderFlash(float scale, double intensity, double alpha, float r, float g, float b) {

		GL11.glScalef(0.2F, 0.2F, 0.2F);

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
			buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(r,g,b, 0.0F).endVertex();
			buf.pos(0.866D * vert2, vert1, -0.5D * vert2).color(r,g,b, 0.0F).endVertex();
			buf.pos(0.0D, vert1, 1.0D * vert2).color(r,g,b, 0.0F).endVertex();
			buf.pos(-0.866D * vert2, vert1, -0.5D * vert2).color(r,g,b, 0.0F).endVertex();
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
}
