/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.s;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Short.BYTES;

    public short x, y, z, w;

    public Vec4s add_(short b) {
        return Glm.add(new Vec4s(), (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s add_(int b) {
        return Glm.add(new Vec4s(), (Vec4s) this, b, b, b, b);
    }

    public Vec4s add_(short bX, short bY, short bZ, short bW) {
        return Glm.add(new Vec4s(), (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4s(), (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s add_(Vec4s b) {
        return Glm.add(new Vec4s(), (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s add(short b) {
        return Glm.add((Vec4s) this, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s add(int b) {
        return Glm.add((Vec4s) this, (Vec4s) this, b, b, b, b);
    }

    public Vec4s add(short bX, short bY, short bZ, short bW) {
        return Glm.add((Vec4s) this, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4s) this, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s add(Vec4s b) {
        return Glm.add((Vec4s) this, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s add(short b, Vec4s res) {
        return Glm.add(res, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s add(int b, Vec4s res) {
        return Glm.add(res, (Vec4s) this, b, b, b, b);
    }

    public Vec4s add(short bX, short bY, short bZ, short bW, Vec4s res) {
        return Glm.add(res, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s add(int bX, int bY, int bZ, int bW, Vec4s res) {
        return Glm.add(res, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s add(Vec4s b, Vec4s res) {
        return Glm.add(res, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s sub_(short b) {
        return Glm.sub(new Vec4s(), (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s sub_(int b) {
        return Glm.sub(new Vec4s(), (Vec4s) this, b, b, b, b);
    }

    public Vec4s sub_(short bX, short bY, short bZ, short bW) {
        return Glm.sub(new Vec4s(), (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4s(), (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s sub_(Vec4s b) {
        return Glm.sub(new Vec4s(), (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s sub(short b) {
        return Glm.sub((Vec4s) this, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s sub(int b) {
        return Glm.sub((Vec4s) this, (Vec4s) this, b, b, b, b);
    }

    public Vec4s sub(short bX, short bY, short bZ, short bW) {
        return Glm.sub((Vec4s) this, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4s) this, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s sub(Vec4s b) {
        return Glm.sub((Vec4s) this, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s sub(short b, Vec4s res) {
        return Glm.sub(res, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s sub(int b, Vec4s res) {
        return Glm.sub(res, (Vec4s) this, b, b, b, b);
    }

    public Vec4s sub(short bX, short bY, short bZ, short bW, Vec4s res) {
        return Glm.sub(res, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s sub(int bX, int bY, int bZ, int bW, Vec4s res) {
        return Glm.sub(res, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s sub(Vec4s b, Vec4s res) {
        return Glm.sub(res, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s mul_(short b) {
        return Glm.mul(new Vec4s(), (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s mul_(int b) {
        return Glm.mul(new Vec4s(), (Vec4s) this, b, b, b, b);
    }

    public Vec4s mul_(short bX, short bY, short bZ, short bW) {
        return Glm.mul(new Vec4s(), (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4s(), (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s mul_(Vec4s b) {
        return Glm.mul(new Vec4s(), (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s mul(short b) {
        return Glm.mul((Vec4s) this, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s mul(int b) {
        return Glm.mul((Vec4s) this, (Vec4s) this, b, b, b, b);
    }

    public Vec4s mul(short bX, short bY, short bZ, short bW) {
        return Glm.mul((Vec4s) this, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4s) this, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s mul(Vec4s b) {
        return Glm.mul((Vec4s) this, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s mul(short b, Vec4s res) {
        return Glm.mul(res, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s mul(int b, Vec4s res) {
        return Glm.mul(res, (Vec4s) this, b, b, b, b);
    }

    public Vec4s mul(short bX, short bY, short bZ, short bW, Vec4s res) {
        return Glm.mul(res, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s mul(int bX, int bY, int bZ, int bW, Vec4s res) {
        return Glm.mul(res, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s mul(Vec4s b, Vec4s res) {
        return Glm.mul(res, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s div_(short b) {
        return Glm.div(new Vec4s(), (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s div_(int b) {
        return Glm.div(new Vec4s(), (Vec4s) this, b, b, b, b);
    }

    public Vec4s div_(short bX, short bY, short bZ, short bW) {
        return Glm.div(new Vec4s(), (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4s(), (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s div_(Vec4s b) {
        return Glm.div(new Vec4s(), (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s div(short b) {
        return Glm.div((Vec4s) this, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s div(int b) {
        return Glm.div((Vec4s) this, (Vec4s) this, b, b, b, b);
    }

    public Vec4s div(short bX, short bY, short bZ, short bW) {
        return Glm.div((Vec4s) this, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4s) this, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s div(Vec4s b) {
        return Glm.div((Vec4s) this, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s div(short b, Vec4s res) {
        return Glm.div(res, (Vec4s) this, (int) b, b, b, b);
    }

    public Vec4s div(int b, Vec4s res) {
        return Glm.div(res, (Vec4s) this, b, b, b, b);
    }

    public Vec4s div(short bX, short bY, short bZ, short bW, Vec4s res) {
        return Glm.div(res, (Vec4s) this, (int) bX, bY, bZ, bW);
    }

    public Vec4s div(int bX, int bY, int bZ, int bW, Vec4s res) {
        return Glm.div(res, (Vec4s) this, bX, bY, bZ, bW);
    }

    public Vec4s div(Vec4s b, Vec4s res) {
        return Glm.div(res, (Vec4s) this, (int) b.x, b.y, b.z, b.w);
    }

    public Vec4s incr_() {
        return Glm.incr_((Vec4s) this);
    }

    public Vec4s incr() {
        return Glm.incr((Vec4s) this);
    }

    public Vec4s incr(Vec4s res) {
        return Glm.incr(res, (Vec4s) this);
    }

    public Vec4s decr_() {
        return Glm.decr_((Vec4s) this);
    }

    public Vec4s decr() {
        return Glm.decr((Vec4s) this);
    }

    public Vec4s decr(Vec4s res) {
        return Glm.decr(res, (Vec4s) this);
    }
}
