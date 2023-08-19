package com.hbm.blocks.generic;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockRotatablePillarHazard extends BlockRotatablePillar implements IItemHazard {

	ItemHazardModule module;
	
	public BlockRotatablePillarHazard(Material materialIn, String s) {
		super(materialIn, s);
		this.module = new ItemHazardModule();
	}

	public BlockRotatablePillarHazard(Material mat, SoundType type, String s) {
		this(mat, s);
		this.setSoundType(type);
	}
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}
}
