package com.hbm.render.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

public class CrucibleTemplateBakedModel implements IBakedModel {

    ItemCameraTransforms.TransformType type;

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        return type == ItemCameraTransforms.TransformType.GUI ? Collections.emptyList() : CrucibleTemplateRender.INSTANCE.itemModel.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return type == ItemCameraTransforms.TransformType.GUI ? false : CrucibleTemplateRender.INSTANCE.itemModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return type == ItemCameraTransforms.TransformType.GUI ? false :CrucibleTemplateRender.INSTANCE.itemModel.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return type == ItemCameraTransforms.TransformType.GUI;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return CrucibleTemplateRender.INSTANCE.itemModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return type == ItemCameraTransforms.TransformType.GUI ? ItemOverrideList.NONE : CrucibleTemplateRender.INSTANCE.itemModel.getOverrides();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {

        CrucibleTemplateRender.INSTANCE.type = cameraTransformType;
        this.type = cameraTransformType;
        return type == ItemCameraTransforms.TransformType.GUI ? IBakedModel.super.handlePerspective(cameraTransformType) : CrucibleTemplateRender.INSTANCE.itemModel.handlePerspective(cameraTransformType);
    }
}
