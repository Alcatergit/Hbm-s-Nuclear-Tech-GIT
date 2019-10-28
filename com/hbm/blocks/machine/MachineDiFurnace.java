package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineDiFurnace extends BlockContainer {

	private final boolean isActive;
	private static boolean keepInventory;
	
	protected MachineDiFurnace(Material materialIn, String s, boolean state) {
		super(materialIn);
		isActive = state;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void updateBlockState(boolean isProcessing, World world, BlockPos pos) {
		keepInventory = true;
		
	}

}
