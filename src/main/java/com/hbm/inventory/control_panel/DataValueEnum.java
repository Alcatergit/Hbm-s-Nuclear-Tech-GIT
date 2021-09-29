package com.hbm.inventory.control_panel;

public class DataValueEnum<T extends Enum<T>> extends DataValue {

	public Enum<T> value;
	public Class<T> enumClass;
	
	@SuppressWarnings("unchecked")
	public DataValueEnum(Enum<T> e){
		value = e;
		enumClass = (Class<T>)e.getClass();
	}
	
	@Override
	public float getNumber(){
		return value.ordinal();
	}

	@Override
	public boolean getBoolean(){
		return value.toString().toLowerCase().equals("true") ? true : false;
	}

	@Override
	public String toString(){
		return value.toString();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> E getEnum(Class<E> clazz){
		if(clazz == enumClass){
			return (E)value;
		} else {
			return clazz.getEnumConstants()[0];
		}
	}

	@Override
	public DataType getType(){
		return DataType.ENUM;
	}
	
	public T[] getPossibleValues(){
		return enumClass.getEnumConstants();
	}

}
