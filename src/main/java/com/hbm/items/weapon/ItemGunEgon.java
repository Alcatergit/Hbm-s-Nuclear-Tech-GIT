package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.config.CompatibilityConfig;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.main.ResourceManager;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PacketSpecialDeath;
import com.hbm.particle.gluon.ParticleGluonBurnTrail;
import com.hbm.particle.gluon.ParticleGluonFlare;
import com.hbm.particle.gluon.ParticleGluonMuzzleSmoke;
import com.hbm.particle.tau.ParticleTauParticle;
import com.hbm.render.RenderHelper;
import com.hbm.render.item.weapon.ItemRenderGunEgon;
import com.hbm.sound.GunEgonSoundHandler;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunEgon extends ItemGunBase {

	public float charge = 0.25F;
	public static float chargeScaling = 1.011619F; //double dmg every 2 sec
	public static int activeTicks = 0;
	public static Map<EntityPlayer, ParticleGluonBurnTrail> activeTrailParticles = new HashMap<>();
	public static Map<EntityPlayer, GunEgonSoundHandler> soundsByPlayer = new HashMap<>();
	
	public ItemGunEgon(GunConfiguration config, String s) {
		super(config, s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateClient(stack, world, player, slot, hand);
		if(hand == EnumHand.OFF_HAND)
			return;
		if(player == Minecraft.getMinecraft().player){
			if(m1 && Library.countInventoryItem(player.inventory, getBeltType(player, stack, true)) >= 2){
				activeTicks = Math.min(activeTicks + 1, 5);
				float[] angles = ItemGunEgon.getBeamDirectionOffset(player.world.getTotalWorldTime()+1);
				Vec3d look = Library.changeByAngle(player.getLook(1), angles[0], angles[1]);
				RayTraceResult r = Library.rayTraceIncludeEntitiesCustomDirection(player, look, 50, 1);
				if(r != null && r.hitVec != null && r.typeOfHit != Type.MISS && r.sideHit != null){
					Vec3i norm = r.sideHit.getDirectionVec();
					Vec3d pos = r.hitVec.addVector(norm.getX()*0.1F, norm.getY()*0.1F, norm.getZ()*0.1F);
					ParticleGluonFlare flare = new ParticleGluonFlare(world, pos.x, pos.y, pos.z, player);
					Minecraft.getMinecraft().effectRenderer.addEffect(flare);
				} else {
					Vec3d pos = player.getPositionEyes(1).add(look.scale(50));
					ParticleGluonFlare flare = new ParticleGluonFlare(world, pos.x, pos.y, pos.z, player);
					Minecraft.getMinecraft().effectRenderer.addEffect(flare);
				}
				
				Random rand = world.rand;
				float partialTicks = MainRegistry.proxy.partialTicks();
				float[] offset = ItemRenderGunEgon.getOffset(player.world.getTotalWorldTime()+partialTicks);
				float fovDiff = (ModEventHandlerClient.currentFOV-70)*0.0002F;
				Vec3d start = new Vec3d(-0.18+offset[0]*0.075F-fovDiff, -0.2+offset[1]*0.1F, 0.5-fovDiff*30);
				start = start.rotatePitch((float) Math.toRadians(-(player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks)));
				start = start.rotateYaw((float) Math.toRadians(-(player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks)));

				start = start.add(player.getPositionEyes(partialTicks));
				for(int i = 0; i < 2; i ++){
					Vec3d randPos = new Vec3d(rand.nextFloat()-0.5, rand.nextFloat()-0.5, rand.nextFloat()-0.5).scale(0.05);
					ParticleTauParticle p = new ParticleTauParticle(world, start.x+randPos.x, start.y+randPos.y, start.z+randPos.z, 0.05F, 0.02F, 1, 3, 0F);
					p.motion((rand.nextFloat()-0.5F)*0.04F, (rand.nextFloat()-0.5F)*0.04F, (rand.nextFloat()-0.5F)*0.04F);
					p.lifetime(6+rand.nextInt(4));
					p.color(0.2F, 0.4F+world.rand.nextFloat()*0.5F, 1F, 2F);
					Minecraft.getMinecraft().effectRenderer.addEffect(p);
				}
				
				
				if(Minecraft.getMinecraft().world.getTotalWorldTime() % 2 == 0){
					ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleGluonMuzzleSmoke(world, 0, 0, 4.1, 0, ResourceManager.gluon_muzzle_smoke, 10, 50, 9).color(0.2F, 0.4F+world.rand.nextFloat()*0.5F, 1F, 3F).lifetime(10));
				}
				if(Minecraft.getMinecraft().world.getTotalWorldTime() % 4 == 0){
					ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleGluonMuzzleSmoke(world, 0, 0, 4, 1, ResourceManager.gluon_muzzle_glow, 30, 50, -1).color(0.2F, 0.4F+world.rand.nextFloat()*0.5F, 1F, 2F).lifetime(16));
				}
				
				
				if(activeTicks < 3){
					for(int i = 0; i < 3; i ++)
						ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleGluonMuzzleSmoke(world, 0, 0, 4.1, 0, ResourceManager.gluon_muzzle_smoke, 10, 50, 25).color(0.2F, 0.4F, 1F, 3F).lifetime(7));
					if(activeTicks == 1){
						ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleGluonMuzzleSmoke(world, 0, 0, 4.1, 0, ResourceManager.flare, 10, 50, 25).color(0.2F, 0.4F, 1F, 3F).lifetime(7));
					}
				}
				
			} else {
				activeTicks = Math.max(activeTicks - 1, 0);
				activeTrailParticles.remove(player);
			}
		}
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateServer(stack, world, player, slot, hand);
		if(hand == EnumHand.OFF_HAND)
			return;
		if(getIsMouseDown(stack) && Library.countInventoryItem(player.inventory, getBeltType(player, stack, true)) >= 2){
			setIsFiring(stack, true);
			if(world.getTotalWorldTime() % 5 == 0){
				Library.consumeInventoryItem(player.inventory, getBeltType(player, stack, true));
				Library.consumeInventoryItem(player.inventory, getBeltType(player, stack, true));
			}
			float[] angles = ItemGunEgon.getBeamDirectionOffset(player.world.getTotalWorldTime()+1);
			Vec3d look = Library.changeByAngle(player.getLook(1), angles[0], angles[1]);
			RayTraceResult r = Library.rayTraceIncludeEntitiesCustomDirection(player, look, 50, 1);
			if(r != null && r.typeOfHit == Type.ENTITY && r.entityHit instanceof EntityLivingBase && CompatibilityConfig.isWarDim(world)){
				EntityLivingBase ent = ((EntityLivingBase)r.entityHit);
				if(ent instanceof EntityPlayer && ((EntityPlayer)ent).isCreative()){
					return;
				}
				this.charge = this.charge * this.chargeScaling;
				float damage = Math.min(ent.getHealth(), this.charge);
				ent.getCombatTracker().trackDamage(ModDamageSource.gluon, ent.getHealth(), damage);
				ent.setHealth(ent.getHealth()-damage);
				
				PacketDispatcher.wrapper.sendToAllTracking(new PacketSpecialDeath(ent, 1), ent);
				//Why doesn't the player count as tracking itself? I don't know.
				if(ent instanceof EntityPlayerMP){
					PacketDispatcher.wrapper.sendTo(new PacketSpecialDeath(ent, 1), (EntityPlayerMP) ent);
				}
				if(ent.getHealth() <= 0){
					PacketDispatcher.wrapper.sendToAllTracking(new PacketSpecialDeath(ent, 0), ent);
					ent.setDead();
					ent.onDeath(ModDamageSource.gluon);
					//For ender dragon so it spawns a portal and all that
					ent.onKillCommand();
					
					if(ent instanceof EntityPlayerMP){
						PacketDispatcher.wrapper.sendTo(new PacketSpecialDeath(ent, 0), (EntityPlayerMP) ent);
					}
				}
			} else {
				this.charge = 1F;
			}	
		} else {
			setIsFiring(stack, false);
		}
	}
	
	/// if the gun is firing ///
	public static void setIsFiring(ItemStack stack, boolean b) {
		writeNBT(stack, "egonFiring", b ? 1 : 0);
	}
	
	public static boolean getIsFiring(ItemStack stack) {
		return readNBT(stack, "egonFiring") == 1;
	}
	
	@Override
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		return false;
	}
	
	public static float getFirstPersonAnimFade(EntityPlayer ent){
		if(ent.getHeldItemMainhand().getItem() != ModItems.gun_egon)
			return 0;
		return MathHelper.clamp((m1 && Library.countInventoryItem(ent.inventory, getBeltType(ent, ent.getHeldItemMainhand(), true)) >= 2 ? activeTicks + MainRegistry.proxy.partialTicks() : activeTicks - MainRegistry.proxy.partialTicks() )/5F, 0, 1);
	}
	
	public static float[] getBeamDirectionOffset(float time){
		float sinval = MathHelper.sin(time*1.2F)+MathHelper.sin(time*0.8F-10)+MathHelper.sin(time*1.0F+10);
		sinval/=3;
		float sinval2 = MathHelper.sin(time*0.6F)+MathHelper.sin(time*0.2F+20)+MathHelper.sin(time*0.1F+20);
		sinval/=3;
		return new float[]{BobMathUtil.remap(sinval, -1, 1, -3, 3), BobMathUtil.remap(sinval2, -1, 1, -0.5F, 0.5F)};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasCustomHudElement() {
		return true;
	}
	
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
}
