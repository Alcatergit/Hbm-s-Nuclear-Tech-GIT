package com.hbm.items.machine;

import java.util.List;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import net.minecraft.client.util.ITooltipFlag;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemAutogen;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemScraps extends ItemAutogen {

	public ItemScraps(String s) {
		super(s, null);
	}

	@Override
	public boolean isMaterialValid(NTMMaterial mat){
		return mat.smeltable == SmeltingBehavior.SMELTABLE || mat.smeltable == SmeltingBehavior.ADDITIVE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {
		MaterialStack contents = getMats(stack);
		
		if(contents != null) {
			if(contents.material.smeltable == SmeltingBehavior.ADDITIVE)
				list.add("§4Additive, not castable!");
		}
	}
	
	public static MaterialStack getMats(ItemStack stack) {
		
		if(stack.getItem() != ModItems.scraps) return null;
		
		NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
		if(mat == null) return null;
		
		int amount = MaterialShapes.INGOT.q(1);
		
		if(stack.hasTagCompound()) {
			amount = stack.getTagCompound().getInteger("amount");
		}
		
		return new MaterialStack(mat, amount);
	}
	
	public static ItemStack create(MaterialStack stack) {
		return create(stack, false);
	}
	
	public static ItemStack create(MaterialStack stack, boolean liquid) {
		if(stack.material == null)
			return new ItemStack(ModItems.nothing); //why do i bother adding checks for fucking everything when they don't work
		ItemStack scrap = new ItemStack(ModItems.scraps, 1, stack.material.id);
		NBTTagCompound scrapNbt = new NBTTagCompound();
		scrapNbt.setInteger("amount", stack.amount);
		if(liquid) scrapNbt.setBoolean("liquid", true);
		scrap.setTagCompound(scrapNbt);
		return scrap;
	}
}
