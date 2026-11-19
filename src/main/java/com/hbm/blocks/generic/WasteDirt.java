package com.hbm.blocks.generic;

import java.util.Locale;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WasteDirt extends WasteEarth {

	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	public enum Variant implements IStringSerializable {
		DIRT,
		PODZOL;

		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	public WasteDirt(Material materialIn, SoundType type, boolean tick, String s) {
		super(materialIn, type, tick, s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, Variant.DIRT));
	}

	public static IBlockState podzolState(int meta) {
		return ModBlocks.waste_dirt.getDefaultState()
				.withProperty(VARIANT, Variant.PODZOL)
				.withProperty(META, Math.max(0, Math.min(6, meta)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META, VARIANT});
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(META);
		return state.getValue(VARIANT) == Variant.PODZOL ? meta + 8 : meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean podzol = meta >= 8;
		return this.getDefaultState()
				.withProperty(VARIANT, podzol ? Variant.PODZOL : Variant.DIRT)
				.withProperty(META, podzol ? meta - 8 : meta);
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		if(state.getValue(VARIANT) == Variant.PODZOL) {
			return new ItemStack(this, 1, 1);
		}
		return super.getItem(world, pos, state);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(this, 1, state.getValue(VARIANT) == Variant.PODZOL ? 1 : 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
		items.add(new ItemStack(this, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}