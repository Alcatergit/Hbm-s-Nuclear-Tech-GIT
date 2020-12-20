/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import glmath.glm.vec._3.Vec3;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v3fn3f {

    public Vec3 position;
    public Vec3 normal;

    public Vertex_v3fn3f(Vec3 position, Vec3 normal) {
        this.position = position;
        this.normal = normal;
    }

    public static final int SIZE = 2 * Vec3.SIZE;
    public static final int OFFSET_POSITION = 0;
    public static final int OFFSET_NORMAL = Vec3.SIZE;
}
