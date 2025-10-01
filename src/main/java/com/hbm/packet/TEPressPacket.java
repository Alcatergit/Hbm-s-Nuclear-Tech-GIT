package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineEPress;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEPressPacket implements IMessage {

	int x;
	int y;
	int z;
	int item;
	int meta;
    int stampItem;
    int stampMeta;
	int progress;

	public TEPressPacket()
	{
		
	}

	public TEPressPacket(int x, int y, int z, ItemStack stack, ItemStack stamp, int progress)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.item = 0;
		this.meta = 0;
        this.stampItem = 0;
        this.stampMeta = 0;
        if(stack != null && !stack.isEmpty()) {
			this.item = Item.getIdFromItem(stack.getItem());
			this.meta = stack.getItemDamage();
		}
        if(stamp != null && !stamp.isEmpty()) {
            this.stampItem = Item.getIdFromItem(stamp.getItem());
            this.stampMeta = stamp.getItemDamage();
        }
		this.progress = progress;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		item = buf.readInt();
		meta = buf.readInt();
        stampItem = buf.readInt();
        stampMeta = buf.readInt();
        progress = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(item);
		buf.writeInt(meta);
        buf.writeInt(stampItem);
        buf.writeInt(stampMeta);
        buf.writeInt(progress);
	}

	public static class Handler implements IMessageHandler<TEPressPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEPressPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				if (te != null && te instanceof TileEntityMachinePress gen) {

                    gen.item = m.item;
					gen.meta = m.meta;
                    gen.stampItem = m.stampItem;
                    gen.stampMeta = m.stampMeta;
                    gen.progress = m.progress;
				}
				if (te != null && te instanceof TileEntityMachineEPress gen) {

                    gen.item = m.item;
					gen.meta = m.meta;
                    gen.stampItem = m.stampItem;
                    gen.stampMeta = m.stampMeta;
                    gen.progress = m.progress;
				}
			});
			
			return null;
		}
	}

}