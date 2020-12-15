/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author elect
 */
abstract class funcRelational extends funcGeometric {

    public boolean any() {
        return Glm.any((Vec3) this);
    }

    public boolean all() {
        return Glm.all((Vec3) this);
    }
    
    public Vec3 not_() {
        return Glm.not((Vec3) this, new Vec3());
    }

    public Vec3 not() {
        return Glm.not((Vec3) this, (Vec3) this);
    }
    
    public Vec3bool lessThan__(Vec3 b) {
        return Glm.lessThan((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3 b, Vec3bool res) {
        return Glm.lessThan((Vec3) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3 b) {
        return Glm.lessThanEqual((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3 b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3 b) {
        return Glm.greaterThan((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3 b, Vec3bool res) {
        return Glm.greaterThan((Vec3) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3 b) {
        return Glm.greaterThanEqual((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3 b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3) this, b, res);
    }

    public Vec3bool equal__(Vec3 b) {
        return Glm.equal((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3 b, Vec3bool res) {
        return Glm.equal((Vec3) this, b, res);
    }

    public Vec3bool notEqual__(Vec3 b) {
        return Glm.notEqual((Vec3) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3 b, Vec3bool res) {
        return Glm.notEqual((Vec3) this, b, res);
    }

    public Vec3 lessThan(Vec3 b) {
        return Glm.lessThan((Vec3) this, b, (Vec3) this);
    }

    public Vec3 lessThan_(Vec3 b) {
        return Glm.lessThan((Vec3) this, b, new Vec3());
    }

    public Vec3 lessThan(Vec3 b, Vec3 res) {
        return Glm.lessThan((Vec3) this, b, res);
    }

    public Vec3 lessThanEqual(Vec3 b) {
        return Glm.lessThanEqual((Vec3) this, b, (Vec3) this);
    }

    public Vec3 lessThanEqual_(Vec3 b) {
        return Glm.lessThanEqual((Vec3) this, b, new Vec3());
    }

    public Vec3 lessThanEqual(Vec3 b, Vec3 res) {
        return Glm.lessThanEqual((Vec3) this, b, res);
    }

    public Vec3 greaterThan(Vec3 b) {
        return Glm.greaterThan((Vec3) this, b, (Vec3) this);
    }

    public Vec3 greaterThan_(Vec3 b) {
        return Glm.greaterThan((Vec3) this, b, new Vec3());
    }

    public Vec3 greaterThan(Vec3 b, Vec3 res) {
        return Glm.greaterThan((Vec3) this, b, res);
    }

    public Vec3 greaterThanEqual(Vec3 b) {
        return Glm.greaterThanEqual((Vec3) this, b, (Vec3) this);
    }

    public Vec3 greaterThanEqual_(Vec3 b) {
        return Glm.greaterThanEqual((Vec3) this, b, new Vec3());
    }

    public Vec3 greaterThanEqual(Vec3 b, Vec3 res) {
        return Glm.greaterThanEqual((Vec3) this, b, res);
    }

    public Vec3 equal(Vec3 b) {
        return Glm.equal((Vec3) this, b, (Vec3) this);
    }

    public Vec3 equal_(Vec3 b) {
        return Glm.equal((Vec3) this, b, new Vec3());
    }

    public Vec3 equal(Vec3 b, Vec3 res) {
        return Glm.equal((Vec3) this, b, res);
    }

    public Vec3 notEqual(Vec3 b) {
        return Glm.notEqual((Vec3) this, b, (Vec3) this);
    }

    public Vec3 notEqual_(Vec3 b) {
        return Glm.notEqual((Vec3) this, b, new Vec3());
    }

    public Vec3 notEqual(Vec3 b, Vec3 res) {
        return Glm.notEqual((Vec3) this, b, res);
    }
}
