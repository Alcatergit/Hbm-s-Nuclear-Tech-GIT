/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.d;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends BooleanOperators {

    public boolean any() {
        return Glm.any((Vec2d) this);
    }

    public boolean all() {
        return Glm.all((Vec2d) this);
    }

    public Vec2d not_() {
        return Glm.not((Vec2d) this, new Vec2d());
    }

    public Vec2d not() {
        return Glm.not((Vec2d) this, (Vec2d) this);
    }

    public Vec2bool lessThan__(Vec2d b) {
        return Glm.lessThan((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2d b, Vec2bool res) {
        return Glm.lessThan((Vec2d) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2d b) {
        return Glm.lessThanEqual((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2d b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2d) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2d b) {
        return Glm.greaterThan((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2d b, Vec2bool res) {
        return Glm.greaterThan((Vec2d) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2d b) {
        return Glm.greaterThanEqual((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2d b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2d) this, b, res);
    }

    public Vec2bool equal__(Vec2d b) {
        return Glm.equal((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2d b, Vec2bool res) {
        return Glm.equal((Vec2d) this, b, res);
    }

    public Vec2bool notEqual__(Vec2d b) {
        return Glm.notEqual((Vec2d) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2d b, Vec2bool res) {
        return Glm.notEqual((Vec2d) this, b, res);
    }

    public Vec2d lessThan(Vec2d b) {
        return Glm.lessThan((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d lessThan_(Vec2d b) {
        return Glm.lessThan((Vec2d) this, b, new Vec2d());
    }

    public Vec2d lessThan(Vec2d b, Vec2d res) {
        return Glm.lessThan((Vec2d) this, b, res);
    }

    public Vec2d lessThanEqual(Vec2d b) {
        return Glm.lessThanEqual((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d lessThanEqual_(Vec2d b) {
        return Glm.lessThanEqual((Vec2d) this, b, new Vec2d());
    }

    public Vec2d lessThanEqual(Vec2d b, Vec2d res) {
        return Glm.lessThanEqual((Vec2d) this, b, res);
    }

    public Vec2d greaterThan(Vec2d b) {
        return Glm.greaterThan((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d greaterThan_(Vec2d b) {
        return Glm.greaterThan((Vec2d) this, b, new Vec2d());
    }

    public Vec2d greaterThan(Vec2d b, Vec2d res) {
        return Glm.greaterThan((Vec2d) this, b, res);
    }

    public Vec2d greaterThanEqual(Vec2d b) {
        return Glm.greaterThanEqual((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d greaterThanEqual_(Vec2d b) {
        return Glm.greaterThanEqual((Vec2d) this, b, new Vec2d());
    }

    public Vec2d greaterThanEqual(Vec2d b, Vec2d res) {
        return Glm.greaterThanEqual((Vec2d) this, b, res);
    }

    public Vec2d equal(Vec2d b) {
        return Glm.equal((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d equal_(Vec2d b) {
        return Glm.equal((Vec2d) this, b, new Vec2d());
    }

    public Vec2d equal(Vec2d b, Vec2d res) {
        return Glm.equal((Vec2d) this, b, res);
    }

    public Vec2d notEqual(Vec2d b) {
        return Glm.notEqual((Vec2d) this, b, (Vec2d) this);
    }

    public Vec2d notEqual_(Vec2d b) {
        return Glm.notEqual((Vec2d) this, b, new Vec2d());
    }

    public Vec2d notEqual(Vec2d b, Vec2d res) {
        return Glm.notEqual((Vec2d) this, b, res);
    }
}
