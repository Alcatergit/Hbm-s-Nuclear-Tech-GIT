/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4;

/**
 *
 * @author elect
 */
abstract class funcGeometric extends funcCommon {

    public float dot(Vec4 y) {
        return dot((Vec4) this, y);
    }

    public static float dot(Vec4 x, Vec4 y) {
        float tX = x.x * y.x;
        float tY = x.y * y.y;
        float tZ = x.z * y.z;
        float tW = x.w * y.w;
        return tX + tY + tZ + tW;
    }

    /**
     * Normalize this vector.
     *
     * @return this
     */
    public Vec4 normalize() {
        return normalize((Vec4) this);
    }

    /**
     * Normalize this vector and store the result in <code>res</code>.
     *
     * @param res will hold the result
     * @return
     */
    public Vec4 normalize(Vec4 res) {
        float invLength = (float) (1.0 / Math.sqrt(x * x + y * y + z * z + w * w));
        res.x = x * invLength;
        res.y = y * invLength;
        res.z = z * invLength;
        res.w = w * invLength;
        return res;
    }
}
