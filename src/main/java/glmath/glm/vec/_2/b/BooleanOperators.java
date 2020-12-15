/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.b;

/**
 *
 * @author elect
 */
abstract class BooleanOperators extends BasicOperators {

    public boolean equals(Vec2b b) {
        return glmath.glm.Glm.equals((Vec2b) this, b);
    }

    public boolean notEquals(Vec2b b) {
        return glmath.glm.Glm.notEquals((Vec2b) this, b);
    }
}
