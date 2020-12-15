/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author elect
 */
abstract class FuncRelational extends funcGeometric {

    public boolean any() {
        return Glm.any((Vec2) this);
    }

    public boolean all() {
        return Glm.all((Vec2) this);
    }
    
    public Vec2 not_() {
        return Glm.not((Vec2) this, new Vec2());
    }

    public Vec2 not() {
        return Glm.not((Vec2) this, (Vec2) this);
    }

    public Vec2bool lessThan__(Vec2 b) {
        return Glm.lessThan((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2 b, Vec2bool res) {
        return Glm.lessThan((Vec2) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2 b) {
        return Glm.lessThanEqual((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2 b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2 b) {
        return Glm.greaterThan((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2 b, Vec2bool res) {
        return Glm.greaterThan((Vec2) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2 b) {
        return Glm.greaterThanEqual((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2 b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2) this, b, res);
    }

    public Vec2bool equal__(Vec2 b) {
        return Glm.equal((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2 b, Vec2bool res) {
        return Glm.equal((Vec2) this, b, res);
    }

    public Vec2bool notEqual__(Vec2 b) {
        return Glm.notEqual((Vec2) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2 b, Vec2bool res) {
        return Glm.notEqual((Vec2) this, b, res);
    }

    public Vec2 lessThan(Vec2 b) {
        return Glm.lessThan((Vec2) this, b, (Vec2) this);
    }

    public Vec2 lessThan_(Vec2 b) {
        return Glm.lessThan((Vec2) this, b, new Vec2());
    }

    public Vec2 lessThan(Vec2 b, Vec2 res) {
        return Glm.lessThan((Vec2) this, b, res);
    }

    public Vec2 lessThanEqual(Vec2 b) {
        return Glm.lessThanEqual((Vec2) this, b, (Vec2) this);
    }

    public Vec2 lessThanEqual_(Vec2 b) {
        return Glm.lessThanEqual((Vec2) this, b, new Vec2());
    }

    public Vec2 lessThanEqual(Vec2 b, Vec2 res) {
        return Glm.lessThanEqual((Vec2) this, b, res);
    }

    public Vec2 greaterThan(Vec2 b) {
        return Glm.greaterThan((Vec2) this, b, (Vec2) this);
    }

    public Vec2 greaterThan_(Vec2 b) {
        return Glm.greaterThan((Vec2) this, b, new Vec2());
    }

    public Vec2 greaterThan(Vec2 b, Vec2 res) {
        return Glm.greaterThan((Vec2) this, b, res);
    }

    public Vec2 greaterThanEqual(Vec2 b) {
        return Glm.greaterThanEqual((Vec2) this, b, (Vec2) this);
    }

    public Vec2 greaterThanEqual_(Vec2 b) {
        return Glm.greaterThanEqual((Vec2) this, b, new Vec2());
    }

    public Vec2 greaterThanEqual(Vec2 b, Vec2 res) {
        return Glm.greaterThanEqual((Vec2) this, b, res);
    }

    public Vec2 equal(Vec2 b) {
        return Glm.equal((Vec2) this, b, (Vec2) this);
    }

    public Vec2 equal_(Vec2 b) {
        return Glm.equal((Vec2) this, b, new Vec2());
    }

    public Vec2 equal(Vec2 b, Vec2 res) {
        return Glm.equal((Vec2) this, b, res);
    }

    public Vec2 notEqual(Vec2 b) {
        return Glm.notEqual((Vec2) this, b, (Vec2) this);
    }

    public Vec2 notEqual_(Vec2 b) {
        return Glm.notEqual((Vec2) this, b, new Vec2());
    }

    public Vec2 notEqual(Vec2 b, Vec2 res) {
        return Glm.notEqual((Vec2) this, b, res);
    }
}
