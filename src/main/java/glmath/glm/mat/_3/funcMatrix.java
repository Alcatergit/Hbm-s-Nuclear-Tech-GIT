/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.mat._3;

/**
 *
 * @author GBarbieri
 */
abstract class funcMatrix {

    public float m00, m10, m20;
    public float m01, m11, m21;
    public float m02, m12, m22;

    public static final int SIZE = 3 * 3 * Float.BYTES;

    public float det() {
        return (m00 * m11 - m01 * m10) * m22 + (m02 * m10 - m00 * m12) * m21 + (m01 * m12 - m02 * m11) * m20;
    }

    public Mat3 inverse() {
        return inverse((Mat3) this);
    }

    public Mat3 inverse_() {
        return inverse(new Mat3());
    }

    /**
     * Matrix is supposed to be invertible
     *
     * @param res
     * @return
     */
    public Mat3 inverse(Mat3 res) {
        float s = 1.0f / det();
        res.set((m11 * m22 - m21 * m12) * s,
                (m21 * m02 - m01 * m22) * s,
                (m01 * m12 - m11 * m02) * s,
                (m20 * m12 - m10 * m22) * s,
                (m00 * m22 - m20 * m02) * s,
                (m10 * m02 - m00 * m12) * s,
                (m10 * m21 - m20 * m11) * s,
                (m20 * m01 - m00 * m21) * s,
                (m00 * m11 - m10 * m01) * s);
        return res;
    }

    public Mat3 invTransp() {
        return invTransp((Mat3) this);
    }

    public Mat3 invTransp_() {
        return invTransp(new Mat3());
    }

    public Mat3 invTransp(Mat3 res) {
        float s = 1.0f / det();
        /* Invert and transpose in one go */
        res.set(
                (m11 * m22 - m21 * m12) * s,
                (m20 * m12 - m10 * m22) * s,
                (m10 * m21 - m20 * m11) * s,
                (m21 * m02 - m01 * m22) * s,
                (m00 * m22 - m20 * m02) * s,
                (m20 * m01 - m00 * m21) * s,
                (m01 * m12 - m11 * m02) * s,
                (m10 * m02 - m00 * m12) * s,
                (m00 * m11 - m10 * m01) * s);
        return res;
    }

    public Mat3 transpose_() {
        return transpose(new Mat3());
    }

    public Mat3 transpose() {
        return transpose((Mat3) this);
    }

    public Mat3 transpose(Mat3 dest) {
        return transpose((Mat3) this, dest);
    }

    public static Mat3 transpose(Mat3 mat, Mat3 dest) {
        dest.set(
                mat.m00, mat.m10, mat.m20,
                mat.m01, mat.m11, mat.m21,
                mat.m02, mat.m12, mat.m22);
        return dest;
    }
}
