package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockArmorTable extends Block {

	public BlockArmorTable(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			player.openGui(MainRegistry.instance, ModBlocks.guiID_armor_table, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return false;
	}
}
