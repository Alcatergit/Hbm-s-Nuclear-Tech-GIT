package com.hbm.inventory.container;

import com.hbm.packet.AuxLongPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ContainerMachineTeleporter extends Container {
	
	private TileEntityMachineTeleporter diFurnace;
	
	BlockPos detectTarget = null;
	EntityPlayerMP player;
	
	public ContainerMachineTeleporter(EntityPlayer player, TileEntityMachineTeleporter tedf) {
		if(player instanceof EntityPlayerMP)
			this.player = (EntityPlayerMP) player;
		diFurnace = tedf;
	}
	
	@Override
	public void addListener(IContainerListener crafting) {
		super.addListener(crafting);
		if(diFurnace.target != null){
			PacketDispatcher.sendTo(new AuxLongPacket(diFurnace.getPos(), diFurnace.target.toLong(), 0), player);
		} else {
			PacketDispatcher.sendTo(new AuxLongPacket(diFurnace.getPos(), 0, 0), player);
		}
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
		if(this.detectTarget != this.diFurnace.target)
		{
			if(diFurnace.target != null){
				PacketDispatcher.sendTo(new AuxLongPacket(diFurnace.getPos(), diFurnace.target.toLong(), 0), player);
			} else {
				PacketDispatcher.sendTo(new AuxLongPacket(diFurnace.getPos(), 0, 0), player);
			}
		}
		this.detectTarget = this.diFurnace.target;
	}
}
