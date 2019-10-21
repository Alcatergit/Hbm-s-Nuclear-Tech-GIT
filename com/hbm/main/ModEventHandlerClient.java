package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePress;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.lib.RefStrings;
import com.hbm.render.entity.DSmokeRenderer;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderRedstoneSword;
import com.hbm.render.tileentity.RenderPress;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandlerClient {

	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		for(Item item : ModItems.ALL_ITEMS){
			registerModel(item, 0);
		}
		for(Block block : ModBlocks.ALL_BLOCKS){
			registerBlockModel(block, 0);
		}
	}

	private void registerBlockModel(Block block, int meta){
		registerModel(Item.getItemFromBlock(block), meta);
	}
	private void registerModel(Item item, int meta) {
		if(item instanceof IHasCustomModel){
			ModelLoader.setCustomModelResourceLocation(item, 0, ((IHasCustomModel)item).getResourceLocation());
		} else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
		}
	}
	
	@SubscribeEvent
	public void modelBaking(ModelBakeEvent evt){
		ResourceManager.init();
		Object obj = evt.getModelRegistry().getObject(RedstoneSword.rsModel);
		if(obj instanceof IBakedModel){
			IBakedModel model = (IBakedModel)obj;
			ItemRedstoneSwordRender.instance.itemModel = model;
			evt.getModelRegistry().putObject(RedstoneSword.rsModel, new ItemRenderRedstoneSword());
		}

	}
	@SubscribeEvent
	public void textureStitch(TextureStitchEvent.Pre evt){
		DSmokeRenderer.sprites[0] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke1"));
		DSmokeRenderer.sprites[1] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke2"));
		DSmokeRenderer.sprites[2] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke3"));
		DSmokeRenderer.sprites[3] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke4"));
		DSmokeRenderer.sprites[4] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke5"));
		DSmokeRenderer.sprites[5] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke6"));
		DSmokeRenderer.sprites[6] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke7"));
		DSmokeRenderer.sprites[7] = evt.getMap().registerSprite(new ResourceLocation(RefStrings.MODID, "particle/d_smoke8"));

		
	}
	
}
