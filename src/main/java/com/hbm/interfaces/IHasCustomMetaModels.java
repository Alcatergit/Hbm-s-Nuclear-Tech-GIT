package com.hbm.interfaces;

import java.util.Set;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public interface IHasCustomMetaModels {

	public Set<Integer> getMetaValues();

	public ModelResourceLocation getResourceLocation(int meta);
}
