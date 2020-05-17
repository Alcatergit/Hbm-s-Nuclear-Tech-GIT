package com.hbm.inventory.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.gui.GUICoreEmitter;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCoreEmitter extends Container {

	private TileEntityCoreEmitter nukeBoy;
	private EntityPlayerMP player;
	
	public ContainerCoreEmitter(EntityPlayer player, TileEntityCoreEmitter tedf) {
		InventoryPlayer invPlayer = player.inventory;
		if(player instanceof EntityPlayerMP)
			this.player = (EntityPlayerMP) player;
		nukeBoy = tedf;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendWindowProperty(this, 0, (int) nukeBoy.power);
		listener.sendWindowProperty(this, 1, nukeBoy.watts);
		listener.sendWindowProperty(this, 2, (int) nukeBoy.prev);
		listener.sendWindowProperty(this, 3, nukeBoy.isOn ? 1 : 0);
	}
	
	int power;
	int watts;
	int prev;
	boolean isOn;
	FluidTank tank;
	
	@Override
	public void detectAndSendChanges() {
		for(IContainerListener l : listeners){
			if(nukeBoy.power != power){
				l.sendWindowProperty(this, 0, (int) nukeBoy.power);
				power = (int) nukeBoy.power;
			}
			if(nukeBoy.watts != watts){
				l.sendWindowProperty(this, 1, nukeBoy.watts);
				watts = nukeBoy.watts;
			}
			if(nukeBoy.prev != prev){
				l.sendWindowProperty(this, 2, (int) nukeBoy.prev);
				prev = (int) nukeBoy.prev;
			}
			if(nukeBoy.isOn != isOn){
				l.sendWindowProperty(this, 3, nukeBoy.isOn ? 1 : 0);
				isOn = nukeBoy.isOn;
			}
			if(!FFUtils.areTanksEqual(tank, nukeBoy.tank)){
				tank = FFUtils.copyTank(nukeBoy.tank);
				PacketDispatcher.wrapper.sendTo(new FluidTankPacket(nukeBoy.getPos(), new FluidTank[] { tank }), player);
			}
		}
		super.detectAndSendChanges();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		switch(id){
		case 0:
			nukeBoy.power = data;
			break;
		case 1:
			nukeBoy.watts = data;
			if(Minecraft.getMinecraft().currentScreen instanceof GUICoreEmitter){
				((GUICoreEmitter)Minecraft.getMinecraft().currentScreen).syncTextField(data);
			}
			break;
		case 2:
			nukeBoy.prev = data;
			break;
		case 3:
			nukeBoy.isOn = data > 0 ? true : false;
			break;
		}
		super.updateProgressBar(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return nukeBoy.isUseableByPlayer(player);
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			return ItemStack.EMPTY;
		}
		
		return var3;
    }
}