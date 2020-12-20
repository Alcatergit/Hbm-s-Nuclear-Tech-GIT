/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.i;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Integer.BYTES;

    public int x, y, z;

    public Vec3i add_(int b) {
        return Glm.add(new Vec3i(), (Vec3i) this, b, b, b);
    }

    public Vec3i add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3i(), (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i add_(Vec3i b) {
        return Glm.add(new Vec3i(), (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i add(int b) {
        return Glm.add((Vec3i) this, (Vec3i) this, b, b, b);
    }

    public Vec3i add(int bX, int bY, int bZ) {
        return Glm.add((Vec3i) this, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i add(Vec3i b) {
        return Glm.add((Vec3i) this, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i add(int b, Vec3i res) {
        return Glm.add(res, (Vec3i) this, b, b, b);
    }

    public Vec3i add(int bX, int bY, int bZ, Vec3i res) {
        return Glm.add(res, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i add(Vec3i b, Vec3i res) {
        return Glm.add(res, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i sub_(int b) {
        return Glm.sub(new Vec3i(), (Vec3i) this, b, b, b);
    }

    public Vec3i sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3i(), (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i sub_(Vec3i b) {
        return Glm.sub(new Vec3i(), (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i sub(int b) {
        return Glm.sub((Vec3i) this, (Vec3i) this, b, b, b);
    }

    public Vec3i sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3i) this, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i sub(Vec3i b) {
        return Glm.sub((Vec3i) this, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i sub(int b, Vec3i res) {
        return Glm.sub(res, (Vec3i) this, b, b, b);
    }

    public Vec3i sub(int bX, int bY, int bZ, Vec3i res) {
        return Glm.sub(res, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i sub(Vec3i b, Vec3i res) {
        return Glm.sub(res, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i mul_(int b) {
        return Glm.mul(new Vec3i(), (Vec3i) this, b, b, b);
    }

    public Vec3i mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3i(), (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i mul_(Vec3i b) {
        return Glm.mul(new Vec3i(), (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i mul(int b) {
        return Glm.mul((Vec3i) this, (Vec3i) this, b, b, b);
    }

    public Vec3i mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3i) this, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i mul(Vec3i b) {
        return Glm.mul((Vec3i) this, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i mul(int b, Vec3i res) {
        return Glm.mul(res, (Vec3i) this, b, b, b);
    }

    public Vec3i mul(int bX, int bY, int bZ, Vec3i res) {
        return Glm.mul(res, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i mul(Vec3i b, Vec3i res) {
        return Glm.mul(res, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i div_(int b) {
        return Glm.div(new Vec3i(), (Vec3i) this, b, b, b);
    }

    public Vec3i div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3i(), (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i div_(Vec3i b) {
        return Glm.div(new Vec3i(), (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i div(int b) {
        return Glm.div((Vec3i) this, (Vec3i) this, b, b, b);
    }

    public Vec3i div(int bX, int bY, int bZ) {
        return Glm.div((Vec3i) this, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i div(Vec3i b) {
        return Glm.div((Vec3i) this, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i div(int b, Vec3i res) {
        return Glm.div(res, (Vec3i) this, b, b, b);
    }

    public Vec3i div(int bX, int bY, int bZ, Vec3i res) {
        return Glm.div(res, (Vec3i) this, bX, bY, bZ);
    }

    public Vec3i div(Vec3i b, Vec3i res) {
        return Glm.div(res, (Vec3i) this, b.x, b.y, b.z);
    }

    public Vec3i incr_() {
        return Glm.incr_((Vec3i) this);
    }

    public Vec3i incr() {
        return Glm.incr((Vec3i) this);
    }

    public Vec3i incr(Vec3i res) {
        return Glm.incr(res, (Vec3i) this);
    }

    public Vec3i decr_() {
        return Glm.decr_((Vec3i) this);
    }

    public Vec3i decr() {
        return Glm.decr((Vec3i) this);
    }

    public Vec3i decr(Vec3i res) {
        return Glm.decr(res, (Vec3i) this);
    }
}
