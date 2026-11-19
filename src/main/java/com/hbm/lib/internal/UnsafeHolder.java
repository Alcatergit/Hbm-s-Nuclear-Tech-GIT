package com.hbm.lib.internal;

import com.hbm.core.HbmCorePlugin;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Java-8 only {@link sun.misc.Unsafe} holder.
 *
 * <p>The community edition's {@code AbstractUnsafe}/{@code SunUnsafeWrapper}/{@code InternalUnsafeWrapper}
 * exist to support running on BOTH Java 8 and Java 9+. This project (NTM-Extended 1.12.2) only ever runs
 * on Java 8, so we only need the Java-8 half. However, the ported executor and {@code ChunkUtil} were written
 * against the Java 9+ Unsafe method names ({@code getReference}, {@code compareAndSetReference},
 * {@code putIntRelease}, ...), which do NOT exist on Java 8's {@link sun.misc.Unsafe}. This holder exposes
 * those Java 9+ names and maps them to the Java 8 names at runtime.</p>
 */
@SuppressWarnings("removal")
public final class UnsafeHolder {

    /** Java 9+ named methods, backed by Java 8's sun.misc.Unsafe. */
    public static final UnsafeAccess U = new UnsafeAccess();

    private UnsafeHolder() {
    }

    public static final class UnsafeAccess {
        private final Unsafe u = getSunUnsafe();

        // ---- offsets & allocation ----
        public long objectFieldOffset(Field f) { return u.objectFieldOffset(f); }
        public long arrayBaseOffset(Class<?> c) { return u.arrayBaseOffset(c); }
        public int arrayIndexScale(Class<?> c) { return u.arrayIndexScale(c); }
        public Object allocateInstance(Class<?> c) throws InstantiationException { return u.allocateInstance(c); }

        // ---- references (Java 9+ name -> Java 8 name) ----
        public Object getReference(Object o, long off) { return u.getObject(o, off); }
        public void putReference(Object o, long off, Object x) { u.putObject(o, off, x); }
        public Object getReferenceVolatile(Object o, long off) { return u.getObjectVolatile(o, off); }
        public void putReferenceVolatile(Object o, long off, Object x) { u.putObjectVolatile(o, off, x); }
        public void putReferenceRelease(Object o, long off, Object x) { u.putOrderedObject(o, off, x); }
        public boolean compareAndSetReference(Object o, long off, Object expected, Object x) {
            return u.compareAndSwapObject(o, off, expected, x);
        }
        public Object getAndSetReference(Object o, long off, Object x) { return u.getAndSetObject(o, off, x); }

        // ---- int ----
        public int getInt(Object o, long off) { return u.getInt(o, off); }
        public void putInt(Object o, long off, int x) { u.putInt(o, off, x); }
        public int getIntVolatile(Object o, long off) { return u.getIntVolatile(o, off); }
        public void putIntVolatile(Object o, long off, int x) { u.putIntVolatile(o, off, x); }
        public void putIntRelease(Object o, long off, int x) { u.putOrderedInt(o, off, x); }
        public boolean compareAndSetInt(Object o, long off, int expected, int x) {
            return u.compareAndSwapInt(o, off, expected, x);
        }
        public int getAndAddInt(Object o, long off, int delta) { return u.getAndAddInt(o, off, delta); }
        public int getAndSetInt(Object o, long off, int x) { return u.getAndSetInt(o, off, x); }

        // ---- long ----
        public long getLong(Object o, long off) { return u.getLong(o, off); }
        public void putLong(Object o, long off, long x) { u.putLong(o, off, x); }
        public long getLongVolatile(Object o, long off) { return u.getLongVolatile(o, off); }
        public void putLongVolatile(Object o, long off, long x) { u.putLongVolatile(o, off, x); }
        public void putLongRelease(Object o, long off, long x) { u.putOrderedLong(o, off, x); }
        public boolean compareAndSetLong(Object o, long off, long expected, long x) {
            return u.compareAndSwapLong(o, off, expected, x);
        }
        public long getAndAddLong(Object o, long off, long delta) { return u.getAndAddLong(o, off, delta); }
        public long getAndSetLong(Object o, long off, long x) { return u.getAndSetLong(o, off, x); }

        // ---- boolean ----
        public boolean getBoolean(Object o, long off) { return u.getBoolean(o, off); }
        public void putBoolean(Object o, long off, boolean x) { u.putBoolean(o, off, x); }
        public boolean getBooleanVolatile(Object o, long off) { return u.getBooleanVolatile(o, off); }
        public void putBooleanVolatile(Object o, long off, boolean x) { u.putBooleanVolatile(o, off, x); }

        private static Unsafe getSunUnsafe() {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (Unsafe) f.get(null);
            } catch (Exception e) {
                throw new RuntimeException("Failed to obtain sun.misc.Unsafe", e);
            }
        }
    }

    public static long fieldOffset(Class<?> clz, String fieldName) {
        try {
            return U.objectFieldOffset(clz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static long fieldOffset(Class<?> clz, String mcp, String srg) {
        try {
            return U.objectFieldOffset(clz.getDeclaredField(HbmCorePlugin.chooseName(mcp, srg)));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T allocateInstance(Class<? extends T> clz) {
        try {
            return (T) U.allocateInstance(clz);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
