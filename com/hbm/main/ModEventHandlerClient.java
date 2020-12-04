package com.hbm.main;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.Project;

import com.google.common.collect.Queues;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.capability.RadiationCapability.EntityRadiationProvider;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.flashlight.Flashlight;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCanister;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumCell;
import com.hbm.forgefluid.SpecialContainerFillLists.EnumGasCanister;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.HbmShaderManager;
import com.hbm.handler.HbmShaderManager2;
import com.hbm.handler.JetpackHandler;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemChemistryTemplate.EnumChemistryTemplate;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.items.special.weapon.GunB92;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.items.weapon.ItemGunShotty;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.RecoilHandler;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.MeathookJumpPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.ParticleDSmokeFX;
import com.hbm.physics.ParticlePhysicsBlocks;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Tessellator;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.item.AssemblyTemplateBakedModel;
import com.hbm.render.item.AssemblyTemplateRender;
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
import com.hbm.render.item.TEISRBase;
import com.hbm.render.item.weapon.B92BakedModel;
import com.hbm.render.item.weapon.GunRevolverBakedModel;
import com.hbm.render.item.weapon.GunRevolverRender;
import com.hbm.render.item.weapon.ItemRedstoneSwordRender;
import com.hbm.render.item.weapon.ItemRenderGunAnim;
import com.hbm.render.item.weapon.ItemRenderRedstoneSword;
import com.hbm.render.misc.RenderAccessoryUtility;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.render.tileentity.RenderMultiblock;
import com.hbm.render.tileentity.RenderSoyuzMultiblock;
import com.hbm.render.tileentity.RenderStructureMarker;
import com.hbm.sound.MovingSoundChopper;
import com.hbm.sound.MovingSoundChopperMine;
import com.hbm.sound.MovingSoundCrashing;
import com.hbm.sound.MovingSoundPlayerLoop;
import com.hbm.sound.MovingSoundPlayerLoop.EnumHbmSound;
import com.hbm.sound.MovingSoundXVL1456;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.CustomNukeEntry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom.EnumEntryType;
import com.hbm.util.BobMathUtil;

import glmath.glm.vec._2.Vec2;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
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
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ModEventHandlerClient {

	public static Set<EntityLivingBase> specialDeathEffectEntities = new HashSet<>();
	public static ArrayDeque<Particle> firstPersonAuxParticles = Queues.newArrayDeque();
	
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
		} else 	if(item == ModItems.siren_track) {
			for(int i = 0; i < TrackType.values().length; i++) {
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		} else if(item == ModItems.ingot_u238m2) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation(RefStrings.MODID + ":hs-elements", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation(RefStrings.MODID + ":hs-arsenic", "inventory"));
			ModelLoader.setCustomModelResourceLocation(item, 3, new ModelResourceLocation(RefStrings.MODID + ":hs-vault", "inventory"));
		} else if(item == ModItems.polaroid || item == ModItems.glitch) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName() + "_" + MainRegistry.polaroidID, "inventory"));
		} else if(item == Item.getItemFromBlock(ModBlocks.brick_jungle_glyph)){
			for(int i = 0; i < 16; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName().toString() + i, "inventory"));
		} else if(item == Item.getItemFromBlock(ModBlocks.brick_jungle_trap)){
			for(int i = 0; i < Trap.values().length; i ++)
				ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
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
		swapModelsNoGui(ModItems.gun_revolver_silver, reg);
		swapModelsNoGui(ModItems.gun_revolver_red, reg);
		swapModelsNoGui(ModItems.gun_lever_action, reg);
		swapModelsNoGui(ModItems.gun_spark, reg);
		swapModelsNoGui(ModItems.gun_b93, reg);
		swapModelsNoGui(ModItems.gun_rpg, reg);
		swapModelsNoGui(ModItems.gun_karl, reg);
		swapModelsNoGui(ModItems.gun_panzerschreck, reg);
		swapModels(ModItems.gun_hk69, reg);
		swapModelsNoGui(ModItems.gun_deagle, reg);
		swapModelsNoGui(ModItems.gun_supershotgun, reg);
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
		swapModelsNoGui(ModItems.multitool_dig, reg);
		swapModelsNoGui(ModItems.multitool_silk, reg);
		swapModelsNoGui(ModItems.multitool_ext, reg);
		swapModelsNoGui(ModItems.multitool_miner, reg);
		swapModelsNoGui(ModItems.multitool_hit, reg);
		swapModelsNoGui(ModItems.multitool_beam, reg);
		swapModelsNoGui(ModItems.multitool_sky, reg);
		swapModelsNoGui(ModItems.multitool_mega, reg);
		swapModelsNoGui(ModItems.multitool_joule, reg);
		swapModelsNoGui(ModItems.multitool_decon, reg);
		swapModelsNoGui(ModItems.big_sword, reg);
		swapModelsNoGui(ModItems.shimmer_sledge, reg);
		swapModelsNoGui(ModItems.shimmer_axe, reg);
		swapModels(ModItems.ff_fluid_duct, reg);
		swapModels(ModItems.fluid_icon, reg);
		swapModelsNoGui(ModItems.gun_brimstone, reg);
		swapModelsNoGui(ModItems.stopsign, reg);
		swapModelsNoGui(ModItems.sopsign, reg);
		swapModels(ModItems.gun_ks23, reg);
		swapModels(ModItems.gun_flamer, reg);
		swapModels(ModItems.gun_flechette, reg);
		swapModels(ModItems.gun_quadro, reg);
		swapModels(ModItems.gun_sauer, reg);
		swapModelsNoGui(ModItems.chernobylsign, reg);
		swapModels(Item.getItemFromBlock(ModBlocks.machine_selenium), reg);
		swapModels(Item.getItemFromBlock(ModBlocks.radiorec), reg);
		swapModels(ModItems.gun_vortex, reg);
		swapModels(ModItems.gun_thompson, reg);
		swapModelsNoGui(ModItems.wood_gavel, reg);
		swapModelsNoGui(ModItems.lead_gavel, reg);
		swapModelsNoGui(ModItems.diamond_gavel, reg);

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
	public void itemColorsEvent(ColorHandlerEvent.Item evt) {
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
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ultrahotsteam_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/ultrahotsteam_flowing"));
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

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mercury_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/mercury_flowing"));
		
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_dt_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_dt_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_hd_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_hd_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_ht_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_ht_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_xm_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_xm_flowing"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_bf_still"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "blocks/forgefluid/plasma_bf_flowing"));

		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "models/boxcar"));
		evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "models/boxcarflipv"));

		contrail = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID + ":particle/contrail"));
		particle_base = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/particle_base"));
		fog = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/fog"));
		uv_debug = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "misc/uv_debug"));

		// evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID,
		// "blocks/forgefluid/toxic_still"));
		// evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID,
		// "blocks/forgefluid/toxic_flowing"));
	}

	@SubscribeEvent
	public void textureStitchPost(TextureStitchEvent.Post evt) {
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

		RenderSoyuzMultiblock.blockIcons[0] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_launcher");
		RenderSoyuzMultiblock.blockIcons[1] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/concrete_smooth");
		RenderSoyuzMultiblock.blockIcons[2] = evt.getMap().getAtlasSprite(RefStrings.MODID + ":blocks/struct_scaffold");
	}

	public static TextureAtlasSprite contrail;
	public static TextureAtlasSprite particle_base;
	public static TextureAtlasSprite fog;
	public static TextureAtlasSprite uv_debug;

	// All of these are called via coremod, EntityRenderer on line 1018. current
	// is the current value for each, and the returned value is added to the
	// current
	public static float getRLightmapColor(float current) {
		return 0.0F;
	}

	public static float getGLightmapColor(float current) {
		return 0.0F;
	}

	public static float getBLightmapColor(float current) {
		return 0.0F;
	}

	// Drillgon200: All this random flashlight shader stuff was ultimately
	// abandoned because it would have caused too many mod incompatibilities and
	// isn't used anywhere. The coremod still exists, but has no transformers so it doesn't do anything.

	private static boolean sentUniforms = false;
	public static boolean renderingDepthOnly = false;

	// Called from asm via coremod, in ChunkRenderContainer#preRenderChunk
	public static void preRenderChunk(RenderChunk chunk) {
		if(!GeneralConfig.useShaders || renderingDepthOnly)
			return;
		GL20.glUniform3i(GL20.glGetUniformLocation(HbmShaderManager.flashlightWorld, "chunkPos"), chunk.getPosition().getX(), chunk.getPosition().getY(), chunk.getPosition().getZ());
	}

	// Called from asm via coremod, in Profiler#endStartSection
	public static void profilerStart(String name) {
		if(!GeneralConfig.useShaders || renderingDepthOnly)
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
		if(!GeneralConfig.useShaders || renderingDepthOnly)
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
		if(!GeneralConfig.useShaders || renderingDepthOnly)
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
	public void fovUpdate(FOVUpdateEvent e){
		EntityPlayer player = e.getEntity();
		if(player.getHeldItemMainhand().getItem() == ModItems.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			e.setNewfov(e.getFov()*1.1F);
		}
	}

	@SubscribeEvent
	public void inputUpdate(InputUpdateEvent e) {
		EntityPlayer player = e.getEntityPlayer();
		if(player.getHeldItemMainhand().getItem() == ModItems.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			MovementInput m = e.getMovementInput();
			//To make it extra responsive, swings faster if the player is swinging in the opposite direction.
			float coeff = 0.25F;
			if((ItemGunShotty.motionStrafe < 0 && m.moveStrafe > 0) || (ItemGunShotty.motionStrafe > 0 && m.moveStrafe < 0))
				coeff *= 2;
			ItemGunShotty.motionStrafe+=m.moveStrafe*coeff;
			m.moveStrafe = 0;
			m.moveForward = 0;
			//If the player jumps, add some velocity in their look direction (don't want to add velocity down though, so always increase y velocity by at least 1)
			if(m.jump) {
				Vec3d look = player.getLookVec().scale(0.75);
				player.motionX += look.x*1.5;
				player.motionY = 1 + MathHelper.clamp(look.y, 0, 1);
				player.motionZ += look.z*1.5;
				ItemGunShotty.setHookedEntity(player, player.getHeldItemMainhand(), null);
				PacketDispatcher.wrapper.sendToServer(new MeathookJumpPacket());
				m.jump = false;
			}
		}
		JetpackHandler.inputUpdate(e);
	}

	@SubscribeEvent
	public void clientTick(ClientTickEvent e) {
		
		if(e.phase == Phase.END) {
			if(!firstPersonAuxParticles.isEmpty()){
				Iterator<Particle> i = firstPersonAuxParticles.iterator();
				while(i.hasNext()){
					Particle p = i.next();
					p.onUpdate();
					if(!p.isAlive()){
						i.remove();
						continue;
					}
				}
			}
			Iterator<EntityLivingBase> itr = specialDeathEffectEntities.iterator();
			while(itr.hasNext()){
				Entity ent = itr.next();
				if(ent.isDead)
					itr.remove();
			}
			EntityPlayer player = Minecraft.getMinecraft().player;
			if(player != null) {
				boolean isHooked = player.getHeldItemMainhand().getItem() == ModItems.gun_supershotgun && ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand());
				if(isHooked)
					player.distanceWalkedModified = player.prevDistanceWalkedModified; //Stops the held shotgun from bobbing when hooked
			}
			
		}
		if(Minecraft.getMinecraft().player != null){
			JetpackHandler.clientTick(e);
		}
	}
	
	@SubscribeEvent
	public void cameraSetup(EntityViewRenderEvent.CameraSetup e){
		RecoilHandler.modifiyCamera(e);
		JetpackHandler.handleCameraTransform(e);
	}
	
	FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
	FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
	IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
	FloatBuffer POSITION = GLAllocation.createDirectFloatBuffer(4);

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void renderWorld(RenderWorldLastEvent evt) {	
		/*
		* my ass is heavy
		*/
		List<Entity> list = Minecraft.getMinecraft().world.loadedEntityList;
		ClientProxy.renderingConstant = true;

		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
		float partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
		double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
		double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
		double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
		for(Entity e : list) {
			if(e instanceof IConstantRenderer) {
				double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
				double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
				double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
				float f = e.prevRotationYaw + (e.rotationYaw - e.prevRotationYaw) * partialTicks;

				Render<Entity> r = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(e);
				r.doRender(e, d0 - d3, d1 - d4, d2 - d5, f, partialTicks);
			}
		}
		ClientProxy.renderingConstant = false;

		//SSG meathook icon projection
		if(ItemGunShotty.rayTrace != null) {
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, MODELVIEW);
			GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, PROJECTION);
			GL11.glGetInteger(GL11.GL_VIEWPORT, VIEWPORT);

			Project.gluProject((float) (ItemGunShotty.rayTrace.x - d3), (float) (ItemGunShotty.rayTrace.y - d4), (float) (ItemGunShotty.rayTrace.z - d5), MODELVIEW, PROJECTION, VIEWPORT, POSITION);

			ItemGunShotty.screenPos = new Vec2(POSITION.get(0), POSITION.get(1));
		} else {
			ItemGunShotty.screenPos = new Vec2(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2);
		}

		//SSG meathook chain rendering
		ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
		if(ItemGunShotty.hasHookedEntity(Minecraft.getMinecraft().world, stack)) {
			Entity e = ItemGunShotty.getHookedEntity(Minecraft.getMinecraft().world, stack);
			
			//Left/right, up/down, forward/backward
			Vec3d ssgChainPos = new Vec3d(-0.08, -0.1, 0.35);
			ssgChainPos = ssgChainPos.rotatePitch((float) Math.toRadians(-(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks)));
			ssgChainPos = ssgChainPos.rotateYaw((float) Math.toRadians(-(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks)));

			ssgChainPos = ssgChainPos.addVector(0, entity.getEyeHeight(), 0);
			
			double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) partialTicks;
			double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) partialTicks;
			double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) partialTicks;
			Vec3d tester = new Vec3d(d0 - d3, d1 + e.getEyeHeight()*0.75 - d4, d2 - d5).subtract(ssgChainPos);

			double yaw = Math.toDegrees(Math.atan2(tester.x, tester.z));
			double sqrt = MathHelper.sqrt(tester.x * tester.x + tester.z * tester.z);
			double pitch = Math.toDegrees(Math.atan2(tester.y, sqrt));

			GL11.glPushMatrix();
			GlStateManager.translate(ssgChainPos.x, ssgChainPos.y, ssgChainPos.z);
			GL11.glRotated(yaw + 90, 0, 1, 0);
			GL11.glRotated(-pitch + 90, 0, 0, 1);
			GL11.glScaled(0.125, 0.25, 0.125);
			
			double len = MathHelper.clamp(tester.lengthVector()*2, 0, 40);

			RenderHelper.bindTexture(ResourceManager.universal);
			GlStateManager.enableLighting();
			Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
			for(int i = 0; i < Math.ceil(len); i++) {
				float offset = 0;
				if(ItemGunShotty.motionStrafe != 0){
					if(i < len*0.75){
						offset = (float) Library.smoothstep(i, 0, len*0.5);
					} else {
						offset = 1-(float) Library.smoothstep(i, len*0.5, len);
					}
					if(ItemGunShotty.motionStrafe > 0)
						offset = -offset;
				}
				float scale = (float) (len/20F);
				Tessellator.instance.setTranslation(0, i, offset*scale);
				ResourceManager.n45_chain.tessellateAll(Tessellator.instance);
			}

			Tessellator.instance.draw();
			GL11.glPopMatrix();
		}/* else {
			//Drillgon200: Used for testing the closetPointonBB method
			AxisAlignedBB bb = new AxisAlignedBB(RenderPress.pos.x, RenderPress.pos.y, RenderPress.pos.z, RenderPress.pos.x+1, RenderPress.pos.y+1, RenderPress.pos.z+1);
			Vec3d pos = Library.closestPointOnBB(bb, new Vec3d(0, entity.getEyeHeight(), 0), entity.getLookVec().scale(20).addVector(0, entity.getEyeHeight(), 0));
			GL11.glPushMatrix();
			GlStateManager.translate(pos.x, pos.y, pos.z);
			RenderHelper.bindTexture(ResourceManager.universal);
			Tessellator.instance.startDrawing(GL11.GL_TRIANGLES);
			ResourceManager.n45_chain.tessellateAll(Tessellator.instance);
			Tessellator.instance.draw();
			GL11.glPopMatrix();
		}*/
		for(Runnable r : ClientProxy.deferredRenderers){
			r.run();
		}
		ClientProxy.deferredRenderers.clear();
		
		/*for(Particle p : firstPersonAuxParticles){
			if(p instanceof ParticlePhysicsBlocks)
				p.renderParticle(null, Minecraft.getMinecraft().getRenderViewEntity(), MainRegistry.proxy.partialTicks(), 0, 0, 0, 0, 0);
		}*/
		//HbmShaderManager2.doPostProcess();
		HbmShaderManager2.bloom();
	}

	@SubscribeEvent
	public void onOverlayRender(RenderGameOverlayEvent.Pre event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(event.getType() == ElementType.CROSSHAIRS && player.getHeldItemMainhand().getItem() == ModItems.gun_supershotgun && !ItemGunShotty.hasHookedEntity(player.world, player.getHeldItemMainhand())) {
			float x1 = ItemGunShotty.prevScreenPos.x + (ItemGunShotty.screenPos.x - ItemGunShotty.prevScreenPos.x) * event.getPartialTicks();
			float y1 = ItemGunShotty.prevScreenPos.y + (ItemGunShotty.screenPos.y - ItemGunShotty.prevScreenPos.y) * event.getPartialTicks();
			float x = BobMathUtil.remap(x1, 0, Minecraft.getMinecraft().displayWidth, 0, event.getResolution().getScaledWidth());
			float y = event.getResolution().getScaledHeight() - BobMathUtil.remap(y1, 0, Minecraft.getMinecraft().displayHeight, 0, event.getResolution().getScaledHeight());
			RenderHelper.bindTexture(ResourceManager.meathook_marker);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_SRC_COLOR, SourceFactor.ONE, DestFactor.ZERO);
			RenderHelper.drawGuiRect(x - 2.5F, y - 2.5F, 0, 0, 5, 5, 1, 1);
			GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
			GlStateManager.disableBlend();
		}
		/// HANDLE GUN AND AMMO OVERLAYS ///
		if(event.getType() == ElementType.HOTBAR && player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {

			ItemGunBase gun = ((ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem());
			GunConfiguration gcfg = gun.mainConfig;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(player.getHeldItem(EnumHand.MAIN_HAND))));

			Item ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(player.getHeldItem(EnumHand.MAIN_HAND));
			int max = gcfg.ammoCap;

			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, player.getHeldItem(EnumHand.MAIN_HAND), true);
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}

			int dura = ItemGunBase.getItemWear(player.getHeldItem(EnumHand.MAIN_HAND)) * 50 / gcfg.durability;

			RenderScreenOverlay.renderAmmo(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, EnumHand.MAIN_HAND);

			if(gun.altConfig != null && gun.altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				Item oldAmmo = ammo;
				ammo = ItemGunBase.getBeltType(player, player.getHeldItemMainhand(), false);

				if(ammo != oldAmmo) {
					count = ItemGunBase.getBeltSize(player, ammo);
					RenderScreenOverlay.renderAmmoAlt(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, EnumHand.MAIN_HAND);
				}
			}
		}

		if(event.getType() == ElementType.HOTBAR && player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {

			ItemGunBase gun = ((ItemGunBase) player.getHeldItem(EnumHand.OFF_HAND).getItem());
			GunConfiguration gcfg = gun.mainConfig;
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(player.getHeldItem(EnumHand.OFF_HAND))));

			Item ammo = bcfg.ammo;
			int count = ItemGunBase.getMag(player.getHeldItem(EnumHand.OFF_HAND));
			int max = gcfg.ammoCap;

			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				ammo = ItemGunBase.getBeltType(player, player.getHeldItem(EnumHand.OFF_HAND), true);
				count = ItemGunBase.getBeltSize(player, ammo);
				max = -1;
			}

			int dura = ItemGunBase.getItemWear(player.getHeldItem(EnumHand.OFF_HAND)) * 50 / gcfg.durability;

			RenderScreenOverlay.renderAmmo(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, EnumHand.OFF_HAND);

			if(gun.altConfig != null && gun.altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				Item oldAmmo = ammo;
				ammo = ItemGunBase.getBeltType(player, player.getHeldItemOffhand(), false);

				if(ammo != oldAmmo) {
					count = ItemGunBase.getBeltSize(player, ammo);
					RenderScreenOverlay.renderAmmoAlt(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, EnumHand.OFF_HAND);
				}
			}
		}

		/// HANDLE GEIGER COUNTER AND JETPACK HUD ///
		if(event.getType() == ElementType.HOTBAR) {
			if(Library.hasInventoryItem(player.inventory, ModItems.geiger_counter)) {

				float rads = 0;

				if(player.hasCapability(EntityRadiationProvider.ENT_RAD_CAP, null))
					rads = player.getCapability(EntityRadiationProvider.ENT_RAD_CAP, null).getRads();

				RenderScreenOverlay.renderRadCounter(event.getResolution(), rads, Minecraft.getMinecraft().ingameGUI);
			}
			if(JetpackHandler.hasJetpack(player)){
				boolean active = JetpackHandler.jetpackActive(player);
				boolean hover = JetpackHandler.isHovering(player);
				int posX = 12;
				int posY = event.getResolution().getScaledHeight()/2 - 120;
				Minecraft.getMinecraft().fontRenderer.drawString("Jetpack Active: " + active, posX, posY, 0xFFFFFFFF);
				Minecraft.getMinecraft().fontRenderer.drawString("Jetpack Hover: " + hover, posX, posY+12, 0xFFFFFFFF);
			}
		}

		/// HANDLE CUSTOM CROSSHAIRS ///
		if(event.getType() == ElementType.CROSSHAIRS && (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon || player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) && GeneralConfig.enableCrosshairs) {
			event.setCanceled(true);
			if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon && !(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase && ((ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).mainConfig.hasSights && player.isSneaking())){
				if(((IHoldableWeapon) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).hasCustomHudElement()){
					((IHoldableWeapon) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).renderHud(event.getResolution(), Minecraft.getMinecraft().ingameGUI, player.getHeldItemMainhand(), event.getPartialTicks());
				} else {
					RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon) player.getHeldItem(EnumHand.MAIN_HAND).getItem()).getCrosshair());
				}
			}
				
			if(!(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
				if(((IHoldableWeapon) player.getHeldItem(EnumHand.OFF_HAND).getItem()).hasCustomHudElement()){
					((IHoldableWeapon) player.getHeldItem(EnumHand.OFF_HAND).getItem()).renderHud(event.getResolution(), Minecraft.getMinecraft().ingameGUI, player.getHeldItemOffhand(), event.getPartialTicks());
				} else {
					RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon) player.getHeldItem(EnumHand.OFF_HAND).getItem()).getCrosshair());
				}
				
			}
		}

		/// HANLDE ANIMATION BUSES ///

		for(int i = 0; i < HbmAnimations.hotbar.length; i++) {

			Animation animation = HbmAnimations.hotbar[i];

			if(animation == null)
				continue;

			long time = System.currentTimeMillis() - animation.startMillis;

			if(time > animation.animation.getDuration())
				HbmAnimations.hotbar[i] = null;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_O) && Minecraft.getMinecraft().currentScreen == null) {
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(0, 0, 0, 999, 0));
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
		//Mouse.isButtonDown(button)
		//ForgeRegistries.ENTITIES.getKey(value);
		//EntityMaskMan ent;
		//EntityRegistry.getEntry(ent.getClass());
		if(specialDeathEffectEntities.contains(event.getEntity())){
			event.setCanceled(true);
		}
		if(event.getEntity() instanceof AbstractClientPlayer){
			RenderPlayer renderer = (RenderPlayer) event.getRenderer();
			AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();

			if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.getMainModel().rightArmPose = ArmPose.BOW_AND_ARROW;
				// renderer.getMainModel().bipedLeftArm.rotateAngleY = 90;
			}
			if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof IHoldableWeapon) {
				renderer.getMainModel().leftArmPose = ArmPose.BOW_AND_ARROW;
			}
			JetpackHandler.preRenderPlayer(player);
		}
	}
	
	@SubscribeEvent
	public void postRenderLiving(RenderLivingEvent.Post<AbstractClientPlayer> event) {
		if(event.getEntity() instanceof AbstractClientPlayer){
			AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();
			JetpackHandler.postRenderPlayer(player);
		}
	}

	@SubscribeEvent
	public void clickHandler(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		boolean m1 = ItemGunBase.m1;
		boolean m2 = ItemGunBase.m2;
		if(player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.MAIN_HAND).getItem();
			if(event.getButton() == 0 && !m1 && !m2) {
				ItemGunBase.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.MAIN_HAND));
				item.startActionClient(player.getHeldItemMainhand(), player.world, player, true, EnumHand.MAIN_HAND);
			}
			else if(event.getButton() == 1 && !m2 && !m1) {
				ItemGunBase.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.MAIN_HAND));
				item.startActionClient(player.getHeldItemMainhand(), player.world, player, false, EnumHand.MAIN_HAND);
			}
		}
		if(player.getHeldItem(EnumHand.OFF_HAND) != null && player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemGunBase) {

			if(event.getButton() == 0)
				event.setCanceled(true);

			ItemGunBase item = (ItemGunBase) player.getHeldItem(EnumHand.OFF_HAND).getItem();
			if(event.getButton() == 0 && !m1 && !m2) {
				ItemGunBase.m1 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 0, EnumHand.OFF_HAND));
				item.startActionClient(player.getHeldItemOffhand(), player.world, player, true, EnumHand.OFF_HAND);
			}
			else if(event.getButton() == 1 && !m2 && !m1) {
				ItemGunBase.m2 = true;
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 1, EnumHand.OFF_HAND));
				item.startActionClient(player.getHeldItemOffhand(), player.world, player, false, EnumHand.OFF_HAND);
			}
		}
	}

	@Spaghetti("please get this shit out of my face")
	@SubscribeEvent
	public void onPlaySound(PlaySoundEvent e) {
		ResourceLocation r = e.getSound().getSoundLocation();

		WorldClient wc = Minecraft.getMinecraft().world;

		// Alright, alright, I give the fuck up, you've wasted my time enough
		// with this bullshit. You win.
		// A winner is you.
		// Conglaturations.
		// Fuck you.

		if(r.toString().equals("hbm:misc.nullTau") && Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityPlayer ent = Library.getClosestPlayerForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundXVL1456(HBMSoundHandler.tauChargeLoop2, SoundCategory.PLAYERS, ent, EnumHbmSound.soundTauLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(0.5F);
			} else {
				if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() < 1.5F)
					MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).setPitch(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundTauLoop).getPitch() + 0.01F);
			}
		}

		if(r.toString().equals("hbm:misc.nullChopper") && Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopper(HBMSoundHandler.chopperFlyingLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundChopperLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundChopperLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullCrashing") && Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityHunterChopper ent = Library.getClosestChopperForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundCrashing(HBMSoundHandler.chopperCrashingLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundCrashingLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundCrashingLoop).setVolume(10.0F);
			}
		}

		if(r.toString().equals("hbm:misc.nullMine") && Library.getClosestMineForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2) != null) {
			EntityChopperMine ent = Library.getClosestMineForSound(wc, e.getSound().getXPosF(), e.getSound().getYPosF(), e.getSound().getZPosF(), 2);

			if(MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop) == null) {
				MovingSoundPlayerLoop.globalSoundList.add(new MovingSoundChopperMine(HBMSoundHandler.chopperMineLoop, SoundCategory.HOSTILE, ent, EnumHbmSound.soundMineLoop));
				MovingSoundPlayerLoop.getSoundByPlayer(ent, EnumHbmSound.soundMineLoop).setVolume(10.0F);
			}
		}

		for(MovingSoundPlayerLoop sounds : MovingSoundPlayerLoop.globalSoundList) {
			if(!sounds.init || sounds.isDonePlaying()) {
				sounds.init = true;
				sounds.setDone(false);
				Minecraft.getMinecraft().getSoundHandler().playSound(sounds);
			}
		}
	}

	@SubscribeEvent
	public void clientDisconnectFromServer(ClientDisconnectionFromServerEvent e) {
		/*if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && ItemAssemblyTemplate.recipesBackup != null) {
			ItemAssemblyTemplate.recipes = ItemAssemblyTemplate.recipesBackup;
			ItemAssemblyTemplate.recipesBackup = null;
		}*/
	}

	@SubscribeEvent
	public void drawTooltip(ItemTooltipEvent event) {

		ItemStack stack = event.getItemStack();
		List<String> list = event.getToolTip();

		float rad = HazmatRegistry.instance.getResistance(stack);

		rad = ((int) (rad * 100)) / 100F;

		if(rad > 0)
			list.add(TextFormatting.YELLOW + "Radiation resistance: " + rad);
		
		ComparableStack comp = new NbtComparableStack(stack).makeSingular();

		CustomNukeEntry entry = TileEntityNukeCustom.entries.get(comp);

		if(entry != null) {

			if(!list.isEmpty())
				list.add("");

			if(entry.entry == EnumEntryType.ADD)
				list.add(TextFormatting.GOLD + "Adds " + entry.value + " to the custom nuke stage " + entry.type);

			if(entry.entry == EnumEntryType.MULT)
				list.add(TextFormatting.GOLD + "Adds multiplier " + entry.value + " to the custom nuke stage " + entry.type);
		}
	}

}
