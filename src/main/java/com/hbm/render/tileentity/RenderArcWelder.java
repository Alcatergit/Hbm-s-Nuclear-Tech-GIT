package com.hbm.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineArcWelder;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderArcWelder extends TileEntitySpecialRenderer<TileEntityMachineArcWelder> {

	@Override
	public void render(TileEntityMachineArcWelder welder, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(welder.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, 0);
		
		bindTexture(ResourceManager.arc_welder_tex);
		ResourceManager.arc_welder.renderAll();

		if(welder.display != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.125D, 0D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			GL11.glRotatef(180, 0F, 1F, 0F);

			IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(welder.display, welder.getWorld(), null);
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.FIXED, false);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			Minecraft.getMinecraft().getRenderItem().renderItem(welder.display, model);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}
