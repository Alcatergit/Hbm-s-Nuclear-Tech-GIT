/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.jglm;

/**
 * @deprecated 
 * @author gbarbieri
 */
public class Vec4i extends Veci {

    public int x;
    public int y;
    public int z;
    public int w;

    public Vec4i() {

        super();
    }

    public Vec4i(int[] vec4) {
        vector = vec4;
        x = vector[0];
        y = vector[1];
        z = vector[2];
        w = vector[3];
    }

    public Vec4i(int value) {
        vector = new int[]{value, value, value, value};
        x = vector[0];
        y = vector[1];
        z = vector[2];
        w = vector[3];
    }

    public Vec4i(Vec3i vec3i, int w) {
        x = vec3i.x;
        y = vec3i.y;
        z = vec3i.z;
        this.w = w;
        vector = new int[]{x, y, z, this.w};
    }

    public Vec4i(int[] intArray, int i) {
        vector = new int[]{intArray[i], intArray[i + 1], intArray[i + 2], intArray[i + 3]};
        x = vector[0];
        y = vector[1];
        z = vector[2];
        w = vector[3];
    }

    public Vec4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        vector = new int[]{this.x, this.y, this.z, this.w};
    }

    public float length() {

        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public void print() {
        System.out.println("(" + x + ", " + y + ", " + z + ", " + w + ")");
    }

    public void print(String title) {
        System.out.println(title + " (" + x + ", " + y + ", " + z + ", " + w + ")");
    }

//    public Vec4 mult(float scalar) {
//
//        return new Vec4(x * scalar, y * scalar, z * scalar, w * scalar);
//    }
//
//    public Vec4 mult(Vec4 vec4) {
//
//        return new Vec4(x * vec4.x, y * vec4.y, z * vec4.z, w * vec4.w);
//    }
//
//    public Vec4 minus(Vec4 vec4) {
//
//        return new Vec4(x - vec4.x, y - vec4.y, z - vec4.z, w - vec4.w);
//    }
//
//    public Vec4 minus(float scalar) {
//
//        return new Vec4(x - scalar, y - scalar, z - scalar, w - scalar);
//    }
//
//    public Vec4 plus(Vec4 vec4) {
//
//        return new Vec4(x + vec4.x, y + vec4.y, z + vec4.z, w + vec4.w);
//    }
//
//    public Vec4 divide(float scalar) {
//
//        return new Vec4(x / scalar, y / scalar, z / scalar, w / scalar);
//    }
//
    public float[] toFloatArray() {

        return new float[]{x, y, z, w};
    }
}
