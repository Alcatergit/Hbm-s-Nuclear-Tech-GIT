/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ub;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec4ub) this);
    }

    public boolean all() {
        return Glm.all((Vec4ub) this);
    }

    public Vec4ub not_() {
        return Glm.not((Vec4ub) this, new Vec4ub());
    }

    public Vec4ub not() {
        return Glm.not((Vec4ub) this, (Vec4ub) this);
    }

    public Vec4bool lessThan__(Vec4ub b) {
        return Glm.lessThan((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4ub b, Vec4bool res) {
        return Glm.lessThan((Vec4ub) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4ub b) {
        return Glm.lessThanEqual((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4ub b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4ub) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4ub b) {
        return Glm.greaterThan((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4ub b, Vec4bool res) {
        return Glm.greaterThan((Vec4ub) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4ub b) {
        return Glm.greaterThanEqual((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4ub b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4ub) this, b, res);
    }

    public Vec4bool equal__(Vec4ub b) {
        return Glm.equal((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4ub b, Vec4bool res) {
        return Glm.equal((Vec4ub) this, b, res);
    }

    public Vec4bool notEqual__(Vec4ub b) {
        return Glm.notEqual((Vec4ub) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4ub b, Vec4bool res) {
        return Glm.notEqual((Vec4ub) this, b, res);
    }

    public Vec4ub lessThan(Vec4ub b) {
        return Glm.lessThan((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub lessThan_(Vec4ub b) {
        return Glm.lessThan((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub lessThan(Vec4ub b, Vec4ub res) {
        return Glm.lessThan((Vec4ub) this, b, res);
    }

    public Vec4ub lessThanEqual(Vec4ub b) {
        return Glm.lessThanEqual((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub lessThanEqual_(Vec4ub b) {
        return Glm.lessThanEqual((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub lessThanEqual(Vec4ub b, Vec4ub res) {
        return Glm.lessThanEqual((Vec4ub) this, b, res);
    }

    public Vec4ub greaterThan(Vec4ub b) {
        return Glm.greaterThan((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub greaterThan_(Vec4ub b) {
        return Glm.greaterThan((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub greaterThan(Vec4ub b, Vec4ub res) {
        return Glm.greaterThan((Vec4ub) this, b, res);
    }

    public Vec4ub greaterThanEqual(Vec4ub b) {
        return Glm.greaterThanEqual((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub greaterThanEqual_(Vec4ub b) {
        return Glm.greaterThanEqual((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub greaterThanEqual(Vec4ub b, Vec4ub res) {
        return Glm.greaterThanEqual((Vec4ub) this, b, res);
    }

    public Vec4ub equal(Vec4ub b) {
        return Glm.equal((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub equal_(Vec4ub b) {
        return Glm.equal((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub equal(Vec4ub b, Vec4ub res) {
        return Glm.equal((Vec4ub) this, b, res);
    }

    public Vec4ub notEqual(Vec4ub b) {
        return Glm.notEqual((Vec4ub) this, b, (Vec4ub) this);
    }

    public Vec4ub notEqual_(Vec4ub b) {
        return Glm.notEqual((Vec4ub) this, b, new Vec4ub());
    }

    public Vec4ub notEqual(Vec4ub b, Vec4ub res) {
        return Glm.notEqual((Vec4ub) this, b, res);
    }
}
