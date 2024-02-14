package com.hbm.handler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.gear.JetpackGlider;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.packet.JetpackSyncPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.ParticleFakeBrightness;
import com.hbm.particle.ParticleHeatDistortion;
import com.hbm.particle.ParticleJetpackTrail;
import com.hbm.particle.rocket.ParticleRocketPlasma;
import com.hbm.render.RenderHelper;
import com.hbm.render.misc.ColorGradient;
import com.hbm.sound.MovingSoundJetpack;
import com.hbm.util.BobMathUtil;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovementInput;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackHandler {

	public static final String JETPACK_NBT = "hbmJetpackAdvanced";
	
	public static Method r_setSize;
	public static Field r_ticksElytraFlying;
	
	private static boolean jet_key_down = false;
	private static boolean hover_key_down = false;
	private static boolean hud_key_down = false;
	
	//I should be able to use this cheesily for both server and client, since they're technically different entities.
	private static Map<PlayerKey, JetpackInfo> perPlayerInfo = new HashMap<>();
	
	public static JetpackInfo get(EntityPlayer p){
		return perPlayerInfo.get(new PlayerKey(p));
	}
	
	public static void put(EntityPlayer p, JetpackInfo j){
		perPlayerInfo.put(new PlayerKey(p), j);
	}
	
	public static boolean hasJetpack(EntityPlayer p){
		ItemStack chest = p.inventory.armorInventory.get(2);
		ItemStack stack = ArmorModHandler.pryMod(chest, 1);
		if(stack.getItem() == ModItems.jetpack_glider)
			return true;
		return false;
	}
	
	public static FluidTank getTank(EntityPlayer p){
		ItemStack chest = p.inventory.armorInventory.get(2);
		ItemStack stack = ArmorModHandler.pryMod(chest, 1);
		return ((JetpackGlider)stack.getItem()).getTank(stack);
	}
	
	public static void setTank(EntityPlayer p, FluidTank tank){
		ItemStack chest = p.inventory.armorInventory.get(2);
		ItemStack stack = ArmorModHandler.pryMod(chest, 1);
		((JetpackGlider)stack.getItem()).setTank(stack, tank);
	}
	
	public static float getSpeed(Fluid f){
		if(f == null)
			return 0;
		if(f == ModForgeFluids.kerosene){
			return 0.3F;
		} else if(f == ModForgeFluids.nitan){
			return 0.5F;
		} else if(f == ModForgeFluids.balefire){
			return 1.5F;
		}
		return 0;
	}
	
	public static int getDrain(Fluid f){
		if(f == null)
			return 0;
		//Drain is already scaled by thrust, which is greater with the higher tier fuels
		if(f == ModForgeFluids.kerosene){
			return 1;
		} else if(f == ModForgeFluids.nitan){
			return 1;
		} else if(f == ModForgeFluids.balefire){
			return 1;
		}
		return 0;
	}
	
	private static float[] keroseneColor = new float[]{1, 0.6F, 0.5F};
	private static float[] nitanColor = new float[]{1F, 0.5F, 1F};
	private static float[] bfColor = new float[]{0.4F, 1, 0.7F};
	private static ColorGradient keroseneGradient = new ColorGradient(
			new float[]{1, 0.918F, 0.882F, 1, 0},
			new float[]{0.887F, 1, 0, 1, 0.177F},
			new float[]{1, 0.19F, 0, 1, 0.336F},
			new float[]{1, 0.14F, 0, 1, 0.85F},
			new float[]{1, 0.14F, 0, 0, 1});
	private static ColorGradient nitanGradient = new ColorGradient(
			new float[]{0.845F, 0.779F, 1F, 1, 0},
			new float[]{1F, 0.3F, 1F, 1, 0.122F},
			new float[]{0.7F, 0.4F, 1F, 1, 0.389F},
			new float[]{0.35F, 0.2F, 1F, 1, 0.891F},
			new float[]{0.1F, 0.1F, 1F, 0, 1});
	private static ColorGradient bfGradient = new ColorGradient(
			new float[]{1F, 0.95F, 0.279F, 1, 0},
			new float[]{1F, 0.9F, 0.1F, 1, 0.122F},
			new float[]{0.013F, 1F, 0.068F, 1, 0.389F},
			new float[]{0.2F, 1F, 0.3F, 1, 0.891F},
			new float[]{0, 1F, 0.4F, 0, 1});
	
	public static ColorGradient getGradientFromFuel(Fluid fuel){
		if(fuel == ModForgeFluids.balefire){
			return bfGradient;
		} else if(fuel == ModForgeFluids.nitan){
			return nitanGradient;
		}
		return keroseneGradient;
	}
	
	public static float[] getBrightnessColorFromFuel(Fluid fuel){
		if(fuel == ModForgeFluids.balefire){
			return bfColor;
		} else if(fuel == ModForgeFluids.nitan){
			return nitanColor;
		}
		return keroseneColor;
	}
	
	//I'm not even going to bother trying to cheat protect this. If a player wants to fly really fast,
	//They can just download a regular hacked anyway.
	@SideOnly(Side.CLIENT)
	public static void inputUpdate(InputUpdateEvent e){
		EntityPlayer player = e.getEntityPlayer();
		if(!hasJetpack(player))
			return;
		FluidTank fuelTank = getTank(player);
		JetpackInfo info = get(player);
		if(info == null){
			info = new JetpackInfo(true);
			put(player, info);
			info.dirty = true;
		}
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
		boolean hudKey = ClientProxy.jetpackHud.isKeyDown();
		if(hudKey && !hud_key_down){
			toggleHUDState(player);
		}
		hud_key_down = hudKey;
		float thrust = info.thrust;
		if(jetpackActive(player) && player.isInWater()){
			info.failureTicks = 80;
		}
		if(jetpackActive(player) && !player.onGround && info.failureTicks <= 0 && fuelTank.getFluidAmount() > 0){
			float speed = getSpeed(fuelTank.getFluid().getFluid());
			player.capabilities.isFlying = false;
			MovementInput m = e.getMovementInput();
			boolean sprint = Minecraft.getMinecraft().gameSettings.keyBindSprint.isKeyDown();
			if(player.isSprinting())
				player.setSprinting(sprint);
			if(isHovering(player)){
				m.moveForward *= (player.isSprinting() ? 0.17 : 0.1)*speed;
				m.moveStrafe *= 0.1F*speed;
				player.motionX -= MathHelper.sin((float) Math.toRadians(player.rotationYawHead)) * m.moveForward;
				player.motionZ += MathHelper.cos((float) Math.toRadians(player.rotationYawHead)) * m.moveForward;
				player.motionX -= MathHelper.sin((float) Math.toRadians(player.rotationYawHead-90)) * m.moveStrafe;
				player.motionZ += MathHelper.cos((float) Math.toRadians(player.rotationYawHead-90)) * m.moveStrafe;
				player.motionY *= 0.75;
				player.motionY += 0.05;
				float extraMY = 0;
				if(m.jump){
					m.jump = false;
					extraMY += 0.3*speed;
				}
				if(m.sneak){
					m.sneak = false;
					extraMY -= Math.min(0.3, 0.3*speed);
				}
				player.motionY += extraMY;
				float diff = (Math.abs(m.moveForward)+Math.abs(m.moveStrafe)+extraMY+0.4F) - thrust;
				setThrust(player, thrust + diff*0.3F);
				m.moveForward = sprint ? 0.81F : 0;
				m.moveStrafe = 0;
			} else {
				float boost = m.jump ? speed : 0;
				float diff = MathHelper.clamp(BobMathUtil.remap(boost, 0, 2, 0F, 4), 0, 2) - thrust;
				setThrust(player, thrust + diff*0.2F);
				Vec3d look = player.getLookVec();
				if(m.jump){
					player.motionX += look.x*boost*0.5F;
					player.motionY += look.y*boost*0.5F;
					if(look.y > 0){
						player.motionY += look.y * 0.08F;
					}
					player.motionZ += look.z*boost*0.5F;
				}
				m.moveForward = 0;
				m.moveStrafe = 0;
				
				//Same thing as lunar's glider, I figured I should make it consistent
				if(player.motionY < -0.08) {
					Vec3d vec = player.getLookVec();
					double mo = player.motionY * -0.4;
					player.motionY += mo*(1-Math.abs(vec.y));
					
					player.motionX += vec.x*mo;
					player.motionY += vec.y*mo*(1-Math.abs(vec.y));
					player.motionZ += vec.z*mo;
				}
			}
			info.prevThrust = thrust;
		} else {
			float diff = -thrust;
			setThrust(player, thrust + diff*0.3F);
			info.prevThrust = thrust;
		}
	}
	
	public static void serverTick(){
		Iterator<Entry<PlayerKey, JetpackInfo>> itr = perPlayerInfo.entrySet().iterator();
		while(itr.hasNext()){
			Entry<PlayerKey, JetpackInfo> e = itr.next();
			EntityPlayer player = e.getKey().player;
			if(player.isDead){
				itr.remove();
				continue;
			}
			if(!player.world.isRemote){
				JetpackInfo info = e.getValue();
				if(jetpackActive(player)){
					FluidTank tank = getTank(player);
					int drain = (int) Math.ceil(getDrain(tank.getFluid() == null ? null : tank.getFluid().getFluid())*info.thrust);
					if(info.thrust < 0.0001)
						drain = 0;

					tank.drain(drain, true);
					setTank(player, tank);
				}
				if(player.motionY > -0.5) player.fallDistance = 0;
				PacketDispatcher.wrapper.sendToAllTracking(new JetpackSyncPacket(player), player);
			}
		}
	}
	
	public static void playerLoggedIn(PlayerLoggedInEvent e){
		EntityPlayer player = e.player;
		ItemStack stack = player.inventory.armorInventory.get(2);
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(JETPACK_NBT)){
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag(JETPACK_NBT);
			JetpackInfo j = new JetpackInfo(player.world.isRemote);
			j.readFromNBT(tag);
			put(e.player, j);
			PacketDispatcher.sendTo(new JetpackSyncPacket(player), (EntityPlayerMP) player);
		}
	}
	
	public static void loadNBT(EntityPlayer player){
		ItemStack stack = player.inventory.armorInventory.get(2);
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey(JETPACK_NBT)){
			NBTTagCompound tag = stack.getTagCompound().getCompoundTag(JETPACK_NBT);
			JetpackInfo j = new JetpackInfo(player.world.isRemote);
			j.readFromNBT(tag);
			put(player, j);
			PacketDispatcher.wrapper.sendToAllTracking(new JetpackSyncPacket(player), (EntityPlayerMP) player);
		}
	}
	
	public static void worldLoad(WorldEvent.Load e){
		for(EntityPlayer player : e.getWorld().playerEntities){
			loadNBT(player);
		}
	}

	public static void saveNBT(EntityPlayer player){
		if(hasJetpack(player)){
			JetpackInfo info = get(player);
			if(info != null){
				NBTTagCompound tag = info.writeToNBT(new NBTTagCompound());
				ItemStack stack = player.inventory.armorInventory.get(2);
				if(stack.isEmpty())
					return;
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setTag(JETPACK_NBT, tag);
			}
		}
	}
	
	public static void worldSave(WorldEvent.Save e){
		for(EntityPlayer player : e.getWorld().playerEntities){
			saveNBT(player);
		}
	}
	
	public static void startTracking(PlayerEvent.StartTracking e){
		if(!e.getEntityPlayer().world.isRemote){
			if(e.getTarget() instanceof EntityPlayer && e.getEntityPlayer() instanceof EntityPlayerMP){
				EntityPlayer player = (EntityPlayer)e.getTarget();
				JetpackInfo j = get(player);
				if(j != null){
					PacketDispatcher.wrapper.sendTo(new JetpackSyncPacket(player), (EntityPlayerMP) e.getEntityPlayer());
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void postPlayerTick(EntityPlayer player){
		JetpackInfo j = get(player);
		if(j == null)
			return;
		if(r_setSize == null){
			r_setSize = ReflectionHelper.findMethod(Entity.class, "setSize", "func_70105_a", float.class, float.class);
		}
		if(jetpackActive(player) && !player.onGround && j.failureTicks <= 0 && getTank(player).getFluidAmount() > 0){
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
	public static void renderHUD(EntityPlayer p, ScaledResolution res){
		if(!hasJetpack(p))
			return;
		JetpackInfo info = get(p);
		if(info == null)
			return;
		if(info.useCompactHUD){
			//GL11.glPushMatrix();
			float maxHeight = 0.78125F;
			float maxHeightPixels = 50*maxHeight;
			//GL11.glTranslated(0, -res.getScaledHeight()+maxHeightPixels, 0);
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.jetpack_hud_small);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GlStateManager.enableAlpha();
			RenderHelper.drawGuiRect(0, res.getScaledHeight()-maxHeightPixels, 0, 1-maxHeight, 50, maxHeightPixels, 1, 1);
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			float oX = 31/256F;
			float width = 88/256F - oX;
			float oY = 8/256F;
			float height = 31/256F - oY;
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			//Thrust
			float thrust = info.prevThrust+(info.thrust-info.prevThrust)*MainRegistry.proxy.partialTicks();
			float thrustDegrees = MathHelper.clamp(thrust*100-27, -27, 200);
			GL11.glPushMatrix();
			float rX = oX*50 + 40.5F*50/256F;
			float rY = res.getScaledHeight()-(maxHeightPixels-maxHeightPixels*oY) + 14.5F*maxHeightPixels/256F;
			GL11.glTranslated(rX+(117/256F)*50, rY+(76/256F)*maxHeightPixels, 0);
			GL11.glRotated(thrustDegrees, 0, 0, 1);
			GL11.glTranslated(-rX, -rY, 0);
			RenderHelper.drawGuiRect(oX*50, res.getScaledHeight()-(maxHeightPixels-maxHeightPixels*oY), oX, oY, width*50, height*50, oX+width, oY+height);
			GL11.glPopMatrix();
			//Fuel
			FluidTank tank = getTank(p);
			float fuelDegrees = ((float)tank.getFluidAmount()/tank.getCapacity());
			fuelDegrees = fuelDegrees * 227 - 27;
			GL11.glPushMatrix();
			GL11.glTranslated(rX, rY+(76/256F)*maxHeightPixels, 0);
			GL11.glRotated(fuelDegrees, 0, 0, 1);
			GL11.glTranslated(-rX, -rY, 0);
			RenderHelper.drawGuiRect(oX*50, res.getScaledHeight()-(maxHeightPixels-maxHeightPixels*oY), oX, oY, width*50, height*50, oX+width, oY+height);
			GL11.glPopMatrix();
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.jetpack_hud_small_text);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			
			float yOffset = 0;
			float yPosition = 0;
			float yHeight = 0;
			if(info.failureTicks > 0){
				yOffset = 76/128F;
				yHeight = 104/128F-yOffset;
				yPosition = -0.5F;
			} else if(jetpackActive(p)){
				if(info.hover){
					yOffset = 25/128F;
					yHeight = 46/128F-yOffset;
					yPosition = -10.25F;
				} else {
					yOffset = 1/128F;
					yHeight = 23/128F-yOffset;
					yPosition = -14.75F;
				}
			} else {
				yOffset = 47/128F;
				yHeight = 75/128F-yOffset;
				yPosition = -6;
			}
			RenderHelper.drawGuiRect(0, res.getScaledHeight()-25-(yOffset-yOffset*25)-yPosition, 0, yOffset, 50, 25*yHeight, 1, yOffset+yHeight);
			//GL11.glPopMatrix();
		} else {
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.jetpack_hud_large);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GlStateManager.enableAlpha();
			RenderHelper.drawGuiRect(0, res.getScaledHeight()-80, 0, 0, 80, 80, 0.5F, 1);
			GlStateManager.disableAlpha();
			
			boolean active = jetpackActive(p);
			boolean hover = isHovering(p);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
			float oX = 40/512F;
			float width = 172/512F - oX;
			//oX = 0.2F;
			//width = 0.8F-oX;
			if(active){
				RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-40, 0.5F+oX*0.5F, 0.5F, width*80, 40, 0.5F+(width+oX)*0.5F, 1);
			}
			oX = 172/512F;
			width = 282/512F - oX;
			if(hover){
				RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-40, 0.5F+oX*0.5F, 0.5F, width*80, 40, 0.5F+(width+oX)*0.5F, 1);
			}
			oX = 282/512F;
			width = 383/512F - oX;
			if(!hover){
				RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-40, 0.5F+oX*0.5F, 0.5F, width*80, 40, 0.5F+(width+oX)*0.5F, 1);
			}
			oX = 383/512F;
			width = 512/512F - oX;
			if(info.failureTicks > 0){
				RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-40, 0.5F+oX*0.5F, 0.5F, width*80, 40, 0.5F+(width+oX)*0.5F, 1);
			}
			oX = 49/512F;
			width = 134/512F - oX;
			float oY = 148/512F;
			float height = 171/512F - oY;
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			//Thrust
			float thrust = info.prevThrust+(info.thrust-info.prevThrust)*MainRegistry.proxy.partialTicks();
			float thrustDegrees = MathHelper.clamp(thrust*100-27, -27, 200);
			GL11.glPushMatrix();
			float rX = oX*80 + 61.5F*80/512F;
			float rY = res.getScaledHeight()-(80-oY*80) + 11.5F*80/512F;
			GL11.glTranslated(rX, rY, 0);
			GL11.glRotated(thrustDegrees, 0, 0, 1);
			GL11.glTranslated(-rX, -rY, 0);
			RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-(80-oY*80), 0.5F+oX*0.5F, oY, width*80, height*80, 0.5F+(width+oX)*0.5F, oY+height);
			GL11.glPopMatrix();
			//Fuel
			FluidTank tank = getTank(p);
			float fuelDegrees = ((float)tank.getFluidAmount()/tank.getCapacity());
			fuelDegrees = fuelDegrees * 227 - 27;
			GL11.glPushMatrix();
			GL11.glTranslated(rX+80*179/512F, rY, 0);
			GL11.glRotated(fuelDegrees, 0, 0, 1);
			GL11.glTranslated(-rX, -rY, 0);
			RenderHelper.drawGuiRect(oX*80, res.getScaledHeight()-(80-oY*80), 0.5F+oX*0.5F, oY, width*80, height*80, 0.5F+(width+oX)*0.5F, oY+height);
			GL11.glPopMatrix();
			//GlStateManager.disableBlend();
			if(tank.getFluid() != null){
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				TextureAtlasSprite fuel = FFUtils.getTextureFromFluid(tank.getFluid().getFluid());
				RenderHelper.drawGuiRect(66.8F, res.getScaledHeight()-71.3F, fuel.getMinU(), fuel.getMinV(), 6.5F, 6.8F, fuel.getMaxU(), fuel.getMaxV());
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void preRenderPlayer(EntityPlayer player){
		if(!hasJetpack(player) || !jetpackActive(player))
			return;
		GL11.glPushMatrix();
		
		float partialTicks = MainRegistry.proxy.partialTicks();
		double playerPosX = player.prevPosX + (player.posX-player.prevPosX)*partialTicks;
		double playerPosY = player.prevPosY + (player.posY-player.prevPosY)*partialTicks;
		double playerPosZ = player.prevPosZ + (player.posZ-player.prevPosZ)*partialTicks;
		double offsetX = playerPosX-TileEntityRendererDispatcher.staticPlayerX;
		double offsetY = playerPosY-TileEntityRendererDispatcher.staticPlayerY;
		double offsetZ = playerPosZ-TileEntityRendererDispatcher.staticPlayerZ;
		
		GL11.glTranslated(offsetX, offsetY, offsetZ);
		
		JetpackInfo j = get(player);
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
		} else if(!player.onGround && j != null && j.failureTicks <= 0 && getTank(player).getFluidAmount() > 0) {
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
				if(!Float.isNaN(angle)){
					//Apparently a Vector2f doesn't have a cross product function
					float cross = lookXZ.y*rotXZ.x-rotXZ.y*lookXZ.x;
					GL11.glRotated(Math.toDegrees(angle)*Math.signum(cross)*mult, 0, 1, 0);
				}
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
		GL11.glTranslated(-offsetX, -offsetY, -offsetZ);
	}
	
	protected static float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks){
        float f;

        for (f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
            ;
        }

        while (f >= 180.0F) {
            f -= 360.0F;
        }

        return prevYawOffset + partialTicks * f;
    }
	
	@SideOnly(Side.CLIENT)
	public static void postRenderPlayer(EntityPlayer player){
		if(!hasJetpack(player) || !jetpackActive(player))
			return;
		if(!isHovering(player) && getTank(player).getFluidAmount() > 0){
			JetpackInfo info = get(player);
			if(!player.onGround && info.failureTicks <= 0 && info.particleSpawnPositions != null && player.motionX*player.motionX+player.motionY*player.motionY+player.motionZ*player.motionZ > 1.5*1.5){
				ClientProxy.deferredRenderers.add(() -> {
					if(info.trails[0] == null){
						Minecraft.getMinecraft().effectRenderer.addEffect(info.trails[0] = new ParticleJetpackTrail(player.world));
						Minecraft.getMinecraft().effectRenderer.addEffect(info.trails[1] = new ParticleJetpackTrail(player.world));
					}
					info.trails[0].tryAddNewPos(info.particleSpawnPositions[0]);
					info.trails[1].tryAddNewPos(info.particleSpawnPositions[1]);
				});
			} else {
				info.trails[0] = null;
				info.trails[1] = null;
			}
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
					player.limbSwingAmount = 0;
					player.limbSwing = 0;
					player.prevLimbSwingAmount = 0;
				}
			}
		} else if(e.phase == Phase.START){
			Iterator<Entry<PlayerKey, JetpackInfo>> itr = perPlayerInfo.entrySet().iterator();
			while(itr.hasNext()){
				Entry<PlayerKey, JetpackInfo> entry = itr.next();
				EntityPlayer player = entry.getKey().player;
				if(player.isDead){
					if(entry.getValue().sound != null){
						entry.getValue().sound.end();
					}
					itr.remove();
					continue;
				}
				if(!player.world.isRemote)
					continue;
				
				JetpackInfo j = entry.getValue();
				if(j.thrust > 0.001){
					if(j.sound == null){
						Minecraft.getMinecraft().getSoundHandler().playSound(j.sound = new MovingSoundJetpack(player, HBMSoundHandler.jetpack, SoundCategory.PLAYERS));
					}
				} else {
					if(j.sound != null){
						j.sound.end();
						j.sound = null;
					}
				}
				if(player == Minecraft.getMinecraft().player){
					if(j.failureTicks > 0)
						j.dirty = true;
					j.failureTicks = Math.max(0, j.failureTicks-1);
				}
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
				boolean active = jetpackActive(player);
				FluidTank tank = active ? getTank(player) : null;
				if(active && !player.onGround && tank.getFluid() != null){
					ColorGradient grad = getGradientFromFuel(tank.getFluid().getFluid());
					float[] color = getBrightnessColorFromFuel(tank.getFluid().getFluid());
					if(j.thrust > 0.05F){
						float thrust = j.thrust - 0.4F;
						float speed = -1-2*thrust;
						float scale = 4+2*thrust;
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
						if(player.world.getTotalWorldTime()%(2-player.world.rand.nextInt(2)) == 0){
							j.brightness_particles.add(new ParticleFakeBrightness(p.world, 1.8, -1, 4, 20+thrust*10, 6+player.world.rand.nextInt(2))
									.color(color[0], color[1], color[2], MathHelper.clamp(0.05F+thrust*0.1F, 0, 1))
									.enableLocalSpaceCorrection());
							j.brightness_particles.add(new ParticleFakeBrightness(p.world, -1.8, -1, 4, 20+thrust*10, 6+player.world.rand.nextInt(2))
									.color(color[0], color[1], color[2], MathHelper.clamp(0.05F+thrust*0.1F, 0, 1))
									.enableLocalSpaceCorrection());
						}
						if(player.world.rand.nextInt(2) == 0){
							j.distortion_particles.add(new ParticleHeatDistortion(p.world, 1.8, -1, 4, 5+thrust, 1.5F+thrust*3, 5, player.world.rand.nextFloat()*20)
									.motion(0.1F, speed*0.5F, 0)
									.enableLocalSpaceCorrection());
						}
						if(player.world.rand.nextInt(2) == 0){
							j.distortion_particles.add(new ParticleHeatDistortion(p.world, -1.8, -1, 4, 5+thrust, 1.5F+thrust*3, 5, player.world.rand.nextFloat()*20)
									.motion(-0.1F, speed*0.5F, 0)
									.enableLocalSpaceCorrection());
						}
					}
				}
				player.ignoreFrustumCheck = true;
				
				//SYNC
				if(player == Minecraft.getMinecraft().player && j.dirty){
					PacketDispatcher.wrapper.sendToServer(new JetpackSyncPacket(player));
					j.dirty = false;
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
		JetpackInfo j = get(p);
		if(j == null){
			j = new JetpackInfo(p.world.isRemote);
			put(p, j);
		}
		j.dirty = true;
		j.animTime = System.currentTimeMillis();
		j.opening = !j.opening;
	}
	
	public static void toggleHoverState(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null){
			j = new JetpackInfo(p.world.isRemote);
			put(p, j);
		}
		j.dirty = true;
		j.hover = !j.hover;
	}
	
	public static void toggleHUDState(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null){
			j = new JetpackInfo(p.world.isRemote);
			put(p, j);
		}
		j.dirty = true;
		j.useCompactHUD = !j.useCompactHUD;
	}
	
	public static void setThrust(EntityPlayer p, float thrust){
		JetpackInfo j = get(p);
		if(j == null){
			j = new JetpackInfo(p.world.isRemote);
			put(p, j);
		}
		if(j.thrust != thrust){
			j.dirty = true;
			j.thrust = thrust;
		}
	}
	
	public static boolean isHovering(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null)
			return false;
		return j.hover;
	}
	
	public static boolean isOpening(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null)
			return true;
		return j.opening;
	}
	
	public static boolean useCompactHUD(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null)
			return false;
		return j.useCompactHUD;
	}
	
	public static long getAnimTime(EntityPlayer p){
		JetpackInfo j = get(p);
		if(j == null)
			return 0;
		return j.animTime;
	}
	
	public static boolean jetpackActive(EntityPlayer p){
		JetpackInfo j = get(p);
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
				JetpackInfo j = get(player);
				if(j != null){
					//left forward down
					j.particleSpawnPositions = BobMathUtil.worldFromLocal(new Vector4f(5.25F, 0.2F, 2.75F, 1F), new Vector4f(-5.25F, 0.2F, 2.75F, 1F));
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
		//How many ticks the jetpack remains in a fail state
		public int failureTicks = 0;
		//If it needs to be network sync'd. Not sure if I want to use this or sync constantly to avoid network errors.
		public boolean dirty = true;
		//The current jetpack mode
		public boolean useCompactHUD = false;
		
		//Values that are kept only client side.
		public long animTime;
		//Used for world space smoke and wing trails
		public Vec3d[] particleSpawnPositions = null;
		//Used for interpolating motion for smooth leaning in hover mode.
		public double prevPrevPosX;
		public double prevPrevPosZ;
		public float prevThrust;
		//The rocket sound
		@SideOnly(Side.CLIENT)
		public MovingSoundJetpack sound;
		//Particles in separate lists so I can control the render order (distortion first, then flames, then brightness).
		@SideOnly(Side.CLIENT)
		public List<Particle> booster_particles;
		@SideOnly(Side.CLIENT)
		public List<Particle> brightness_particles;
		@SideOnly(Side.CLIENT)
		public List<Particle> distortion_particles;
		@SideOnly(Side.CLIENT)
		public ParticleJetpackTrail[] trails;
		
		public JetpackInfo(boolean client) {
			if(client)
				initClient();
		}
		
		@SideOnly(Side.CLIENT)
		public void initClient(){
			sound = null;
			booster_particles = new ArrayList<>();
			brightness_particles = new ArrayList<>();
			distortion_particles = new ArrayList<>();
			trails = new ParticleJetpackTrail[2];
		}
		
		public NBTTagCompound writeToNBT(NBTTagCompound tag){
			tag.setBoolean("opening", opening);
			tag.setBoolean("hover", hover);
			tag.setFloat("thrust", thrust);
			tag.setInteger("flyTime", jetpackFlyTime);
			tag.setBoolean("compact", useCompactHUD);
			tag.setInteger("failureTicks", failureTicks);
			return tag;
		}
		
		public void readFromNBT(NBTTagCompound tag){
			opening = tag.getBoolean("opening");
			hover = tag.getBoolean("hover");
			thrust = tag.getFloat("thrust");
			jetpackFlyTime = tag.getInteger("flyTime");
			useCompactHUD = tag.getBoolean("compact");
			failureTicks = tag.getInteger("failureTicks");
			dirty = true;
		}
		
		public void write(ByteBuf buf){
			buf.writeBoolean(opening);
			buf.writeBoolean(hover);
			buf.writeFloat(thrust);
			buf.writeInt(jetpackFlyTime);
			buf.writeBoolean(useCompactHUD);
			buf.writeInt(failureTicks);
		}
		
		public void read(ByteBuf buf){
			opening = buf.readBoolean();
			hover = buf.readBoolean();
			thrust = buf.readFloat();
			jetpackFlyTime = buf.readInt();
			useCompactHUD = buf.readBoolean();
			failureTicks = buf.readInt();
		}

		public void setFromServer(JetpackInfo other) {
			if(other.opening != this.opening){
				this.animTime = System.currentTimeMillis();
			}
			this.opening = other.opening;
			this.hover = other.hover;
			this.thrust = other.thrust;
			this.jetpackFlyTime = other.jetpackFlyTime;
			this.useCompactHUD = other.useCompactHUD;
			this.failureTicks = other.failureTicks;
		}
	}
	
	public static class PlayerKey {
		
		public EntityPlayer player;
		
		public PlayerKey(EntityPlayer player) {
			this.player = player;
		}
		
		@Override
		public int hashCode() {
			return System.identityHashCode(player);
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PlayerKey))
				return false;
			return player == ((PlayerKey)obj).player;
		}
	}
}
