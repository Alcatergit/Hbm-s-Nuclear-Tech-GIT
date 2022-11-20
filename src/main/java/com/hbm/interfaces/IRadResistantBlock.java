package com.hbm.interfaces;

public interface IRadResistantBlock {

	//Anything implementing this must override onBlockAdded and breakBlock and call RadiationSystemNT.markChunkForRebuild or it won't work
	
	public default boolean isRadResistant(){
		return true;
	}
}
