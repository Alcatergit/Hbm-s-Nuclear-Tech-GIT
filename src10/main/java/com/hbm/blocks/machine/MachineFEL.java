package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityFEL;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineFEL extends BlockDummyable {

	public MachineFEL(Material mat, String s) {
		super(mat, s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityFEL();
		
		if(meta >= extra)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 4, 2, 1, 1};
	}

	@Override
	public int getOffset() {
		return 2;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos1, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking()){
			int[] pos = this.findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());
			
			if(pos == null)
				return false;
			
			player.openGui(MainRegistry.instance, ModBlocks.guiID_fel, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		this.makeExtra(world, x + dir.offsetX * (o - 4), y + 1, z + dir.offsetZ * (o - 4));
	}
}