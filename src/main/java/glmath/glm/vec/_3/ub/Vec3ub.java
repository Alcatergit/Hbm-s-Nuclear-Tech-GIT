/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ub;

import glmath.joou.UByte;

/**
 *
 * @author elect
 */
public class Vec3ub extends FuncRelational {

    public Vec3ub() {
        x.value = 0;
        y.value = 0;
        z.value = 0;
    }

    public Vec3ub(int x, int y, int z) {
        this((byte) x, (byte) y, (byte) z);
    }

    public Vec3ub(byte x, byte y, byte z) {
        this(new UByte(x), new UByte(y), new UByte(z));
    }

    public Vec3ub(UByte x, UByte y, UByte z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3ub set(UByte x, UByte y, UByte z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}
