package com.hbm.inventory.control_panel.nodes.leafia;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.DataValue.DataType;
import com.hbm.inventory.control_panel.nodes.Node;
import com.llib.math.SIPfx;
import net.minecraft.nbt.NBTTagCompound;

public class NodeSIPfx extends Node {
	public NodeSIPfx(float x,float y) {
		super(x,y);
		this.outputs.add(new NodeConnection("Shortened", this, outputs.size(), false, DataType.STRING, new DataValueString("")));
		this.inputs.add(new NodeConnection("Input", this, inputs.size(), true, DataType.NUMBER, new DataValueFloat(0)));
		this.inputs.add(new NodeConnection("Show Full", this, inputs.size(), true, DataType.NUMBER, new DataValueFloat(0)));
		evalCache = new DataValue[1];
		recalcSize();
	}

	@Override
	public DataValue evaluate(int idx) {
		if (cacheValid)
			return evalCache[0];
		cacheValid = true;

		DataValue in = inputs.get(0).evaluate();
		DataValue full = inputs.get(1).evaluate();
		if (in == null || full == null)
			return null;

		return evalCache[0] = new DataValueString(SIPfx.auto(in.getNumber(),full.getBoolean()));
	}

	@Override
	public NodeType getType() {
		return NodeType.STRING;
	}

	@Override
	public String getDisplayName() {
		return "SIPfx";
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag,NodeSystem sys) {
		tag.setString("nodeType","sipfx");
		return super.writeToNBT(tag,sys);
	}
}
