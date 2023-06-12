package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityFEL;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopFel extends SoundLoopMachine {
	
	public static List<SoundLoopFel> list = new ArrayList<SoundLoopFel>();

	public SoundLoopFel(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityFEL) {
			TileEntityFEL plant = (TileEntityFEL)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(!plant.isOn)
				this.donePlaying = true;
		}

		if(te instanceof TileEntityMachineMiningLaser) {
			TileEntityMachineMiningLaser plant = (TileEntityMachineMiningLaser)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(!plant.isOn)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}