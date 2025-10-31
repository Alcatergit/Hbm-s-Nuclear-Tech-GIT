package com.hbm.render.model;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class ModelArmorRPA extends ModelArmorBase {

    public ModelArmorRPA(int type) {
        super(type);

        head = new ModelRendererObj(ResourceManager.armor_RPA, "Head");
        body = new ModelRendererObj(ResourceManager.armor_RPA, "Body");
        leftArm = new ModelRendererObj(ResourceManager.armor_RPA, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
        rightArm = new ModelRendererObj(ResourceManager.armor_RPA, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
        leftLeg = new ModelRendererObj(ResourceManager.armor_RPA, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg = new ModelRendererObj(ResourceManager.armor_RPA, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftFoot = new ModelRendererObj(ResourceManager.armor_RPA, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
        rightFoot = new ModelRendererObj(ResourceManager.armor_RPA, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
    }

    @Override
    public void renderArmor(Entity par1Entity, float par7) {
        switch (type) {
            case 3 -> {
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_helmet);
                head.render(par7 * 1.05F);
            }
            case 2 -> {
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_chest);
                body.render(par7 * 1.05F);
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_arm);
                leftArm.render(par7 * 1.05F);
                rightArm.render(par7 * 1.05F);
            }
            case 1 -> {
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
                leftLeg.render(par7 * 1.05F);
                rightLeg.render(par7 * 1.05F);
            }
            case 0 -> {
                Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
                leftFoot.render(par7 * 1.05F);
                rightFoot.render(par7 * 1.05F);
            }
        }
    }
}