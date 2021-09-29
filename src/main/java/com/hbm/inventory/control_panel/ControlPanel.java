package com.hbm.inventory.control_panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlPanel {

	public IControllable parent;
	public List<Control> controls = new ArrayList<>();
	//Global variables, accessible by every control as well as the owner tile entity for state like redstone
	public Map<String, DataValue> globalVars = new HashMap<>();
	//Client only transform for getting from panel local space to block space. Used for both rendering and figuring out which control a player clicks
	@SideOnly(Side.CLIENT)
	public Matrix4f transform;
	@SideOnly(Side.CLIENT)
	public Matrix4f inv_transform;
	public float width;
	public float height;
	
	public ControlPanel(IControllable parent, float width, float height){
		this.parent = parent;
		this.width = width;
		this.height = height;
	}
	
	@SideOnly(Side.CLIENT)
	public ControlPanel setTransform(Matrix4f transform){
		this.transform = transform;
		inv_transform = new Matrix4f();
		Matrix4f.invert(transform, inv_transform);
		return this;
	}
	
	public void render(){
		for(Control c : controls){
			c.render();
		}
	}
	
	public void update(){
		
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		
		return nbt;
	}
	
	public void readFromNBT(NBTTagCompound tag){
		
	}
	
	public void receiveEvent(BlockPos from, ControlEvent evt){
		for(Control c : controls){
			int idx = c.connectedSet.indexOf(from);
			if(idx != -1 || parent.getPos().equals(from)){
				evt.setVar("from index", idx);
				c.receiveEvent(evt);
			}
		}
	}
	
	public float[] getBox(){
		float wHalf = width*0.5F;
		float hHalf = height*0.5F;
		return new float[]{-wHalf, -hHalf, wHalf, hHalf};
	}

	public DataValue getVar(String name){
		return globalVars.getOrDefault(name, new DataValueFloat(0));
	}
	
	@SideOnly(Side.CLIENT)
	public Control getSelectedControl(Vec3d pos, Vec3d direction){
		BlockPos blockPos = parent.getPos();
		Vector4f localPos = new Vector4f((float)pos.x-blockPos.getX(), (float)pos.y-blockPos.getY(), (float)pos.z-blockPos.getZ(), 1);
		Vector4f localDir = new Vector4f((float)direction.x, (float)direction.y, (float)direction.z, 0);
		
		Matrix4f.transform(inv_transform, localPos, localPos);
		Matrix4f.transform(inv_transform, localDir, localDir);
		Vec3d start = new Vec3d(localPos.x, localPos.y, localPos.z);
		Vec3d end = new Vec3d(localPos.x+localDir.x, localPos.y+localDir.y, localPos.z+localDir.z);
		double dist = Double.MAX_VALUE;
		Control ctrl = null;
		for(Control c : controls){
			RayTraceResult r = c.getBoundingBox().calculateIntercept(start, end);
			double newDist = r.hitVec.squareDistanceTo(start);
			if(r != null && r.typeOfHit != Type.MISS && newDist < dist){
				dist = newDist;
				ctrl = c;
			}
		}
		return ctrl;
	}
}
