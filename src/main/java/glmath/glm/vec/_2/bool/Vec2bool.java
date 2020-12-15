/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.bool;

/**
 *
 * @author GBarbieri
 */
public class Vec2bool extends FuncRelational {

    public Vec2bool() {
    }

    public Vec2bool(boolean x, boolean y) {
        this.x = x;
        this.y = y;
    }

    public Vec2bool set(boolean x, boolean y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
