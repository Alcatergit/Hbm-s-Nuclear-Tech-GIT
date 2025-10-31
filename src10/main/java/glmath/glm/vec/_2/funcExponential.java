/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2;

import glmath.glm.Glm;

/**
 *
 * @author elect
 */
abstract class funcExponential extends funcCommon {

    public Vec2 exp() {
        return exp((Vec2) this, (Vec2) this);
    }

    public Vec2 exp_() {
        return exp((Vec2) this);
    }

    public Vec2 exp(Vec2 result) {
        return exp((Vec2) this, result);
    }

    public static Vec2 exp(Vec2 x, Vec2 result) {
        result.x = (float) Math.exp(x.x);
        result.y = (float) Math.exp(x.y);
        return result;
    }

    public Vec2 exp2() {
        return exp2((Vec2) this, (Vec2) this);
    }

    public Vec2 exp2_() {
        return exp2((Vec2) this);
    }

    public Vec2 exp2(Vec2 result) {
        return exp2((Vec2) this, result);
    }

    public static Vec2 exp2(Vec2 x, Vec2 result) {
        result.x = (float) Math.pow(2, x.x);
        result.y = (float) Math.pow(2, x.y);
        return result;
    }

    public Vec2 inverseSqrt() {
        return inverseSqrt((Vec2) this);
    }

    public Vec2 inverseSqrt_() {
        return inverseSqrt(new Vec2());
    }

    public Vec2 inverseSqrt(Vec2 result) {
        return inverseSqrt((Vec2) this, result);
    }

    public static Vec2 inverseSqrt(Vec2 x, Vec2 result) {
        result.x = Glm.inverseSqrt(x.x);
        result.y = Glm.inverseSqrt(x.y);
        return result;
    }

    public Vec2 log() {
        return log((Vec2) this);
    }

    public Vec2 log_() {
        return log(new Vec2());
    }

    public Vec2 log(Vec2 result) {
        return log((Vec2) this, result);
    }

    public static Vec2 log(Vec2 x, Vec2 result) {
        result.x = (float) Math.log(x.x);
        result.y = (float) Math.log(x.y);
        return result;
    }

    public Vec2 log2() {
        return log2((Vec2) this);
    }

    public Vec2 log2_() {
        return log2(new Vec2());
    }

    public Vec2 log2(Vec2 result) {
        return log2((Vec2) this, result);
    }

    public static Vec2 log2(Vec2 x, Vec2 result) {
        result.x = (float) (Math.log(x.x) / Math.log(2));
        result.y = (float) (Math.log(x.y) / Math.log(2));
        return result;
    }

    public Vec2 pow(Vec2 y) {
        return pow(y, (Vec2) this);
    }

    public Vec2 pow_(Vec2 y) {
        return pow(y, new Vec2());
    }

    public Vec2 pow(Vec2 y, Vec2 result) {
        return pow((Vec2) this, y, result);
    }

    public static Vec2 pow(Vec2 x, Vec2 y, Vec2 result) {
        result.x = (float) Math.pow(x.x, y.x);
        result.y = (float) Math.pow(x.y, y.y);
        return result;
    }

    public Vec2 sqrt() {
        return sqrt((Vec2) this);
    }

    public Vec2 sqrt_() {
        return sqrt(new Vec2());
    }

    public Vec2 sqrt(Vec2 result) {
        return sqrt((Vec2) this, result);
    }

    public static Vec2 sqrt(Vec2 x, Vec2 result) {
        result.x = (float) Math.sqrt(x.x);
        result.y = (float) Math.sqrt(x.y);
        return result;
    }

}
