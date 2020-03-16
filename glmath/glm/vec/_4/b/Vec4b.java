/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.b;

/**
 *
 * @author elect
 */
public class Vec4b extends FuncRelational {

    public Vec4b() {
        this(0);
    }

    public Vec4b(int i) {
        this(i, i, i, i);
    }

    public Vec4b(byte b) {
        this(b, b, b, b);
    }

    public Vec4b(byte x, byte y, byte z, byte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4b(int x, int y, int z, int w) {
        this.x = (byte) x;
        this.y = (byte) y;
        this.z = (byte) z;
        this.w = (byte) w;
    }
    
    public Vec4b set(int x, int y, int z, int w) {
        return set((byte) x, (byte) y, (byte) z, (byte) w);
    }

    public Vec4b set(byte x, byte y, byte z, byte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public boolean all() {
        return x == 1 && y == 1 && z == 1 && w == 1;
    }
}
