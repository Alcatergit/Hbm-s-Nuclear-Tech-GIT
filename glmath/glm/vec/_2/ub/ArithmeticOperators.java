/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ub;

import glmath.glm.Glm;
import glmath.joou.UByte;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Byte.BYTES;

    public UByte x = new UByte(), y = new UByte();

    public Vec2ub add_(UByte b) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub add_(byte b) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub add_(int b) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, b, b);
    }

    public Vec2ub add_(UByte bX, UByte bY) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub add_(byte bX, byte bY) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub add_(int bX, int bY) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, bX, bY);
    }

    public Vec2ub add_(Vec2ub b) {
        return Glm.add(new Vec2ub(), (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub add(UByte b) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub add(byte b) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub add(int b) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, b, b);
    }

    public Vec2ub add(UByte bX, UByte bY) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub add(byte bX, byte bY) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub add(int bX, int bY) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, bX, bY);
    }

    public Vec2ub add(Vec2ub b) {
        return Glm.add((Vec2ub) this, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub add(UByte b, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub add(byte b, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub add(int b, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, b, b);
    }

    public Vec2ub add(UByte bX, UByte bY, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub add(byte bX, byte bY, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub add(int bX, int bY, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, bX, bY);
    }

    public Vec2ub add(Vec2ub b, Vec2ub res) {
        return Glm.add(res, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub sub_(UByte b) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub sub_(byte b) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub sub_(int b) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, b, b);
    }

    public Vec2ub sub_(UByte bX, UByte bY) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub sub_(byte bX, byte bY) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub sub_(int bX, int bY) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, bX, bY);
    }

    public Vec2ub sub_(Vec2ub b) {
        return Glm.sub(new Vec2ub(), (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub sub(UByte b) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub sub(byte b) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub sub(int b) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, b, b);
    }

    public Vec2ub sub(UByte bX, UByte bY) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub sub(byte bX, byte bY) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub sub(int bX, int bY) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, bX, bY);
    }

    public Vec2ub sub(Vec2ub b) {
        return Glm.sub((Vec2ub) this, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub sub(UByte b, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub sub(byte b, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub sub(int b, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, b, b);
    }

    public Vec2ub sub(UByte bX, UByte bY, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub sub(byte bX, byte bY, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub sub(int bX, int bY, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, bX, bY);
    }

    public Vec2ub sub(Vec2ub b, Vec2ub res) {
        return Glm.sub(res, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub mul_(UByte b) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub mul_(byte b) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub mul_(int b) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, b, b);
    }

    public Vec2ub mul_(UByte bX, UByte bY) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub mul_(byte bX, byte bY) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub mul_(int bX, int bY) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, bX, bY);
    }

    public Vec2ub mul_(Vec2ub b) {
        return Glm.mul(new Vec2ub(), (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub mul(UByte b) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub mul(byte b) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub mul(int b) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, b, b);
    }

    public Vec2ub mul(UByte bX, UByte bY) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub mul(byte bX, byte bY) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub mul(int bX, int bY) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, bX, bY);
    }

    public Vec2ub mul(Vec2ub b) {
        return Glm.mul((Vec2ub) this, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub mul(UByte b, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub mul(byte b, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub mul(int b, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, b, b);
    }

    public Vec2ub mul(UByte bX, UByte bY, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub mul(byte bX, byte bY, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub mul(int bX, int bY, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, bX, bY);
    }

    public Vec2ub mul(Vec2ub b, Vec2ub res) {
        return Glm.mul(res, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub div_(UByte b) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub div_(byte b) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub div_(int b) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, b, b);
    }

    public Vec2ub div_(UByte bX, UByte bY) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub div_(byte bX, byte bY) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub div_(int bX, int bY) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, bX, bY);
    }

    public Vec2ub div_(Vec2ub b) {
        return Glm.div(new Vec2ub(), (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub div(UByte b) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub div(byte b) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub div(int b) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, b, b);
    }

    public Vec2ub div(UByte bX, UByte bY) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub div(byte bX, byte bY) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub div(int bX, int bY) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, bX, bY);
    }

    public Vec2ub div(Vec2ub b) {
        return Glm.div((Vec2ub) this, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub div(UByte b, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, b.value & 0xff, b.value& 0xff);
    }

    public Vec2ub div(byte b, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, b & 0xff, b & 0xff);
    }

    public Vec2ub div(int b, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, b, b);
    }

    public Vec2ub div(UByte bX, UByte bY, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, bX.value & 0xff, bY.value & 0xff);
    }

    public Vec2ub div(byte bX, byte bY, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, bX & 0xff, bY & 0xff);
    }

    public Vec2ub div(int bX, int bY, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, bX, bY);
    }

    public Vec2ub div(Vec2ub b, Vec2ub res) {
        return Glm.div(res, (Vec2ub) this, b.x.value & 0xff, b.y.value & 0xff);
    }

    public Vec2ub incr_() {
        return Glm.incr_((Vec2ub) this);
    }

    public Vec2ub incr() {
        return Glm.incr((Vec2ub) this);
    }

    public Vec2ub incr(Vec2ub res) {
        return Glm.incr(res, (Vec2ub) this);
    }

    public Vec2ub decr_() {
        return Glm.decr_((Vec2ub) this);
    }

    public Vec2ub decr() {
        return Glm.decr((Vec2ub) this);
    }

    public Vec2ub decr(Vec2ub res) {
        return Glm.decr(res, (Vec2ub) this);
    }
}
