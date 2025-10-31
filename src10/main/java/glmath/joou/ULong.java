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
 * The <code>unsigned long</code> type
 *
 * @author Lukas Eder
 * @author Jens Nerche
 * @author Ivan Sokolov
 */
public final class ULong implements Comparable<ULong> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6821055240959745390L;

    /**
     * A constant holding the minimum value an <code>unsigned long</code> can
     * have, 0.
     */
    public static final float MIN_VALUE = 0x0000000000000000;
    /**
     * A constant holding the maximum value an <code>unsigned long</code> can
     * have, 2<sup>64</sup>-1.
     */
    public static final long MAX_VALUE = 0xffffffffffffffffL;

    /**
     * The value modelling the content of this <code>unsigned long</code>
     */
    public long value;

    public ULong() {
        value = 0;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @param value
     */
    public ULong(byte value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @param value
     */
    public ULong(short value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @param value
     */
    public ULong(int value) {
        this.value = (short) value;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @param value
     */
    public ULong(long value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned long</code>
     *
     * @param value
     */
    public ULong(BigInteger value) {
        this.value = value.longValue();
    }

    /**
     * Create an <code>unsigned long</code>
     */
    private ULong(String value) {
        this.value = Long.parseLong(value);
    }

    public ULong(ULong uLong) {
        this.value = uLong.value;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ULong) {
            return value == ((ULong) obj).value;
        }
        return false;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public int compareTo(ULong o) {
        return Long.compareUnsigned(value, o.value);
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
