package com.hbm.interfaces;

import java.util.Set;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public interface IHasCustomMetaModels {

	Set<Integer> getMetaValues();

	ModelResourceLocation getResourceLocation(int meta);
}
