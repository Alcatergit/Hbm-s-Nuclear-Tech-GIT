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
	int operator;
	int value;
	int reference;
	EnumHand hand;

	public ItemDesignatorPacket()
	{
		
	}

	public ItemDesignatorPacket(int operator, int value, int reference, EnumHand hand)
	{
		this.operator = operator;
		this.value = value;
		this.reference = reference;
		this.hand = hand;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		operator = buf.readInt();
		value = buf.readInt();
		reference = buf.readInt();
		hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(operator);
		buf.writeInt(value);
		buf.writeInt(reference);
		buf.writeBoolean(hand == EnumHand.MAIN_HAND ? true : false);
	}

	public static class Handler implements IMessageHandler<ItemDesignatorPacket, IMessage> {
		
		@Override
		public IMessage onMessage(ItemDesignatorPacket m, MessageContext ctx) {
			ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				
				ItemStack stack = p.getHeldItem(m.hand);
				
				if(stack != null && stack.getItem() == ModItems.designator_manual) {
					if(!stack.hasTagCompound())
						stack.setTagCompound(new NBTTagCompound());
					int x = stack.getTagCompound().getInteger("xCoord");
					int z = stack.getTagCompound().getInteger("zCoord");
					
					int result = 0;

					if(m.operator == 0)
						result += m.value;
					if(m.operator == 1)
						result -= m.value;
					if(m.operator == 2) {
						if(m.reference == 0)
							stack.getTagCompound().setInteger("xCoord", (int)Math.round(p.posX));
						else
							stack.getTagCompound().setInteger("zCoord", (int)Math.round(p.posZ));
						return;
					}
					
					if(m.reference == 0)
						stack.getTagCompound().setInteger("xCoord", x + result);
					else
						stack.getTagCompound().setInteger("zCoord", z + result);
				}
			});
			
			
			return null;
		}
	}
}

