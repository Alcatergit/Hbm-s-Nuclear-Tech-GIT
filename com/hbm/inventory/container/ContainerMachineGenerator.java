package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineGenerator extends Container {

	private TileEntityMachineGenerator diFurnace;
	
	private int heat;
	EntityPlayerMP player;
	
	public ContainerMachineGenerator(EntityPlayer player, TileEntityMachineGenerator tedf) {
		if(player instanceof EntityPlayerMP)
			this.player = (EntityPlayerMP) player;
		diFurnace = tedf;
		InventoryPlayer invPlayer = player.inventory;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 116, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 134, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 152, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 116, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 134, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 152, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 116, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 134, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 152, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 8, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 26, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 62, 90));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 12, 8, 90 + 18));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 13, 26, 90 + 18));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56));
		}
	}
	
	@Override
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heat, 0), player);
	}
	

	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 11) {
				if (!this.mergeItemStack(var5, 12, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 12, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (var5.isEmpty())
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return diFurnace.isUseableByPlayer(player);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		if(this.heat != this.diFurnace.heat)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heat, 0), player);
		}
		
		this.heat = this.diFurnace.heat;
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		if(i == 1)
		{
			diFurnace.heat = j;
		}
	}
}
