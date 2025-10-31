package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEAssemblerPacket extends RecordablePacket {

	int x;
	int y;
	int z;
	boolean progress;

	public TEAssemblerPacket()
	{
		
	}

	public TEAssemblerPacket(BlockPos pos, boolean bool)
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.progress = bool;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		progress = buf.readBoolean();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(progress);
	}

	public static class Handler implements IMessageHandler<TEAssemblerPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEAssemblerPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(m.x, m.y, m.z);
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);

				if (te != null && te instanceof TileEntityMachineAssembler) {
						
					TileEntityMachineAssembler gen = (TileEntityMachineAssembler) te;
					gen.isProgressing = m.progress;
				}
			});
			
			return null;
		}
	}
}