package com.hbm.inventory.control_panel.nodes.leafia;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.DataValue.DataType;
import com.hbm.inventory.control_panel.nodes.Node;
import net.minecraft.nbt.NBTTagCompound;

public class NodeSubString extends Node {
	public NodeSubString(float x,float y) {
		super(x,y);
		this.outputs.add(new NodeConnection("Output", this, outputs.size(), false, DataType.STRING, new DataValueString("")));
		this.inputs.add(new NodeConnection("Input", this, inputs.size(), true, DataType.STRING, new DataValueString("")));
		this.inputs.add(new NodeConnection("Start", this, inputs.size(), true, DataType.NUMBER, new DataValueFloat(1)));
		this.inputs.add(new NodeConnection("End", this, inputs.size(), true, DataType.NUMBER, new DataValueFloat(-1)));
		evalCache = new DataValue[1];
		recalcSize();
	}

	@Override
	public DataValue evaluate(int idx) {
		if (cacheValid)
			return evalCache[0];
		cacheValid = true;

		DataValue in = inputs.get(0).evaluate();
		DataValue start0 = inputs.get(1).evaluate();
		DataValue end0 = inputs.get(2).evaluate();
		if (in == null || start0 == null || end0 == null)
			return null;

		String s = in.toString();
		int start = (int)start0.getNumber();
		int end = (int)end0.getNumber();
		//start = Math.min(start,s.length());
		//end = Math.min(end,s.length());

		if (start < 0)
			start = s.length()-Math.abs(start);
		else
			start = Math.max(0,start-1);
		if (end < 0)
			end = s.length()-Math.abs(end)+1;
		try {
			s = s.substring(start,end);
		} catch (IndexOutOfBoundsException e) {
			s = "ERROR";
		}
		return evalCache[0] = new DataValueString(s);
	}

	@Override
	public NodeType getType() {
		return NodeType.STRING;
	}

	@Override
	public String getDisplayName() {
		return "Substring";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag,NodeSystem sys) {
		tag.setString("nodeType","subString");
		return super.writeToNBT(tag,sys);
	}
}
