/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.ui;

import glmath.joou.UInt;

/**
 *
 * @author elect
 */
public class Vec3ui extends FuncRelational {

    public Vec3ui() {
        x.value = 0;
        y.value = 0;
    }

    public Vec3ui(int x, int y, int z) {
        this(new UInt(x), new UInt(y), new UInt(z));
    }

    public Vec3ui(UInt x, UInt y, UInt z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3ui set(UInt x, UInt y, UInt z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}
