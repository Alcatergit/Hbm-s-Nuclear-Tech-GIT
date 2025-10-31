package com.hbm.packet;

import com.hbm.items.ModItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemDesignatorPacket implements IMessage {

	//0: Add
	//1: Subtract
	//2: Set
	int x;
	int z;

	public ItemDesignatorPacket(){
	}

	public ItemDesignatorPacket(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.z = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<ItemDesignatorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ItemDesignatorPacket m, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				
				ItemStack stack = p.getHeldItem(EnumHand.MAIN_HAND);
				
				if(stack == null || stack.getItem() != ModItems.designator_manual) {
					stack = p.getHeldItem(EnumHand.OFF_HAND);
					if(stack == null || stack.getItem() != ModItems.designator_manual)
						return;
				}
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());

				stack.getTagCompound().setInteger("xCoord", m.x);
				stack.getTagCompound().setInteger("zCoord", m.z);
			});
			return null;
		}
	}
}

