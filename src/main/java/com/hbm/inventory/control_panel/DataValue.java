package com.hbm.inventory.control_panel;

public abstract class DataValue {
	public abstract float getNumber();
	public abstract boolean getBoolean();
	public abstract String toString();
	public abstract DataType getType();
	public abstract <E extends Enum<E>> E getEnum(Class<E> clazz);
	
	public static enum DataType {
		GENERIC(new float[]{0.5F, 0.5F, 0.5F}),
		NUMBER(new float[]{0.4F, 0.6F, 0}),
		STRING(new float[]{0, 1, 1}),
		ENUM(new float[]{0.29F, 0, 0.5F});
		
		private float[] color;
		
		private DataType(float[] color){
			this.color = color;
		}
		
		public float[] getColor(){
			return color;
		}
	}
}
