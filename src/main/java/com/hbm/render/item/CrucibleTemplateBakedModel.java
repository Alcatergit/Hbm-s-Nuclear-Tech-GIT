package com.hbm.render.item;

import java.util.Collections;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import com.hbm.inventory.CrucibleRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class CrucibleTemplateBakedModel implements IBakedModel {

	TransformType type;
	ItemStack recipeStack;

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		IBakedModel im = CrucibleTemplateRender.INSTANCE.itemModel;
		return type == TransformType.GUI ? Collections.emptyList() : im.getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		IBakedModel im = CrucibleTemplateRender.INSTANCE.itemModel;
		return type != TransformType.GUI && im.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		IBakedModel im = CrucibleTemplateRender.INSTANCE.itemModel;
		return type != TransformType.GUI && im.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return type == null || type == TransformType.GUI || (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && type != null);
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return CrucibleTemplateRender.INSTANCE.itemModel.getParticleTexture();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return new ItemOverrideList(Collections.emptyList()) {
			@Override
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
				if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					recipeStack = CrucibleRecipes.getIcon(stack);
				} else {
					recipeStack = null;
				}
				return originalModel;
			}
		};
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {

		IBakedModel im = CrucibleTemplateRender.INSTANCE.itemModel;
		if (cameraTransformType == TransformType.GUI) {
			CrucibleTemplateRender.INSTANCE.type = cameraTransformType;
			this.type = cameraTransformType;
			return IBakedModel.super.handlePerspective(cameraTransformType);
		} else {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && recipeStack != null && !recipeStack.isEmpty()) {
				CrucibleTemplateRender.INSTANCE.type = cameraTransformType;
				this.type = cameraTransformType;
				try {
					IBakedModel recipeModel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(recipeStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player);
					return Pair.of(this, recipeModel.handlePerspective(cameraTransformType).getRight());
				} catch (Exception ignored) {
				}
				return Pair.of(this, null);
			}
			CrucibleTemplateRender.INSTANCE.type = null;
			this.type = null;
			return im.handlePerspective(cameraTransformType);
		}
	}
}
