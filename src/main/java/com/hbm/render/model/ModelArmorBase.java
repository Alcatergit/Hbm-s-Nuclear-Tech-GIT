package com.hbm.render.model;

import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
	}

	@Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

    	boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
        head.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag)
        {
            head.rotateAngleX = -((float)Math.PI / 4F);
        }
        else
        {
            head.rotateAngleX = headPitch * 0.017453292F;
        }

        body.rotateAngleY = 0.0F;
        rightArm.rotationPointZ = 0.0F;
        rightArm.rotationPointX = -5.0F;
        leftArm.rotationPointZ = 0.0F;
        leftArm.rotationPointX = 5.0F;
        float f = 1.0F;

        if (flag)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }

        rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;
        rightFoot.rotateAngleX = rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
        leftFoot.rotateAngleX = leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount / f;
        rightFoot.rotateAngleY = rightLeg.rotateAngleY = 0.0F;
        leftFoot.rotateAngleY = leftLeg.rotateAngleY = 0.0F;
        rightFoot.rotateAngleZ = rightLeg.rotateAngleZ = 0.0F;
        leftFoot.rotateAngleZ = leftLeg.rotateAngleZ = 0.0F;

        if (this.isRiding)
        {
            rightArm.rotateAngleX += -((float)Math.PI / 5F);
            leftArm.rotateAngleX += -((float)Math.PI / 5F);
            rightFoot.rotateAngleX = rightLeg.rotateAngleX = -1.4137167F;
            rightFoot.rotateAngleY = rightLeg.rotateAngleY = ((float)Math.PI / 10F);
            rightFoot.rotateAngleZ = rightLeg.rotateAngleZ = 0.07853982F;
            leftFoot.rotateAngleX = leftLeg.rotateAngleX = -1.4137167F;
            leftFoot.rotateAngleY = leftLeg.rotateAngleY = -((float)Math.PI / 10F);
            leftFoot.rotateAngleZ = leftLeg.rotateAngleZ = -0.07853982F;
        }

        rightArm.rotateAngleY = 0.0F;
        rightArm.rotateAngleZ = 0.0F;

        switch (this.leftArmPose)
        {
            case EMPTY:
                leftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - 0.9424779F;
                leftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                leftArm.rotateAngleY = 0.0F;
        }

        switch (this.rightArmPose)
        {
            case EMPTY:
                rightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - 0.9424779F;
                rightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                rightArm.rotateAngleY = 0.0F;
        }

        if (this.swingProgress > 0.0F)
        {
            EnumHandSide enumhandside = this.getMainHand(entityIn);
            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
            float f1 = this.swingProgress;
            body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

            if (enumhandside == EnumHandSide.LEFT)
            {
                body.rotateAngleY *= -1.0F;
            }

            rightArm.rotationPointZ = MathHelper.sin(body.rotateAngleY) * 5.0F;
            rightArm.rotationPointX = -MathHelper.cos(body.rotateAngleY) * 5.0F;
            leftArm.rotationPointZ = -MathHelper.sin(body.rotateAngleY) * 5.0F;
            leftArm.rotationPointX = MathHelper.cos(body.rotateAngleY) * 5.0F;
            rightArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleY += body.rotateAngleY;
            leftArm.rotateAngleX += body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float)Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
            modelrenderer.rotateAngleY += body.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
        	body.rotateAngleX = 0.5F;
            rightArm.rotateAngleX += 0.4F;
            leftArm.rotateAngleX += 0.4F;
            rightFoot.offsetZ = rightLeg.offsetZ = 4.0F;
            leftFoot.offsetZ = leftLeg.offsetZ = 4.0F;
            rightFoot.offsetY = rightLeg.offsetY = -3.0F;
            leftFoot.offsetY = leftLeg.offsetY = -3.0F;
            head.offsetY = 1.0F;
        }
        else
        {
            body.rotateAngleX = 0.0F;
            rightFoot.offsetZ = rightLeg.offsetZ = 0.1F;
            leftFoot.offsetZ = leftLeg.offsetZ = 0.1F;
            rightFoot.offsetY = rightLeg.offsetY = 0.0F;
            leftFoot.offsetY = leftLeg.offsetY = 0.0F;
            head.offsetY = 0.0F;
        }

        rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            rightArm.rotateAngleY = -0.1F + head.rotateAngleY;
            leftArm.rotateAngleY = 0.1F + head.rotateAngleY + 0.4F;
            rightArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
            leftArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
        }
        else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            rightArm.rotateAngleY = -0.1F + head.rotateAngleY - 0.4F;
            leftArm.rotateAngleY = 0.1F + head.rotateAngleY;
            rightArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
            leftArm.rotateAngleX = -((float)Math.PI / 2F) + head.rotateAngleX;
        }
    }
}