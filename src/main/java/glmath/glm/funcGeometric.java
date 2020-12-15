/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.quat.Quat;
import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
abstract class funcGeometric extends funcExponential {

    public static Quat angleAxis_(float degAngle, Vec3 v) {
        return Quat.angleAxis_(degAngle, v);
    }
    
    public static Quat angleAxis(float degAngle, Vec3 v, Quat res) {
        return Quat.angleAxis(degAngle, v, res);
    }
    
    public static float length(float x) {
        return (float)Math.abs(x);
    }
    
    public static float dot(Vec4 v0, Vec4 v1) {
        return Vec4.dot(v0, v1);
    }
    
    public static float dot(Quat v0, Quat v1) {
        return Quat.dot(v0, v1);
    }
    
    public static Vec3 cross(Vec3 v0, Vec3 v1) {
        return v0.cross(v1);
    }
    
    public static Vec3 cross_(Vec3 v0, Vec3 v1) {
        return v0.cross_(v1);
    }
}
