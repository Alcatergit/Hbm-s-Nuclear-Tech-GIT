/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import glmath.glm.vec._2.i.Vec2i;
import glmath.glm.vec._3.Vec3;
import glmath.glm.vec._4.Vec4;
import glmath.joou.UInt;

/**
 *
 * @author GBarbieri
 */
public class Vec2ui extends FuncRelational {

    public Vec2ui() {
        x.value = 0;
        y.value = 0;
    }

    public Vec2ui(int i) {
        x.value = i;
        y.value = i;
    }

    public Vec2ui(int x, int y) {
        this.x.value = x;
        this.y.value = y;
    }

    public Vec2ui(Vec2ui v) {
        x = v.x;
        y = v.y;
    }

    public Vec2ui(Vec2i v) {
        x.value = v.x;
        y.value = v.y;
    }

    public Vec2ui(Vec3 v) {
        x.value =  (int) v.x;
        y.value = (int) v.y;
    }

    public Vec2ui(Vec4 v) {
        x.value = (int) v.x;
        y.value = (int) v.y;;
    }

    public Vec2ui set(int i) {
        x.value = i;
        y.value = i;
        return this;
    }

    public Vec2ui set(UInt x, UInt y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2ui set(float x, float y) {
        this.x.value = (int) x;
        this.y.value = (int) y;
        return this;
    }

    public Vec2ui set(int[] ia) {
        x.value = ia[0];
        y.value = ia[1];
        return this;
    }

    public float[] toIa_() {
        return toIa(new float[2]);
    }

    public float[] toIa(float[] fa) {
        fa[0] = x.value;
        fa[1] = y.value;
        return fa;
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
                .put(index + 1, (int) y.value);
    }

    public ByteBuffer toDbb_() {
        return toDbb(ByteBuffer.allocateDirect(SIZE).order(ByteOrder.nativeOrder()));
    }

    public ByteBuffer toDbb(ByteBuffer bb) {
        return toDbb(bb, 0);
    }

    public ByteBuffer toDbb(ByteBuffer bb, int index) {
        return bb
                .putInt(index + 0 * Integer.BYTES, (int) x.value)
                .putInt(index + 1 * Integer.BYTES, (int) y.value);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
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
        String res = title + "\n(" + x + ", " + y + ")";
        if (outStream) {
            System.out.print(res);
        } else {
            System.err.print(res);
        }
    }
}
