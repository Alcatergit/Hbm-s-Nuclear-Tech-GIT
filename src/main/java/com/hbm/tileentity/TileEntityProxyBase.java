package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityProxyBase extends TileEntity {

	public TileEntity getTE() {

		if(this.getBlockType() instanceof BlockDummyable) {

			BlockDummyable dummy = (BlockDummyable)this.getBlockType();

			int[] pos = dummy.findCore(world, this.pos.getX(), this.pos.getY(), this.pos.getZ());

			if(pos != null) {

				TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

				if(te != null)
					return te;
			}
		}

		return null;
	}
}