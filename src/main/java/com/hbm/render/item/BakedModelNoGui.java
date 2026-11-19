package com.hbm.render.item;

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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BakedModelNoGui implements IBakedModel {

	private TEISRBase renderer;
	
	public BakedModelNoGui(TEISRBase renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(renderer.useIdentityTransform()) return Collections.emptyList();
		return renderer.type != TransformType.GUI ? Collections.emptyList() : renderer.itemModel.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return renderer.type == TransformType.GUI && renderer.itemModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		if(renderer.useIdentityTransform()) return true;
		return renderer.type == TransformType.GUI && renderer.itemModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		if(renderer.useIdentityTransform()) return true;
		return renderer.type != TransformType.GUI || renderer.itemModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return renderer.itemModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return renderer.type != TransformType.GUI ? new ItemOverrideList(Collections.emptyList()){
			@Override
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
				renderer.entity = entity;
				renderer.world = world;
				return super.handleItemState(originalModel, stack, world, entity);
			}
		} : renderer.itemModel.getOverrides();
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		renderer.type = cameraTransformType;
		if(renderer.useIdentityTransform()) {
			Matrix4f identity = new Matrix4f();
			identity.setIdentity();
			return Pair.of(this, identity);
		}
		return Pair.of(this, renderer.itemModel.handlePerspective(cameraTransformType).getRight());
	}
}
