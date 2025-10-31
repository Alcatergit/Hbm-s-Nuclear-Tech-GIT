/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ub;

import glmath.glm.Glm;
import glmath.joou.UByte;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Byte.BYTES;

    public UByte x = new UByte(), y = new UByte(), z = new UByte(), w = new UByte();

    public Vec4ub add_(UByte b) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub add_(byte b) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub add_(int b) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub add_(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub add_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub add_(Vec4ub b) {
        return Glm.add(new Vec4ub(), (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub add(UByte b) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub add(byte b) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub add(int b) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub add(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub add(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub add(Vec4ub b) {
        return Glm.add((Vec4ub) this, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub add(UByte b, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub add(byte b, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub add(int b, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub add(UByte bX, UByte bY, UByte bZ, UByte bW, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub add(byte bX, byte bY, byte bZ, byte bW, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub add(int bX, int bY, int bZ, int bW, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub add(Vec4ub b, Vec4ub res) {
        return Glm.add(res, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub sub_(UByte b) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub sub_(byte b) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub sub_(int b) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub sub_(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub sub_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub sub_(Vec4ub b) {
        return Glm.sub(new Vec4ub(), (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub sub(UByte b) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub sub(byte b) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub sub(int b) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub sub(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub sub(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub sub(Vec4ub b) {
        return Glm.sub((Vec4ub) this, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub sub(UByte b, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub sub(byte b, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub sub(int b, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub sub(UByte bX, UByte bY, UByte bZ, UByte bW, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub sub(byte bX, byte bY, byte bZ, byte bW, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub sub(int bX, int bY, int bZ, int bW, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub sub(Vec4ub b, Vec4ub res) {
        return Glm.sub(res, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub mul_(UByte b) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub mul_(byte b) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub mul_(int b) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub mul_(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub mul_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub mul_(Vec4ub b) {
        return Glm.mul(new Vec4ub(), (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub mul(UByte b) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub mul(byte b) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub mul(int b) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub mul(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub mul(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub mul(Vec4ub b) {
        return Glm.mul((Vec4ub) this, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub mul(UByte b, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub mul(byte b, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub mul(int b, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub mul(UByte bX, UByte bY, UByte bZ, UByte bW, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub mul(byte bX, byte bY, byte bZ, byte bW, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub mul(int bX, int bY, int bZ, int bW, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub mul(Vec4ub b, Vec4ub res) {
        return Glm.mul(res, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub div_(UByte b) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub div_(byte b) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub div_(int b) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub div_(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub div_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub div_(Vec4ub b) {
        return Glm.div(new Vec4ub(), (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub div(UByte b) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub div(byte b) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub div(int b) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub div(UByte bX, UByte bY, UByte bZ, UByte bW) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub div(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub div(Vec4ub b) {
        return Glm.div((Vec4ub) this, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub div(UByte b, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec4ub div(byte b, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, b & 0xff, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec4ub div(int b, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, b, b, b, b);
    }

    public Vec4ub div(UByte bX, UByte bY, UByte bZ, UByte bW, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff, bW.value & 0xff);
    }

    public Vec4ub div(byte bX, byte bY, byte bZ, byte bW, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, bX & 0xff, bY & 0xff, bZ & 0xff, bW & 0xff);
    }

    public Vec4ub div(int bX, int bY, int bZ, int bW, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, bX, bY, bZ, bW);
    }

    public Vec4ub div(Vec4ub b, Vec4ub res) {
        return Glm.div(res, (Vec4ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff, b.w.value & 0xff);
    }

    public Vec4ub incr_() {
        return Glm.incr_((Vec4ub) this);
    }

    public Vec4ub incr() {
        return Glm.incr((Vec4ub) this);
    }

    public Vec4ub incr(Vec4ub res) {
        return Glm.incr(res, (Vec4ub) this);
    }

    public Vec4ub decr_() {
        return Glm.decr_((Vec4ub) this);
    }

    public Vec4ub decr() {
        return Glm.decr((Vec4ub) this);
    }

    public Vec4ub decr(Vec4ub res) {
        return Glm.decr(res, (Vec4ub) this);
    }
}
