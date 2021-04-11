package com.hbm.render.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

import com.hbm.util.BobMathUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedModelUtil {

	public static int generateDecalMesh(World world, Vec3d normal, float scale, float offsetX, float offsetY, float offsetZ){
		Vec3d euler = BobMathUtil.getEulerAngles(normal);
		Matrix3f rot = eulerToMat((float)Math.toRadians(euler.x), (float)Math.toRadians(euler.y+90), world.rand.nextFloat()*2F*(float)Math.PI);
		Vec3d c1 = new Vec3d(rot.m00, rot.m01, rot.m02);
		Vec3d c2 = new Vec3d(rot.m10, rot.m11, rot.m12);
		Vec3d c3 = new Vec3d(rot.m20, rot.m21, rot.m22);
		
		float[][] planes = new float[6][4];
		planes[0] = new float[]{(float)c1.x, (float)c1.y, (float)c1.z, scale};
		planes[1] = new float[]{(float)-c1.x, (float)-c1.y, (float)-c1.z, scale};
		planes[2] = new float[]{(float)c2.x, (float)c2.y, (float)c2.z, scale};
		planes[3] = new float[]{(float)-c2.x, (float)-c2.y, (float)-c2.z, scale};
		planes[4] = new float[]{(float)c3.x, (float)c3.y, (float)c3.z, scale*3};
		planes[5] = new float[]{(float)-c3.x, (float)-c3.y, (float)-c3.z, scale*3};
		
		AxisAlignedBB box = getBox(c1.scale(scale), c2.scale(scale), c3.scale(scale*3)).offset(offsetX, offsetY, offsetZ);
		
		List<Triangle> tris = new ArrayList<>();
		for(int i = (int) Math.floor(box.minX); i <= Math.ceil(box.maxX); i ++){
			for(int j = (int) Math.floor(box.minY); j <= Math.ceil(box.maxY); j ++){
				for(int k = (int) Math.floor(box.minZ); k <= Math.ceil(box.maxZ); k ++){
					BlockPos pos = new BlockPos(i, j, k);
					IBlockState state = world.getBlockState(pos);
					state = state.getActualState(world, pos);
					if(state.getRenderType() != EnumBlockRenderType.MODEL)
						continue;
					IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state);
					List<Triangle> block_tris = triangulateBlockModel(world, pos, model, state, MathHelper.getPositionRandom(pos), i-offsetX, j-offsetY, k-offsetZ, true);
					Iterator<Triangle> itr = block_tris.iterator();
					while(itr.hasNext()){
						Triangle t = itr.next();
						Vec3d tnorm = t.v2.pos.subtract(t.v1.pos).crossProduct(t.v3.pos.subtract(t.v1.pos)).normalize();
						if(tnorm.dotProduct(normal) > 0)
							itr.remove();
					}
					List<Triangle> newTris;
					for(int p = 0; p < planes.length; p ++){
						newTris = new ArrayList<>();
						float[] plane = planes[p];
						for(Triangle t : block_tris){
							Triangle[][] clipped = planeClipTriangle(t, plane);
							if(clipped[0][0] != null)
								newTris.add(clipped[0][0]);
							if(clipped[0][1] != null)
								newTris.add(clipped[0][1]);
						}
						block_tris = newTris;
					}
					tris.addAll(block_tris);
				}
			}
		}
		//Render into display list
		int dl = GL11.glGenLists(1);
		GL11.glNewList(dl, GL11.GL_COMPILE);
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_NORMAL);
		for(Triangle t : tris){
			Vec3d norm = t.v2.pos.subtract(t.v1.pos).crossProduct(t.v3.pos.subtract(t.v1.pos)).normalize();
			Vector3f tex1 = new Vector3f((float)t.v1.pos.x, (float)t.v1.pos.y, (float)t.v1.pos.z);
			rot.transform(tex1);
			Vector3f tex2 = new Vector3f((float)t.v2.pos.x, (float)t.v2.pos.y, (float)t.v2.pos.z);
			rot.transform(tex2);
			Vector3f tex3 = new Vector3f((float)t.v3.pos.x, (float)t.v3.pos.y, (float)t.v3.pos.z);
			rot.transform(tex3);
			buf.pos(t.v1.pos.x, t.v1.pos.y, t.v1.pos.z).tex(tex1.x*0.5+0.5, tex1.y*0.5+0.5).normal((float)norm.x, (float)norm.y, (float)norm.z).endVertex();
			buf.pos(t.v2.pos.x, t.v2.pos.y, t.v2.pos.z).tex(tex2.x*0.5+0.5, tex2.y*0.5+0.5).normal((float)norm.x, (float)norm.y, (float)norm.z).endVertex();
			buf.pos(t.v3.pos.x, t.v3.pos.y, t.v3.pos.z).tex(tex3.x*0.5+0.5, tex3.y*0.5+0.5).normal((float)norm.x, (float)norm.y, (float)norm.z).endVertex();
		}
		tes.draw();
		GL11.glEndList();
		return dl;
	}
	
	public static AxisAlignedBB getBox(Vec3d a, Vec3d b, Vec3d c){
		double x = Math.max(Math.max(Math.abs(a.x), Math.abs(b.x)), Math.abs(c.x));
		double y = Math.max(Math.max(Math.abs(a.y), Math.abs(b.y)), Math.abs(c.y));
		double z = Math.max(Math.max(Math.abs(a.z), Math.abs(b.z)), Math.abs(c.z));
		
		return new AxisAlignedBB(-x, -y, -z, x, y, z);
	}
	
	public static Matrix3f normalToMat(Vec3d normal, float roll){
		Vec3d euler = BobMathUtil.getEulerAngles(normal);
		return eulerToMat((float)Math.toRadians(euler.x), (float)Math.toRadians(euler.y+90), roll);
	}
	
	public static Matrix3f eulerToMat(float yaw, float pitch, float roll){
		//Could this be more optimized? Yeah, but it's not like I'm calling it that often.
		Matrix3f mY = new Matrix3f();
		mY.rotY(-yaw);
		Matrix3f mP = new Matrix3f();
		mP.rotX(pitch);
		Matrix3f mR = new Matrix3f();
		mR.rotZ(roll);
		mR.mul(mP);
		mR.mul(mY);
		return mR;
	}
	
	public static List<Triangle> triangulateBlockModel(IBakedModel b, IBlockState state, long rand, float offsetX, float offsetY, float offsetZ){
		return triangulateBlockModel(null, null, b, state, rand, offsetX, offsetY, offsetZ, false);
	}
	
	public static List<Triangle> triangulateBlockModel(World world, BlockPos pos, IBakedModel b, IBlockState state, long rand, float offsetX, float offsetY, float offsetZ, boolean checkSides){
		List<Triangle> tris = new ArrayList<>();
		
		List<BakedQuad> l = new ArrayList<>();
		for(EnumFacing e : EnumFacing.VALUES){
			if(!checkSides || state.shouldSideBeRendered(world, pos, e)){
				l.addAll(b.getQuads(state, e, rand));
			}
		}
		l.addAll(b.getQuads(state, null, rand));
		
		for(BakedQuad quad : l){
			int[] vertexData = quad.getVertexData();
			Vertex v0 = new Vertex(vertexData, 0, offsetX, offsetY, offsetZ);
			Vertex v1 = new Vertex(vertexData, 1, offsetX, offsetY, offsetZ);
			Vertex v2 = new Vertex(vertexData, 2, offsetX, offsetY, offsetZ);
			Vertex v3 = new Vertex(vertexData, 3, offsetX, offsetY, offsetZ);
			tris.add(new Triangle(v0, v1, v2));
			tris.add(new Triangle(v2, v3, v0));
		}
		return tris;
	}
	
	public static Triangle[][] planeClipTriangle(Triangle t, float[] plane){
		Triangle[][] clipped = new Triangle[2][2];
		clipped[0] = new Triangle[]{null, null};
		clipped[1] = new Triangle[]{null, null};
		//Clip each triangle to the plane.
		//TODO move this to a generic helper method for clipping triangles?
		boolean p1 = t.v1.pos.x*plane[0]+t.v1.pos.y*plane[1]+t.v1.pos.z*plane[2]+plane[3] > 0;
		boolean p2 = t.v2.pos.x*plane[0]+t.v2.pos.y*plane[1]+t.v2.pos.z*plane[2]+plane[3] > 0;
		boolean p3 = t.v3.pos.x*plane[0]+t.v3.pos.y*plane[1]+t.v3.pos.z*plane[2]+plane[3] > 0;
		//If all points on positive side, add to side 1 
		if(p1 && p2 && p3){
			clipped[0][0] = t;
		//else if all on negative side, add to size 2
		} else if(!p1 && !p2 && !p3){
			clipped[1][0] = t;
		//else if only one is positive, clip and add 1 triangle to side 1, 2 to side 2
		} else if(p1 ^ p2 ^ p3){
			Vertex a, b, c;
			if(p1){
				a = t.v1;
				b = t.v2;
				c = t.v3;
			} else if(p2){
				a = t.v2;
				b = t.v3;
				c = t.v1;
			} else {
				a = t.v3;
				b = t.v1;
				c = t.v2;
			}
			Vec3d rAB = b.pos.subtract(a.pos);
			Vec3d rAC = c.pos.subtract(a.pos);
			float interceptAB = (float) rayPlaneIntercept(a.pos, rAB, plane);
			float interceptAC = (float) rayPlaneIntercept(a.pos, rAC, plane);
			Vertex d = a.lerp(b, interceptAB);
			Vertex e = a.lerp(c, interceptAC);
			
			clipped[1][0] = new Triangle(d, b, e);
			clipped[1][1] = new Triangle(b, c, e);
			clipped[0][0] = new Triangle(a, d, e);
			
		//else one is negative, clip and add 2 triangles to side 1, 1 to side 2.
		} else {
			Vertex a, b, c;
			if(!p1){
				a = t.v1;
				b = t.v2;
				c = t.v3;
			} else if(!p2){
				a = t.v2;
				b = t.v3;
				c = t.v1;
			} else {
				a = t.v3;
				b = t.v1;
				c = t.v2;
			}
			//Duplicated code. Eh, I don't feel like redesigning this.
			Vec3d rAB = b.pos.subtract(a.pos);
			Vec3d rAC = c.pos.subtract(a.pos);
			float interceptAB = (float) rayPlaneIntercept(a.pos, rAB, plane);
			float interceptAC = (float) rayPlaneIntercept(a.pos, rAC, plane);
			Vertex d = a.lerp(b, interceptAB);
			Vertex e = a.lerp(c, interceptAC);
			
			clipped[0][0] = new Triangle(d, b, e);
			clipped[0][1] = new Triangle(b, c, e);
			clipped[1][0] = new Triangle(a, d, e);
		}
		return clipped;
	}
	
	public static double rayPlaneIntercept(Vec3d start, Vec3d ray, float[] plane){
		double num = -(plane[0]*start.x + plane[1]*start.y + plane[2]*start.z + plane[3]);
		double denom = plane[0]*ray.x + plane[1]*ray.y + plane[2]*ray.z;
		return num/denom;
	}
	
	public static class Vertex {
		public Vec3d pos;
		public byte a, r, g, b;
		public float u, v;
		public int normal;
		
		public Vertex(int[] vertexData, int offset, float oX, float oY, float oZ) {
			offset *= 7;
			float x = Float.intBitsToFloat(vertexData[0+offset])+oX;
			float y = Float.intBitsToFloat(vertexData[1+offset])+oY;
			float z = Float.intBitsToFloat(vertexData[2+offset])+oZ;
			pos = new Vec3d(x, y, z);
			int color = vertexData[3+offset];
			a = (byte) ((color >> 24) & 255);
			r = (byte) ((color >> 16) & 255);
			g = (byte) ((color >> 8) & 255);
			b = (byte) ((color) & 255);
			u = Float.intBitsToFloat(vertexData[4+offset]);
			v = Float.intBitsToFloat(vertexData[5+offset]);
			normal = vertexData[6+offset];
		}
		
		public Vertex() {
		}
		
		public Vertex lerp(Vertex other, float amount){
			Vertex l = new Vertex();
			l.pos = pos.add(other.pos.subtract(pos).scale(amount));
			l.a = (byte) (a + (other.a-a)*amount);
			l.r = (byte) (r + (other.r-r)*amount);
			l.g = (byte) (g + (other.g-g)*amount);
			l.b = (byte) (b + (other.b-b)*amount);
			l.u = u + (other.u-u)*amount;
			l.v = v + (other.v-v)*amount;
			l.normal = normal;
			return l;
		}
	}
	
	public static class Triangle {
		
		public Vertex v1, v2, v3;
		
		public Triangle(Vertex v1, Vertex v2, Vertex v3) {
			this.v1 = v1;
			this.v2 = v2;
			this.v3 = v3;
		}
	}
}
