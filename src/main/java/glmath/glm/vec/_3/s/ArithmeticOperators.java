/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.s;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Short.BYTES;

    public short x, y, z;

    public Vec3s add_(short b) {
        return Glm.add(new Vec3s(), (Vec3s) this, (int) b, b, b);
    }

    public Vec3s add_(int b) {
        return Glm.add(new Vec3s(), (Vec3s) this, b, b, b);
    }

    public Vec3s add_(short bX, short bY, short bZ) {
        return Glm.add(new Vec3s(), (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3s(), (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s add_(Vec3s b) {
        return Glm.add(new Vec3s(), (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s add(short b) {
        return Glm.add((Vec3s) this, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s add(int b) {
        return Glm.add((Vec3s) this, (Vec3s) this, b, b, b);
    }

    public Vec3s add(short bX, short bY, short bZ) {
        return Glm.add((Vec3s) this, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s add(int bX, int bY, int bZ) {
        return Glm.add((Vec3s) this, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s add(Vec3s b) {
        return Glm.add((Vec3s) this, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s add(short b, Vec3s res) {
        return Glm.add(res, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s add(int b, Vec3s res) {
        return Glm.add(res, (Vec3s) this, b, b, b);
    }

    public Vec3s add(short bX, short bY, short bZ, Vec3s res) {
        return Glm.add(res, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s add(int bX, int bY, int bZ, Vec3s res) {
        return Glm.add(res, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s add(Vec3s b, Vec3s res) {
        return Glm.add(res, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s sub_(short b) {
        return Glm.sub(new Vec3s(), (Vec3s) this, (int) b, b, b);
    }

    public Vec3s sub_(int b) {
        return Glm.sub(new Vec3s(), (Vec3s) this, b, b, b);
    }

    public Vec3s sub_(short bX, short bY, short bZ) {
        return Glm.sub(new Vec3s(), (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3s(), (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s sub_(Vec3s b) {
        return Glm.sub(new Vec3s(), (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s sub(short b) {
        return Glm.sub((Vec3s) this, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s sub(int b) {
        return Glm.sub((Vec3s) this, (Vec3s) this, b, b, b);
    }

    public Vec3s sub(short bX, short bY, short bZ) {
        return Glm.sub((Vec3s) this, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3s) this, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s sub(Vec3s b) {
        return Glm.sub((Vec3s) this, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s sub(short b, Vec3s res) {
        return Glm.sub(res, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s sub(int b, Vec3s res) {
        return Glm.sub(res, (Vec3s) this, b, b, b);
    }

    public Vec3s sub(short bX, short bY, short bZ, Vec3s res) {
        return Glm.sub(res, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s sub(int bX, int bY, int bZ, Vec3s res) {
        return Glm.sub(res, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s sub(Vec3s b, Vec3s res) {
        return Glm.sub(res, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s mul_(short b) {
        return Glm.mul(new Vec3s(), (Vec3s) this, (int) b, b, b);
    }

    public Vec3s mul_(int b) {
        return Glm.mul(new Vec3s(), (Vec3s) this, b, b, b);
    }

    public Vec3s mul_(short bX, short bY, short bZ) {
        return Glm.mul(new Vec3s(), (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3s(), (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s mul_(Vec3s b) {
        return Glm.mul(new Vec3s(), (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s mul(short b) {
        return Glm.mul((Vec3s) this, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s mul(int b) {
        return Glm.mul((Vec3s) this, (Vec3s) this, b, b, b);
    }

    public Vec3s mul(short bX, short bY, short bZ) {
        return Glm.mul((Vec3s) this, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3s) this, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s mul(Vec3s b) {
        return Glm.mul((Vec3s) this, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s mul(short b, Vec3s res) {
        return Glm.mul(res, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s mul(int b, Vec3s res) {
        return Glm.mul(res, (Vec3s) this, b, b, b);
    }

    public Vec3s mul(short bX, short bY, short bZ, Vec3s res) {
        return Glm.mul(res, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s mul(int bX, int bY, int bZ, Vec3s res) {
        return Glm.mul(res, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s mul(Vec3s b, Vec3s res) {
        return Glm.mul(res, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s div_(short b) {
        return Glm.div(new Vec3s(), (Vec3s) this, (int) b, b, b);
    }

    public Vec3s div_(int b) {
        return Glm.div(new Vec3s(), (Vec3s) this, b, b, b);
    }

    public Vec3s div_(short bX, short bY, short bZ) {
        return Glm.div(new Vec3s(), (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3s(), (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s div_(Vec3s b) {
        return Glm.div(new Vec3s(), (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s div(short b) {
        return Glm.div((Vec3s) this, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s div(int b) {
        return Glm.div((Vec3s) this, (Vec3s) this, b, b, b);
    }

    public Vec3s div(short bX, short bY, short bZ) {
        return Glm.div((Vec3s) this, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s div(int bX, int bY, int bZ) {
        return Glm.div((Vec3s) this, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s div(Vec3s b) {
        return Glm.div((Vec3s) this, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s div(short b, Vec3s res) {
        return Glm.div(res, (Vec3s) this, (int) b, b, b);
    }

    public Vec3s div(int b, Vec3s res) {
        return Glm.div(res, (Vec3s) this, b, b, b);
    }

    public Vec3s div(short bX, short bY, short bZ, Vec3s res) {
        return Glm.div(res, (Vec3s) this, (int) bX, bY, bZ);
    }

    public Vec3s div(int bX, int bY, int bZ, Vec3s res) {
        return Glm.div(res, (Vec3s) this, bX, bY, bZ);
    }

    public Vec3s div(Vec3s b, Vec3s res) {
        return Glm.div(res, (Vec3s) this, (int) b.x, b.y, b.z);
    }

    public Vec3s incr_() {
        return Glm.incr_((Vec3s) this);
    }

    public Vec3s incr() {
        return Glm.incr((Vec3s) this);
    }

    public Vec3s incr(Vec3s res) {
        return Glm.incr(res, (Vec3s) this);
    }

    public Vec3s decr_() {
        return Glm.decr_((Vec3s) this);
    }

    public Vec3s decr() {
        return Glm.decr((Vec3s) this);
    }

    public Vec3s decr(Vec3s res) {
        return Glm.decr(res, (Vec3s) this);
    }
}
