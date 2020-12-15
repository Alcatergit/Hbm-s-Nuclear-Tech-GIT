/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.s;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {
    
    public static final int SIZE = 2 * Short.BYTES;

    public short x, y;
    
    public Vec2s add_(short b) {
        return Glm.add(new Vec2s(), (Vec2s) this, (int) b, b);
    }

    public Vec2s add_(int b) {
        return Glm.add(new Vec2s(), (Vec2s) this, b, b);
    }

    public Vec2s add_(short bX, short bY) {
        return Glm.add(new Vec2s(), (Vec2s) this, (int) bX, bY);
    }

    public Vec2s add_(int bX, int bY) {
        return Glm.add(new Vec2s(), (Vec2s) this, bX, bY);
    }

    public Vec2s add_(Vec2s b) {
        return Glm.add(new Vec2s(), (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s add(short b) {
        return Glm.add((Vec2s) this, (Vec2s) this, (int) b, b);
    }

    public Vec2s add(int b) {
        return Glm.add((Vec2s) this, (Vec2s) this, b, b);
    }

    public Vec2s add(short bX, short bY) {
        return Glm.add((Vec2s) this, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s add(int bX, int bY) {
        return Glm.add((Vec2s) this, (Vec2s) this, bX, bY);
    }

    public Vec2s add(Vec2s b) {
        return Glm.add((Vec2s) this, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s add(short b, Vec2s res) {
        return Glm.add(res, (Vec2s) this, (int) b, b);
    }

    public Vec2s add(int b, Vec2s res) {
        return Glm.add(res, (Vec2s) this, b, b);
    }

    public Vec2s add(short bX, short bY, Vec2s res) {
        return Glm.add(res, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s add(int bX, int bY, Vec2s res) {
        return Glm.add(res, (Vec2s) this, bX, bY);
    }

    public Vec2s add(Vec2s b, Vec2s res) {
        return Glm.add(res, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s sub_(short b) {
        return Glm.sub(new Vec2s(), (Vec2s) this, (int) b, b);
    }

    public Vec2s sub_(int b) {
        return Glm.sub(new Vec2s(), (Vec2s) this, b, b);
    }

    public Vec2s sub_(short bX, short bY) {
        return Glm.sub(new Vec2s(), (Vec2s) this, (int) bX, bY);
    }

    public Vec2s sub_(int bX, int bY) {
        return Glm.sub(new Vec2s(), (Vec2s) this, bX, bY);
    }

    public Vec2s sub_(Vec2s b) {
        return Glm.sub(new Vec2s(), (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s sub(short b) {
        return Glm.sub((Vec2s) this, (Vec2s) this, (int) b, b);
    }

    public Vec2s sub(int b) {
        return Glm.sub((Vec2s) this, (Vec2s) this, b, b);
    }

    public Vec2s sub(short bX, short bY) {
        return Glm.sub((Vec2s) this, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s sub(int bX, int bY) {
        return Glm.sub((Vec2s) this, (Vec2s) this, bX, bY);
    }

    public Vec2s sub(Vec2s b) {
        return Glm.sub((Vec2s) this, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s sub(short b, Vec2s res) {
        return Glm.sub(res, (Vec2s) this, (int) b, b);
    }

    public Vec2s sub(int b, Vec2s res) {
        return Glm.sub(res, (Vec2s) this, b, b);
    }

    public Vec2s sub(short bX, short bY, Vec2s res) {
        return Glm.sub(res, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s sub(int bX, int bY, Vec2s res) {
        return Glm.sub(res, (Vec2s) this, bX, bY);
    }

    public Vec2s sub(Vec2s b, Vec2s res) {
        return Glm.sub(res, (Vec2s) this, (int) b.x, b.y);
    }
    
    public Vec2s mul_(short b) {
        return Glm.mul(new Vec2s(), (Vec2s) this, (int) b, b);
    }

    public Vec2s mul_(int b) {
        return Glm.mul(new Vec2s(), (Vec2s) this, b, b);
    }

    public Vec2s mul_(short bX, short bY) {
        return Glm.mul(new Vec2s(), (Vec2s) this, (int) bX, bY);
    }

    public Vec2s mul_(int bX, int bY) {
        return Glm.mul(new Vec2s(), (Vec2s) this, bX, bY);
    }

    public Vec2s mul_(Vec2s b) {
        return Glm.mul(new Vec2s(), (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s mul(short b) {
        return Glm.mul((Vec2s) this, (Vec2s) this, (int) b, b);
    }

    public Vec2s mul(int b) {
        return Glm.mul((Vec2s) this, (Vec2s) this, b, b);
    }

    public Vec2s mul(short bX, short bY) {
        return Glm.mul((Vec2s) this, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s mul(int bX, int bY) {
        return Glm.mul((Vec2s) this, (Vec2s) this, bX, bY);
    }

    public Vec2s mul(Vec2s b) {
        return Glm.mul((Vec2s) this, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s mul(short b, Vec2s res) {
        return Glm.mul(res, (Vec2s) this, (int) b, b);
    }

    public Vec2s mul(int b, Vec2s res) {
        return Glm.mul(res, (Vec2s) this, b, b);
    }

    public Vec2s mul(short bX, short bY, Vec2s res) {
        return Glm.mul(res, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s mul(int bX, int bY, Vec2s res) {
        return Glm.mul(res, (Vec2s) this, bX, bY);
    }

    public Vec2s mul(Vec2s b, Vec2s res) {
        return Glm.mul(res, (Vec2s) this, (int) b.x, b.y);
    }
    
    public Vec2s div_(short b) {
        return Glm.div(new Vec2s(), (Vec2s) this, (int) b, b);
    }

    public Vec2s div_(int b) {
        return Glm.div(new Vec2s(), (Vec2s) this, b, b);
    }

    public Vec2s div_(short bX, short bY) {
        return Glm.div(new Vec2s(), (Vec2s) this, (int) bX, bY);
    }

    public Vec2s div_(int bX, int bY) {
        return Glm.div(new Vec2s(), (Vec2s) this, bX, bY);
    }

    public Vec2s div_(Vec2s b) {
        return Glm.div(new Vec2s(), (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s div(short b) {
        return Glm.div((Vec2s) this, (Vec2s) this, (int) b, b);
    }

    public Vec2s div(int b) {
        return Glm.div((Vec2s) this, (Vec2s) this, b, b);
    }

    public Vec2s div(short bX, short bY) {
        return Glm.div((Vec2s) this, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s div(int bX, int bY) {
        return Glm.div((Vec2s) this, (Vec2s) this, bX, bY);
    }

    public Vec2s div(Vec2s b) {
        return Glm.div((Vec2s) this, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s div(short b, Vec2s res) {
        return Glm.div(res, (Vec2s) this, (int) b, b);
    }

    public Vec2s div(int b, Vec2s res) {
        return Glm.div(res, (Vec2s) this, b, b);
    }

    public Vec2s div(short bX, short bY, Vec2s res) {
        return Glm.div(res, (Vec2s) this, (int) bX, bY);
    }

    public Vec2s div(int bX, int bY, Vec2s res) {
        return Glm.div(res, (Vec2s) this, bX, bY);
    }

    public Vec2s div(Vec2s b, Vec2s res) {
        return Glm.div(res, (Vec2s) this, (int) b.x, b.y);
    }

    public Vec2s incr_() {
        return Glm.incr_((Vec2s) this);
    }

    public Vec2s incr() {
        return Glm.incr((Vec2s) this);
    }

    public Vec2s incr(Vec2s res) {
        return Glm.incr(res, (Vec2s) this);
    }

    public Vec2s decr_() {
        return Glm.decr_((Vec2s) this);
    }

    public Vec2s decr() {
        return Glm.decr((Vec2s) this);
    }

    public Vec2s decr(Vec2s res) {
        return Glm.decr(res, (Vec2s) this);
    }
}
