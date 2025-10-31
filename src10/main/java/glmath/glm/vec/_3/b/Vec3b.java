/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.b;

/**
 *
 * @author elect
 */
public class Vec3b extends FuncRelational{

    

    public Vec3b() {
        this(0);
    }

    public Vec3b(int i) {
        this(i, i, i);
    }

    public Vec3b(byte b) {
        this(b, b, b);
    }

    public Vec3b(byte x, byte y, byte z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3b(int x, int y, int z) {
        this.x = (byte) x;
        this.y = (byte) y;
        this.z = (byte) z;
    }

    public Vec3b set(int x, int y, int z) {
        return set((byte) x, (byte) y, (byte) z);
    }

    public Vec3b set(byte x, byte y, byte z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}
