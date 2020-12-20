package com.hbm.handler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.particle.ParticleFakeBrightness;
import com.hbm.particle.ParticleHeatDistortion;
import com.hbm.particle.rocket.ParticleRocketPlasma;
import com.hbm.render.misc.ColorGradient;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackHandler {

	public static Method r_setSize;
	public static Field r_ticksElytraFlying;
	
	private static boolean jet_key_down = false;
	private static boolean hover_key_down = false;
	
	public static Map<EntityPlayer, JetpackInfo> perPlayerInfo = new WeakHashMap<>();
	
	public static boolean hasJetpack(EntityPlayer p){
		ItemStack chest = p.inventory.armorInventory.get(2);
		if(chest.getTagCompound() != null && chest.getTagCompound().hasKey("hbm_jetpack_power")){
			return true;
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public static void inputUpdate(InputUpdateEvent e){
		EntityPlayer player = e.getEntityPlayer();
		if(!hasJetpack(player))
			return;
		boolean jKey = ClientProxy.jetpackActivate.isKeyDown();
		if(jKey && !jet_key_down && System.currentTimeMillis()-getAnimTime(player) > 1000){
			toggleOpenState(player);
		}
		jet_key_down = jKey;
		boolean hKey = ClientProxy.jetpackHover.isKeyDown();
		if(hKey && !hover_key_down){
			toggleHoverState(player);
		}
		hover_key_down = hKey;
		if(jetpackActive(player) && !player.onGround){
			MovementInput m = e.getMovementInput();
			boolean sprint = Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
			if(player.isSprinting())
				player.setSprinting(sprint);
			if(isHovering(player)){
				m.moveForward *= player.isSprinting() ? 0.17 : 0.1;
				m.moveStrafe *= 0.1F;
				player.motionX -= MathHelper.sin((float) Math.toRadians(player.rotationYawHead)) * m.moveForward;
				player.motionZ += MathHelper.cos((float) Math.toRadians(player.rotationYawHead)) * m.moveForward;
				player.motionX -= MathHelper.sin((float) Math.toRadians(player.rotationYawHead-90)) * m.moveStrafe;
				player.motionZ += MathHelper.cos((float) Math.toRadians(player.rotationYawHead-90)) * m.moveStrafe;
				player.motionY *= 0.75;
				player.motionY += 0.05;
				float extraMY = 0;
				if(m.jump){
					m.jump = false;
					extraMY +=0.3;
				}
				if(m.sneak){
					m.sneak = false;
					extraMY -= 0.3;
				}
				player.motionY += extraMY;
				setThrust(player, m.moveForward+m.moveStrafe+extraMY);
				m.moveForward = sprint ? 0.81F : 0;
				m.moveStrafe = 0;
			} else {
				setThrust(player, m.jump ? 2 : 0);
				if(m.jump){
					Vec3d look = player.getLookVec();
					player.motionX += look.x;
					player.motionY += look.y;
					player.motionZ += look.z;
					m.jump = false;
				}
				if(m.sneak){
					m.sneak = false;
				}
				m.moveForward = 0;
				m.moveStrafe = 0;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void postPlayerTick(EntityPlayer player){
		JetpackInfo j = perPlayerInfo.get(player);
		if(r_setSize == null){
			r_setSize = ReflectionHelper.findMethod(Entity.class, "setSize", "func_70105_a", float.class, float.class);
		}
		if(jetpackActive(player) && !player.onGround){
			if(isHovering(player)){
				try {
					r_setSize.invoke(player, player.width, 1.8F);
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if(j != null && j.jetpackFlyTime >= 0 && player.world.isRemote){
					j.jetpackFlyTime = -1;
				}
			} else {
				try {
					//The magic number 0.6 seems to also make sure the eye height is set correctly automatically in getEyeHeight.
					r_setSize.invoke(player, player.width, 0.6F);
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if(j != null && player.world.isRemote){
					j.jetpackFlyTime ++;
				}
			}
		} else {
			if(j != null && j.jetpackFlyTime >= 0 && player.world.isRemote){
				j.jetpackFlyTime = -1;
				try {
					r_setSize.invoke(player, player.width, 1.8F);
				} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void preRenderPlayer(EntityPlayer player){
		if(!hasJetpack(player) || !jetpackActive(player))
			return;
		GL11.glPushMatrix();
		JetpackInfo j = perPlayerInfo.get(player);
		if(isHovering(player)){
			if(j != null){
				
				float prevMX = (float) (player.prevPosX-j.prevPrevPosX);
				float prevMZ = (float) (player.prevPosZ-j.prevPrevPosZ);
				float mX = (float) (player.posX-player.prevPosX);
				float mZ = (float) (player.posZ-player.prevPosZ);
				float motionX = (float) (prevMX + (mX-prevMX)*MainRegistry.proxy.partialTicks());
				float motionZ = (float) (prevMZ + (mZ-prevMZ)*MainRegistry.proxy.partialTicks());
				float angle = (float) (Math.atan2(motionX, motionZ) + Math.PI*0.5F);
				float amount = MathHelper.clamp(MathHelper.sqrt(motionX*motionX+motionZ*motionZ), 0, 2);
				GL11.glRotated(amount*22.5, Math.toDegrees(MathHelper.sin(angle)), 0, Math.toDegrees(MathHelper.cos(angle)));
			}
		} else if(!player.onGround && j != null) {
			Vec3d look = player.getLook(MainRegistry.proxy.partialTicks());
			float renderYaw = interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, MainRegistry.proxy.partialTicks());
			GlStateManager.rotate(180.0F - renderYaw, 0.0F, 1.0F, 0.0F);
			float time = j.jetpackFlyTime+MainRegistry.proxy.partialTicks();
			float mult = BobMathUtil.remap01_clamp(time*time*0.5F, 0, 100);
			GL11.glRotated((-player.rotationPitch-90)*mult, 1, 0, 0);
			Vector2f lookXZ = new Vector2f((float)look.x, (float)look.z);
			Vector2f rotXZ = new Vector2f(MathHelper.cos((float) Math.toRadians(renderYaw+90)),MathHelper.sin((float) Math.toRadians(renderYaw+90)));
			if(lookXZ.lengthSquared() != 0 && rotXZ.lengthSquared() != 0){
				lookXZ = (Vector2f) lookXZ.normalise();
				rotXZ = (Vector2f) rotXZ.normalise();
				float angle = (float) Math.acos(Math.max(Vector2f.dot(lookXZ, rotXZ), 0));
				//Apparently a Vector2f doesn't have a cross product function
				float cross = lookXZ.y*rotXZ.x-rotXZ.y*lookXZ.x;
				GL11.glRotated(Math.toDegrees(angle)*Math.signum(cross)*mult, 0, 1, 0);
			}
			GlStateManager.rotate(-(180.0F - renderYaw), 0.0F, 1.0F, 0.0F);
			if(j != null){
				if(r_ticksElytraFlying == null)
					r_ticksElytraFlying = ReflectionHelper.findField(EntityLivingBase.class, "ticksElytraFlying", "field_184629_bo");
				try {
					r_ticksElytraFlying.setInt(player, j.jetpackFlyTime);
				} catch(IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks){
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
	
	@SideOnly(Side.CLIENT)
	public static void postRenderPlayer(EntityPlayer player){
		if(!hasJetpack(player) || !jetpackActive(player))
			return;
		if(!isHovering(player)){
			if(r_ticksElytraFlying == null)
				r_ticksElytraFlying = ReflectionHelper.findField(EntityLivingBase.class, "ticksElytraFlying", "field_184629_bo");
			try {
				r_ticksElytraFlying.setInt(player, 0);
			} catch(IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		GL11.glPopMatrix();
	}
	
	@SideOnly(Side.CLIENT)
	public static void clientTick(ClientTickEvent e){
		EntityPlayer p = Minecraft.getMinecraft().player;
		if(e.phase == Phase.END){
			for(EntityPlayer player : p.world.playerEntities){
				if(jetpackActive(player) && !player.onGround){
					p.limbSwingAmount = 0;
					p.limbSwing = 0;
					p.prevLimbSwingAmount = 0;
				}
			}
		//Do networking stuff
		} else if(e.phase == Phase.START){
			for(EntityPlayer player : p.world.playerEntities){
				JetpackInfo j = perPlayerInfo.get(player);
				if(j != null){
					j.prevPrevPosX = player.prevPosX;
					j.prevPrevPosZ = player.prevPosZ;
					Iterator<Particle> it = j.booster_particles.iterator();
					while(it.hasNext()){
						Particle part = it.next();
						part.onUpdate();
						if(!part.isAlive())
							it.remove();
					}
					it = j.distortion_particles.iterator();
					while(it.hasNext()){
						Particle part = it.next();
						part.onUpdate();
						if(!part.isAlive())
							it.remove();
					}
					it = j.brightness_particles.iterator();
					while(it.hasNext()){
						Particle part = it.next();
						part.onUpdate();
						if(!part.isAlive())
							it.remove();
					}
					if(jetpackActive(player) && !player.onGround ){
						ColorGradient grad = new ColorGradient(
								new float[]{1, 0.918F, 0.882F, 1, 0},
								new float[]{0.887F, 1, 0, 1, 0.177F},
								new float[]{1, 0.19F, 0, 1, 0.336F},
								new float[]{1, 0.14F, 0, 1, 0.85F},
								new float[]{1, 0.14F, 0, 0, 1});
						if(isHovering(player) || j.thrust != 0){
							float speed = -1-2*j.thrust;
							float scale = 4+2*j.thrust;
							int numParticles = 3;
							for(int i = 0; i < numParticles; i ++){
								float iN = (float)i/(float)numParticles;
								float randX = (float) (p.world.rand.nextGaussian()*0.05F);
								float randZ = (float) (p.world.rand.nextGaussian()*0.05F);
								j.booster_particles.add(new ParticleRocketPlasma(p.world, -1.8, iN*speed, 4, scale, grad)
									.motion(randX-0.1F, speed, randZ));
								randX = (float) (p.world.rand.nextGaussian()*0.05F);
								randZ = (float) (p.world.rand.nextGaussian()*0.05F);
								j.booster_particles.add(new ParticleRocketPlasma(p.world, 1.8, iN*speed, 4, scale, grad)
									.motion(randX+0.1F, speed, randZ));
							}
							if(player.world.getWorldTime()%(2-player.world.rand.nextInt(2)) == 0){
								j.brightness_particles.add(new ParticleFakeBrightness(p.world, 1.8, -1, 4, 20+j.thrust*10, 6+player.world.rand.nextInt(2))
										.color(1, 0.6F, 0.5F, MathHelper.clamp(0.05F+j.thrust*0.1F, 0, 1))
										.enableLocalSpaceCorrection());
								j.brightness_particles.add(new ParticleFakeBrightness(p.world, -1.8, -1, 4, 20+j.thrust*10, 6+player.world.rand.nextInt(2))
										.color(1, 0.6F, 0.5F, MathHelper.clamp(0.05F+j.thrust*0.1F, 0, 1))
										.enableLocalSpaceCorrection());
							}
							if(player.world.rand.nextInt(2) == 0){
								j.distortion_particles.add(new ParticleHeatDistortion(p.world, 1.8, -1, 4, 5+j.thrust, 1.5F+j.thrust*3, 5, player.world.rand.nextFloat()*20)
										.motion(0.1F, speed*0.5F, 0)
										.enableLocalSpaceCorrection());
							}
							if(player.world.rand.nextInt(2) == 0){
								j.distortion_particles.add(new ParticleHeatDistortion(p.world, -1.8, -1, 4, 5+j.thrust, 1.5F+j.thrust*3, 5, player.world.rand.nextFloat()*20)
										.motion(-0.1F, speed*0.5F, 0)
										.enableLocalSpaceCorrection());
							}
						}
					}
					player.ignoreFrustumCheck = true;
					j.particleSpawnPositions = null;
				}
			}
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void handleCameraTransform(EntityViewRenderEvent.CameraSetup e){
		if(JetpackHandler.jetpackActive(Minecraft.getMinecraft().player) && !JetpackHandler.isHovering(Minecraft.getMinecraft().player) && Minecraft.getMinecraft().gameSettings.thirdPersonView > 0 && !Minecraft.getMinecraft().player.onGround){
			GL11.glTranslated(0, -1.22, 0);
		}
	}
	
	public static void toggleOpenState(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null){
			j = new JetpackInfo();
			perPlayerInfo.put(p, j);
		}
		j.dirty = true;
		j.animTime = System.currentTimeMillis();
		j.opening = !j.opening;
	}
	
	public static void toggleHoverState(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null){
			j = new JetpackInfo();
			perPlayerInfo.put(p, j);
		}
		j.dirty = true;
		j.hover = !j.hover;
	}
	
	public static void setThrust(EntityPlayer p, float thrust){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null){
			j = new JetpackInfo();
			perPlayerInfo.put(p, j);
		}
		j.dirty = true;
		j.thrust = thrust;
	}
	
	public static boolean isHovering(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null)
			return false;
		return j.hover;
	}
	
	public static boolean isOpening(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null)
			return true;
		return j.opening;
	}
	
	public static long getAnimTime(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null)
			return 0;
		return j.animTime;
	}
	
	public static boolean jetpackActive(EntityPlayer p){
		JetpackInfo j = perPlayerInfo.get(p);
		if(j == null || !hasJetpack(p))
			return false;
		return j.opening && System.currentTimeMillis() - j.animTime > 1000;
	}
	
	@SideOnly(Side.CLIENT)
	public static class JetpackLayer implements LayerRenderer<EntityPlayer> {
		
		@Override
		public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
			if(!hasJetpack(player))
				return;
			GL11.glPushMatrix();
			GlStateManager.enableCull();
			GL11.glTranslated(0, 0.9, 1.25);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glScaled(0.25, 0.25, 0.25);
			//That looks more or less correct I think.
			if(player.isSneaking()){
				GL11.glTranslated(0, 5.4, 1);
				GL11.glRotated(Math.toDegrees(0.5), 1, 0, 0);
				GL11.glTranslated(0, -4, 0);
			}
			GlStateManager.shadeModel(GL11.GL_SMOOTH);
	        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.jetpack_tex);
	        long startTime = getAnimTime(player);
	        boolean reverse = !isOpening(player);
	        AnimationWrapper w = new AnimationWrapper(startTime, ResourceManager.jetpack_activate);
	        if(reverse){
	        	w.reverse();
	        }
	        w.onEnd(new EndResult(EndType.STAY, null));
			ResourceManager.jetpack.controller.setAnim(w);
			ResourceManager.jetpack.renderAnimated(System.currentTimeMillis());
			GlStateManager.shadeModel(GL11.GL_FLAT);
			float[] matrix = new float[16];
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
			ClientProxy.AUX_GL_BUFFER.get(matrix);
			ClientProxy.AUX_GL_BUFFER.rewind();
			ClientProxy.deferredRenderers.add(() -> {
				GL11.glPushMatrix();
				ClientProxy.AUX_GL_BUFFER.put(matrix);
				ClientProxy.AUX_GL_BUFFER.rewind();
				GL11.glLoadMatrix(ClientProxy.AUX_GL_BUFFER);
				//Particles//
				JetpackInfo j = perPlayerInfo.get(player);
				if(j != null){
					if(j.particleSpawnPositions == null)
						j.particleSpawnPositions = BobMathUtil.worldFromLocal(new Vector4f(1.8F, 1F, 4F, 1F), new Vector4f(-1.8F, 1F, 4F, 1F), new Vector4f(0, -1, 0, 0));
					Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.fresnel_ms);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
					GlStateManager.disableAlpha();
					GlStateManager.disableCull();
					GlStateManager.depthMask(false);
					Entity entityIn = Minecraft.getMinecraft().getRenderViewEntity();
					float f1 = MathHelper.cos(entityIn.rotationYaw * 0.017453292F);
			        float f2 = MathHelper.sin(entityIn.rotationYaw * 0.017453292F);
			        float f3 = -f2 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
			        float f4 = f1 * MathHelper.sin(entityIn.rotationPitch * 0.017453292F);
			        float f5 = MathHelper.cos(entityIn.rotationPitch * 0.017453292F);
			        for(Particle p : j.distortion_particles){
						p.renderParticle(Tessellator.getInstance().getBuffer(), entityIn, partialTicks, f1, f5, f2, f3, f4);
					}
			        GlStateManager.depthMask(false);
					GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
					for(Particle p : j.booster_particles){
						p.renderParticle(Tessellator.getInstance().getBuffer(), entityIn, partialTicks, f1, f5, f2, f3, f4);
					}
					for(Particle p : j.brightness_particles){
						p.renderParticle(Tessellator.getInstance().getBuffer(), entityIn, partialTicks, f1, f5, f2, f3, f4);
					}
					GlStateManager.depthMask(true);
					GlStateManager.enableAlpha();
					GlStateManager.disableBlend();
				}
				GL11.glPopMatrix();
			});
			
			GL11.glPopMatrix();
		}

		@Override
		public boolean shouldCombineTextures() {
			return false;
		}
		
	}
	
	public static class JetpackInfo {
		//Values that must be sync'd.
		public boolean opening;
		public boolean hover = false;
		//Used to determine how much rocket flame and smoke there should be.
		public float thrust;
		//How long the jetpack has been active in regular mode. Used for tipping the player over for a smooth transition.
		public int jetpackFlyTime;
		//If it needs to be network sync'd. Not sure if I want to use this or sync constantly to avoid network errors.
		public boolean dirty = false;
		
		//Values that are kept only client side.
		public long animTime;
		//Used for world space smoke and wing trails
		public Vec3d[] particleSpawnPositions = null;
		//Used for interpolating motion for smooth leaning in hover mode.
		public double prevPrevPosX;
		public double prevPrevPosZ;
		//Particles in separate lists so I can control the render order (distortion first, then flames, then brightness).
		public List<Particle> booster_particles = new ArrayList<>();
		public List<Particle> brightness_particles = new ArrayList<>();
		public List<Particle> distortion_particles = new ArrayList<>();
	}
}
