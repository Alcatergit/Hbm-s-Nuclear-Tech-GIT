package com.hbm.items.armor;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.render.model.ModelJetPack;
import com.hbm.util.I18nUtil;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackBase extends ItemArmor {

	private ModelJetPack model;
	public Fluid fuel;
	public int maxFuel;
	
	public JetpackBase(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, Fluid fuel, int maxFuel, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.fuel = fuel;
		this.maxFuel = maxFuel;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey(fuel.getUnlocalizedName()) + ": " + getFuel(stack) + "mB / " + this.maxFuel + "mB");
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return armorType == EntityEquipmentSlot.CHEST;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (armorSlot == EntityEquipmentSlot.CHEST) {
			if (model == null) {
				this.model = new ModelJetPack();
			}
			return this.model;
		}

		return null;
	}
	
	protected void useUpFuel(EntityPlayer player, ItemStack stack, int rate) {

		if(player.ticksExisted % rate == 0)
			setFuel(stack, getFuel(stack) - 1);
	}

    public static int getFuel(ItemStack stack) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
			return 0;
		}

		return stack.getTagCompound().getInteger("fuel");

	}

	public static void setFuel(ItemStack stack, int i) {
		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.getTagCompound().setInteger("fuel", i);

	}

}
