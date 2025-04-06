package com.hbm.items.tool;

import api.hbm.item.IDesignatorItem;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDesignatorManual extends Item implements IDesignatorItem {

	public ItemDesignatorManual(String s) {
		this.setTranslationKey(s);
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
			tooltip.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.targetcoord")+"§r");
			tooltip.add("§aX: " + String.valueOf(stack.getTagCompound().getInteger("xCoord"))+"§r");
			tooltip.add("§aZ: " + String.valueOf(stack.getTagCompound().getInteger("zCoord"))+"§r");
		} else {
			tooltip.add(TextFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget2"));
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote)
			playerIn.openGui(MainRegistry.instance, ModItems.guiID_item_designator, worldIn, handIn == EnumHand.MAIN_HAND ? 1 : 0, 0, 0);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean isReady(World world, ItemStack stack, int x, int y, int z) {
		return stack.hasTagCompound();
	}

	@Override
	public Vec3 getCoords(World world, ItemStack stack, int x, int y, int z) {
		return Vec3.createVectorHelper(stack.getTagCompound().getInteger("xCoord"), 0, stack.getTagCompound().getInteger("zCoord"));
	}
}
