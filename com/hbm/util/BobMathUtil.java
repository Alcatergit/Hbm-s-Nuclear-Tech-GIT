package com.hbm.util;

import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BobMathUtil {

	public static double getAngleFrom2DVecs(double x1, double z1, double x2, double z2) {

		double upper = x1 * x2 + z1 * z2;
		double lower = Math.sqrt(x1 * x1 + z1 * z1) * Math.sqrt(x2 * x2 + z2 * z2);

		double result = Math.toDegrees(Math.cos(upper / lower));

		if(result >= 180)
			result -= 180;

		return result;
	}
	
	public static double getCrossAngle(Vec3d vel, Vec3d rel) {

		vel = vel.normalize();
		rel = rel.normalize();

		double angle = Math.toDegrees(Math.acos(vel.dotProduct(rel)));

		if(angle >= 180)
			angle -= 180;

		return angle;
	}
	
	public static double getCrossAngle(Vec3 vel, Vec3 rel) {

		vel.normalize();
		rel.normalize();

		double angle = Math.toDegrees(Math.acos(vel.dotProduct(rel)));

		if(angle >= 180)
			angle -= 180;

		return angle;
	}

	public static float remap(float num, float min1, float max1, float min2, float max2){
		return ((num - min1) / (max1 - min1)) * (max2 - min2) + min2;
	}

	public static float remap01(float num, float min1, float max1){
		return (num - min1) / (max1 - min1);
	}
	
	public static float remap01_clamp(float num, float min1, float max1){
		return MathHelper.clamp((num - min1) / (max1 - min1), 0, 1);
	}

	public static Vec3 getEulerAngles(Vec3 vec) {
		double yaw = Math.toDegrees(Math.atan2(vec.xCoord, vec.zCoord));
		double sqrt = MathHelper.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord);
		double pitch = Math.toDegrees(Math.atan2(vec.yCoord, sqrt));
		return Vec3.createVectorHelper(yaw, pitch, 0);
	}
	
	public static Vec3d getEulerAngles(Vec3d vec) {
		double yaw = Math.toDegrees(Math.atan2(vec.x, vec.z));
		double sqrt = MathHelper.sqrt(vec.x * vec.x + vec.z * vec.z);
		double pitch = Math.toDegrees(Math.atan2(vec.y, sqrt));
		return new Vec3d(yaw, pitch-90, 0);
	}
	
	public static Vec3d getVectorFromAngle(float yaw, float pitch){
		Vec3d vec = new Vec3d(0, 1, 0);
		return vec.rotatePitch((float) Math.toRadians(pitch)).rotateYaw((float) Math.toRadians(yaw));
	}
	
	public static Vec3d getVectorFromAngle(Vec3d vec){
		return getVectorFromAngle((float)vec.x, (float)vec.y);
	}
}