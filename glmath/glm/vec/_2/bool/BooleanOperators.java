/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.bool;

/**
 *
 * @author elect
 */
abstract class BooleanOperators {

    public static final int SIZE = 2 * Byte.BYTES;

    public boolean x, y;

    public boolean equals(Vec2bool b) {
        return x == b.x & y == b.y;
    }

    public boolean notEquals(Vec2bool b) {
        return x != b.x | y != b.y;
    }
}
