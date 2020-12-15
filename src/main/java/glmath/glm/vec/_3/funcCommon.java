/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3;

import glmath.glm.Glm;
import glmath.glm.vec._3.b.Vec3b;
import glmath.glm.vec._3.i.Vec3i;

/**
 *
 * @author elect
 */
abstract class funcCommon extends ArithmeticOperators {

    public Vec3 abs() {
        return abs((Vec3) this);
    }

    public Vec3 abs_() {
        return abs(new Vec3());
    }

    public Vec3 abs(Vec3 result) {
        return abs((Vec3) this, result);
    }

    public static Vec3 abs(Vec3 x, Vec3 result) {
        result.x = Math.abs(x.x);
        result.y = Math.abs(x.y);
        result.z = Math.abs(x.z);
        return result;
    }

    public Vec3 ceil() {
        return ceil((Vec3) this);
    }

    public Vec3 ceil_() {
        return ceil(new Vec3());
    }

    public Vec3 ceil(Vec3 result) {
        return ceil((Vec3) this, (Vec3) this);
    }

    public static Vec3 ceil(Vec3 x, Vec3 result) {
        result.x = (float) Math.ceil(x.x);
        result.y = (float) Math.ceil(x.y);
        result.z = (float) Math.ceil(x.z);
        return result;
    }

    public Vec3 clamp(float minVal, float maxVal) {
        return clamp(minVal, maxVal, (Vec3) this);
    }

    public Vec3 clamp_(float minVal, float maxVal) {
        return clamp(minVal, maxVal, new Vec3());
    }

    public Vec3 clamp(float minVal, float maxVal, Vec3 result) {
        return clamp((Vec3) this, minVal, maxVal, result);
    }

    public static Vec3 clamp(Vec3 x, float minVal, float maxVal, Vec3 result) {
        result.x = Math.min(Math.max(x.x, minVal), maxVal);
        result.y = Math.min(Math.max(x.y, minVal), maxVal);
        result.z = Math.min(Math.max(x.z, minVal), maxVal);
        return result;
    }

    public Vec3 clamp(Vec3 minVal, Vec3 maxVal) {
        return clamp(minVal, maxVal, (Vec3) this);
    }

    public Vec3 clamp_(Vec3 minVal, Vec3 maxVal) {
        return clamp(minVal, maxVal, new Vec3());
    }

    public Vec3 clamp(Vec3 minVal, Vec3 maxVal, Vec3 result) {
        return clamp((Vec3) this, minVal, maxVal, result);
    }

    public static Vec3 clamp(Vec3 x, Vec3 minVal, Vec3 maxVal, Vec3 result) {
        result.x = Math.min(Math.max(x.x, minVal.x), maxVal.x);
        result.y = Math.min(Math.max(x.y, minVal.y), maxVal.y);
        result.z = Math.min(Math.max(x.z, minVal.z), maxVal.z);
        return result;
    }

    public Vec3i floatToIntBits_() {
        return floatToIntBits((Vec3) this, new Vec3i());
    }

    public Vec3i floatToIntBits(Vec3i result) {
        return floatToIntBits((Vec3) this, result);
    }

    public static Vec3i floatToIntBits(Vec3 x, Vec3i result) {
        result.x = Float.floatToIntBits(x.x);
        result.y = Float.floatToIntBits(x.y);
        result.z = Float.floatToIntBits(x.z);
        return result;
    }

    public Vec3 floor() {
        return floor((Vec3) this);
    }

    public Vec3 floor_() {
        return floor(new Vec3());
    }

    public Vec3 floor(Vec3 result) {
        return floor((Vec3) this, result);
    }

    public static Vec3 floor(Vec3 x, Vec3 result) {
        result.x = (float) Math.floor(x.x);
        result.y = (float) Math.floor(x.y);
        result.z = (float) Math.floor(x.z);
        return result;
    }

    public Vec3 fma(Vec3 b, Vec3 c) {
        return fma(b, c, (Vec3) this);
    }

    public Vec3 fma_(Vec3 b, Vec3 c) {
        return fma(b, c, new Vec3());
    }

    public Vec3 fma(Vec3 b, Vec3 c, Vec3 result) {
        return fma((Vec3) this, b, c, result);
    }

    public static Vec3 fma(Vec3 a, Vec3 b, Vec3 c, Vec3 result) {
        result.x = a.x * b.x + c.x;
        result.y = a.y * b.y + c.y;
        result.z = a.z * b.z + c.z;
        return result;
    }

    public Vec3 fract() {
        return fract((Vec3) this);
    }

    public Vec3 fract_() {
        return fract(new Vec3());
    }

    public Vec3 fract(Vec3 result) {
        return fract((Vec3) this, result);
    }

    public static Vec3 fract(Vec3 x, Vec3 result) {
        result.x = (float) (x.x - Math.floor(x.x));
        result.y = (float) (x.y - Math.floor(x.y));
        result.z = (float) (x.z - Math.floor(x.z));
        return result;
    }

    public Vec3b isInf_() {
        return isInf((Vec3) this, new Vec3b());
    }

    public Vec3b isInf(Vec3b result) {
        return isInf((Vec3) this, result);
    }

    public static Vec3b isInf(Vec3 x, Vec3b result) {
        result.x = (byte) (Float.isInfinite(x.x) ? 1 : 0);
        result.y = (byte) (Float.isInfinite(x.y) ? 1 : 0);
        result.z = (byte) (Float.isInfinite(x.z) ? 1 : 0);
        return result;
    }

    public Vec3b isNan() {
        return isNan((Vec3) this, new Vec3b());
    }

    public Vec3b isNan(Vec3b result) {
        return isNan((Vec3) this, result);
    }

    public static Vec3b isNan(Vec3 x, Vec3b result) {
        result.x = (byte) (Float.isNaN(x.x) ? 1 : 0);
        result.y = (byte) (Float.isNaN(x.y) ? 1 : 0);
        result.z = (byte) (Float.isNaN(x.z) ? 1 : 0);
        return result;
    }

    public Vec3 max(Vec3 y) {
        return max(y, (Vec3) this);
    }

    public Vec3 max_(Vec3 y) {
        return max((Vec3) this, y, new Vec3());
    }

    public Vec3 max(Vec3 y, Vec3 result) {
        return max((Vec3) this, y, result);
    }

    public static Vec3 max(Vec3 x, Vec3 y, Vec3 result) {
        result.x = Math.max(x.x, y.x);
        result.y = Math.max(x.y, y.y);
        result.z = Math.max(x.z, y.z);
        return result;
    }

    public Vec3 min(Vec3 y) {
        return min(y, (Vec3) this);
    }

    public Vec3 min_(Vec3 y) {
        return min(y, new Vec3());
    }

    public Vec3 min(Vec3 y, Vec3 result) {
        return min((Vec3) this, y, result);
    }

    public static Vec3 min(Vec3 x, Vec3 y, Vec3 result) {
        result.x = Math.min(x.x, y.x);
        result.y = Math.min(x.y, y.y);
        result.z = Math.min(x.z, y.z);
        return result;
    }

    public Vec3 mix(Vec3 y, boolean a) {
        return mix(y, a, (Vec3) this);
    }

    public Vec3 mix_(Vec3 y, boolean a) {
        return mix(y, a, new Vec3());
    }

    public Vec3 mix(Vec3 y, boolean a, Vec3 result) {
        return mix((Vec3) this, y, a, result);
    }

    public static Vec3 mix(Vec3 x, Vec3 y, boolean a, Vec3 result) {
        result.x = a ? y.x : x.x;
        result.y = a ? y.y : x.y;
        result.z = a ? y.z : x.z;
        return result;
    }

    public Vec3 mix(Vec3 y, Vec3b a) {
        return mix(y, a, (Vec3) this);
    }

    public Vec3 mix_(Vec3 y, Vec3b a) {
        return mix(y, a, new Vec3());
    }

    public Vec3 mix(Vec3 y, Vec3b a, Vec3 result) {
        return mix((Vec3) this, y, a, result);
    }

    public static Vec3 mix(Vec3 x, Vec3 y, Vec3b a, Vec3 result) {
        result.x = a.x == 1 ? y.x : x.x;
        result.y = a.y == 1 ? y.y : x.y;
        result.z = a.z == 1 ? y.z : x.z;
        return result;
    }

    public Vec3 mix(Vec3 y, float a) {
        return mix(y, a, (Vec3) this);
    }

    public Vec3 mix_(Vec3 y, float a) {
        return mix(y, a, new Vec3());
    }

    public Vec3 mix(Vec3 y, float a, Vec3 result) {
        return mix((Vec3) this, y, a, result);
    }

    public static Vec3 mix(Vec3 x, Vec3 y, float a, Vec3 result) {
        result.x = x.x + a * (y.x - x.x);
        result.y = x.y + a * (y.y - x.y);
        result.z = x.z + a * (y.z - x.z);
        return result;
    }

    public Vec3 mix(Vec3 y, Vec3 a) {
        return mix(y, a, (Vec3) this);
    }

    public Vec3 mix_(Vec3 y, Vec3 a) {
        return mix(y, a, new Vec3());
    }

    public Vec3 mix(Vec3 y, Vec3 a, Vec3 result) {
        return mix((Vec3) this, y, a, result);
    }

    public static Vec3 mix(Vec3 x, Vec3 y, Vec3 a, Vec3 result) {
        result.x = x.x + a.x * (y.x - x.x);
        result.y = x.y + a.y * (y.y - x.y);
        result.z = x.z + a.z * (y.z - x.z);
        return result;
    }

    public Vec3 mod(Vec3 y) {
        return mod(y, (Vec3) this);
    }

    public Vec3 mod_(Vec3 y) {
        return mod(y, new Vec3());
    }

    public Vec3 mod(Vec3 y, Vec3 result) {
        return mod((Vec3) this, y, result);
    }

    public static Vec3 mod(Vec3 x, Vec3 y, Vec3 result) {
        result.x = (float) (x.x - y.x * Glm.floor(x.x / y.x));
        result.y = (float) (x.y - y.y * Glm.floor(x.y / y.y));
        result.z = (float) (x.z - y.z * Glm.floor(x.z / y.z));
        return result;
    }

    public Vec3i round_() {
        return round(new Vec3i());
    }

    public Vec3i round(Vec3i result) {
        return round((Vec3) this, result);
    }

    public static Vec3i round(Vec3 x, Vec3i result) {
        result.x = Math.round(x.x);
        result.y = Math.round(x.y);
        result.z = Math.round(x.z);
        return result;
    }

    public Vec3 sign() {
        return sign((Vec3) this, (Vec3) this);
    }

    public Vec3 sign_() {
        return sign(new Vec3());
    }

    public Vec3 sign(Vec3 result) {
        return sign((Vec3) this, result);
    }

    public static Vec3 sign(Vec3 x, Vec3 result) {
        result.x = Math.signum(x.x);
        result.y = Math.signum(x.y);
        result.z = Math.signum(x.z);
        return result;
    }

    public Vec3 smoothStep(Vec3 edge0, Vec3 edge1) {
        return smoothStep(edge0, edge1, (Vec3) this);
    }

    public Vec3 smoothStep_(Vec3 edge0, Vec3 edge1) {
        return smoothStep(edge0, edge1, new Vec3());
    }

    public Vec3 smoothStep(Vec3 edge0, Vec3 edge1, Vec3 result) {
        return smoothStep(edge0, edge1, (Vec3) this, result);
    }

    public static Vec3 smoothStep(Vec3 edge0, Vec3 edge1, Vec3 x, Vec3 result) {
        // clamp
        float tmpX = Math.min(Math.max((x.x - edge0.x) / (edge1.x - edge0.x), 0), 1);
        float tmpY = Math.min(Math.max((x.y - edge0.y) / (edge1.y - edge0.y), 0), 1);
        float tmpZ = Math.min(Math.max((x.z - edge0.z) / (edge1.z - edge0.z), 0), 1);
        result.x = tmpX * tmpX * (3 - 2 * tmpX);
        result.y = tmpY * tmpY * (3 - 2 * tmpY);
        result.z = tmpZ * tmpZ * (3 - 2 * tmpZ);
        return result;
    }

    public Vec3 step(Vec3 edge) {
        return step(edge, (Vec3) this);
    }

    public Vec3 step_(Vec3 edge) {
        return step(edge, new Vec3());
    }

    public Vec3 step(Vec3 edge, Vec3 result) {
        return step(edge, (Vec3) this, result);
    }

    public static Vec3 step(Vec3 edge, Vec3 x, Vec3 result) {
        // glm::lessThan(x, edge)
        float ltX = x.x < edge.x ? 1f : 0f;
        float ltY = x.y < edge.y ? 1f : 0f;
        float ltZ = x.z < edge.z ? 1f : 0f;
        // mix(1, 0, glm::lessThan(x, edge));
        result.x = 1 + ltX * (0 - 1);
        result.y = 1 + ltY * (0 - 1);
        result.z = 1 + ltZ * (0 - 1);
        return result;
    }

    public Vec3 toUnsignedFloat() {
        return toUnsignedFloat((Vec3) this);
    }

    public Vec3 toUnsignedFloat_() {
        return toUnsignedFloat(new Vec3());
    }

    public Vec3 toUnsignedFloat(Vec3 result) {
        return toUnsignedFloat((Vec3) this, result);
    }

    public static Vec3 toUnsignedFloat(Vec3 x, Vec3 result) {
        result.x = Float.intBitsToFloat((int) x.x);
        result.y = Float.intBitsToFloat((int) x.y);
        result.z = Float.intBitsToFloat((int) x.z);
        return result;
    }
}
