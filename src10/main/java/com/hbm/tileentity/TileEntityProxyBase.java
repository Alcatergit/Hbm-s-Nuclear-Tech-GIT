package com.hbm.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.BlockHadronAccess;
import com.hbm.blocks.machine.MachineDiFurnaceExtension;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityHadron;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TileEntityProxyBase extends TileEntityLoadedBase {

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

		/// this spares me the hassle of registering a new child class TE that aims at the right target ///
		//Drillgon200: Incidentally, it's also a gateway to some very messy code, the very thing this class is supposed to prevent.

		if(this.getBlockType() instanceof BlockHadronAccess) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());


			for(int i = 1; i < 3; i++) {
				TileEntity te = world.getTileEntity(new BlockPos(pos.getX() + dir.offsetX * i, pos.getY() + dir.offsetY * i, pos.getZ() + dir.offsetZ * i));

				if(te instanceof TileEntityHadron) {
					return te;
				}
			}
		}

		if (this.getBlockType() instanceof MachineDiFurnaceExtension) {
			TileEntity te = world.getTileEntity(pos.down());

			if (te instanceof TileEntityDiFurnace) {
				return te;
			}
		}

		return null;
	}
}