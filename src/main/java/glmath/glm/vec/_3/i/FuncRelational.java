/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.i;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author fschaefers
 */
abstract class FuncRelational extends funcCommon {

    public boolean any() {
        return Glm.any((Vec3i) this);
    }

    public boolean all() {
        return Glm.all((Vec3i) this);
    }
    
    public Vec3i not_() {
        return Glm.not((Vec3i) this, new Vec3i());
    }

    public Vec3i not() {
        return Glm.not((Vec3i) this, (Vec3i) this);
    }
    
    public Vec3bool lessThan__(Vec3i b) {
        return Glm.lessThan((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3i b, Vec3bool res) {
        return Glm.lessThan((Vec3i) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3i b) {
        return Glm.lessThanEqual((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3i b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3i) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3i b) {
        return Glm.greaterThan((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3i b, Vec3bool res) {
        return Glm.greaterThan((Vec3i) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3i b) {
        return Glm.greaterThanEqual((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3i b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3i) this, b, res);
    }

    public Vec3bool equal__(Vec3i b) {
        return Glm.equal((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3i b, Vec3bool res) {
        return Glm.equal((Vec3i) this, b, res);
    }

    public Vec3bool notEqual__(Vec3i b) {
        return Glm.notEqual((Vec3i) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3i b, Vec3bool res) {
        return Glm.notEqual((Vec3i) this, b, res);
    }

    public Vec3i lessThan(Vec3i b) {
        return Glm.lessThan((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i lessThan_(Vec3i b) {
        return Glm.lessThan((Vec3i) this, b, new Vec3i());
    }

    public Vec3i lessThan(Vec3i b, Vec3i res) {
        return Glm.lessThan((Vec3i) this, b, res);
    }

    public Vec3i lessThanEqual(Vec3i b) {
        return Glm.lessThanEqual((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i lessThanEqual_(Vec3i b) {
        return Glm.lessThanEqual((Vec3i) this, b, new Vec3i());
    }

    public Vec3i lessThanEqual(Vec3i b, Vec3i res) {
        return Glm.lessThanEqual((Vec3i) this, b, res);
    }

    public Vec3i greaterThan(Vec3i b) {
        return Glm.greaterThan((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i greaterThan_(Vec3i b) {
        return Glm.greaterThan((Vec3i) this, b, new Vec3i());
    }

    public Vec3i greaterThan(Vec3i b, Vec3i res) {
        return Glm.greaterThan((Vec3i) this, b, res);
    }

    public Vec3i greaterThanEqual(Vec3i b) {
        return Glm.greaterThanEqual((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i greaterThanEqual_(Vec3i b) {
        return Glm.greaterThanEqual((Vec3i) this, b, new Vec3i());
    }

    public Vec3i greaterThanEqual(Vec3i b, Vec3i res) {
        return Glm.greaterThanEqual((Vec3i) this, b, res);
    }

    public Vec3i equal(Vec3i b) {
        return Glm.equal((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i equal_(Vec3i b) {
        return Glm.equal((Vec3i) this, b, new Vec3i());
    }

    public Vec3i equal(Vec3i b, Vec3i res) {
        return Glm.equal((Vec3i) this, b, res);
    }

    public Vec3i notEqual(Vec3i b) {
        return Glm.notEqual((Vec3i) this, b, (Vec3i) this);
    }

    public Vec3i notEqual_(Vec3i b) {
        return Glm.notEqual((Vec3i) this, b, new Vec3i());
    }

    public Vec3i notEqual(Vec3i b, Vec3i res) {
        return Glm.notEqual((Vec3i) this, b, res);
    }
}
