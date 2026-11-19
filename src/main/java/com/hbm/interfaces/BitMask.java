package com.hbm.interfaces;

public interface BitMask {
    boolean get(int bit);

    void set(int bit);

    boolean getAndSet(int bit);

    int nextSetBit(int from);

    int nextClearBit(int from);

    int previousSetBit(int from);

    int previousClearBit(int from);

    boolean isEmpty();

    long cardinality();

    int length();

    int size();

    int logicalSize();

    long[] toLongArray();

    default void free() {
    }
}