package com.leafia.contents.network.spk_cable;

import com.hbm.blocks.network.energy.BlockCable;
import com.hbm.interfaces.ILaserable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SPKCableBlock extends BlockCable {
	public SPKCableBlock(Material materialIn,String s) {
		super(materialIn,s);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new SPKCableTE();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state,IBlockAccess world,BlockPos pos) {
		if (world.getTileEntity(pos) instanceof SPKCableTE) {
			boolean pX = world.getTileEntity(pos.add(1, 0, 0)) instanceof ILaserable;
			boolean nX = world.getTileEntity(pos.add(-1, 0, 0)) instanceof ILaserable;
			boolean pY = world.getTileEntity(pos.add(0, 1, 0)) instanceof ILaserable;
			boolean nY = world.getTileEntity(pos.add(0, -1, 0)) instanceof ILaserable;
			boolean pZ = world.getTileEntity(pos.add(0, 0, 1)) instanceof ILaserable;
			boolean nZ = world.getTileEntity(pos.add(0, 0, -1)) instanceof ILaserable;
			return getBB(pX,pY,pZ,nX,nY,nZ);
		}
		return FULL_BLOCK_AABB;
	}
}
