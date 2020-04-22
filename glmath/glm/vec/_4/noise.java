/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glmath.glm.vec._4;

/**
 *
 * @author elect
 */
abstract class noise extends FuncRelational {

//    public Vec4 grad4_(float j) {
//        return grad4_(j, (Vec4) this);
//    }
//
//    public static Vec4 grad4_(float j, Vec4 ip) {
//        Vec3 pXYZ = new Vec3(j).mul(new Vec3(ip)).fract().mul(7).floor().mul(ip.z).sub(1);
//        float pW = 1.5f - pXYZ.abs_().dot(new Vec3(1));
//        Vec4 s = new Vec4(pXYZ, pW).lessThan(new Vec4(0.0f));
//        pXYZ.add(new Vec3(s).mul(2).sub(1).mul(s.w));
//        return new Vec4(pXYZ, pW);
//    }
//
//    public float simplex_() {
//        return simplex_((Vec4) this);
//    }
//
//    public static float simplex_(Vec4 v) {
//
//        Vec4 c = new Vec4(
//                +0.138196601125011f, // (5 - sqrt(5))/20  G4
//                +0.276393202250021f, // 2 * G4
//                +0.414589803375032f, // 3 * G4
//                -0.447213595499958f); // -1 + 4 * G4
//
//        // (sqrt(5) - 1)/4 = F4, used once below
//        float f4 = 0.309016994374947451f;
//
//        // First corner
//        Vec4 i = v.add_(v.dot(new Vec4(f4))).floor_();
//        Vec4 x0 = v.sub_(i).add(i.dot(new Vec4(c.x)));
//
//        // Other corners
//        // Rank sorting originally contributed by Bill Licea-Kane, AMD (formerly ATI)
//        Vec4 i0;
//        Vec3 isX = new Vec3(x0.x).step(new Vec3(x0.y, x0.z, x0.w));
//        Vec3 isYZ = new Vec3(x0.y, x0.y, x0.z).step(new Vec3(x0.z, x0.w, x0.w));
//        //  i0.x = dot(isX, vec3(1.0));
//        //i0.x = isX.x + isX.y + isX.z;
//        //i0.yzw = static_cast<T>(1) - isX;
//        i0 = new Vec4(isX.x + isX.y + isX.z, new Vec3(1).sub(isX));
//        //  i0.y += dot(isYZ.xy, vec2(1.0));
//        i0.y += isYZ.x + isYZ.y;
//        //i0.zw += 1.0 - tvec2<T, P>(isYZ.x, isYZ.y);
//        i0.z += 1 - isYZ.x;
//        i0.w += 1 - isYZ.y;
//        i0.z += isYZ.z;
//        i0.w += 1 - isYZ.z;
//
//        // i0 now contains the unique values 0,1,2,3 in each channel
//        Vec4 i3 = i0.clamp_(0, 1);
//        Vec4 i2 = i0.sub_(1).clamp_(0, 1);
//        Vec4 i1 = i0.sub_(2).clamp_(0, 1);
//
//        //  x0 = x0 - 0.0 + 0.0 * C.xxxx
//        //  x1 = x0 - i1  + 0.0 * C.xxxx
//        //  x2 = x0 - i2  + 0.0 * C.xxxx
//        //  x3 = x0 - i3  + 0.0 * C.xxxx
//        //  x4 = x0 - 1.0 + 4.0 * C.xxxx
//        Vec4 x1 = x0.sub_(i1).add(c.x);
//        Vec4 x2 = x0.sub_(i2).add(c.y);
//        Vec4 x3 = x0.sub_(i3).add(c.z);
//        Vec4 x4 = x0.add_(c.w);
//
//        // Permutations
//        i.mod_(new Vec4(289));
//        float j0 = permute(permute(permute(permute(i.w) + i.z) + i.y) + i.x);
//        Vec4 j1 = permute_(permute_(permute_(permute_(
//                new Vec4(i.w).add(i1.w, i2.w, i3.w, 1))
//                .add(i.z).add(i1.z, i2.z, i3.z, 1))
//                .add(i.y).add(i1.y, i2.y, i3.y, 1))
//                .add(i.x).add(i1.x, i2.x, i3.x, 1));
//
//        // Gradients: 7x7x6 points over a cube, mapped onto a 4-cross polytope
//        // 7*7*6 = 294, which is close to the ring size 17*17 = 289.
//        Vec4 ip = new Vec4(1f / 294, 1f / 49, 1f / 7, 0);
//
//        Vec4 p0 = grad4_(j0, ip);
//        Vec4 p1 = grad4_(j1.x, ip);
//        Vec4 p2 = grad4_(j1.y, ip);
//        Vec4 p3 = grad4_(j1.z, ip);
//        Vec4 p4 = grad4_(j1.w, ip);
//
//        // Normalise gradients
//        Vec4 norm = taylorInvSqrt_(new Vec4(p0.dot(p0), p1.dot(p1), p2.dot(p2), p3.dot(p3)));
//        p0.mul(norm.x);
//        p1.mul(norm.y);
//        p2.mul(norm.z);
//        p3.mul(norm.w);
//        p4.mul(taylorInvSqrt_(p4.dot(p4)));
//
//        // Mix contributions from the five corners
//        Vec3 m0 = new Vec3(0.6f).sub(new Vec3(x0.dot(x0), x1.dot(x1), x2.dot(x2))).max(new Vec3(0.0f));
//        Vec2 m1 = new Vec2(0.6f).sub(new Vec2(x3.dot(x3), x4.dot(x4))).max(new Vec2(0));
//
//        m0.mul(m0);
//        m1.mul(m1);
//
//        return 49f * (m0.mul(m0).dot(new Vec3(p0.dot(x0), p1.dot(x1), p2.dot(x2)))
//                + m1.mul(m1).dot(new Vec2(p3.dot(x3), p4.dot(x4))));
//    }
//
//    public static Vec4 taylorInvSqrt_(Vec4 r) {
//        return new Vec4(1.79284291400159f).sub(r.mul_(0.85373472095314f));
//    }
//
//    public static float taylorInvSqrt_(float r) {
//        return 1.79284291400159f - r * 0.85373472095314f;
//    }
//
//    private static float permute(float x) {
//        return mod289((x * 34 + 1) * x);
//    }
//
//    private static float mod289(float x) {
//        return (float) (x - glm.Glm.floor(x * 1 / 289) * 289);
//    }
//
//    private static Vec4 permute_(Vec4 x) {
//        return mod289_(new Vec4(x).mul(34).add(1).mul(x));
//    }
//
//    private static Vec4 mod289_(Vec4 v) {
//        Vec4 result = new Vec4();
//        result.x = (float) (v.x - glm.Glm.floor(v.x * 1 / 289) * 289);
//        result.y = (float) (v.y - glm.Glm.floor(v.y * 1 / 289) * 289);
//        result.z = (float) (v.z - glm.Glm.floor(v.z * 1 / 289) * 289);
//        result.w = (float) (v.w - glm.Glm.floor(v.w * 1 / 289) * 289);
//        return result;
//    }
}
