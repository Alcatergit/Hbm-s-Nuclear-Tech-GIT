package com.hbm.blocks.generic;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.material.Material;

public class BlockRadResistantHazard extends BlockRadResistant implements IItemHazard {

	ItemHazardModule module;
	
	public BlockRadResistantHazard(Material materialIn, String s) {
		super(materialIn, s);
		this.module = new ItemHazardModule();
	}
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}
}
