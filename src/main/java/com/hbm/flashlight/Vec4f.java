package com.hbm.flashlight;

import javax.vecmath.Tuple4f;

import net.minecraft.util.math.Vec3d;


public class Vec4f extends Tuple4f {

	private static final long serialVersionUID = 7725582154646111264L;
	
	public Vec4f(Vec3d in) {
		super((float)in.x, (float)in.y, (float)in.z, (float)1.0f);
	}

	public Vec4f(float x, float y, float z, float w) {
		super(x, y, z, w);
	}
}
