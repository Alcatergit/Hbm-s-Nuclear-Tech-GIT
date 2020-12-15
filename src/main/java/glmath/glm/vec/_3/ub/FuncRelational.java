/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ub;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3ub) this);
    }

    public boolean all() {
        return Glm.all((Vec3ub) this);
    }

    public Vec3ub not_() {
        return Glm.not((Vec3ub) this, new Vec3ub());
    }

    public Vec3ub not() {
        return Glm.not((Vec3ub) this, (Vec3ub) this);
    }

    public Vec3bool lessThan__(Vec3ub b) {
        return Glm.lessThan((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3ub b, Vec3bool res) {
        return Glm.lessThan((Vec3ub) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3ub b) {
        return Glm.lessThanEqual((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3ub b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3ub) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3ub b) {
        return Glm.greaterThan((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3ub b, Vec3bool res) {
        return Glm.greaterThan((Vec3ub) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3ub b) {
        return Glm.greaterThanEqual((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3ub b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3ub) this, b, res);
    }

    public Vec3bool equal__(Vec3ub b) {
        return Glm.equal((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3ub b, Vec3bool res) {
        return Glm.equal((Vec3ub) this, b, res);
    }

    public Vec3bool notEqual__(Vec3ub b) {
        return Glm.notEqual((Vec3ub) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3ub b, Vec3bool res) {
        return Glm.notEqual((Vec3ub) this, b, res);
    }

    public Vec3ub lessThan(Vec3ub b) {
        return Glm.lessThan((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub lessThan_(Vec3ub b) {
        return Glm.lessThan((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub lessThan(Vec3ub b, Vec3ub res) {
        return Glm.lessThan((Vec3ub) this, b, res);
    }

    public Vec3ub lessThanEqual(Vec3ub b) {
        return Glm.lessThanEqual((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub lessThanEqual_(Vec3ub b) {
        return Glm.lessThanEqual((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub lessThanEqual(Vec3ub b, Vec3ub res) {
        return Glm.lessThanEqual((Vec3ub) this, b, res);
    }

    public Vec3ub greaterThan(Vec3ub b) {
        return Glm.greaterThan((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub greaterThan_(Vec3ub b) {
        return Glm.greaterThan((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub greaterThan(Vec3ub b, Vec3ub res) {
        return Glm.greaterThan((Vec3ub) this, b, res);
    }

    public Vec3ub greaterThanEqual(Vec3ub b) {
        return Glm.greaterThanEqual((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub greaterThanEqual_(Vec3ub b) {
        return Glm.greaterThanEqual((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub greaterThanEqual(Vec3ub b, Vec3ub res) {
        return Glm.greaterThanEqual((Vec3ub) this, b, res);
    }

    public Vec3ub equal(Vec3ub b) {
        return Glm.equal((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub equal_(Vec3ub b) {
        return Glm.equal((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub equal(Vec3ub b, Vec3ub res) {
        return Glm.equal((Vec3ub) this, b, res);
    }

    public Vec3ub notEqual(Vec3ub b) {
        return Glm.notEqual((Vec3ub) this, b, (Vec3ub) this);
    }

    public Vec3ub notEqual_(Vec3ub b) {
        return Glm.notEqual((Vec3ub) this, b, new Vec3ub());
    }

    public Vec3ub notEqual(Vec3ub b, Vec3ub res) {
        return Glm.notEqual((Vec3ub) this, b, res);
    }
}
