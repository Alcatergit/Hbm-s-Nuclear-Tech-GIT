package com.hbm.render.block;

import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

//Drillgon200: I want to die.
public class HbmCustomBakedModel implements IBakedModel {

	private TransformType currentTransformType;
	
	private List<BakedQuad>[] bakedQuadsCache;
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand){
		return null;
	}

	@Override
	public boolean isAmbientOcclusion(){
		return false;
	}

	@Override
	public boolean isGui3d(){
		return false;
	}

	@Override
	public boolean isBuiltInRenderer(){
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture(){
		return null;
	}

	@Override
	public ItemOverrideList getOverrides(){
		return null;
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType){
		currentTransformType = cameraTransformType;
		return IBakedModel.super.handlePerspective(cameraTransformType);
	}
	
	@Override
	public ItemCameraTransforms getItemCameraTransforms(){
		return IBakedModel.super.getItemCameraTransforms();
	}

}
