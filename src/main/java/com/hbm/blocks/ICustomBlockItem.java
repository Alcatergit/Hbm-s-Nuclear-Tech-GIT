package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public interface ICustomBlockItem {

    default void registerItem() {
        ItemBlock itemBlock = new customBlockItem((Block) this);
        itemBlock.setRegistryName(((Block) this).getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
    }


    class customBlockItem extends ItemBlock {
        public customBlockItem(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.canRepair = false;
        }

    }

}
