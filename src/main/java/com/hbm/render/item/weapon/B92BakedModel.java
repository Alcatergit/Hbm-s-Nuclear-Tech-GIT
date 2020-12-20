package com.hbm.render.item.weapon;

import java.util.Collections;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class B92BakedModel implements IBakedModel {
	
	TransformType type;
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		
		return type != TransformType.GUI ? Collections.emptyList() : ItemRenderGunAnim.INSTANCE.b92ItemModel.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return type != TransformType.GUI ? false : ItemRenderGunAnim.INSTANCE.b92ItemModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return type != TransformType.GUI ? false :ItemRenderGunAnim.INSTANCE.b92ItemModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return type != TransformType.GUI;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return ItemRenderGunAnim.INSTANCE.b92ItemModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return type != TransformType.GUI ? ItemOverrideList.NONE : ItemRenderGunAnim.INSTANCE.b92ItemModel.getOverrides();
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		
		ItemRenderGunAnim.INSTANCE.type = cameraTransformType;
		this.type = cameraTransformType;
		return type != TransformType.GUI ? Pair.of(this, null) : ItemRenderGunAnim.INSTANCE.b92ItemModel.handlePerspective(cameraTransformType);
	}

}
