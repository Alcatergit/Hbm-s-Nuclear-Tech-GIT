/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.jglm;

/**
 * @deprecated 
 * @author gbarbieri
 */
public class Vec3 extends Vec {

    public float x;
    public float y;
    public float z;

    public Vec3() {
    }

    public Vec3(float[] floatArray) {
        vector = floatArray;
        x = vector[0];
        y = vector[1];
        z = vector[2];
    }

    public Vec3(float x, float y, float z) {

        this.x = x;
        this.y = y;
        this.z = z;
        vector = new float[]{x, y, z};
    }
    
    public Vec3(float f) {

        x = f;
        y = f;
        z = f;
        vector = new float[]{f, f, f};
    }
    
    public Vec3(Vec3 v) {

        x = v.x;
        y = v.y;
        z = v.z;
        vector = new float[]{x, y, z};
    }

    public Vec3(float[] floatArray, int i) {

        vector = new float[]{floatArray[i], floatArray[i + 1], floatArray[i + 2]};
        x = vector[0];
        y = vector[1];
        z = vector[2];
    }

    public Vec3(Vec4 vec4) {

        x = vec4.x;
        y = vec4.y;
        z = vec4.z;
        vector = new float[]{x, y, z};
    }

    public static Vec3 getMiddlePoint(Vec3 p1, Vec3 p2) {

        return new Vec3((p1.x + p2.x) / 2, (p1.y + p2.y) / 2, (p1.z + p2.z) / 2);
    }

    public Vec3 normalize() {

        float length = ((float) Math.sqrt(x * x + y * y + z * z));
        
        return length != 0 ? new Vec3(x / length, y / length, z / length) : new Vec3();
    }

    public float dot(Vec3 vec3) {

        return (x * vec3.x + y * vec3.y + z * vec3.z);
    }

    public Vec3 times(float scalar) {

        return new Vec3(x * scalar, y * scalar, z * scalar);
    }

    public static Vec3 mix(Vec3 start, Vec3 end, float lerp) {
        
        Vec3 result = end.minus(start);
        
        result = result.times(lerp);
        
        result = start.plus(result);
        
//        return (start + lerp * (end - start));
        return result;
    }
    
    /**
     * Not traditional way.
     * v' = q * v * conjugate(q)
     *
     * This is supposed to be around 35% faster.
     * http://molecularmusings.wordpress.com/2013/05/24/a-faster-quaternion-vector-multiplication/
     * t = 2 * cross(q.xyz, v)
     * v' = v + q.w * t + cross(q.xyz, t)
     *
     * @param quat
     * @return
     */
    public Vec3 times(Quat quat) {

//        Quat inverse = quat.conjugate();
//
//        return inverse.mult(this);
        Vec3 q = new Vec3(quat.x, quat.y, quat.z);

        Vec3 t = (q).crossProduct(this);

        t = t.times(2);

        t = t.times(quat.w).plus(q.crossProduct(t));

        Vec3 v = t.plus(this);

        return v;

//        return quat.m
    }

    public Vec3 transformQuat(Quat quat) {

        Vec3 result = new Vec3();

        Quat i = new Quat();

        // calculate quat * vec
        i.x = quat.w * x + quat.y * z - quat.z * y;
        i.y = quat.w * y + quat.z * x - quat.x * z;
        i.z = quat.w * z + quat.x * y - quat.y * x;
        i.w = -quat.x * x - quat.y * y - quat.z * z;

        // calculate result * inverse quat
        result.x = i.x * quat.w + i.w * -quat.x + i.y * -quat.z - i.z * -quat.y;
        result.y = i.y * quat.w + i.w * -quat.y + i.z * -quat.x - i.x * -quat.z;
        result.z = i.z * quat.w + i.w * -quat.z + i.x * -quat.y - i.y * -quat.x;

        return result;
    }

    public Vec3 plus(Vec3 vec3) {

        return new Vec3(x + vec3.x, y + vec3.y, z + vec3.z);
    }

    public Vec3 minus(Vec3 vec3) {

        return new Vec3(x - vec3.x, y - vec3.y, z - vec3.z);
    }

    public Vec3 crossProduct(Vec3 vec3) {

        return new Vec3(y * vec3.z - z * vec3.y, z * vec3.x - x * vec3.z, x * vec3.y - y * vec3.x);
    }

    public Vec3 negated() {

        return new Vec3(-x, -y, -z);
    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public void print() {
        System.out.println("(" + x + ", " + y + ", " + z + ")");
    }

    public void print(String title) {
        System.out.println(title + " (" + x + ", " + y + ", " + z + ")");
    }

    public float[] toFloatArray() {

        return new float[]{x, y, z};
    }

    public float getDistance(Vec3 v) {

        return (float) Math.sqrt(Math.pow(x - v.x, 2) + Math.pow(y - v.y, 2) + Math.pow(z - v.z, 2));
    }

    public boolean isEqual(Vec3 second) {

        boolean equal = true;

        for (int i = 0; i < 3; i++) {

            if (toFloatArray()[i] != second.toFloatArray()[i]) {

                equal = false;
            }
        }
        return equal;
    }
}
