package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelBackTesla extends ModelArmorBase {

	public ModelBackTesla() {
		super(1);
		body = new ModelRendererObj(ResourceManager.armor_mod_tesla);
	}

	@Override
	public void setRotationAngles(float walkCycle, float walkAmplitude, float idleCycle, float headYaw, float headPitch, float scale,
								  Entity entity) {
		super.setRotationAngles(walkCycle, walkAmplitude, idleCycle, headYaw, headPitch, scale, entity);
		this.body.rotateAngleY += (float) Math.toRadians(headYaw);
	}

	@Override
	public void renderArmor(Entity par1Entity, float par7) {
		if(type == 1){
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mod_tesla);
			body.render(par7);
		}
	}
}