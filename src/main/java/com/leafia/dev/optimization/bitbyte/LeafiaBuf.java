package com.leafia.dev.optimization.bitbyte;

import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.technical.BitByteBuf;
import com.llib.technical.FifthString;
import com.llib.technical.FifthString.ControlType;
import com.llib.technical.LeafiaBitByteUTF;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;

public class LeafiaBuf extends BitByteBuf {
	public static class Config {
		public enum PacketStringEncoding {UTF,MODIFIED,FIFTH}
		public static PacketStringEncoding stringMode = PacketStringEncoding.FIFTH;
		public static boolean singleBitBooleans = true;
	}

	int busy = 0;
	final ByteBuf buffer;
	public int writerIndex = 0;
	public int readerIndex = 0;
	public int writerIndexMarker = 0;
	public int readerIndexMarker = 0;
	//public LeafiaBuf identifia = null;
	public LeafiaBuf(ByteBuf buffer) {
		super();
		this.buffer = buffer;
	}
	public void insert(int value,int length) {/*
		if (identifia != null) {
			System.out.println("Saving "+length+", value: "+value);
			if (Integer.toUnsignedLong(value) >= (long)1<<(long)length) {
				throw new LeafiaDevFlaw("Inserting value exceeding bit length!!! "+value+" as "+length+" bits");
			}
			identifia.writeByte(length);
		}*/
		writerIndex = this.insertBits(writerIndex,value,length);
	}
	public int extract(int length) {
		if (busy > 0)
			throw new LeafiaDevFlaw("readerIndex is confused!");
		int value = this.extractBits(readerIndex,readerIndex+length-1);/*
		if (identifia != null) {
			int expected = identifia.readByte()&0xFF;
			System.out.println("Extracting "+length+" bits (Index "+readerIndex+"<"+(identifia.readerIndex-1)+">, Expected: "+expected+" bits) value: "+value+", unsigned: "+Integer.toUnsignedLong(value));
			if (length > expected) {
				if (Math.floorMod(length,expected) == 0) {
					for (int i = 1; i < Math.floorDiv(length,expected); i++) {
						int expected2 = identifia.readByte()&0xFF;
						System.out.println("Next expected "+expected2+" bits");
					}
				} else {
					System.out.println("SERIOUS WARNING ############################## LENGTH "+length+", EXPECTATION "+expected+", POSSIBLE CORRUPTION!");
				}
			}
		}*/
		readerIndex += length;
		return value;
	}
	public String extractToString(int length) {
		String str = Long.toBinaryString(extract(length)&0xFFFFFFFFL);
		for (int i = str.length(); i < length; i++)
			str = "0"+str;
		return str;
	}
	byte[] shift(int startBit) {
		int newLength = bytes.length-Math.floorDiv(startBit,8);
		if (newLength < 0)
			return new byte[0];
		byte[] outArray = new byte[newLength];
		int offset = Math.floorMod(startBit,8);
		System.arraycopy(bytes,0,outArray,0,outArray.length);
		for (int i = outArray.length-1; i >= 0; i--) {
			int next = (i == 0) ? 0 : outArray[i-1]&0xFF;
			outArray[i] = (byte)((outArray[i]&0xFF)>>offset|((next&((1<<offset+1)-1))<<8-offset));
		}
		return outArray;
	}/*
	void printBytes(byte[] bytes) {
		String str = "";
		for (byte b : bytes) {
			if (identifia != null) {
				int expected = identifia.readByte()&0xFF;
				System.out.println("Length: extracting 8 bits (Expected: "+expected+" bits)");
			}
			String s = Integer.toBinaryString((int)b&0xFF);
			for (int i = s.length(); i < 8; i++)
				s = "0" + s;
			System.out.println(s+" (Value: "+b+", unsigned: "+((int)b&0xFF)+")");
			str = str+s;
		}
	}*/
	ByteBuf substitute(int startBit) {
		//System.out.println("Creating substitute!");
		byte[] subBytes = shift(startBit);
		ByteBuf buf = buffer.alloc().buffer(subBytes.length,buffer.maxCapacity());
		for (int i = subBytes.length-1; i >= 0; i--)/*
			if (identifia != null) {
				int expected = identifia.readByte()&0xFF;
				System.out.println("Length: extracting 8 bits (Expected: "+expected+" bits)");
				int b = subBytes[i]&0xFF;
				String s = Integer.toBinaryString((int)b&0xFF);
				for (int i2 = s.length(); i2 < 8; i2++)
					s = "0" + s;
				System.out.println(s+" (Value: "+b+", unsigned: "+b+")");
			}*/
			buf.writeByte(subBytes[i]);
		//System.out.println("Shifted "+startBit+" bits | Own bytes: "+bytes.length+" -> "+(subBytes.length+startBit*8));
		return buf;
	}

	// ByteBuf & AbstractByteBuf
	public LeafiaBuf resetReaderIndex() {
		readerIndex = readerIndexMarker;
		return this;
	}
	public LeafiaBuf resetWriterIndex() {
		writerIndex = writerIndexMarker;
		return this;
	}
	public LeafiaBuf markReaderIndex() {
		readerIndexMarker = readerIndex;
		return this;
	}
	public LeafiaBuf markWriterIndex() {
		writerIndexMarker = writerIndex;
		return this;
	}
	public int readableBits() {
		return writerIndex-readerIndex;
	}
	public boolean isReadable() {
		return readableBits() > 0;
	}
	public boolean isReadable(int size) {
		return readableBits() >= size;
	}
	public boolean readBoolean() {
		return extract(Config.singleBitBooleans ? 1 : 8) >= 1;
	}
	public byte readByte() {
		return (byte)extract(8);
	}
	public short readUnsignedByte() {
		return (short)(extract(8)&0xFF);
	}
	public short readShort() {
		return (short)extract(16);
	}
	public int readUnsignedShort() {
		return (int)(extract(16)&0xFFFF);
	}
	public int readMedium() {
		return extract(24);
	}
	public int readUnsignedMedium() {
		return (extract(24)&0xFFFFFF);
	}
	public int readInt() {
		return extract(32);
	}
	public BlockPos readPos() {
		return new BlockPos(readInt(),readInt(),readInt());
	}
	public Vec3i readVec3i() {
		return new Vec3i(readInt(),readInt(),readInt());
	}
	public long readUnsignedInt() {
		return (long)extract(32)&0xFFFFFFFFL;
	}
	public long readLong() {
		return (long)extract(32)&0xFFFFFFFFL|((long)extract(32)<<32L);
	}
	public char readChar() {
		return (char)extract(16);
	}
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}
	public LeafiaBuf readBytes(byte[] dst, int dstIndex, int length) {
		//System.out.println("readBytes "+length);
		for (int i = 0; i < length; i++)
			dst[dstIndex+i] = readByte();
		return this;
	}
	public LeafiaBuf readBytes(byte[] dst) {
		//System.out.println("readBytes dst");
		readBytes(dst, 0, dst.length);
		return this;
	}
	public int forecast(int maxBits) {
		return this.extractBits(readerIndex,readerIndex+Math.min(maxBits,readableBits())-1);
	}
	public LeafiaBuf skipBytes(int length) {
		return skipBits(length*8);
	}
	public LeafiaBuf skipBits(int length) {
		readerIndex += length;
		return this;
	}
	public LeafiaBuf writeBoolean(boolean value) {
		insert(value ? 1 : 0,Config.singleBitBooleans ? 1 : 8);
		return this;
	}
	public LeafiaBuf writeByte(int value) {
		insert(value&0xFF,8);
		return this;
	}
	public LeafiaBuf writeShort(int value) {
		insert(value&0xFFFF,16);
		return this;
	}
	public LeafiaBuf writeMedium(int value) {
		insert(value&0xFFFFFF,24);
		return this;
	}
	public LeafiaBuf writeInt(int value) {
		insert(value,32);
		return this;
	}
	public LeafiaBuf writeVec3i(Vec3i vec) {
		writeInt(vec.getX());
		writeInt(vec.getY());
		writeInt(vec.getZ());
		return this;
	}
	public LeafiaBuf writeLong(long value) {
		insert((int)value,32);
		insert((int)(value>>>32),32);
		return this;
	}
	public LeafiaBuf writeChar(int value) {
		insert(value&0xFFFF,16);
		return this;
	}
	public LeafiaBuf writeFloat(float value) {
		writeInt(Float.floatToIntBits(value));
		return this;
	}
	public LeafiaBuf writeDouble(double value) {
		writeLong(Double.doubleToLongBits(value));
		return this;
	}
	public LeafiaBuf writeBytes(ByteBuf src) {
		//System.out.println("writeBytes "+src.readableBytes());
		int pos = writerIndex;
		while (src.isReadable()) // i think this is retarded but whatever
			writeByte(src.readByte());
		//System.out.println("writeBytes complete (+"+((writerIndex-pos)/8f)+" bytes)");
		return this;
	}
	public LeafiaBuf writeBytes(byte[] src) {
		for (byte b : src) {
			writeByte(b);
		}
		return this;
	}
	public LeafiaBuf writeZero(int length) {
		return writeZeroBits(length*8);
	}
	public LeafiaBuf writeZeroBits(int length) {
		insert(0,length);
		return this;
	}
	public String toString(Charset charset) {
		return buffer.toString(charset);
	}
	public String toString(int index,int length,Charset charset) {
		return buffer.toString(index,length,charset);
	}
	public String toString() {
		return substitute(readerIndex).toString();
	}

	// ByteBufUtils
	@Nullable
	public NBTTagCompound readNBT() {
		byte b0 = this.readByte();
		if (b0 == 0) {
			return null;
		} else {
			readerIndex -= 8;
			try {
				return CompressedStreamTools.read(new LeafiaBufInputStream(this), new NBTSizeTracker(2097152L));
			} catch (IOException ioexception) {
				throw new EncoderException(ioexception);
			} catch (NullPointerException fucker) {
				System.out.println("motherfucker is comitting "+fucker.getMessage());
			}
		}
		return null;
	}
	public ItemStack readItemStack()
	{
		int i = this.readShort();

		if (i < 0)
		{
			return ItemStack.EMPTY;
		}
		else
		{
			int j = this.readByte();
			int k = this.readShort();
			ItemStack itemstack = new ItemStack(Item.getItemById(i), j, k);
			itemstack.getItem().readNBTShareTag(itemstack, this.readNBT());
			return itemstack;
		}
	}
	public String readUTF8String() {
		ByteBuf substitute = substitute(readerIndex);
		busy++;
		int index0 = substitute.readerIndex();
		String output = ByteBufUtils.readUTF8String(substitute);
		int delta = substitute.readerIndex()-index0;
		//System.out.println("##### DELTA: "+delta);
		readerIndex += delta*8;
		busy--;
		return output;
	}
	public LeafiaBuf writeUTF8String(String string) {
		ByteBuf buf = buffer.alloc().buffer(0,buffer.maxCapacity());
		ByteBufUtils.writeUTF8String(buf,string);
		//System.out.println("writin stRINNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
		//int bitchy = writerIndex;
		writeBytes(buf);
		//System.out.println("Written ########### "+(writerIndex-bitchy));
		return this;
	}
	public LeafiaBuf writeNBT(@Nullable NBTTagCompound nbt) {
		if (nbt == null)
		{
			this.writeByte(0);
		}
		else
		{
			try
			{
				CompressedStreamTools.write(nbt, new LeafiaBufOutputStream(this));
			}
			catch (IOException ioexception)
			{
				throw new EncoderException(ioexception);
			}
		}
		return this;
	}
	public LeafiaBuf writeItemStack(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			this.writeShort(-1);
		}
		else
		{
			this.writeShort(Item.getIdFromItem(stack.getItem()));
			this.writeByte(stack.getCount());
			this.writeShort(stack.getMetadata());
			NBTTagCompound nbttagcompound = null;

			if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
			{
				nbttagcompound = stack.getItem().getNBTShareTag(stack);
			}

			this.writeNBT(nbttagcompound);
		}

		return this;
	}
	public FifthString readFifthString() {
		FifthString fifth = new FifthString(null);
		for (int code; (readableBits() >= 5 && (code = extract(5))*0==0);) {
			fifth.append(code);
			if (code == ControlType.END.code)
				break;
			else if (code == ControlType.SPECIAL.code) {
				if (true) // for future possiblities where you would want to put a branch here
					fifth.append(LeafiaBitByteUTF.readUTFLeafia(this));
			}
		}
		return fifth;
	}
	public LeafiaBuf writeFifthString(FifthString string) {
		int utfIndex = 0;
		for (int code : string.codes) {
			insert(code,5);
			if (code == ControlType.END.code)
				break;
			else if (code == ControlType.SPECIAL.code) {
				if (true) { // for future possiblities where you would want to put a branch here
					LeafiaBitByteUTF.writeUTFLeafia(this,string.utfs[utfIndex]);
					utfIndex++;
				}
			}
		}
		return this;
	}
}
