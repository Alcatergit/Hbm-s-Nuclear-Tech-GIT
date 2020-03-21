package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ContainerMachineTeleporter extends Container {
	
	private TileEntityMachineTeleporter diFurnace;
	
	BlockPos detectTarget = null;
	
	private int[] buffer = {0, 0, 0};
	
	public ContainerMachineTeleporter(InventoryPlayer invPlayer, TileEntityMachineTeleporter tedf) {
		
		diFurnace = tedf;
	}
	
	@Override
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		if(diFurnace.target != null){
			crafting.sendWindowProperty(this, 1, this.diFurnace.target.getX());
			crafting.sendWindowProperty(this, 2, this.diFurnace.target.getY());
			crafting.sendWindowProperty(this, 3, this.diFurnace.target.getZ());
		} else {
			crafting.sendWindowProperty(this, 1, 0);
			crafting.sendWindowProperty(this, 2, 0);
			crafting.sendWindowProperty(this, 3, 0);
		}
		crafting.sendWindowProperty(this, 4, 0);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return null;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++)
		{
			IContainerListener par1 = (IContainerListener)this.listeners.get(i);
			if(this.detectTarget != this.diFurnace.target)
			{
				if(diFurnace.target != null){
					par1.sendWindowProperty(this, 1, this.diFurnace.target.getX());
					par1.sendWindowProperty(this, 2, this.diFurnace.target.getY());
					par1.sendWindowProperty(this, 3, this.diFurnace.target.getZ());
				} else {
					par1.sendWindowProperty(this, 1, 0);
					par1.sendWindowProperty(this, 2, 0);
					par1.sendWindowProperty(this, 3, 0);
				}
				par1.sendWindowProperty(this, 4, 0);
			}
		}
		this.detectTarget = this.diFurnace.target;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			buffer[0] = j;
		}
		if(i == 2)
		{
			buffer[1] = j;
		}
		if(i == 3)
		{
			buffer[2] = j;
		}
		if(i == 4){
			if(buffer[0] == 0 && buffer[1] == 0 && buffer[2] == 0){
				diFurnace.target = null;
			} else {
				diFurnace.target = new BlockPos(buffer[0], buffer[1], buffer[2]);
			}
		}
	}
}
