package com.hbm.packet;

import com.hbm.inventory.gui.GUIScreenTemplateFolder;
import com.hbm.items.ModItems;
import com.hbm.items.machine.*;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Consumer;

public class ItemFolderPacket extends RecordablePacket {

	ItemStack stack;
	//PacketBuffer buffer; Have you ever heard of ByteBufUtils

	public ItemFolderPacket() {

	}

	public ItemFolderPacket(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		/*
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buffer.writeBytes(buf);
		try {
			stack = new ItemStack(buffer.readCompoundTag());
		} catch(IOException e) {
			e.printStackTrace();
		}*/
		stack = buf.readItemStack();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		/*
		if (buffer == null) {
			buffer = new PacketBuffer(Unpooled.buffer());
		}
		buf.writeBytes(buffer);*/
		buf.writeItemStack(stack);
	}

	public static class FolderResponsePacket implements LeafiaCustomPacketEncoder {
		boolean serverSuccess = false;
		public FolderResponsePacket() {}
		public FolderResponsePacket(boolean success) {
			serverSuccess = success;
		}
		@Override
		public void encode(LeafiaBuf buf) {
			buf.writeBoolean(serverSuccess);
		}
		@Override
		@SideOnly(Side.CLIENT)
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			boolean success = buf.readBoolean();
			return (ctx)->{
				if (success) {
					Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
					Minecraft.getMinecraft().player.closeScreen();
				} else
					Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundEvents.UI_BUTTON_INVALID, 1.0F));
				GUIScreenTemplateFolder.cooldown = false;
			};
		}
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
					LeafiaCustomPacket.__start(new FolderResponsePacket(true)).__sendToClient(p);
					return;
				}
				boolean success = false;
				if(stack.getItem() instanceof ItemForgeFluidIdentifier) {
					if(Library.hasInventoryOreDict(p.inventory, "plateIron") && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, ModItems.plate_iron);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() instanceof ItemAssemblyTemplate) {
					if(Library.hasInventoryItem(p.inventory, Items.PAPER) && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, Items.PAPER);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() instanceof ItemChemistryTemplate) {
					if(Library.hasInventoryItem(p.inventory, Items.PAPER) && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, Items.PAPER);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() instanceof ItemCrucibleTemplate) {
					if(Library.hasInventoryItem(p.inventory, Items.PAPER) && Library.hasInventoryItem(p.inventory, Items.DYE)) {
						Library.consumeInventoryItem(p.inventory, Items.PAPER);
						Library.consumeInventoryItem(p.inventory, Items.DYE);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() instanceof ItemCassette) {
					if(Library.hasInventoryItem(p.inventory, ModItems.plate_polymer) && Library.hasInventoryOreDict(p.inventory, "plateSteel")) {
						Library.consumeInventoryItem(p.inventory, ModItems.plate_polymer);
						Library.consumeInventoryItem(p.inventory, ModItems.plate_steel);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_stone_plate || stack.getItem() == ModItems.stamp_stone_wire || stack.getItem() == ModItems.stamp_stone_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_stone_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_stone_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_iron_plate || stack.getItem() == ModItems.stamp_iron_wire || stack.getItem() == ModItems.stamp_iron_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_iron_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_iron_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_steel_plate || stack.getItem() == ModItems.stamp_steel_wire || stack.getItem() == ModItems.stamp_steel_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_steel_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_steel_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_titanium_plate || stack.getItem() == ModItems.stamp_titanium_wire || stack.getItem() == ModItems.stamp_titanium_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_titanium_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_titanium_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_obsidian_plate || stack.getItem() == ModItems.stamp_obsidian_wire || stack.getItem() == ModItems.stamp_obsidian_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_obsidian_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_obsidian_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_schrabidium_plate || stack.getItem() == ModItems.stamp_schrabidium_wire || stack.getItem() == ModItems.stamp_schrabidium_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_schrabidium_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_schrabidium_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				if(stack.getItem() == ModItems.stamp_desh_plate || stack.getItem() == ModItems.stamp_desh_wire || stack.getItem() == ModItems.stamp_desh_circuit) {
					if(Library.hasInventoryItem(p.inventory, ModItems.stamp_desh_flat)) {
						Library.consumeInventoryItem(p.inventory, ModItems.stamp_desh_flat);
						if(!p.inventory.addItemStackToInventory(stack.copy()))
							p.dropItem(stack, true);
						success = true;
					}
				}
				LeafiaCustomPacket.__start(new FolderResponsePacket(success)).__sendToClient(p);
			});

			
			return null;
		}
	}
}