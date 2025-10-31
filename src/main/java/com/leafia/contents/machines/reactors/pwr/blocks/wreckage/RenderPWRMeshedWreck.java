package com.leafia.contents.machines.reactors.pwr.blocks.wreckage;

import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.hfr.render.loader.S_GroupObject;
import com.leafia.contents.machines.reactors.pwr.blocks.wreckage.PWRMeshedWreck.RuntimeRenderType;
import com.leafia.contents.machines.reactors.pwr.blocks.wreckage.PWRMeshedWreck.Variation;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;

public class RenderPWRMeshedWreck extends TileEntitySpecialRenderer<PWRMeshedWreckEntity> {

	@Override
	public boolean isGlobalRenderer(PWRMeshedWreckEntity entity) {
		return true;
	}

	@Override
	public void render(PWRMeshedWreckEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (entity.resourceLocation == null) return;

		LeafiaGls._push();
		LeafiaGls.pushMatrix();
		{
			LeafiaGls.translate(x + 0.5D, y + 0.5D, z + 0.5D);
			LeafiaGls.enableLighting();
			LeafiaGls.disableCull();
			IBlockState state = entity.getWorld().getBlockState(entity.getPos());
			if (state.getBlock() instanceof PWRMeshedWreck) {
				EnumFacing face = state.getValue(PWRMeshedWreck.FACING);
				// rotation algorithm parity to PWRMeshedWreck
				if (face.getYOffset() != 0) {
					if (face.equals(EnumFacing.DOWN)) LeafiaGls.rotate(-180,1,0,0);
				} else {
					LeafiaGls.rotate(180-face.getHorizontalAngle(),0,1,0);
					LeafiaGls.rotate(-90,1,0,0);
				}
			}
			Variation var = entity.getVariation();
			HFRWavefrontObject mesh = var.mesh;
			if (var.renderType == null) {
				int mode = 0b000;
				for (S_GroupObject group : mesh.groupObjects) {
					switch(group.name) {
						case "FRONT":
							mode = 0b100;
							break;
						case "TOP":
							mode = mode|0b010;
							break;
						case "BOTTOM":
							mode = mode|0b001;
							break;
					}
				}
				if ((mode&0b100) > 0)
					var.renderType = RuntimeRenderType.ALL_SIX;
				else if ((mode&0b001) > 0)
					var.renderType = RuntimeRenderType.SIDE_TOP_BOTTOM;
				else if ((mode&0b010) > 0)
					var.renderType = RuntimeRenderType.SIDE_TOP;
				else
					var.renderType = RuntimeRenderType.PARTICLE;
			}
			double scorch = Math.pow(entity.scorch/(double)7,0.8)*0.9;
			double scorch2 = Math.pow(entity.scorch/(double)7,4);
			LeafiaGls.color((float)(1-scorch),(float)(1-scorch-scorch2*0.05),(float)(1-scorch-scorch2*0.06));

			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(entity.resourceLocation));
			IBlockState display = block.getStateFromMeta(entity.meta);
			IBakedModel baked = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(display);
			switch(var.renderType) {
				case PARTICLE:
					bindByIconName(baked.getParticleTexture().getIconName());
					mesh.renderAll();
					break;
				case ALL_SIX:
					tryBindQuads(baked,state,EnumFacing.NORTH);
					mesh.renderPart("FRONT");
					tryBindQuads(baked,state,EnumFacing.EAST);
					mesh.renderPart("RIGHT");
					tryBindQuads(baked,state,EnumFacing.SOUTH);
					mesh.renderPart("BACK");
					tryBindQuads(baked,state,EnumFacing.WEST);
					mesh.renderPart("LEFT");
				case SIDE_TOP_BOTTOM:
					tryBindQuads(baked,state,EnumFacing.DOWN);
					mesh.renderPart("BOTTOM");
				case SIDE_TOP:
					tryBindQuads(baked,state,EnumFacing.UP);
					mesh.renderPart("TOP");
					if (!var.renderType.equals(RuntimeRenderType.ALL_SIX)) {
						bindByIconName(baked.getParticleTexture().getIconName());
						mesh.renderPart("SIDE");
					}
					break;
			}

			//GL11.glShadeModel(GL11.GL_SMOOTH);
		}
		LeafiaGls.popMatrix();
		LeafiaGls._pop();
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