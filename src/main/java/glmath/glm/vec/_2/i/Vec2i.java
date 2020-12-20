/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._2.i;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

/**
 *
 * @author GBarbieri
 */
public class Vec2i extends FuncRelational {

    public Vec2i() {
        x = 0;
        y = 0;
    }

    public Vec2i(int i) {
        x = i;
        y = i;
    }

    public Vec2i(Vec2i v) {
        x = v.x;
        y = v.y;
    }

    public Vec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2i set(Vec2i v) {
        x = v.x;
        y = v.y;
        return this;
    }

    public Vec2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
     public int[] toIA_() {
        return toIA_(new int[2]);
    }

    public int[] toIA_(int[] ia) {
        ia[0] = x;
        ia[1] = y;
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
                .put(index + 1, y);
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
                .putInt(index + 1 * Integer.BYTES, y);
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
