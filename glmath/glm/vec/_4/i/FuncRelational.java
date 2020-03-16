/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.i;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author fschaefers
 */
abstract class FuncRelational extends funcCommon {

    public boolean any() {
        return Glm.any((Vec4i) this);
    }

    public boolean all() {
        return Glm.all((Vec4i) this);
    }
    
    public Vec4i not_() {
        return Glm.not((Vec4i) this, new Vec4i());
    }

    public Vec4i not() {
        return Glm.not((Vec4i) this, (Vec4i) this);
    }
    
    public Vec4bool lessThan__(Vec4i b) {
        return Glm.lessThan((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4i b, Vec4bool res) {
        return Glm.lessThan((Vec4i) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4i b) {
        return Glm.lessThanEqual((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4i b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4i) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4i b) {
        return Glm.greaterThan((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4i b, Vec4bool res) {
        return Glm.greaterThan((Vec4i) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4i b) {
        return Glm.greaterThanEqual((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4i b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4i) this, b, res);
    }

    public Vec4bool equal__(Vec4i b) {
        return Glm.equal((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4i b, Vec4bool res) {
        return Glm.equal((Vec4i) this, b, res);
    }

    public Vec4bool notEqual__(Vec4i b) {
        return Glm.notEqual((Vec4i) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4i b, Vec4bool res) {
        return Glm.notEqual((Vec4i) this, b, res);
    }

    public Vec4i lessThan(Vec4i b) {
        return Glm.lessThan((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i lessThan_(Vec4i b) {
        return Glm.lessThan((Vec4i) this, b, new Vec4i());
    }

    public Vec4i lessThan(Vec4i b, Vec4i res) {
        return Glm.lessThan((Vec4i) this, b, res);
    }

    public Vec4i lessThanEqual(Vec4i b) {
        return Glm.lessThanEqual((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i lessThanEqual_(Vec4i b) {
        return Glm.lessThanEqual((Vec4i) this, b, new Vec4i());
    }

    public Vec4i lessThanEqual(Vec4i b, Vec4i res) {
        return Glm.lessThanEqual((Vec4i) this, b, res);
    }

    public Vec4i greaterThan(Vec4i b) {
        return Glm.greaterThan((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i greaterThan_(Vec4i b) {
        return Glm.greaterThan((Vec4i) this, b, new Vec4i());
    }

    public Vec4i greaterThan(Vec4i b, Vec4i res) {
        return Glm.greaterThan((Vec4i) this, b, res);
    }

    public Vec4i greaterThanEqual(Vec4i b) {
        return Glm.greaterThanEqual((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i greaterThanEqual_(Vec4i b) {
        return Glm.greaterThanEqual((Vec4i) this, b, new Vec4i());
    }

    public Vec4i greaterThanEqual(Vec4i b, Vec4i res) {
        return Glm.greaterThanEqual((Vec4i) this, b, res);
    }

    public Vec4i equal(Vec4i b) {
        return Glm.equal((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i equal_(Vec4i b) {
        return Glm.equal((Vec4i) this, b, new Vec4i());
    }

    public Vec4i equal(Vec4i b, Vec4i res) {
        return Glm.equal((Vec4i) this, b, res);
    }

    public Vec4i notEqual(Vec4i b) {
        return Glm.notEqual((Vec4i) this, b, (Vec4i) this);
    }

    public Vec4i notEqual_(Vec4i b) {
        return Glm.notEqual((Vec4i) this, b, new Vec4i());
    }

    public Vec4i notEqual(Vec4i b, Vec4i res) {
        return Glm.notEqual((Vec4i) this, b, res);
    }
}
