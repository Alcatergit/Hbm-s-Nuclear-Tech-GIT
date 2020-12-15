/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import java.nio.ByteBuffer;

import glmath.dev.Vec4u8;
import glmath.glm.vec._3.Vec3;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v3fv4u8 {

    public static final int SIZE = Vec3.SIZE + Vec4u8.SIZE;

    public Vec3 position;
    public Vec4u8 color;

    public Vertex_v3fv4u8(Vec3 position, Vec4u8 color) {
        this.position = position;
        this.color = color;
    }

    public void toBb(ByteBuffer bb, int index) {
        bb
                .putFloat(index * SIZE + 0 * Float.BYTES, position.x)
                .putFloat(index * SIZE + 1 * Float.BYTES, position.y)
                .putFloat(index * SIZE + 2 * Float.BYTES, position.z)
                .put(index * SIZE + 3 * Float.BYTES + 0 * Byte.BYTES, color.x)
                .put(index * SIZE + 3 * Float.BYTES + 1 * Byte.BYTES, color.y)
                .put(index * SIZE + 3 * Float.BYTES + 2 * Byte.BYTES, color.z)
                .put(index * SIZE + 3 * Float.BYTES + 3 * Byte.BYTES, color.w);
    }
}
