package com.hbm.inventory.container;

import com.hbm.inventory.gui.GUICoreStabilizer;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCoreStabilizer extends Container {

	private TileEntityCoreStabilizer nukeBoy;
	private EntityPlayerMP player;
	
	public ContainerCoreStabilizer(EntityPlayer player, TileEntityCoreStabilizer tedf) {
		InventoryPlayer invPlayer = player.inventory;
		if(player instanceof EntityPlayerMP)
			this.player = (EntityPlayerMP) player;
		nukeBoy = tedf;
		
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 47, 26));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 88 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 146));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, nukeBoy.watts);
	}
	
	@Override
	public void detectAndSendChanges() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", nukeBoy.power);
		data.setInteger("watts", nukeBoy.watts);
		data.setBoolean("isOn", nukeBoy.isOn);
		PacketDispatcher.sendTo(new NBTPacket(data, nukeBoy.getPos()), player);
		super.detectAndSendChanges();
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		if(id == 0){
			if(Minecraft.getMinecraft().currentScreen instanceof GUICoreStabilizer){
				((GUICoreStabilizer)Minecraft.getMinecraft().currentScreen).syncTextField(data);
			}
		}
		super.updateProgressBar(id, data);
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
			
            if (par2 == 0) {
				if (!this.mergeItemStack(var5, 1, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.mergeItemStack(var5, 0, 1, true)) {
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
		return nukeBoy.isUseableByPlayer(player);
	}
}
