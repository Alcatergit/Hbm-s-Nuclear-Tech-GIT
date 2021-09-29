package com.hbm.inventory.control_panel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ControlEvent {

	private static final HashMap<String, ControlEvent> REGISTRY = new HashMap<>();
	
	public String name;
	public Map<String, DataValue> vars = new HashMap<>();
	
	public ControlEvent(String name){
		this.name = name;
	}
	
	public ControlEvent setVar(String key, DataValue val){
		vars.put(key, val);
		return this;
	}
	
	public ControlEvent setVar(String key, float f){
		vars.put(key, new DataValueFloat(f));
		return this;
	}
	
	public ControlEvent setVar(String key, boolean b){
		vars.put(key, new DataValueFloat(b ? 1 : 0));
		return this;
	}
	
	public ControlEvent setVar(String key, String str){
		vars.put(key, new DataValueString(str));
		return this;
	}
	
	public <E extends Enum<E>> ControlEvent setVar(String key, E enm){
		vars.put(key, new DataValueEnum<E>(enm));
		return this;
	}
	
	public ControlEvent copy(){
		ControlEvent evt = new ControlEvent(name);
		//Set default values
		for(Entry<String, DataValue> def : vars.entrySet()){
			evt.vars.put(def.getKey(), def.getValue());
		}
		return evt;
	}
	
	public static ControlEvent newEvent(String name){
		return getRegisteredEvent(name).copy();
	}
	
	public static ControlEvent getRegisteredEvent(String name){
		ControlEvent e = REGISTRY.get(name);
		if(e == null)
			throw new RuntimeException("Unregistered control event: " + name);
		return e;
	}
	
	public static void register(ControlEvent c){
		REGISTRY.put(c.name, c);
	}
	
	public static void init(){
		register(new ControlEvent("tick").setVar("time", 0));
		register(new ControlEvent("door_open_state").setVar("state", 0));
		register(new ControlEvent("door_toggle"));
		register(new ControlEvent("ctrl_button_press"));
		register(new ControlEvent("initialize"));
	}
}
