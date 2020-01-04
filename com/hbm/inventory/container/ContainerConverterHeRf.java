package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityConverterHeRf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;

public class ContainerConverterHeRf extends Container {

private TileEntityConverterHeRf diFurnace;
	
	private long power;
	private int flux;
	
	public ContainerConverterHeRf(InventoryPlayer invPlayer, TileEntityConverterHeRf tedf) {
		
		diFurnace = tedf;
	}
	
	@Override
		public void addListener(IContainerListener listener) {
			super.addListener(listener);
			listener.sendWindowProperty(this, 1, this.diFurnace.storage.getEnergyStored());
			listener.sendWindowProperty(this, 0, Math.toIntExact(this.diFurnace.power));
		}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
		return ItemStack.EMPTY;
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
			IContainerListener par1 = this.listeners.get(i);
			
			if(this.flux != this.diFurnace.storage.getEnergyStored())
			{
				par1.sendWindowProperty(this, 1, this.diFurnace.storage.getEnergyStored());
			}
			if(this.power != diFurnace.power){
				par1.sendWindowProperty(this, 0, Math.toIntExact(this.diFurnace.power));
			}
		}
		
		this.flux = this.diFurnace.storage.getEnergyStored();
		this.power = diFurnace.power;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 0){
			diFurnace.power = j;
		}
		if(i == 1)
		{
			diFurnace.storage.setEnergyStored(j);
		}
	}
}
