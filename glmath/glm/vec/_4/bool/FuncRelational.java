/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.bool;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational {

    public static final int SIZE = 4 * Byte.BYTES;

    public boolean x, y, z, w;

    public boolean any() {
        return Glm.any((Vec4bool) this);
    }

    public boolean all() {
        return Glm.all((Vec4bool) this);
    }

    public Vec4bool not_() {
        return Glm.not((Vec4bool) this, new Vec4bool());
    }

    public Vec4bool not() {
        return Glm.not((Vec4bool) this, (Vec4bool) this);
    }
}
