package com.hbm.lib;

//This apparently doesn't exist in the standard lib...?
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}
