package com.hbm.packet;

import com.hbm.handler.MissileStruct;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TEMissileMultipartPacket implements IMessage {

	int x;
	int y;
	int z;
	MissileStruct missile;

	public TEMissileMultipartPacket()
	{
		
	}
	
	public TEMissileMultipartPacket(BlockPos pos, MissileStruct missile){
		this(pos.getX(), pos.getY(), pos.getZ(), missile);
	}

	public TEMissileMultipartPacket(int x, int y, int z, MissileStruct missile)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.missile = missile;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		missile = MissileStruct.readFromByteBuffer(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		missile.writeToByteBuffer(buf);
	}

	public static class Handler implements IMessageHandler<TEMissileMultipartPacket, IMessage> {
		
		@Override
		public IMessage onMessage(TEMissileMultipartPacket m, MessageContext ctx) {

			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

			if (te != null && te instanceof TileEntityCompactLauncher) {
				
				TileEntityCompactLauncher launcher = (TileEntityCompactLauncher) te;
				launcher.load = m.missile;
			}

			if (te != null && te instanceof TileEntityLaunchTable) {
				
				TileEntityLaunchTable launcher = (TileEntityLaunchTable) te;
				launcher.load = m.missile;
			}

			if (te != null && te instanceof TileEntityMachineMissileAssembly) {
				
				TileEntityMachineMissileAssembly rack = (TileEntityMachineMissileAssembly) te;
				rack.load = m.missile;
			}
			
			return null;
		}
	}
}