package com.hbm.inventory.control_panel;

public class DataValueString extends DataValue {

	public final String str;
	private final float floatVal;
	
	public DataValueString(String s){
		this.str = s;
		float num = 0;
		try {
			num = Float.parseFloat(str);
		} catch(Exception x){
		}
		floatVal = num;
	}

	@Override
	public float getNumber(){
		return floatVal;
	}

	@Override
	public boolean getBoolean(){
		if(str.equals("true"))
			return true;
		return false;
	}

	@Override
	public String toString(){
		return str;
	}

	@Override
	public <E extends Enum<E>> E getEnum(Class<E> clazz){
		E[] enms = clazz.getEnumConstants();
		for(E enm : enms){
			if(enm.name().equalsIgnoreCase(str))
				return enm;
		}
		return enms[0];
	}
	
	@Override
	public DataType getType(){
		return DataType.STRING;
	}

}
