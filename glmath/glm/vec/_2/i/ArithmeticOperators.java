/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.i;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Integer.BYTES;

    public int x, y;

    public Vec2i add_(int b) {
        return Glm.add(new Vec2i(), (Vec2i) this, b, b);
    }

    public Vec2i add_(int bX, int bY) {
        return Glm.add(new Vec2i(), (Vec2i) this, bX, bY);
    }

    public Vec2i add_(Vec2i b) {
        return Glm.add(new Vec2i(), (Vec2i) this, b.x, b.y);
    }

    public Vec2i add(int b) {
        return Glm.add((Vec2i) this, (Vec2i) this, b, b);
    }

    public Vec2i add(int bX, int bY) {
        return Glm.add((Vec2i) this, (Vec2i) this, bX, bY);
    }

    public Vec2i add(Vec2i b) {
        return Glm.add((Vec2i) this, (Vec2i) this, b.x, b.y);
    }

    public Vec2i add(int b, Vec2i res) {
        return Glm.add(res, (Vec2i) this, b, b);
    }

    public Vec2i add(int bX, int bY, Vec2i res) {
        return Glm.add(res, (Vec2i) this, bX, bY);
    }

    public Vec2i add(Vec2i b, Vec2i res) {
        return Glm.add(res, (Vec2i) this, b.x, b.y);
    }

    public Vec2i sub_(int b) {
        return Glm.sub(new Vec2i(), (Vec2i) this, b, b);
    }

    public Vec2i sub_(int bX, int bY) {
        return Glm.sub(new Vec2i(), (Vec2i) this, bX, bY);
    }

    public Vec2i sub_(Vec2i b) {
        return Glm.sub(new Vec2i(), (Vec2i) this, b.x, b.y);
    }

    public Vec2i sub(int b) {
        return Glm.sub((Vec2i) this, (Vec2i) this, b, b);
    }

    public Vec2i sub(int bX, int bY) {
        return Glm.sub((Vec2i) this, (Vec2i) this, bX, bY);
    }

    public Vec2i sub(Vec2i b) {
        return Glm.sub((Vec2i) this, (Vec2i) this, b.x, b.y);
    }

    public Vec2i sub(int b, Vec2i res) {
        return Glm.sub(res, (Vec2i) this, b, b);
    }

    public Vec2i sub(int bX, int bY, Vec2i res) {
        return Glm.sub(res, (Vec2i) this, bX, bY);
    }

    public Vec2i sub(Vec2i b, Vec2i res) {
        return Glm.sub(res, (Vec2i) this, b.x, b.y);
    }

    public Vec2i mul_(int b) {
        return Glm.mul(new Vec2i(), (Vec2i) this, b, b);
    }

    public Vec2i mul_(int bX, int bY) {
        return Glm.mul(new Vec2i(), (Vec2i) this, bX, bY);
    }

    public Vec2i mul_(Vec2i b) {
        return Glm.mul(new Vec2i(), (Vec2i) this, b.x, b.y);
    }

    public Vec2i mul(int b) {
        return Glm.mul((Vec2i) this, (Vec2i) this, b, b);
    }

    public Vec2i mul(int bX, int bY) {
        return Glm.mul((Vec2i) this, (Vec2i) this, bX, bY);
    }

    public Vec2i mul(Vec2i b) {
        return Glm.mul((Vec2i) this, (Vec2i) this, b.x, b.y);
    }

    public Vec2i mul(int b, Vec2i res) {
        return Glm.mul(res, (Vec2i) this, b, b);
    }

    public Vec2i mul(int bX, int bY, Vec2i res) {
        return Glm.mul(res, (Vec2i) this, bX, bY);
    }

    public Vec2i mul(Vec2i b, Vec2i res) {
        return Glm.mul(res, (Vec2i) this, b.x, b.y);
    }

    public Vec2i div_(int b) {
        return Glm.div(new Vec2i(), (Vec2i) this, b, b);
    }

    public Vec2i div_(int bX, int bY) {
        return Glm.div(new Vec2i(), (Vec2i) this, bX, bY);
    }

    public Vec2i div_(Vec2i b) {
        return Glm.div(new Vec2i(), (Vec2i) this, b.x, b.y);
    }

    public Vec2i div(int b) {
        return Glm.div((Vec2i) this, (Vec2i) this, b, b);
    }

    public Vec2i div(int bX, int bY) {
        return Glm.div((Vec2i) this, (Vec2i) this, bX, bY);
    }

    public Vec2i div(Vec2i b) {
        return Glm.div((Vec2i) this, (Vec2i) this, b.x, b.y);
    }

    public Vec2i div(int b, Vec2i res) {
        return Glm.div(res, (Vec2i) this, b, b);
    }

    public Vec2i div(int bX, int bY, Vec2i res) {
        return Glm.div(res, (Vec2i) this, bX, bY);
    }

    public Vec2i div(Vec2i b, Vec2i res) {
        return Glm.div(res, (Vec2i) this, b.x, b.y);
    }

    public Vec2i incr_() {
        return Glm.incr_((Vec2i) this);
    }

    public Vec2i incr() {
        return Glm.incr((Vec2i) this);
    }

    public Vec2i incr(Vec2i res) {
        return Glm.incr(res, (Vec2i) this);
    }

    public Vec2i decr_() {
        return Glm.decr_((Vec2i) this);
    }

    public Vec2i decr() {
        return Glm.decr((Vec2i) this);
    }

    public Vec2i decr(Vec2i res) {
        return Glm.decr(res, (Vec2i) this);
    }
}
