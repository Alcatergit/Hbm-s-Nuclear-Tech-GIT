package com.hbm.packet;

import com.hbm.tileentity.turret.TileEntityTurretBase;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TETurretPacket extends RecordablePacket {

	public int x, y, z;
	public boolean isAI;
	
	public TETurretPacket() {
	}
	
	public TETurretPacket(int x, int y, int z, boolean isAI) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isAI = isAI;
	}
	
	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		isAI = buf.readBoolean();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(isAI);
	}
	
	public static class Handler implements IMessageHandler<TETurretPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TETurretPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(m.x, m.y, m.z);
				World world = Minecraft.getMinecraft().world;
				if(world.isBlockLoaded(pos)){
					TileEntity te = world.getTileEntity(pos);
					if(te instanceof TileEntityTurretBase){
						((TileEntityTurretBase)te).isAI = m.isAI;
					}
				}
			});
			return null;
		}
		
	}

}
