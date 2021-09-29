package com.hbm.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

//'t was about time
public class InventoryUtil {

	/**
	 * Will attempt to cram a much of the given itemstack into the stack array as possible
	 * The rest will be returned
	 * @param inv the stack array, usually a TE's inventory
	 * @param start the starting index (inclusive)
	 * @param end the end index (inclusive)
	 * @param stack the stack to be added to the inventory
	 * @return the remainder of the stack that could not have been added, can return null
	 */
	public static ItemStack tryAddItemToInventory(ItemStack[] inv, int start, int end, ItemStack stack) {

		ItemStack rem = tryAddItemToExistingStack(inv, start, end, stack);

		if(rem == null)
			return ItemStack.EMPTY;

		boolean didAdd = tryAddItemToNewSlot(inv, start, end, rem);

		if(didAdd)
			return ItemStack.EMPTY;
		else
			return rem;
	}

	/**
	 * Functionally equal to tryAddItemToInventory, but will not try to create new stacks in empty slots
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return
	 */
	public static ItemStack tryAddItemToExistingStack(ItemStack[] inv, int start, int end, ItemStack stack) {

		if(stack == null || stack.isEmpty())
			return ItemStack.EMPTY;

		for(int i = start; i <= end; i++) {

			if(doesStackDataMatch(inv[i], stack)) {

				int transfer = Math.min(stack.getCount(), inv[i].getMaxStackSize() - inv[i].getCount());

				if(transfer > 0) {
					inv[i].setCount(inv[i].getCount() + transfer);
					stack.setCount(stack.getCount() - transfer);

					if(stack.isEmpty())
						return ItemStack.EMPTY;
				}
			}
		}

		return stack;
	}

	/**
	 * Will place the stack in the first empty slot
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return whether the stack could be added or not
	 */
	public static boolean tryAddItemToNewSlot(ItemStack[] inv, int start, int end, ItemStack stack) {

		if(stack == null || stack.isEmpty())
			return true;

		for(int i = start; i <= end; i++) {

			if(inv[i] == null) {
				inv[i] = stack;
				return true;
			}
		}

		return false;
	}

	/**
	 * Much of the same but with an ISidedInventory instance instead of a slot array
	 * @param inv
	 * @param start
	 * @param end
	 * @param stack
	 * @return
	 */
	public static ItemStack tryAddItemToInventory(IItemHandlerModifiable inv, int start, int end, ItemStack stack) {

		ItemStack rem = tryAddItemToExistingStack(inv, start, end, stack);

		if(rem.isEmpty())
			return ItemStack.EMPTY;

		boolean didAdd = tryAddItemToNewSlot(inv, start, end, rem);

		if(didAdd)
			return ItemStack.EMPTY;
		else
			return rem;
	}

	public static ItemStack tryAddItemToExistingStack(IItemHandlerModifiable inv, int start, int end, ItemStack stack) {

		if(stack == null || stack.isEmpty())
			return ItemStack.EMPTY;

		for(int i = start; i <= end; i++) {

			if(doesStackDataMatch(inv.getStackInSlot(i), stack)) {

				int transfer = Math.min(stack.getCount(), inv.getStackInSlot(i).getMaxStackSize() - inv.getStackInSlot(i).getCount());

				if(transfer > 0) {
					inv.getStackInSlot(i).setCount(inv.getStackInSlot(i).getCount() + transfer);
					stack.setCount(stack.getCount() - transfer);

					if(stack.isEmpty())
						return ItemStack.EMPTY;
				}
			}
		}

		return stack;
	}

	public static boolean tryAddItemToNewSlot(IItemHandlerModifiable inv, int start, int end, ItemStack stack) {

		if(stack == null || stack.isEmpty())
			return true;

		for(int i = start; i <= end; i++) {

			if(inv.getStackInSlot(i).isEmpty()) {
				inv.setStackInSlot(i, stack);
				return true;
			}
		}

		return false;
	}

	/**
	 * Compares item, metadata and NBT data of two stacks. Also handles null values!
	 * @param stack1
	 * @param stack2
	 * @return
	 */
	public static boolean doesStackDataMatch(ItemStack stack1, ItemStack stack2) {

		if(stack1 == null && stack2 == null)
			return true;

		if(stack1 == null && stack2 != null)
			return false;

		if(stack1 != null && stack2 == null)
			return false;

		if(stack1.getItem() != stack2.getItem())
			return false;

		if(stack1.getItemDamage() != stack2.getItemDamage())
			return false;

		if(!stack1.hasTagCompound() && !stack2.hasTagCompound())
			return true;

		if(stack1.hasTagCompound() && !stack2.hasTagCompound())
			return false;

		if(!stack1.hasTagCompound() && stack2.hasTagCompound())
			return false;

		return stack1.getTagCompound().equals(stack2.getTagCompound());
	}
}