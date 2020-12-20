/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ul;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec4ul) this);
    }

    public boolean all() {
        return Glm.all((Vec4ul) this);
    }

    public Vec4ul not_() {
        return Glm.not((Vec4ul) this, new Vec4ul());
    }

    public Vec4ul not() {
        return Glm.not((Vec4ul) this, (Vec4ul) this);
    }

    public Vec4bool lessThan__(Vec4ul b) {
        return Glm.lessThan((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4ul b, Vec4bool res) {
        return Glm.lessThan((Vec4ul) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4ul b) {
        return Glm.lessThanEqual((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4ul b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4ul) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4ul b) {
        return Glm.greaterThan((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4ul b, Vec4bool res) {
        return Glm.greaterThan((Vec4ul) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4ul b) {
        return Glm.greaterThanEqual((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4ul b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4ul) this, b, res);
    }

    public Vec4bool equal__(Vec4ul b) {
        return Glm.equal((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4ul b, Vec4bool res) {
        return Glm.equal((Vec4ul) this, b, res);
    }

    public Vec4bool notEqual__(Vec4ul b) {
        return Glm.notEqual((Vec4ul) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4ul b, Vec4bool res) {
        return Glm.notEqual((Vec4ul) this, b, res);
    }

    public Vec4ul lessThan(Vec4ul b) {
        return Glm.lessThan((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul lessThan_(Vec4ul b) {
        return Glm.lessThan((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul lessThan(Vec4ul b, Vec4ul res) {
        return Glm.lessThan((Vec4ul) this, b, res);
    }

    public Vec4ul lessThanEqual(Vec4ul b) {
        return Glm.lessThanEqual((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul lessThanEqual_(Vec4ul b) {
        return Glm.lessThanEqual((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul lessThanEqual(Vec4ul b, Vec4ul res) {
        return Glm.lessThanEqual((Vec4ul) this, b, res);
    }

    public Vec4ul greaterThan(Vec4ul b) {
        return Glm.greaterThan((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul greaterThan_(Vec4ul b) {
        return Glm.greaterThan((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul greaterThan(Vec4ul b, Vec4ul res) {
        return Glm.greaterThan((Vec4ul) this, b, res);
    }

    public Vec4ul greaterThanEqual(Vec4ul b) {
        return Glm.greaterThanEqual((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul greaterThanEqual_(Vec4ul b) {
        return Glm.greaterThanEqual((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul greaterThanEqual(Vec4ul b, Vec4ul res) {
        return Glm.greaterThanEqual((Vec4ul) this, b, res);
    }

    public Vec4ul equal(Vec4ul b) {
        return Glm.equal((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul equal_(Vec4ul b) {
        return Glm.equal((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul equal(Vec4ul b, Vec4ul res) {
        return Glm.equal((Vec4ul) this, b, res);
    }

    public Vec4ul notEqual(Vec4ul b) {
        return Glm.notEqual((Vec4ul) this, b, (Vec4ul) this);
    }

    public Vec4ul notEqual_(Vec4ul b) {
        return Glm.notEqual((Vec4ul) this, b, new Vec4ul());
    }

    public Vec4ul notEqual(Vec4ul b, Vec4ul res) {
        return Glm.notEqual((Vec4ul) this, b, res);
    }
}
