package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityProxyCombo;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}
	
	@Override
	public void checkFluidInteraction() { } //NO!

	@Override
	public BlockPos[] getConnectionPositions() {
		List<BlockPos> positions = new ArrayList<>();
		for(int y = 0; y <= 4; y += 4) {
			for(int x = -1; x <= 1; x++) {
				for(int z = -1; z <= 1; z++) {
					if(x == 0 && z == 0 && y == 0) continue;
					BlockPos checkPos = pos.add(x, y, z);
					if(world.getTileEntity(checkPos) instanceof TileEntityProxyCombo) {
						positions.add(checkPos.add(0, y == 0 ? -1 : 1, 0));
					}
				}
			}
		}
		return positions.toArray(new BlockPos[0]);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 2,
					pos.getY(),
					pos.getZ() - 2,
					pos.getX() + 2,
					pos.getY() + 5,
					pos.getZ() + 2
					);
		}
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
