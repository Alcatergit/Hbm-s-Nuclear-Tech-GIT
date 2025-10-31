/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.mat._4;

/**
 *
 * @author GBarbieri
 */
abstract class funcMatrix {

    public float m00, m10, m20, m30;
    public float m01, m11, m21, m31;
    public float m02, m12, m22, m32;
    public float m03, m13, m23, m33;

    public static final int SIZE = 4 * 4 * Float.BYTES;

    public Mat4 inverse() {
        return inverse((Mat4) this);
    }
    
    public Mat4 inverse_() {
        return inverse(new Mat4());
    }

    public Mat4 inverse(Mat4 dest) {
        float a = m00 * m11 - m01 * m10;
        float b = m00 * m12 - m02 * m10;
        float c = m00 * m13 - m03 * m10;
        float d = m01 * m12 - m02 * m11;
        float e = m01 * m13 - m03 * m11;
        float f = m02 * m13 - m03 * m12;
        float g = m20 * m31 - m21 * m30;
        float h = m20 * m32 - m22 * m30;
        float i = m20 * m33 - m23 * m30;
        float j = m21 * m32 - m22 * m31;
        float k = m21 * m33 - m23 * m31;
        float l = m22 * m33 - m23 * m32;
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        dest.set(
                (+m11 * l - m12 * k + m13 * j) * det,
                (-m01 * l + m02 * k - m03 * j) * det,
                (+m31 * f - m32 * e + m33 * d) * det,
                (-m21 * f + m22 * e - m23 * d) * det,
                (-m10 * l + m12 * i - m13 * h) * det,
                (+m00 * l - m02 * i + m03 * h) * det,
                (-m30 * f + m32 * c - m33 * b) * det,
                (+m20 * f - m22 * c + m23 * b) * det,
                (+m10 * k - m11 * i + m13 * g) * det,
                (-m00 * k + m01 * i - m03 * g) * det,
                (+m30 * e - m31 * c + m33 * a) * det,
                (-m20 * e + m21 * c - m23 * a) * det,
                (-m10 * j + m11 * h - m12 * g) * det,
                (+m00 * j - m01 * h + m02 * g) * det,
                (-m30 * d + m31 * b - m32 * a) * det,
                (+m20 * d - m21 * b + m22 * a) * det);
        return dest;
    }

    public Mat4 invTransp() {
        return invTransp3((Mat4) this);
    }

    public Mat4 invTransp3_() {
        return invTransp3(new Mat4());
    }

    public Mat4 invTransp3(Mat4 dest) {
        float det = det3();
        float s = 1.0f / det;
        /* Invert and transpose in one go */
        dest.set((m11 * m22 - m21 * m12) * s,
                (m20 * m12 - m10 * m22) * s,
                (m10 * m21 - m20 * m11) * s,
                0.0f,
                (m21 * m02 - m01 * m22) * s,
                (m00 * m22 - m20 * m02) * s,
                (m20 * m01 - m00 * m21) * s,
                0.0f,
                (m01 * m12 - m11 * m02) * s,
                (m10 * m02 - m00 * m12) * s,
                (m00 * m11 - m10 * m01) * s,
                0.0f,
                0.0f, 0.0f, 0.0f, 1.0f);
        return dest;
    }

    public Mat4 transpose_() {
        return transpose(new Mat4());
    }

    public Mat4 transpose() {
        return transpose((Mat4) this);
    }

    public Mat4 transpose(Mat4 dest) {
        return transpose((Mat4) this, dest);
    }

    public static Mat4 transpose(Mat4 mat, Mat4 dest) {
        dest.set(mat.m00, mat.m10, mat.m20, mat.m30,
                mat.m01, mat.m11, mat.m21, mat.m31,
                mat.m02, mat.m12, mat.m22, mat.m32,
                mat.m03, mat.m13, mat.m23, mat.m33);
        return dest;
    }

    public float det() {
        return (m00 * m11 - m01 * m10) * (m22 * m33 - m23 * m32)
                + (m02 * m10 - m00 * m12) * (m21 * m33 - m23 * m31)
                + (m00 * m13 - m03 * m10) * (m21 * m32 - m22 * m31)
                + (m01 * m12 - m02 * m11) * (m20 * m33 - m23 * m30)
                + (m03 * m11 - m01 * m13) * (m20 * m32 - m22 * m30)
                + (m02 * m13 - m03 * m12) * (m20 * m31 - m21 * m30);
    }

    public float det3() {
        return (m00 * m11 - m01 * m10) * m22
                + (m02 * m10 - m00 * m12) * m21
                + (m01 * m12 - m02 * m11) * m20;
    }
}
