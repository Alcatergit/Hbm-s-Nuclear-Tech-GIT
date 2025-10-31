/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.i;

import glmath.glm.Glm;
import glmath.glm.vec._4.b.Vec4b;

/**
 *
 * @author fschaefers
 */
abstract class funcCommon extends ArithmeticOperators {

    public Vec4i abs() {
        return abs((Vec4i) this);
    }

    public Vec4i abs_() {
        return abs(new Vec4i());
    }

    public Vec4i abs(Vec4i result) {
        return abs((Vec4i) this, result);
    }

    public static Vec4i abs(Vec4i x, Vec4i result) {
        result.x = Math.abs(x.x);
        result.y = Math.abs(x.y);
        result.z = Math.abs(x.z);
        result.w = Math.abs(x.w);
        return result;
    }

    public Vec4i ceil() {
        return ceil((Vec4i) this);
    }

    public Vec4i ceil_() {
        return ceil(new Vec4i());
    }

    public Vec4i ceil(Vec4i result) {
        return ceil((Vec4i) this, (Vec4i) this);
    }

    public static Vec4i ceil(Vec4i x, Vec4i result) {
        result.x = (int) Math.ceil(x.x);
        result.y = (int) Math.ceil(x.y);
        result.z = (int) Math.ceil(x.z);
        result.w = (int) Math.ceil(x.w);
        return result;
    }

    public Vec4i clamp(int minVal, int maxVal) {
        return clamp(minVal, maxVal, (Vec4i) this);
    }

    public Vec4i clamp_(int minVal, int maxVal) {
        return clamp(minVal, maxVal, new Vec4i());
    }

    public Vec4i clamp(int minVal, int maxVal, Vec4i result) {
        return clamp((Vec4i) this, minVal, maxVal, result);
    }

    public static Vec4i clamp(Vec4i x, int minVal, int maxVal, Vec4i result) {
        result.x = Math.min(Math.max(x.x, minVal), maxVal);
        result.y = Math.min(Math.max(x.y, minVal), maxVal);
        result.z = Math.min(Math.max(x.z, minVal), maxVal);
        result.w = Math.min(Math.max(x.w, minVal), maxVal);
        return result;
    }

    public Vec4i clamp(Vec4i minVal, Vec4i maxVal) {
        return clamp(minVal, maxVal, (Vec4i) this);
    }

    public Vec4i clamp_(Vec4i minVal, Vec4i maxVal) {
        return clamp(minVal, maxVal, new Vec4i());
    }

    public Vec4i clamp(Vec4i minVal, Vec4i maxVal, Vec4i result) {
        return clamp((Vec4i) this, minVal, maxVal, result);
    }

    public static Vec4i clamp(Vec4i x, Vec4i minVal, Vec4i maxVal, Vec4i result) {
        result.x = Math.min(Math.max(x.x, minVal.x), maxVal.x);
        result.y = Math.min(Math.max(x.y, minVal.y), maxVal.y);
        result.z = Math.min(Math.max(x.z, minVal.z), maxVal.z);
        result.w = Math.min(Math.max(x.w, minVal.w), maxVal.w);
        return result;
    }

    public Vec4i floor() {
        return floor((Vec4i) this);
    }

    public Vec4i floor_() {
        return floor(new Vec4i());
    }

    public Vec4i floor(Vec4i result) {
        return floor((Vec4i) this, result);
    }

    public static Vec4i floor(Vec4i x, Vec4i result) {
        result.x = (int) Math.floor(x.x);
        result.y = (int) Math.floor(x.y);
        result.z = (int) Math.floor(x.z);
        result.w = (int) Math.floor(x.w);
        return result;
    }

    public Vec4i fma(Vec4i b, Vec4i c) {
        return fma(b, c, (Vec4i) this);
    }

    public Vec4i fma_(Vec4i b, Vec4i c) {
        return fma(b, c, new Vec4i());
    }

    public Vec4i fma(Vec4i b, Vec4i c, Vec4i result) {
        return fma((Vec4i) this, b, c, result);
    }

    public static Vec4i fma(Vec4i a, Vec4i b, Vec4i c, Vec4i result) {
        result.x = a.x * b.x + c.x;
        result.y = a.y * b.y + c.y;
        result.z = a.z * b.z + c.z;
        result.w = a.w * b.w + c.w;
        return result;
    }

    public Vec4i max(Vec4i y) {
        return max(y, (Vec4i) this);
    }

    public Vec4i max_(Vec4i y) {
        return max((Vec4i) this, y, new Vec4i());
    }

    public Vec4i max(Vec4i y, Vec4i result) {
        return max((Vec4i) this, y, result);
    }

    public static Vec4i max(Vec4i x, Vec4i y, Vec4i result) {
        result.x = Math.max(x.x, y.x);
        result.y = Math.max(x.y, y.y);
        result.z = Math.max(x.z, y.z);
        result.w = Math.max(x.z, y.z);
        return result;
    }

    public Vec4i min(Vec4i y) {
        return min(y, (Vec4i) this);
    }

    public Vec4i min_(Vec4i y) {
        return min(y, new Vec4i());
    }

    public Vec4i min(Vec4i y, Vec4i result) {
        return min((Vec4i) this, y, result);
    }

    public static Vec4i min(Vec4i x, Vec4i y, Vec4i result) {
        result.x = Math.min(x.x, y.x);
        result.y = Math.min(x.y, y.y);
        result.z = Math.min(x.z, y.z);
        result.w = Math.min(x.w, y.w);
        return result;
    }

    public Vec4i mix(Vec4i y, boolean a) {
        return mix(y, a, (Vec4i) this);
    }

    public Vec4i mix_(Vec4i y, boolean a) {
        return mix(y, a, new Vec4i());
    }

    public Vec4i mix(Vec4i y, boolean a, Vec4i result) {
        return mix((Vec4i) this, y, a, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, boolean a, Vec4i result) {
        return mix(x, y, a, a, a, a, result);
    }

    public Vec4i mix(Vec4i y, Vec4b a) {
        return mix(y, a, (Vec4i) this);
    }

    public Vec4i mix_(Vec4i y, Vec4b a) {
        return mix(y, a, new Vec4i());
    }

    public Vec4i mix(Vec4i y, Vec4b a, Vec4i result) {
        return mix((Vec4i) this, y, a, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, Vec4b a, Vec4i result) {
        return mix(x, y, a.x == 1, a.y == 1, a.z == 1, a.w == 1, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, boolean a0, boolean a1, boolean a2, boolean a3, Vec4i result) {
        result.x = a0 ? y.x : x.x;
        result.y = a1 ? y.y : x.y;
        result.z = a2 ? y.z : x.z;
        result.w = a3 ? y.w : x.w;
        return result;
    }

    public Vec4i mix(Vec4i y, int a) {
        return mix(y, a, (Vec4i) this);
    }

    public Vec4i mix_(Vec4i y, int a) {
        return mix(y, a, new Vec4i());
    }

    public Vec4i mix(Vec4i y, int a, Vec4i result) {
        return mix((Vec4i) this, y, a, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, int a, Vec4i result) {
        return mix(x, y, a, a, a, a, result);
    }

    public Vec4i mix(Vec4i y, Vec4i a) {
        return mix(y, a, (Vec4i) this);
    }

    public Vec4i mix_(Vec4i y, Vec4i a) {
        return mix(y, a, new Vec4i());
    }

    public Vec4i mix(Vec4i y, Vec4i a, Vec4i result) {
        return mix((Vec4i) this, y, a, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, Vec4i a, Vec4i result) {
        return mix(x, y, a.x, a.y, a.z, a.w, result);
    }

    public static Vec4i mix(Vec4i x, Vec4i y, int a0, int a1, int a2, int a3, Vec4i result) {
        result.x = x.x + a0 * (y.x - x.x);
        result.y = x.y + a1 * (y.y - x.y);
        result.z = x.z + a2 * (y.z - x.z);
        result.w = x.w + a3 * (y.w - x.w);
        return result;
    }

    public Vec4i mod(Vec4i y) {
        return mod(y, (Vec4i) this);
    }

    public Vec4i mod_(Vec4i y) {
        return mod(y, new Vec4i());
    }

    public Vec4i mod(Vec4i y, Vec4i result) {
        return mod((Vec4i) this, y, result);
    }

    public static Vec4i mod(Vec4i x, Vec4i y, Vec4i result) {
        result.x = (int) (x.x - y.x * Glm.floor(x.x / y.x));
        result.y = (int) (x.y - y.y * Glm.floor(x.y / y.y));
        result.z = (int) (x.z - y.z * Glm.floor(x.z / y.z));
        result.w = (int) (x.w - y.w * Glm.floor(x.w / y.w));
        return result;
    }

    public Vec4i sign() {
        return sign((Vec4i) this, (Vec4i) this);
    }

    public Vec4i sign_() {
        return sign(new Vec4i());
    }

    public Vec4i sign(Vec4i result) {
        return sign((Vec4i) this, result);
    }

    public static Vec4i sign(Vec4i x, Vec4i result) {
        result.x = (int) Math.signum(x.x);
        result.y = (int) Math.signum(x.y);
        result.z = (int) Math.signum(x.z);
        result.w = (int) Math.signum(x.w);
        return result;
    }

    public Vec4i smoothStep(Vec4i edge0, Vec4i edge1) {
        return smoothStep(edge0, edge1, (Vec4i) this);
    }

    public Vec4i smoothStep_(Vec4i edge0, Vec4i edge1) {
        return smoothStep(edge0, edge1, new Vec4i());
    }

    public Vec4i smoothStep(Vec4i edge0, Vec4i edge1, Vec4i result) {
        return smoothStep(edge0, edge1, (Vec4i) this, result);
    }

    public static Vec4i smoothStep(Vec4i edge0, Vec4i edge1, Vec4i x, Vec4i result) {
        // clamp
        int tmpX = Math.min(Math.max((x.x - edge0.x) / (edge1.x - edge0.x), 0), 1);
        int tmpY = Math.min(Math.max((x.y - edge0.y) / (edge1.y - edge0.y), 0), 1);
        int tmpZ = Math.min(Math.max((x.z - edge0.z) / (edge1.z - edge0.z), 0), 1);
        int tmpW = Math.min(Math.max((x.w - edge0.w) / (edge1.w - edge0.w), 0), 1);
        result.x = tmpX * tmpX * (3 - 2 * tmpX);
        result.y = tmpY * tmpY * (3 - 2 * tmpY);
        result.z = tmpZ * tmpZ * (3 - 2 * tmpZ);
        result.w = tmpW * tmpW * (3 - 2 * tmpW);
        return result;
    }

    public Vec4i step(Vec4i edge) {
        return step(edge, (Vec4i) this);
    }

    public Vec4i step_(Vec4i edge) {
        return step(edge, new Vec4i());
    }

    public Vec4i step(Vec4i edge, Vec4i result) {
        return step(edge, (Vec4i) this, result);
    }

    public static Vec4i step(Vec4i edge, Vec4i x, Vec4i result) {
        result.x = x.x < edge.x ? 0 : 1;
        result.y = x.y < edge.y ? 0 : 1;
        result.z = x.z < edge.z ? 0 : 1;
        result.w = x.w < edge.w ? 0 : 1;
        return result;
    }
}
