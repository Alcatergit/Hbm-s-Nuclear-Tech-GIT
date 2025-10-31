package com.leafia.contents.machines.elevators.floors;

import api.hbm.block.IToolable.ToolType;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ModBlocks.Elevators;
import com.hbm.items.tool.ItemTooling;
import com.hbm.main.MainRegistry;
import com.leafia.dev.math.FiaBB;
import com.leafia.dev.math.FiaMatrix;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EvFloor extends BlockDummyable {
	public EvFloor(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		int[] core = this.findCore(world,pos.getX(),pos.getY(),pos.getZ());
		if (core == null) return false;
		if (player.getHeldItem(hand).getItem() instanceof ItemTooling) {
			ItemTooling tool = (ItemTooling)player.getHeldItem(hand).getItem();
			if (tool.getType().equals(ToolType.SCREWDRIVER)) {
				if (!world.isRemote) {
					TileEntity te = world.getTileEntity(new BlockPos(core[0],core[1],core[2]));
					if (te instanceof EvFloorTE)
						((EvFloorTE)te).openGui(player);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public int[] getDimensions(){
		return new int[]{1, 0, 0, 0, 0, 0};
	}
	@Override
	public int getOffset() {
		return 0;
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
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		if (meta >= 12)
			return new EvFloorTE();
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state,IBlockAccess source,BlockPos pos) {
		FiaBB bb = new FiaBB(new FiaMatrix(new Vec3d(0.5,0,0.5)).rotateAlong(getMatrix(source,pos)).translate(0,0,0.5).rotateY(180),-0.5,0,0.5,1,2/16d);
		return bb.toAABB();
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState,IBlockAccess worldIn,BlockPos pos) {
		int[] shit = findCore(worldIn,pos.getX(),pos.getY(),pos.getZ());
		if (shit != null) {
			TileEntity te = worldIn.getTileEntity(new BlockPos(shit[0],shit[1],shit[2]));
			if (te instanceof EvFloorTE) {
				if (((EvFloorTE) te).open.cur > 0) return NULL_AABB;
			}
		}
		return super.getCollisionBoundingBox(blockState,worldIn,pos);
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
