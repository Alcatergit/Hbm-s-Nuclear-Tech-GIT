/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm;

import glmath.glm.mat._4.Mat4;

/**
 *
 * @author GBarbieri
 */
abstract class funcMatrix extends packing {

    public static Mat4 transpose_(Mat4 mat) {
        return Mat4.transpose(mat, new Mat4());
    }

    public static Mat4 transpose(Mat4 mat, Mat4 dest) {
        return Mat4.transpose(mat, dest);
    }
}
