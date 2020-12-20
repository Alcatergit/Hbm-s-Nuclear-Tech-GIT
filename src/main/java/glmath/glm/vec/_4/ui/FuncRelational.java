/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ui;

import glmath.glm.Glm;
import glmath.glm.vec._4.bool.Vec4bool;

/**
 *
 * @author fschaefers
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec4ui) this);
    }

    public boolean all() {
        return Glm.all((Vec4ui) this);
    }

    public Vec4ui not_() {
        return Glm.not((Vec4ui) this, new Vec4ui());
    }

    public Vec4ui not() {
        return Glm.not((Vec4ui) this, (Vec4ui) this);
    }

    public Vec4bool lessThan__(Vec4ui b) {
        return Glm.lessThan((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool lessThan(Vec4ui b, Vec4bool res) {
        return Glm.lessThan((Vec4ui) this, b, res);
    }

    public Vec4bool lessThanEqual__(Vec4ui b) {
        return Glm.lessThanEqual((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool lessThanEqual(Vec4ui b, Vec4bool res) {
        return Glm.lessThanEqual((Vec4ui) this, b, res);
    }

    public Vec4bool greaterThan__(Vec4ui b) {
        return Glm.greaterThan((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool greaterThan(Vec4ui b, Vec4bool res) {
        return Glm.greaterThan((Vec4ui) this, b, res);
    }

    public Vec4bool greaterThanEqual__(Vec4ui b) {
        return Glm.greaterThanEqual((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool greaterThanEqual(Vec4ui b, Vec4bool res) {
        return Glm.greaterThanEqual((Vec4ui) this, b, res);
    }

    public Vec4bool equal__(Vec4ui b) {
        return Glm.equal((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool equal(Vec4ui b, Vec4bool res) {
        return Glm.equal((Vec4ui) this, b, res);
    }

    public Vec4bool notEqual__(Vec4ui b) {
        return Glm.notEqual((Vec4ui) this, b, new Vec4bool());
    }

    public Vec4bool notEqual(Vec4ui b, Vec4bool res) {
        return Glm.notEqual((Vec4ui) this, b, res);
    }

    public Vec4ui lessThan(Vec4ui b) {
        return Glm.lessThan((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui lessThan_(Vec4ui b) {
        return Glm.lessThan((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui lessThan(Vec4ui b, Vec4ui res) {
        return Glm.lessThan((Vec4ui) this, b, res);
    }

    public Vec4ui lessThanEqual(Vec4ui b) {
        return Glm.lessThanEqual((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui lessThanEqual_(Vec4ui b) {
        return Glm.lessThanEqual((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui lessThanEqual(Vec4ui b, Vec4ui res) {
        return Glm.lessThanEqual((Vec4ui) this, b, res);
    }

    public Vec4ui greaterThan(Vec4ui b) {
        return Glm.greaterThan((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui greaterThan_(Vec4ui b) {
        return Glm.greaterThan((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui greaterThan(Vec4ui b, Vec4ui res) {
        return Glm.greaterThan((Vec4ui) this, b, res);
    }

    public Vec4ui greaterThanEqual(Vec4ui b) {
        return Glm.greaterThanEqual((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui greaterThanEqual_(Vec4ui b) {
        return Glm.greaterThanEqual((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui greaterThanEqual(Vec4ui b, Vec4ui res) {
        return Glm.greaterThanEqual((Vec4ui) this, b, res);
    }

    public Vec4ui equal(Vec4ui b) {
        return Glm.equal((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui equal_(Vec4ui b) {
        return Glm.equal((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui equal(Vec4ui b, Vec4ui res) {
        return Glm.equal((Vec4ui) this, b, res);
    }

    public Vec4ui notEqual(Vec4ui b) {
        return Glm.notEqual((Vec4ui) this, b, (Vec4ui) this);
    }

    public Vec4ui notEqual_(Vec4ui b) {
        return Glm.notEqual((Vec4ui) this, b, new Vec4ui());
    }

    public Vec4ui notEqual(Vec4ui b, Vec4ui res) {
        return Glm.notEqual((Vec4ui) this, b, res);
    }
}
