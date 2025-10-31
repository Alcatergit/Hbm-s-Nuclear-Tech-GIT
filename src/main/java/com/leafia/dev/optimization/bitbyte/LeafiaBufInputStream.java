package com.leafia.dev.optimization.bitbyte;

import com.leafia.dev.optimization.bitbyte.LeafiaBuf.Config;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.technical.LeafiaBitByteUTF;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class LeafiaBufInputStream extends InputStream implements DataInput {
	final LeafiaBuf buffer;
	final int startIndex;
	final int endIndex;

	public LeafiaBufInputStream(LeafiaBuf buffer) {
		this(buffer,(int)Math.ceil(buffer.readableBits()/8d));
	}
	public LeafiaBufInputStream(LeafiaBuf buffer,int length) {
		this.buffer = buffer;
		this.startIndex = buffer.readerIndex;
		this.endIndex = startIndex+length-1;
	}

	@Override
	public int read() throws IOException {
		if (!buffer.isReadable())
			return -1;
		return buffer.readByte()&0xFF;
	}
	@Override
	public int read(byte[] b) throws IOException {
		buffer.readBytes(b);
		return b.length;
	}
	@Override
	public int read(byte[] src,int srcIndex,int len) throws IOException {
		if (len == 0) {
			return -1;
		}
		byte[] crop = new byte[len];
		System.arraycopy(src,srcIndex,crop,0,len);
		buffer.readBytes(crop);
		return len;
	}
	@Override
	public void mark(int readlimit) {
		buffer.markReaderIndex();
	}
	@Override
	public boolean markSupported() {
		return true;
	}
	@Override
	public long skip(long n) throws IOException {
		if (n > Integer.MAX_VALUE) {
			return skipBytes(Integer.MAX_VALUE);
		} else {
			return skipBytes((int) n);
		}
	}
	@Override
	public void reset() throws IOException {
		buffer.resetReaderIndex();
	}

	@Override
	public int available() throws IOException {
		return endIndex+1-buffer.readerIndex;
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		checkAvailable(b.length);
		for (int i = 0; i < b.length; i++)
			b[i] = buffer.readByte();
	}
	@Override
	public void readFully(byte[] b,int off,int len) throws IOException {
		checkAvailable(len);
		for (int i = 0; i < len; i++)
			b[i+off] = buffer.readByte();
	}
	@Override
	public int skipBytes(int n) throws IOException {
		int b = Math.min(available(),n*8);
		buffer.skipBits(b);
		return b;
	}
	@Override
	public boolean readBoolean() throws IOException {
		return buffer.readBoolean();
	}
	@Override
	public byte readByte() throws IOException {
		if (!buffer.isReadable()) {
			throw new EOFException();
		}
		return buffer.readByte();
	}
	@Override
	public int readUnsignedByte() throws IOException {
		return buffer.readUnsignedByte();
	}
	@Override
	public short readShort() throws IOException {
		return buffer.readShort();
	}
	@Override
	public int readUnsignedShort() throws IOException {
		return buffer.readUnsignedShort();
	}
	@Override
	public char readChar() throws IOException {
		return buffer.readChar();
	}
	@Override
	public int readInt() throws IOException {
		return buffer.readInt();
	}
	@Override
	public long readLong() throws IOException {
		return buffer.readLong();
	}
	@Override
	public float readFloat() throws IOException {
		return buffer.readFloat();
	}
	@Override
	public double readDouble() throws IOException {
		return buffer.readDouble();
	}
	@Override
	public String readLine() throws IOException {
		throw new UnsupportedOperationException("fuck you");
	}
	@Override
	public String readUTF() throws IOException {
		switch(Config.stringMode) {
			case UTF:
				return LeafiaBitByteUTF.readUTF(buffer);
			case MODIFIED:
				return LeafiaBitByteUTF.readUTFLeafia(buffer);
			case FIFTH:
				return buffer.readFifthString().toString();
			default:
				throw new LeafiaDevFlaw("LeafiaBuf.Config.stringMode >> unrecognized enum "+Config.stringMode.toString());
		}
		//return DataInputStream.readUTF(this);
	}
	private void checkAvailable(int fieldSize) throws IOException {
		if (fieldSize < 0) {
			throw new IndexOutOfBoundsException("fieldSize cannot be a negative number");
		}
		if (fieldSize*8 > available()) {
			throw new EOFException("fieldSize is too long! Length is " + fieldSize + " (bytes), "
					+ ", but maximum is " + available() + " (bytes)");
		}
	}
}
