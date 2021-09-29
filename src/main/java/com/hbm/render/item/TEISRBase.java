package com.hbm.render.item;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class TEISRBase extends TileEntityItemStackRenderer {

	public IBakedModel itemModel;
	public TransformType type;
	/** Can be null. */
	public EntityLivingBase entity;
	public World world;
	
	public boolean doNullTransform(){
		return false;
	}
}
