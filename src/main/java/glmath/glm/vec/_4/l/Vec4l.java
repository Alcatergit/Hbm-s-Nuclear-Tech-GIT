/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.l;

/**
 *
 * @author GBarbieri
 */
public class Vec4l extends FuncRelational {

    public Vec4l() {
        x = 0;
        y = 0;
    }

    public Vec4l(int x, int y, int z, int w) {
        this((long) x, y, z, w);
    }

    public Vec4l(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Vec4l set(long x, long y, long z, long w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }
}
