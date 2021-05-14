package com.hbm.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundLoopGunEgonFire extends MovingSound {

	public EntityPlayer player;
	
	public SoundLoopGunEgonFire(SoundEvent soundIn, SoundCategory categoryIn, EntityPlayer p) {
		super(soundIn, categoryIn);
		this.repeat = true;
		this.repeatDelay = 0;
		this.player = p;
	}

	@Override
	public void update() {
		this.xPosF = (float) player.posX;
		this.yPosF = (float) player.posY;
		this.zPosF = (float) player.posZ;
	}
	
	public void setDone(boolean b) {
		this.donePlaying = b;
	}

}
