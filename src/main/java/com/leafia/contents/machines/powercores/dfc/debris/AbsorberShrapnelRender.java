package com.leafia.contents.machines.powercores.dfc.debris;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.contents.machines.powercores.dfc.debris.AbsorberShrapnelEntity.DebrisType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class AbsorberShrapnelRender extends Render<AbsorberShrapnelEntity> {

	public static final IRenderFactory<AbsorberShrapnelEntity> FACTORY = man -> new AbsorberShrapnelRender(man);

	static final IModelCustom core = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID, "models/leafia/dfc_receiver_shrapnels/core.obj"));
	static final IModelCustom framebeam = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID, "models/leafia/dfc_receiver_shrapnels/framebeam.obj"));
	static final IModelCustom framecable = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID, "models/leafia/dfc_receiver_shrapnels/framecable.obj"));
	static final IModelCustom framecorner = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID, "models/leafia/dfc_receiver_shrapnels/framecorner.obj"));
	static final IModelCustom framefront = AdvancedModelLoader.loadModel(
			new ResourceLocation(RefStrings.MODID, "models/leafia/dfc_receiver_shrapnels/framefront.obj"));

	protected AbsorberShrapnelRender(RenderManager renderManager){
		super(renderManager);
	}

	@Override
	public void doRender(AbsorberShrapnelEntity entity,double x,double y,double z,float entityYaw,float partialTicks){
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.125D, z);

		AbsorberShrapnelEntity debris = (AbsorberShrapnelEntity)entity;

		GL11.glRotatef(debris.getEntityId() % 360, 0, 1, 0); //rotate based on entity ID to add unique randomness
		GL11.glRotatef(debris.lastRot + (debris.rot - debris.lastRot) * partialTicks, 1, 1, 1);
		
		DebrisType type = debris.getType();
		bindTexture(ResourceManager.dfc_receiver_tex);

		switch(type) {
			case CABLE: framecable.renderAll(); break;
			case CORE: GL11.glShadeModel(GL11.GL_SMOOTH); core.renderAll(); GL11.glShadeModel(GL11.GL_FLAT); break;
			case CORNER: framecorner.renderAll(); break;
			case FRONT: framefront.renderAll(); break;
			case BEAM: framebeam.renderAll(); break;
			default: break;
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(AbsorberShrapnelEntity entity){
		return ResourceManager.dfc_receiver_tex;
	}

}
