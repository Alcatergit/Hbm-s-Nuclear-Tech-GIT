package com.hbm.interfaces;

import com.hbm.util.Keypad;

public interface IKeypadHandler {

	public Keypad getKeypad();
	
	public default void keypadActivated(){};
	
	public default void passwordSet(){};
}
