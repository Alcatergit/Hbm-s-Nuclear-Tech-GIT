package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineOrbus;
import com.hbm.tileentity.machine.oil.TileEntityMachineFrackingTower;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineOilWell extends BlockDummyable {

	public MachineOilWell(Material mat, String s) {
		super(mat, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta >= 12) return new TileEntityMachineOilWell();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {9, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {1, -1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x, y + 1, z, new int[] {8, 0, 1, 1, 1, 1}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x + 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x - 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir) &&
				MultiblockHandlerXR.checkSpace(world, x - 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, x, y, z, dir);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {1, -1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y + 1, z, new int[] {8, 0, 1, 1, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - 1, y + 1, z + 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - 1, y + 1, z - 1, new int[] {-1, 1, 0, 0, 0, 0}, this, dir);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] posC = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());

			if (posC == null)
				return false;

			TileEntityMachineOilWell entity = (TileEntityMachineOilWell) world.getTileEntity(new BlockPos(posC[0], posC[1], posC[2]));
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_well, world, posC[0], posC[1], posC[2]);
			}
			return true;
		} else {
			return false;
		}
	}
}
