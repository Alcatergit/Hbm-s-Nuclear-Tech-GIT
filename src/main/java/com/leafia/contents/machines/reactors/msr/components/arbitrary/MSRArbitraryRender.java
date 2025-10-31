package com.leafia.contents.machines.reactors.msr.components.arbitrary;

import com.hbm.render.amlfrom1710.CompositeBrush;
import com.leafia.dev.math.FiaMatrix;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class MSRArbitraryRender extends TileEntitySpecialRenderer<MSRArbitraryTE> {
	@Override
	public void render(MSRArbitraryTE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		super.render(te,x,y,z,partialTicks,destroyStage,alpha);
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x+0.5,y+0.5,z+0.5);
		if (te.inventory.getStackInSlot(0).getItem() instanceof ItemBlock block) {
			IBlockState display = block.getBlock().getDefaultState();
			IBakedModel baked = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(display);
			tryBindQuads(baked,display,EnumFacing.NORTH);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GlStateManager.color(1, 1, 1, 1);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			CompositeBrush brush = CompositeBrush.instance;
			for (EnumFacing facing : EnumFacing.values()) {
				brush.startDrawingQuads();
				FiaMatrix mat = new FiaMatrix(new Vec3d(0,0,0),new Vec3d(facing.getDirectionVec())).translate(0,0,0.502);
				Vec3d vec0 = mat.translate(-6/16d,-6/16d,0).position;
				Vec3d vec1 = mat.translate(6/16d,-6/16d,0).position;
				Vec3d vec2 = mat.translate(6/16d,6/16d,0).position;
				Vec3d vec3 = mat.translate(-6/16d,6/16d,0).position;
				brush.addVertexWithUV(vec0.x,vec0.y,vec0.z,0,1);
				brush.addVertexWithUV(vec1.x,vec1.y,vec1.z,1,1);
				brush.addVertexWithUV(vec2.x,vec2.y,vec2.z,1,0);
				brush.addVertexWithUV(vec3.x,vec3.y,vec3.z,0,0);
				brush.draw();
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		LeafiaGls.popMatrix();
	}
	void tryBindQuads(IBakedModel baked,IBlockState display,EnumFacing face) {
		try {
			List<BakedQuad> quads = baked.getQuads(display,face,0);
			if (quads.size() > 0)
				bindByIconName(quads.get(0).getSprite().getIconName());
			else
				bindByIconName(baked.getParticleTexture().getIconName());
		} catch (IllegalArgumentException ignored) {} // FUCK YOUU
	}
	void bindByIconName(String resource) {
		// convert format like "hbm:         blocks/brick_concrete    "
		//                  to "hbm:textures/blocks/brick_concrete.png"
		bindTexture(new ResourceLocation(resource.replaceFirst("(\\w+:)?(.*)","$1textures/$2.png")));
	}
}
