/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.mat._3;

import glmath.glm.vec._3.Vec3;

/**
 *
 * @author GBarbieri
 */
abstract class matrixTransform extends funcMatrix {

    public Mat3 rotation(float ang, Vec3 v) {
        return rotation(ang, v.x, v.y, v.z);
    }

    /**
     * Vec must be normalized
     *
     * @param ang
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Mat3 rotation(float ang, float x, float y, float z) {
        float cos = (float) Math.cos(ang);
        float sin = (float) Math.sin(ang);
        float C = 1.0f - cos;
        float xy = x * y, xz = x * z, yz = y * z;
        m00 = cos + x * x * C;
        m10 = xy * C - z * sin;
        m20 = xz * C + y * sin;
        m01 = xy * C + z * sin;
        m11 = cos + y * y * C;
        m21 = yz * C - x * sin;
        m02 = xz * C - y * sin;
        m12 = yz * C + x * sin;
        m22 = cos + z * z * C;
        return (Mat3) this;
    }

    public Mat3 rotate(float angle, Vec3 v) {
        return rotate(angle, v.x, v.y, v.z, (Mat3) this);
    }

    public Mat3 rotate(float angle, float x, float y, float z) {
        return rotate(angle, x, y, z, (Mat3) this);
    }

    /**
     * Vec must be normalized.
     *
     * @param ang
     * @param x
     * @param y
     * @param z
     * @param res
     * @return
     */
    public Mat3 rotate(float ang, float x, float y, float z, Mat3 res) {
        float sin = (float) Math.sin(ang);
        float cos = (float) Math.cos(ang);
        float invCos = 1.0f - cos;

        // rotation matrix elements:
        // m30, m31, m32, m03, m13, m23 = 0
        float xx = x * x, xy = x * y, xz = x * z;
        float yy = y * y, yz = y * z;
        float zz = z * z;
        float rm00 = xx * invCos + cos;
        float rm01 = xy * invCos + z * sin;
        float rm02 = xz * invCos - y * sin;
        float rm10 = xy * invCos - z * sin;
        float rm11 = yy * invCos + cos;
        float rm12 = yz * invCos + x * sin;
        float rm20 = xz * invCos + y * sin;
        float rm21 = yz * invCos - x * sin;
        float rm22 = zz * invCos + cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01 + m20 * rm02;
        float nm01 = m01 * rm00 + m11 * rm01 + m21 * rm02;
        float nm02 = m02 * rm00 + m12 * rm01 + m22 * rm02;
        float nm10 = m00 * rm10 + m10 * rm11 + m20 * rm12;
        float nm11 = m01 * rm10 + m11 * rm11 + m21 * rm12;
        float nm12 = m02 * rm10 + m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        res.m20 = m00 * rm20 + m10 * rm21 + m20 * rm22;
        res.m21 = m01 * rm20 + m11 * rm21 + m21 * rm22;
        res.m22 = m02 * rm20 + m12 * rm21 + m22 * rm22;
        // set other values
        res.m00 = nm00;
        res.m01 = nm01;
        res.m02 = nm02;
        res.m10 = nm10;
        res.m11 = nm11;
        res.m12 = nm12;

        return res;
    }

    public Mat3 rotateX(double ang) {
        return rotateX((float) ang, (Mat3) this);
    }

    public Mat3 rotateX(float ang) {
        return rotateX(ang, (Mat3) this);
    }

    public Mat3 rotateX(float ang, Mat3 res) {
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
        float rm21 = -sin;
        float rm12 = +sin;
        float rm22 = +cos;

        // add temporaries for dependent values
        float nm10 = m10 * rm11 + m20 * rm12;
        float nm11 = m11 * rm11 + m21 * rm12;
        float nm12 = m12 * rm11 + m22 * rm12;
        // set non-dependent values directly
        res.m20 = m10 * rm21 + m20 * rm22;
        res.m21 = m11 * rm21 + m21 * rm22;
        res.m22 = m12 * rm21 + m22 * rm22;
        // set other values
        res.m10 = nm10;
        res.m11 = nm11;
        res.m12 = nm12;
        res.m00 = m00;
        res.m01 = m01;
        res.m02 = m02;

        return res;
    }

    public Mat3 rotateY(double ang) {
        return rotateY((float) ang, (Mat3) this);
    }

    public Mat3 rotateY(float ang) {
        return rotateY(ang, (Mat3) this);
    }

    public Mat3 rotateY(float ang, Mat3 dest) {
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
        float rm20 = sin;
        float rm02 = -sin;
        float rm22 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m20 * rm02;
        float nm01 = m01 * rm00 + m21 * rm02;
        float nm02 = m02 * rm00 + m22 * rm02;
        // set non-dependent values directly
        dest.m20 = m00 * rm20 + m20 * rm22;
        dest.m21 = m01 * rm20 + m21 * rm22;
        dest.m22 = m02 * rm20 + m22 * rm22;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m10 = m10;
        dest.m11 = m11;
        dest.m12 = m12;

        return dest;
    }

    public Mat3 rotateZ(double ang) {
        return rotateZ((float) ang, (Mat3) this);
    }

    public Mat3 rotateZ(float ang) {
        return rotateZ(ang, (Mat3) this);
    }

    public Mat3 rotateZ(float ang, Mat3 dest) {
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
        float rm10 = -sin;
        float rm01 = sin;
        float rm11 = cos;

        // add temporaries for dependent values
        float nm00 = m00 * rm00 + m10 * rm01;
        float nm01 = m01 * rm00 + m11 * rm01;
        float nm02 = m02 * rm00 + m12 * rm01;
        // set non-dependent values directly
        dest.m10 = m00 * rm10 + m10 * rm11;
        dest.m11 = m01 * rm10 + m11 * rm11;
        dest.m12 = m02 * rm10 + m12 * rm11;
        // set other values
        dest.m00 = nm00;
        dest.m01 = nm01;
        dest.m02 = nm02;
        dest.m20 = m20;
        dest.m21 = m21;
        dest.m22 = m22;

        return dest;
    }

    public Mat3 rotationX(float ang) {
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
        m00 = 1.0f;
        m01 = 0.0f;
        m02 = 0.0f;
        m10 = 0.0f;
        m11 = +cos;
        m12 = +sin;
        m20 = 0.0f;
        m21 = -sin;
        m22 = +cos;
        return (Mat3) this;
    }

    public Mat3 rotationY(float ang) {
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
        m00 = +cos;
        m01 = 0.0f;
        m02 = -sin;
        m10 = 0.0f;
        m11 = 1.0f;
        m12 = 0.0f;
        m20 = +sin;
        m21 = 0.0f;
        m22 = +cos;
        return (Mat3) this;
    }

    public Mat3 rotationZ(float ang) {
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
        m00 = cos;
        m01 = sin;
        m02 = 0.0f;
        m10 = -sin;
        m11 = cos;
        m12 = 0.0f;
        m20 = 0.0f;
        m21 = 0.0f;
        m22 = 1.0f;
        return (Mat3) this;
    }
}
