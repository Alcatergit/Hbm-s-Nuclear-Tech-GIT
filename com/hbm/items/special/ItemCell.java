package com.hbm.items.special;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCell extends Item {

	public ItemCell(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.partsTab);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	//TODO balefire bomb
		/*	if(world.getBlockState(pos).getBlock() instanceof BlockCrashedBomb)
		{
			Random rand = new Random();
			int i = rand.nextInt(100);
			if(i == 0)
			{
	            if (!world.isRemote)
	            {
	            	((BlockCrashedBomb)world.getBlockState(pos)).getBlock().explode(world, pos);
	            }
			} else if(i < 90)
			{
	            //if (!world.isRemote)
	            {
	            	player.inventory.consumeInventoryItem(ModItems.cell_empty);

	            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_antimatter)))
	            	{
	            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_antimatter, 1, 0), false);
	            	}
	            }
			} else {
	            //if (!world.isRemote)
	            {
	            	player.inventory.consumeInventoryItem(ModItems.cell_empty);

	            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.cell_anti_schrabidium)))
	            	{
	            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.cell_anti_schrabidium, 1, 0), false);
	            	}
	            }
			}
			return true;
		}
		return false;*/
		return EnumActionResult.PASS;
    }
	
}
