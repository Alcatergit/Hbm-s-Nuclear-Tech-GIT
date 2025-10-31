package com.leafia.dev.optimization.bitbyte;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/**
 * <h1>UNUSED<br>NEVER USED</h1>
 */
public class LeafiaBufProxy extends ByteBuf {
	final LeafiaBuf buf;
	public LeafiaBufProxy(LeafiaBuf buf) {
		this.buf = buf;
	}
	@Override
	public int capacity() {
		return buf.bytes.length;
	}
	@Override
	public ByteBuf capacity(int newCapacity) {
		if (buf.bytes.length > newCapacity)
			throw new UnsupportedOperationException("LeafiaBuf uses unaligned bit-based internal array and thus cannot be cropped in regular byte-based manner.");
		return this;
	}
	@Override
	public int maxCapacity() {
		return buf.buffer.maxCapacity();
	}
	@Override
	public ByteBufAllocator alloc() {
		return buf.buffer.alloc();
	}
	@Override
	public ByteOrder order() {
		return null;
	}

	@Override
	public ByteBuf order(ByteOrder endianness) {
		return null;
	}

	@Override
	public ByteBuf unwrap() {
		return null;
	}

	@Override
	public boolean isDirect() {
		return false;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public ByteBuf asReadOnly() {
		return null;
	}

	@Override
	public int readerIndex() {
		return 0;
	}

	@Override
	public ByteBuf readerIndex(int readerIndex) {
		return null;
	}

	@Override
	public int writerIndex() {
		return 0;
	}

	@Override
	public ByteBuf writerIndex(int writerIndex) {
		return null;
	}

	@Override
	public ByteBuf setIndex(int readerIndex,int writerIndex) {
		return null;
	}

	@Override
	public int readableBytes() {
		return 0;
	}

	@Override
	public int writableBytes() {
		return 0;
	}

	@Override
	public int maxWritableBytes() {
		return 0;
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isReadable(int size) {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public boolean isWritable(int size) {
		return false;
	}

	@Override
	public ByteBuf clear() {
		return null;
	}

	@Override
	public ByteBuf markReaderIndex() {
		return null;
	}

	@Override
	public ByteBuf resetReaderIndex() {
		return null;
	}

	@Override
	public ByteBuf markWriterIndex() {
		return null;
	}

	@Override
	public ByteBuf resetWriterIndex() {
		return null;
	}

	@Override
	public ByteBuf discardReadBytes() {
		return null;
	}

	@Override
	public ByteBuf discardSomeReadBytes() {
		return null;
	}

	@Override
	public ByteBuf ensureWritable(int minWritableBytes) {
		return null;
	}

	@Override
	public int ensureWritable(int minWritableBytes,boolean force) {
		return 0;
	}

	@Override
	public boolean getBoolean(int index) {
		return false;
	}

	@Override
	public byte getByte(int index) {
		return 0;
	}

	@Override
	public short getUnsignedByte(int index) {
		return 0;
	}

	@Override
	public short getShort(int index) {
		return 0;
	}

	@Override
	public short getShortLE(int index) {
		return 0;
	}

	@Override
	public int getUnsignedShort(int index) {
		return 0;
	}

	@Override
	public int getUnsignedShortLE(int index) {
		return 0;
	}

	@Override
	public int getMedium(int index) {
		return 0;
	}

	@Override
	public int getMediumLE(int index) {
		return 0;
	}

	@Override
	public int getUnsignedMedium(int index) {
		return 0;
	}

	@Override
	public int getUnsignedMediumLE(int index) {
		return 0;
	}

	@Override
	public int getInt(int index) {
		return 0;
	}

	@Override
	public int getIntLE(int index) {
		return 0;
	}

	@Override
	public long getUnsignedInt(int index) {
		return 0;
	}

	@Override
	public long getUnsignedIntLE(int index) {
		return 0;
	}

	@Override
	public long getLong(int index) {
		return 0;
	}

	@Override
	public long getLongLE(int index) {
		return 0;
	}

	@Override
	public char getChar(int index) {
		return 0;
	}

	@Override
	public float getFloat(int index) {
		return 0;
	}

	@Override
	public double getDouble(int index) {
		return 0;
	}

	@Override
	public ByteBuf getBytes(int index,ByteBuf dst) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,ByteBuf dst,int length) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,ByteBuf dst,int dstIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,byte[] dst) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,byte[] dst,int dstIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,ByteBuffer dst) {
		return null;
	}

	@Override
	public ByteBuf getBytes(int index,OutputStream out,int length) throws IOException {
		return null;
	}

	@Override
	public int getBytes(int index,GatheringByteChannel out,int length) throws IOException {
		return 0;
	}

	@Override
	public int getBytes(int index,FileChannel out,long position,int length) throws IOException {
		return 0;
	}

	@Override
	public CharSequence getCharSequence(int index,int length,Charset charset) {
		return null;
	}

	@Override
	public ByteBuf setBoolean(int index,boolean value) {
		return null;
	}

	@Override
	public ByteBuf setByte(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setShort(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setShortLE(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setMedium(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setMediumLE(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setInt(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setIntLE(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setLong(int index,long value) {
		return null;
	}

	@Override
	public ByteBuf setLongLE(int index,long value) {
		return null;
	}

	@Override
	public ByteBuf setChar(int index,int value) {
		return null;
	}

	@Override
	public ByteBuf setFloat(int index,float value) {
		return null;
	}

	@Override
	public ByteBuf setDouble(int index,double value) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,ByteBuf src) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,ByteBuf src,int length) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,ByteBuf src,int srcIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,byte[] src) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,byte[] src,int srcIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf setBytes(int index,ByteBuffer src) {
		return null;
	}

	@Override
	public int setBytes(int index,InputStream in,int length) throws IOException {
		return 0;
	}

	@Override
	public int setBytes(int index,ScatteringByteChannel in,int length) throws IOException {
		return 0;
	}

	@Override
	public int setBytes(int index,FileChannel in,long position,int length) throws IOException {
		return 0;
	}

	@Override
	public ByteBuf setZero(int index,int length) {
		return null;
	}

	@Override
	public int setCharSequence(int index,CharSequence sequence,Charset charset) {
		return 0;
	}

	@Override
	public boolean readBoolean() {
		return false;
	}

	@Override
	public byte readByte() {
		return 0;
	}

	@Override
	public short readUnsignedByte() {
		return 0;
	}

	@Override
	public short readShort() {
		return 0;
	}

	@Override
	public short readShortLE() {
		return 0;
	}

	@Override
	public int readUnsignedShort() {
		return 0;
	}

	@Override
	public int readUnsignedShortLE() {
		return 0;
	}

	@Override
	public int readMedium() {
		return 0;
	}

	@Override
	public int readMediumLE() {
		return 0;
	}

	@Override
	public int readUnsignedMedium() {
		return 0;
	}

	@Override
	public int readUnsignedMediumLE() {
		return 0;
	}

	@Override
	public int readInt() {
		return 0;
	}

	@Override
	public int readIntLE() {
		return 0;
	}

	@Override
	public long readUnsignedInt() {
		return 0;
	}

	@Override
	public long readUnsignedIntLE() {
		return 0;
	}

	@Override
	public long readLong() {
		return 0;
	}

	@Override
	public long readLongLE() {
		return 0;
	}

	@Override
	public char readChar() {
		return 0;
	}

	@Override
	public float readFloat() {
		return 0;
	}

	@Override
	public double readDouble() {
		return 0;
	}

	@Override
	public ByteBuf readBytes(int length) {
		return null;
	}

	@Override
	public ByteBuf readSlice(int length) {
		return null;
	}

	@Override
	public ByteBuf readRetainedSlice(int length) {
		return null;
	}

	@Override
	public ByteBuf readBytes(ByteBuf dst) {
		return null;
	}

	@Override
	public ByteBuf readBytes(ByteBuf dst,int length) {
		return null;
	}

	@Override
	public ByteBuf readBytes(ByteBuf dst,int dstIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf readBytes(byte[] dst) {
		return null;
	}

	@Override
	public ByteBuf readBytes(byte[] dst,int dstIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf readBytes(ByteBuffer dst) {
		return null;
	}

	@Override
	public ByteBuf readBytes(OutputStream out,int length) throws IOException {
		return null;
	}

	@Override
	public int readBytes(GatheringByteChannel out,int length) throws IOException {
		return 0;
	}

	@Override
	public CharSequence readCharSequence(int length,Charset charset) {
		return null;
	}

	@Override
	public int readBytes(FileChannel out,long position,int length) throws IOException {
		return 0;
	}

	@Override
	public ByteBuf skipBytes(int length) {
		return null;
	}

	@Override
	public ByteBuf writeBoolean(boolean value) {
		return null;
	}

	@Override
	public ByteBuf writeByte(int value) {
		return null;
	}

	@Override
	public ByteBuf writeShort(int value) {
		return null;
	}

	@Override
	public ByteBuf writeShortLE(int value) {
		return null;
	}

	@Override
	public ByteBuf writeMedium(int value) {
		return null;
	}

	@Override
	public ByteBuf writeMediumLE(int value) {
		return null;
	}

	@Override
	public ByteBuf writeInt(int value) {
		return null;
	}

	@Override
	public ByteBuf writeIntLE(int value) {
		return null;
	}

	@Override
	public ByteBuf writeLong(long value) {
		return null;
	}

	@Override
	public ByteBuf writeLongLE(long value) {
		return null;
	}

	@Override
	public ByteBuf writeChar(int value) {
		return null;
	}

	@Override
	public ByteBuf writeFloat(float value) {
		return null;
	}

	@Override
	public ByteBuf writeDouble(double value) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(ByteBuf src) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(ByteBuf src,int length) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(ByteBuf src,int srcIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(byte[] src) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(byte[] src,int srcIndex,int length) {
		return null;
	}

	@Override
	public ByteBuf writeBytes(ByteBuffer src) {
		return null;
	}

	@Override
	public int writeBytes(InputStream in,int length) throws IOException {
		return 0;
	}

	@Override
	public int writeBytes(ScatteringByteChannel in,int length) throws IOException {
		return 0;
	}

	@Override
	public int writeBytes(FileChannel in,long position,int length) throws IOException {
		return 0;
	}

	@Override
	public ByteBuf writeZero(int length) {
		return null;
	}

	@Override
	public int writeCharSequence(CharSequence sequence,Charset charset) {
		return 0;
	}

	@Override
	public int indexOf(int fromIndex,int toIndex,byte value) {
		return 0;
	}

	@Override
	public int bytesBefore(byte value) {
		return 0;
	}

	@Override
	public int bytesBefore(int length,byte value) {
		return 0;
	}

	@Override
	public int bytesBefore(int index,int length,byte value) {
		return 0;
	}

	@Override
	public int forEachByte(ByteProcessor processor) {
		return 0;
	}

	@Override
	public int forEachByte(int index,int length,ByteProcessor processor) {
		return 0;
	}

	@Override
	public int forEachByteDesc(ByteProcessor processor) {
		return 0;
	}

	@Override
	public int forEachByteDesc(int index,int length,ByteProcessor processor) {
		return 0;
	}

	@Override
	public ByteBuf copy() {
		return null;
	}

	@Override
	public ByteBuf copy(int index,int length) {
		return null;
	}

	@Override
	public ByteBuf slice() {
		return null;
	}

	@Override
	public ByteBuf retainedSlice() {
		return null;
	}

	@Override
	public ByteBuf slice(int index,int length) {
		return null;
	}

	@Override
	public ByteBuf retainedSlice(int index,int length) {
		return null;
	}

	@Override
	public ByteBuf duplicate() {
		return null;
	}

	@Override
	public ByteBuf retainedDuplicate() {
		return null;
	}

	@Override
	public int nioBufferCount() {
		return 0;
	}

	@Override
	public ByteBuffer nioBuffer() {
		return null;
	}

	@Override
	public ByteBuffer nioBuffer(int index,int length) {
		return null;
	}

	@Override
	public ByteBuffer internalNioBuffer(int index,int length) {
		return null;
	}

	@Override
	public ByteBuffer[] nioBuffers() {
		return new ByteBuffer[0];
	}

	@Override
	public ByteBuffer[] nioBuffers(int index,int length) {
		return new ByteBuffer[0];
	}

	@Override
	public boolean hasArray() {
		return false;
	}

	@Override
	public byte[] array() {
		return new byte[0];
	}

	@Override
	public int arrayOffset() {
		return 0;
	}

	@Override
	public boolean hasMemoryAddress() {
		return false;
	}

	@Override
	public long memoryAddress() {
		return 0;
	}

	@Override
	public String toString(Charset charset) {
		return null;
	}

	@Override
	public String toString(int index,int length,Charset charset) {
		return null;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	@Override
	public int compareTo(ByteBuf buffer) {
		return 0;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public ByteBuf retain(int increment) {
		return null;
	}

	@Override
	public int refCnt() {
		return 0;
	}

	@Override
	public ByteBuf retain() {
		return null;
	}

	@Override
	public ByteBuf touch() {
		return null;
	}

	@Override
	public ByteBuf touch(Object hint) {
		return null;
	}

	@Override
	public boolean release() {
		return false;
	}

	@Override
	public boolean release(int decrement) {
		return false;
	}
}
