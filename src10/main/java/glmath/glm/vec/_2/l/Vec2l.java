/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.l;

/**
 *
 * @author GBarbieri
 */
public class Vec2l extends FuncRelational {

    public Vec2l() {
        x = 0;
        y = 0;
    }

    public Vec2l(int x, int y) {
        this((long) x, y);
    }

    public Vec2l(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Vec2l set(long x, long y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
