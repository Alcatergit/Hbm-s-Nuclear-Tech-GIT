package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePress;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.items.ModItems;
import com.hbm.items.gear.RedstoneSword;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.item.ItemRenderRedstoneSword;
import com.hbm.render.tileentity.RenderPress;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
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
	
	
}
