/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.us;

import glmath.glm.Glm;
import glmath.joou.UShort;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Short.BYTES;

    public UShort x = new UShort(), y = new UShort(), z = new UShort();

    public Vec3us add_(UShort b) {
        return Glm.add(new Vec3us(), (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us add_(short b) {
        return Glm.add(new Vec3us(), (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us add_(int b) {
        return Glm.add(new Vec3us(), (Vec3us) this, b, b, b);
    }

    public Vec3us add_(UShort bX, UShort bY, UShort bZ) {
        return Glm.add(new Vec3us(), (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us add_(short bX, short bY, short bZ) {
        return Glm.add(new Vec3us(), (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3us(), (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us add_(Vec3us b) {
        return Glm.add(new Vec3us(), (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us add(UShort b) {
        return Glm.add((Vec3us) this, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us add(short b) {
        return Glm.add((Vec3us) this, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us add(int b) {
        return Glm.add((Vec3us) this, (Vec3us) this, b, b, b);
    }

    public Vec3us add(UShort bX, UShort bY, UShort bZ) {
        return Glm.add((Vec3us) this, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us add(short bX, short bY, short bZ) {
        return Glm.add((Vec3us) this, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us add(int bX, int bY, int bZ) {
        return Glm.add((Vec3us) this, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us add(Vec3us b) {
        return Glm.add((Vec3us) this, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us add(UShort b, Vec3us res) {
        return Glm.add(res, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us add(short b, Vec3us res) {
        return Glm.add(res, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us add(int b, Vec3us res) {
        return Glm.add(res, (Vec3us) this, b, b, b);
    }

    public Vec3us add(UShort bX, UShort bY, UShort bZ, Vec3us res) {
        return Glm.add(res, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us add(short bX, short bY, short bZ, Vec3us res) {
        return Glm.add(res, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us add(int bX, int bY, int bZ, Vec3us res) {
        return Glm.add(res, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us add(Vec3us b, Vec3us res) {
        return Glm.add(res, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us sub_(UShort b) {
        return Glm.sub(new Vec3us(), (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us sub_(short b) {
        return Glm.sub(new Vec3us(), (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us sub_(int b) {
        return Glm.sub(new Vec3us(), (Vec3us) this, b, b, b);
    }

    public Vec3us sub_(UShort bX, UShort bY, UShort bZ) {
        return Glm.sub(new Vec3us(), (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us sub_(short bX, short bY, short bZ) {
        return Glm.sub(new Vec3us(), (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3us(), (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us sub_(Vec3us b) {
        return Glm.sub(new Vec3us(), (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us sub(UShort b) {
        return Glm.sub((Vec3us) this, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us sub(short b) {
        return Glm.sub((Vec3us) this, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us sub(int b) {
        return Glm.sub((Vec3us) this, (Vec3us) this, b, b, b);
    }

    public Vec3us sub(UShort bX, UShort bY, UShort bZ) {
        return Glm.sub((Vec3us) this, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us sub(short bX, short bY, short bZ) {
        return Glm.sub((Vec3us) this, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3us) this, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us sub(Vec3us b) {
        return Glm.sub((Vec3us) this, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us sub(UShort b, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us sub(short b, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us sub(int b, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, b, b, b);
    }

    public Vec3us sub(UShort bX, UShort bY, UShort bZ, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us sub(short bX, short bY, short bZ, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us sub(int bX, int bY, int bZ, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us sub(Vec3us b, Vec3us res) {
        return Glm.sub(res, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us mul_(UShort b) {
        return Glm.mul(new Vec3us(), (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us mul_(short b) {
        return Glm.mul(new Vec3us(), (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us mul_(int b) {
        return Glm.mul(new Vec3us(), (Vec3us) this, b, b, b);
    }

    public Vec3us mul_(UShort bX, UShort bY, UShort bZ) {
        return Glm.mul(new Vec3us(), (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us mul_(short bX, short bY, short bZ) {
        return Glm.mul(new Vec3us(), (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3us(), (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us mul_(Vec3us b) {
        return Glm.mul(new Vec3us(), (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us mul(UShort b) {
        return Glm.mul((Vec3us) this, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us mul(short b) {
        return Glm.mul((Vec3us) this, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us mul(int b) {
        return Glm.mul((Vec3us) this, (Vec3us) this, b, b, b);
    }

    public Vec3us mul(UShort bX, UShort bY, UShort bZ) {
        return Glm.mul((Vec3us) this, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us mul(short bX, short bY, short bZ) {
        return Glm.mul((Vec3us) this, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3us) this, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us mul(Vec3us b) {
        return Glm.mul((Vec3us) this, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us mul(UShort b, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us mul(short b, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us mul(int b, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, b, b, b);
    }

    public Vec3us mul(UShort bX, UShort bY, UShort bZ, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us mul(short bX, short bY, short bZ, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us mul(int bX, int bY, int bZ, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us mul(Vec3us b, Vec3us res) {
        return Glm.mul(res, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us div_(UShort b) {
        return Glm.div(new Vec3us(), (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us div_(short b) {
        return Glm.div(new Vec3us(), (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us div_(int b) {
        return Glm.div(new Vec3us(), (Vec3us) this, b, b, b);
    }

    public Vec3us div_(UShort bX, UShort bY, UShort bZ) {
        return Glm.div(new Vec3us(), (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us div_(short bX, short bY, short bZ) {
        return Glm.div(new Vec3us(), (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3us(), (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us div_(Vec3us b) {
        return Glm.div(new Vec3us(), (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us div(UShort b) {
        return Glm.div((Vec3us) this, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us div(short b) {
        return Glm.div((Vec3us) this, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us div(int b) {
        return Glm.div((Vec3us) this, (Vec3us) this, b, b, b);
    }

    public Vec3us div(UShort bX, UShort bY, UShort bZ) {
        return Glm.div((Vec3us) this, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us div(short bX, short bY, short bZ) {
        return Glm.div((Vec3us) this, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us div(int bX, int bY, int bZ) {
        return Glm.div((Vec3us) this, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us div(Vec3us b) {
        return Glm.div((Vec3us) this, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us div(UShort b, Vec3us res) {
        return Glm.div(res, (Vec3us) this, b.value & 0xffff, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec3us div(short b, Vec3us res) {
        return Glm.div(res, (Vec3us) this, b & 0xffff, b & 0xffff, b & 0xffff);
    }

    public Vec3us div(int b, Vec3us res) {
        return Glm.div(res, (Vec3us) this, b, b, b);
    }

    public Vec3us div(UShort bX, UShort bY, UShort bZ, Vec3us res) {
        return Glm.div(res, (Vec3us) this, bX.value & 0xffff, bY.value & 0xffff, bZ.value & 0xffff);
    }

    public Vec3us div(short bX, short bY, short bZ, Vec3us res) {
        return Glm.div(res, (Vec3us) this, bX & 0xffff, bY & 0xffff, bZ & 0xffff);
    }

    public Vec3us div(int bX, int bY, int bZ, Vec3us res) {
        return Glm.div(res, (Vec3us) this, bX, bY, bZ);
    }

    public Vec3us div(Vec3us b, Vec3us res) {
        return Glm.div(res, (Vec3us) this, b.x.value & 0xffff, b.y.value & 0xffff, b.z.value & 0xffff);
    }

    public Vec3us incr_() {
        return Glm.incr_((Vec3us) this);
    }

    public Vec3us incr() {
        return Glm.incr((Vec3us) this);
    }

    public Vec3us incr(Vec3us res) {
        return Glm.incr(res, (Vec3us) this);
    }

    public Vec3us decr_() {
        return Glm.decr_((Vec3us) this);
    }

    public Vec3us decr() {
        return Glm.decr((Vec3us) this);
    }

    public Vec3us decr(Vec3us res) {
        return Glm.decr(res, (Vec3us) this);
    }
}
