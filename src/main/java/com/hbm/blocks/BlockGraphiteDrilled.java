package com.hbm.blocks;

import com.hbm.blocks.machine.pile.BlockGraphiteDrilledBase;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGraphiteDrilled extends BlockGraphiteDrilledBase {
	
	public BlockGraphiteDrilled(String s){
		super(s);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!player.getHeldItem(hand).isEmpty()) {
			
			EnumFacing.Axis axis = state.getValue(AXIS);

			if(facing.getAxis() == axis) {
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				if(checkInteraction(world, x, y, z, axis, player, hand, ModItems.pile_rod_uranium, ModBlocks.block_graphite_fuel)) return true;
				if(checkInteraction(world, x, y, z, axis, player, hand, ModItems.pile_rod_plutonium, ModBlocks.block_graphite_plutonium)) return true;
				if(checkInteraction(world, x, y, z, axis, player, hand, ModItems.pile_rod_source, ModBlocks.block_graphite_source)) return true;
				if(checkInteraction(world, x, y, z, axis, player, hand, ModItems.pile_rod_boron, ModBlocks.block_graphite_rod)) return true;
				if(checkInteraction(world, x, y, z, null, player, hand, ModItems.ingot_graphite, ModBlocks.block_graphite)) return true;
			}
		}
		
		return false;
	}
	
	private boolean checkInteraction(World world, int x, int y, int z, EnumFacing.Axis meta, EntityPlayer player, EnumHand hand, Item item, Block block) {
		
		if(player.getHeldItem(hand).getItem() == item) {
			player.getHeldItem(hand).shrink(1);
			if(block instanceof BlockGraphiteDrilledBase){
				world.setBlockState(new BlockPos(x, y, z), block.getDefaultState().withProperty(AXIS, meta), 3);
			} else {
				world.setBlockState(new BlockPos(x, y, z), block.getDefaultState(), 3);
			}
			

			world.playSound(null, x + 0.5, y + 1.5, z + 0.5, HBMSoundHandler.upgradePlug, SoundCategory.BLOCKS, 1.0F, 1.0F);
			
			return true;
		}
		
		return false;
	}
}