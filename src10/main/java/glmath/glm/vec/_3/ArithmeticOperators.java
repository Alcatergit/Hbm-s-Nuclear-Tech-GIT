/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Float.BYTES;

    public float x, y, z;

    public Vec3 add_(float b) {
        return Glm.add(new Vec3(), (Vec3) this, b, b, b);
    }

    public Vec3 add_(float bX, float bY, float bZ) {
        return Glm.add(new Vec3(), (Vec3) this, bX, bY, bZ);
    }

    public Vec3 add_(Vec3 b) {
        return Glm.add(new Vec3(), (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 add(float b) {
        return Glm.add((Vec3) this, (Vec3) this, b, b, b);
    }

    public Vec3 add(float bX, float bY, float bZ) {
        return Glm.add((Vec3) this, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 add(Vec3 b) {
        return Glm.add((Vec3) this, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 add(float b, Vec3 res) {
        return Glm.add(res, (Vec3) this, b, b, b);
    }

    public Vec3 add(float bX, float bY, float bZ, Vec3 res) {
        return Glm.add(res, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 add(Vec3 b, Vec3 res) {
        return Glm.add(res, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 sub_(float b) {
        return Glm.sub(new Vec3(), (Vec3) this, b, b, b);
    }

    public Vec3 sub_(float bX, float bY, float bZ) {
        return Glm.sub(new Vec3(), (Vec3) this, bX, bY, bZ);
    }

    public Vec3 sub_(Vec3 b) {
        return Glm.sub(new Vec3(), (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 sub(float b) {
        return Glm.sub((Vec3) this, (Vec3) this, b, b, b);
    }

    public Vec3 sub(float bX, float bY, float bZ) {
        return Glm.sub((Vec3) this, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 sub(Vec3 b) {
        return Glm.sub((Vec3) this, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 sub(float b, Vec3 res) {
        return Glm.sub(res, (Vec3) this, b, b, b);
    }

    public Vec3 sub(float bX, float bY, float bZ, Vec3 res) {
        return Glm.sub(res, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 sub(Vec3 b, Vec3 res) {
        return Glm.sub(res, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 mul_(float b) {
        return Glm.mul(new Vec3(), (Vec3) this, b, b, b);
    }

    public Vec3 mul_(float bX, float bY, float bZ) {
        return Glm.mul(new Vec3(), (Vec3) this, bX, bY, bZ);
    }

    public Vec3 mul_(Vec3 b) {
        return Glm.mul(new Vec3(), (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 mul(float b) {
        return Glm.mul((Vec3) this, (Vec3) this, b, b, b);
    }

    public Vec3 mul(float bX, float bY, float bZ) {
        return Glm.mul((Vec3) this, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 mul(Vec3 b) {
        return Glm.mul((Vec3) this, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 mul(float b, Vec3 res) {
        return Glm.mul(res, (Vec3) this, b, b, b);
    }

    public Vec3 mul(float bX, float bY, float bZ, Vec3 res) {
        return Glm.mul(res, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 mul(Vec3 b, Vec3 res) {
        return Glm.mul(res, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 div_(float b) {
        return Glm.div(new Vec3(), (Vec3) this, b, b, b);
    }

    public Vec3 div_(float bX, float bY, float bZ) {
        return Glm.div(new Vec3(), (Vec3) this, bX, bY, bZ);
    }

    public Vec3 div_(Vec3 b) {
        return Glm.div(new Vec3(), (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 div(float b) {
        return Glm.div((Vec3) this, (Vec3) this, b, b, b);
    }

    public Vec3 div(float bX, float bY, float bZ) {
        return Glm.div((Vec3) this, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 div(Vec3 b) {
        return Glm.div((Vec3) this, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 div(float b, Vec3 res) {
        return Glm.div(res, (Vec3) this, b, b, b);
    }

    public Vec3 div(float bX, float bY, float bZ, Vec3 res) {
        return Glm.div(res, (Vec3) this, bX, bY, bZ);
    }

    public Vec3 div(Vec3 b, Vec3 res) {
        return Glm.div(res, (Vec3) this, b.x, b.y, b.z);
    }

    public Vec3 incr_() {
        return Glm.incr_((Vec3) this);
    }

    public Vec3 incr() {
        return Glm.incr((Vec3) this);
    }

    public Vec3 incr(Vec3 res) {
        return Glm.incr(res, (Vec3) this);
    }

    public Vec3 decr_() {
        return Glm.decr_((Vec3) this);
    }

    public Vec3 decr() {
        return Glm.decr((Vec3) this);
    }

    public Vec3 decr(Vec3 res) {
        return Glm.decr(res, (Vec3) this);
    }
}
