/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.i;

import glmath.glm.Glm;
import glmath.glm.vec._3.b.Vec3b;

/**
 *
 * @author fschaefers
 */
abstract class funcCommon extends ArithmeticOperators {

    public Vec3i abs() {
        return abs((Vec3i) this);
    }

    public Vec3i abs_() {
        return abs(new Vec3i());
    }

    public Vec3i abs(Vec3i result) {
        return abs((Vec3i) this, result);
    }

    public static Vec3i abs(Vec3i x, Vec3i result) {
        result.x = Math.abs(x.x);
        result.y = Math.abs(x.y);
        result.z = Math.abs(x.z);
        return result;
    }

    public Vec3i ceil() {
        return ceil((Vec3i) this);
    }

    public Vec3i ceil_() {
        return ceil(new Vec3i());
    }

    public Vec3i ceil(Vec3i result) {
        return ceil((Vec3i) this, (Vec3i) this);
    }

    public static Vec3i ceil(Vec3i x, Vec3i result) {
        result.x = (int) Math.ceil(x.x);
        result.y = (int) Math.ceil(x.y);
        result.z = (int) Math.ceil(x.z);
        return result;
    }

    public Vec3i clamp(int minVal, int maxVal) {
        return clamp(minVal, maxVal, (Vec3i) this);
    }

    public Vec3i clamp_(int minVal, int maxVal) {
        return clamp(minVal, maxVal, new Vec3i());
    }

    public Vec3i clamp(int minVal, int maxVal, Vec3i result) {
        return clamp((Vec3i) this, minVal, maxVal, result);
    }

    public static Vec3i clamp(Vec3i x, int minVal, int maxVal, Vec3i result) {
        result.x = Math.min(Math.max(x.x, minVal), maxVal);
        result.y = Math.min(Math.max(x.y, minVal), maxVal);
        result.z = Math.min(Math.max(x.z, minVal), maxVal);
        return result;
    }

    public Vec3i clamp(Vec3i minVal, Vec3i maxVal) {
        return clamp(minVal, maxVal, (Vec3i) this);
    }

    public Vec3i clamp_(Vec3i minVal, Vec3i maxVal) {
        return clamp(minVal, maxVal, new Vec3i());
    }

    public Vec3i clamp(Vec3i minVal, Vec3i maxVal, Vec3i result) {
        return clamp((Vec3i) this, minVal, maxVal, result);
    }

    public static Vec3i clamp(Vec3i x, Vec3i minVal, Vec3i maxVal, Vec3i result) {
        result.x = Math.min(Math.max(x.x, minVal.x), maxVal.x);
        result.y = Math.min(Math.max(x.y, minVal.y), maxVal.y);
        result.z = Math.min(Math.max(x.z, minVal.z), maxVal.z);
        return result;
    }

    public Vec3i floor() {
        return floor((Vec3i) this);
    }

    public Vec3i floor_() {
        return floor(new Vec3i());
    }

    public Vec3i floor(Vec3i result) {
        return floor((Vec3i) this, result);
    }

    public static Vec3i floor(Vec3i x, Vec3i result) {
        result.x = (int) Math.floor(x.x);
        result.y = (int) Math.floor(x.y);
        result.z = (int) Math.floor(x.z);
        return result;
    }

    public Vec3i fma(Vec3i b, Vec3i c) {
        return fma(b, c, (Vec3i) this);
    }

    public Vec3i fma_(Vec3i b, Vec3i c) {
        return fma(b, c, new Vec3i());
    }

    public Vec3i fma(Vec3i b, Vec3i c, Vec3i result) {
        return fma((Vec3i) this, b, c, result);
    }

    public static Vec3i fma(Vec3i a, Vec3i b, Vec3i c, Vec3i result) {
        result.x = a.x * b.x + c.x;
        result.y = a.y * b.y + c.y;
        result.z = a.z * b.z + c.z;
        return result;
    }

    public Vec3i max(int i) {
        return max(x, y, z, i, i, i, (Vec3i) this);
    }

    public Vec3i max(Vec3i b) {
        return max(b, (Vec3i) this);
    }

    public Vec3i max_(Vec3i b) {
        return max((Vec3i) this, b, new Vec3i());
    }

    public Vec3i max(Vec3i b, Vec3i result) {
        return max((Vec3i) this, b, result);
    }

    public static Vec3i max(Vec3i a, Vec3i b, Vec3i result) {
        return max(a.x, a.y, a.z, b.x, b.y, b.z, result);
    }

    public static Vec3i max(int aX, int aY, int aZ, int bX, int bY, int bZ, Vec3i result) {
        result.x = Math.max(aX, bX);
        result.y = Math.max(aY, bY);
        result.z = Math.max(aZ, bZ);
        return result;
    }

    public Vec3i min(Vec3i b) {
        return min(b, (Vec3i) this);
    }

    public Vec3i min_(Vec3i b) {
        return min(b, new Vec3i());
    }

    public Vec3i min(Vec3i b, Vec3i result) {
        return min((Vec3i) this, b, result);
    }

    public static Vec3i min(Vec3i a, Vec3i b, Vec3i result) {
        result.x = Math.min(a.x, b.x);
        result.y = Math.min(a.y, b.y);
        result.z = Math.min(a.z, b.z);
        return result;
    }

    public Vec3i mix(Vec3i y, boolean a) {
        return mix(y, a, (Vec3i) this);
    }

    public Vec3i mix_(Vec3i y, boolean a) {
        return mix(y, a, new Vec3i());
    }

    public Vec3i mix(Vec3i y, boolean a, Vec3i result) {
        return mix((Vec3i) this, y, a, result);
    }

    public static Vec3i mix(Vec3i x, Vec3i y, boolean a, Vec3i result) {
        result.x = a ? y.x : x.x;
        result.y = a ? y.y : x.y;
        result.z = a ? y.z : x.z;
        return result;
    }

    public Vec3i mix(Vec3i y, Vec3b a) {
        return mix(y, a, (Vec3i) this);
    }

    public Vec3i mix_(Vec3i y, Vec3b a) {
        return mix(y, a, new Vec3i());
    }

    public Vec3i mix(Vec3i y, Vec3b a, Vec3i result) {
        return mix((Vec3i) this, y, a, result);
    }

    public static Vec3i mix(Vec3i x, Vec3i y, Vec3b a, Vec3i result) {
        result.x = a.x == 1 ? y.x : x.x;
        result.y = a.y == 1 ? y.y : x.y;
        result.z = a.z == 1 ? y.z : x.z;
        return result;
    }

    public Vec3i mix(Vec3i y, int a) {
        return mix(y, a, (Vec3i) this);
    }

    public Vec3i mix_(Vec3i y, int a) {
        return mix(y, a, new Vec3i());
    }

    public Vec3i mix(Vec3i y, int a, Vec3i result) {
        return mix((Vec3i) this, y, a, result);
    }

    public static Vec3i mix(Vec3i x, Vec3i y, int a, Vec3i result) {
        result.x = x.x + a * (y.x - x.x);
        result.y = x.y + a * (y.y - x.y);
        result.z = x.z + a * (y.z - x.z);
        return result;
    }

    public Vec3i mix(Vec3i y, Vec3i a) {
        return mix(y, a, (Vec3i) this);
    }

    public Vec3i mix_(Vec3i y, Vec3i a) {
        return mix(y, a, new Vec3i());
    }

    public Vec3i mix(Vec3i y, Vec3i a, Vec3i result) {
        return mix((Vec3i) this, y, a, result);
    }

    public static Vec3i mix(Vec3i x, Vec3i y, Vec3i a, Vec3i result) {
        result.x = x.x + a.x * (y.x - x.x);
        result.y = x.y + a.y * (y.y - x.y);
        result.z = x.z + a.z * (y.z - x.z);
        return result;
    }

    public Vec3i mod(Vec3i y) {
        return mod(y, (Vec3i) this);
    }

    public Vec3i mod_(Vec3i y) {
        return mod(y, new Vec3i());
    }

    public Vec3i mod(Vec3i y, Vec3i result) {
        return mod((Vec3i) this, y, result);
    }

    public static Vec3i mod(Vec3i x, Vec3i y, Vec3i result) {
        result.x = (int) (x.x - y.x * Glm.floor(x.x / y.x));
        result.y = (int) (x.y - y.y * Glm.floor(x.y / y.y));
        result.z = (int) (x.z - y.z * Glm.floor(x.z / y.z));
        return result;
    }

    public Vec3i sign() {
        return sign((Vec3i) this, (Vec3i) this);
    }

    public Vec3i sign_() {
        return sign(new Vec3i());
    }

    public Vec3i sign(Vec3i result) {
        return sign((Vec3i) this, result);
    }

    public static Vec3i sign(Vec3i x, Vec3i result) {
        result.x = (int) Math.signum(x.x);
        result.y = (int) Math.signum(x.y);
        result.z = (int) Math.signum(x.z);
        return result;
    }

    public Vec3i smoothStep(Vec3i edge0, Vec3i edge1) {
        return smoothStep(edge0, edge1, (Vec3i) this);
    }

    public Vec3i smoothStep_(Vec3i edge0, Vec3i edge1) {
        return smoothStep(edge0, edge1, new Vec3i());
    }

    public Vec3i smoothStep(Vec3i edge0, Vec3i edge1, Vec3i result) {
        return smoothStep(edge0, edge1, (Vec3i) this, result);
    }

    public static Vec3i smoothStep(Vec3i edge0, Vec3i edge1, Vec3i x, Vec3i result) {
        // clamp
        int tmpX = Math.min(Math.max((x.x - edge0.x) / (edge1.x - edge0.x), 0), 1);
        int tmpY = Math.min(Math.max((x.y - edge0.y) / (edge1.y - edge0.y), 0), 1);
        int tmpZ = Math.min(Math.max((x.z - edge0.z) / (edge1.z - edge0.z), 0), 1);
        result.x = tmpX * tmpX * (3 - 2 * tmpX);
        result.y = tmpY * tmpY * (3 - 2 * tmpY);
        result.z = tmpZ * tmpZ * (3 - 2 * tmpZ);
        return result;
    }

    public Vec3i step(Vec3i edge) {
        return step(edge, (Vec3i) this);
    }

    public Vec3i step_(Vec3i edge) {
        return step(edge, new Vec3i());
    }

    public Vec3i step(Vec3i edge, Vec3i result) {
        return step(edge, (Vec3i) this, result);
    }

    public static Vec3i step(Vec3i edge, Vec3i x, Vec3i result) {
        // glm::lessThan(x, edge)
        int ltX = x.x < edge.x ? 1 : 0;
        int ltY = x.y < edge.y ? 1 : 0;
        int ltZ = x.z < edge.z ? 1 : 0;
        // mix(1, 0, glm::lessThan(x, edge));
        result.x = 1 + ltX * (0 - 1);
        result.y = 1 + ltY * (0 - 1);
        result.z = 1 + ltZ * (0 - 1);
        return result;
    }

}
