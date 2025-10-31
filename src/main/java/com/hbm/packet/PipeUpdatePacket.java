package com.hbm.packet;

import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
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

public class PipeUpdatePacket extends RecordablePacket {

	BlockPos pos;
	int id;
	
	public PipeUpdatePacket() {
	}
	
	public PipeUpdatePacket(BlockPos pos) {
		this(pos, 0);
	}
	
	public PipeUpdatePacket(BlockPos pos, int id) {
		this.pos = pos;
		this.id = id;
	}
	

	@Override
	public void fromBits(LeafiaBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		id = buf.readInt();
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(id);
	}
	
	public static class Handler implements IMessageHandler<PipeUpdatePacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PipeUpdatePacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(message.pos);
				
				if(te instanceof TileEntityFFDuctBaseMk2){
					switch(message.id){
					case 0:
						//Don't need you anymore, getActualState seems to be called when the neighbor changes.
						//((TileEntityFFDuctBaseMk2)te).onNeighborChange();
						break;
					case 1:
						TileEntityFFDuctBaseMk2.rebuildNetworks(Minecraft.getMinecraft().world, message.pos);
						break;
					default:
						break;
					}
				}
			});
			return null;
		}
		
	}
}
