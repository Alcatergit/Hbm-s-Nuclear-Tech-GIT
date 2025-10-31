/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ul;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3ul) this);
    }

    public boolean all() {
        return Glm.all((Vec3ul) this);
    }

    public Vec3ul not_() {
        return Glm.not((Vec3ul) this, new Vec3ul());
    }

    public Vec3ul not() {
        return Glm.not((Vec3ul) this, (Vec3ul) this);
    }

    public Vec3bool lessThan__(Vec3ul b) {
        return Glm.lessThan((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3ul b, Vec3bool res) {
        return Glm.lessThan((Vec3ul) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3ul b) {
        return Glm.lessThanEqual((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3ul b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3ul) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3ul b) {
        return Glm.greaterThan((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3ul b, Vec3bool res) {
        return Glm.greaterThan((Vec3ul) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3ul b) {
        return Glm.greaterThanEqual((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3ul b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3ul) this, b, res);
    }

    public Vec3bool equal__(Vec3ul b) {
        return Glm.equal((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3ul b, Vec3bool res) {
        return Glm.equal((Vec3ul) this, b, res);
    }

    public Vec3bool notEqual__(Vec3ul b) {
        return Glm.notEqual((Vec3ul) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3ul b, Vec3bool res) {
        return Glm.notEqual((Vec3ul) this, b, res);
    }

    public Vec3ul lessThan(Vec3ul b) {
        return Glm.lessThan((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul lessThan_(Vec3ul b) {
        return Glm.lessThan((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul lessThan(Vec3ul b, Vec3ul res) {
        return Glm.lessThan((Vec3ul) this, b, res);
    }

    public Vec3ul lessThanEqual(Vec3ul b) {
        return Glm.lessThanEqual((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul lessThanEqual_(Vec3ul b) {
        return Glm.lessThanEqual((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul lessThanEqual(Vec3ul b, Vec3ul res) {
        return Glm.lessThanEqual((Vec3ul) this, b, res);
    }

    public Vec3ul greaterThan(Vec3ul b) {
        return Glm.greaterThan((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul greaterThan_(Vec3ul b) {
        return Glm.greaterThan((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul greaterThan(Vec3ul b, Vec3ul res) {
        return Glm.greaterThan((Vec3ul) this, b, res);
    }

    public Vec3ul greaterThanEqual(Vec3ul b) {
        return Glm.greaterThanEqual((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul greaterThanEqual_(Vec3ul b) {
        return Glm.greaterThanEqual((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul greaterThanEqual(Vec3ul b, Vec3ul res) {
        return Glm.greaterThanEqual((Vec3ul) this, b, res);
    }

    public Vec3ul equal(Vec3ul b) {
        return Glm.equal((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul equal_(Vec3ul b) {
        return Glm.equal((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul equal(Vec3ul b, Vec3ul res) {
        return Glm.equal((Vec3ul) this, b, res);
    }

    public Vec3ul notEqual(Vec3ul b) {
        return Glm.notEqual((Vec3ul) this, b, (Vec3ul) this);
    }

    public Vec3ul notEqual_(Vec3ul b) {
        return Glm.notEqual((Vec3ul) this, b, new Vec3ul());
    }

    public Vec3ul notEqual(Vec3ul b, Vec3ul res) {
        return Glm.notEqual((Vec3ul) this, b, res);
    }
}
