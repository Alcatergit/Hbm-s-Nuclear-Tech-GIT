package com.hbm.particle.lightning_test;

import java.nio.ByteBuffer;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.math.Vec3d;

public class TrailRenderer2 {

	public static final int BYTES_PER_VERTEX = 3*4 + 2*2 + 4;
	
	public static ByteBuffer aux_buf = GLAllocation.createDirectByteBuffer(512);
	public static int array_buf;
	public static int element_buf;
	public static int vao;
	private static boolean init = false;
	
	public static float[] color = new float[]{1, 1, 1, 1};
	
	public static void init(){
		array_buf = GL15.glGenBuffers();
		element_buf = GL15.glGenBuffers();
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, array_buf);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, element_buf);
		//pos
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, BYTES_PER_VERTEX, 0);
		GL20.glEnableVertexAttribArray(0);
		//tex
		GL20.glVertexAttribPointer(1, 2, GL11.GL_UNSIGNED_SHORT, true, BYTES_PER_VERTEX, 12);
		GL20.glEnableVertexAttribArray(1);
		//color
		GL20.glVertexAttribPointer(2, 4, GL11.GL_UNSIGNED_BYTE, true, BYTES_PER_VERTEX, 16);
		GL20.glEnableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public static void draw(Vec3d playerPos, List<Vec3d> points, float scale){
		if(!init){
			init = true;
			init();
		}
		int size = BYTES_PER_VERTEX * (points.size()*3+2);
		if(size > aux_buf.capacity()){
			aux_buf = GLAllocation.createDirectByteBuffer(size);
		}
		Vec3d first = points.get(0);
		Vec3d cross = points.get(1).subtract(first).crossProduct(playerPos.subtract(first)).normalize().scale(scale);
		putVertex(first.add(cross), 0F, 1F);
		putVertex(first.add(cross.scale(-1)), 0F, 0F);
		for(int i = 1; i < points.size(); i ++){
			Vec3d last = points.get(i-1);
			Vec3d current = points.get(i);
			Vec3d next = points.get(i);
			if(i < points.size()-1){
				next = points.get(i+1);
			}
			Vec3d toNext = points.get(i).subtract(last);
			Vec3d tangent = next.subtract(last);
			
			cross = tangent.crossProduct(playerPos.subtract(last)).normalize().scale(scale);
			
			putVertex(last.add(toNext.scale(0.5)), (float)(i-0.5F)/(float)(points.size()-1), 0.5F);
			putVertex(current.add(cross), (float)(i)/(float)(points.size()-1), 1F);
			putVertex(current.add(cross.scale(-1)), (float)(i)/(float)(points.size()-1), 0F);
		}
		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, array_buf);
		aux_buf.rewind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, aux_buf, GL15.GL_DYNAMIC_DRAW);
		
		for(int i = 0; i < points.size()-1; i ++){
			int offset = i*3;
			aux_buf.putInt(0+offset);
			aux_buf.putInt(2+offset);
			aux_buf.putInt(1+offset);
			
			aux_buf.putInt(0+offset);
			aux_buf.putInt(3+offset);
			aux_buf.putInt(2+offset);
			
			aux_buf.putInt(2+offset);
			aux_buf.putInt(3+offset);
			aux_buf.putInt(4+offset);
			
			aux_buf.putInt(2+offset);
			aux_buf.putInt(4+offset);
			aux_buf.putInt(1+offset);
		}
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, element_buf);
		aux_buf.rewind();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, aux_buf, GL15.GL_DYNAMIC_DRAW);
		
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, (points.size()-1)*12, GL11.GL_UNSIGNED_INT, 0);
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private static void putVertex(Vec3d pos, float texU, float texV){
		aux_buf.putFloat((float) pos.x);
		aux_buf.putFloat((float) pos.y);
		aux_buf.putFloat((float) pos.z);
		aux_buf.putShort((short)(texU*65535));
		aux_buf.putShort((short)(texV*65535));
		aux_buf.put((byte)(color[0]*255));
		aux_buf.put((byte)(color[1]*255));
		aux_buf.put((byte)(color[2]*255));
		aux_buf.put((byte)(color[3]*255));
	}
}
