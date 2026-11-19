package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMushHuge extends Block {

	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);

	public BlockMushHuge(Material mat, String s) {
		super(mat);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.CAP));
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {
		int i = rand.nextInt(10) - 7;
		if(i < 0) {
			i = 0;
		}
		return i;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.mush);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(ModBlocks.mush), 1, 0);
	}

	public static IBlockState getState(boolean red, boolean stem) {
		Block mb = red ? ModBlocks.mush_block_red : ModBlocks.mush_block;
		return mb.getDefaultState().withProperty(VARIANT, stem ? EnumType.STEM : EnumType.CAP);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		CAP(0, "cap"),
		STEM(1, "stem");

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String name;

		EnumType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMetadata() {
			return this.meta;
		}

		public String getName() {
			return this.name;
		}

		public static EnumType byMetadata(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			return META_LOOKUP[meta];
		}

		static {
			for(EnumType e : values()) {
				META_LOOKUP[e.getMetadata()] = e;
			}
		}
	}
}