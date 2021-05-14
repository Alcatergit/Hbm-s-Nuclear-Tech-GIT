package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidVisualConnectable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockReactor extends Block implements IFluidVisualConnectable {

	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");

	public BlockReactor(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(this != ModBlocks.reactor_element)
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

		if(playerIn.isSneaking()) {
			if(state.getValue(ACTIVATED) == false) {
				worldIn.setBlockState(pos, state.withProperty(ACTIVATED, true), 3);
			} else {
				worldIn.setBlockState(pos, state.withProperty(ACTIVATED, false), 3);
			}

			return true;
		}

		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(ACTIVATED, false);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ACTIVATED });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVATED) == true ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return meta == 0 ? this.getDefaultState().withProperty(ACTIVATED, false) : this.getDefaultState().withProperty(ACTIVATED, true);
	}

	@Override
	public boolean shouldConnect(Fluid f) {
		return this == ModBlocks.reactor_conductor;
	}

}
