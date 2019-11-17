package com.hbm.main;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.lib.RefStrings;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.item.AssemblyTemplateBakedModel;
import com.hbm.render.item.AssemblyTemplateRender;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderRedstoneSword;
import com.hbm.render.tileentity.RenderPress;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandlerClient {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		for (Item item : ModItems.ALL_ITEMS) {
			registerModel(item, 0);
		}
		for (Block block : ModBlocks.ALL_BLOCKS) {
			registerBlockModel(block, 0);
		}
	}

	private void registerBlockModel(Block block, int meta) {
		registerModel(Item.getItemFromBlock(block), meta);
	}

	private void registerModel(Item item, int meta) {
		if (item instanceof IHasCustomModel) {
			ModelLoader.setCustomModelResourceLocation(item, 0, ((IHasCustomModel) item).getResourceLocation());
		} else {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	@SubscribeEvent
	public void modelBaking(ModelBakeEvent evt) {
		ResourceManager.init();
		Object obj = evt.getModelRegistry().getObject(RedstoneSword.rsModel);
		if (obj instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) obj;
			ItemRedstoneSwordRender.instance.itemModel = model;
			evt.getModelRegistry().putObject(RedstoneSword.rsModel, new ItemRenderRedstoneSword());
		}
		Object object = evt.getModelRegistry().getObject(ItemAssemblyTemplate.location);
		if (object instanceof IBakedModel) {
			IBakedModel model = (IBakedModel) object;
			AssemblyTemplateRender.INSTANCE.itemModel = model;
			evt.getModelRegistry().putObject(ItemAssemblyTemplate.location, new AssemblyTemplateBakedModel());
		}

	}

	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre evt) {
		DSmokeRenderer.sprites[0] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke1"));
		DSmokeRenderer.sprites[1] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke2"));
		DSmokeRenderer.sprites[2] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke3"));
		DSmokeRenderer.sprites[3] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke4"));
		DSmokeRenderer.sprites[4] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke5"));
		DSmokeRenderer.sprites[5] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke6"));
		DSmokeRenderer.sprites[6] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke7"));
		DSmokeRenderer.sprites[7] = evt.getMap()
				.registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke8"));

	}

	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent evt) {
		List<Entity> list = Minecraft.getMinecraft().world.loadedEntityList;
		for (Entity e : list) {
			if (e instanceof IConstantRenderer) {

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
	}
	
	@SubscribeEvent
	public void fovUpdate(FOVUpdateEvent evt){
		RenderPress.fov = evt.getFov();
		
	}

}
