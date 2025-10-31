package com.leafia.contents.machines.reactors.pwr.debris;

import com.hbm.util.ContaminationUtil;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PWRDebrisCrafting extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean matches(InventoryCrafting inv,World worldIn) {
		if (inv.getSizeInventory() == 9) {
			ItemStack oneStack = null;
			for (int i = 0; i < 9; i++) {
				ItemStack stack = inv.getStackInSlot(i);
				if (oneStack == null) oneStack = stack;
				if (!ItemStack.areItemStacksEqual(oneStack,stack))
					return false;
			}
			if (oneStack.isEmpty()) return false;
			if (oneStack.getItem() instanceof PWRDebrisItem) {
				return (((PWRDebrisItem) oneStack.getItem()).canBeCraftedBack) && oneStack.getTagCompound() != null;
			}
		}
		return false;
	}
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stk = inv.getStackInSlot(0);
		if (!stk.isEmpty()) {
			NBTTagCompound nbt = stk.getTagCompound();
			if (nbt != null && stk.getItem() instanceof PWRDebrisItem) {
				PWRDebrisItem debrisItem = (PWRDebrisItem)stk.getItem();
				Block block = Block.getBlockFromName(nbt.getString("block"));
				if (block != null) {
					ItemStack stack = new ItemStack(block);
					if (!ContaminationUtil.isRadItem(stack)) {
						NBTTagCompound compound = stack.getTagCompound();
						if (compound == null)
							compound = new NBTTagCompound();
						compound.setFloat(ContaminationUtil.NTM_NEUTRON_NBT_KEY,debrisItem.hazard.radiation.total()*3);
						stack.setTagCompound(compound);
					}
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}
	@Override
	public boolean isDynamic() {
		return true;
	}
	@Override
	public boolean canFit(int width,int height) {
		return width == 3 && height == 3;
	}
	@Override
	public ItemStack getRecipeOutput() { return ItemStack.EMPTY; }
}