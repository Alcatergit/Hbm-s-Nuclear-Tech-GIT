package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopCentrifuge extends SoundLoopMachine {

	public static List<SoundLoopCentrifuge> list = new ArrayList<SoundLoopCentrifuge>();
	
	public SoundLoopCentrifuge(SoundEvent path, TileEntity te) {
		super(path, te, 1);
		list.add(this);
	}

    public static boolean isProcessing(TileEntity te){
        boolean shouldPlay = false;
        if(te instanceof TileEntityMachineCentrifuge plant) {
            shouldPlay = plant.isProgressing;
        } else if(te instanceof TileEntityMachineGasCent gasCent) {
            shouldPlay = gasCent.isProgressing && !gasCent.hasMuffler();
        }
        return shouldPlay;
    }

    @Override
    public boolean isThisProcessing(){
        return isProcessing(te);
    }
}
