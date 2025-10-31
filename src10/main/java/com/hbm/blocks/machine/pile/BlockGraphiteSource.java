package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.pile.TileEntityPileSource;

import api.hbm.block.IToolable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGraphiteSource extends BlockGraphiteDrilledTE implements IToolable {

	public BlockGraphiteSource(String s){
		super(s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int mets) {
		return new TileEntityPileSource();
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		super.getDrops(drops, world, pos, state, fortune);
		drops.add(new ItemStack(whoAmIAgain()));
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool){
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		if(!world.isRemote) {

			EnumFacing.Axis axis = world.getBlockState(new BlockPos(x, y, z)).getValue(AXIS);
			
			if(side.getAxis() == axis) {
				world.setBlockState(new BlockPos(x, y, z), ModBlocks.block_graphite_drilled.getDefaultState().withProperty(AXIS, axis), 3);
				ejectItem(world, x, y, z, side, new ItemStack(whoAmIAgain()));
			}
		}
		
		return true;
	}
	
	private Item whoAmIAgain() {
		return this == ModBlocks.block_graphite_plutonium ? ModItems.pile_rod_plutonium : ModItems.pile_rod_source;
	}
}