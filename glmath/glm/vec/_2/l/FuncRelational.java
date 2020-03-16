/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.l;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec2l) this);
    }

    public boolean all() {
        return Glm.all((Vec2l) this);
    }

    public Vec2l not_() {
        return Glm.not((Vec2l) this, new Vec2l());
    }

    public Vec2l not() {
        return Glm.not((Vec2l) this, (Vec2l) this);
    }

    public Vec2bool lessThan__(Vec2l b) {
        return Glm.lessThan((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2l a, Vec2l b, Vec2bool res) {
        return Glm.lessThan((Vec2l) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2l b) {
        return Glm.lessThanEqual((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2l b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2l) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2l b) {
        return Glm.greaterThan((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2l b, Vec2bool res) {
        return Glm.greaterThan((Vec2l) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2l b) {
        return Glm.greaterThanEqual((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2l b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2l) this, b, res);
    }

    public Vec2bool equal__(Vec2l b) {
        return Glm.equal((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2l b, Vec2bool res) {
        return Glm.equal((Vec2l) this, b, res);
    }

    public Vec2bool notEqual__(Vec2l b) {
        return Glm.notEqual((Vec2l) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2l b, Vec2bool res) {
        return Glm.notEqual((Vec2l) this, b, res);
    }

    public Vec2l lessThan(Vec2l b) {
        return Glm.lessThan((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l lessThan_(Vec2l b) {
        return Glm.lessThan((Vec2l) this, b, new Vec2l());
    }

    public Vec2l lessThan(Vec2l a, Vec2l b, Vec2l res) {
        return Glm.lessThan((Vec2l) this, b, res);
    }

    public Vec2l lessThanEqual(Vec2l b) {
        return Glm.lessThanEqual((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l lessThanEqual_(Vec2l b) {
        return Glm.lessThanEqual((Vec2l) this, b, new Vec2l());
    }

    public Vec2l lessThanEqual(Vec2l b, Vec2l res) {
        return Glm.lessThanEqual((Vec2l) this, b, res);
    }

    public Vec2l greaterThan(Vec2l b) {
        return Glm.greaterThan((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l greaterThan_(Vec2l b) {
        return Glm.greaterThan((Vec2l) this, b, new Vec2l());
    }

    public Vec2l greaterThan(Vec2l b, Vec2l res) {
        return Glm.greaterThan((Vec2l) this, b, res);
    }

    public Vec2l greaterThanEqual(Vec2l b) {
        return Glm.greaterThanEqual((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l greaterThanEqual_(Vec2l b) {
        return Glm.greaterThanEqual((Vec2l) this, b, new Vec2l());
    }

    public Vec2l greaterThanEqual(Vec2l b, Vec2l res) {
        return Glm.greaterThanEqual((Vec2l) this, b, res);
    }

    public Vec2l equal(Vec2l b) {
        return Glm.equal((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l equal_(Vec2l b) {
        return Glm.equal((Vec2l) this, b, new Vec2l());
    }

    public Vec2l equal(Vec2l b, Vec2l res) {
        return Glm.equal((Vec2l) this, b, res);
    }

    public Vec2l notEqual(Vec2l b) {
        return Glm.notEqual((Vec2l) this, b, (Vec2l) this);
    }

    public Vec2l notEqual_(Vec2l b) {
        return Glm.notEqual((Vec2l) this, b, new Vec2l());
    }

    public Vec2l notEqual(Vec2l b, Vec2l res) {
        return Glm.notEqual((Vec2l) this, b, res);
    }
}
