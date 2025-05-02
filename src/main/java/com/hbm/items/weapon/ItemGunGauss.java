package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.hbm.config.CompatibilityConfig;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.main.ResourceManager;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunFXPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.GunFXPacket.FXType;
import com.hbm.particle.tau.ParticleTauBeam;
import com.hbm.particle.tau.ParticleTauHit;
import com.hbm.particle.tau.ParticleTauLightning;
import com.hbm.particle.tau.ParticleTauMuzzleLightning;
import com.hbm.particle.tau.ParticleTauParticleFirstPerson;
import com.hbm.particle.tau.ParticleTauRay;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunGauss extends ItemGunBase {
	
	private AudioWrapper chargeLoop;
	public static int firstPersonFireCounter = -1;

	public ItemGunGauss(GunConfiguration config, GunConfiguration alt, String s) {
		super(config, alt, s);
	}
	
	@Override
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(getHasShot(stack)) {
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.sparkShoot, SoundCategory.PLAYERS, 1.0F, 1.0F);
			setHasShot(stack, false);
		}
		if(!main && getStored(stack) > 0) {
			doTauShot(player.world, player, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), Math.min(getStored(stack), 13) * 3.5F);
			//EntityBulletBase bullet = new EntityBulletBase(world, altConfig.config.get(0), player);
			//bullet.overrideDamage = Math.min(getStored(stack), 13) * 3.5F;
			PacketDispatcher.sendTo(new GunAnimationPacket(AnimType.ALT_CYCLE.ordinal(), hand), (EntityPlayerMP) player);
			//world.spawnEntity(bullet);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauShoot, SoundCategory.PLAYERS, 1.0F, 0.75F);
			setItemWear(stack, getItemWear(stack) + (getCharge(stack)) * 2);
			setCharge(stack, 0);
		}
	}
	
	
	public void doTauShot(World world, @Nullable Entity player, Vec3d prevPos, Vec3d direction, float damage){
		final int MAX_BOUNCES = 4;
		for(int i = 0; i < MAX_BOUNCES; i ++){
			RayTraceResult r = Library.rayTraceIncludeEntities(world, prevPos, direction.scale(40).add(prevPos), player);
			if(r == null || r.typeOfHit == Type.MISS){
				break;
			} else if(r.typeOfHit == Type.ENTITY && CompatibilityConfig.isWarDim(world)){
				try {
					if(hurtResistantTime == null)
						hurtResistantTime = ReflectionHelper.findField(Entity.class, "hurtResistantTime", "field_70172_ad");
					hurtResistantTime.setInt(r.entityHit, 0);
				} catch (Exception x){
					x.printStackTrace();
				}
				r.entityHit.attackEntityFrom(ModDamageSource.causeTauDamage(player, null), damage);
				break;
			} else {
				Vec3d normal = new Vec3d(r.sideHit.getXOffset(), r.sideHit.getYOffset(), r.sideHit.getZOffset());
				if(Math.acos(normal.dotProduct(direction.scale(-1))) > Math.toRadians(20)){
					switch(r.sideHit.getAxis()){
					case X:
						direction = new Vec3d(-direction.x, direction.y, direction.z);
						break;
					case Y:
						direction = new Vec3d(direction.x, -direction.y, direction.z);
						break;
					case Z:
						direction = new Vec3d(direction.x, direction.y, -direction.z);
						break;
					}
					//Offsetting slightly makes the ray trace not run into any problems.
					prevPos = r.hitVec.add(direction.scale(0.01));
				} else {
					//Spawn the little sin wave particles that happen when the black mesa tau gun hits a solid (not sure if it happens in the original half life).
					for(int j = 0; j < 3 + world.rand.nextInt(5); j ++){
						//Gets a random vector rotated within a cone and then rotates it to the particle data's direction
						//Create a new vector and rotate it randomly about the x axis within the angle specified, then rotate that by random degrees to get the random cone vector
						Vec3 up = Vec3.createVectorHelper(0, 1, 0);
						up.rotateAroundX((float) Math.toRadians(world.rand.nextFloat()*75));
						up.rotateAroundY((float) Math.toRadians(world.rand.nextFloat()*360));
						//Finds the angles for the particle direction and rotate our random cone vector to it.
						Vec3 direction2 = new Vec3(direction);
						Vec3 angles = BobMathUtil.getEulerAngles(direction2);
						Vec3 newDirection = Vec3.createVectorHelper(up.xCoord, up.yCoord, up.zCoord);
						newDirection.rotateAroundX((float) Math.toRadians(angles.yCoord-90));
						newDirection.rotateAroundY((float) Math.toRadians(angles.xCoord));
						newDirection = newDirection.mult(3);
						RayTraceResult r2 = Library.rayTraceIncludeEntities(world, r.hitVec.add(newDirection.xCoord*0.01, newDirection.yCoord*0.01, newDirection.zCoord*0.01), r.hitVec.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord), player);
						if(r2 != null && r2.typeOfHit == Type.BLOCK){
							Vec3d vec1 = r2.hitVec.add(new Vec3d(newDirection.xCoord*0.01, newDirection.yCoord*0.01, newDirection.zCoord*0.01));
							Vec3d vec2 = r.hitVec.add(new Vec3d(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord));
							r2 = Library.rayTraceIncludeEntities(world, vec1, vec2, player);
						}
						//Yeah, it's some repeated code, but the alternative was even worse and buggier.
						if(r2 != null && r2.typeOfHit == Type.ENTITY && CompatibilityConfig.isWarDim(world)){
							try {
								if(hurtResistantTime == null)
									hurtResistantTime = ReflectionHelper.findField(Entity.class, "hurtResistantTime", "field_70172_ad");
								hurtResistantTime.setInt(r2.entityHit, 0);
							} catch (Exception x){
								x.printStackTrace();
							}
							r2.entityHit.attackEntityFrom(ModDamageSource.causeTauDamage(player, null), damage*0.5F);
						}
					}
					break;
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(chargeLoop != null) {
			chargeLoop.stopSound();
			chargeLoop = null;
		}
		if(firstPersonFireCounter > 10){
			for(int i = 0; i < 50; i ++){
				double randX = player.world.rand.nextGaussian()*0.01;
				double randY = player.world.rand.nextGaussian()*0.01;
				double randZ = player.world.rand.nextGaussian()*0.01;
				ParticleTauParticleFirstPerson t = new ParticleTauParticleFirstPerson(player.world, -1.25-player.world.rand.nextFloat()*0.28F+randX, 0.25+randY, 0.2+randZ, 1.8F);
				t.color(1F, 0.7F, 0.1F, 0.05F);
				t.lifetime(40);
				t.fadeIn(false);
				ModEventHandlerClient.firstPersonAuxParticles.add(t);
			}
			doTauBeamFX(player, 1.0F, 0.9F, 0.6F, 1.0F, 12, player);
			Vec3d motion = player.getLookVec().scale(-((float)firstPersonFireCounter/300F));
			player.motionX += motion.x;
			player.motionY += motion.y;
			player.motionZ += motion.z;
		}
		firstPersonFireCounter = -1;
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		setCharge(stack, 1);
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		super.startAction(stack, world, player, main, hand);
		if(!main && getItemWear(stack) < mainConfig.durability && Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
			PacketDispatcher.sendTo(new GunAnimationPacket(AnimType.SPINUP.ordinal(), hand), (EntityPlayerMP) player);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onFireClient(ItemStack stack, EntityPlayer player, boolean shouldDoThirdPerson) {
		for(int i = 0; i < 50; i ++){
			double randX = player.world.rand.nextGaussian()*0.01;
			double randY = player.world.rand.nextGaussian()*0.01;
			double randZ = player.world.rand.nextGaussian()*0.01;
			ParticleTauParticleFirstPerson t = new ParticleTauParticleFirstPerson(player.world, -1.25-player.world.rand.nextFloat()*0.28F+randX, 0.25+randY, 0.2+randZ, 1.8F);
			t.color(1F, 0.7F, 0.1F, 0.05F);
			t.lifetime(40);
			t.fadeIn(false);
			ModEventHandlerClient.firstPersonAuxParticles.add(t);
		}
		doTauBeamFX(player, 1.0F, 0.7F, 0.1F, 1.0F, 4, player);
	}
	
	@SideOnly(Side.CLIENT)
	public static void doTauBeamFX(EntityPlayer player, float r, float g, float b, float a, int life, @Nullable Entity shooter){
		ArrayList<Vec3d> hitPoints = new ArrayList<>(3);
		doTauBeamHits(player.world, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), hitPoints, shooter);
		Vec3d[] hps = hitPoints.toArray(new Vec3d[hitPoints.size()]);
		hps[0] = new Vec3d(-0.38, -0.22, 0.3).rotatePitch(-(float) Math.toRadians(player.rotationPitch)).rotateYaw(-(float) Math.toRadians(player.rotationYawHead)).add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
		ParticleTauBeam beam = new ParticleTauBeam(player.world, hps, 0.2F);
		beam.color(r, g, b, a);
		beam.lifetime(life);
		Minecraft.getMinecraft().effectRenderer.addEffect(beam);
	}
	
	//This code looks like a mess but I don't know how to improve it without making more of a mess.
	@SideOnly(Side.CLIENT)
	public static void doTauBeamHits(World world, Vec3d prevPos, Vec3d direction, List<Vec3d> hitPoints, @Nullable Entity shooter){
		final int MAX_BOUNCES = 4;
		hitPoints.add(prevPos);
		for(int i = 0; i < MAX_BOUNCES; i ++){
			RayTraceResult r = Library.rayTraceIncludeEntities(world, prevPos, direction.scale(40).add(prevPos), shooter);
			if(r == null || r.typeOfHit == Type.MISS){
				hitPoints.add(direction.scale(40).add(prevPos));
				break;
			} else if(r.typeOfHit == Type.ENTITY){
				Vec3d normal = new Vec3d(r.sideHit.getXOffset(), r.sideHit.getYOffset(), r.sideHit.getZOffset());
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, r.hitVec.x, r.hitVec.y, r.hitVec.z, 0.5F, normal));
				hitPoints.add(r.hitVec);
				switch(r.sideHit.getAxis()){
				case X:
					direction = new Vec3d(-direction.x, direction.y, direction.z);
					break;
				case Y:
					direction = new Vec3d(direction.x, -direction.y, direction.z);
					break;
				case Z:
					direction = new Vec3d(direction.x, direction.y, -direction.z);
					break;
				}
				
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("type", "spark");
				tag.setString("mode", "coneBurst");
				tag.setDouble("posX", r.hitVec.x);
				tag.setDouble("posY", r.hitVec.y);
				tag.setDouble("posZ", r.hitVec.z);
				tag.setDouble("dirX", direction.x*0.5);
				tag.setDouble("dirY", direction.y*0.5);
				tag.setDouble("dirZ", direction.z*0.5);
				tag.setFloat("r", 1F);
				tag.setFloat("g", 0.9F);
				tag.setFloat("b", 0.9F);
				tag.setFloat("a", 1F);
				tag.setInteger("lifetime", 5);
				tag.setInteger("randLifetime", 8);
				tag.setFloat("width", 0.01F);
				tag.setFloat("length", 0.5F);
				tag.setFloat("gravity", 0.1F);
				tag.setFloat("angle", 70F);
				tag.setInteger("count", 5+world.rand.nextInt(3));
				tag.setFloat("randomVelocity", 0.1F);
				MainRegistry.proxy.effectNT(tag);
				
				break;
			} else {
				hitPoints.add(r.hitVec);
				Vec3d normal = new Vec3d(r.sideHit.getXOffset(), r.sideHit.getYOffset(), r.sideHit.getZOffset());
				Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, r.hitVec.x, r.hitVec.y, r.hitVec.z, 0.75F, normal));
				if(Math.acos(normal.dotProduct(direction.scale(-1))) > Math.toRadians(20)){
					switch(r.sideHit.getAxis()){
					case X:
						direction = new Vec3d(-direction.x, direction.y, direction.z);
						break;
					case Y:
						direction = new Vec3d(direction.x, -direction.y, direction.z);
						break;
					case Z:
						direction = new Vec3d(direction.x, direction.y, -direction.z);
						break;
					}
					//Offsetting slightly makes the ray trace not run into any problems.
					prevPos = r.hitVec.add(direction.scale(0.01));
				} else {
					//Spawn the little sin wave particles that happen when the black mesa tau gun hits a solid (not sure if it happens in the original half life).
					for(int j = 0; j < 3 + world.rand.nextInt(5); j ++){
						//Gets a random vector rotated within a cone and then rotates it to the particle data's direction
						//Create a new vector and rotate it randomly about the x axis within the angle specified, then rotate that by random degrees to get the random cone vector
						Vec3 up = Vec3.createVectorHelper(0, 1, 0);
						up.rotateAroundX((float) Math.toRadians(world.rand.nextFloat()*45));
						up.rotateAroundY((float) Math.toRadians(world.rand.nextFloat()*360));
						//Finds the angles for the particle direction and rotate our random cone vector to it.
						Vec3 direction2 = new Vec3(direction);
						Vec3 angles = BobMathUtil.getEulerAngles(direction2);
						Vec3 newDirection = Vec3.createVectorHelper(up.xCoord, up.yCoord, up.zCoord);
						newDirection.rotateAroundX((float) Math.toRadians(angles.yCoord-90));
						newDirection.rotateAroundY((float) Math.toRadians(angles.xCoord));
						newDirection = newDirection.mult(3);
						RayTraceResult r2 = Library.rayTraceIncludeEntities(world, r.hitVec.add(newDirection.xCoord*0.01, newDirection.yCoord*0.01, newDirection.zCoord*0.01), r.hitVec.add(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord), shooter);
						if(r2 != null && r2.typeOfHit == Type.BLOCK){
							Vec3d vec1 = r2.hitVec.add(new Vec3d(newDirection.xCoord*0.01, newDirection.yCoord*0.01, newDirection.zCoord*0.01));
							Vec3d vec2 = r.hitVec.add(new Vec3d(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord));
							r2 = Library.rayTraceIncludeEntities(world, vec1, vec2, shooter);
						}
						//Yeah, it's some repeated code, but the alternative was even worse and buggier.
						if(r2 == null || r2.typeOfHit == Type.MISS){
							ParticleTauRay ray = new ParticleTauRay(world, new Vec3d[]{r.hitVec, new Vec3d(newDirection.xCoord, newDirection.yCoord, newDirection.zCoord).add(r.hitVec)}, 0.25F);
							Minecraft.getMinecraft().effectRenderer.addEffect(ray);
						} else if(r2.typeOfHit == Type.ENTITY){
							normal = new Vec3d(r2.sideHit.getXOffset(), r2.sideHit.getYOffset(), r2.sideHit.getZOffset());
							Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, r2.hitVec.x, r2.hitVec.y, r2.hitVec.z, 0.5F, normal));
							ParticleTauRay ray = new ParticleTauRay(world, new Vec3d[]{r.hitVec, r2.hitVec}, 0.25F);
							Minecraft.getMinecraft().effectRenderer.addEffect(ray);
							switch(r2.sideHit.getAxis()){
							case X:
								direction = new Vec3d(-direction.x, direction.y, direction.z);
								break;
							case Y:
								direction = new Vec3d(direction.x, -direction.y, direction.z);
								break;
							case Z:
								direction = new Vec3d(direction.x, direction.y, -direction.z);
								break;
							}
							
							NBTTagCompound tag = new NBTTagCompound();
							tag.setString("type", "spark");
							tag.setString("mode", "coneBurst");
							tag.setDouble("posX", r2.hitVec.x);
							tag.setDouble("posY", r2.hitVec.y);
							tag.setDouble("posZ", r2.hitVec.z);
							tag.setDouble("dirX", direction.x*0.5);
							tag.setDouble("dirY", direction.y*0.5);
							tag.setDouble("dirZ", direction.z*0.5);
							tag.setFloat("r", 1F);
							tag.setFloat("g", 0.9F);
							tag.setFloat("b", 0.9F);
							tag.setFloat("a", 1F);
							tag.setInteger("lifetime", 5);
							tag.setInteger("randLifetime", 8);
							tag.setFloat("width", 0.01F);
							tag.setFloat("length", 0.5F);
							tag.setFloat("gravity", 0.1F);
							tag.setFloat("angle", 70F);
							tag.setInteger("count", 5+world.rand.nextInt(3));
							tag.setFloat("randomVelocity", 0.1F);
							MainRegistry.proxy.effectNT(tag);
						} else {
							ParticleTauRay ray = new ParticleTauRay(world, new Vec3d[]{r.hitVec, r2.hitVec}, 0.25F);
							Minecraft.getMinecraft().effectRenderer.addEffect(ray);
							normal = new Vec3d(r2.sideHit.getXOffset(), r2.sideHit.getYOffset(), r2.sideHit.getZOffset());
							Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleTauHit(world, r2.hitVec.x, r2.hitVec.y, r2.hitVec.z, 0.75F, normal));
						}
					}
					break;
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(!main && getItemWear(stack) < mainConfig.durability && Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
			chargeLoop = MainRegistry.proxy.getLoopedSound(HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, (float)player.posX, (float)player.posY, (float)player.posZ, 1.0F, 0.75F);
			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, 1.0F, 0.75F);
			firstPersonFireCounter = 0;
			if(chargeLoop != null) {
				chargeLoop.startSound();
			}
			ModEventHandlerClient.firstPersonAuxParticles.clear();
		}
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand isCurrentItem) {
		super.updateServer(stack, world, player, slot, isCurrentItem);
		if(getIsAltDown(stack) && getItemWear(stack) < mainConfig.durability) {
			
			int c = getCharge(stack);
			
			if(c > 200) {
				setCharge(stack, 0);
				setItemWear(stack, mainConfig.durability);
				if(CompatibilityConfig.isWarDim(world)){
					player.attackEntityFrom(ModDamageSource.tauBlast, 1000);
					world.newExplosion(player, player.posX, player.posY + player.eyeHeight, player.posZ, 5.0F, true, true);
				}
				return;
			}
			
			if(c > 0) {
				setCharge(stack, c + 1);
				
				if(c % 10 == 1 && c < 140 && c > 2) {
					if(Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo)) {
						Library.consumeInventoryItem(player.inventory, ModItems.gun_xvl1456_ammo);
						setStored(stack, getStored(stack) + 1);
					} else {
						setCharge(stack, 0);
						setStored(stack, 0);
					}
				}
			} else {
				setStored(stack, 0);
			}
		} else {
			setCharge(stack, 0);
			setStored(stack, 0);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasCustomHudElement() {
		return true;
	}
	
	//Drillgon200: The default trefoil reticle looks terrible in my opinion, so I'm going to borrow the circle from black mesa.
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHud(ScaledResolution res, GuiIngame gui, ItemStack stack, float partialTicks) {
		float x = res.getScaledWidth()/2;
		float y = res.getScaledHeight()/2;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.gluontau_hud);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.color(0.9F, 0.9F, 0F, 1F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE, SourceFactor.ONE, DestFactor.ZERO);
		RenderHelper.drawGuiRect(x - 2F, y - 2F, 0, 0, 4, 4, 1, 1);
		RenderHelper.resetColor();
		GlStateManager.disableBlend();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand isCurrentItem) {
		super.updateClient(stack, world, player, slot, isCurrentItem);
		if(getItemWear(stack) >= mainConfig.durability && firstPersonFireCounter >= 0){
			endActionClient(stack, world, player, false, isCurrentItem);
		}
		if(firstPersonFireCounter >= 0){
			ParticleTauLightning lightning = new ParticleTauLightning(world, 0, 0.25, 0.2, 5+Math.min(((float)firstPersonFireCounter/25F), 2), 8+world.rand.nextFloat()*15);
			lightning.lifetime(4);
			lightning.color(1F, 0.5F, 0.1F, 0.1F+(float)Math.min(((float)firstPersonFireCounter/400F), 0.1));
			ModEventHandlerClient.firstPersonAuxParticles.add(lightning);
			if(firstPersonFireCounter % 3 == 0){
				ParticleTauParticleFirstPerson t = new ParticleTauParticleFirstPerson(world, -1.25, 0.25, 0.2, 1+Math.min(((float)firstPersonFireCounter/10F), 10));
				ParticleTauParticleFirstPerson t2 = new ParticleTauParticleFirstPerson(world, 0, 0.25, 0.2, 10+Math.min(((float)firstPersonFireCounter/10F), 10));
				t.color(1F, 0.35F, 0.1F, 1.5F);
				t.lifetime(5);
				t2.color(1F, 0.35F, 0.1F, 0.8F+Math.min(((float)firstPersonFireCounter/800F), 1));
				t2.lifetime(5);
				ModEventHandlerClient.firstPersonAuxParticles.add(t);
				ModEventHandlerClient.firstPersonAuxParticles.add(t2);
			}
			if(firstPersonFireCounter > 20){
				for(int i = 0; i < 3; i ++){
					double randX = player.world.rand.nextGaussian()*0.01;
					double randY = player.world.rand.nextGaussian()*0.01;
					double randZ = player.world.rand.nextGaussian()*0.01;
					ParticleTauParticleFirstPerson t = new ParticleTauParticleFirstPerson(world, -1.25-world.rand.nextFloat()*0.28F+randX, 0.25+randY, 0.2+randZ, 0.4F+Math.min(((float)firstPersonFireCounter/10F), 1.5F));
					t.color(1F, 0.7F, 0.1F, 0.05F);
					t.lifetime(40);
					ModEventHandlerClient.firstPersonAuxParticles.add(t);
				}
			}
			if(firstPersonFireCounter == 100){
				ParticleTauMuzzleLightning ml = new ParticleTauMuzzleLightning(world, -1.5, 0.25, 0.2, 0.1F);
				ModEventHandlerClient.firstPersonAuxParticles.add(ml);
			}
			if((firstPersonFireCounter > 40 && (firstPersonFireCounter % 10 == 0 || firstPersonFireCounter % 7 == 0)) ||
					(firstPersonFireCounter > 120 && (firstPersonFireCounter % 8 == 0 || firstPersonFireCounter % 5 == 0))){
				float offset = (1-((float)firstPersonFireCounter/200F))*0.05F;
				Vec3d pos = new Vec3d(-0.48, -0.15-offset, 1.3).rotatePitch(-(float) Math.toRadians(player.rotationPitch)).rotateYaw(-(float) Math.toRadians(player.rotationYawHead)).add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
				Vec3d pos2 = new Vec3d(-0.47, -0.15-offset, 1.3).rotatePitch(-(float) Math.toRadians(player.rotationPitch)).rotateYaw(-(float) Math.toRadians(player.rotationYawHead)).add(player.getPositionEyes(MainRegistry.proxy.partialTicks()));
				Vec3d direction = pos2.subtract(pos).normalize();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("type", "spark");
				tag.setString("mode", "coneBurst");
				tag.setDouble("posX", pos.x);
				tag.setDouble("posY", pos.y);
				tag.setDouble("posZ", pos.z);
				tag.setDouble("dirX", direction.x*0.1);
				tag.setDouble("dirY", direction.y*0.1-0.05);
				tag.setDouble("dirZ", direction.z*0.1);
				tag.setFloat("r", 1F);
				tag.setFloat("g", 0.7F);
				tag.setFloat("b", 0.1F);
				tag.setFloat("a", 1F);
				tag.setInteger("lifetime", 5);
				tag.setInteger("randLifetime", 8);
				tag.setFloat("width", 0.0075F);
				tag.setFloat("length", 0.3F);
				tag.setFloat("gravity", 0.04F);
				tag.setFloat("angle", 30F);
				tag.setInteger("count", 3+player.world.rand.nextInt(3));
				tag.setFloat("randomVelocity", 0.1F);
				MainRegistry.proxy.effectNT(tag);
			}
			firstPersonFireCounter ++;
		}
		if(chargeLoop != null) {
			chargeLoop.updatePosition((float)player.posX, (float)player.posY, (float)player.posZ);
			chargeLoop.updatePitch(chargeLoop.getPitch() + 0.01F);
		}
	}
	
	@Override
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand) {
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal(), hand), (EntityPlayerMP) player);
		PacketDispatcher.wrapper.sendToAllTracking(new GunFXPacket(player, hand, FXType.FIRE), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 1));
		doTauShot(player.world, player, player.getPositionEyes(MainRegistry.proxy.partialTicks()), player.getLook(MainRegistry.proxy.partialTicks()), 8);
		setHasShot(stack, true);
	}
	
	public static void setHasShot(ItemStack stack, boolean b) {
		writeNBT(stack, "hasShot", b ? 1 : 0);
	}
	
	public static boolean getHasShot(ItemStack stack) {
		return readNBT(stack, "hasShot") == 1;
	}
	
	/// gauss charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "gauss_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "gauss_charge");
	}
	
	public static void setStored(ItemStack stack, int i) {
		writeNBT(stack, "gauss_stored", i);
	}
	
	public static int getStored(ItemStack stack) {
		return readNBT(stack, "gauss_stored");
	}
}