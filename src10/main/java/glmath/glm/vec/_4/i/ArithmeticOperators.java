/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.i;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Float.BYTES;

    public int x, y, z, w;

    public Vec4i add_(int b) {
        return Glm.add(new Vec4i(), (Vec4i) this, b, b, b, b);
    }

    public Vec4i add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4i(), (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i add_(Vec4i b) {
        return Glm.add(new Vec4i(), (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i add(int b) {
        return Glm.add((Vec4i) this, (Vec4i) this, b, b, b, b);
    }

    public Vec4i add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4i) this, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i add(Vec4i b) {
        return Glm.add((Vec4i) this, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i add(int b, Vec4i res) {
        return Glm.add(res, (Vec4i) this, b, b, b, b);
    }

    public Vec4i add(int bX, int bY, int bZ, int bW, Vec4i res) {
        return Glm.add(res, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i add(Vec4i b, Vec4i res) {
        return Glm.add(res, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i sub_(int b) {
        return Glm.sub(new Vec4i(), (Vec4i) this, b, b, b, b);
    }

    public Vec4i sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4i(), (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i sub_(Vec4i b) {
        return Glm.sub(new Vec4i(), (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i sub(int b) {
        return Glm.sub((Vec4i) this, (Vec4i) this, b, b, b, b);
    }

    public Vec4i sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4i) this, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i sub(Vec4i b) {
        return Glm.sub((Vec4i) this, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i sub(int b, Vec4i res) {
        return Glm.sub(res, (Vec4i) this, b, b, b, b);
    }

    public Vec4i sub(int bX, int bY, int bZ, int bW, Vec4i res) {
        return Glm.sub(res, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i sub(Vec4i b, Vec4i res) {
        return Glm.sub(res, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i mul_(int b) {
        return Glm.mul(new Vec4i(), (Vec4i) this, b, b, b, b);
    }

    public Vec4i mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4i(), (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i mul_(Vec4i b) {
        return Glm.mul(new Vec4i(), (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i mul(int b) {
        return Glm.mul((Vec4i) this, (Vec4i) this, b, b, b, b);
    }

    public Vec4i mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4i) this, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i mul(Vec4i b) {
        return Glm.mul((Vec4i) this, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i mul(int b, Vec4i res) {
        return Glm.mul(res, (Vec4i) this, b, b, b, b);
    }

    public Vec4i mul(int bX, int bY, int bZ, int bW, Vec4i res) {
        return Glm.mul(res, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i mul(Vec4i b, Vec4i res) {
        return Glm.mul(res, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i div_(int b) {
        return Glm.div(new Vec4i(), (Vec4i) this, b, b, b, b);
    }

    public Vec4i div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4i(), (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i div_(Vec4i b) {
        return Glm.div(new Vec4i(), (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i div(int b) {
        return Glm.div((Vec4i) this, (Vec4i) this, b, b, b, b);
    }

    public Vec4i div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4i) this, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i div(Vec4i b) {
        return Glm.div((Vec4i) this, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i div(int b, Vec4i res) {
        return Glm.div(res, (Vec4i) this, b, b, b, b);
    }

    public Vec4i div(int bX, int bY, int bZ, int bW, Vec4i res) {
        return Glm.div(res, (Vec4i) this, bX, bY, bZ, bW);
    }

    public Vec4i div(Vec4i b, Vec4i res) {
        return Glm.div(res, (Vec4i) this, b.x, b.y, b.z, b.w);
    }

    public Vec4i incr_() {
        return Glm.incr_((Vec4i) this);
    }

    public Vec4i incr() {
        return Glm.incr((Vec4i) this);
    }

    public Vec4i incr(Vec4i res) {
        return Glm.incr(res, (Vec4i) this);
    }

    public Vec4i decr_() {
        return Glm.decr_((Vec4i) this);
    }

    public Vec4i decr() {
        return Glm.decr((Vec4i) this);
    }

    public Vec4i decr(Vec4i res) {
        return Glm.decr(res, (Vec4i) this);
    }
}
