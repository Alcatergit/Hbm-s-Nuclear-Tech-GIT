/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.i;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import glmath.glm.vec._2.i.Vec2i;
import glmath.glm.vec._3.i.Vec3i;

/**
 *
 * @author GBarbieri
 */
public class Vec4i extends FuncRelational {

    public Vec4i(Vec4i v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4i() {
        this(0);
    }

    public Vec4i(int i) {
        this(i, i, i, i);
    }

    public Vec4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4i(int[] ia) {
        this(ia, 0);
    }

    public Vec4i(int[] ia, int i) {
        this(ia[i + 0], ia[i + 1], ia[i + 2], ia[i + 3]);
    }

    public Vec4i(long l) {
        this((int) l);
    }

    public Vec4i(long[] la) {
        this(la, 0);
    }

    public Vec4i(long[] la, int i) {
        this(la[i + 0], la[i + 1], la[i + 2], la[i + 3]);
    }

    public Vec4i(int i, Vec3i v) {
        this(i, v.x, v.y, v.z);
    }

    public Vec4i(Vec3i v, int i) {
        this(v.x, v.y, v.z, i);
    }

    public Vec4i(Vec2i v0, Vec2i v1) {
        this(v0.x, v0.y, v1.x, v1.y);
    }

    public Vec4i(Vec2i v, int i0, int i1) {
        this(v.x, v.y, i0, i1);
    }

    public Vec4i(long x, long y, long z, long w) {
        this((int) x, (int) y, (int) z, (int) w);
    }

    public Vec4i set() {
        return set(0);
    }

    public Vec4i set(Vec4i v) {
        return set(v.x, v.y, v.z, v.w);
    }

    public Vec4i set(int i) {
        return set(i, i, i, i);
    }

    public Vec4i set(int[] ia) {
        return set(ia, 0);
    }

    public Vec4i set(int[] ia, int i) {
        return set(ia[i + 0], ia[i + 1], ia[i + 2], ia[i + 3]);
    }

    public Vec4i set(long l) {
        return set(l, l, l, l);
    }

    public Vec4i set(long[] la) {
        return set(la, 0);
    }

    public Vec4i set(long[] la, int i) {
        return set(la[i + 0], la[i + 1], la[i + 2], la[i + 3]);
    }

    public Vec4i set(int i, Vec3i v) {
        return set(i, v.x, v.y, v.z);
    }

    public Vec4i set(Vec3i v, int i) {
        return set(v.x, v.y, v.z, i);
    }

    public Vec4i set(Vec2i v0, Vec2i v1) {
        return set(v0.x, v0.y, v1.x, v1.y);
    }

    public Vec4i set(Vec2i v, int i0, int i1) {
        return set(v.x, v.y, i0, i1);
    }

    public Vec4i set(long x, long y, long z, long w) {
        return set((int) x, (int) y, (int) z, (int) w);
    }

    public Vec4i set(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public int[] toIA_() {
        return toIA_(new int[4]);
    }

    public int[] toIA_(int[] ia) {
        ia[0] = x;
        ia[1] = y;
        ia[2] = z;
        ia[3] = w;
        return ia;
    }

    public IntBuffer toDib_() {
        return toDib(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()).asIntBuffer());
    }

    public IntBuffer toDib(IntBuffer ib) {
        return toDib(ib, 0);
    }

    public IntBuffer toDib(IntBuffer ib, int index) {
        return ib
                .put(index + 0, x)
                .put(index + 1, y)
                .put(index + 2, z)
                .put(index + 3, w);
    }

    public FloatBuffer toDfb_() {
        return toDfb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()).asFloatBuffer());
    }

    public FloatBuffer toDfb(FloatBuffer fb) {
        return toDfb(fb, 0);
    }

    public FloatBuffer toDfb(FloatBuffer fb, int index) {
        return fb
                .put(index + 0, x)
                .put(index + 1, y)
                .put(index + 2, z)
                .put(index + 3, w);
    }

    public ByteBuffer toDbb_() {
        return toDbb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()));
    }

    public ByteBuffer toDbb(ByteBuffer bb) {
        return toDbb(bb, 0);
    }

    public ByteBuffer toDbb(ByteBuffer bb, int index) {
        return bb
                .putInt(index + 0 * Integer.BYTES, x)
                .putInt(index + 1 * Integer.BYTES, y)
                .putInt(index + 2 * Integer.BYTES, z)
                .putInt(index + 3 * Integer.BYTES, w);
    }

    public void print() {
        print("", true);
    }

    public void print(String title) {
        print(title, true);
    }

    public void print(boolean outStream) {
        print("", outStream);
    }

    public void print(String title, boolean outStream) {
        String res = title + "\n(" + x + ", " + y + ", " + z + ", " + w + ")";
        if (outStream) {
            System.out.print(res);
        } else {
            System.err.print(res);
        }
    }
}
