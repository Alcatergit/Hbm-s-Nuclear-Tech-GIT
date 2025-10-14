package com.hbm.items.special;

import com.hbm.blocks.generic.WasteLeaves;
import net.minecraft.item.ItemLeaves;

public class ItemNTMLeaves extends ItemLeaves {

	public ItemNTMLeaves(WasteLeaves block){
        super(block);
	}

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
