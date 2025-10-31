/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ul;

import glmath.glm.Glm;
import glmath.joou.ULong;

/**
 *
 * @author GBarbieri
 */
abstract class ArithmeticOperators {

    public static final int SIZE = 2 * Long.BYTES;

    public ULong x = new ULong(), y = new ULong();

    public Vec2ul add_(ULong b) {
        return Glm.add(new Vec2ul(), (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul add_(long b) {
        return Glm.add(new Vec2ul(), (Vec2ul) this, b, b);
    }

    public Vec2ul add_(ULong bX, ULong bY) {
        return Glm.add(new Vec2ul(), (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul add_(long bX, long bY) {
        return Glm.add(new Vec2ul(), (Vec2ul) this, bX, bY);
    }

    public Vec2ul add_(Vec2ul b) {
        return Glm.add(new Vec2ul(), (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul add(ULong b) {
        return Glm.add((Vec2ul) this, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul add(long b) {
        return Glm.add((Vec2ul) this, (Vec2ul) this, b, b);
    }

    public Vec2ul add(ULong bX, ULong bY) {
        return Glm.add((Vec2ul) this, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul add(long bX, long bY) {
        return Glm.add((Vec2ul) this, (Vec2ul) this, bX, bY);
    }

    public Vec2ul add(Vec2ul b) {
        return Glm.add((Vec2ul) this, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul add(ULong b, Vec2ul res) {
        return Glm.add(res, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul add(long b, Vec2ul res) {
        return Glm.add(res, (Vec2ul) this, b, b);
    }

    public Vec2ul add(ULong bX, ULong bY, Vec2ul res) {
        return Glm.add(res, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul add(long bX, long bY, Vec2ul res) {
        return Glm.add(res, (Vec2ul) this, bX, bY);
    }

    public Vec2ul add(Vec2ul b, Vec2ul res) {
        return Glm.add(res, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul sub_(ULong b) {
        return Glm.sub(new Vec2ul(), (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul sub_(long b) {
        return Glm.sub(new Vec2ul(), (Vec2ul) this, b, b);
    }

    public Vec2ul sub_(ULong bX, ULong bY) {
        return Glm.sub(new Vec2ul(), (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul sub_(long bX, long bY) {
        return Glm.sub(new Vec2ul(), (Vec2ul) this, bX, bY);
    }

    public Vec2ul sub_(Vec2ul b) {
        return Glm.sub(new Vec2ul(), (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul sub(ULong b) {
        return Glm.sub((Vec2ul) this, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul sub(long b) {
        return Glm.sub((Vec2ul) this, (Vec2ul) this, b, b);
    }

    public Vec2ul sub(ULong bX, ULong bY) {
        return Glm.sub((Vec2ul) this, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul sub(long bX, long bY) {
        return Glm.sub((Vec2ul) this, (Vec2ul) this, bX, bY);
    }

    public Vec2ul sub(Vec2ul b) {
        return Glm.sub((Vec2ul) this, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul sub(ULong b, Vec2ul res) {
        return Glm.sub(res, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul sub(long b, Vec2ul res) {
        return Glm.sub(res, (Vec2ul) this, b, b);
    }

    public Vec2ul sub(ULong bX, ULong bY, Vec2ul res) {
        return Glm.sub(res, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul sub(long bX, long bY, Vec2ul res) {
        return Glm.sub(res, (Vec2ul) this, bX, bY);
    }

    public Vec2ul sub(Vec2ul b, Vec2ul res) {
        return Glm.sub(res, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul mul_(ULong b) {
        return Glm.mul(new Vec2ul(), (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul mul_(long b) {
        return Glm.mul(new Vec2ul(), (Vec2ul) this, b, b);
    }

    public Vec2ul mul_(ULong bX, ULong bY) {
        return Glm.mul(new Vec2ul(), (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul mul_(long bX, long bY) {
        return Glm.mul(new Vec2ul(), (Vec2ul) this, bX, bY);
    }

    public Vec2ul mul_(Vec2ul b) {
        return Glm.mul(new Vec2ul(), (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul mul(ULong b) {
        return Glm.mul((Vec2ul) this, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul mul(long b) {
        return Glm.mul((Vec2ul) this, (Vec2ul) this, b, b);
    }

    public Vec2ul mul(ULong bX, ULong bY) {
        return Glm.mul((Vec2ul) this, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul mul(long bX, long bY) {
        return Glm.mul((Vec2ul) this, (Vec2ul) this, bX, bY);
    }

    public Vec2ul mul(Vec2ul b) {
        return Glm.mul((Vec2ul) this, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul mul(ULong b, Vec2ul res) {
        return Glm.mul(res, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul mul(long b, Vec2ul res) {
        return Glm.mul(res, (Vec2ul) this, b, b);
    }

    public Vec2ul mul(ULong bX, ULong bY, Vec2ul res) {
        return Glm.mul(res, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul mul(long bX, long bY, Vec2ul res) {
        return Glm.mul(res, (Vec2ul) this, bX, bY);
    }

    public Vec2ul mul(Vec2ul b, Vec2ul res) {
        return Glm.mul(res, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul div_(ULong b) {
        return Glm.div(new Vec2ul(), (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul div_(long b) {
        return Glm.div(new Vec2ul(), (Vec2ul) this, b, b);
    }

    public Vec2ul div_(ULong bX, ULong bY) {
        return Glm.div(new Vec2ul(), (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul div_(long bX, long bY) {
        return Glm.div(new Vec2ul(), (Vec2ul) this, bX, bY);
    }

    public Vec2ul div_(Vec2ul b) {
        return Glm.div(new Vec2ul(), (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul div(ULong b) {
        return Glm.div((Vec2ul) this, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul div(long b) {
        return Glm.div((Vec2ul) this, (Vec2ul) this, b, b);
    }

    public Vec2ul div(ULong bX, ULong bY) {
        return Glm.div((Vec2ul) this, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul div(long bX, long bY) {
        return Glm.div((Vec2ul) this, (Vec2ul) this, bX, bY);
    }

    public Vec2ul div(Vec2ul b) {
        return Glm.div((Vec2ul) this, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul div(ULong b, Vec2ul res) {
        return Glm.div(res, (Vec2ul) this, b.value, b.value);
    }

    public Vec2ul div(long b, Vec2ul res) {
        return Glm.div(res, (Vec2ul) this, b, b);
    }

    public Vec2ul div(ULong bX, ULong bY, Vec2ul res) {
        return Glm.div(res, (Vec2ul) this, bX.value, bY.value);
    }

    public Vec2ul div(long bX, long bY, Vec2ul res) {
        return Glm.div(res, (Vec2ul) this, bX, bY);
    }

    public Vec2ul div(Vec2ul b, Vec2ul res) {
        return Glm.div(res, (Vec2ul) this, b.x.value, b.y.value);
    }

    public Vec2ul incr_() {
        return Glm.incr_((Vec2ul) this);
    }

    public Vec2ul incr() {
        return Glm.incr((Vec2ul) this);
    }

    public Vec2ul incr(Vec2ul res) {
        return Glm.incr(res, (Vec2ul) this);
    }

    public Vec2ul decr_() {
        return Glm.decr_((Vec2ul) this);
    }

    public Vec2ul decr() {
        return Glm.decr((Vec2ul) this);
    }

    public Vec2ul decr(Vec2ul res) {
        return Glm.decr(res, (Vec2ul) this);
    }
}
