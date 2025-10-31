/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ul;

import glmath.glm.Glm;
import glmath.joou.ULong;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 3 * Long.BYTES;

    public ULong x = new ULong(), y = new ULong(), z = new ULong();

    public Vec3ul add_(ULong b) {
        return Glm.add(new Vec3ul(), (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul add_(long b) {
        return Glm.add(new Vec3ul(), (Vec3ul) this, b, b, b);
    }

    public Vec3ul add_(ULong bX, ULong bY, ULong bZ) {
        return Glm.add(new Vec3ul(), (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul add_(long bX, long bY, long bZ) {
        return Glm.add(new Vec3ul(), (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul add_(Vec3ul b) {
        return Glm.add(new Vec3ul(), (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul add(ULong b) {
        return Glm.add((Vec3ul) this, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul add(long b) {
        return Glm.add((Vec3ul) this, (Vec3ul) this, b, b, b);
    }

    public Vec3ul add(ULong bX, ULong bY, ULong bZ) {
        return Glm.add((Vec3ul) this, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul add(long bX, long bY, long bZ) {
        return Glm.add((Vec3ul) this, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul add(Vec3ul b) {
        return Glm.add((Vec3ul) this, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul add(ULong b, Vec3ul res) {
        return Glm.add(res, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul add(long b, Vec3ul res) {
        return Glm.add(res, (Vec3ul) this, b, b, b);
    }

    public Vec3ul add(ULong bX, ULong bY, ULong bZ, Vec3ul res) {
        return Glm.add(res, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul add(long bX, long bY, long bZ, Vec3ul res) {
        return Glm.add(res, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul add(Vec3ul b, Vec3ul res) {
        return Glm.add(res, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul sub_(ULong b) {
        return Glm.sub(new Vec3ul(), (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul sub_(long b) {
        return Glm.sub(new Vec3ul(), (Vec3ul) this, b, b, b);
    }

    public Vec3ul sub_(ULong bX, ULong bY, ULong bZ) {
        return Glm.sub(new Vec3ul(), (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul sub_(long bX, long bY, long bZ) {
        return Glm.sub(new Vec3ul(), (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul sub_(Vec3ul b) {
        return Glm.sub(new Vec3ul(), (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul sub(ULong b) {
        return Glm.sub((Vec3ul) this, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul sub(long b) {
        return Glm.sub((Vec3ul) this, (Vec3ul) this, b, b, b);
    }

    public Vec3ul sub(ULong bX, ULong bY, ULong bZ) {
        return Glm.sub((Vec3ul) this, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul sub(long bX, long bY, long bZ) {
        return Glm.sub((Vec3ul) this, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul sub(Vec3ul b) {
        return Glm.sub((Vec3ul) this, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul sub(ULong b, Vec3ul res) {
        return Glm.sub(res, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul sub(long b, Vec3ul res) {
        return Glm.sub(res, (Vec3ul) this, b, b, b);
    }

    public Vec3ul sub(ULong bX, ULong bY, ULong bZ, Vec3ul res) {
        return Glm.sub(res, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul sub(long bX, long bY, long bZ, Vec3ul res) {
        return Glm.sub(res, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul sub(Vec3ul b, Vec3ul res) {
        return Glm.sub(res, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul mul_(ULong b) {
        return Glm.mul(new Vec3ul(), (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul mul_(long b) {
        return Glm.mul(new Vec3ul(), (Vec3ul) this, b, b, b);
    }

    public Vec3ul mul_(ULong bX, ULong bY, ULong bZ) {
        return Glm.mul(new Vec3ul(), (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul mul_(long bX, long bY, long bZ) {
        return Glm.mul(new Vec3ul(), (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul mul_(Vec3ul b) {
        return Glm.mul(new Vec3ul(), (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul mul(ULong b) {
        return Glm.mul((Vec3ul) this, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul mul(long b) {
        return Glm.mul((Vec3ul) this, (Vec3ul) this, b, b, b);
    }

    public Vec3ul mul(ULong bX, ULong bY, ULong bZ) {
        return Glm.mul((Vec3ul) this, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul mul(long bX, long bY, long bZ) {
        return Glm.mul((Vec3ul) this, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul mul(Vec3ul b) {
        return Glm.mul((Vec3ul) this, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul mul(ULong b, Vec3ul res) {
        return Glm.mul(res, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul mul(long b, Vec3ul res) {
        return Glm.mul(res, (Vec3ul) this, b, b, b);
    }

    public Vec3ul mul(ULong bX, ULong bY, ULong bZ, Vec3ul res) {
        return Glm.mul(res, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul mul(long bX, long bY, long bZ, Vec3ul res) {
        return Glm.mul(res, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul mul(Vec3ul b, Vec3ul res) {
        return Glm.mul(res, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul div_(ULong b) {
        return Glm.div(new Vec3ul(), (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul div_(long b) {
        return Glm.div(new Vec3ul(), (Vec3ul) this, b, b, b);
    }

    public Vec3ul div_(ULong bX, ULong bY, ULong bZ) {
        return Glm.div(new Vec3ul(), (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul div_(long bX, long bY, long bZ) {
        return Glm.div(new Vec3ul(), (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul div_(Vec3ul b) {
        return Glm.div(new Vec3ul(), (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul div(ULong b) {
        return Glm.div((Vec3ul) this, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul div(long b) {
        return Glm.div((Vec3ul) this, (Vec3ul) this, b, b, b);
    }

    public Vec3ul div(ULong bX, ULong bY, ULong bZ) {
        return Glm.div((Vec3ul) this, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul div(long bX, long bY, long bZ) {
        return Glm.div((Vec3ul) this, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul div(Vec3ul b) {
        return Glm.div((Vec3ul) this, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul div(ULong b, Vec3ul res) {
        return Glm.div(res, (Vec3ul) this, b.value, b.value, b.value);
    }

    public Vec3ul div(long b, Vec3ul res) {
        return Glm.div(res, (Vec3ul) this, b, b, b);
    }

    public Vec3ul div(ULong bX, ULong bY, ULong bZ, Vec3ul res) {
        return Glm.div(res, (Vec3ul) this, bX.value, bY.value, bZ.value);
    }

    public Vec3ul div(long bX, long bY, long bZ, Vec3ul res) {
        return Glm.div(res, (Vec3ul) this, bX, bY, bZ);
    }

    public Vec3ul div(Vec3ul b, Vec3ul res) {
        return Glm.div(res, (Vec3ul) this, b.x.value, b.y.value, b.z.value);
    }

    public Vec3ul incr_() {
        return Glm.incr_((Vec3ul) this);
    }

    public Vec3ul incr() {
        return Glm.incr((Vec3ul) this);
    }

    public Vec3ul incr(Vec3ul res) {
        return Glm.incr(res, (Vec3ul) this);
    }

    public Vec3ul decr_() {
        return Glm.decr_((Vec3ul) this);
    }

    public Vec3ul decr() {
        return Glm.decr((Vec3ul) this);
    }

    public Vec3ul decr(Vec3ul res) {
        return Glm.decr(res, (Vec3ul) this);
    }
}
