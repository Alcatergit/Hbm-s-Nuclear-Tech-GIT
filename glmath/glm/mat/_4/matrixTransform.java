/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.mat._4;

import glmath.glm.Glm;
import glmath.glm.vec._3.Vec3;

/**
 *
 * @author GBarbieri
 */
abstract class matrixTransform extends funcMatrix {

    /**
     * Vec must be normalized
     *
     * @param radAngle
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Mat4 rotation(float radAngle, float x, float y, float z) {
        float c = (float) Math.cos(radAngle);
        float s = (float) Math.sin(radAngle);
        float t = (float) (1.0 - c);
        m00 = c + x * x * t;
        m11 = c + y * y * t;
        m22 = c + z * z * t;
        float tmp1 = x * y * t;
        float tmp2 = z * s;
        m01 = tmp1 + tmp2;
        m10 = tmp1 - tmp2;
        tmp1 = x * z * t;
        tmp2 = y * s;
        m02 = tmp1 - tmp2;
        m20 = tmp1 + tmp2;
        tmp1 = y * z * t;
        tmp2 = x * s;
        m12 = tmp1 + tmp2;
        m21 = tmp1 - tmp2;
        m03 = 0;
        m13 = 0;
        m23 = 0;
        m30 = 0;
        m31 = 0;
        m32 = 0;
        m33 = 1;
        return (Mat4) this;
    }

    public Mat4 rotate(float angle, Vec3 v) {
        return rotate(angle, v.x, v.y, v.z, (Mat4) this);
    }

    public Mat4 rotate(float angle, float x, float y, float z) {
        return rotate(angle, x, y, z, (Mat4) this);
    }

    /**
     * Vec must be normalized.
     *
     * @param angle
     * @param x
     * @param y
     * @param z
     * @param res
     * @return
     */
    public Mat4 rotate(float angle, float x, float y, float z, Mat4 res) {
        float s = (float) Math.sin(angle);
        float c = (float) Math.cos(angle);
        float C = 1.0f - c;
        // rotation matrix elements: m30, m31, m32, m03, m13, m23 = 0, m33 = 1
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float rm00 = xx * C + c;
        float rm01 = xy * C + z * s;
        float rm02 = xz * C - y * s;
        float rm10 = xy * C - z * s;
        float rm11 = yy * C + c;
        float rm12 = yz * C + x * s;
        float rm20 = xz * C + y * s;
        float rm21 = yz * C - x * s;
        float rm22 = zz * C + c;
        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm03 = m03 * rm00 + m13 * rm01 + m23 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        float nm13 = m03 * rm10 + m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        res.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        res.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        res.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        res.m23 = m03 * rm20 + m13 * rm21 + m23 * rm22;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m03 = nm03;
        res.m10 = nm10;
        res.m11 = nm11;
        res.m12 = nm12;
        res.m13 = nm13;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4 rotateX(double ang) {
        return rotateX((float) ang, (Mat4) this);
    }

    public Mat4 rotateX(float ang) {
        return rotateX(ang, (Mat4) this);
    }

    public Mat4 rotateX(float ang, Mat4 res) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm11 = +cos;
        float rm12 = +sin;
        float rm21 = -sin;
        float rm22 = +cos;
        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        float nm13 = m13 * rm11 + m23 * rm12;
        // set non-dependent values directly
        res.m20 = m10 * rm21 + m20 * rm22;
        res.m21 = m11 * rm21 + m21 * rm22;
        res.m22 = m12 * rm21 + m22 * rm22;
        res.m23 = m13 * rm21 + m23 * rm22;
        // set other values
        res.m10 = nm10;
        res.m11 = nm11;
        res.m12 = nm12;
        res.m13 = nm13;
        res.m00 = m00;
        res.m01 = m01;
        res.m02 = m02;
        res.m03 = m03;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4 rotateY(double ang) {
        return rotateY((float) ang, (Mat4) this);
    }

    public Mat4 rotateY(float ang) {
        return rotateY(ang, (Mat4) this);
    }

    public Mat4 rotateY(float ang, Mat4 res) {
        float cos, sin;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm00 = cos;
        float rm02 = -sin;
        float rm20 = sin;
        float rm22 = cos;
        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        float nm03 = m03 * rm00 + m23 * rm02;
        // set non-dependent values directly
        res.m20 = m00 * rm20 + m20 * rm22;
        res.m21 = m01 * rm20 + m21 * rm22;
        res.m22 = m02 * rm20 + m22 * rm22;
        res.m23 = m03 * rm20 + m23 * rm22;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m03 = nm03;
        res.m10 = m10;
        res.m11 = m11;
        res.m12 = m12;
        res.m13 = m13;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4 rotateZ(double ang) {
        return rotateZ((float) ang, (Mat4) this);
    }

    public Mat4 rotateZ(float ang) {
        return rotateZ(ang, (Mat4) this);
    }

    public Mat4 rotateZ(float ang, Mat4 res) {
        float sin, cos;
        if (ang == (float) Math.PI || ang == -(float) Math.PI) {
            cos = -1.0f;
            sin = 0.0f;
        } else if (ang == (float) Math.PI * 0.5f || ang == -(float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = 1.0f;
        } else if (ang == (float) -Math.PI * 0.5f || ang == (float) Math.PI * 1.5f) {
            cos = 0.0f;
            sin = -1.0f;
        } else {
            cos = (float) Math.cos(ang);
            sin = (float) Math.sin(ang);
        }
        float rm00 = cos;
        float rm01 = sin;
        float rm10 = -sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        float nm03 = m03 * rm00 + m13 * rm01;
        // set non-dependent values directly
        res.m10 = m00 * rm10 + m10 * rm11;
        res.m11 = m01 * rm10 + m11 * rm11;
        res.m12 = m02 * rm10 + m12 * rm11;
        res.m13 = m03 * rm10 + m13 * rm11;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m03 = nm03;
        res.m20 = m20;
        res.m21 = m21;
        res.m22 = m22;
        res.m23 = m23;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4 rotationX(float ang) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = 0.0f;
        m11 = cos;
        m12 = sin;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = -sin;
        m22 = cos;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
        return (Mat4) this;
    }

    public Mat4 rotationY(float ang) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        m00 = cos;
        m01 = 0.0f;
        m02 = -sin;
        m03 = 0.0f;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = sin;
        m21 = 0.0f;
        m22 = cos;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
        return (Mat4) this;
    }

    public Mat4 rotationZ(float ang) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        m00 = cos;
        m01 = sin;
        m02 = 0.0f;
        m03 = 0.0f;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0f;
        m13 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        m23 = 0.0f;
        m30 = 0.0f;
        m31 = 0.0f;
        m32 = 0.0f;
        m33 = 1.0f;
        return (Mat4) this;
    }

    public Mat4 scale(float[] f) {
        return scale(f[0], f[1], f[2]);
    }

    public Mat4 scale(float s) {
        return scale(s, s, s);
    }

    public Mat4 scale(Vec3 v) {
        return scale(v.x, v.y, v.z);
    }

    public Mat4 scale(Vec3 v, Mat4 res) {
        return scale(v.x, v.y, v.z, res);
    }

    public Mat4 scale(float x, float y, float z) {
        return scale(x, y, z, (Mat4) this);
    }

    public Mat4 scale(float x, float y, float z, Mat4 res) {
        res.m00 = m00 * x;
        res.m01 = m01 * x;
        res.m02 = m02 * x;
        res.m03 = m03 * x;
        res.m10 = m10 * y;
        res.m11 = m11 * y;
        res.m12 = m12 * y;
        res.m13 = m13 * y;
        res.m20 = m20 * z;
        res.m21 = m21 * z;
        res.m22 = m22 * z;
        res.m23 = m23 * z;
        res.m30 = m30;
        res.m31 = m31;
        res.m32 = m32;
        res.m33 = m33;
        return res;
    }

    public Mat4 translation(Vec3 v) {
        return translation((Mat4) this, v.x, v.y, v.z);
    }

    public Mat4 translation(float x, float y, float z) {
        return translation((Mat4) this, x, y, z);
    }

    public static Mat4 translation(Mat4 result, float x, float y, float z) {
        result.m00 = 1.0f;
        result.m01 = 0.0f;
        result.m02 = 0.0f;
        result.m03 = 0.0f;
        result.m10 = 0.0f;
        result.m11 = 1.0f;
        result.m12 = 0.0f;
        result.m13 = 0.0f;
        result.m20 = 0.0f;
        result.m21 = 0.0f;
        result.m22 = 1.0f;
        result.m23 = 0.0f;
        result.m30 = x;
        result.m31 = y;
        result.m32 = z;
        result.m33 = 1.0f;
        return result;
    }

    public Mat4 translate(Vec3 v) {
        return translate((Mat4) this, v.x, v.y, v.z);
    }

    public Mat4 translate(float[] t) {
        return translate((Mat4) this, t[0], t[1], t[2]);
    }

    public Mat4 translate(float x, float y, float z) {
        return translate((Mat4) this, x, y, z);
    }

    public Mat4 translate(Mat4 res, Vec3 v) {
        return translate(res, v.x, v.y, v.z);
    }

    public Mat4 translate(Mat4 res, float x, float y, float z) {
        // translation matrix elements: m00, m11, m22, m33 = 1
        // m30 = x, m31 = y, m32 = z, all others = 0
        res.m30 = res.m00 * x + res.m10 * y + res.m20 * z + res.m30;
        res.m31 = res.m01 * x + res.m11 * y + res.m21 * z + res.m31;
        res.m32 = res.m02 * x + res.m12 * y + res.m22 * z + res.m32;
        res.m33 = res.m03 * x + res.m13 * y + res.m23 * z + res.m33;
        return (Mat4) this;
    }

    public Mat4 lookAt(Vec3 eye, Vec3 center, Vec3 up) {
        return Glm.lookAt(eye, center, up, (Mat4) this);
    }

    public static Mat4 lookAt(Vec3 eye, Vec3 center, Vec3 up, Mat4 res) {
        return Glm.lookAt(eye, center, up, res);
    }

    public Mat4 ortho(float left, float right, float bottom, float top, float zNear, float zFar) {
        return Glm.ortho((Mat4) this, left, right, bottom, top, zNear, zFar);
    }

    public Mat4 perspective(float fovy, float aspect, float zNear, float zFar) {
        return Glm.perspective(fovy, aspect, zNear, zFar, (Mat4) this);
    }

    public Mat4 perspectiveFov(float fov, float width, float height, float zNear, float zFar) {
        return Glm.perspectiveFov(fov, width, height, zNear, zFar, (Mat4) this);
    }
}
