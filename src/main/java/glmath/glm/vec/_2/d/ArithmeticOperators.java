/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.d;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public final static int SIZE = 2 * Double.BYTES;

    public double x, y;

    public Vec2d add_(double b) {
        return Glm.add(new Vec2d(), (Vec2d) this, b, b);
    }

    public Vec2d add_(double bX, double bY) {
        return Glm.add(new Vec2d(), (Vec2d) this, bX, bY);
    }

    public Vec2d add_(Vec2d b) {
        return Glm.add(new Vec2d(), (Vec2d) this, b.x, b.y);
    }

    public Vec2d add(double b) {
        return Glm.add((Vec2d) this, (Vec2d) this, b, b);
    }

    public Vec2d add(double bX, double bY) {
        return Glm.add((Vec2d) this, (Vec2d) this, bX, bY);
    }

    public Vec2d add(Vec2d b) {
        return Glm.add((Vec2d) this, (Vec2d) this, b.x, b.y);
    }

    public Vec2d add(double b, Vec2d res) {
        return Glm.add(res, (Vec2d) this, b, b);
    }

    public Vec2d add(double bX, double bY, Vec2d res) {
        return Glm.add(res, (Vec2d) this, bX, bY);
    }

    public Vec2d add(Vec2d b, Vec2d res) {
        return Glm.add(res, (Vec2d) this, b.x, b.y);
    }

    public Vec2d sub_(double b) {
        return Glm.sub(new Vec2d(), (Vec2d) this, b, b);
    }

    public Vec2d sub_(double bX, double bY) {
        return Glm.sub(new Vec2d(), (Vec2d) this, bX, bY);
    }

    public Vec2d sub_(Vec2d b) {
        return Glm.sub(new Vec2d(), (Vec2d) this, b.x, b.y);
    }

    public Vec2d sub(double b) {
        return Glm.sub((Vec2d) this, (Vec2d) this, b, b);
    }

    public Vec2d sub(double bX, double bY) {
        return Glm.sub((Vec2d) this, (Vec2d) this, bX, bY);
    }

    public Vec2d sub(Vec2d b) {
        return Glm.sub((Vec2d) this, (Vec2d) this, b.x, b.y);
    }

    public Vec2d sub(double b, Vec2d res) {
        return Glm.sub(res, (Vec2d) this, b, b);
    }

    public Vec2d sub(double bX, double bY, Vec2d res) {
        return Glm.sub(res, (Vec2d) this, bX, bY);
    }

    public Vec2d sub(Vec2d b, Vec2d res) {
        return Glm.sub(res, (Vec2d) this, b.x, b.y);
    }

    public Vec2d mul_(double b) {
        return Glm.mul(new Vec2d(), (Vec2d) this, b, b);
    }

    public Vec2d mul_(double bX, double bY) {
        return Glm.mul(new Vec2d(), (Vec2d) this, bX, bY);
    }

    public Vec2d mul_(Vec2d b) {
        return Glm.mul(new Vec2d(), (Vec2d) this, b.x, b.y);
    }

    public Vec2d mul(double b) {
        return Glm.mul((Vec2d) this, (Vec2d) this, b, b);
    }

    public Vec2d mul(double bX, double bY) {
        return Glm.mul((Vec2d) this, (Vec2d) this, bX, bY);
    }

    public Vec2d mul(Vec2d b) {
        return Glm.mul((Vec2d) this, (Vec2d) this, b.x, b.y);
    }

    public Vec2d mul(double b, Vec2d res) {
        return Glm.mul(res, (Vec2d) this, b, b);
    }

    public Vec2d mul(double bX, double bY, Vec2d res) {
        return Glm.mul(res, (Vec2d) this, bX, bY);
    }

    public Vec2d mul(Vec2d b, Vec2d res) {
        return Glm.mul(res, (Vec2d) this, b.x, b.y);
    }

    public Vec2d div_(double b) {
        return Glm.div(new Vec2d(), (Vec2d) this, b, b);
    }

    public Vec2d div_(double bX, double bY) {
        return Glm.div(new Vec2d(), (Vec2d) this, bX, bY);
    }

    public Vec2d div_(Vec2d b) {
        return Glm.div(new Vec2d(), (Vec2d) this, b.x, b.y);
    }

    public Vec2d div(double b) {
        return Glm.div((Vec2d) this, (Vec2d) this, b, b);
    }

    public Vec2d div(double bX, double bY) {
        return Glm.div((Vec2d) this, (Vec2d) this, bX, bY);
    }

    public Vec2d div(Vec2d b) {
        return Glm.div((Vec2d) this, (Vec2d) this, b.x, b.y);
    }

    public Vec2d div(double b, Vec2d res) {
        return Glm.div(res, (Vec2d) this, b, b);
    }

    public Vec2d div(double bX, double bY, Vec2d res) {
        return Glm.div(res, (Vec2d) this, bX, bY);
    }

    public Vec2d div(Vec2d b, Vec2d res) {
        return Glm.div(res, (Vec2d) this, b.x, b.y);
    }

    public Vec2d incr_() {
        return Glm.incr_((Vec2d) this);
    }

    public Vec2d incr() {
        return Glm.incr((Vec2d) this);
    }

    public Vec2d incr(Vec2d res) {
        return Glm.incr(res, (Vec2d) this);
    }

    public Vec2d decr_() {
        return Glm.decr_((Vec2d) this);
    }

    public Vec2d decr() {
        return Glm.decr((Vec2d) this);
    }

    public Vec2d decr(Vec2d res) {
        return Glm.decr(res, (Vec2d) this);
    }
}
