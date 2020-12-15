/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ui;

import glmath.glm.Glm;
import glmath.glm.vec._3.bool.Vec3bool;

/**
 *
 * @author fschaefers
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec3ui) this);
    }

    public boolean all() {
        return Glm.all((Vec3ui) this);
    }

    public Vec3ui not_() {
        return Glm.not((Vec3ui) this, new Vec3ui());
    }

    public Vec3ui not() {
        return Glm.not((Vec3ui) this, (Vec3ui) this);
    }

    public Vec3bool lessThan__(Vec3ui b) {
        return Glm.lessThan((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool lessThan(Vec3ui b, Vec3bool res) {
        return Glm.lessThan((Vec3ui) this, b, res);
    }

    public Vec3bool lessThanEqual__(Vec3ui b) {
        return Glm.lessThanEqual((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool lessThanEqual(Vec3ui b, Vec3bool res) {
        return Glm.lessThanEqual((Vec3ui) this, b, res);
    }

    public Vec3bool greaterThan__(Vec3ui b) {
        return Glm.greaterThan((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool greaterThan(Vec3ui b, Vec3bool res) {
        return Glm.greaterThan((Vec3ui) this, b, res);
    }

    public Vec3bool greaterThanEqual__(Vec3ui b) {
        return Glm.greaterThanEqual((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool greaterThanEqual(Vec3ui b, Vec3bool res) {
        return Glm.greaterThanEqual((Vec3ui) this, b, res);
    }

    public Vec3bool equal__(Vec3ui b) {
        return Glm.equal((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool equal(Vec3ui b, Vec3bool res) {
        return Glm.equal((Vec3ui) this, b, res);
    }

    public Vec3bool notEqual__(Vec3ui b) {
        return Glm.notEqual((Vec3ui) this, b, new Vec3bool());
    }

    public Vec3bool notEqual(Vec3ui b, Vec3bool res) {
        return Glm.notEqual((Vec3ui) this, b, res);
    }

    public Vec3ui lessThan(Vec3ui b) {
        return Glm.lessThan((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui lessThan_(Vec3ui b) {
        return Glm.lessThan((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui lessThan(Vec3ui b, Vec3ui res) {
        return Glm.lessThan((Vec3ui) this, b, res);
    }

    public Vec3ui lessThanEqual(Vec3ui b) {
        return Glm.lessThanEqual((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui lessThanEqual_(Vec3ui b) {
        return Glm.lessThanEqual((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui lessThanEqual(Vec3ui b, Vec3ui res) {
        return Glm.lessThanEqual((Vec3ui) this, b, res);
    }

    public Vec3ui greaterThan(Vec3ui b) {
        return Glm.greaterThan((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui greaterThan_(Vec3ui b) {
        return Glm.greaterThan((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui greaterThan(Vec3ui b, Vec3ui res) {
        return Glm.greaterThan((Vec3ui) this, b, res);
    }

    public Vec3ui greaterThanEqual(Vec3ui b) {
        return Glm.greaterThanEqual((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui greaterThanEqual_(Vec3ui b) {
        return Glm.greaterThanEqual((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui greaterThanEqual(Vec3ui b, Vec3ui res) {
        return Glm.greaterThanEqual((Vec3ui) this, b, res);
    }

    public Vec3ui equal(Vec3ui b) {
        return Glm.equal((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui equal_(Vec3ui b) {
        return Glm.equal((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui equal(Vec3ui b, Vec3ui res) {
        return Glm.equal((Vec3ui) this, b, res);
    }

    public Vec3ui notEqual(Vec3ui b) {
        return Glm.notEqual((Vec3ui) this, b, (Vec3ui) this);
    }

    public Vec3ui notEqual_(Vec3ui b) {
        return Glm.notEqual((Vec3ui) this, b, new Vec3ui());
    }

    public Vec3ui notEqual(Vec3ui b, Vec3ui res) {
        return Glm.notEqual((Vec3ui) this, b, res);
    }
}
