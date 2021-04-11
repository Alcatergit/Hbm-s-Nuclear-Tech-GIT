package com.hbm.render.util;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.Vec3d;

public class Triangle {

	public TexVertex p1, p2, p3;
	
	public Triangle(Vec3d p1, Vec3d p2, Vec3d p3) {
		this.p1 = new TexVertex(p1);
		this.p2 = new TexVertex(p2);
		this.p3 = new TexVertex(p3);
	}
	
	public Triangle(Vec3d p1, Vec3d p2, Vec3d p3, float[] texCoords) {
		this.p1 = new TexVertex(p1, texCoords[0], texCoords[1]);
		this.p2 = new TexVertex(p2, texCoords[2], texCoords[3]);
		this.p3 = new TexVertex(p3, texCoords[4], texCoords[5]);
	}
	
	public void tessellateAll(BufferBuilder buf){
		buf.pos(p1.pos.x, p1.pos.y, p1.pos.z).tex(p1.texX, p1.texY).endVertex();
		buf.pos(p2.pos.x, p2.pos.y, p2.pos.z).tex(p2.texX, p2.texY).endVertex();
		buf.pos(p3.pos.x, p3.pos.y, p3.pos.z).tex(p3.texX, p3.texY).endVertex();
	}
	
	public static class TexVertex {
		public Vec3d pos;
		public float texX, texY;
		
		public TexVertex(Vec3d pos) {
			this.pos = pos;
			texX = 0;
			texY = 0;
		}
		
		public TexVertex(Vec3d pos, float x, float y) {
			this.pos = pos;
			texX = x;
			texY = y;
		}
	}
	
}