/**
 * Copyright (c) 2011-2016, Data Geekery GmbH (http://www.datageekery.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package glmath.joou;

import java.math.BigInteger;

/**
 * The <code>unsigned byte</code> type
 *
 * @author Lukas Eder
 * @author Ed Schaller
 * @author Jens Nerche
 */
public final class UByte extends UNumber implements Comparable<UByte> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6821055240959745390L;

    /**
     * A constant holding the minimum value an <code>unsigned byte</code> can
     * have, 0.
     */
    public static final int MIN_VALUE = 0x00;

    /**
     * A constant holding the maximum value an <code>unsigned byte</code> can
     * have, 2<sup>8</sup>-1.
     */
    public static final int MAX_VALUE = 0xff;
    /**
     * The value modelling the content of this <code>unsigned byte</code>
     */
    public byte value;

    public UByte() {
        value = 0;
    }

    /**
     * Create an <code>unsigned byte</code> by masking it with <code>0xFF</code>
     * i.e. <code>(byte) -1</code> becomes <code>(ubyte) 255</code>
     *
     * @param value
     */
    public UByte(byte value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @param value
     */
    public UByte(short value) {
        this.value = (byte) value;
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @param value
     */
    public UByte(int value) {
        this.value = (byte) value;
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @param value
     */
    public UByte(long value) {
        this.value = (byte) value;
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @param value
     */
    public UByte(BigInteger value) {
        this.value = value.byteValue();
    }

    /**
     * Create an <code>unsigned byte</code>
     *
     * @param value
     */
    public UByte(String value) {
        this.value = Byte.parseByte(value);
    }

    public UByte(UByte uByte) {
        this.value = uByte.value;
    }

    @Override
    public int hashCode() {
        return Short.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UByte) {
            return value == ((UByte) obj).value;
        }
        return false;
    }

    @Override
    public String toString() {
        return Short.toString(value);
    }

    @Override
    public int compareTo(UByte o) {
        int a = value & 0xff, b = o.value & 0xff;
        return a < b ? -1 : (a == b ? 0 : 1);
    }
    
    public short shortValue() {
        return (short) (value & 0xff);
    }
    
    public int intValue() {
        return value & 0xff;
    }
    
    public long longValue() {
        return value & 0xff;
    }

    /**
     * Throw exception if value out of range (short version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(byte value) throws ArithmeticException {
        if (value < 0) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value;
    }

    /**
     * Throw exception if value out of range (short version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(short value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (byte) value;
    }

    /**
     * Throw exception if value out of range (int version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(int value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (byte) value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(long value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (byte) value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(BigInteger value) throws ArithmeticException {
        if (value.compareTo(BigInteger.ZERO) < 0 || value.intValue() > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value.byteValue();
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static byte checkSigned(String value) throws ArithmeticException {
        if (value.startsWith("-")) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return Byte.parseByte(value);
    }

//    public UByte add_(UByte b) throws NumberFormatException {
//        return new UByte(value + b.value);
//    }
//
//    public UByte add_(int s) throws NumberFormatException {
//        return new UByte(value + (s & MAX_VALUE));
//    }
//
//    public UByte add(UByte b) throws NumberFormatException {
//        return add(b.value);
//    }
//
//    public UByte add(int s) throws NumberFormatException {
//        return set((short) (value + (s & MAX_VALUE)));
//    }
//
//    public UByte div_(UByte b) throws NumberFormatException {
//        return new UByte(value / b.value);
//    }
//
//    public UByte div_(int s) throws NumberFormatException {
//        return new UByte(value / (s & MAX_VALUE));
//    }
//
//    public UByte div(UByte b) throws NumberFormatException {
//        return div(b.value);
//    }
//
//    public UByte div(int s) throws NumberFormatException {
//        return set((short) (value / (s & MAX_VALUE)));
//    }
//
//    public UByte mul_(UByte b) throws NumberFormatException {
//        return new UByte(value * b.value);
//    }
//
//    public UByte mul_(int s) throws NumberFormatException {
//        return set((short) (value * (s & MAX_VALUE)));
//    }
//
//    public UByte mul(UByte b) throws NumberFormatException {
//        return mul(b.value);
//    }
//
//    public UByte mul(int s) throws NumberFormatException {
//        return set((short) (value * (s & MAX_VALUE)));
//    }
//
//    public UByte sub_(UByte b) throws NumberFormatException {
//        return new UByte(b.value);
//    }
//
//    public UByte sub_(int s) throws NumberFormatException {
//        return new UByte((short) (value - (s & MAX_VALUE)));
//    }
//
//    public UByte sub(UByte b) throws NumberFormatException {
//        return sub(b.value);
//    }
//
//    public UByte sub(int s) throws NumberFormatException {
//        return set((short) (value - (s & MAX_VALUE)));
//    }
}
