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
import glmath.joou.UByte;
import glmath.joou.UInt;
import glmath.joou.ULong;
import glmath.joou.UShort;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends BasicOperatorsScalarFirst {

    // any ----------------------------------------------------------------------------------------------------------
    public static boolean any(Vec2 v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2b v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2bool v) {
        return v.x | v.y;
    }

    public static boolean any(Vec2d v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2i v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2l v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2s v) {
        return v.x != 0 | v.y != 0;
    }

    public static boolean any(Vec2ub v) {
        return v.x.value != 0 | v.y.value != 0;
    }

    public static boolean any(Vec2ui v) {
        return v.x.value != 0 | v.y.value != 0;
    }

    public static boolean any(Vec2ul v) {
        return v.x.value != 0 | v.y.value != 0;
    }

    public static boolean any(Vec2us v) {
        return v.x.value != 0 | v.y.value != 0;
    }

    public static boolean any(Vec3 v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3b v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3bool v) {
        return v.x | v.y | v.z;
    }

    public static boolean any(Vec3d v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3i v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3l v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3s v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec3ub v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0;
    }

    public static boolean any(Vec3ui v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0;
    }

    public static boolean any(Vec3ul v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0;
    }

    public static boolean any(Vec3us v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0;
    }

    public static boolean any(Vec4 v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec4b v) {
        return v.x != 0 | v.y != 0 | v.z != 0;
    }

    public static boolean any(Vec4bool v) {
        return v.x | v.y | v.z | v.w;
    }

    public static boolean any(Vec4d v) {
        return v.x != 0 | v.y != 0 | v.z != 0 | v.w != 0;
    }

    public static boolean any(Vec4i v) {
        return v.x != 0 | v.y != 0 | v.z != 0 | v.w != 0;
    }

    public static boolean any(Vec4l v) {
        return v.x != 0 | v.y != 0 | v.z != 0 | v.w != 0;
    }

    public static boolean any(Vec4s v) {
        return v.x != 0 | v.y != 0 | v.z != 0 | v.w != 0;
    }

    public static boolean any(Vec4ub v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0 | v.w.value != 0;
    }

    public static boolean any(Vec4ui v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0 | v.w.value != 0;
    }

    public static boolean any(Vec4ul v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0 | v.w.value != 0;
    }

    public static boolean any(Vec4us v) {
        return v.x.value != 0 | v.y.value != 0 | v.z.value != 0 | v.w.value != 0;
    }

    // all ----------------------------------------------------------------------------------------------------------
    public static boolean all(Vec2 v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2b v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2bool v) {
        return v.x & v.y;
    }

    public static boolean all(Vec2d v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2i v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2l v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2s v) {
        return v.x != 0 & v.y != 0;
    }

    public static boolean all(Vec2ub v) {
        return v.x.value != 0 & v.y.value != 0;
    }

    public static boolean all(Vec2ui v) {
        return v.x.value != 0 & v.y.value != 0;
    }

    public static boolean all(Vec2ul v) {
        return v.x.value != 0 & v.y.value != 0;
    }

    public static boolean all(Vec2us v) {
        return v.x.value != 0 & v.y.value != 0;
    }

    public static boolean all(Vec3 v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3b v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3bool v) {
        return v.x & v.y & v.z;
    }

    public static boolean all(Vec3d v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3i v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3l v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3s v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec3ub v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0;
    }

    public static boolean all(Vec3ui v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0;
    }

    public static boolean all(Vec3ul v) {
        return v.x.value != 0 & v.y.value != 0
                & v.z.value != 0;
    }

    public static boolean all(Vec3us v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0;
    }

    public static boolean all(Vec4 v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec4b v) {
        return v.x != 0 & v.y != 0 & v.z != 0;
    }

    public static boolean all(Vec4bool v) {
        return v.x & v.y & v.z & v.w;
    }

    public static boolean all(Vec4d v) {
        return v.x != 0 & v.y != 0 & v.z != 0 & v.w != 0;
    }

    public static boolean all(Vec4i v) {
        return v.x != 0 & v.y != 0 & v.z != 0 & v.w != 0;
    }

    public static boolean all(Vec4l v) {
        return v.x != 0 & v.y != 0 & v.z != 0 & v.w != 0;
    }

    public static boolean all(Vec4s v) {
        return v.x != 0 & v.y != 0 & v.z != 0 & v.w != 0;
    }

    public static boolean all(Vec4ub v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0 & v.w.value != 0;
    }

    public static boolean all(Vec4ui v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0 & v.w.value != 0;
    }

    public static boolean all(Vec4ul v) {
        return v.x.value != 0 & v.y.value != 0
                & v.z.value != 0 & v.w.value != 0;
    }

    public static boolean all(Vec4us v) {
        return v.x.value != 0 & v.y.value != 0 & v.z.value != 0 & v.w.value != 0;
    }

    // not ----------------------------------------------------------------------------------------------------------
    public static Vec2 not_(Vec2 v) {
        return not(v, new Vec2());
    }

    public static Vec2 not(Vec2 v, Vec2 res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        return res;
    }

    public static Vec2b not_(Vec2b v) {
        return not(v, new Vec2b());
    }

    public static Vec2b not(Vec2b v, Vec2b res) {
        res.x = (byte) (v.x == 0 ? 1 : 0);
        res.y = (byte) (v.y == 0 ? 1 : 0);
        return res;
    }

    public static Vec2bool not_(Vec2bool v) {
        return not(v, new Vec2bool());
    }

    public static Vec2bool not(Vec2bool v, Vec2bool res) {
        res.x = !v.x;
        res.y = !v.y;
        return res;
    }

    public static Vec2d not_(Vec2d v) {
        return not(v, new Vec2d());
    }

    public static Vec2d not(Vec2d v, Vec2d res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        return res;
    }

    public static Vec2i not_(Vec2i v) {
        return not(v, new Vec2i());
    }

    public static Vec2i not(Vec2i v, Vec2i res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        return res;
    }

    public static Vec2l not_(Vec2l v) {
        return not(v, new Vec2l());
    }

    public static Vec2l not(Vec2l v, Vec2l res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        return res;
    }

    public static Vec2s not_(Vec2s v) {
        return not(v, new Vec2s());
    }

    public static Vec2s not(Vec2s v, Vec2s res) {
        res.x = (short) (v.x == 0 ? 1 : 0);
        res.y = (short) (v.y == 0 ? 1 : 0);
        return res;
    }

    public static Vec2ub not_(Vec2ub v) {
        return not(v, new Vec2ub());
    }

    public static Vec2ub not(Vec2ub v, Vec2ub res) {
        res.x.value = (byte) (v.x.value == 0 ? 1 : 0);
        res.y.value = (byte) (v.y.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui not_(Vec2ui v) {
        return not(v, new Vec2ui());
    }

    public static Vec2ui not(Vec2ui v, Vec2ui res) {
        res.x.value = v.x.value == 0 ? 1 : 0;
        res.y.value = v.y.value == 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul not_(Vec2ul v) {
        return not(v, new Vec2ul());
    }

    public static Vec2ul not(Vec2ul v, Vec2ul res) {
        res.x.value = v.x.value == 0 ? 1 : 0;
        res.y.value = v.y.value == 0 ? 1 : 0;
        return res;
    }

    public static Vec2us not_(Vec2us v) {
        return not(v, new Vec2us());
    }

    public static Vec2us not(Vec2us v, Vec2us res) {
        res.x.value = (short) (v.x.value == 0 ? 1 : 0);
        res.y.value = (short) (v.y.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec3 not_(Vec3 v) {
        return not(v, new Vec3());
    }

    public static Vec3 not(Vec3 v, Vec3 res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        return res;
    }

    public static Vec3b not_(Vec3b v) {
        return not(v, new Vec3b());
    }

    public static Vec3b not(Vec3b v, Vec3b res) {
        res.x = (byte) (v.x == 0 ? 1 : 0);
        res.y = (byte) (v.y == 0 ? 1 : 0);
        res.z = (byte) (v.z == 0 ? 1 : 0);
        return res;
    }

    public static Vec3bool not_(Vec3bool v) {
        return not(v, new Vec3bool());
    }

    public static Vec3bool not(Vec3bool v, Vec3bool res) {
        res.x = !v.x;
        res.y = !v.y;
        res.z = !v.z;
        return res;
    }

    public static Vec3d not_(Vec3d v) {
        return not(v, new Vec3d());
    }

    public static Vec3d not(Vec3d v, Vec3d res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        return res;
    }

    public static Vec3i not_(Vec3i v) {
        return not(v, new Vec3i());
    }

    public static Vec3i not(Vec3i v, Vec3i res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        return res;
    }

    public static Vec3l not_(Vec3l v) {
        return not(v, new Vec3l());
    }

    public static Vec3l not(Vec3l v, Vec3l res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        return res;
    }

    public static Vec3s not_(Vec3s v) {
        return not(v, new Vec3s());
    }

    public static Vec3s not(Vec3s v, Vec3s res) {
        res.x = (short) (v.x == 0 ? 1 : 0);
        res.y = (short) (v.y == 0 ? 1 : 0);
        res.z = (short) (v.z == 0 ? 1 : 0);
        return res;
    }

    public static Vec3ub not_(Vec3ub v) {
        return not(v, new Vec3ub());
    }

    public static Vec3ub not(Vec3ub v, Vec3ub res) {
        res.x.value = (byte) (v.x.value == 0 ? 1 : 0);
        res.y.value = (byte) (v.y.value == 0 ? 1 : 0);
        res.z.value = (byte) (v.z.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui not_(Vec3ui v) {
        return not(v, new Vec3ui());
    }

    public static Vec3ui not(Vec3ui v, Vec3ui res) {
        res.x.value = v.x.value == 0 ? 1 : 0;
        res.y.value = v.y.value == 0 ? 1 : 0;
        res.z.value = v.z.value == 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul not_(Vec3ul v) {
        return not(v, new Vec3ul());
    }

    public static Vec3ul not(Vec3ul v, Vec3ul res) {
        res.x.value = v.x.value == 0 ? 1 : 0;
        res.y.value = v.y.value == 0 ? 1 : 0;
        res.z.value = v.z.value == 0 ? 1 : 0;
        return res;
    }

    public static Vec3us not_(Vec3us v) {
        return not(v, new Vec3us());
    }

    public static Vec3us not(Vec3us v, Vec3us res) {
        res.x.value = (short) (v.x.value == 0 ? 1 : 0);
        res.y.value = (short) (v.y.value == 0 ? 1 : 0);
        res.z.value = (short) (v.z.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec4 not_(Vec4 v) {
        return not(v, new Vec4());
    }

    public static Vec4 not(Vec4 v, Vec4 res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        res.w = v.w == 0 ? 1 : 0;
        return res;
    }

    public static Vec4b not_(Vec4b v) {
        return not(v, new Vec4b());
    }

    public static Vec4b not(Vec4b v, Vec4b res) {
        res.x = (byte) (v.x == 0 ? 1 : 0);
        res.y = (byte) (v.y == 0 ? 1 : 0);
        res.z = (byte) (v.z == 0 ? 1 : 0);
        res.w = (byte) (v.w == 0 ? 1 : 0);
        return res;
    }

    public static Vec4bool not_(Vec4bool v) {
        return not(v, new Vec4bool());
    }

    public static Vec4bool not(Vec4bool v, Vec4bool res) {
        res.x = !v.x;
        res.y = !v.y;
        res.z = !v.z;
        res.w = !v.w;
        return res;
    }

    public static Vec4d not_(Vec4d v) {
        return not(v, new Vec4d());
    }

    public static Vec4d not(Vec4d v, Vec4d res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        res.w = v.w == 0 ? 1 : 0;
        return res;
    }

    public static Vec4i not_(Vec4i v) {
        return not(v, new Vec4i());
    }

    public static Vec4i not(Vec4i v, Vec4i res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        res.w = v.w == 0 ? 1 : 0;
        return res;
    }

    public static Vec4l not_(Vec4l v) {
        return not(v, new Vec4l());
    }

    public static Vec4l not(Vec4l v, Vec4l res) {
        res.x = v.x == 0 ? 1 : 0;
        res.y = v.y == 0 ? 1 : 0;
        res.z = v.z == 0 ? 1 : 0;
        res.w = v.w == 0 ? 1 : 0;
        return res;
    }

    public static Vec4s not_(Vec4s v) {
        return not(v, new Vec4s());
    }

    public static Vec4s not(Vec4s v, Vec4s res) {
        res.x = (short) (v.x == 0 ? 1 : 0);
        res.y = (short) (v.y == 0 ? 1 : 0);
        res.z = (short) (v.z == 0 ? 1 : 0);
        res.w = (short) (v.w == 0 ? 1 : 0);
        return res;
    }

    public static Vec4ub not_(Vec4ub v) {
        return not(v, new Vec4ub());
    }

    public static Vec4ub not(Vec4ub v, Vec4ub res) {
        res.x.value = (byte) (v.x.value == 0 ? 1 : 0);
        res.y.value = (byte) (v.y.value == 0 ? 1 : 0);
        res.z.value = (byte) (v.z.value == 0 ? 1 : 0);
        res.w.value = (byte) (v.w.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui not_(Vec4ui v) {
        return not(v, new Vec4ui());
    }

    public static Vec4ui not(Vec4ui v, Vec4ui res) {
        res.x.value = v.x.value == 0 ? 1 : 0;
        res.y.value = v.y.value == 0 ? 1 : 0;
        res.z.value = v.z.value == 0 ? 1 : 0;
        res.w.value = v.w.value == 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul not_(Vec4ul v) {
        return not(v, new Vec4ul());
    }

    public static Vec4ul not(Vec4ul v, Vec4ul res) {
        res.x.value = (v.x.value == 0 ? 1 : 0);
        res.y.value = (v.y.value == 0 ? 1 : 0);
        res.z.value = (v.z.value == 0 ? 1 : 0);
        res.w.value = (v.w.value == 0 ? 1 : 0);
        return res;
    }

    public static Vec4us not_(Vec4us v) {
        return not(v, new Vec4us());
    }

    public static Vec4us not(Vec4us v, Vec4us res) {
        res.x.value = (short) (v.x.value == 0 ? 1 : 0);
        res.y.value = (short) (v.y.value == 0 ? 1 : 0);
        res.z.value = (short) (v.z.value == 0 ? 1 : 0);
        res.w.value = (short) (v.w.value == 0 ? 1 : 0);
        return res;
    }

    // lessThan -----------------------------------------------------------------------------------------------------
    public static boolean lessThan(byte a, byte b) {
        return a < b;
    }

    public static boolean lessThan(double a, double b) {
        return a < b;
    }

    public static boolean lessThan(float a, float b) {
        return a < b;
    }

    public static boolean lessThan(int a, int b) {
        return a < b;
    }

    public static boolean lessThan(long a, long b) {
        return a < b;
    }

    public static boolean lessThan(short a, short b) {
        return a < b;
    }

    public static boolean lessThan(UByte a, UByte b) {
        return a.compareTo(b) < 0;
    }

    public static boolean lessThan(UInt a, UInt b) {
        return a.compareTo(b) < 0;
    }

    public static boolean lessThan(ULong a, ULong b) {
        return a.compareTo(b) < 0;
    }

    public static boolean lessThan(UShort a, UShort b) {
        return a.compareTo(b) < 0;
    }

    // lessThan (vec2) ---------------------------------------------------------
    public static Vec2bool lessThan__(Vec2b a, Vec2b b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2d a, Vec2d b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2 a, Vec2 b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2i a, Vec2i b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2l a, Vec2l b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2s a, Vec2s b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        return res;
    }

    public static Vec2bool lessThan__(Vec2ub a, Vec2ub b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        return res;
    }

    public static Vec2bool lessThan__(Vec2ui a, Vec2ui b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        return res;
    }

    public static Vec2bool lessThan__(Vec2ul a, Vec2ul b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        return res;
    }

    public static Vec2bool lessThan__(Vec2us a, Vec2us b) {
        return lessThan(a, b, new Vec2bool());
    }

    public static Vec2bool lessThan(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        return res;
    }

    public static Vec2b lessThan_(Vec2b a, Vec2b b) {
        return lessThan(a, b, new Vec2b());
    }

    public static Vec2b lessThan(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x < b.x ? 1 : 0);
        res.y = (byte) (a.y < b.y ? 1 : 0);
        return res;
    }

    public static Vec2d lessThan_(Vec2d a, Vec2d b) {
        return lessThan(a, b, new Vec2d());
    }

    public static Vec2d lessThan(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        return res;
    }

    public static Vec2 lessThan_(Vec2 a, Vec2 b) {
        return lessThan(a, b, new Vec2());
    }

    public static Vec2 lessThan(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        return res;
    }

    public static Vec2i lessThan_(Vec2i a, Vec2i b) {
        return lessThan(a, b, new Vec2i());
    }

    public static Vec2i lessThan(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        return res;
    }

    public static Vec2l lessThan_(Vec2l a, Vec2l b) {
        return lessThan(a, b, new Vec2l());
    }

    public static Vec2l lessThan(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        return res;
    }

    public static Vec2s lessThan_(Vec2s a, Vec2s b) {
        return lessThan(a, b, new Vec2s());
    }

    public static Vec2s lessThan(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x < b.x ? 1 : 0);
        res.y = (short) (a.y < b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub lessThan_(Vec2ub a, Vec2ub b) {
        return lessThan(a, b, new Vec2ub());
    }

    public static Vec2ub lessThan(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui lessThan_(Vec2ui a, Vec2ui b) {
        return lessThan(a, b, new Vec2ui());
    }

    public static Vec2ui lessThan(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul lessThan_(Vec2ul a, Vec2ul b) {
        return lessThan(a, b, new Vec2ul());
    }

    public static Vec2ul lessThan(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        return res;
    }

    public static Vec2us lessThan_(Vec2us a, Vec2us b) {
        return lessThan(a, b, new Vec2us());
    }

    public static Vec2us lessThan(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        return res;
    }

    // lessThan (vec3) ---------------------------------------------------------
    public static Vec3bool lessThan__(Vec3b a, Vec3b b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3d a, Vec3d b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3 a, Vec3 b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3i a, Vec3i b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3l a, Vec3l b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3s a, Vec3s b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        return res;
    }

    public static Vec3bool lessThan__(Vec3ub a, Vec3ub b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        return res;
    }

    public static Vec3bool lessThan__(Vec3ui a, Vec3ui b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        return res;
    }

    public static Vec3bool lessThan__(Vec3ul a, Vec3ul b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        return res;
    }

    public static Vec3bool lessThan__(Vec3us a, Vec3us b) {
        return lessThan(a, b, new Vec3bool());
    }

    public static Vec3bool lessThan(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        return res;
    }

    public static Vec3b lessThan_(Vec3b a, Vec3b b) {
        return lessThan(a, b, new Vec3b());
    }

    public static Vec3b lessThan(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x < b.x ? 1 : 0);
        res.y = (byte) (a.y < b.y ? 1 : 0);
        res.z = (byte) (a.z < b.z ? 1 : 0);
        return res;
    }

    public static Vec3d lessThan_(Vec3d a, Vec3d b) {
        return lessThan(a, b, new Vec3d());
    }

    public static Vec3d lessThan(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        return res;
    }

    public static Vec3 lessThan_(Vec3 a, Vec3 b) {
        return lessThan(a, b, new Vec3());
    }

    public static Vec3 lessThan(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        return res;
    }

    public static Vec3i lessThan_(Vec3i a, Vec3i b) {
        return lessThan(a, b, new Vec3i());
    }

    public static Vec3i lessThan(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        return res;
    }

    public static Vec3l lessThan_(Vec3l a, Vec3l b) {
        return lessThan(a, b, new Vec3l());
    }

    public static Vec3l lessThan(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        return res;
    }

    public static Vec3s lessThan_(Vec3s a, Vec3s b) {
        return lessThan(a, b, new Vec3s());
    }

    public static Vec3s lessThan(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x < b.x ? 1 : 0);
        res.y = (short) (a.y < b.y ? 1 : 0);
        res.z = (short) (a.z < b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub lessThan_(Vec3ub a, Vec3ub b) {
        return lessThan(a, b, new Vec3ub());
    }

    public static Vec3ub lessThan(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) < 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui lessThan_(Vec3ui a, Vec3ui b) {
        return lessThan(a, b, new Vec3ui());
    }

    public static Vec3ui lessThan(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) < 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul lessThan_(Vec3ul a, Vec3ul b) {
        return lessThan(a, b, new Vec3ul());
    }

    public static Vec3ul lessThan(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) < 0 ? 1 : 0;
        return res;
    }

    public static Vec3us lessThan_(Vec3us a, Vec3us b) {
        return lessThan(a, b, new Vec3us());
    }

    public static Vec3us lessThan(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) < 0 ? 1 : 0);
        return res;
    }

    // lessThan (vec3) ---------------------------------------------------------
    public static Vec4bool lessThan__(Vec4b a, Vec4b b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4d a, Vec4d b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4 a, Vec4 b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4i a, Vec4i b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4l a, Vec4l b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4s a, Vec4s b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x < b.x;
        res.y = a.y < b.y;
        res.z = a.z < b.z;
        res.w = a.w < b.w;
        return res;
    }

    public static Vec4bool lessThan__(Vec4ub a, Vec4ub b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        res.w = a.w.compareTo(b.w) < 0;
        return res;
    }

    public static Vec4bool lessThan__(Vec4ui a, Vec4ui b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        res.w = a.w.compareTo(b.w) < 0;
        return res;
    }

    public static Vec4bool lessThan__(Vec4ul a, Vec4ul b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        res.w = a.w.compareTo(b.w) < 0;
        return res;
    }

    public static Vec4bool lessThan__(Vec4us a, Vec4us b) {
        return lessThan(a, b, new Vec4bool());
    }

    public static Vec4bool lessThan(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) < 0;
        res.y = a.y.compareTo(b.y) < 0;
        res.z = a.z.compareTo(b.z) < 0;
        res.w = a.w.compareTo(b.w) < 0;
        return res;
    }

    public static Vec4b lessThan_(Vec4b a, Vec4b b) {
        return lessThan(a, b, new Vec4b());
    }

    public static Vec4b lessThan(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x < b.x ? 1 : 0);
        res.y = (byte) (a.y < b.y ? 1 : 0);
        res.z = (byte) (a.z < b.z ? 1 : 0);
        res.w = (byte) (a.w < b.w ? 1 : 0);
        return res;
    }

    public static Vec4d lessThan_(Vec4d a, Vec4d b) {
        return lessThan(a, b, new Vec4d());
    }

    public static Vec4d lessThan(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        res.w = a.w < b.w ? 1 : 0;
        return res;
    }

    public static Vec4 lessThan_(Vec4 a, Vec4 b) {
        return lessThan(a, b, new Vec4());
    }

    public static Vec4 lessThan(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        res.w = a.w < b.w ? 1 : 0;
        return res;
    }

    public static Vec4i lessThan_(Vec4i a, Vec4i b) {
        return lessThan(a, b, new Vec4i());
    }

    public static Vec4i lessThan(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        res.w = a.w < b.w ? 1 : 0;
        return res;
    }

    public static Vec4l lessThan_(Vec4l a, Vec4l b) {
        return lessThan(a, b, new Vec4l());
    }

    public static Vec4l lessThan(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x < b.x ? 1 : 0;
        res.y = a.y < b.y ? 1 : 0;
        res.z = a.z < b.z ? 1 : 0;
        res.w = a.w < b.w ? 1 : 0;
        return res;
    }

    public static Vec4s lessThan_(Vec4s a, Vec4s b) {
        return lessThan(a, b, new Vec4s());
    }

    public static Vec4s lessThan(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x < b.x ? 1 : 0);
        res.y = (short) (a.y < b.y ? 1 : 0);
        res.z = (short) (a.z < b.z ? 1 : 0);
        res.w = (short) (a.w < b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub lessThan_(Vec4ub a, Vec4ub b) {
        return lessThan(a, b, new Vec4ub());
    }

    public static Vec4ub lessThan(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) < 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) < 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui lessThan_(Vec4ui a, Vec4ui b) {
        return lessThan(a, b, new Vec4ui());
    }

    public static Vec4ui lessThan(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) < 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) < 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul lessThan_(Vec4ul a, Vec4ul b) {
        return lessThan(a, b, new Vec4ul());
    }

    public static Vec4ul lessThan(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) < 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) < 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) < 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) < 0 ? 1 : 0;
        return res;
    }

    public static Vec4us lessThan_(Vec4us a, Vec4us b) {
        return lessThan(a, b, new Vec4us());
    }

    public static Vec4us lessThan(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) < 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) < 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) < 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) < 0 ? 1 : 0);
        return res;
    }

    // lessThanEqual -------------------------------------------------------------------------------------------------
    public static boolean lessThanEqual(byte a, byte b) {
        return a <= b;
    }

    public static boolean lessThanEqual(double a, double b) {
        return a <= b;
    }

    public static boolean lessThanEqual(float a, float b) {
        return a <= b;
    }

    public static boolean lessThanEqual(int a, int b) {
        return a <= b;
    }

    public static boolean lessThanEqual(long a, long b) {
        return a <= b;
    }

    public static boolean lessThanEqual(short a, short b) {
        return a <= b;
    }

    public static boolean lessThanEqual(UByte a, UByte b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean lessThanEqual(UInt a, UInt b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean lessThanEqual(ULong a, ULong b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean lessThanEqual(UShort a, UShort b) {
        return a.compareTo(b) <= 0;
    }

    // lessThanEqual (vec2) ----------------------------------------------------
    public static Vec2bool lessThanEqual__(Vec2b a, Vec2b b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2d a, Vec2d b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2 a, Vec2 b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2i a, Vec2i b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2l a, Vec2l b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2s a, Vec2s b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2ub a, Vec2ub b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2ui a, Vec2ui b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2ul a, Vec2ul b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        return res;
    }

    public static Vec2bool lessThanEqual__(Vec2us a, Vec2us b) {
        return lessThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool lessThanEqual(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        return res;
    }

    public static Vec2b lessThanEqual_(Vec2b a, Vec2b b) {
        return lessThanEqual(a, b, new Vec2b());
    }

    public static Vec2b lessThanEqual(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x <= b.x ? 1 : 0);
        res.y = (byte) (a.y <= b.y ? 1 : 0);
        return res;
    }

    public static Vec2d lessThanEqual_(Vec2d a, Vec2d b) {
        return lessThanEqual(a, b, new Vec2d());
    }

    public static Vec2d lessThanEqual(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        return res;
    }

    public static Vec2 lessThanEqual_(Vec2 a, Vec2 b) {
        return lessThanEqual(a, b, new Vec2());
    }

    public static Vec2 lessThanEqual(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        return res;
    }

    public static Vec2i lessThanEqual_(Vec2i a, Vec2i b) {
        return lessThanEqual(a, b, new Vec2i());
    }

    public static Vec2i lessThanEqual(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        return res;
    }

    public static Vec2l lessThanEqual_(Vec2l a, Vec2l b) {
        return lessThanEqual(a, b, new Vec2l());
    }

    public static Vec2l lessThanEqual(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        return res;
    }

    public static Vec2s lessThanEqual_(Vec2s a, Vec2s b) {
        return lessThanEqual(a, b, new Vec2s());
    }

    public static Vec2s lessThanEqual(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x <= b.x ? 1 : 0);
        res.y = (short) (a.y <= b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub lessThanEqual_(Vec2ub a, Vec2ub b) {
        return lessThanEqual(a, b, new Vec2ub());
    }

    public static Vec2ub lessThanEqual(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui lessThanEqual_(Vec2ui a, Vec2ui b) {
        return lessThanEqual(a, b, new Vec2ui());
    }

    public static Vec2ui lessThanEqual(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul lessThanEqual_(Vec2ul a, Vec2ul b) {
        return lessThanEqual(a, b, new Vec2ul());
    }

    public static Vec2ul lessThanEqual(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec2us lessThanEqual_(Vec2us a, Vec2us b) {
        return lessThanEqual(a, b, new Vec2us());
    }

    public static Vec2us lessThanEqual(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        return res;
    }

    // lessThan (vec3) ---------------------------------------------------------
    public static Vec3bool lessThanEqual__(Vec3b a, Vec3b b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3d a, Vec3d b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3 a, Vec3 b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3i a, Vec3i b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3l a, Vec3l b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3s a, Vec3s b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3ub a, Vec3ub b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3ui a, Vec3ui b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3ul a, Vec3ul b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        return res;
    }

    public static Vec3bool lessThanEqual__(Vec3us a, Vec3us b) {
        return lessThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool lessThanEqual(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        return res;
    }

    public static Vec3b lessThanEqual_(Vec3b a, Vec3b b) {
        return lessThanEqual(a, b, new Vec3b());
    }

    public static Vec3b lessThanEqual(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x <= b.x ? 1 : 0);
        res.y = (byte) (a.y <= b.y ? 1 : 0);
        res.z = (byte) (a.z <= b.z ? 1 : 0);
        return res;
    }

    public static Vec3d lessThanEqual_(Vec3d a, Vec3d b) {
        return lessThanEqual(a, b, new Vec3d());
    }

    public static Vec3d lessThanEqual(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        return res;
    }

    public static Vec3 lessThanEqual_(Vec3 a, Vec3 b) {
        return lessThanEqual(a, b, new Vec3());
    }

    public static Vec3 lessThanEqual(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        return res;
    }

    public static Vec3i lessThanEqual_(Vec3i a, Vec3i b) {
        return lessThanEqual(a, b, new Vec3i());
    }

    public static Vec3i lessThanEqual(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        return res;
    }

    public static Vec3l lessThanEqual_(Vec3l a, Vec3l b) {
        return lessThanEqual(a, b, new Vec3l());
    }

    public static Vec3l lessThanEqual(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        return res;
    }

    public static Vec3s lessThanEqual_(Vec3s a, Vec3s b) {
        return lessThanEqual(a, b, new Vec3s());
    }

    public static Vec3s lessThanEqual(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x <= b.x ? 1 : 0);
        res.y = (short) (a.y <= b.y ? 1 : 0);
        res.z = (short) (a.z <= b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub lessThanEqual_(Vec3ub a, Vec3ub b) {
        return lessThanEqual(a, b, new Vec3ub());
    }

    public static Vec3ub lessThanEqual(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) <= 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui lessThanEqual_(Vec3ui a, Vec3ui b) {
        return lessThanEqual(a, b, new Vec3ui());
    }

    public static Vec3ui lessThanEqual(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul lessThanEqual_(Vec3ul a, Vec3ul b) {
        return lessThanEqual(a, b, new Vec3ul());
    }

    public static Vec3ul lessThanEqual(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec3us lessThanEqual_(Vec3us a, Vec3us b) {
        return lessThanEqual(a, b, new Vec3us());
    }

    public static Vec3us lessThanEqual(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) <= 0 ? 1 : 0);
        return res;
    }

    // lessThan (vec3) ---------------------------------------------------------
    public static Vec4bool lessThanEqual__(Vec4b a, Vec4b b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4d a, Vec4d b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4 a, Vec4 b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4i a, Vec4i b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4l a, Vec4l b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4s a, Vec4s b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x <= b.x;
        res.y = a.y <= b.y;
        res.z = a.z <= b.z;
        res.w = a.w <= b.w;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4ub a, Vec4ub b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        res.w = a.w.compareTo(b.w) <= 0;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4ui a, Vec4ui b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        res.w = a.w.compareTo(b.w) <= 0;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4ul a, Vec4ul b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        res.w = a.w.compareTo(b.w) <= 0;
        return res;
    }

    public static Vec4bool lessThanEqual__(Vec4us a, Vec4us b) {
        return lessThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool lessThanEqual(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) <= 0;
        res.y = a.y.compareTo(b.y) <= 0;
        res.z = a.z.compareTo(b.z) <= 0;
        res.w = a.w.compareTo(b.w) <= 0;
        return res;
    }

    public static Vec4b lessThanEqual_(Vec4b a, Vec4b b) {
        return lessThanEqual(a, b, new Vec4b());
    }

    public static Vec4b lessThanEqual(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x <= b.x ? 1 : 0);
        res.y = (byte) (a.y <= b.y ? 1 : 0);
        res.z = (byte) (a.z <= b.z ? 1 : 0);
        res.w = (byte) (a.w <= b.w ? 1 : 0);
        return res;
    }

    public static Vec4d lessThanEqual_(Vec4d a, Vec4d b) {
        return lessThanEqual(a, b, new Vec4d());
    }

    public static Vec4d lessThanEqual(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        res.w = a.w <= b.w ? 1 : 0;
        return res;
    }

    public static Vec4 lessThanEqual_(Vec4 a, Vec4 b) {
        return lessThanEqual(a, b, new Vec4());
    }

    public static Vec4 lessThanEqual(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        res.w = a.w <= b.w ? 1 : 0;
        return res;
    }

    public static Vec4i lessThanEqual_(Vec4i a, Vec4i b) {
        return lessThanEqual(a, b, new Vec4i());
    }

    public static Vec4i lessThanEqual(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        res.w = a.w <= b.w ? 1 : 0;
        return res;
    }

    public static Vec4l lessThanEqual_(Vec4l a, Vec4l b) {
        return lessThanEqual(a, b, new Vec4l());
    }

    public static Vec4l lessThanEqual(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x <= b.x ? 1 : 0;
        res.y = a.y <= b.y ? 1 : 0;
        res.z = a.z <= b.z ? 1 : 0;
        res.w = a.w <= b.w ? 1 : 0;
        return res;
    }

    public static Vec4s lessThanEqual_(Vec4s a, Vec4s b) {
        return lessThanEqual(a, b, new Vec4s());
    }

    public static Vec4s lessThanEqual(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x <= b.x ? 1 : 0);
        res.y = (short) (a.y <= b.y ? 1 : 0);
        res.z = (short) (a.z <= b.z ? 1 : 0);
        res.w = (short) (a.w <= b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub lessThanEqual_(Vec4ub a, Vec4ub b) {
        return lessThanEqual(a, b, new Vec4ub());
    }

    public static Vec4ub lessThanEqual(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) <= 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) <= 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui lessThanEqual_(Vec4ui a, Vec4ui b) {
        return lessThanEqual(a, b, new Vec4ui());
    }

    public static Vec4ui lessThanEqual(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) <= 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul lessThanEqual_(Vec4ul a, Vec4ul b) {
        return lessThanEqual(a, b, new Vec4ul());
    }

    public static Vec4ul lessThanEqual(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) <= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) <= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) <= 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) <= 0 ? 1 : 0;
        return res;
    }

    public static Vec4us lessThanEqual_(Vec4us a, Vec4us b) {
        return lessThanEqual(a, b, new Vec4us());
    }

    public static Vec4us lessThanEqual(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) <= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) <= 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) <= 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) <= 0 ? 1 : 0);
        return res;
    }

    // greaterThan --------------------------------------------------------------------------------------------------
    public static boolean greaterThan(byte a, byte b) {
        return a > b;
    }

    public static boolean greaterThan(double a, double b) {
        return a > b;
    }

    public static boolean greaterThan(float a, float b) {
        return a > b;
    }

    public static boolean greaterThan(int a, int b) {
        return a > b;
    }

    public static boolean greaterThan(long a, long b) {
        return a > b;
    }

    public static boolean greaterThan(short a, short b) {
        return a > b;
    }

    public static boolean greaterThan(UByte a, UByte b) {
        return a.compareTo(b) > 0;
    }

    public static boolean greaterThan(UInt a, UInt b) {
        return a.compareTo(b) > 0;
    }

    public static boolean greaterThan(ULong a, ULong b) {
        return a.compareTo(b) > 0;
    }

    public static boolean greaterThan(UShort a, UShort b) {
        return a.compareTo(b) > 0;
    }

    // greaterThan (vec2) ------------------------------------------------------
    public static Vec2bool greaterThan__(Vec2b a, Vec2b b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2d a, Vec2d b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2 a, Vec2 b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2i a, Vec2i b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2l a, Vec2l b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2s a, Vec2s b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2ub a, Vec2ub b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2ui a, Vec2ui b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2ul a, Vec2ul b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        return res;
    }

    public static Vec2bool greaterThan__(Vec2us a, Vec2us b) {
        return greaterThan(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThan(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        return res;
    }

    public static Vec2b greaterThan_(Vec2b a, Vec2b b) {
        return greaterThan(a, b, new Vec2b());
    }

    public static Vec2b greaterThan(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x > b.x ? 1 : 0);
        res.y = (byte) (a.y > b.y ? 1 : 0);
        return res;
    }

    public static Vec2d greaterThan_(Vec2d a, Vec2d b) {
        return greaterThan(a, b, new Vec2d());
    }

    public static Vec2d greaterThan(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        return res;
    }

    public static Vec2 greaterThan_(Vec2 a, Vec2 b) {
        return greaterThan(a, b, new Vec2());
    }

    public static Vec2 greaterThan(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        return res;
    }

    public static Vec2i greaterThan_(Vec2i a, Vec2i b) {
        return greaterThan(a, b, new Vec2i());
    }

    public static Vec2i greaterThan(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        return res;
    }

    public static Vec2l greaterThan_(Vec2l a, Vec2l b) {
        return greaterThan(a, b, new Vec2l());
    }

    public static Vec2l greaterThan(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        return res;
    }

    public static Vec2s greaterThan_(Vec2s a, Vec2s b) {
        return greaterThan(a, b, new Vec2s());
    }

    public static Vec2s greaterThan(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x > b.x ? 1 : 0);
        res.y = (short) (a.y > b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub greaterThan_(Vec2ub a, Vec2ub b) {
        return greaterThan(a, b, new Vec2ub());
    }

    public static Vec2ub greaterThan(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui greaterThan_(Vec2ui a, Vec2ui b) {
        return greaterThan(a, b, new Vec2ui());
    }

    public static Vec2ui greaterThan(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul greaterThan_(Vec2ul a, Vec2ul b) {
        return greaterThan(a, b, new Vec2ul());
    }

    public static Vec2ul greaterThan(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        return res;
    }

    public static Vec2us greaterThan_(Vec2us a, Vec2us b) {
        return greaterThan(a, b, new Vec2us());
    }

    public static Vec2us greaterThan(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        return res;
    }

    // greaterThan (vec3) ------------------------------------------------------
    public static Vec3bool greaterThan__(Vec3b a, Vec3b b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3d a, Vec3d b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3 a, Vec3 b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3i a, Vec3i b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3l a, Vec3l b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3s a, Vec3s b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3ub a, Vec3ub b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3ui a, Vec3ui b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3ul a, Vec3ul b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        return res;
    }

    public static Vec3bool greaterThan__(Vec3us a, Vec3us b) {
        return greaterThan(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThan(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        return res;
    }

    public static Vec3b greaterThan_(Vec3b a, Vec3b b) {
        return greaterThan(a, b, new Vec3b());
    }

    public static Vec3b greaterThan(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x > b.x ? 1 : 0);
        res.y = (byte) (a.y > b.y ? 1 : 0);
        res.z = (byte) (a.z > b.z ? 1 : 0);
        return res;
    }

    public static Vec3d greaterThan_(Vec3d a, Vec3d b) {
        return greaterThan(a, b, new Vec3d());
    }

    public static Vec3d greaterThan(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        return res;
    }

    public static Vec3 greaterThan_(Vec3 a, Vec3 b) {
        return greaterThan(a, b, new Vec3());
    }

    public static Vec3 greaterThan(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        return res;
    }

    public static Vec3i greaterThan_(Vec3i a, Vec3i b) {
        return greaterThan(a, b, new Vec3i());
    }

    public static Vec3i greaterThan(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        return res;
    }

    public static Vec3l greaterThan_(Vec3l a, Vec3l b) {
        return greaterThan(a, b, new Vec3l());
    }

    public static Vec3l greaterThan(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        return res;
    }

    public static Vec3s greaterThan_(Vec3s a, Vec3s b) {
        return greaterThan(a, b, new Vec3s());
    }

    public static Vec3s greaterThan(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x > b.x ? 1 : 0);
        res.y = (short) (a.y > b.y ? 1 : 0);
        res.z = (short) (a.z > b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub greaterThan_(Vec3ub a, Vec3ub b) {
        return greaterThan(a, b, new Vec3ub());
    }

    public static Vec3ub greaterThan(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) > 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui greaterThan_(Vec3ui a, Vec3ui b) {
        return greaterThan(a, b, new Vec3ui());
    }

    public static Vec3ui greaterThan(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) > 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul greaterThan_(Vec3ul a, Vec3ul b) {
        return greaterThan(a, b, new Vec3ul());
    }

    public static Vec3ul greaterThan(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) > 0 ? 1 : 0;
        return res;
    }

    public static Vec3us greaterThan_(Vec3us a, Vec3us b) {
        return greaterThan(a, b, new Vec3us());
    }

    public static Vec3us greaterThan(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) > 0 ? 1 : 0);
        return res;
    }

    // greaterThan (vec4) ------------------------------------------------------
    public static Vec4bool greaterThan__(Vec4b a, Vec4b b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4d a, Vec4d b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4 a, Vec4 b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4i a, Vec4i b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4l a, Vec4l b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4s a, Vec4s b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x > b.x;
        res.y = a.y > b.y;
        res.z = a.z > b.z;
        res.w = a.w > b.w;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4ub a, Vec4ub b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        res.w = a.w.compareTo(b.w) > 0;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4ui a, Vec4ui b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        res.w = a.w.compareTo(b.w) > 0;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4ul a, Vec4ul b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        res.w = a.w.compareTo(b.w) > 0;
        return res;
    }

    public static Vec4bool greaterThan__(Vec4us a, Vec4us b) {
        return greaterThan(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThan(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) > 0;
        res.y = a.y.compareTo(b.y) > 0;
        res.z = a.z.compareTo(b.z) > 0;
        res.w = a.w.compareTo(b.w) > 0;
        return res;
    }

    public static Vec4b greaterThan_(Vec4b a, Vec4b b) {
        return greaterThan(a, b, new Vec4b());
    }

    public static Vec4b greaterThan(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x > b.x ? 1 : 0);
        res.y = (byte) (a.y > b.y ? 1 : 0);
        res.z = (byte) (a.z > b.z ? 1 : 0);
        res.w = (byte) (a.w > b.w ? 1 : 0);
        return res;
    }

    public static Vec4d greaterThan_(Vec4d a, Vec4d b) {
        return greaterThan(a, b, new Vec4d());
    }

    public static Vec4d greaterThan(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        res.w = a.w > b.w ? 1 : 0;
        return res;
    }

    public static Vec4 greaterThan_(Vec4 a, Vec4 b) {
        return greaterThan(a, b, new Vec4());
    }

    public static Vec4 greaterThan(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        res.w = a.w > b.w ? 1 : 0;
        return res;
    }

    public static Vec4i greaterThan_(Vec4i a, Vec4i b) {
        return greaterThan(a, b, new Vec4i());
    }

    public static Vec4i greaterThan(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        res.w = a.w > b.w ? 1 : 0;
        return res;
    }

    public static Vec4l greaterThan_(Vec4l a, Vec4l b) {
        return greaterThan(a, b, new Vec4l());
    }

    public static Vec4l greaterThan(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x > b.x ? 1 : 0;
        res.y = a.y > b.y ? 1 : 0;
        res.z = a.z > b.z ? 1 : 0;
        res.w = a.w > b.w ? 1 : 0;
        return res;
    }

    public static Vec4s greaterThan_(Vec4s a, Vec4s b) {
        return greaterThan(a, b, new Vec4s());
    }

    public static Vec4s greaterThan(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x > b.x ? 1 : 0);
        res.y = (short) (a.y > b.y ? 1 : 0);
        res.z = (short) (a.z > b.z ? 1 : 0);
        res.w = (short) (a.w > b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub greaterThan_(Vec4ub a, Vec4ub b) {
        return greaterThan(a, b, new Vec4ub());
    }

    public static Vec4ub greaterThan(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) > 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) > 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui greaterThan_(Vec4ui a, Vec4ui b) {
        return greaterThan(a, b, new Vec4ui());
    }

    public static Vec4ui greaterThan(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) > 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) > 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul greaterThan_(Vec4ul a, Vec4ul b) {
        return greaterThan(a, b, new Vec4ul());
    }

    public static Vec4ul greaterThan(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) > 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) > 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) > 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) > 0 ? 1 : 0;
        return res;
    }

    public static Vec4us greaterThan_(Vec4us a, Vec4us b) {
        return greaterThan(a, b, new Vec4us());
    }

    public static Vec4us greaterThan(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) > 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) > 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) > 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) > 0 ? 1 : 0);
        return res;
    }

    // greaterThanEqual ---------------------------------------------------------------------------------------------
    public static boolean greaterThanEqual(byte a, byte b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(double a, double b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(float a, float b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(int a, int b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(long a, long b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(short a, short b) {
        return a >= b;
    }

    public static boolean greaterThanEqual(UByte a, UByte b) {
        return a.compareTo(b) >= 0;
    }

    public static boolean greaterThanEqual(UInt a, UInt b) {
        return a.compareTo(b) >= 0;
    }

    public static boolean greaterThanEqual(ULong a, ULong b) {
        return a.compareTo(b) >= 0;
    }

    public static boolean greaterThanEqual(UShort a, UShort b) {
        return a.compareTo(b) >= 0;
    }

    // greaterThanEqual (vec2) -------------------------------------------------
    public static Vec2bool greaterThanEqual__(Vec2b a, Vec2b b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2d a, Vec2d b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2 a, Vec2 b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2i a, Vec2i b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2l a, Vec2l b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2s a, Vec2s b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2ub a, Vec2ub b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2ui a, Vec2ui b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2ul a, Vec2ul b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        return res;
    }

    public static Vec2bool greaterThanEqual__(Vec2us a, Vec2us b) {
        return greaterThanEqual(a, b, new Vec2bool());
    }

    public static Vec2bool greaterThanEqual(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        return res;
    }

    public static Vec2b greaterThanEqual_(Vec2b a, Vec2b b) {
        return greaterThanEqual(a, b, new Vec2b());
    }

    public static Vec2b greaterThanEqual(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x >= b.x ? 1 : 0);
        res.y = (byte) (a.y >= b.y ? 1 : 0);
        return res;
    }

    public static Vec2d greaterThanEqual_(Vec2d a, Vec2d b) {
        return greaterThanEqual(a, b, new Vec2d());
    }

    public static Vec2d greaterThanEqual(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        return res;
    }

    public static Vec2 greaterThanEqual_(Vec2 a, Vec2 b) {
        return greaterThanEqual(a, b, new Vec2());
    }

    public static Vec2 greaterThanEqual(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        return res;
    }

    public static Vec2i greaterThanEqual_(Vec2i a, Vec2i b) {
        return greaterThanEqual(a, b, new Vec2i());
    }

    public static Vec2i greaterThanEqual(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        return res;
    }

    public static Vec2l greaterThanEqual_(Vec2l a, Vec2l b) {
        return greaterThanEqual(a, b, new Vec2l());
    }

    public static Vec2l greaterThanEqual(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        return res;
    }

    public static Vec2s greaterThanEqual_(Vec2s a, Vec2s b) {
        return greaterThanEqual(a, b, new Vec2s());
    }

    public static Vec2s greaterThanEqual(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x >= b.x ? 1 : 0);
        res.y = (short) (a.y >= b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub greaterThanEqual_(Vec2ub a, Vec2ub b) {
        return greaterThanEqual(a, b, new Vec2ub());
    }

    public static Vec2ub greaterThanEqual(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui greaterThanEqual_(Vec2ui a, Vec2ui b) {
        return greaterThanEqual(a, b, new Vec2ui());
    }

    public static Vec2ui greaterThanEqual(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul greaterThanEqual_(Vec2ul a, Vec2ul b) {
        return greaterThanEqual(a, b, new Vec2ul());
    }

    public static Vec2ul greaterThanEqual(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec2us greaterThanEqual_(Vec2us a, Vec2us b) {
        return greaterThanEqual(a, b, new Vec2us());
    }

    public static Vec2us greaterThanEqual(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        return res;
    }

    // greaterThanEqual (vec3) -------------------------------------------------
    public static Vec3bool greaterThanEqual__(Vec3b a, Vec3b b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3d a, Vec3d b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3 a, Vec3 b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3i a, Vec3i b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3l a, Vec3l b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3s a, Vec3s b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3ub a, Vec3ub b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3ui a, Vec3ui b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3ul a, Vec3ul b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        return res;
    }

    public static Vec3bool greaterThanEqual__(Vec3us a, Vec3us b) {
        return greaterThanEqual(a, b, new Vec3bool());
    }

    public static Vec3bool greaterThanEqual(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        return res;
    }

    public static Vec3b greaterThanEqual_(Vec3b a, Vec3b b) {
        return greaterThanEqual(a, b, new Vec3b());
    }

    public static Vec3b greaterThanEqual(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x >= b.x ? 1 : 0);
        res.y = (byte) (a.y >= b.y ? 1 : 0);
        res.z = (byte) (a.z >= b.z ? 1 : 0);
        return res;
    }

    public static Vec3d greaterThanEqual_(Vec3d a, Vec3d b) {
        return greaterThanEqual(a, b, new Vec3d());
    }

    public static Vec3d greaterThanEqual(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        return res;
    }

    public static Vec3 greaterThanEqual_(Vec3 a, Vec3 b) {
        return greaterThanEqual(a, b, new Vec3());
    }

    public static Vec3 greaterThanEqual(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        return res;
    }

    public static Vec3i greaterThanEqual_(Vec3i a, Vec3i b) {
        return greaterThanEqual(a, b, new Vec3i());
    }

    public static Vec3i greaterThanEqual(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        return res;
    }

    public static Vec3l greaterThanEqual_(Vec3l a, Vec3l b) {
        return greaterThanEqual(a, b, new Vec3l());
    }

    public static Vec3l greaterThanEqual(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        return res;
    }

    public static Vec3s greaterThanEqual_(Vec3s a, Vec3s b) {
        return greaterThanEqual(a, b, new Vec3s());
    }

    public static Vec3s greaterThanEqual(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x >= b.x ? 1 : 0);
        res.y = (short) (a.y >= b.y ? 1 : 0);
        res.z = (short) (a.z >= b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub greaterThanEqual_(Vec3ub a, Vec3ub b) {
        return greaterThanEqual(a, b, new Vec3ub());
    }

    public static Vec3ub greaterThanEqual(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) >= 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui greaterThanEqual_(Vec3ui a, Vec3ui b) {
        return greaterThanEqual(a, b, new Vec3ui());
    }

    public static Vec3ui greaterThanEqual(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul greaterThanEqual_(Vec3ul a, Vec3ul b) {
        return greaterThanEqual(a, b, new Vec3ul());
    }

    public static Vec3ul greaterThanEqual(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec3us greaterThanEqual_(Vec3us a, Vec3us b) {
        return greaterThanEqual(a, b, new Vec3us());
    }

    public static Vec3us greaterThanEqual(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) >= 0 ? 1 : 0);
        return res;
    }

    // greaterThanEqual (vec4) -------------------------------------------------
    public static Vec4bool greaterThanEqual__(Vec4b a, Vec4b b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4d a, Vec4d b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4 a, Vec4 b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4i a, Vec4i b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4l a, Vec4l b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4s a, Vec4s b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x >= b.x;
        res.y = a.y >= b.y;
        res.z = a.z >= b.z;
        res.w = a.w >= b.w;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4ub a, Vec4ub b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        res.w = a.w.compareTo(b.w) >= 0;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4ui a, Vec4ui b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        res.w = a.w.compareTo(b.w) >= 0;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4ul a, Vec4ul b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        res.w = a.w.compareTo(b.w) >= 0;
        return res;
    }

    public static Vec4bool greaterThanEqual__(Vec4us a, Vec4us b) {
        return greaterThanEqual(a, b, new Vec4bool());
    }

    public static Vec4bool greaterThanEqual(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) >= 0;
        res.y = a.y.compareTo(b.y) >= 0;
        res.z = a.z.compareTo(b.z) >= 0;
        res.w = a.w.compareTo(b.w) >= 0;
        return res;
    }

    public static Vec4b greaterThanEqual_(Vec4b a, Vec4b b) {
        return greaterThanEqual(a, b, new Vec4b());
    }

    public static Vec4b greaterThanEqual(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x >= b.x ? 1 : 0);
        res.y = (byte) (a.y >= b.y ? 1 : 0);
        res.z = (byte) (a.z >= b.z ? 1 : 0);
        res.w = (byte) (a.w >= b.w ? 1 : 0);
        return res;
    }

    public static Vec4d greaterThanEqual_(Vec4d a, Vec4d b) {
        return greaterThanEqual(a, b, new Vec4d());
    }

    public static Vec4d greaterThanEqual(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        res.w = a.w >= b.w ? 1 : 0;
        return res;
    }

    public static Vec4 greaterThanEqual_(Vec4 a, Vec4 b) {
        return greaterThanEqual(a, b, new Vec4());
    }

    public static Vec4 greaterThanEqual(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        res.w = a.w >= b.w ? 1 : 0;
        return res;
    }

    public static Vec4i greaterThanEqual_(Vec4i a, Vec4i b) {
        return greaterThanEqual(a, b, new Vec4i());
    }

    public static Vec4i greaterThanEqual(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        res.w = a.w >= b.w ? 1 : 0;
        return res;
    }

    public static Vec4l greaterThanEqual_(Vec4l a, Vec4l b) {
        return greaterThanEqual(a, b, new Vec4l());
    }

    public static Vec4l greaterThanEqual(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x >= b.x ? 1 : 0;
        res.y = a.y >= b.y ? 1 : 0;
        res.z = a.z >= b.z ? 1 : 0;
        res.w = a.w >= b.w ? 1 : 0;
        return res;
    }

    public static Vec4s greaterThanEqual_(Vec4s a, Vec4s b) {
        return greaterThanEqual(a, b, new Vec4s());
    }

    public static Vec4s greaterThanEqual(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x >= b.x ? 1 : 0);
        res.y = (short) (a.y >= b.y ? 1 : 0);
        res.z = (short) (a.z >= b.z ? 1 : 0);
        res.w = (short) (a.w >= b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub greaterThanEqual_(Vec4ub a, Vec4ub b) {
        return greaterThanEqual(a, b, new Vec4ub());
    }

    public static Vec4ub greaterThanEqual(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) >= 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) >= 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui greaterThanEqual_(Vec4ui a, Vec4ui b) {
        return greaterThanEqual(a, b, new Vec4ui());
    }

    public static Vec4ui greaterThanEqual(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) >= 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul greaterThanEqual_(Vec4ul a, Vec4ul b) {
        return greaterThanEqual(a, b, new Vec4ul());
    }

    public static Vec4ul greaterThanEqual(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) >= 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) >= 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) >= 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) >= 0 ? 1 : 0;
        return res;
    }

    public static Vec4us greaterThanEqual_(Vec4us a, Vec4us b) {
        return greaterThanEqual(a, b, new Vec4us());
    }

    public static Vec4us greaterThanEqual(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) >= 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) >= 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) >= 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) >= 0 ? 1 : 0);
        return res;
    }

    // equal --------------------------------------------------------------------------------------------------------
    public static boolean equal(byte a, byte b) {
        return a == b;
    }

    public static boolean equal(double a, double b) {
        return a == b;
    }

    public static boolean equal(float a, float b) {
        return a == b;
    }

    public static boolean equal(int a, int b) {
        return a == b;
    }

    public static boolean equal(long a, long b) {
        return a == b;
    }

    public static boolean equal(short a, short b) {
        return a == b;
    }

    public static boolean equal(UByte a, UByte b) {
        return a.compareTo(b) == 0;
    }

    public static boolean equal(UInt a, UInt b) {
        return a.compareTo(b) == 0;
    }

    public static boolean equal(ULong a, ULong b) {
        return a.compareTo(b) == 0;
    }

    public static boolean equal(UShort a, UShort b) {
        return a.compareTo(b) == 0;
    }

    // equal (vec2) ------------------------------------------------------------
    public static Vec2bool equal__(Vec2b a, Vec2b b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2d a, Vec2d b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2 a, Vec2 b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2i a, Vec2i b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2l a, Vec2l b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2s a, Vec2s b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        return res;
    }

    public static Vec2bool equal__(Vec2ub a, Vec2ub b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        return res;
    }

    public static Vec2bool equal__(Vec2ui a, Vec2ui b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        return res;
    }

    public static Vec2bool equal__(Vec2ul a, Vec2ul b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        return res;
    }

    public static Vec2bool equal__(Vec2us a, Vec2us b) {
        return equal(a, b, new Vec2bool());
    }

    public static Vec2bool equal(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        return res;
    }

    public static Vec2b equal_(Vec2b a, Vec2b b) {
        return equal(a, b, new Vec2b());
    }

    public static Vec2b equal(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x == b.x ? 1 : 0);
        res.y = (byte) (a.y == b.y ? 1 : 0);
        return res;
    }

    public static Vec2d equal_(Vec2d a, Vec2d b) {
        return equal(a, b, new Vec2d());
    }

    public static Vec2d equal(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        return res;
    }

    public static Vec2 equal_(Vec2 a, Vec2 b) {
        return equal(a, b, new Vec2());
    }

    public static Vec2 equal(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        return res;
    }

    public static Vec2i equal_(Vec2i a, Vec2i b) {
        return equal(a, b, new Vec2i());
    }

    public static Vec2i equal(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        return res;
    }

    public static Vec2l equal_(Vec2l a, Vec2l b) {
        return equal(a, b, new Vec2l());
    }

    public static Vec2l equal(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        return res;
    }

    public static Vec2s equal_(Vec2s a, Vec2s b) {
        return equal(a, b, new Vec2s());
    }

    public static Vec2s equal(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x == b.x ? 1 : 0);
        res.y = (short) (a.y == b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub equal_(Vec2ub a, Vec2ub b) {
        return equal(a, b, new Vec2ub());
    }

    public static Vec2ub equal(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui equal_(Vec2ui a, Vec2ui b) {
        return equal(a, b, new Vec2ui());
    }

    public static Vec2ui equal(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul equal_(Vec2ul a, Vec2ul b) {
        return equal(a, b, new Vec2ul());
    }

    public static Vec2ul equal(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        return res;
    }

    public static Vec2us equal_(Vec2us a, Vec2us b) {
        return equal(a, b, new Vec2us());
    }

    public static Vec2us equal(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        return res;
    }

    // equal (vec3) ------------------------------------------------------------
    public static Vec3bool equal__(Vec3b a, Vec3b b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3d a, Vec3d b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3 a, Vec3 b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3i a, Vec3i b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3l a, Vec3l b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3s a, Vec3s b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        return res;
    }

    public static Vec3bool equal__(Vec3ub a, Vec3ub b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        return res;
    }

    public static Vec3bool equal__(Vec3ui a, Vec3ui b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        return res;
    }

    public static Vec3bool equal__(Vec3ul a, Vec3ul b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        return res;
    }

    public static Vec3bool equal__(Vec3us a, Vec3us b) {
        return equal(a, b, new Vec3bool());
    }

    public static Vec3bool equal(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        return res;
    }

    public static Vec3b equal_(Vec3b a, Vec3b b) {
        return equal(a, b, new Vec3b());
    }

    public static Vec3b equal(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x == b.x ? 1 : 0);
        res.y = (byte) (a.y == b.y ? 1 : 0);
        res.z = (byte) (a.z == b.z ? 1 : 0);
        return res;
    }

    public static Vec3d equal_(Vec3d a, Vec3d b) {
        return equal(a, b, new Vec3d());
    }

    public static Vec3d equal(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        return res;
    }

    public static Vec3 equal_(Vec3 a, Vec3 b) {
        return equal(a, b, new Vec3());
    }

    public static Vec3 equal(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        return res;
    }

    public static Vec3i equal_(Vec3i a, Vec3i b) {
        return equal(a, b, new Vec3i());
    }

    public static Vec3i equal(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        return res;
    }

    public static Vec3l equal_(Vec3l a, Vec3l b) {
        return equal(a, b, new Vec3l());
    }

    public static Vec3l equal(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        return res;
    }

    public static Vec3s equal_(Vec3s a, Vec3s b) {
        return equal(a, b, new Vec3s());
    }

    public static Vec3s equal(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x == b.x ? 1 : 0);
        res.y = (short) (a.y == b.y ? 1 : 0);
        res.z = (short) (a.z == b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub equal_(Vec3ub a, Vec3ub b) {
        return equal(a, b, new Vec3ub());
    }

    public static Vec3ub equal(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) == 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui equal_(Vec3ui a, Vec3ui b) {
        return equal(a, b, new Vec3ui());
    }

    public static Vec3ui equal(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) == 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul equal_(Vec3ul a, Vec3ul b) {
        return equal(a, b, new Vec3ul());
    }

    public static Vec3ul equal(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) == 0 ? 1 : 0;
        return res;
    }

    public static Vec3us equal_(Vec3us a, Vec3us b) {
        return equal(a, b, new Vec3us());
    }

    public static Vec3us equal(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) == 0 ? 1 : 0);
        return res;
    }

    // equal (vec4) ------------------------------------------------------------
    public static Vec4bool equal__(Vec4b a, Vec4b b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4d a, Vec4d b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4 a, Vec4 b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4i a, Vec4i b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4l a, Vec4l b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4s a, Vec4s b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x == b.x;
        res.y = a.y == b.y;
        res.z = a.z == b.z;
        res.w = a.w == b.w;
        return res;
    }

    public static Vec4bool equal__(Vec4ub a, Vec4ub b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        res.w = a.w.compareTo(b.w) == 0;
        return res;
    }

    public static Vec4bool equal__(Vec4ui a, Vec4ui b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        res.w = a.w.compareTo(b.w) == 0;
        return res;
    }

    public static Vec4bool equal__(Vec4ul a, Vec4ul b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        res.w = a.w.compareTo(b.w) == 0;
        return res;
    }

    public static Vec4bool equal__(Vec4us a, Vec4us b) {
        return equal(a, b, new Vec4bool());
    }

    public static Vec4bool equal(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) == 0;
        res.y = a.y.compareTo(b.y) == 0;
        res.z = a.z.compareTo(b.z) == 0;
        res.w = a.w.compareTo(b.w) == 0;
        return res;
    }

    public static Vec4b equal_(Vec4b a, Vec4b b) {
        return equal(a, b, new Vec4b());
    }

    public static Vec4b equal(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x == b.x ? 1 : 0);
        res.y = (byte) (a.y == b.y ? 1 : 0);
        res.z = (byte) (a.z == b.z ? 1 : 0);
        res.w = (byte) (a.w == b.w ? 1 : 0);
        return res;
    }

    public static Vec4d equal_(Vec4d a, Vec4d b) {
        return equal(a, b, new Vec4d());
    }

    public static Vec4d equal(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        res.w = a.w == b.w ? 1 : 0;
        return res;
    }

    public static Vec4 equal_(Vec4 a, Vec4 b) {
        return equal(a, b, new Vec4());
    }

    public static Vec4 equal(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        res.w = a.w == b.w ? 1 : 0;
        return res;
    }

    public static Vec4i equal_(Vec4i a, Vec4i b) {
        return equal(a, b, new Vec4i());
    }

    public static Vec4i equal(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        res.w = a.w == b.w ? 1 : 0;
        return res;
    }

    public static Vec4l equal_(Vec4l a, Vec4l b) {
        return equal(a, b, new Vec4l());
    }

    public static Vec4l equal(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x == b.x ? 1 : 0;
        res.y = a.y == b.y ? 1 : 0;
        res.z = a.z == b.z ? 1 : 0;
        res.w = a.w == b.w ? 1 : 0;
        return res;
    }

    public static Vec4s equal_(Vec4s a, Vec4s b) {
        return equal(a, b, new Vec4s());
    }

    public static Vec4s equal(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x == b.x ? 1 : 0);
        res.y = (short) (a.y == b.y ? 1 : 0);
        res.z = (short) (a.z == b.z ? 1 : 0);
        res.w = (short) (a.w == b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub equal_(Vec4ub a, Vec4ub b) {
        return equal(a, b, new Vec4ub());
    }

    public static Vec4ub equal(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) == 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) == 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui equal_(Vec4ui a, Vec4ui b) {
        return equal(a, b, new Vec4ui());
    }

    public static Vec4ui equal(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) == 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) == 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul equal_(Vec4ul a, Vec4ul b) {
        return equal(a, b, new Vec4ul());
    }

    public static Vec4ul equal(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) == 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) == 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) == 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) == 0 ? 1 : 0;
        return res;
    }

    public static Vec4us equal_(Vec4us a, Vec4us b) {
        return equal(a, b, new Vec4us());
    }

    public static Vec4us equal(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) == 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) == 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) == 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) == 0 ? 1 : 0);
        return res;
    }

    // notEqual -----------------------------------------------------------------------------------------------------
    public static boolean notEqual(byte a, byte b) {
        return a != b;
    }

    public static boolean notEqual(double a, double b) {
        return a != b;
    }

    public static boolean notEqual(float a, float b) {
        return a != b;
    }

    public static boolean notEqual(int a, int b) {
        return a != b;
    }

    public static boolean notEqual(long a, long b) {
        return a != b;
    }

    public static boolean notEqual(short a, short b) {
        return a != b;
    }

    public static boolean notEqual(UByte a, UByte b) {
        return a.compareTo(b) != 0;
    }

    public static boolean notEqual(UInt a, UInt b) {
        return a.compareTo(b) != 0;
    }

    public static boolean notEqual(ULong a, ULong b) {
        return a.compareTo(b) != 0;
    }

    public static boolean notEqual(UShort a, UShort b) {
        return a.compareTo(b) != 0;
    }

    // notEqual (vec2) ---------------------------------------------------------
    public static Vec2bool notEqual__(Vec2b a, Vec2b b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2b a, Vec2b b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2d a, Vec2d b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2d a, Vec2d b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2 a, Vec2 b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2 a, Vec2 b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2i a, Vec2i b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2i a, Vec2i b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2l a, Vec2l b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2l a, Vec2l b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2s a, Vec2s b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2s a, Vec2s b, Vec2bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        return res;
    }

    public static Vec2bool notEqual__(Vec2ub a, Vec2ub b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2ub a, Vec2ub b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        return res;
    }

    public static Vec2bool notEqual__(Vec2ui a, Vec2ui b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2ui a, Vec2ui b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        return res;
    }

    public static Vec2bool notEqual__(Vec2ul a, Vec2ul b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2ul a, Vec2ul b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        return res;
    }

    public static Vec2bool notEqual__(Vec2us a, Vec2us b) {
        return notEqual(a, b, new Vec2bool());
    }

    public static Vec2bool notEqual(Vec2us a, Vec2us b, Vec2bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        return res;
    }

    public static Vec2b notEqual_(Vec2b a, Vec2b b) {
        return notEqual(a, b, new Vec2b());
    }

    public static Vec2b notEqual(Vec2b a, Vec2b b, Vec2b res) {
        res.x = (byte) (a.x != b.x ? 1 : 0);
        res.y = (byte) (a.y != b.y ? 1 : 0);
        return res;
    }

    public static Vec2d notEqual_(Vec2d a, Vec2d b) {
        return notEqual(a, b, new Vec2d());
    }

    public static Vec2d notEqual(Vec2d a, Vec2d b, Vec2d res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        return res;
    }

    public static Vec2 notEqual_(Vec2 a, Vec2 b) {
        return notEqual(a, b, new Vec2());
    }

    public static Vec2 notEqual(Vec2 a, Vec2 b, Vec2 res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        return res;
    }

    public static Vec2i notEqual_(Vec2i a, Vec2i b) {
        return notEqual(a, b, new Vec2i());
    }

    public static Vec2i notEqual(Vec2i a, Vec2i b, Vec2i res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        return res;
    }

    public static Vec2l notEqual_(Vec2l a, Vec2l b) {
        return notEqual(a, b, new Vec2l());
    }

    public static Vec2l notEqual(Vec2l a, Vec2l b, Vec2l res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        return res;
    }

    public static Vec2s notEqual_(Vec2s a, Vec2s b) {
        return notEqual(a, b, new Vec2s());
    }

    public static Vec2s notEqual(Vec2s a, Vec2s b, Vec2s res) {
        res.x = (short) (a.x != b.x ? 1 : 0);
        res.y = (short) (a.y != b.y ? 1 : 0);
        return res;
    }

    public static Vec2ub notEqual_(Vec2ub a, Vec2ub b) {
        return notEqual(a, b, new Vec2ub());
    }

    public static Vec2ub notEqual(Vec2ub a, Vec2ub b, Vec2ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        return res;
    }

    public static Vec2ui notEqual_(Vec2ui a, Vec2ui b) {
        return notEqual(a, b, new Vec2ui());
    }

    public static Vec2ui notEqual(Vec2ui a, Vec2ui b, Vec2ui res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        return res;
    }

    public static Vec2ul notEqual_(Vec2ul a, Vec2ul b) {
        return notEqual(a, b, new Vec2ul());
    }

    public static Vec2ul notEqual(Vec2ul a, Vec2ul b, Vec2ul res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        return res;
    }

    public static Vec2us notEqual_(Vec2us a, Vec2us b) {
        return notEqual(a, b, new Vec2us());
    }

    public static Vec2us notEqual(Vec2us a, Vec2us b, Vec2us res) {
        res.x.value = (short) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        return res;
    }

    // notEqual (vec3) ---------------------------------------------------------
    public static Vec3bool notEqual__(Vec3b a, Vec3b b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3b a, Vec3b b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3d a, Vec3d b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3d a, Vec3d b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3 a, Vec3 b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3 a, Vec3 b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3i a, Vec3i b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3i a, Vec3i b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3l a, Vec3l b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3l a, Vec3l b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3s a, Vec3s b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3s a, Vec3s b, Vec3bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        return res;
    }

    public static Vec3bool notEqual__(Vec3ub a, Vec3ub b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3ub a, Vec3ub b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        return res;
    }

    public static Vec3bool notEqual__(Vec3ui a, Vec3ui b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3ui a, Vec3ui b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        return res;
    }

    public static Vec3bool notEqual__(Vec3ul a, Vec3ul b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3ul a, Vec3ul b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        return res;
    }

    public static Vec3bool notEqual__(Vec3us a, Vec3us b) {
        return notEqual(a, b, new Vec3bool());
    }

    public static Vec3bool notEqual(Vec3us a, Vec3us b, Vec3bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        return res;
    }

    public static Vec3b notEqual_(Vec3b a, Vec3b b) {
        return notEqual(a, b, new Vec3b());
    }

    public static Vec3b notEqual(Vec3b a, Vec3b b, Vec3b res) {
        res.x = (byte) (a.x != b.x ? 1 : 0);
        res.y = (byte) (a.y != b.y ? 1 : 0);
        res.z = (byte) (a.z != b.z ? 1 : 0);
        return res;
    }

    public static Vec3d notEqual_(Vec3d a, Vec3d b) {
        return notEqual(a, b, new Vec3d());
    }

    public static Vec3d notEqual(Vec3d a, Vec3d b, Vec3d res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        return res;
    }

    public static Vec3 notEqual_(Vec3 a, Vec3 b) {
        return notEqual(a, b, new Vec3());
    }

    public static Vec3 notEqual(Vec3 a, Vec3 b, Vec3 res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        return res;
    }

    public static Vec3i notEqual_(Vec3i a, Vec3i b) {
        return notEqual(a, b, new Vec3i());
    }

    public static Vec3i notEqual(Vec3i a, Vec3i b, Vec3i res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        return res;
    }

    public static Vec3l notEqual_(Vec3l a, Vec3l b) {
        return notEqual(a, b, new Vec3l());
    }

    public static Vec3l notEqual(Vec3l a, Vec3l b, Vec3l res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        return res;
    }

    public static Vec3s notEqual_(Vec3s a, Vec3s b) {
        return notEqual(a, b, new Vec3s());
    }

    public static Vec3s notEqual(Vec3s a, Vec3s b, Vec3s res) {
        res.x = (short) (a.x != b.x ? 1 : 0);
        res.y = (short) (a.y != b.y ? 1 : 0);
        res.z = (short) (a.z != b.z ? 1 : 0);
        return res;
    }

    public static Vec3ub notEqual_(Vec3ub a, Vec3ub b) {
        return notEqual(a, b, new Vec3ub());
    }

    public static Vec3ub notEqual(Vec3ub a, Vec3ub b, Vec3ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) != 0 ? 1 : 0);
        return res;
    }

    public static Vec3ui notEqual_(Vec3ui a, Vec3ui b) {
        return notEqual(a, b, new Vec3ui());
    }

    public static Vec3ui notEqual(Vec3ui a, Vec3ui b, Vec3ui res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) != 0 ? 1 : 0;
        return res;
    }

    public static Vec3ul notEqual_(Vec3ul a, Vec3ul b) {
        return notEqual(a, b, new Vec3ul());
    }

    public static Vec3ul notEqual(Vec3ul a, Vec3ul b, Vec3ul res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) != 0 ? 1 : 0;
        return res;
    }

    public static Vec3us notEqual_(Vec3us a, Vec3us b) {
        return notEqual(a, b, new Vec3us());
    }

    public static Vec3us notEqual(Vec3us a, Vec3us b, Vec3us res) {
        res.x.value = (short) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) != 0 ? 1 : 0);
        return res;
    }

    // notEqual (vec4) ---------------------------------------------------------
    public static Vec4bool notEqual__(Vec4b a, Vec4b b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4b a, Vec4b b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4d a, Vec4d b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4d a, Vec4d b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4 a, Vec4 b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4 a, Vec4 b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4i a, Vec4i b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4i a, Vec4i b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4l a, Vec4l b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4l a, Vec4l b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4s a, Vec4s b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4s a, Vec4s b, Vec4bool res) {
        res.x = a.x != b.x;
        res.y = a.y != b.y;
        res.z = a.z != b.z;
        res.w = a.w != b.w;
        return res;
    }

    public static Vec4bool notEqual__(Vec4ub a, Vec4ub b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4ub a, Vec4ub b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        res.w = a.w.compareTo(b.w) != 0;
        return res;
    }

    public static Vec4bool notEqual__(Vec4ui a, Vec4ui b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4ui a, Vec4ui b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        res.w = a.w.compareTo(b.w) != 0;
        return res;
    }

    public static Vec4bool notEqual__(Vec4ul a, Vec4ul b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4ul a, Vec4ul b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        res.w = a.w.compareTo(b.w) != 0;
        return res;
    }

    public static Vec4bool notEqual__(Vec4us a, Vec4us b) {
        return notEqual(a, b, new Vec4bool());
    }

    public static Vec4bool notEqual(Vec4us a, Vec4us b, Vec4bool res) {
        res.x = a.x.compareTo(b.x) != 0;
        res.y = a.y.compareTo(b.y) != 0;
        res.z = a.z.compareTo(b.z) != 0;
        res.w = a.w.compareTo(b.w) != 0;
        return res;
    }

    public static Vec4b notEqual_(Vec4b a, Vec4b b) {
        return notEqual(a, b, new Vec4b());
    }

    public static Vec4b notEqual(Vec4b a, Vec4b b, Vec4b res) {
        res.x = (byte) (a.x != b.x ? 1 : 0);
        res.y = (byte) (a.y != b.y ? 1 : 0);
        res.z = (byte) (a.z != b.z ? 1 : 0);
        res.w = (byte) (a.w != b.w ? 1 : 0);
        return res;
    }

    public static Vec4d notEqual_(Vec4d a, Vec4d b) {
        return notEqual(a, b, new Vec4d());
    }

    public static Vec4d notEqual(Vec4d a, Vec4d b, Vec4d res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        res.w = a.w != b.w ? 1 : 0;
        return res;
    }

    public static Vec4 notEqual_(Vec4 a, Vec4 b) {
        return notEqual(a, b, new Vec4());
    }

    public static Vec4 notEqual(Vec4 a, Vec4 b, Vec4 res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        res.w = a.w != b.w ? 1 : 0;
        return res;
    }

    public static Vec4i notEqual_(Vec4i a, Vec4i b) {
        return notEqual(a, b, new Vec4i());
    }

    public static Vec4i notEqual(Vec4i a, Vec4i b, Vec4i res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        res.w = a.w != b.w ? 1 : 0;
        return res;
    }

    public static Vec4l notEqual_(Vec4l a, Vec4l b) {
        return notEqual(a, b, new Vec4l());
    }

    public static Vec4l notEqual(Vec4l a, Vec4l b, Vec4l res) {
        res.x = a.x != b.x ? 1 : 0;
        res.y = a.y != b.y ? 1 : 0;
        res.z = a.z != b.z ? 1 : 0;
        res.w = a.w != b.w ? 1 : 0;
        return res;
    }

    public static Vec4s notEqual_(Vec4s a, Vec4s b) {
        return notEqual(a, b, new Vec4s());
    }

    public static Vec4s notEqual(Vec4s a, Vec4s b, Vec4s res) {
        res.x = (short) (a.x != b.x ? 1 : 0);
        res.y = (short) (a.y != b.y ? 1 : 0);
        res.z = (short) (a.z != b.z ? 1 : 0);
        res.w = (short) (a.w != b.w ? 1 : 0);
        return res;
    }

    public static Vec4ub notEqual_(Vec4ub a, Vec4ub b) {
        return notEqual(a, b, new Vec4ub());
    }

    public static Vec4ub notEqual(Vec4ub a, Vec4ub b, Vec4ub res) {
        res.x.value = (byte) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (byte) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        res.z.value = (byte) (a.z.compareTo(b.z) != 0 ? 1 : 0);
        res.w.value = (byte) (a.w.compareTo(b.w) != 0 ? 1 : 0);
        return res;
    }

    public static Vec4ui notEqual_(Vec4ui a, Vec4ui b) {
        return notEqual(a, b, new Vec4ui());
    }

    public static Vec4ui notEqual(Vec4ui a, Vec4ui b, Vec4ui res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) != 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) != 0 ? 1 : 0;
        return res;
    }

    public static Vec4ul notEqual_(Vec4ul a, Vec4ul b) {
        return notEqual(a, b, new Vec4ul());
    }

    public static Vec4ul notEqual(Vec4ul a, Vec4ul b, Vec4ul res) {
        res.x.value = a.x.compareTo(b.x) != 0 ? 1 : 0;
        res.y.value = a.y.compareTo(b.y) != 0 ? 1 : 0;
        res.z.value = a.z.compareTo(b.z) != 0 ? 1 : 0;
        res.w.value = a.w.compareTo(b.w) != 0 ? 1 : 0;
        return res;
    }

    public static Vec4us notEqual_(Vec4us a, Vec4us b) {
        return notEqual(a, b, new Vec4us());
    }

    public static Vec4us notEqual(Vec4us a, Vec4us b, Vec4us res) {
        res.x.value = (short) (a.x.compareTo(b.x) != 0 ? 1 : 0);
        res.y.value = (short) (a.y.compareTo(b.y) != 0 ? 1 : 0);
        res.z.value = (short) (a.z.compareTo(b.z) != 0 ? 1 : 0);
        res.w.value = (short) (a.w.compareTo(b.w) != 0 ? 1 : 0);
        return res;
    }
}
