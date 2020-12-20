package com.hbm.inventory.container;

import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;

public class ContainerConverterRfHe extends Container {

	private TileEntityConverterRfHe diFurnace;

	private long power;
	private int flux;
	EntityPlayerMP player;

	public ContainerConverterRfHe(EntityPlayer invPlayer, TileEntityConverterRfHe tedf) {
		diFurnace = tedf;
		if(invPlayer instanceof EntityPlayerMP)
			player = (EntityPlayerMP) invPlayer;
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.storage.getEnergyStored(), 0), player);
		PacketDispatcher.sendTo(new AuxElectricityPacket(diFurnace.getPos(), diFurnace.power), player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		if(this.flux != this.diFurnace.storage.getEnergyStored()) {
			PacketDispatcher.sendTo(new AuxGaugePacket(diFurnace.getPos(), diFurnace.storage.getEnergyStored(), 0), player);
		}
		if(this.power != this.diFurnace.power) {
			PacketDispatcher.sendTo(new AuxElectricityPacket(diFurnace.getPos(), diFurnace.power), player);
		}

		this.flux = this.diFurnace.storage.getEnergyStored();
		this.power = this.diFurnace.power;
	}

}
