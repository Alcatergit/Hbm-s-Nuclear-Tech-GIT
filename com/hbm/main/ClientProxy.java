package com.hbm.main;

import java.io.File;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.particle.ParticleCloudFX;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.entity.FogRenderer;
import com.hbm.render.entity.RenderBeam5;
import com.hbm.render.entity.RenderCloudFleija;
import com.hbm.render.entity.RenderCloudRainbow;
import com.hbm.render.entity.RenderNoCloud;
import com.hbm.render.entity.RenderRocket;
import com.hbm.render.factories.MultiCloudRendererFactory;
import com.hbm.render.factories.RenderBurningFOEQFactory;
import com.hbm.render.factories.RenderDSmokeFXFactory;
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
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderGunAnim;
import com.hbm.render.tileentity.RenderAssembler;
import com.hbm.render.tileentity.RenderCable;
import com.hbm.render.tileentity.RenderCheapoTurret;
import com.hbm.render.tileentity.RenderChemplant;
import com.hbm.render.tileentity.RenderCloudResidue;
import com.hbm.render.tileentity.RenderFluidDuct;
import com.hbm.render.tileentity.RenderNukeFleija;
import com.hbm.render.tileentity.RenderNukeMan;
import com.hbm.render.tileentity.RenderPress;
import com.hbm.render.tileentity.RenderSmallReactor;
import com.hbm.render.tileentity.RenderTaint;
import com.hbm.render.tileentity.RenderTestRender;
import com.hbm.render.util.HmfModelLoader;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityFFFluidDuct;
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
import net.minecraft.client.particle.ParticleExplosionHuge;
import net.minecraft.client.particle.ParticleMobAppearance;
import net.minecraft.client.particle.ParticleSmokeLarge;
import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityFogFX.class, new RenderFogRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDSmokeFX.class, new MultiCloudRendererFactory(new Item[] {ModItems.d_smoke1, ModItems.d_smoke2, ModItems.d_smoke3, ModItems.d_smoke4, ModItems.d_smoke5, ModItems.d_smoke6, ModItems.d_smoke7, ModItems.d_smoke8}));
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
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderRocket.FACTORY);
		
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
			
		//	ParticleContrail contrail = new ParticleContrail(man, world, x, y, z);
		//	Minecraft.getMinecraft().effectRenderer.addEffect(contrail);
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
	}
	
}
