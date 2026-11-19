package com.hbm.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeSyncPacket implements IMessage {

	private int chunkX;
	private int chunkZ;
	private byte[] biomeArray;

	public BiomeSyncPacket() {
	}

	public BiomeSyncPacket(int chunkX, int chunkZ, byte[] biomeArray) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.biomeArray = biomeArray;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.chunkX);
		buf.writeInt(this.chunkZ);
		for (int i = 0; i < 256; i++) {
			buf.writeByte(this.biomeArray[i]);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.chunkX = buf.readInt();
		this.chunkZ = buf.readInt();
		this.biomeArray = new byte[256];
		for (int i = 0; i < 256; i++) {
			this.biomeArray[i] = buf.readByte();
		}
	}

	public static class Handler implements IMessageHandler<BiomeSyncPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(BiomeSyncPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				WorldClient world = Minecraft.getMinecraft().world;
				if (world == null) return;
				if (!world.getChunkProvider().isChunkGeneratedAt(m.chunkX, m.chunkZ)) return;
				Chunk chunk = world.getChunk(m.chunkX, m.chunkZ);
				byte[] target = chunk.getBiomeArray();
				if (target.length != 256) return;
				System.arraycopy(m.biomeArray, 0, target, 0, 256);
				chunk.markDirty();
			});
			return null;
		}
	}
}