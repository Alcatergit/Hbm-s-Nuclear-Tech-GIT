package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundRocket extends MovingSound {

	public static List<MovingSoundRocket> globalSoundList = new ArrayList<MovingSoundRocket>();
	public Entity rocket;
	
	public MovingSoundRocket(SoundEvent soundIn, Entity rocket) {
		super(soundIn, SoundCategory.NEUTRAL);
		this.rocket = rocket;
		this.repeat = true;
		this.attenuationType = ISound.AttenuationType.NONE;
		globalSoundList.add(this);
	}

	@Override
	public void update() {
		if(this.rocket == null || this.rocket.isDead){
			this.stop();
		} else {
			this.xPosF = (float)rocket.posX;
			this.yPosF = (float)rocket.posY;
			this.zPosF = (float)rocket.posZ;
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			float dist = 0;
			float iVolume = 100;
			if(player != null) {
				dist = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
				volume = (dist / iVolume) * -2 + 2;
			} else {
				volume = iVolume;
			}
		}
	}

	public void stop() {
		this.donePlaying = true;
		this.repeat = false;
		globalSoundList.remove(this);
	}
}
