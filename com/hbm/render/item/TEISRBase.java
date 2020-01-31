package com.hbm.render.item;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;

public class TEISRBase extends TileEntityItemStackRenderer {

	public IBakedModel itemModel;
	public TransformType type;
}
