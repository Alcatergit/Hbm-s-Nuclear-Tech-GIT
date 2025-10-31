/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.dev;

import glmath.glm.vec._4.Vec4;

/**
 *
 * @author GBarbieri
 */
public class Vec4u8 {

    public static final int SIZE = 4 * Byte.BYTES;

    public byte x, y, z, w;

    public Vec4u8() {
        x = 0;
        y = 0;
        z = 0;
        w = 0;
    }
    
    public Vec4u8(Vec4 v) {
        x = (byte) v.x;
        y = (byte) v.y;
        z = (byte) v.z;
        w = (byte) v.w;
    }

    public Vec4u8(byte x, byte y, byte z, byte w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vec4u8(float x, float y, float z, float w) {
        this.x = (byte) x;
        this.y = (byte) y;
        this.z = (byte) z;
        this.w = (byte) w;
    }

    public byte[] toBa_() {
        return toBa(new byte[4]);
    }

    public byte[] toBa(byte[] byteArray) {
        byteArray[0] = x;
        byteArray[1] = y;
        byteArray[2] = z;
        byteArray[3] = w;
        return byteArray;
    }
}
