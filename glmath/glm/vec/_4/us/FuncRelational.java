/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.us;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec4us) this);
    }

    public boolean all() {
        return Glm.all((Vec4us) this);
    }

    public Vec4us not_() {
        return Glm.not((Vec4us) this, new Vec4us());
    }

    public Vec4us not() {
        return Glm.not((Vec4us) this, (Vec4us) this);
    }

    public Vec4bool lessThan__(Vec4us b) {
        return Glm.lessThan((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4us b, Vec4bool res) {
        return Glm.lessThan((Vec4us) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4us b) {
        return Glm.lessThanEqual((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4us b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4us) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4us b) {
        return Glm.greaterThan((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4us b, Vec4bool res) {
        return Glm.greaterThan((Vec4us) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4us b) {
        return Glm.greaterThanEqual((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4us b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4us) this, b, res);
    }

    public Vec4bool equal__(Vec4us b) {
        return Glm.equal((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4us b, Vec4bool res) {
        return Glm.equal((Vec4us) this, b, res);
    }

    public Vec4bool notEqual__(Vec4us b) {
        return Glm.notEqual((Vec4us) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4us b, Vec4bool res) {
        return Glm.notEqual((Vec4us) this, b, res);
    }

    public Vec4us lessThan(Vec4us b) {
        return Glm.lessThan((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us lessThan_(Vec4us b) {
        return Glm.lessThan((Vec4us) this, b, new Vec4us());
    }

    public Vec4us lessThan(Vec4us b, Vec4us res) {
        return Glm.lessThan((Vec4us) this, b, res);
    }

    public Vec4us lessThanEqual(Vec4us b) {
        return Glm.lessThanEqual((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us lessThanEqual_(Vec4us b) {
        return Glm.lessThanEqual((Vec4us) this, b, new Vec4us());
    }

    public Vec4us lessThanEqual(Vec4us b, Vec4us res) {
        return Glm.lessThanEqual((Vec4us) this, b, res);
    }

    public Vec4us greaterThan(Vec4us b) {
        return Glm.greaterThan((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us greaterThan_(Vec4us b) {
        return Glm.greaterThan((Vec4us) this, b, new Vec4us());
    }

    public Vec4us greaterThan(Vec4us b, Vec4us res) {
        return Glm.greaterThan((Vec4us) this, b, res);
    }

    public Vec4us greaterThanEqual(Vec4us b) {
        return Glm.greaterThanEqual((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us greaterThanEqual_(Vec4us b) {
        return Glm.greaterThanEqual((Vec4us) this, b, new Vec4us());
    }

    public Vec4us greaterThanEqual(Vec4us b, Vec4us res) {
        return Glm.greaterThanEqual((Vec4us) this, b, res);
    }

    public Vec4us equal(Vec4us b) {
        return Glm.equal((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us equal_(Vec4us b) {
        return Glm.equal((Vec4us) this, b, new Vec4us());
    }

    public Vec4us equal(Vec4us b, Vec4us res) {
        return Glm.equal((Vec4us) this, b, res);
    }

    public Vec4us notEqual(Vec4us b) {
        return Glm.notEqual((Vec4us) this, b, (Vec4us) this);
    }

    public Vec4us notEqual_(Vec4us b) {
        return Glm.notEqual((Vec4us) this, b, new Vec4us());
    }

    public Vec4us notEqual(Vec4us b, Vec4us res) {
        return Glm.notEqual((Vec4us) this, b, res);
    }

}
