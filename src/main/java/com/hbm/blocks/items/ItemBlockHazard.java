package com.hbm.blocks.items;

import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;
import com.hbm.blocks.generic.BlockHazardFuel;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBlockHazard extends ItemBlock {

	private int burntime = 0;
	public ItemHazardModule module;

	public ItemBlockHazard(Block block) {
		super(block);
		
		if(block instanceof IItemHazard) {
			this.module = ((IItemHazard)block).getModule();
		}

		if(block instanceof BlockHazardFuel) {
			this.burntime = ((BlockHazardFuel)block).getBurnTime();
		}
	}

	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected){
		if(entity instanceof EntityLivingBase && this.module != null)
			this.module.applyEffects((EntityLivingBase) entity, stack.getCount(), itemSlot, isSelected, ((EntityLivingBase)entity).getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		if(this.module != null)
			this.module.addInformation(stack, tooltip, flagIn);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item){
		super.onEntityItemUpdate(item);
		
		if(this.module != null)
			return this.module.onEntityItemUpdate(item);
		return super.onEntityItemUpdate(item);
	}
}
