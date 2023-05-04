package com.hbm.items.machine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRBMKPellet extends Item implements IItemHazard {
	
	public String fullName = "";
	ItemHazardModule module;

	public ItemRBMKPellet(String fullName, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.fullName = fullName;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.controlTab);
		this.module = new ItemHazardModule();
		//generateJsons(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab()){
			for(int i = 0; i < 10; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(TextFormatting.ITALIC + this.fullName);
		tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Pellet for recycling");
		
		int meta = rectify(stack.getItemDamage());
		
		switch(meta % 5) {
		case 0: tooltip.add(TextFormatting.GOLD + "Brand New"); break;
		case 1: tooltip.add(TextFormatting.YELLOW + "Barely Depleted"); break;
		case 2: tooltip.add(TextFormatting.GREEN + "Moderately Depleted"); break;
		case 3: tooltip.add(TextFormatting.DARK_GREEN + "Highly Depleted"); break;
		case 4: tooltip.add(TextFormatting.DARK_GRAY + "Fully Depleted"); break;
		}
		
		if(hasXenon(meta))
			tooltip.add(TextFormatting.DARK_PURPLE + "High Xenon Poison");
		
		updateModule(stack);
		this.module.addInformation(stack, tooltip, flagIn);
	}
	
	private boolean hasXenon(int meta) {
		return rectify(meta) >= 5;
	}
	
	private int rectify(int meta) {
		return Math.abs(meta) % 10;
	}

	@Override
	public ItemHazardModule getModule() {
		return this.module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase) {
			updateModule(stack);
			this.module.applyEffects((EntityLivingBase) entity, stack.getCount(), i, b, ((EntityLivingBase)entity).getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item) {
		
		super.onEntityItemUpdate(item);
		updateModule(item.getItem());
		return this.module.onEntityItemUpdate(item);
	}
	
	private void updateModule(ItemStack stack) {
		
		int index = stack.getItemDamage() % 5;
		float mod = (index * index) / 5F;
		
		if(stack.getItemDamage() >= 5) {
			mod *= 10F;
			mod += 1F;
		}
		
		this.module.setMod(1F + mod);
	}
}