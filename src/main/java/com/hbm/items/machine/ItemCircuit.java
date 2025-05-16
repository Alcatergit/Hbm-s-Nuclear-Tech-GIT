package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemCircuit extends ItemEnumMulti {

	public ItemCircuit(String s) {
		super(EnumCircuitType.class, s);
	}

	public static enum EnumCircuitType {
		VACUUM_TUBE,
		CAPACITOR,
		CAPACITOR_TANTALIUM,
		ATOMIC_CLOCK,
		PCB,
		CRT_TUBE,
		SILICON,
		CHIP,
		CHIP_BISMOID,
		CHIP_QUANTUM,
		ANALOG,
		BASIC,
		CAPACITOR_BOARD,
		ADVANCED,
		BISMOID,
		QUANTUM,
		CONTROLLER_CHASSIS,
		CONTROLLER,
		CONTROLLER_ADVANCED,
		CONTROLLER_QUANTUM,

	}
}
