package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemChemicalDye extends ItemEnumMulti {

	public ItemChemicalDye(String s) {
		super(EnumChemDye.class, s);
	}

	
	public enum EnumChemDye {
		BLACK(1973019, "Black"),
		RED(11743532, "Red"),
		GREEN(3887386, "Green"),
		BROWN(5320730, "Brown"),
		BLUE(2437522, "Blue"),
		PURPLE(8073150, "Purple"),
		CYAN(2651799, "Cyan"),
		SILVER(11250603, "LightGray"),
		GRAY(4408131, "Gray"),
		PINK(14188952, "Pink"),
		LIME(4312372, "Lime"),
		YELLOW(14602026, "Yellow"),
		LIGHTBLUE(6719955, "LightBlue"),
		MAGENTA(12801229, "Magenta"),
		ORANGE(15435844, "Orange"),
		WHITE(15790320, "White");
		
		public final int color;
		public final String dictName;

		EnumChemDye(int color, String name) {
			this.color = color;
			this.dictName = "dye"+name;
		}
	}
}
