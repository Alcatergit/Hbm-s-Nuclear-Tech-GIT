package com.hbm.blocks.network.energy;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.network.energy.TileEntityPylon;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PylonRedWire extends PylonBase {

	public PylonRedWire(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPylon();
	}
}
