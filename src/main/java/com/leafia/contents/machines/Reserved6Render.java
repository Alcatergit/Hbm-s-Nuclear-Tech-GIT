package com.leafia.contents.machines;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.CompositeBrush;
import com.leafia.dev.math.FiaMatrix;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Reserved6Render extends TileEntitySpecialRenderer<Reserved6TE> {
	static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID+":textures/blocks/leafia/reserved6.png");
	@Override
	public void render(Reserved6TE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		super.render(te,x,y,z,partialTicks,destroyStage,alpha);
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y+0.5,z+0.5);
		bindTexture(texture);
		CompositeBrush brush = CompositeBrush.instance;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		for (EnumFacing facing : EnumFacing.values()) {
			brush.startDrawingQuads();
			FiaMatrix mat = new FiaMatrix(new Vec3d(0,0,0),new Vec3d(facing.getDirectionVec())).translate(0,0,0.5);
			Vec3d vec0 = mat.translate(-0.5,-0.5,0).position;
			Vec3d vec1 = mat.translate(0.5,-0.5,0).position;
			Vec3d vec2 = mat.translate(0.5,0.5,0).position;
			Vec3d vec3 = mat.translate(-0.5,0.5,0).position;
			brush.addVertexWithUV(vec0.x,vec0.y,vec0.z,0,1);
			brush.addVertexWithUV(vec1.x,vec1.y,vec1.z,1,1);
			brush.addVertexWithUV(vec2.x,vec2.y,vec2.z,1,0);
			brush.addVertexWithUV(vec3.x,vec3.y,vec3.z,0,0);
			brush.draw();
		}
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		LeafiaGls.popMatrix();
	}
}
