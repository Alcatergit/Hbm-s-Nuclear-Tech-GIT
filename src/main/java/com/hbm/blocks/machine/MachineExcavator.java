package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineExcavator extends BlockDummyable {

	public MachineExcavator(Material mat, String s) {
        super(Material.IRON, s);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12) return new TileEntityMachineExcavator();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return standardOpenBehavior(world, pos.getX(), pos.getY(), pos.getZ(), player, 0);
    }

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 3, 3, 3};
	}

	@Override
	public int getOffset() {
		return 3;
	}

	@Override
	public int getHeightOffset() {
		return 3;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x += dir.offsetX * o;
		y += dir.offsetY * o;
		z += dir.offsetZ * o;
		
		return MultiblockHandlerXR.checkSpace(world, x, y, z, getDimensions(), x, y, z, dir);
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		x += dir.offsetX * o;
		y += dir.offsetY * o;
		z += dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, getDimensions(), this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {-1, 3, 3, -2, 3, -2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {-1, 3, 3, -2, -2, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {-1, 3, -2, 3, 3, 3}, this, dir);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * 3 + rot.offsetX, y + 1, z + dir.offsetZ * 3 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 3 - rot.offsetX, y + 1, z + dir.offsetZ * 3 - rot.offsetZ);
		this.makeExtra(world, x + rot.offsetX * 3, y + 1, z + rot.offsetZ * 3);
		this.makeExtra(world, x - rot.offsetX * 3, y + 1, z - rot.offsetZ * 3);
	}
}
