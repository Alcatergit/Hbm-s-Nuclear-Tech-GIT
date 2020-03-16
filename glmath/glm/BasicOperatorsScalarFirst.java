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
abstract class BasicOperatorsScalarFirst extends BasicOperators {

    public static Vec2 add_(float a, Vec2 b) {
        return BasicOperators.add(new Vec2(), b, a, a);
    }

    public static Vec2 add(float a, Vec2 b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2 add(Vec2 res, float a, Vec2 b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2 add(Vec2 res, float aX, float aY, Vec2 b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2b add_(byte a, Vec2b b) {
        return BasicOperators.add(new Vec2b(), b, (int) a, a);
    }

    public static Vec2b add_(int a, Vec2b b) {
        return BasicOperators.add(new Vec2b(), b, a, a);
    }

    public static Vec2b add(byte a, Vec2b b) {
        return BasicOperators.add(b, b, (int) a, a);
    }

    public static Vec2b add(int a, Vec2b b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2b add(Vec2b res, byte a, Vec2b b) {
        return BasicOperators.add(res, b, (int) a, a);
    }

    public static Vec2b add(Vec2b res, int a, Vec2b b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2b add(Vec2b res, byte aX, byte aY, Vec2b b) {
        return BasicOperators.add(res, b, (int) aX, aY);
    }

    public static Vec2b add(Vec2b res, int aX, int aY, Vec2b b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2d add_(double a, Vec2d b) {
        return BasicOperators.add(new Vec2d(), b, a, a);
    }

    public static Vec2d add(double a, Vec2d b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2d add(Vec2d res, double a, Vec2d b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2d add(Vec2d res, double aX, double aY, Vec2d b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2i add_(int a, Vec2i b) {
        return BasicOperators.add(new Vec2i(), b, a, a);
    }

    public static Vec2i add(int a, Vec2i b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2i add(Vec2i res, int a, Vec2i b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2i add(Vec2i res, int aX, int aY, Vec2i b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2l add_(long a, Vec2l b) {
        return BasicOperators.add(new Vec2l(), b, a, a);
    }

    public static Vec2l add(long a, Vec2l b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2l add(Vec2l res, long a, Vec2l b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2l add(Vec2l res, long aX, long aY, Vec2l b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2s add_(short a, Vec2s b) {
        return BasicOperators.add(new Vec2s(), b, a, a);
    }

    public static Vec2s add(short a, Vec2s b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2s add(Vec2s res, short a, Vec2s b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2s add(Vec2s res, short aX, short aY, Vec2s b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2ub add_(UByte a, Vec2ub b) {
        return BasicOperators.add(new Vec2ub(), b, (int) a.value, a.value);
    }

    public static Vec2ub add_(byte a, Vec2ub b) {
        return BasicOperators.add(new Vec2ub(), b, (int) a, a);
    }

    public static Vec2ub add_(int a, Vec2ub b) {
        return BasicOperators.add(new Vec2ub(), b, a & 0xff, a & 0xff);
    }

    public static Vec2ub add(UByte a, Vec2ub b) {
        return BasicOperators.add(b, b, a.value & 0xff, a.value & 0xff);
    }

    public static Vec2ub add(byte a, Vec2ub b) {
        return BasicOperators.add(b, b, a & 0xff, a & 0xff);
    }

    public static Vec2ub add(int a, Vec2ub b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2ub add(Vec2ub res, UByte a, Vec2ub b) {
        return BasicOperators.add(res, b, a.value & 0xff, a.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, byte a, Vec2ub b) {
        return BasicOperators.add(res, b, a & 0xff, a & 0xff);
    }

    public static Vec2ub add(Vec2ub res, int a, Vec2ub b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2ub add(Vec2ub res, UByte aX, UByte aY, Vec2ub b) {
        return BasicOperators.add(res, b, aX.value & 0xff, aY.value & 0xff);
    }

    public static Vec2ub add(Vec2ub res, byte aX, byte aY, Vec2ub b) {
        return BasicOperators.add(res, b, aX & 0xff, aY & 0xff);
    }

    public static Vec2ub add(Vec2ub res, int aX, int aY, Vec2ub b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2ui add_(UInt a, Vec2ui b) {
        return BasicOperators.add(new Vec2ui(), b, a.value, a.value);
    }

    public static Vec2ui add_(int a, Vec2ui b) {
        return BasicOperators.add(new Vec2ui(), b, a, a);
    }

    public static Vec2ui add(UInt a, Vec2ui b) {
        return BasicOperators.add(b, b, a.value, a.value);
    }

    public static Vec2ui add(int a, Vec2ui b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2ui add(Vec2ui res, UInt a, Vec2ui b) {
        return BasicOperators.add(res, b, a.value, a.value);
    }

    public static Vec2ui add(Vec2ui res, int a, Vec2ui b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2ui add(Vec2ui res, UInt aX, UInt aY, Vec2ui b) {
        return BasicOperators.add(res, b, aX.value, aY.value);
    }

    public static Vec2ui add(Vec2ui res, int aX, int aY, Vec2ui b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2ul add_(ULong a, Vec2ul b) {
        return BasicOperators.add(new Vec2ul(), b, a.value, a.value);
    }

    public static Vec2ul add_(long a, Vec2ul b) {
        return BasicOperators.add(new Vec2ul(), b, a, a);
    }

    public static Vec2ul add(ULong a, Vec2ul b) {
        return BasicOperators.add(b, b, a.value, a.value);
    }

    public static Vec2ul add(long a, Vec2ul b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2ul add(Vec2ul res, ULong a, Vec2ul b) {
        return BasicOperators.add(res, b, a.value, a.value);
    }

    public static Vec2ul add(Vec2ul res, long a, Vec2ul b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2ul add(Vec2ul res, ULong aX, ULong aY, Vec2ul b) {
        return BasicOperators.add(res, b, aX.value, aY.value);
    }

    public static Vec2ul add(Vec2ul res, long aX, long aY, Vec2ul b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec2us add_(UShort a, Vec2us b) {
        return BasicOperators.add(new Vec2us(), b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us add_(short a, Vec2us b) {
        return BasicOperators.add(new Vec2us(), b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us add_(int a, Vec2us b) {
        return BasicOperators.add(new Vec2us(), b, a, a);
    }

    public static Vec2us add(UShort a, Vec2us b) {
        return BasicOperators.add(b, b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us add(short a, Vec2us b) {
        return BasicOperators.add(b, b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us add(int a, Vec2us b) {
        return BasicOperators.add(b, b, a, a);
    }

    public static Vec2us add(Vec2us res, UShort a, Vec2us b) {
        return BasicOperators.add(res, b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, short a, Vec2us b) {
        return BasicOperators.add(res, b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us add(Vec2us res, int a, Vec2us b) {
        return BasicOperators.add(res, b, a, a);
    }

    public static Vec2us add(Vec2us res, UShort aX, UShort aY, Vec2us b) {
        return BasicOperators.add(res, b, aX.value & 0xffff, aY.value & 0xffff);
    }

    public static Vec2us add(Vec2us res, short aX, short aY, Vec2us b) {
        return BasicOperators.add(res, b, aX & 0xffff, aY & 0xffff);
    }

    public static Vec2us add(Vec2us res, int aX, int aY, Vec2us b) {
        return BasicOperators.add(res, b, aX, aY);
    }

    public static Vec3 add_(float a, Vec3 b) {
        return BasicOperators.add(new Vec3(), b, a, a, a);
    }

    public static Vec3 add(float a, Vec3 b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3 add(Vec3 res, float a, Vec3 b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3 add(Vec3 res, float aX, float aY, float aZ, Vec3 b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3b add_(byte a, Vec3b b) {
        return BasicOperators.add(new Vec3b(), b, (int) a, a, a);
    }

    public static Vec3b add_(int a, Vec3b b) {
        return BasicOperators.add(new Vec3b(), b, a, a, a);
    }

    public static Vec3b add(byte a, Vec3b b) {
        return BasicOperators.add(b, b, (int) a, a, a);
    }

    public static Vec3b add(int a, Vec3b b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3b add(Vec3b res, byte a, Vec3b b) {
        return BasicOperators.add(res, b, (int) a, a, a);
    }

    public static Vec3b add(Vec3b res, int a, Vec3b b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3b add(Vec3b res, byte aX, byte aY, byte aZ, Vec3b b) {
        return BasicOperators.add(res, b, (int) aX, aY, aZ);
    }

    public static Vec3b add(Vec3b res, int aX, int aY, int aZ, Vec3b b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3d add_(double a, Vec3d b) {
        return BasicOperators.add(new Vec3d(), b, a, a, a);
    }

    public static Vec3d add(double a, Vec3d b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3d add(Vec3d res, double a, Vec3d b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3d add(Vec3d res, double aX, double aY, double aZ, Vec3d b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3i add_(int a, Vec3i b) {
        return BasicOperators.add(new Vec3i(), b, a, a, a);
    }

    public static Vec3i add(int a, Vec3i b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3i add(Vec3i res, int a, Vec3i b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3i add(Vec3i res, int aX, int aY, int aZ, Vec3i b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3l add_(long a, Vec3l b) {
        return BasicOperators.add(new Vec3l(), b, a, a, a);
    }

    public static Vec3l add(long a, Vec3l b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3l add(Vec3l res, long a, Vec3l b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3l add(Vec3l res, long aX, long aY, long aZ, Vec3l b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3s add_(short a, Vec3s b) {
        return BasicOperators.add(new Vec3s(), b, a, a, a);
    }

    public static Vec3s add(short a, Vec3s b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3s add(Vec3s res, short a, Vec3s b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3s add(Vec3s res, short aX, short aY, short aZ, Vec3s b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3ub add_(UByte a, Vec3ub b) {
        return BasicOperators.add(new Vec3ub(), b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub add_(byte a, Vec3ub b) {
        return BasicOperators.add(new Vec3ub(), b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub add_(int a, Vec3ub b) {
        return BasicOperators.add(new Vec3ub(), b, a, a, a);
    }

    public static Vec3ub add(UByte a, Vec3ub b) {
        return BasicOperators.add(b, b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub add(byte a, Vec3ub b) {
        return BasicOperators.add(b, b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub add(int a, Vec3ub b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3ub add(Vec3ub res, UByte a, Vec3ub b) {
        return BasicOperators.add(res, b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, byte a, Vec3ub b) {
        return BasicOperators.add(res, b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub add(Vec3ub res, int a, Vec3ub b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3ub add(Vec3ub res, UByte aX, UByte aY, UByte aZ, Vec3ub b) {
        return BasicOperators.add(res, b, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff);
    }

    public static Vec3ub add(Vec3ub res, byte aX, byte aY, byte aZ, Vec3ub b) {
        return BasicOperators.add(res, b, aX & 0xff, aY & 0xff, aZ & 0xff);
    }

    public static Vec3ub add(Vec3ub res, int aX, int aY, int aZ, Vec3ub b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3ui add_(UInt a, Vec3ui b) {
        return BasicOperators.add(new Vec3ui(), b, a.value, a.value, a.value);
    }

    public static Vec3ui add_(int a, Vec3ui b) {
        return BasicOperators.add(new Vec3ui(), b, a, a, a);
    }

    public static Vec3ui add(UInt a, Vec3ui b) {
        return BasicOperators.add(b, b, a.value, a.value, a.value);
    }

    public static Vec3ui add(int a, Vec3ui b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3ui add(Vec3ui res, UInt a, Vec3ui b) {
        return BasicOperators.add(res, b, a.value, a.value, a.value);
    }

    public static Vec3ui add(Vec3ui res, int a, Vec3ui b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3ui add(Vec3ui res, UInt aX, UInt aY, UInt aZ, Vec3ui b) {
        return BasicOperators.add(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ui add(Vec3ui res, int aX, int aY, int aZ, Vec3ui b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3ul add_(ULong a, Vec3ul b) {
        return BasicOperators.add(new Vec3ul(), b, a.value, a.value, a.value);
    }

    public static Vec3ul add_(long a, Vec3ul b) {
        return BasicOperators.add(new Vec3ul(), b, a, a, a);
    }

    public static Vec3ul add(ULong a, Vec3ul b) {
        return BasicOperators.add(b, b, a.value, a.value, a.value);
    }

    public static Vec3ul add(long a, Vec3ul b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3ul add(Vec3ul res, ULong a, Vec3ul b) {
        return BasicOperators.add(res, b, a.value, a.value, a.value);
    }

    public static Vec3ul add(Vec3ul res, long a, Vec3ul b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3ul add(Vec3ul res, ULong aX, ULong aY, ULong aZ, Vec3ul b) {
        return BasicOperators.add(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ul add(Vec3ul res, long aX, long aY, long aZ, Vec3ul b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec3us add_(UShort a, Vec3us b) {
        return BasicOperators.add(new Vec3us(), b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us add_(short a, Vec3us b) {
        return BasicOperators.add(new Vec3us(), b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us add_(int a, Vec3us b) {
        return BasicOperators.add(new Vec3us(), b, a, a, a);
    }

    public static Vec3us add(UShort a, Vec3us b) {
        return BasicOperators.add(b, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us add(short a, Vec3us b) {
        return BasicOperators.add(b, b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us add(int a, Vec3us b) {
        return BasicOperators.add(b, b, a, a, a);
    }

    public static Vec3us add(Vec3us res, UShort a, Vec3us b) {
        return BasicOperators.add(res, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, short a, Vec3us b) {
        return BasicOperators.add(res, b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us add(Vec3us res, int a, Vec3us b) {
        return BasicOperators.add(res, b, a, a, a);
    }

    public static Vec3us add(Vec3us res, UShort aX, UShort aY, UShort aZ, Vec3us b) {
        return BasicOperators.add(res, b, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff);
    }

    public static Vec3us add(Vec3us res, short aX, short aY, short aZ, Vec3us b) {
        return BasicOperators.add(res, b, aX & 0xffff, aY & 0xffff, aZ & 0xffff);
    }

    public static Vec3us add(Vec3us res, int aX, int aY, int aZ, Vec3us b) {
        return BasicOperators.add(res, b, aX, aY, aZ);
    }

    public static Vec4 add_(float a, Vec4 b) {
        return BasicOperators.add(new Vec4(), b, a, a, a, a);
    }

    public static Vec4 add(float a, Vec4 b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4 add(Vec4 res, float a, Vec4 b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4 add(Vec4 res, float aX, float aY, float aZ, float aW, Vec4 b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4b add_(byte a, Vec4b b) {
        return BasicOperators.add(new Vec4b(), b, (int) a, a, a, a);
    }

    public static Vec4b add_(int a, Vec4b b) {
        return BasicOperators.add(new Vec4b(), b, a, a, a, a);
    }

    public static Vec4b add(byte a, Vec4b b) {
        return BasicOperators.add(b, b, (int) a, a, a, a);
    }

    public static Vec4b add(int a, Vec4b b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4b add(Vec4b res, byte a, Vec4b b) {
        return BasicOperators.add(res, b, (int) a, a, a, a);
    }

    public static Vec4b add(Vec4b res, int a, Vec4b b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4b add(Vec4b res, byte aX, byte aY, byte aZ, byte aW, Vec4b b) {
        return BasicOperators.add(res, b, (int) aX, aY, aZ, aW);
    }

    public static Vec4b add(Vec4b res, int aX, int aY, int aZ, int aW, Vec4b b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4d add_(double a, Vec4d b) {
        return BasicOperators.add(new Vec4d(), b, a, a, a, a);
    }

    public static Vec4d add(double a, Vec4d b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4d add(Vec4d res, double a, Vec4d b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4d add(Vec4d res, double aX, double aY, double aZ, double aW, Vec4d b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4i add_(int a, Vec4i b) {
        return BasicOperators.add(new Vec4i(), b, a, a, a, a);
    }

    public static Vec4i add(int a, Vec4i b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4i add(Vec4i res, int a, Vec4i b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4i add(Vec4i res, int aX, int aY, int aZ, int aW, Vec4i b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4l add_(long a, Vec4l b) {
        return BasicOperators.add(new Vec4l(), b, a, a, a, a);
    }

    public static Vec4l add(long a, Vec4l b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4l add(Vec4l res, long a, Vec4l b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4l add(Vec4l res, long aX, long aY, long aZ, long aW, Vec4l b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4s add_(short a, Vec4s b) {
        return BasicOperators.add(new Vec4s(), b, a, a, a, a);
    }

    public static Vec4s add(short a, Vec4s b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4s add(Vec4s res, short a, Vec4s b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4s add(Vec4s res, short aX, short aY, short aZ, short aW, Vec4s b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ub add_(UByte a, Vec4ub b) {
        return BasicOperators.add(new Vec4ub(), b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub add_(byte a, Vec4ub b) {
        return BasicOperators.add(new Vec4ub(), b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub add_(int a, Vec4ub b) {
        return BasicOperators.add(new Vec4ub(), b, a, a, a, a);
    }

    public static Vec4ub add(UByte a, Vec4ub b) {
        return BasicOperators.add(b, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub add(byte a, Vec4ub b) {
        return BasicOperators.add(b, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub add(int a, Vec4ub b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4ub add(Vec4ub res, UByte a, Vec4ub b) {
        return BasicOperators.add(res, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, byte a, Vec4ub b) {
        return BasicOperators.add(res, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub add(Vec4ub res, int a, Vec4ub b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4ub add(Vec4ub res, UByte aX, UByte aY, UByte aZ, UByte aW, Vec4ub b) {
        return BasicOperators.add(res, b, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, aW.value & 0xff);
    }

    public static Vec4ub add(Vec4ub res, byte aX, byte aY, byte aZ, byte aW, Vec4ub b) {
        return BasicOperators.add(res, b, aX & 0xff, aY & 0xff, aZ & 0xff, aW & 0xff);
    }

    public static Vec4ub add(Vec4ub res, int aX, int aY, int aZ, int aW, Vec4ub b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ui add_(UInt a, Vec4ui b) {
        return BasicOperators.add(new Vec4ui(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui add_(int a, Vec4ui b) {
        return BasicOperators.add(new Vec4ui(), b, a, a, a, a);
    }

    public static Vec4ui add(UInt a, Vec4ui b) {
        return BasicOperators.add(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui add(int a, Vec4ui b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4ui add(Vec4ui res, UInt a, Vec4ui b) {
        return BasicOperators.add(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui add(Vec4ui res, int a, Vec4ui b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4ui add(Vec4ui res, UInt aX, UInt aY, UInt aZ, UInt aW, Vec4ui b) {
        return BasicOperators.add(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ui add(Vec4ui res, int aX, int aY, int aZ, int aW, Vec4ui b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ul add_(ULong a, Vec4ul b) {
        return BasicOperators.add(new Vec4ul(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul add_(long a, Vec4ul b) {
        return BasicOperators.add(new Vec4ul(), b, a, a, a, a);
    }

    public static Vec4ul add(ULong a, Vec4ul b) {
        return BasicOperators.add(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul add(long a, Vec4ul b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4ul add(Vec4ul res, ULong a, Vec4ul b) {
        return BasicOperators.add(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul add(Vec4ul res, long a, Vec4ul b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4ul add(Vec4ul res, ULong aX, ULong aY, ULong aZ, ULong aW, Vec4ul b) {
        return BasicOperators.add(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ul add(Vec4ul res, long aX, long aY, long aZ, long aW, Vec4ul b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec4us add_(UShort a, Vec4us b) {
        return BasicOperators.add(new Vec4us(), b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us add_(short a, Vec4us b) {
        return BasicOperators.add(new Vec4us(), b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us add_(int a, Vec4us b) {
        return BasicOperators.add(new Vec4us(), b, a, a, a, a);
    }

    public static Vec4us add(UShort a, Vec4us b) {
        return BasicOperators.add(b, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us add(short a, Vec4us b) {
        return BasicOperators.add(b, b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us add(int a, Vec4us b) {
        return BasicOperators.add(b, b, a, a, a, a);
    }

    public static Vec4us add(Vec4us res, UShort a, Vec4us b) {
        return BasicOperators.add(res, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, short a, Vec4us b) {
        return BasicOperators.add(res, b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us add(Vec4us res, int a, Vec4us b) {
        return BasicOperators.add(res, b, a, a, a, a);
    }

    public static Vec4us add(Vec4us res, UShort aX, UShort aY, UShort aZ, UShort aW, Vec4us b) {
        return BasicOperators.add(res, b, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, aW.value & 0xffff);
    }

    public static Vec4us add(Vec4us res, short aX, short aY, short aZ, short aW, Vec4us b) {
        return BasicOperators.add(res, b, aX & 0xffff, aY & 0xffff, aZ & 0xffff, aW & 0xffff);
    }

    public static Vec4us add(Vec4us res, int aX, int aY, int aZ, int aW, Vec4us b) {
        return BasicOperators.add(res, b, aX, aY, aZ, aW);
    }

    public static Vec2 sub_(float a, Vec2 b) {
        return sub(new Vec2(), a, a, b);
    }

    public static Vec2 sub(float a, Vec2 b) {
        return sub(b, a, a, b);
    }

    public static Vec2 sub(Vec2 res, float a, Vec2 b) {
        return sub(res, a, a, b);
    }

    public static Vec2 sub(Vec2 res, float aX, float aY, Vec2 b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        return res;
    }

    public static Vec2b sub_(byte a, Vec2b b) {
        return sub(new Vec2b(), (int) a, a, b);
    }

    public static Vec2b sub_(int a, Vec2b b) {
        return sub(new Vec2b(), a, a, b);
    }

    public static Vec2b sub(byte a, Vec2b b) {
        return sub(b, (int) a, a, b);
    }

    public static Vec2b sub(int a, Vec2b b) {
        return sub(b, a, a, b);
    }

    public static Vec2b sub(Vec2b res, byte a, Vec2b b) {
        return sub(res, (int) a, a, b);
    }

    public static Vec2b sub(Vec2b res, int a, Vec2b b) {
        return sub(res, a, a, b);
    }

    public static Vec2b sub(Vec2b res, byte aX, byte aY, Vec2b b) {
        return sub(res, (int) aX, aY, b);
    }

    public static Vec2b sub(Vec2b res, int aX, int aY, Vec2b b) {
        res.x = (byte) (aX - b.x);
        res.y = (byte) (aY - b.y);
        return res;
    }

    public static Vec2d sub_(double a, Vec2d b) {
        return sub(new Vec2d(), a, a, b);
    }

    public static Vec2d sub(double a, Vec2d b) {
        return sub(b, a, a, b);
    }

    public static Vec2d sub(Vec2d res, double a, Vec2d b) {
        return sub(res, a, a, b);
    }

    public static Vec2d sub(Vec2d res, double aX, double aY, Vec2d b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        return res;
    }

    public static Vec2i sub_(int a, Vec2i b) {
        return sub(new Vec2i(), a, a, b);
    }

    public static Vec2i sub(int a, Vec2i b) {
        return sub(b, a, a, b);
    }

    public static Vec2i sub(Vec2i res, int a, Vec2i b) {
        return sub(res, a, a, b);
    }

    public static Vec2i sub(Vec2i res, int aX, int aY, Vec2i b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        return res;
    }

    public static Vec2l sub_(long a, Vec2l b) {
        return sub(new Vec2l(), a, a, b);
    }

    public static Vec2l sub(long a, Vec2l b) {
        return sub(b, a, a, b);
    }

    public static Vec2l sub(Vec2l res, long a, Vec2l b) {
        return sub(res, a, a, b);
    }

    public static Vec2l sub(Vec2l res, long aX, long aY, Vec2l b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        return res;
    }

    public static Vec2s sub_(short a, Vec2s b) {
        return sub(new Vec2s(), a, a, b);
    }

    public static Vec2s sub(short a, Vec2s b) {
        return sub(b, a, a, b);
    }

    public static Vec2s sub(Vec2s res, short a, Vec2s b) {
        return sub(res, a, a, b);
    }

    public static Vec2s sub(Vec2s res, short aX, short aY, Vec2s b) {
        res.x = (short) (aX - b.x);
        res.y = (short) (aY - b.y);
        return res;
    }

    public static Vec2ub sub_(UByte a, Vec2ub b) {
        return sub(new Vec2ub(), a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub sub_(byte a, Vec2ub b) {
        return sub(new Vec2ub(), a & 0xff, a & 0xff, b);
    }

    public static Vec2ub sub_(int a, Vec2ub b) {
        return sub(new Vec2ub(), a, a, b);
    }

    public static Vec2ub sub(UByte a, Vec2ub b) {
        return sub(b, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub sub(byte a, Vec2ub b) {
        return sub(b, a & 0xff, a & 0xff, b);
    }

    public static Vec2ub sub(int a, Vec2ub b) {
        return sub(b, a, a, b);
    }

    public static Vec2ub sub(Vec2ub res, UByte a, Vec2ub b) {
        return sub(res, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub sub(Vec2ub res, byte a, Vec2ub b) {
        return sub(res, a & 0xff, a & 0xff, b);
    }

    public static Vec2ub sub(Vec2ub res, int a, Vec2ub b) {
        return sub(res, a, a, b);
    }

    public static Vec2ub sub(Vec2ub res, UByte aX, UByte aY, Vec2ub b) {
        return sub(res, aX.value & 0xff, aY.value & 0xff, b);
    }

    public static Vec2ub sub(Vec2ub res, byte aX, byte aY, Vec2ub b) {
        return sub(res, aX & 0xff, aY & 0xff, b);
    }

    public static Vec2ub sub(Vec2ub res, int aX, int aY, Vec2ub b) {
        res.x.value = (byte) (aX - (b.x.value & 0xff));
        res.y.value = (byte) (aY - (b.y.value & 0xff));
        return res;
    }

    public static Vec2ui sub_(UInt a, Vec2ui b) {
        return sub(new Vec2ui(), a.value, a.value, b);
    }

    public static Vec2ui sub_(int a, Vec2ui b) {
        return sub(new Vec2ui(), a, a, b);
    }

    public static Vec2ui sub(UInt a, Vec2ui b) {
        return sub(b, a.value, a.value, b);
    }

    public static Vec2ui sub(int a, Vec2ui b) {
        return sub(b, a, a, b);
    }

    public static Vec2ui sub(Vec2ui res, UInt a, Vec2ui b) {
        return sub(res, a.value, a.value, b);
    }

    public static Vec2ui sub(Vec2ui res, int a, Vec2ui b) {
        return sub(res, a, a, b);
    }

    public static Vec2ui sub(Vec2ui res, UInt aX, UInt aY, Vec2ui b) {
        return sub(res, aX.value, aY.value, b);
    }

    public static Vec2ui sub(Vec2ui res, int aX, int aY, Vec2ui b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        return res;
    }

    public static Vec2ul sub_(ULong a, Vec2ul b) {
        return sub(new Vec2ul(), a.value, a.value, b);
    }

    public static Vec2ul sub_(long a, Vec2ul b) {
        return sub(new Vec2ul(), a, a, b);
    }

    public static Vec2ul sub(ULong a, Vec2ul b) {
        return sub(b, a.value, a.value, b);
    }

    public static Vec2ul sub(long a, Vec2ul b) {
        return sub(b, a, a, b);
    }

    public static Vec2ul sub(Vec2ul res, ULong a, Vec2ul b) {
        return sub(res, a.value, a.value, b);
    }

    public static Vec2ul sub(Vec2ul res, long a, Vec2ul b) {
        return sub(res, a, a, b);
    }

    public static Vec2ul sub(Vec2ul res, ULong aX, ULong aY, Vec2ul b) {
        return sub(res, aX.value, aY.value, b);
    }

    public static Vec2ul sub(Vec2ul res, long aX, long aY, Vec2ul b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        return res;
    }

    public static Vec2us sub_(UShort a, Vec2us b) {
        return sub(new Vec2us(), a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us sub_(short a, Vec2us b) {
        return sub(new Vec2us(), a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us sub_(int a, Vec2us b) {
        return sub(new Vec2us(), a, a, b);
    }

    public static Vec2us sub(UShort a, Vec2us b) {
        return sub(b, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us sub(short a, Vec2us b) {
        return sub(b, a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us sub(int a, Vec2us b) {
        return sub(b, a, a, b);
    }

    public static Vec2us sub(Vec2us res, UShort a, Vec2us b) {
        return sub(res, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us sub(Vec2us res, short a, Vec2us b) {
        return sub(res, a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us sub(Vec2us res, int a, Vec2us b) {
        return sub(res, a, a, b);
    }

    public static Vec2us sub(Vec2us res, UShort aX, UShort aY, Vec2us b) {
        return sub(res, aX.value & 0xffff, aY.value & 0xffff, b);
    }

    public static Vec2us sub(Vec2us res, short aX, short aY, Vec2us b) {
        return sub(res, aX & 0xffff, aY & 0xffff, b);
    }

    public static Vec2us sub(Vec2us res, int aX, int aY, Vec2us b) {
        res.x.value = (short) (aX - (b.x.value & 0xffff));
        res.y.value = (short) (aY - (b.y.value & 0xffff));
        return res;
    }

    public static Vec3 sub_(float a, Vec3 b) {
        return sub(new Vec3(), a, a, a, b);
    }

    public static Vec3 sub(float a, Vec3 b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3 sub(Vec3 res, float a, Vec3 b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3 sub(Vec3 res, float aX, float aY, float aZ, Vec3 b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        return res;
    }

    public static Vec3b sub_(byte a, Vec3b b) {
        return sub(new Vec3b(), (int) a, a, a, b);
    }

    public static Vec3b sub_(int a, Vec3b b) {
        return sub(new Vec3b(), a, a, a, b);
    }

    public static Vec3b sub(byte a, Vec3b b) {
        return sub(b, (int) a, a, a, b);
    }

    public static Vec3b sub(int a, Vec3b b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3b sub(Vec3b res, byte a, Vec3b b) {
        return sub(res, (int) a, a, a, b);
    }

    public static Vec3b sub(Vec3b res, int a, Vec3b b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3b sub(Vec3b res, byte aX, byte aY, byte aZ, Vec3b b) {
        return sub(res, (int) aX, aY, aZ, b);
    }

    public static Vec3b sub(Vec3b res, int aX, int aY, int aZ, Vec3b b) {
        res.x = (byte) (aX - b.x);
        res.y = (byte) (aY - b.y);
        res.z = (byte) (aZ - b.z);
        return res;
    }

    public static Vec3d sub_(double a, Vec3d b) {
        return sub(new Vec3d(), a, a, a, b);
    }

    public static Vec3d sub(double a, Vec3d b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3d sub(Vec3d res, double a, Vec3d b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3d sub(Vec3d res, double aX, double aY, double aZ, Vec3d b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        return res;
    }

    public static Vec3i sub_(int a, Vec3i b) {
        return sub(new Vec3i(), a, a, a, b);
    }

    public static Vec3i sub(int a, Vec3i b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3i sub(Vec3i res, int a, Vec3i b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3i sub(Vec3i res, int aX, int aY, int aZ, Vec3i b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        return res;
    }

    public static Vec3l sub_(long a, Vec3l b) {
        return sub(new Vec3l(), a, a, a, b);
    }

    public static Vec3l sub(long a, Vec3l b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3l sub(Vec3l res, long a, Vec3l b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3l sub(Vec3l res, long aX, long aY, long aZ, Vec3l b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        return res;
    }

    public static Vec3s sub_(short a, Vec3s b) {
        return sub(new Vec3s(), a, a, a, b);
    }

    public static Vec3s sub(short a, Vec3s b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3s sub(Vec3s res, short a, Vec3s b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3s sub(Vec3s res, short aX, short aY, short aZ, Vec3s b) {
        res.x = (short) (aX - b.x);
        res.y = (short) (aY - b.y);
        res.z = (short) (aZ - b.z);
        return res;
    }

    public static Vec3ub sub_(UByte a, Vec3ub b) {
        return sub(new Vec3ub(), a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub sub_(byte a, Vec3ub b) {
        return sub(new Vec3ub(), a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub sub_(int a, Vec3ub b) {
        return sub(new Vec3ub(), a, a, a, b);
    }

    public static Vec3ub sub(UByte a, Vec3ub b) {
        return sub(b, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub sub(byte a, Vec3ub b) {
        return sub(b, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub sub(int a, Vec3ub b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3ub sub(Vec3ub res, UByte a, Vec3ub b) {
        return sub(res, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub sub(Vec3ub res, byte a, Vec3ub b) {
        return sub(res, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub sub(Vec3ub res, int a, Vec3ub b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3ub sub(Vec3ub res, UByte aX, UByte aY, UByte aZ, Vec3ub b) {
        return sub(res, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, b);
    }

    public static Vec3ub sub(Vec3ub res, byte aX, byte aY, byte aZ, Vec3ub b) {
        return sub(res, aX & 0xff, aY & 0xff, aZ & 0xff, b);
    }

    public static Vec3ub sub(Vec3ub res, int aX, int aY, int aZ, Vec3ub b) {
        res.x.value = (byte) (aX - (b.x.value & 0xff));
        res.y.value = (byte) (aY - (b.y.value & 0xff));
        res.z.value = (byte) (aZ - (b.z.value & 0xff));
        return res;
    }

    public static Vec3ui sub_(UInt a, Vec3ui b) {
        return sub(new Vec3ui(), a.value, a.value, a.value, b);
    }

    public static Vec3ui sub_(int a, Vec3ui b) {
        return sub(new Vec3ui(), a, a, a, b);
    }

    public static Vec3ui sub(UInt a, Vec3ui b) {
        return sub(b, a.value, a.value, a.value, b);
    }

    public static Vec3ui sub(int a, Vec3ui b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3ui sub(Vec3ui res, UInt a, Vec3ui b) {
        return sub(res, a.value, a.value, a.value, b);
    }

    public static Vec3ui sub(Vec3ui res, int a, Vec3ui b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3ui sub(Vec3ui res, UInt aX, UInt aY, UInt aZ, Vec3ui b) {
        return sub(res, aX.value, aY.value, aZ.value, b);
    }

    public static Vec3ui sub(Vec3ui res, int aX, int aY, int aZ, Vec3ui b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        res.z.value = aZ - b.z.value;
        return res;
    }

    public static Vec3ul sub_(ULong a, Vec3ul b) {
        return sub(new Vec3ul(), a.value, a.value, a.value, b);
    }

    public static Vec3ul sub_(long a, Vec3ul b) {
        return sub(new Vec3ul(), a, a, a, b);
    }

    public static Vec3ul sub(ULong a, Vec3ul b) {
        return sub(b, a.value, a.value, a.value, b);
    }

    public static Vec3ul sub(long a, Vec3ul b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3ul sub(Vec3ul res, ULong a, Vec3ul b) {
        return sub(res, a.value, a.value, a.value, b);
    }

    public static Vec3ul sub(Vec3ul res, long a, Vec3ul b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3ul sub(Vec3ul res, ULong aX, ULong aY, ULong aZ, Vec3ul b) {
        return sub(res, aX.value, aY.value, aZ.value, b);
    }

    public static Vec3ul sub(Vec3ul res, long aX, long aY, long aZ, Vec3ul b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        res.z.value = aZ - b.z.value;
        return res;
    }

    public static Vec3us sub_(UShort a, Vec3us b) {
        return sub(new Vec3us(), a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us sub_(short a, Vec3us b) {
        return sub(new Vec3us(), a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us sub_(int a, Vec3us b) {
        return sub(new Vec3us(), a, a, a, b);
    }

    public static Vec3us sub(UShort a, Vec3us b) {
        return sub(b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us sub(short a, Vec3us b) {
        return sub(b, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us sub(int a, Vec3us b) {
        return sub(b, a, a, a, b);
    }

    public static Vec3us sub(Vec3us res, UShort a, Vec3us b) {
        return sub(res, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us sub(Vec3us res, short a, Vec3us b) {
        return sub(res, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us sub(Vec3us res, int a, Vec3us b) {
        return sub(res, a, a, a, b);
    }

    public static Vec3us sub(Vec3us res, UShort aX, UShort aY, UShort aZ, Vec3us b) {
        return sub(res, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, b);
    }

    public static Vec3us sub(Vec3us res, short aX, short aY, short aZ, Vec3us b) {
        return sub(res, aX & 0xffff, aY & 0xffff, aZ & 0xffff, b);
    }

    public static Vec3us sub(Vec3us res, int aX, int aY, int aZ, Vec3us b) {
        res.x.value = (short) (aX - (b.x.value & 0xffff));
        res.y.value = (short) (aY - (b.y.value & 0xffff));
        res.z.value = (short) (aZ - (b.z.value & 0xffff));
        return res;
    }

    public static Vec4 sub_(float a, Vec4 b) {
        return sub(new Vec4(), a, a, a, a, b);
    }

    public static Vec4 sub(float a, Vec4 b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4 sub(Vec4 res, float a, Vec4 b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4 sub(Vec4 res, float aX, float aY, float aZ, float aW, Vec4 b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        res.w = aW - b.w;
        return res;
    }

    public static Vec4b sub_(byte a, Vec4b b) {
        return sub(new Vec4b(), (int) a, a, a, a, b);
    }

    public static Vec4b sub_(int a, Vec4b b) {
        return sub(new Vec4b(), a, a, a, a, b);
    }

    public static Vec4b sub(byte a, Vec4b b) {
        return sub(b, (int) a, a, a, a, b);
    }

    public static Vec4b sub(int a, Vec4b b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4b sub(Vec4b res, byte a, Vec4b b) {
        return sub(res, (int) a, a, a, a, b);
    }

    public static Vec4b sub(Vec4b res, int a, Vec4b b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4b sub(Vec4b res, byte aX, byte aY, byte aZ, byte aW, Vec4b b) {
        return sub(res, (int) aX, aY, aZ, aW, b);
    }

    public static Vec4b sub(Vec4b res, int aX, int aY, int aZ, int aW, Vec4b b) {
        res.x = (byte) (aX - b.x);
        res.y = (byte) (aY - b.y);
        res.z = (byte) (aZ - b.z);
        res.w = (byte) (aW - b.w);
        return res;
    }

    public static Vec4d sub_(double a, Vec4d b) {
        return sub(new Vec4d(), a, a, a, a, b);
    }

    public static Vec4d sub(double a, Vec4d b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4d sub(Vec4d res, double a, Vec4d b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4d sub(Vec4d res, double aX, double aY, double aZ, double aW, Vec4d b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        res.w = aW - b.w;
        return res;
    }

    public static Vec4i sub_(int a, Vec4i b) {
        return sub(new Vec4i(), a, a, a, a, b);
    }

    public static Vec4i sub(int a, Vec4i b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4i sub(Vec4i res, int a, Vec4i b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4i sub(Vec4i res, int aX, int aY, int aZ, int aW, Vec4i b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        res.w = aW - b.w;
        return res;
    }

    public static Vec4l sub_(long a, Vec4l b) {
        return sub(new Vec4l(), a, a, a, a, b);
    }

    public static Vec4l sub(long a, Vec4l b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4l sub(Vec4l res, long a, Vec4l b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4l sub(Vec4l res, long aX, long aY, long aZ, long aW, Vec4l b) {
        res.x = aX - b.x;
        res.y = aY - b.y;
        res.z = aZ - b.z;
        res.w = aW - b.w;
        return res;
    }

    public static Vec4s sub_(short a, Vec4s b) {
        return sub(new Vec4s(), a, a, a, a, b);
    }

    public static Vec4s sub(short a, Vec4s b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4s sub(Vec4s res, short a, Vec4s b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4s sub(Vec4s res, short aX, short aY, short aZ, short aW, Vec4s b) {
        res.x = (short) (aX - b.x);
        res.y = (short) (aY - b.y);
        res.z = (short) (aZ - b.z);
        res.w = (short) (aW - b.w);
        return res;
    }

    public static Vec4ub sub_(UByte a, Vec4ub b) {
        return sub(new Vec4ub(), a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec4ub sub_(byte a, Vec4ub b) {
        return sub(new Vec4ub(), a & 0xff, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec4ub sub_(int a, Vec4ub b) {
        return sub(new Vec4ub(), a, a, a, a, b);
    }

    public static Vec4ub sub(UByte a, Vec4ub b) {
        return sub(b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec4ub sub(byte a, Vec4ub b) {
        return sub(b, a & 0xff, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec4ub sub(int a, Vec4ub b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4ub sub(Vec4ub res, UByte a, Vec4ub b) {
        return sub(res, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec4ub sub(Vec4ub res, byte a, Vec4ub b) {
        return sub(res, a & 0xff, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec4ub sub(Vec4ub res, int a, Vec4ub b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4ub sub(Vec4ub res, UByte aX, UByte aY, UByte aZ, UByte aW, Vec4ub b) {
        return sub(res, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, aW.value & 0xff, b);
    }

    public static Vec4ub sub(Vec4ub res, byte aX, byte aY, byte aZ, byte aW, Vec4ub b) {
        return sub(res, aX & 0xff, aY & 0xff, aZ & 0xff, aW & 0xff, b);
    }

    public static Vec4ub sub(Vec4ub res, int aX, int aY, int aZ, int aW, Vec4ub b) {
        res.x.value = (byte) (aX - (b.x.value & 0xff));
        res.y.value = (byte) (aY - (b.y.value & 0xff));
        res.z.value = (byte) (aZ - (b.z.value & 0xff));
        res.w.value = (byte) (aW - (b.w.value & 0xff));
        return res;
    }

    public static Vec4ui sub_(UInt a, Vec4ui b) {
        return sub(new Vec4ui(), a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ui sub_(int a, Vec4ui b) {
        return sub(new Vec4ui(), a, a, a, a, b);
    }

    public static Vec4ui sub(UInt a, Vec4ui b) {
        return sub(b, a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ui sub(int a, Vec4ui b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4ui sub(Vec4ui res, UInt a, Vec4ui b) {
        return sub(res, a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ui sub(Vec4ui res, int a, Vec4ui b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4ui sub(Vec4ui res, UInt aX, UInt aY, UInt aZ, UInt aW, Vec4ui b) {
        return sub(res, aX.value, aY.value, aZ.value, aW.value, b);
    }

    public static Vec4ui sub(Vec4ui res, int aX, int aY, int aZ, int aW, Vec4ui b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        res.z.value = aZ - b.z.value;
        res.w.value = aW - b.w.value;
        return res;
    }

    public static Vec4ul sub_(ULong a, Vec4ul b) {
        return sub(new Vec4ul(), a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ul sub_(long a, Vec4ul b) {
        return sub(new Vec4ul(), a, a, a, a, b);
    }

    public static Vec4ul sub(ULong a, Vec4ul b) {
        return sub(b, a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ul sub(long a, Vec4ul b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4ul sub(Vec4ul res, ULong a, Vec4ul b) {
        return sub(res, a.value, a.value, a.value, a.value, b);
    }

    public static Vec4ul sub(Vec4ul res, long a, Vec4ul b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4ul sub(Vec4ul res, ULong aX, ULong aY, ULong aZ, ULong aW, Vec4ul b) {
        return sub(res, aX.value, aY.value, aZ.value, aW.value, b);
    }

    public static Vec4ul sub(Vec4ul res, long aX, long aY, long aZ, long aW, Vec4ul b) {
        res.x.value = aX - b.x.value;
        res.y.value = aY - b.y.value;
        res.z.value = aZ - b.z.value;
        res.w.value = aW - b.w.value;
        return res;
    }

    public static Vec4us sub_(UShort a, Vec4us b) {
        return sub(new Vec4us(), a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us sub_(short a, Vec4us b) {
        return sub(new Vec4us(), a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us sub_(int a, Vec4us b) {
        return sub(new Vec4us(), a, a, a, a, b);
    }

    public static Vec4us sub(UShort a, Vec4us b) {
        return sub(b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us sub(short a, Vec4us b) {
        return sub(b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us sub(int a, Vec4us b) {
        return sub(b, a, a, a, a, b);
    }

    public static Vec4us sub(Vec4us res, UShort a, Vec4us b) {
        return sub(res, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us sub(Vec4us res, short a, Vec4us b) {
        return sub(res, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us sub(Vec4us res, int a, Vec4us b) {
        return sub(res, a, a, a, a, b);
    }

    public static Vec4us sub(Vec4us res, UShort aX, UShort aY, UShort aZ, UShort aW, Vec4us b) {
        return sub(res, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, aW.value & 0xffff, b);
    }

    public static Vec4us sub(Vec4us res, short aX, short aY, short aZ, short aW, Vec4us b) {
        return sub(res, aX & 0xffff, aY & 0xffff, aZ & 0xffff, aW & 0xffff, b);
    }

    public static Vec4us sub(Vec4us res, int aX, int aY, int aZ, int aW, Vec4us b) {
        res.x.value = (short) (aX - (b.x.value & 0xffff));
        res.y.value = (short) (aY - (b.y.value & 0xffff));
        res.z.value = (short) (aZ - (b.z.value & 0xffff));
        res.w.value = (short) (aW - (b.w.value & 0xffff));
        return res;
    }

    public static Vec2 mul_(float a, Vec2 b) {
        return BasicOperators.mul(new Vec2(), b, a, a);
    }

    public static Vec2 mul(float a, Vec2 b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2 mul(Vec2 res, float a, Vec2 b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2 mul(Vec2 res, float aX, float aY, Vec2 b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2b mul_(byte a, Vec2b b) {
        return BasicOperators.mul(new Vec2b(), b, (int) a, a);
    }

    public static Vec2b mul_(int a, Vec2b b) {
        return BasicOperators.mul(new Vec2b(), b, a, a);
    }

    public static Vec2b mul(byte a, Vec2b b) {
        return BasicOperators.mul(b, b, (int) a, a);
    }

    public static Vec2b mul(int a, Vec2b b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2b mul(Vec2b res, byte a, Vec2b b) {
        return BasicOperators.mul(res, b, (int) a, a);
    }

    public static Vec2b mul(Vec2b res, int a, Vec2b b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2b mul(Vec2b res, byte aX, byte aY, Vec2b b) {
        return BasicOperators.mul(res, b, (int) aX, aY);
    }

    public static Vec2b mul(Vec2b res, int aX, int aY, Vec2b b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2d mul_(double a, Vec2d b) {
        return BasicOperators.mul(new Vec2d(), b, a, a);
    }

    public static Vec2d mul(double a, Vec2d b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2d mul(Vec2d res, double a, Vec2d b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2d mul(Vec2d res, double aX, double aY, Vec2d b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2i mul_(int a, Vec2i b) {
        return BasicOperators.mul(new Vec2i(), b, a, a);
    }

    public static Vec2i mul(int a, Vec2i b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2i mul(Vec2i res, int a, Vec2i b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2i mul(Vec2i res, int aX, int aY, Vec2i b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2l mul_(long a, Vec2l b) {
        return BasicOperators.mul(new Vec2l(), b, a, a);
    }

    public static Vec2l mul(long a, Vec2l b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2l mul(Vec2l res, long a, Vec2l b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2l mul(Vec2l res, long aX, long aY, Vec2l b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2s mul_(short a, Vec2s b) {
        return BasicOperators.mul(new Vec2s(), b, a, a);
    }

    public static Vec2s mul(short a, Vec2s b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2s mul(Vec2s res, short a, Vec2s b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2s mul(Vec2s res, short aX, short aY, Vec2s b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2ub mul_(UByte a, Vec2ub b) {
        return BasicOperators.mul(new Vec2ub(), b, a.value & 0xff, a.value & 0xff);
    }

    public static Vec2ub mul_(byte a, Vec2ub b) {
        return BasicOperators.mul(new Vec2ub(), b, a & 0xff, a & 0xff);
    }

    public static Vec2ub mul_(int a, Vec2ub b) {
        return BasicOperators.mul(new Vec2ub(), b, a, a);
    }

    public static Vec2ub mul(UByte a, Vec2ub b) {
        return BasicOperators.mul(b, b, a.value & 0xff, a.value & 0xff);
    }

    public static Vec2ub mul(byte a, Vec2ub b) {
        return BasicOperators.mul(b, b, a & 0xff, a & 0xff);
    }

    public static Vec2ub mul(int a, Vec2ub b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2ub mul(Vec2ub res, UByte a, Vec2ub b) {
        return BasicOperators.mul(res, b, a.value & 0xff, a.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, byte a, Vec2ub b) {
        return BasicOperators.mul(res, b, a & 0xff, a & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, int a, Vec2ub b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2ub mul(Vec2ub res, UByte aX, UByte aY, Vec2ub b) {
        return BasicOperators.mul(res, b, aX.value & 0xff, aY.value & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, byte aX, byte aY, Vec2ub b) {
        return BasicOperators.mul(res, b, aX & 0xff, aY & 0xff);
    }

    public static Vec2ub mul(Vec2ub res, int aX, int aY, Vec2ub b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2ui mul_(UInt a, Vec2ui b) {
        return BasicOperators.mul(new Vec2ui(), b, a.value, a.value);
    }

    public static Vec2ui mul_(int a, Vec2ui b) {
        return BasicOperators.mul(new Vec2ui(), b, a, a);
    }

    public static Vec2ui mul(UInt a, Vec2ui b) {
        return BasicOperators.mul(b, b, a.value, a.value);
    }

    public static Vec2ui mul(int a, Vec2ui b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2ui mul(Vec2ui res, UInt a, Vec2ui b) {
        return BasicOperators.mul(res, b, a.value, a.value);
    }

    public static Vec2ui mul(Vec2ui res, int a, Vec2ui b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2ui mul(Vec2ui res, UInt aX, UInt aY, Vec2ui b) {
        return BasicOperators.mul(res, b, aX.value, aY.value);
    }

    public static Vec2ui mul(Vec2ui res, int aX, int aY, Vec2ui b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2ul mul_(ULong a, Vec2ul b) {
        return BasicOperators.mul(new Vec2ul(), b, a.value, a.value);
    }

    public static Vec2ul mul_(long a, Vec2ul b) {
        return BasicOperators.mul(new Vec2ul(), b, a, a);
    }

    public static Vec2ul mul(ULong a, Vec2ul b) {
        return BasicOperators.mul(b, b, a.value, a.value);
    }

    public static Vec2ul mul(long a, Vec2ul b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2ul mul(Vec2ul res, ULong a, Vec2ul b) {
        return BasicOperators.mul(res, b, a.value, a.value);
    }

    public static Vec2ul mul(Vec2ul res, long a, Vec2ul b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2ul mul(Vec2ul res, ULong aX, ULong aY, Vec2ul b) {
        return BasicOperators.mul(res, b, aX.value, aY.value);
    }

    public static Vec2ul mul(Vec2ul res, long aX, long aY, Vec2ul b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec2us mul_(UShort a, Vec2us b) {
        return BasicOperators.mul(new Vec2us(), b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us mul_(short a, Vec2us b) {
        return BasicOperators.mul(new Vec2us(), b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us mul_(int a, Vec2us b) {
        return BasicOperators.mul(new Vec2us(), b, a, a);
    }

    public static Vec2us mul(UShort a, Vec2us b) {
        return BasicOperators.mul(b, b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us mul(short a, Vec2us b) {
        return BasicOperators.mul(b, b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us mul(int a, Vec2us b) {
        return BasicOperators.mul(b, b, a, a);
    }

    public static Vec2us mul(Vec2us res, UShort a, Vec2us b) {
        return BasicOperators.mul(res, b, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, short a, Vec2us b) {
        return BasicOperators.mul(res, b, a & 0xffff, a & 0xffff);
    }

    public static Vec2us mul(Vec2us res, int a, Vec2us b) {
        return BasicOperators.mul(res, b, a, a);
    }

    public static Vec2us mul(Vec2us res, UShort aX, UShort aY, Vec2us b) {
        return BasicOperators.mul(res, b, aX.value & 0xffff, aY.value & 0xffff);
    }

    public static Vec2us mul(Vec2us res, short aX, short aY, Vec2us b) {
        return BasicOperators.mul(res, b, aX & 0xffff, aY & 0xffff);
    }

    public static Vec2us mul(Vec2us res, int aX, int aY, Vec2us b) {
        return BasicOperators.mul(res, b, aX, aY);
    }

    public static Vec3 mul_(float a, Vec3 b) {
        return BasicOperators.mul(new Vec3(), b, a, a, a);
    }

    public static Vec3 mul(float a, Vec3 b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3 mul(Vec3 res, float a, Vec3 b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3 mul(Vec3 res, float aX, float aY, float aZ, Vec3 b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3b mul_(byte a, Vec3b b) {
        return BasicOperators.mul(new Vec3b(), b, (int) a, a, a);
    }

    public static Vec3b mul_(int a, Vec3b b) {
        return BasicOperators.mul(new Vec3b(), b, a, a, a);
    }

    public static Vec3b mul(byte a, Vec3b b) {
        return BasicOperators.mul(b, b, (int) a, a, a);
    }

    public static Vec3b mul(int a, Vec3b b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3b mul(Vec3b res, byte a, Vec3b b) {
        return BasicOperators.mul(res, b, (int) a, a, a);
    }

    public static Vec3b mul(Vec3b res, int a, Vec3b b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3b mul(Vec3b res, byte aX, byte aY, byte aZ, Vec3b b) {
        return BasicOperators.mul(res, b, (int) aX, aY, aZ);
    }

    public static Vec3b mul(Vec3b res, int aX, int aY, int aZ, Vec3b b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3d mul_(double a, Vec3d b) {
        return BasicOperators.mul(new Vec3d(), b, a, a, a);
    }

    public static Vec3d mul(double a, Vec3d b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3d mul(Vec3d res, double a, Vec3d b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3d mul(Vec3d res, double aX, double aY, double aZ, Vec3d b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3i mul_(int a, Vec3i b) {
        return BasicOperators.mul(new Vec3i(), b, a, a, a);
    }

    public static Vec3i mul(int a, Vec3i b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3i mul(Vec3i res, int a, Vec3i b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3i mul(Vec3i res, int aX, int aY, int aZ, Vec3i b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3l mul_(long a, Vec3l b) {
        return BasicOperators.mul(new Vec3l(), b, a, a, a);
    }

    public static Vec3l mul(long a, Vec3l b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3l mul(Vec3l res, long a, Vec3l b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3l mul(Vec3l res, long aX, long aY, long aZ, Vec3l b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3s mul_(short a, Vec3s b) {
        return BasicOperators.mul(new Vec3s(), b, a, a, a);
    }

    public static Vec3s mul(short a, Vec3s b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3s mul(Vec3s res, short a, Vec3s b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3s mul(Vec3s res, short aX, short aY, short aZ, Vec3s b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3ub mul_(UByte a, Vec3ub b) {
        return BasicOperators.mul(new Vec3ub(), b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub mul_(byte a, Vec3ub b) {
        return BasicOperators.mul(new Vec3ub(), b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub mul_(int a, Vec3ub b) {
        return BasicOperators.mul(new Vec3ub(), b, a, a, a);
    }

    public static Vec3ub mul(UByte a, Vec3ub b) {
        return BasicOperators.mul(b, b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub mul(byte a, Vec3ub b) {
        return BasicOperators.mul(b, b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub mul(int a, Vec3ub b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3ub mul(Vec3ub res, UByte a, Vec3ub b) {
        return BasicOperators.mul(res, b, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, byte a, Vec3ub b) {
        return BasicOperators.mul(res, b, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, int a, Vec3ub b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3ub mul(Vec3ub res, UByte aX, UByte aY, UByte aZ, Vec3ub b) {
        return BasicOperators.mul(res, b, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, byte aX, byte aY, byte aZ, Vec3ub b) {
        return BasicOperators.mul(res, b, aX & 0xff, aY & 0xff, aZ & 0xff);
    }

    public static Vec3ub mul(Vec3ub res, int aX, int aY, int aZ, Vec3ub b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3ui mul_(UInt a, Vec3ui b) {
        return BasicOperators.mul(new Vec3ui(), b, a.value, a.value, a.value);
    }

    public static Vec3ui mul_(int a, Vec3ui b) {
        return BasicOperators.mul(new Vec3ui(), b, a, a, a);
    }

    public static Vec3ui mul(UInt a, Vec3ui b) {
        return BasicOperators.mul(b, b, a.value, a.value, a.value);
    }

    public static Vec3ui mul(int a, Vec3ui b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3ui mul(Vec3ui res, UInt a, Vec3ui b) {
        return BasicOperators.mul(res, b, a.value, a.value, a.value);
    }

    public static Vec3ui mul(Vec3ui res, int a, Vec3ui b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3ui mul(Vec3ui res, UInt aX, UInt aY, UInt aZ, Vec3ui b) {
        return BasicOperators.mul(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ui mul(Vec3ui res, int aX, int aY, int aZ, Vec3ui b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3ul mul_(ULong a, Vec3ul b) {
        return BasicOperators.mul(new Vec3ul(), b, a.value, a.value, a.value);
    }

    public static Vec3ul mul_(long a, Vec3ul b) {
        return BasicOperators.mul(new Vec3ul(), b, a, a, a);
    }

    public static Vec3ul mul(ULong a, Vec3ul b) {
        return BasicOperators.mul(b, b, a.value, a.value, a.value);
    }

    public static Vec3ul mul(long a, Vec3ul b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3ul mul(Vec3ul res, ULong a, Vec3ul b) {
        return BasicOperators.mul(res, b, a.value, a.value, a.value);
    }

    public static Vec3ul mul(Vec3ul res, long a, Vec3ul b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3ul mul(Vec3ul res, ULong aX, ULong aY, ULong aZ, Vec3ul b) {
        return BasicOperators.mul(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ul mul(Vec3ul res, long aX, long aY, long aZ, Vec3ul b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec3us mul_(UShort a, Vec3us b) {
        return BasicOperators.mul(new Vec3us(), b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us mul_(short a, Vec3us b) {
        return BasicOperators.mul(new Vec3us(), b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us mul_(int a, Vec3us b) {
        return BasicOperators.mul(new Vec3us(), b, a, a, a);
    }

    public static Vec3us mul(UShort a, Vec3us b) {
        return BasicOperators.mul(b, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us mul(short a, Vec3us b) {
        return BasicOperators.mul(b, b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us mul(int a, Vec3us b) {
        return BasicOperators.mul(b, b, a, a, a);
    }

    public static Vec3us mul(Vec3us res, UShort a, Vec3us b) {
        return BasicOperators.mul(res, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, short a, Vec3us b) {
        return BasicOperators.mul(res, b, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec3us mul(Vec3us res, int a, Vec3us b) {
        return BasicOperators.mul(res, b, a, a, a);
    }

    public static Vec3us mul(Vec3us res, UShort aX, UShort aY, UShort aZ, Vec3us b) {
        return BasicOperators.mul(res, b, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff);
    }

    public static Vec3us mul(Vec3us res, short aX, short aY, short aZ, Vec3us b) {
        return BasicOperators.mul(res, b, aX & 0xffff, aY & 0xffff, aZ & 0xffff);
    }

    public static Vec3us mul(Vec3us res, int aX, int aY, int aZ, Vec3us b) {
        return BasicOperators.mul(res, b, aX, aY, aZ);
    }

    public static Vec4 mul_(float a, Vec4 b) {
        return BasicOperators.mul(new Vec4(), b, a, a, a, a);
    }

    public static Vec4 mul(float a, Vec4 b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4 mul(Vec4 res, float a, Vec4 b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4 mul(Vec4 res, float aX, float aY, float aZ, float aW, Vec4 b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4b mul_(byte a, Vec4b b) {
        return BasicOperators.mul(new Vec4b(), b, (int) a, a, a, a);
    }

    public static Vec4b mul_(int a, Vec4b b) {
        return BasicOperators.mul(new Vec4b(), b, a, a, a, a);
    }

    public static Vec4b mul(byte a, Vec4b b) {
        return BasicOperators.mul(b, b, (int) a, a, a, a);
    }

    public static Vec4b mul(int a, Vec4b b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4b mul(Vec4b res, byte a, Vec4b b) {
        return BasicOperators.mul(res, b, (int) a, a, a, a);
    }

    public static Vec4b mul(Vec4b res, int a, Vec4b b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4b mul(Vec4b res, byte aX, byte aY, byte aZ, byte aW, Vec4b b) {
        return BasicOperators.mul(res, b, (int) aX, aY, aZ, aW);
    }

    public static Vec4b mul(Vec4b res, int aX, int aY, int aZ, int aW, Vec4b b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4d mul_(double a, Vec4d b) {
        return BasicOperators.mul(new Vec4d(), b, a, a, a, a);
    }

    public static Vec4d mul(double a, Vec4d b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4d mul(Vec4d res, double a, Vec4d b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4d mul(Vec4d res, double aX, double aY, double aZ, double aW, Vec4d b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4i mul_(int a, Vec4i b) {
        return BasicOperators.mul(new Vec4i(), b, a, a, a, a);
    }

    public static Vec4i mul(int a, Vec4i b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4i mul(Vec4i res, int a, Vec4i b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4i mul(Vec4i res, int aX, int aY, int aZ, int aW, Vec4i b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4l mul_(long a, Vec4l b) {
        return BasicOperators.mul(new Vec4l(), b, a, a, a, a);
    }

    public static Vec4l mul(long a, Vec4l b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4l mul(Vec4l res, long a, Vec4l b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4l mul(Vec4l res, long aX, long aY, long aZ, long aW, Vec4l b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4s mul_(short a, Vec4s b) {
        return BasicOperators.mul(new Vec4s(), b, a, a, a, a);
    }

    public static Vec4s mul(short a, Vec4s b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4s mul(Vec4s res, short a, Vec4s b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4s mul(Vec4s res, short aX, short aY, short aZ, short aW, Vec4s b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ub mul_(UByte a, Vec4ub b) {
        return BasicOperators.mul(new Vec4ub(), b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub mul_(byte a, Vec4ub b) {
        return BasicOperators.mul(new Vec4ub(), b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub mul_(int a, Vec4ub b) {
        return BasicOperators.mul(new Vec4ub(), b, a, a, a, a);
    }

    public static Vec4ub mul(UByte a, Vec4ub b) {
        return BasicOperators.mul(b, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub mul(byte a, Vec4ub b) {
        return BasicOperators.mul(b, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub mul(int a, Vec4ub b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4ub mul(Vec4ub res, UByte a, Vec4ub b) {
        return BasicOperators.mul(res, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, byte a, Vec4ub b) {
        return BasicOperators.mul(res, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, int a, Vec4ub b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4ub mul(Vec4ub res, UByte aX, UByte aY, UByte aZ, UByte aW, Vec4ub b) {
        return BasicOperators.mul(res, b, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, aW.value & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, byte aX, byte aY, byte aZ, byte aW, Vec4ub b) {
        return BasicOperators.mul(res, b, aX & 0xff, aY & 0xff, aZ & 0xff, aW & 0xff);
    }

    public static Vec4ub mul(Vec4ub res, int aX, int aY, int aZ, int aW, Vec4ub b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ui mul_(UInt a, Vec4ui b) {
        return BasicOperators.mul(new Vec4ui(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui mul_(int a, Vec4ui b) {
        return BasicOperators.mul(new Vec4ui(), b, a, a, a, a);
    }

    public static Vec4ui mul(UInt a, Vec4ui b) {
        return BasicOperators.mul(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui mul(int a, Vec4ui b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4ui mul(Vec4ui res, UInt a, Vec4ui b) {
        return BasicOperators.mul(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui mul(Vec4ui res, int a, Vec4ui b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4ui mul(Vec4ui res, UInt aX, UInt aY, UInt aZ, UInt aW, Vec4ui b) {
        return BasicOperators.mul(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ui mul(Vec4ui res, int aX, int aY, int aZ, int aW, Vec4ui b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ul mul_(ULong a, Vec4ul b) {
        return BasicOperators.mul(new Vec4ul(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul mul_(long a, Vec4ul b) {
        return BasicOperators.mul(new Vec4ul(), b, a, a, a, a);
    }

    public static Vec4ul mul(ULong a, Vec4ul b) {
        return BasicOperators.mul(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul mul(long a, Vec4ul b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4ul mul(Vec4ul res, ULong a, Vec4ul b) {
        return BasicOperators.mul(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul mul(Vec4ul res, long a, Vec4ul b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4ul mul(Vec4ul res, ULong aX, ULong aY, ULong aZ, ULong aW, Vec4ul b) {
        return BasicOperators.mul(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ul mul(Vec4ul res, long aX, long aY, long aZ, long aW, Vec4ul b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec4us mul_(UShort a, Vec4us b) {
        return BasicOperators.mul(new Vec4us(), b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us mul_(short a, Vec4us b) {
        return BasicOperators.mul(new Vec4us(), b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us mul_(int a, Vec4us b) {
        return BasicOperators.mul(new Vec4us(), b, a, a, a, a);
    }

    public static Vec4us mul(UShort a, Vec4us b) {
        return BasicOperators.mul(b, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us mul(short a, Vec4us b) {
        return BasicOperators.mul(b, b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us mul(int a, Vec4us b) {
        return BasicOperators.mul(b, b, a, a, a, a);
    }

    public static Vec4us mul(Vec4us res, UShort a, Vec4us b) {
        return BasicOperators.mul(res, b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, short a, Vec4us b) {
        return BasicOperators.mul(res, b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff);
    }

    public static Vec4us mul(Vec4us res, int a, Vec4us b) {
        return BasicOperators.mul(res, b, a, a, a, a);
    }

    public static Vec4us mul(Vec4us res, UShort aX, UShort aY, UShort aZ, UShort aW, Vec4us b) {
        return BasicOperators.mul(res, b, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, aW.value & 0xffff);
    }

    public static Vec4us mul(Vec4us res, short aX, short aY, short aZ, short aW, Vec4us b) {
        return BasicOperators.mul(res, b, aX & 0xffff, aY & 0xffff, aZ & 0xffff, aW & 0xffff);
    }

    public static Vec4us mul(Vec4us res, int aX, int aY, int aZ, int aW, Vec4us b) {
        return BasicOperators.mul(res, b, aX, aY, aZ, aW);
    }

    public static Vec2 div_(float a, Vec2 b) {
        return div(new Vec2(), a, a, b);
    }

    public static Vec2 div(float a, Vec2 b) {
        return div(b, a, a, b);
    }

    public static Vec2 div(Vec2 res, float a, Vec2 b) {
        return div(res, a, a, b);
    }

    public static Vec2 div(Vec2 res, float aX, float aY, Vec2 b) {
        res.x = aX / b.x;
        res.y = aY / b.y;
        return res;
    }

    public static Vec2b div_(byte a, Vec2b b) {
        return div(new Vec2b(), (int) a, a, b);
    }

    public static Vec2b div_(int a, Vec2b b) {
        return div(new Vec2b(), a, a, b);
    }

    public static Vec2b div(byte a, Vec2b b) {
        return div(b, (int) a, a, b);
    }

    public static Vec2b div(int a, Vec2b b) {
        return div(b, a, a, b);
    }

    public static Vec2b div(Vec2b res, byte a, Vec2b b) {
        return div(res, (int) a, a, b);
    }

    public static Vec2b div(Vec2b res, int a, Vec2b b) {
        return div(res, a, a, b);
    }

    public static Vec2b div(Vec2b res, byte aX, byte aY, Vec2b b) {
        return div(res, (int) aX, aY, b);
    }

    public static Vec2b div(Vec2b res, int aX, int aY, Vec2b b) {
        res.x = (byte) (aX / b.x);
        res.y = (byte) (aY / b.y);
        return res;
    }

    public static Vec2d div_(double a, Vec2d b) {
        return div(new Vec2d(), a, a, b);
    }

    public static Vec2d div(double a, Vec2d b) {
        return div(b, a, a, b);
    }

    public static Vec2d div(Vec2d res, double a, Vec2d b) {
        return div(res, a, a, b);
    }

    public static Vec2d div(Vec2d res, double aX, double aY, Vec2d b) {
        res.x = aX / b.x;
        res.y = aY / b.y;
        return res;
    }

    public static Vec2i div_(int a, Vec2i b) {
        return div(new Vec2i(), a, a, b);
    }

    public static Vec2i div(int a, Vec2i b) {
        return div(b, a, a, b);
    }

    public static Vec2i div(Vec2i res, int a, Vec2i b) {
        return div(res, a, a, b);
    }

    public static Vec2i div(Vec2i res, int aX, int aY, Vec2i b) {
        res.x = aX / b.x;
        res.y = aY / b.y;
        return res;
    }

    public static Vec2l div_(long a, Vec2l b) {
        return div(new Vec2l(), a, a, b);
    }

    public static Vec2l div(long a, Vec2l b) {
        return div(b, a, a, b);
    }

    public static Vec2l div(Vec2l res, long a, Vec2l b) {
        return div(res, a, a, b);
    }

    public static Vec2l div(Vec2l res, long aX, long aY, Vec2l b) {
        res.x = aX / b.x;
        res.y = aY / b.y;
        return res;
    }

    public static Vec2s div_(short a, Vec2s b) {
        return div(new Vec2s(), a, a, b);
    }

    public static Vec2s div(short a, Vec2s b) {
        return div(b, a, a, b);
    }

    public static Vec2s div(Vec2s res, short a, Vec2s b) {
        return div(res, a, a, b);
    }

    public static Vec2s div(Vec2s res, short aX, short aY, Vec2s b) {
        res.x = (short) (aX / b.x);
        res.y = (short) (aY / b.y);
        return res;
    }

    public static Vec2ub div_(UByte a, Vec2ub b) {
        return div(new Vec2ub(), a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub div_(byte a, Vec2ub b) {
        return div(new Vec2ub(), a & 0xff, a & 0xff, b);
    }

    public static Vec2ub div_(int a, Vec2ub b) {
        return div(new Vec2ub(), a, a, b);
    }

    public static Vec2ub div(UByte a, Vec2ub b) {
        return div(b, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub div(byte a, Vec2ub b) {
        return div(b, a & 0xff, a & 0xff, b);
    }

    public static Vec2ub div(int a, Vec2ub b) {
        return div(b, a, a, b);
    }

    public static Vec2ub div(Vec2ub res, UByte a, Vec2ub b) {
        return div(res, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec2ub div(Vec2ub res, byte a, Vec2ub b) {
        return div(res, a & 0xff, a & 0xff, b);
    }

    public static Vec2ub div(Vec2ub res, int a, Vec2ub b) {
        return div(res, a, a, b);
    }

    public static Vec2ub div(Vec2ub res, UByte aX, UByte aY, Vec2ub b) {
        return div(res, aX.value & 0xff, aY.value & 0xff, b);
    }

    public static Vec2ub div(Vec2ub res, byte aX, byte aY, Vec2ub b) {
        return div(res, aX & 0xff, aY & 0xff, b);
    }

    public static Vec2ub div(Vec2ub res, int aX, int aY, Vec2ub b) {
        res.x.value = (byte) Integer.divideUnsigned(aX, b.x.value & 0xff);
        res.y.value = (byte) Integer.divideUnsigned(aY, b.y.value & 0xff);
        return res;
    }

    public static Vec2ui div_(UInt a, Vec2ui b) {
        return div(new Vec2ui(), a.value, a.value, b);
    }

    public static Vec2ui div_(int a, Vec2ui b) {
        return div(new Vec2ui(), a, a, b);
    }

    public static Vec2ui div(UInt a, Vec2ui b) {
        return div(b, a.value, a.value, b);
    }

    public static Vec2ui div(int a, Vec2ui b) {
        return div(b, a, a, b);
    }

    public static Vec2ui div(Vec2ui res, UInt a, Vec2ui b) {
        return div(res, a.value, a.value, b);
    }

    public static Vec2ui div(Vec2ui res, int a, Vec2ui b) {
        return div(res, a, a, b);
    }

    public static Vec2ui div(Vec2ui res, UInt aX, UInt aY, Vec2ui b) {
        return div(res, aX.value, aY.value, b);
    }

    public static Vec2ui div(Vec2ui res, int aX, int aY, Vec2ui b) {
        res.x.value = Integer.divideUnsigned(aX, b.x.value);
        res.y.value = Integer.divideUnsigned(aY, b.y.value);
        return res;
    }

    public static Vec2ul div_(ULong a, Vec2ul b) {
        return div(new Vec2ul(), a.value, a.value, b);
    }

    public static Vec2ul div_(long a, Vec2ul b) {
        return div(new Vec2ul(), a, a, b);
    }

    public static Vec2ul div(ULong a, Vec2ul b) {
        return div(b, a.value, a.value, b);
    }

    public static Vec2ul div(long a, Vec2ul b) {
        return div(b, a, a, b);
    }

    public static Vec2ul div(Vec2ul res, ULong a, Vec2ul b) {
        return div(res, a.value, a.value, b);
    }

    public static Vec2ul div(Vec2ul res, long a, Vec2ul b) {
        return div(res, a, a, b);
    }

    public static Vec2ul div(Vec2ul res, ULong aX, ULong aY, Vec2ul b) {
        return div(res, aX.value, aY.value, b);
    }

    public static Vec2ul div(Vec2ul res, long aX, long aY, Vec2ul b) {
        res.x.value = Long.divideUnsigned(aX, b.x.value);
        res.y.value = Long.divideUnsigned(aY, b.y.value);
        return res;
    }

    public static Vec2us div_(UShort a, Vec2us b) {
        return div(new Vec2us(), a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us div_(short a, Vec2us b) {
        return div(new Vec2us(), a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us div_(int a, Vec2us b) {
        return div(new Vec2us(), a, a, b);
    }

    public static Vec2us div(UShort a, Vec2us b) {
        return div(b, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us div(short a, Vec2us b) {
        return div(b, a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us div(int a, Vec2us b) {
        return div(b, a, a, b);
    }

    public static Vec2us div(Vec2us res, UShort a, Vec2us b) {
        return div(res, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec2us div(Vec2us res, short a, Vec2us b) {
        return div(res, a & 0xffff, a & 0xffff, b);
    }

    public static Vec2us div(Vec2us res, int a, Vec2us b) {
        return div(res, a, a, b);
    }

    public static Vec2us div(Vec2us res, UShort aX, UShort aY, Vec2us b) {
        return div(res, aX.value & 0xffff, aY.value & 0xffff, b);
    }

    public static Vec2us div(Vec2us res, short aX, short aY, Vec2us b) {
        return div(res, aX & 0xffff, aY & 0xffff, b);
    }

    public static Vec2us div(Vec2us res, int aX, int aY, Vec2us b) {
        res.x.value = (short) Integer.divideUnsigned(aX, b.x.value & 0xffff);
        res.y.value = (short) Integer.divideUnsigned(aY, b.y.value & 0xffff);
        return res;
    }

    public static Vec3 div_(float a, Vec3 b) {
        return BasicOperators.div(new Vec3(), b, a, a, a);
    }

    public static Vec3 div(float a, Vec3 b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3 div(Vec3 res, float a, Vec3 b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3 div(Vec3 res, float aX, float aY, float aZ, Vec3 b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3b div_(byte a, Vec3b b) {
        return BasicOperators.div(new Vec3b(), b, (int) a, a, a);
    }

    public static Vec3b div_(int a, Vec3b b) {
        return BasicOperators.div(new Vec3b(), b, a, a, a);
    }

    public static Vec3b div(byte a, Vec3b b) {
        return BasicOperators.div(b, b, (int) a, a, a);
    }

    public static Vec3b div(int a, Vec3b b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3b div(Vec3b res, byte a, Vec3b b) {
        return BasicOperators.div(res, b, (int) a, a, a);
    }

    public static Vec3b div(Vec3b res, int a, Vec3b b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3b div(Vec3b res, byte aX, byte aY, byte aZ, Vec3b b) {
        return BasicOperators.div(res, b, (int) aX, aY, aZ);
    }

    public static Vec3b div(Vec3b res, int aX, int aY, int aZ, Vec3b b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3d div_(double a, Vec3d b) {
        return BasicOperators.div(new Vec3d(), b, a, a, a);
    }

    public static Vec3d div(double a, Vec3d b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3d div(Vec3d res, double a, Vec3d b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3d div(Vec3d res, double aX, double aY, double aZ, Vec3d b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3i div_(int a, Vec3i b) {
        return BasicOperators.div(new Vec3i(), b, a, a, a);
    }

    public static Vec3i div(int a, Vec3i b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3i div(Vec3i res, int a, Vec3i b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3i div(Vec3i res, int aX, int aY, int aZ, Vec3i b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3l div_(long a, Vec3l b) {
        return BasicOperators.div(new Vec3l(), b, a, a, a);
    }

    public static Vec3l div(long a, Vec3l b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3l div(Vec3l res, long a, Vec3l b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3l div(Vec3l res, long aX, long aY, long aZ, Vec3l b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3s div_(short a, Vec3s b) {
        return BasicOperators.div(new Vec3s(), b, a, a, a);
    }

    public static Vec3s div(short a, Vec3s b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3s div(Vec3s res, short a, Vec3s b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3s div(Vec3s res, short aX, short aY, short aZ, Vec3s b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3ub div_(UByte a, Vec3ub b) {
        return div(new Vec3ub(), a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub div_(byte a, Vec3ub b) {
        return div(new Vec3ub(), a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub div_(int a, Vec3ub b) {
        return div(new Vec3ub(), a, a, a, b);
    }

    public static Vec3ub div(UByte a, Vec3ub b) {
        return div(b, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub div(byte a, Vec3ub b) {
        return div(b, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub div(int a, Vec3ub b) {
        return div(b, a, a, a, b);
    }

    public static Vec3ub div(Vec3ub res, UByte a, Vec3ub b) {
        return div(res, a.value & 0xff, a.value & 0xff, a.value & 0xff, b);
    }

    public static Vec3ub div(Vec3ub res, byte a, Vec3ub b) {
        return div(res, a & 0xff, a & 0xff, a & 0xff, b);
    }

    public static Vec3ub div(Vec3ub res, int a, Vec3ub b) {
        return div(res, a, a, a, b);
    }

    public static Vec3ub div(Vec3ub res, UByte aX, UByte aY, UByte aZ, Vec3ub b) {
        return div(res, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, b);
    }

    public static Vec3ub div(Vec3ub res, byte aX, byte aY, byte aZ, Vec3ub b) {
        return div(res, aX & 0xff, aY & 0xff, aZ & 0xff, b);
    }

    public static Vec3ub div(Vec3ub res, int aX, int aY, int aZ, Vec3ub b) {
        res.x.value = (byte) Integer.divideUnsigned(aX, b.x.value & 0xff);
        res.y.value = (byte) Integer.divideUnsigned(aY, b.y.value & 0xff);
        res.z.value = (byte) Integer.divideUnsigned(aZ, b.z.value & 0xff);
        return res;
    }

    public static Vec3ui div_(UInt a, Vec3ui b) {
        return BasicOperators.div(new Vec3ui(), b, a.value, a.value, a.value);
    }

    public static Vec3ui div_(int a, Vec3ui b) {
        return BasicOperators.div(new Vec3ui(), b, a, a, a);
    }

    public static Vec3ui div(UInt a, Vec3ui b) {
        return BasicOperators.div(b, b, a.value, a.value, a.value);
    }

    public static Vec3ui div(int a, Vec3ui b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3ui div(Vec3ui res, UInt a, Vec3ui b) {
        return BasicOperators.div(res, b, a.value, a.value, a.value);
    }

    public static Vec3ui div(Vec3ui res, int a, Vec3ui b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3ui div(Vec3ui res, UInt aX, UInt aY, UInt aZ, Vec3ui b) {
        return BasicOperators.div(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ui div(Vec3ui res, int aX, int aY, int aZ, Vec3ui b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3ul div_(ULong a, Vec3ul b) {
        return BasicOperators.div(new Vec3ul(), b, a.value, a.value, a.value);
    }

    public static Vec3ul div_(long a, Vec3ul b) {
        return BasicOperators.div(new Vec3ul(), b, a, a, a);
    }

    public static Vec3ul div(ULong a, Vec3ul b) {
        return BasicOperators.div(b, b, a.value, a.value, a.value);
    }

    public static Vec3ul div(long a, Vec3ul b) {
        return BasicOperators.div(b, b, a, a, a);
    }

    public static Vec3ul div(Vec3ul res, ULong a, Vec3ul b) {
        return BasicOperators.div(res, b, a.value, a.value, a.value);
    }

    public static Vec3ul div(Vec3ul res, long a, Vec3ul b) {
        return BasicOperators.div(res, b, a, a, a);
    }

    public static Vec3ul div(Vec3ul res, ULong aX, ULong aY, ULong aZ, Vec3ul b) {
        return BasicOperators.div(res, b, aX.value, aY.value, aZ.value);
    }

    public static Vec3ul div(Vec3ul res, long aX, long aY, long aZ, Vec3ul b) {
        return BasicOperators.div(res, b, aX, aY, aZ);
    }

    public static Vec3us div_(UShort a, Vec3us b) {
        return div(new Vec3us(), a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us div_(short a, Vec3us b) {
        return div(new Vec3us(), a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us div_(int a, Vec3us b) {
        return div(new Vec3us(), a, a, a, b);
    }

    public static Vec3us div(UShort a, Vec3us b) {
        return div(b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us div(short a, Vec3us b) {
        return div(b, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us div(int a, Vec3us b) {
        return div(b, a, a, a, b);
    }

    public static Vec3us div(Vec3us res, UShort a, Vec3us b) {
        return div(res, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec3us div(Vec3us res, short a, Vec3us b) {
        return div(res, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec3us div(Vec3us res, int a, Vec3us b) {
        return div(res, a, a, a, b);
    }

    public static Vec3us div(Vec3us res, UShort aX, UShort aY, UShort aZ, Vec3us b) {
        return div(res, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, b);
    }

    public static Vec3us div(Vec3us res, short aX, short aY, short aZ, Vec3us b) {
        return div(res, aX & 0xffff, aY & 0xffff, aZ & 0xffff, b);
    }

    public static Vec3us div(Vec3us res, int aX, int aY, int aZ, Vec3us b) {
        res.x.value = (short) Integer.divideUnsigned(aX, b.x.value & 0xffff);
        res.y.value = (short) Integer.divideUnsigned(aY, b.y.value & 0xffff);
        res.z.value = (short) Integer.divideUnsigned(aZ, b.z.value & 0xffff);
        return res;
    }

    public static Vec4 div_(float a, Vec4 b) {
        return BasicOperators.div(new Vec4(), b, a, a, a, a);
    }

    public static Vec4 div(float a, Vec4 b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4 div(Vec4 res, float a, Vec4 b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4 div(Vec4 res, float aX, float aY, float aZ, float aW, Vec4 b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4b div_(byte a, Vec4b b) {
        return BasicOperators.div(new Vec4b(), b, (int) a, a, a, a);
    }

    public static Vec4b div_(int a, Vec4b b) {
        return BasicOperators.div(new Vec4b(), b, a, a, a, a);
    }

    public static Vec4b div(byte a, Vec4b b) {
        return BasicOperators.div(b, b, (int) a, a, a, a);
    }

    public static Vec4b div(int a, Vec4b b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4b div(Vec4b res, byte a, Vec4b b) {
        return BasicOperators.div(res, b, (int) a, a, a, a);
    }

    public static Vec4b div(Vec4b res, int a, Vec4b b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4b div(Vec4b res, byte aX, byte aY, byte aZ, byte aW, Vec4b b) {
        return BasicOperators.div(res, b, (int) aX, aY, aZ, aW);
    }

    public static Vec4b div(Vec4b res, int aX, int aY, int aZ, int aW, Vec4b b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4d div_(double a, Vec4d b) {
        return BasicOperators.div(new Vec4d(), b, a, a, a, a);
    }

    public static Vec4d div(double a, Vec4d b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4d div(Vec4d res, double a, Vec4d b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4d div(Vec4d res, double aX, double aY, double aZ, double aW, Vec4d b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4i div_(int a, Vec4i b) {
        return BasicOperators.div(new Vec4i(), b, a, a, a, a);
    }

    public static Vec4i div(int a, Vec4i b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4i div(Vec4i res, int a, Vec4i b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4i div(Vec4i res, int aX, int aY, int aZ, int aW, Vec4i b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4l div_(long a, Vec4l b) {
        return BasicOperators.div(new Vec4l(), b, a, a, a, a);
    }

    public static Vec4l div(long a, Vec4l b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4l div(Vec4l res, long a, Vec4l b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4l div(Vec4l res, long aX, long aY, long aZ, long aW, Vec4l b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4s div_(short a, Vec4s b) {
        return BasicOperators.div(new Vec4s(), b, a, a, a, a);
    }

    public static Vec4s div(short a, Vec4s b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4s div(Vec4s res, short a, Vec4s b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4s div(Vec4s res, short aX, short aY, short aZ, short aW, Vec4s b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ub div_(UByte a, Vec4ub b) {
        return div(new Vec4ub(), b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub div_(byte a, Vec4ub b) {
        return div(new Vec4ub(), b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub div_(int a, Vec4ub b) {
        return div(new Vec4ub(), b, a, a, a, a);
    }

    public static Vec4ub div(UByte a, Vec4ub b) {
        return div(b, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub div(byte a, Vec4ub b) {
        return div(b, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub div(int a, Vec4ub b) {
        return div(b, b, a, a, a, a);
    }

    public static Vec4ub div(Vec4ub res, UByte a, Vec4ub b) {
        return div(res, b, a.value & 0xff, a.value & 0xff, a.value & 0xff, a.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, byte a, Vec4ub b) {
        return div(res, b, a & 0xff, a & 0xff, a & 0xff, a & 0xff);
    }

    public static Vec4ub div(Vec4ub res, int a, Vec4ub b) {
        return div(res, b, a, a, a, a);
    }

    public static Vec4ub div(Vec4ub res, UByte aX, UByte aY, UByte aZ, UByte aW, Vec4ub b) {
        return div(res, b, aX.value & 0xff, aY.value & 0xff, aZ.value & 0xff, aW.value & 0xff);
    }

    public static Vec4ub div(Vec4ub res, byte aX, byte aY, byte aZ, byte aW, Vec4ub b) {
        return div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ub div(Vec4ub res, int aX, int aY, int aZ, int aW, Vec4ub b) {
        res.x.value = (byte) Integer.divideUnsigned(aX, b.x.value & 0xff);
        res.y.value = (byte) Integer.divideUnsigned(aY, b.y.value & 0xff);
        res.z.value = (byte) Integer.divideUnsigned(aZ, b.z.value & 0xff);
        res.w.value = (byte) Integer.divideUnsigned(aW, b.w.value & 0xff);
        return res;
    }

    public static Vec4ui div_(UInt a, Vec4ui b) {
        return BasicOperators.div(new Vec4ui(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui div_(int a, Vec4ui b) {
        return BasicOperators.div(new Vec4ui(), b, a, a, a, a);
    }

    public static Vec4ui div(UInt a, Vec4ui b) {
        return BasicOperators.div(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui div(int a, Vec4ui b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4ui div(Vec4ui res, UInt a, Vec4ui b) {
        return BasicOperators.div(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ui div(Vec4ui res, int a, Vec4ui b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4ui div(Vec4ui res, UInt aX, UInt aY, UInt aZ, UInt aW, Vec4ui b) {
        return BasicOperators.div(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ui div(Vec4ui res, int aX, int aY, int aZ, int aW, Vec4ui b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4ul div_(ULong a, Vec4ul b) {
        return BasicOperators.div(new Vec4ul(), b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul div_(long a, Vec4ul b) {
        return BasicOperators.div(new Vec4ul(), b, a, a, a, a);
    }

    public static Vec4ul div(ULong a, Vec4ul b) {
        return BasicOperators.div(b, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul div(long a, Vec4ul b) {
        return BasicOperators.div(b, b, a, a, a, a);
    }

    public static Vec4ul div(Vec4ul res, ULong a, Vec4ul b) {
        return BasicOperators.div(res, b, a.value, a.value, a.value, a.value);
    }

    public static Vec4ul div(Vec4ul res, long a, Vec4ul b) {
        return BasicOperators.div(res, b, a, a, a, a);
    }

    public static Vec4ul div(Vec4ul res, ULong aX, ULong aY, ULong aZ, ULong aW, Vec4ul b) {
        return BasicOperators.div(res, b, aX.value, aY.value, aZ.value, aW.value);
    }

    public static Vec4ul div(Vec4ul res, long aX, long aY, long aZ, long aW, Vec4ul b) {
        return BasicOperators.div(res, b, aX, aY, aZ, aW);
    }

    public static Vec4us div_(UShort a, Vec4us b) {
        return div(new Vec4us(), a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us div_(short a, Vec4us b) {
        return div(new Vec4us(), a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us div_(int a, Vec4us b) {
        return div(new Vec4us(), a, a, a, a, b);
    }

    public static Vec4us div(UShort a, Vec4us b) {
        return div(b, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us div(short a, Vec4us b) {
        return div(b, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us div(int a, Vec4us b) {
        return div(b, a, a, a, a, b);
    }

    public static Vec4us div(Vec4us res, UShort a, Vec4us b) {
        return div(res, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, a.value & 0xffff, b);
    }

    public static Vec4us div(Vec4us res, short a, Vec4us b) {
        return div(res, a & 0xffff, a & 0xffff, a & 0xffff, a & 0xffff, b);
    }

    public static Vec4us div(Vec4us res, int a, Vec4us b) {
        return div(res, a, a, a, a, b);
    }

    public static Vec4us div(Vec4us res, UShort aX, UShort aY, UShort aZ, UShort aW, Vec4us b) {
        return div(res, aX.value & 0xffff, aY.value & 0xffff, aZ.value & 0xffff, aW.value & 0xffff, b);
    }

    public static Vec4us div(Vec4us res, short aX, short aY, short aZ, short aW, Vec4us b) {
        return div(res, aX & 0xffff, aY & 0xffff, aZ & 0xffff, aW & 0xffff, b);
    }

    public static Vec4us div(Vec4us res, int aX, int aY, int aZ, int aW, Vec4us b) {
        res.x.value = (short) Integer.divideUnsigned(aX, b.x.value & 0xffff);
        res.y.value = (short) Integer.divideUnsigned(aY, b.y.value & 0xffff);
        res.z.value = (short) Integer.divideUnsigned(aZ, b.z.value & 0xffff);
        res.w.value = (short) Integer.divideUnsigned(aW, b.w.value & 0xffff);
        return res;
    }
}
