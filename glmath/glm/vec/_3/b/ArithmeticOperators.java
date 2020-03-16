/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.b;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Byte.BYTES;

    public byte x, y, z;

    public Vec3b add_(byte b) {
        return Glm.add(new Vec3b(), (Vec3b) this, (int) b, b, b);
    }

    public Vec3b add_(int b) {
        return Glm.add(new Vec3b(), (Vec3b) this, b, b, b);
    }

    public Vec3b add_(byte bX, byte bY, byte bZ) {
        return Glm.add(new Vec3b(), (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3b(), (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b add_(Vec3b b) {
        return Glm.add(new Vec3b(), (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b add(byte b) {
        return Glm.add((Vec3b) this, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b add(int b) {
        return Glm.add((Vec3b) this, (Vec3b) this, b, b, b);
    }

    public Vec3b add(byte bX, byte bY, byte bZ) {
        return Glm.add((Vec3b) this, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b add(int bX, int bY, int bZ) {
        return Glm.add((Vec3b) this, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b add(Vec3b b) {
        return Glm.add((Vec3b) this, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b add(byte b, Vec3b res) {
        return Glm.add(res, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b add(int b, Vec3b res) {
        return Glm.add(res, (Vec3b) this, b, b, b);
    }

    public Vec3b add(byte bX, byte bY, byte bZ, Vec3b res) {
        return Glm.add(res, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b add(int bX, int bY, int bZ, Vec3b res) {
        return Glm.add(res, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b add(Vec3b b, Vec3b res) {
        return Glm.add(res, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b sub_(byte b) {
        return Glm.sub(new Vec3b(), (Vec3b) this, (int) b, b, b);
    }

    public Vec3b sub_(int b) {
        return Glm.sub(new Vec3b(), (Vec3b) this, b, b, b);
    }

    public Vec3b sub_(byte bX, byte bY, byte bZ) {
        return Glm.sub(new Vec3b(), (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3b(), (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b sub_(Vec3b b) {
        return Glm.sub(new Vec3b(), (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b sub(byte b) {
        return Glm.sub((Vec3b) this, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b sub(int b) {
        return Glm.sub((Vec3b) this, (Vec3b) this, b, b, b);
    }

    public Vec3b sub(byte bX, byte bY, byte bZ) {
        return Glm.sub((Vec3b) this, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3b) this, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b sub(Vec3b b) {
        return Glm.sub((Vec3b) this, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b sub(byte b, Vec3b res) {
        return Glm.sub(res, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b sub(int b, Vec3b res) {
        return Glm.sub(res, (Vec3b) this, b, b, b);
    }

    public Vec3b sub(byte bX, byte bY, byte bZ, Vec3b res) {
        return Glm.sub(res, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b sub(int bX, int bY, int bZ, Vec3b res) {
        return Glm.sub(res, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b sub(Vec3b b, Vec3b res) {
        return Glm.sub(res, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b mul_(byte b) {
        return Glm.mul(new Vec3b(), (Vec3b) this, (int) b, b, b);
    }

    public Vec3b mul_(int b) {
        return Glm.mul(new Vec3b(), (Vec3b) this, b, b, b);
    }

    public Vec3b mul_(byte bX, byte bY, byte bZ) {
        return Glm.mul(new Vec3b(), (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3b(), (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b mul_(Vec3b b) {
        return Glm.mul(new Vec3b(), (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b mul(byte b) {
        return Glm.mul((Vec3b) this, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b mul(int b) {
        return Glm.mul((Vec3b) this, (Vec3b) this, b, b, b);
    }

    public Vec3b mul(byte bX, byte bY, byte bZ) {
        return Glm.mul((Vec3b) this, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3b) this, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b mul(Vec3b b) {
        return Glm.mul((Vec3b) this, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b mul(byte b, Vec3b res) {
        return Glm.mul(res, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b mul(int b, Vec3b res) {
        return Glm.mul(res, (Vec3b) this, b, b, b);
    }

    public Vec3b mul(byte bX, byte bY, byte bZ, Vec3b res) {
        return Glm.mul(res, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b mul(int bX, int bY, int bZ, Vec3b res) {
        return Glm.mul(res, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b mul(Vec3b b, Vec3b res) {
        return Glm.mul(res, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b div_(byte b) {
        return Glm.div(new Vec3b(), (Vec3b) this, (int) b, b, b);
    }

    public Vec3b div_(int b) {
        return Glm.div(new Vec3b(), (Vec3b) this, b, b, b);
    }

    public Vec3b div_(byte bX, byte bY, byte bZ) {
        return Glm.div(new Vec3b(), (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3b(), (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b div_(Vec3b b) {
        return Glm.div(new Vec3b(), (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b div(byte b) {
        return Glm.div((Vec3b) this, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b div(int b) {
        return Glm.div((Vec3b) this, (Vec3b) this, b, b, b);
    }

    public Vec3b div(byte bX, byte bY, byte bZ) {
        return Glm.div((Vec3b) this, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b div(int bX, int bY, int bZ) {
        return Glm.div((Vec3b) this, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b div(Vec3b b) {
        return Glm.div((Vec3b) this, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b div(byte b, Vec3b res) {
        return Glm.div(res, (Vec3b) this, (int) b, b, b);
    }

    public Vec3b div(int b, Vec3b res) {
        return Glm.div(res, (Vec3b) this, b, b, b);
    }

    public Vec3b div(byte bX, byte bY, byte bZ, Vec3b res) {
        return Glm.div(res, (Vec3b) this, (int) bX, bY, bZ);
    }

    public Vec3b div(int bX, int bY, int bZ, Vec3b res) {
        return Glm.div(res, (Vec3b) this, bX, bY, bZ);
    }

    public Vec3b div(Vec3b b, Vec3b res) {
        return Glm.div(res, (Vec3b) this, (int) b.x, b.y, b.z);
    }

    public Vec3b incr_() {
        return Glm.incr_((Vec3b) this);
    }

    public Vec3b incr() {
        return Glm.incr((Vec3b) this);
    }

    public Vec3b incr(Vec3b res) {
        return Glm.incr(res, (Vec3b) this);
    }

    public Vec3b decr_() {
        return Glm.decr_((Vec3b) this);
    }

    public Vec3b decr() {
        return Glm.decr((Vec3b) this);
    }

    public Vec3b decr(Vec3b res) {
        return Glm.decr(res, (Vec3b) this);
    }
}
