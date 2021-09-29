package com.hbm.inventory.control_panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.inventory.control_panel.controls.Button;

public class ControlRegistry {
	
	private static Map<String, Control> registry = new HashMap<>();
	
	private ControlRegistry(){
	}
	
	public static void init(){
		registry.put("button", new Button("Button", null));
	}
	
	public static List<Control> getAllControls(){
		List<Control> l = new ArrayList<>(registry.size());
		for(Control c : registry.values())
			l.add(c);
		return l;
	}

	public static Control getNew(String name, ControlPanel panel){
		return registry.get(name).newControl(panel);
	}
}
