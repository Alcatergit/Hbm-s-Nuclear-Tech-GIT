package com.hbm.render.model;

import java.nio.FloatBuffer;

import net.minecraft.client.renderer.GLAllocation;

public final class BakedModelMatrixUtil {

	private BakedModelMatrixUtil() { }

	public static double[] identity() {
		double[] m = new double[16];
		m[0] = m[5] = m[10] = m[15] = 1.0;
		return m;
	}

	public static double[] mul(double[] a, double[] b) {
		double[] r = new double[16];
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++) {
				double s = 0;
				for(int k = 0; k < 4; k++) s += a[i * 4 + k] * b[k * 4 + j];
				r[i * 4 + j] = s;
			}
		return r;
	}

	public static double[] translate(double x, double y, double z) {
		double[] m = identity();
		m[3] = x; m[7] = y; m[11] = z;
		return m;
	}

	public static double[] scale(double xyz) { return scale(xyz, xyz, xyz); }

	public static double[] scale(double x, double y, double z) {
		double[] m = identity();
		m[0] = x; m[5] = y; m[10] = z;
		return m;
	}

	public static double[] rotateX(double deg) {
		double r = Math.toRadians(deg), c = Math.cos(r), s = Math.sin(r);
		double[] m = identity();
		m[5] = c; m[6] = -s; m[9] = s; m[10] = c;
		return m;
	}

	public static double[] rotateY(double deg) {
		double r = Math.toRadians(deg), c = Math.cos(r), s = Math.sin(r);
		double[] m = identity();
		m[0] = c; m[2] = s; m[8] = -s; m[10] = c;
		return m;
	}

	public static double[] rotateZ(double deg) {
		double r = Math.toRadians(deg), c = Math.cos(r), s = Math.sin(r);
		double[] m = identity();
		m[0] = c; m[1] = -s; m[4] = s; m[5] = c;
		return m;
	}

	public static FloatBuffer glMatrix(double[]... operations) {
		double[] m = identity();
		for(double[] op : operations) m = mul(m, op);
		FloatBuffer buf = GLAllocation.createDirectFloatBuffer(16);
		buf.put((float) m[0]).put((float) m[4]).put((float) m[8]).put((float) m[12]);
		buf.put((float) m[1]).put((float) m[5]).put((float) m[9]).put((float) m[13]);
		buf.put((float) m[2]).put((float) m[6]).put((float) m[10]).put((float) m[14]);
		buf.put((float) m[3]).put((float) m[7]).put((float) m[11]).put((float) m[15]);
		buf.flip();
		return buf;
	}
}
