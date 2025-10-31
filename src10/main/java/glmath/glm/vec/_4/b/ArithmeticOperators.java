/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.b;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Byte.BYTES;

    public byte x, y, z, w;

    public Vec4b add_(byte b) {
        return Glm.add(new Vec4b(), (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b add_(int b) {
        return Glm.add(new Vec4b(), (Vec4b) this, b, b, b, b);
    }

    public Vec4b add_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.add(new Vec4b(), (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4b(), (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b add_(Vec4b b) {
        return Glm.add(new Vec4b(), (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b add(byte b) {
        return Glm.add((Vec4b) this, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b add(int b) {
        return Glm.add((Vec4b) this, (Vec4b) this, b, b, b, b);
    }

    public Vec4b add(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.add((Vec4b) this, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4b) this, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b add(Vec4b b) {
        return Glm.add((Vec4b) this, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b add(byte b, Vec4b res) {
        return Glm.add(res, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b add(int b, Vec4b res) {
        return Glm.add(res, (Vec4b) this, b, b, b, b);
    }

    public Vec4b add(byte bX, byte bY, byte bZ, byte bW, Vec4b res) {
        return Glm.add(res, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b add(int bX, int bY, int bZ, int bW, Vec4b res) {
        return Glm.add(res, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b add(Vec4b b, Vec4b res) {
        return Glm.add(res, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b sub_(byte b) {
        return Glm.sub(new Vec4b(), (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b sub_(int b) {
        return Glm.sub(new Vec4b(), (Vec4b) this, b, b, b, b);
    }

    public Vec4b sub_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.sub(new Vec4b(), (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4b(), (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b sub_(Vec4b b) {
        return Glm.sub(new Vec4b(), (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b sub(byte b) {
        return Glm.sub((Vec4b) this, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b sub(int b) {
        return Glm.sub((Vec4b) this, (Vec4b) this, b, b, b, b);
    }

    public Vec4b sub(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.sub((Vec4b) this, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4b) this, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b sub(Vec4b b) {
        return Glm.sub((Vec4b) this, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b sub(byte b, Vec4b res) {
        return Glm.sub(res, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b sub(int b, Vec4b res) {
        return Glm.sub(res, (Vec4b) this, b, b, b, b);
    }

    public Vec4b sub(byte bX, byte bY, byte bZ, byte bW, Vec4b res) {
        return Glm.sub(res, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b sub(int bX, int bY, int bZ, int bW, Vec4b res) {
        return Glm.sub(res, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b sub(Vec4b b, Vec4b res) {
        return Glm.sub(res, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b mul_(byte b) {
        return Glm.mul(new Vec4b(), (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b mul_(int b) {
        return Glm.mul(new Vec4b(), (Vec4b) this, b, b, b, b);
    }

    public Vec4b mul_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.mul(new Vec4b(), (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4b(), (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b mul_(Vec4b b) {
        return Glm.mul(new Vec4b(), (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b mul(byte b) {
        return Glm.mul((Vec4b) this, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b mul(int b) {
        return Glm.mul((Vec4b) this, (Vec4b) this, b, b, b, b);
    }

    public Vec4b mul(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.mul((Vec4b) this, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4b) this, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b mul(Vec4b b) {
        return Glm.mul((Vec4b) this, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b mul(byte b, Vec4b res) {
        return Glm.mul(res, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b mul(int b, Vec4b res) {
        return Glm.mul(res, (Vec4b) this, b, b, b, b);
    }

    public Vec4b mul(byte bX, byte bY, byte bZ, byte bW, Vec4b res) {
        return Glm.mul(res, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b mul(int bX, int bY, int bZ, int bW, Vec4b res) {
        return Glm.mul(res, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b mul(Vec4b b, Vec4b res) {
        return Glm.mul(res, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b div_(byte b) {
        return Glm.div(new Vec4b(), (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b div_(int b) {
        return Glm.div(new Vec4b(), (Vec4b) this, b, b, b, b);
    }

    public Vec4b div_(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.div(new Vec4b(), (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4b(), (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b div_(Vec4b b) {
        return Glm.div(new Vec4b(), (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b div(byte b) {
        return Glm.div((Vec4b) this, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b div(int b) {
        return Glm.div((Vec4b) this, (Vec4b) this, b, b, b, b);
    }

    public Vec4b div(byte bX, byte bY, byte bZ, byte bW) {
        return Glm.div((Vec4b) this, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4b) this, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b div(Vec4b b) {
        return Glm.div((Vec4b) this, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b div(byte b, Vec4b res) {
        return Glm.div(res, (Vec4b) this, (int) b, b, b, b);
    }

    public Vec4b div(int b, Vec4b res) {
        return Glm.div(res, (Vec4b) this, b, b, b, b);
    }

    public Vec4b div(byte bX, byte bY, byte bZ, byte bW, Vec4b res) {
        return Glm.div(res, (Vec4b) this, (int) bX, bY, bZ, bW);
    }

    public Vec4b div(int bX, int bY, int bZ, int bW, Vec4b res) {
        return Glm.div(res, (Vec4b) this, bX, bY, bZ, bW);
    }

    public Vec4b div(Vec4b b, Vec4b res) {
        return Glm.div(res, (Vec4b) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4b incr_() {
        return Glm.incr_((Vec4b) this);
    }

    public Vec4b incr() {
        return Glm.incr((Vec4b) this);
    }

    public Vec4b incr(Vec4b res) {
        return Glm.incr(res, (Vec4b) this);
    }

    public Vec4b decr_() {
        return Glm.decr_((Vec4b) this);
    }

    public Vec4b decr() {
        return Glm.decr((Vec4b) this);
    }

    public Vec4b decr(Vec4b res) {
        return Glm.decr(res, (Vec4b) this);
    }
}
