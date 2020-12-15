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
abstract class BooleanOperators extends ArithmeticOperators {

    public boolean equals(Vec2d b) {
        return x == b.x & y == b.y;
    }

    public boolean notEquals(Vec2d b) {
        return x != b.x | y != b.y;
    }
}
