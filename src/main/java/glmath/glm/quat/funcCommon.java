/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.quat;

import glmath.glm.vec._3.Vec3;

/**
 *
 * @author elect
 */
abstract class funcCommon {

    public static final int SIZE = 4 * Float.BYTES;

    public float w, x, y, z;

    public Quat add(Quat y) {
        return add(y, (Quat) this);
    }

    public Quat add_(Quat y) {
        return add(y, new Quat());
    }

    public Quat add(Quat y, Quat res) {
        return add((Quat) this, y, res);
    }

    public static Quat add(Quat x, Quat y, Quat res) {
        res.w = x.w + y.w;
        res.x = x.x + y.x;
        res.y = x.y + y.y;
        res.z = x.z + y.z;
        return res;
    }

    public Quat mul_(Quat q) {
        return mul(q, new Quat());
    }

    public Quat mul(Quat q) {
        return mul(q, (Quat) this);
    }

    public Quat mul(Quat q, Quat res) {
        return Quat.mul((Quat) this, q, res);
    }

    public static Quat mul(Quat x, Quat y, Quat res) {
        return res.set(
                x.w * y.w - x.x * y.x - x.y * y.y - x.z * y.z,
                x.w * y.x + x.x * y.w + x.y * y.z - x.z * y.y,
                x.w * y.y + x.y * y.w + x.z * y.x - x.x * y.z,
                x.w * y.z + x.z * y.w + x.x * y.y - x.y * y.x);
    }

    public Vec3 mul(Vec3 v) {
        return Quat.mul((Quat) this, v, v);
    }

    public Vec3 mul_(Vec3 v) {
        return Quat.mul((Quat) this, v, new Vec3());
    }

    public Vec3 mul(Vec3 v, Vec3 res) {
        return Quat.mul((Quat) this, v, res);
    }

    public static Vec3 mul(Quat q, Vec3 v, Vec3 res) {
        float n0 = q.x + q.x;
        float n1 = q.y + q.y;
        float n2 = q.z + q.z;
        float n3 = q.x * n0;
        float n4 = q.y * n1;
        float n5 = q.z * n2;
        float n6 = q.x * n1;
        float n7 = q.x * n2;
        float n8 = q.y * n2;
        float n9 = q.w * n0;
        float n10 = q.w * n1;
        float n11 = q.w * n2;
        res.set((1.0f - (n4 + n5)) * v.x + (n6 - n11) * v.y + (n7 + n10) * v.z,
                (n6 + n11) * v.x + (1.0f - (n3 + n5)) * v.y + (n8 - n9) * v.z,
                (n7 - n10) * v.x + (n8 + n9) * v.y + (1.0f - (n3 + n4)) * v.z);
        return res;
    }

    public Quat mul(double d) {
        return mul((float) d, (Quat) this);
    }

    public Quat mul_(double d) {
        return mul((float) d, new Quat());
    }

    public Quat mul(float f) {
        return mul(f, (Quat) this);
    }

    public Quat mul_(float f) {
        return mul(f, new Quat());
    }

    public Quat mul(float f, Quat res) {
        return Quat.mul((Quat) this, f, res);
    }

    public static Quat mul(Quat x, float y, Quat res) {
        return res.set(
                x.w * y,
                x.x * y,
                x.y * y,
                x.z * y);
    }

    public Quat sub(Quat y) {
        return sub(y, (Quat) this);
    }

    public Quat sub_(Quat y) {
        return sub(y, new Quat());
    }

    public Quat sub(Quat y, Quat res) {
        return sub((Quat) this, y, res);
    }

    public static Quat sub(Quat x, Quat y, Quat res) {
        res.w = x.w - y.w;
        res.x = x.x - y.x;
        res.y = x.y - y.y;
        res.z = x.z - y.z;
        return res;
    }
}
