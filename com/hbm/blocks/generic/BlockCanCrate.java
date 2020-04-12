package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockCanCrate extends Block {

	public BlockCanCrate(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
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
			playerIn.sendMessage(new TextComponentTranslation("The one crate you are allowed to smash!"));
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		List<Item> items = new ArrayList<Item>();
    	items.add(ModItems.canned_beef);
    	items.add(ModItems.canned_tuna);
    	items.add(ModItems.canned_mystery);
    	items.add(ModItems.canned_pashtet);
    	items.add(ModItems.canned_cheese);
    	items.add(ModItems.canned_jizz);
    	items.add(ModItems.canned_milk);
    	items.add(ModItems.canned_ass);
    	items.add(ModItems.canned_pizza);
    	items.add(ModItems.canned_tomato);
    	items.add(ModItems.canned_tube);
    	items.add(ModItems.canned_asbestos);
    	items.add(ModItems.canned_bhole);
    	items.add(ModItems.canned_hotdogs);
    	items.add(ModItems.canned_leftovers);
    	items.add(ModItems.canned_yogurt);
    	items.add(ModItems.canned_stew);
    	items.add(ModItems.canned_chinese);
    	items.add(ModItems.canned_oil);
    	items.add(ModItems.canned_fist);
    	items.add(ModItems.canned_spam);
    	items.add(ModItems.canned_fried);
    	items.add(ModItems.canned_napalm);
    	items.add(ModItems.canned_diesel);
    	items.add(ModItems.canned_kerosene);
    	items.add(ModItems.canned_recursion);
    	items.add(ModItems.canned_bark);
    	items.add(ModItems.can_smart);
    	items.add(ModItems.can_creature);
    	items.add(ModItems.can_redbomb);
    	items.add(ModItems.can_mrsugar);
    	items.add(ModItems.can_overcharge);
    	items.add(ModItems.can_luna);
    	items.add(ModItems.can_breen);
    	items.add(ModItems.can_bepis);
    	items.add(ModItems.pudding);
    	
        return items.get(rand.nextInt(items.size()));
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 5 + random.nextInt(4);
	}
	
}
