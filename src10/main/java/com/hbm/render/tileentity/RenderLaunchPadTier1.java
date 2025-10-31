package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderLaunchPadTier1 extends TileEntitySpecialRenderer<TileEntityLaunchPad> {

	public static final float h_1 = 1F;
	public static final float h_2 = 1F;
	public static final float h_3 = 0.8F;

	public static final float w_2 = 1F;
	
	@Override
	public boolean isGlobalRenderer(TileEntityLaunchPad te) {
		return true;
	}
	
	@Override
	public void render(TileEntityLaunchPad te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.disableCull();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.missile_pad_tex);
	    ResourceManager.missile_pad.renderAll();

		if(te.clearingTimer == 0){
			int state = te.state;
			GL11.glTranslated(0, 1, 0);
			if (state == 1) {
				GL11.glScalef(1.0F, h_1, 1.0F);
				bindTexture(ResourceManager.missileV2_HE_tex);
				ResourceManager.missileV2.renderAll();
			}
			if (state == 2) {
				GL11.glScalef(w_2, h_2, w_2);
				bindTexture(ResourceManager.missileStrong_HE_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if (state == 3) {
				GL11.glScalef(1.0F, h_1, 1.0F);
				bindTexture(ResourceManager.missileV2_CL_tex);
				ResourceManager.missileV2.renderAll();
			}
			if (state == 4) {
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileNuclear_tex);
				ResourceManager.missileNuclear.renderAll();
			}
			if (state == 5) {
				GL11.glScalef(1.0F, h_1, 1.0F);
				bindTexture(ResourceManager.missileV2_IN_tex);
				ResourceManager.missileV2.renderAll();
			}
			if (state == 6) {
				GL11.glScalef(1.0F, h_1, 1.0F);
				bindTexture(ResourceManager.missileV2_BU_tex);
				ResourceManager.missileV2.renderAll();
			}
			if (state == 7) {
				GL11.glScalef(w_2, h_2, w_2);
				bindTexture(ResourceManager.missileStrong_IN_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if (state == 8) {
				GL11.glScalef(w_2, h_2, w_2);
				bindTexture(ResourceManager.missileStrong_CL_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if (state == 9) {
				GL11.glScalef(w_2, h_2, w_2);
				bindTexture(ResourceManager.missileStrong_BU_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if (state == 10) {
				GL11.glScalef(h_3, h_3, h_3);
				bindTexture(ResourceManager.missileHuge_HE_tex);
				ResourceManager.missileHuge.renderAll();
			}
			if (state == 11) {
				GL11.glScalef(h_3, h_3, h_3);
				bindTexture(ResourceManager.missileHuge_IN_tex);
				ResourceManager.missileHuge.renderAll();
			}
			if (state == 12) {
				GL11.glScalef(h_3, h_3, h_3);
				bindTexture(ResourceManager.missileHuge_CL_tex);
				ResourceManager.missileHuge.renderAll();
			}
			if (state == 13) {
				GL11.glScalef(h_3, h_3, h_3);
				bindTexture(ResourceManager.missileHuge_BU_tex);
				ResourceManager.missileHuge.renderAll();
			}
			if (state == 14) {
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileEndo_tex);
				ResourceManager.missileThermo.renderAll();
			}
			if (state == 15) {
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileExo_tex);
				ResourceManager.missileThermo.renderAll();
			}
			if (state == 16) {
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileMIRV_tex);
				ResourceManager.missileNuclear.renderAll();
			}
			if (state == 17) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileDoomsday_tex);
				ResourceManager.missileDoomsday.renderAll();
			}
			if (state == 18) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileTaint_tex);
				ResourceManager.missileTaint.renderAll();
			}
			if (state == 19) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileMicro_tex);
				ResourceManager.missileTaint.renderAll();
			}
			if (state == 20) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileCarrier_tex);
				ResourceManager.missileCarrier.renderAll();
				GL11.glTranslated(0.0D, 0.5D, 0.0D);
				GL11.glTranslated(1.25D, 0.0D, 0.0D);
				bindTexture(ResourceManager.missileBooster_tex);
				ResourceManager.missileBooster.renderAll();
				GL11.glTranslated(-2.5D, 0.0D, 0.0D);
				ResourceManager.missileBooster.renderAll();
				GL11.glTranslated(1.25D, 0.0D, 0.0D);
				GL11.glTranslated(0.0D, 0.0D, 1.25D);
				ResourceManager.missileBooster.renderAll();
				GL11.glTranslated(0.0D, 0.0D, -2.5D);
				ResourceManager.missileBooster.renderAll();
				GL11.glTranslated(0.0D, 0.0D, 1.25D);
			}
			if (state == 21) {
				GL11.glScalef(1.0F, 1.0F, 1.0F);
				bindTexture(ResourceManager.missileAA_tex);
				ResourceManager.missileAB.renderAll();
			}
			if (state == 22) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileMicroBHole_tex);
				ResourceManager.missileTaint.renderAll();
			}
			if (state == 23) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileMicroSchrab_tex);
				ResourceManager.missileTaint.renderAll();
			}
			if (state == 24) {
				GL11.glScalef(2F, 2F, 2F);
				bindTexture(ResourceManager.missileMicroEMP_tex);
				ResourceManager.missileTaint.renderAll();
			}
			if (state == 25) {
				GL11.glScalef(w_2, h_2, w_2);
				bindTexture(ResourceManager.missileStrong_EMP_tex);
				ResourceManager.missileStrong.renderAll();
			}
			if(state == 26)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileVolcano_tex);
				ResourceManager.missileNuclear.renderAll();
			}
			if(state == 27)
			{
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				bindTexture(ResourceManager.missileN2_tex);
				ResourceManager.missileNuclear.renderAll();
			}
		}

		GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableCull();

		GL11.glPopMatrix();
	}
}
