package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFuel extends Item {

	private int burntime;
	
	public ItemFuel(String s, int burntime){
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.burntime = burntime;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this == ModItems.dust)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Another one bites the dust!");
			else
				list.add("I hate dust!");
		}
		if(this == ModItems.powder_fire)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Incendiary bombs are fun!");
		}
	}
}
