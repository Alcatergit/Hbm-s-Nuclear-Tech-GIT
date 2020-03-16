/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.us;

import glmath.glm.Glm;
import glmath.joou.UShort;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Short.BYTES;

    public UShort x = new UShort(), y = new UShort();

    public Vec2us add_(UShort b) {
        return Glm.add(new Vec2us(), (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us add_(short b) {
        return Glm.add(new Vec2us(), (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us add_(int b) {
        return Glm.add(new Vec2us(), (Vec2us) this, b, b);
    }

    public Vec2us add_(UShort bX, UShort bY) {
        return Glm.add(new Vec2us(), (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us add_(short bX, short bY) {
        return Glm.add(new Vec2us(), (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us add_(int bX, int bY) {
        return Glm.add(new Vec2us(), (Vec2us) this, bX, bY);
    }

    public Vec2us add_(Vec2us b) {
        return Glm.add(new Vec2us(), (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us add(UShort b) {
        return Glm.add((Vec2us) this, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us add(short b) {
        return Glm.add((Vec2us) this, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us add(int b) {
        return Glm.add((Vec2us) this, (Vec2us) this, b, b);
    }

    public Vec2us add(UShort bX, UShort bY) {
        return Glm.add((Vec2us) this, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us add(short bX, short bY) {
        return Glm.add((Vec2us) this, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us add(int bX, int bY) {
        return Glm.add((Vec2us) this, (Vec2us) this, bX, bY);
    }

    public Vec2us add(Vec2us b) {
        return Glm.add((Vec2us) this, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us add(UShort b, Vec2us res) {
        return Glm.add(res, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us add(short b, Vec2us res) {
        return Glm.add(res, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us add(int b, Vec2us res) {
        return Glm.add(res, (Vec2us) this, b, b);
    }

    public Vec2us add(UShort bX, UShort bY, Vec2us res) {
        return Glm.add(res, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us add(short bX, short bY, Vec2us res) {
        return Glm.add(res, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us add(int bX, int bY, Vec2us res) {
        return Glm.add(res, (Vec2us) this, bX, bY);
    }

    public Vec2us add(Vec2us b, Vec2us res) {
        return Glm.add(res, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us sub_(UShort b) {
        return Glm.sub(new Vec2us(), (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us sub_(short b) {
        return Glm.sub(new Vec2us(), (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us sub_(int b) {
        return Glm.sub(new Vec2us(), (Vec2us) this, b, b);
    }

    public Vec2us sub_(UShort bX, UShort bY) {
        return Glm.sub(new Vec2us(), (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us sub_(short bX, short bY) {
        return Glm.sub(new Vec2us(), (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us sub_(int bX, int bY) {
        return Glm.sub(new Vec2us(), (Vec2us) this, bX, bY);
    }

    public Vec2us sub_(Vec2us b) {
        return Glm.sub(new Vec2us(), (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us sub(UShort b) {
        return Glm.sub((Vec2us) this, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us sub(short b) {
        return Glm.sub((Vec2us) this, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us sub(int b) {
        return Glm.sub((Vec2us) this, (Vec2us) this, b, b);
    }

    public Vec2us sub(UShort bX, UShort bY) {
        return Glm.sub((Vec2us) this, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us sub(short bX, short bY) {
        return Glm.sub((Vec2us) this, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us sub(int bX, int bY) {
        return Glm.sub((Vec2us) this, (Vec2us) this, bX, bY);
    }

    public Vec2us sub(Vec2us b) {
        return Glm.sub((Vec2us) this, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us sub(UShort b, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us sub(short b, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us sub(int b, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, b, b);
    }

    public Vec2us sub(UShort bX, UShort bY, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us sub(short bX, short bY, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us sub(int bX, int bY, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, bX, bY);
    }

    public Vec2us sub(Vec2us b, Vec2us res) {
        return Glm.sub(res, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us mul_(UShort b) {
        return Glm.mul(new Vec2us(), (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us mul_(short b) {
        return Glm.mul(new Vec2us(), (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us mul_(int b) {
        return Glm.mul(new Vec2us(), (Vec2us) this, b, b);
    }

    public Vec2us mul_(UShort bX, UShort bY) {
        return Glm.mul(new Vec2us(), (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us mul_(short bX, short bY) {
        return Glm.mul(new Vec2us(), (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us mul_(int bX, int bY) {
        return Glm.mul(new Vec2us(), (Vec2us) this, bX, bY);
    }

    public Vec2us mul_(Vec2us b) {
        return Glm.mul(new Vec2us(), (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us mul(UShort b) {
        return Glm.mul((Vec2us) this, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us mul(short b) {
        return Glm.mul((Vec2us) this, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us mul(int b) {
        return Glm.mul((Vec2us) this, (Vec2us) this, b, b);
    }

    public Vec2us mul(UShort bX, UShort bY) {
        return Glm.mul((Vec2us) this, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us mul(short bX, short bY) {
        return Glm.mul((Vec2us) this, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us mul(int bX, int bY) {
        return Glm.mul((Vec2us) this, (Vec2us) this, bX, bY);
    }

    public Vec2us mul(Vec2us b) {
        return Glm.mul((Vec2us) this, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us mul(UShort b, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us mul(short b, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us mul(int b, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, b, b);
    }

    public Vec2us mul(UShort bX, UShort bY, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us mul(short bX, short bY, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us mul(int bX, int bY, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, bX, bY);
    }

    public Vec2us mul(Vec2us b, Vec2us res) {
        return Glm.mul(res, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us div_(UShort b) {
        return Glm.div(new Vec2us(), (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us div_(short b) {
        return Glm.div(new Vec2us(), (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us div_(int b) {
        return Glm.div(new Vec2us(), (Vec2us) this, b, b);
    }

    public Vec2us div_(UShort bX, UShort bY) {
        return Glm.div(new Vec2us(), (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us div_(short bX, short bY) {
        return Glm.div(new Vec2us(), (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us div_(int bX, int bY) {
        return Glm.div(new Vec2us(), (Vec2us) this, bX, bY);
    }

    public Vec2us div_(Vec2us b) {
        return Glm.div(new Vec2us(), (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us div(UShort b) {
        return Glm.div((Vec2us) this, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us div(short b) {
        return Glm.div((Vec2us) this, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us div(int b) {
        return Glm.div((Vec2us) this, (Vec2us) this, b, b);
    }

    public Vec2us div(UShort bX, UShort bY) {
        return Glm.div((Vec2us) this, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us div(short bX, short bY) {
        return Glm.div((Vec2us) this, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us div(int bX, int bY) {
        return Glm.div((Vec2us) this, (Vec2us) this, bX, bY);
    }

    public Vec2us div(Vec2us b) {
        return Glm.div((Vec2us) this, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us div(UShort b, Vec2us res) {
        return Glm.div(res, (Vec2us) this, b.value & 0xffff, b.value & 0xffff);
    }

    public Vec2us div(short b, Vec2us res) {
        return Glm.div(res, (Vec2us) this, b & 0xffff, b & 0xffff);
    }

    public Vec2us div(int b, Vec2us res) {
        return Glm.div(res, (Vec2us) this, b, b);
    }

    public Vec2us div(UShort bX, UShort bY, Vec2us res) {
        return Glm.div(res, (Vec2us) this, bX.value & 0xffff, bY.value & 0xdd);
    }

    public Vec2us div(short bX, short bY, Vec2us res) {
        return Glm.div(res, (Vec2us) this, bX & 0xffff, bY & 0xffff);
    }

    public Vec2us div(int bX, int bY, Vec2us res) {
        return Glm.div(res, (Vec2us) this, bX, bY);
    }

    public Vec2us div(Vec2us b, Vec2us res) {
        return Glm.div(res, (Vec2us) this, b.x.value & 0xffff, b.y.value & 0xffff);
    }

    public Vec2us incr_() {
        return Glm.incr_((Vec2us) this);
    }

    public Vec2us incr() {
        return Glm.incr((Vec2us) this);
    }

    public Vec2us incr(Vec2us res) {
        return Glm.incr(res, (Vec2us) this);
    }

    public Vec2us decr_() {
        return Glm.decr_((Vec2us) this);
    }

    public Vec2us decr() {
        return Glm.decr((Vec2us) this);
    }

    public Vec2us decr(Vec2us res) {
        return Glm.decr(res, (Vec2us) this);
    }
}
