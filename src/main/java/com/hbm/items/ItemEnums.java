package com.hbm.items;


public class ItemEnums {

	public static interface IEnumFurnaceFuel {
		int getBurnTime();
	}

	public static enum EnumCokeType implements IEnumFurnaceFuel {
		COAL(1200),
		LIGNITE(1000),
		PETROLEUM(1400);


		final int burntime;
		EnumCokeType(int burntime){
			this.burntime = burntime;
		}
		@Override
		public int getBurnTime() {
			return burntime;
		}
	}

	public static enum EnumTarType implements IEnumFurnaceFuel {
		CRUDE(800),
		CRACK(1200),
		COAL(1000),
		WOOD(500),
		WAX(1000),
		PARAFFIN(2000);


		final int burntime;
		EnumTarType(int burntime){
			this.burntime = burntime;
		}
		@Override
		public int getBurnTime() {
			return burntime;
		}
	}
}
