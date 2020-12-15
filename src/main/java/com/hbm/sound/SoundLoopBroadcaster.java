package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityBroadcaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundLoopBroadcaster extends SoundLoopMachine {
	
	public static List<SoundLoopBroadcaster> list = new ArrayList<SoundLoopBroadcaster>();
	public float intendedVolume = 25.0F;

	public SoundLoopBroadcaster(SoundEvent path, TileEntity te) {
		super(path, te);
		list.add(this);
		this.attenuationType = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		super.update();
		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		float f = 0;
		
		if(player != null) {
			f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
			volume = func(f, intendedVolume);
			
			if(!(player.world.getTileEntity(new BlockPos((int)xPosF, (int)yPosF, (int)zPosF)) instanceof TileEntityBroadcaster)) {
				this.donePlaying = true;
				volume = 0;
			}
		} else {
			volume = intendedVolume;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}

}