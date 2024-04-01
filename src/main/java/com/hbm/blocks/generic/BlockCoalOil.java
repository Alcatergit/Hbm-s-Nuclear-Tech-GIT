package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemToolAbility;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockCoalOil extends BlockOre {

	public BlockCoalOil(String s) {
		super();
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		for(EnumFacing dir : EnumFacing.VALUES) {

        	IBlockState nS = world.getBlockState(pos.offset(dir));
        	Block n = nS.getBlock();

        	if(n == ModBlocks.ore_coal_oil_burning || n == ModBlocks.balefire || n == Blocks.FIRE || nS.getMaterial() == Material.LAVA) {
        		world.scheduleUpdate(pos, this, world.rand.nextInt(20) + 2);
        	}
        }
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if(player.getHeldItemMainhand() == null)
    		return;

    	if(!(player.getHeldItemMainhand().getItem() instanceof ItemTool || player.getHeldItemMainhand().getItem() instanceof ItemToolAbility))
    		return;

    	ItemTool tool = (ItemTool)player.getHeldItemMainhand().getItem();

    	if(tool.getToolMaterialName() != ToolMaterial.WOOD.toString()) {

    		if(world.rand.nextInt(10) == 0)
    			world.setBlockState(pos, Blocks.FIRE.getDefaultState());
    	}
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.setBlockState(pos, ModBlocks.ore_coal_oil_burning.getDefaultState());
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.COAL;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 2 + random.nextInt(2);
	}
}
