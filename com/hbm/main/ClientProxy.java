package com.hbm.main;

import java.io.File;
import java.util.function.Function;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.logic.EntityEMP;
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
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntityOrangeFX;
import com.hbm.entity.particle.EntityPinkCloudFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.entity.particle.ParticleContrail;
import com.hbm.entity.projectile.EntityAAShell;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.entity.projectile.EntityBoxcar;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityDuchessGambit;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.entity.projectile.EntityRocket;
import com.hbm.entity.projectile.EntityRocketHoming;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.entity.GasFlameRenderer;
import com.hbm.render.entity.RenderAAShell;
import com.hbm.render.entity.RenderBeam5;
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
import com.hbm.render.entity.RenderNoCloud;
import com.hbm.render.entity.RenderRocket;
import com.hbm.render.entity.RenderSRocket;
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
import com.hbm.render.item.ItemRenderGunAnim;
import com.hbm.render.tileentity.RenderAssembler;
import com.hbm.render.tileentity.RenderCIWSTurret;
import com.hbm.render.tileentity.RenderCable;
import com.hbm.render.tileentity.RenderCheapoTurret;
import com.hbm.render.tileentity.RenderChemplant;
import com.hbm.render.tileentity.RenderCloudResidue;
import com.hbm.render.tileentity.RenderDecoBlock;
import com.hbm.render.tileentity.RenderFlamerTurret;
import com.hbm.render.tileentity.RenderFluidDuct;
import com.hbm.render.tileentity.RenderHeavyTurret;
import com.hbm.render.tileentity.RenderLaunchPadTier1;
import com.hbm.render.tileentity.RenderLightTurret;
import com.hbm.render.tileentity.RenderNukeFleija;
import com.hbm.render.tileentity.RenderNukeMan;
import com.hbm.render.tileentity.RenderPress;
import com.hbm.render.tileentity.RenderRocketTurret;
import com.hbm.render.tileentity.RenderSmallReactor;
import com.hbm.render.tileentity.RenderSpitfireTurret;
import com.hbm.render.tileentity.RenderTaint;
import com.hbm.render.tileentity.RenderTauTurret;
import com.hbm.render.tileentity.RenderTestRender;
import com.hbm.render.util.HmfModelLoader;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
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
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.tileentity.deco.TileEntityTestRender;
import com.hbm.tileentity.generic.TileEntityCloudResidue;
import com.hbm.tileentity.generic.TileEntityTaint;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFogFX.class, new RenderFogRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDSmokeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.d_smoke1, ModItems.d_smoke2, ModItems.d_smoke3, ModItems.d_smoke4, ModItems.d_smoke5, ModItems.d_smoke6, ModItems.d_smoke7, ModItems.d_smoke8}));
		RenderingRegistry.registerEntityRenderingHandler(EntityOrangeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.orange1, ModItems.orange2, ModItems.orange3, ModItems.orange4, ModItems.orange5, ModItems.orange6, ModItems.orange7, ModItems.orange8}));
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
		
		ModelLoader.setCustomStateMapper(ModBlocks.toxic_block, new StateMap.Builder().ignore(BlockFluidClassic.LEVEL).build());
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
	}
	
	public static IBakedModel boxcar;
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		
		try {
			IModel model = OBJLoader.INSTANCE.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
			boxcar = model.bake(model.getDefaultState(), DefaultVertexFormats.POSITION_TEX_NORMAL, BoxcarTextureGetter.INSTANCE);
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
