/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.jglm;

/**
 * @deprecated
 * @author gbarbieri
 */
public class Vec2i extends Veci {

    public int x;
    public int y;

    public Vec2i() {

        x = 0;
        y = 0;
    }

    public Vec2i(int[] floatArray) {

        vector = floatArray;

        x = vector[0];
        y = vector[1];
    }

    public Vec2i(int x, int y) {

        this.x = x;
        this.y = y;
        vector = new int[]{x, y};
    }

    public Vec2i(int[] intArray, int i) {

        vector = new int[]{intArray[i], intArray[i + 1]};
        x = vector[0];
        y = vector[1];
    }

    public Vec2i plus(Vec2i vec2i) {

        return new Vec2i(x + vec2i.x, y + vec2i.y);
    }

    public Vec2i minus(Vec2i vec2i) {

        return new Vec2i(x - vec2i.x, y - vec2i.y);
    }

    public Vec2i times(int scalar) {

        return new Vec2i(x * scalar, y * scalar);
    }

    public Vec2 times(float scalar) {

        return new Vec2(x * scalar, y * scalar);
    }

    public Vec2i negated() {

        return new Vec2i(-x, -y);
    }

    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }

    public void print(String title) {
        System.out.println(title + " (" + x + ", " + y + ")");
    }

    public int[] toIntArray() {

        return new int[]{x, y};
    }
}
