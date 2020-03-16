/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.us;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3us) this);
    }

    public boolean all() {
        return Glm.all((Vec3us) this);
    }

    public Vec3us not_() {
        return Glm.not((Vec3us) this, new Vec3us());
    }

    public Vec3us not() {
        return Glm.not((Vec3us) this, (Vec3us) this);
    }

    public Vec3bool lessThan__(Vec3us b) {
        return Glm.lessThan((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3us b, Vec3bool res) {
        return Glm.lessThan((Vec3us) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3us b) {
        return Glm.lessThanEqual((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3us b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3us) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3us b) {
        return Glm.greaterThan((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3us b, Vec3bool res) {
        return Glm.greaterThan((Vec3us) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3us b) {
        return Glm.greaterThanEqual((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3us b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3us) this, b, res);
    }

    public Vec3bool equal__(Vec3us b) {
        return Glm.equal((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3us b, Vec3bool res) {
        return Glm.equal((Vec3us) this, b, res);
    }

    public Vec3bool notEqual__(Vec3us b) {
        return Glm.notEqual((Vec3us) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3us b, Vec3bool res) {
        return Glm.notEqual((Vec3us) this, b, res);
    }

    public Vec3us lessThan(Vec3us b) {
        return Glm.lessThan((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us lessThan_(Vec3us b) {
        return Glm.lessThan((Vec3us) this, b, new Vec3us());
    }

    public Vec3us lessThan(Vec3us b, Vec3us res) {
        return Glm.lessThan((Vec3us) this, b, res);
    }

    public Vec3us lessThanEqual(Vec3us b) {
        return Glm.lessThanEqual((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us lessThanEqual_(Vec3us b) {
        return Glm.lessThanEqual((Vec3us) this, b, new Vec3us());
    }

    public Vec3us lessThanEqual(Vec3us b, Vec3us res) {
        return Glm.lessThanEqual((Vec3us) this, b, res);
    }

    public Vec3us greaterThan(Vec3us b) {
        return Glm.greaterThan((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us greaterThan_(Vec3us b) {
        return Glm.greaterThan((Vec3us) this, b, new Vec3us());
    }

    public Vec3us greaterThan(Vec3us b, Vec3us res) {
        return Glm.greaterThan((Vec3us) this, b, res);
    }

    public Vec3us greaterThanEqual(Vec3us b) {
        return Glm.greaterThanEqual((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us greaterThanEqual_(Vec3us b) {
        return Glm.greaterThanEqual((Vec3us) this, b, new Vec3us());
    }

    public Vec3us greaterThanEqual(Vec3us b, Vec3us res) {
        return Glm.greaterThanEqual((Vec3us) this, b, res);
    }

    public Vec3us equal(Vec3us b) {
        return Glm.equal((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us equal_(Vec3us b) {
        return Glm.equal((Vec3us) this, b, new Vec3us());
    }

    public Vec3us equal(Vec3us b, Vec3us res) {
        return Glm.equal((Vec3us) this, b, res);
    }

    public Vec3us notEqual(Vec3us b) {
        return Glm.notEqual((Vec3us) this, b, (Vec3us) this);
    }

    public Vec3us notEqual_(Vec3us b) {
        return Glm.notEqual((Vec3us) this, b, new Vec3us());
    }

    public Vec3us notEqual(Vec3us b, Vec3us res) {
        return Glm.notEqual((Vec3us) this, b, res);
    }

}
