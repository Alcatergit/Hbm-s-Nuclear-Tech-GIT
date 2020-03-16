/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.us;

import glmath.joou.UShort;

/**
 *
 * @author GBarbieri
 */
public class Vec2us extends FuncRelational {

    public Vec2us() {
        x.value = 0;
        y.value = 0;
    }

    public Vec2us(int x, int y) {
        this((short) x, (short) y);
    }

    public Vec2us(short x, short y) {
        this.x.value = x;
        this.y.value = y;
    }

    public Vec2us set(UShort x, UShort y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
