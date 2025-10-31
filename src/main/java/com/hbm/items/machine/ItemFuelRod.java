package com.hbm.items.machine;

import com.hbm.items.ModItems.Materials.Billets;
import com.hbm.items.ModItems.RetroRods;
import com.hbm.items.special.ItemHazard;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import com.leafia.dev.MultiRad.RadiationType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class ItemFuelRod extends ItemHazard {
	
	private int lifeTime;
	private int heat;
	private float irad;
	private boolean iblind;

	public ItemFuelRod(float radiation, boolean blinding, int life, int heat, String s) {
		super(RadiationType.NEUTRONS, radiation, false, blinding, s);
		this.irad = radiation;
		this.iblind = blinding;
		this.lifeTime = life;
		this.heat = heat;
		this.setMaxDamage(100);
		this.canRepair = false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		//tooltip.add(TextFormatting.GREEN + "["+ I18nUtil.resolveKey("trait._hazarditem.radioactive") +"]");
		//tooltip.add(TextFormatting.YELLOW + "" + this.irad + " "+I18nUtil.resolveKey("desc.rads"));
		//if(this.iblind){
		//	tooltip.add(TextFormatting.DARK_AQUA + "["+I18nUtil.resolveKey("trait.blinding")+"]");
		//}
		getModule().addInformation(stack,tooltip,flagIn);
		tooltip.add(TextFormatting.GOLD + "["+I18nUtil.resolveKey("trait.reactorrod")+"]");
		
		tooltip.add(TextFormatting.DARK_AQUA + "  "+I18nUtil.resolveKey("desc.generates")+" " + heat + " "+I18nUtil.resolveKey("desc.heatpt"));
		tooltip.add(TextFormatting.DARK_AQUA + "  "+I18nUtil.resolveKey("desc.lasts")+" " + Library.getShortNumber(lifeTime) + " "+I18nUtil.resolveKey("desc.ticks"));
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return super.initCapabilities(stack, nbt);
	}
	
	public static void setLifetime(ItemStack stack, int time){
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("life", time);
	}
	
	public static int getLifeTime(ItemStack stack){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
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
    	if(this == RetroRods.rod_uranium_fuel) return new ItemStack(Billets.billet_uranium_fuel);
    	if(this == RetroRods.rod_dual_uranium_fuel) return new ItemStack(Billets.billet_uranium_fuel, 2);
    	if(this == RetroRods.rod_quad_uranium_fuel) return new ItemStack(Billets.billet_uranium_fuel, 4);
    	
    	if(this == RetroRods.rod_thorium_fuel) return new ItemStack(Billets.billet_thorium_fuel);
    	if(this == RetroRods.rod_dual_thorium_fuel) return new ItemStack(Billets.billet_thorium_fuel, 2);
    	if(this == RetroRods.rod_quad_thorium_fuel) return new ItemStack(Billets.billet_thorium_fuel, 4);
    	
    	if(this == RetroRods.rod_plutonium_fuel) return new ItemStack(Billets.billet_plutonium_fuel);
    	if(this == RetroRods.rod_dual_plutonium_fuel) return new ItemStack(Billets.billet_plutonium_fuel, 2);
    	if(this == RetroRods.rod_quad_plutonium_fuel) return new ItemStack(Billets.billet_plutonium_fuel, 4);
    	
    	if(this == RetroRods.rod_mox_fuel) return new ItemStack(Billets.billet_mox_fuel);
    	if(this == RetroRods.rod_dual_mox_fuel) return new ItemStack(Billets.billet_mox_fuel, 2);
    	if(this == RetroRods.rod_quad_mox_fuel) return new ItemStack(Billets.billet_mox_fuel, 4);
    	
    	if(this == RetroRods.rod_schrabidium_fuel) return new ItemStack(Billets.billet_schrabidium_fuel);
    	if(this == RetroRods.rod_dual_schrabidium_fuel) return new ItemStack(Billets.billet_schrabidium_fuel, 2);
    	if(this == RetroRods.rod_quad_schrabidium_fuel) return new ItemStack(Billets.billet_schrabidium_fuel, 4);
    	return ItemStack.EMPTY;
    }
}
