/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author elect
 */
abstract class FuncRelational extends funcGeometric {

    public boolean any() {
        return Glm.any((Vec4) this);
    }

    public boolean all() {
        return Glm.all((Vec4) this);
    }
    
    public Vec4 not_() {
        return Glm.not((Vec4) this, new Vec4());
    }

    public Vec4 not() {
        return Glm.not((Vec4) this, (Vec4) this);
    }
    
    public Vec4bool lessThan__(Vec4 b) {
        return Glm.lessThan((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4 b, Vec4bool res) {
        return Glm.lessThan((Vec4) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4 b) {
        return Glm.lessThanEqual((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4 b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4 b) {
        return Glm.greaterThan((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4 b, Vec4bool res) {
        return Glm.greaterThan((Vec4) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4 b) {
        return Glm.greaterThanEqual((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4 b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4) this, b, res);
    }

    public Vec4bool equal__(Vec4 b) {
        return Glm.equal((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4 b, Vec4bool res) {
        return Glm.equal((Vec4) this, b, res);
    }

    public Vec4bool notEqual__(Vec4 b) {
        return Glm.notEqual((Vec4) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4 b, Vec4bool res) {
        return Glm.notEqual((Vec4) this, b, res);
    }

    public Vec4 lessThan(Vec4 b) {
        return Glm.lessThan((Vec4) this, b, (Vec4) this);
    }

    public Vec4 lessThan_(Vec4 b) {
        return Glm.lessThan((Vec4) this, b, new Vec4());
    }

    public Vec4 lessThan(Vec4 b, Vec4 res) {
        return Glm.lessThan((Vec4) this, b, res);
    }

    public Vec4 lessThanEqual(Vec4 b) {
        return Glm.lessThanEqual((Vec4) this, b, (Vec4) this);
    }

    public Vec4 lessThanEqual_(Vec4 b) {
        return Glm.lessThanEqual((Vec4) this, b, new Vec4());
    }

    public Vec4 lessThanEqual(Vec4 b, Vec4 res) {
        return Glm.lessThanEqual((Vec4) this, b, res);
    }

    public Vec4 greaterThan(Vec4 b) {
        return Glm.greaterThan((Vec4) this, b, (Vec4) this);
    }

    public Vec4 greaterThan_(Vec4 b) {
        return Glm.greaterThan((Vec4) this, b, new Vec4());
    }

    public Vec4 greaterThan(Vec4 b, Vec4 res) {
        return Glm.greaterThan((Vec4) this, b, res);
    }

    public Vec4 greaterThanEqual(Vec4 b) {
        return Glm.greaterThanEqual((Vec4) this, b, (Vec4) this);
    }

    public Vec4 greaterThanEqual_(Vec4 b) {
        return Glm.greaterThanEqual((Vec4) this, b, new Vec4());
    }

    public Vec4 greaterThanEqual(Vec4 b, Vec4 res) {
        return Glm.greaterThanEqual((Vec4) this, b, res);
    }

    public Vec4 equal(Vec4 b) {
        return Glm.equal((Vec4) this, b, (Vec4) this);
    }

    public Vec4 equal_(Vec4 b) {
        return Glm.equal((Vec4) this, b, new Vec4());
    }

    public Vec4 equal(Vec4 b, Vec4 res) {
        return Glm.equal((Vec4) this, b, res);
    }

    public Vec4 notEqual(Vec4 b) {
        return Glm.notEqual((Vec4) this, b, (Vec4) this);
    }

    public Vec4 notEqual_(Vec4 b) {
        return Glm.notEqual((Vec4) this, b, new Vec4());
    }

    public Vec4 notEqual(Vec4 b, Vec4 res) {
        return Glm.notEqual((Vec4) this, b, res);
    }
}
