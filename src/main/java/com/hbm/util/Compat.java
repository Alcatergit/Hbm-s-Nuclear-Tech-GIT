package com.hbm.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class Compat {
	public static Item tryLoadItem(String domain, String name) {
		return Item.REGISTRY.getObject(new ResourceLocation(domain, name));
	}
	
	public static Block tryLoadBlock(String domain, String name){
		return Block.REGISTRY.getObject(new ResourceLocation(domain, name));
	}
}
