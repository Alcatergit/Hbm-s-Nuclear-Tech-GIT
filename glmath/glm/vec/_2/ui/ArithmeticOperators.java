/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ui;

import glmath.glm.Glm;
import glmath.joou.UInt;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Integer.BYTES;

    public UInt x = new UInt(), y = new UInt();

    public Vec2ui add_(UInt b) {
        return Glm.add(new Vec2ui(), (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui add_(int b) {
        return Glm.add(new Vec2ui(), (Vec2ui) this, b, b);
    }

    public Vec2ui add_(UInt bX, UInt bY) {
        return Glm.add(new Vec2ui(), (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui add_(int bX, int bY) {
        return Glm.add(new Vec2ui(), (Vec2ui) this, bX, bY);
    }

    public Vec2ui add_(Vec2ui b) {
        return Glm.add(new Vec2ui(), (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui add(UInt b) {
        return Glm.add((Vec2ui) this, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui add(int b) {
        return Glm.add((Vec2ui) this, (Vec2ui) this, b, b);
    }

    public Vec2ui add(UInt bX, UInt bY) {
        return Glm.add((Vec2ui) this, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui add(int bX, int bY) {
        return Glm.add((Vec2ui) this, (Vec2ui) this, bX, bY);
    }

    public Vec2ui add(Vec2ui b) {
        return Glm.add((Vec2ui) this, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui add(UInt b, Vec2ui res) {
        return Glm.add(res, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui add(int b, Vec2ui res) {
        return Glm.add(res, (Vec2ui) this, b, b);
    }

    public Vec2ui add(UInt bX, UInt bY, Vec2ui res) {
        return Glm.add(res, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui add(int bX, int bY, Vec2ui res) {
        return Glm.add(res, (Vec2ui) this, bX, bY);
    }

    public Vec2ui add(Vec2ui b, Vec2ui res) {
        return Glm.add(res, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui sub_(UInt b) {
        return Glm.sub(new Vec2ui(), (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui sub_(int b) {
        return Glm.sub(new Vec2ui(), (Vec2ui) this, b, b);
    }

    public Vec2ui sub_(UInt bX, UInt bY) {
        return Glm.sub(new Vec2ui(), (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui sub_(int bX, int bY) {
        return Glm.sub(new Vec2ui(), (Vec2ui) this, bX, bY);
    }

    public Vec2ui sub_(Vec2ui b) {
        return Glm.sub(new Vec2ui(), (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui sub(UInt b) {
        return Glm.sub((Vec2ui) this, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui sub(int b) {
        return Glm.sub((Vec2ui) this, (Vec2ui) this, b, b);
    }

    public Vec2ui sub(UInt bX, UInt bY) {
        return Glm.sub((Vec2ui) this, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui sub(int bX, int bY) {
        return Glm.sub((Vec2ui) this, (Vec2ui) this, bX, bY);
    }

    public Vec2ui sub(Vec2ui b) {
        return Glm.sub((Vec2ui) this, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui sub(UInt b, Vec2ui res) {
        return Glm.sub(res, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui sub(int b, Vec2ui res) {
        return Glm.sub(res, (Vec2ui) this, b, b);
    }

    public Vec2ui sub(UInt bX, UInt bY, Vec2ui res) {
        return Glm.sub(res, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui sub(int bX, int bY, Vec2ui res) {
        return Glm.sub(res, (Vec2ui) this, bX, bY);
    }

    public Vec2ui sub(Vec2ui b, Vec2ui res) {
        return Glm.sub(res, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui mul_(UInt b) {
        return Glm.mul(new Vec2ui(), (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui mul_(int b) {
        return Glm.mul(new Vec2ui(), (Vec2ui) this, b, b);
    }

    public Vec2ui mul_(UInt bX, UInt bY) {
        return Glm.mul(new Vec2ui(), (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui mul_(int bX, int bY) {
        return Glm.mul(new Vec2ui(), (Vec2ui) this, bX, bY);
    }

    public Vec2ui mul_(Vec2ui b) {
        return Glm.mul(new Vec2ui(), (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui mul(UInt b) {
        return Glm.mul((Vec2ui) this, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui mul(int b) {
        return Glm.mul((Vec2ui) this, (Vec2ui) this, b, b);
    }

    public Vec2ui mul(UInt bX, UInt bY) {
        return Glm.mul((Vec2ui) this, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui mul(int bX, int bY) {
        return Glm.mul((Vec2ui) this, (Vec2ui) this, bX, bY);
    }

    public Vec2ui mul(Vec2ui b) {
        return Glm.mul((Vec2ui) this, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui mul(UInt b, Vec2ui res) {
        return Glm.mul(res, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui mul(int b, Vec2ui res) {
        return Glm.mul(res, (Vec2ui) this, b, b);
    }

    public Vec2ui mul(UInt bX, UInt bY, Vec2ui res) {
        return Glm.mul(res, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui mul(int bX, int bY, Vec2ui res) {
        return Glm.mul(res, (Vec2ui) this, bX, bY);
    }

    public Vec2ui mul(Vec2ui b, Vec2ui res) {
        return Glm.mul(res, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui div_(UInt b) {
        return Glm.div(new Vec2ui(), (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui div_(int b) {
        return Glm.div(new Vec2ui(), (Vec2ui) this, b, b);
    }

    public Vec2ui div_(UInt bX, UInt bY) {
        return Glm.div(new Vec2ui(), (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui div_(int bX, int bY) {
        return Glm.div(new Vec2ui(), (Vec2ui) this, bX, bY);
    }

    public Vec2ui div_(Vec2ui b) {
        return Glm.div(new Vec2ui(), (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui div(UInt b) {
        return Glm.div((Vec2ui) this, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui div(int b) {
        return Glm.div((Vec2ui) this, (Vec2ui) this, b, b);
    }

    public Vec2ui div(UInt bX, UInt bY) {
        return Glm.div((Vec2ui) this, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui div(int bX, int bY) {
        return Glm.div((Vec2ui) this, (Vec2ui) this, bX, bY);
    }

    public Vec2ui div(Vec2ui b) {
        return Glm.div((Vec2ui) this, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui div(UInt b, Vec2ui res) {
        return Glm.div(res, (Vec2ui) this, b.value, b.value);
    }

    public Vec2ui div(int b, Vec2ui res) {
        return Glm.div(res, (Vec2ui) this, b, b);
    }

    public Vec2ui div(UInt bX, UInt bY, Vec2ui res) {
        return Glm.div(res, (Vec2ui) this, bX.value, bY.value);
    }

    public Vec2ui div(int bX, int bY, Vec2ui res) {
        return Glm.div(res, (Vec2ui) this, bX, bY);
    }

    public Vec2ui div(Vec2ui b, Vec2ui res) {
        return Glm.div(res, (Vec2ui) this, b.x.value, b.y.value);
    }

    public Vec2ui incr_() {
        return Glm.incr_((Vec2ui) this);
    }

    public Vec2ui incr() {
        return Glm.incr((Vec2ui) this);
    }

    public Vec2ui incr(Vec2ui res) {
        return Glm.incr(res, (Vec2ui) this);
    }

    public Vec2ui decr_() {
        return Glm.decr_((Vec2ui) this);
    }

    public Vec2ui decr() {
        return Glm.decr((Vec2ui) this);
    }

    public Vec2ui decr(Vec2ui res) {
        return Glm.decr(res, (Vec2ui) this);
    }
}
