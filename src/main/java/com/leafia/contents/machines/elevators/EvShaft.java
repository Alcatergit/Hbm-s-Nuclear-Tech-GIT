package com.leafia.contents.machines.elevators;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.ForgeDirection;
import com.leafia.dev.math.FiaBB;
import com.leafia.dev.math.FiaMatrix;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EvShaft extends BlockDummyable {
	public EvShaft(Material materialIn,String s) {
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

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world,int i) {
		if (i >= 12)
			return new EvShaftTE();
		return null;
	}

	@Override
	protected void fillSpace(World world,int x,int y,int z,ForgeDirection dir,int o) {
		super.fillSpace(world,x,y,z,dir,o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0,0,0,0,-2,2}, this, dir);
	}

	public FiaMatrix getMatrix(int meta) {
		FiaMatrix mat = new FiaMatrix();
		switch(meta - 10) {
			case 2:
				mat = mat.rotateY(180); break;
			case 3:
				mat = mat.rotateY(0); break;
			case 4:
				mat = mat.rotateY(270); break;
			case 5:
				mat = mat.rotateY(90); break;
		}
		return mat;
	}
	public FiaMatrix getMatrix(IBlockAccess source,BlockPos pos) {
		int[] shit = findCore(source,pos.getX(),pos.getY(),pos.getZ());
		if (shit == null) return new FiaMatrix();
		IBlockState state = source.getBlockState(new BlockPos(shit[0],shit[1],shit[2]));
		return getMatrix(getMetaFromState(state));
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state,IBlockAccess source,BlockPos pos) {
		int ang = -90;
		if (state.getValue(META) >= 12)
			ang = 90;
		FiaBB bb = new FiaBB(new FiaMatrix(new Vec3d(0.5,0,0.5)).rotateAlong(getMatrix(source,pos)).rotateY(ang).translate(0,0,-0.5+2/16d).rotateY(180),-0.5,0,0.5,1,1/16d);
		return bb.toAABB();
	}

	@Override
	public void breakBlock(World world,BlockPos pos,IBlockState state) {
		int meta = state.getValue(META);
		super.breakBlock(world,pos,state);
		EnumFacing face = EnumFacing.NORTH;
		switch(meta - 10) {
			case 2:
				face = EnumFacing.SOUTH; break;
			case 3:
				face = EnumFacing.NORTH; break;
			case 4:
				face = EnumFacing.EAST; break;
			case 5:
				face = EnumFacing.WEST; break;
			default:
				return;
		}
		BlockPos breakPos = pos.add(face.rotateY().getDirectionVec()).add(face.rotateY().getDirectionVec());
		if (world.getBlockState(breakPos).getBlock() instanceof EvShaft)
			world.setBlockToAir(breakPos);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
