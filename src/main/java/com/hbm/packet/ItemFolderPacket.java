package com.hbm.packet;

import java.io.IOException;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.Library;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemFolderPacket implements IMessage {

	ItemStack stack;
	PacketBuffer buffer;

	public ItemFolderPacket() {

	}

	public ItemFolderPacket(ItemStack stack) {
		buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeCompoundTag(stack.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
		try {
			stack = new ItemStack(buffer.readCompoundTag());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);
	}

	public static class Handler implements IMessageHandler<ItemFolderPacket, IMessage> {

		@Override
		public IMessage onMessage(ItemFolderPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().player;
			if(m.stack == null)
				return null;
			p.getServer().addScheduledTask(() -> {
				
				if(p.getHeldItemMainhand().getItem() != ModItems.template_folder && p.getHeldItemOffhand().getItem() != ModItems.template_folder)
					return;
				
				ItemStack stack = m.stack;
				
				if(p.capabilities.isCreativeMode) {
					
					p.inventory.addItemStackToInventory(stack.copy());
					return;
				}

				if(stack.getItem() instanceof ItemForgeFluidIdentifier) {
					if(Library.hasInventoryOreDict(p.inventory, "plateIron") && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, ModItems.plate_iron);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() instanceof ItemAssemblyTemplate) {
					if(Library.hasInventoryItem(p.inventory, Items.PAPER) && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, Items.PAPER);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() instanceof ItemChemistryTemplate) {
					if(Library.hasInventoryItem(p.inventory, Items.PAPER) && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, Items.PAPER);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() instanceof ItemCassette) {
					if(Library.hasInventoryItem(p.inventory, ModItems.plate_polymer) && Library.hasInventoryOreDict(p.inventory, "plateSteel")) {
						Library.consumeInventoryItem(p.inventory, ModItems.plate_polymer);
						Library.consumeInventoryItem(p.inventory, ModItems.plate_steel);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_stone_plate || stack.getItem() == ModItems.stamp_stone_wire || stack.getItem() == ModItems.stamp_stone_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_stone_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_stone_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_iron_plate || stack.getItem() == ModItems.stamp_iron_wire || stack.getItem() == ModItems.stamp_iron_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_iron_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_iron_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_steel_plate || stack.getItem() == ModItems.stamp_steel_wire || stack.getItem() == ModItems.stamp_steel_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_steel_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_steel_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_titanium_plate || stack.getItem() == ModItems.stamp_titanium_wire || stack.getItem() == ModItems.stamp_titanium_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_titanium_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_titanium_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_obsidian_plate || stack.getItem() == ModItems.stamp_obsidian_wire || stack.getItem() == ModItems.stamp_obsidian_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_obsidian_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_obsidian_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_schrabidium_plate || stack.getItem() == ModItems.stamp_schrabidium_wire || stack.getItem() == ModItems.stamp_schrabidium_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_schrabidium_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_schrabidium_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
				if(stack.getItem() == ModItems.stamp_desh_plate || stack.getItem() == ModItems.stamp_desh_wire || stack.getItem() == ModItems.stamp_desh_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_desh_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_desh_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
					}
				}
			});

			
			return null;
		}
	}
}