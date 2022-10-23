package com.hbm.blocks.generic;

import java.util.Random;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNTMGlass extends BlockBreakable implements IRadResistantBlock {

	BlockRenderLayer layer;
	boolean doesDrop = false;
	
	public BlockNTMGlass(Material materialIn, BlockRenderLayer layer, boolean doesDrop, String s) {
		super(materialIn, false);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.layer = layer;
		this.doesDrop = doesDrop;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	public BlockNTMGlass(Material materialIn, BlockRenderLayer layer, String s) {
		this(materialIn, layer, false, s);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public int quantityDropped(Random random){
		return doesDrop ? 1 : 0;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(this == ModBlocks.reinforced_glass){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(this == ModBlocks.reinforced_glass){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 0;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return layer;
	}
	
	@Override
	protected boolean canSilkHarvest() {
		return false;
	}
	
	@Override
	public float getResistance() {
		return this == ModBlocks.reinforced_glass ? 1 : 0;
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		float hardness = this.getExplosionResistance(null);
		tooltip.add("§2[Radiation Shielding]§r");
		if(hardness > 50){
			tooltip.add("§6Blast Resistance: "+hardness+"§r");
		}
	}

}
