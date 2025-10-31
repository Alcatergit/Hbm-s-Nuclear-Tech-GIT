package com.leafia.contents.machines.elevators;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EvPulley extends BlockDummyable {
	public EvPulley(Material materialIn,String s) {
		super(materialIn,s);
	}
	@Override
	public int[] getDimensions() {
		return new int[]{0,0,1,1,1,1};
	}
	@Override
	public int getOffset() {
		return 0;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world,int meta) {
		if (meta >= 12)
			return new EvPulleyTE();
		else if (meta >= extra)
			return new TileEntityProxyCombo(false,true,false);
		return null;
	}

	@Override
	protected void fillSpace(World world,int x,int y,int z,ForgeDirection dir,int o) {
		super.fillSpace(world,x,y,z,dir,o);
		ForgeDirection d = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world,x+dir.offsetX*o-d.offsetX,y,z+dir.offsetZ*o-d.offsetZ);
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
