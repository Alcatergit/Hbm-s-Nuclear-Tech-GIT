package com.hbm.blocks.generic;

import java.util.Random;
import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
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
import org.jetbrains.annotations.NotNull;

public class WasteLeaves extends BlockOldLeaf {

	public WasteLeaves(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK).withProperty(CHECK_DECAY, Boolean.FALSE).withProperty(DECAYABLE, Boolean.FALSE));
		this.setTickRandomly(false);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, VARIANT, CHECK_DECAY, DECAYABLE);
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
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random){
    }

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		if(RANDOM.nextInt(4) == 0)
			drops.add(new ItemStack(Item.getItemFromBlock(Blocks.DEADBUSH)));
		if(RANDOM.nextInt(3) == 0)
			drops.add(new ItemStack(Items.STICK));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		if(rand.nextInt(4) == 0)
			return Item.getItemFromBlock(Blocks.DEADBUSH);
		return null;
	}

	@Override
	protected int getSaplingDropChance(IBlockState state){
		return 0;
	}

	@Override
	public void dropBlockAsItemWithChance(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, float chance, int fortune){
	}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance){
		if(worldIn.rand.nextInt(chance) == 0) {
			spawnAsEntity(worldIn, pos, new ItemStack(ModItems.nuclear_waste_tiny));
		}
	}

	@Override
	public BlockPlanks.@NotNull EnumType getWoodType(int meta){
		return BlockPlanks.EnumType.OAK;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items){
		items.add(new ItemStack(this, 1, BlockPlanks.EnumType.OAK.getMetadata()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Blocks.LEAVES.getRenderLayer();
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
