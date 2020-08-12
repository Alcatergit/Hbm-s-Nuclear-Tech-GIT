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

public class GunRevolverBakedModel implements IBakedModel {

	private TransformType type;
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return type != TransformType.GUI ? Collections.emptyList() : GunRevolverRender.INSTANCE.revolverModel.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return type != TransformType.GUI ? false : GunRevolverRender.INSTANCE.revolverModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return type != TransformType.GUI ? false : GunRevolverRender.INSTANCE.revolverModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return type != TransformType.GUI;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return GunRevolverRender.INSTANCE.revolverModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return type != TransformType.GUI ? ItemOverrideList.NONE : GunRevolverRender.INSTANCE.revolverModel.getOverrides();
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType type) {
		this.type = type;
		GunRevolverRender.INSTANCE.type = type;
		return Pair.of(this, GunRevolverRender.INSTANCE.revolverModel.handlePerspective(type).getRight());
	}

}
