package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.dynamic_texture.DynamicTextureRegistry;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;
import com.hbm.tileentity.machine.rbmk.RBMKDials;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderRBMKControlRod extends TileEntitySpecialRenderer<TileEntityRBMKControl>{

	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");
	
	@Override
	public boolean isGlobalRenderer(TileEntityRBMKControl te){
		return true;
	}
	
	@Override
	public void render(TileEntityRBMKControl control, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		bindTexture(((RBMKBase)control.getBlockType()).columnTexture);
		com.hbm.render.amlfrom1710.Tessellator tes = com.hbm.render.amlfrom1710.Tessellator.instance;
		tes.startDrawing(GL11.GL_TRIANGLES);

		ResourceManager.rbmk_rods.tessellatePartSplit(tes, "Column", 0.5F, (float)control.jumpHeight + RBMKDials.getColumnHeight(control.getWorld()));
		
		tes.draw();
		
		GlStateManager.enableLighting();
		GlStateManager.enableCull();

		
		if(control.getBlockType() instanceof RBMKBase) {
			ResourceLocation cover = ((RBMKBase) control.getBlockType()).coverTexture;
			String variant = "NONE";
			if(control instanceof TileEntityRBMKControlManual manual && manual.color != null) {
				variant = manual.color.name();
			}
			int gl = DynamicTextureRegistry.getTexture(cover, variant);
			if(gl >= 0) {
				GlStateManager.bindTexture(gl);
			} else {
				bindTexture(cover);
			}
		} else {
			bindTexture(texture);
		}
		
		double level = control.lastLevel + (control.level - control.lastLevel) * partialTicks;
		
		GL11.glTranslated(0, RBMKDials.getColumnHeight(control.getWorld()) + control.jumpHeight + level, 0);
		
		tes.startDrawing(GL11.GL_TRIANGLES);
		ResourceManager.rbmk_rods.tessellatePart(tes, "Lid");
		tes.draw();

		GL11.glPopMatrix();
	}
}