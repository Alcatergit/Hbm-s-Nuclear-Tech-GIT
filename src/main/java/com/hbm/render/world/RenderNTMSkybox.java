package com.hbm.render.world;

import com.hbm.capability.HbmLivingProps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.opengl.GL11;

public class RenderNTMSkybox extends IRenderHandler { //why an abstract class uses the I-prefix is beyond me but ok, alright, whatever
	
	/*
	 * To get the terrain render order right, making a sky rendering handler is absolutely necessary. Turns out MC can only handle one of these, so what do we do?
	 * We make out own renderer, grab any existing renderers that are already occupying the slot, doing what is effectively chainloading while adding our own garbage.
	 * If somebody does the exact same thing as we do we might be screwed due to increasingly long recursive loops but we can fix that too, no worries.
	 */
	private IRenderHandler parent;

	private static final ResourceLocation digammaStar = new ResourceLocation("hbm:textures/misc/star_digamma.png");
	private static final ResourceLocation bobmazonSat = new ResourceLocation("hbm:textures/misc/sat_bobmazon.png");
	
	/*
	 * If the skybox was rendered successfully in the last tick (even from other mods' skyboxes chainloading this one) then we don't need to add it again
	 */
	public static boolean didLastRender = false;
	private int fuck = -1;
	private void zazaaz(BufferBuilder bufferBuilderIn, float posY, boolean reverseX)
	{
		int i = 64;
		int j = 6;

		for (int k = -384; k <= 384; k += 64)
		{
			for (int l = -384; l <= 384; l += 64)
			{
				float f = (float)k;
				float f1 = (float)(k + 64);

				if (reverseX)
				{
					f1 = (float)k;
					f = (float)(k + 64);
				}

				bufferBuilderIn.pos((double)f, (double)posY, (double)l).endVertex();
				bufferBuilderIn.pos((double)f1, (double)posY, (double)l).endVertex();
				bufferBuilderIn.pos((double)f1, (double)posY, (double)(l + 64)).endVertex();
				bufferBuilderIn.pos((double)f, (double)posY, (double)(l + 64)).endVertex();
			}
		}
	}
	
	public RenderNTMSkybox(IRenderHandler parent) {
		this.parent = parent;
		/*
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		this.fuck = GLAllocation.generateDisplayLists(1);
		GlStateManager.glNewList(this.fuck, 4864);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		this.zazaaz(bufferbuilder, 16.0F, false);
		this.zazaaz(bufferbuilder, -16.0F, true);
		tessellator.draw();
		GlStateManager.glEndList();*/
	}
/*
	public void renderVanillaSrf(float partialTicks, WorldClient world, Minecraft mc) {
		{
			TextureManager renderEngine = FMLClientHandler.instance().getClient().renderEngine;
			GlStateManager.disableTexture2D();
			Vec3d vec3d = world.getSkyColor(mc.getRenderViewEntity(), partialTicks);
			float f = (float)vec3d.x;
			float f1 = (float)vec3d.y;
			float f2 = (float)vec3d.z;

			if (pass != 2)
			{
				float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
				float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
				float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
				f = f3;
				f1 = f4;
				f2 = f5;
			}

			GlStateManager.color(f, f1, f2);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();
			GlStateManager.color(f, f1, f2);

			if (this.vboEnabled)
			{
				this.skyVBO.bindBuffer();
				GlStateManager.glEnableClientState(32884);
				GlStateManager.glVertexPointer(3, 5126, 12, 0);
				this.skyVBO.drawArrays(7);
				this.skyVBO.unbindBuffer();
				GlStateManager.glDisableClientState(32884);
			}
			else
			{
				GlStateManager.callList(this.glSkyList);
			}

			GlStateManager.disableFog();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			RenderHelper.disableStandardItemLighting();
			float[] afloat = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);

			if (afloat != null)
			{
				GlStateManager.disableTexture2D();
				GlStateManager.shadeModel(7425);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				float f6 = afloat[0];
				float f7 = afloat[1];
				float f8 = afloat[2];

				if (pass != 2)
				{
					float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
					float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
					float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
					f6 = f9;
					f7 = f10;
					f8 = f11;
				}

				bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
				int l1 = 16;

				for (int j2 = 0; j2 <= 16; ++j2)
				{
					float f21 = (float)j2 * ((float)Math.PI * 2F) / 16.0F;
					float f12 = MathHelper.sin(f21);
					float f13 = MathHelper.cos(f21);
					bufferbuilder.pos((double)(f12 * 120.0F), (double)(f13 * 120.0F), (double)(-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
				}

				tessellator.draw();
				GlStateManager.popMatrix();
				GlStateManager.shadeModel(7424);
			}

			GlStateManager.enableTexture2D();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.pushMatrix();
			float f16 = 1.0F - world.getRainStrength(partialTicks);
			GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			float f17 = 30.0F;
			renderEngine.bindTexture(SUN_TEXTURES);
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double)(-f17), 100.0D, (double)(-f17)).tex(0.0D, 0.0D).endVertex();
			bufferbuilder.pos((double)f17, 100.0D, (double)(-f17)).tex(1.0D, 0.0D).endVertex();
			bufferbuilder.pos((double)f17, 100.0D, (double)f17).tex(1.0D, 1.0D).endVertex();
			bufferbuilder.pos((double)(-f17), 100.0D, (double)f17).tex(0.0D, 1.0D).endVertex();
			tessellator.draw();
			f17 = 20.0F;
			renderEngine.bindTexture(MOON_PHASES_TEXTURES);
			int k1 = world.getMoonPhase();
			int i2 = k1 % 4;
			int k2 = k1 / 4 % 2;
			float f22 = (float)(i2 + 0) / 4.0F;
			float f23 = (float)(k2 + 0) / 2.0F;
			float f24 = (float)(i2 + 1) / 4.0F;
			float f14 = (float)(k2 + 1) / 2.0F;
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((double)(-f17), -100.0D, (double)f17).tex((double)f24, (double)f14).endVertex();
			bufferbuilder.pos((double)f17, -100.0D, (double)f17).tex((double)f22, (double)f14).endVertex();
			bufferbuilder.pos((double)f17, -100.0D, (double)(-f17)).tex((double)f22, (double)f23).endVertex();
			bufferbuilder.pos((double)(-f17), -100.0D, (double)(-f17)).tex((double)f24, (double)f23).endVertex();
			tessellator.draw();
			GlStateManager.disableTexture2D();
			float f15 = world.getStarBrightness(partialTicks) * f16;

			if (f15 > 0.0F)
			{
				GlStateManager.color(f15, f15, f15, f15);

				if (this.vboEnabled)
				{
					this.starVBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.starVBO.drawArrays(7);
					this.starVBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				}
				else
				{
					GlStateManager.callList(this.starGLCallList);
				}
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableFog();
			GlStateManager.popMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.color(0.0F, 0.0F, 0.0F);
			double d3 = mc.player.getPositionEyes(partialTicks).y - world.getHorizon();

			if (d3 < 0.0D)
			{
				GlStateManager.pushMatrix();
				GlStateManager.translate(0.0F, 12.0F, 0.0F);

				if (this.vboEnabled)
				{
					this.sky2VBO.bindBuffer();
					GlStateManager.glEnableClientState(32884);
					GlStateManager.glVertexPointer(3, 5126, 12, 0);
					this.sky2VBO.drawArrays(7);
					this.sky2VBO.unbindBuffer();
					GlStateManager.glDisableClientState(32884);
				}
				else
				{
					GlStateManager.callList(glSkyList2);
				}

				GlStateManager.popMatrix();
				float f18 = 1.0F;
				float f19 = -((float)(d3 + 65.0D));
				float f20 = -1.0F;
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
				bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, (double)f19, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, (double)f19, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
			}

			if (world.provider.isSkyColored())
			{
				GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
			}
			else
			{
				GlStateManager.color(f, f1, f2);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -((float)(d3 - 16.0D)), 0.0F);
			GlStateManager.callList(this.glSkyList2);
			GlStateManager.popMatrix();
			GlStateManager.enableTexture2D();
			GlStateManager.depthMask(true);
		}
	}*/
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		if(parent != null) {
			parent.render(partialTicks, world, mc);
		} else{
			RenderGlobal rg = Minecraft.getMinecraft().renderGlobal;
			world.provider.setSkyRenderer(null);
			rg.renderSky(partialTicks, 2);
			world.provider.setSkyRenderer(this);
		}
		
		GL11.glPushMatrix();
		GlStateManager.depthMask(false);
		//GlStateManager.color(1,0,0);
		//GlStateManager.callList(this.fuck);

		GlStateManager.disableFog();
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		
		float brightness = (float) Math.sin(world.getCelestialAngle(partialTicks) * Math.PI);
		brightness *= brightness;
		
		GlStateManager.color(brightness, brightness, brightness, 1.0F);
		
		GL11.glPushMatrix();
		GL11.glScalef(0.9999F, 0.9999F, 0.9999F);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(digammaStar);
		
		float digamma = HbmLivingProps.getDigamma(Minecraft.getMinecraft().player);
		float var12 = 1F * (1 + digamma * 0.25F);
		double dist = 100D - digamma * 2.5;
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(-var12, dist, -var12).tex(0, 0).endVertex();
		buf.pos(var12, dist, -var12).tex(0, 1).endVertex();
		buf.pos(var12, dist, var12).tex(1, 1).endVertex();
		buf.pos(-var12, dist, var12).tex(1, 0).endVertex();
		tessellator.draw();
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glRotatef(-40.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((System.currentTimeMillis() % (360 * 1000) / 1000F), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((System.currentTimeMillis() % (360 * 100) / 100F), 1.0F, 0.0F, 0.0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(bobmazonSat);

		var12 = 0.5F;
		dist = 100D;
		
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(-var12, dist, -var12).tex(0, 0).endVertex();
		buf.pos(var12, dist, -var12).tex(0, 1).endVertex();
		buf.pos(var12, dist, var12).tex(1, 1).endVertex();
		buf.pos(-var12, dist, var12).tex(1, 0).endVertex();
		tessellator.draw();
		GL11.glPopMatrix();
		
		GlStateManager.depthMask(true);

		GlStateManager.enableFog();
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1, 1);
		
		GL11.glPopMatrix();
		
		didLastRender = true;
	}

}