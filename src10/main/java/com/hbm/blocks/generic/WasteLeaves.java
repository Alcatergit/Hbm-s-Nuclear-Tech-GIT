package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyEnum;
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

public class WasteLeaves extends BlockLeaves {

    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class);

    public WasteLeaves(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK));
		this.setTickRandomly(false);
		ModBlocks.ALL_BLOCKS.add(this);
	}

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random){
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
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
		return Items.AIR;
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
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items){
        for(BlockPlanks.EnumType type : BlockPlanks.EnumType.values()){
		    items.add(new ItemStack(this, 1, type.getMetadata()));
        }
	}

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, this.getWoodType(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return BlockPlanks.EnumType.byMetadata(meta);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
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

    @Override
    public NonNullList<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMetadata()));
    }
}
