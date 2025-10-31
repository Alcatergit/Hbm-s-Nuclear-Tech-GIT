/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.bool;

/**
 *
 * @author GBarbieri
 */
public class Vec4bool extends FuncRelational {

    public Vec4bool() {
    }

    public Vec4bool(boolean x, boolean y, boolean z, boolean w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vec4bool set(boolean x, boolean y, boolean z, boolean w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
}
