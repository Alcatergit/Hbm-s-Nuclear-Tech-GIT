package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelSteelCorner;
import com.hbm.render.model.ModelSteelRoof;
import com.hbm.render.model.ModelSteelWall;
import com.hbm.tileentity.deco.TileEntityDecoBlock;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderDecoBlock extends TileEntitySpecialRenderer<TileEntityDecoBlock> {

	private static final ResourceLocation texture1 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/deco/SteelWall.png");
	private static final ResourceLocation texture2 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/deco/SteelCorner.png");
	private static final ResourceLocation texture3 = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/deco/SteelRoof.png");

	private ModelSteelWall model1;
	private ModelSteelCorner model2;
	private ModelSteelRoof model3;

	public RenderDecoBlock() {
		this.model1 = new ModelSteelWall();
		this.model2 = new ModelSteelCorner();
		this.model3 = new ModelSteelRoof();
	}

	@Override
	public boolean isGlobalRenderer(TileEntityDecoBlock te) {
		return te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boxcar || te.getWorld().getBlockState(te.getPos()).getBlock() == ModBlocks.boat;
	}

	@Override
	public void render(TileEntityDecoBlock te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0F, 0F, 1F);

		Block block = te.getWorld().getBlockState(te.getPos()).getBlock();

		GlStateManager.enableLighting();
		if(block == ModBlocks.steel_wall) {
			this.bindTexture(texture1);
			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}
			this.model1.renderModel(0.0625F);
		} else if(block == ModBlocks.steel_corner) {
			this.bindTexture(texture2);
			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}
			this.model2.renderModel(0.0625F);
		} else if(block == ModBlocks.boxcar) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			switch(te.getBlockMetadata()) {
			case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
			default:
				GL11.glRotatef(180, 0F, 0F, 1F);
				GL11.glRotated(90, 1, 0, 0);
				GL11.glTranslatef(0, -1.5F, 0);
				break;
			}
			GlStateManager.enableCull();
			bindTexture(ResourceManager.boxcar_tex);
			// ResourceManager.boxcar.renderAll();
			// RenderHelper.renderAll(ClientProxy.boxcar);
			GL11.glCallList(ClientProxy.boxcarCalllist);

			GlStateManager.enableCull();
		} else if(block == ModBlocks.steel_roof) {
			this.bindTexture(texture3);
			this.model3.renderModel(0.0625F);
		} else if(block == ModBlocks.boat) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, 0, -1.5F);
			GL11.glTranslatef(0, 0.5F, 0);

			GlStateManager.enableCull();
			bindTexture(ResourceManager.duchessgambit_tex);
			ResourceManager.duchessgambit.renderAll();
		} else if(block == ModBlocks.sat_radar) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_radar_tex);
			ResourceManager.sat_radar.renderAll();
		} else if(block == ModBlocks.sat_resonator) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_resonator_tex);
			ResourceManager.sat_resonator.renderAll();
		} else if(block == ModBlocks.sat_scanner) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_scanner_tex);
			ResourceManager.sat_scanner.renderAll();
		} else if(block == ModBlocks.sat_mapper) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_mapper_tex);
			ResourceManager.sat_mapper.renderAll();
		} else if(block == ModBlocks.sat_laser) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_base_tex);
			ResourceManager.sat_base.renderAll();
			bindTexture(ResourceManager.sat_laser_tex);
			ResourceManager.sat_laser.renderAll();
		} else if(block == ModBlocks.sat_foeq) {
			GL11.glRotatef(180, 0F, 0F, 1F);
			GL11.glTranslatef(0, -1.5F, 0);

			GL11.glRotated(90, 0, 1, 0);

			switch(te.getBlockMetadata()) {
			case 4:
				GL11.glRotatef(90, 0F, 1F, 0F);
				break;
			case 2:
				GL11.glRotatef(180, 0F, 1F, 0F);
				break;
			case 5:
				GL11.glRotatef(270, 0F, 1F, 0F);
				break;
			case 3:
				GL11.glRotatef(0, 0F, 1F, 0F);
				break;
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			bindTexture(ResourceManager.sat_foeq_tex);
			ResourceManager.sat_foeq.renderAll();
		}
		GL11.glPopMatrix();
	}
}
