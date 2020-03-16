/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.d;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3d) this);
    }

    public boolean all() {
        return Glm.all((Vec3d) this);
    }

    public Vec3d not_() {
        return Glm.not((Vec3d) this, new Vec3d());
    }

    public Vec3d not() {
        return Glm.not((Vec3d) this, (Vec3d) this);
    }

    public Vec3bool lessThan__(Vec3d b) {
        return Glm.lessThan((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3d b, Vec3bool res) {
        return Glm.lessThan((Vec3d) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3d b) {
        return Glm.lessThanEqual((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3d b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3d) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3d b) {
        return Glm.greaterThan((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3d b, Vec3bool res) {
        return Glm.greaterThan((Vec3d) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3d b) {
        return Glm.greaterThanEqual((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3d b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3d) this, b, res);
    }

    public Vec3bool equal__(Vec3d b) {
        return Glm.equal((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3d b, Vec3bool res) {
        return Glm.equal((Vec3d) this, b, res);
    }

    public Vec3bool notEqual__(Vec3d b) {
        return Glm.notEqual((Vec3d) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3d b, Vec3bool res) {
        return Glm.notEqual((Vec3d) this, b, res);
    }

    public Vec3d lessThan(Vec3d b) {
        return Glm.lessThan((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d lessThan_(Vec3d b) {
        return Glm.lessThan((Vec3d) this, b, new Vec3d());
    }

    public Vec3d lessThan(Vec3d b, Vec3d res) {
        return Glm.lessThan((Vec3d) this, b, res);
    }

    public Vec3d lessThanEqual(Vec3d b) {
        return Glm.lessThanEqual((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d lessThanEqual_(Vec3d b) {
        return Glm.lessThanEqual((Vec3d) this, b, new Vec3d());
    }

    public Vec3d lessThanEqual(Vec3d b, Vec3d res) {
        return Glm.lessThanEqual((Vec3d) this, b, res);
    }

    public Vec3d greaterThan(Vec3d b) {
        return Glm.greaterThan((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d greaterThan_(Vec3d b) {
        return Glm.greaterThan((Vec3d) this, b, new Vec3d());
    }

    public Vec3d greaterThan(Vec3d b, Vec3d res) {
        return Glm.greaterThan((Vec3d) this, b, res);
    }

    public Vec3d greaterThanEqual(Vec3d b) {
        return Glm.greaterThanEqual((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d greaterThanEqual_(Vec3d b) {
        return Glm.greaterThanEqual((Vec3d) this, b, new Vec3d());
    }

    public Vec3d greaterThanEqual(Vec3d b, Vec3d res) {
        return Glm.greaterThanEqual((Vec3d) this, b, res);
    }

    public Vec3d equal(Vec3d b) {
        return Glm.equal((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d equal_(Vec3d b) {
        return Glm.equal((Vec3d) this, b, new Vec3d());
    }

    public Vec3d equal(Vec3d b, Vec3d res) {
        return Glm.equal((Vec3d) this, b, res);
    }

    public Vec3d notEqual(Vec3d b) {
        return Glm.notEqual((Vec3d) this, b, (Vec3d) this);
    }

    public Vec3d notEqual_(Vec3d b) {
        return Glm.notEqual((Vec3d) this, b, new Vec3d());
    }

    public Vec3d notEqual(Vec3d b, Vec3d res) {
        return Glm.notEqual((Vec3d) this, b, res);
    }
}
