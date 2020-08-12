package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachinePlasmaHeater extends BlockDummyable {

	public MachinePlasmaHeater(String s) {
		super(Material.IRON, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 1, 8};
	}

	@Override
	public int getOffset() {
		return 1;
	}

}
