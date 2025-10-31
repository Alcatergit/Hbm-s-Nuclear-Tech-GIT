package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelArmorAJR extends ModelArmorBase {

	public ModelArmorAJR(int type) {
		super(type);

		head = new ModelRendererObj(ResourceManager.armor_ajr, "Head");
		body = new ModelRendererObj(ResourceManager.armor_ajr, "Body");
		leftArm = new ModelRendererObj(ResourceManager.armor_ajr, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(ResourceManager.armor_ajr, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(ResourceManager.armor_ajr, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(ResourceManager.armor_ajr, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(ResourceManager.armor_ajr, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(ResourceManager.armor_ajr, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

	@Override
	public void renderArmor(Entity par1Entity, float scale) {
		switch (type) {
			case 0 -> {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ajr_helmet);
				head.render(scale * 1.001F);
			}
			case 1 -> {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ajr_chest);
				body.render(scale);
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ajr_arm);
				leftArm.render(scale);
				rightArm.render(scale);
			}
			case 2 -> {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ajr_leg);
				leftLeg.render(scale);
				rightLeg.render(scale);
			}
			case 3 -> {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ajr_leg);
				leftFoot.render(scale);
				rightFoot.render(scale);
			}
		}
	}
}