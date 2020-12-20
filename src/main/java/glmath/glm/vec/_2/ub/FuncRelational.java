/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ub;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec2ub) this);
    }

    public boolean all() {
        return Glm.all((Vec2ub) this);
    }

    public Vec2ub not_() {
        return Glm.not((Vec2ub) this, new Vec2ub());
    }

    public Vec2ub not() {
        return Glm.not((Vec2ub) this, (Vec2ub) this);
    }

    public Vec2bool lessThan__(Vec2ub b) {
        return Glm.lessThan((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2ub b, Vec2bool res) {
        return Glm.lessThan((Vec2ub) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2ub b) {
        return Glm.lessThanEqual((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2ub b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2ub) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2ub b) {
        return Glm.greaterThan((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2ub b, Vec2bool res) {
        return Glm.greaterThan((Vec2ub) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2ub b) {
        return Glm.greaterThanEqual((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2ub b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2ub) this, b, res);
    }

    public Vec2bool equal__(Vec2ub b) {
        return Glm.equal((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2ub b, Vec2bool res) {
        return Glm.equal((Vec2ub) this, b, res);
    }

    public Vec2bool notEqual__(Vec2ub b) {
        return Glm.notEqual((Vec2ub) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2ub b, Vec2bool res) {
        return Glm.notEqual((Vec2ub) this, b, res);
    }

    public Vec2ub lessThan(Vec2ub b) {
        return Glm.lessThan((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub lessThan_(Vec2ub b) {
        return Glm.lessThan((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub lessThan(Vec2ub b, Vec2ub res) {
        return Glm.lessThan((Vec2ub) this, b, res);
    }

    public Vec2ub lessThanEqual(Vec2ub b) {
        return Glm.lessThanEqual((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub lessThanEqual_(Vec2ub b) {
        return Glm.lessThanEqual((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub lessThanEqual(Vec2ub b, Vec2ub res) {
        return Glm.lessThanEqual((Vec2ub) this, b, res);
    }

    public Vec2ub greaterThan(Vec2ub b) {
        return Glm.greaterThan((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub greaterThan_(Vec2ub b) {
        return Glm.greaterThan((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub greaterThan(Vec2ub b, Vec2ub res) {
        return Glm.greaterThan((Vec2ub) this, b, res);
    }

    public Vec2ub greaterThanEqual(Vec2ub b) {
        return Glm.greaterThanEqual((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub greaterThanEqual_(Vec2ub b) {
        return Glm.greaterThanEqual((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub greaterThanEqual(Vec2ub b, Vec2ub res) {
        return Glm.greaterThanEqual((Vec2ub) this, b, res);
    }

    public Vec2ub equal(Vec2ub b) {
        return Glm.equal((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub equal_(Vec2ub b) {
        return Glm.equal((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub equal(Vec2ub b, Vec2ub res) {
        return Glm.equal((Vec2ub) this, b, res);
    }

    public Vec2ub notEqual(Vec2ub b) {
        return Glm.notEqual((Vec2ub) this, b, (Vec2ub) this);
    }

    public Vec2ub notEqual_(Vec2ub b) {
        return Glm.notEqual((Vec2ub) this, b, new Vec2ub());
    }

    public Vec2ub notEqual(Vec2ub b, Vec2ub res) {
        return Glm.notEqual((Vec2ub) this, b, res);
    }
}
