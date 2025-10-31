package com.hbm.packet;

import com.hbm.tileentity.machine.oil.TileEntityMachinePumpjack;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEPumpjackPacket extends RecordablePacket {

	int x;
	int y;
	int z;
	float spin;
	boolean progress;

	public TEPumpjackPacket()
	{
		
	}

	public TEPumpjackPacket(int x, int y, int z, float spin, boolean bool)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.spin = spin;
		this.progress = bool;
	}

	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		spin = buf.readFloat();
		progress = buf.readBoolean();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(spin);
		buf.writeBoolean(progress);
	}

	public static class Handler implements IMessageHandler<TEPumpjackPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEPumpjackPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

					if (te != null && te instanceof TileEntityMachinePumpjack) {
							
						TileEntityMachinePumpjack gen = (TileEntityMachinePumpjack) te;
						gen.rotation = m.spin;
						gen.isProgressing = m.progress;
					}
				} catch(Exception x) { }
			});
			
			return null;
		}
	}
}
