package com.hbm.inventory.control_panel;

import java.util.Map;
import java.util.Map.Entry;

public class InputNode extends Node {

	public String name;
	
	public InputNode(float x, float y, String name){
		super(x, y);
		this.name = name;
	}
	
	@Override
	public DataValue evaluate(int idx){
		return outputs.get(idx).defaultValue;
	}

	@Override
	public NodeType getType(){
		return NodeType.INPUT;
	}
	
	@Override
	public String getDisplayName(){
		return name;
	}
	
	public InputNode setVars(Map<String, DataValue> vars){
		outputs.clear();
		for(Entry<String, DataValue> e : vars.entrySet()){
			NodeConnection c = new NodeConnection(e.getKey(), this, outputs.size(), false, e.getValue().getType(), e.getValue());
			outputs.add(c);
		}
		this.recalcSize();
		return this;
	}
	
	public InputNode setOutputFromVars(Map<String, DataValue> vars){
		for(NodeConnection c : outputs){
			c.setDefault(vars.get(c.name));
		}
		return this;
	}
}
