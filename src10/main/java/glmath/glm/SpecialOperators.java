/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.vec._2.b.Vec2b;
import glmath.glm.vec._2.i.Vec2i;
import glmath.glm.vec._2.l.Vec2l;
import glmath.glm.vec._2.s.Vec2s;
import glmath.glm.vec._2.ub.Vec2ub;
import glmath.glm.vec._2.ui.Vec2ui;
import glmath.glm.vec._2.ul.Vec2ul;
import glmath.glm.vec._2.us.Vec2us;
import glmath.glm.vec._3.b.Vec3b;
import glmath.glm.vec._3.i.Vec3i;
import glmath.glm.vec._3.l.Vec3l;
import glmath.glm.vec._3.s.Vec3s;
import glmath.glm.vec._3.ub.Vec3ub;
import glmath.glm.vec._3.ui.Vec3ui;
import glmath.glm.vec._3.ul.Vec3ul;
import glmath.glm.vec._3.us.Vec3us;
import glmath.glm.vec._4.b.Vec4b;
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
abstract class SpecialOperators extends BooleansOperators {

    public static Vec2b mod_(Vec2b a, byte b) {
        return mod(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b mod_(Vec2b a, int b) {
        return mod(new Vec2b(), a, b, b);
    }

    public static Vec2b mod_(Vec2b a, Vec2b b) {
        return mod(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b mod(Vec2b a, byte b) {
        return mod(a, a, (int) b, b);
    }

    public static Vec2b mod(Vec2b a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2b mod(Vec2b a, Vec2b b) {
        return mod(a, a, (int) b.x, b.y);
    }

    public static Vec2b mod(Vec2b res, Vec2b a, byte b) {
        return mod(res, a, (int) b, b);
    }

    public static Vec2b mod(Vec2b res, Vec2b a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2b mod(Vec2b res, Vec2b a, Vec2b b) {
        return mod(res, a, (int) b.x, b.y);
    }

    public static Vec2b mod(Vec2b res, Vec2b a, byte bX, byte bY) {
        return mod(res, a, (int) bX, bY);
    }

    public static Vec2b mod(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x % bX);
        res.y = (byte) (a.y % bY);
        return res;
    }

    public static Vec2i mod_(Vec2i a, int b) {
        return mod(new Vec2i(), a, b, b);
    }

    public static Vec2i mod_(Vec2i a, Vec2i b) {
        return mod(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i mod(Vec2i a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2i mod(Vec2i a, Vec2i b) {
        return mod(a, a, b.x, b.y);
    }

    public static Vec2i mod(Vec2i res, Vec2i a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2i mod(Vec2i res, Vec2i a, Vec2i b) {
        return mod(res, a, b.x, b.y);
    }

    public static Vec2i mod(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        return res;
    }

    public static Vec2l mod_(Vec2l a, long b) {
        return mod(new Vec2l(), a, b, b);
    }

    public static Vec2l mod_(Vec2l a, Vec2l b) {
        return mod(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l mod(Vec2l a, long b) {
        return mod(a, a, b, b);
    }

    public static Vec2l mod(Vec2l a, Vec2l b) {
        return mod(a, a, b.x, b.y);
    }

    public static Vec2l mod(Vec2l res, Vec2l a, long b) {
        return mod(res, a, b, b);
    }

    public static Vec2l mod(Vec2l res, Vec2l a, Vec2l b) {
        return mod(res, a, b.x, b.y);
    }

    public static Vec2l mod(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        return res;
    }

    public static Vec2s mod_(Vec2s a, short b) {
        return mod(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s mod_(Vec2s a, int b) {
        return mod(new Vec2s(), a, b, b);
    }

    public static Vec2s mod_(Vec2s a, Vec2s b) {
        return mod(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s mod(Vec2s a, short b) {
        return mod(a, a, (int) b, b);
    }

    public static Vec2s mod(Vec2s a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2s mod(Vec2s a, Vec2s b) {
        return mod(a, a, (int) b.x, b.y);
    }

    public static Vec2s mod(Vec2s res, Vec2s a, short b) {
        return mod(res, a, (int) b, b);
    }

    public static Vec2s mod(Vec2s res, Vec2s a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2s mod(Vec2s res, Vec2s a, Vec2s b) {
        return mod(res, a, (int) b.x, b.y);
    }

    public static Vec2s mod(Vec2s res, Vec2s a, short bX, short bY) {
        return mod(res, a, (int) bX, bY);
    }

    public static Vec2s mod(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x % bX);
        res.y = (short) (a.y % bY);
        return res;
    }

    public static Vec2ub mod_(Vec2ub a, UByte b) {
        return mod(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mod_(Vec2ub a, byte b) {
        return mod(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mod_(Vec2ub a, int b) {
        return mod(new Vec2ub(), a, b, b);
    }

    public static Vec2ub mod_(Vec2ub a, Vec2ub b) {
        return mod(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub a, UByte b) {
        return mod(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub a, byte b) {
        return mod(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mod(Vec2ub a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2ub mod(Vec2ub a, Vec2ub b) {
        return mod(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, UByte b) {
        return mod(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, byte b) {
        return mod(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, Vec2ub b) {
        return mod(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return mod(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return mod(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub mod(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) (Integer.remainderUnsigned(a.x.value & 0xff, bX));
        res.y.value = (byte) (Integer.remainderUnsigned(a.y.value & 0xff, bY));
        return res;
    }

    public static Vec2ui mod_(Vec2ui a, UInt b) {
        return mod(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui mod_(Vec2ui a, int b) {
        return mod(new Vec2ui(), a, b, b);
    }

    public static Vec2ui mod_(Vec2ui a, Vec2ui b) {
        return mod(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui mod(Vec2ui a, UInt b) {
        return mod(a, a, b.value, b.value);
    }

    public static Vec2ui mod(Vec2ui a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2ui mod(Vec2ui a, Vec2ui b) {
        return mod(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui mod(Vec2ui res, Vec2ui a, UInt b) {
        return mod(res, a, b.value, b.value);
    }

    public static Vec2ui mod(Vec2ui res, Vec2ui a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2ui mod(Vec2ui res, Vec2ui a, Vec2ui b) {
        return mod(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui mod(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return mod(res, a, bX.value, bY.value);
    }

    public static Vec2ui mod(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = Integer.remainderUnsigned(a.x.value, bX);
        res.y.value = Integer.remainderUnsigned(a.y.value, bY);
        return res;
    }

    public static Vec2ul mod_(Vec2ul a, ULong b) {
        return mod(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul mod_(Vec2ul a, long b) {
        return mod(new Vec2ul(), a, b, b);
    }

    public static Vec2ul mod_(Vec2ul a, Vec2ul b) {
        return mod(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul mod(Vec2ul a, ULong b) {
        return mod(a, a, b.value, b.value);
    }

    public static Vec2ul mod(Vec2ul a, long b) {
        return mod(a, a, b, b);
    }

    public static Vec2ul mod(Vec2ul a, Vec2ul b) {
        return mod(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul mod(Vec2ul res, Vec2ul a, ULong b) {
        return mod(res, a, b.value, b.value);
    }

    public static Vec2ul mod(Vec2ul res, Vec2ul a, long b) {
        return mod(res, a, b, b);
    }

    public static Vec2ul mod(Vec2ul res, Vec2ul a, Vec2ul b) {
        return mod(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul mod(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return mod(res, a, bX.value, bY.value);
    }

    public static Vec2ul mod(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = Long.remainderUnsigned(a.x.value, bX);
        res.y.value = Long.remainderUnsigned(a.y.value, bY);
        return res;
    }

    public static Vec2us mod_(Vec2us a, UShort b) {
        return mod(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mod_(Vec2us a, short b) {
        return mod(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mod_(Vec2us a, int b) {
        return mod(new Vec2us(), a, b, b);
    }

    public static Vec2us mod_(Vec2us a, Vec2us b) {
        return mod(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mod(Vec2us a, UShort b) {
        return mod(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mod(Vec2us a, short b) {
        return mod(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mod(Vec2us a, int b) {
        return mod(a, a, b, b);
    }

    public static Vec2us mod(Vec2us a, Vec2us b) {
        return mod(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, UShort b) {
        return mod(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, short b) {
        return mod(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, int b) {
        return mod(res, a, b, b);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, Vec2us b) {
        return mod(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return mod(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, short bX, short bY) {
        return mod(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us mod(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) (Integer.remainderUnsigned(a.x.value & 0xffff, bX));
        res.y.value = (short) (Integer.remainderUnsigned(a.y.value & 0xffff, bY));
        return res;
    }

    public static Vec3b mod_(Vec3b a, byte b) {
        return mod(new Vec3b(), a, b, b, b);
    }

    public static Vec3b mod_(Vec3b a, int b) {
        return mod(new Vec3b(), a, b, b, b);
    }

    public static Vec3b mod_(Vec3b a, Vec3b b) {
        return mod(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b mod(Vec3b a, byte b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3b mod(Vec3b a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3b mod(Vec3b a, Vec3b b) {
        return mod(a, a, b.x, b.y, b.z);
    }

    public static Vec3b mod(Vec3b res, Vec3b a, byte b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3b mod(Vec3b res, Vec3b a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3b mod(Vec3b res, Vec3b a, Vec3b b) {
        return mod(res, a, b.x, b.y, b.z);
    }

    public static Vec3b mod(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return mod(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b mod(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x % bX);
        res.y = (byte) (a.y % bY);
        res.z = (byte) (a.z % bZ);
        return res;
    }

    public static Vec3i mod_(Vec3i a, int b) {
        return mod(new Vec3i(), a, b, b, b);
    }

    public static Vec3i mod_(Vec3i a, Vec3i b) {
        return mod(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i mod(Vec3i a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3i mod(Vec3i a, Vec3i b) {
        return mod(a, a, b.x, b.y, b.z);
    }

    public static Vec3i mod(Vec3i res, Vec3i a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3i mod(Vec3i res, Vec3i a, Vec3i b) {
        return mod(res, a, b.x, b.y, b.z);
    }

    public static Vec3i mod(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        res.z = a.z % bZ;
        return res;
    }

    public static Vec3l mod_(Vec3l a, long b) {
        return mod(new Vec3l(), a, b, b, b);
    }

    public static Vec3l mod_(Vec3l a, Vec3l b) {
        return mod(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l mod(Vec3l a, long b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3l mod(Vec3l a, Vec3l b) {
        return mod(a, a, b.x, b.y, b.z);
    }

    public static Vec3l mod(Vec3l res, Vec3l a, long b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3l mod(Vec3l res, Vec3l a, Vec3l b) {
        return mod(res, a, b.x, b.y, b.z);
    }

    public static Vec3l mod(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        res.z = a.z % bZ;
        return res;
    }

    public static Vec3s mod_(Vec3s a, short b) {
        return mod(new Vec3s(), a, b, b, b);
    }

    public static Vec3s mod_(Vec3s a, int b) {
        return mod(new Vec3s(), a, b, b, b);
    }

    public static Vec3s mod_(Vec3s a, Vec3s b) {
        return mod(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s mod(Vec3s a, short b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3s mod(Vec3s a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3s mod(Vec3s a, Vec3s b) {
        return mod(a, a, b.x, b.y, b.z);
    }

    public static Vec3s mod(Vec3s res, Vec3s a, short b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3s mod(Vec3s res, Vec3s a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3s mod(Vec3s res, Vec3s a, Vec3s b) {
        return mod(res, a, b.x, b.y, b.z);
    }

    public static Vec3s mod(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return mod(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s mod(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x % bX);
        res.y = (short) (a.y % bY);
        res.z = (short) (a.z % bZ);
        return res;
    }

    public static Vec3ub mod_(Vec3ub a, UByte b) {
        return mod(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mod_(Vec3ub a, byte b) {
        return mod(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mod_(Vec3ub a, int b) {
        return mod(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub mod_(Vec3ub a, Vec3ub b) {
        return mod(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub a, UByte b) {
        return mod(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub a, byte b) {
        return mod(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mod(Vec3ub a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3ub mod(Vec3ub a, Vec3ub b) {
        return mod(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, UByte b) {
        return mod(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, byte b) {
        return mod(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, Vec3ub b) {
        return mod(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return mod(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return mod(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub mod(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) (Integer.remainderUnsigned(a.x.value & 0xff, bX));
        res.y.value = (byte) (Integer.remainderUnsigned(a.y.value & 0xff, bY));
        res.z.value = (byte) (Integer.remainderUnsigned(a.z.value & 0xff, bZ));
        return res;
    }

    public static Vec3ui mod_(Vec3ui a, UInt b) {
        return mod(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui mod_(Vec3ui a, int b) {
        return mod(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui mod_(Vec3ui a, Vec3ui b) {
        return mod(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui mod(Vec3ui a, UInt b) {
        return mod(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui mod(Vec3ui a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3ui mod(Vec3ui a, Vec3ui b) {
        return mod(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui mod(Vec3ui res, Vec3ui a, UInt b) {
        return mod(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui mod(Vec3ui res, Vec3ui a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3ui mod(Vec3ui res, Vec3ui a, Vec3ui b) {
        return mod(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui mod(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return mod(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui mod(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = Integer.remainderUnsigned(a.x.value, bX);
        res.y.value = Integer.remainderUnsigned(a.y.value, bY);
        res.z.value = Integer.remainderUnsigned(a.z.value, bZ);
        return res;
    }

    public static Vec3ul mod_(Vec3ul a, ULong b) {
        return mod(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul mod_(Vec3ul a, long b) {
        return mod(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul mod_(Vec3ul a, Vec3ul b) {
        return mod(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mod(Vec3ul a, ULong b) {
        return mod(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul mod(Vec3ul a, long b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3ul mod(Vec3ul a, Vec3ul b) {
        return mod(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mod(Vec3ul res, Vec3ul a, ULong b) {
        return mod(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul mod(Vec3ul res, Vec3ul a, long b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3ul mod(Vec3ul res, Vec3ul a, Vec3ul b) {
        return mod(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul mod(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return mod(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul mod(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = Long.remainderUnsigned(a.x.value, bX);
        res.y.value = Long.remainderUnsigned(a.y.value, bY);
        res.z.value = Long.remainderUnsigned(a.z.value, bZ);
        return res;
    }

    public static Vec3us mod_(Vec3us a, UShort b) {
        return mod(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mod_(Vec3us a, short b) {
        return mod(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mod_(Vec3us a, int b) {
        return mod(new Vec3us(), a, b, b, b);
    }

    public static Vec3us mod_(Vec3us a, Vec3us b) {
        return mod(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mod(Vec3us a, UShort b) {
        return mod(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mod(Vec3us a, short b) {
        return mod(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mod(Vec3us a, int b) {
        return mod(a, a, b, b, b);
    }

    public static Vec3us mod(Vec3us a, Vec3us b) {
        return mod(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, UShort b) {
        return mod(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, short b) {
        return mod(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, int b) {
        return mod(res, a, b, b, b);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, Vec3us b) {
        return mod(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return mod(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return mod(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us mod(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) (Integer.remainderUnsigned(a.x.value & 0xffff, bX));
        res.y.value = (short) (Integer.remainderUnsigned(a.y.value & 0xffff, bY));
        res.z.value = (short) (Integer.remainderUnsigned(a.z.value & 0xffff, bZ));
        return res;
    }

    public static Vec4b mod_(Vec4b a, byte b) {
        return mod(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b mod_(Vec4b a, int b) {
        return mod(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b mod_(Vec4b a, Vec4b b) {
        return mod(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mod(Vec4b a, byte b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4b mod(Vec4b a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4b mod(Vec4b a, Vec4b b) {
        return mod(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mod(Vec4b res, Vec4b a, byte b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4b mod(Vec4b res, Vec4b a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4b mod(Vec4b res, Vec4b a, Vec4b b) {
        return mod(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b mod(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return mod(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b mod(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x % bX);
        res.y = (byte) (a.y % bY);
        res.z = (byte) (a.z % bZ);
        res.w = (byte) (a.w % bW);
        return res;
    }

    public static Vec4i mod_(Vec4i a, int b) {
        return mod(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i mod_(Vec4i a, Vec4i b) {
        return mod(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mod(Vec4i a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4i mod(Vec4i a, Vec4i b) {
        return mod(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mod(Vec4i res, Vec4i a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4i mod(Vec4i res, Vec4i a, Vec4i b) {
        return mod(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i mod(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        res.z = a.z % bZ;
        res.w = a.w % bW;
        return res;
    }

    public static Vec4l mod_(Vec4l a, long b) {
        return mod(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l mod_(Vec4l a, Vec4l b) {
        return mod(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mod(Vec4l a, long b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4l mod(Vec4l a, Vec4l b) {
        return mod(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mod(Vec4l res, Vec4l a, long b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4l mod(Vec4l res, Vec4l a, Vec4l b) {
        return mod(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l mod(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x % bX;
        res.y = a.y % bY;
        res.z = a.z % bZ;
        res.w = a.w % bW;
        return res;
    }

    public static Vec4s mod_(Vec4s a, short b) {
        return mod(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s mod_(Vec4s a, int b) {
        return mod(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s mod_(Vec4s a, Vec4s b) {
        return mod(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mod(Vec4s a, short b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4s mod(Vec4s a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4s mod(Vec4s a, Vec4s b) {
        return mod(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mod(Vec4s res, Vec4s a, short b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4s mod(Vec4s res, Vec4s a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4s mod(Vec4s res, Vec4s a, Vec4s b) {
        return mod(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s mod(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return mod(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s mod(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x % bX);
        res.y = (short) (a.y % bY);
        res.z = (short) (a.z % bZ);
        res.w = (short) (a.w % bW);
        return res;
    }

    public static Vec4ub mod_(Vec4ub a, UByte b) {
        return mod(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mod_(Vec4ub a, byte b) {
        return mod(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mod_(Vec4ub a, int b) {
        return mod(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub mod_(Vec4ub a, Vec4ub b) {
        return mod(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub a, UByte b) {
        return mod(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub a, byte b) {
        return mod(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mod(Vec4ub a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4ub mod(Vec4ub a, Vec4ub b) {
        return mod(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, UByte b) {
        return mod(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, byte b) {
        return mod(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, Vec4ub b) {
        return mod(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return mod(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return mod(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub mod(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) (Integer.remainderUnsigned(a.x.value & 0xff, bX));
        res.y.value = (byte) (Integer.remainderUnsigned(a.y.value & 0xff, bY));
        res.z.value = (byte) (Integer.remainderUnsigned(a.z.value & 0xff, bZ));
        res.w.value = (byte) (Integer.remainderUnsigned(a.w.value & 0xff, bW));
        return res;
    }

    public static Vec4ui mod_(Vec4ui a, UInt b) {
        return mod(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mod_(Vec4ui a, int b) {
        return mod(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui mod_(Vec4ui a, Vec4ui b) {
        return mod(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui mod(Vec4ui a, UInt b) {
        return mod(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mod(Vec4ui a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4ui mod(Vec4ui a, Vec4ui b) {
        return mod(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui mod(Vec4ui res, Vec4ui a, UInt b) {
        return mod(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui mod(Vec4ui res, Vec4ui a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4ui mod(Vec4ui res, Vec4ui a, Vec4ui b) {
        return mod(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui mod(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return mod(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui mod(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = Integer.remainderUnsigned(a.x.value, bX);
        res.y.value = Integer.remainderUnsigned(a.y.value, bY);
        res.z.value = Integer.remainderUnsigned(a.z.value, bZ);
        res.w.value = Integer.remainderUnsigned(a.w.value, bZ);
        return res;
    }

    public static Vec4ul mod_(Vec4ul a, ULong b) {
        return mod(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mod_(Vec4ul a, long b) {
        return mod(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul mod_(Vec4ul a, Vec4ul b) {
        return mod(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mod(Vec4ul a, ULong b) {
        return mod(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mod(Vec4ul a, long b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4ul mod(Vec4ul a, Vec4ul b) {
        return mod(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mod(Vec4ul res, Vec4ul a, ULong b) {
        return mod(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul mod(Vec4ul res, Vec4ul a, long b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4ul mod(Vec4ul res, Vec4ul a, Vec4ul b) {
        return mod(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul mod(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return mod(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul mod(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = Long.remainderUnsigned(a.x.value, bX);
        res.y.value = Long.remainderUnsigned(a.y.value, bY);
        res.z.value = Long.remainderUnsigned(a.z.value, bZ);
        res.w.value = Long.remainderUnsigned(a.w.value, bW);
        return res;
    }

    public static Vec4us mod_(Vec4us a, UShort b) {
        return mod(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mod_(Vec4us a, short b) {
        return mod(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mod_(Vec4us a, int b) {
        return mod(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us mod_(Vec4us a, Vec4us b) {
        return mod(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mod(Vec4us a, UShort b) {
        return mod(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mod(Vec4us a, short b) {
        return mod(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mod(Vec4us a, int b) {
        return mod(a, a, b, b, b, b);
    }

    public static Vec4us mod(Vec4us a, Vec4us b) {
        return mod(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, UShort b) {
        return mod(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, short b) {
        return mod(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, int b) {
        return mod(res, a, b, b, b, b);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, Vec4us b) {
        return mod(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return mod(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return mod(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us mod(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) (Integer.remainderUnsigned(a.x.value & 0xffff, bX));
        res.y.value = (short) (Integer.remainderUnsigned(a.y.value & 0xffff, bY));
        res.z.value = (short) (Integer.remainderUnsigned(a.z.value & 0xffff, bZ));
        res.w.value = (short) (Integer.remainderUnsigned(a.w.value & 0xffff, bW));
        return res;
    }

    public static Vec2b and_(Vec2b a, byte b) {
        return and(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b and_(Vec2b a, int b) {
        return and(new Vec2b(), a, b, b);
    }

    public static Vec2b and_(Vec2b a, Vec2b b) {
        return and(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b and(Vec2b a, byte b) {
        return and(a, a, (int) b, b);
    }

    public static Vec2b and(Vec2b a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2b and(Vec2b a, Vec2b b) {
        return and(a, a, (int) b.x, b.y);
    }

    public static Vec2b and(Vec2b res, Vec2b a, byte b) {
        return and(res, a, (int) b, b);
    }

    public static Vec2b and(Vec2b res, Vec2b a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2b and(Vec2b res, Vec2b a, Vec2b b) {
        return and(res, a, (int) b.x, b.y);
    }

    public static Vec2b and(Vec2b res, Vec2b a, byte bX, byte bY) {
        return and(res, a, (int) bX, bY);
    }

    public static Vec2b and(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x & bX);
        res.y = (byte) (a.y & bY);
        return res;
    }

    public static Vec2i and_(Vec2i a, int b) {
        return and(new Vec2i(), a, b, b);
    }

    public static Vec2i and_(Vec2i a, Vec2i b) {
        return and(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i and(Vec2i a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2i and(Vec2i a, Vec2i b) {
        return and(a, a, b.x, b.y);
    }

    public static Vec2i and(Vec2i res, Vec2i a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2i and(Vec2i res, Vec2i a, Vec2i b) {
        return and(res, a, b.x, b.y);
    }

    public static Vec2i and(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        return res;
    }

    public static Vec2l and_(Vec2l a, long b) {
        return and(new Vec2l(), a, b, b);
    }

    public static Vec2l and_(Vec2l a, Vec2l b) {
        return and(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l and(Vec2l a, long b) {
        return and(a, a, b, b);
    }

    public static Vec2l and(Vec2l a, Vec2l b) {
        return and(a, a, b.x, b.y);
    }

    public static Vec2l and(Vec2l res, Vec2l a, long b) {
        return and(res, a, b, b);
    }

    public static Vec2l and(Vec2l res, Vec2l a, Vec2l b) {
        return and(res, a, b.x, b.y);
    }

    public static Vec2l and(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        return res;
    }

    public static Vec2s and_(Vec2s a, short b) {
        return and(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s and_(Vec2s a, int b) {
        return and(new Vec2s(), a, b, b);
    }

    public static Vec2s and_(Vec2s a, Vec2s b) {
        return and(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s and(Vec2s a, short b) {
        return and(a, a, (int) b, b);
    }

    public static Vec2s and(Vec2s a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2s and(Vec2s a, Vec2s b) {
        return and(a, a, (int) b.x, b.y);
    }

    public static Vec2s and(Vec2s res, Vec2s a, short b) {
        return and(res, a, (int) b, b);
    }

    public static Vec2s and(Vec2s res, Vec2s a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2s and(Vec2s res, Vec2s a, Vec2s b) {
        return and(res, a, (int) b.x, b.y);
    }

    public static Vec2s and(Vec2s res, Vec2s a, short bX, short bY) {
        return and(res, a, (int) bX, bY);
    }

    public static Vec2s and(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x & bX);
        res.y = (short) (a.y & bY);
        return res;
    }

    public static Vec2ub and_(Vec2ub a, UByte b) {
        return and(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub and_(Vec2ub a, byte b) {
        return and(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub and_(Vec2ub a, int b) {
        return and(new Vec2ub(), a, b, b);
    }

    public static Vec2ub and_(Vec2ub a, Vec2ub b) {
        return and(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub and(Vec2ub a, UByte b) {
        return and(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub and(Vec2ub a, byte b) {
        return and(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub and(Vec2ub a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2ub and(Vec2ub a, Vec2ub b) {
        return and(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, UByte b) {
        return and(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, byte b) {
        return and(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, Vec2ub b) {
        return and(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return and(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return and(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub and(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) & bX);
        res.y.value = (byte) ((a.y.value & 0xff) & bY);
        return res;
    }

    public static Vec2ui and_(Vec2ui a, UInt b) {
        return and(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui and_(Vec2ui a, int b) {
        return and(new Vec2ui(), a, b, b);
    }

    public static Vec2ui and_(Vec2ui a, Vec2ui b) {
        return and(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui and(Vec2ui a, UInt b) {
        return and(a, a, b.value, b.value);
    }

    public static Vec2ui and(Vec2ui a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2ui and(Vec2ui a, Vec2ui b) {
        return and(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui and(Vec2ui res, Vec2ui a, UInt b) {
        return and(res, a, b.value, b.value);
    }

    public static Vec2ui and(Vec2ui res, Vec2ui a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2ui and(Vec2ui res, Vec2ui a, Vec2ui b) {
        return and(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui and(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return and(res, a, bX.value, bY.value);
    }

    public static Vec2ui and(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        return res;
    }

    public static Vec2ul and_(Vec2ul a, ULong b) {
        return and(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul and_(Vec2ul a, long b) {
        return and(new Vec2ul(), a, b, b);
    }

    public static Vec2ul and_(Vec2ul a, Vec2ul b) {
        return and(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul and(Vec2ul a, ULong b) {
        return and(a, a, b.value, b.value);
    }

    public static Vec2ul and(Vec2ul a, long b) {
        return and(a, a, b, b);
    }

    public static Vec2ul and(Vec2ul a, Vec2ul b) {
        return and(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul and(Vec2ul res, Vec2ul a, ULong b) {
        return and(res, a, b.value, b.value);
    }

    public static Vec2ul and(Vec2ul res, Vec2ul a, long b) {
        return and(res, a, b, b);
    }

    public static Vec2ul and(Vec2ul res, Vec2ul a, Vec2ul b) {
        return and(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul and(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return and(res, a, bX.value, bY.value);
    }

    public static Vec2ul and(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        return res;
    }

    public static Vec2us and_(Vec2us a, UShort b) {
        return and(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us and_(Vec2us a, short b) {
        return and(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us and_(Vec2us a, int b) {
        return and(new Vec2us(), a, b, b);
    }

    public static Vec2us and_(Vec2us a, Vec2us b) {
        return and(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us and(Vec2us a, UShort b) {
        return and(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us and(Vec2us a, short b) {
        return and(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us and(Vec2us a, int b) {
        return and(a, a, b, b);
    }

    public static Vec2us and(Vec2us a, Vec2us b) {
        return and(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, UShort b) {
        return and(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, short b) {
        return and(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, int b) {
        return and(res, a, b, b);
    }

    public static Vec2us and(Vec2us res, Vec2us a, Vec2us b) {
        return and(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return and(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, short bX, short bY) {
        return and(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us and(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) & bX);
        res.y.value = (short) ((a.y.value & 0xffff) & bY);
        return res;
    }

    public static Vec3b and_(Vec3b a, byte b) {
        return and(new Vec3b(), a, b, b, b);
    }

    public static Vec3b and_(Vec3b a, int b) {
        return and(new Vec3b(), a, b, b, b);
    }

    public static Vec3b and_(Vec3b a, Vec3b b) {
        return and(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b and(Vec3b a, byte b) {
        return and(a, a, b, b, b);
    }

    public static Vec3b and(Vec3b a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3b and(Vec3b a, Vec3b b) {
        return and(a, a, b.x, b.y, b.z);
    }

    public static Vec3b and(Vec3b res, Vec3b a, byte b) {
        return and(res, a, b, b, b);
    }

    public static Vec3b and(Vec3b res, Vec3b a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3b and(Vec3b res, Vec3b a, Vec3b b) {
        return and(res, a, b.x, b.y, b.z);
    }

    public static Vec3b and(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return and(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b and(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x & bX);
        res.y = (byte) (a.y & bY);
        res.z = (byte) (a.z & bZ);
        return res;
    }

    public static Vec3i and_(Vec3i a, int b) {
        return and(new Vec3i(), a, b, b, b);
    }

    public static Vec3i and_(Vec3i a, Vec3i b) {
        return and(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i and(Vec3i a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3i and(Vec3i a, Vec3i b) {
        return and(a, a, b.x, b.y, b.z);
    }

    public static Vec3i and(Vec3i res, Vec3i a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3i and(Vec3i res, Vec3i a, Vec3i b) {
        return and(res, a, b.x, b.y, b.z);
    }

    public static Vec3i and(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        res.z = a.z & bZ;
        return res;
    }

    public static Vec3l and_(Vec3l a, long b) {
        return and(new Vec3l(), a, b, b, b);
    }

    public static Vec3l and_(Vec3l a, Vec3l b) {
        return and(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l and(Vec3l a, long b) {
        return and(a, a, b, b, b);
    }

    public static Vec3l and(Vec3l a, Vec3l b) {
        return and(a, a, b.x, b.y, b.z);
    }

    public static Vec3l and(Vec3l res, Vec3l a, long b) {
        return and(res, a, b, b, b);
    }

    public static Vec3l and(Vec3l res, Vec3l a, Vec3l b) {
        return and(res, a, b.x, b.y, b.z);
    }

    public static Vec3l and(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        res.z = a.z & bZ;
        return res;
    }

    public static Vec3s and_(Vec3s a, short b) {
        return and(new Vec3s(), a, b, b, b);
    }

    public static Vec3s and_(Vec3s a, int b) {
        return and(new Vec3s(), a, b, b, b);
    }

    public static Vec3s and_(Vec3s a, Vec3s b) {
        return and(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s and(Vec3s a, short b) {
        return and(a, a, b, b, b);
    }

    public static Vec3s and(Vec3s a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3s and(Vec3s a, Vec3s b) {
        return and(a, a, b.x, b.y, b.z);
    }

    public static Vec3s and(Vec3s res, Vec3s a, short b) {
        return and(res, a, b, b, b);
    }

    public static Vec3s and(Vec3s res, Vec3s a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3s and(Vec3s res, Vec3s a, Vec3s b) {
        return and(res, a, b.x, b.y, b.z);
    }

    public static Vec3s and(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return and(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s and(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x & bX);
        res.y = (short) (a.y & bY);
        res.z = (short) (a.z & bZ);
        return res;
    }

    public static Vec3ub and_(Vec3ub a, UByte b) {
        return and(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub and_(Vec3ub a, byte b) {
        return and(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub and_(Vec3ub a, int b) {
        return and(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub and_(Vec3ub a, Vec3ub b) {
        return and(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub and(Vec3ub a, UByte b) {
        return and(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub and(Vec3ub a, byte b) {
        return and(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub and(Vec3ub a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3ub and(Vec3ub a, Vec3ub b) {
        return and(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, UByte b) {
        return and(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, byte b) {
        return and(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, Vec3ub b) {
        return and(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return and(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return and(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub and(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) & bX);
        res.y.value = (byte) ((a.y.value & 0xff) & bY);
        res.z.value = (byte) ((a.z.value & 0xff) & bZ);
        return res;
    }

    public static Vec3ui and_(Vec3ui a, UInt b) {
        return and(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui and_(Vec3ui a, int b) {
        return and(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui and_(Vec3ui a, Vec3ui b) {
        return and(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui and(Vec3ui a, UInt b) {
        return and(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui and(Vec3ui a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3ui and(Vec3ui a, Vec3ui b) {
        return and(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui and(Vec3ui res, Vec3ui a, UInt b) {
        return and(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui and(Vec3ui res, Vec3ui a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3ui and(Vec3ui res, Vec3ui a, Vec3ui b) {
        return and(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui and(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return and(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui and(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        res.z.value = a.z.value & bZ;
        return res;
    }

    public static Vec3ul and_(Vec3ul a, ULong b) {
        return and(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul and_(Vec3ul a, long b) {
        return and(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul and_(Vec3ul a, Vec3ul b) {
        return and(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul and(Vec3ul a, ULong b) {
        return and(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul and(Vec3ul a, long b) {
        return and(a, a, b, b, b);
    }

    public static Vec3ul and(Vec3ul a, Vec3ul b) {
        return and(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul and(Vec3ul res, Vec3ul a, ULong b) {
        return and(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul and(Vec3ul res, Vec3ul a, long b) {
        return and(res, a, b, b, b);
    }

    public static Vec3ul and(Vec3ul res, Vec3ul a, Vec3ul b) {
        return and(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul and(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return and(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul and(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        res.z.value = a.z.value & bZ;
        return res;
    }

    public static Vec3us and_(Vec3us a, UShort b) {
        return and(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us and_(Vec3us a, short b) {
        return and(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us and_(Vec3us a, int b) {
        return and(new Vec3us(), a, b, b, b);
    }

    public static Vec3us and_(Vec3us a, Vec3us b) {
        return and(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us and(Vec3us a, UShort b) {
        return and(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us and(Vec3us a, short b) {
        return and(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us and(Vec3us a, int b) {
        return and(a, a, b, b, b);
    }

    public static Vec3us and(Vec3us a, Vec3us b) {
        return and(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, UShort b) {
        return and(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, short b) {
        return and(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, int b) {
        return and(res, a, b, b, b);
    }

    public static Vec3us and(Vec3us res, Vec3us a, Vec3us b) {
        return and(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return and(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return and(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us and(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) & bX);
        res.y.value = (short) ((a.y.value & 0xffff) & bY);
        res.z.value = (short) ((a.z.value & 0xffff) & bZ);
        return res;
    }

    public static Vec4b and_(Vec4b a, byte b) {
        return and(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b and_(Vec4b a, int b) {
        return and(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b and_(Vec4b a, Vec4b b) {
        return and(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b and(Vec4b a, byte b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4b and(Vec4b a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4b and(Vec4b a, Vec4b b) {
        return and(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b and(Vec4b res, Vec4b a, byte b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4b and(Vec4b res, Vec4b a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4b and(Vec4b res, Vec4b a, Vec4b b) {
        return and(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b and(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return and(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b and(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x & bX);
        res.y = (byte) (a.y & bY);
        res.z = (byte) (a.z & bZ);
        res.w = (byte) (a.w & bW);
        return res;
    }

    public static Vec4i and_(Vec4i a, int b) {
        return and(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i and_(Vec4i a, Vec4i b) {
        return and(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i and(Vec4i a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4i and(Vec4i a, Vec4i b) {
        return and(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i and(Vec4i res, Vec4i a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4i and(Vec4i res, Vec4i a, Vec4i b) {
        return and(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i and(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        res.z = a.z & bZ;
        res.w = a.w & bW;
        return res;
    }

    public static Vec4l and_(Vec4l a, long b) {
        return and(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l and_(Vec4l a, Vec4l b) {
        return and(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l and(Vec4l a, long b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4l and(Vec4l a, Vec4l b) {
        return and(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l and(Vec4l res, Vec4l a, long b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4l and(Vec4l res, Vec4l a, Vec4l b) {
        return and(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l and(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x & bX;
        res.y = a.y & bY;
        res.z = a.z & bZ;
        res.w = a.w & bW;
        return res;
    }

    public static Vec4s and_(Vec4s a, short b) {
        return and(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s and_(Vec4s a, int b) {
        return and(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s and_(Vec4s a, Vec4s b) {
        return and(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s and(Vec4s a, short b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4s and(Vec4s a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4s and(Vec4s a, Vec4s b) {
        return and(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s and(Vec4s res, Vec4s a, short b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4s and(Vec4s res, Vec4s a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4s and(Vec4s res, Vec4s a, Vec4s b) {
        return and(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s and(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return and(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s and(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x & bX);
        res.y = (short) (a.y & bY);
        res.z = (short) (a.z & bZ);
        res.w = (short) (a.w & bW);
        return res;
    }

    public static Vec4ub and_(Vec4ub a, UByte b) {
        return and(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub and_(Vec4ub a, byte b) {
        return and(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub and_(Vec4ub a, int b) {
        return and(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub and_(Vec4ub a, Vec4ub b) {
        return and(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub and(Vec4ub a, UByte b) {
        return and(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub and(Vec4ub a, byte b) {
        return and(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub and(Vec4ub a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4ub and(Vec4ub a, Vec4ub b) {
        return and(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, UByte b) {
        return and(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, byte b) {
        return and(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, Vec4ub b) {
        return and(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return and(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return and(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub and(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) & bX);
        res.y.value = (byte) ((a.y.value & 0xff) & bY);
        res.z.value = (byte) ((a.z.value & 0xff) & bZ);
        res.w.value = (byte) ((a.w.value & 0xff) & bW);
        return res;
    }

    public static Vec4ui and_(Vec4ui a, UInt b) {
        return and(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui and_(Vec4ui a, int b) {
        return and(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui and_(Vec4ui a, Vec4ui b) {
        return and(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui and(Vec4ui a, UInt b) {
        return and(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui and(Vec4ui a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4ui and(Vec4ui a, Vec4ui b) {
        return and(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui and(Vec4ui res, Vec4ui a, UInt b) {
        return and(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui and(Vec4ui res, Vec4ui a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4ui and(Vec4ui res, Vec4ui a, Vec4ui b) {
        return and(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui and(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return and(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui and(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        res.z.value = a.z.value & bZ;
        res.w.value = a.w.value & bW;
        return res;
    }

    public static Vec4ul and_(Vec4ul a, ULong b) {
        return and(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul and_(Vec4ul a, long b) {
        return and(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul and_(Vec4ul a, Vec4ul b) {
        return and(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul and(Vec4ul a, ULong b) {
        return and(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul and(Vec4ul a, long b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4ul and(Vec4ul a, Vec4ul b) {
        return and(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul and(Vec4ul res, Vec4ul a, ULong b) {
        return and(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul and(Vec4ul res, Vec4ul a, long b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4ul and(Vec4ul res, Vec4ul a, Vec4ul b) {
        return and(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul and(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return and(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul and(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value & bX;
        res.y.value = a.y.value & bY;
        res.z.value = a.z.value & bZ;
        res.w.value = a.w.value & bW;
        return res;
    }

    public static Vec4us and_(Vec4us a, UShort b) {
        return and(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us and_(Vec4us a, short b) {
        return and(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us and_(Vec4us a, int b) {
        return and(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us and_(Vec4us a, Vec4us b) {
        return and(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us and(Vec4us a, UShort b) {
        return and(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us and(Vec4us a, short b) {
        return and(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us and(Vec4us a, int b) {
        return and(a, a, b, b, b, b);
    }

    public static Vec4us and(Vec4us a, Vec4us b) {
        return and(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, UShort b) {
        return and(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, short b) {
        return and(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, int b) {
        return and(res, a, b, b, b, b);
    }

    public static Vec4us and(Vec4us res, Vec4us a, Vec4us b) {
        return and(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return and(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return and(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us and(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) & bX);
        res.y.value = (short) ((a.y.value & 0xffff) & bY);
        res.z.value = (short) ((a.z.value & 0xffff) & bZ);
        res.w.value = (short) ((a.w.value & 0xffff) & bW);
        return res;
    }

    public static Vec2b or_(Vec2b a, byte b) {
        return or(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b or_(Vec2b a, int b) {
        return or(new Vec2b(), a, b, b);
    }

    public static Vec2b or_(Vec2b a, Vec2b b) {
        return or(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b or(Vec2b a, byte b) {
        return or(a, a, (int) b, b);
    }

    public static Vec2b or(Vec2b a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2b or(Vec2b a, Vec2b b) {
        return or(a, a, (int) b.x, b.y);
    }

    public static Vec2b or(Vec2b res, Vec2b a, byte b) {
        return or(res, a, (int) b, b);
    }

    public static Vec2b or(Vec2b res, Vec2b a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2b or(Vec2b res, Vec2b a, Vec2b b) {
        return or(res, a, (int) b.x, b.y);
    }

    public static Vec2b or(Vec2b res, Vec2b a, byte bX, byte bY) {
        return or(res, a, (int) bX, bY);
    }

    public static Vec2b or(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x | bX);
        res.y = (byte) (a.y | bY);
        return res;
    }

    public static Vec2i or_(Vec2i a, int b) {
        return or(new Vec2i(), a, b, b);
    }

    public static Vec2i or_(Vec2i a, Vec2i b) {
        return or(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i or(Vec2i a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2i or(Vec2i a, Vec2i b) {
        return or(a, a, b.x, b.y);
    }

    public static Vec2i or(Vec2i res, Vec2i a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2i or(Vec2i res, Vec2i a, Vec2i b) {
        return or(res, a, b.x, b.y);
    }

    public static Vec2i or(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        return res;
    }

    public static Vec2l or_(Vec2l a, long b) {
        return or(new Vec2l(), a, b, b);
    }

    public static Vec2l or_(Vec2l a, Vec2l b) {
        return or(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l or(Vec2l a, long b) {
        return or(a, a, b, b);
    }

    public static Vec2l or(Vec2l a, Vec2l b) {
        return or(a, a, b.x, b.y);
    }

    public static Vec2l or(Vec2l res, Vec2l a, long b) {
        return or(res, a, b, b);
    }

    public static Vec2l or(Vec2l res, Vec2l a, Vec2l b) {
        return or(res, a, b.x, b.y);
    }

    public static Vec2l or(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        return res;
    }

    public static Vec2s or_(Vec2s a, short b) {
        return or(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s or_(Vec2s a, int b) {
        return or(new Vec2s(), a, b, b);
    }

    public static Vec2s or_(Vec2s a, Vec2s b) {
        return or(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s or(Vec2s a, short b) {
        return or(a, a, (int) b, b);
    }

    public static Vec2s or(Vec2s a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2s or(Vec2s a, Vec2s b) {
        return or(a, a, (int) b.x, b.y);
    }

    public static Vec2s or(Vec2s res, Vec2s a, short b) {
        return or(res, a, (int) b, b);
    }

    public static Vec2s or(Vec2s res, Vec2s a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2s or(Vec2s res, Vec2s a, Vec2s b) {
        return or(res, a, (int) b.x, b.y);
    }

    public static Vec2s or(Vec2s res, Vec2s a, short bX, short bY) {
        return or(res, a, (int) bX, bY);
    }

    public static Vec2s or(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x | bX);
        res.y = (short) (a.y | bY);
        return res;
    }

    public static Vec2ub or_(Vec2ub a, UByte b) {
        return or(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub or_(Vec2ub a, byte b) {
        return or(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub or_(Vec2ub a, int b) {
        return or(new Vec2ub(), a, b, b);
    }

    public static Vec2ub or_(Vec2ub a, Vec2ub b) {
        return or(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub or(Vec2ub a, UByte b) {
        return or(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub or(Vec2ub a, byte b) {
        return or(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub or(Vec2ub a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2ub or(Vec2ub a, Vec2ub b) {
        return or(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, UByte b) {
        return or(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, byte b) {
        return or(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, Vec2ub b) {
        return or(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return or(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return or(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub or(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) | bX);
        res.y.value = (byte) ((a.y.value & 0xff) | bY);
        return res;
    }

    public static Vec2ui or_(Vec2ui a, UInt b) {
        return or(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui or_(Vec2ui a, int b) {
        return or(new Vec2ui(), a, b, b);
    }

    public static Vec2ui or_(Vec2ui a, Vec2ui b) {
        return or(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui or(Vec2ui a, UInt b) {
        return or(a, a, b.value, b.value);
    }

    public static Vec2ui or(Vec2ui a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2ui or(Vec2ui a, Vec2ui b) {
        return or(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui or(Vec2ui res, Vec2ui a, UInt b) {
        return or(res, a, b.value, b.value);
    }

    public static Vec2ui or(Vec2ui res, Vec2ui a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2ui or(Vec2ui res, Vec2ui a, Vec2ui b) {
        return or(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui or(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return or(res, a, bX.value, bY.value);
    }

    public static Vec2ui or(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        return res;
    }

    public static Vec2ul or_(Vec2ul a, ULong b) {
        return or(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul or_(Vec2ul a, long b) {
        return or(new Vec2ul(), a, b, b);
    }

    public static Vec2ul or_(Vec2ul a, Vec2ul b) {
        return or(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul or(Vec2ul a, ULong b) {
        return or(a, a, b.value, b.value);
    }

    public static Vec2ul or(Vec2ul a, long b) {
        return or(a, a, b, b);
    }

    public static Vec2ul or(Vec2ul a, Vec2ul b) {
        return or(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul or(Vec2ul res, Vec2ul a, ULong b) {
        return or(res, a, b.value, b.value);
    }

    public static Vec2ul or(Vec2ul res, Vec2ul a, long b) {
        return or(res, a, b, b);
    }

    public static Vec2ul or(Vec2ul res, Vec2ul a, Vec2ul b) {
        return or(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul or(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return or(res, a, bX.value, bY.value);
    }

    public static Vec2ul or(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        return res;
    }

    public static Vec2us or_(Vec2us a, UShort b) {
        return or(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us or_(Vec2us a, short b) {
        return or(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us or_(Vec2us a, int b) {
        return or(new Vec2us(), a, b, b);
    }

    public static Vec2us or_(Vec2us a, Vec2us b) {
        return or(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us or(Vec2us a, UShort b) {
        return or(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us or(Vec2us a, short b) {
        return or(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us or(Vec2us a, int b) {
        return or(a, a, b, b);
    }

    public static Vec2us or(Vec2us a, Vec2us b) {
        return or(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, UShort b) {
        return or(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, short b) {
        return or(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, int b) {
        return or(res, a, b, b);
    }

    public static Vec2us or(Vec2us res, Vec2us a, Vec2us b) {
        return or(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return or(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, short bX, short bY) {
        return or(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us or(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) | bX);
        res.y.value = (short) ((a.y.value & 0xffff) | bY);
        return res;
    }

    public static Vec3b or_(Vec3b a, byte b) {
        return or(new Vec3b(), a, b, b, b);
    }

    public static Vec3b or_(Vec3b a, int b) {
        return or(new Vec3b(), a, b, b, b);
    }

    public static Vec3b or_(Vec3b a, Vec3b b) {
        return or(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b or(Vec3b a, byte b) {
        return or(a, a, b, b, b);
    }

    public static Vec3b or(Vec3b a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3b or(Vec3b a, Vec3b b) {
        return or(a, a, b.x, b.y, b.z);
    }

    public static Vec3b or(Vec3b res, Vec3b a, byte b) {
        return or(res, a, b, b, b);
    }

    public static Vec3b or(Vec3b res, Vec3b a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3b or(Vec3b res, Vec3b a, Vec3b b) {
        return or(res, a, b.x, b.y, b.z);
    }

    public static Vec3b or(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return or(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b or(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x | bX);
        res.y = (byte) (a.y | bY);
        res.z = (byte) (a.z | bZ);
        return res;
    }

    public static Vec3i or_(Vec3i a, int b) {
        return or(new Vec3i(), a, b, b, b);
    }

    public static Vec3i or_(Vec3i a, Vec3i b) {
        return or(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i or(Vec3i a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3i or(Vec3i a, Vec3i b) {
        return or(a, a, b.x, b.y, b.z);
    }

    public static Vec3i or(Vec3i res, Vec3i a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3i or(Vec3i res, Vec3i a, Vec3i b) {
        return or(res, a, b.x, b.y, b.z);
    }

    public static Vec3i or(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        res.z = a.z | bZ;
        return res;
    }

    public static Vec3l or_(Vec3l a, long b) {
        return or(new Vec3l(), a, b, b, b);
    }

    public static Vec3l or_(Vec3l a, Vec3l b) {
        return or(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l or(Vec3l a, long b) {
        return or(a, a, b, b, b);
    }

    public static Vec3l or(Vec3l a, Vec3l b) {
        return or(a, a, b.x, b.y, b.z);
    }

    public static Vec3l or(Vec3l res, Vec3l a, long b) {
        return or(res, a, b, b, b);
    }

    public static Vec3l or(Vec3l res, Vec3l a, Vec3l b) {
        return or(res, a, b.x, b.y, b.z);
    }

    public static Vec3l or(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        res.z = a.z | bZ;
        return res;
    }

    public static Vec3s or_(Vec3s a, short b) {
        return or(new Vec3s(), a, b, b, b);
    }

    public static Vec3s or_(Vec3s a, int b) {
        return or(new Vec3s(), a, b, b, b);
    }

    public static Vec3s or_(Vec3s a, Vec3s b) {
        return or(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s or(Vec3s a, short b) {
        return or(a, a, b, b, b);
    }

    public static Vec3s or(Vec3s a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3s or(Vec3s a, Vec3s b) {
        return or(a, a, b.x, b.y, b.z);
    }

    public static Vec3s or(Vec3s res, Vec3s a, short b) {
        return or(res, a, b, b, b);
    }

    public static Vec3s or(Vec3s res, Vec3s a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3s or(Vec3s res, Vec3s a, Vec3s b) {
        return or(res, a, b.x, b.y, b.z);
    }

    public static Vec3s or(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return or(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s or(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x | bX);
        res.y = (short) (a.y | bY);
        res.z = (short) (a.z | bZ);
        return res;
    }

    public static Vec3ub or_(Vec3ub a, UByte b) {
        return or(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub or_(Vec3ub a, byte b) {
        return or(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub or_(Vec3ub a, int b) {
        return or(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub or_(Vec3ub a, Vec3ub b) {
        return or(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub or(Vec3ub a, UByte b) {
        return or(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub or(Vec3ub a, byte b) {
        return or(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub or(Vec3ub a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3ub or(Vec3ub a, Vec3ub b) {
        return or(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, UByte b) {
        return or(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, byte b) {
        return or(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, Vec3ub b) {
        return or(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return or(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return or(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub or(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) | bX);
        res.y.value = (byte) ((a.y.value & 0xff) | bY);
        res.z.value = (byte) ((a.z.value & 0xff) | bZ);
        return res;
    }

    public static Vec3ui or_(Vec3ui a, UInt b) {
        return or(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui or_(Vec3ui a, int b) {
        return or(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui or_(Vec3ui a, Vec3ui b) {
        return or(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui or(Vec3ui a, UInt b) {
        return or(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui or(Vec3ui a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3ui or(Vec3ui a, Vec3ui b) {
        return or(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui or(Vec3ui res, Vec3ui a, UInt b) {
        return or(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui or(Vec3ui res, Vec3ui a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3ui or(Vec3ui res, Vec3ui a, Vec3ui b) {
        return or(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui or(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return or(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui or(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        res.z.value = a.z.value | bZ;
        return res;
    }

    public static Vec3ul or_(Vec3ul a, ULong b) {
        return or(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul or_(Vec3ul a, long b) {
        return or(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul or_(Vec3ul a, Vec3ul b) {
        return or(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul or(Vec3ul a, ULong b) {
        return or(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul or(Vec3ul a, long b) {
        return or(a, a, b, b, b);
    }

    public static Vec3ul or(Vec3ul a, Vec3ul b) {
        return or(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul or(Vec3ul res, Vec3ul a, ULong b) {
        return or(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul or(Vec3ul res, Vec3ul a, long b) {
        return or(res, a, b, b, b);
    }

    public static Vec3ul or(Vec3ul res, Vec3ul a, Vec3ul b) {
        return or(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul or(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return or(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul or(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        res.z.value = a.z.value | bZ;
        return res;
    }

    public static Vec3us or_(Vec3us a, UShort b) {
        return or(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us or_(Vec3us a, short b) {
        return or(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us or_(Vec3us a, int b) {
        return or(new Vec3us(), a, b, b, b);
    }

    public static Vec3us or_(Vec3us a, Vec3us b) {
        return or(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us or(Vec3us a, UShort b) {
        return or(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us or(Vec3us a, short b) {
        return or(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us or(Vec3us a, int b) {
        return or(a, a, b, b, b);
    }

    public static Vec3us or(Vec3us a, Vec3us b) {
        return or(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, UShort b) {
        return or(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, short b) {
        return or(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, int b) {
        return or(res, a, b, b, b);
    }

    public static Vec3us or(Vec3us res, Vec3us a, Vec3us b) {
        return or(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return or(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return or(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us or(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) | bX);
        res.y.value = (short) ((a.y.value & 0xffff) | bY);
        res.z.value = (short) ((a.z.value & 0xffff) | bZ);
        return res;
    }

    public static Vec4b or_(Vec4b a, byte b) {
        return or(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b or_(Vec4b a, int b) {
        return or(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b or_(Vec4b a, Vec4b b) {
        return or(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b or(Vec4b a, byte b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4b or(Vec4b a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4b or(Vec4b a, Vec4b b) {
        return or(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b or(Vec4b res, Vec4b a, byte b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4b or(Vec4b res, Vec4b a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4b or(Vec4b res, Vec4b a, Vec4b b) {
        return or(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b or(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return or(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b or(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x | bX);
        res.y = (byte) (a.y | bY);
        res.z = (byte) (a.z | bZ);
        res.w = (byte) (a.w | bW);
        return res;
    }

    public static Vec4i or_(Vec4i a, int b) {
        return or(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i or_(Vec4i a, Vec4i b) {
        return or(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i or(Vec4i a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4i or(Vec4i a, Vec4i b) {
        return or(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i or(Vec4i res, Vec4i a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4i or(Vec4i res, Vec4i a, Vec4i b) {
        return or(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i or(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        res.z = a.z | bZ;
        res.w = a.w | bW;
        return res;
    }

    public static Vec4l or_(Vec4l a, long b) {
        return or(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l or_(Vec4l a, Vec4l b) {
        return or(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l or(Vec4l a, long b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4l or(Vec4l a, Vec4l b) {
        return or(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l or(Vec4l res, Vec4l a, long b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4l or(Vec4l res, Vec4l a, Vec4l b) {
        return or(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l or(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x | bX;
        res.y = a.y | bY;
        res.z = a.z | bZ;
        res.w = a.w | bW;
        return res;
    }

    public static Vec4s or_(Vec4s a, short b) {
        return or(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s or_(Vec4s a, int b) {
        return or(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s or_(Vec4s a, Vec4s b) {
        return or(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s or(Vec4s a, short b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4s or(Vec4s a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4s or(Vec4s a, Vec4s b) {
        return or(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s or(Vec4s res, Vec4s a, short b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4s or(Vec4s res, Vec4s a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4s or(Vec4s res, Vec4s a, Vec4s b) {
        return or(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s or(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return or(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s or(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x | bX);
        res.y = (short) (a.y | bY);
        res.z = (short) (a.z | bZ);
        res.w = (short) (a.w | bW);
        return res;
    }

    public static Vec4ub or_(Vec4ub a, UByte b) {
        return or(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub or_(Vec4ub a, byte b) {
        return or(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub or_(Vec4ub a, int b) {
        return or(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub or_(Vec4ub a, Vec4ub b) {
        return or(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub or(Vec4ub a, UByte b) {
        return or(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub or(Vec4ub a, byte b) {
        return or(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub or(Vec4ub a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4ub or(Vec4ub a, Vec4ub b) {
        return or(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, UByte b) {
        return or(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, byte b) {
        return or(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, Vec4ub b) {
        return or(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return or(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return or(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub or(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) | bX);
        res.y.value = (byte) ((a.y.value & 0xff) | bY);
        res.z.value = (byte) ((a.z.value & 0xff) | bZ);
        res.w.value = (byte) ((a.w.value & 0xff) | bW);
        return res;
    }

    public static Vec4ui or_(Vec4ui a, UInt b) {
        return or(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui or_(Vec4ui a, int b) {
        return or(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui or_(Vec4ui a, Vec4ui b) {
        return or(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui or(Vec4ui a, UInt b) {
        return or(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui or(Vec4ui a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4ui or(Vec4ui a, Vec4ui b) {
        return or(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui or(Vec4ui res, Vec4ui a, UInt b) {
        return or(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui or(Vec4ui res, Vec4ui a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4ui or(Vec4ui res, Vec4ui a, Vec4ui b) {
        return or(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui or(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return or(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui or(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        res.z.value = a.z.value | bZ;
        res.w.value = a.w.value | bW;
        return res;
    }

    public static Vec4ul or_(Vec4ul a, ULong b) {
        return or(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul or_(Vec4ul a, long b) {
        return or(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul or_(Vec4ul a, Vec4ul b) {
        return or(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul or(Vec4ul a, ULong b) {
        return or(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul or(Vec4ul a, long b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4ul or(Vec4ul a, Vec4ul b) {
        return or(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul or(Vec4ul res, Vec4ul a, ULong b) {
        return or(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul or(Vec4ul res, Vec4ul a, long b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4ul or(Vec4ul res, Vec4ul a, Vec4ul b) {
        return or(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul or(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return or(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul or(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value | bX;
        res.y.value = a.y.value | bY;
        res.z.value = a.z.value | bZ;
        res.w.value = a.w.value | bW;
        return res;
    }

    public static Vec4us or_(Vec4us a, UShort b) {
        return or(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us or_(Vec4us a, short b) {
        return or(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us or_(Vec4us a, int b) {
        return or(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us or_(Vec4us a, Vec4us b) {
        return or(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us or(Vec4us a, UShort b) {
        return or(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us or(Vec4us a, short b) {
        return or(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us or(Vec4us a, int b) {
        return or(a, a, b, b, b, b);
    }

    public static Vec4us or(Vec4us a, Vec4us b) {
        return or(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, UShort b) {
        return or(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, short b) {
        return or(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, int b) {
        return or(res, a, b, b, b, b);
    }

    public static Vec4us or(Vec4us res, Vec4us a, Vec4us b) {
        return or(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return or(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return or(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us or(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) | bX);
        res.y.value = (short) ((a.y.value & 0xffff) | bY);
        res.z.value = (short) ((a.z.value & 0xffff) | bZ);
        res.w.value = (short) ((a.w.value & 0xffff) | bW);
        return res;
    }

    public static Vec2b xor_(Vec2b a, byte b) {
        return xor(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b xor_(Vec2b a, int b) {
        return xor(new Vec2b(), a, b, b);
    }

    public static Vec2b xor_(Vec2b a, Vec2b b) {
        return xor(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b xor(Vec2b a, byte b) {
        return xor(a, a, (int) b, b);
    }

    public static Vec2b xor(Vec2b a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2b xor(Vec2b a, Vec2b b) {
        return xor(a, a, (int) b.x, b.y);
    }

    public static Vec2b xor(Vec2b res, Vec2b a, byte b) {
        return xor(res, a, (int) b, b);
    }

    public static Vec2b xor(Vec2b res, Vec2b a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2b xor(Vec2b res, Vec2b a, Vec2b b) {
        return xor(res, a, (int) b.x, b.y);
    }

    public static Vec2b xor(Vec2b res, Vec2b a, byte bX, byte bY) {
        return xor(res, a, (int) bX, bY);
    }

    public static Vec2b xor(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x ^ bX);
        res.y = (byte) (a.y ^ bY);
        return res;
    }

    public static Vec2i xor_(Vec2i a, int b) {
        return xor(new Vec2i(), a, b, b);
    }

    public static Vec2i xor_(Vec2i a, Vec2i b) {
        return xor(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i xor(Vec2i a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2i xor(Vec2i a, Vec2i b) {
        return xor(a, a, b.x, b.y);
    }

    public static Vec2i xor(Vec2i res, Vec2i a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2i xor(Vec2i res, Vec2i a, Vec2i b) {
        return xor(res, a, b.x, b.y);
    }

    public static Vec2i xor(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        return res;
    }

    public static Vec2l xor_(Vec2l a, long b) {
        return xor(new Vec2l(), a, b, b);
    }

    public static Vec2l xor_(Vec2l a, Vec2l b) {
        return xor(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l xor(Vec2l a, long b) {
        return xor(a, a, b, b);
    }

    public static Vec2l xor(Vec2l a, Vec2l b) {
        return xor(a, a, b.x, b.y);
    }

    public static Vec2l xor(Vec2l res, Vec2l a, long b) {
        return xor(res, a, b, b);
    }

    public static Vec2l xor(Vec2l res, Vec2l a, Vec2l b) {
        return xor(res, a, b.x, b.y);
    }

    public static Vec2l xor(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        return res;
    }

    public static Vec2s xor_(Vec2s a, short b) {
        return xor(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s xor_(Vec2s a, int b) {
        return xor(new Vec2s(), a, b, b);
    }

    public static Vec2s xor_(Vec2s a, Vec2s b) {
        return xor(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s xor(Vec2s a, short b) {
        return xor(a, a, (int) b, b);
    }

    public static Vec2s xor(Vec2s a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2s xor(Vec2s a, Vec2s b) {
        return xor(a, a, (int) b.x, b.y);
    }

    public static Vec2s xor(Vec2s res, Vec2s a, short b) {
        return xor(res, a, (int) b, b);
    }

    public static Vec2s xor(Vec2s res, Vec2s a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2s xor(Vec2s res, Vec2s a, Vec2s b) {
        return xor(res, a, (int) b.x, b.y);
    }

    public static Vec2s xor(Vec2s res, Vec2s a, short bX, short bY) {
        return xor(res, a, (int) bX, bY);
    }

    public static Vec2s xor(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x ^ bX);
        res.y = (short) (a.y ^ bY);
        return res;
    }

    public static Vec2ub xor_(Vec2ub a, UByte b) {
        return xor(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub xor_(Vec2ub a, byte b) {
        return xor(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub xor_(Vec2ub a, int b) {
        return xor(new Vec2ub(), a, b, b);
    }

    public static Vec2ub xor_(Vec2ub a, Vec2ub b) {
        return xor(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub a, UByte b) {
        return xor(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub a, byte b) {
        return xor(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub xor(Vec2ub a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2ub xor(Vec2ub a, Vec2ub b) {
        return xor(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, UByte b) {
        return xor(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, byte b) {
        return xor(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, Vec2ub b) {
        return xor(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return xor(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return xor(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub xor(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) ^ bX);
        res.y.value = (byte) ((a.y.value & 0xff) ^ bY);
        return res;
    }

    public static Vec2ui xor_(Vec2ui a, UInt b) {
        return xor(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui xor_(Vec2ui a, int b) {
        return xor(new Vec2ui(), a, b, b);
    }

    public static Vec2ui xor_(Vec2ui a, Vec2ui b) {
        return xor(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui xor(Vec2ui a, UInt b) {
        return xor(a, a, b.value, b.value);
    }

    public static Vec2ui xor(Vec2ui a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2ui xor(Vec2ui a, Vec2ui b) {
        return xor(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui xor(Vec2ui res, Vec2ui a, UInt b) {
        return xor(res, a, b.value, b.value);
    }

    public static Vec2ui xor(Vec2ui res, Vec2ui a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2ui xor(Vec2ui res, Vec2ui a, Vec2ui b) {
        return xor(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui xor(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return xor(res, a, bX.value, bY.value);
    }

    public static Vec2ui xor(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        return res;
    }

    public static Vec2ul xor_(Vec2ul a, ULong b) {
        return xor(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul xor_(Vec2ul a, long b) {
        return xor(new Vec2ul(), a, b, b);
    }

    public static Vec2ul xor_(Vec2ul a, Vec2ul b) {
        return xor(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul xor(Vec2ul a, ULong b) {
        return xor(a, a, b.value, b.value);
    }

    public static Vec2ul xor(Vec2ul a, long b) {
        return xor(a, a, b, b);
    }

    public static Vec2ul xor(Vec2ul a, Vec2ul b) {
        return xor(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul xor(Vec2ul res, Vec2ul a, ULong b) {
        return xor(res, a, b.value, b.value);
    }

    public static Vec2ul xor(Vec2ul res, Vec2ul a, long b) {
        return xor(res, a, b, b);
    }

    public static Vec2ul xor(Vec2ul res, Vec2ul a, Vec2ul b) {
        return xor(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul xor(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return xor(res, a, bX.value, bY.value);
    }

    public static Vec2ul xor(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        return res;
    }

    public static Vec2us xor_(Vec2us a, UShort b) {
        return xor(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us xor_(Vec2us a, short b) {
        return xor(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us xor_(Vec2us a, int b) {
        return xor(new Vec2us(), a, b, b);
    }

    public static Vec2us xor_(Vec2us a, Vec2us b) {
        return xor(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us xor(Vec2us a, UShort b) {
        return xor(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us xor(Vec2us a, short b) {
        return xor(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us xor(Vec2us a, int b) {
        return xor(a, a, b, b);
    }

    public static Vec2us xor(Vec2us a, Vec2us b) {
        return xor(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, UShort b) {
        return xor(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, short b) {
        return xor(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, int b) {
        return xor(res, a, b, b);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, Vec2us b) {
        return xor(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return xor(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, short bX, short bY) {
        return xor(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us xor(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) ^ bX);
        res.y.value = (short) ((a.y.value & 0xffff) ^ bY);
        return res;
    }

    public static Vec3b xor_(Vec3b a, byte b) {
        return xor(new Vec3b(), a, b, b, b);
    }

    public static Vec3b xor_(Vec3b a, int b) {
        return xor(new Vec3b(), a, b, b, b);
    }

    public static Vec3b xor_(Vec3b a, Vec3b b) {
        return xor(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b xor(Vec3b a, byte b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3b xor(Vec3b a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3b xor(Vec3b a, Vec3b b) {
        return xor(a, a, b.x, b.y, b.z);
    }

    public static Vec3b xor(Vec3b res, Vec3b a, byte b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3b xor(Vec3b res, Vec3b a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3b xor(Vec3b res, Vec3b a, Vec3b b) {
        return xor(res, a, b.x, b.y, b.z);
    }

    public static Vec3b xor(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return xor(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b xor(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x ^ bX);
        res.y = (byte) (a.y ^ bY);
        res.z = (byte) (a.z ^ bZ);
        return res;
    }

    public static Vec3i xor_(Vec3i a, int b) {
        return xor(new Vec3i(), a, b, b, b);
    }

    public static Vec3i xor_(Vec3i a, Vec3i b) {
        return xor(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i xor(Vec3i a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3i xor(Vec3i a, Vec3i b) {
        return xor(a, a, b.x, b.y, b.z);
    }

    public static Vec3i xor(Vec3i res, Vec3i a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3i xor(Vec3i res, Vec3i a, Vec3i b) {
        return xor(res, a, b.x, b.y, b.z);
    }

    public static Vec3i xor(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        res.z = a.z ^ bZ;
        return res;
    }

    public static Vec3l xor_(Vec3l a, long b) {
        return xor(new Vec3l(), a, b, b, b);
    }

    public static Vec3l xor_(Vec3l a, Vec3l b) {
        return xor(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l xor(Vec3l a, long b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3l xor(Vec3l a, Vec3l b) {
        return xor(a, a, b.x, b.y, b.z);
    }

    public static Vec3l xor(Vec3l res, Vec3l a, long b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3l xor(Vec3l res, Vec3l a, Vec3l b) {
        return xor(res, a, b.x, b.y, b.z);
    }

    public static Vec3l xor(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        res.z = a.z ^ bZ;
        return res;
    }

    public static Vec3s xor_(Vec3s a, short b) {
        return xor(new Vec3s(), a, b, b, b);
    }

    public static Vec3s xor_(Vec3s a, int b) {
        return xor(new Vec3s(), a, b, b, b);
    }

    public static Vec3s xor_(Vec3s a, Vec3s b) {
        return xor(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s xor(Vec3s a, short b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3s xor(Vec3s a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3s xor(Vec3s a, Vec3s b) {
        return xor(a, a, b.x, b.y, b.z);
    }

    public static Vec3s xor(Vec3s res, Vec3s a, short b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3s xor(Vec3s res, Vec3s a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3s xor(Vec3s res, Vec3s a, Vec3s b) {
        return xor(res, a, b.x, b.y, b.z);
    }

    public static Vec3s xor(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return xor(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s xor(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x ^ bX);
        res.y = (short) (a.y ^ bY);
        res.z = (short) (a.z ^ bZ);
        return res;
    }

    public static Vec3ub xor_(Vec3ub a, UByte b) {
        return xor(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub xor_(Vec3ub a, byte b) {
        return xor(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub xor_(Vec3ub a, int b) {
        return xor(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub xor_(Vec3ub a, Vec3ub b) {
        return xor(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub a, UByte b) {
        return xor(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub a, byte b) {
        return xor(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub xor(Vec3ub a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3ub xor(Vec3ub a, Vec3ub b) {
        return xor(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, UByte b) {
        return xor(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, byte b) {
        return xor(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, Vec3ub b) {
        return xor(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return xor(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return xor(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub xor(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) ^ bX);
        res.y.value = (byte) ((a.y.value & 0xff) ^ bY);
        res.z.value = (byte) ((a.z.value & 0xff) ^ bZ);
        return res;
    }

    public static Vec3ui xor_(Vec3ui a, UInt b) {
        return xor(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui xor_(Vec3ui a, int b) {
        return xor(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui xor_(Vec3ui a, Vec3ui b) {
        return xor(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui xor(Vec3ui a, UInt b) {
        return xor(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui xor(Vec3ui a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3ui xor(Vec3ui a, Vec3ui b) {
        return xor(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui xor(Vec3ui res, Vec3ui a, UInt b) {
        return xor(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui xor(Vec3ui res, Vec3ui a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3ui xor(Vec3ui res, Vec3ui a, Vec3ui b) {
        return xor(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui xor(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return xor(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui xor(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        res.z.value = a.z.value ^ bZ;
        return res;
    }

    public static Vec3ul xor_(Vec3ul a, ULong b) {
        return xor(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul xor_(Vec3ul a, long b) {
        return xor(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul xor_(Vec3ul a, Vec3ul b) {
        return xor(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul xor(Vec3ul a, ULong b) {
        return xor(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul xor(Vec3ul a, long b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3ul xor(Vec3ul a, Vec3ul b) {
        return xor(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul xor(Vec3ul res, Vec3ul a, ULong b) {
        return xor(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul xor(Vec3ul res, Vec3ul a, long b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3ul xor(Vec3ul res, Vec3ul a, Vec3ul b) {
        return xor(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul xor(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return xor(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul xor(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        res.z.value = a.z.value ^ bZ;
        return res;
    }

    public static Vec3us xor_(Vec3us a, UShort b) {
        return xor(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us xor_(Vec3us a, short b) {
        return xor(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us xor_(Vec3us a, int b) {
        return xor(new Vec3us(), a, b, b, b);
    }

    public static Vec3us xor_(Vec3us a, Vec3us b) {
        return xor(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us xor(Vec3us a, UShort b) {
        return xor(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us xor(Vec3us a, short b) {
        return xor(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us xor(Vec3us a, int b) {
        return xor(a, a, b, b, b);
    }

    public static Vec3us xor(Vec3us a, Vec3us b) {
        return xor(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, UShort b) {
        return xor(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, short b) {
        return xor(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, int b) {
        return xor(res, a, b, b, b);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, Vec3us b) {
        return xor(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return xor(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return xor(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us xor(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) ^ bX);
        res.y.value = (short) ((a.y.value & 0xffff) ^ bY);
        res.z.value = (short) ((a.z.value & 0xffff) ^ bZ);
        return res;
    }

    public static Vec4b xor_(Vec4b a, byte b) {
        return xor(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b xor_(Vec4b a, int b) {
        return xor(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b xor_(Vec4b a, Vec4b b) {
        return xor(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b xor(Vec4b a, byte b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4b xor(Vec4b a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4b xor(Vec4b a, Vec4b b) {
        return xor(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b xor(Vec4b res, Vec4b a, byte b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4b xor(Vec4b res, Vec4b a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4b xor(Vec4b res, Vec4b a, Vec4b b) {
        return xor(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b xor(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return xor(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b xor(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x ^ bX);
        res.y = (byte) (a.y ^ bY);
        res.z = (byte) (a.z ^ bZ);
        res.w = (byte) (a.w ^ bW);
        return res;
    }

    public static Vec4i xor_(Vec4i a, int b) {
        return xor(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i xor_(Vec4i a, Vec4i b) {
        return xor(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i xor(Vec4i a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4i xor(Vec4i a, Vec4i b) {
        return xor(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i xor(Vec4i res, Vec4i a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4i xor(Vec4i res, Vec4i a, Vec4i b) {
        return xor(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i xor(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        res.z = a.z ^ bZ;
        res.w = a.w ^ bW;
        return res;
    }

    public static Vec4l xor_(Vec4l a, long b) {
        return xor(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l xor_(Vec4l a, Vec4l b) {
        return xor(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l xor(Vec4l a, long b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4l xor(Vec4l a, Vec4l b) {
        return xor(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l xor(Vec4l res, Vec4l a, long b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4l xor(Vec4l res, Vec4l a, Vec4l b) {
        return xor(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l xor(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x ^ bX;
        res.y = a.y ^ bY;
        res.z = a.z ^ bZ;
        res.w = a.w ^ bW;
        return res;
    }

    public static Vec4s xor_(Vec4s a, short b) {
        return xor(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s xor_(Vec4s a, int b) {
        return xor(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s xor_(Vec4s a, Vec4s b) {
        return xor(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s xor(Vec4s a, short b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4s xor(Vec4s a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4s xor(Vec4s a, Vec4s b) {
        return xor(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s xor(Vec4s res, Vec4s a, short b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4s xor(Vec4s res, Vec4s a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4s xor(Vec4s res, Vec4s a, Vec4s b) {
        return xor(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s xor(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return xor(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s xor(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x ^ bX);
        res.y = (short) (a.y ^ bY);
        res.z = (short) (a.z ^ bZ);
        res.w = (short) (a.w ^ bW);
        return res;
    }

    public static Vec4ub xor_(Vec4ub a, UByte b) {
        return xor(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub xor_(Vec4ub a, byte b) {
        return xor(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub xor_(Vec4ub a, int b) {
        return xor(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub xor_(Vec4ub a, Vec4ub b) {
        return xor(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub a, UByte b) {
        return xor(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub a, byte b) {
        return xor(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub xor(Vec4ub a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4ub xor(Vec4ub a, Vec4ub b) {
        return xor(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, UByte b) {
        return xor(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, byte b) {
        return xor(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, Vec4ub b) {
        return xor(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return xor(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return xor(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub xor(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) ^ bX);
        res.y.value = (byte) ((a.y.value & 0xff) ^ bY);
        res.z.value = (byte) ((a.z.value & 0xff) ^ bZ);
        res.w.value = (byte) ((a.w.value & 0xff) ^ bW);
        return res;
    }

    public static Vec4ui xor_(Vec4ui a, UInt b) {
        return xor(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui xor_(Vec4ui a, int b) {
        return xor(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui xor_(Vec4ui a, Vec4ui b) {
        return xor(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui xor(Vec4ui a, UInt b) {
        return xor(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui xor(Vec4ui a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4ui xor(Vec4ui a, Vec4ui b) {
        return xor(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui xor(Vec4ui res, Vec4ui a, UInt b) {
        return xor(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui xor(Vec4ui res, Vec4ui a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4ui xor(Vec4ui res, Vec4ui a, Vec4ui b) {
        return xor(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui xor(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return xor(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui xor(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        res.z.value = a.z.value ^ bZ;
        res.w.value = a.w.value ^ bW;
        return res;
    }

    public static Vec4ul xor_(Vec4ul a, ULong b) {
        return xor(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul xor_(Vec4ul a, long b) {
        return xor(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul xor_(Vec4ul a, Vec4ul b) {
        return xor(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul xor(Vec4ul a, ULong b) {
        return xor(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul xor(Vec4ul a, long b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4ul xor(Vec4ul a, Vec4ul b) {
        return xor(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul xor(Vec4ul res, Vec4ul a, ULong b) {
        return xor(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul xor(Vec4ul res, Vec4ul a, long b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4ul xor(Vec4ul res, Vec4ul a, Vec4ul b) {
        return xor(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul xor(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return xor(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul xor(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value ^ bX;
        res.y.value = a.y.value ^ bY;
        res.z.value = a.z.value ^ bZ;
        res.w.value = a.w.value ^ bW;
        return res;
    }

    public static Vec4us xor_(Vec4us a, UShort b) {
        return xor(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us xor_(Vec4us a, short b) {
        return xor(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us xor_(Vec4us a, int b) {
        return xor(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us xor_(Vec4us a, Vec4us b) {
        return xor(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us xor(Vec4us a, UShort b) {
        return xor(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us xor(Vec4us a, short b) {
        return xor(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us xor(Vec4us a, int b) {
        return xor(a, a, b, b, b, b);
    }

    public static Vec4us xor(Vec4us a, Vec4us b) {
        return xor(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, UShort b) {
        return xor(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, short b) {
        return xor(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, int b) {
        return xor(res, a, b, b, b, b);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, Vec4us b) {
        return xor(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return xor(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return xor(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us xor(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) ^ bX);
        res.y.value = (short) ((a.y.value & 0xffff) ^ bY);
        res.z.value = (short) ((a.z.value & 0xffff) ^ bZ);
        res.w.value = (short) ((a.w.value & 0xffff) ^ bW);
        return res;
    }

    public static Vec2b shL_(Vec2b a, byte b) {
        return shL(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b shL_(Vec2b a, int b) {
        return shL(new Vec2b(), a, b, b);
    }

    public static Vec2b shL_(Vec2b a, Vec2b b) {
        return shL(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b shL(Vec2b a, byte b) {
        return shL(a, a, (int) b, b);
    }

    public static Vec2b shL(Vec2b a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2b shL(Vec2b a, Vec2b b) {
        return shL(a, a, (int) b.x, b.y);
    }

    public static Vec2b shL(Vec2b res, Vec2b a, byte b) {
        return shL(res, a, (int) b, b);
    }

    public static Vec2b shL(Vec2b res, Vec2b a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2b shL(Vec2b res, Vec2b a, Vec2b b) {
        return shL(res, a, (int) b.x, b.y);
    }

    public static Vec2b shL(Vec2b res, Vec2b a, byte bX, byte bY) {
        return shL(res, a, (int) bX, bY);
    }

    public static Vec2b shL(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x << bX);
        res.y = (byte) (a.y << bY);
        return res;
    }

    public static Vec2i shL_(Vec2i a, int b) {
        return shL(new Vec2i(), a, b, b);
    }

    public static Vec2i shL_(Vec2i a, Vec2i b) {
        return shL(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i shL(Vec2i a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2i shL(Vec2i a, Vec2i b) {
        return shL(a, a, b.x, b.y);
    }

    public static Vec2i shL(Vec2i res, Vec2i a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2i shL(Vec2i res, Vec2i a, Vec2i b) {
        return shL(res, a, b.x, b.y);
    }

    public static Vec2i shL(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        return res;
    }

    public static Vec2l shL_(Vec2l a, long b) {
        return shL(new Vec2l(), a, b, b);
    }

    public static Vec2l shL_(Vec2l a, Vec2l b) {
        return shL(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l shL(Vec2l a, long b) {
        return shL(a, a, b, b);
    }

    public static Vec2l shL(Vec2l a, Vec2l b) {
        return shL(a, a, b.x, b.y);
    }

    public static Vec2l shL(Vec2l res, Vec2l a, long b) {
        return shL(res, a, b, b);
    }

    public static Vec2l shL(Vec2l res, Vec2l a, Vec2l b) {
        return shL(res, a, b.x, b.y);
    }

    public static Vec2l shL(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        return res;
    }

    public static Vec2s shL_(Vec2s a, short b) {
        return shL(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s shL_(Vec2s a, int b) {
        return shL(new Vec2s(), a, b, b);
    }

    public static Vec2s shL_(Vec2s a, Vec2s b) {
        return shL(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s shL(Vec2s a, short b) {
        return shL(a, a, (int) b, b);
    }

    public static Vec2s shL(Vec2s a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2s shL(Vec2s a, Vec2s b) {
        return shL(a, a, (int) b.x, b.y);
    }

    public static Vec2s shL(Vec2s res, Vec2s a, short b) {
        return shL(res, a, (int) b, b);
    }

    public static Vec2s shL(Vec2s res, Vec2s a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2s shL(Vec2s res, Vec2s a, Vec2s b) {
        return shL(res, a, (int) b.x, b.y);
    }

    public static Vec2s shL(Vec2s res, Vec2s a, short bX, short bY) {
        return shL(res, a, (int) bX, bY);
    }

    public static Vec2s shL(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x << bX);
        res.y = (short) (a.y << bY);
        return res;
    }

    public static Vec2ub shL_(Vec2ub a, UByte b) {
        return shL(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shL_(Vec2ub a, byte b) {
        return shL(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shL_(Vec2ub a, int b) {
        return shL(new Vec2ub(), a, b, b);
    }

    public static Vec2ub shL_(Vec2ub a, Vec2ub b) {
        return shL(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub a, UByte b) {
        return shL(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub a, byte b) {
        return shL(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shL(Vec2ub a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2ub shL(Vec2ub a, Vec2ub b) {
        return shL(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, UByte b) {
        return shL(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, byte b) {
        return shL(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, Vec2ub b) {
        return shL(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return shL(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return shL(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub shL(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) << bX);
        res.y.value = (byte) ((a.y.value & 0xff) << bY);
        return res;
    }

    public static Vec2ui shL_(Vec2ui a, UInt b) {
        return shL(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui shL_(Vec2ui a, int b) {
        return shL(new Vec2ui(), a, b, b);
    }

    public static Vec2ui shL_(Vec2ui a, Vec2ui b) {
        return shL(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui shL(Vec2ui a, UInt b) {
        return shL(a, a, b.value, b.value);
    }

    public static Vec2ui shL(Vec2ui a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2ui shL(Vec2ui a, Vec2ui b) {
        return shL(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui shL(Vec2ui res, Vec2ui a, UInt b) {
        return shL(res, a, b.value, b.value);
    }

    public static Vec2ui shL(Vec2ui res, Vec2ui a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2ui shL(Vec2ui res, Vec2ui a, Vec2ui b) {
        return shL(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui shL(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return shL(res, a, bX.value, bY.value);
    }

    public static Vec2ui shL(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        return res;
    }

    public static Vec2ul shL_(Vec2ul a, ULong b) {
        return shL(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul shL_(Vec2ul a, long b) {
        return shL(new Vec2ul(), a, b, b);
    }

    public static Vec2ul shL_(Vec2ul a, Vec2ul b) {
        return shL(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul shL(Vec2ul a, ULong b) {
        return shL(a, a, b.value, b.value);
    }

    public static Vec2ul shL(Vec2ul a, long b) {
        return shL(a, a, b, b);
    }

    public static Vec2ul shL(Vec2ul a, Vec2ul b) {
        return shL(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul shL(Vec2ul res, Vec2ul a, ULong b) {
        return shL(res, a, b.value, b.value);
    }

    public static Vec2ul shL(Vec2ul res, Vec2ul a, long b) {
        return shL(res, a, b, b);
    }

    public static Vec2ul shL(Vec2ul res, Vec2ul a, Vec2ul b) {
        return shL(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul shL(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return shL(res, a, bX.value, bY.value);
    }

    public static Vec2ul shL(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        return res;
    }

    public static Vec2us shL_(Vec2us a, UShort b) {
        return shL(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shL_(Vec2us a, short b) {
        return shL(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shL_(Vec2us a, int b) {
        return shL(new Vec2us(), a, b, b);
    }

    public static Vec2us shL_(Vec2us a, Vec2us b) {
        return shL(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shL(Vec2us a, UShort b) {
        return shL(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shL(Vec2us a, short b) {
        return shL(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shL(Vec2us a, int b) {
        return shL(a, a, b, b);
    }

    public static Vec2us shL(Vec2us a, Vec2us b) {
        return shL(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, UShort b) {
        return shL(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, short b) {
        return shL(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, int b) {
        return shL(res, a, b, b);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, Vec2us b) {
        return shL(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return shL(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, short bX, short bY) {
        return shL(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us shL(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) << bX);
        res.y.value = (short) ((a.y.value & 0xffff) << bY);
        return res;
    }

    public static Vec3b shL_(Vec3b a, byte b) {
        return shL(new Vec3b(), a, b, b, b);
    }

    public static Vec3b shL_(Vec3b a, int b) {
        return shL(new Vec3b(), a, b, b, b);
    }

    public static Vec3b shL_(Vec3b a, Vec3b b) {
        return shL(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b shL(Vec3b a, byte b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3b shL(Vec3b a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3b shL(Vec3b a, Vec3b b) {
        return shL(a, a, b.x, b.y, b.z);
    }

    public static Vec3b shL(Vec3b res, Vec3b a, byte b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3b shL(Vec3b res, Vec3b a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3b shL(Vec3b res, Vec3b a, Vec3b b) {
        return shL(res, a, b.x, b.y, b.z);
    }

    public static Vec3b shL(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return shL(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b shL(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x << bX);
        res.y = (byte) (a.y << bY);
        res.z = (byte) (a.z << bZ);
        return res;
    }

    public static Vec3i shL_(Vec3i a, int b) {
        return shL(new Vec3i(), a, b, b, b);
    }

    public static Vec3i shL_(Vec3i a, Vec3i b) {
        return shL(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i shL(Vec3i a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3i shL(Vec3i a, Vec3i b) {
        return shL(a, a, b.x, b.y, b.z);
    }

    public static Vec3i shL(Vec3i res, Vec3i a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3i shL(Vec3i res, Vec3i a, Vec3i b) {
        return shL(res, a, b.x, b.y, b.z);
    }

    public static Vec3i shL(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        res.z = a.z << bZ;
        return res;
    }

    public static Vec3l shL_(Vec3l a, long b) {
        return shL(new Vec3l(), a, b, b, b);
    }

    public static Vec3l shL_(Vec3l a, Vec3l b) {
        return shL(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l shL(Vec3l a, long b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3l shL(Vec3l a, Vec3l b) {
        return shL(a, a, b.x, b.y, b.z);
    }

    public static Vec3l shL(Vec3l res, Vec3l a, long b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3l shL(Vec3l res, Vec3l a, Vec3l b) {
        return shL(res, a, b.x, b.y, b.z);
    }

    public static Vec3l shL(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        res.z = a.z << bZ;
        return res;
    }

    public static Vec3s shL_(Vec3s a, short b) {
        return shL(new Vec3s(), a, b, b, b);
    }

    public static Vec3s shL_(Vec3s a, int b) {
        return shL(new Vec3s(), a, b, b, b);
    }

    public static Vec3s shL_(Vec3s a, Vec3s b) {
        return shL(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s shL(Vec3s a, short b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3s shL(Vec3s a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3s shL(Vec3s a, Vec3s b) {
        return shL(a, a, b.x, b.y, b.z);
    }

    public static Vec3s shL(Vec3s res, Vec3s a, short b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3s shL(Vec3s res, Vec3s a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3s shL(Vec3s res, Vec3s a, Vec3s b) {
        return shL(res, a, b.x, b.y, b.z);
    }

    public static Vec3s shL(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return shL(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s shL(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x << bX);
        res.y = (short) (a.y << bY);
        res.z = (short) (a.z << bZ);
        return res;
    }

    public static Vec3ub shL_(Vec3ub a, UByte b) {
        return shL(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shL_(Vec3ub a, byte b) {
        return shL(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shL_(Vec3ub a, int b) {
        return shL(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub shL_(Vec3ub a, Vec3ub b) {
        return shL(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub a, UByte b) {
        return shL(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub a, byte b) {
        return shL(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shL(Vec3ub a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3ub shL(Vec3ub a, Vec3ub b) {
        return shL(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, UByte b) {
        return shL(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, byte b) {
        return shL(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, Vec3ub b) {
        return shL(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return shL(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return shL(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub shL(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) << bX);
        res.y.value = (byte) ((a.y.value & 0xff) << bY);
        res.z.value = (byte) ((a.z.value & 0xff) << bZ);
        return res;
    }

    public static Vec3ui shL_(Vec3ui a, UInt b) {
        return shL(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui shL_(Vec3ui a, int b) {
        return shL(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui shL_(Vec3ui a, Vec3ui b) {
        return shL(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui shL(Vec3ui a, UInt b) {
        return shL(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui shL(Vec3ui a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3ui shL(Vec3ui a, Vec3ui b) {
        return shL(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui shL(Vec3ui res, Vec3ui a, UInt b) {
        return shL(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui shL(Vec3ui res, Vec3ui a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3ui shL(Vec3ui res, Vec3ui a, Vec3ui b) {
        return shL(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui shL(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return shL(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui shL(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        res.z.value = a.z.value << bZ;
        return res;
    }

    public static Vec3ul shL_(Vec3ul a, ULong b) {
        return shL(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul shL_(Vec3ul a, long b) {
        return shL(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul shL_(Vec3ul a, Vec3ul b) {
        return shL(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shL(Vec3ul a, ULong b) {
        return shL(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul shL(Vec3ul a, long b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3ul shL(Vec3ul a, Vec3ul b) {
        return shL(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shL(Vec3ul res, Vec3ul a, ULong b) {
        return shL(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul shL(Vec3ul res, Vec3ul a, long b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3ul shL(Vec3ul res, Vec3ul a, Vec3ul b) {
        return shL(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shL(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return shL(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul shL(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        res.z.value = a.z.value << bZ;
        return res;
    }

    public static Vec3us shL_(Vec3us a, UShort b) {
        return shL(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shL_(Vec3us a, short b) {
        return shL(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shL_(Vec3us a, int b) {
        return shL(new Vec3us(), a, b, b, b);
    }

    public static Vec3us shL_(Vec3us a, Vec3us b) {
        return shL(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shL(Vec3us a, UShort b) {
        return shL(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shL(Vec3us a, short b) {
        return shL(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shL(Vec3us a, int b) {
        return shL(a, a, b, b, b);
    }

    public static Vec3us shL(Vec3us a, Vec3us b) {
        return shL(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, UShort b) {
        return shL(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, short b) {
        return shL(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, int b) {
        return shL(res, a, b, b, b);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, Vec3us b) {
        return shL(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return shL(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return shL(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us shL(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) << bX);
        res.y.value = (short) ((a.y.value & 0xffff) << bY);
        res.z.value = (short) ((a.z.value & 0xffff) << bZ);
        return res;
    }

    public static Vec4b shL_(Vec4b a, byte b) {
        return shL(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b shL_(Vec4b a, int b) {
        return shL(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b shL_(Vec4b a, Vec4b b) {
        return shL(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shL(Vec4b a, byte b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4b shL(Vec4b a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4b shL(Vec4b a, Vec4b b) {
        return shL(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shL(Vec4b res, Vec4b a, byte b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4b shL(Vec4b res, Vec4b a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4b shL(Vec4b res, Vec4b a, Vec4b b) {
        return shL(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shL(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return shL(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b shL(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x << bX);
        res.y = (byte) (a.y << bY);
        res.z = (byte) (a.z << bZ);
        res.w = (byte) (a.w << bW);
        return res;
    }

    public static Vec4i shL_(Vec4i a, int b) {
        return shL(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i shL_(Vec4i a, Vec4i b) {
        return shL(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shL(Vec4i a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4i shL(Vec4i a, Vec4i b) {
        return shL(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shL(Vec4i res, Vec4i a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4i shL(Vec4i res, Vec4i a, Vec4i b) {
        return shL(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shL(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        res.z = a.z << bZ;
        res.w = a.w << bW;
        return res;
    }

    public static Vec4l shL_(Vec4l a, long b) {
        return shL(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l shL_(Vec4l a, Vec4l b) {
        return shL(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shL(Vec4l a, long b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4l shL(Vec4l a, Vec4l b) {
        return shL(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shL(Vec4l res, Vec4l a, long b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4l shL(Vec4l res, Vec4l a, Vec4l b) {
        return shL(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shL(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x << bX;
        res.y = a.y << bY;
        res.z = a.z << bZ;
        res.w = a.w << bW;
        return res;
    }

    public static Vec4s shL_(Vec4s a, short b) {
        return shL(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s shL_(Vec4s a, int b) {
        return shL(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s shL_(Vec4s a, Vec4s b) {
        return shL(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shL(Vec4s a, short b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4s shL(Vec4s a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4s shL(Vec4s a, Vec4s b) {
        return shL(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shL(Vec4s res, Vec4s a, short b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4s shL(Vec4s res, Vec4s a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4s shL(Vec4s res, Vec4s a, Vec4s b) {
        return shL(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shL(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return shL(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s shL(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x << bX);
        res.y = (short) (a.y << bY);
        res.z = (short) (a.z << bZ);
        res.w = (short) (a.w << bW);
        return res;
    }

    public static Vec4ub shL_(Vec4ub a, UByte b) {
        return shL(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shL_(Vec4ub a, byte b) {
        return shL(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shL_(Vec4ub a, int b) {
        return shL(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub shL_(Vec4ub a, Vec4ub b) {
        return shL(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub a, UByte b) {
        return shL(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub a, byte b) {
        return shL(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shL(Vec4ub a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4ub shL(Vec4ub a, Vec4ub b) {
        return shL(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, UByte b) {
        return shL(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, byte b) {
        return shL(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, Vec4ub b) {
        return shL(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return shL(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return shL(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub shL(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) << bX);
        res.y.value = (byte) ((a.y.value & 0xff) << bY);
        res.z.value = (byte) ((a.z.value & 0xff) << bZ);
        res.w.value = (byte) ((a.w.value & 0xff) << bW);
        return res;
    }

    public static Vec4ui shL_(Vec4ui a, UInt b) {
        return shL(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shL_(Vec4ui a, int b) {
        return shL(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui shL_(Vec4ui a, Vec4ui b) {
        return shL(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui shL(Vec4ui a, UInt b) {
        return shL(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shL(Vec4ui a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4ui shL(Vec4ui a, Vec4ui b) {
        return shL(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui shL(Vec4ui res, Vec4ui a, UInt b) {
        return shL(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shL(Vec4ui res, Vec4ui a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4ui shL(Vec4ui res, Vec4ui a, Vec4ui b) {
        return shL(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui shL(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return shL(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui shL(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        res.z.value = a.z.value << bZ;
        res.w.value = a.w.value << bW;
        return res;
    }

    public static Vec4ul shL_(Vec4ul a, ULong b) {
        return shL(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shL_(Vec4ul a, long b) {
        return shL(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul shL_(Vec4ul a, Vec4ul b) {
        return shL(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shL(Vec4ul a, ULong b) {
        return shL(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shL(Vec4ul a, long b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4ul shL(Vec4ul a, Vec4ul b) {
        return shL(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shL(Vec4ul res, Vec4ul a, ULong b) {
        return shL(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shL(Vec4ul res, Vec4ul a, long b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4ul shL(Vec4ul res, Vec4ul a, Vec4ul b) {
        return shL(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shL(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return shL(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul shL(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value << bX;
        res.y.value = a.y.value << bY;
        res.z.value = a.z.value << bZ;
        res.w.value = a.w.value << bW;
        return res;
    }

    public static Vec4us shL_(Vec4us a, UShort b) {
        return shL(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shL_(Vec4us a, short b) {
        return shL(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shL_(Vec4us a, int b) {
        return shL(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us shL_(Vec4us a, Vec4us b) {
        return shL(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shL(Vec4us a, UShort b) {
        return shL(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shL(Vec4us a, short b) {
        return shL(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shL(Vec4us a, int b) {
        return shL(a, a, b, b, b, b);
    }

    public static Vec4us shL(Vec4us a, Vec4us b) {
        return shL(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, UShort b) {
        return shL(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, short b) {
        return shL(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, int b) {
        return shL(res, a, b, b, b, b);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, Vec4us b) {
        return shL(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return shL(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return shL(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us shL(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) << bX);
        res.y.value = (short) ((a.y.value & 0xffff) << bY);
        res.z.value = (short) ((a.z.value & 0xffff) << bZ);
        res.w.value = (short) ((a.w.value & 0xffff) << bW);
        return res;
    }

    public static Vec2b shR_(Vec2b a, byte b) {
        return shR(new Vec2b(), a, (int) b, b);
    }

    public static Vec2b shR_(Vec2b a, int b) {
        return shR(new Vec2b(), a, b, b);
    }

    public static Vec2b shR_(Vec2b a, Vec2b b) {
        return shR(new Vec2b(), a, (int) b.x, b.y);
    }

    public static Vec2b shR(Vec2b a, byte b) {
        return shR(a, a, (int) b, b);
    }

    public static Vec2b shR(Vec2b a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2b shR(Vec2b a, Vec2b b) {
        return shR(a, a, (int) b.x, b.y);
    }

    public static Vec2b shR(Vec2b res, Vec2b a, byte b) {
        return shR(res, a, (int) b, b);
    }

    public static Vec2b shR(Vec2b res, Vec2b a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2b shR(Vec2b res, Vec2b a, Vec2b b) {
        return shR(res, a, (int) b.x, b.y);
    }

    public static Vec2b shR(Vec2b res, Vec2b a, byte bX, byte bY) {
        return shR(res, a, (int) bX, bY);
    }

    public static Vec2b shR(Vec2b res, Vec2b a, int bX, int bY) {
        res.x = (byte) (a.x >> bX);
        res.y = (byte) (a.y >> bY);
        return res;
    }

    public static Vec2i shR_(Vec2i a, int b) {
        return shR(new Vec2i(), a, b, b);
    }

    public static Vec2i shR_(Vec2i a, Vec2i b) {
        return shR(new Vec2i(), a, b.x, b.y);
    }

    public static Vec2i shR(Vec2i a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2i shR(Vec2i a, Vec2i b) {
        return shR(a, a, b.x, b.y);
    }

    public static Vec2i shR(Vec2i res, Vec2i a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2i shR(Vec2i res, Vec2i a, Vec2i b) {
        return shR(res, a, b.x, b.y);
    }

    public static Vec2i shR(Vec2i res, Vec2i a, int bX, int bY) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        return res;
    }

    public static Vec2l shR_(Vec2l a, long b) {
        return shR(new Vec2l(), a, b, b);
    }

    public static Vec2l shR_(Vec2l a, Vec2l b) {
        return shR(new Vec2l(), a, b.x, b.y);
    }

    public static Vec2l shR(Vec2l a, long b) {
        return shR(a, a, b, b);
    }

    public static Vec2l shR(Vec2l a, Vec2l b) {
        return shR(a, a, b.x, b.y);
    }

    public static Vec2l shR(Vec2l res, Vec2l a, long b) {
        return shR(res, a, b, b);
    }

    public static Vec2l shR(Vec2l res, Vec2l a, Vec2l b) {
        return shR(res, a, b.x, b.y);
    }

    public static Vec2l shR(Vec2l res, Vec2l a, long bX, long bY) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        return res;
    }

    public static Vec2s shR_(Vec2s a, short b) {
        return shR(new Vec2s(), a, (int) b, b);
    }

    public static Vec2s shR_(Vec2s a, int b) {
        return shR(new Vec2s(), a, b, b);
    }

    public static Vec2s shR_(Vec2s a, Vec2s b) {
        return shR(new Vec2s(), a, (int) b.x, b.y);
    }

    public static Vec2s shR(Vec2s a, short b) {
        return shR(a, a, (int) b, b);
    }

    public static Vec2s shR(Vec2s a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2s shR(Vec2s a, Vec2s b) {
        return shR(a, a, (int) b.x, b.y);
    }

    public static Vec2s shR(Vec2s res, Vec2s a, short b) {
        return shR(res, a, (int) b, b);
    }

    public static Vec2s shR(Vec2s res, Vec2s a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2s shR(Vec2s res, Vec2s a, Vec2s b) {
        return shR(res, a, (int) b.x, b.y);
    }

    public static Vec2s shR(Vec2s res, Vec2s a, short bX, short bY) {
        return shR(res, a, (int) bX, bY);
    }

    public static Vec2s shR(Vec2s res, Vec2s a, int bX, int bY) {
        res.x = (short) (a.x >> bX);
        res.y = (short) (a.y >> bY);
        return res;
    }

    public static Vec2ub shR_(Vec2ub a, UByte b) {
        return shR(new Vec2ub(), a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shR_(Vec2ub a, byte b) {
        return shR(new Vec2ub(), a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shR_(Vec2ub a, int b) {
        return shR(new Vec2ub(), a, b, b);
    }

    public static Vec2ub shR_(Vec2ub a, Vec2ub b) {
        return shR(new Vec2ub(), a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub a, UByte b) {
        return shR(a, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub a, byte b) {
        return shR(a, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shR(Vec2ub a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2ub shR(Vec2ub a, Vec2ub b) {
        return shR(a, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, UByte b) {
        return shR(res, a, b.value & 0xff, b.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, byte b) {
        return shR(res, a, b & 0xff, b & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, Vec2ub b) {
        return shR(res, a, b.x.value & 0xff, b.y.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, UByte bX, UByte bY) {
        return shR(res, a, bX.value & 0xff, bY.value & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, byte bX, byte bY) {
        return shR(res, a, bX & 0xff, bY & 0xff);
    }

    public static Vec2ub shR(Vec2ub res, Vec2ub a, int bX, int bY) {
        res.x.value = (byte) ((a.x.value & 0xff) >>> bX);
        res.y.value = (byte) ((a.y.value & 0xff) >>> bY);
        return res;
    }

    public static Vec2ui shR_(Vec2ui a, UInt b) {
        return shR(new Vec2ui(), a, b.value, b.value);
    }

    public static Vec2ui shR_(Vec2ui a, int b) {
        return shR(new Vec2ui(), a, b, b);
    }

    public static Vec2ui shR_(Vec2ui a, Vec2ui b) {
        return shR(new Vec2ui(), a, b.x.value, b.y.value);
    }

    public static Vec2ui shR(Vec2ui a, UInt b) {
        return shR(a, a, b.value, b.value);
    }

    public static Vec2ui shR(Vec2ui a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2ui shR(Vec2ui a, Vec2ui b) {
        return shR(a, a, b.x.value, b.y.value);
    }

    public static Vec2ui shR(Vec2ui res, Vec2ui a, UInt b) {
        return shR(res, a, b.value, b.value);
    }

    public static Vec2ui shR(Vec2ui res, Vec2ui a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2ui shR(Vec2ui res, Vec2ui a, Vec2ui b) {
        return shR(res, a, b.x.value, b.y.value);
    }

    public static Vec2ui shR(Vec2ui res, Vec2ui a, UInt bX, UInt bY) {
        return shR(res, a, bX.value, bY.value);
    }

    public static Vec2ui shR(Vec2ui res, Vec2ui a, int bX, int bY) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        return res;
    }

    public static Vec2ul shR_(Vec2ul a, ULong b) {
        return shR(new Vec2ul(), a, b.value, b.value);
    }

    public static Vec2ul shR_(Vec2ul a, long b) {
        return shR(new Vec2ul(), a, b, b);
    }

    public static Vec2ul shR_(Vec2ul a, Vec2ul b) {
        return shR(new Vec2ul(), a, b.x.value, b.y.value);
    }

    public static Vec2ul shR(Vec2ul a, ULong b) {
        return shR(a, a, b.value, b.value);
    }

    public static Vec2ul shR(Vec2ul a, long b) {
        return shR(a, a, b, b);
    }

    public static Vec2ul shR(Vec2ul a, Vec2ul b) {
        return shR(a, a, b.x.value, b.y.value);
    }

    public static Vec2ul shR(Vec2ul res, Vec2ul a, ULong b) {
        return shR(res, a, b.value, b.value);
    }

    public static Vec2ul shR(Vec2ul res, Vec2ul a, long b) {
        return shR(res, a, b, b);
    }

    public static Vec2ul shR(Vec2ul res, Vec2ul a, Vec2ul b) {
        return shR(res, a, b.x.value, b.y.value);
    }

    public static Vec2ul shR(Vec2ul res, Vec2ul a, ULong bX, ULong bY) {
        return shR(res, a, bX.value, bY.value);
    }

    public static Vec2ul shR(Vec2ul res, Vec2ul a, long bX, long bY) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        return res;
    }

    public static Vec2us shR_(Vec2us a, UShort b) {
        return shR(new Vec2us(), a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shR_(Vec2us a, short b) {
        return shR(new Vec2us(), a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shR_(Vec2us a, int b) {
        return shR(new Vec2us(), a, b, b);
    }

    public static Vec2us shR_(Vec2us a, Vec2us b) {
        return shR(new Vec2us(), a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shR(Vec2us a, UShort b) {
        return shR(a, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shR(Vec2us a, short b) {
        return shR(a, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shR(Vec2us a, int b) {
        return shR(a, a, b, b);
    }

    public static Vec2us shR(Vec2us a, Vec2us b) {
        return shR(a, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, UShort b) {
        return shR(res, a, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, short b) {
        return shR(res, a, b & 0xffff, b & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, int b) {
        return shR(res, a, b, b);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, Vec2us b) {
        return shR(res, a, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, UShort bX, UShort bY) {
        return shR(res, a, bX.value & 0xffff, bY.value & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, short bX, short bY) {
        return shR(res, a, bX & 0xffff, bY & 0xffff);
    }

    public static Vec2us shR(Vec2us res, Vec2us a, int bX, int bY) {
        res.x.value = (short) ((a.x.value & 0xffff) >>> bX);
        res.y.value = (short) ((a.y.value & 0xffff) >>> bY);
        return res;
    }

    public static Vec3b shR_(Vec3b a, byte b) {
        return shR(new Vec3b(), a, b, b, b);
    }

    public static Vec3b shR_(Vec3b a, int b) {
        return shR(new Vec3b(), a, b, b, b);
    }

    public static Vec3b shR_(Vec3b a, Vec3b b) {
        return shR(new Vec3b(), a, b.x, b.y, b.z);
    }

    public static Vec3b shR(Vec3b a, byte b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3b shR(Vec3b a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3b shR(Vec3b a, Vec3b b) {
        return shR(a, a, b.x, b.y, b.z);
    }

    public static Vec3b shR(Vec3b res, Vec3b a, byte b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3b shR(Vec3b res, Vec3b a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3b shR(Vec3b res, Vec3b a, Vec3b b) {
        return shR(res, a, b.x, b.y, b.z);
    }

    public static Vec3b shR(Vec3b res, Vec3b a, byte bX, byte bY, byte bZ) {
        return shR(res, a, (int) bX, bY, bZ);
    }

    public static Vec3b shR(Vec3b res, Vec3b a, int bX, int bY, int bZ) {
        res.x = (byte) (a.x >> bX);
        res.y = (byte) (a.y >> bY);
        res.z = (byte) (a.z >> bZ);
        return res;
    }

    public static Vec3i shR_(Vec3i a, int b) {
        return shR(new Vec3i(), a, b, b, b);
    }

    public static Vec3i shR_(Vec3i a, Vec3i b) {
        return shR(new Vec3i(), a, b.x, b.y, b.z);
    }

    public static Vec3i shR(Vec3i a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3i shR(Vec3i a, Vec3i b) {
        return shR(a, a, b.x, b.y, b.z);
    }

    public static Vec3i shR(Vec3i res, Vec3i a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3i shR(Vec3i res, Vec3i a, Vec3i b) {
        return shR(res, a, b.x, b.y, b.z);
    }

    public static Vec3i shR(Vec3i res, Vec3i a, int bX, int bY, int bZ) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        res.z = a.z >> bZ;
        return res;
    }

    public static Vec3l shR_(Vec3l a, long b) {
        return shR(new Vec3l(), a, b, b, b);
    }

    public static Vec3l shR_(Vec3l a, Vec3l b) {
        return shR(new Vec3l(), a, b.x, b.y, b.z);
    }

    public static Vec3l shR(Vec3l a, long b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3l shR(Vec3l a, Vec3l b) {
        return shR(a, a, b.x, b.y, b.z);
    }

    public static Vec3l shR(Vec3l res, Vec3l a, long b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3l shR(Vec3l res, Vec3l a, Vec3l b) {
        return shR(res, a, b.x, b.y, b.z);
    }

    public static Vec3l shR(Vec3l res, Vec3l a, long bX, long bY, long bZ) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        res.z = a.z >> bZ;
        return res;
    }

    public static Vec3s shR_(Vec3s a, short b) {
        return shR(new Vec3s(), a, b, b, b);
    }

    public static Vec3s shR_(Vec3s a, int b) {
        return shR(new Vec3s(), a, b, b, b);
    }

    public static Vec3s shR_(Vec3s a, Vec3s b) {
        return shR(new Vec3s(), a, b.x, b.y, b.z);
    }

    public static Vec3s shR(Vec3s a, short b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3s shR(Vec3s a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3s shR(Vec3s a, Vec3s b) {
        return shR(a, a, b.x, b.y, b.z);
    }

    public static Vec3s shR(Vec3s res, Vec3s a, short b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3s shR(Vec3s res, Vec3s a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3s shR(Vec3s res, Vec3s a, Vec3s b) {
        return shR(res, a, b.x, b.y, b.z);
    }

    public static Vec3s shR(Vec3s res, Vec3s a, short bX, short bY, short bZ) {
        return shR(res, a, (int) bX, bY, bZ);
    }

    public static Vec3s shR(Vec3s res, Vec3s a, int bX, int bY, int bZ) {
        res.x = (short) (a.x >> bX);
        res.y = (short) (a.y >> bY);
        res.z = (short) (a.z >> bZ);
        return res;
    }

    public static Vec3ub shR_(Vec3ub a, UByte b) {
        return shR(new Vec3ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shR_(Vec3ub a, byte b) {
        return shR(new Vec3ub(), a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shR_(Vec3ub a, int b) {
        return shR(new Vec3ub(), a, b, b, b);
    }

    public static Vec3ub shR_(Vec3ub a, Vec3ub b) {
        return shR(new Vec3ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub a, UByte b) {
        return shR(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub a, byte b) {
        return shR(a, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shR(Vec3ub a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3ub shR(Vec3ub a, Vec3ub b) {
        return shR(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, UByte b) {
        return shR(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, byte b) {
        return shR(res, a, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, Vec3ub b) {
        return shR(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, UByte bX, UByte bY, UByte bZ) {
        return shR(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, byte bX, byte bY, byte bZ) {
        return shR(res, a, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public static Vec3ub shR(Vec3ub res, Vec3ub a, int bX, int bY, int bZ) {
        res.x.value = (byte) ((a.x.value & 0xff) >>> bX);
        res.y.value = (byte) ((a.y.value & 0xff) >>> bY);
        res.z.value = (byte) ((a.z.value & 0xff) >>> bZ);
        return res;
    }

    public static Vec3ui shR_(Vec3ui a, UInt b) {
        return shR(new Vec3ui(), a, b.value, b.value, b.value);
    }

    public static Vec3ui shR_(Vec3ui a, int b) {
        return shR(new Vec3ui(), a, b, b, b);
    }

    public static Vec3ui shR_(Vec3ui a, Vec3ui b) {
        return shR(new Vec3ui(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui shR(Vec3ui a, UInt b) {
        return shR(a, a, b.value, b.value, b.value);
    }

    public static Vec3ui shR(Vec3ui a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3ui shR(Vec3ui a, Vec3ui b) {
        return shR(a, a, b.x.value, b.y.value, b.y.value);
    }

    public static Vec3ui shR(Vec3ui res, Vec3ui a, UInt b) {
        return shR(res, a, b.value, b.value, b.value);
    }

    public static Vec3ui shR(Vec3ui res, Vec3ui a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3ui shR(Vec3ui res, Vec3ui a, Vec3ui b) {
        return shR(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ui shR(Vec3ui res, Vec3ui a, UInt bX, UInt bY, UInt bZ) {
        return shR(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ui shR(Vec3ui res, Vec3ui a, int bX, int bY, int bZ) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        res.z.value = a.z.value >>> bZ;
        return res;
    }

    public static Vec3ul shR_(Vec3ul a, ULong b) {
        return shR(new Vec3ul(), a, b.value, b.value, b.value);
    }

    public static Vec3ul shR_(Vec3ul a, long b) {
        return shR(new Vec3ul(), a, b, b, b);
    }

    public static Vec3ul shR_(Vec3ul a, Vec3ul b) {
        return shR(new Vec3ul(), a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shR(Vec3ul a, ULong b) {
        return shR(a, a, b.value, b.value, b.value);
    }

    public static Vec3ul shR(Vec3ul a, long b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3ul shR(Vec3ul a, Vec3ul b) {
        return shR(a, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shR(Vec3ul res, Vec3ul a, ULong b) {
        return shR(res, a, b.value, b.value, b.value);
    }

    public static Vec3ul shR(Vec3ul res, Vec3ul a, long b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3ul shR(Vec3ul res, Vec3ul a, Vec3ul b) {
        return shR(res, a, b.x.value, b.y.value, b.z.value);
    }

    public static Vec3ul shR(Vec3ul res, Vec3ul a, ULong bX, ULong bY, ULong bZ) {
        return shR(res, a, bX.value, bY.value, bZ.value);
    }

    public static Vec3ul shR(Vec3ul res, Vec3ul a, long bX, long bY, long bZ) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        res.z.value = a.z.value >>> bZ;
        return res;
    }

    public static Vec3us shR_(Vec3us a, UShort b) {
        return shR(new Vec3us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shR_(Vec3us a, short b) {
        return shR(new Vec3us(), a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shR_(Vec3us a, int b) {
        return shR(new Vec3us(), a, b, b, b);
    }

    public static Vec3us shR_(Vec3us a, Vec3us b) {
        return shR(new Vec3us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shR(Vec3us a, UShort b) {
        return shR(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shR(Vec3us a, short b) {
        return shR(a, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shR(Vec3us a, int b) {
        return shR(a, a, b, b, b);
    }

    public static Vec3us shR(Vec3us a, Vec3us b) {
        return shR(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, UShort b) {
        return shR(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, short b) {
        return shR(res, a, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, int b) {
        return shR(res, a, b, b, b);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, Vec3us b) {
        return shR(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, UShort bX, UShort bY, UShort bZ) {
        return shR(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, short bX, short bY, short bZ) {
        return shR(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public static Vec3us shR(Vec3us res, Vec3us a, int bX, int bY, int bZ) {
        res.x.value = (short) ((a.x.value & 0xffff) >>> bX);
        res.y.value = (short) ((a.y.value & 0xffff) >>> bY);
        res.z.value = (short) ((a.z.value & 0xffff) >>> bZ);
        return res;
    }

    public static Vec4b shR_(Vec4b a, byte b) {
        return shR(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b shR_(Vec4b a, int b) {
        return shR(new Vec4b(), a, b, b, b, b);
    }

    public static Vec4b shR_(Vec4b a, Vec4b b) {
        return shR(new Vec4b(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shR(Vec4b a, byte b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4b shR(Vec4b a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4b shR(Vec4b a, Vec4b b) {
        return shR(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shR(Vec4b res, Vec4b a, byte b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4b shR(Vec4b res, Vec4b a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4b shR(Vec4b res, Vec4b a, Vec4b b) {
        return shR(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4b shR(Vec4b res, Vec4b a, byte bX, byte bY, byte bZ, byte bW) {
        return shR(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4b shR(Vec4b res, Vec4b a, int bX, int bY, int bZ, int bW) {
        res.x = (byte) (a.x >> bX);
        res.y = (byte) (a.y >> bY);
        res.z = (byte) (a.z >> bZ);
        res.w = (byte) (a.w >> bW);
        return res;
    }

    public static Vec4i shR_(Vec4i a, int b) {
        return shR(new Vec4i(), a, b, b, b, b);
    }

    public static Vec4i shR_(Vec4i a, Vec4i b) {
        return shR(new Vec4i(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shR(Vec4i a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4i shR(Vec4i a, Vec4i b) {
        return shR(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shR(Vec4i res, Vec4i a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4i shR(Vec4i res, Vec4i a, Vec4i b) {
        return shR(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4i shR(Vec4i res, Vec4i a, int bX, int bY, int bZ, int bW) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        res.z = a.z >> bZ;
        res.w = a.w >> bW;
        return res;
    }

    public static Vec4l shR_(Vec4l a, long b) {
        return shR(new Vec4l(), a, b, b, b, b);
    }

    public static Vec4l shR_(Vec4l a, Vec4l b) {
        return shR(new Vec4l(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shR(Vec4l a, long b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4l shR(Vec4l a, Vec4l b) {
        return shR(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shR(Vec4l res, Vec4l a, long b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4l shR(Vec4l res, Vec4l a, Vec4l b) {
        return shR(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4l shR(Vec4l res, Vec4l a, long bX, long bY, long bZ, long bW) {
        res.x = a.x >> bX;
        res.y = a.y >> bY;
        res.z = a.z >> bZ;
        res.w = a.w >> bW;
        return res;
    }

    public static Vec4s shR_(Vec4s a, short b) {
        return shR(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s shR_(Vec4s a, int b) {
        return shR(new Vec4s(), a, b, b, b, b);
    }

    public static Vec4s shR_(Vec4s a, Vec4s b) {
        return shR(new Vec4s(), a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shR(Vec4s a, short b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4s shR(Vec4s a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4s shR(Vec4s a, Vec4s b) {
        return shR(a, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shR(Vec4s res, Vec4s a, short b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4s shR(Vec4s res, Vec4s a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4s shR(Vec4s res, Vec4s a, Vec4s b) {
        return shR(res, a, b.x, b.y, b.z, b.w);
    }

    public static Vec4s shR(Vec4s res, Vec4s a, short bX, short bY, short bZ, short bW) {
        return shR(res, a, (int) bX, bY, bZ, bW);
    }

    public static Vec4s shR(Vec4s res, Vec4s a, int bX, int bY, int bZ, int bW) {
        res.x = (short) (a.x >> bX);
        res.y = (short) (a.y >> bY);
        res.z = (short) (a.z >> bZ);
        res.w = (short) (a.w >> bW);
        return res;
    }

    public static Vec4ub shR_(Vec4ub a, UByte b) {
        return shR(new Vec4ub(), a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shR_(Vec4ub a, byte b) {
        return shR(new Vec4ub(), a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shR_(Vec4ub a, int b) {
        return shR(new Vec4ub(), a, b, b, b, b);
    }

    public static Vec4ub shR_(Vec4ub a, Vec4ub b) {
        return shR(new Vec4ub(), a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub a, UByte b) {
        return shR(a, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub a, byte b) {
        return shR(a, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shR(Vec4ub a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4ub shR(Vec4ub a, Vec4ub b) {
        return shR(a, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, UByte b) {
        return shR(res, a, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, byte b) {
        return shR(res, a, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, Vec4ub b) {
        return shR(res, a, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, UByte bX, UByte bY, UByte bZ, UByte bW) {
        return shR(res, a, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bZ.value & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, byte bX, byte bY, byte bZ, byte bW) {
        return shR(res, a, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public static Vec4ub shR(Vec4ub res, Vec4ub a, int bX, int bY, int bZ, int bW) {
        res.x.value = (byte) ((a.x.value & 0xff) >>> bX);
        res.y.value = (byte) ((a.y.value & 0xff) >>> bY);
        res.z.value = (byte) ((a.z.value & 0xff) >>> bZ);
        res.w.value = (byte) ((a.w.value & 0xff) >>> bW);
        return res;
    }

    public static Vec4ui shR_(Vec4ui a, UInt b) {
        return shR(new Vec4ui(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shR_(Vec4ui a, int b) {
        return shR(new Vec4ui(), a, b, b, b, b);
    }

    public static Vec4ui shR_(Vec4ui a, Vec4ui b) {
        return shR(new Vec4ui(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui shR(Vec4ui a, UInt b) {
        return shR(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shR(Vec4ui a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4ui shR(Vec4ui a, Vec4ui b) {
        return shR(a, a, b.x.value, b.y.value, b.y.value, b.w.value);
    }

    public static Vec4ui shR(Vec4ui res, Vec4ui a, UInt b) {
        return shR(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ui shR(Vec4ui res, Vec4ui a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4ui shR(Vec4ui res, Vec4ui a, Vec4ui b) {
        return shR(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ui shR(Vec4ui res, Vec4ui a, UInt bX, UInt bY, UInt bZ, UInt bW) {
        return shR(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ui shR(Vec4ui res, Vec4ui a, int bX, int bY, int bZ, int bW) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        res.z.value = a.z.value >>> bZ;
        res.w.value = a.w.value >>> bW;
        return res;
    }

    public static Vec4ul shR_(Vec4ul a, ULong b) {
        return shR(new Vec4ul(), a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shR_(Vec4ul a, long b) {
        return shR(new Vec4ul(), a, b, b, b, b);
    }

    public static Vec4ul shR_(Vec4ul a, Vec4ul b) {
        return shR(new Vec4ul(), a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shR(Vec4ul a, ULong b) {
        return shR(a, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shR(Vec4ul a, long b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4ul shR(Vec4ul a, Vec4ul b) {
        return shR(a, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shR(Vec4ul res, Vec4ul a, ULong b) {
        return shR(res, a, b.value, b.value, b.value, b.value);
    }

    public static Vec4ul shR(Vec4ul res, Vec4ul a, long b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4ul shR(Vec4ul res, Vec4ul a, Vec4ul b) {
        return shR(res, a, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public static Vec4ul shR(Vec4ul res, Vec4ul a, ULong bX, ULong bY, ULong bZ, ULong bW) {
        return shR(res, a, bX.value, bY.value, bZ.value, bW.value);
    }

    public static Vec4ul shR(Vec4ul res, Vec4ul a, long bX, long bY, long bZ, long bW) {
        res.x.value = a.x.value >>> bX;
        res.y.value = a.y.value >>> bY;
        res.z.value = a.z.value >>> bZ;
        res.w.value = a.w.value >>> bW;
        return res;
    }

    public static Vec4us shR_(Vec4us a, UShort b) {
        return shR(new Vec4us(), a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shR_(Vec4us a, short b) {
        return shR(new Vec4us(), a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shR_(Vec4us a, int b) {
        return shR(new Vec4us(), a, b, b, b, b);
    }

    public static Vec4us shR_(Vec4us a, Vec4us b) {
        return shR(new Vec4us(), a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shR(Vec4us a, UShort b) {
        return shR(a, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shR(Vec4us a, short b) {
        return shR(a, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shR(Vec4us a, int b) {
        return shR(a, a, b, b, b, b);
    }

    public static Vec4us shR(Vec4us a, Vec4us b) {
        return shR(a, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, UShort b) {
        return shR(res, a, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, short b) {
        return shR(res, a, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, int b) {
        return shR(res, a, b, b, b, b);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, Vec4us b) {
        return shR(res, a, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, UShort bX, UShort bY, UShort bZ, UShort bW) {
        return shR(res, a, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, short bX, short bY, short bZ, short bW) {
        return shR(res, a, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public static Vec4us shR(Vec4us res, Vec4us a, int bX, int bY, int bZ, int bW) {
        res.x.value = (short) ((a.x.value & 0xffff) >>> bX);
        res.y.value = (short) ((a.y.value & 0xffff) >>> bY);
        res.z.value = (short) ((a.z.value & 0xffff) >>> bZ);
        res.w.value = (short) ((a.w.value & 0xffff) >>> bW);
        return res;
    }

    public static Vec2b compl_(Vec2b a) {
        return compl(new Vec2b(), a);
    }

    public static Vec2b compl(Vec2b res, Vec2b a) {
        res.x = (byte) ~(a.x & 0xff);
        res.y = (byte) ~(a.y & 0xff);
        return res;
    }

    public static Vec2i compl_(Vec2i a) {
        return compl(new Vec2i(), a);
    }

    public static Vec2i compl(Vec2i res, Vec2i a) {
        res.x = ~a.x;
        res.y = ~a.y;
        return res;
    }

    public static Vec2l compl_(Vec2l a, long b) {
        return compl(new Vec2l(), a);
    }

    public static Vec2l compl(Vec2l res, Vec2l a) {
        res.x = ~a.x;
        res.y = ~a.y;
        return res;
    }

    public static Vec2s compl_(Vec2s a) {
        return compl(new Vec2s(), a);
    }

    public static Vec2s compl(Vec2s res, Vec2s a) {
        res.x = (short) ~a.x;
        res.y = (short) ~a.y;
        return res;
    }

    public static Vec2ub compl_(Vec2ub a) {
        return compl(new Vec2ub(), a);
    }

    public static Vec2ub compl(Vec2ub res, Vec2ub a) {
        res.x.value = (byte) ~(a.x.value & 0xff);
        res.y.value = (byte) ~(a.y.value & 0xff);
        return res;
    }

    public static Vec2ui compl_(Vec2ui a) {
        return compl(new Vec2ui(), a);
    }

    public static Vec2ui compl(Vec2ui res, Vec2ui a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        return res;
    }

    public static Vec2ul compl_(Vec2ul a) {
        return compl(new Vec2ul(), a);
    }

    public static Vec2ul compl(Vec2ul res, Vec2ul a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        return res;
    }

    public static Vec2us compl_(Vec2us a) {
        return compl(new Vec2us(), a);
    }

    public static Vec2us compl(Vec2us res, Vec2us a) {
        res.x.value = (short) ~(a.x.value & 0xffff);
        res.y.value = (short) ~(a.y.value & 0xffff);
        return res;
    }

    public static Vec3b compl_(Vec3b a) {
        return compl(new Vec3b(), a);
    }

    public static Vec3b compl(Vec3b res, Vec3b a) {
        res.x = (byte) ~a.x;
        res.y = (byte) ~a.y;
        res.z = (byte) ~a.z;
        return res;
    }

    public static Vec3i compl_(Vec3i a) {
        return compl(new Vec3i(), a);
    }

    public static Vec3i compl(Vec3i res, Vec3i a) {
        res.x = ~a.x;
        res.y = ~a.y;
        res.z = ~a.z;
        return res;
    }

    public static Vec3l compl_(Vec3l a) {
        return compl(new Vec3l(), a);
    }

    public static Vec3l compl(Vec3l res, Vec3l a) {
        res.x = ~a.x;
        res.y = ~a.y;
        res.z = ~a.z;
        return res;
    }

    public static Vec3s compl_(Vec3s a) {
        return compl(new Vec3s(), a);
    }

    public static Vec3s compl(Vec3s res, Vec3s a) {
        res.x = (short) ~a.x;
        res.y = (short) ~a.y;
        res.z = (short) ~a.z;
        return res;
    }

    public static Vec3ub compl_(Vec3ub a) {
        return compl(new Vec3ub(), a);
    }

    public static Vec3ub compl(Vec3ub res, Vec3ub a) {
        res.x.value = (byte) ~(a.x.value & 0xff);
        res.y.value = (byte) ~(a.y.value & 0xff);
        res.z.value = (byte) ~(a.z.value & 0xff);
        return res;
    }

    public static Vec3ui compl_(Vec3ui a) {
        return compl(new Vec3ui(), a);
    }

    public static Vec3ui compl(Vec3ui res, Vec3ui a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        res.z.value = ~a.z.value;
        return res;
    }

    public static Vec3ul compl_(Vec3ul a) {
        return compl(new Vec3ul(), a);
    }

    public static Vec3ul compl(Vec3ul res, Vec3ul a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        res.z.value = ~a.z.value;
        return res;
    }

    public static Vec3us compl_(Vec3us a) {
        return compl(new Vec3us(), a);
    }

    public static Vec3us compl(Vec3us res, Vec3us a) {
        res.x.value = (short) ~(a.x.value & 0xffff);
        res.y.value = (short) ~(a.y.value & 0xffff);
        res.z.value = (short) ~(a.z.value & 0xffff);
        return res;
    }

    public static Vec4b compl_(Vec4b a) {
        return compl(new Vec4b(), a);
    }

    public static Vec4b compl(Vec4b res, Vec4b a) {
        res.x = (byte) ~a.x;
        res.y = (byte) ~a.y;
        res.z = (byte) ~a.z;
        res.w = (byte) ~a.w;
        return res;
    }

    public static Vec4i compl_(Vec4i a) {
        return compl(new Vec4i(), a);
    }

    public static Vec4i compl(Vec4i res, Vec4i a) {
        res.x = ~a.x;
        res.y = ~a.y;
        res.z = ~a.z;
        res.w = ~a.w;
        return res;
    }

    public static Vec4l compl_(Vec4l a) {
        return compl(new Vec4l(), a);
    }

    public static Vec4l compl(Vec4l res, Vec4l a) {
        res.x = ~a.x;
        res.y = ~a.y;
        res.z = ~a.z;
        res.w = ~a.w;
        return res;
    }

    public static Vec4s compl_(Vec4s a) {
        return compl(new Vec4s(), a);
    }

    public static Vec4s compl(Vec4s res, Vec4s a) {
        res.x = (short) ~a.x;
        res.y = (short) ~a.y;
        res.z = (short) ~a.z;
        res.w = (short) ~a.w;
        return res;
    }

    public static Vec4ub compl_(Vec4ub a) {
        return compl(new Vec4ub(), a);
    }

    public static Vec4ub compl(Vec4ub res, Vec4ub a) {
        res.x.value = (byte) ~(a.x.value & 0xff);
        res.y.value = (byte) ~(a.y.value & 0xff);
        res.z.value = (byte) ~(a.z.value & 0xff);
        res.w.value = (byte) ~(a.w.value & 0xff);
        return res;
    }

    public static Vec4ui compl_(Vec4ui a) {
        return compl(new Vec4ui(), a);
    }

    public static Vec4ui compl(Vec4ui res, Vec4ui a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        res.z.value = ~a.z.value;
        res.w.value = ~a.w.value;
        return res;
    }

    public static Vec4ul compl_(Vec4ul a) {
        return compl(new Vec4ul(), a);
    }

    public static Vec4ul compl(Vec4ul res, Vec4ul a) {
        res.x.value = ~a.x.value;
        res.y.value = ~a.y.value;
        res.z.value = ~a.z.value;
        res.w.value = ~a.w.value;
        return res;
    }

    public static Vec4us compl_(Vec4us a) {
        return compl(new Vec4us(), a);
    }

    public static Vec4us compl(Vec4us res, Vec4us a) {
        res.x.value = (short) ~(a.x.value & 0xffff);
        res.y.value = (short) ~(a.y.value & 0xffff);
        res.z.value = (short) ~(a.z.value & 0xffff);
        res.w.value = (short) ~(a.w.value & 0xffff);
        return res;
    }

    public static void main(String[] args) {
        int a = 0b1100;
        int b = 0b1001;
        int c = Integer.remainderUnsigned(a, b);
        System.out.println(Integer.toUnsignedString(c));
    }
}
