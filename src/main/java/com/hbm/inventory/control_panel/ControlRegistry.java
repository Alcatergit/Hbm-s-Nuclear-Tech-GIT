package com.hbm.inventory.control_panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.inventory.control_panel.controls.*;

public class ControlRegistry {
	
	public static Map<String, Control> registry = new HashMap<>();
	private static Map<Class<? extends Control>, String> classToName = new HashMap<>();

	private ControlRegistry(){
	}
	
	public static void init(){
		registry.put("button_push", new ButtonPush("Push Button", null));
		registry.put("button_emergency_push", new ButtonEmergencyPush("Emergency Push Button", null));
		registry.put("button_encased_push", new ButtonEncasedPush("Encased Push Button", null));

		registry.put("switch_toggle", new SwitchToggle("Toggle Switch", null));
		registry.put("switch_rotary_toggle", new SwitchRotaryToggle("Rotary Toggle Switch", null));

		registry.put("slider_vertical", new SliderVertical("Vertical Slider", null));

		registry.put("knob_control", new KnobControl("Control Knob", null));

		registry.put("display_7seg", new DisplaySevenSeg("7-seg Display", null));
		registry.put("display_text", new DisplayText("Text Display", null));

		registry.put("dial_square", new DialSquare("Square Dial", null));
		registry.put("dial_large", new DialLarge("Large Dial", null));

		registry.put("indicator_lamp", new IndicatorLamp("Indicator Lamp", null));

		registry.put("label", new Label("Label", null));

		for(Entry<String, Control> e : registry.entrySet()){
			classToName.put(e.getValue().getClass(), e.getKey());
		}
	}
	
	public static List<Control> getAllControls(){
		List<Control> l = new ArrayList<>(registry.size());
		for(Control c : registry.values())
			l.add(c);
		return l;
	}

	public static List<String> getAllControlsOfType(ControlType type) {
		List<String> l = new ArrayList<>();
		for (Entry<String, Control> c : registry.entrySet())
			if (c.getValue().getControlType() == type) {
				l.add(c.getKey());
			}
		return l;
	}

	public static Control getNew(String name, ControlPanel panel){
		return registry.get(name).newControl(panel);
	}

	public static String getName(Class<? extends Control> clazz){
		return classToName.get(clazz);
	}
}
