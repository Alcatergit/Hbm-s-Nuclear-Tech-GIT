package com.hbm.interfaces;

import java.util.List;

import net.minecraft.util.math.BlockPos;

public interface ISource {

	void ffgeuaInit();

	void ffgeua(BlockPos pos, boolean newTact);

	boolean getTact();
	long getSPower();
	void setSPower(long i);
	List<IConsumer> getList();
	void clearList();

	
}
