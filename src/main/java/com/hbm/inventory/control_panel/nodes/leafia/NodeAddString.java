package com.hbm.inventory.control_panel.nodes.leafia;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.DataValue.DataType;
import com.hbm.inventory.control_panel.nodes.Node;
import net.minecraft.nbt.NBTTagCompound;

public class NodeAddString extends Node {
	public NodeAddString(float x,float y) {
		super(x,y);
		this.outputs.add(new NodeConnection("Output", this, outputs.size(), false, DataType.STRING, new DataValueString("")));
		this.inputs.add(new NodeConnection("Input 1", this, inputs.size(), true, DataType.STRING, new DataValueString("")));
		this.inputs.add(new NodeConnection("Input 2", this, inputs.size(), true, DataType.STRING, new DataValueString("")));
		evalCache = new DataValue[1];
		recalcSize();
	}

	@Override
	public DataValue evaluate(int idx) {
		if (cacheValid)
			return evalCache[0];
		cacheValid = true;

		DataValue in1 = inputs.get(0).evaluate();
		DataValue in2 = inputs.get(1).evaluate();
		if (in1 == null || in2 == null)
			return null;

		return evalCache[0] = new DataValueString(in1.toString()+in2.toString());
	}

	@Override
	public NodeType getType() {
		return NodeType.STRING;
	}

	@Override
	public String getDisplayName() {
		return "String Add";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag,NodeSystem sys) {
		tag.setString("nodeType","addString");
		return super.writeToNBT(tag,sys);
	}
}
