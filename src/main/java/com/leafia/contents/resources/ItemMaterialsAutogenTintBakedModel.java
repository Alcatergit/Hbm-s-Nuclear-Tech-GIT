package com.leafia.contents.resources;

import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Type;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class ItemMaterialsAutogenTintBakedModel implements IBakedModel {
	public final ItemMaterialsAutogenTint item;
	public final int meta;
	public final IBakedModel original;
	public ItemMaterialsAutogenTintBakedModel(ItemMaterialsAutogenTint item,int meta,IBakedModel original) {
		this.item = item;
		this.meta = meta;
		this.original = original;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return ItemMaterialsAutogenTintRender.INSTANCE.itemModels.get(item).get(meta).getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	@Override
	public Pair<? extends IBakedModel,Matrix4f> handlePerspective(TransformType cameraTransformType) {
		ItemMaterialsAutogenTintRender.INSTANCE.type = cameraTransformType;
		Pair<? extends IBakedModel, Matrix4f> par = ItemMaterialsAutogenTintRender.INSTANCE.itemModels.get(item).get(meta).handlePerspective(cameraTransformType);
		return Pair.of(this, par.getRight());
	}
}
