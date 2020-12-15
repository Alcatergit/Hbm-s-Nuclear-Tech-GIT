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
 * The <code>unsigned short</code> type
 *
 * @author Lukas Eder
 * @author Jens Nerche
 */
public final class UShort extends UNumber implements Comparable<UShort> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6821055240959745390L;

    public static final int MIN_VALUE = 0x0000;

    /**
     * A constant holding the maximum value an <code>unsigned short</code> can
     * have, 2<sup>16</sup>-1.
     */
    public static final int MAX_VALUE = 0xffff;

    /**
     * The value modelling the content of this <code>unsigned short</code>
     */
    public short value;

    public UShort() {
        value = 0;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @param value
     */
    public UShort(byte value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @param value
     */
    public UShort(short value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @param value
     */
    public UShort(int value) {
        this.value = (short) value;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @param value
     */
    public UShort(long value) {
        this.value = (short) value;
    }

    /**
     * Create an <code>unsigned short</code>
     *
     * @param value
     */
    public UShort(BigInteger value) {
        this.value = value.shortValue();
    }

    /**
     * Create an <code>unsigned short</code>
     *
     */
    private UShort(String value) {
        this.value = Short.parseShort(value);
    }

    public UShort(UShort uShort) {
        this.value = uShort.value;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UShort) {
            return value == ((UShort) obj).value;
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public int compareTo(UShort o) {
        int a = value, b = o.value;
        return a < b ? -1 : (a == b ? 0 : 1);
    }
    
    public int intValue() {
        return value & 0xffff;
    }
    
    public long longValue() {
        return value & 0xffff;
    }

    /**
     * Throw exception if value out of range (short version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static short checkSigned(byte value) throws ArithmeticException {
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
    public static short checkSigned(short value) throws ArithmeticException {
        if (value < 0) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value;
    }

    /**
     * Throw exception if value out of range (int version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static short checkSigned(int value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (short) value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static short checkSigned(long value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (short) value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static short checkSigned(BigInteger value) throws ArithmeticException {
        if (value.compareTo(BigInteger.ZERO) < 0 || value.intValue() > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value.shortValue();
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static short checkSigned(String value) throws ArithmeticException {
        if (value.startsWith("-")) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return Short.parseShort(value);
    }
}
