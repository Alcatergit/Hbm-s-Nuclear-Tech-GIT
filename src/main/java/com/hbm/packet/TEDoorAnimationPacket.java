package com.hbm.packet;

import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.interfaces.IDoor;
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

public class TEDoorAnimationPacket extends RecordablePacket {

	public int x, y, z;
	public byte state;
	public byte texture;
	
	public TEDoorAnimationPacket() {
	}
	
	public TEDoorAnimationPacket(BlockPos pos, byte state) {
		this(pos, state, (byte) -1);
	}
	
	public TEDoorAnimationPacket(BlockPos pos, byte state, byte tex) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.state = state;
		this.texture = tex;
	}
	
	@Override
	public void fromBits(LeafiaBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		state = buf.readByte();
		if(buf.readableBits() >= 8){
			texture = buf.readByte();
		}
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(state);
		if(texture != -1){
			buf.writeByte(texture);
		}
	}
	
	public static class Handler implements IMessageHandler<TEDoorAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEDoorAnimationPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(m.x, m.y, m.z);
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
				if(te instanceof IAnimatedDoor){
					((IAnimatedDoor) te).handleNewState(IDoor.DoorState.values()[m.state]);
					((IAnimatedDoor) te).setTextureState(m.texture);
				}
			});
			return null;
		}
		
	}

}
