/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ui;

import glmath.glm.Glm;
import glmath.glm.vec._2.bool.Vec2bool;

/**
 *
 * @author fschaefers
 */
abstract class FuncRelational extends ArithmeticOperators {

    public boolean any() {
        return Glm.any((Vec2ui) this);
    }

    public boolean all() {
        return Glm.all((Vec2ui) this);
    }

    public Vec2ui not_() {
        return Glm.not((Vec2ui) this, new Vec2ui());
    }

    public Vec2ui not() {
        return Glm.not((Vec2ui) this, (Vec2ui) this);
    }

    public Vec2bool lessThan__(Vec2ui b) {
        return Glm.lessThan((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool lessThan(Vec2ui b, Vec2bool res) {
        return Glm.lessThan((Vec2ui) this, b, res);
    }

    public Vec2bool lessThanEqual__(Vec2ui b) {
        return Glm.lessThanEqual((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool lessThanEqual(Vec2ui b, Vec2bool res) {
        return Glm.lessThanEqual((Vec2ui) this, b, res);
    }

    public Vec2bool greaterThan__(Vec2ui b) {
        return Glm.greaterThan((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool greaterThan(Vec2ui b, Vec2bool res) {
        return Glm.greaterThan((Vec2ui) this, b, res);
    }

    public Vec2bool greaterThanEqual__(Vec2ui b) {
        return Glm.greaterThanEqual((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool greaterThanEqual(Vec2ui b, Vec2bool res) {
        return Glm.greaterThanEqual((Vec2ui) this, b, res);
    }

    public Vec2bool equal__(Vec2ui b) {
        return Glm.equal((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool equal(Vec2ui b, Vec2bool res) {
        return Glm.equal((Vec2ui) this, b, res);
    }

    public Vec2bool notEqual__(Vec2ui b) {
        return Glm.notEqual((Vec2ui) this, b, new Vec2bool());
    }

    public Vec2bool notEqual(Vec2ui b, Vec2bool res) {
        return Glm.notEqual((Vec2ui) this, b, res);
    }

    public Vec2ui lessThan(Vec2ui b) {
        return Glm.lessThan((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui lessThan_(Vec2ui b) {
        return Glm.lessThan((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui lessThan(Vec2ui b, Vec2ui res) {
        return Glm.lessThan((Vec2ui) this, b, res);
    }

    public Vec2ui lessThanEqual_(Vec2ui b) {
        return Glm.lessThanEqual((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui lessThanEqual(Vec2ui b) {
        return Glm.lessThanEqual((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui lessThanEqual(Vec2ui b, Vec2ui res) {
        return Glm.lessThanEqual((Vec2ui) this, b, res);
    }

    public Vec2ui greaterThan(Vec2ui b) {
        return Glm.greaterThan((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui greaterThan_(Vec2ui b) {
        return Glm.greaterThan((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui greaterThan(Vec2ui b, Vec2ui res) {
        return Glm.greaterThan((Vec2ui) this, b, res);
    }

    public Vec2ui greaterThanEqual(Vec2ui b) {
        return Glm.greaterThanEqual((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui greaterThanEqual_(Vec2ui b) {
        return Glm.greaterThanEqual((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui greaterThanEqual(Vec2ui b, Vec2ui res) {
        return Glm.greaterThanEqual((Vec2ui) this, b, res);
    }

    public Vec2ui equal(Vec2ui b) {
        return Glm.equal((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui equal_(Vec2ui b) {
        return Glm.equal((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui equal(Vec2ui b, Vec2ui res) {
        return Glm.equal((Vec2ui) this, b, res);
    }

    public Vec2ui notEqual(Vec2ui b) {
        return Glm.notEqual((Vec2ui) this, b, (Vec2ui) this);
    }

    public Vec2ui notEqual_(Vec2ui b) {
        return Glm.notEqual((Vec2ui) this, b, new Vec2ui());
    }

    public Vec2ui notEqual(Vec2ui b, Vec2ui res) {
        return Glm.notEqual((Vec2ui) this, b, res);
    }
}
