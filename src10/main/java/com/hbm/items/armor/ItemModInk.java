package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemModInk extends ItemArmorMod {

	public ItemModInk(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.LIGHT_PURPLE + "10% chance to nullify damage");
		list.add(TextFormatting.LIGHT_PURPLE + "Flowers!");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.LIGHT_PURPLE + "  " + stack.getDisplayName() + " (10% chance to nullify damage)");
	}
	
	@Override
	public void modDamage(LivingHurtEvent event, ItemStack armor) {
		
		if(event.getEntity().world.rand.nextInt(10) == 0) {
			event.setAmount(0);
			
			if(!event.getEntity().world.isRemote) {
				
				if(event.getEntity().world.rand.nextInt(10) == 0)
					event.getEntity().entityDropItem(new ItemStack(Blocks.YELLOW_FLOWER), 1.0F);
				
				event.getEntity().entityDropItem(new ItemStack(Blocks.RED_FLOWER, 1, event.getEntity().world.rand.nextInt(9)), 1.0F);
			}
		}
	}
}