package com.hbm.tileentity.machine.rbmk;

import net.minecraft.item.ItemStack;

public interface IRBMKLoadable {

	/**
	 * @param toLoad the ItemStack that should be loaded
	 * @return TRUE if the provided ItemStack can be inserted into the column
	 */
    boolean canLoad(ItemStack toLoad);
	
	/**
	 * Loads the given ItemStack, canLoad check necessary first
	 * @param toLoad
	 */
    void load(ItemStack toLoad);
	
	/**
	 * @return TRUE if the column contains an ItemStack that can be unloaded
	 */
    boolean canUnload();
	
	/**
	 * @return The next ItemStack to be unloaded
	 */
    ItemStack provideNext();
	
	/**
	 * Removes the next ItemStack as part of the unloading process
	 */
    void unload();
}
