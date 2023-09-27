package com.hbm.items.weapon;

import java.util.List;

import com.hbm.handler.MissileStruct;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.WarheadType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemCustomMissile extends Item {

	public ItemCustomMissile(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	public static ItemStack buildMissile(Item chip, Item warhead, Item fuselage, Item stability, Item thruster) {

		if(stability == null) {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), null, new ItemStack(thruster));
		} else {
			return buildMissile(new ItemStack(chip), new ItemStack(warhead), new ItemStack(fuselage), new ItemStack(stability), new ItemStack(thruster));
		}
	}

	public static ItemStack buildMissile(ItemStack chip, ItemStack warhead, ItemStack fuselage, ItemStack stability, ItemStack thruster) {

		ItemStack missile = new ItemStack(ModItems.missile_custom);

		writeToNBT(missile, "chip", Item.getIdFromItem(chip.getItem()));
		writeToNBT(missile, "warhead", Item.getIdFromItem(warhead.getItem()));
		writeToNBT(missile, "fuselage", Item.getIdFromItem(fuselage.getItem()));
		writeToNBT(missile, "thruster", Item.getIdFromItem(thruster.getItem()));

		if(stability != null)
			writeToNBT(missile, "stability", Item.getIdFromItem(stability.getItem()));

		return missile;
	}

	private static void writeToNBT(ItemStack stack, String key, int value) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger(key, value);
	}

	public static int readFromNBT(ItemStack stack, String key) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound().getInteger(key);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		try {
			ItemMissile chip = (ItemMissile) Item.getItemById(readFromNBT(stack, "chip"));
			ItemMissile warhead = (ItemMissile) Item.getItemById(readFromNBT(stack, "warhead"));
			ItemMissile fuselage = (ItemMissile) Item.getItemById(readFromNBT(stack, "fuselage"));
			ItemMissile stability = null;
			Item item = Item.getItemById(readFromNBT(stack, "stability"));
			if(item instanceof ItemMissile)
				stability = (ItemMissile) item;
			ItemMissile thruster = (ItemMissile) Item.getItemById(readFromNBT(stack, "thruster"));

			tooltip.add(TextFormatting.BOLD + "Warhead: " + TextFormatting.GRAY + warhead.getWarhead((WarheadType) warhead.attributes[0]));
			tooltip.add(TextFormatting.BOLD + "Strength: " + TextFormatting.RED + (Float) warhead.attributes[1]);
			tooltip.add(TextFormatting.BOLD + "Fuel Type: " + TextFormatting.GRAY + fuselage.getFuel((FuelType) fuselage.attributes[0]));
			tooltip.add(TextFormatting.BOLD + "Fuel amount: " + TextFormatting.GRAY + (Float) fuselage.attributes[1] + "l");
			tooltip.add(TextFormatting.BOLD + "Chip inaccuracy: " + TextFormatting.GRAY + (Float) chip.attributes[0] * 100 + "%");

			if(stability != null)
				tooltip.add(TextFormatting.BOLD + "Fin inaccuracy: " + TextFormatting.GRAY + (Float) stability.attributes[0] * 100 + "%");
			else
				tooltip.add(TextFormatting.BOLD + "Fin inaccuracy: " + TextFormatting.GRAY + "100%");

			tooltip.add(TextFormatting.BOLD + "Size: " + TextFormatting.GRAY + fuselage.getSize(fuselage.top) + "/" + fuselage.getSize(fuselage.bottom));

			float health = warhead.health + fuselage.health + thruster.health;
			if(stability != null)
				health += stability.health;

			tooltip.add(TextFormatting.BOLD + "Health: " + TextFormatting.GREEN + health + "HP");
		} catch(ClassCastException x) {
			//Drillgon200: Why is this even necessary, JEI?
			return;
		}
	}

	public static MissileStruct getStruct(ItemStack stack) {

		if(stack == null || stack.isEmpty() || stack.getTagCompound() == null || !(stack.getItem() instanceof ItemCustomMissile))
			return null;
		// This is a stupid fix
		try {
			ItemMissile warhead = (ItemMissile) Item.getItemById(readFromNBT(stack, "warhead"));
			ItemMissile fuselage = (ItemMissile) Item.getItemById(readFromNBT(stack, "fuselage"));
			ItemMissile stability = null;
			Item item = Item.getItemById(readFromNBT(stack, "stability"));
			if(item instanceof ItemMissile)
				stability = (ItemMissile) item;
			ItemMissile thruster = (ItemMissile) Item.getItemById(readFromNBT(stack, "thruster"));

			MissileStruct missile = new MissileStruct(warhead, fuselage, stability, thruster);

			return missile;
		} catch(ClassCastException x) {
			return null;
		}

	}
}
