/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.s;

/**
 *
 * @author GBarbieri
 */
public class Vec4s extends FuncRelational {

    public Vec4s() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }

    public Vec4s(int x, int y, int z, int w) {
        this((short) x, (short) y, (short) z, (short) w);
    }

    public Vec4s(short x, short y, short z, short w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4s set(int x, int y, int z, int w) {
        return set((short) x, (short) y, (short) z, (short) w);
    }

    public Vec4s set(short x, short y, short z, short w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
}
