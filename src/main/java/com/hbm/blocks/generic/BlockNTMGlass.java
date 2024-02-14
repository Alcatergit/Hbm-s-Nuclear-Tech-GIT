package com.hbm.blocks.generic;

import java.util.Random;
import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockNTMGlass extends BlockBreakable implements IRadResistantBlock, IItemHazard {

	BlockRenderLayer layer;
	ItemHazardModule module;
	boolean doesDrop = false;
	boolean isRadResistant = false;
	
	public BlockNTMGlass(Material materialIn, BlockRenderLayer layer, String s) {
		this(materialIn, layer, false, s);
	}

	public BlockNTMGlass(Material materialIn, BlockRenderLayer layer, boolean doesDrop, String s) {
		this(materialIn, layer, doesDrop, false, s);
	}

	public BlockNTMGlass(Material materialIn, BlockRenderLayer layer, boolean doesDrop, boolean isRadResistant, String s) {
		super(materialIn, false);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.layer = layer;
		this.doesDrop = doesDrop;
		this.isRadResistant = isRadResistant;
		this.module = new ItemHazardModule();
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	public BlockNTMGlass setSoundType(SoundType sound) {
		return (BlockNTMGlass)super.setSoundType(sound);
	}
	
	@Override
	public int quantityDropped(Random random){
		return doesDrop ? 1 : 0;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if(this.isRadResistant){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(this.isRadResistant){
			RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return doesDrop ? 1 : 0;
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
	public boolean isRadResistant(World worldIn, BlockPos blockPos){
		return this.isRadResistant;
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		float hardness = this.getExplosionResistance(null);
		if(this.isRadResistant){
			tooltip.add("§2[" + I18nUtil.resolveKey("trait.radshield") + "]");
		}
		if(hardness > 50){
			tooltip.add("§6" + I18nUtil.resolveKey("trait.blastres", hardness));
		}
	}
}
