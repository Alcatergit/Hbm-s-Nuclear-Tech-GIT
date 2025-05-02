package com.hbm.items.tool;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDyatlov extends Item {

	public ItemDyatlov(String s){
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!world.isRemote) {
			
			if(world.getBlockState(pos).getBlock() instanceof RBMKBase) {
				
				RBMKBase rbmk = (RBMKBase)world.getBlockState(pos).getBlock();
				
				int[] pos1 = rbmk.findCore(world, pos.getX(), pos.getY(), pos.getZ());
				
				if(pos1 != null) {
					
					TileEntity te = world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));
					
					if(te instanceof TileEntityRBMKBase) {

						((TileEntityRBMKBase)te).meltdown();
						//((TileEntityRBMKBase)te).heat = 100000;
					}
				}
			}
		}
		
		return EnumActionResult.PASS;
	}
	
}