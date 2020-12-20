/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import java.nio.ByteBuffer;

import glmath.glm.vec._2.Vec2;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v2fv2f {

    public static final int SIZE = 2 * Vec2.SIZE;

    public Vec2 position;
    public Vec2 texCoord;

    public Vertex_v2fv2f(Vec2 position, Vec2 texCoord) {
        this.position = position;
        this.texCoord = texCoord;
    }

    public void toBb(ByteBuffer bb, int index) {
        bb
                .putFloat(index * SIZE + 0 * Float.BYTES, position.x)
                .putFloat(index * SIZE + 1 * Float.BYTES, position.y)
                .putFloat(index * SIZE + 2 * Float.BYTES, texCoord.x)
                .putFloat(index * SIZE + 3 * Float.BYTES, texCoord.y);
    }
}
