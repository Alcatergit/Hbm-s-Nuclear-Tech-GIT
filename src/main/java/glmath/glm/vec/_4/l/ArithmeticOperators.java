/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.l;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Long.BYTES;

    public long x, y, z, w;

    public Vec4l add_(long b) {
        return Glm.add(new Vec4l(), (Vec4l) this, b, b, b, b);
    }

    public Vec4l add_(long bX, long bY, long bZ, long bW) {
        return Glm.add(new Vec4l(), (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l add_(Vec4l b) {
        return Glm.add(new Vec4l(), (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l add(long b) {
        return Glm.add((Vec4l) this, (Vec4l) this, b, b, b, b);
    }

    public Vec4l add(long bX, long bY, long bZ, long bW) {
        return Glm.add((Vec4l) this, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l add(Vec4l b) {
        return Glm.add((Vec4l) this, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l add(long b, Vec4l res) {
        return Glm.add(res, (Vec4l) this, b, b, b, b);
    }

    public Vec4l add(long bX, long bY, long bZ, long bW, Vec4l res) {
        return Glm.add(res, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l add(Vec4l b, Vec4l res) {
        return Glm.add(res, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l sub_(long b) {
        return Glm.sub(new Vec4l(), (Vec4l) this, b, b, b, b);
    }

    public Vec4l sub_(long bX, long bY, long bZ, long bW) {
        return Glm.sub(new Vec4l(), (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l sub_(Vec4l b) {
        return Glm.sub(new Vec4l(), (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l sub(long b) {
        return Glm.sub((Vec4l) this, (Vec4l) this, b, b, b, b);
    }

    public Vec4l sub(long bX, long bY, long bZ, long bW) {
        return Glm.sub((Vec4l) this, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l sub(Vec4l b) {
        return Glm.sub((Vec4l) this, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l sub(long b, Vec4l res) {
        return Glm.sub(res, (Vec4l) this, b, b, b, b);
    }

    public Vec4l sub(long bX, long bY, long bZ, long bW, Vec4l res) {
        return Glm.sub(res, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l sub(Vec4l b, Vec4l res) {
        return Glm.sub(res, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l mul_(long b) {
        return Glm.mul(new Vec4l(), (Vec4l) this, b, b, b, b);
    }

    public Vec4l mul_(long bX, long bY, long bZ, long bW) {
        return Glm.mul(new Vec4l(), (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l mul_(Vec4l b) {
        return Glm.mul(new Vec4l(), (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l mul(long b) {
        return Glm.mul((Vec4l) this, (Vec4l) this, b, b, b, b);
    }

    public Vec4l mul(long bX, long bY, long bZ, long bW) {
        return Glm.mul((Vec4l) this, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l mul(Vec4l b) {
        return Glm.mul((Vec4l) this, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l mul(long b, Vec4l res) {
        return Glm.mul(res, (Vec4l) this, b, b, b, b);
    }

    public Vec4l mul(long bX, long bY, long bZ, long bW, Vec4l res) {
        return Glm.mul(res, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l mul(Vec4l b, Vec4l res) {
        return Glm.mul(res, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l div_(long b) {
        return Glm.div(new Vec4l(), (Vec4l) this, b, b, b, b);
    }

    public Vec4l div_(long bX, long bY, long bZ, long bW) {
        return Glm.div(new Vec4l(), (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l div_(Vec4l b) {
        return Glm.div(new Vec4l(), (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l div(long b) {
        return Glm.div((Vec4l) this, (Vec4l) this, b, b, b, b);
    }

    public Vec4l div(long bX, long bY, long bZ, long bW) {
        return Glm.div((Vec4l) this, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l div(Vec4l b) {
        return Glm.div((Vec4l) this, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l div(long b, Vec4l res) {
        return Glm.div(res, (Vec4l) this, b, b, b, b);
    }

    public Vec4l div(long bX, long bY, long bZ, long bW, Vec4l res) {
        return Glm.div(res, (Vec4l) this, bX, bY, bZ, bW);
    }

    public Vec4l div(Vec4l b, Vec4l res) {
        return Glm.div(res, (Vec4l) this, b.x, b.y, b.z, b.w);
    }

    public Vec4l incr_() {
        return Glm.incr_((Vec4l) this);
    }

    public Vec4l incr() {
        return Glm.incr((Vec4l) this);
    }

    public Vec4l incr(Vec4l res) {
        return Glm.incr(res, (Vec4l) this);
    }

    public Vec4l decr_() {
        return Glm.decr_((Vec4l) this);
    }

    public Vec4l decr() {
        return Glm.decr((Vec4l) this);
    }

    public Vec4l decr(Vec4l res) {
        return Glm.decr(res, (Vec4l) this);
    }
}
