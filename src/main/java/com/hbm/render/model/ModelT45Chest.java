package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;

public class ModelT45Chest extends ModelBiped {

	// fields
		ModelRenderer chest;
		ModelRenderer leftarm;
		ModelRenderer rightarm;
		ModelRenderer Shape1;
		ModelRenderer Shape2;
		ModelRenderer Shape3;
		ModelRenderer Shape4;
		ModelRenderer Shape5;
		ModelRenderer Shape6;
		ModelRenderer Shape7;
		ModelRenderer Shape8;
		ModelRenderer Shape9;
		ModelRenderer Shape10;
		ModelRenderer Shape11;
		ModelRenderer Shape12;
		ModelRenderer Shape13;
		ModelRenderer Shape14;
		ModelRenderer Shape15;
		ModelRenderer Shape16;
		ModelRenderer Shape17;
		ModelRenderer Shape18;
		ModelRenderer Shape19;

		public ModelT45Chest() {
			textureWidth = 128;
			textureHeight = 64;

			chest = new ModelRenderer(this, 0, 0);
			leftarm = new ModelRenderer(this, 0, 0);
			rightarm = new ModelRenderer(this, 0, 0);
			Shape1 = new ModelRenderer(this, 0, 0);
			Shape1.addBox(0F, 0F, 0F, 8, 12, 4);
			Shape1.setRotationPoint(-4F, 0F - 0.0625F / 2, -2F);
			Shape1.setTextureSize(128, 64);
			Shape1.mirror = true;
			setRotation(Shape1, 0F, 0F, 0F);
			convertToChild(chest, Shape1);
			Shape2 = new ModelRenderer(this, 0, 16);
			Shape2.addBox(0F, 0F, 0F, 7, 5, 2);
			Shape2.setRotationPoint(-3.5F, 2F - 0.0625F / 2, -3.5F);
			Shape2.setTextureSize(128, 64);
			Shape2.mirror = true;
			setRotation(Shape2, 0F, 0F, 0F);
			convertToChild(chest, Shape2);
			Shape3 = new ModelRenderer(this, 0, 23);
			Shape3.addBox(0F, 0F, 0F, 1, 1, 1);
			Shape3.setRotationPoint(-2.5F, 7F - 0.0625F / 2, -3F);
			Shape3.setTextureSize(128, 64);
			Shape3.mirror = true;
			setRotation(Shape3, 0F, 0F, 0F);
			convertToChild(chest, Shape3);
			Shape4 = new ModelRenderer(this, 0, 25);
			Shape4.addBox(0F, 0F, 0F, 1, 1, 1);
			Shape4.setRotationPoint(1.5F, 7F - 0.0625F / 2, -3F);
			Shape4.setTextureSize(128, 64);
			Shape4.mirror = true;
			setRotation(Shape4, 0F, 0F, 0F);
			convertToChild(chest, Shape4);
			Shape5 = new ModelRenderer(this, 0, 28);
			Shape5.addBox(0F, -2F, 0F, 7, 2, 2);
			Shape5.setRotationPoint(-3.5F, 2F - 0.0625F / 2, -3.5F);
			Shape5.setTextureSize(128, 64);
			Shape5.mirror = true;
			setRotation(Shape5, -0.6108652F, 0F, 0F);
			convertToChild(chest, Shape5);
			Shape6 = new ModelRenderer(this, 48, 0);
			Shape6.addBox(0F, 0F, 0F, 4, 12, 4);
			Shape6.setRotationPoint(4F - 6 + 0.25F, 0F - 3, -2F);
			Shape6.setTextureSize(128, 64);
			Shape6.mirror = true;
			setRotation(Shape6, 0F, 0F, 0F);
			convertToChild(leftarm, Shape6);
			Shape7 = new ModelRenderer(this, 32, 0);
			Shape7.addBox(0F, 0F, 0F, 4, 12, 4);
			Shape7.setRotationPoint(-8F + 6 - 0.25F, 0F - 3, -2F);
			Shape7.setTextureSize(128, 64);
			Shape7.mirror = true;
			setRotation(Shape7, 0F, 0F, 0F);
			convertToChild(rightarm, Shape7);
			Shape8 = new ModelRenderer(this, 32, 16);
			Shape8.addBox(0F, 0F, 0F, 5, 6, 6);
			Shape8.setRotationPoint(4F - 6 + 0.25F, 4F - 3, -3F);
			Shape8.setTextureSize(128, 64);
			Shape8.mirror = true;
			setRotation(Shape8, 0F, 0F, 0F);
			convertToChild(leftarm, Shape8);
			Shape9 = new ModelRenderer(this, 0, 34);
			Shape9.addBox(0F, 0F, 0F, 5, 6, 6);
			Shape9.setRotationPoint(-9F + 6 - 0.25F, 4F - 3, -3F);
			Shape9.setTextureSize(128, 64);
			Shape9.mirror = true;
			setRotation(Shape9, 0F, 0F, 0F);
			convertToChild(rightarm, Shape9);
			Shape10 = new ModelRenderer(this, 32, 30);
			Shape10.addBox(0F, 0F, 0F, 2, 6, 2);
			Shape10.setRotationPoint(1F, 4F - 0.0625F / 2, 2F);
			Shape10.setTextureSize(128, 64);
			Shape10.mirror = true;
			setRotation(Shape10, 0F, 0F, 0F);
			convertToChild(chest, Shape10);
			Shape11 = new ModelRenderer(this, 42, 30);
			Shape11.addBox(0F, 0F, 0F, 2, 6, 2);
			Shape11.setRotationPoint(-3F, 4F - 0.0625F / 2, 2F);
			Shape11.setTextureSize(128, 64);
			Shape11.mirror = true;
			setRotation(Shape11, 0F, 0F, 0F);
			convertToChild(chest, Shape11);
			Shape12 = new ModelRenderer(this, 26, 9);
			Shape12.addBox(0F, 0F, 0F, 1, 6, 1);
			Shape12.setRotationPoint(1.5F, -2F - 0.0625F / 2, 2F);
			Shape12.setTextureSize(128, 64);
			Shape12.mirror = true;
			setRotation(Shape12, 0F, 0F, 0F);
			convertToChild(chest, Shape12);
			Shape13 = new ModelRenderer(this, 26, 0);
			Shape13.addBox(0F, 0F, 0F, 1, 6, 1);
			Shape13.setRotationPoint(-2.5F, -2F - 0.0625F / 2, 2F);
			Shape13.setTextureSize(128, 64);
			Shape13.mirror = true;
			setRotation(Shape13, 0F, 0F, 0F);
			convertToChild(chest, Shape13);
			Shape14 = new ModelRenderer(this, 20, 18);
			Shape14.addBox(0F, 0F, 0F, 2, 2, 1);
			Shape14.setRotationPoint(-1F, 1F - 0.0625F / 2, 2F);
			Shape14.setTextureSize(128, 64);
			Shape14.mirror = true;
			setRotation(Shape14, 0F, 0F, 0F);
			convertToChild(chest, Shape14);
			Shape15 = new ModelRenderer(this, 21, 23);
			Shape15.addBox(-1.5F, -1.5F, 0F, 3, 3, 1);
			Shape15.setRotationPoint(0F, 2F - 0.0625F / 2, 3F);
			Shape15.setTextureSize(128, 64);
			Shape15.mirror = true;
			setRotation(Shape15, 0F, 0F, 0.7853982F);
			convertToChild(chest, Shape15);
			Shape16 = new ModelRenderer(this, 0, 48);
			Shape16.addBox(0F, -1F, 0F, 3, 1, 4);
			Shape16.setRotationPoint(-8F + 6 - 0.25F, 12F - 3, -2F);
			Shape16.setTextureSize(128, 64);
			Shape16.mirror = true;
			setRotation(Shape16, 0F, 0F, 0.5235988F);
			convertToChild(rightarm, Shape16);
			Shape17 = new ModelRenderer(this, 0, 55);
			Shape17.addBox(-3F, -1F, 0F, 3, 1, 4);
			Shape17.setRotationPoint(8F - 6 + 0.25F, 12F - 3, -2F);
			Shape17.setTextureSize(128, 64);
			Shape17.mirror = true;
			setRotation(Shape17, 0F, 0F, -0.5235988F);
			convertToChild(leftarm, Shape17);
			Shape18 = new ModelRenderer(this, 90, 0);
			Shape18.addBox(0F, -3F, 0F, 5, 3, 6);
			Shape18.setRotationPoint(4F - 6 + 0.25F, 0F - 3, -3F);
			Shape18.setTextureSize(128, 64);
			Shape18.mirror = true;
			setRotation(Shape18, 0F, 0F, 0.2617994F);
			convertToChild(leftarm, Shape18);
			Shape19 = new ModelRenderer(this, 66, 0);
			Shape19.addBox(-5F, -3F, 0F, 5, 3, 6);
			Shape19.setRotationPoint(-4F + 6 - 0.25F, 0F - 3, -3F);
			Shape19.setTextureSize(128, 64);
			Shape19.mirror = true;
			setRotation(Shape19, 0F, 0F, -0.2617994F);
			convertToChild(rightarm, Shape19);
		}

		private void setRotation(ModelRenderer model, float x, float y, float z) {
			model.rotateAngleX = x;
			model.rotateAngleY = y;
			model.rotateAngleZ = z;
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
			this.chest.rotationPointX = this.bipedBody.rotationPointX;
			this.chest.rotationPointY = this.bipedBody.rotationPointY;
			this.chest.rotationPointZ = this.bipedBody.rotationPointZ;
			this.chest.rotateAngleX = this.bipedBody.rotateAngleX;
			this.chest.rotateAngleY = this.bipedBody.rotateAngleY;
			this.chest.rotateAngleZ = this.bipedBody.rotateAngleZ;
			this.leftarm.rotationPointX = this.bipedLeftArm.rotationPointX;
			this.leftarm.rotationPointY = this.bipedLeftArm.rotationPointY;
			this.leftarm.rotationPointZ = this.bipedLeftArm.rotationPointZ;
			this.leftarm.rotateAngleX = this.bipedLeftArm.rotateAngleX;
			this.leftarm.rotateAngleY = this.bipedLeftArm.rotateAngleY;
			this.leftarm.rotateAngleZ = this.bipedLeftArm.rotateAngleZ;
			this.rightarm.rotationPointX = this.bipedRightArm.rotationPointX;
			this.rightarm.rotationPointY = this.bipedRightArm.rotationPointY;
			this.rightarm.rotationPointZ = this.bipedRightArm.rotationPointZ;
			this.rightarm.rotateAngleX = this.bipedRightArm.rotateAngleX;
			this.rightarm.rotateAngleY = this.bipedRightArm.rotateAngleY;
			this.rightarm.rotateAngleZ = this.bipedRightArm.rotateAngleZ;
			
			if(entity instanceof EntityZombie) {
	            boolean armsRaised = false;
	            if(entity instanceof EntityZombie)
	                armsRaised = ((EntityZombie)entity).isArmsRaised();

	            this.leftarm.rotateAngleY = (float)(8 * Math.PI / 180D);
	            this.rightarm.rotateAngleY = -(float)(8 * Math.PI / 180D);
	            if(armsRaised){
	                this.leftarm.rotateAngleX = -(float)(120 * Math.PI / 180D);
	                this.rightarm.rotateAngleX = -(float)(120 * Math.PI / 180D);
	            } else {
	                this.leftarm.rotateAngleX = -(float)(80 * Math.PI / 180D);
	                this.rightarm.rotateAngleX = -(float)(80 * Math.PI / 180D);
	            }
	        }

	        if (this.isSneak) {
	            this.chest.offsetY = 0.25F;
	            this.rightarm.offsetY = 0.25F;
	            this.leftarm.offsetY = 0.25F;

	        } else {
	            this.chest.offsetY = 0F;
	            this.rightarm.offsetY = 0F;
	            this.leftarm.offsetY = 0F;
	        }
		}


		@Override
		public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
			setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
			GL11.glPushMatrix();
			GL11.glScalef(1.13F, 1.13F, 1.13F);
			if(this.isChild) {
				GL11.glScalef(0.75F, 0.75F, 0.75F);
				GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
				GL11.glScalef(0.75F, 0.75F, 0.75F);
			}
			this.chest.render(par7);
			GL11.glPopMatrix();
			this.renderLeft(par1Entity, par2, par3, par4, par5, par6, par7);
			this.renderRight(par1Entity, par2, par3, par4, par5, par6, par7);
		}

		public void renderLeft(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
			setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
			GL11.glPushMatrix();
			GL11.glScalef(1.13F, 1.13F, 1.13F);
			this.leftarm.render(par7);
			GL11.glPopMatrix();
		}

		public void renderRight(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
			setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
			GL11.glPushMatrix();
			GL11.glScalef(1.13F, 1.13F, 1.13F);
			this.rightarm.render(par7);
			GL11.glPopMatrix();
		}

		// Jabelar, you saved my time! I was about to redo all this crap if you can
		// simply use this method!!
		protected void convertToChild(ModelRenderer parParent, ModelRenderer parChild) {
			// move child rotation point to be relative to parent
			parChild.rotationPointX -= parParent.rotationPointX;
			parChild.rotationPointY -= parParent.rotationPointY;
			parChild.rotationPointZ -= parParent.rotationPointZ;
			// make rotations relative to parent
			parChild.rotateAngleX -= parParent.rotateAngleX;
			parChild.rotateAngleY -= parParent.rotateAngleY;
			parChild.rotateAngleZ -= parParent.rotateAngleZ;
			// create relationship
			parParent.addChild(parChild);
		}
}
