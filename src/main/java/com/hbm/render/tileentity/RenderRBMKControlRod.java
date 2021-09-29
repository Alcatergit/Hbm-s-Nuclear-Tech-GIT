package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RenderRBMKControlRod extends TileEntitySpecialRenderer<TileEntityRBMKControl>{

	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");
	
	@Override
	public boolean isGlobalRenderer(TileEntityRBMKControl te){
		return true;
	}
	
	@Override
	public void render(TileEntityRBMKControl control, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		
		int offset = 1;
		
		for(int o = 1; o < 16; o++) {
			
			if(control.getWorld().getBlockState(new BlockPos(control.getPos().getX(), control.getPos().getY() + o, control.getPos().getZ())).getBlock() == control.getBlockType()) {
				offset = o;
			} else {
				break;
			}
		}
		
		GL11.glTranslated(x + 0.5, y + offset, z + 0.5);
		
		bindTexture(((RBMKBase)control.getBlockType()).columnTexture);
		com.hbm.render.amlfrom1710.Tessellator tes = com.hbm.render.amlfrom1710.Tessellator.instance;
		tes.startDrawing(GL11.GL_TRIANGLES);
		for(int i = 0; i < TileEntityRBMKBase.rbmkHeight+1; i ++){
			ResourceManager.rbmk_rods.tessellatePart(tes, "Column");
			tes.addTranslation(0, -1, 0);
		}
		tes.draw();
		
		GlStateManager.enableLighting();
		GlStateManager.enableCull();

		
		if(control.getBlockType() instanceof RBMKBase) {
			bindTexture(((RBMKBase)control.getBlockType()).coverTexture);
		} else {
			bindTexture(texture);
		}
		
		double level = control.lastLevel + (control.level - control.lastLevel) * partialTicks;
		
		GL11.glTranslated(0, level, 0);
		ResourceManager.rbmk_rods.renderPart("Lid");

		GL11.glPopMatrix();
	}
}
