package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.hbm.particle.lightning_test.TrailRenderer2;
import com.hbm.particle.lightning_test.TrailRenderer2.IColorGetter;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightningGenerator {

	private static Random rand = new Random();
	
	public static LightningNode generateLightning(Vec3d from, Vec3d to, LightningGenInfo info){
		rand.setSeed(Minecraft.getMinecraft().world.getTotalWorldTime());
		LightningNode lfrom = new LightningNode(from);
		LightningNode lto = new LightningNode(to);
		lfrom.children.add(lto);
		lto.parent = lfrom;
		generateLightning(lfrom, info);
		return lfrom;
	}
	
	public static void generateLightning(LightningNode node, LightningGenInfo info){
		Vec3d from = node.pos;
		Vec3d to = node.children.get(0).pos;
		subdivide(node, info.subdivisions, info.subdivMult, info.subdivRecurse-1, info.randAmount, info.randAmountSubdivMultiplier);
		LightningNode child = node.children.get(0);
		float value = 0;
		while(child.children.size() > 0){
			value += 0.02F;
			LightningNode next = child.children.get(0);
			if(rand.nextFloat() < info.forkChance-value){
				Vec3d randVec = BobMathUtil.randVecInCone(to.subtract(from).normalize(), info.forkConeDegrees, rand);
				LightningNode fork1 = new LightningNode(child.pos);
				float len = 1+rand.nextFloat()*info.forkLengthRandom;
				LightningNode fork2 = new LightningNode(child.pos.add(randVec.scale(len*from.subtract(to).length()*0.25F)));
				fork1.children.add(fork2);
				fork2.parent = fork1;
				subdivide(fork1, (int) (len*0.75*info.forkSubdivisions), info.forkSubdivMult, info.forkSubdivRecurse, info.forkRandAmount*info.randAmount*rand.nextFloat()*0.8F, info.forkRandAmountSubdivMultiplier);
				child.children.add(fork1);
			}
			child = next;
		}
	}
	
	public static void subdivide(LightningNode n, int subdivisions, float subdivMult, int recurse, float randAmount, float randAmountSubdivMultiplier){
		LightningNode parent = n;
		LightningNode child = n.children.get(0);
		float subdivision = 1F/(float)(subdivisions+1);
		for(int i = 1; i <= subdivisions; i ++){
			Vec3d newPos = BobMathUtil.mix(n.pos, child.pos, subdivision*i).add((rand.nextFloat()*2-1)*randAmount, (rand.nextFloat()*2-1)*randAmount, (rand.nextFloat()*2-1)*randAmount);
			LightningNode insert = new LightningNode(newPos);
			insert.parent = parent;
			insert.children.add(child);
			parent.children.set(0, insert);
			child.parent = insert;
			parent = insert;
		}
		if(recurse <= 0)
			return;
		child = n;
		while(child.children.size() > 0){
			LightningNode next = child.children.get(0);
			subdivide(child, (int)(subdivisions*subdivMult), subdivMult, recurse-1, randAmount*randAmountSubdivMultiplier, randAmountSubdivMultiplier);
			child = next;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void render(LightningNode n, Vec3d playerPos, float scale){
		render(n, playerPos, scale, 0, 0, 0, false, null);
	}
	
	@SideOnly(Side.CLIENT)
	public static void render(LightningNode n, Vec3d playerPos, float scale, float x, float y, float z, boolean fadeEnd, @Nullable IColorGetter c){
		List<Vec3d> toRender = new ArrayList<>();
		toRender.add(n.pos.add(x, y, z));
		while(n.children.size() > 0){
			//Render forks
			for(int i = 1; i < n.children.size(); i ++){
				render(n.children.get(i), playerPos, scale*0.5F, x, y, z, fadeEnd, c);
			}
			n = n.children.get(0);
			toRender.add(n.pos.add(x, y, z));
		}
		TrailRenderer2.draw(playerPos, toRender, scale, fadeEnd, true, c);
	}
	
	public static class LightningNode {
		public LightningNode parent = null;
		public List<LightningNode> children = new ArrayList<>(1);
		public Vec3d pos;
		
		public LightningNode(Vec3d pos) {
			this.pos = pos;
		}
	}
	
	public static class LightningGenInfo {
		public int subdivisions = 4;
		public int subdivRecurse = 2;
		public float randAmount = 0.2F;
		public float forkChance = 0.1F;
		public float forkSubdivMult = 1F;
		public float forkSubdivisions = 1;
		public int forkSubdivRecurse = 1;
		public float forkLengthRandom = 4;
		public float forkRandAmount = 0.2F;
		public float forkRandAmountSubdivMultiplier = 0.25F;
		public float randAmountSubdivMultiplier = 0.25F;
		public float forkConeDegrees = 25;
		public float subdivMult = 1.5F;
	}
}
