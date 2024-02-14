package com.hbm.items.tool;

import java.util.List;

import com.hbm.forgefluid.HbmFluidHandlerItemStackInf;
import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemFluidContainerInfinite extends Item {

	private int maxDrain;
	
	public ItemFluidContainerInfinite(int maxDrain, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxDrain = maxDrain;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new HbmFluidHandlerItemStackInf(stack, maxDrain);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
		super.addInformation(stack, world, list, flagIn);
		list.add(I18nUtil.resolveKey("desc.canisterinfinite", maxDrain * 0.02F));
	}
}
