/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.d;

/**
 *
 * @author elect
 */
public class Vec2d extends FuncRelational {

    public Vec2d() {
        x = 0;
        y = 0;
    }

    public Vec2d(int x, int y) {
        this((double) x, (double) y);
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
