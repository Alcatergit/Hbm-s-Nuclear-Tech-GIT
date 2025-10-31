package com.leafia.contents.machines.reactors.pwr.container;

import com.hbm.items.ModItems;
import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.dev.container_utility.LeafiaItemTransferable;
import com.llib.group.LeafiaSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PWRTerminalContainer extends LeafiaItemTransferable {
	static class ResourceSlot extends SlotItemHandler {
		public ResourceSlot(IItemHandler itemHandler,int index,int xPosition,int yPosition) {
			super(itemHandler,index,xPosition,yPosition);
		}
	}
	static class RemoteSlot extends SlotItemHandler {
		BlockPos localPos;
		public RemoteSlot(IItemHandler itemHandler,int index,BlockPos pos) {
			super(itemHandler,index,0,0);
			this.localPos = pos;
		}
	}
	final PWRData core;
	final ItemStackHandler remote;
	final PWRComponentEntity entity;
	final IBlockState terminalState;
	final BlockPos terminalPos;
	final LeafiaSet<BlockPos> fuels;
	int resourceSlots = 0;
	public PWRTerminalContainer(InventoryPlayer playerInventory,TileEntity terminal,PWRData core) {
		this.core = core;
		this.remote = core.remoteContainer;
		this.entity = (PWRComponentEntity)core.companion;
		terminalPos = terminal.getPos();
		terminalState = terminal.getWorld().getBlockState(terminalPos);
		fuels = core.getProjectionFuelAndControlPositions().getA();

		// player slots
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 53 + j * 18, 36 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 53 + i * 18, 94));
		}
		// pwr slots
		this.addSlotToContainer(new ResourceSlot(core.resourceContainer,resourceSlots++,5,43));
		this.addSlotToContainer(new ResourceSlot(core.resourceContainer,resourceSlots++,-22,79));
		this.addSlotToContainer(new ResourceSlot(core.resourceContainer,resourceSlots++,-22,97));
		int slotsAdded = 0;
		for (BlockPos fuel : fuels) {
			TileEntity tileEntity = core.getWorld().getTileEntity(fuel);
			if (tileEntity instanceof PWRElementTE) {
				PWRElementTE fuelEntity = (PWRElementTE)tileEntity;
				BlockPos pos = core.terminal_toLocal(terminalState,terminalPos,fuel);
				this.addSlotToContainer(new RemoteSlot(fuelEntity.inventory,0,pos));
				slotsAdded++;
			}
		}
		World world = terminal.getWorld();
		EntityPlayer player = playerInventory.player;
		if (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d)
			player.sendMessage(new TextComponentString((world.isRemote ? "REMOTE: " : "SERVER: ")+"Elements "+fuels.size()+", added slots "+slotsAdded));
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player,int index) {
		LeafiaItemTransfer transfer = new LeafiaItemTransfer(this.inventorySlots.size())._selected(index);
		return transfer.__forSlots(0,9*4-1)
				.__tryMoveToSlot(9*4+resourceSlots,this.inventorySlots.size()-1,false)

				.__forSlots(9*4,this.inventorySlots.size()-1)
				.__tryMoveToSlot(0,9*4-1,true)

				.__getReturn();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !(core.remoteContainer != remote || ((TileEntity)entity).isInvalid() || entity.getCore() != core);
	}
}
