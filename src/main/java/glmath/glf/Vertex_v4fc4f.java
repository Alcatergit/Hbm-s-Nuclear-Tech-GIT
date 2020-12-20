/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v4fc4f {

    public Vec4 position;
    public Vec4 color;

    public static final int SIZE = 2 * Vec4.SIZE;

    public Vertex_v4fc4f() {
    }

    public Vertex_v4fc4f(Vec4 position, Vec4 color) {
        this.position = position;
        this.color = color;
    }

    public float[] toFa_() {
        return new float[]{
            position.x, position.y, position.z, position.w,
            color.x, color.y, color.z, color.w};
    }
}
