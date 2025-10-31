package com.leafia.contents.machines.reactors.tokamakt2;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TokamakT2Render extends TileEntitySpecialRenderer<TokamakT2TE> {
	static final IModelCustom mdl = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID+":models/xenoulexi/fusion_tier_2.obj")
	);
	static ResourceLocation getRsc(String s) {
		return new ResourceLocation(RefStrings.MODID+":textures/models/xenoulexi/t2tokamak/"+s+".png");
	}
	static final ResourceLocation microwave = getRsc("microwave");
	static final ResourceLocation plasma = getRsc("plasma");
	static final ResourceLocation t2glass = getRsc("t2glass");
	static final ResourceLocation t2rails = getRsc("t2rails");
	static final ResourceLocation t2solenoid = getRsc("t2solenoid");
	static final ResourceLocation t2toroidal = getRsc("t2toroidal");
	static final ResourceLocation t2torus = getRsc("t2torus");

	@Override
	public void render(TokamakT2TE te,double x,double y,double z,float partialTicks,int destroyStage,float alpha) {
		LeafiaGls.pushMatrix();

		LeafiaGls.enableCull();
		LeafiaGls.enableLighting();

		LeafiaGls.translate(x+0.5,y,z+0.5);
		bindTexture(microwave);
		mdl.renderPart("Frame");
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);
		mdl.renderPart("Injectors");
		bindTexture(t2torus);
		mdl.renderPart("Torus");
		LeafiaGls.shadeModel(GL11.GL_FLAT);
		bindTexture(t2toroidal);
		mdl.renderPart("Toroidal");
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);
		bindTexture(t2rails);
		mdl.renderPart("Rails");

		LeafiaGls.pushMatrix(); // rotation
		bindTexture(t2solenoid);
		mdl.renderPart("Solenoid");
		LeafiaGls.popMatrix();

		bindTexture(t2glass);
		mdl.renderPart("Windows");
		bindTexture(plasma);
		LeafiaGls.enableBlend();
		LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA,DestFactor.ONE);
		mdl.renderPart("Plasma");
		LeafiaGls.disableBlend();
		LeafiaGls.shadeModel(GL11.GL_FLAT);
		LeafiaGls.popMatrix();
	}
}
