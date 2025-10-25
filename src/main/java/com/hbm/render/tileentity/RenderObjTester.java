package com.hbm.render.tileentity;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.handler.HbmShaderManager2;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.util.ModelRendererUtil;
import com.hbm.render.util.ModelRendererUtil.VertexData;
import com.hbm.render.util.RenderMiscEffects;
import com.hbm.tileentity.deco.TileEntityObjTester;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

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

        GL11.glPushMatrix();
        GL11.glTranslated(0, 8, 0);
        Vec3d player = new Vec3d(x + 0.5, y + 8, z + 0.5);
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        bindTexture(ResourceManager.turbofan_blades_tex);

        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        
        GL11.glPopMatrix();
        
		GL11.glRotatef(-90, 0, 1, 0);
        GL11.glTranslated(0, 3, 0);
        //Drillgon200: The thing is dead.
        bindTexture(ResourceManager.bobkotium_tex);
        ResourceManager.nikonium.renderAll();

        
        
        GL11.glTranslated(0, 4, 0);
        EntityCreeper creep = new EntityCreeper(Minecraft.getMinecraft().world);
        creep.setPosition(te.getPos().getX()+0.5F, te.getPos().getY()+7, te.getPos().getZ()+0.5F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ModelRendererUtil.getEntityTexture(creep));
        List<Pair<Matrix4f, ModelRenderer>> boxes = ModelRendererUtil.getBoxesFromMob(creep);
        Vec3d nor = new Vec3d(0, 1, 1).normalize();
        float[] plane = new float[]{(float)nor.x, (float)nor.y, (float)nor.z, -0.5F};
        GlStateManager.disableDepth();
//        HbmShaderManager2.distort(0.5F, () -> {
//        	for(Pair<Matrix4f, ModelRenderer> p : boxes){
//            	GL11.glPushMatrix();
//            	BufferBuilder buf = Tessellator.getInstance().getBuffer();
//            	buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX);
//            	for(ModelBox b : p.getRight().cubeList){
//            		VertexData[] dat = ModelRendererUtil.cutAndCapModelBox(b, plane, p.getLeft());
//            		//dat[0].tessellate(buf);
//            		dat[1].tessellate(buf, false);
//            		//dat[2].tessellate(buf, true);
//            	}
//            	Tessellator.getInstance().draw();
//            	GL11.glPopMatrix();
//            }
//        });
        GlStateManager.enableDepth();
        GL11.glTranslated(0, -7, 0);
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
        
        GL11.glTranslated(0, 2, 0);
        bindTexture(ResourceManager.turbofan_blades_tex);

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

        
        int index = (int) ((System.currentTimeMillis()%1000000)/10F%64);
        float size = 1/8F;
        float u = (index%8)*size;
        float v = (index/8)*size;
        
        bindTexture(ResourceManager.bfg_smoke);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        int bruh = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GlStateManager.bindTexture(bruh);

        GlStateManager.disableBlend();
        ResourceManager.test.draw();
        GlStateManager.enableBlend();
        
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
        
		GL11.glTranslated(-0.5, -4, 0);
		RayTraceResult r = Library.rayTraceIncludeEntities(te.getWorld(), new Vec3d(te.getPos()).add(0, 2, 0.5), new Vec3d(te.getPos()).add(12, 2, 0.5), null);
		if(r != null && r.hitVec != null){
			BeamPronter.gluonBeam(Vec3.createVectorHelper(0, 0, 0), new Vec3(r.hitVec.subtract(te.getPos().getX(), te.getPos().getY()+2, te.getPos().getZ()+0.5)), 0.8F);
		} else {
			BeamPronter.gluonBeam(Vec3.createVectorHelper(0, 0, 0), Vec3.createVectorHelper(11, 0, 0), 0.8F);
		}

		GlStateManager.enableTexture2D();
		
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableFog();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        
        GL11.glPopMatrix();
        
	}
	
	//public int spikeV = -1;
}
