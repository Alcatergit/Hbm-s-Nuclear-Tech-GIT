package com.hbm.util;

import com.hbm.interfaces.BitMask;
import com.hbm.interfaces.ThreadSafeMethod;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * Thread-safe bitset backed by {@link AtomicLongArray}.
 *
 * <p>Replaces the community edition's {@code ConcurrentBitSet} (Unsafe CAS on a {@code long[]}) and
 * {@code OffHeapBitSet} (off-heap) with a single on-heap, JDK-only implementation. Bit setting is atomic,
 * so many worker threads may set distinct bits of the same bitset concurrently.</p>
 */
public final class AtomicLongBitSet implements BitMask, Cloneable {

    private final AtomicLongArray words;
    private final int wordCount;
    private final int logicalSize;
    private final AtomicLong bitCount = new AtomicLong();

    public AtomicLongBitSet(int logicalSize) {
        if (logicalSize < 0) throw new NegativeArraySizeException("logicalSize < 0: " + logicalSize);
        this.logicalSize = logicalSize;
        this.wordCount = (logicalSize + 63) >>> 6;
        this.words = new AtomicLongArray(wordCount);
    }

    public AtomicLongBitSet(AtomicLongBitSet other) {
        this.logicalSize = other.logicalSize;
        this.wordCount = other.wordCount;
        this.words = new AtomicLongArray(wordCount);
        long total = 0L;
        for (int i = 0; i < wordCount; i++) {
            long w = other.words.get(i);
            this.words.set(i, w);
            total += Long.bitCount(w);
        }
        this.bitCount.set(total);
    }

    @ThreadSafeMethod
    public static AtomicLongBitSet fromLongArray(long[] data, int logicalSize) {
        AtomicLongBitSet bitSet = new AtomicLongBitSet(logicalSize);
        if (logicalSize == 0) return bitSet;
        int wordsToCopy = Math.min(data.length, bitSet.wordCount);
        int lastIdx = (logicalSize - 1) >>> 6;
        int rem = logicalSize & 63;
        long tailMask = rem == 0 ? -1L : ((1L << rem) - 1L);
        long total = 0L;
        for (int i = 0; i < wordsToCopy; i++) {
            long w = data[i];
            if (i > lastIdx) w = 0L;
            else if (i == lastIdx && rem != 0) w &= tailMask;
            bitSet.words.set(i, w);
            total += Long.bitCount(w);
        }
        bitSet.bitCount.set(total);
        return bitSet;
    }

    @ThreadSafeMethod
    @Override
    public boolean get(int bit) {
        if (bit < 0) throw new IndexOutOfBoundsException("bit < 0: " + bit);
        if (bit >= logicalSize) return false;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        return (words.get(wi) & mask) != 0L;
    }

    @ThreadSafeMethod
    @Override
    public void set(int bit) {
        if (bit < 0 || bit >= logicalSize) return;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long old = words.get(wi);
            if ((old & mask) != 0L) return;
            long next = old | mask;
            if (words.compareAndSet(wi, old, next)) {
                bitCount.incrementAndGet();
                return;
            }
        }
    }

    @ThreadSafeMethod
    @Override
    public boolean getAndSet(int bit) {
        if (bit < 0 || bit >= logicalSize) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long old = words.get(wi);
            if ((old & mask) != 0L) return true;
            long next = old | mask;
            if (words.compareAndSet(wi, old, next)) {
                bitCount.incrementAndGet();
                return false;
            }
        }
    }

    @ThreadSafeMethod
    public void clear(int bit) {
        if (bit < 0 || bit >= logicalSize) return;
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long old = words.get(wi);
            if ((old & mask) == 0L) return;
            long next = old & ~mask;
            if (words.compareAndSet(wi, old, next)) {
                bitCount.decrementAndGet();
                return;
            }
        }
    }

    @ThreadSafeMethod
    public boolean getAndClear(int bit) {
        if (bit < 0 || bit >= logicalSize) throw new IndexOutOfBoundsException("bit index out of bounds: " + bit);
        int wi = bit >>> 6;
        long mask = 1L << (bit & 63);
        while (true) {
            long old = words.get(wi);
            if ((old & mask) == 0L) return false;
            long next = old & ~mask;
            if (words.compareAndSet(wi, old, next)) {
                bitCount.decrementAndGet();
                return true;
            }
        }
    }

    @ThreadSafeMethod
    @Override
    public int nextSetBit(int from) {
        if (from < 0) from = 0;
        int wi = from >>> 6;
        if (wi >= wordCount) return -1;
        long word = words.get(wi) & (~0L << (from & 63));
        while (true) {
            if (word != 0L) {
                int idx = (wi << 6) + Long.numberOfTrailingZeros(word);
                return (idx < logicalSize) ? idx : -1;
            }
            wi++;
            if (wi >= wordCount) return -1;
            word = words.get(wi);
        }
    }

    @ThreadSafeMethod
    @Override
    public int nextClearBit(int from) {
        if (from < 0) throw new IndexOutOfBoundsException("from < 0: " + from);
        if (from >= logicalSize) return from;
        int wi = from >>> 6;
        if (wi >= wordCount) return from;
        long word = ~words.get(wi) & (-1L << (from & 63));
        while (true) {
            if (word != 0L) {
                int idx = (wi << 6) + Long.numberOfTrailingZeros(word);
                return Math.min(idx, logicalSize);
            }
            wi++;
            if (wi >= wordCount) return logicalSize;
            word = ~words.get(wi);
        }
    }

    @ThreadSafeMethod
    @Override
    public int previousSetBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSize) from = logicalSize - 1;
        if (from < 0) return -1;
        int wi = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = words.get(wi) & mask;
        while (true) {
            if (word != 0L) return (wi << 6) + (63 - Long.numberOfLeadingZeros(word));
            wi--;
            if (wi < 0) return -1;
            word = words.get(wi);
        }
    }

    @ThreadSafeMethod
    @Override
    public int previousClearBit(int from) {
        if (from < 0) return -1;
        if (from >= logicalSize) from = logicalSize - 1;
        if (from < 0) return -1;
        int wi = from >>> 6;
        long mask = ~0L >>> (63 - (from & 63));
        long word = ~words.get(wi) & mask;
        while (true) {
            if (word != 0L) return (wi << 6) + (63 - Long.numberOfLeadingZeros(word));
            wi--;
            if (wi < 0) return -1;
            word = ~words.get(wi);
        }
    }

    @ThreadSafeMethod
    @Override
    public boolean isEmpty() {
        return bitCount.get() == 0L;
    }

    @ThreadSafeMethod
    @Override
    public long cardinality() {
        return bitCount.get();
    }

    @ThreadSafeMethod
    @Override
    public int length() {
        if (logicalSize == 0) return 0;
        int maxWord = (logicalSize - 1) >>> 6;
        long mask = lastWordMask();
        for (int i = maxWord; i >= 0; i--) {
            long w = words.get(i);
            if (i == maxWord) w &= mask;
            if (w != 0L) {
                return (i << 6) + (64 - Long.numberOfLeadingZeros(w));
            }
        }
        return 0;
    }

    @ThreadSafeMethod
    @Override
    public int size() {
        return wordCount << 6;
    }

    @ThreadSafeMethod
    @Override
    public int logicalSize() {
        return logicalSize;
    }

    @ThreadSafeMethod
    @Override
    public long[] toLongArray() {
        int len = length();
        if (len == 0) return new long[0];
        int used = (len + 63) >>> 6;
        long[] out = new long[used];
        int last = used - 1;
        int rem = len & 63;
        long tailMask = rem == 0 ? -1L : ((1L << rem) - 1L);
        for (int i = 0; i < used; i++) {
            long v = words.get(i);
            if (i == last && rem != 0) v &= tailMask;
            out[i] = v;
        }
        return out;
    }

    private long lastWordMask() {
        int r = logicalSize & 63;
        return r == 0 ? -1L : ((1L << r) - 1L);
    }

    @ThreadSafeMethod
    @Override
    public AtomicLongBitSet clone() {
        return new AtomicLongBitSet(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        int i = nextSetBit(0);
        if (i != -1) {
            sb.append(i);
            while (true) {
                i = nextSetBit(i + 1);
                if (i == -1) break;
                sb.append(", ").append(i);
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
