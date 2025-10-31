package com.hbm.items.armor;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.handler.ArmorModHandler;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemModLodestone extends ItemArmorMod {

	int range;
	
	public ItemModLodestone(int range, String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
		this.range = range;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add(TextFormatting.DARK_GRAY + "Attracts nearby items");
		list.add(TextFormatting.DARK_GRAY + "Item attraction range: " + range);
		if(this == ModItems.lodestone)
			list.add("Dropped by 1:64 Smelted Iron Ingots");
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.DARK_GRAY + "  " + stack.getDisplayName() + " (Magnetic range: " + range + ")");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {
		
		List<EntityItem> items = entity.world.getEntitiesWithinAABB(EntityItem.class, entity.getEntityBoundingBox().grow(range, range, range));
		
		for(EntityItem item : items) {
			
			Vec3 vec = Vec3.createVectorHelper(entity.posX - item.posX, entity.posY - item.posY, entity.posZ - item.posZ);
			vec = vec.normalize();

			item.motionX += vec.xCoord * 0.05;
			item.motionY += vec.yCoord * 0.05;
			item.motionZ += vec.zCoord * 0.05;
			
			if(vec.yCoord > 0 && item.motionY < 0.04)
				item.motionY += 0.2;
		}
	}
}