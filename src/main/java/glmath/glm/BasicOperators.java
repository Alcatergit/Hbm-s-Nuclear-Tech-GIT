/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.vec._2.Vec2;
import glmath.glm.vec._2.b.Vec2b;
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
abstract class BasicOperators extends SpecialOperators {

    public static Vec2 add_(Vec2 a, float b) {
        return add(new Vec2(), a, b, b);
    }

    public static Vec2 add_(Vec2 a, Vec2 b) {
        return add(new Vec2(), a, b.x, b.y);
    }

    public static Vec2 add(Vec2 a, float b) {
        return add(a, a, b, b);
    }

    public static Vec2 add(Vec2 a, Vec2 b) {
        return add(a, a, b.x, b.y);
    }

    public static Vec2 add(Vec2 res, Vec2 a, float b) {
        return add(res, a, b, b);
    }

    public static Vec2 add(Vec2 res, Vec2 a, Vec2 b) {
        return add(res, a, b.x, b.y);
    }

    public static Vec2 add(Vec2 res, Vec2 a, float bX, float bY) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        return res;
    }

    public static Vec2b add_(Vec2b a, byte b) {
        return add(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b add_(Vec2b a, int b) {
        return add(new Vec2b(), a, b, b);
    }

    public static Vec2b add_(Vec2b a, Vec2b b) {
        return add(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b add(Vec2b a, byte b) {
        return add(a, a, (int) b, b);
    }

    public static Vec2b add(Vec2b a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2b add(Vec2b a, Vec2b b) {
        return add(a, a, (int) b.x, b.y);
    }

    public static Vec2b add(Vec2b res, Vec2b a, byte b) {
        return add(res, a, (int) b, b);
    }

    public static Vec2b add(Vec2b res, Vec2b a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2b add(Vec2b res, Vec2b a, Vec2b b) {
        return add(res, a, (int) b.x, b.y);
    }

    public static Vec2b add(Vec2b res, Vec2b a, byte bX, byte bY) {
        return add(res, a, (int) bX, bY);
    }

    public static Vec2b add(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x + bX);
        res.y = (byte) (a.y + bY);
        return res;
    }

    public static Vec2d add_(Vec2d a, double b) {
        return add(new Vec2d(), a, b, b);
    }

    public static Vec2d add_(Vec2d a, Vec2d b) {
        return add(new Vec2d(), a, b.x, b.y);
    }

    public static Vec2d add(Vec2d a, double b) {
        return add(a, a, b, b);
    }

    public static Vec2d add(Vec2d a, Vec2d b) {
        return add(a, a, b.x, b.y);
    }

    public static Vec2d add(Vec2d res, Vec2d a, double b) {
        return add(res, a, b, b);
    }

    public static Vec2d add(Vec2d res, Vec2d a, Vec2d b) {
        return add(res, a, b.x, b.y);
    }

    public static Vec2d add(Vec2d res, Vec2d a, double bX, double bY) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        return res;
    }

    public static Vec2i add_(Vec2i a, int b) {
        return add(new Vec2i(), a, b, b);
    }

    public static Vec2i add_(Vec2i a, Vec2i b) {
        return add(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i add(Vec2i a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2i add(Vec2i a, Vec2i b) {
        return add(a, a, b.x, b.y);
    }

    public static Vec2i add(Vec2i res, Vec2i a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2i add(Vec2i res, Vec2i a, Vec2i b) {
        return add(res, a, b.x, b.y);
    }

    public static Vec2i add(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        return res;
    }

    public static Vec2l add_(Vec2l a, long b) {
        return add(new Vec2l(), a, b, b);
    }

    public static Vec2l add_(Vec2l a, Vec2l b) {
        return add(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l add(Vec2l a, long b) {
        return add(a, a, b, b);
    }

    public static Vec2l add(Vec2l a, Vec2l b) {
        return add(a, a, b.x, b.y);
    }

    public static Vec2l add(Vec2l res, Vec2l a, long b) {
        return add(res, a, b, b);
    }

    public static Vec2l add(Vec2l res, Vec2l a, Vec2l b) {
        return add(res, a, b.x, b.y);
    }

    public static Vec2l add(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        return res;
    }

    public static Vec2s add_(Vec2s a, short b) {
        return add(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s add_(Vec2s a, int b) {
        return add(new Vec2s(), a, b, b);
    }

    public static Vec2s add_(Vec2s a, Vec2s b) {
        return add(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s add(Vec2s a, short b) {
        return add(a, a, (int) b, b);
    }

    public static Vec2s add(Vec2s a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2s add(Vec2s a, Vec2s b) {
        return add(a, a, (int) b.x, b.y);
    }

    public static Vec2s add(Vec2s res, Vec2s a, short b) {
        return add(res, a, (int) b, b);
    }

    public static Vec2s add(Vec2s res, Vec2s a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2s add(Vec2s res, Vec2s a, Vec2s b) {
        return add(res, a, (int) b.x, b.y);
    }

    public static Vec2s add(Vec2s res, Vec2s a, short bX, short bY) {
        return add(res, a, (int) bX, bY);
    }

    public static Vec2s add(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x + bX);
        res.y = (short) (a.y + bY);
        return res;
    }

    public static Vec2ub add_(Vec2ub a, UByte b) {
        return add(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub add_(Vec2ub a, byte b) {
        return add(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub add_(Vec2ub a, int b) {
        return add(new Vec2ub(), a, b, b);
    }

    public static Vec2ub add_(Vec2ub a, Vec2ub b) {
        return add(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub add(Vec2ub a, UByte b) {
        return add(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub add(Vec2ub a, byte b) {
        return add(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub add(Vec2ub a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2ub add(Vec2ub a, Vec2ub b) {
        return add(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, UByte b) {
        return add(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, byte b) {
        return add(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, Vec2ub b) {
        return add(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return add(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return add(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub add(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) + bX);
        res.y.value = (byte) ((a.y.value & 0xff) + bY);
        return res;
    }

    public static Vec2ui add_(Vec2ui a, UInt b) {
        return add(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui add_(Vec2ui a, int b) {
        return add(new Vec2ui(), a, b, b);
    }

    public static Vec2ui add_(Vec2ui a, Vec2ui b) {
        return add(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui add(Vec2ui a, UInt b) {
        return add(a, a, b.value, b.value);
    }

    public static Vec2ui add(Vec2ui a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2ui add(Vec2ui a, Vec2ui b) {
        return add(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui add(Vec2ui res, Vec2ui a, UInt b) {
        return add(res, a, b.value, b.value);
    }

    public static Vec2ui add(Vec2ui res, Vec2ui a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2ui add(Vec2ui res, Vec2ui a, Vec2ui b) {
        return add(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui add(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return add(res, a, bX.value, bY.value);
    }

    public static Vec2ui add(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        return res;
    }

    public static Vec2ul add_(Vec2ul a, ULong b) {
        return add(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul add_(Vec2ul a, long b) {
        return add(new Vec2ul(), a, b, b);
    }

    public static Vec2ul add_(Vec2ul a, Vec2ul b) {
        return add(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul add(Vec2ul a, ULong b) {
        return add(a, a, b.value, b.value);
    }

    public static Vec2ul add(Vec2ul a, long b) {
        return add(a, a, b, b);
    }

    public static Vec2ul add(Vec2ul a, Vec2ul b) {
        return add(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul add(Vec2ul res, Vec2ul a, ULong b) {
        return add(res, a, b.value, b.value);
    }

    public static Vec2ul add(Vec2ul res, Vec2ul a, long b) {
        return add(res, a, b, b);
    }

    public static Vec2ul add(Vec2ul res, Vec2ul a, Vec2ul b) {
        return add(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul add(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return add(res, a, bX.value, bY.value);
    }

    public static Vec2ul add(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        return res;
    }

    public static Vec2us add_(Vec2us a, UShort b) {
        return add(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us add_(Vec2us a, short b) {
        return add(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us add_(Vec2us a, int b) {
        return add(new Vec2us(), a, b, b);
    }

    public static Vec2us add_(Vec2us a, Vec2us b) {
        return add(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us add(Vec2us a, UShort b) {
        return add(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us add(Vec2us a, short b) {
        return add(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us add(Vec2us a, int b) {
        return add(a, a, b, b);
    }

    public static Vec2us add(Vec2us a, Vec2us b) {
        return add(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, UShort b) {
        return add(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, short b) {
        return add(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, int b) {
        return add(res, a, b, b);
    }

    public static Vec2us add(Vec2us res, Vec2us a, Vec2us b) {
        return add(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return add(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, short bX, short bY) {
        return add(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us add(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) + bX);
        res.y.value = (short) ((a.y.value & 0xffff) + bY);
        return res;
    }

    public static Vec3 add_(Vec3 a, float b) {
        return add(new Vec3(), a, b, b, b);
    }

    public static Vec3 add_(Vec3 a, Vec3 b) {
        return add(new Vec3(), a, b.x, b.y, b.z);
    }

    public static Vec3 add(Vec3 a, float b) {
        return add(a, a, b, b, b);
    }

    public static Vec3 add(Vec3 a, Vec3 b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3 add(Vec3 res, Vec3 a, float b) {
        return add(res, a, b, b, b);
    }

    public static Vec3 add(Vec3 res, Vec3 a, Vec3 b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3 add(Vec3 res, Vec3 a, float bX, float bY, float bZ) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        return res;
    }

    public static Vec3b add_(Vec3b a, byte b) {
        return add(new Vec3b(), a, b, b, b);
    }

    public static Vec3b add_(Vec3b a, int b) {
        return add(new Vec3b(), a, b, b, b);
    }

    public static Vec3b add_(Vec3b a, Vec3b b) {
        return add(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b add(Vec3b a, byte b) {
        return add(a, a, b, b, b);
    }

    public static Vec3b add(Vec3b a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3b add(Vec3b a, Vec3b b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3b add(Vec3b res, Vec3b a, byte b) {
        return add(res, a, b, b, b);
    }

    public static Vec3b add(Vec3b res, Vec3b a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3b add(Vec3b res, Vec3b a, Vec3b b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3b add(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return add(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b add(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x + bX);
        res.y = (byte) (a.y + bY);
        res.z = (byte) (a.z + bZ);
        return res;
    }

    public static Vec3d add_(Vec3d a, double b) {
        return add(new Vec3d(), a, b, b, b);
    }

    public static Vec3d add_(Vec3d a, Vec3d b) {
        return add(new Vec3d(), a, b.x, b.y, b.z);
    }

    public static Vec3d add(Vec3d a, double b) {
        return add(a, a, b, b, b);
    }

    public static Vec3d add(Vec3d a, Vec3d b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3d add(Vec3d res, Vec3d a, double b) {
        return add(res, a, b, b, b);
    }

    public static Vec3d add(Vec3d res, Vec3d a, Vec3d b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3d add(Vec3d res, Vec3d a, double bX, double bY, double bZ) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        return res;
    }

    public static Vec3i add_(Vec3i a, int b) {
        return add(new Vec3i(), a, b, b, b);
    }

    public static Vec3i add_(Vec3i a, Vec3i b) {
        return add(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i add(Vec3i a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3i add(Vec3i a, Vec3i b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3i add(Vec3i res, Vec3i a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3i add(Vec3i res, Vec3i a, Vec3i b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3i add(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        return res;
    }

    public static Vec3l add_(Vec3l a, long b) {
        return add(new Vec3l(), a, b, b, b);
    }

    public static Vec3l add_(Vec3l a, Vec3l b) {
        return add(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l add(Vec3l a, long b) {
        return add(a, a, b, b, b);
    }

    public static Vec3l add(Vec3l a, Vec3l b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3l add(Vec3l res, Vec3l a, long b) {
        return add(res, a, b, b, b);
    }

    public static Vec3l add(Vec3l res, Vec3l a, Vec3l b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3l add(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        return res;
    }

    public static Vec3s add_(Vec3s a, short b) {
        return add(new Vec3s(), a, b, b, b);
    }

    public static Vec3s add_(Vec3s a, int b) {
        return add(new Vec3s(), a, b, b, b);
    }

    public static Vec3s add_(Vec3s a, Vec3s b) {
        return add(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s add(Vec3s a, short b) {
        return add(a, a, b, b, b);
    }

    public static Vec3s add(Vec3s a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3s add(Vec3s a, Vec3s b) {
        return add(a, a, b.x, b.y, b.z);
    }

    public static Vec3s add(Vec3s res, Vec3s a, short b) {
        return add(res, a, b, b, b);
    }

    public static Vec3s add(Vec3s res, Vec3s a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3s add(Vec3s res, Vec3s a, Vec3s b) {
        return add(res, a, b.x, b.y, b.z);
    }

    public static Vec3s add(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return add(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s add(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x + bX);
        res.y = (short) (a.y + bY);
        res.z = (short) (a.z + bZ);
        return res;
    }

    public static Vec3ub add_(Vec3ub a, UByte b) {
        return add(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub add_(Vec3ub a, byte b) {
        return add(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub add_(Vec3ub a, int b) {
        return add(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub add_(Vec3ub a, Vec3ub b) {
        return add(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub add(Vec3ub a, UByte b) {
        return add(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub add(Vec3ub a, byte b) {
        return add(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub add(Vec3ub a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3ub add(Vec3ub a, Vec3ub b) {
        return add(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, UByte b) {
        return add(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, byte b) {
        return add(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, Vec3ub b) {
        return add(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return add(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return add(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub add(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) + bX);
        res.y.value = (byte) ((a.y.value & 0xff) + bY);
        res.z.value = (byte) ((a.z.value & 0xff) + bZ);
        return res;
    }

    public static Vec3ui add_(Vec3ui a, UInt b) {
        return add(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui add_(Vec3ui a, int b) {
        return add(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui add_(Vec3ui a, Vec3ui b) {
        return add(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui add(Vec3ui a, UInt b) {
        return add(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui add(Vec3ui a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3ui add(Vec3ui a, Vec3ui b) {
        return add(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui add(Vec3ui res, Vec3ui a, UInt b) {
        return add(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui add(Vec3ui res, Vec3ui a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3ui add(Vec3ui res, Vec3ui a, Vec3ui b) {
        return add(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui add(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return add(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui add(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        res.z.value = a.z.value + bZ;
        return res;
    }

    public static Vec3ul add_(Vec3ul a, ULong b) {
        return add(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul add_(Vec3ul a, long b) {
        return add(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul add_(Vec3ul a, Vec3ul b) {
        return add(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul add(Vec3ul a, ULong b) {
        return add(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul add(Vec3ul a, long b) {
        return add(a, a, b, b, b);
    }

    public static Vec3ul add(Vec3ul a, Vec3ul b) {
        return add(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul add(Vec3ul res, Vec3ul a, ULong b) {
        return add(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul add(Vec3ul res, Vec3ul a, long b) {
        return add(res, a, b, b, b);
    }

    public static Vec3ul add(Vec3ul res, Vec3ul a, Vec3ul b) {
        return add(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul add(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return add(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul add(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        res.z.value = a.z.value + bZ;
        return res;
    }

    public static Vec3us add_(Vec3us a, UShort b) {
        return add(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us add_(Vec3us a, short b) {
        return add(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us add_(Vec3us a, int b) {
        return add(new Vec3us(), a, b, b, b);
    }

    public static Vec3us add_(Vec3us a, Vec3us b) {
        return add(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us add(Vec3us a, UShort b) {
        return add(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us add(Vec3us a, short b) {
        return add(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us add(Vec3us a, int b) {
        return add(a, a, b, b, b);
    }

    public static Vec3us add(Vec3us a, Vec3us b) {
        return add(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, UShort b) {
        return add(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, short b) {
        return add(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, int b) {
        return add(res, a, b, b, b);
    }

    public static Vec3us add(Vec3us res, Vec3us a, Vec3us b) {
        return add(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return add(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return add(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us add(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) + bX);
        res.y.value = (short) ((a.y.value & 0xffff) + bY);
        res.z.value = (short) ((a.z.value & 0xffff) + bZ);
        return res;
    }

    public static Vec4 add_(Vec4 a, float b) {
        return add(new Vec4(), a, b, b, b, b);
    }

    public static Vec4 add_(Vec4 a, Vec4 b) {
        return add(new Vec4(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 add(Vec4 a, float b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4 add(Vec4 a, Vec4 b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 add(Vec4 res, Vec4 a, float b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4 add(Vec4 res, Vec4 a, Vec4 b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 add(Vec4 res, Vec4 a, float bX, float bY, float bZ, float bW) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        res.w = a.w + bW;
        return res;
    }

    public static Vec4b add_(Vec4b a, byte b) {
        return add(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b add_(Vec4b a, int b) {
        return add(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b add_(Vec4b a, Vec4b b) {
        return add(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b add(Vec4b a, byte b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4b add(Vec4b a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4b add(Vec4b a, Vec4b b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b add(Vec4b res, Vec4b a, byte b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4b add(Vec4b res, Vec4b a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4b add(Vec4b res, Vec4b a, Vec4b b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b add(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return add(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b add(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x + bX);
        res.y = (byte) (a.y + bY);
        res.z = (byte) (a.z + bZ);
        res.w = (byte) (a.w + bW);
        return res;
    }

    public static Vec4d add_(Vec4d a, double b) {
        return add(new Vec4d(), a, b, b, b, b);
    }

    public static Vec4d add_(Vec4d a, Vec4d b) {
        return add(new Vec4d(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d add(Vec4d a, double b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4d add(Vec4d a, Vec4d b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d add(Vec4d res, Vec4d a, double b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4d add(Vec4d res, Vec4d a, Vec4d b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d add(Vec4d res, Vec4d a, double bX, double bY, double bZ, double bW) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        res.w = a.w + bW;
        return res;
    }

    public static Vec4i add_(Vec4i a, int b) {
        return add(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i add_(Vec4i a, Vec4i b) {
        return add(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i add(Vec4i a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4i add(Vec4i a, Vec4i b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i add(Vec4i res, Vec4i a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4i add(Vec4i res, Vec4i a, Vec4i b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i add(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        res.w = a.w + bW;
        return res;
    }

    public static Vec4l add_(Vec4l a, long b) {
        return add(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l add_(Vec4l a, Vec4l b) {
        return add(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l add(Vec4l a, long b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4l add(Vec4l a, Vec4l b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l add(Vec4l res, Vec4l a, long b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4l add(Vec4l res, Vec4l a, Vec4l b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l add(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x + bX;
        res.y = a.y + bY;
        res.z = a.z + bZ;
        res.w = a.w + bW;
        return res;
    }

    public static Vec4s add_(Vec4s a, short b) {
        return add(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s add_(Vec4s a, int b) {
        return add(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s add_(Vec4s a, Vec4s b) {
        return add(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s add(Vec4s a, short b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4s add(Vec4s a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4s add(Vec4s a, Vec4s b) {
        return add(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s add(Vec4s res, Vec4s a, short b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4s add(Vec4s res, Vec4s a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4s add(Vec4s res, Vec4s a, Vec4s b) {
        return add(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s add(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return add(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s add(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x + bX);
        res.y = (short) (a.y + bY);
        res.z = (short) (a.z + bZ);
        res.w = (short) (a.w + bW);
        return res;
    }

    public static Vec4ub add_(Vec4ub a, UByte b) {
        return add(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub add_(Vec4ub a, byte b) {
        return add(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub add_(Vec4ub a, int b) {
        return add(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub add_(Vec4ub a, Vec4ub b) {
        return add(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub add(Vec4ub a, UByte b) {
        return add(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub add(Vec4ub a, byte b) {
        return add(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub add(Vec4ub a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4ub add(Vec4ub a, Vec4ub b) {
        return add(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, UByte b) {
        return add(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, byte b) {
        return add(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, Vec4ub b) {
        return add(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return add(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return add(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub add(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) + bX);
        res.y.value = (byte) ((a.y.value & 0xff) + bY);
        res.z.value = (byte) ((a.z.value & 0xff) + bZ);
        res.w.value = (byte) ((a.w.value & 0xff) + bW);
        return res;
    }

    public static Vec4ui add_(Vec4ui a, UInt b) {
        return add(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui add_(Vec4ui a, int b) {
        return add(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui add_(Vec4ui a, Vec4ui b) {
        return add(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui add(Vec4ui a, UInt b) {
        return add(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui add(Vec4ui a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4ui add(Vec4ui a, Vec4ui b) {
        return add(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui add(Vec4ui res, Vec4ui a, UInt b) {
        return add(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui add(Vec4ui res, Vec4ui a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4ui add(Vec4ui res, Vec4ui a, Vec4ui b) {
        return add(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui add(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return add(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui add(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        res.z.value = a.z.value + bZ;
        res.w.value = a.w.value + bW;
        return res;
    }

    public static Vec4ul add_(Vec4ul a, ULong b) {
        return add(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul add_(Vec4ul a, long b) {
        return add(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul add_(Vec4ul a, Vec4ul b) {
        return add(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul add(Vec4ul a, ULong b) {
        return add(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul add(Vec4ul a, long b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4ul add(Vec4ul a, Vec4ul b) {
        return add(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul add(Vec4ul res, Vec4ul a, ULong b) {
        return add(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul add(Vec4ul res, Vec4ul a, long b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4ul add(Vec4ul res, Vec4ul a, Vec4ul b) {
        return add(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul add(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return add(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul add(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value + bX;
        res.y.value = a.y.value + bY;
        res.z.value = a.z.value + bZ;
        res.w.value = a.w.value + bW;
        return res;
    }

    public static Vec4us add_(Vec4us a, UShort b) {
        return add(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us add_(Vec4us a, short b) {
        return add(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us add_(Vec4us a, int b) {
        return add(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us add_(Vec4us a, Vec4us b) {
        return add(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us add(Vec4us a, UShort b) {
        return add(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us add(Vec4us a, short b) {
        return add(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us add(Vec4us a, int b) {
        return add(a, a, b, b, b, b);
    }

    public static Vec4us add(Vec4us a, Vec4us b) {
        return add(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, UShort b) {
        return add(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, short b) {
        return add(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, int b) {
        return add(res, a, b, b, b, b);
    }

    public static Vec4us add(Vec4us res, Vec4us a, Vec4us b) {
        return add(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return add(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return add(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us add(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) + bX);
        res.y.value = (short) ((a.y.value & 0xffff) + bY);
        res.z.value = (short) ((a.z.value & 0xffff) + bZ);
        res.w.value = (short) ((a.w.value & 0xffff) + bW);
        return res;
    }

    public static Vec2 sub_(Vec2 a, float b) {
        return sub(new Vec2(), a, b, b);
    }

    public static Vec2 sub_(Vec2 a, Vec2 b) {
        return sub(new Vec2(), a, b.x, b.y);
    }

    public static Vec2 sub(Vec2 a, float b) {
        return sub(a, a, b, b);
    }

    public static Vec2 sub(Vec2 a, Vec2 b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2 sub(Vec2 res, Vec2 a, float b) {
        return sub(res, a, b, b);
    }

    public static Vec2 sub(Vec2 res, Vec2 a, Vec2 b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2 sub(Vec2 res, Vec2 a, float bX, float bY) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        return res;
    }

    public static Vec2b sub_(Vec2b a, byte b) {
        return sub(new Vec2b(), a, b, b);
    }

    public static Vec2b sub_(Vec2b a, int b) {
        return sub(new Vec2b(), a, b, b);
    }

    public static Vec2b sub_(Vec2b a, Vec2b b) {
        return sub(new Vec2b(), a, b.x, b.y);
    }

    public static Vec2b sub(Vec2b a, byte b) {
        return sub(a, a, b, b);
    }

    public static Vec2b sub(Vec2b a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2b sub(Vec2b a, Vec2b b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2b sub(Vec2b res, Vec2b a, byte b) {
        return sub(res, a, b, b);
    }

    public static Vec2b sub(Vec2b res, Vec2b a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2b sub(Vec2b res, Vec2b a, Vec2b b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2b sub(Vec2b res, Vec2b a, byte bX, byte bY) {
        return sub(res, a, (int) bX, bY);
    }

    public static Vec2b sub(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x - bX);
        res.y = (byte) (a.y - bY);
        return res;
    }

    public static Vec2d sub_(Vec2d a, double b) {
        return sub(new Vec2d(), a, b, b);
    }

    public static Vec2d sub_(Vec2d a, Vec2d b) {
        return sub(new Vec2d(), a, b.x, b.y);
    }

    public static Vec2d sub(Vec2d a, double b) {
        return sub(a, a, b, b);
    }

    public static Vec2d sub(Vec2d a, Vec2d b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2d sub(Vec2d res, Vec2d a, double b) {
        return sub(res, a, b, b);
    }

    public static Vec2d sub(Vec2d res, Vec2d a, Vec2d b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2d sub(Vec2d res, Vec2d a, double bX, double bY) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        return res;
    }

    public static Vec2i sub_(Vec2i a, int b) {
        return sub(new Vec2i(), a, b, b);
    }

    public static Vec2i sub_(Vec2i a, Vec2i b) {
        return sub(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i sub(Vec2i a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2i sub(Vec2i a, Vec2i b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2i sub(Vec2i res, Vec2i a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2i sub(Vec2i res, Vec2i a, Vec2i b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2i sub(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        return res;
    }

    public static Vec2l sub_(Vec2l a, long b) {
        return sub(new Vec2l(), a, b, b);
    }

    public static Vec2l sub_(Vec2l a, Vec2l b) {
        return sub(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l sub(Vec2l a, long b) {
        return sub(a, a, b, b);
    }

    public static Vec2l sub(Vec2l a, Vec2l b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2l sub(Vec2l res, Vec2l a, long b) {
        return sub(res, a, b, b);
    }

    public static Vec2l sub(Vec2l res, Vec2l a, Vec2l b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2l sub(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        return res;
    }

    public static Vec2s sub_(Vec2s a, short b) {
        return sub(new Vec2s(), a, b, b);
    }

    public static Vec2s sub_(Vec2s a, int b) {
        return sub(new Vec2s(), a, b, b);
    }

    public static Vec2s sub_(Vec2s a, Vec2s b) {
        return sub(new Vec2s(), a, b.x, b.y);
    }

    public static Vec2s sub(Vec2s a, short b) {
        return sub(a, a, b, b);
    }

    public static Vec2s sub(Vec2s a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2s sub(Vec2s a, Vec2s b) {
        return sub(a, a, b.x, b.y);
    }

    public static Vec2s sub(Vec2s res, Vec2s a, short b) {
        return sub(res, a, b, b);
    }

    public static Vec2s sub(Vec2s res, Vec2s a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2s sub(Vec2s res, Vec2s a, Vec2s b) {
        return sub(res, a, b.x, b.y);
    }

    public static Vec2s sub(Vec2s res, Vec2s a, short bX, short bY) {
        return sub(res, a, (int) bX, bY);
    }

    public static Vec2s sub(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x - bX);
        res.y = (short) (a.y - bY);
        return res;
    }

    public static Vec2ub sub_(Vec2ub a, UByte b) {
        return sub(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub sub_(Vec2ub a, byte b) {
        return sub(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub sub_(Vec2ub a, int b) {
        return sub(new Vec2ub(), a, b, b);
    }

    public static Vec2ub sub_(Vec2ub a, Vec2ub b) {
        return sub(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub a, UByte b) {
        return sub(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub a, byte b) {
        return sub(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub sub(Vec2ub a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2ub sub(Vec2ub a, Vec2ub b) {
        return sub(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, UByte b) {
        return sub(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, byte b) {
        return sub(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, Vec2ub b) {
        return sub(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return sub(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return sub(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub sub(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) - bX);
        res.y.value = (byte) ((a.y.value & 0xff) - bY);
        return res;
    }

    public static Vec2ui sub_(Vec2ui a, UInt b) {
        return sub(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui sub_(Vec2ui a, int b) {
        return sub(new Vec2ui(), a, b, b);
    }

    public static Vec2ui sub_(Vec2ui a, Vec2ui b) {
        return sub(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui sub(Vec2ui a, UInt b) {
        return sub(a, a, b.value, b.value);
    }

    public static Vec2ui sub(Vec2ui a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2ui sub(Vec2ui a, Vec2ui b) {
        return sub(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui sub(Vec2ui res, Vec2ui a, UInt b) {
        return sub(res, a, b.value, b.value);
    }

    public static Vec2ui sub(Vec2ui res, Vec2ui a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2ui sub(Vec2ui res, Vec2ui a, Vec2ui b) {
        return sub(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui sub(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return sub(res, a, bX.value, bY.value);
    }

    public static Vec2ui sub(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        return res;
    }

    public static Vec2ul sub_(Vec2ul a, ULong b) {
        return sub(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul sub_(Vec2ul a, long b) {
        return sub(new Vec2ul(), a, b, b);
    }

    public static Vec2ul sub_(Vec2ul a, Vec2ul b) {
        return sub(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul sub(Vec2ul a, ULong b) {
        return sub(a, a, b.value, b.value);
    }

    public static Vec2ul sub(Vec2ul a, long b) {
        return sub(a, a, b, b);
    }

    public static Vec2ul sub(Vec2ul a, Vec2ul b) {
        return sub(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul sub(Vec2ul res, Vec2ul a, ULong b) {
        return sub(res, a, b.value, b.value);
    }

    public static Vec2ul sub(Vec2ul res, Vec2ul a, long b) {
        return sub(res, a, b, b);
    }

    public static Vec2ul sub(Vec2ul res, Vec2ul a, Vec2ul b) {
        return sub(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul sub(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return sub(res, a, bX.value, bY.value);
    }

    public static Vec2ul sub(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        return res;
    }

    public static Vec2us sub_(Vec2us a, UShort b) {
        return sub(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us sub_(Vec2us a, short b) {
        return sub(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us sub_(Vec2us a, int b) {
        return sub(new Vec2us(), a, b, b);
    }

    public static Vec2us sub_(Vec2us a, Vec2us b) {
        return sub(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us sub(Vec2us a, UShort b) {
        return sub(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us sub(Vec2us a, short b) {
        return sub(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us sub(Vec2us a, int b) {
        return sub(a, a, b, b);
    }

    public static Vec2us sub(Vec2us a, Vec2us b) {
        return sub(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, UShort b) {
        return sub(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, short b) {
        return sub(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, int b) {
        return sub(res, a, b, b);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, Vec2us b) {
        return sub(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return sub(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, short bX, short bY) {
        return sub(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us sub(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) - bX);
        res.y.value = (short) ((a.y.value & 0xffff) - bY);
        return res;
    }

    public static Vec3 sub_(Vec3 a, float b) {
        return sub(new Vec3(), a, b, b, b);
    }

    public static Vec3 sub_(Vec3 a, Vec3 b) {
        return sub(new Vec3(), a, b.x, b.y, b.z);
    }

    public static Vec3 sub(Vec3 a, float b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3 sub(Vec3 a, Vec3 b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3 sub(Vec3 res, Vec3 a, float b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3 sub(Vec3 res, Vec3 a, Vec3 b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3 sub(Vec3 res, Vec3 a, float bX, float bY, float bZ) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        return res;
    }

    public static Vec3b sub_(Vec3b a, byte b) {
        return sub(new Vec3b(), a, b, b, b);
    }

    public static Vec3b sub_(Vec3b a, int b) {
        return sub(new Vec3b(), a, b, b, b);
    }

    public static Vec3b sub_(Vec3b a, Vec3b b) {
        return sub(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b sub(Vec3b a, byte b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3b sub(Vec3b a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3b sub(Vec3b a, Vec3b b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3b sub(Vec3b res, Vec3b a, byte b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3b sub(Vec3b res, Vec3b a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3b sub(Vec3b res, Vec3b a, Vec3b b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3b sub(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return sub(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b sub(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x - bX);
        res.y = (byte) (a.y - bY);
        res.z = (byte) (a.z - bZ);
        return res;
    }

    public static Vec3d sub_(Vec3d a, double b) {
        return sub(new Vec3d(), a, b, b, b);
    }

    public static Vec3d sub_(Vec3d a, Vec3d b) {
        return sub(new Vec3d(), a, b.x, b.y, b.z);
    }

    public static Vec3d sub(Vec3d a, double b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3d sub(Vec3d a, Vec3d b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3d sub(Vec3d res, Vec3d a, double b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3d sub(Vec3d res, Vec3d a, Vec3d b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3d sub(Vec3d res, Vec3d a, double bX, double bY, double bZ) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        return res;
    }

    public static Vec3i sub_(Vec3i a, int b) {
        return sub(new Vec3i(), a, b, b, b);
    }

    public static Vec3i sub_(Vec3i a, Vec3i b) {
        return sub(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i sub(Vec3i a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3i sub(Vec3i a, Vec3i b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3i sub(Vec3i res, Vec3i a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3i sub(Vec3i res, Vec3i a, Vec3i b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3i sub(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        return res;
    }

    public static Vec3l sub_(Vec3l a, long b) {
        return sub(new Vec3l(), a, b, b, b);
    }

    public static Vec3l sub_(Vec3l a, Vec3l b) {
        return sub(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l sub(Vec3l a, long b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3l sub(Vec3l a, Vec3l b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3l sub(Vec3l res, Vec3l a, long b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3l sub(Vec3l res, Vec3l a, Vec3l b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3l sub(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        return res;
    }

    public static Vec3s sub_(Vec3s a, short b) {
        return sub(new Vec3s(), a, b, b, b);
    }

    public static Vec3s sub_(Vec3s a, int b) {
        return sub(new Vec3s(), a, b, b, b);
    }

    public static Vec3s sub_(Vec3s a, Vec3s b) {
        return sub(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s sub(Vec3s a, short b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3s sub(Vec3s a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3s sub(Vec3s a, Vec3s b) {
        return sub(a, a, b.x, b.y, b.z);
    }

    public static Vec3s sub(Vec3s res, Vec3s a, short b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3s sub(Vec3s res, Vec3s a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3s sub(Vec3s res, Vec3s a, Vec3s b) {
        return sub(res, a, b.x, b.y, b.z);
    }

    public static Vec3s sub(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return sub(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s sub(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x - bX);
        res.y = (short) (a.y - bY);
        res.z = (short) (a.z - bZ);
        return res;
    }

    public static Vec3ub sub_(Vec3ub a, UByte b) {
        return sub(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub sub_(Vec3ub a, byte b) {
        return sub(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub sub_(Vec3ub a, int b) {
        return sub(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub sub_(Vec3ub a, Vec3ub b) {
        return sub(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub a, UByte b) {
        return sub(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub a, byte b) {
        return sub(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub sub(Vec3ub a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3ub sub(Vec3ub a, Vec3ub b) {
        return sub(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, UByte b) {
        return sub(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, byte b) {
        return sub(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, Vec3ub b) {
        return sub(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return sub(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return sub(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub sub(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) - bX);
        res.y.value = (byte) ((a.y.value & 0xff) - bY);
        res.z.value = (byte) ((a.z.value & 0xff) - bZ);
        return res;
    }

    public static Vec3ui sub_(Vec3ui a, UInt b) {
        return sub(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui sub_(Vec3ui a, int b) {
        return sub(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui sub_(Vec3ui a, Vec3ui b) {
        return sub(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui sub(Vec3ui a, UInt b) {
        return sub(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui sub(Vec3ui a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3ui sub(Vec3ui a, Vec3ui b) {
        return sub(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui sub(Vec3ui res, Vec3ui a, UInt b) {
        return sub(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui sub(Vec3ui res, Vec3ui a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3ui sub(Vec3ui res, Vec3ui a, Vec3ui b) {
        return sub(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui sub(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return sub(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui sub(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        res.z.value = a.z.value - bZ;
        return res;
    }

    public static Vec3ul sub_(Vec3ul a, ULong b) {
        return sub(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul sub_(Vec3ul a, long b) {
        return sub(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul sub_(Vec3ul a, Vec3ul b) {
        return sub(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul sub(Vec3ul a, ULong b) {
        return sub(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul sub(Vec3ul a, long b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3ul sub(Vec3ul a, Vec3ul b) {
        return sub(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul sub(Vec3ul res, Vec3ul a, ULong b) {
        return sub(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul sub(Vec3ul res, Vec3ul a, long b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3ul sub(Vec3ul res, Vec3ul a, Vec3ul b) {
        return sub(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul sub(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return sub(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul sub(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        res.z.value = a.z.value - bZ;
        return res;
    }

    public static Vec3us sub_(Vec3us a, UShort b) {
        return sub(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us sub_(Vec3us a, short b) {
        return sub(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us sub_(Vec3us a, int b) {
        return sub(new Vec3us(), a, b, b, b);
    }

    public static Vec3us sub_(Vec3us a, Vec3us b) {
        return sub(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us sub(Vec3us a, UShort b) {
        return sub(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us sub(Vec3us a, short b) {
        return sub(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us sub(Vec3us a, int b) {
        return sub(a, a, b, b, b);
    }

    public static Vec3us sub(Vec3us a, Vec3us b) {
        return sub(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, UShort b) {
        return sub(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, short b) {
        return sub(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, int b) {
        return sub(res, a, b, b, b);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, Vec3us b) {
        return sub(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return sub(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return sub(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us sub(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) - bX);
        res.y.value = (short) ((a.y.value & 0xffff) - bY);
        res.z.value = (short) ((a.z.value & 0xffff) - bZ);
        return res;
    }

    public static Vec4 sub_(Vec4 a, float b) {
        return sub(new Vec4(), a, b, b, b, b);
    }

    public static Vec4 sub_(Vec4 a, Vec4 b) {
        return sub(new Vec4(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 sub(Vec4 a, float b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4 sub(Vec4 a, Vec4 b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 sub(Vec4 res, Vec4 a, float b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4 sub(Vec4 res, Vec4 a, Vec4 b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 sub(Vec4 res, Vec4 a, float bX, float bY, float bZ, float bW) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        res.w = a.w - bW;
        return res;
    }

    public static Vec4b sub_(Vec4b a, byte b) {
        return sub(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b sub_(Vec4b a, int b) {
        return sub(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b sub_(Vec4b a, Vec4b b) {
        return sub(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b sub(Vec4b a, byte b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4b sub(Vec4b a, int b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4b sub(Vec4b a, Vec4b b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b sub(Vec4b res, Vec4b a, byte b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4b sub(Vec4b res, Vec4b a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4b sub(Vec4b res, Vec4b a, Vec4b b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b sub(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return sub(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b sub(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x - bX);
        res.y = (byte) (a.y - bY);
        res.z = (byte) (a.z - bZ);
        res.w = (byte) (a.w - bW);
        return res;
    }

    public static Vec4d sub_(Vec4d a, double b) {
        return sub(new Vec4d(), a, b, b, b, b);
    }

    public static Vec4d sub_(Vec4d a, Vec4d b) {
        return sub(new Vec4d(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d sub(Vec4d a, double b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4d sub(Vec4d a, Vec4d b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d sub(Vec4d res, Vec4d a, double b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4d sub(Vec4d res, Vec4d a, Vec4d b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d sub(Vec4d res, Vec4d a, double bX, double bY, double bZ, double bW) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        res.w = a.w - bW;
        return res;
    }

    public static Vec4i sub_(Vec4i a, int b) {
        return sub(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i sub_(Vec4i a, Vec4i b) {
        return sub(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i sub(Vec4i a, int b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4i sub(Vec4i a, Vec4i b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i sub(Vec4i res, Vec4i a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4i sub(Vec4i res, Vec4i a, Vec4i b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i sub(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        res.w = a.w - bW;
        return res;
    }

    public static Vec4l sub_(Vec4l a, long b) {
        return sub(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l sub_(Vec4l a, Vec4l b) {
        return sub(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l sub(Vec4l a, long b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4l sub(Vec4l a, Vec4l b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l sub(Vec4l res, Vec4l a, long b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4l sub(Vec4l res, Vec4l a, Vec4l b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l sub(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x - bX;
        res.y = a.y - bY;
        res.z = a.z - bZ;
        res.w = a.w - bW;
        return res;
    }

    public static Vec4s sub_(Vec4s a, short b) {
        return sub(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s sub_(Vec4s a, int b) {
        return sub(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s sub_(Vec4s a, Vec4s b) {
        return sub(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s sub(Vec4s a, short b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4s sub(Vec4s a, int b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4s sub(Vec4s a, Vec4s b) {
        return sub(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s sub(Vec4s res, Vec4s a, short b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4s sub(Vec4s res, Vec4s a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4s sub(Vec4s res, Vec4s a, Vec4s b) {
        return sub(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s sub(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return sub(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s sub(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x - bX);
        res.y = (short) (a.y - bY);
        res.z = (short) (a.z - bZ);
        res.w = (short) (a.w - bW);
        return res;
    }

    public static Vec4ub sub_(Vec4ub a, UByte b) {
        return sub(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub sub_(Vec4ub a, byte b) {
        return sub(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub sub_(Vec4ub a, int b) {
        return sub(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub sub_(Vec4ub a, Vec4ub b) {
        return sub(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub a, UByte b) {
        return sub(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub a, byte b) {
        return sub(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub sub(Vec4ub a, int b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4ub sub(Vec4ub a, Vec4ub b) {
        return sub(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, UByte b) {
        return sub(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, byte b) {
        return sub(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, Vec4ub b) {
        return sub(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return sub(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return sub(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub sub(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) - bX);
        res.y.value = (byte) ((a.y.value & 0xff) - bY);
        res.z.value = (byte) ((a.z.value & 0xff) - bZ);
        res.w.value = (byte) ((a.w.value & 0xff) - bW);
        return res;
    }

    public static Vec4ui sub_(Vec4ui a, UInt b) {
        return sub(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui sub_(Vec4ui a, int b) {
        return sub(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui sub_(Vec4ui a, Vec4ui b) {
        return sub(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui sub(Vec4ui a, UInt b) {
        return sub(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui sub(Vec4ui a, int b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4ui sub(Vec4ui a, Vec4ui b) {
        return sub(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui sub(Vec4ui res, Vec4ui a, UInt b) {
        return sub(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui sub(Vec4ui res, Vec4ui a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4ui sub(Vec4ui res, Vec4ui a, Vec4ui b) {
        return sub(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui sub(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return sub(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui sub(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        res.z.value = a.z.value - bZ;
        res.w.value = a.w.value - bW;
        return res;
    }

    public static Vec4ul sub_(Vec4ul a, ULong b) {
        return sub(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul sub_(Vec4ul a, long b) {
        return sub(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul sub_(Vec4ul a, Vec4ul b) {
        return sub(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul sub(Vec4ul a, ULong b) {
        return sub(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul sub(Vec4ul a, long b) {
        return sub(a, a, b, b, b, b);
    }

    public static Vec4ul sub(Vec4ul a, Vec4ul b) {
        return sub(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul sub(Vec4ul res, Vec4ul a, ULong b) {
        return sub(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul sub(Vec4ul res, Vec4ul a, long b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4ul sub(Vec4ul res, Vec4ul a, Vec4ul b) {
        return sub(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul sub(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return sub(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul sub(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value - bX;
        res.y.value = a.y.value - bY;
        res.z.value = a.z.value - bZ;
        res.w.value = a.w.value - bW;
        return res;
    }

    public static Vec4us sub_(Vec4us a, UShort b) {
        return sub(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us sub_(Vec4us a, short b) {
        return sub(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us sub_(Vec4us a, int b) {
        return sub(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us sub_(Vec4us a, Vec4us b) {
        return sub(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us sub(Vec4us a, UShort b) {
        return sub(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us sub(Vec4us a, short b) {
        return sub(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us sub(Vec4us a, int b) {
        return sub(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us sub(Vec4us a, Vec4us b) {
        return sub(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, UShort b) {
        return sub(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, short b) {
        return sub(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, int b) {
        return sub(res, a, b, b, b, b);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, Vec4us b) {
        return sub(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return sub(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return sub(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us sub(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) - bX);
        res.y.value = (short) ((a.y.value & 0xffff) - bY);
        res.z.value = (short) ((a.z.value & 0xffff) - bZ);
        res.w.value = (short) ((a.w.value & 0xffff) - bW);
        return res;
    }

    public static Vec2 mul_(Vec2 a, float b) {
        return mul(new Vec2(), a, b, b);
    }

    public static Vec2 mul_(Vec2 a, Vec2 b) {
        return mul(new Vec2(), a, b.x, b.y);
    }

    public static Vec2 mul(Vec2 a, float b) {
        return mul(a, a, b, b);
    }

    public static Vec2 mul(Vec2 a, Vec2 b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2 mul(Vec2 res, Vec2 a, float b) {
        return mul(res, a, b, b);
    }

    public static Vec2 mul(Vec2 res, Vec2 a, Vec2 b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2 mul(Vec2 res, Vec2 a, float bX, float bY) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        return res;
    }

    public static Vec2b mul_(Vec2b a, byte b) {
        return mul(new Vec2b(), a, b, b);
    }

    public static Vec2b mul_(Vec2b a, int b) {
        return mul(new Vec2b(), a, b, b);
    }

    public static Vec2b mul_(Vec2b a, Vec2b b) {
        return mul(new Vec2b(), a, b.x, b.y);
    }

    public static Vec2b mul(Vec2b a, byte b) {
        return mul(a, a, b, b);
    }

    public static Vec2b mul(Vec2b a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2b mul(Vec2b a, Vec2b b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2b mul(Vec2b res, Vec2b a, byte b) {
        return mul(res, a, b, b);
    }

    public static Vec2b mul(Vec2b res, Vec2b a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2b mul(Vec2b res, Vec2b a, Vec2b b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2b mul(Vec2b res, Vec2b a, byte bX, byte bY) {
        return mul(res, a, (int) bX, bY);
    }

    public static Vec2b mul(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x * bX);
        res.y = (byte) (a.y * bY);
        return res;
    }

    public static Vec2d mul_(Vec2d a, double b) {
        return mul(new Vec2d(), a, b, b);
    }

    public static Vec2d mul_(Vec2d a, Vec2d b) {
        return mul(new Vec2d(), a, b.x, b.y);
    }

    public static Vec2d mul(Vec2d a, double b) {
        return mul(a, a, b, b);
    }

    public static Vec2d mul(Vec2d a, Vec2d b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2d mul(Vec2d res, Vec2d a, double b) {
        return mul(res, a, b, b);
    }

    public static Vec2d mul(Vec2d res, Vec2d a, Vec2d b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2d mul(Vec2d res, Vec2d a, double bX, double bY) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        return res;
    }

    public static Vec2i mul_(Vec2i a, int b) {
        return mul(new Vec2i(), a, b, b);
    }

    public static Vec2i mul_(Vec2i a, Vec2i b) {
        return mul(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i mul(Vec2i a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2i mul(Vec2i a, Vec2i b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2i mul(Vec2i res, Vec2i a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2i mul(Vec2i res, Vec2i a, Vec2i b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2i mul(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        return res;
    }

    public static Vec2l mul_(Vec2l a, long b) {
        return mul(new Vec2l(), a, b, b);
    }

    public static Vec2l mul_(Vec2l a, Vec2l b) {
        return mul(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l mul(Vec2l a, long b) {
        return mul(a, a, b, b);
    }

    public static Vec2l mul(Vec2l a, Vec2l b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2l mul(Vec2l res, Vec2l a, long b) {
        return mul(res, a, b, b);
    }

    public static Vec2l mul(Vec2l res, Vec2l a, Vec2l b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2l mul(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        return res;
    }

    public static Vec2s mul_(Vec2s a, short b) {
        return mul(new Vec2s(), a, b, b);
    }

    public static Vec2s mul_(Vec2s a, int b) {
        return mul(new Vec2s(), a, b, b);
    }

    public static Vec2s mul_(Vec2s a, Vec2s b) {
        return mul(new Vec2s(), a, b.x, b.y);
    }

    public static Vec2s mul(Vec2s a, short b) {
        return mul(a, a, b, b);
    }

    public static Vec2s mul(Vec2s a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2s mul(Vec2s a, Vec2s b) {
        return mul(a, a, b.x, b.y);
    }

    public static Vec2s mul(Vec2s res, Vec2s a, short b) {
        return mul(res, a, b, b);
    }

    public static Vec2s mul(Vec2s res, Vec2s a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2s mul(Vec2s res, Vec2s a, Vec2s b) {
        return mul(res, a, b.x, b.y);
    }

    public static Vec2s mul(Vec2s res, Vec2s a, short bX, short bY) {
        return mul(res, a, (int) bX, bY);
    }

    public static Vec2s mul(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x * bX);
        res.y = (short) (a.y * bY);
        return res;
    }

    public static Vec2ub mul_(Vec2ub a, UByte b) {
        return mul(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mul_(Vec2ub a, byte b) {
        return mul(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mul_(Vec2ub a, int b) {
        return mul(new Vec2ub(), a, b, b);
    }

    public static Vec2ub mul_(Vec2ub a, Vec2ub b) {
        return mul(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub a, UByte b) {
        return mul(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub a, byte b) {
        return mul(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mul(Vec2ub a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2ub mul(Vec2ub a, Vec2ub b) {
        return mul(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, UByte b) {
        return mul(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, byte b) {
        return mul(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, Vec2ub b) {
        return mul(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return mul(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return mul(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) * bX);
        res.y.value = (byte) ((a.y.value & 0xff) * bY);
        return res;
    }

    public static Vec2ui mul_(Vec2ui a, UInt b) {
        return mul(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui mul_(Vec2ui a, int b) {
        return mul(new Vec2ui(), a, b, b);
    }

    public static Vec2ui mul_(Vec2ui a, Vec2ui b) {
        return mul(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui mul(Vec2ui a, UInt b) {
        return mul(a, a, b.value, b.value);
    }

    public static Vec2ui mul(Vec2ui a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2ui mul(Vec2ui a, Vec2ui b) {
        return mul(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui mul(Vec2ui res, Vec2ui a, UInt b) {
        return mul(res, a, b.value, b.value);
    }

    public static Vec2ui mul(Vec2ui res, Vec2ui a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2ui mul(Vec2ui res, Vec2ui a, Vec2ui b) {
        return mul(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui mul(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return mul(res, a, bX.value, bY.value);
    }

    public static Vec2ui mul(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        return res;
    }

    public static Vec2ul mul_(Vec2ul a, ULong b) {
        return mul(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul mul_(Vec2ul a, long b) {
        return mul(new Vec2ul(), a, b, b);
    }

    public static Vec2ul mul_(Vec2ul a, Vec2ul b) {
        return mul(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul mul(Vec2ul a, ULong b) {
        return mul(a, a, b.value, b.value);
    }

    public static Vec2ul mul(Vec2ul a, long b) {
        return mul(a, a, b, b);
    }

    public static Vec2ul mul(Vec2ul a, Vec2ul b) {
        return mul(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul mul(Vec2ul res, Vec2ul a, ULong b) {
        return mul(res, a, b.value, b.value);
    }

    public static Vec2ul mul(Vec2ul res, Vec2ul a, long b) {
        return mul(res, a, b, b);
    }

    public static Vec2ul mul(Vec2ul res, Vec2ul a, Vec2ul b) {
        return mul(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul mul(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return mul(res, a, bX.value, bY.value);
    }

    public static Vec2ul mul(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        return res;
    }

    public static Vec2us mul_(Vec2us a, UShort b) {
        return mul(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mul_(Vec2us a, short b) {
        return mul(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mul_(Vec2us a, int b) {
        return mul(new Vec2us(), a, b, b);
    }

    public static Vec2us mul_(Vec2us a, Vec2us b) {
        return mul(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mul(Vec2us a, UShort b) {
        return mul(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mul(Vec2us a, short b) {
        return mul(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mul(Vec2us a, int b) {
        return mul(a, a, b, b);
    }

    public static Vec2us mul(Vec2us a, Vec2us b) {
        return mul(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, UShort b) {
        return mul(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, short b) {
        return mul(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, int b) {
        return mul(res, a, b, b);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, Vec2us b) {
        return mul(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return mul(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, short bX, short bY) {
        return mul(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us mul(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) * bX);
        res.y.value = (short) ((a.y.value & 0xffff) * bY);
        return res;
    }

    public static Vec3 mul_(Vec3 a, float b) {
        return mul(new Vec3(), a, b, b, b);
    }

    public static Vec3 mul_(Vec3 a, Vec3 b) {
        return mul(new Vec3(), a, b.x, b.y, b.z);
    }

    public static Vec3 mul(Vec3 a, float b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3 mul(Vec3 a, Vec3 b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3 mul(Vec3 res, Vec3 a, float b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3 mul(Vec3 res, Vec3 a, Vec3 b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3 mul(Vec3 res, Vec3 a, float bX, float bY, float bZ) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        return res;
    }

    public static Vec3b mul_(Vec3b a, byte b) {
        return mul(new Vec3b(), a, b, b, b);
    }

    public static Vec3b mul_(Vec3b a, int b) {
        return mul(new Vec3b(), a, b, b, b);
    }

    public static Vec3b mul_(Vec3b a, Vec3b b) {
        return mul(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b mul(Vec3b a, byte b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3b mul(Vec3b a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3b mul(Vec3b a, Vec3b b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3b mul(Vec3b res, Vec3b a, byte b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3b mul(Vec3b res, Vec3b a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3b mul(Vec3b res, Vec3b a, Vec3b b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3b mul(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return mul(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b mul(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x * bX);
        res.y = (byte) (a.y * bY);
        res.z = (byte) (a.z * bZ);
        return res;
    }

    public static Vec3d mul_(Vec3d a, double b) {
        return mul(new Vec3d(), a, b, b, b);
    }

    public static Vec3d mul_(Vec3d a, Vec3d b) {
        return mul(new Vec3d(), a, b.x, b.y, b.z);
    }

    public static Vec3d mul(Vec3d a, double b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3d mul(Vec3d a, Vec3d b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3d mul(Vec3d res, Vec3d a, double b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3d mul(Vec3d res, Vec3d a, Vec3d b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3d mul(Vec3d res, Vec3d a, double bX, double bY, double bZ) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        return res;
    }

    public static Vec3i mul_(Vec3i a, int b) {
        return mul(new Vec3i(), a, b, b, b);
    }

    public static Vec3i mul_(Vec3i a, Vec3i b) {
        return mul(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i mul(Vec3i a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3i mul(Vec3i a, Vec3i b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3i mul(Vec3i res, Vec3i a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3i mul(Vec3i res, Vec3i a, Vec3i b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3i mul(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        return res;
    }

    public static Vec3l mul_(Vec3l a, long b) {
        return mul(new Vec3l(), a, b, b, b);
    }

    public static Vec3l mul_(Vec3l a, Vec3l b) {
        return mul(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l mul(Vec3l a, long b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3l mul(Vec3l a, Vec3l b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3l mul(Vec3l res, Vec3l a, long b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3l mul(Vec3l res, Vec3l a, Vec3l b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3l mul(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        return res;
    }

    public static Vec3s mul_(Vec3s a, short b) {
        return mul(new Vec3s(), a, b, b, b);
    }

    public static Vec3s mul_(Vec3s a, int b) {
        return mul(new Vec3s(), a, b, b, b);
    }

    public static Vec3s mul_(Vec3s a, Vec3s b) {
        return mul(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s mul(Vec3s a, short b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3s mul(Vec3s a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3s mul(Vec3s a, Vec3s b) {
        return mul(a, a, b.x, b.y, b.z);
    }

    public static Vec3s mul(Vec3s res, Vec3s a, short b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3s mul(Vec3s res, Vec3s a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3s mul(Vec3s res, Vec3s a, Vec3s b) {
        return mul(res, a, b.x, b.y, b.z);
    }

    public static Vec3s mul(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return mul(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s mul(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x * bX);
        res.y = (short) (a.y * bY);
        res.z = (short) (a.z * bZ);
        return res;
    }

    public static Vec3ub mul_(Vec3ub a, UByte b) {
        return mul(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mul_(Vec3ub a, byte b) {
        return mul(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mul_(Vec3ub a, int b) {
        return mul(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub mul_(Vec3ub a, Vec3ub b) {
        return mul(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub a, UByte b) {
        return mul(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub a, byte b) {
        return mul(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mul(Vec3ub a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3ub mul(Vec3ub a, Vec3ub b) {
        return mul(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, UByte b) {
        return mul(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, byte b) {
        return mul(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, Vec3ub b) {
        return mul(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return mul(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return mul(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) * bX);
        res.y.value = (byte) ((a.y.value & 0xff) * bY);
        res.z.value = (byte) ((a.z.value & 0xff) * bZ);
        return res;
    }

    public static Vec3ui mul_(Vec3ui a, UInt b) {
        return mul(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui mul_(Vec3ui a, int b) {
        return mul(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui mul_(Vec3ui a, Vec3ui b) {
        return mul(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui mul(Vec3ui a, UInt b) {
        return mul(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui mul(Vec3ui a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3ui mul(Vec3ui a, Vec3ui b) {
        return mul(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui mul(Vec3ui res, Vec3ui a, UInt b) {
        return mul(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui mul(Vec3ui res, Vec3ui a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3ui mul(Vec3ui res, Vec3ui a, Vec3ui b) {
        return mul(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui mul(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return mul(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui mul(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        res.z.value = a.z.value * bZ;
        return res;
    }

    public static Vec3ul mul_(Vec3ul a, ULong b) {
        return mul(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul mul_(Vec3ul a, long b) {
        return mul(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul mul_(Vec3ul a, Vec3ul b) {
        return mul(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mul(Vec3ul a, ULong b) {
        return mul(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul mul(Vec3ul a, long b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3ul mul(Vec3ul a, Vec3ul b) {
        return mul(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mul(Vec3ul res, Vec3ul a, ULong b) {
        return mul(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul mul(Vec3ul res, Vec3ul a, long b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3ul mul(Vec3ul res, Vec3ul a, Vec3ul b) {
        return mul(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mul(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return mul(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul mul(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        res.z.value = a.z.value * bZ;
        return res;
    }

    public static Vec3us mul_(Vec3us a, UShort b) {
        return mul(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mul_(Vec3us a, short b) {
        return mul(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mul_(Vec3us a, int b) {
        return mul(new Vec3us(), a, b, b, b);
    }

    public static Vec3us mul_(Vec3us a, Vec3us b) {
        return mul(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mul(Vec3us a, UShort b) {
        return mul(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mul(Vec3us a, short b) {
        return mul(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mul(Vec3us a, int b) {
        return mul(a, a, b, b, b);
    }

    public static Vec3us mul(Vec3us a, Vec3us b) {
        return mul(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, UShort b) {
        return mul(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, short b) {
        return mul(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, int b) {
        return mul(res, a, b, b, b);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, Vec3us b) {
        return mul(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return mul(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return mul(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us mul(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) * bX);
        res.y.value = (short) ((a.y.value & 0xffff) * bY);
        res.z.value = (short) ((a.z.value & 0xffff) * bZ);
        return res;
    }

    public static Vec4 mul_(Vec4 a, float b) {
        return mul(new Vec4(), a, b, b, b, b);
    }

    public static Vec4 mul_(Vec4 a, Vec4 b) {
        return mul(new Vec4(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 mul(Vec4 a, float b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4 mul(Vec4 a, Vec4 b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 mul(Vec4 res, Vec4 a, float b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4 mul(Vec4 res, Vec4 a, Vec4 b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 mul(Vec4 res, Vec4 a, float bX, float bY, float bZ, float bW) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        res.w = a.w * bW;
        return res;
    }

    public static Vec4b mul_(Vec4b a, byte b) {
        return mul(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b mul_(Vec4b a, int b) {
        return mul(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b mul_(Vec4b a, Vec4b b) {
        return mul(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mul(Vec4b a, byte b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4b mul(Vec4b a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4b mul(Vec4b a, Vec4b b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mul(Vec4b res, Vec4b a, byte b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4b mul(Vec4b res, Vec4b a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4b mul(Vec4b res, Vec4b a, Vec4b b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mul(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return mul(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b mul(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x * bX);
        res.y = (byte) (a.y * bY);
        res.z = (byte) (a.z * bZ);
        res.w = (byte) (a.w * bW);
        return res;
    }

    public static Vec4d mul_(Vec4d a, double b) {
        return mul(new Vec4d(), a, b, b, b, b);
    }

    public static Vec4d mul_(Vec4d a, Vec4d b) {
        return mul(new Vec4d(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d mul(Vec4d a, double b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4d mul(Vec4d a, Vec4d b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d mul(Vec4d res, Vec4d a, double b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4d mul(Vec4d res, Vec4d a, Vec4d b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d mul(Vec4d res, Vec4d a, double bX, double bY, double bZ, double bW) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        res.w = a.w * bW;
        return res;
    }

    public static Vec4i mul_(Vec4i a, int b) {
        return mul(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i mul_(Vec4i a, Vec4i b) {
        return mul(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mul(Vec4i a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4i mul(Vec4i a, Vec4i b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mul(Vec4i res, Vec4i a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4i mul(Vec4i res, Vec4i a, Vec4i b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mul(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        res.w = a.w * bW;
        return res;
    }

    public static Vec4l mul_(Vec4l a, long b) {
        return mul(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l mul_(Vec4l a, Vec4l b) {
        return mul(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mul(Vec4l a, long b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4l mul(Vec4l a, Vec4l b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mul(Vec4l res, Vec4l a, long b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4l mul(Vec4l res, Vec4l a, Vec4l b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mul(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x * bX;
        res.y = a.y * bY;
        res.z = a.z * bZ;
        res.w = a.w * bW;
        return res;
    }

    public static Vec4s mul_(Vec4s a, short b) {
        return mul(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s mul_(Vec4s a, int b) {
        return mul(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s mul_(Vec4s a, Vec4s b) {
        return mul(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mul(Vec4s a, short b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4s mul(Vec4s a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4s mul(Vec4s a, Vec4s b) {
        return mul(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mul(Vec4s res, Vec4s a, short b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4s mul(Vec4s res, Vec4s a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4s mul(Vec4s res, Vec4s a, Vec4s b) {
        return mul(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mul(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return mul(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s mul(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x * bX);
        res.y = (short) (a.y * bY);
        res.z = (short) (a.z * bZ);
        res.w = (short) (a.w * bW);
        return res;
    }

    public static Vec4ub mul_(Vec4ub a, UByte b) {
        return mul(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mul_(Vec4ub a, byte b) {
        return mul(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mul_(Vec4ub a, int b) {
        return mul(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub mul_(Vec4ub a, Vec4ub b) {
        return mul(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub a, UByte b) {
        return mul(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub a, byte b) {
        return mul(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mul(Vec4ub a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4ub mul(Vec4ub a, Vec4ub b) {
        return mul(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, UByte b) {
        return mul(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, byte b) {
        return mul(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, Vec4ub b) {
        return mul(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return mul(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return mul(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) * bX);
        res.y.value = (byte) ((a.y.value & 0xff) * bY);
        res.z.value = (byte) ((a.z.value & 0xff) * bZ);
        res.w.value = (byte) ((a.w.value & 0xff) * bW);
        return res;
    }

    public static Vec4ui mul_(Vec4ui a, UInt b) {
        return mul(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mul_(Vec4ui a, int b) {
        return mul(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui mul_(Vec4ui a, Vec4ui b) {
        return mul(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui mul(Vec4ui a, UInt b) {
        return mul(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mul(Vec4ui a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4ui mul(Vec4ui a, Vec4ui b) {
        return mul(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui mul(Vec4ui res, Vec4ui a, UInt b) {
        return mul(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mul(Vec4ui res, Vec4ui a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4ui mul(Vec4ui res, Vec4ui a, Vec4ui b) {
        return mul(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui mul(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return mul(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui mul(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        res.z.value = a.z.value * bZ;
        res.w.value = a.w.value * bW;
        return res;
    }

    public static Vec4ul mul_(Vec4ul a, ULong b) {
        return mul(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mul_(Vec4ul a, long b) {
        return mul(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul mul_(Vec4ul a, Vec4ul b) {
        return mul(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mul(Vec4ul a, ULong b) {
        return mul(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mul(Vec4ul a, long b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4ul mul(Vec4ul a, Vec4ul b) {
        return mul(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mul(Vec4ul res, Vec4ul a, ULong b) {
        return mul(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mul(Vec4ul res, Vec4ul a, long b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4ul mul(Vec4ul res, Vec4ul a, Vec4ul b) {
        return mul(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mul(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return mul(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul mul(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value * bX;
        res.y.value = a.y.value * bY;
        res.z.value = a.z.value * bZ;
        res.w.value = a.w.value * bW;
        return res;
    }

    public static Vec4us mul_(Vec4us a, UShort b) {
        return mul(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mul_(Vec4us a, short b) {
        return mul(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mul_(Vec4us a, int b) {
        return mul(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us mul_(Vec4us a, Vec4us b) {
        return mul(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mul(Vec4us a, UShort b) {
        return mul(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mul(Vec4us a, short b) {
        return mul(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mul(Vec4us a, int b) {
        return mul(a, a, b, b, b, b);
    }

    public static Vec4us mul(Vec4us a, Vec4us b) {
        return mul(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, UShort b) {
        return mul(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, short b) {
        return mul(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, int b) {
        return mul(res, a, b, b, b, b);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, Vec4us b) {
        return mul(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return mul(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return mul(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us mul(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) * bX);
        res.y.value = (short) ((a.y.value & 0xffff) * bY);
        res.z.value = (short) ((a.z.value & 0xffff) * bZ);
        res.w.value = (short) ((a.w.value & 0xffff) * bW);
        return res;
    }

    public static Vec2 div_(Vec2 a, float b) {
        return div(new Vec2(), a, b, b);
    }

    public static Vec2 div_(Vec2 a, Vec2 b) {
        return div(new Vec2(), a, b.x, b.y);
    }

    public static Vec2 div(Vec2 a, float b) {
        return div(a, a, b, b);
    }

    public static Vec2 div(Vec2 a, Vec2 b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2 div(Vec2 res, Vec2 a, float b) {
        return div(res, a, b, b);
    }

    public static Vec2 div(Vec2 res, Vec2 a, Vec2 b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2 div(Vec2 res, Vec2 a, float bX, float bY) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        return res;
    }

    public static Vec2b div_(Vec2b a, byte b) {
        return div(new Vec2b(), a, b, b);
    }

    public static Vec2b div_(Vec2b a, int b) {
        return div(new Vec2b(), a, b, b);
    }

    public static Vec2b div_(Vec2b a, Vec2b b) {
        return div(new Vec2b(), a, b.x, b.y);
    }

    public static Vec2b div(Vec2b a, byte b) {
        return div(a, a, b, b);
    }

    public static Vec2b div(Vec2b a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2b div(Vec2b a, Vec2b b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2b div(Vec2b res, Vec2b a, byte b) {
        return div(res, a, b, b);
    }

    public static Vec2b div(Vec2b res, Vec2b a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2b div(Vec2b res, Vec2b a, Vec2b b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2b div(Vec2b res, Vec2b a, byte bX, byte bY) {
        return div(res, a, (int) bX, bY);
    }

    public static Vec2b div(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x / bX);
        res.y = (byte) (a.y / bY);
        return res;
    }

    public static Vec2d div_(Vec2d a, double b) {
        return div(new Vec2d(), a, b, b);
    }

    public static Vec2d div_(Vec2d a, Vec2d b) {
        return div(new Vec2d(), a, b.x, b.y);
    }

    public static Vec2d div(Vec2d a, double b) {
        return div(a, a, b, b);
    }

    public static Vec2d div(Vec2d a, Vec2d b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2d div(Vec2d res, Vec2d a, double b) {
        return div(res, a, b, b);
    }

    public static Vec2d div(Vec2d res, Vec2d a, Vec2d b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2d div(Vec2d res, Vec2d a, double bX, double bY) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        return res;
    }

    public static Vec2i div_(Vec2i a, int b) {
        return div(new Vec2i(), a, b, b);
    }

    public static Vec2i div_(Vec2i a, Vec2i b) {
        return div(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i div(Vec2i a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2i div(Vec2i a, Vec2i b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2i div(Vec2i res, Vec2i a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2i div(Vec2i res, Vec2i a, Vec2i b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2i div(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        return res;
    }

    public static Vec2l div_(Vec2l a, long b) {
        return div(new Vec2l(), a, b, b);
    }

    public static Vec2l div_(Vec2l a, Vec2l b) {
        return div(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l div(Vec2l a, long b) {
        return div(a, a, b, b);
    }

    public static Vec2l div(Vec2l a, Vec2l b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2l div(Vec2l res, Vec2l a, long b) {
        return div(res, a, b, b);
    }

    public static Vec2l div(Vec2l res, Vec2l a, Vec2l b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2l div(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        return res;
    }

    public static Vec2s div_(Vec2s a, short b) {
        return div(new Vec2s(), a, b, b);
    }

    public static Vec2s div_(Vec2s a, int b) {
        return div(new Vec2s(), a, b, b);
    }

    public static Vec2s div_(Vec2s a, Vec2s b) {
        return div(new Vec2s(), a, b.x, b.y);
    }

    public static Vec2s div(Vec2s a, short b) {
        return div(a, a, b, b);
    }

    public static Vec2s div(Vec2s a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2s div(Vec2s a, Vec2s b) {
        return div(a, a, b.x, b.y);
    }

    public static Vec2s div(Vec2s res, Vec2s a, short b) {
        return div(res, a, b, b);
    }

    public static Vec2s div(Vec2s res, Vec2s a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2s div(Vec2s res, Vec2s a, Vec2s b) {
        return div(res, a, b.x, b.y);
    }

    public static Vec2s div(Vec2s res, Vec2s a, short bX, short bY) {
        return div(res, a, (int) bX, bY);
    }

    public static Vec2s div(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x / bX);
        res.y = (short) (a.y / bY);
        return res;
    }

    public static Vec2ub div_(Vec2ub a, UByte b) {
        return div(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub div_(Vec2ub a, byte b) {
        return div(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub div_(Vec2ub a, int b) {
        return div(new Vec2ub(), a, b, b);
    }

    public static Vec2ub div_(Vec2ub a, Vec2ub b) {
        return div(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub div(Vec2ub a, UByte b) {
        return div(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub div(Vec2ub a, byte b) {
        return div(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub div(Vec2ub a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2ub div(Vec2ub a, Vec2ub b) {
        return div(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, UByte b) {
        return div(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, byte b) {
        return div(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, Vec2ub b) {
        return div(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return div(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return div(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub div(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) Integer.divideUnsigned(a.x.value & 0xff, bX);
        res.y.value = (byte) Integer.divideUnsigned(a.y.value & 0xff, bY);
        return res;
    }

    public static Vec2ui div_(Vec2ui a, UInt b) {
        return div(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui div_(Vec2ui a, int b) {
        return div(new Vec2ui(), a, b, b);
    }

    public static Vec2ui div_(Vec2ui a, Vec2ui b) {
        return div(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui div(Vec2ui a, UInt b) {
        return div(a, a, b.value, b.value);
    }

    public static Vec2ui div(Vec2ui a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2ui div(Vec2ui a, Vec2ui b) {
        return div(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui div(Vec2ui res, Vec2ui a, UInt b) {
        return div(res, a, b.value, b.value);
    }

    public static Vec2ui div(Vec2ui res, Vec2ui a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2ui div(Vec2ui res, Vec2ui a, Vec2ui b) {
        return div(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui div(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return div(res, a, bX.value, bY.value);
    }

    public static Vec2ui div(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = Integer.divideUnsigned(a.x.value, bX);
        res.y.value = Integer.divideUnsigned(a.y.value, bY);
        return res;
    }

    public static Vec2ul div_(Vec2ul a, ULong b) {
        return div(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul div_(Vec2ul a, long b) {
        return div(new Vec2ul(), a, b, b);
    }

    public static Vec2ul div_(Vec2ul a, Vec2ul b) {
        return div(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul div(Vec2ul a, ULong b) {
        return div(a, a, b.value, b.value);
    }

    public static Vec2ul div(Vec2ul a, long b) {
        return div(a, a, b, b);
    }

    public static Vec2ul div(Vec2ul a, Vec2ul b) {
        return div(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul div(Vec2ul res, Vec2ul a, ULong b) {
        return div(res, a, b.value, b.value);
    }

    public static Vec2ul div(Vec2ul res, Vec2ul a, long b) {
        return div(res, a, b, b);
    }

    public static Vec2ul div(Vec2ul res, Vec2ul a, Vec2ul b) {
        return div(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul div(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return div(res, a, bX.value, bY.value);
    }

    public static Vec2ul div(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = Long.divideUnsigned(a.x.value, bX);
        res.y.value = Long.divideUnsigned(a.y.value, bY);
        return res;
    }

    public static Vec2us div_(Vec2us a, UShort b) {
        return div(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us div_(Vec2us a, short b) {
        return div(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us div_(Vec2us a, int b) {
        return div(new Vec2us(), a, b, b);
    }

    public static Vec2us div_(Vec2us a, Vec2us b) {
        return div(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us div(Vec2us a, UShort b) {
        return div(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us div(Vec2us a, short b) {
        return div(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us div(Vec2us a, int b) {
        return div(a, a, b, b);
    }

    public static Vec2us div(Vec2us a, Vec2us b) {
        return div(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, UShort b) {
        return div(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, short b) {
        return div(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, int b) {
        return div(res, a, b, b);
    }

    public static Vec2us div(Vec2us res, Vec2us a, Vec2us b) {
        return div(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return div(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, short bX, short bY) {
        return div(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us div(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) Integer.divideUnsigned(a.x.value & 0xffff, bX);
        res.y.value = (short) Integer.divideUnsigned(a.y.value & 0xffff, bY);
        return res;
    }

    public static Vec3 div_(Vec3 a, float b) {
        return div(new Vec3(), a, b, b, b);
    }

    public static Vec3 div_(Vec3 a, Vec3 b) {
        return div(new Vec3(), a, b.x, b.y, b.z);
    }

    public static Vec3 div(Vec3 a, float b) {
        return div(a, a, b, b, b);
    }

    public static Vec3 div(Vec3 a, Vec3 b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3 div(Vec3 res, Vec3 a, float b) {
        return div(res, a, b, b, b);
    }

    public static Vec3 div(Vec3 res, Vec3 a, Vec3 b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3 div(Vec3 res, Vec3 a, float bX, float bY, float bZ) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        return res;
    }

    public static Vec3b div_(Vec3b a, byte b) {
        return div(new Vec3b(), a, b, b, b);
    }

    public static Vec3b div_(Vec3b a, int b) {
        return div(new Vec3b(), a, b, b, b);
    }

    public static Vec3b div_(Vec3b a, Vec3b b) {
        return div(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b div(Vec3b a, byte b) {
        return div(a, a, b, b, b);
    }

    public static Vec3b div(Vec3b a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3b div(Vec3b a, Vec3b b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3b div(Vec3b res, Vec3b a, byte b) {
        return div(res, a, b, b, b);
    }

    public static Vec3b div(Vec3b res, Vec3b a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3b div(Vec3b res, Vec3b a, Vec3b b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3b div(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return div(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b div(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x / bX);
        res.y = (byte) (a.y / bY);
        res.z = (byte) (a.z / bZ);
        return res;
    }

    public static Vec3d div_(Vec3d a, double b) {
        return div(new Vec3d(), a, b, b, b);
    }

    public static Vec3d div_(Vec3d a, Vec3d b) {
        return div(new Vec3d(), a, b.x, b.y, b.z);
    }

    public static Vec3d div(Vec3d a, double b) {
        return div(a, a, b, b, b);
    }

    public static Vec3d div(Vec3d a, Vec3d b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3d div(Vec3d res, Vec3d a, double b) {
        return div(res, a, b, b, b);
    }

    public static Vec3d div(Vec3d res, Vec3d a, Vec3d b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3d div(Vec3d res, Vec3d a, double bX, double bY, double bZ) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        return res;
    }

    public static Vec3i div_(Vec3i a, int b) {
        return div(new Vec3i(), a, b, b, b);
    }

    public static Vec3i div_(Vec3i a, Vec3i b) {
        return div(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i div(Vec3i a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3i div(Vec3i a, Vec3i b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3i div(Vec3i res, Vec3i a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3i div(Vec3i res, Vec3i a, Vec3i b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3i div(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        return res;
    }

    public static Vec3l div_(Vec3l a, long b) {
        return div(new Vec3l(), a, b, b, b);
    }

    public static Vec3l div_(Vec3l a, Vec3l b) {
        return div(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l div(Vec3l a, long b) {
        return div(a, a, b, b, b);
    }

    public static Vec3l div(Vec3l a, Vec3l b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3l div(Vec3l res, Vec3l a, long b) {
        return div(res, a, b, b, b);
    }

    public static Vec3l div(Vec3l res, Vec3l a, Vec3l b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3l div(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        return res;
    }

    public static Vec3s div_(Vec3s a, short b) {
        return div(new Vec3s(), a, b, b, b);
    }

    public static Vec3s div_(Vec3s a, int b) {
        return div(new Vec3s(), a, b, b, b);
    }

    public static Vec3s div_(Vec3s a, Vec3s b) {
        return div(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s div(Vec3s a, short b) {
        return div(a, a, b, b, b);
    }

    public static Vec3s div(Vec3s a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3s div(Vec3s a, Vec3s b) {
        return div(a, a, b.x, b.y, b.z);
    }

    public static Vec3s div(Vec3s res, Vec3s a, short b) {
        return div(res, a, b, b, b);
    }

    public static Vec3s div(Vec3s res, Vec3s a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3s div(Vec3s res, Vec3s a, Vec3s b) {
        return div(res, a, b.x, b.y, b.z);
    }

    public static Vec3s div(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return div(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s div(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x / bX);
        res.y = (short) (a.y / bY);
        res.z = (short) (a.z / bZ);
        return res;
    }

    public static Vec3ub div_(Vec3ub a, UByte b) {
        return div(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub div_(Vec3ub a, byte b) {
        return div(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub div_(Vec3ub a, int b) {
        return div(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub div_(Vec3ub a, Vec3ub b) {
        return div(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub div(Vec3ub a, UByte b) {
        return div(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub div(Vec3ub a, byte b) {
        return div(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub div(Vec3ub a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3ub div(Vec3ub a, Vec3ub b) {
        return div(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, UByte b) {
        return div(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, byte b) {
        return div(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, Vec3ub b) {
        return div(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return div(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return div(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub div(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) Integer.divideUnsigned(a.x.value & 0xff, bX);
        res.y.value = (byte) Integer.divideUnsigned(a.y.value & 0xff, bY);
        res.z.value = (byte) Integer.divideUnsigned(a.z.value & 0xff, bZ);
        return res;
    }

    public static Vec3ui div_(Vec3ui a, UInt b) {
        return div(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui div_(Vec3ui a, int b) {
        return div(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui div_(Vec3ui a, Vec3ui b) {
        return div(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui div(Vec3ui a, UInt b) {
        return div(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui div(Vec3ui a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3ui div(Vec3ui a, Vec3ui b) {
        return div(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui div(Vec3ui res, Vec3ui a, UInt b) {
        return div(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui div(Vec3ui res, Vec3ui a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3ui div(Vec3ui res, Vec3ui a, Vec3ui b) {
        return div(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui div(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return div(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui div(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = Integer.divideUnsigned(a.x.value, bX);
        res.y.value = Integer.divideUnsigned(a.y.value, bY);
        res.z.value = Integer.divideUnsigned(a.z.value, bZ);
        return res;
    }

    public static Vec3ul div_(Vec3ul a, ULong b) {
        return div(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul div_(Vec3ul a, long b) {
        return div(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul div_(Vec3ul a, Vec3ul b) {
        return div(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul div(Vec3ul a, ULong b) {
        return div(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul div(Vec3ul a, long b) {
        return div(a, a, b, b, b);
    }

    public static Vec3ul div(Vec3ul a, Vec3ul b) {
        return div(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul div(Vec3ul res, Vec3ul a, ULong b) {
        return div(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul div(Vec3ul res, Vec3ul a, long b) {
        return div(res, a, b, b, b);
    }

    public static Vec3ul div(Vec3ul res, Vec3ul a, Vec3ul b) {
        return div(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul div(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return div(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul div(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = Long.divideUnsigned(a.x.value, bX);
        res.y.value = Long.divideUnsigned(a.y.value, bY);
        res.z.value = Long.divideUnsigned(a.z.value, bZ);
        return res;
    }

    public static Vec3us div_(Vec3us a, UShort b) {
        return div(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us div_(Vec3us a, short b) {
        return div(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us div_(Vec3us a, int b) {
        return div(new Vec3us(), a, b, b, b);
    }

    public static Vec3us div_(Vec3us a, Vec3us b) {
        return div(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us div(Vec3us a, UShort b) {
        return div(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us div(Vec3us a, short b) {
        return div(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us div(Vec3us a, int b) {
        return div(a, a, b, b, b);
    }

    public static Vec3us div(Vec3us a, Vec3us b) {
        return div(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, UShort b) {
        return div(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, short b) {
        return div(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, int b) {
        return div(res, a, b, b, b);
    }

    public static Vec3us div(Vec3us res, Vec3us a, Vec3us b) {
        return div(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return div(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return div(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us div(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) Integer.divideUnsigned(a.x.value & 0xffff, bX);
        res.y.value = (short) Integer.divideUnsigned(a.y.value & 0xffff, bY);
        res.z.value = (short) Integer.divideUnsigned(a.z.value & 0xffff, bZ);
        return res;
    }

    public static Vec4 div_(Vec4 a, float b) {
        return div(new Vec4(), a, b, b, b, b);
    }

    public static Vec4 div_(Vec4 a, Vec4 b) {
        return div(new Vec4(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 div(Vec4 a, float b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4 div(Vec4 a, Vec4 b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 div(Vec4 res, Vec4 a, float b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4 div(Vec4 res, Vec4 a, Vec4 b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4 div(Vec4 res, Vec4 a, float bX, float bY, float bZ, float bW) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        res.w = a.w / bW;
        return res;
    }

    public static Vec4b div_(Vec4b a, byte b) {
        return div(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b div_(Vec4b a, int b) {
        return div(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b div_(Vec4b a, Vec4b b) {
        return div(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b div(Vec4b a, byte b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4b div(Vec4b a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4b div(Vec4b a, Vec4b b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b div(Vec4b res, Vec4b a, byte b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4b div(Vec4b res, Vec4b a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4b div(Vec4b res, Vec4b a, Vec4b b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b div(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return div(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b div(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x / bX);
        res.y = (byte) (a.y / bY);
        res.z = (byte) (a.z / bZ);
        res.w = (byte) (a.w / bW);
        return res;
    }

    public static Vec4d div_(Vec4d a, double b) {
        return div(new Vec4d(), a, b, b, b, b);
    }

    public static Vec4d div_(Vec4d a, Vec4d b) {
        return div(new Vec4d(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d div(Vec4d a, double b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4d div(Vec4d a, Vec4d b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d div(Vec4d res, Vec4d a, double b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4d div(Vec4d res, Vec4d a, Vec4d b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4d div(Vec4d res, Vec4d a, double bX, double bY, double bZ, double bW) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        res.w = a.w / bW;
        return res;
    }

    public static Vec4i div_(Vec4i a, int b) {
        return div(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i div_(Vec4i a, Vec4i b) {
        return div(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i div(Vec4i a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4i div(Vec4i a, Vec4i b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i div(Vec4i res, Vec4i a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4i div(Vec4i res, Vec4i a, Vec4i b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i div(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        res.w = a.w / bW;
        return res;
    }

    public static Vec4l div_(Vec4l a, long b) {
        return div(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l div_(Vec4l a, Vec4l b) {
        return div(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l div(Vec4l a, long b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4l div(Vec4l a, Vec4l b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l div(Vec4l res, Vec4l a, long b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4l div(Vec4l res, Vec4l a, Vec4l b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l div(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x / bX;
        res.y = a.y / bY;
        res.z = a.z / bZ;
        res.w = a.w / bW;
        return res;
    }

    public static Vec4s div_(Vec4s a, short b) {
        return div(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s div_(Vec4s a, int b) {
        return div(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s div_(Vec4s a, Vec4s b) {
        return div(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s div(Vec4s a, short b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4s div(Vec4s a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4s div(Vec4s a, Vec4s b) {
        return div(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s div(Vec4s res, Vec4s a, short b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4s div(Vec4s res, Vec4s a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4s div(Vec4s res, Vec4s a, Vec4s b) {
        return div(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s div(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return div(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s div(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x / bX);
        res.y = (short) (a.y / bY);
        res.z = (short) (a.z / bZ);
        res.w = (short) (a.w / bW);
        return res;
    }

    public static Vec4ub div_(Vec4ub a, UByte b) {
        return div(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub div_(Vec4ub a, byte b) {
        return div(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub div_(Vec4ub a, int b) {
        return div(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub div_(Vec4ub a, Vec4ub b) {
        return div(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub div(Vec4ub a, UByte b) {
        return div(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub div(Vec4ub a, byte b) {
        return div(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub div(Vec4ub a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4ub div(Vec4ub a, Vec4ub b) {
        return div(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, UByte b) {
        return div(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, byte b) {
        return div(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, Vec4ub b) {
        return div(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return div(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return div(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub div(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) Integer.divideUnsigned(a.x.value & 0xff, bX);
        res.y.value = (byte) Integer.divideUnsigned(a.y.value & 0xff, bY);
        res.z.value = (byte) Integer.divideUnsigned(a.z.value & 0xff, bZ);
        res.w.value = (byte) Integer.divideUnsigned(a.w.value & 0xff, bW);
        return res;
    }

    public static Vec4ui div_(Vec4ui a, UInt b) {
        return div(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui div_(Vec4ui a, int b) {
        return div(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui div_(Vec4ui a, Vec4ui b) {
        return div(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui div(Vec4ui a, UInt b) {
        return div(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui div(Vec4ui a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4ui div(Vec4ui a, Vec4ui b) {
        return div(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui div(Vec4ui res, Vec4ui a, UInt b) {
        return div(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui div(Vec4ui res, Vec4ui a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4ui div(Vec4ui res, Vec4ui a, Vec4ui b) {
        return div(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui div(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return div(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui div(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = Integer.divideUnsigned(a.x.value, bX);
        res.y.value = Integer.divideUnsigned(a.y.value, bY);
        res.z.value = Integer.divideUnsigned(a.z.value, bZ);
        res.w.value = Integer.divideUnsigned(a.w.value, bW);
        return res;
    }

    public static Vec4ul div_(Vec4ul a, ULong b) {
        return div(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul div_(Vec4ul a, long b) {
        return div(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul div_(Vec4ul a, Vec4ul b) {
        return div(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul div(Vec4ul a, ULong b) {
        return div(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul div(Vec4ul a, long b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4ul div(Vec4ul a, Vec4ul b) {
        return div(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul div(Vec4ul res, Vec4ul a, ULong b) {
        return div(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul div(Vec4ul res, Vec4ul a, long b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4ul div(Vec4ul res, Vec4ul a, Vec4ul b) {
        return div(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul div(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return div(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul div(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = Long.divideUnsigned(a.x.value, bX);
        res.y.value = Long.divideUnsigned(a.y.value, bY);
        res.z.value = Long.divideUnsigned(a.z.value, bZ);
        res.w.value = Long.divideUnsigned(a.w.value, bW);
        return res;
    }

    public static Vec4us div_(Vec4us a, UShort b) {
        return div(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us div_(Vec4us a, short b) {
        return div(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us div_(Vec4us a, int b) {
        return div(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us div_(Vec4us a, Vec4us b) {
        return div(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us div(Vec4us a, UShort b) {
        return div(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us div(Vec4us a, short b) {
        return div(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us div(Vec4us a, int b) {
        return div(a, a, b, b, b, b);
    }

    public static Vec4us div(Vec4us a, Vec4us b) {
        return div(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, UShort b) {
        return div(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, short b) {
        return div(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, int b) {
        return div(res, a, b, b, b, b);
    }

    public static Vec4us div(Vec4us res, Vec4us a, Vec4us b) {
        return div(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return div(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return div(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us div(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) Integer.divideUnsigned(a.x.value & 0xffff, bX);
        res.y.value = (short) Integer.divideUnsigned(a.y.value & 0xffff, bY);
        res.z.value = (short) Integer.divideUnsigned(a.z.value & 0xffff, bZ);
        res.w.value = (short) Integer.divideUnsigned(a.w.value & 0xffff, bW);
        return res;
    }

    public static Vec2 incr_(Vec2 v) {
        return incr(new Vec2(), v);
    }

    public static Vec2 incr(Vec2 v) {
        return incr(v, v);
    }

    public static Vec2 incr(Vec2 res, Vec2 a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        return res;
    }

    public static Vec2b incr_(Vec2b v) {
        return incr(new Vec2b(), v);
    }

    public static Vec2b incr(Vec2b v) {
        return incr(v, v);
    }

    public static Vec2b incr(Vec2b res, Vec2b a) {
        res.x = (byte) (a.x + 1);
        res.y = (byte) (a.y + 1);
        return res;
    }

    public static Vec2d incr_(Vec2d v) {
        return incr(new Vec2d(), v);
    }

    public static Vec2d incr(Vec2d v) {
        return incr(v, v);
    }

    public static Vec2d incr(Vec2d res, Vec2d a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        return res;
    }

    public static Vec2i incr_(Vec2i v) {
        return incr(new Vec2i(), v);
    }

    public static Vec2i incr(Vec2i v) {
        return incr(v, v);
    }

    public static Vec2i incr(Vec2i res, Vec2i a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        return res;
    }

    public static Vec2l incr_(Vec2l v) {
        return incr(new Vec2l(), v);
    }

    public static Vec2l incr(Vec2l v) {
        return incr(v, v);
    }

    public static Vec2l incr(Vec2l res, Vec2l a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        return res;
    }

    public static Vec2s incr_(Vec2s v) {
        return incr(new Vec2s(), v);
    }

    public static Vec2s incr(Vec2s v) {
        return incr(v, v);
    }

    public static Vec2s incr(Vec2s res, Vec2s a) {
        res.x = (short) (a.x + 1);
        res.y = (short) (a.y + 1);
        return res;
    }

    public static Vec2ub incr_(Vec2ub v) {
        return incr(new Vec2ub(), v);
    }

    public static Vec2ub incr(Vec2ub v) {
        return incr(v, v);
    }

    public static Vec2ub incr(Vec2ub res, Vec2ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) + 1);
        res.y.value = (byte) ((a.y.value & 0xff) + 1);
        return res;
    }

    public static Vec2ui incr_(Vec2ui v) {
        return incr(new Vec2ui(), v);
    }

    public static Vec2ui incr(Vec2ui v) {
        return incr(v, v);
    }

    public static Vec2ui incr(Vec2ui res, Vec2ui a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        return res;
    }

    public static Vec2ul incr_(Vec2ul v) {
        return incr(new Vec2ul(), v);
    }

    public static Vec2ul incr(Vec2ul v) {
        return incr(v, v);
    }

    public static Vec2ul incr(Vec2ul res, Vec2ul a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        return res;
    }

    public static Vec2us incr_(Vec2us v) {
        return incr(new Vec2us(), v);
    }

    public static Vec2us incr(Vec2us v) {
        return incr(v, v);
    }

    public static Vec2us incr(Vec2us res, Vec2us a) {
        res.x.value = (short) ((a.x.value & 0xffff) + 1);
        res.y.value = (short) ((a.y.value & 0xffff) + 1);
        return res;
    }

    public static Vec3 incr_(Vec3 v) {
        return incr(new Vec3(), v);
    }

    public static Vec3 incr(Vec3 v) {
        return incr(v, v);
    }

    public static Vec3 incr(Vec3 res, Vec3 a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        return res;
    }

    public static Vec3b incr_(Vec3b v) {
        return incr(new Vec3b(), v);
    }

    public static Vec3b incr(Vec3b v) {
        return incr(v, v);
    }

    public static Vec3b incr(Vec3b res, Vec3b a) {
        res.x = (byte) (a.x + 1);
        res.y = (byte) (a.y + 1);
        res.z = (byte) (a.z + 1);
        return res;
    }

    public static Vec3d incr_(Vec3d v) {
        return incr(new Vec3d(), v);
    }

    public static Vec3d incr(Vec3d v) {
        return incr(v, v);
    }

    public static Vec3d incr(Vec3d res, Vec3d a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        return res;
    }

    public static Vec3i incr_(Vec3i v) {
        return incr(new Vec3i(), v);
    }

    public static Vec3i incr(Vec3i v) {
        return incr(v, v);
    }

    public static Vec3i incr(Vec3i res, Vec3i a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        return res;
    }

    public static Vec3l incr_(Vec3l v) {
        return incr(new Vec3l(), v);
    }

    public static Vec3l incr(Vec3l v) {
        return incr(v, v);
    }

    public static Vec3l incr(Vec3l res, Vec3l a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        return res;
    }

    public static Vec3s incr_(Vec3s v) {
        return incr(new Vec3s(), v);
    }

    public static Vec3s incr(Vec3s v) {
        return incr(v, v);
    }

    public static Vec3s incr(Vec3s res, Vec3s a) {
        res.x = (short) (a.x + 1);
        res.y = (short) (a.y + 1);
        res.z = (short) (a.z + 1);
        return res;
    }

    public static Vec3ub incr_(Vec3ub v) {
        return incr(new Vec3ub(), v);
    }

    public static Vec3ub incr(Vec3ub v) {
        return incr(v, v);
    }

    public static Vec3ub incr(Vec3ub res, Vec3ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) + 1);
        res.y.value = (byte) ((a.y.value & 0xff) + 1);
        res.z.value = (byte) ((a.z.value & 0xff) + 1);
        return res;
    }

    public static Vec3ui incr_(Vec3ui v) {
        return incr(new Vec3ui(), v);
    }

    public static Vec3ui incr(Vec3ui v) {
        return incr(v, v);
    }

    public static Vec3ui incr(Vec3ui res, Vec3ui a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        res.z.value = a.z.value + 1;
        return res;
    }

    public static Vec3ul incr_(Vec3ul v) {
        return incr(new Vec3ul(), v);
    }

    public static Vec3ul incr(Vec3ul v) {
        return incr(v, v);
    }

    public static Vec3ul incr(Vec3ul res, Vec3ul a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        res.z.value = a.z.value + 1;
        return res;
    }

    public static Vec3us incr_(Vec3us v) {
        return incr(new Vec3us(), v);
    }

    public static Vec3us incr(Vec3us v) {
        return incr(v, v);
    }

    public static Vec3us incr(Vec3us res, Vec3us a) {
        res.x.value = (short) ((a.x.value & 0xffff) + 1);
        res.y.value = (short) ((a.y.value & 0xffff) + 1);
        res.z.value = (short) ((a.z.value & 0xffff) + 1);
        return res;
    }

    public static Vec4 incr_(Vec4 v) {
        return incr(new Vec4(), v);
    }

    public static Vec4 incr(Vec4 v) {
        return incr(v, v);
    }

    public static Vec4 incr(Vec4 res, Vec4 a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        res.w = a.w + 1;
        return res;
    }

    public static Vec4b incr_(Vec4b v) {
        return incr(new Vec4b(), v);
    }

    public static Vec4b incr(Vec4b v) {
        return incr(v, v);
    }

    public static Vec4b incr(Vec4b res, Vec4b a) {
        res.x = (byte) (a.x + 1);
        res.y = (byte) (a.y + 1);
        res.z = (byte) (a.z + 1);
        res.w = (byte) (a.w + 1);
        return res;
    }

    public static Vec4d incr_(Vec4d v) {
        return incr(new Vec4d(), v);
    }

    public static Vec4d incr(Vec4d v) {
        return incr(v, v);
    }

    public static Vec4d incr(Vec4d res, Vec4d a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        res.w = a.w + 1;
        return res;
    }

    public static Vec4i incr_(Vec4i v) {
        return incr(new Vec4i(), v);
    }

    public static Vec4i incr(Vec4i v) {
        return incr(v, v);
    }

    public static Vec4i incr(Vec4i res, Vec4i a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        res.w = a.w + 1;
        return res;
    }

    public static Vec4l incr_(Vec4l v) {
        return incr(new Vec4l(), v);
    }

    public static Vec4l incr(Vec4l v) {
        return incr(v, v);
    }

    public static Vec4l incr(Vec4l res, Vec4l a) {
        res.x = a.x + 1;
        res.y = a.y + 1;
        res.z = a.z + 1;
        res.w = a.w + 1;
        return res;
    }

    public static Vec4s incr_(Vec4s v) {
        return incr(new Vec4s(), v);
    }

    public static Vec4s incr(Vec4s v) {
        return incr(v, v);
    }

    public static Vec4s incr(Vec4s res, Vec4s a) {
        res.x = (short) (a.x + 1);
        res.y = (short) (a.y + 1);
        res.z = (short) (a.z + 1);
        res.w = (short) (a.w + 1);
        return res;
    }

    public static Vec4ub incr_(Vec4ub v) {
        return incr(new Vec4ub(), v);
    }

    public static Vec4ub incr(Vec4ub v) {
        return incr(v, v);
    }

    public static Vec4ub incr(Vec4ub res, Vec4ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) + 1);
        res.y.value = (byte) ((a.y.value & 0xff) + 1);
        res.z.value = (byte) ((a.z.value & 0xff) + 1);
        res.w.value = (byte) ((a.w.value & 0xff) + 1);
        return res;
    }

    public static Vec4ui incr_(Vec4ui v) {
        return incr(new Vec4ui(), v);
    }

    public static Vec4ui incr(Vec4ui v) {
        return incr(v, v);
    }

    public static Vec4ui incr(Vec4ui res, Vec4ui a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        res.z.value = a.z.value + 1;
        res.w.value = a.w.value + 1;
        return res;
    }

    public static Vec4ul incr_(Vec4ul v) {
        return incr(new Vec4ul(), v);
    }

    public static Vec4ul incr(Vec4ul v) {
        return incr(v, v);
    }

    public static Vec4ul incr(Vec4ul res, Vec4ul a) {
        res.x.value = a.x.value + 1;
        res.y.value = a.y.value + 1;
        res.z.value = a.z.value + 1;
        res.w.value = a.w.value + 1;
        return res;
    }

    public static Vec4us incr_(Vec4us v) {
        return incr(new Vec4us(), v);
    }

    public static Vec4us incr(Vec4us v) {
        return incr(v, v);
    }

    public static Vec4us incr(Vec4us res, Vec4us a) {
        res.x.value = (short) ((a.x.value & 0xffff) + 1);
        res.y.value = (short) ((a.y.value & 0xffff) + 1);
        res.z.value = (short) ((a.z.value & 0xffff) + 1);
        res.w.value = (short) ((a.w.value & 0xffff) + 1);
        return res;
    }

    public static Vec2 decr_(Vec2 v) {
        return decr(new Vec2(), v);
    }

    public static Vec2 decr(Vec2 v) {
        return decr(v, v);
    }

    public static Vec2 decr(Vec2 res, Vec2 a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        return res;
    }

    public static Vec2b decr_(Vec2b v) {
        return decr(new Vec2b(), v);
    }

    public static Vec2b decr(Vec2b v) {
        return decr(v, v);
    }

    public static Vec2b decr(Vec2b res, Vec2b a) {
        res.x = (byte) (a.x - 1);
        res.y = (byte) (a.y - 1);
        return res;
    }

    public static Vec2d decr_(Vec2d v) {
        return decr(new Vec2d(), v);
    }

    public static Vec2d decr(Vec2d v) {
        return decr(v, v);
    }

    public static Vec2d decr(Vec2d res, Vec2d a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        return res;
    }

    public static Vec2i decr_(Vec2i v) {
        return decr(new Vec2i(), v);
    }

    public static Vec2i decr(Vec2i v) {
        return decr(v, v);
    }

    public static Vec2i decr(Vec2i res, Vec2i a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        return res;
    }

    public static Vec2l decr_(Vec2l v) {
        return decr(new Vec2l(), v);
    }

    public static Vec2l decr(Vec2l v) {
        return decr(v, v);
    }

    public static Vec2l decr(Vec2l res, Vec2l a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        return res;
    }

    public static Vec2s decr_(Vec2s v) {
        return decr(new Vec2s(), v);
    }

    public static Vec2s decr(Vec2s v) {
        return decr(v, v);
    }

    public static Vec2s decr(Vec2s res, Vec2s a) {
        res.x = (short) (a.x - 1);
        res.y = (short) (a.y - 1);
        return res;
    }

    public static Vec2ub decr_(Vec2ub v) {
        return decr(new Vec2ub(), v);
    }

    public static Vec2ub decr(Vec2ub v) {
        return decr(v, v);
    }

    public static Vec2ub decr(Vec2ub res, Vec2ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) - 1);
        res.y.value = (byte) ((a.y.value & 0xff) - 1);
        return res;
    }

    public static Vec2ui decr_(Vec2ui v) {
        return decr(new Vec2ui(), v);
    }

    public static Vec2ui decr(Vec2ui v) {
        return decr(v, v);
    }

    public static Vec2ui decr(Vec2ui res, Vec2ui a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        return res;
    }

    public static Vec2ul decr_(Vec2ul v) {
        return decr(new Vec2ul(), v);
    }

    public static Vec2ul decr(Vec2ul v) {
        return decr(v, v);
    }

    public static Vec2ul decr(Vec2ul res, Vec2ul a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        return res;
    }

    public static Vec2us decr_(Vec2us v) {
        return decr(new Vec2us(), v);
    }

    public static Vec2us decr(Vec2us v) {
        return decr(v, v);
    }

    public static Vec2us decr(Vec2us res, Vec2us a) {
        res.x.value = (short) ((a.x.value & 0xffff) - 1);
        res.y.value = (short) ((a.y.value & 0xffff) - 1);
        return res;
    }

    public static Vec3 decr_(Vec3 v) {
        return decr(new Vec3(), v);
    }

    public static Vec3 decr(Vec3 v) {
        return decr(v, v);
    }

    public static Vec3 decr(Vec3 res, Vec3 a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        return res;
    }

    public static Vec3b decr_(Vec3b v) {
        return decr(new Vec3b(), v);
    }

    public static Vec3b decr(Vec3b v) {
        return decr(v, v);
    }

    public static Vec3b decr(Vec3b res, Vec3b a) {
        res.x = (byte) (a.x - 1);
        res.y = (byte) (a.y - 1);
        res.z = (byte) (a.z - 1);
        return res;
    }

    public static Vec3d decr_(Vec3d v) {
        return decr(new Vec3d(), v);
    }

    public static Vec3d decr(Vec3d v) {
        return decr(v, v);
    }

    public static Vec3d decr(Vec3d res, Vec3d a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        return res;
    }

    public static Vec3i decr_(Vec3i v) {
        return decr(new Vec3i(), v);
    }

    public static Vec3i decr(Vec3i v) {
        return decr(v, v);
    }

    public static Vec3i decr(Vec3i res, Vec3i a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        return res;
    }

    public static Vec3l decr_(Vec3l v) {
        return decr(new Vec3l(), v);
    }

    public static Vec3l decr(Vec3l v) {
        return decr(v, v);
    }

    public static Vec3l decr(Vec3l res, Vec3l a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        return res;
    }

    public static Vec3s decr_(Vec3s v) {
        return decr(new Vec3s(), v);
    }

    public static Vec3s decr(Vec3s v) {
        return decr(v, v);
    }

    public static Vec3s decr(Vec3s res, Vec3s a) {
        res.x = (short) (a.x - 1);
        res.y = (short) (a.y - 1);
        res.z = (short) (a.z - 1);
        return res;
    }

    public static Vec3ub decr_(Vec3ub v) {
        return decr(new Vec3ub(), v);
    }

    public static Vec3ub decr(Vec3ub v) {
        return decr(v, v);
    }

    public static Vec3ub decr(Vec3ub res, Vec3ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) - 1);
        res.y.value = (byte) ((a.y.value & 0xff) - 1);
        res.z.value = (byte) ((a.z.value & 0xff) - 1);
        return res;
    }

    public static Vec3ui decr_(Vec3ui v) {
        return decr(new Vec3ui(), v);
    }

    public static Vec3ui decr(Vec3ui v) {
        return decr(v, v);
    }

    public static Vec3ui decr(Vec3ui res, Vec3ui a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        res.z.value = a.z.value - 1;
        return res;
    }

    public static Vec3ul decr_(Vec3ul v) {
        return decr(new Vec3ul(), v);
    }

    public static Vec3ul decr(Vec3ul v) {
        return decr(v, v);
    }

    public static Vec3ul decr(Vec3ul res, Vec3ul a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        res.z.value = a.z.value - 1;
        return res;
    }

    public static Vec3us decr_(Vec3us v) {
        return decr(new Vec3us(), v);
    }

    public static Vec3us decr(Vec3us v) {
        return decr(v, v);
    }

    public static Vec3us decr(Vec3us res, Vec3us a) {
        res.x.value = (short) ((a.x.value & 0xffff) - 1);
        res.y.value = (short) ((a.y.value & 0xffff) - 1);
        res.z.value = (short) ((a.z.value & 0xffff) - 1);
        return res;
    }

    public static Vec4 decr_(Vec4 v) {
        return decr(new Vec4(), v);
    }

    public static Vec4 decr(Vec4 v) {
        return decr(v, v);
    }

    public static Vec4 decr(Vec4 res, Vec4 a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        res.w = a.w - 1;
        return res;
    }

    public static Vec4b decr_(Vec4b v) {
        return decr(new Vec4b(), v);
    }

    public static Vec4b decr(Vec4b v) {
        return decr(v, v);
    }

    public static Vec4b decr(Vec4b res, Vec4b a) {
        res.x = (byte) (a.x - 1);
        res.y = (byte) (a.y - 1);
        res.z = (byte) (a.z - 1);
        res.w = (byte) (a.w - 1);
        return res;
    }

    public static Vec4d decr_(Vec4d v) {
        return decr(new Vec4d(), v);
    }

    public static Vec4d decr(Vec4d v) {
        return decr(v, v);
    }

    public static Vec4d decr(Vec4d res, Vec4d a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        res.w = a.w - 1;
        return res;
    }

    public static Vec4i decr_(Vec4i v) {
        return decr(new Vec4i(), v);
    }

    public static Vec4i decr(Vec4i v) {
        return decr(v, v);
    }

    public static Vec4i decr(Vec4i res, Vec4i a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        res.w = a.w - 1;
        return res;
    }

    public static Vec4l decr_(Vec4l v) {
        return decr(new Vec4l(), v);
    }

    public static Vec4l decr(Vec4l v) {
        return decr(v, v);
    }

    public static Vec4l decr(Vec4l res, Vec4l a) {
        res.x = a.x - 1;
        res.y = a.y - 1;
        res.z = a.z - 1;
        res.w = a.w - 1;
        return res;
    }

    public static Vec4s decr_(Vec4s v) {
        return decr(new Vec4s(), v);
    }

    public static Vec4s decr(Vec4s v) {
        return decr(v, v);
    }

    public static Vec4s decr(Vec4s res, Vec4s a) {
        res.x = (short) (a.x - 1);
        res.y = (short) (a.y - 1);
        res.z = (short) (a.z - 1);
        res.w = (short) (a.w - 1);
        return res;
    }

    public static Vec4ub decr_(Vec4ub v) {
        return decr(new Vec4ub(), v);
    }

    public static Vec4ub decr(Vec4ub v) {
        return decr(v, v);
    }

    public static Vec4ub decr(Vec4ub res, Vec4ub a) {
        res.x.value = (byte) ((a.x.value & 0xff) - 1);
        res.y.value = (byte) ((a.y.value & 0xff) - 1);
        res.z.value = (byte) ((a.z.value & 0xff) - 1);
        res.w.value = (byte) ((a.w.value & 0xff) - 1);
        return res;
    }

    public static Vec4ui decr_(Vec4ui v) {
        return decr(new Vec4ui(), v);
    }

    public static Vec4ui decr(Vec4ui v) {
        return decr(v, v);
    }

    public static Vec4ui decr(Vec4ui res, Vec4ui a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        res.z.value = a.z.value - 1;
        res.w.value = a.w.value - 1;
        return res;
    }

    public static Vec4ul decr_(Vec4ul v) {
        return decr(new Vec4ul(), v);
    }

    public static Vec4ul decr(Vec4ul v) {
        return decr(v, v);
    }

    public static Vec4ul decr(Vec4ul res, Vec4ul a) {
        res.x.value = a.x.value - 1;
        res.y.value = a.y.value - 1;
        res.z.value = a.z.value - 1;
        res.w.value = a.w.value - 1;
        return res;
    }

    public static Vec4us decr_(Vec4us v) {
        return decr(new Vec4us(), v);
    }

    public static Vec4us decr(Vec4us v) {
        return decr(v, v);
    }

    public static Vec4us decr(Vec4us res, Vec4us a) {
        res.x.value = (short) ((a.x.value & 0xffff) - 1);
        res.y.value = (short) ((a.y.value & 0xffff) - 1);
        res.z.value = (short) ((a.z.value & 0xffff) - 1);
        res.w.value = (short) ((a.w.value & 0xffff) - 1);
        return res;
    }
}
