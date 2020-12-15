/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import glmath.glm.vec._2.Vec2;
import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v2fc4f {

    public static final int SIZE = Vec4.SIZE + Vec2.SIZE;

    public Vec4 position;
    public Vec2 texCoord;

    public Vertex_v2fc4f() {
    }

    public Vertex_v2fc4f(Vec4 position, Vec2 texCoord) {
        this.position = position;
        this.texCoord = texCoord;
    }
}
