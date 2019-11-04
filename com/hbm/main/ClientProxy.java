package com.hbm.main;

import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.items.ModItems;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.entity.FogRenderer;
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
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.tileentity.RenderAssembler;
import com.hbm.render.tileentity.RenderPress;
import com.hbm.render.tileentity.RenderTaint;
import com.hbm.render.util.HmfModelLoader;
import com.hbm.tileentity.generic.TileEntityTaint;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	
	public static final ModelResourceLocation IRRELEVANT_MRL = new ModelResourceLocation("hbm:placeholdermodel", "inventory");
	
	@Override
	public void registerRenderInfo()
	{
		MinecraftForge.EVENT_BUS.register(new ModEventHandlerClient());
		AdvancedModelLoader.registerModelHandler(new HmfModelLoader());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePress.class, new RenderPress());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineAssembler.class, new RenderAssembler());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTaint.class, new RenderTaint());
		
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
	}
	@Override
	public void registerMissileItems() {
		
	}
	@Override
	public void registerTileEntitySpecialRenderer() {
		
	}
	@Override
	public void particleControl(double x, double y, double z, int type) {
		
	}
	//version 2, now with strings!
	@Override
	public void spawnParticle(double x, double y, double z, String type, float args[]) {
		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent evt){
		OBJLoader.INSTANCE.addDomain("hbm");
		ModItems.redstone_sword.setTileEntityItemStackRenderer(ItemRedstoneSwordRender.instance);
		ModItems.assembly_template.setTileEntityItemStackRenderer(AssemblyTemplateRender.INSTANCE);
	}
	
}
