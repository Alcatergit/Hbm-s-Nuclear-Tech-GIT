package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDesignatorManual extends Item {

	public ItemDesignatorManual(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.missileTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("xCoord", 0);
		stack.getTagCompound().setInteger("zCoord", 0);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null)
		{
			tooltip.add("Target Coordinates:");
			tooltip.add("X: " + String.valueOf(stack.getTagCompound().getInteger("xCoord")));
			tooltip.add("Z: " + String.valueOf(stack.getTagCompound().getInteger("zCoord")));
		} else {
			tooltip.add("Please select a target.");
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote)
			playerIn.openGui(MainRegistry.instance, ModItems.guiID_item_designator, worldIn, handIn == EnumHand.MAIN_HAND ? 1 : 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
