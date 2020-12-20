/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.b;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3b) this);
    }

    public boolean all() {
        return Glm.all((Vec3b) this);
    }

    public Vec3b not_() {
        return Glm.not((Vec3b) this, new Vec3b());
    }

    public Vec3b not() {
        return Glm.not((Vec3b) this, (Vec3b) this);
    }

    public Vec3bool lessThan__(Vec3b b) {
        return Glm.lessThan((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3b b, Vec3bool res) {
        return Glm.lessThan((Vec3b) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3b b) {
        return Glm.lessThanEqual((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3b b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3b) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3b b) {
        return Glm.greaterThan((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3b b, Vec3bool res) {
        return Glm.greaterThan((Vec3b) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3b b) {
        return Glm.greaterThanEqual((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3b b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3b) this, b, res);
    }

    public Vec3bool equal__(Vec3b b) {
        return Glm.equal((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3b b, Vec3bool res) {
        return Glm.equal((Vec3b) this, b, res);
    }

    public Vec3bool notEqual__(Vec3b b) {
        return Glm.notEqual((Vec3b) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3b b, Vec3bool res) {
        return Glm.notEqual((Vec3b) this, b, res);
    }

    public Vec3b lessThan(Vec3b b) {
        return Glm.lessThan((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b lessThan_(Vec3b b) {
        return Glm.lessThan((Vec3b) this, b, new Vec3b());
    }

    public Vec3b lessThan(Vec3b b, Vec3b res) {
        return Glm.lessThan((Vec3b) this, b, res);
    }

    public Vec3b lessThanEqual(Vec3b b) {
        return Glm.lessThanEqual((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b lessThanEqual_(Vec3b b) {
        return Glm.lessThanEqual((Vec3b) this, b, new Vec3b());
    }

    public Vec3b lessThanEqual(Vec3b b, Vec3b res) {
        return Glm.lessThanEqual((Vec3b) this, b, res);
    }

    public Vec3b greaterThan(Vec3b b) {
        return Glm.greaterThan((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b greaterThan_(Vec3b b) {
        return Glm.greaterThan((Vec3b) this, b, new Vec3b());
    }

    public Vec3b greaterThan(Vec3b b, Vec3b res) {
        return Glm.greaterThan((Vec3b) this, b, res);
    }

    public Vec3b greaterThanEqual(Vec3b b) {
        return Glm.greaterThanEqual((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b greaterThanEqual_(Vec3b b) {
        return Glm.greaterThanEqual((Vec3b) this, b, new Vec3b());
    }

    public Vec3b greaterThanEqual(Vec3b b, Vec3b res) {
        return Glm.greaterThanEqual((Vec3b) this, b, res);
    }

    public Vec3b equal(Vec3b b) {
        return Glm.equal((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b equal_(Vec3b b) {
        return Glm.equal((Vec3b) this, b, new Vec3b());
    }

    public Vec3b equal(Vec3b b, Vec3b res) {
        return Glm.equal((Vec3b) this, b, res);
    }

    public Vec3b notEqual(Vec3b b) {
        return Glm.notEqual((Vec3b) this, b, (Vec3b) this);
    }

    public Vec3b notEqual_(Vec3b b) {
        return Glm.notEqual((Vec3b) this, b, new Vec3b());
    }

    public Vec3b notEqual(Vec3b b, Vec3b res) {
        return Glm.notEqual((Vec3b) this, b, res);
    }
}
