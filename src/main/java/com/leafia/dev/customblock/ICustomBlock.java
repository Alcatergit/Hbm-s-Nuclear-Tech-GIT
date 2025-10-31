package com.leafia.dev.customblock;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public interface ICustomBlock {
	/// thanks community edition
	List<ICustomBlock> INSTANCES = new ArrayList<>();

	@SideOnly(Side.CLIENT)
	static void bakeModels(ModelBakeEvent event) { INSTANCES.forEach(blockMeta -> blockMeta.bakeModel(event)); }
	@SideOnly(Side.CLIENT)
	static void registerModels() { INSTANCES.forEach(ICustomBlock::registerModel); }
	@SideOnly(Side.CLIENT)
	static void registerSprites(TextureMap map) { INSTANCES.forEach(dynamicSprite -> dynamicSprite.registerSprite(map)); }


	@SideOnly(Side.CLIENT)
	void bakeModel(ModelBakeEvent event);

	@SideOnly(Side.CLIENT)
	void registerModel();

	@SideOnly(Side.CLIENT)
	void registerSprite(TextureMap map);
}
