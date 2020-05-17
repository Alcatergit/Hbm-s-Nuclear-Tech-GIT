package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.RenderSparks;
import com.hbm.tileentity.machine.TileEntityCore;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCore extends TileEntitySpecialRenderer<TileEntityCore> {

	//Drillgon200: Not sure why this has end portal code in it
	//private static final ResourceLocation sky = new ResourceLocation("textures/environment/end_sky.png");
	//private static final ResourceLocation portal = new ResourceLocation("textures/entity/end_portal.png");
	//private static final Random random = new Random(31100L);
	//FloatBuffer buffer = GLAllocation.createDirectFloatBuffer(16);
	
	@Override
	public boolean isGlobalRenderer(TileEntityCore te) {
		return true;
	}
	
	@Override
	public void render(TileEntityCore core, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(core.heat == 0)
        	renderStandby(x, y, z);
        else
        	renderOrb(core, x, y, z);
	}
	
	public void renderStandby(double x, double y, double z) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.disableTexture2D();
        
        GL11.glScalef(0.25F, 0.25F, 0.25F);
        GlStateManager.color(0.5F, 0.5F, 0.5F);
        ResourceManager.sphere_uv.renderAll();
        
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        GL11.glScalef(1.25F, 1.25F, 1.25F);
        GlStateManager.color(0.1F, 0.1F, 0.1F);
        ResourceManager.sphere_uv.renderAll();
		GlStateManager.disableBlend();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        
        if((System.currentTimeMillis() / 100) % 10 == 0) {
			for(int i = 0; i < 3; i++) {
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 100 + i * 10000, 0, 0, 0, 1.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
				RenderSparks.renderSpark((int) System.currentTimeMillis() / 50 + i * 10000, 0, 0, 0, 1.5F, 5, 10, 0xFFFF00, 0xFFFFFF);
			}
        }
		
        GL11.glPopMatrix();
    }
    
    public void renderOrb(TileEntityCore tile, double x, double y, double z) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

        int color = tile.color;
        float r = (color >> 16 & 255)/255F;
		float g = (color >> 8 & 255)/255F;
		float b = (color & 255)/255F;
		GlStateManager.color(r, g, b, 1.0F);
		
		int tot = tile.tanks[0].getCapacity() + tile.tanks[1].getCapacity();
		int fill = tile.tanks[0].getFluidAmount() + tile.tanks[1].getFluidAmount();
		
		float scale = 4.5F * fill / tot + 0.5F;
		GL11.glScalef(scale, scale, scale);

        GlStateManager.enableCull();
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();

		GL11.glScalef(0.5F, 0.5F, 0.5F);
		ResourceManager.sphere_ruv.renderAll();
		GL11.glScalef(2F, 2F, 2F);
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		
		for(int i = 6; i <= 10; i++) {

	        GL11.glPushMatrix();
			GL11.glScalef(i * 0.1F, i * 0.1F, i * 0.1F);
			ResourceManager.sphere_ruv.renderAll();
	        GL11.glPopMatrix();
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
        GlStateManager.disableCull();
        GL11.glPopMatrix();
    }
    
   /* public void renderVoid(TileEntity tile, double x, double y, double z) {

		World world = tile.getWorld();
        GL11.glPushMatrix();
        
        GL11.glDisable(GL11.GL_LIGHTING);
        
        random.setSeed(31110L);

		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_S);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_T);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_R);
		GL11.glEnable(GL11.GL_TEXTURE_GEN_Q);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		float f100 = world.getTotalWorldTime() % 500L / 500F;
		GL11.glTranslatef(random.nextFloat(), f100, random.nextFloat());

		Tessellator tessellator = Tessellator.instance;

		final int end = 10;
		for (int i = 0; i < end; ++i) {
			float f5 = end - i;
			float f7 = 1.0F / (f5 + 1.0F);

			if (i == 0) {
				this.bindTexture(sky);
				f7 = 0.0F;
				f5 = 65.0F;
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			if (i == 1) {
				this.bindTexture(portal);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
			}

			GL11.glTranslatef(random.nextFloat() * (1 - f7), random.nextFloat() * (1 - f7), random.nextFloat() * (1 - f7));
			float scale = 0.9F;
            GL11.glScalef(scale, scale, scale);
            float ang = 360 / end;
            GL11.glRotatef(ang * i + ang * random.nextFloat(), 0.0F, 0.0F, 1.0F);

			float f11 = (float) random.nextDouble() * 0.5F + 0.9F;
			float f12 = (float) random.nextDouble() * 0.5F + 0.1F;
			float f13 = (float) random.nextDouble() * 0.5F + 0.9F;
			if (i == 0) {
				f13 = 1.0F;
				f12 = 1.0F;
				f11 = 1.0F;
			}
			f13 *= f7;
			f12 *= f7;
			f11 *= f7;

			GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));

			GL11.glRotatef(180, 0, 0, 1);
			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 1.0);
			tessellator.draw();

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 1.0);
			tessellator.draw();

			GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 0.0);

			tessellator.addVertex(x + 1.0, y + 0.0, z + 1.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 1.0);
			tessellator.draw();

			GL11.glTexGen(GL11.GL_S, GL11.GL_EYE_PLANE, this.func_147525_a(0, 1, 0, 0));
			GL11.glTexGen(GL11.GL_T, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 1, 0));
			GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0, 0, 0, 1));
			GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(1, 0, 0, 0));

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 0.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 0.0, y + 0.0, z + 0.0);
			tessellator.draw();

			tessellator.startDrawingQuads();
			tessellator.setColorOpaque_F(f11, f12, f13);
			tessellator.setBrightness(0xF000F0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 0.0);
			tessellator.addVertex(x + 1.0, y + 1.0, z + 1.0);
			tessellator.addVertex(x + 1.0, y + 0.0, z + 1.0);
			tessellator.draw();
		}

		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_T);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_R);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_Q);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
    }

    private FloatBuffer func_147525_a(float x, float y, float z, float w) {
    	
        this.buffer.clear();
        this.buffer.put(x).put(y).put(z).put(w);
        this.buffer.flip();
        return this.buffer;
    }*/
}
