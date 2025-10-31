/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.bool;

import glmath.glm.Glm;

/**
 *
 * @author GBarbieri
 */
abstract class FuncRelational extends BooleanOperators {

    public boolean any() {
        return Glm.any((Vec2bool) this);
    }

    public boolean all() {
        return Glm.all((Vec2bool) this);
    }

    public Vec2bool not_() {
        return Glm.not((Vec2bool) this, new Vec2bool());
    }

    public Vec2bool not() {
        return Glm.not((Vec2bool) this, (Vec2bool) this);
    }
}
