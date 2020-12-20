/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ul;

import glmath.glm.Glm;
import glmath.joou.ULong;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 4 * Long.BYTES;

    public ULong x = new ULong(), y = new ULong(), z = new ULong(), w = new ULong();

    public Vec4ul add_(ULong b) {
        return Glm.add(new Vec4ul(), (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul add_(long b) {
        return Glm.add(new Vec4ul(), (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul add_(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.add(new Vec4ul(), (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul add_(long bX, long bY, long bZ, long bW) {
        return Glm.add(new Vec4ul(), (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul add_(Vec4ul b) {
        return Glm.add(new Vec4ul(), (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul add(ULong b) {
        return Glm.add((Vec4ul) this, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul add(long b) {
        return Glm.add((Vec4ul) this, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul add(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.add((Vec4ul) this, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul add(long bX, long bY, long bZ, long bW) {
        return Glm.add((Vec4ul) this, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul add(Vec4ul b) {
        return Glm.add((Vec4ul) this, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul add(ULong b, Vec4ul res) {
        return Glm.add(res, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul add(long b, Vec4ul res) {
        return Glm.add(res, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul add(ULong bX, ULong bY, ULong bZ, ULong bW, Vec4ul res) {
        return Glm.add(res, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul add(long bX, long bY, long bZ, long bW, Vec4ul res) {
        return Glm.add(res, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul add(Vec4ul b, Vec4ul res) {
        return Glm.add(res, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul sub_(ULong b) {
        return Glm.sub(new Vec4ul(), (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul sub_(long b) {
        return Glm.sub(new Vec4ul(), (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul sub_(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.sub(new Vec4ul(), (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul sub_(long bX, long bY, long bZ, long bW) {
        return Glm.sub(new Vec4ul(), (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul sub_(Vec4ul b) {
        return Glm.sub(new Vec4ul(), (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul sub(ULong b) {
        return Glm.sub((Vec4ul) this, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul sub(long b) {
        return Glm.sub((Vec4ul) this, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul sub(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.sub((Vec4ul) this, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul sub(long bX, long bY, long bZ, long bW) {
        return Glm.sub((Vec4ul) this, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul sub(Vec4ul b) {
        return Glm.sub((Vec4ul) this, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul sub(ULong b, Vec4ul res) {
        return Glm.sub(res, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul sub(long b, Vec4ul res) {
        return Glm.sub(res, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul sub(ULong bX, ULong bY, ULong bZ, ULong bW, Vec4ul res) {
        return Glm.sub(res, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul sub(long bX, long bY, long bZ, long bW, Vec4ul res) {
        return Glm.sub(res, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul sub(Vec4ul b, Vec4ul res) {
        return Glm.sub(res, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul mul_(ULong b) {
        return Glm.mul(new Vec4ul(), (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul mul_(long b) {
        return Glm.mul(new Vec4ul(), (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul mul_(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.mul(new Vec4ul(), (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul mul_(long bX, long bY, long bZ, long bW) {
        return Glm.mul(new Vec4ul(), (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul mul_(Vec4ul b) {
        return Glm.mul(new Vec4ul(), (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul mul(ULong b) {
        return Glm.mul((Vec4ul) this, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul mul(long b) {
        return Glm.mul((Vec4ul) this, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul mul(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.mul((Vec4ul) this, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul mul(long bX, long bY, long bZ, long bW) {
        return Glm.mul((Vec4ul) this, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul mul(Vec4ul b) {
        return Glm.mul((Vec4ul) this, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul mul(ULong b, Vec4ul res) {
        return Glm.mul(res, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul mul(long b, Vec4ul res) {
        return Glm.mul(res, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul mul(ULong bX, ULong bY, ULong bZ, ULong bW, Vec4ul res) {
        return Glm.mul(res, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul mul(long bX, long bY, long bZ, long bW, Vec4ul res) {
        return Glm.mul(res, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul mul(Vec4ul b, Vec4ul res) {
        return Glm.mul(res, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul div_(ULong b) {
        return Glm.div(new Vec4ul(), (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul div_(long b) {
        return Glm.div(new Vec4ul(), (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul div_(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.div(new Vec4ul(), (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul div_(long bX, long bY, long bZ, long bW) {
        return Glm.div(new Vec4ul(), (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul div_(Vec4ul b) {
        return Glm.div(new Vec4ul(), (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul div(ULong b) {
        return Glm.div((Vec4ul) this, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul div(long b) {
        return Glm.div((Vec4ul) this, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul div(ULong bX, ULong bY, ULong bZ, ULong bW) {
        return Glm.div((Vec4ul) this, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul div(long bX, long bY, long bZ, long bW) {
        return Glm.div((Vec4ul) this, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul div(Vec4ul b) {
        return Glm.div((Vec4ul) this, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul div(ULong b, Vec4ul res) {
        return Glm.div(res, (Vec4ul) this, b.value, b.value, b.value, b.value);
    }

    public Vec4ul div(long b, Vec4ul res) {
        return Glm.div(res, (Vec4ul) this, b, b, b, b);
    }

    public Vec4ul div(ULong bX, ULong bY, ULong bZ, ULong bW, Vec4ul res) {
        return Glm.div(res, (Vec4ul) this, bX.value, bY.value, bZ.value, bW.value);
    }

    public Vec4ul div(long bX, long bY, long bZ, long bW, Vec4ul res) {
        return Glm.div(res, (Vec4ul) this, bX, bY, bZ, bW);
    }

    public Vec4ul div(Vec4ul b, Vec4ul res) {
        return Glm.div(res, (Vec4ul) this, b.x.value, b.y.value, b.z.value, b.w.value);
    }

    public Vec4ul incr_() {
        return Glm.incr_((Vec4ul) this);
    }

    public Vec4ul incr() {
        return Glm.incr((Vec4ul) this);
    }

    public Vec4ul incr(Vec4ul res) {
        return Glm.incr(res, (Vec4ul) this);
    }

    public Vec4ul decr_() {
        return Glm.decr_((Vec4ul) this);
    }

    public Vec4ul decr() {
        return Glm.decr((Vec4ul) this);
    }

    public Vec4ul decr(Vec4ul res) {
        return Glm.decr(res, (Vec4ul) this);
    }
}
