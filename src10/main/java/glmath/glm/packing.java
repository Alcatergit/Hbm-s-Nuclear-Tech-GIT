/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;

/**
 *
 * @author elect
 */
abstract class packing extends funcGeometric {

    public static int packSnorm3x10_1x2(Vec4 v) {
        int[] result = new int[4];
        result[0] = (int) (Math.max(-1, Math.min(1, v.x)) * 511.f);
        result[1] = (int) (Math.max(-1, Math.min(1, v.y)) * 511.f);
        result[2] = (int) (Math.max(-1, Math.min(1, v.z)) * 511.f);
        result[3] = (int) (Math.max(-1, Math.min(1, v.w)) * 1.f);
        result[0] = (result[0] << 22) >>> 22;
        result[1] = (result[1] << 22) >>> 12;
        result[2] = (result[2] << 22) >>> 2;
        result[3] = (result[3] << 30);
        return result[0] | result[1] | result[2] | result[3];
    }

    public static int packF2x11_1x10(Vec3 v) {
        return ((floatTo11bit(v.x) & ((1 << 11) - 1)) << 0)
                | ((floatTo11bit(v.y) & ((1 << 11) - 1)) << 11)
                | ((floatTo10bit(v.z) & ((1 << 10) - 1)) << 22);
    }

    public static int floatTo11bit(float x) {
        if (x == 0f) {
            return 0;
        } else if (Float.isNaN(x)) {
            return ~0;
        } else if (Float.isInfinite(x)) {
            return 0x1f << 6;
        }
        return float2packed11(Float.floatToIntBits(x));
    }

    public static int floatTo10bit(float x) {
        if (x == 0f) {
            return 0;
        } else if (Float.isNaN(x)) {
            return ~0;
        } else if (Float.isInfinite(x)) {
            return 0x1f << 6;
        }
        return float2packed10(Float.floatToIntBits(x));
    }

//    public static void main(String[] args) {
//        System.out.println("Integer.toUnsignedLong(0x000007c0): " + Integer.toUnsignedLong(0x00008000));
////        System.out.println("Integer.toUnsignedString(0x000007c0): " + Integer.toUnsignedString(0x00000x00007c0007c0));
//    }

    public static int float2packed11(int i) {
        // 10 bits    =>                         EE EEEFFFFF
        // 11 bits    =>                        EEE EEFFFFFF
        // Half bits  =>                   SEEEEEFF FFFFFFFF
        // Float bits => SEEEEEEE EFFFFFFF FFFFFFFF FFFFFFFF

        // 0x000007c0 => 00000000 00000000 00000111 11000000
        // 0x00007c00 => 00000000 00000000 01111100 00000000
        // 0x000003ff => 00000000 00000000 00000011 11111111
        // 0x38000000 => 00111000 00000000 00000000 00000000
        // 0x7f800000 => 01111111 10000000 00000000 00000000
        // 0x00008000 => 00000000 00000000 10000000 00000000
        return ((((i & 0x7f800000) - 0x38000000) >> 17) & 0x07c0) // exponential
                | ((i >> 17) & 0x003f); // Mantissa
    }

    public static int float2packed10(int f) {
        // 10 bits    =>                         EE EEEFFFFF
        // 11 bits    =>                        EEE EEFFFFFF
        // Half bits  =>                   SEEEEEFF FFFFFFFF
        // Float bits => SEEEEEEE EFFFFFFF FFFFFFFF FFFFFFFF

        // 0x0000001F => 00000000 00000000 00000000 00011111
        // 0x0000003F => 00000000 00000000 00000000 00111111
        // 0x000003E0 => 00000000 00000000 00000011 11100000
        // 0x000007C0 => 00000000 00000000 00000111 11000000
        // 0x00007C00 => 00000000 00000000 01111100 00000000
        // 0x000003FF => 00000000 00000000 00000011 11111111
        // 0x38000000 => 00111000 00000000 00000000 00000000
        // 0x7f800000 => 01111111 10000000 00000000 00000000
        // 0x00008000 => 00000000 00000000 10000000 00000000
        return ((((f & 0x7f800000) - 0x38000000) >> 18) & 0x03E0) // exponential
                | ((f >> 18) & 0x001f); // Mantissa
    }

    public static short packHalf1x16(float f) {
        return toFloat16(f);
    }

    private static short toFloat16(float f) {

        int i = Float.floatToRawIntBits(f);

        //
        // Our floating point number, f, is represented by the bit
        // pattern in integer i.  Disassemble that bit pattern into
        // the sign, s, the exponent, e, and the significand, m.
        // Shift s into the position where it will go in in the
        // resulting half number.
        // Adjust e, accounting for the different exponent bias
        // of float and half (127 versus 15).
        //
        int s = (i >> 16) & 0x00008000;
        int e = ((i >> 23) & 0x000000ff) - (127 - 15);
        int m = i & 0x007fffff;

        //
        // Now reassemble s, e and m into a half:
        //
        if (e <= 0) {

            if (e < -10) {
                //
                // E is less than -10.  The absolute value of f is
                // less than half_MIN (f may be a small normalized
                // float, a denormalized float or a zero).
                //
                // We convert f to a half zero.
                //

                return (short) s;
            }

            //
            // E is between -10 and 0.  F is a normalized float,
            // whose magnitude is less than __half_NRM_MIN.
            //
            // We convert f to a denormalized half.
            // 
            m = (m | 0x00800000) >> (1 - e);

            //
            // Round to nearest, round "0.5" up.
            //
            // Rounding may cause the significand to overflow and make
            // our number normalized.  Because of the way a half's bits
            // are laid out, we don't have to treat this case separately;
            // the code below will handle it correctly.
            // 
            if ((m & 0x00001000) != 0) {
                m += 0x00002000;
            }

            //
            // Assemble the half from s, e (zero) and m.
            //
            return (short) (s | (m >> 13));

        } else if (e == 0xff - (127 - 15)) {

            if (m == 0) {
                //
                // F is an infinity; convert f to a half
                // infinity with the same sign as f.
                //

                return (short) (s | 0x7c00);

            } else {
                //
                // F is a NAN; we produce a half NAN that preserves
                // the sign bit and the 10 leftmost bits of the
                // significand of f, with one exception: If the 10
                // leftmost bits are all zero, the NAN would turn 
                // into an infinity, so we have to set at least one
                // bit in the significand.
                //

                m >>= 13;

                return (short) (s | 0x7c00 | m | (m == 0 ? 1 : 0));
            }
        } else {
            //
            // E is greater than zero.  F is a normalized float.
            // We try to convert f to a normalized half.
            //

            //
            // Round to nearest, round "0.5" up
            //
            if ((m & 0x00001000) != 0) {
                m += 0x00002000;

                if ((m & 0x00800000) != 0) {
                    m = 0;     // overflow in significand,
                    e += 1;     // adjust exponent
                }
            }

            //
            // Handle exponent overflow
            //
            if (e > 30) {
                overflow();        // Cause a hardware floating point overflow;

                return (short) (s | 0x7c00);
                // if this returns, the half becomes an
            }   // infinity with the same sign as f.

            //
            // Assemble the half from s, e and m.
            //
            return (short) (s | (e << 10) | (m >> 13));
        }
    }

    public static float overflow() {
        float f = 1.0e10f;

        for (int i = 0; i < 10; i++) {
            f *= f;  // this will overflow before the for loop terminates
        }
        return f;
    }
}
