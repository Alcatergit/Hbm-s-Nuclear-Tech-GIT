package com.hbm.sound;

import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.leafia.contents.machines.processing.gascent.GasCentTE;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SoundLoopCentrifuge extends SoundLoopMachine {

	public static List<SoundLoopCentrifuge> list = new ArrayList<SoundLoopCentrifuge>();
	
	public SoundLoopCentrifuge(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
	}

	@Override
	public void update() {
		super.update();
		
		if(te instanceof TileEntityMachineCentrifuge) {
			TileEntityMachineCentrifuge plant = (TileEntityMachineCentrifuge)te;
			
			if(this.volume != 1)
				volume = 1;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
		
		if(te instanceof GasCentTE) {
			GasCentTE plant = (GasCentTE)te;
			
			if(this.volume != 1)
				volume = 1;
			
			if(!plant.isProgressing)
				this.donePlaying = true;
		}
		
		if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) {
			stop();
		}
	}
	
	public TileEntity getTE() {
		return te;
	}
	
}
