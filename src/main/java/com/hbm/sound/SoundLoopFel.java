package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityFEL;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopFel extends SoundLoopMachine {
	
	public static List<SoundLoopFel> list = new ArrayList<SoundLoopFel>();

	public SoundLoopFel(SoundEvent path, TileEntity te) {
		super(path, te, 3);
		list.add(this);
	}

    public static boolean isProcessing(TileEntity te){
        boolean shouldPlay = false;
        if(te instanceof TileEntityFEL plant) {
            shouldPlay = plant.isOn;
        } else if(te instanceof TileEntityMachineMiningLaser plant) {
            shouldPlay = plant.isOn;
        }
        return shouldPlay;
    }

    @Override
    public boolean isThisProcessing(){
        return isProcessing(te);
    }
}