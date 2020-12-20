/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ui;

import glmath.glm.Glm;
import glmath.joou.UInt;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Integer.BYTES;

    public UInt x = new UInt(), y = new UInt(), z = new UInt();

    public Vec3ui add_(UInt b) {
        return Glm.add(new Vec3ui(), (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui add_(int b) {
        return Glm.add(new Vec3ui(), (Vec3ui) this, b, b, b);
    }

    public Vec3ui add_(UInt bX, UInt bY, UInt bZ) {
        return Glm.add(new Vec3ui(), (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui add_(int bX, int bY, int bZ) {
        return Glm.add(new Vec3ui(), (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui add_(Vec3ui b) {
        return Glm.add(new Vec3ui(), (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui add(UInt b) {
        return Glm.add((Vec3ui) this, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui add(int b) {
        return Glm.add((Vec3ui) this, (Vec3ui) this, b, b, b);
    }

    public Vec3ui add(UInt bX, UInt bY, UInt bZ) {
        return Glm.add((Vec3ui) this, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui add(int bX, int bY, int bZ) {
        return Glm.add((Vec3ui) this, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui add(Vec3ui b) {
        return Glm.add((Vec3ui) this, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui add(UInt b, Vec3ui res) {
        return Glm.add(res, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui add(int b, Vec3ui res) {
        return Glm.add(res, (Vec3ui) this, b, b, b);
    }

    public Vec3ui add(UInt bX, UInt bY, UInt bZ, Vec3ui res) {
        return Glm.add(res, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui add(int bX, int bY, int bZ, Vec3ui res) {
        return Glm.add(res, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui add(Vec3ui b, Vec3ui res) {
        return Glm.add(res, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui sub_(UInt b) {
        return Glm.sub(new Vec3ui(), (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui sub_(int b) {
        return Glm.sub(new Vec3ui(), (Vec3ui) this, b, b, b);
    }

    public Vec3ui sub_(UInt bX, UInt bY, UInt bZ) {
        return Glm.sub(new Vec3ui(), (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui sub_(int bX, int bY, int bZ) {
        return Glm.sub(new Vec3ui(), (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui sub_(Vec3ui b) {
        return Glm.sub(new Vec3ui(), (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui sub(UInt b) {
        return Glm.sub((Vec3ui) this, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui sub(int b) {
        return Glm.sub((Vec3ui) this, (Vec3ui) this, b, b, b);
    }

    public Vec3ui sub(UInt bX, UInt bY, UInt bZ) {
        return Glm.sub((Vec3ui) this, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui sub(int bX, int bY, int bZ) {
        return Glm.sub((Vec3ui) this, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui sub(Vec3ui b) {
        return Glm.sub((Vec3ui) this, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui sub(UInt b, Vec3ui res) {
        return Glm.sub(res, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui sub(int b, Vec3ui res) {
        return Glm.sub(res, (Vec3ui) this, b, b, b);
    }

    public Vec3ui sub(UInt bX, UInt bY, UInt bZ, Vec3ui res) {
        return Glm.sub(res, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui sub(int bX, int bY, int bZ, Vec3ui res) {
        return Glm.sub(res, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui sub(Vec3ui b, Vec3ui res) {
        return Glm.sub(res, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui mul_(UInt b) {
        return Glm.mul(new Vec3ui(), (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui mul_(int b) {
        return Glm.mul(new Vec3ui(), (Vec3ui) this, b, b, b);
    }

    public Vec3ui mul_(UInt bX, UInt bY, UInt bZ) {
        return Glm.mul(new Vec3ui(), (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui mul_(int bX, int bY, int bZ) {
        return Glm.mul(new Vec3ui(), (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui mul_(Vec3ui b) {
        return Glm.mul(new Vec3ui(), (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui mul(UInt b) {
        return Glm.mul((Vec3ui) this, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui mul(int b) {
        return Glm.mul((Vec3ui) this, (Vec3ui) this, b, b, b);
    }

    public Vec3ui mul(UInt bX, UInt bY, UInt bZ) {
        return Glm.mul((Vec3ui) this, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui mul(int bX, int bY, int bZ) {
        return Glm.mul((Vec3ui) this, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui mul(Vec3ui b) {
        return Glm.mul((Vec3ui) this, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui mul(UInt b, Vec3ui res) {
        return Glm.mul(res, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui mul(int b, Vec3ui res) {
        return Glm.mul(res, (Vec3ui) this, b, b, b);
    }

    public Vec3ui mul(UInt bX, UInt bY, UInt bZ, Vec3ui res) {
        return Glm.mul(res, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui mul(int bX, int bY, int bZ, Vec3ui res) {
        return Glm.mul(res, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui mul(Vec3ui b, Vec3ui res) {
        return Glm.mul(res, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui div_(UInt b) {
        return Glm.div(new Vec3ui(), (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui div_(int b) {
        return Glm.div(new Vec3ui(), (Vec3ui) this, b, b, b);
    }

    public Vec3ui div_(UInt bX, UInt bY, UInt bZ) {
        return Glm.div(new Vec3ui(), (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui div_(int bX, int bY, int bZ) {
        return Glm.div(new Vec3ui(), (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui div_(Vec3ui b) {
        return Glm.div(new Vec3ui(), (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui div(UInt b) {
        return Glm.div((Vec3ui) this, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui div(int b) {
        return Glm.div((Vec3ui) this, (Vec3ui) this, b, b, b);
    }

    public Vec3ui div(UInt bX, UInt bY, UInt bZ) {
        return Glm.div((Vec3ui) this, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui div(int bX, int bY, int bZ) {
        return Glm.div((Vec3ui) this, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui div(Vec3ui b) {
        return Glm.div((Vec3ui) this, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui div(UInt b, Vec3ui res) {
        return Glm.div(res, (Vec3ui) this, b.value, b.value, b.value);
    }

    public Vec3ui div(int b, Vec3ui res) {
        return Glm.div(res, (Vec3ui) this, b, b, b);
    }

    public Vec3ui div(UInt bX, UInt bY, UInt bZ, Vec3ui res) {
        return Glm.div(res, (Vec3ui) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ui div(int bX, int bY, int bZ, Vec3ui res) {
        return Glm.div(res, (Vec3ui) this, bX, bY, bZ);
    }

    public Vec3ui div(Vec3ui b, Vec3ui res) {
        return Glm.div(res, (Vec3ui) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ui incr_() {
        return Glm.incr_((Vec3ui) this);
    }

    public Vec3ui incr() {
        return Glm.incr((Vec3ui) this);
    }

    public Vec3ui incr(Vec3ui res) {
        return Glm.incr(res, (Vec3ui) this);
    }

    public Vec3ui decr_() {
        return Glm.decr_((Vec3ui) this);
    }

    public Vec3ui decr() {
        return Glm.decr((Vec3ui) this);
    }

    public Vec3ui decr(Vec3ui res) {
        return Glm.decr(res, (Vec3ui) this);
    }
}
