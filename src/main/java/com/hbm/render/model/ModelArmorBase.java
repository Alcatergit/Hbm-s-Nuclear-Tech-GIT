package com.hbm.render.model;

import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelArmorBase extends ModelBiped {

	int type;

	ModelRendererObj head;
	ModelRendererObj body;
	ModelRendererObj leftArm;
	ModelRendererObj rightArm;
	ModelRendererObj leftLeg;
	ModelRendererObj rightLeg;
	ModelRendererObj leftFoot;
	ModelRendererObj rightFoot;

	public ModelArmorBase(int type) {
		this.type = type;
		
		//generate null defaults to prevent major breakage from using incomplete models
		head = new ModelRendererObj(null);
		body = new ModelRendererObj(null);
		leftArm = new ModelRendererObj(null).setRotationPoint(-5.0F, 2.0F, 0.0F);
		rightArm = new ModelRendererObj(null).setRotationPoint(5.0F, 2.0F, 0.0F);
		leftLeg = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightLeg = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
		leftFoot = new ModelRendererObj(null).setRotationPoint(1.9F, 12.0F, 0.0F);
		rightFoot = new ModelRendererObj(null).setRotationPoint(-1.9F, 12.0F, 0.0F);
	}

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isSneaking()) {
                this.isSneak = true;
            } else {
                this.isSneak = false;
            }
        }

        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.head.rotationPointX = this.bipedHead.rotationPointX;
        this.head.rotationPointY = this.bipedHead.rotationPointY;
        this.head.rotateAngleY = this.bipedHead.rotateAngleY;
        this.head.rotateAngleX = this.bipedHead.rotateAngleX;

        this.body.rotationPointX = this.bipedBody.rotationPointX;
        this.body.rotationPointY = this.bipedBody.rotationPointY;
        this.body.rotationPointZ = this.bipedBody.rotationPointZ;
        this.body.rotateAngleX = this.bipedBody.rotateAngleX;
        this.body.rotateAngleY = this.bipedBody.rotateAngleY;
        this.body.rotateAngleZ = this.bipedBody.rotateAngleZ;

        this.leftArm.rotationPointX = this.bipedLeftArm.rotationPointX;
        this.leftArm.rotationPointY = this.bipedLeftArm.rotationPointY;
        this.leftArm.rotationPointZ = this.bipedLeftArm.rotationPointZ;
        this.leftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX;
        this.leftArm.rotateAngleY = this.bipedLeftArm.rotateAngleY;
        this.leftArm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;

        this.rightArm.rotationPointX = this.bipedRightArm.rotationPointX;
        this.rightArm.rotationPointY = this.bipedRightArm.rotationPointY;
        this.rightArm.rotationPointZ = this.bipedRightArm.rotationPointZ;
        this.rightArm.rotateAngleX = this.bipedRightArm.rotateAngleX;
        this.rightArm.rotateAngleY = this.bipedRightArm.rotateAngleY;
        this.rightArm.rotateAngleZ = this.bipedRightArm.rotateAngleZ;

        this.leftLeg.rotationPointX = this.bipedLeftLeg.rotationPointX;
        this.leftLeg.rotationPointY = this.bipedLeftLeg.rotationPointY - 1.5F;
        this.leftLeg.rotationPointZ = this.bipedLeftLeg.rotationPointZ;
        this.leftLeg.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
        this.leftLeg.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
        this.leftLeg.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;

        this.rightLeg.rotationPointX = this.bipedRightLeg.rotationPointX;
        this.rightLeg.rotationPointY = this.bipedRightLeg.rotationPointY - 1.5F;
        this.rightLeg.rotationPointZ = this.bipedRightLeg.rotationPointZ;
        this.rightLeg.rotateAngleX = this.bipedRightLeg.rotateAngleX;
        this.rightLeg.rotateAngleY = this.bipedRightLeg.rotateAngleY;
        this.rightLeg.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;

        this.leftFoot.rotationPointX = this.bipedLeftLeg.rotationPointX;
        this.leftFoot.rotationPointY = this.bipedLeftLeg.rotationPointY - 1.5F;
        this.leftFoot.rotationPointZ = this.bipedLeftLeg.rotationPointZ;
        this.leftFoot.rotateAngleX = this.bipedLeftLeg.rotateAngleX;
        this.leftFoot.rotateAngleY = this.bipedLeftLeg.rotateAngleY;
        this.leftFoot.rotateAngleZ = this.bipedLeftLeg.rotateAngleZ;

        this.rightFoot.rotationPointX = this.bipedRightLeg.rotationPointX;
        this.rightFoot.rotationPointY = this.bipedRightLeg.rotationPointY - 1.5F;
        this.rightFoot.rotationPointZ = this.bipedRightLeg.rotationPointZ;
        this.rightFoot.rotateAngleX = this.bipedRightLeg.rotateAngleX;
        this.rightFoot.rotateAngleY = this.bipedRightLeg.rotateAngleY;
        this.rightFoot.rotateAngleZ = this.bipedRightLeg.rotateAngleZ;
        
        if(entity instanceof EntityZombie || entity instanceof EntityPigZombie || entity instanceof EntitySkeleton) {
            this.leftArm.rotateAngleX -= (90 * Math.PI / 180D);
            this.rightArm.rotateAngleX -= (90 * Math.PI / 180D);
        }

        if (this.isSneak) {
            this.leftLeg.rotationPointZ -= 0.5F;
            this.rightLeg.rotationPointZ -= 0.5F;
            this.leftLeg.rotationPointY += 0.5F;
            this.rightLeg.rotationPointY += 0.5F;
        }
    }
}