/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4.t;

/**
 *
 * @author elect
 */
public class Vec4t<T> {
    
    public T r;
    public T g;
    public T b;
    public T a;

    public Vec4t(T t) {
        r = t;
        g = t;
        b = t;
        a = t;
    }

    public Vec4t(T r, T g, T b, T a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
//    public T[] toArray() {
//        return new T[]{r, g, b, a};
//    }
}
