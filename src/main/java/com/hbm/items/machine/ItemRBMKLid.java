package com.hbm.items.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRBMKLid extends Item {

	public ItemRBMKLid(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos bpos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		Block b = world.getBlockState(bpos).getBlock();
		
		if(!world.isRemote && b instanceof RBMKBase) {
			RBMKBase rbmk = (RBMKBase) b;
			
			int[] pos = rbmk.findCore(world, bpos.getX(), bpos.getY(), bpos.getZ());
			
			if(pos == null)
				return EnumActionResult.FAIL;
			
			TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
			
			if(!(te instanceof TileEntityRBMKBase))
				return EnumActionResult.FAIL;
			
			TileEntityRBMKBase tile = (TileEntityRBMKBase) te;
			
			if(tile.hasLid())
				return EnumActionResult.FAIL;
			
			int meta = RBMKBase.DIR_NORMAL_LID.ordinal();
			
			if(this == ModItems.rbmk_lid_glass) {
				meta = RBMKBase.DIR_GLASS_LID.ordinal();
				world.playSound(null, bpos.getX() + 0.5, bpos.getY() + 0.5, bpos.getZ() + 0.5, SoundEvents.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1, 0.8F);
			} else {
				world.playSound(null, bpos.getX() + 0.5, bpos.getY() + 0.5, bpos.getZ() + 0.5, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, 0.8F);
			}
			
			world.setBlockState(new BlockPos(pos[0], pos[1], pos[2]), world.getBlockState(new BlockPos(pos[0], pos[1], pos[2])).withProperty(BlockDummyable.META, meta + RBMKBase.offset), 3);
			NBTTagCompound nbt = tile.writeToNBT(new NBTTagCompound());
			world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2])).readFromNBT(nbt);
			
			player.getHeldItem(hand).shrink(1);
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
}