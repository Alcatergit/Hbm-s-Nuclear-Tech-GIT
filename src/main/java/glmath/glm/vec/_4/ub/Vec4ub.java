/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ub;

import glmath.joou.UByte;

/**
 *
 * @author GBarbieri
 */
public class Vec4ub extends FuncRelational {

    public Vec4ub() {
        x.value = 0;
        y.value = 0;
        z.value = 0;
        w.value = 0;
    }

    public Vec4ub(int x, int y, int z, int w) {
        this((byte) x, (byte) y, (byte) z, (byte) w);
    }

    public Vec4ub(byte x, byte y, byte z, byte w) {
        this(new UByte(x), new UByte(y), new UByte(z), new UByte(w));
    }

    public Vec4ub(UByte x, UByte y, UByte z, UByte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4ub set(UByte x, UByte y, UByte z, UByte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
}
