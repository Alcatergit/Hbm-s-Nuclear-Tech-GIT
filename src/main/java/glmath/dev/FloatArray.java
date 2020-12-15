/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.dev;

/**
 *
 * @author GBarbieri
 */
public class FloatArray {

    public static final int M00 = 0, M10 = 4, M20 = 8, M30 = 12;
    public static final int M01 = 1, M11 = 5, M21 = 9, M31 = 13;
    public static final int M02 = 2, M12 = 6, M22 = 10, M32 = 14;
    public static final int M03 = 3, M13 = 7, M23 = 11, M33 = 15;
    public static final int COUNT = 16;

    public static float[] perspective_(float fovy, float aspect, float zNear, float zFar) {
        return perspective(fovy, aspect, zNear, zFar, new float[COUNT]);
    }

    public static float[] perspective(float fovy, float aspect, float zNear, float zFar, float[] res) {
        float h = (float) Math.tan(fovy * 0.5f) * zNear;
        float w = h * aspect;
        res[M00] = zNear / w;
        res[M01] = 0.0f;
        res[M02] = 0.0f;
        res[M03] = 0.0f;
        res[M10] = 0.0f;
        res[M11] = zNear / h;
        res[M12] = 0.0f;
        res[M13] = 0.0f;
        res[M20] = 0.0f;
        res[M21] = 0.0f;
        res[M22] = -(zFar + zNear) / (zFar - zNear);
        res[M23] = -1.0f;
        res[M30] = 0.0f;
        res[M31] = 0.0f;
        res[M32] = -2.0f * zFar * zNear / (zFar - zNear);
        res[M33] = 0.0f;
        return res;
    }
}
