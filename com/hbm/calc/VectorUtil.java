package com.hbm.calc;

import net.minecraft.util.math.Vec3d;

public class VectorUtil {

public static double getCrossAngle(Vec3d vel, Vec3d rel) {
		
		vel.normalize();
		rel.normalize();

		double vecProd = rel.x * vel.x + rel.y * vel.y + rel.z * vel.z;
		double bot = rel.lengthVector() * vel.lengthVector();
		double angle = Math.acos(vecProd / bot) * 180 / Math.PI;
		
		return angle;
	}
}
