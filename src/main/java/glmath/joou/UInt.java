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
 * The <code>unsigned int</code> type
 *
 * @author Lukas Eder
 * @author Ed Schaller
 * @author Jens Nerche
 */
public final class UInt extends UNumber implements Comparable<UInt> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6821055240959745390L;

    /**
     * A constant holding the minimum value an <code>unsigned int</code> can
     * have, 0.
     */
    public static final int MIN_VALUE = 0x00000000;

    /**
     * A constant holding the maximum value an <code>unsigned int</code> can
     * have, 2<sup>32</sup>-1.
     */
    public static final int MAX_VALUE = 0xffffffff;

    /**
     * The value modelling the content of this <code>unsigned int</code>
     */
    public int value;

    public UInt() {
        value = 0;
    }

    /**
     * Create an <code>unsigned int</code>
     *
     * @param value
     */
    public UInt(byte value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned int</code>
     *
     * @param value
     */
    public UInt(short value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned int</code>.
     */
    public UInt(int value) {
        this.value = value;
    }

    /**
     * Create an <code>unsigned int</code>
     */
    public UInt(long value) {
        this.value = (int) value;
    }

    /**
     * Create an <code>unsigned int</code>
     */
    public UInt(BigInteger value) {
        this.value = value.intValue();
    }

    /**
     * Create an <code>unsigned int</code> /code>
     */
    public UInt(String value) {
        this.value = Integer.parseInt(value);
    }

    public UInt(UInt uInteger) {
        this.value = uInteger.value;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UInt) {
            return value == ((UInt) obj).value;
        }

        return false;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public int compareTo(UInt o) {
        return Integer.compareUnsigned(value, o.value);
    }
    
    public long longValue() {
        return value & 0xffffffffL;
    }

    /**
     * Throw exception if value out of range (short version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static int checkSigned(byte value) throws ArithmeticException {
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
    public static int checkSigned(short value) throws ArithmeticException {
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
    public static int checkSigned(int value) throws ArithmeticException {
        if (value < 0) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static int checkSigned(long value) throws ArithmeticException {
        if (value < 0 || value > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return (int) value;
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static int checkSigned(BigInteger value) throws ArithmeticException {
        if (value.compareTo(BigInteger.ZERO) < 0 || value.longValue() > MAX_VALUE) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return value.intValue();
    }

    /**
     * Throw exception if value out of range (long version)
     *
     * @param value Value to check
     * @return value if it is in range
     * @throws ArithmeticException if value is out of range
     */
    public static int checkSigned(String value) throws ArithmeticException {
        if (value.startsWith("-")) {
            throw new ArithmeticException("Value is out of range : " + value);
        }
        return Integer.parseInt(value);
    }
}
