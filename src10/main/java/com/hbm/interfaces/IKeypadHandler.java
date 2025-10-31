package com.hbm.interfaces;

import com.hbm.util.Keypad;

public interface IKeypadHandler {

	Keypad getKeypad();
	
	default void keypadActivated(){}

    default void passwordSet(){}
}
