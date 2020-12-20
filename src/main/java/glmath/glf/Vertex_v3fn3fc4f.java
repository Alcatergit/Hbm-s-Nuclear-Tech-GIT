/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glf;

import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
public class Vertex_v3fn3fc4f {

    public Vec3 position;
    public Vec3 texCoord;
    public Vec4 color;

    public Vertex_v3fn3fc4f(Vec3 position, Vec3 texCoord, Vec4 color) {
        this.position = position;
        this.texCoord = texCoord;
        this.color = color;
    }

    public static final int SIZE = 2 * Vec3.SIZE + Vec4.SIZE;
    public static final int OFFSET_POSITION = 0;
    public static final int OFFSET_NORMAL = Vec3.SIZE;
    public static final int OFFSET_COLOR = 2 * Vec3.SIZE;
}
