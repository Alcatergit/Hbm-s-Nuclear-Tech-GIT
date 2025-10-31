package com.hbm.inventory.material;

import java.util.ArrayList;
import java.util.List;

public class MaterialShapes {
	
	public static final List<MaterialShapes> allShapes = new ArrayList();
	
	public static final MaterialShapes QUANTUM = new MaterialShapes(1); // 1/72 of an ingot, allows the ingot to be divisible through 2, 4, 6, 8, 9, 12, 24 and 36
	public static final MaterialShapes NUGGET = new MaterialShapes(8, "nugget");
	public static final MaterialShapes DUSTTINY = new MaterialShapes(NUGGET.quantity, "dustTiny");
	public static final MaterialShapes WIRE = new MaterialShapes(9, "wire");
	public static final MaterialShapes BOLT = new MaterialShapes(9, "bolt");
	public static final MaterialShapes BILLET = new MaterialShapes(NUGGET.quantity * 6, "billet");
	public static final MaterialShapes INGOT = new MaterialShapes(NUGGET.quantity * 9, "ingot");
	public static final MaterialShapes GEM = new MaterialShapes(INGOT.quantity, "gem");
	public static final MaterialShapes CRYSTAL = new MaterialShapes(INGOT.quantity, "crystal");
	public static final MaterialShapes DUST = new MaterialShapes(INGOT.quantity, "dust");
	public static final MaterialShapes DENSEWIRE = new MaterialShapes(INGOT.quantity, "wireDense");
	public static final MaterialShapes PLATE = new MaterialShapes(INGOT.quantity, "plate");
	public static final MaterialShapes CASTPLATE = new MaterialShapes(INGOT.quantity * 3, "plateTriple");
	public static final MaterialShapes WELDEDPLATE = new MaterialShapes(INGOT.quantity * 6, "plateSextuple");
	public static final MaterialShapes SHELL = new MaterialShapes(INGOT.quantity * 4, "shell");
	public static final MaterialShapes PIPE = new MaterialShapes(INGOT.quantity * 3, "pipe");
	public static final MaterialShapes QUART = new MaterialShapes(162);
	public static final MaterialShapes BLOCK = new MaterialShapes(INGOT.quantity * 9, "block");
	public static final MaterialShapes HEAVY_COMPONENT = new MaterialShapes(CASTPLATE.quantity * 256, "componentHeavy");
	
	
	private final int quantity;
	public final String[] prefixes;
	
	private MaterialShapes(int quantity, String... prefixes) {
		this.quantity = quantity;
		this.prefixes = prefixes;
		
		for(String prefix : prefixes) {
			Mats.prefixByName.put(prefix, this);
		}
		
		allShapes.add(this);
	}
	
	public int q(int amount) {
		return this.quantity * amount;
	}
	
	public int q(int unitsUsed, int itemsProduced) { //eg rails: INOGT.q(6, 16) since the recipe uses 6 iron ingots producing 16 individual rail blocks
		return (this.quantity * unitsUsed) / itemsProduced;
	}

	public String name() {
		return (prefixes != null && prefixes.length > 0) ? prefixes[0] : "unknown";
	}

	public String make(NTMMaterial mat) {
		return this.name() + mat.names[0];
	}
}
