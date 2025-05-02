package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineTurbine extends BlockContainer {

	public MachineTurbine(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineTurbine();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntity te = world.getTileEntity(pos);

			TileEntityMachineTurbine entity = (TileEntityMachineTurbine) te;
			if (entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_turbine, world, pos.getX(), pos.getY(), pos.getZ());
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMachineTurbine) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachineTurbine) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
