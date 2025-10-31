package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityTesla;
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

import java.util.ArrayList;
import java.util.List;

public class TETeslaPacket extends RecordablePacket {
	
	public BlockPos pos;
	public List<double[]> targets;
	
	
	public TETeslaPacket() {
	}
	
	public TETeslaPacket(BlockPos pos, List<double[]> targets) {
		this.targets = targets;
		this.pos = pos;
	}
	
	
	
	@Override
	public void fromBits(LeafiaBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		int size = buf.readInt();
		targets = new ArrayList<double[]>(size);
		for(int i = 0; i < size; i++){
			targets.add(new double[]{buf.readDouble(), buf.readDouble(), buf.readDouble()});
		}
		
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(targets.size());
		for(int i = 0; i < targets.size(); i ++){
			double[] d = targets.get(i);
			buf.writeDouble(d[0]);
			buf.writeDouble(d[1]);
			buf.writeDouble(d[2]);
		}
	}
	
	public static class Handler implements IMessageHandler<TETeslaPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TETeslaPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(m.pos);
				if(te instanceof TileEntityTesla){
					((TileEntityTesla) te).targets = m.targets;
				}
			});
			return null;
		}
		
	}
}
