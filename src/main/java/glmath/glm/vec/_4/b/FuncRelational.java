/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.b;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec4b) this);
    }

    public boolean all() {
        return Glm.all((Vec4b) this);
    }

    public Vec4b not_() {
        return Glm.not((Vec4b) this, new Vec4b());
    }

    public Vec4b not() {
        return Glm.not((Vec4b) this, (Vec4b) this);
    }

    public Vec4bool lessThan__(Vec4b b) {
        return Glm.lessThan((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4b b, Vec4bool res) {
        return Glm.lessThan((Vec4b) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4b b) {
        return Glm.lessThanEqual((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4b b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4b) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4b b) {
        return Glm.greaterThan((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4b b, Vec4bool res) {
        return Glm.greaterThan((Vec4b) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4b b) {
        return Glm.greaterThanEqual((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4b b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4b) this, b, res);
    }

    public Vec4bool equal__(Vec4b b) {
        return Glm.equal((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4b b, Vec4bool res) {
        return Glm.equal((Vec4b) this, b, res);
    }

    public Vec4bool notEqual__(Vec4b b) {
        return Glm.notEqual((Vec4b) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4b b, Vec4bool res) {
        return Glm.notEqual((Vec4b) this, b, res);
    }

    public Vec4b lessThan(Vec4b b) {
        return Glm.lessThan((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b lessThan_(Vec4b b) {
        return Glm.lessThan((Vec4b) this, b, new Vec4b());
    }

    public Vec4b lessThan(Vec4b b, Vec4b res) {
        return Glm.lessThan((Vec4b) this, b, res);
    }

    public Vec4b lessThanEqual(Vec4b b) {
        return Glm.lessThanEqual((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b lessThanEqual_(Vec4b b) {
        return Glm.lessThanEqual((Vec4b) this, b, new Vec4b());
    }

    public Vec4b lessThanEqual(Vec4b b, Vec4b res) {
        return Glm.lessThanEqual((Vec4b) this, b, res);
    }

    public Vec4b greaterThan(Vec4b b) {
        return Glm.greaterThan((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b greaterThan_(Vec4b b) {
        return Glm.greaterThan((Vec4b) this, b, new Vec4b());
    }

    public Vec4b greaterThan(Vec4b b, Vec4b res) {
        return Glm.greaterThan((Vec4b) this, b, res);
    }

    public Vec4b greaterThanEqual(Vec4b b) {
        return Glm.greaterThanEqual((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b greaterThanEqual_(Vec4b b) {
        return Glm.greaterThanEqual((Vec4b) this, b, new Vec4b());
    }

    public Vec4b greaterThanEqual(Vec4b b, Vec4b res) {
        return Glm.greaterThanEqual((Vec4b) this, b, res);
    }

    public Vec4b equal(Vec4b b) {
        return Glm.equal((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b equal_(Vec4b b) {
        return Glm.equal((Vec4b) this, b, new Vec4b());
    }

    public Vec4b equal(Vec4b b, Vec4b res) {
        return Glm.equal((Vec4b) this, b, res);
    }

    public Vec4b notEqual(Vec4b b) {
        return Glm.notEqual((Vec4b) this, b, (Vec4b) this);
    }

    public Vec4b notEqual_(Vec4b b) {
        return Glm.notEqual((Vec4b) this, b, new Vec4b());
    }

    public Vec4b notEqual(Vec4b b, Vec4b res) {
        return Glm.notEqual((Vec4b) this, b, res);
    }
}
