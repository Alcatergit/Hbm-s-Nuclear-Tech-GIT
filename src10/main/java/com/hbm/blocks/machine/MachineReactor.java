package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.TileEntityMachineReactor;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineReactor extends BlockDummyable {

	public MachineReactor(Material m, String s) {
		super(m, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta >= 12)
			return new TileEntityMachineReactor();
		return new TileEntityProxyInventory();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos1, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos = this.findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());

			if(pos == null)
				return false;

			TileEntityMachineReactor entity = (TileEntityMachineReactor) world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_reactor, world, pos[0], pos[1], pos[2]);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 0, 0, 0, 0 };
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
}
