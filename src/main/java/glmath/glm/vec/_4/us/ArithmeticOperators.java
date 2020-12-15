/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.us;

import glmath.glm.Glm;
import glmath.joou.UShort;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Short.BYTES;

    public UShort x = new UShort(), y = new UShort(), z = new UShort(), w = new UShort();

    public Vec4us add_(UShort b) {
        return Glm.add(new Vec4us(), (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us add_(short b) {
        return Glm.add(new Vec4us(), (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us add_(int b) {
        return Glm.add(new Vec4us(), (Vec4us) this, b, b, b, b);
    }

    public Vec4us add_(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.add(new Vec4us(), (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us add_(short bX, short bY, short bZ, short bW) {
        return Glm.add(new Vec4us(), (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4us(), (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us add_(Vec4us b) {
        return Glm.add(new Vec4us(), (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us add(UShort b) {
        return Glm.add((Vec4us) this, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us add(short b) {
        return Glm.add((Vec4us) this, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us add(int b) {
        return Glm.add((Vec4us) this, (Vec4us) this, b, b, b, b);
    }

    public Vec4us add(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.add((Vec4us) this, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us add(short bX, short bY, short bZ, short bW) {
        return Glm.add((Vec4us) this, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4us) this, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us add(Vec4us b) {
        return Glm.add((Vec4us) this, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us add(UShort b, Vec4us res) {
        return Glm.add(res, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us add(short b, Vec4us res) {
        return Glm.add(res, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us add(int b, Vec4us res) {
        return Glm.add(res, (Vec4us) this, b, b, b, b);
    }

    public Vec4us add(UShort bX, UShort bY, UShort bZ, UShort bW, Vec4us res) {
        return Glm.add(res, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us add(short bX, short bY, short bZ, short bW, Vec4us res) {
        return Glm.add(res, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us add(int bX, int bY, int bZ, int bW, Vec4us res) {
        return Glm.add(res, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us add(Vec4us b, Vec4us res) {
        return Glm.add(res, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us sub_(UShort b) {
        return Glm.sub(new Vec4us(), (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us sub_(short b) {
        return Glm.sub(new Vec4us(), (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us sub_(int b) {
        return Glm.sub(new Vec4us(), (Vec4us) this, b, b, b, b);
    }

    public Vec4us sub_(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.sub(new Vec4us(), (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us sub_(short bX, short bY, short bZ, short bW) {
        return Glm.sub(new Vec4us(), (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4us(), (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us sub_(Vec4us b) {
        return Glm.sub(new Vec4us(), (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us sub(UShort b) {
        return Glm.sub((Vec4us) this, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us sub(short b) {
        return Glm.sub((Vec4us) this, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us sub(int b) {
        return Glm.sub((Vec4us) this, (Vec4us) this, b, b, b, b);
    }

    public Vec4us sub(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.sub((Vec4us) this, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us sub(short bX, short bY, short bZ, short bW) {
        return Glm.sub((Vec4us) this, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4us) this, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us sub(Vec4us b) {
        return Glm.sub((Vec4us) this, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us sub(UShort b, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us sub(short b, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us sub(int b, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, b, b, b, b);
    }

    public Vec4us sub(UShort bX, UShort bY, UShort bZ, UShort bW, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us sub(short bX, short bY, short bZ, short bW, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us sub(int bX, int bY, int bZ, int bW, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us sub(Vec4us b, Vec4us res) {
        return Glm.sub(res, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us mul_(UShort b) {
        return Glm.mul(new Vec4us(), (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us mul_(short b) {
        return Glm.mul(new Vec4us(), (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us mul_(int b) {
        return Glm.mul(new Vec4us(), (Vec4us) this, b, b, b, b);
    }

    public Vec4us mul_(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.mul(new Vec4us(), (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us mul_(short bX, short bY, short bZ, short bW) {
        return Glm.mul(new Vec4us(), (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4us(), (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us mul_(Vec4us b) {
        return Glm.mul(new Vec4us(), (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us mul(UShort b) {
        return Glm.mul((Vec4us) this, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us mul(short b) {
        return Glm.mul((Vec4us) this, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us mul(int b) {
        return Glm.mul((Vec4us) this, (Vec4us) this, b, b, b, b);
    }

    public Vec4us mul(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.mul((Vec4us) this, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us mul(short bX, short bY, short bZ, short bW) {
        return Glm.mul((Vec4us) this, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4us) this, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us mul(Vec4us b) {
        return Glm.mul((Vec4us) this, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us mul(UShort b, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us mul(short b, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us mul(int b, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, b, b, b, b);
    }

    public Vec4us mul(UShort bX, UShort bY, UShort bZ, UShort bW, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us mul(short bX, short bY, short bZ, short bW, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us mul(int bX, int bY, int bZ, int bW, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us mul(Vec4us b, Vec4us res) {
        return Glm.mul(res, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us div_(UShort b) {
        return Glm.div(new Vec4us(), (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us div_(short b) {
        return Glm.div(new Vec4us(), (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us div_(int b) {
        return Glm.div(new Vec4us(), (Vec4us) this, b, b, b, b);
    }

    public Vec4us div_(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.div(new Vec4us(), (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us div_(short bX, short bY, short bZ, short bW) {
        return Glm.div(new Vec4us(), (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4us(), (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us div_(Vec4us b) {
        return Glm.div(new Vec4us(), (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us div(UShort b) {
        return Glm.div((Vec4us) this, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us div(short b) {
        return Glm.div((Vec4us) this, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us div(int b) {
        return Glm.div((Vec4us) this, (Vec4us) this, b, b, b, b);
    }

    public Vec4us div(UShort bX, UShort bY, UShort bZ, UShort bW) {
        return Glm.div((Vec4us) this, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us div(short bX, short bY, short bZ, short bW) {
        return Glm.div((Vec4us) this, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4us) this, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us div(Vec4us b) {
        return Glm.div((Vec4us) this, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us div(UShort b, Vec4us res) {
        return Glm.div(res, (Vec4us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec4us div(short b, Vec4us res) {
        return Glm.div(res, (Vec4us) this, b & 0xffff, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec4us div(int b, Vec4us res) {
        return Glm.div(res, (Vec4us) this, b, b, b, b);
    }

    public Vec4us div(UShort bX, UShort bY, UShort bZ, UShort bW, Vec4us res) {
        return Glm.div(res, (Vec4us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff, bW.value & 0xffff);
    }

    public Vec4us div(short bX, short bY, short bZ, short bW, Vec4us res) {
        return Glm.div(res, (Vec4us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff, bW & 0xffff);
    }

    public Vec4us div(int bX, int bY, int bZ, int bW, Vec4us res) {
        return Glm.div(res, (Vec4us) this, bX, bY, bZ, bW);
    }

    public Vec4us div(Vec4us b, Vec4us res) {
        return Glm.div(res, (Vec4us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff, b.w.value & 0xffff);
    }

    public Vec4us incr_() {
        return Glm.incr_((Vec4us) this);
    }

    public Vec4us incr() {
        return Glm.incr((Vec4us) this);
    }

    public Vec4us incr(Vec4us res) {
        return Glm.incr(res, (Vec4us) this);
    }

    public Vec4us decr_() {
        return Glm.decr_((Vec4us) this);
    }

    public Vec4us decr() {
        return Glm.decr((Vec4us) this);
    }

    public Vec4us decr(Vec4us res) {
        return Glm.decr(res, (Vec4us) this);
    }
}
