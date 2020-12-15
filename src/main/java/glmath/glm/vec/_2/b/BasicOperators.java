/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.b;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class BasicOperators {

    public static final int SIZE = 2 * Byte.BYTES;

    public byte x, y;

    public Vec2b add_(byte b) {
        return Glm.add(new Vec2b(), (Vec2b) this, (int) b, b);
    }

    public Vec2b add_(int b) {
        return Glm.add(new Vec2b(), (Vec2b) this, b, b);
    }

    public Vec2b add_(byte bX, byte bY) {
        return Glm.add(new Vec2b(), (Vec2b) this, (int) bX, bY);
    }

    public Vec2b add_(int bX, int bY) {
        return Glm.add(new Vec2b(), (Vec2b) this, bX, bY);
    }

    public Vec2b add_(Vec2b b) {
        return Glm.add(new Vec2b(), (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b add(byte b) {
        return Glm.add((Vec2b) this, (Vec2b) this, (int) b, b);
    }

    public Vec2b add(int b) {
        return Glm.add((Vec2b) this, (Vec2b) this, b, b);
    }

    public Vec2b add(byte bX, byte bY) {
        return Glm.add((Vec2b) this, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b add(int bX, int bY) {
        return Glm.add((Vec2b) this, (Vec2b) this, bX, bY);
    }

    public Vec2b add(Vec2b b) {
        return Glm.add((Vec2b) this, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b add(byte b, Vec2b res) {
        return Glm.add(res, (Vec2b) this, (int) b, b);
    }

    public Vec2b add(int b, Vec2b res) {
        return Glm.add(res, (Vec2b) this, b, b);
    }

    public Vec2b add(byte bX, byte bY, Vec2b res) {
        return Glm.add(res, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b add(int bX, int bY, Vec2b res) {
        return Glm.add(res, (Vec2b) this, bX, bY);
    }

    public Vec2b add(Vec2b b, Vec2b res) {
        return Glm.add(res, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b sub_(byte b) {
        return Glm.sub(new Vec2b(), (Vec2b) this, (int) b, b);
    }

    public Vec2b sub_(int b) {
        return Glm.sub(new Vec2b(), (Vec2b) this, b, b);
    }

    public Vec2b sub_(byte bX, byte bY) {
        return Glm.sub(new Vec2b(), (Vec2b) this, (int) bX, bY);
    }

    public Vec2b sub_(int bX, int bY) {
        return Glm.sub(new Vec2b(), (Vec2b) this, bX, bY);
    }

    public Vec2b sub_(Vec2b b) {
        return Glm.sub(new Vec2b(), (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b sub(byte b) {
        return Glm.sub((Vec2b) this, (Vec2b) this, (int) b, b);
    }

    public Vec2b sub(int b) {
        return Glm.sub((Vec2b) this, (Vec2b) this, b, b);
    }

    public Vec2b sub(byte bX, byte bY) {
        return Glm.sub((Vec2b) this, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b sub(int bX, int bY) {
        return Glm.sub((Vec2b) this, (Vec2b) this, bX, bY);
    }

    public Vec2b sub(Vec2b b) {
        return Glm.sub((Vec2b) this, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b sub(byte b, Vec2b res) {
        return Glm.sub(res, (Vec2b) this, (int) b, b);
    }

    public Vec2b sub(int b, Vec2b res) {
        return Glm.sub(res, (Vec2b) this, b, b);
    }

    public Vec2b sub(byte bX, byte bY, Vec2b res) {
        return Glm.sub(res, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b sub(int bX, int bY, Vec2b res) {
        return Glm.sub(res, (Vec2b) this, bX, bY);
    }

    public Vec2b sub(Vec2b b, Vec2b res) {
        return Glm.sub(res, (Vec2b) this, (int) b.x, b.y);
    }
    
    public Vec2b mul_(byte b) {
        return Glm.mul(new Vec2b(), (Vec2b) this, (int) b, b);
    }

    public Vec2b mul_(int b) {
        return Glm.mul(new Vec2b(), (Vec2b) this, b, b);
    }

    public Vec2b mul_(byte bX, byte bY) {
        return Glm.mul(new Vec2b(), (Vec2b) this, (int) bX, bY);
    }

    public Vec2b mul_(int bX, int bY) {
        return Glm.mul(new Vec2b(), (Vec2b) this, bX, bY);
    }

    public Vec2b mul_(Vec2b b) {
        return Glm.mul(new Vec2b(), (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b mul(byte b) {
        return Glm.mul((Vec2b) this, (Vec2b) this, (int) b, b);
    }

    public Vec2b mul(int b) {
        return Glm.mul((Vec2b) this, (Vec2b) this, b, b);
    }

    public Vec2b mul(byte bX, byte bY) {
        return Glm.mul((Vec2b) this, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b mul(int bX, int bY) {
        return Glm.mul((Vec2b) this, (Vec2b) this, bX, bY);
    }

    public Vec2b mul(Vec2b b) {
        return Glm.mul((Vec2b) this, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b mul(byte b, Vec2b res) {
        return Glm.mul(res, (Vec2b) this, (int) b, b);
    }

    public Vec2b mul(int b, Vec2b res) {
        return Glm.mul(res, (Vec2b) this, b, b);
    }

    public Vec2b mul(byte bX, byte bY, Vec2b res) {
        return Glm.mul(res, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b mul(int bX, int bY, Vec2b res) {
        return Glm.mul(res, (Vec2b) this, bX, bY);
    }

    public Vec2b mul(Vec2b b, Vec2b res) {
        return Glm.mul(res, (Vec2b) this, (int) b.x, b.y);
    }
    
    public Vec2b div_(byte b) {
        return Glm.div(new Vec2b(), (Vec2b) this, (int) b, b);
    }

    public Vec2b div_(int b) {
        return Glm.div(new Vec2b(), (Vec2b) this, b, b);
    }

    public Vec2b div_(byte bX, byte bY) {
        return Glm.div(new Vec2b(), (Vec2b) this, (int) bX, bY);
    }

    public Vec2b div_(int bX, int bY) {
        return Glm.div(new Vec2b(), (Vec2b) this, bX, bY);
    }

    public Vec2b div_(Vec2b b) {
        return Glm.div(new Vec2b(), (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b div(byte b) {
        return Glm.div((Vec2b) this, (Vec2b) this, (int) b, b);
    }

    public Vec2b div(int b) {
        return Glm.div((Vec2b) this, (Vec2b) this, b, b);
    }

    public Vec2b div(byte bX, byte bY) {
        return Glm.div((Vec2b) this, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b div(int bX, int bY) {
        return Glm.div((Vec2b) this, (Vec2b) this, bX, bY);
    }

    public Vec2b div(Vec2b b) {
        return Glm.div((Vec2b) this, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b div(byte b, Vec2b res) {
        return Glm.div(res, (Vec2b) this, (int) b, b);
    }

    public Vec2b div(int b, Vec2b res) {
        return Glm.div(res, (Vec2b) this, b, b);
    }

    public Vec2b div(byte bX, byte bY, Vec2b res) {
        return Glm.div(res, (Vec2b) this, (int) bX, bY);
    }

    public Vec2b div(int bX, int bY, Vec2b res) {
        return Glm.div(res, (Vec2b) this, bX, bY);
    }

    public Vec2b div(Vec2b b, Vec2b res) {
        return Glm.div(res, (Vec2b) this, (int) b.x, b.y);
    }

    public Vec2b incr_() {
        return Glm.incr_((Vec2b) this);
    }

    public Vec2b incr() {
        return Glm.incr((Vec2b) this);
    }

    public Vec2b incr(Vec2b res) {
        return Glm.incr(res, (Vec2b) this);
    }

    public Vec2b decr_() {
        return Glm.decr_((Vec2b) this);
    }

    public Vec2b decr() {
        return Glm.decr((Vec2b) this);
    }

    public Vec2b decr(Vec2b res) {
        return Glm.decr(res, (Vec2b) this);
    }
}
