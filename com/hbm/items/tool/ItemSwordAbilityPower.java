package com.hbm.items.tool;

import java.util.List;

import com.hbm.lib.Library;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSwordAbilityPower extends ItemSwordAbility {

	public long maxPower = 1;
	public long chargeRate;
	public long consumption;
	
	public ItemSwordAbilityPower(float damage, double movement, ToolMaterial material, long maxPower, long chargeRate, long consumption, String s) {
		super(damage, movement, material, s);
		this.maxPower = maxPower;
		this.chargeRate = chargeRate;
		this.consumption = consumption;
		this.setMaxDamage(1);
	}
	
	public void chargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", stack.getTagCompound().getLong("charge") + i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());
    			stack.getTagCompound().setLong("charge", i);
    		}
    	}
    }

    public void setCharge(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());
    			stack.getTagCompound().setLong("charge", i);
    		}
    	}
    }

    public void dischargeBattery(ItemStack stack, long i) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			stack.getTagCompound().setLong("charge", stack.getTagCompound().getLong("charge") - i);
    		} else {
    			stack.setTagCompound(new NBTTagCompound());
    			stack.getTagCompound().setLong("charge", this.maxPower - i);
    		}

    		if(stack.getTagCompound().getLong("charge") < 0)
    			stack.getTagCompound().setLong("charge", 0);
    	}
    }

    public static long getCharge(ItemStack stack) {
    	if(stack.getItem() instanceof ItemSwordAbilityPower) {
    		if(stack.hasTagCompound()) {
    			return stack.getTagCompound().getLong("charge");
    		} else {
    			stack.setTagCompound(new NBTTagCompound());
    			stack.getTagCompound().setLong("charge", ((ItemSwordAbilityPower)stack.getItem()).maxPower);
    			return stack.getTagCompound().getLong("charge");
    		}
    	}

    	return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
    	list.add("Charge: " + Library.getShortNumber(getCharge(stack)) + " / " + Library.getShortNumber(maxPower));
    	super.addInformation(stack, worldIn, list, flagIn);
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack) {
    	return getCharge(stack) < maxPower;
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
    	return 1 - (double)getCharge(stack) / (double)maxPower;
    }
    
    @Override
    protected boolean canOperate(ItemStack stack) {
    	return getCharge(stack) >= this.consumption;
    }
    
    public long getMaxCharge() {
    	return maxPower;
    }

    public long getChargeRate() {
    	return chargeRate;
    }

    public static long getMaxChargeStatic(ItemStack stack) {
    	return ((ItemSwordAbilityPower)stack.getItem()).maxPower;
    }
    
    @Override
    public void setDamage(ItemStack stack, int damage) {
    	this.dischargeBattery(stack, damage * consumption);
    }
    
    @Override
    public boolean isDamageable() {
    	return true;
    }
}
