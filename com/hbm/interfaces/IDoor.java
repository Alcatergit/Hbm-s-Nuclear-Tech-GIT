package com.hbm.interfaces;

public interface IDoor {

	public void open();
	public void close();
	public DoorState getState();
	public void toggle();
	
	public enum DoorState {
		CLOSED,
		OPEN,
		CLOSING,
		OPENING;
	}
}
