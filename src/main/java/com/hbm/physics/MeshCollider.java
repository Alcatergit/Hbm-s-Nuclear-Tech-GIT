package com.hbm.physics;

import com.hbm.render.amlfrom1710.Vec3;

public class MeshCollider extends Collider {

	public float[] vertices;
	public int[] triangles;
	
	public MeshCollider() {
	}

	@Override
	public Vec3 support(Vec3 dir) {
		double dot = -1;
		int index = 0;
		for(int i = 0; i < vertices.length; i += 3){
			double newDot = dir.xCoord*vertices[i] + dir.yCoord*vertices[i+1] + dir.zCoord*vertices[i+2];
			if(newDot > dot){
				dot = newDot;
				index = i;
			}
		}
		return new Vec3(vertices[index], vertices[index+1], vertices[index+2]);
	}

	@Override
	public Collider copy() {
		MeshCollider c = new MeshCollider();
		c.vertices = vertices;
		c.triangles = triangles;
		c.localCentroid = localCentroid;
		c.localInertiaTensor = localInertiaTensor;
		c.mass = mass;
		return c;
	}
	
}
