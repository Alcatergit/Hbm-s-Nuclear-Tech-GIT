package com.hbm.items.special;

import com.hbm.interfaces.IDoor;
import com.hbm.items.ModItems;
import com.hbm.blocks.BlockDummyable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDoorSkin extends Item {

	public String tex;
	
	public ItemDoorSkin(String s, String tex) {
		this.tex = tex;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){

			if(worldIn.getBlockState(pos).getBlock() instanceof BlockDummyable){
				int[] pos1 = ((BlockDummyable) worldIn.getBlockState(pos).getBlock()).findCore(worldIn, pos.getX(), pos.getY(), pos.getZ());
				if(pos1 != null){
					TileEntity te = worldIn.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));
					if(te instanceof IDoor){
						if(((IDoor) te).setTexture(tex)){
							return EnumActionResult.SUCCESS;
						}
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
