package com.hbm.render.loader;

//TODO: make you work someday
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.HashMap;
import java.util.Map;

import static com.hbm.blocks.generic.BlockSellafieldSlaked.TEXTURE_VARIANTS;

//public class RandomBlockModelRenderer extends BlockRendererDispatcher {
//
//    private final Map<Integer, IBakedModel> bakedModels = new HashMap<>();
//
//
//    public RandomBlockModelRenderer(BlockModelShapes p_i46577_1_, BlockColors p_i46577_2_) {
//        super(p_i46577_1_, p_i46577_2_);
//        loadModels();
//    }
//
//    private void loadModels(){
//        bakedModels.put(0, getModelForState())
//    }
//
//    @Override
//    public boolean renderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn){
//
//        long l = (pos.getX() * 3129871L) ^ (long) pos.getY() * 116129781L ^ (long) pos.getZ();
//        l = l * l * 42317861L + l * 11L;
//        int i = (int) (l >> 16 & 3L);
//
//        IBakedModel model = bakedModels.get(Math.abs(i) % TEXTURE_VARIANTS);
//
//        renderModel(model, pos, buffer, packedLight, packedOverlay);
//    }
//}
