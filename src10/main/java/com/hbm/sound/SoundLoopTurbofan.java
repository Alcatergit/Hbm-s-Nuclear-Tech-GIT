package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopTurbofan extends SoundLoopMachine {
	
	public static List<SoundLoopTurbofan> list = new ArrayList<SoundLoopTurbofan>();

	public SoundLoopTurbofan(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineTurbofan) {
			TileEntityMachineTurbofan drill = (TileEntityMachineTurbofan)te;
			
			if(this.volume != 10)
				volume = 10;
			
			if(!drill.isRunning)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}
