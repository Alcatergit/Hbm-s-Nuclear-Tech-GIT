package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.render.item.ItemRenderFrames17;
import com.hbm.render.item.TEISRBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public abstract class ItemRenderWeaponBase extends TEISRBase {

	@Override
	public void renderByItem(ItemStack stack) {
		switch(type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			renderFirstPersonPipeline(stack, type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
			break;
		case THIRD_PERSON_RIGHT_HAND:
			GlStateManager.multMatrix(ItemRenderFrames17.THIRD_PERSON);
			setupThirdPerson(stack);
			renderOther(stack);
			break;
		case THIRD_PERSON_LEFT_HAND:
			GlStateManager.multMatrix(ItemRenderFrames17.THIRD_PERSON);
			setupThirdPerson(stack);
			GlStateManager.scale(-1, 1, 1);
			GL11.glFrontFace(GL11.GL_CW);
			renderOther(stack);
			GL11.glFrontFace(GL11.GL_CCW);
			break;
		case HEAD:
			GlStateManager.multMatrix(ItemRenderFrames17.HEAD);
			setupThirdPerson(stack);
			renderOther(stack);
			break;
		case GROUND:
			GlStateManager.multMatrix(ItemRenderFrames17.GROUND);
			setupEntity(stack);
			renderOther(stack);
			break;
		case FIXED:
			GlStateManager.multMatrix(ItemRenderFrames17.FIXED);
			setupEntity(stack);
			renderOther(stack);
			break;
		case GUI:
			GlStateManager.multMatrix(ItemRenderFrames17.GUI);
			GlStateManager.disableCull();
			setupInv(stack);
			renderOther(stack);
			GlStateManager.enableCull();
			break;
		default:
			break;
		}
	}

	protected void renderFirstPersonPipeline(ItemStack stack, EnumHand hand) {
		Minecraft mc = Minecraft.getMinecraft();
		float interp = mc.getRenderPartialTicks();
		float farPlaneDistance = mc.gameSettings.renderDistanceChunks * 16;

		GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);

		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		Project.gluPerspective(70.0F, (float) mc.displayWidth / (float) mc.displayHeight, 0.05F, farPlaneDistance * 2.0F);
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();

		if(mc.gameSettings.thirdPersonView == 0 && !mc.gameSettings.hideGUI) {
			setupTransformsAndRender(stack, hand, interp);
		}

		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_PROJECTION);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);
	}

	protected void setupTransformsAndRender(ItemStack stack, EnumHand hand, float interp) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.player;
		float swayMagnitude = getSwayMagnitude(stack, hand);
		float swayPeriod = 0.75F;
		float turnMagnitude = getTurnMagnitude(stack, hand);

		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * interp;
		float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * interp;
		GlStateManager.pushMatrix();
		GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
		GlStateManager.popMatrix();

		float armPitch = player.prevRenderArmPitch + (player.renderArmPitch - player.prevRenderArmPitch) * interp;
		float armYaw = player.prevRenderArmYaw + (player.renderArmYaw - player.prevRenderArmYaw) * interp;
		GlStateManager.rotate((player.rotationPitch - armPitch) * 0.1F * turnMagnitude, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate((player.rotationYaw - armYaw) * 0.1F * turnMagnitude, 0.0F, 1.0F, 0.0F);

		int brightness = mc.world.getCombinedLight(new BlockPos(player.posX, player.posY + player.getEyeHeight(), player.posZ), 0);
		int j = brightness % 65536;
		int k = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		int color = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, 0);
		float r = (float) (color >> 16 & 255) / 255.0F;
		float g = (float) (color >> 8 & 255) / 255.0F;
		float b = (float) (color & 255) / 255.0F;
		GlStateManager.color(r, g, b, 1.0F);

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(180, 0, 1, 0);

		if(mc.getRenderViewEntity() instanceof EntityPlayer entityplayer) {
			float distanceDelta = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
			float distanceInterp = -(entityplayer.distanceWalkedModified + distanceDelta * interp);
			float camYaw = entityplayer.prevCameraYaw + (entityplayer.cameraYaw - entityplayer.prevCameraYaw) * interp;
			float camPitch = entityplayer.prevCameraPitch + (entityplayer.cameraPitch - entityplayer.prevCameraPitch) * interp;
			GlStateManager.translate(
					MathHelper.sin(distanceInterp * (float) Math.PI * swayPeriod) * camYaw * 0.5F * swayMagnitude,
					-Math.abs(MathHelper.cos(distanceInterp * (float) Math.PI * swayPeriod) * camYaw) * swayMagnitude,
					0.0F);
			GlStateManager.rotate(MathHelper.sin(distanceInterp * (float) Math.PI * swayPeriod) * camYaw * 3.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(Math.abs(MathHelper.cos(distanceInterp * (float) Math.PI * swayPeriod - 0.2F) * camYaw) * 5.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(camPitch, 1.0F, 0.0F, 0.0F);
		}

		if(hand == EnumHand.OFF_HAND) {
			GlStateManager.scale(-1, 1, 1);
			GL11.glFrontFace(GL11.GL_CW);
		}
		setupFirstPerson(stack, hand, interp);
		renderFirstPerson(stack, hand);
		if(hand == EnumHand.OFF_HAND) {
			GL11.glFrontFace(GL11.GL_CCW);
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
	}

	protected float getSwayMagnitude(ItemStack stack, EnumHand hand) {
		return ItemGunBase.getIsAiming(hand) ? 0.1F : 0.5F;
	}

	protected float getTurnMagnitude(ItemStack stack, EnumHand hand) {
		return 2.75F;
	}

	protected void setupFirstPerson(ItemStack stack, EnumHand hand, float interp) {
		GlStateManager.translate(0, 0, 1);
		if(Minecraft.getMinecraft().player.isSneaking()) {
			GlStateManager.translate(0, -3.875 / 8D, 0);
		} else {
			float offset = 0.8F;
			GlStateManager.rotate(180, 0, 1, 0);
			GlStateManager.translate(offset, -0.75F * offset, -0.5F * offset);
			GlStateManager.rotate(180, 0, 1, 0);
		}
	}

	protected void setupThirdPerson(ItemStack stack) {
		double scale = 0.125D;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(12.5F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(15.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(3.5, 0, 0);
	}

	protected void setupEntity(ItemStack stack) {
		double scale = 0.125D;
		GlStateManager.scale(scale, scale, scale);
		GlStateManager.rotate(-90, 0, 1, 0);
	}

	protected void setupInv(ItemStack stack) {
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0F);
		GlStateManager.enableAlpha();
		GlStateManager.scale(1, 1, -1);
		GlStateManager.translate(8, 8, 0);
		GlStateManager.rotate(225, 0, 0, 1);
		GlStateManager.rotate(90, 0, 1, 0);
	}

	public float getViewFOV(float fov, EnumHand hand) {
		return fov;
	}

	protected static void standardAimingTransform(EnumHand hand, float interp, double sX, double sY, double sZ, double aX, double aY, double aZ) {
		double p = ItemGunBase.getPrevAimingProgress(hand) + (ItemGunBase.getAimingProgress(hand) - ItemGunBase.getPrevAimingProgress(hand)) * interp;
		GlStateManager.translate(sX + (aX - sX) * p, sY + (aY - sY) * p, sZ + (aZ - sZ) * p);
	}

	protected abstract void renderFirstPerson(ItemStack stack, EnumHand hand);

	protected abstract void renderOther(ItemStack stack);
}
