package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemBase;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFuelRod extends ItemBase {
	
	private int lifeTime;
	private int heat;

	public ItemFuelRod(int life, int heat, String s) {
		super(s);
		this.lifeTime = life;
		this.heat = heat;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.GOLD + "["+I18nUtil.resolveKey("trait.reactorrod")+"]");
		
		tooltip.add(TextFormatting.DARK_AQUA + "  "+I18nUtil.resolveKey("desc.generates")+" " + heat + " "+I18nUtil.resolveKey("desc.heatpt"));
		tooltip.add(TextFormatting.DARK_AQUA + "  "+I18nUtil.resolveKey("desc.lasts")+" " + Library.getShortNumber(lifeTime) + " "+I18nUtil.resolveKey("desc.ticks"));
	}
	
	public static void setLifetime(ItemStack stack, int time){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack){
		if(!stack.hasTagCompound()){
			return 0;
		}
		return stack.getTagCompound().getInteger("life");
	}
	
	public int getMaxLifeTime() {
		return lifeTime;
	}
	
	public int getHeatPerTick(){
		return heat;
	}
	
	public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getLifeTime(stack) / (double)((ItemFuelRod)stack.getItem()).lifeTime;
    }

    public ItemStack getUncrafting(){
    	if(this == ModItems.rod_uranium_fuel) return new ItemStack(ModItems.billet_uranium_fuel);
    	if(this == ModItems.rod_dual_uranium_fuel) return new ItemStack(ModItems.billet_uranium_fuel, 2);
    	if(this == ModItems.rod_quad_uranium_fuel) return new ItemStack(ModItems.billet_uranium_fuel, 4);
    	
    	if(this == ModItems.rod_thorium_fuel) return new ItemStack(ModItems.billet_thorium_fuel);
    	if(this == ModItems.rod_dual_thorium_fuel) return new ItemStack(ModItems.billet_thorium_fuel, 2);
    	if(this == ModItems.rod_quad_thorium_fuel) return new ItemStack(ModItems.billet_thorium_fuel, 4);
    	
    	if(this == ModItems.rod_plutonium_fuel) return new ItemStack(ModItems.billet_plutonium_fuel);
    	if(this == ModItems.rod_dual_plutonium_fuel) return new ItemStack(ModItems.billet_plutonium_fuel, 2);
    	if(this == ModItems.rod_quad_plutonium_fuel) return new ItemStack(ModItems.billet_plutonium_fuel, 4);
    	
    	if(this == ModItems.rod_mox_fuel) return new ItemStack(ModItems.billet_mox_fuel);
    	if(this == ModItems.rod_dual_mox_fuel) return new ItemStack(ModItems.billet_mox_fuel, 2);
    	if(this == ModItems.rod_quad_mox_fuel) return new ItemStack(ModItems.billet_mox_fuel, 4);
    	
    	if(this == ModItems.rod_schrabidium_fuel) return new ItemStack(ModItems.billet_schrabidium_fuel);
    	if(this == ModItems.rod_dual_schrabidium_fuel) return new ItemStack(ModItems.billet_schrabidium_fuel, 2);
    	if(this == ModItems.rod_quad_schrabidium_fuel) return new ItemStack(ModItems.billet_schrabidium_fuel, 4);
    	return ItemStack.EMPTY;
    }
}
