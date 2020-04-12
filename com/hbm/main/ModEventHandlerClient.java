package com.hbm.main;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import com.hbm.blocks.ModBlocks;
import com.hbm.capability.RadiationCapability.EntityRadiationProvider;
import com.hbm.flashlight.Flashlight;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCanister;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCell;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumGasCanister;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HbmShaderManager;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.special.weapon.GunB92;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemCassette.TrackType;
import com.hbm.items.tool.ItemChemistryTemplate;
import com.hbm.items.tool.ItemChemistryTemplate.EnumChemistryTemplate;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.tool.ItemFluidTank;
import com.hbm.items.tool.ItemForgeFluidIdentifier;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.ParticleDSmokeFX;
import com.hbm.render.FakeWorldRenderer;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.item.AssemblyTemplateBakedModel;
import com.hbm.render.item.AssemblyTemplateRender;
import com.hbm.render.item.B92BakedModel;
import com.hbm.render.item.BakedModelCustom;
import com.hbm.render.item.BakedModelNoGui;
import com.hbm.render.item.ChemTemplateBakedModel;
import com.hbm.render.item.ChemTemplateRender;
import com.hbm.render.item.FFIdentifierModel;
import com.hbm.render.item.FFIdentifierRender;
import com.hbm.render.item.FluidBarrelBakedModel;
import com.hbm.render.item.FluidBarrelRender;
import com.hbm.render.item.FluidCanisterBakedModel;
import com.hbm.render.item.FluidCanisterRender;
import com.hbm.render.item.FluidTankBakedModel;
import com.hbm.render.item.FluidTankRender;
import com.hbm.render.item.GunRevolverBakedModel;
import com.hbm.render.item.GunRevolverRender;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderGunAnim;
import com.hbm.render.item.ItemRenderRedstoneSword;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.misc.RenderAccessoryUtility;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.render.tileentity.RenderMultiblock;
import com.hbm.render.tileentity.RenderStructureMarker;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

@SuppressWarnings("deprecation")
public class ModEventHandlerClient {

	private Field debugViewDirection = null;
	private Field debugView = null;

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {

		int i = 0;
		ResourceLocation[] list = new ResourceLocation[EnumCanister.values().length];
		for(EnumCanister e : EnumCanister.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.canister_generic, list);

		i = 0;
		list = new ResourceLocation[EnumCell.values().length];
		for(EnumCell e : EnumCell.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.cell, list);

		i = 0;
		list = new ResourceLocation[EnumGasCanister.values().length];
		for(EnumGasCanister e : EnumGasCanister.values()) {
			list[i] = e.getResourceLocation();
			i++;
		}
		ModelLoader.registerItemVariants(ModItems.cell, list);
		
		
		for(Item item : ModItems.ALL_ITEMS) {
			registerModel(item, 0);
		}
		for(Block block : ModBlocks.ALL_BLOCKS) {
			registerBlockModel(block, 0);
		}
	}

	private void registerBlockModel(Block block, int meta) {
		registerModel(Item.getItemFromBlock(block), meta);
	}

	private void registerModel(Item item, int meta) {
		if(item == Items.AIR)
			return;
		
		if(item == ModItems.chemistry_icon) {
			for(int i = 0; i < EnumChemistryTemplate.values().length; i++) {
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(RefStrings.MODID + ":chem_icon_" + EnumChemistryTemplate.getEnum(i).getName().toLowerCase(), "inventory"));
			}
		} else if(item == ModItems.chemistry_template) {
			for(int i = 0; i < EnumChemistryTemplate.values().length; i++) {
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == ModItems.siren_track){
			for(int i = 0; i < TrackType.values().length; i ++){
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == ModItems.ingot_u238m2){
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation(RefStrings.MODID + ":hs-elements", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation(RefStrings.MODID + ":hs-arsenic", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 3, new ModelResourceLocation(RefStrings.MODID + ":hs-vault", "inventory"));
		} else if(item == ModItems.polaroid) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName() + "_" + MainRegistry.polaroidID, "inventory"));
		} else if(item instanceof IHasCustomModel) {
			ModelLoader.setCustomModelResourceLocation(item, 0, ((IHasCustomModel) item).getResourceLocation());
		} else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	@SubscribeEvent
	public void modelBaking(ModelBakeEvent evt) {

		for(EnumCanister e : EnumCanister.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}
		for(EnumCell e : EnumCell.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}
		for(EnumGasCanister e : EnumGasCanister.values()) {
			Object o = evt.getModelRegistry().getObject(e.getResourceLocation());
			if(o instanceof IBakedModel)
				e.putRenderModel((IBakedModel) o);
		}

		// Drillgon200: Sigh... find a better custom model loading system.
		// Drillgon200: Removed todo, found a better way. Now I just have to
		// deal with all these ugly things. That can wait.
		ResourceManager.init();
		Object obj = evt.getModelRegistry().getObject(RedstoneSword.rsModel);
		if(obj instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) obj;
			ItemRedstoneSwordRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(RedstoneSword.rsModel, new ItemRenderRedstoneSword());
		}
		Object object = evt.getModelRegistry().getObject(ItemAssemblyTemplate.location);
		if(object instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object;
			AssemblyTemplateRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemAssemblyTemplate.location, new AssemblyTemplateBakedModel());
		}

		Object object3 = evt.getModelRegistry().getObject(GunB92.b92Model);
		if(object instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object3;
			ItemRenderGunAnim.INSTANCE.b92ItemModel = model;
			evt.getModelRegistry().putObject(GunB92.b92Model, new B92BakedModel());
		}
		Object object4 = evt.getModelRegistry().getObject(ItemFluidTank.fluidTankModel);
		if(object4 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object4;
			FluidTankRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidTank.fluidTankModel, new FluidTankBakedModel());
		}
		Object object5 = evt.getModelRegistry().getObject(ItemFluidTank.fluidBarrelModel);
		if(object5 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object5;
			FluidBarrelRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidTank.fluidBarrelModel, new FluidBarrelBakedModel());
		}
		Object object6 = evt.getModelRegistry().getObject(ItemFluidCanister.fluidCanisterModel);
		if(object6 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object6;
			FluidCanisterRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemFluidCanister.fluidCanisterModel, new FluidCanisterBakedModel());
		}
		Object object7 = evt.getModelRegistry().getObject(ItemChemistryTemplate.chemModel);
		if(object7 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object7;
			ChemTemplateRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemChemistryTemplate.chemModel, new ChemTemplateBakedModel());
		}
		Object object8 = evt.getModelRegistry().getObject(ItemForgeFluidIdentifier.identifierModel);
		if(object8 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object8;
			FFIdentifierRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemForgeFluidIdentifier.identifierModel, new FFIdentifierModel());
		}
		Object object9 = evt.getModelRegistry().getObject(new ModelResourceLocation(ModItems.gun_revolver.getRegistryName(), "inventory"));
		if(object9 instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object9;
			GunRevolverRender.INSTANCE.revolverModel = model;
			evt.getModelRegistry().putObject(new ModelResourceLocation(ModItems.gun_revolver.getRegistryName(), "inventory"), new GunRevolverBakedModel());
		}
		IRegistry<ModelResourceLocation, IBakedModel> reg = evt.getModelRegistry();
		swapModelsNoGui(ModItems.gun_revolver_nightmare, reg);
		swapModelsNoGui(ModItems.gun_revolver_nightmare2, reg);
		swapModelsNoGui(ModItems.gun_revolver_iron, reg);
		swapModelsNoGui(ModItems.gun_revolver_gold, reg);
		swapModelsNoGui(ModItems.gun_revolver_lead, reg);
		swapModelsNoGui(ModItems.gun_revolver_schrabidium, reg);
		swapModelsNoGui(ModItems.gun_revolver_cursed, reg);
		swapModelsNoGui(ModItems.gun_revolver_pip, reg);
		swapModelsNoGui(ModItems.gun_revolver_nopip, reg);
		swapModelsNoGui(ModItems.gun_revolver_blackjack, reg);
		swapModelsNoGui(ModItems.gun_revolver_red, reg);
		swapModelsNoGui(ModItems.gun_lever_action, reg);
		swapModelsNoGui(ModItems.gun_spark, reg);
		swapModelsNoGui(ModItems.gun_b93, reg);
		swapModelsNoGui(ModItems.gun_rpg, reg);
		swapModelsNoGui(ModItems.gun_karl, reg);
		swapModelsNoGui(ModItems.gun_panzerschreck, reg);
		swapModelsNoGui(ModItems.gun_hk69, reg);
		swapModelsNoGui(ModItems.gun_fatman, reg);
		swapModelsNoGui(ModItems.gun_proto, reg);
		swapModelsNoGui(ModItems.gun_mirv, reg);
		swapModelsNoGui(ModItems.gun_bf, reg);
		swapModelsNoGui(ModItems.gun_zomg, reg);
		swapModelsNoGui(ModItems.gun_xvl1456, reg);
		swapModelsNoGui(ModItems.gun_hp, reg);
		swapModelsNoGui(ModItems.gun_defabricator, reg);
		swapModelsNoGui(ModItems.gun_uboinik, reg);
		swapModelsNoGui(ModItems.gun_euthanasia, reg);
		swapModelsNoGui(ModItems.gun_stinger, reg);
		swapModelsNoGui(ModItems.gun_skystinger, reg);
		swapModelsNoGui(ModItems.gun_mp, reg);
		swapModelsNoGui(ModItems.gun_cryolator, reg);
		swapModelsNoGui(ModItems.gun_jack, reg);
		swapModelsNoGui(ModItems.gun_immolator, reg);
		swapModelsNoGui(ModItems.gun_osipr, reg);
		swapModelsNoGui(ModItems.gun_emp, reg);
		swapModelsNoGui(ModItems.gun_revolver_inverted, reg);
		swapModelsNoGui(ModItems.gun_lever_action_sonata, reg);
		swapModelsNoGui(ModItems.gun_bolt_action_saturnite, reg);
		swapModelsNoGui(ModItems.gun_folly, reg);
		swapModelsNoGui(ModItems.gun_dampfmaschine, reg);
		swapModelsNoGui(ModItems.gun_revolver_saturnite, reg);
		swapModelsNoGui(ModItems.gun_calamity, reg);
		swapModelsNoGui(ModItems.gun_calamity_dual, reg);
		swapModelsNoGui(ModItems.gun_minigun, reg);
		swapModelsNoGui(ModItems.gun_avenger, reg);
		swapModelsNoGui(ModItems.gun_lacunae, reg);
		swapModelsNoGui(ModItems.gun_lever_action_dark, reg);
		swapModelsNoGui(ModItems.gun_bolt_action, reg);
		swapModelsNoGui(ModItems.gun_bolt_action_green, reg);
		swapModelsNoGui(ModItems.gun_uzi, reg);
		swapModelsNoGui(ModItems.gun_uzi_silencer, reg);
		swapModelsNoGui(ModItems.gun_uzi_saturnite, reg);
		swapModelsNoGui(ModItems.gun_uzi_saturnite_silencer, reg);
		swapModelsNoGui(ModItems.gun_mp40, reg);
		swapModels(ModItems.cell, reg);
		swapModels(ModItems.gas_canister, reg);
		
		MainRegistry.proxy.registerMissileItems(reg);
	}

	public static void swapModels(Item item, IRegistry<ModelResourceLocation, IBakedModel> reg) {
		ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
		IBakedModel model = reg.getObject(loc);
		TileEntityItemStackRenderer render = item.getTileEntityItemStackRenderer();
		if(render instanceof TEISRBase) {
			((TEISRBase) render).itemModel = model;
			reg.putObject(loc, new BakedModelCustom((TEISRBase) render));
		}

	}

	public static void swapModelsNoGui(Item item, IRegistry<ModelResourceLocation, IBakedModel> reg) {
		ModelResourceLocation loc = new ModelResourceLocation(item.getRegistryName(), "inventory");
		IBakedModel model = reg.getObject(loc);
		TileEntityItemStackRenderer render = item.getTileEntityItemStackRenderer();
		if(render instanceof TEISRBase) {
			((TEISRBase) render).itemModel = model;
			reg.putObject(loc, new BakedModelNoGui((TEISRBase) render));
		}

	}
	
	@SubscribeEvent
	public void itemColorsEvent(ColorHandlerEvent.Item evt){
		evt.getItemColors().registerItemColorHandler((ItemStack stack, int tintIndex) -> {
			if(tintIndex == 1) {
				int j = TrackType.getEnum(stack.getItemDamage()).getColor();

				if(j < 0) {
					j = 0xFFFFFF;
				}

				return j;
			}
			return 0xFFFFFF;
		}, ModItems.siren_track);
	}

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre evt) {
		DSmokeRenderer.sprites[0] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke1"));
		DSmokeRenderer.sprites[1] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke2"));
		DSmokeRenderer.sprites[2] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke3"));
		DSmokeRenderer.sprites[3] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke4"));
		DSmokeRenderer.sprites[4] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke5"));
		DSmokeRenderer.sprites[5] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke6"));
		DSmokeRenderer.sprites[6] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke7"));
		DSmokeRenderer.sprites[7] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke8"));
		ParticleDSmokeFX.sprites = DSmokeRenderer.sprites;

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/steam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/superhotsteam_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/coolant_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/deuterium_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/tritium_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hotoil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heavyoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/bitumen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/smear_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/heatingoil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/reclaimed_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroil_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lubricant_flowing"));

		// Yes yes I know, I spelled 'naphtha' wrong.
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/napatha_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/diesel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/lightoil_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/kerosene_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/gas_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/petroleum_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biogas_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/biofuel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/nitan_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/uf6_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/puf6_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/sas3_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/amat_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/aschrab_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/acid_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/watz_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/cryogel_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/hydrogen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/oxygen_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/xenon_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/balefire_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "models/boxcar"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "models/boxcarflipv"));

		contrail = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID + ":particle/contrail"));

		// evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID,
		// "blocks/forgefluid/toxic_still"));
		// evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID,
		// "blocks/forgefluid/toxic_flowing"));
	}
	
	@SubscribeEvent
	public void textureStitchPost(TextureStitchEvent.Post evt){
		RenderStructureMarker.fac_ti[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_hull");
		RenderStructureMarker.fac_ti[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_furnace");
		RenderStructureMarker.fac_ti[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_core");
		RenderStructureMarker.fac_ti[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/factory_titanium_core");
		
		RenderStructureMarker.reactor[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_element_top");
		RenderStructureMarker.reactor[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_element_side");
		RenderStructureMarker.reactor[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_computer");
		RenderStructureMarker.reactor[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_computer");
		RenderStructureMarker.reactor[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_control_top");
		RenderStructureMarker.reactor[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_control_side");
		RenderStructureMarker.reactor[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");
		RenderStructureMarker.reactor[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_hatch");
		RenderStructureMarker.reactor[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_conductor_top");
		RenderStructureMarker.reactor[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reactor_conductor_side");
		RenderStructureMarker.reactor[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");
		RenderStructureMarker.reactor[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/brick_concrete");
		
		RenderStructureMarker.fusion[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_steel");
		RenderStructureMarker.fusion[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_conductor_side_alt3");
		RenderStructureMarker.fusion[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_heater_top");
		RenderStructureMarker.fusion[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_heater_side");
		RenderStructureMarker.fusion[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		RenderStructureMarker.fusion[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_hatch");
		RenderStructureMarker.fusion[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_motor_top_alt");
		RenderStructureMarker.fusion[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_motor_side_alt");
		RenderStructureMarker.fusion[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_top_alt");
		RenderStructureMarker.fusion[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_side_alt");
		RenderStructureMarker.fusion[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_center_top_alt");
		RenderStructureMarker.fusion[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fusion_core_side_alt");
		RenderStructureMarker.fusion[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		RenderStructureMarker.fusion[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_tungsten");
		
		RenderStructureMarker.watz[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/reinforced_brick");
		RenderStructureMarker.watz[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_hatch");
		RenderStructureMarker.watz[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_control_top");
		RenderStructureMarker.watz[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_control_side");
		RenderStructureMarker.watz[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_end");
		RenderStructureMarker.watz[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_end");
		RenderStructureMarker.watz[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_conductor_top");
		RenderStructureMarker.watz[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_conductor_side");
		RenderStructureMarker.watz[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_computer");
		RenderStructureMarker.watz[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_computer");
		RenderStructureMarker.watz[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_cooler");
		RenderStructureMarker.watz[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_cooler");
		RenderStructureMarker.watz[7][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_element_top");
		RenderStructureMarker.watz[7][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/watz_element_side");
		
		RenderStructureMarker.fwatz[0][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[0][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[1][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_scaffold");
		RenderStructureMarker.fwatz[1][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_hatch");
		RenderStructureMarker.fwatz[2][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_cooler_top");
		RenderStructureMarker.fwatz[2][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_cooler");
		RenderStructureMarker.fwatz[3][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_tank");
		RenderStructureMarker.fwatz[3][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_tank");
		RenderStructureMarker.fwatz[4][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/block_combine_steel");
		RenderStructureMarker.fwatz[4][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_conductor_side");
		RenderStructureMarker.fwatz[5][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_computer");
		RenderStructureMarker.fwatz[5][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_computer");
		RenderStructureMarker.fwatz[6][0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_core");
		RenderStructureMarker.fwatz[6][1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/fwatz_core");
		
		RenderMultiblock.structLauncher = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_launcher");
		RenderMultiblock.structScaffold = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_scaffold");
		
	}

	public static TextureAtlasSprite contrail;
	int renderCount = 0;

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void renderWorld(RenderWorldLastEvent evt) {
		List<Entity> list = Minecraft.getMinecraft().world.loadedEntityList;
		ClientProxy.renderingConstant = true;
		for(Entity e : list) {
			if(e instanceof IConstantRenderer) {

				float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
				double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
				double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
				double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
				float f = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks;
				Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
				double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
				double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
				double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;

				Render<Entity> r = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(e);
				r.doRender(e, d0 - d3, d1 - d4, d2 - d5, f, partialTicks);
			}
		}
		ClientProxy.renderingConstant = false;
		if(OpenGlHelper.shadersSupported && MainRegistry.useShaders) {
			HbmShaderManager.renderGauss();
			Flashlight.ALL_RENDER_FLASHLIGHTS.forEach(Flashlight::renderBeam);
		}
		sentUniforms = false;

	}

	@SubscribeEvent
	public void drawScreenPre(DrawScreenEvent.Pre evt) {
		if(!MainRegistry.useShaders || renderingDepthOnly)
			return;
		HbmShaderManager.releaseShader();
	}

	public static Map<TileEntity, Flashlight> tester = new HashMap<TileEntity, Flashlight>();

	@SubscribeEvent
	public void renderLast(TickEvent.RenderTickEvent evt) {
		if(evt.phase == Phase.END) {

		}
		if(evt.phase == Phase.START) {
			if(MainRegistry.useShaders) {
				FakeWorldRenderer.INSTANCE.rendererUpdateCount++;
				Flashlight.ALL_RENDER_FLASHLIGHTS.clear();
				Iterator<Entry<TileEntity, Flashlight>> itr = tester.entrySet().iterator();
				while(itr.hasNext()) {
					Entry<TileEntity, Flashlight> entry = itr.next();
					if(entry.getKey().isInvalid()) {
						itr.remove();
						continue;
					}
					Flashlight.ALL_RENDER_FLASHLIGHTS.add(entry.getValue());
				}
				Flashlight.ALL_RENDER_FLASHLIGHTS.forEach(Flashlight::generateShadowMap);
				GL13.glActiveTexture(GL13.GL_TEXTURE5);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, Flashlight.depthTexture);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
			}
		}
	}

	private static boolean sentUniforms = false;
	public static boolean renderingDepthOnly = false;

	// Called from asm via coremod, in ChunkRenderContainer#preRenderChunk
	public static void preRenderChunk(RenderChunk chunk) {
		if(!MainRegistry.useShaders || renderingDepthOnly)
			return;
		GL20.glUniform3i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "chunkPos"), chunk.getPosition().getX(), chunk.getPosition().getY(), chunk.getPosition().getZ());
	}

	// Called from asm via coremod, in Profiler#endStartSection
	public static void profilerStart(String name) {
		if(!MainRegistry.useShaders || renderingDepthOnly)
			return;
		if(name.equals("terrain")) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);

			if(!sentUniforms) {
				Flashlight.setUniforms();
				sentUniforms = true;
			}
		}
		if(name.equals("sky")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("particles")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("litParticles")) {
			if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
				HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
		if(name.equals("weather")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("hand")) {
			HbmShaderManager.releaseShader();
		}
		if(name.equals("translucent")) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		}
		if(name.equals("gui")) {
			HbmShaderManager.releaseShader();
		}
	}

	// Called from asm via coremod, in RenderManager#renderEntity
	public static void onEntityRender(Entity e) {
		if(!MainRegistry.useShaders || renderingDepthOnly)
			return;
		if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		} else {
			GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
		}
		if(e instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) e;
			if(living.deathTime > 0 || living.hurtTime > 0) {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 0.0F, 0.0F, 0.3F);
			} else {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
	}

	// Called from asm via coremod, in TileEntityRendererDispatcher#render
	public static void onTileEntityRender(TileEntity t) {
		if(!MainRegistry.useShaders || renderingDepthOnly)
			return;
		if(t instanceof TileEntityEndPortal || t instanceof TileEntityEndGateway) {
			HbmShaderManager.releaseShader();
		} else {
			if(!HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
				HbmShaderManager.useShader(HbmShaderManager.flashlightWorld);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightmap"), 1);
				GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "flashlightDepth"), 6);
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			} else {
				GL20.glUniform4f(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "colorMult"), 1.0F, 1.0F, 1.0F, 0.0F);
			}
		}
	}

	// Called from asm via coremod, in GlStateManager#disableLighting
	public static void onLightingDisable() {
		if(HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightingEnabled"), 0);
		}
	}

	// Called from asm via coremod, in GlStateManager#enableLighting
	public static void onLightingEnable() {
		if(HbmShaderManager.isActiveShader(HbmShaderManager.flashlightWorld)) {
			GL20.glUniform1i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "lightingEnabled"), 1);
		}
	}

	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {

		EntityPlayer player = Minecraft.getMinecraft().player;
		if(this.debugViewDirection == null) {
			// Drillgon200: Oof, OfbReflect didn't work.
			this.debugViewDirection = ReflectionHelper.findField(EntityRenderer.class, "debugViewDirection", "field_175079_V");
			debugViewDirection.setAccessible(true);
		}
		if(this.debugView == null) {
			// Drillgon200: Oof, OfbReflect didn't work.
			this.debugView = ReflectionHelper.findField(EntityRenderer.class, "debugView", "field_175078_W");
			debugView.setAccessible(true);
		}
		if(player.getUniqueID().toString().equals("c874fd4e-5841-42e4-8f77-70efd5881bc1"))
			if(player.ticksExisted > 5 * 60 * 20) {
				try {
					debugViewDirection.setInt(Minecraft.getMinecraft().entityRenderer, 4);
					debugView.setBoolean(Minecraft.getMinecraft().entityRenderer, true);
				} catch(IllegalArgumentException e) {
					e.printStackTrace();
				} catch(IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		if(event.getType() == ElementType.HOTBAR && player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {

			ItemGunBase gun = ((ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem());
			GunConfiguration gcfg = gun.mainConfig;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(player.getHeldItem(EnumHand.MAIN_HAND))));

			Item ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(player.getHeldItem(EnumHand.MAIN_HAND));
			int max = gcfg.ammoCap;

			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, player.getHeldItem(EnumHand.MAIN_HAND));
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}

			int dura = ItemGunBase.getItemWear(player.getHeldItem(EnumHand.MAIN_HAND)) * 50 / gcfg.durability;

			RenderScreenOverlay.renderAmmo(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, EnumHand.MAIN_HAND);
			// RenderScreenOverlay.renderRadCounter(event.resolution, 0,
			// Minecraft.getMinecraft().ingameGUI);
		}

		if(event.getType() == ElementType.HOTBAR && player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {

			ItemGunBase gun = ((ItemGunBase) player.getHeldItem(EnumHand.OFF_HAND).getItem());
			GunConfiguration gcfg = gun.mainConfig;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(player.getHeldItem(EnumHand.OFF_HAND))));

			Item ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(player.getHeldItem(EnumHand.OFF_HAND));
			int max = gcfg.ammoCap;

			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, player.getHeldItem(EnumHand.OFF_HAND));
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}

			int dura = ItemGunBase.getItemWear(player.getHeldItem(EnumHand.OFF_HAND)) * 50 / gcfg.durability;

			RenderScreenOverlay.renderAmmo(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, EnumHand.OFF_HAND);
			// RenderScreenOverlay.renderRadCounter(event.resolution, 0,
			// Minecraft.getMinecraft().ingameGUI);
		}

		if(event.getType() == ElementType.HOTBAR) {
			if(Library.hasInventoryItem(player.inventory, ModItems.geiger_counter)) {
			
				float rads = 0;
			
				if(player.hasCapability(EntityRadiationProvider.ENT_RAD_CAP, null))
					rads = player.getCapability(EntityRadiationProvider.ENT_RAD_CAP, null).getRads();
				
				RenderScreenOverlay.renderRadCounter(event.getResolution(), rads, Minecraft.getMinecraft().ingameGUI);
			}
		}

		if(event.getType() == ElementType.CROSSHAIRS && (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon || player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon)) {
			event.setCanceled(true);

			if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase && ((ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).mainConfig.hasSights && player.isSneaking()))
				RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).getCrosshair());
			if(!(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
				RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon) player.getHeldItem(EnumHand.OFF_HAND).getItem()).getCrosshair());
			}
		}
	}

	@SubscribeEvent
	public void preRenderPlayer(RenderPlayerEvent.Pre evt) {
		// event.setCanceled(true);
		AbstractClientPlayer player = (AbstractClientPlayer) evt.getEntityPlayer();

		ResourceLocation cloak = RenderAccessoryUtility.getCloakFromPlayer(player);
		// GL11.glRotated(180, 1, 0, 0);
		NetworkPlayerInfo info = Minecraft.getMinecraft().getConnection().getPlayerInfo(player.getUniqueID());
		if(cloak != null)
			RenderAccessoryUtility.loadCape(info, cloak);
	}

	@SubscribeEvent
	public void preRenderLiving(RenderLivingEvent.Pre<AbstractClientPlayer> event) {
		if(event.getEntity() instanceof AbstractClientPlayer) {
			RenderPlayer renderer = (RenderPlayer) event.getRenderer();
			AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();

			if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.getMainModel().rightArmPose = ArmPose.BOW_AND_ARROW;
				// renderer.getMainModel().bipedLeftArm.rotateAngleY = 90;
			}
			if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.getMainModel().leftArmPose = ArmPose.BOW_AND_ARROW;
			}
		}
	}

	@SubscribeEvent
	public void clickHandler(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem();

			if(event.getButton() == 0 && !item.m1r && !item.m2r) {
				item.m1r = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.MAIN_HAND));
				// System.out.println("M1");
			} else if(event.getButton() == 1 && !item.m2r && !item.m1r) {
				item.m2r = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.MAIN_HAND));
				// System.out.println("M2");
			}
		}
		if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.OFF_HAND).getItem();
			if(event.getButton() == 0 && !item.m1l && !item.m2l) {
				item.m1l = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.OFF_HAND));

				// System.out.println("M1");
			} else if(event.getButton() == 1 && !item.m2l && !item.m1l) {
				item.m2l = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.OFF_HAND));
				// System.out.println("M2");
			}
		}
	}

	@SubscribeEvent
	public void clientDisconnectFromServer(ClientDisconnectionFromServerEvent e) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && ItemAssemblyTemplate.recipesBackup != null) {
			ItemAssemblyTemplate.recipes = ItemAssemblyTemplate.recipesBackup;
			ItemAssemblyTemplate.recipesBackup = null;
		}
	}

}
