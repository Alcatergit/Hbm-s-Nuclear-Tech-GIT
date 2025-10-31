/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.mat._3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import glmath.glm.mat._4.Mat4;
import glmath.glm.quat.Quat;
import glmath.glm.vec._2.Vec2;
import glmath.glm.vec._3.Vec3;

/**
 *
 * @author GBarbieri
 */
public class Mat3 extends matrixQuery {

    public Mat3() {
        this(1.0f);
    }

    public Mat3(float f) {
        this(
                f, 0, 0,
                0, f, 0,
                0, 0, f);
    }

    public Mat3(Vec3 v) {
        this(
                v.x, 0, 0,
                0, v.y, 0,
                0, 0, v.z);
    }   

    public Mat3(Mat3 mat) {
        this(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22);
    }

    public Mat3(Mat4 mat) {
        this(
                mat.m00, mat.m01, mat.m02,
                mat.m10, mat.m11, mat.m12,
                mat.m20, mat.m21, mat.m22);
    }

    public Mat3(float[] f) {
        this(f, 0);
    }

    public Mat3(float[] f, int offset) {
        this(
                f[offset + 0], f[offset + 1], f[offset + 2],
                f[offset + 3], f[offset + 4], f[offset + 5],
                f[offset + 6], f[offset + 7], f[offset + 8]);
    }

    public Mat3(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        this(
                (float) m00, (float) m01, (float) m02,
                (float) m10, (float) m11, (float) m12,
                (float) m20, (float) m21, (float) m22);
    }

    public Mat3(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public static Mat3 cast(Quat q) {
        Mat3 result = new Mat3();
        result.m00 = 1 - 2 * q.y * q.y - 2 * q.z * q.z;
        result.m01 = 2 * q.x * q.y + 2 * q.w * q.z;
        result.m02 = 2 * q.x * q.z - 2 * q.w * q.y;

        result.m10 = 2 * q.x * q.y - 2 * q.w * q.z;
        result.m11 = 1 - 2 * q.x * q.x - 2 * q.z * q.z;
        result.m12 = 2 * q.y * q.z + 2 * q.w * q.x;

        result.m20 = 2 * q.x * q.z + 2 * q.w * q.y;
        result.m21 = 2 * q.y * q.z - 2 * q.w * q.x;
        result.m22 = 1 - 2 * q.x * q.x - 2 * q.y * q.y;
        return result;
    }

    public Mat3 set() {
        return set(1.0f);
    }

    public Mat3 set(float f) {
        return set(f, 0, 0, 0, f, 0, 0, 0, f);
    }

    public Mat3 set(Mat3 m) {
        return set(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public Mat3 set(Mat4 m) {
        return set(m.m00, m.m01, m.m02, m.m10, m.m11, m.m12, m.m20, m.m21, m.m22);
    }

    public Mat3 set(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        return this;
    }

    public Mat3 set(int column, int row, float value) {

        if (column >= 0 && column < 3 && row >= 0 && row < 3) {

            float[] f = this.toFa_();
            f[(column * 3) + row] = value;
            this.set(f);

        }

        return this;
    }

    public Mat3 set(float[] f) {
        return set(f, 0);
    }

    public Mat3 set(float[] f, int offset) {
        set(
                f[offset + 0], f[offset + 1], f[offset + 2],
                f[offset + 3], f[offset + 4], f[offset + 5],
                f[offset + 6], f[offset + 7], f[offset + 8]);

        return this;
    }

    public Mat3 c0(Vec3 v) {
        return set(v.x, v.y, v.z, m10, m11, m12, m20, m21, m22);
    }

    public Mat3 c0(Vec2 v, float f) {
        return set(v.x, v.y, f, m10, m11, m12, m20, m21, m22);
    }

    public Mat3 c0(float x, float y, float z) {
        return set(x, y, z, m10, m11, m12, m20, m21, m22);
    }

    public Mat3 c1(Vec3 v) {
        return set(m00, m01, m02, v.x, v.y, v.z, m20, m21, m22);
    }

    public Mat3 c1(Vec2 v, float f) {
        return set(m00, m01, m02, v.x, v.y, f, m20, m21, m22);
    }

    public Mat3 c1(float x, float y, float z) {
        return set(m00, m01, m02, x, y, z, m20, m21, m22);
    }

    public Mat3 c2(Vec3 v) {
        return set(m00, m01, m02, m10, m11, m12, v.x, v.y, v.z);
    }

    public Mat3 c2(Vec2 v, float f) {
        return set(m00, m01, m02, m10, m11, m12, v.x, v.y, f);
    }

    public Mat3 c2(float x, float y, float z) {
        return set(m00, m01, m02, m10, m11, m12, x, y, z);
    }

    public Mat3 identity() {
        return set();
    }

    public Vec3 mul(Vec3 v) {
        return mul(v, new Vec3());
    }
    public Vec3 mul_(Vec3 v) {
        return mul(v, v);
    }
    
    
    public Vec3 mul(Vec3 right, Vec3 res) {
        res.set(m00 * right.x + m10 * right.y + m20 * right.z,
                m01 * right.x + m11 * right.y + m21 * right.z,
                m02 * right.x + m12 * right.y + m22 * right.z
        );
        return res;
    }

    public Mat3 mul_(Mat3 right) {
        return mul(right, new Mat3());
    }

    public Mat3 mul(Mat3 right) {
        return mul(right, this);
    }

    public Mat3 mul(Mat3 right, Mat3 dest) {
        dest.set(
                m00 * right.m00 + m10 * right.m01 + m20 * right.m02,
                m01 * right.m00 + m11 * right.m01 + m21 * right.m02,
                m02 * right.m00 + m12 * right.m01 + m22 * right.m02,
                m00 * right.m10 + m10 * right.m11 + m20 * right.m12,
                m01 * right.m10 + m11 * right.m11 + m21 * right.m12,
                m02 * right.m10 + m12 * right.m11 + m22 * right.m12,
                m00 * right.m20 + m10 * right.m21 + m20 * right.m22,
                m01 * right.m20 + m11 * right.m21 + m21 * right.m22,
                m02 * right.m20 + m12 * right.m21 + m22 * right.m22
        );
        return dest;
    }

    public boolean equals(Mat3 other) {
        return equals(other, 2);
    }

    public boolean equals(Mat3 other, int maxUlps) {
        if (!compareFloatEquals(m00, other.m00, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m01, other.m01, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m02, other.m02, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m10, other.m10, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m11, other.m11, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m12, other.m12, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m20, other.m20, maxUlps)) {
            return false;
        }
        if (!compareFloatEquals(m21, other.m21, maxUlps)) {
            return false;
        }
        return compareFloatEquals(m22, other.m22, maxUlps);
    }

    /**
     * Compare two floating points for equality within a margin of error.
     *
     * This can be used to compensate for inequality caused by accumulated
     * floating point math errors.
     *
     * The error margin is specified in ULPs (units of least precision). A
     * one-ULP difference means there are no representable floats in between.
     * E.g. 0f and 1.4e-45f are one ULP apart. So are -6.1340704f and -6.13407f.
     * Depending on the number of calculations involved, typically a margin of
     * 1-5 ULPs should be enough.
     *
     * @param expected The expected value.
     * @param actual The actual value.
     * @param maxUlps The maximum difference in ULPs.
     * @return
     */
    public static boolean compareFloatEquals(float expected, float actual, int maxUlps) {
        int expectedBits = Float.floatToIntBits(expected) < 0 ? 0x80000000 - Float.floatToIntBits(expected) : Float.floatToIntBits(expected);
        int actualBits = Float.floatToIntBits(actual) < 0 ? 0x80000000 - Float.floatToIntBits(actual) : Float.floatToIntBits(actual);
        int difference = expectedBits > actualBits ? expectedBits - actualBits : actualBits - expectedBits;
//        if (difference > maxUlps) {
        System.out.println("expected: " + expected + ", actual: " + actual);
        System.out.println("diff " + difference);
//        }
        return !Float.isNaN(expected) && !Float.isNaN(actual) && difference <= maxUlps;
    }
    
    public Mat4 toMat4_() {
        return toMat4(new Mat4());
    }

    public Mat4 toMat4(Mat4 res) {
        return res.set(
                m00, m01, m02, 0, 
                m10, m11, m12, 0,
                m20, m21, m22, 0,
                0, 0, 0, 1);
    }

    public float[] toFa_() {
        return Mat3.this.toFa(new float[9], 0);
    }

    public float[] toFa(float[] res) {
        return Mat3.this.toFa(res, 0);
    }

    public float[] toFa(float[] res, int index) {
        res[index + 0] = m00;
        res[index + 1] = m01;
        res[index + 2] = m02;
        res[index + 3] = m10;
        res[index + 4] = m11;
        res[index + 5] = m12;
        res[index + 6] = m20;
        res[index + 7] = m21;
        res[index + 8] = m22;
        return res;
    }

    public ByteBuffer toDbb_() {
        return toDbb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()));
    }

    public ByteBuffer toDbb(ByteBuffer res) {
        return toDbb(res, 0);
    }

    public ByteBuffer toDbb(ByteBuffer res, int index) {
        res.putFloat(index + 0 * Float.BYTES, m00);
        res.putFloat(index + 1 * Float.BYTES, m01);
        res.putFloat(index + 2 * Float.BYTES, m02);
        res.putFloat(index + 3 * Float.BYTES, m10);
        res.putFloat(index + 4 * Float.BYTES, m11);
        res.putFloat(index + 5 * Float.BYTES, m12);
        res.putFloat(index + 6 * Float.BYTES, m20);
        res.putFloat(index + 7 * Float.BYTES, m21);
        res.putFloat(index + 8 * Float.BYTES, m22);
        return res;
    }

    public FloatBuffer toDfb_() {
        return toDfb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer());
    }

    public FloatBuffer toDfb(FloatBuffer res) {
        return toDfb(res, 0);
    }

    public FloatBuffer toDfb(FloatBuffer res, int index) {
        res.put(index + 0, m00);
        res.put(index + 1, m01);
        res.put(index + 2, m02);
        res.put(index + 3, m10);
        res.put(index + 4, m11);
        res.put(index + 5, m12);
        res.put(index + 6, m20);
        res.put(index + 7, m21);
        res.put(index + 8, m22);
        return res;
    }

    public void print() {
        print("", true);
    }

    public void print(String title) {
        print(title, true);
    }

    public void print(boolean outStream) {
        print("", outStream);
    }

    public void print(String title, boolean outStream) {
        String res = title + "\n"
                + "| " + m00 + " " + m10 + " " + m20 + " |\n"
                + "| " + m01 + " " + m11 + " " + m21 + " |\n"
                + "| " + m02 + " " + m12 + " " + m22 + " |\n";
        if (outStream) {
            System.out.print(res);
        } else {
            System.err.print(res);
        }
    }
}
