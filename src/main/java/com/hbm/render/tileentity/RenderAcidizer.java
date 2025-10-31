package com.hbm.render.tileentity;

import com.hbm.tileentity.machine.CrystallizerCopyBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RenderAcidizer extends TileEntitySpecialRenderer<TileEntity> {

	@Override
	public boolean isGlobalRenderer(TileEntity te) {
		return true;
	}
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		switch(te.getBlockMetadata() - 10) {
			case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		CrystallizerCopyBase crys = (CrystallizerCopyBase) te;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		LeafiaQuickModel mdl = (LeafiaQuickModel)te;
		bindTexture(mdl.__getTexture());
		mdl.__getModel().renderPart("Body");

		GL11.glPushMatrix();
		GL11.glRotatef(crys.prevAngle + (crys.angle - crys.prevAngle) * partialTicks, 0, 1, 0);
		mdl.__getModel().renderPart("Spinner");
		GL11.glPopMatrix();

		if(crys.prevAngle != crys.angle) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDepthMask(false);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			FluidStack stuck = crys.tank.getFluid();
			if (stuck != null)
				bindByResource(stuck.getFluid().getStill().toString());
			mdl.__getModel().renderPart("Fluid");
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_BLEND);
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
	void bindByResource(String resource) {
		// convert format like "hbm:         blocks/brick_concrete    "
		//                  to "hbm:textures/blocks/brick_concrete.png"
		bindTexture(new ResourceLocation(resource.replaceFirst("(\\w+:)?(.*)","$1textures/$2.png")));
	}
}
