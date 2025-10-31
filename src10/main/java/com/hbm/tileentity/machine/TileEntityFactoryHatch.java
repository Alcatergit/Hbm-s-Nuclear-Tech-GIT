package com.hbm.tileentity.machine;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileEntityFactoryHatch extends TileEntity {

	TileEntity tile;

	private boolean isTEOK(TileEntity te){
		if(te instanceof TileEntityCoreTitanium)
			return ((TileEntityCoreTitanium)te).isStructureValid(world);
		if(te instanceof TileEntityCoreAdvanced)
			return ((TileEntityCoreAdvanced)te).isStructureValid(world);
		return false;
	}
	
	private TileEntity getTE(World world, BlockPos pos) {
		EnumFacing e = world.getBlockState(pos).getValue(BlockHorizontal.FACING);
		if(e == EnumFacing.NORTH)
		{
			TileEntity te = world.getTileEntity(pos.add(0, 0, 1));
			if(isTEOK(te)){
				return te;
			} else {
				return null;
			}
		}
		if(e == EnumFacing.SOUTH)
		{
			TileEntity te = world.getTileEntity(pos.add(0, 0, -1));
			if(isTEOK(te)){
				return te;
			} else {
				return null;
			}
		}
		if(e == EnumFacing.WEST)
		{
			TileEntity te = world.getTileEntity(pos.add(1, 0, 0));
			if(isTEOK(te)){
				return te;
			} else {
				return null;
			}
		}
		if(e == EnumFacing.EAST)
		{
			TileEntity te = world.getTileEntity(pos.add(-1, 0, 0));
			if(isTEOK(te)){
				return te;
			} else {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			if(tile == null) {
				tile = this.getTE(world, pos);
				if(tile == null){
					return super.getCapability(capability, facing);
				}
			}
			return tile.getCapability(capability, facing);
		}

		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			if(tile == null) {
				tile = this.getTE(world, pos);
				if(tile == null){
					return super.hasCapability(capability, facing);
				}
			}
			return tile.hasCapability(capability, facing);
		}

		return super.hasCapability(capability, facing);
	}
}
