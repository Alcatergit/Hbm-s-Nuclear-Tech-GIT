package com.hbm.items.special;

import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.main.MainRegistry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemFuelHazard extends ItemHazard {

	private int burntime;
	
	public ItemFuelHazard(String s, int burntime, boolean pythonic){
		super(0F, pythonic, s);
		this.burntime = burntime;
	}

	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		if(this == ModItems.dust)
		{
			if(MainRegistry.polaroidID == 11)
				list.add("Another one bites the dust!");
			else
				list.add("I hate dust!");
		}
		if(this == Powders.powder_fire)
		{
			list.add("Used in multi purpose bombs:");
			list.add("Incendiary bombs are fun!");
		}
	}
}
