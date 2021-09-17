package com.hbm.render;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GLAllocation;

public class Vbo {

	//Pos, tex, normal, color
	public static final int BYTES_PER_VERTEX = 3*4 + 2*4 + 3 + 4;
	
	int drawMode;
	int numVertices;
	int vboId;
	
	public Vbo(int vboId, int drawMode, int numVertices) {
		this.vboId = vboId;
		this.drawMode = drawMode;
		this.numVertices = numVertices;
	}
	
	private void preDraw(){
		GL11.glVertexPointer(3, GL11.GL_FLOAT, BYTES_PER_VERTEX, 0);
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glTexCoordPointer(2, GL11.GL_FLOAT, BYTES_PER_VERTEX, 12);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glNormalPointer(GL11.GL_BYTE, BYTES_PER_VERTEX, 20);
		GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, BYTES_PER_VERTEX, 23);
		GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	public void draw(){
		GLCompat.bindBuffer(GLCompat.GL_ARRAY_BUFFER, vboId);
		preDraw();
		GL11.glDrawArrays(drawMode, 0, numVertices);
		postDraw();
		GLCompat.bindBuffer(GLCompat.GL_ARRAY_BUFFER, 0);
	}
	
	private void postDraw(){
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
		GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	public static Vbo setupTestVbo(){
		Vertex bottomLeft = new Vertex(-0.5F, -0.5F, 0F, 0F, 0F, 0F, 0F, 1F, 1F, 1F, 1F, 1F);
		Vertex bottomRight = new Vertex(0.5F, -0.5F, 0F, 1F, 0F, 0F, 0F, 1F, 1F, 1F, 1F, 1F);
		Vertex topLeft = new Vertex(-0.5F, 0.5F, 0F, 0F, 1F, 0F, 0F, 1F, 1F, 1F, 1F, 1F);
		Vertex topRight = new Vertex(0.5F, 0.5F, 0F, 1F, 1F, 0F, 0F, 1F, 1F, 1F, 1F, 1F);
		Vertex[] vertices = new Vertex[]{bottomLeft, bottomRight, topRight, topLeft};
		
		
		int vboId = GLCompat.genBuffers();
		ByteBuffer data = GLAllocation.createDirectByteBuffer(vertices.length*BYTES_PER_VERTEX);
		for(Vertex v : vertices){
			data.putFloat(v.x);
			data.putFloat(v.y);
			data.putFloat(v.z);
			data.putFloat(v.u);
			data.putFloat(v.v);
			//Normals don't need as much precision as tex coords or positions
			data.put((byte)((int)(v.normalX*127)&0xFF));
			data.put((byte)((int)(v.normalY*127)&0xFF));
			data.put((byte)((int)(v.normalZ*127)&0xFF));
			//Neither do colors
			data.put((byte)(v.r*255));
			data.put((byte)(v.g*255));
			data.put((byte)(v.b*255));
			data.put((byte)(v.a*255));
		}
		data.rewind();
		GLCompat.bindBuffer(GLCompat.GL_ARRAY_BUFFER, vboId);
		GLCompat.bufferData(GLCompat.GL_ARRAY_BUFFER, data, GLCompat.GL_STATIC_DRAW);
		GLCompat.bindBuffer(GLCompat.GL_ARRAY_BUFFER, 0);
		
		Vbo vbo = new Vbo(vboId, GL11.GL_QUADS, vertices.length);
		return vbo;
	}
	
	public static class Vertex {
		public float x;
		public float y;
		public float z;
		public float u;
		public float v;
		public float normalX;
		public float normalY;
		public float normalZ;
		public float r;
		public float g;
		public float b;
		public float a;
		
		public Vertex(float x, float y, float z, float u, float v, float nX, float nY, float nZ, float r, float g, float b, float a) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.u = u;
			this.v = v;
			this.normalX = nX;
			this.normalY = nY;
			this.normalZ = nZ;
			this.r = r;
			this.g = g;
			this.b = b;
			this.a = a;
		}
	}
}
