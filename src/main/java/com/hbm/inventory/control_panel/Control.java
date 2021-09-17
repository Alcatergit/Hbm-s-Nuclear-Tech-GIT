package com.hbm.inventory.control_panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class Control {

	public String name;
	public ControlPanel panel;
	//Set of block positions this control is connected to. When an event is sent, it gets sent to each one
	public List<BlockPos> connectedSet = new ArrayList<>();
	//A map of event names to node system for events this control is sending out to connected blocks
	public Map<String, NodeSystem> sendNodeMap = new HashMap<>();
	//A map of event names to node systems for events this control is receiving
	public Map<String, NodeSystem> receiveNodeMap = new HashMap<>();
	//A map of all variables, either used internally by the control or in the node systems
	public Map<String, DataValue> vars = new HashMap<>();
	//A set of the custom variables the user is allowed to remove
	public Set<String> customVarNames = new HashSet<>();
	public float posX;
	public float posY;
	
	public Control(String name, ControlPanel panel){
		this.name = name;
		this.panel = panel;
	}
	
	public void renderBatched(){};
	public void render(){};
	public List<String> getOutEvents(){return Collections.emptyList();};
	public List<String> getInEvents(){return Arrays.asList("tick", "initialize");};
	@SideOnly(Side.CLIENT)
	public abstract IModelCustom getModel();
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getGuiTexture();
	public abstract AxisAlignedBB getBoundingBox();
	public abstract float[] getBox();
	public abstract Control newControl(ControlPanel panel);

	public void receiveEvent(ControlEvent evt){
		NodeSystem sys = receiveNodeMap.get(evt.name);
		if(sys != null){
			sys.receiveEvent(panel, this, evt);
		}
	}
	
	public DataValue getVar(String name){
		return vars.getOrDefault(name, new DataValueFloat(0));
	}
	
	public DataValue getGlobalVar(String name){
		return panel.getVar(name);
	}
}
