package com.llib.technical;

import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.math.range.RangeInt;

import java.util.ArrayList;
import java.util.List;

/** writeUTF, readUTF algorithm for BitByteBuf format
 * <p><h1>Explanation:</h1>
 * For example, imagine we had a number 735.
 * <p>This is 001011011111 (12 digits) in binary. Digits in binary are called bits, so <b>this is 12 bits</b>.
 * <p>Bytes are group of 8 bits as 1 byte, but y'know 8 bits (digits)
 * <br>are somewhat long, so in this example we'll pretend as if <b>1 byte equaled 4 bits</b>.
 * <p>If we separate aforementioned binary number into bytes, it would be
 * <br><tt>0010</tt> <tt>1101</tt> <tt>1111</tt>
 * <hr>
 * <h3>Here's where problems occur:</h3>
 * We're used to read numbers from <i>right to left</i>, right? And so are computers.
 * <br>So in order to make it readable, <b>normal ByteBufs</b> inverts the orders of above binaries like so:
 * <pre>{@code {0b1111,0b1101,0b0010}}</pre>
 * Like so, you can read this array from left to right like usual.
 * <p>
 * <h3>However, I decided to not do that with BitByteBuf (LeafiaBuf).</h3>
 * Instead, I made it just put those bytes in the same order, <b>to the start of the array</b>.
 * <br>When reading, it will just start from right to left, just like how we read numbers.
 * <p>Because writeUTF and readUTF are coded to write multiple byte long data (like short which is 2 bytes) by
 * <br>inserting one byte by one, but read them in masses (for some reason), it wonderfully breaks for my buffer,
 * <br>by inserting bytes in the wrong order.
 * <pre>{@code ~~ [byte abcd] [byte abcd] [byte abcd] [short ....efgh] [short abcd....];
 * -------------------------------------------------------------------------
 *          ^^^^        ^^^^        ^^^^             ^^^^         ^^^^
 *     byte abcd   byte abcd   byte abcd       short efghabcd    <----}
 *                                           ^^^^^^^^ Wrong</pre>
 */
public class LeafiaBitByteUTF {
	public static final RangeInt ascii = new RangeInt(0x1,0x7f);
	/* Original UTF format, which is in following categories:
	* 0xxx xxxx                       (1~127,    1h~7Fh)
	* 110x xxxx 10xx xxxx             (128~2047, 80h~7FFh)
	* 1110 xxxx 10xx xxxx 10xx xxxx   (2048+,    800h+)
	* All of these are made into single 16bit char type at the end.
	*/
	public static int writeUTF(LeafiaBuf buf,String string) {
		int utfLength = 0;
		int allocation = buf.writerIndex;
		buf.writeShort(0); // allocate
		for (char c : string.toCharArray()) {
			if (ascii.isInRange(c)) {
				utfLength++;
				buf.writeByte(c);
			} else if (c >= 0x800) {
				utfLength += 3;
				buf.writeMedium(
						0b1110_0000_1000_0000_1000_0000
								| (c& 0b0000_0000_0011_1111)
								| (c& 0b0000_1111_1100_0000)<<2
								| (c& 0b1111_0000_0000_0000)<<4
				);
			} else {
				utfLength += 2;
				buf.writeShort(
						0b1100_0000_1000_0000
								| (c& 0b0000_0011_1111)
								| (c& 0b1111_1100_0000)<<2
				); //                   ^ extra bit just to be safe
			}
		}
		if (utfLength > 65535)
			throw new LeafiaDevFlaw("encoded string too long: " + utfLength + " bytes");
		int pos = buf.writerIndex;
		buf.writerIndex = allocation;
		buf.writeShort(utfLength);
		buf.writerIndex = pos;
		return utfLength*8+16;
	}
	public static String readUTF(LeafiaBuf buf) {
		int utfLength = buf.readUnsignedShort();
		int writtenBytes = 0;
		List<Character> chars = new ArrayList<>();
		while (writtenBytes < utfLength) {
			int preview = buf.forecast(24);
			if ((preview>>7&1) == 0) {
				writtenBytes++;
				chars.add((char)buf.readUnsignedByte());
			} else {
				if (((preview>>14)&1) == 0) {
					writtenBytes += 3;
					int med = buf.readUnsignedMedium();
					int med0 = med&0b0000_0000_0000_0000_0011_1111;
					int med1 = med&0b0000_0000_0011_1111_0000_0000;
					int med2 = med&0b0000_1111_0000_0000_0000_0000;
					chars.add((char)(med2>>4|med1>>2|med0));
				} else {
					writtenBytes += 2;
					int sh = buf.readUnsignedShort();
					int sh0 = sh&0b0000_0000_0011_1111;
					int sh1 = sh&0b0001_1111_0000_0000;
					chars.add((char)(sh1>>2|sh0));
				}
			}
		}
		char[] outArray = new char[chars.size()];
		for (int i = 0; i < outArray.length; i++)
			outArray[i] = chars.get(i);
		return new String(outArray);
	}
	/* My UTF format, utilizing BitByte's ability to allow insertion of unaligned bits:
	 * xxxx xxx0                       (1~127,    1h~7Fh)
	 * x xxxx xxxx xx01             (128~2047, 80h~7FFh)
	 * xx xxxx xxxx xxxx xx11   (2048+,    800h+)
	 * All of these are made into single 16bit char type at the end.
	 */
	public static int writeUTFLeafia(LeafiaBuf buf,String string) {
		if (string.length() > 65535)
			throw new LeafiaDevFlaw("string too long: " + string.length() + " bytes");
		int indexStart = buf.writerIndex;
		buf.writeShort(string.length());
		for (char c : string.toCharArray()) {
			if (ascii.isInRange(c))
				buf.writeByte(c<<1);
			else if (c >= 0x800)
				buf.insert((c&0b11_1111_1111_1111_11)<<2|0b11,18);
			else
				buf.insert((c&0b1_1111_1111_11)<<2|0b01,13);
		}
		return buf.writerIndex-indexStart;
	}
	public static String readUTFLeafia(LeafiaBuf buf) {
		char[] chars = new char[buf.readUnsignedShort()];
		for (int i = 0; i < chars.length; i++) {
			if (buf.extract(1) == 0)
				chars[i] = (char)buf.extract(7);
			else if (buf.extract(1) == 0)
				chars[i] = (char)buf.extract(11);
			else
				chars[i] = (char)buf.extract(16);
		}
		return new String(chars);
	}
}
