/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ub;

import glmath.joou.UByte;

/**
 *
 * @author elect
 */
public class Vec2ub extends FuncRelational {

    public Vec2ub() {
        x.value = 0;
        y.value = 0;
    }

    public Vec2ub(int x, int y) {
        this((byte) x, (byte) y);
    }

    public Vec2ub(byte x, byte y) {
        this.x.value = x;
        this.y.value = y;
    }

    public Vec2ub set(UByte x, UByte y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
