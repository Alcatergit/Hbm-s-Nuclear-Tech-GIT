package com.hbm.render.item;

import java.nio.FloatBuffer;

import static com.hbm.render.model.BakedModelMatrixUtil.*;

public final class ItemRenderFrames17 {

	private ItemRenderFrames17() { }

	public static final FloatBuffer GUI = glMatrix(
			translate(0.5, 0.375, 0), rotateX(30), rotateY(225), scale(1.0 / 16),
			scale(-1, -1, -1), rotateY(-45), rotateX(30), translate(-8, -10, 0));

	public static final FloatBuffer THIRD_PERSON = glMatrix(
			translate(0.4375, 0.375, 1.125), rotateY(180), rotateX(90),
			translate(0.1875, 0.625, -0.125), scale(0.375),
			rotateZ(60), rotateX(-90), rotateZ(20),
			translate(0, -0.3, 0), scale(1.5), rotateY(50), rotateZ(335), translate(-0.9375, -0.0625, 0));

	public static final FloatBuffer HEAD = glMatrix(
			translate(0.5, 0.5, 0.5), scale(1.6, -1.6, -1.6), rotateY(180), translate(0, 0.25, 0),
			translate(0, -0.3, 0), scale(1.5), rotateY(50), rotateZ(335), translate(-0.9375, -0.0625, 0));

	public static final FloatBuffer GROUND = glMatrix(
			translate(0.5, 0.25, 0.5), scale(0.5));

	public static final FloatBuffer FIXED = glMatrix(
			translate(0.5, 0.34, 0.53125), rotateY(180));
}
