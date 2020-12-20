/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._3.bool;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational {

    public static final int SIZE = 3 * Byte.BYTES;

    public boolean x, y, z;

    public boolean any() {
        return Glm.any((Vec3bool) this);
    }

    public boolean all() {
        return Glm.all((Vec3bool) this);
    }

    public Vec3bool not_() {
        return Glm.not((Vec3bool) this, new Vec3bool());
    }

    public Vec3bool not() {
        return Glm.not((Vec3bool) this, (Vec3bool) this);
    }
}
