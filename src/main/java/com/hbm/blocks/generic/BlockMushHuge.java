package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMushHuge extends Block implements IItemHazard {

	ItemHazardModule module;

	public BlockMushHuge(Material mat, String s) {
		super(mat);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setSoundType(SoundType.PLANT);
		this.module = new ItemHazardModule();
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
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
	
}
