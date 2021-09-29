package com.hbm.inventory.control_panel.nodes;

import java.util.List;
import java.util.Map;

import com.hbm.inventory.control_panel.DataValue;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.inventory.control_panel.NodeSystem;

import net.minecraft.util.math.BlockPos;

public abstract class NodeOutput extends Node {

	public NodeOutput(float x, float y){
		super(x, y);
	}
	
	@Override
	public DataValue evaluate(int idx){
		//Output nodes don't need this
		return null;
	}

	public abstract boolean doOutput(IControllable from, Map<String, NodeSystem> sendNodeMap, List<BlockPos> positions);
}
