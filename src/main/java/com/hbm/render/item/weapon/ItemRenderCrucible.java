package com.hbm.render.item.weapon;

import java.nio.DoubleBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.animloader.AnimatedModel.IAnimatedModelCallback;
import com.hbm.animloader.AnimationWrapper;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.HbmShaderManager2;
import com.hbm.items.weapon.ItemCrucible;
import com.hbm.items.weapon.ItemSwordCutter;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.main.ResourceManager;
import com.hbm.particle.ParticleCrucibleSpark;
import com.hbm.particle.ParticleFirstPerson;
import com.hbm.particle.ParticleFirstPerson.ParticleType;
import com.hbm.particle.lightning_test.TrailRenderer2;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.item.TEISRBase;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;

public class ItemRenderCrucible extends TEISRBase {

	private static DoubleBuffer buf = null;
	public static float lastX = 0;
	public static float lastY = 0;
	public static Vec3d playerPos;
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		if(buf == null){
			buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		}
		boolean depleted = ItemCrucible.getCharges(itemStackIn) == 0;
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			EnumHand hand = type == TransformType.FIRST_PERSON_RIGHT_HAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
			GL11.glScaled(5, 5, 5);
			GL11.glTranslated(0.2, -1.5, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-20, 1, 0, 0);
			GL11.glRotated(5, 0, 0, 1);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.turbofan_blades_tex);
			
			Animation anim = HbmAnimations.getRelevantAnim(hand);
			if(ItemSwordCutter.startPos != null && anim != null && (ItemSwordCutter.clicked || anim.animation != null)){
				double[] swing_rot = HbmAnimations.getRelevantTransformation("SWING", hand);
				EntityPlayer p = Minecraft.getMinecraft().player;
				Vec3d v = ItemSwordCutter.startPos.rotateYaw((float) Math.toRadians(p.rotationYaw+180)).rotatePitch((float) Math.toRadians(-p.rotationPitch));
				double angle = Math.toDegrees(Math.atan2(v.y, v.x))-80;
				float oX = 0.4F;
				float oY = -1.55F;
				float oZ = 0;
				boolean flag = false;
				if(anim.animation != null){
					angle = ItemSwordCutter.prevAngle;
					long time = System.currentTimeMillis() - anim.startMillis;
					if(anim.animation.getDuration()-time < 400){
						flag = true;
					}
				} else {
					ItemSwordCutter.prevAngle = angle;
				}
				if(!flag){
					GL11.glTranslated(0.3F, -0.1F, 0);
					GL11.glTranslated(-oX, -oY, -oZ);
					GL11.glRotated(-angle, 0, 0, 1);
					GL11.glTranslated(oX, oY, oZ);
					GL11.glTranslated(0F, -0.2F, 0F);
					GL11.glRotated(10, 0, 1, 0);
				}
				GL11.glRotated(swing_rot[0], 1, 0, 0);
			}
			
			AnimationWrapper w = HbmAnimations.getRelevantBlenderAnim(hand);
			if(w == AnimationWrapper.EMPTY){
				GlStateManager.shadeModel(GL11.GL_FLAT);
				return;
			}
			if(depleted){
				GL11.glTranslated(-0.1, -0.25, 0.1);
				w.startTime = System.currentTimeMillis()-400;
			}
			double[] sRot = HbmAnimations.getRelevantTransformation("SWING_ROT", hand);
			double[] sTrans = HbmAnimations.getRelevantTransformation("SWING_TRANS", hand);
			GL11.glTranslated(sTrans[0], sTrans[1], sTrans[2]);
			GL11.glRotated(sRot[0], 1, 0, 0);
			GL11.glRotated(sRot[2], 0, 0, 1);
			GL11.glRotated(sRot[1], 0, 1, 0);
			
			ResourceManager.crucible_anim.controller.setAnim(w);
			
			ResourceManager.crucible_anim.renderAnimated(System.currentTimeMillis(), new IAnimatedModelCallback() {

				@Override
				public boolean onRender(int prevFrame, int currentFrame, int model, float diffN, String name) {
						if(name.startsWith("Guard")){
							Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.crucible_guard);
						} else if(name.equals("Hilt")){
							Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.crucible_hilt);
						} else if(name.equals("Blade")){
							int[] particleFrames = {13, 14, 15, 16, 17, 18, 19, 20};
							if(currentFrame <= 20)
								for(int f : particleFrames){
									if(currentFrame >= f && prevFrame < f){
										for(int i = 0; i < 50; i ++)
											ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleCrucibleSpark(world, 2, 0.0025F, 0, (world.rand.nextFloat()-0.5F)*0.2F, 0.6F-world.rand.nextFloat()*0.75F, 0, 0, -0.01F*world.rand.nextFloat()).lifetime(6+(int)world.rand.nextGaussian()*10));
									}
								}
							
							//PARTICLES//
							Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.crucible_spark);
							GlStateManager.disableAlpha();
							GlStateManager.depthMask(false);
							GlStateManager.enableBlend();
							GlStateManager.disableCull();
							GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
							
							ItemRenderCrucible.playerPos = BobMathUtil.viewToLocal(new Vector4f(0, 0, 0, 1))[0];
							GlStateManager.color(1, 0.5F, 0.5F, 1);
							TrailRenderer2.color[1] = 0.5F;
							TrailRenderer2.color[2] = 0.5F;
							for(ParticleFirstPerson p : ModEventHandlerClient.firstPersonAuxParticles){
								if(p.getType() == ParticleType.CRUCIBLE)
									p.renderParticle(Tessellator.getInstance().getBuffer(), entity, MainRegistry.proxy.partialTicks(), 0, 0, 0, 0, 0);
							}
							GlStateManager.color(1, 0.2F, 0.2F, 1);
							TrailRenderer2.color[1] = 0.2F;
							TrailRenderer2.color[2] = 0.2F;
							TrailRenderer2.color[3] = 0.5F;
							if(GeneralConfig.bloom){
								HbmShaderManager2.bloomData.bindFramebuffer(true);
								for(ParticleFirstPerson p : ModEventHandlerClient.firstPersonAuxParticles){
									if(p.getType() == ParticleType.CRUCIBLE)
										p.renderParticle(Tessellator.getInstance().getBuffer(), entity, MainRegistry.proxy.partialTicks(), 0, 0, 0, 0, 0);
								}
								
								Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
							}
							
							TrailRenderer2.color[1] = 1F;
							TrailRenderer2.color[2] = 1F;
							TrailRenderer2.color[3] = 1F;
							
							GlStateManager.enableCull();
						    GlStateManager.enableAlpha();
						    GlStateManager.depthMask(true);
						    GlStateManager.disableBlend();
							//END PARTICLES//
							
							GL11.glEnable(GL11.GL_CLIP_PLANE0);
							buf.put(0);
							buf.put(0);
							buf.put(1);
							buf.put(BobMathUtil.remap(diffN, 0.6F, 0.7F, -0.7F, 0.7F));
							buf.rewind();
							GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
							GlStateManager.enableBlend();
							GlStateManager.disableLighting();
							lastX = OpenGlHelper.lastBrightnessX;
							lastY = OpenGlHelper.lastBrightnessY;
							OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
							GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
							GlStateManager.color(1, 1, 1, 0.7F);
							Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.crucible_blade);
						} else {
							Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
						}
						return false;
				}
				
				@Override
				public void postRender(int prevFrame, int currentFrame, int model, float diffN, String modelName) {
					if(modelName.equals("Blade")){
						GlStateManager.color(1F, 1F, 1F, 1);
						GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
						Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.crucible_blade_bloom);
						if(GeneralConfig.bloom){
							HbmShaderManager2.bloomData.bindFramebuffer(true);
							GL11.glCallList(model);
							Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
						}
						GlStateManager.disableBlend();
						if(GeneralConfig.heatDistortion && diffN > 0.6){
							GL11.glScaled(1.15, 1.15, 1.05);
							GlStateManager.depthMask(false);
							HbmShaderManager2.distort(0.5F, () -> {
								GL11.glCallList(model);
							});
							GlStateManager.depthMask(true);
						}
						GL11.glDisable(GL11.GL_CLIP_PLANE0);
						
						OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastX, lastY);
						GlStateManager.color(1, 1, 1, 1);
					}
				}
			});
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.5, -0.3, 0.5);
			GL11.glScaled(0.4, 0.4, 0.4);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			ResourceManager.crucible.renderPart("GuardLeft");
			ResourceManager.crucible.renderPart("GuardRight");

			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			GL11.glDisable(GL11.GL_LIGHTING);
			GlStateManager.disableCull();
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(0.005, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
			ResourceManager.crucible.renderPart("Blade");
			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glPopAttrib();
			GL11.glPopMatrix();
			break;
		case GUI:
			GL11.glTranslated(0.15, 0.15, 0);
			GL11.glRotated(-135+90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			double scale = 0.09D;
			GL11.glScaled(scale, scale, scale);

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			ResourceManager.crucible.renderPart("GuardLeft");
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glTranslated(0.005, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
			ResourceManager.crucible.renderPart("Blade");
			break;
		case NONE:
			break;
		}
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
}
