package com.leafia.contents.machines.panel.controltorch;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ControlTorchBlock extends BlockTorch implements ITileEntityProvider {
	private final boolean isOn;
	public ControlTorchBlock(String s,boolean isOn){
		super();
		this.isOn = isOn;
		this.setTranslationKey("control_torch");
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn,World worldIn,BlockPos pos,Random rand) {
		if (this.isOn) {
			double d0 = (double)pos.getX() + (double)0.5F + (rand.nextDouble() - (double)0.5F) * 0.2;
			double d1 = (double)pos.getY() + 0.7 + (rand.nextDouble() - (double)0.5F) * 0.2;
			double d2 = (double)pos.getZ() + (double)0.5F + (rand.nextDouble() - (double)0.5F) * 0.2;
			EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
			if (enumfacing.getAxis().isHorizontal()) {
				EnumFacing enumfacing1 = enumfacing.getOpposite();
				double d3 = 0.27;
				d0 += 0.27 * (double)enumfacing1.getXOffset();
				d1 += 0.22;
				d2 += 0.27 * (double)enumfacing1.getZOffset();
			}

			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double)0.0F, (double)0.0F, (double)0.0F, new int[0]);
		}
	}
	@Override
	public ItemStack getItem(World worldIn,BlockPos pos,IBlockState state) {
		return new ItemStack(ModBlocks.control_torch_unlit);
	}
	@Override
	public boolean isAssociatedBlock(Block other) {
		return other == ModBlocks.control_torch || other == ModBlocks.control_torch_unlit;
	}
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.isOn && blockState.getValue(FACING) != side ? 15 : 0;
	}
	public int getStrongPower(IBlockState blockState,IBlockAccess blockAccess,BlockPos pos,EnumFacing side) {
		return side == EnumFacing.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (this.isOn) {
			for(EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (this.isOn) {
			for(EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		return new ControlTorchTE(isOn);
	}
}
