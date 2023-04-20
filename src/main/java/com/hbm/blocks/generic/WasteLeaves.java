package com.hbm.blocks.generic;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WasteLeaves extends BlockLeaves implements IItemHazard {

	ItemHazardModule module;

	public WasteLeaves(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(false)).withProperty(DECAYABLE, Boolean.valueOf(false)));
		this.setTickRandomly(false);
		this.module = new ItemHazardModule();
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (!state.getValue(DECAYABLE)) {
			i |= 4;
		}

		if (state.getValue(CHECK_DECAY)) {
			i |= 8;
		}

		return i;
	}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

	@Override
	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		if(rand.nextInt(4) == 0)
			return Item.getItemFromBlock(Blocks.DEADBUSH);
		return null;
	}

	public BlockPlanks.EnumType getWoodType(int meta){
		return null;
	}

	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune){
		List<ItemStack> output = new ArrayList<ItemStack>();
		output.add(new ItemStack(ModBlocks.waste_leaves, fortune+1));
		return output;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return Blocks.LEAVES.getBlockLayer();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return Blocks.LEAVES.isOpaqueCube(state);
	}

	 @Override
  	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		this.leavesFancy = !Blocks.LEAVES.isOpaqueCube(blockState);
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
}
