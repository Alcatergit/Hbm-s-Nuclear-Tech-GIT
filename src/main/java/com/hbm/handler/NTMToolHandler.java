package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.HashMap;

import static api.hbm.block.IToolable.ToolType;
import static com.hbm.inventory.RecipesCommon.*;

public class NTMToolHandler {

    public static HashMap<Pair<ToolType, MetaBlock>, Pair<AStack[], MetaBlock>> conversions = new HashMap<>();

    public static HashMap<Pair<ToolType, MetaBlock>, Pair<AStack[], MetaBlock>> getConversions() {
        return conversions;
    }

    public static void addRecepie(ToolType toolType, String blockInTralslationKey, int metaIn, String blockOutTranslationKey, int metaOut, AStack... materials) {
        Block blockInRaw = Block.getBlockFromName(blockInTralslationKey);
        Block blockOutRaw = Block.getBlockFromName(blockOutTranslationKey);
        if (blockInRaw == null) {
            MainRegistry.logger.warn("Block '{}' not found for input block in recipe.", blockInTralslationKey);
            return;
        }
        if (blockOutRaw == null) {
            MainRegistry.logger.warn("Block '{}' not found for output block in recipe.", blockOutTranslationKey);
            return;
        }
        try {
            MetaBlock blockIn = new MetaBlock(blockInRaw, metaIn);
            MetaBlock blockOut = new MetaBlock(blockOutRaw, metaOut);


            MainRegistry.logger.warn("Ore Dictionary entry is invalid or empty for recipe with blocks: {} and {}.", blockInTralslationKey, blockOutTranslationKey);
            Pair<AStack[], MetaBlock> pair = new Pair<>(materials, blockOut);
            conversions.put(new Pair<>(toolType, blockIn), pair);


        } catch (Exception e) {
            MainRegistry.logger.error("An error occurred while processing recipe for blocks '{}' and '{}': {}", blockInTralslationKey, blockOutTranslationKey, e.getMessage());
        }
    }

    public static void addRecepie(ToolType toolType, Block blockIn, Block blockOut, AStack... boltOredict) {
        addRecepie(toolType, blockIn.getTranslationKey(), 0, blockOut.getTranslationKey(), 0, boltOredict);
    }

    public static void register() {
        conversions.put(new Pair<>(ToolType.BOLT, new MetaBlock(ModBlocks.watz_casing, 0)), new Pair<>(new AStack[]{new OreDictStack(OreDictManager.DURA.bolt(), 4)}, new MetaBlock(ModBlocks.watz_casing, 1)));
        conversions.put(new Pair<>(ToolType.BOLT, new MetaBlock(Blocks.STONE)), new Pair<>(new AStack[]{new OreDictStack(OreDictManager.DURA.bolt(), 1)}, new MetaBlock(Blocks.COBBLESTONE)));
    }


}
