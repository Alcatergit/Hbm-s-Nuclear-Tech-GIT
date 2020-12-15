/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.vec._2.Vec2;
import glmath.glm.vec._2.b.Vec2b;
import glmath.glm.vec._2.bool.Vec2bool;
import glmath.glm.vec._2.d.Vec2d;
import glmath.glm.vec._2.i.Vec2i;
import glmath.glm.vec._2.l.Vec2l;
import glmath.glm.vec._2.s.Vec2s;
import glmath.glm.vec._2.ub.Vec2ub;
import glmath.glm.vec._2.ui.Vec2ui;
import glmath.glm.vec._2.ul.Vec2ul;
import glmath.glm.vec._2.us.Vec2us;
import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._3.b.Vec3b;
import glmath.glm.vec._3.bool.Vec3bool;
import glmath.glm.vec._3.d.Vec3d;
import glmath.glm.vec._3.i.Vec3i;
import glmath.glm.vec._3.l.Vec3l;
import glmath.glm.vec._3.s.Vec3s;
import glmath.glm.vec._3.ub.Vec3ub;
import glmath.glm.vec._3.ui.Vec3ui;
import glmath.glm.vec._3.ul.Vec3ul;
import glmath.glm.vec._3.us.Vec3us;
import glmath.glm.vec._4.Vec4;
import glmath.glm.vec._4.b.Vec4b;
import glmath.glm.vec._4.bool.Vec4bool;
import glmath.glm.vec._4.d.Vec4d;
import glmath.glm.vec._4.i.Vec4i;
import glmath.glm.vec._4.l.Vec4l;
import glmath.glm.vec._4.s.Vec4s;
import glmath.glm.vec._4.ub.Vec4ub;
import glmath.glm.vec._4.ui.Vec4ui;
import glmath.glm.vec._4.ul.Vec4ul;
import glmath.glm.vec._4.us.Vec4us;

/**
 *
 * @author GBarbieri
 */
abstract class BooleansOperators {

    public static boolean equals(Vec2 a, Vec2 b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2b a, Vec2b b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2bool a, Vec2bool b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2d a, Vec2d b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2i a, Vec2i b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2l a, Vec2l b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2s a, Vec2s b) {
        return a.x == b.x & a.y == b.y;
    }

    public static boolean equals(Vec2ub a, Vec2ub b) {
        return a.x.equals(b.x) & a.y.equals(b.y);
    }

    public static boolean equals(Vec2ui a, Vec2ui b) {
        return a.x.equals(b.x) & a.y.equals(b.y);
    }

    public static boolean equals(Vec2ul a, Vec2ul b) {
        return a.x.equals(b.x) & a.y.equals(b.y);
    }

    public static boolean equals(Vec2us a, Vec2us b) {
        return a.x.equals(b.x) & a.y.equals(b.y);
    }

    public static boolean equals(Vec3 a, Vec3 b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3b a, Vec3b b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3bool a, Vec3bool b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3d a, Vec3d b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3i a, Vec3i b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3l a, Vec3l b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3s a, Vec3s b) {
        return a.x == b.x & a.y == b.y & a.z == b.z;
    }

    public static boolean equals(Vec3ub a, Vec3ub b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z);
    }

    public static boolean equals(Vec3ui a, Vec3ui b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z);
    }

    public static boolean equals(Vec3ul a, Vec3ul b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z);
    }

    public static boolean equals(Vec3us a, Vec3us b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z);
    }

    public static boolean equals(Vec4 a, Vec4 b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4b a, Vec4b b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4bool a, Vec4bool b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4d a, Vec4d b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4i a, Vec4i b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4l a, Vec4l b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4s a, Vec4s b) {
        return a.x == b.x & a.y == b.y & a.z == b.z & a.w == b.w;
    }

    public static boolean equals(Vec4ub a, Vec4ub b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z) & a.w.equals(b.w);
    }

    public static boolean equals(Vec4ui a, Vec4ui b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z) & a.w.equals(b.w);
    }

    public static boolean equals(Vec4ul a, Vec4ul b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z) & a.w.equals(b.w);
    }

    public static boolean equals(Vec4us a, Vec4us b) {
        return a.x.equals(b.x) & a.y.equals(b.y) & a.z.equals(b.z) & a.w.equals(b.w);
    }

    public static boolean notEquals(Vec2 a, Vec2 b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2b a, Vec2b b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2bool a, Vec2bool b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2d a, Vec2d b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2i a, Vec2i b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2l a, Vec2l b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2s a, Vec2s b) {
        return a.x != b.x | a.y != b.y;
    }

    public static boolean notEquals(Vec2ub a, Vec2ub b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y);
    }

    public static boolean notEquals(Vec2ui a, Vec2ui b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y);
    }

    public static boolean notEquals(Vec2ul a, Vec2ul b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y);
    }

    public static boolean notEquals(Vec2us a, Vec2us b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y);
    }

    public static boolean notEquals(Vec3 a, Vec3 b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3b a, Vec3b b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3bool a, Vec3bool b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3d a, Vec3d b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3i a, Vec3i b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3l a, Vec3l b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3s a, Vec3s b) {
        return a.x != b.x | a.y != b.y | a.z != b.z;
    }

    public static boolean notEquals(Vec3ub a, Vec3ub b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z);
    }

    public static boolean notEquals(Vec3ui a, Vec3ui b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z);
    }

    public static boolean notEquals(Vec3ul a, Vec3ul b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z);
    }

    public static boolean notEquals(Vec3us a, Vec3us b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z);
    }

    public static boolean notEquals(Vec4 a, Vec4 b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4b a, Vec4b b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4bool a, Vec4bool b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4d a, Vec4d b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4i a, Vec4i b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4l a, Vec4l b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4s a, Vec4s b) {
        return a.x != b.x | a.y != b.y | a.z != b.z | a.w != b.w;
    }

    public static boolean notEquals(Vec4ub a, Vec4ub b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z) | !a.w.equals(b.w);
    }

    public static boolean notEquals(Vec4ui a, Vec4ui b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z) | !a.w.equals(b.w);
    }

    public static boolean notEquals(Vec4ul a, Vec4ul b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z) | !a.w.equals(b.w);
    }

    public static boolean notEquals(Vec4us a, Vec4us b) {
        return !a.x.equals(b.x) | !a.y.equals(b.y) | !a.z.equals(b.z) | !a.w.equals(b.w);
    }

    public static Vec2bool and(Vec2bool a, Vec2bool b) {
        return and(new Vec2bool(), a, b);
    }

    public static Vec2bool and(Vec2bool res, Vec2bool a, Vec2bool b) {
        res.x = a.x & b.x;
        res.y = a.y & b.y;
        return res;
    }

    public static Vec3bool and(Vec3bool a, Vec3bool b) {
        return and(new Vec3bool(), a, b);
    }

    public static Vec3bool and(Vec3bool res, Vec3bool a, Vec3bool b) {
        res.x = a.x & b.x;
        res.y = a.y & b.y;
        res.z = a.z & b.z;
        return res;
    }

    public static Vec4bool and(Vec4bool a, Vec4bool b) {
        return and(new Vec4bool(), a, b);
    }

    public static Vec4bool and(Vec4bool res, Vec4bool a, Vec4bool b) {
        res.x = a.x & b.x;
        res.y = a.y & b.y;
        res.z = a.z & b.z;
        res.w = a.w & b.w;
        return res;
    }

    public static Vec2bool or(Vec2bool a, Vec2bool b) {
        return or(new Vec2bool(), a, b);
    }

    public static Vec2bool or(Vec2bool res, Vec2bool a, Vec2bool b) {
        res.x = a.x | b.x;
        res.y = a.y | b.y;
        return res;
    }

    public static Vec3bool or(Vec3bool a, Vec3bool b) {
        return or(new Vec3bool(), a, b);
    }

    public static Vec3bool or(Vec3bool res, Vec3bool a, Vec3bool b) {
        res.x = a.x | b.x;
        res.y = a.y | b.y;
        res.z = a.z | b.z;
        return res;
    }

    public static Vec4bool or(Vec4bool a, Vec4bool b) {
        return or(new Vec4bool(), a, b);
    }

    public static Vec4bool or(Vec4bool res, Vec4bool a, Vec4bool b) {
        res.x = a.x | b.x;
        res.y = a.y | b.y;
        res.z = a.z | b.z;
        res.w = a.w | b.w;
        return res;
    }
}
