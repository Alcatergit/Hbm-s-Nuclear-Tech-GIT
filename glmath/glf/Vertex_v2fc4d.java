/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import java.nio.ByteBuffer;

import glmath.glm.vec._2.Vec2;
import glmath.glm.vec._4.d.Vec4d;

/**
 *
 * @author elect
 */
public class Vertex_v2fc4d {

    public static final int SIZE = Vec2.SIZE + Vec4d.SIZE;

    public Vec2 position;
    public Vec4d color;

    public Vertex_v2fc4d(Vec2 position, Vec4d color) {
        this.position = position;
        this.color = color;
    }

    public ByteBuffer toBB(ByteBuffer bb, int index) {
        return bb
                .putFloat(index * SIZE + 0 * Float.BYTES, position.x)
                .putFloat(index * SIZE + 1 * Float.BYTES, position.y)
                .putDouble(index * SIZE + 2 * Float.BYTES + 0 * Double.BYTES, color.x)
                .putDouble(index * SIZE + 2 * Float.BYTES + 1 * Double.BYTES, color.y)
                .putDouble(index * SIZE + 2 * Float.BYTES + 2 * Double.BYTES, color.z)
                .putDouble(index * SIZE + 2 * Float.BYTES + 3 * Double.BYTES, color.w);
    }
}
