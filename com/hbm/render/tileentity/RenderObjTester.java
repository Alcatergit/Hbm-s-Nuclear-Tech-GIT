package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.handler.HbmShaderManager2;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.util.RenderMiscEffects;
import com.hbm.tileentity.deco.TileEntityObjTester;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderObjTester extends TileEntitySpecialRenderer<TileEntityObjTester> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityObjTester te) {
		return true;
	}
	
	@Override
	public void render(TileEntityObjTester te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GlStateManager.disableLighting();
		/*GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        
        //Drillgon200: Aha! I finally started using bob's tester block!
        Quaternion identity = new Quaternion();
        Quaternion q = new Quaternion(0, 0.7071068F, 0, 0.7071068F);
        Quaternion a = identity;
        Quaternion b = q;
        float t = 0.5F;
        
        Quaternion r = new Quaternion();
		float t_ = 1 - t;
		float Wa, Wb;
		float theta = (float) Math.acos(Quaternion.dot(a, b));
		
		float sn = (float) Math.sin(theta);
		Wa = (float) (Math.sin(t_*theta) / sn);
		Wb = (float) (Math.sin(t*theta) / sn);
		if(sn == 0)
			Wa = Wb = 1;
		
		r.x = Wa*a.x + Wb*b.x;
		r.y = Wa*a.y + Wb*b.y;
		r.z = Wa*a.z + Wb*b.z;
		r.w = Wa*a.w + Wb*b.w;
		
		r.normalise(r);
		//System.out.println(r);
        
        //System.out.println(q);

        GL11.glPopMatrix();*/
		GL11.glRotatef(-90, 0, 1, 0);
        GL11.glTranslated(0, 3, 0);
        //Drillgon200: Reeee
        //Drillgon200: I hate niko
        bindTexture(ResourceManager.bobkotium_tex);
        ResourceManager.nikonium.renderAll();
        GL11.glTranslated(0, -3, 0);
        GL11.glRotatef(90, 0, 1, 0);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.fstbmb_tex);
        ResourceManager.fstbmb.renderPart("Body");
        ResourceManager.fstbmb.renderPart("Balefire");

        bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/misc/glintBF.png"));
        RenderMiscEffects.renderClassicGlint(te.getWorld(), partialTicks, ResourceManager.fstbmb, "Balefire", 0.0F, 0.8F, 0.15F, 5, 2F);

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        float f3 = 0.04F;
        GL11.glTranslatef(0.815F, 0.9275F, 0.5F);
        GL11.glScalef(f3, -f3, f3);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
        GL11.glRotatef(90, 0, 1, 0);
        GlStateManager.depthMask(false);
        GL11.glTranslatef(0, 1, 0);
        font.drawString("00:15", 0, 0, 0xff0000);
        RenderHelper.resetColor();
        GlStateManager.depthMask(true);

        GlStateManager.shadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        
        GL11.glTranslated(x+0.5, y+20, z+0.5);
        bindTexture(ResourceManager.turbofan_blades_tex);
        
        ResourceManager.BFG10K.renderAll();
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y+6, z+0.5);
        
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(0.5F, 1F, 0.5F, 0.6F);
        GlStateManager.color(1, 1, 1);
        
        
        /*bindTexture(ResourceManager.bfg_beam2);
        GlStateManager.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL14.GL_MIRRORED_REPEAT);
        
        float time = te.getWorld().getTotalWorldTime() + partialTicks;
        time *= 0.25;
        
        GL11.glScaled(1, 2, 1);
        
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        for(int i = 0; i < 5; i ++){
        	int offset = i*20;
        	int texFlip = i % 2 == 1 ? 1 : 0;
        	buf.pos(-25+offset, -1, 0).tex(0+texFlip+time, 0).endVertex();
            buf.pos(-25+offset, 12, 0).tex(0+texFlip+time, 1).endVertex();
            buf.pos(-5+offset, 12, 0).tex(1+texFlip+time, 1).endVertex();
            buf.pos(-5+offset, -1, 0).tex(1+texFlip+time, 0).endVertex();
        }
        tes.draw();
        GL11.glScaled(5, 1, 1);
        
        time *= 2;
        
        bindTexture(ResourceManager.bfg_beam1);
        HbmShaderManager.useShader2(HbmShaderManager.bfg_beam);
        GlStateManager.color(1F, 1F, 1F, 1F);
        
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buf.pos(-5, 4, 0).tex(0+time, 0).endVertex();
        buf.pos(-5, 6, 0).tex(0+time, 1).endVertex();
        buf.pos(15, 6, 0).tex(1+time, 1).endVertex();
        buf.pos(15, 4, 0).tex(1+time, 0).endVertex();
        tes.draw();
        
        HbmShaderManager.releaseShader2();*/
        
        int index = (int) ((System.currentTimeMillis()%1000000)/10F%64);
        float size = 1/8F;
        float u = (index%8)*size;
        float v = (index/8)*size;
        
        bindTexture(ResourceManager.bfg_smoke);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        int bruh = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GlStateManager.bindTexture(bruh);
        
       // buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
       /* buf.pos(-1, -1, 0).tex(u+size, v+size).endVertex();
        buf.pos(-1, 1, 0).tex(u+size, v).endVertex();
        buf.pos(1, 1, 0).tex(u, v).endVertex();
        buf.pos(1, -1, 0).tex(u, v+size).endVertex();
        buf.pos(-1, -1, 0).tex(u+size, v+size).endVertex();
        buf.pos(-1, 1, 0).tex(u+size, v).endVertex();
        buf.pos(1, 1, 0).tex(u, v).endVertex();
        buf.pos(1, -1, 0).tex(u, v+size).endVertex();*/
        /*buf.pos(-1, -1, 0).tex(0, 0).endVertex();
        buf.pos(-1, 1, 0).tex(0, 1).endVertex();
        buf.pos(1, 1, 0).tex(1, 1).endVertex();
        buf.pos(1, -1, 0).tex(1, 0).endVertex();
        tes.draw();*/
        ResourceManager.test.draw();
        
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		Matrix4f mvMatrix = new Matrix4f();
		mvMatrix.load(HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		Matrix4f pMatrix = new Matrix4f();
		pMatrix.load(HbmShaderManager2.AUX_GL_BUFFER);
		HbmShaderManager2.AUX_GL_BUFFER.rewind();
		
		Matrix4f.mul(pMatrix, mvMatrix, mvMatrix);
		
		Vector4f bruh1 = new Vector4f(0, 0, 0, 1);
		Matrix4f.transform(mvMatrix, bruh1, bruh1);
		Vector3f bruh2 = new Vector3f(bruh1.x/bruh1.w, bruh1.y/bruh1.w, bruh1.z/bruh1.w);
		//System.out.println(bruh2);
        
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableFog();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        
        GL11.glPopMatrix();
        
	}
}
