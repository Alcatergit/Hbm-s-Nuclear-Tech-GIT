package com.leafia.dev.optimization.diagnosis;

import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class RecordablePacket implements IMessage {
	public static double bytesUsage = 0;
	public static double previousByteUsage = 0;
	public static double bytesUsageSec = 0;
	public static double previousByteUsageSec = 0;
	public static double bytesUsageMin = 0;
	public static double previousByteUsageMin = 0;
	@Override
	final public void fromBytes(ByteBuf buf) { // Can you feel the pain I've gone through debugging this class?
		//System.out.println("##################################### DECODING");
		LeafiaBuf compress = new LeafiaBuf(buf);
		//LeafiaBuf identifia = new LeafiaBuf(buf);
		compress.writerIndex = buf.readInt();
		//identifia.writerIndex = buf.readInt();
		//identifia.bytes = new byte[(int)Math.ceil(identifia.writerIndex/8d)];
		compress.bytes = new byte[(int)Math.ceil(compress.writerIndex/8d)];
		/*for (int i = 0; i < identifia.bytes.length; i++) {
			byte by = buf.readByte();
			identifia.bytes[i] = by;
		}
		compress.identifia = identifia;*/
		for (int i = 0; i < compress.bytes.length; i++)
			compress.bytes[i] = buf.readByte();
		int pos = 0;
		/*for (int i = identifia.bytes.length-1; i >= 0; i--) {
			int bits = identifia.bytes[i];
			int posNext = pos + bits;
			if (bits > 32) {
				System.out.println("Loading "+bits+" bits (cannot show value due to oversize)");
			} else {
				int value = compress.extractBits(pos,posNext-1);
				long fil = ((long)1<<(long)(bits+1))-1;
				System.out.println("Loading "+bits+" bits (value: "+value+", unsigned: "+((long)value&fil)+")");
			}
			pos = posNext;
		}*/
		int cap = buf.writerIndex();
		bytesUsage += cap;
		bytesUsageSec += cap;
		bytesUsageMin += cap;
		fromBits(compress);
		//System.out.println("DEC COMPLETE #####################################");
	}
	@Override
	final public void toBytes(ByteBuf buf) {
		//System.out.println("##################################### ENCODING");
		//LeafiaBuf identifia = new LeafiaBuf(buf);
		LeafiaBuf compress = new LeafiaBuf(buf);
		//compress.identifia = identifia;
		toBits(compress);
		buf.writeInt(compress.writerIndex);
		//buf.writeInt(identifia.writerIndex);
		//buf.writeBytes(identifia.bytes);
		buf.writeBytes(compress.bytes);
		int cap = buf.writerIndex();
		bytesUsage += cap;
		bytesUsageSec += cap;
		bytesUsageMin += cap;
		//System.out.println("ENC COMPLETE #####################################");
	}
	abstract protected void fromBits(LeafiaBuf buf);
	abstract protected void toBits(LeafiaBuf buf);
}
