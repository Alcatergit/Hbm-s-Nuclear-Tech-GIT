package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemBattery extends Item {

	private long maxCharge;
	private long chargeRate;
	private long dischargeRate;
	
	public ItemBattery(long dura, long chargeRate, long dischargeRate, String s){
		this.maxCharge = dura;
		this.chargeRate = chargeRate;
		this.dischargeRate = dischargeRate;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setMaxDamage(100);
		ModItems.ALL_ITEMS.add(this);
	}
	
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(stack.getItem() == ModItems.battery_creative)
			return;
		long charge = maxCharge;
		if(stack.hasTagCompound())
			charge = ItemBattery.getCharge(stack);
		
		
		if(stack.getItem() != ModItems.fusion_core && 
				stack.getItem() != ModItems.factory_core_titanium && 
				stack.getItem() != ModItems.factory_core_advanced && 
				stack.getItem() != ModItems.energy_core && 
				stack.getItem() != ModItems.dynosphere_desh && 
				stack.getItem() != ModItems.dynosphere_schrabidium && 
				stack.getItem() != ModItems.dynosphere_euphemium && 
				stack.getItem() != ModItems.dynosphere_dineutronium)
				
		{
			list.add("Energy stored: " + Library.getShortNumber(charge * 100) + "/" + Library.getShortNumber(maxCharge * 100) + "HE");
		} else {
			String charge1 = Library.getShortNumber((charge  * 100) / this.maxCharge);
			list.add("Charge: " + charge1 + "%");
			list.add("(" + Library.getShortNumber(charge * 100) + "/" + Library.getShortNumber(maxCharge * 100) + "HE)");
		}
		list.add("Charge rate: " + Library.getShortNumber(chargeRate * 100) + "HE/t");
		list.add("Discharge rate: " + Library.getShortNumber(dischargeRate * 100) + "HE/t");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.battery_schrabidium)
    	{
        	return EnumRarity.RARE;
    	}

    	if(this == ModItems.fusion_core || 
    			this == ModItems.factory_core_titanium || 
    			this == ModItems.factory_core_advanced || 
    			this == ModItems.energy_core || 
    			this == ModItems.dynosphere_desh || 
    			this == ModItems.dynosphere_schrabidium || 
    			this == ModItems.dynosphere_euphemium || 
    			this == ModItems.dynosphere_dineutronium)
    			
    	{
        	return EnumRarity.UNCOMMON;
    	}
    	
    	return EnumRarity.COMMON;
    }
	
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() == ModItems.battery_creative)
			return;
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", stack.getTagCompound().getLong("charge") + i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());
    			stack.getTagCompound().setLong("charge", i);
    		}
    	}
    }
    
    public void setCharge(ItemStack stack, long i) {
    	if(stack.getItem() == ModItems.battery_creative)
			return;
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());;
    			stack.getTagCompound().setLong("charge", i);
    		}
    	}
    }
    
    public void dischargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() == ModItems.battery_creative)
			return;
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", stack.getTagCompound().getLong("charge") - i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());;
    			stack.getTagCompound().setLong("charge", this.maxCharge - i);
    		}
    	}
    }
    
    public static long getCharge(ItemStack stack) {
    	if(stack.getItem() == ModItems.battery_creative)
			return Long.MAX_VALUE;
    	if(stack.getItem() instanceof ItemBattery) {
    		if(stack.hasTagCompound()) {
    			return stack.getTagCompound().getLong("charge");
    		} else {
    			stack.setTagCompound(new NBTTagCompound());;
    			stack.getTagCompound().setLong("charge", ((ItemBattery)stack.getItem()).maxCharge);
    			return stack.getTagCompound().getLong("charge");
    		}
    	}
    	
    	return 0;
    }
    
    public long getMaxCharge() {
    	return maxCharge;
    }
    
    public long getChargeRate() {
    	return chargeRate;
    }
    
    public long getDischargeRate() {
    	return dischargeRate;
    }
    
    public static long getMaxChargeStatic(ItemStack stack) {
    	return ((ItemBattery)stack.getItem()).maxCharge;
    }
    
    public static ItemStack getEmptyBattery(Item item) {
    	
    	if(item instanceof ItemBattery) {
    		ItemStack stack = new ItemStack(item);
    		stack.setTagCompound(new NBTTagCompound());;
    		stack.getTagCompound().setLong("charge", 0);
    		stack.setItemDamage(100);
    		return stack.copy();
    	}
    	
    	return null;
    }
    
    public static ItemStack getFullBattery(Item item) {
    	
    	if(item instanceof ItemBattery) {
    		ItemStack stack = new ItemStack(item);
    		stack.setTagCompound(new NBTTagCompound());;
    		stack.getTagCompound().setLong("charge", getMaxChargeStatic(stack));
    		return stack.copy();
    	}
    	
    	return null;
    }
	
	public static void updateDamage(ItemStack stack) {
		if(stack.getItem() == ModItems.battery_creative)
			return;
		if(!stack.hasTagCompound()) {
			stack = getFullBattery(stack.getItem()).copy();
		}

		stack.setItemDamage(100 - (int)((double)getCharge(stack) / (double)getMaxChargeStatic(stack) * 100D));
	}
	
}
