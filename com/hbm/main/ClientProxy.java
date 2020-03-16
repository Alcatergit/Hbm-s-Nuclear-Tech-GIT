package com.hbm.main;

import java.io.File;
import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.EntityGrenadeTau;
import com.hbm.blocks.machine.BlockSeal;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.grenade.EntityGrenadeASchrab;
import com.hbm.entity.grenade.EntityGrenadeBlackHole;
import com.hbm.entity.grenade.EntityGrenadeBreach;
import com.hbm.entity.grenade.EntityGrenadeBurst;
import com.hbm.entity.grenade.EntityGrenadeCloud;
import com.hbm.entity.grenade.EntityGrenadeCluster;
import com.hbm.entity.grenade.EntityGrenadeElectric;
import com.hbm.entity.grenade.EntityGrenadeFire;
import com.hbm.entity.grenade.EntityGrenadeFlare;
import com.hbm.entity.grenade.EntityGrenadeFrag;
import com.hbm.entity.grenade.EntityGrenadeGas;
import com.hbm.entity.grenade.EntityGrenadeGascan;
import com.hbm.entity.grenade.EntityGrenadeGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFBouncy;
import com.hbm.entity.grenade.EntityGrenadeIFBrimstone;
import com.hbm.entity.grenade.EntityGrenadeIFConcussion;
import com.hbm.entity.grenade.EntityGrenadeIFGeneric;
import com.hbm.entity.grenade.EntityGrenadeIFHE;
import com.hbm.entity.grenade.EntityGrenadeIFHopwire;
import com.hbm.entity.grenade.EntityGrenadeIFImpact;
import com.hbm.entity.grenade.EntityGrenadeIFIncendiary;
import com.hbm.entity.grenade.EntityGrenadeIFMystery;
import com.hbm.entity.grenade.EntityGrenadeIFNull;
import com.hbm.entity.grenade.EntityGrenadeIFSpark;
import com.hbm.entity.grenade.EntityGrenadeIFSticky;
import com.hbm.entity.grenade.EntityGrenadeIFToxic;
import com.hbm.entity.grenade.EntityGrenadeLemon;
import com.hbm.entity.grenade.EntityGrenadeMIRV;
import com.hbm.entity.grenade.EntityGrenadeMk2;
import com.hbm.entity.grenade.EntityGrenadeNuclear;
import com.hbm.entity.grenade.EntityGrenadeNuke;
import com.hbm.entity.grenade.EntityGrenadePC;
import com.hbm.entity.grenade.EntityGrenadePlasma;
import com.hbm.entity.grenade.EntityGrenadePoison;
import com.hbm.entity.grenade.EntityGrenadePulse;
import com.hbm.entity.grenade.EntityGrenadeSchrabidium;
import com.hbm.entity.grenade.EntityGrenadeShrapnel;
import com.hbm.entity.grenade.EntityGrenadeSmart;
import com.hbm.entity.grenade.EntityGrenadeStrong;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.logic.EntityBlast;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.missile.EntityBombletSelena;
import com.hbm.entity.missile.EntityBombletTheta;
import com.hbm.entity.missile.EntityBooster;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityChlorineFX;
import com.hbm.entity.particle.EntityCloudFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOilSpillFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.particle.ParticleContrail;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBaleflare;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityLN2;
import com.hbm.entity.projectile.EntityMiniMIRV;
import com.hbm.entity.projectile.EntityMiniNuke;
import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityPlasmaBeam;
import com.hbm.entity.projectile.EntityRailgunBlast;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntitySchrab;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.entity.projectile.EntitySparkBeam;
import com.hbm.handler.HbmShaderManager;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.entity.ElectricityRenderer;
import com.hbm.render.entity.GasFlameRenderer;
import com.hbm.render.entity.GasRenderer;
import com.hbm.render.entity.RenderAAShell;
import com.hbm.render.entity.RenderBaleflare;
import com.hbm.render.entity.RenderBeam;
import com.hbm.render.entity.RenderBeam4;
import com.hbm.render.entity.RenderBeam5;
import com.hbm.render.entity.RenderBeam6;
import com.hbm.render.entity.RenderBlackHole;
import com.hbm.render.entity.RenderBoat;
import com.hbm.render.entity.RenderBomber;
import com.hbm.render.entity.RenderBombletSelena;
import com.hbm.render.entity.RenderBombletTheta;
import com.hbm.render.entity.RenderBombletZeta;
import com.hbm.render.entity.RenderBoxcar;
import com.hbm.render.entity.RenderBullet;
import com.hbm.render.entity.RenderBulletMk2;
import com.hbm.render.entity.RenderCloudFleija;
import com.hbm.render.entity.RenderCloudRainbow;
import com.hbm.render.entity.RenderEMPBlast;
import com.hbm.render.entity.RenderEmpty;
import com.hbm.render.entity.RenderFireProjectile;
import com.hbm.render.entity.RenderFlare;
import com.hbm.render.entity.RenderLN2;
import com.hbm.render.entity.RenderMiniMIRV;
import com.hbm.render.entity.RenderMiniNuke;
import com.hbm.render.entity.RenderNoCloud;
import com.hbm.render.entity.RenderNukeMK4;
import com.hbm.render.entity.RenderRagingVortex;
import com.hbm.render.entity.RenderRainbow;
import com.hbm.render.entity.RenderRocket;
import com.hbm.render.entity.RenderSRocket;
import com.hbm.render.entity.RenderTom;
import com.hbm.render.entity.RenderVortex;
import com.hbm.render.entity.SpillRenderer;
import com.hbm.render.entity.TSmokeRenderer;
import com.hbm.render.entity.missile.RenderBoosterMissile;
import com.hbm.render.entity.missile.RenderCarrierMissile;
import com.hbm.render.entity.missile.RenderMissileAB;
import com.hbm.render.entity.missile.RenderMissileBHole;
import com.hbm.render.entity.missile.RenderMissileBunkerBuster;
import com.hbm.render.entity.missile.RenderMissileBurst;
import com.hbm.render.entity.missile.RenderMissileBusterStrong;
import com.hbm.render.entity.missile.RenderMissileCluster;
import com.hbm.render.entity.missile.RenderMissileClusterStrong;
import com.hbm.render.entity.missile.RenderMissileDoomsday;
import com.hbm.render.entity.missile.RenderMissileDrill;
import com.hbm.render.entity.missile.RenderMissileEMP;
import com.hbm.render.entity.missile.RenderMissileEMPStrong;
import com.hbm.render.entity.missile.RenderMissileEndo;
import com.hbm.render.entity.missile.RenderMissileExo;
import com.hbm.render.entity.missile.RenderMissileGeneric;
import com.hbm.render.entity.missile.RenderMissileIncendiary;
import com.hbm.render.entity.missile.RenderMissileIncendiaryStrong;
import com.hbm.render.entity.missile.RenderMissileInferno;
import com.hbm.render.entity.missile.RenderMissileMicro;
import com.hbm.render.entity.missile.RenderMissileMirv;
import com.hbm.render.entity.missile.RenderMissileNuclear;
import com.hbm.render.entity.missile.RenderMissileRain;
import com.hbm.render.entity.missile.RenderMissileSchrabidium;
import com.hbm.render.entity.missile.RenderMissileStrong;
import com.hbm.render.entity.missile.RenderMissileTaint;
import com.hbm.render.factories.MultiCloudRendererFactory;
import com.hbm.render.factories.RenderBurningFOEQFactory;
import com.hbm.render.factories.RenderFalloutRainFactory;
import com.hbm.render.factories.RenderFogRenderFactory;
import com.hbm.render.factories.RenderNuclearCreeperFactory;
import com.hbm.render.factories.RenderRubbleFactory;
import com.hbm.render.factories.RenderSSmokeFactory;
import com.hbm.render.factories.RenderSmallNukeMK3Factory;
import com.hbm.render.factories.RenderTaintedCreeperFactory;
import com.hbm.render.factories.ShrapnelRendererFactory;
import com.hbm.render.item.AssemblyTemplateRender;
import com.hbm.render.item.ChemTemplateRender;
import com.hbm.render.item.FFIdentifierRender;
import com.hbm.render.item.FluidBarrelRender;
import com.hbm.render.item.FluidCanisterRender;
import com.hbm.render.item.FluidTankRender;
import com.hbm.render.item.GunRevolverRender;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderBFLauncher;
import com.hbm.render.item.ItemRenderBullshit;
import com.hbm.render.item.ItemRenderCalamity;
import com.hbm.render.item.ItemRenderCell;
import com.hbm.render.item.ItemRenderCryolator;
import com.hbm.render.item.ItemRenderEMPRay;
import com.hbm.render.item.ItemRenderEuthanasia;
import com.hbm.render.item.ItemRenderFatMan;
import com.hbm.render.item.ItemRenderFolly;
import com.hbm.render.item.ItemRenderGunAnim;
import com.hbm.render.item.ItemRenderGunAnim2;
import com.hbm.render.item.ItemRenderGunDefab;
import com.hbm.render.item.ItemRenderGunHP;
import com.hbm.render.item.ItemRenderGunJack;
import com.hbm.render.item.ItemRenderGunSaturnite;
import com.hbm.render.item.ItemRenderGunSonata;
import com.hbm.render.item.ItemRenderImmolator;
import com.hbm.render.item.ItemRenderMIRVLauncher;
import com.hbm.render.item.ItemRenderMP;
import com.hbm.render.item.ItemRenderMP40;
import com.hbm.render.item.ItemRenderMinigun;
import com.hbm.render.item.ItemRenderOSIPR;
import com.hbm.render.item.ItemRenderOverkill;
import com.hbm.render.item.ItemRenderRevolverCursed;
import com.hbm.render.item.ItemRenderRevolverGold;
import com.hbm.render.item.ItemRenderRevolverInverted;
import com.hbm.render.item.ItemRenderRevolverIron;
import com.hbm.render.item.ItemRenderRevolverLead;
import com.hbm.render.item.ItemRenderRevolverNightmare;
import com.hbm.render.item.ItemRenderRevolverSaturnite;
import com.hbm.render.item.ItemRenderRevolverSchrabidium;
import com.hbm.render.item.ItemRenderRpg;
import com.hbm.render.item.ItemRenderStinger;
import com.hbm.render.item.ItemRenderUboinik;
import com.hbm.render.item.ItemRenderUzi;
import com.hbm.render.item.ItemRenderWeaponObj;
import com.hbm.render.item.ItemRenderXVL1456;
import com.hbm.render.item.ItemRenderZOMG;
import com.hbm.render.item.RenderGunB93;
import com.hbm.render.tileentity.RenderAssembler;
import com.hbm.render.tileentity.RenderBlastDoor;
import com.hbm.render.tileentity.RenderBroadcaster;
import com.hbm.render.tileentity.RenderCIWSTurret;
import com.hbm.render.tileentity.RenderCable;
import com.hbm.render.tileentity.RenderCentrifuge;
import com.hbm.render.tileentity.RenderCheapoTurret;
import com.hbm.render.tileentity.RenderChemplant;
import com.hbm.render.tileentity.RenderCloudResidue;
import com.hbm.render.tileentity.RenderCyclotron;
import com.hbm.render.tileentity.RenderDecoBlock;
import com.hbm.render.tileentity.RenderDerrick;
import com.hbm.render.tileentity.RenderEPress;
import com.hbm.render.tileentity.RenderFlamerTurret;
import com.hbm.render.tileentity.RenderFluidDuct;
import com.hbm.render.tileentity.RenderFluidTank;
import com.hbm.render.tileentity.RenderGasCent;
import com.hbm.render.tileentity.RenderGasDuct;
import com.hbm.render.tileentity.RenderGasFlare;
import com.hbm.render.tileentity.RenderGeiger;
import com.hbm.render.tileentity.RenderHeavyTurret;
import com.hbm.render.tileentity.RenderLaunchPadTier1;
import com.hbm.render.tileentity.RenderLightTurret;
import com.hbm.render.tileentity.RenderMiningDrill;
import com.hbm.render.tileentity.RenderNukeFleija;
import com.hbm.render.tileentity.RenderNukeMan;
import com.hbm.render.tileentity.RenderOilDuct;
import com.hbm.render.tileentity.RenderPress;
import com.hbm.render.tileentity.RenderPuF6Tank;
import com.hbm.render.tileentity.RenderPumpjack;
import com.hbm.render.tileentity.RenderPylon;
import com.hbm.render.tileentity.RenderRadGen;
import com.hbm.render.tileentity.RenderRailgun;
import com.hbm.render.tileentity.RenderRefinery;
import com.hbm.render.tileentity.RenderRocketTurret;
import com.hbm.render.tileentity.RenderSelenium;
import com.hbm.render.tileentity.RenderSmallReactor;
import com.hbm.render.tileentity.RenderSpitfireTurret;
import com.hbm.render.tileentity.RenderTaint;
import com.hbm.render.tileentity.RenderTauTurret;
import com.hbm.render.tileentity.RenderTestRender;
import com.hbm.render.tileentity.RenderUF6Tank;
import com.hbm.render.tileentity.RenderVaultDoor;
import com.hbm.render.util.HmfModelLoader;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityRailgun;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.bomb.TileEntityTurretFlamer;
import com.hbm.tileentity.bomb.TileEntityTurretHeavy;
import com.hbm.tileentity.bomb.TileEntityTurretLight;
import com.hbm.tileentity.bomb.TileEntityTurretRocket;
import com.hbm.tileentity.bomb.TileEntityTurretSpitfire;
import com.hbm.tileentity.bomb.TileEntityTurretTau;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuct;
import com.hbm.tileentity.conductor.TileEntityFFGasDuct;
import com.hbm.tileentity.conductor.TileEntityFFOilDuct;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.generic.TileEntityCloudResidue;
import com.hbm.tileentity.generic.TileEntityTaint;
import com.hbm.tileentity.machine.TileEntityBlastDoor;
import com.hbm.tileentity.machine.TileEntityBroadcaster;
import com.hbm.tileentity.machine.TileEntityGeiger;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.tileentity.machine.TileEntityMachineEPress;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineGasFlare;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineRefinery;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;
import com.hbm.tileentity.machine.TileEntityPylonRedWire;
import com.hbm.tileentity.machine.TileEntityVaultDoor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	
	public static final ModelResourceLocation IRRELEVANT_MRL = new ModelResourceLocation("hbm:placeholdermodel", "inventory");
	
	@Override
	public File getDataDir() {
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public void registerRenderInfo()
	{
		if(!Minecraft.getMinecraft().getFramebuffer().isStencilEnabled())
			Minecraft.getMinecraft().getFramebuffer().enableStencil();
		
		MinecraftForge.EVENT_BUS.register(new ModEventHandlerClient());
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		
		HbmShaderManager.loadShaders();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePress.class, new RenderPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new RenderAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTaint.class, new RenderTaint());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTestRender.class, new RenderTestRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChemplant.class, new RenderChemplant());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCloudResidue.class, new RenderCloudResidue());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeMan.class, new RenderNukeMan());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityNukeFleija.class, new RenderNukeFleija());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineReactorSmall.class, new RenderSmallReactor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFFFluidDuct.class, new RenderFluidDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFFOilDuct.class, new RenderOilDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFFGasDuct.class, new RenderGasDuct());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretCheapo.class, new RenderCheapoTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretRocket.class, new RenderRocketTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretLight.class, new RenderLightTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretHeavy.class, new RenderHeavyTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretFlamer.class, new RenderFlamerTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretTau.class, new RenderTauTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretSpitfire.class, new RenderSpitfireTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretCIWS.class, new RenderCIWSTurret());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecoBlock.class, new RenderDecoBlock());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaunchPad.class, new RenderLaunchPadTier1());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineEPress.class, new RenderEPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPylonRedWire.class, new RenderPylon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCentrifuge.class, new RenderCentrifuge());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasCent.class, new RenderGasCent());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineUF6Tank.class, new RenderUF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePuF6Tank.class, new RenderPuF6Tank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRailgun.class, new RenderRailgun());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineFluidTank.class, new RenderFluidTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRefinery.class, new RenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineCyclotron.class, new RenderCyclotron());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBroadcaster.class, new RenderBroadcaster());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGeiger.class, new RenderGeiger());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVaultDoor.class, new RenderVaultDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlastDoor.class, new RenderBlastDoor());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineSeleniumEngine.class, new RenderSelenium());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineRadGen.class, new RenderRadGen());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineOilWell.class, new RenderDerrick());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePumpjack.class, new RenderPumpjack());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineGasFlare.class, new RenderGasFlare());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineMiningDrill.class, new RenderMiningDrill());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFogFX.class, new RenderFogRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDSmokeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.d_smoke1, ModItems.d_smoke2, ModItems.d_smoke3, ModItems.d_smoke4, ModItems.d_smoke5, ModItems.d_smoke6, ModItems.d_smoke7, ModItems.d_smoke8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityOrangeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.orange1, ModItems.orange2, ModItems.orange3, ModItems.orange4, ModItems.orange5, ModItems.orange6, ModItems.orange7, ModItems.orange8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityCloudFX.class, new MultiCloudRendererFactory(new Item[]{ModItems.cloud1, ModItems.cloud2, ModItems.cloud3, ModItems.cloud4, ModItems.cloud5, ModItems.cloud6, ModItems.cloud7, ModItems.cloud8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityPinkCloudFX.class, new MultiCloudRendererFactory(new Item[] { ModItems.pc1, ModItems.pc2, ModItems.pc3, ModItems.pc4, ModItems.pc5, ModItems.pc6, ModItems.pc7, ModItems.pc8 }));
		RenderingRegistry.registerEntityRenderingHandler(EntityChlorineFX.class, new MultiCloudRendererFactory(new Item[] { ModItems.chlorine1, ModItems.chlorine2, ModItems.chlorine3, ModItems.chlorine4, ModItems.chlorine5, ModItems.chlorine6, ModItems.chlorine7, ModItems.chlorine8 }));
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudSmall.class, new RenderSmallNukeMK3Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTaintedCreeper.class, new RenderTaintedCreeperFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityNuclearCreeper.class, new RenderNuclearCreeperFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFalloutRain.class, new RenderFalloutRainFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntitySmokeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.smoke1, ModItems.smoke2, ModItems.smoke3, ModItems.smoke4, ModItems.smoke5, ModItems.smoke6, ModItems.smoke7, ModItems.smoke8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityBSmokeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.b_smoke1, ModItems.b_smoke2, ModItems.b_smoke3, ModItems.b_smoke4, ModItems.b_smoke5, ModItems.b_smoke6, ModItems.b_smoke7, ModItems.b_smoke8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityShrapnel.class, new ShrapnelRendererFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntitySSmokeFX.class, new RenderSSmokeFactory(ModItems.nuclear_waste));
		RenderingRegistry.registerEntityRenderingHandler(EntityRubble.class, new RenderRubbleFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBurningFOEQ.class, new RenderBurningFOEQFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleijaRainbow.class, RenderCloudRainbow.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveBeam.class, RenderBeam5.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeCloudNoShroom.class, RenderNoCloud.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCloudFleija.class, RenderCloudFleija.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderBullet.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityGasFlameFX.class, GasFlameRenderer.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, RenderRocket.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityFire.class, RenderFireProjectile.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityAAShell.class, RenderAAShell.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBomber.class, RenderBomber.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileGeneric.class, RenderMissileGeneric.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRocketHoming.class, RenderSRocket.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityTSmokeFX.class, TSmokeRenderer.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBoxcar.class, RenderBoxcar.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBombletZeta.class, RenderBombletZeta.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiary.class, RenderMissileIncendiary.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileCluster.class, RenderMissileCluster.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBunkerBuster.class, RenderMissileBunkerBuster.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileStrong.class, RenderMissileStrong.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileIncendiaryStrong.class, RenderMissileIncendiaryStrong.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileClusterStrong.class, RenderMissileClusterStrong.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBusterStrong.class, RenderMissileBusterStrong.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMPStrong.class, RenderMissileEMPStrong.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityEMP.class, RenderEmpty.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBurst.class, RenderMissileBurst.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileInferno.class, RenderMissileInferno.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileRain.class, RenderMissileRain.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDrill.class, RenderMissileDrill.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileNuclear.class, RenderMissileNuclear.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileMirv.class, RenderMissileMirv.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEndo.class, RenderMissileEndo.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileExo.class, RenderMissileExo.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBombletTheta.class, RenderBombletTheta.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBombletSelena.class, RenderBombletSelena.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileDoomsday.class, RenderMissileDoomsday.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileTaint.class, RenderMissileTaint.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileMicro.class, RenderMissileMicro.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileBHole.class, RenderMissileBHole.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, RenderBlackHole.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileSchrabidium.class, RenderMissileSchrabidium.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityEMPBlast.class, RenderEMPBlast.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileEMP.class, RenderMissileEMP.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMissileAntiBallistic.class, RenderMissileAB.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBooster.class, RenderBoosterMissile.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrier.class, RenderCarrierMissile.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletBase.class, RenderBulletMk2.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityDuchessGambit.class, RenderBoat.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntitySparkBeam.class, RenderBeam4.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityModBeam.class, RenderBeam6.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityVortex.class, RenderVortex.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRagingVortex.class, RenderRagingVortex.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeExplosionMK4.class, RenderNukeMK4.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniNuke.class, RenderMiniNuke.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityMiniMIRV.class, RenderMiniMIRV.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBaleflare.class, RenderBaleflare.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityRainbow.class, RenderRainbow.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityPlasmaBeam.class, RenderBeam.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityLN2.class, RenderLN2.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityCombineBall.class, (RenderManager man) -> {return new RenderSnowball<EntityCombineBall>(man, ModItems.energy_ball, Minecraft.getMinecraft().getRenderItem()){
			@Override
			public void doRender(EntityCombineBall entity, double x, double y, double z, float entityYaw, float partialTicks)
		    {
		        GlStateManager.disableLighting();
		        super.doRender(entity, x, y, z, entityYaw, partialTicks);
		        GlStateManager.enableLighting();
		    }
		};});
		RenderingRegistry.registerEntityRenderingHandler(EntityDischarge.class, ElectricityRenderer.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeGeneric.class, (RenderManager man) -> {return new RenderSnowball<EntityGrenadeGeneric>(man, ModItems.grenade_generic, Minecraft.getMinecraft().getRenderItem());});
		registerGrenadeRenderer(EntityGrenadeStrong.class, ModItems.grenade_strong);
		registerGrenadeRenderer(EntityGrenadeFrag.class, ModItems.grenade_frag);
		registerGrenadeRenderer(EntityGrenadeFire.class, ModItems.grenade_fire);
		registerGrenadeRenderer(EntityGrenadeCluster.class, ModItems.grenade_cluster);
		RenderingRegistry.registerEntityRenderingHandler(EntityGrenadeFlare.class, RenderFlare.FACTORY);
		registerGrenadeRenderer(EntityGrenadeElectric.class, ModItems.grenade_electric);
		registerGrenadeRenderer(EntityGrenadePoison.class, ModItems.grenade_poison);
		registerGrenadeRenderer(EntityGrenadeGas.class, ModItems.grenade_gas);
		RenderingRegistry.registerEntityRenderingHandler(EntitySchrab.class, RenderFlare.FACTORY_SCHRAB);
		registerGrenadeRenderer(EntityGrenadeSchrabidium.class, ModItems.grenade_schrabidium);
		registerGrenadeRenderer(EntityGrenadePulse.class, ModItems.grenade_pulse);
		registerGrenadeRenderer(EntityGrenadePlasma.class, ModItems.grenade_plasma);
		registerGrenadeRenderer(EntityGrenadeTau.class, ModItems.grenade_tau);
		registerGrenadeRenderer(EntityGrenadeCloud.class, ModItems.grenade_cloud);
		registerGrenadeRenderer(EntityGrenadePC.class, ModItems.grenade_pink_cloud);
		registerGrenadeRenderer(EntityGrenadeSmart.class, ModItems.grenade_smart);
		registerGrenadeRenderer(EntityGrenadeMIRV.class, ModItems.grenade_mirv);
		registerGrenadeRenderer(EntityGrenadeBreach.class, ModItems.grenade_breach);
		registerGrenadeRenderer(EntityGrenadeBurst.class, ModItems.grenade_burst);
		registerGrenadeRenderer(EntityGrenadeLemon.class, ModItems.grenade_lemon);
		registerGrenadeRenderer(EntityGrenadeMk2.class, ModItems.grenade_mk2);
		registerGrenadeRenderer(EntityGrenadeASchrab.class, ModItems.grenade_aschrab);
		registerGrenadeRenderer(EntityGrenadeZOMG.class, ModItems.grenade_zomg);
		registerGrenadeRenderer(EntityGrenadeShrapnel.class, ModItems.grenade_shrapnel);
		registerGrenadeRenderer(EntityGrenadeBlackHole.class, ModItems.grenade_black_hole);
		registerGrenadeRenderer(EntityGrenadeGascan.class, ModItems.grenade_gascan);
		registerGrenadeRenderer(EntityGrenadeNuke.class, ModItems.grenade_nuke);
		registerGrenadeRenderer(EntityGrenadeNuclear.class, ModItems.grenade_nuclear);
		registerGrenadeRenderer(EntityGrenadeIFGeneric.class, ModItems.grenade_if_generic);
		registerGrenadeRenderer(EntityGrenadeIFHE.class, ModItems.grenade_if_he);
		registerGrenadeRenderer(EntityGrenadeIFBouncy.class, ModItems.grenade_if_bouncy);
		registerGrenadeRenderer(EntityGrenadeIFSticky.class, ModItems.grenade_if_sticky);
		registerGrenadeRenderer(EntityGrenadeIFImpact.class, ModItems.grenade_if_impact);
		registerGrenadeRenderer(EntityGrenadeIFIncendiary.class, ModItems.grenade_if_incendiary);
		registerGrenadeRenderer(EntityGrenadeIFToxic.class, ModItems.grenade_if_toxic);
		registerGrenadeRenderer(EntityGrenadeIFConcussion.class, ModItems.grenade_if_concussion);
		registerGrenadeRenderer(EntityGrenadeIFBrimstone.class, ModItems.grenade_if_brimstone);
		registerGrenadeRenderer(EntityGrenadeIFMystery.class, ModItems.grenade_if_mystery);
		registerGrenadeRenderer(EntityGrenadeIFSpark.class, ModItems.grenade_if_spark);
		registerGrenadeRenderer(EntityGrenadeIFHopwire.class, ModItems.grenade_if_hopwire);
		registerGrenadeRenderer(EntityGrenadeIFNull.class, ModItems.grenade_if_null);
		RenderingRegistry.registerEntityRenderingHandler(EntityRailgunBlast.class, RenderTom.RAIL_FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityBlast.class, RenderEmpty.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityNukeExplosionMK3.class, RenderEmpty.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityGasFX.class, GasRenderer.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityOilSpill.class, RenderEmpty.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(EntityOilSpillFX.class, SpillRenderer.FACTORY);
		
		ModelLoader.setCustomStateMapper(ModBlocks.toxic_block, new StateMap.Builder().ignore(BlockFluidClassic.LEVEL).build());
		ModelLoader.setCustomStateMapper(ModBlocks.seal_controller, new StateMap.Builder().ignore(BlockSeal.ACTIVATED).build());
	}
	
	private <E extends Entity> void registerGrenadeRenderer(Class<E> clazz, Item grenade) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, (RenderManager man) -> {return new RenderSnowball<E>(man, grenade, Minecraft.getMinecraft().getRenderItem());});
	}
	
	@Override
	public void registerMissileItems() {
		
	}
	@Override
	public void registerTileEntitySpecialRenderer() {
		
	}
	@Override
	public void particleControl(double x, double y, double z, int type) {
		World world = Minecraft.getMinecraft().world;
		
		switch(type) {
		case 0:
			
			for(int i = 0; i < 10; i++) {
				Particle smoke = new ParticleCloud.Factory().createParticle(EnumParticleTypes.CLOUD.getParticleID(), world, x + world.rand.nextGaussian(), y + world.rand.nextGaussian(), z + world.rand.nextGaussian(), 0.0, 0.0, 0.0);
				Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
			}
			break;
			
		case 1:
			Particle s = new ParticleCloud.Factory().createParticle(EnumParticleTypes.CLOUD.getParticleID(), world, x, y, z, 0.0, 0.1, 0.0);
			Minecraft.getMinecraft().effectRenderer.addEffect(s);
			
			break;
			
		case 2:
			
			ParticleContrail contrail = new ParticleContrail(Minecraft.getMinecraft().renderEngine, world, x, y, z);
			Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
			break;
		}
	}
	//version 2, now with strings!
	@Override
	public void spawnParticle(double x, double y, double z, String type, float args[]) {
		
	}
	
	@Override
	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Vec3 payload) {
		int pow = 250;
		float angle = 25;
		float base = 0.5F;
		for(int i = 0; i < pow; i++) {

			float momentum = base * world.rand.nextFloat();
			float sway = (pow - i) / (float)pow;
			Vec3 vec = Vec3.createVectorHelper(((Vec3)payload).xCoord, ((Vec3)payload).yCoord, ((Vec3)payload).zCoord);
			vec.rotateAroundZ((float) (angle * world.rand.nextGaussian() * sway * Math.PI / 180D));
			vec.rotateAroundY((float) (angle * world.rand.nextGaussian() * sway * Math.PI / 180D));
			
			ParticleFirework.Spark blast = new ParticleFirework.Spark(world, posX, posY, posZ, vec.xCoord * momentum, vec.yCoord * momentum, vec.zCoord * momentum, Minecraft.getMinecraft().effectRenderer);
			
			if(world.rand.nextBoolean())
				blast.setColor(0x0088EA);
			else
				blast.setColor(0x52A8E6);
			
			Minecraft.getMinecraft().effectRenderer.addEffect(blast);
		}
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent evt){
		OBJLoader.INSTANCE.addDomain("hbm");
		ModItems.redstone_sword.setTileEntityItemStackRenderer(ItemRedstoneSwordRender.INSTANCE);
		ModItems.assembly_template.setTileEntityItemStackRenderer(AssemblyTemplateRender.INSTANCE);
		ModItems.gun_b92.setTileEntityItemStackRenderer(ItemRenderGunAnim.INSTANCE);
		ModItems.fluid_tank_full.setTileEntityItemStackRenderer(FluidTankRender.INSTANCE);
		ModItems.fluid_barrel_full.setTileEntityItemStackRenderer(FluidBarrelRender.INSTANCE);
		ModItems.canister_generic.setTileEntityItemStackRenderer(FluidCanisterRender.INSTANCE);
		ModItems.chemistry_template.setTileEntityItemStackRenderer(ChemTemplateRender.INSTANCE);
		ModItems.forge_fluid_identifier.setTileEntityItemStackRenderer(FFIdentifierRender.INSTANCE);
		ModItems.gun_revolver.setTileEntityItemStackRenderer(GunRevolverRender.INSTANCE);
		ModItems.gun_revolver_nightmare.setTileEntityItemStackRenderer(new ItemRenderRevolverNightmare());
		ModItems.gun_revolver_nightmare2.setTileEntityItemStackRenderer(new ItemRenderRevolverNightmare());
		ModItems.gun_revolver_iron.setTileEntityItemStackRenderer(new ItemRenderRevolverIron());
		ModItems.gun_revolver_gold.setTileEntityItemStackRenderer(new ItemRenderRevolverGold());
		ModItems.gun_revolver_lead.setTileEntityItemStackRenderer(new ItemRenderRevolverLead());
		ModItems.gun_revolver_schrabidium.setTileEntityItemStackRenderer(new ItemRenderRevolverSchrabidium());
		ModItems.gun_revolver_cursed.setTileEntityItemStackRenderer(new ItemRenderRevolverCursed());
		ModItems.gun_revolver_pip.setTileEntityItemStackRenderer(new ItemRenderOverkill());
		ModItems.gun_revolver_nopip.setTileEntityItemStackRenderer(new ItemRenderOverkill());
		ModItems.gun_revolver_blackjack.setTileEntityItemStackRenderer(new ItemRenderOverkill());
		ModItems.gun_revolver_red.setTileEntityItemStackRenderer(new ItemRenderOverkill());
		ModItems.gun_lever_action.setTileEntityItemStackRenderer(new ItemRenderGunAnim2());
		ModItems.gun_spark.setTileEntityItemStackRenderer(new ItemRenderOverkill());
		ModItems.gun_b93.setTileEntityItemStackRenderer(new RenderGunB93());
		ModItems.gun_rpg.setTileEntityItemStackRenderer(new ItemRenderRpg());
		ModItems.gun_karl.setTileEntityItemStackRenderer(new ItemRenderRpg());
		ModItems.gun_panzerschreck.setTileEntityItemStackRenderer(new ItemRenderRpg());
		ModItems.gun_hk69.setTileEntityItemStackRenderer(new ItemRenderWeaponObj());
		ModItems.gun_fatman.setTileEntityItemStackRenderer(new ItemRenderFatMan());
		ModItems.gun_proto.setTileEntityItemStackRenderer(new ItemRenderFatMan());
		ModItems.gun_mirv.setTileEntityItemStackRenderer(new ItemRenderMIRVLauncher());
		ModItems.gun_bf.setTileEntityItemStackRenderer(new ItemRenderBFLauncher());
		ModItems.gun_zomg.setTileEntityItemStackRenderer(new ItemRenderZOMG());
		ModItems.gun_xvl1456.setTileEntityItemStackRenderer(new ItemRenderXVL1456());
		ModItems.gun_hp.setTileEntityItemStackRenderer(new ItemRenderGunHP());
		ModItems.gun_defabricator.setTileEntityItemStackRenderer(new ItemRenderGunDefab());
		ModItems.gun_uboinik.setTileEntityItemStackRenderer(new ItemRenderUboinik());
		ModItems.gun_euthanasia.setTileEntityItemStackRenderer(new ItemRenderEuthanasia());
		ModItems.gun_stinger.setTileEntityItemStackRenderer(new ItemRenderStinger());
		ModItems.gun_skystinger.setTileEntityItemStackRenderer(new ItemRenderStinger());
		ModItems.gun_mp.setTileEntityItemStackRenderer(new ItemRenderMP());
		ModItems.gun_cryolator.setTileEntityItemStackRenderer(new ItemRenderCryolator());
		ModItems.gun_jack.setTileEntityItemStackRenderer(new ItemRenderGunJack());
		ModItems.gun_immolator.setTileEntityItemStackRenderer(new ItemRenderImmolator());
		ModItems.gun_osipr.setTileEntityItemStackRenderer(new ItemRenderOSIPR());
		ModItems.gun_emp.setTileEntityItemStackRenderer(new ItemRenderEMPRay());
		ModItems.gun_revolver_inverted.setTileEntityItemStackRenderer(new ItemRenderRevolverInverted());
		ModItems.gun_lever_action_sonata.setTileEntityItemStackRenderer(new ItemRenderGunSonata());
		ModItems.gun_bolt_action_saturnite.setTileEntityItemStackRenderer(new ItemRenderGunSaturnite());
		ModItems.gun_folly.setTileEntityItemStackRenderer(new ItemRenderFolly());
		ModItems.gun_dampfmaschine.setTileEntityItemStackRenderer(new ItemRenderBullshit());
		ModItems.gun_revolver_saturnite.setTileEntityItemStackRenderer(new ItemRenderRevolverSaturnite());
		ModItems.gun_calamity.setTileEntityItemStackRenderer(new ItemRenderCalamity());
		ModItems.gun_calamity_dual.setTileEntityItemStackRenderer(new ItemRenderCalamity());
		ModItems.gun_minigun.setTileEntityItemStackRenderer(new ItemRenderMinigun());
		ModItems.gun_avenger.setTileEntityItemStackRenderer(new ItemRenderMinigun());
		ModItems.gun_lacunae.setTileEntityItemStackRenderer(new ItemRenderMinigun());
		ModItems.gun_bolt_action.setTileEntityItemStackRenderer(new ItemRenderGunAnim2());
		ModItems.gun_bolt_action_green.setTileEntityItemStackRenderer(new ItemRenderGunAnim2());
		ModItems.gun_lever_action_dark.setTileEntityItemStackRenderer(new ItemRenderGunAnim2());
		ModItems.gun_uzi.setTileEntityItemStackRenderer(new ItemRenderUzi());
		ModItems.gun_uzi_silencer.setTileEntityItemStackRenderer(new ItemRenderUzi());
		ModItems.gun_uzi_saturnite.setTileEntityItemStackRenderer(new ItemRenderUzi());
		ModItems.gun_uzi_saturnite_silencer.setTileEntityItemStackRenderer(new ItemRenderUzi());
		ModItems.gun_mp40.setTileEntityItemStackRenderer(new ItemRenderMP40());
		ModItems.cell.setTileEntityItemStackRenderer(new ItemRenderCell());
	}
	
	public static IBakedModel boxcar;
	public static int boxcarCalllist;
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		
		try {
			IModel model = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
			boxcar = model.bake(model.getDefaultState(), DefaultVertexFormats.POSITION_TEX_NORMAL, BoxcarTextureGetter.INSTANCE);
			boxcarCalllist = GL11.glGenLists(1);
			GL11.glNewList(boxcarCalllist, GL11.GL_COMPILE);
			RenderHelper.renderAll(boxcar);
			GL11.glEndList();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private static enum BoxcarTextureGetter implements Function<ResourceLocation, TextureAtlasSprite>
    {
        INSTANCE;

        @Override
        public TextureAtlasSprite apply(ResourceLocation location)
        {
            return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(new ResourceLocation(RefStrings.MODID, "models/boxcarflipv").toString());
        }
    }
	
}
