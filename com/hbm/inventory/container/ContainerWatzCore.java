package com.hbm.inventory.container;

import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerWatzCore extends Container {
	
	private TileEntityWatzCore diFurnace;

	private int powerList;
	private int heatList;
	private int decayMultiplier;
	private int powerMultiplier;
	private int heatMultiplier;
	private int heat;
	EntityPlayerMP player;
	
	public ContainerWatzCore(EntityPlayer player, TileEntityWatzCore tedf) {
		if(player instanceof EntityPlayerMP)
			this.player = (EntityPlayerMP) player;
		InventoryPlayer invPlayer = player.inventory;
		powerList = 0;
		heatList = 0;
		decayMultiplier = 0;
		powerMultiplier = 0;
		heatMultiplier = 0;
		heat = 0;
		
		diFurnace = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 1, 26, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 44, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 3, 62, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 98, 18));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 6, 8, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 7, 26, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 8, 44, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 9, 62, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 10, 80, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 11, 98, 36));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 12, 8, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 13, 26, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 14, 44, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 15, 62, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 16, 80, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 17, 98, 54));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 18, 8, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 19, 26, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 20, 44, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 21, 62, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 22, 80, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 23, 98, 72));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 24, 8, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 25, 26, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 26, 44, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 27, 62, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 28, 80, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 29, 98, 90));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 30, 8, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 31, 26, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 32, 44, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 33, 62, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 34, 80, 108));
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 35, 98, 108));
		//Mud Input
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 36, 134, 108 - 18));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 37, 152, 108 - 18));
		//Filter
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 38, 116, 63));
		//Mud Output
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 39, 134, 108));
		
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
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.powerList, 0), player);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heatList, 1), player);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.decayMultiplier, 2), player);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.powerMultiplier, 3), player);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heatMultiplier, 4), player);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heat, 5), player);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 39) {
				if (!this.mergeItemStack(var5, 40, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			} else {
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
		
		if(this.powerList != this.diFurnace.powerList)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.powerList, 0), player);
		}
		
		if(this.heatList != this.diFurnace.heatList)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heatList, 1), player);
		}
		
		if(this.decayMultiplier != this.diFurnace.decayMultiplier)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.decayMultiplier, 2), player);
		}
		
		if(this.powerMultiplier != this.diFurnace.powerMultiplier)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.powerMultiplier, 3), player);
		}
		
		if(this.heatMultiplier != this.diFurnace.heatMultiplier)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heatMultiplier, 4), player);
		}
		
		if(this.heat != this.diFurnace.heat)
		{
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.heat, 5), player);
		}

		this.powerList = this.diFurnace.powerList;
		this.heatList = this.diFurnace.heatList;
		this.decayMultiplier = this.diFurnace.decayMultiplier;
		this.powerMultiplier = this.diFurnace.powerMultiplier;
		this.heatMultiplier = this.diFurnace.heatMultiplier;
		this.heat = this.diFurnace.heat;
	}
	
}