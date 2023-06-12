package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopChemplant extends SoundLoopMachine {
	
	public static List<SoundLoopChemplant> list = new ArrayList<SoundLoopChemplant>();

	public SoundLoopChemplant(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineChemplant) {
			TileEntityMachineChemplant plant = (TileEntityMachineChemplant)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}

		if(te instanceof TileEntityMachineChemfac) {
			TileEntityMachineChemfac plant = (TileEntityMachineChemfac)te;
			
			if(this.volume != 3)
				volume = 3;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}

}