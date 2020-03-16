/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ub;

import glmath.glm.Glm;
import glmath.joou.UByte;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Byte.BYTES;

    public UByte x = new UByte(), y = new UByte(), z = new UByte();

    public Vec3ub add_(UByte b) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub add_(byte b) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub add_(int b) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, b, b, b);
    }

    public Vec3ub add_(UByte bX, UByte bY, UByte bZ) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub add_(byte bX, byte bY, byte bZ) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub add_(Vec3ub b) {
        return Glm.add(new Vec3ub(), (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub add(UByte b) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub add(byte b) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub add(int b) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, b, b, b);
    }

    public Vec3ub add(UByte bX, UByte bY, UByte bZ) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub add(byte bX, byte bY, byte bZ) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub add(int bX, int bY, int bZ) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub add(Vec3ub b) {
        return Glm.add((Vec3ub) this, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub add(UByte b, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub add(byte b, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub add(int b, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, b, b, b);
    }

    public Vec3ub add(UByte bX, UByte bY, UByte bZ, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub add(byte bX, byte bY, byte bZ, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub add(int bX, int bY, int bZ, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub add(Vec3ub b, Vec3ub res) {
        return Glm.add(res, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub sub_(UByte b) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub sub_(byte b) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub sub_(int b) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, b, b, b);
    }

    public Vec3ub sub_(UByte bX, UByte bY, UByte bZ) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub sub_(byte bX, byte bY, byte bZ) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub sub_(Vec3ub b) {
        return Glm.sub(new Vec3ub(), (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub sub(UByte b) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub sub(byte b) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub sub(int b) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, b, b, b);
    }

    public Vec3ub sub(UByte bX, UByte bY, UByte bZ) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub sub(byte bX, byte bY, byte bZ) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub sub(Vec3ub b) {
        return Glm.sub((Vec3ub) this, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub sub(UByte b, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub sub(byte b, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub sub(int b, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, b, b, b);
    }

    public Vec3ub sub(UByte bX, UByte bY, UByte bZ, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub sub(byte bX, byte bY, byte bZ, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub sub(int bX, int bY, int bZ, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub sub(Vec3ub b, Vec3ub res) {
        return Glm.sub(res, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub mul_(UByte b) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub mul_(byte b) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub mul_(int b) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, b, b, b);
    }

    public Vec3ub mul_(UByte bX, UByte bY, UByte bZ) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub mul_(byte bX, byte bY, byte bZ) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub mul_(Vec3ub b) {
        return Glm.mul(new Vec3ub(), (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub mul(UByte b) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub mul(byte b) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub mul(int b) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, b, b, b);
    }

    public Vec3ub mul(UByte bX, UByte bY, UByte bZ) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub mul(byte bX, byte bY, byte bZ) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub mul(Vec3ub b) {
        return Glm.mul((Vec3ub) this, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub mul(UByte b, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub mul(byte b, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub mul(int b, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, b, b, b);
    }

    public Vec3ub mul(UByte bX, UByte bY, UByte bZ, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub mul(byte bX, byte bY, byte bZ, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub mul(int bX, int bY, int bZ, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub mul(Vec3ub b, Vec3ub res) {
        return Glm.mul(res, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub div_(UByte b) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub div_(byte b) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub div_(int b) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, b, b, b);
    }

    public Vec3ub div_(UByte bX, UByte bY, UByte bZ) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub div_(byte bX, byte bY, byte bZ) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub div_(Vec3ub b) {
        return Glm.div(new Vec3ub(), (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub div(UByte b) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub div(byte b) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub div(int b) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, b, b, b);
    }

    public Vec3ub div(UByte bX, UByte bY, UByte bZ) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub div(byte bX, byte bY, byte bZ) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub div(int bX, int bY, int bZ) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub div(Vec3ub b) {
        return Glm.div((Vec3ub) this, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub div(UByte b, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, b.value & 0xff, b.value & 0xff, b.value & 0xff);
    }

    public Vec3ub div(byte b, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, b & 0xff, b & 0xff, b & 0xff);
    }

    public Vec3ub div(int b, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, b, b, b);
    }

    public Vec3ub div(UByte bX, UByte bY, UByte bZ, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, bX.value & 0xff, bY.value & 0xff, bZ.value & 0xff);
    }

    public Vec3ub div(byte bX, byte bY, byte bZ, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, bX & 0xff, bY & 0xff, bZ & 0xff);
    }

    public Vec3ub div(int bX, int bY, int bZ, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, bX, bY, bZ);
    }

    public Vec3ub div(Vec3ub b, Vec3ub res) {
        return Glm.div(res, (Vec3ub) this, b.x.value & 0xff, b.y.value & 0xff, b.z.value & 0xff);
    }

    public Vec3ub incr_() {
        return Glm.incr_((Vec3ub) this);
    }

    public Vec3ub incr() {
        return Glm.incr((Vec3ub) this);
    }

    public Vec3ub incr(Vec3ub res) {
        return Glm.incr(res, (Vec3ub) this);
    }

    public Vec3ub decr_() {
        return Glm.decr_((Vec3ub) this);
    }

    public Vec3ub decr() {
        return Glm.decr((Vec3ub) this);
    }

    public Vec3ub decr(Vec3ub res) {
        return Glm.decr(res, (Vec3ub) this);
    }
}
