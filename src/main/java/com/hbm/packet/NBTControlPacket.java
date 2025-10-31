package com.hbm.packet;

import com.hbm.interfaces.IControlReceiver;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NBTControlPacket extends RecordablePacket {
	
	NBTTagCompound buffer; // i have no wrods
	int x;
	int y;
	int z;

	public NBTControlPacket() { }

	public NBTControlPacket(NBTTagCompound nbt, BlockPos pos) {
		this(nbt, pos.getX(), pos.getY(), pos.getZ());
	}
	
	public NBTControlPacket(NBTTagCompound nbt, int x, int y, int z) {

		this.x = x;
		this.y = y;
		this.z = z;
		
		buffer = (nbt);
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();

		buffer = buf.readNBT();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);

		buf.writeNBT(buffer);
	}

	public static class Handler implements IMessageHandler<NBTControlPacket, IMessage> {
		
		@Override
		public IMessage onMessage(NBTControlPacket m, MessageContext ctx) {

			ctx.getServerHandler().player.server.addScheduledTask(() -> {
				EntityPlayer p = ctx.getServerHandler().player;
				
				if(p.world == null)
					return;
				
				TileEntity te = p.world.getTileEntity(new BlockPos(m.x, m.y, m.z));
				
				 {
					
					NBTTagCompound nbt = m.buffer;
					
					if(nbt != null) {
						if(te instanceof IControlReceiver) {
							
							IControlReceiver tile = (IControlReceiver)te;
							
							if(tile.hasPermission(p))
								tile.receiveControl(nbt);
						}
					}
					
				}
			});
			
			return null;
		}
	}
}