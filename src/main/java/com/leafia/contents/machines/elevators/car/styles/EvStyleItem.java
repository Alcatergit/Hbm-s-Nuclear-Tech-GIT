package com.leafia.contents.machines.elevators.car.styles;

import com.hbm.items.ModItems.ElevatorStyles;
import com.hbm.items.special.ItemCustomLore;

public class EvStyleItem extends ItemCustomLore {
	final String style;
	public EvStyleItem(String s) {
		super(s);
		ElevatorStyles.styleItems.add(this);
		style = s.substring(3);
		this.setMaxStackSize(1);
	}
	public String getStyleId() {
		return style;
	}
}
