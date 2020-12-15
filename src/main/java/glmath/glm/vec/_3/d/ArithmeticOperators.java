/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.d;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public final static int SIZE = 3 * Double.BYTES;

    public double x, y, z;

    public Vec3d add_(double b) {
        return Glm.add(new Vec3d(), (Vec3d) this, b, b, b);
    }

    public Vec3d add_(double bX, double bY, double bZ) {
        return Glm.add(new Vec3d(), (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d add_(Vec3d b) {
        return Glm.add(new Vec3d(), (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d add(double b) {
        return Glm.add((Vec3d) this, (Vec3d) this, b, b, b);
    }

    public Vec3d add(double bX, double bY, double bZ) {
        return Glm.add((Vec3d) this, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d add(Vec3d b) {
        return Glm.add((Vec3d) this, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d add(double b, Vec3d res) {
        return Glm.add(res, (Vec3d) this, b, b, b);
    }

    public Vec3d add(double bX, double bY, double bZ, Vec3d res) {
        return Glm.add(res, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d add(Vec3d b, Vec3d res) {
        return Glm.add(res, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d sub_(double b) {
        return Glm.sub(new Vec3d(), (Vec3d) this, b, b, b);
    }

    public Vec3d sub_(double bX, double bY, double bZ) {
        return Glm.sub(new Vec3d(), (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d sub_(Vec3d b) {
        return Glm.sub(new Vec3d(), (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d sub(double b) {
        return Glm.sub((Vec3d) this, (Vec3d) this, b, b, b);
    }

    public Vec3d sub(double bX, double bY, double bZ) {
        return Glm.sub((Vec3d) this, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d sub(Vec3d b) {
        return Glm.sub((Vec3d) this, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d sub(double b, Vec3d res) {
        return Glm.sub(res, (Vec3d) this, b, b, b);
    }

    public Vec3d sub(double bX, double bY, double bZ, Vec3d res) {
        return Glm.sub(res, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d sub(Vec3d b, Vec3d res) {
        return Glm.sub(res, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d mul_(double b) {
        return Glm.mul(new Vec3d(), (Vec3d) this, b, b, b);
    }

    public Vec3d mul_(double bX, double bY, double bZ) {
        return Glm.mul(new Vec3d(), (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d mul_(Vec3d b) {
        return Glm.mul(new Vec3d(), (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d mul(double b) {
        return Glm.mul((Vec3d) this, (Vec3d) this, b, b, b);
    }

    public Vec3d mul(double bX, double bY, double bZ) {
        return Glm.mul((Vec3d) this, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d mul(Vec3d b) {
        return Glm.mul((Vec3d) this, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d mul(double b, Vec3d res) {
        return Glm.mul(res, (Vec3d) this, b, b, b);
    }

    public Vec3d mul(double bX, double bY, double bZ, Vec3d res) {
        return Glm.mul(res, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d mul(Vec3d b, Vec3d res) {
        return Glm.mul(res, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d div_(double b) {
        return Glm.div(new Vec3d(), (Vec3d) this, b, b, b);
    }

    public Vec3d div_(double bX, double bY, double bZ) {
        return Glm.div(new Vec3d(), (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d div_(Vec3d b) {
        return Glm.div(new Vec3d(), (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d div(double b) {
        return Glm.div((Vec3d) this, (Vec3d) this, b, b, b);
    }

    public Vec3d div(double bX, double bY, double bZ) {
        return Glm.div((Vec3d) this, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d div(Vec3d b) {
        return Glm.div((Vec3d) this, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d div(double b, Vec3d res) {
        return Glm.div(res, (Vec3d) this, b, b, b);
    }

    public Vec3d div(double bX, double bY, double bZ, Vec3d res) {
        return Glm.div(res, (Vec3d) this, bX, bY, bZ);
    }

    public Vec3d div(Vec3d b, Vec3d res) {
        return Glm.div(res, (Vec3d) this, b.x, b.y, b.z);
    }

    public Vec3d incr_() {
        return Glm.incr_((Vec3d) this);
    }

    public Vec3d incr() {
        return Glm.incr((Vec3d) this);
    }

    public Vec3d incr(Vec3d res) {
        return Glm.incr(res, (Vec3d) this);
    }

    public Vec3d decr_() {
        return Glm.decr_((Vec3d) this);
    }

    public Vec3d decr() {
        return Glm.decr((Vec3d) this);
    }

    public Vec3d decr(Vec3d res) {
        return Glm.decr(res, (Vec3d) this);
    }
}
