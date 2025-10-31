package com.leafia.dev.optimization.bitbyte;

import com.leafia.dev.optimization.bitbyte.LeafiaBuf.Config;
import com.llib.technical.FifthString;
import com.llib.technical.LeafiaBitByteUTF;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

public class LeafiaBufOutputStream extends OutputStream implements DataOutput {
	final LeafiaBuf buffer;
	final int startIndex;

	public LeafiaBufOutputStream(LeafiaBuf buffer) {
		this.buffer = buffer;
		startIndex = buffer.writerIndex;
	}

	@Override
	public void write(int b) throws IOException {
		buffer.writeByte(b);
	}
	@Override
	public void write(byte[] src,int srcIndex,int len) throws IOException {
		if (len == 0) {
			return;
		}
		byte[] crop = new byte[len];
		System.arraycopy(src,srcIndex,crop,0,len);
		buffer.writeBytes(crop);
	}
	@Override
	public void write(byte[] b) throws IOException {
		buffer.writeBytes(b);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		buffer.writeBoolean(v);
	}
	@Override
	public void writeByte(int v) throws IOException {
		buffer.writeByte(v);
	}
	@Override
	public void writeShort(int v) throws IOException {
		buffer.writeShort(v);
	}
	@Override
	public void writeChar(int v) throws IOException {
		buffer.writeChar(v);
	}
	@Override
	public void writeInt(int v) throws IOException {
		buffer.writeInt(v);
	}
	@Override
	public void writeLong(long v) throws IOException {
		buffer.writeLong(v);
	}
	@Override
	public void writeFloat(float v) throws IOException {
		buffer.writeFloat(v);
	}
	@Override
	public void writeDouble(double v) throws IOException {
		buffer.writeDouble(v);
	}
	@Override
	public void writeBytes(String s) throws IOException {
		int len = s.length();
		for (int i = 0; i < len; i ++) {
			write((byte) s.charAt(i));
		}
	}
	@Override
	public void writeChars(String s) throws IOException {
		int len = s.length();
		for (int i = 0 ; i < len ; i ++) {
			writeChar(s.charAt(i));
		}
	}
	@Override
	public void writeUTF(String s) throws IOException {
		switch(Config.stringMode) {
			case UTF:
				LeafiaBitByteUTF.writeUTF(buffer,s);
				break;
			case MODIFIED:
				LeafiaBitByteUTF.writeUTFLeafia(buffer,s);
				break;
			case FIFTH:
				buffer.writeFifthString(new FifthString(s));
				break;
		}
	}
}
