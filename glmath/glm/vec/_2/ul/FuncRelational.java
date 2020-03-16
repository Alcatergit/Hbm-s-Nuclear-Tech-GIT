/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ul;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec2ul) this);
    }

    public boolean all() {
        return Glm.all((Vec2ul) this);
    }

    public Vec2ul not_() {
        return Glm.not((Vec2ul) this, new Vec2ul());
    }

    public Vec2ul not() {
        return Glm.not((Vec2ul) this, (Vec2ul) this);
    }

    public Vec2bool lessThan__(Vec2ul b) {
        return Glm.lessThan((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2ul b, Vec2bool res) {
        return Glm.lessThan((Vec2ul) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2ul b) {
        return Glm.lessThanEqual((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2ul b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2ul) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2ul b) {
        return Glm.greaterThan((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2ul b, Vec2bool res) {
        return Glm.greaterThan((Vec2ul) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2ul b) {
        return Glm.greaterThanEqual((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2ul b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2ul) this, b, res);
    }

    public Vec2bool equal__(Vec2ul b) {
        return Glm.equal((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2ul b, Vec2bool res) {
        return Glm.equal((Vec2ul) this, b, res);
    }

    public Vec2bool notEqual__(Vec2ul b) {
        return Glm.notEqual((Vec2ul) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2ul b, Vec2bool res) {
        return Glm.notEqual((Vec2ul) this, b, res);
    }

    public Vec2ul lessThan(Vec2ul b) {
        return Glm.lessThan((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul lessThan_(Vec2ul b) {
        return Glm.lessThan((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul lessThan(Vec2ul b, Vec2ul res) {
        return Glm.lessThan((Vec2ul) this, b, res);
    }

    public Vec2ul lessThanEqual(Vec2ul b) {
        return Glm.lessThanEqual((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul lessThanEqual_(Vec2ul b) {
        return Glm.lessThanEqual((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul lessThanEqual(Vec2ul b, Vec2ul res) {
        return Glm.lessThanEqual((Vec2ul) this, b, res);
    }

    public Vec2ul greaterThan(Vec2ul b) {
        return Glm.greaterThan((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul greaterThan_(Vec2ul b) {
        return Glm.greaterThan((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul greaterThan(Vec2ul b, Vec2ul res) {
        return Glm.greaterThan((Vec2ul) this, b, res);
    }

    public Vec2ul greaterThanEqual(Vec2ul b) {
        return Glm.greaterThanEqual((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul greaterThanEqual_(Vec2ul b) {
        return Glm.greaterThanEqual((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul greaterThanEqual(Vec2ul b, Vec2ul res) {
        return Glm.greaterThanEqual((Vec2ul) this, b, res);
    }

    public Vec2ul equal(Vec2ul b) {
        return Glm.equal((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul equal_(Vec2ul b) {
        return Glm.equal((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul equal(Vec2ul b, Vec2ul res) {
        return Glm.equal((Vec2ul) this, b, res);
    }

    public Vec2ul notEqual(Vec2ul b) {
        return Glm.notEqual((Vec2ul) this, b, (Vec2ul) this);
    }

    public Vec2ul notEqual_(Vec2ul b) {
        return Glm.notEqual((Vec2ul) this, b, new Vec2ul());
    }

    public Vec2ul notEqual(Vec2ul b, Vec2ul res) {
        return Glm.notEqual((Vec2ul) this, b, res);
    }
}
