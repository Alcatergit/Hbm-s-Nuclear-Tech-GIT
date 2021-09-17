package com.hbm.inventory.control_panel;

import java.util.List;
import java.util.Map;

import com.hbm.inventory.control_panel.DataValue.DataType;

import net.minecraft.util.math.BlockPos;

public class NodeCancelEvent extends NodeOutput {

	public NodeCancelEvent(float x, float y){
		super(x, y);
		this.inputs.add(new NodeConnection("do cancel", this, 0, true, DataType.NUMBER, new DataValueFloat(0)));
		recalcSize();
	}

	@Override
	public boolean doOutput(IControllable from, Map<String, NodeSystem> sendNodeMap, List<BlockPos> positions){
		return !inputs.get(0).evaluate().getBoolean();
	}

	@Override
	public NodeType getType(){
		return NodeType.OUTPUT;
	}

	@Override
	public String getDisplayName(){
		return "Cancel Event";
	}

}
