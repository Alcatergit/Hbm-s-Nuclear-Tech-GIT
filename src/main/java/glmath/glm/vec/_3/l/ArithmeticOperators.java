/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.l;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Long.BYTES;

    public long x, y, z;

    public Vec3l add_(long b) {
        return Glm.add(new Vec3l(), (Vec3l) this, b, b, b);
    }

    public Vec3l add_(long bX, long bY, long bZ) {
        return Glm.add(new Vec3l(), (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l add_(Vec3l b) {
        return Glm.add(new Vec3l(), (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l add(long b) {
        return Glm.add((Vec3l) this, (Vec3l) this, b, b, b);
    }

    public Vec3l add(long bX, long bY, long bZ) {
        return Glm.add((Vec3l) this, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l add(Vec3l b) {
        return Glm.add((Vec3l) this, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l add(long b, Vec3l res) {
        return Glm.add(res, (Vec3l) this, b, b, b);
    }

    public Vec3l add(long bX, long bY, long bZ, Vec3l res) {
        return Glm.add(res, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l add(Vec3l b, Vec3l res) {
        return Glm.add(res, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l sub_(long b) {
        return Glm.sub(new Vec3l(), (Vec3l) this, b, b, b);
    }

    public Vec3l sub_(long bX, long bY, long bZ) {
        return Glm.sub(new Vec3l(), (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l sub_(Vec3l b) {
        return Glm.sub(new Vec3l(), (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l sub(long b) {
        return Glm.sub((Vec3l) this, (Vec3l) this, b, b, b);
    }

    public Vec3l sub(long bX, long bY, long bZ) {
        return Glm.sub((Vec3l) this, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l sub(Vec3l b) {
        return Glm.sub((Vec3l) this, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l sub(long b, Vec3l res) {
        return Glm.sub(res, (Vec3l) this, b, b, b);
    }

    public Vec3l sub(long bX, long bY, long bZ, Vec3l res) {
        return Glm.sub(res, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l sub(Vec3l b, Vec3l res) {
        return Glm.sub(res, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l mul_(long b) {
        return Glm.mul(new Vec3l(), (Vec3l) this, b, b, b);
    }

    public Vec3l mul_(long bX, long bY, long bZ) {
        return Glm.mul(new Vec3l(), (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l mul_(Vec3l b) {
        return Glm.mul(new Vec3l(), (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l mul(long b) {
        return Glm.mul((Vec3l) this, (Vec3l) this, b, b, b);
    }

    public Vec3l mul(long bX, long bY, long bZ) {
        return Glm.mul((Vec3l) this, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l mul(Vec3l b) {
        return Glm.mul((Vec3l) this, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l mul(long b, Vec3l res) {
        return Glm.mul(res, (Vec3l) this, b, b, b);
    }

    public Vec3l mul(long bX, long bY, long bZ, Vec3l res) {
        return Glm.mul(res, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l mul(Vec3l b, Vec3l res) {
        return Glm.mul(res, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l div_(long b) {
        return Glm.div(new Vec3l(), (Vec3l) this, b, b, b);
    }

    public Vec3l div_(long bX, long bY, long bZ) {
        return Glm.div(new Vec3l(), (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l div_(Vec3l b) {
        return Glm.div(new Vec3l(), (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l div(long b) {
        return Glm.div((Vec3l) this, (Vec3l) this, b, b, b);
    }

    public Vec3l div(long bX, long bY, long bZ) {
        return Glm.div((Vec3l) this, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l div(Vec3l b) {
        return Glm.div((Vec3l) this, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l div(long b, Vec3l res) {
        return Glm.div(res, (Vec3l) this, b, b, b);
    }

    public Vec3l div(long bX, long bY, long bZ, Vec3l res) {
        return Glm.div(res, (Vec3l) this, bX, bY, bZ);
    }

    public Vec3l div(Vec3l b, Vec3l res) {
        return Glm.div(res, (Vec3l) this, b.x, b.y, b.z);
    }

    public Vec3l incr_() {
        return Glm.incr_((Vec3l) this);
    }

    public Vec3l incr() {
        return Glm.incr((Vec3l) this);
    }

    public Vec3l incr(Vec3l res) {
        return Glm.incr(res, (Vec3l) this);
    }

    public Vec3l decr_() {
        return Glm.decr_((Vec3l) this);
    }

    public Vec3l decr() {
        return Glm.decr((Vec3l) this);
    }

    public Vec3l decr(Vec3l res) {
        return Glm.decr(res, (Vec3l) this);
    }
}
