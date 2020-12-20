/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.l;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Long.BYTES;

    public long x, y;

    public Vec2l add_(long b) {
        return Glm.add(new Vec2l(), (Vec2l) this, b, b);
    }

    public Vec2l add_(long bX, long bY) {
        return Glm.add(new Vec2l(), (Vec2l) this, bX, bY);
    }

    public Vec2l add_(Vec2l b) {
        return Glm.add(new Vec2l(), (Vec2l) this, b.x, b.y);
    }

    public Vec2l add(long b) {
        return Glm.add((Vec2l) this, (Vec2l) this, b, b);
    }

    public Vec2l add(long bX, long bY) {
        return Glm.add((Vec2l) this, (Vec2l) this, bX, bY);
    }

    public Vec2l add(Vec2l b) {
        return Glm.add((Vec2l) this, (Vec2l) this, b.x, b.y);
    }

    public Vec2l add(long b, Vec2l res) {
        return Glm.add(res, (Vec2l) this, b, b);
    }

    public Vec2l add(long bX, long bY, Vec2l res) {
        return Glm.add(res, (Vec2l) this, bX, bY);
    }

    public Vec2l add(Vec2l b, Vec2l res) {
        return Glm.add(res, (Vec2l) this, b.x, b.y);
    }

    public Vec2l sub_(long b) {
        return Glm.sub(new Vec2l(), (Vec2l) this, b, b);
    }

    public Vec2l sub_(long bX, long bY) {
        return Glm.sub(new Vec2l(), (Vec2l) this, bX, bY);
    }

    public Vec2l sub_(Vec2l b) {
        return Glm.sub(new Vec2l(), (Vec2l) this, b.x, b.y);
    }

    public Vec2l sub(long b) {
        return Glm.sub((Vec2l) this, (Vec2l) this, b, b);
    }

    public Vec2l sub(long bX, long bY) {
        return Glm.sub((Vec2l) this, (Vec2l) this, bX, bY);
    }

    public Vec2l sub(Vec2l b) {
        return Glm.sub((Vec2l) this, (Vec2l) this, b.x, b.y);
    }

    public Vec2l sub(long b, Vec2l res) {
        return Glm.sub(res, (Vec2l) this, b, b);
    }

    public Vec2l sub(long bX, long bY, Vec2l res) {
        return Glm.sub(res, (Vec2l) this, bX, bY);
    }

    public Vec2l sub(Vec2l b, Vec2l res) {
        return Glm.sub(res, (Vec2l) this, b.x, b.y);
    }

    public Vec2l mul_(long b) {
        return Glm.mul(new Vec2l(), (Vec2l) this, b, b);
    }

    public Vec2l mul_(long bX, long bY) {
        return Glm.mul(new Vec2l(), (Vec2l) this, bX, bY);
    }

    public Vec2l mul_(Vec2l b) {
        return Glm.mul(new Vec2l(), (Vec2l) this, b.x, b.y);
    }

    public Vec2l mul(long b) {
        return Glm.mul((Vec2l) this, (Vec2l) this, b, b);
    }

    public Vec2l mul(long bX, long bY) {
        return Glm.mul((Vec2l) this, (Vec2l) this, bX, bY);
    }

    public Vec2l mul(Vec2l b) {
        return Glm.mul((Vec2l) this, (Vec2l) this, b.x, b.y);
    }

    public Vec2l mul(long b, Vec2l res) {
        return Glm.mul(res, (Vec2l) this, b, b);
    }

    public Vec2l mul(long bX, long bY, Vec2l res) {
        return Glm.mul(res, (Vec2l) this, bX, bY);
    }

    public Vec2l mul(Vec2l b, Vec2l res) {
        return Glm.mul(res, (Vec2l) this, b.x, b.y);
    }

    public Vec2l div_(long b) {
        return Glm.div(new Vec2l(), (Vec2l) this, b, b);
    }

    public Vec2l div_(long bX, long bY) {
        return Glm.div(new Vec2l(), (Vec2l) this, bX, bY);
    }

    public Vec2l div_(Vec2l b) {
        return Glm.div(new Vec2l(), (Vec2l) this, b.x, b.y);
    }

    public Vec2l div(long b) {
        return Glm.div((Vec2l) this, (Vec2l) this, b, b);
    }

    public Vec2l div(long bX, long bY) {
        return Glm.div((Vec2l) this, (Vec2l) this, bX, bY);
    }

    public Vec2l div(Vec2l b) {
        return Glm.div((Vec2l) this, (Vec2l) this, b.x, b.y);
    }

    public Vec2l div(long b, Vec2l res) {
        return Glm.div(res, (Vec2l) this, b, b);
    }

    public Vec2l div(long bX, long bY, Vec2l res) {
        return Glm.div(res, (Vec2l) this, bX, bY);
    }

    public Vec2l div(Vec2l b, Vec2l res) {
        return Glm.div(res, (Vec2l) this, b.x, b.y);
    }

    public Vec2l incr_() {
        return Glm.incr_((Vec2l) this);
    }

    public Vec2l incr() {
        return Glm.incr((Vec2l) this);
    }

    public Vec2l incr(Vec2l res) {
        return Glm.incr(res, (Vec2l) this);
    }

    public Vec2l decr_() {
        return Glm.decr_((Vec2l) this);
    }

    public Vec2l decr() {
        return Glm.decr((Vec2l) this);
    }

    public Vec2l decr(Vec2l res) {
        return Glm.decr(res, (Vec2l) this);
    }
}
