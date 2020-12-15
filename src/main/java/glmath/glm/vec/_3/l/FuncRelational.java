/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.l;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3l) this);
    }

    public boolean all() {
        return Glm.all((Vec3l) this);
    }

    public Vec3l not_() {
        return Glm.not((Vec3l) this, new Vec3l());
    }

    public Vec3l not() {
        return Glm.not((Vec3l) this, (Vec3l) this);
    }

    public Vec3bool lessThan__(Vec3l b) {
        return Glm.lessThan((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3l a, Vec3l b, Vec3bool res) {
        return Glm.lessThan((Vec3l) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3l b) {
        return Glm.lessThanEqual((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3l b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3l) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3l b) {
        return Glm.greaterThan((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3l b, Vec3bool res) {
        return Glm.greaterThan((Vec3l) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3l b) {
        return Glm.greaterThanEqual((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3l b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3l) this, b, res);
    }

    public Vec3bool equal__(Vec3l b) {
        return Glm.equal((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3l b, Vec3bool res) {
        return Glm.equal((Vec3l) this, b, res);
    }

    public Vec3bool notEqual__(Vec3l b) {
        return Glm.notEqual((Vec3l) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3l b, Vec3bool res) {
        return Glm.notEqual((Vec3l) this, b, res);
    }

    public Vec3l lessThan(Vec3l b) {
        return Glm.lessThan((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l lessThan_(Vec3l b) {
        return Glm.lessThan((Vec3l) this, b, new Vec3l());
    }

    public Vec3l lessThan(Vec3l a, Vec3l b, Vec3l res) {
        return Glm.lessThan((Vec3l) this, b, res);
    }

    public Vec3l lessThanEqual(Vec3l b) {
        return Glm.lessThanEqual((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l lessThanEqual_(Vec3l b) {
        return Glm.lessThanEqual((Vec3l) this, b, new Vec3l());
    }

    public Vec3l lessThanEqual(Vec3l b, Vec3l res) {
        return Glm.lessThanEqual((Vec3l) this, b, res);
    }

    public Vec3l greaterThan(Vec3l b) {
        return Glm.greaterThan((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l greaterThan_(Vec3l b) {
        return Glm.greaterThan((Vec3l) this, b, new Vec3l());
    }

    public Vec3l greaterThan(Vec3l b, Vec3l res) {
        return Glm.greaterThan((Vec3l) this, b, res);
    }

    public Vec3l greaterThanEqual(Vec3l b) {
        return Glm.greaterThanEqual((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l greaterThanEqual_(Vec3l b) {
        return Glm.greaterThanEqual((Vec3l) this, b, new Vec3l());
    }

    public Vec3l greaterThanEqual(Vec3l b, Vec3l res) {
        return Glm.greaterThanEqual((Vec3l) this, b, res);
    }

    public Vec3l equal(Vec3l b) {
        return Glm.equal((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l equal_(Vec3l b) {
        return Glm.equal((Vec3l) this, b, new Vec3l());
    }

    public Vec3l equal(Vec3l b, Vec3l res) {
        return Glm.equal((Vec3l) this, b, res);
    }

    public Vec3l notEqual(Vec3l b) {
        return Glm.notEqual((Vec3l) this, b, (Vec3l) this);
    }

    public Vec3l notEqual_(Vec3l b) {
        return Glm.notEqual((Vec3l) this, b, new Vec3l());
    }

    public Vec3l notEqual(Vec3l b, Vec3l res) {
        return Glm.notEqual((Vec3l) this, b, res);
    }
}
