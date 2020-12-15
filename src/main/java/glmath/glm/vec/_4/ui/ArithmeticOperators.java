/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ui;

import glmath.glm.Glm;
import glmath.joou.UInt;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Integer.BYTES;

    public UInt x = new UInt(), y = new UInt(), z = new UInt(), w = new UInt();

    public Vec4ui add_(UInt b) {
        return Glm.add(new Vec4ui(), (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui add_(int b) {
        return Glm.add(new Vec4ui(), (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui add_(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.add(new Vec4ui(), (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui add_(int bX, int bY, int bZ, int bW) {
        return Glm.add(new Vec4ui(), (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui add_(Vec4ui b) {
        return Glm.add(new Vec4ui(), (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui add(UInt b) {
        return Glm.add((Vec4ui) this, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui add(int b) {
        return Glm.add((Vec4ui) this, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui add(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.add((Vec4ui) this, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui add(int bX, int bY, int bZ, int bW) {
        return Glm.add((Vec4ui) this, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui add(Vec4ui b) {
        return Glm.add((Vec4ui) this, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui add(UInt b, Vec4ui res) {
        return Glm.add(res, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui add(int b, Vec4ui res) {
        return Glm.add(res, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui add(UInt bX, UInt bY, UInt bZ, UInt bW, Vec4ui res) {
        return Glm.add(res, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui add(int bX, int bY, int bZ, int bW, Vec4ui res) {
        return Glm.add(res, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui add(Vec4ui b, Vec4ui res) {
        return Glm.add(res, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui sub_(UInt b) {
        return Glm.sub(new Vec4ui(), (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui sub_(int b) {
        return Glm.sub(new Vec4ui(), (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui sub_(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.sub(new Vec4ui(), (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui sub_(int bX, int bY, int bZ, int bW) {
        return Glm.sub(new Vec4ui(), (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui sub_(Vec4ui b) {
        return Glm.sub(new Vec4ui(), (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui sub(UInt b) {
        return Glm.sub((Vec4ui) this, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui sub(int b) {
        return Glm.sub((Vec4ui) this, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui sub(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.sub((Vec4ui) this, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui sub(int bX, int bY, int bZ, int bW) {
        return Glm.sub((Vec4ui) this, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui sub(Vec4ui b) {
        return Glm.sub((Vec4ui) this, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui sub(UInt b, Vec4ui res) {
        return Glm.sub(res, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui sub(int b, Vec4ui res) {
        return Glm.sub(res, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui sub(UInt bX, UInt bY, UInt bZ, UInt bW, Vec4ui res) {
        return Glm.sub(res, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui sub(int bX, int bY, int bZ, int bW, Vec4ui res) {
        return Glm.sub(res, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui sub(Vec4ui b, Vec4ui res) {
        return Glm.sub(res, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui mul_(UInt b) {
        return Glm.mul(new Vec4ui(), (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui mul_(int b) {
        return Glm.mul(new Vec4ui(), (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui mul_(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.mul(new Vec4ui(), (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui mul_(int bX, int bY, int bZ, int bW) {
        return Glm.mul(new Vec4ui(), (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui mul_(Vec4ui b) {
        return Glm.mul(new Vec4ui(), (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui mul(UInt b) {
        return Glm.mul((Vec4ui) this, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui mul(int b) {
        return Glm.mul((Vec4ui) this, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui mul(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.mul((Vec4ui) this, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui mul(int bX, int bY, int bZ, int bW) {
        return Glm.mul((Vec4ui) this, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui mul(Vec4ui b) {
        return Glm.mul((Vec4ui) this, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui mul(UInt b, Vec4ui res) {
        return Glm.mul(res, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui mul(int b, Vec4ui res) {
        return Glm.mul(res, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui mul(UInt bX, UInt bY, UInt bZ, UInt bW, Vec4ui res) {
        return Glm.mul(res, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui mul(int bX, int bY, int bZ, int bW, Vec4ui res) {
        return Glm.mul(res, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui mul(Vec4ui b, Vec4ui res) {
        return Glm.mul(res, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui div_(UInt b) {
        return Glm.div(new Vec4ui(), (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui div_(int b) {
        return Glm.div(new Vec4ui(), (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui div_(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.div(new Vec4ui(), (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui div_(int bX, int bY, int bZ, int bW) {
        return Glm.div(new Vec4ui(), (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui div_(Vec4ui b) {
        return Glm.div(new Vec4ui(), (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui div(UInt b) {
        return Glm.div((Vec4ui) this, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui div(int b) {
        return Glm.div((Vec4ui) this, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui div(UInt bX, UInt bY, UInt bZ, UInt bW) {
        return Glm.div((Vec4ui) this, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui div(int bX, int bY, int bZ, int bW) {
        return Glm.div((Vec4ui) this, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui div(Vec4ui b) {
        return Glm.div((Vec4ui) this, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui div(UInt b, Vec4ui res) {
        return Glm.div(res, (Vec4ui) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ui div(int b, Vec4ui res) {
        return Glm.div(res, (Vec4ui) this, b, b, b, b);
    }

    public Vec4ui div(UInt bX, UInt bY, UInt bZ, UInt bW, Vec4ui res) {
        return Glm.div(res, (Vec4ui) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ui div(int bX, int bY, int bZ, int bW, Vec4ui res) {
        return Glm.div(res, (Vec4ui) this, bX, bY, bZ, bW);
    }

    public Vec4ui div(Vec4ui b, Vec4ui res) {
        return Glm.div(res, (Vec4ui) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ui incr_() {
        return Glm.incr_((Vec4ui) this);
    }

    public Vec4ui incr() {
        return Glm.incr((Vec4ui) this);
    }

    public Vec4ui incr(Vec4ui res) {
        return Glm.incr(res, (Vec4ui) this);
    }

    public Vec4ui decr_() {
        return Glm.decr_((Vec4ui) this);
    }

    public Vec4ui decr() {
        return Glm.decr((Vec4ui) this);
    }

    public Vec4ui decr(Vec4ui res) {
        return Glm.decr(res, (Vec4ui) this);
    }
}
