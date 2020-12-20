/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.bool;

/**
 *
 * @author GBarbieri
 */
public class Vec3bool extends FuncRelational {

    public Vec3bool() {
    }

    public Vec3bool(boolean x, boolean y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3bool set(boolean x, boolean y, boolean z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}
