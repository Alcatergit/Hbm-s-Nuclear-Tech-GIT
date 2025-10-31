package com.leafia.contents.machines.elevators.car.styles.panels;

public class S6Door extends EvGenericDoorBase {
	public S6Door(int rotation) {
		super(rotation);
	}
	@Override
	public String getId() {
		return "s6door";
	}
	@Override
	public int getStaticX() {
		return -11;
	}
}
