/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.mat._4.Mat4;
import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
public class Glm extends noise {

    public static int ceilMultiple(int source, int multiple) {
        if (source > 0) {
            float tmp = source - 1;
            return (int) (tmp + (multiple - (tmp % multiple)));
        } else {
            return source + (-source % multiple);
        }
    }

    // http://stackoverflow.com/a/18946610/1047713
    public static double linearRand() {
        return linearRand(0, 1);
    }

    public static double linearRand(double min, double max) {
        return Math.random() * (max + Double.MIN_VALUE) + min;
    }

    public static int linearRand(int min, int max) {
        return (int) (Math.random() * (max + Integer.MIN_VALUE) + min);
    }

    public static Vec3 linearRand(Vec3 min, Vec3 max, Vec3 res) {
        return Vec3.linearRand(min, max, res);
    }

    public static Vec3 linearRand_(Vec3 min, Vec3 max) {
        return Vec3.linearRand_(min, max);
    }

    public static Vec3 project_(Vec3 obj, Mat4 model, Mat4 proj, Vec4 viewport) {
        return project(obj, model, proj, viewport, new Vec3());
    }

    public static Vec3 project(Vec3 obj, Mat4 model, Mat4 proj, Vec4 viewport, Vec3 res) {
        // tvec4<T, P> tmp = tvec4<T, P>(obj, T(1));		tmp = model * tmp;
        float tX = model.m00 * obj.x + model.m10 * obj.y + model.m20 * obj.z + model.m30 * 1f;
        float tY = model.m01 * obj.x + model.m11 * obj.y + model.m21 * obj.z + model.m31 * 1f;
        float tZ = model.m02 * obj.x + model.m12 * obj.y + model.m22 * obj.z + model.m32 * 1f;
        float tW = model.m03 * obj.x + model.m13 * obj.y + model.m23 * obj.z + model.m33 * 1f;
        // tmp = proj * tmp;
        float tmpX = proj.m00 * tX + proj.m10 * tY + proj.m20 * tZ + proj.m30 * tW;
        float tmpY = proj.m01 * tX + proj.m11 * tY + proj.m21 * tZ + proj.m31 * tW;
        float tmpZ = proj.m02 * tX + proj.m12 * tY + proj.m22 * tZ + proj.m32 * tW;
        float tmpW = proj.m03 * tX + proj.m13 * tY + proj.m23 * tZ + proj.m33 * tW;
        // tmp /= tmp.w;        
        tmpX /= tmpW;
        tmpY /= tmpW;
        tmpZ /= tmpW;
//        tmpW = 1f;
        // tmp = tmp * T(0.5) + T(0.5);
        tmpX = tmpX * 0.5f + 0.5f;
        tmpY = tmpY * 0.5f + 0.5f;
        tmpZ = tmpZ * 0.5f + 0.5f;
//        tmpW = tmpW * 0.5f + 0.5f;
        // tmp[0] = tmp[0] * T(viewport[2]) + T(viewport[0]);
        tmpX = tmpX * viewport.z + viewport.x;
        tmpY = tmpY * viewport.w + viewport.y;
        return res.set(tmpX, tmpY, tmpZ);
    }
}
