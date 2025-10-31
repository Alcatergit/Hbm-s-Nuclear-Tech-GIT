package com.leafia.contents.machines.reactors.tokamakt2;

import com.hbm.blocks.BlockDummyable;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TokamakT2Block extends BlockDummyable {
	public TokamakT2Block(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[]{0,0,0,0,0,0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		return new TokamakT2TE();
	}
}
