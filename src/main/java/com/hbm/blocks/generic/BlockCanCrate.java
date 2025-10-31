package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems.Foods;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCanCrate extends Block {

	public BlockCanCrate(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)
		{
			playerIn.sendMessage(new TextComponentTranslation("chat.crate.cansmash"));
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		List<Item> items = new ArrayList<Item>();
    	items.add(Foods.canned_beef);
    	items.add(Foods.canned_tuna);
    	items.add(Foods.canned_mystery);
    	items.add(Foods.canned_pashtet);
    	items.add(Foods.canned_cheese);
    	items.add(Foods.canned_jizz);
    	items.add(Foods.canned_milk);
    	items.add(Foods.canned_ass);
    	items.add(Foods.canned_pizza);
    	items.add(Foods.canned_tomato);
    	items.add(Foods.canned_tube);
    	items.add(Foods.canned_asbestos);
    	items.add(Foods.canned_bhole);
    	items.add(Foods.canned_hotdogs);
    	items.add(Foods.canned_leftovers);
    	items.add(Foods.canned_yogurt);
    	items.add(Foods.canned_stew);
    	items.add(Foods.canned_chinese);
    	items.add(Foods.canned_oil);
    	items.add(Foods.canned_fist);
    	items.add(Foods.canned_spam);
    	items.add(Foods.canned_fried);
    	items.add(Foods.canned_napalm);
    	items.add(Foods.canned_diesel);
    	items.add(Foods.canned_kerosene);
    	items.add(Foods.canned_recursion);
    	items.add(Foods.canned_bark);
    	items.add(Foods.can_smart);
    	items.add(Foods.can_creature);
    	items.add(Foods.can_redbomb);
    	items.add(Foods.can_mrsugar);
    	items.add(Foods.can_overcharge);
    	items.add(Foods.can_luna);
    	items.add(Foods.can_breen);
    	items.add(Foods.can_bepis);
    	items.add(Foods.pudding);
    	
        return items.get(rand.nextInt(items.size()));
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 5 + random.nextInt(4);
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
}
