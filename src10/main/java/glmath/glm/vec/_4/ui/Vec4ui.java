/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import glmath.joou.UInt;

/**
 *
 * @author GBarbieri
 */
public class Vec4ui extends FuncRelational {

    public Vec4ui() {
        this(0);
    }

    public Vec4ui(Vec4ui v) {
        this(v.x, v.y, v.z, v.w);
    }

    public Vec4ui(int i) {
        this(i, i, i, i);
    }

    public Vec4ui(int[] ia) {
        this(ia, 0);
    }

    public Vec4ui(int[] ia, int i) {
        this(ia[i + 0], ia[i + 1], ia[i + 2], ia[i + 3]);
    }

    public Vec4ui(long l) {
        this((int) l);
    }

    public Vec4ui(long[] la) {
        this(la, 0);
    }

    public Vec4ui(long[] la, int offset) { 
        this(la[offset + 0], la[offset + 1], la[offset + 2], la[offset + 3]);
    }

    public Vec4ui(ByteBuffer bb) {
        this(bb, 0);
    }

    public Vec4ui(ByteBuffer bb, int offset) {
        this(bb.getInt(offset + 0 * Integer.BYTES), bb.getInt(offset + 1 * Integer.BYTES),
                bb.getInt(offset + 2 * Integer.BYTES), bb.getInt(offset + 3 * Integer.BYTES));
    }

    public Vec4ui(IntBuffer ib) {
        this(ib, 0);
    }

    public Vec4ui(IntBuffer ib, int offset) {
        this(ib.get(offset + 0), ib.get(offset + 1), ib.get(offset + 2), ib.get(offset + 3));
    }

//    public Vec4ui(int f, Vec3 v) {
//        this(f, v.x, v.y, v.z);
//    }
//
//    public Vec4ui(Vec3 v, float f) {
//        this(v.x, v.y, v.z, f);
//    }
//
//    public Vec4ui(Vec2 v0, Vec2 v1) {
//        this(v0.x, v0.y, v1.x, v1.y);
//    }
//
//    public Vec4ui(Vec2 v, float f0, float f1) {
//        this(v.x, v.y, f0, f1);
//    }
    public Vec4ui(long x, long y, long z, long w) {
        this((int) x, (int) y, (int) z, (int) w);
    }

    public Vec4ui(UInt x, UInt y, UInt z, UInt w) {
        this.x.value = x.value;
        this.y.value = y.value;
        this.z.value = z.value;
        this.w.value = w.value;
    }

    public Vec4ui(int x, int y, int z, int w) {
        this.x.value = x;
        this.y.value = y;
        this.z.value = z;
        this.w.value = w;
    }

    public Vec4ui set() {
        return set(0);
    }

    public Vec4ui set(Vec4ui v) {
        return set(v.x, v.y, v.z, v.w);
    }

    public Vec4ui set(int i) {
        return set(i, i, i, i);
    }

    public Vec4ui set(int[] ia) {
        return set(ia, 0);
    }

    public Vec4ui set(int[] ia, int i) {
        return set(ia[i + 0], ia[i + 1], ia[i + 2], ia[i + 3]);
    }

    public Vec4ui set(long l) {
        return set(l, l, l, l);
    }

    public Vec4ui set(long[] la) {
        return set(la, 0);
    }

    public Vec4ui set(long[] la, int i) {
        return set(la[i + 0], la[i + 1], la[i + 2], la[i + 3]);
    }

    public Vec4ui set(ByteBuffer bb) {
        return set(bb, 0);
    }

    public Vec4ui set(ByteBuffer bb, int offset) {
        return set(bb.getInt(offset + 0 * Integer.BYTES), bb.getInt(offset + 1 * Integer.BYTES),
                bb.getInt(offset + 2 * Integer.BYTES), bb.getInt(offset + 3 * Integer.BYTES));
    }

    public Vec4ui set(IntBuffer ib) {
        return set(ib, 0);
    }

    public Vec4ui set(IntBuffer ib, int offset) {
        return set(ib.get(offset + 0), ib.get(offset + 1), ib.get(offset + 2), ib.get(offset + 3));
    }

//    public Vec4ui set(int i, Vec3 v) {
//        return set(i, v.x, v.y, v.z);
//    }
//
//    public Vec4ui set(Vec3 v, float f) {
//        return set(v.x, v.y, v.z, f);
//    }
//
//    public Vec4ui set(Vec2 v0, Vec2 v1) {
//        return set(v0.x, v0.y, v1.x, v1.y);
//    }
//
//    public Vec4ui set(Vec2 v, float f0, float f1) {
//        return set(v.x, v.y, f0, f1);
//    }
    public Vec4ui set(long x, long y, long z, long w) {
        return set((int) x, (int) y, (int) z, (int) w);
    }

    public Vec4ui set(int x, int y, int z, int w) {
        this.x.value = x;
        this.y.value = y;
        this.z.value = z;
        this.w.value = w;
        return this;
    }

    public Vec4ui set(UInt x, UInt y, UInt z, UInt w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

//    public Vec3 toVec3_() {
//        return toVec3(new Vec3());
//    }
//
//    public Vec3 toVec3(Vec3 res) {
//        return res.set(x, y, z);
//    }
    public int[] toIa_() {
        return toIa(new int[4]);
    }

    public int[] toIa(int[] ia) {
        ia[0] = (int) x.value;
        ia[1] = (int) y.value;
        ia[2] = (int) z.value;
        ia[3] = (int) w.value;
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
                .put(index + 0, (int) x.value)
                .put(index + 1, (int) y.value)
                .put(index + 2, (int) z.value)
                .put(index + 3, (int) w.value);
    }

    public ByteBuffer toDbb_() {
        return toDbb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()));
    }

    public ByteBuffer toDbb(ByteBuffer bb) {
        return toDbb(bb, 0);
    }

    public ByteBuffer toDbb(ByteBuffer bb, int index) {
        return bb
                .putInt(index + 0 * Float.BYTES, (int) x.value)
                .putInt(index + 1 * Float.BYTES, (int) y.value)
                .putInt(index + 2 * Float.BYTES, (int) z.value)
                .putInt(index + 3 * Float.BYTES, (int) w.value);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ", " + w + ")";
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
