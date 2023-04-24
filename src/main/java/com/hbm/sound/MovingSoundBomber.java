package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.EntityBomber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundBomber extends MovingSound {

	public static List<MovingSoundBomber> globalSoundList = new ArrayList<MovingSoundBomber>();
	public EntityBomber bomber;
	
	public MovingSoundBomber(SoundEvent soundIn, SoundCategory categoryIn, EntityBomber bomber) {
		super(soundIn, categoryIn);
		this.bomber = bomber;
		globalSoundList.add(this);
		this.repeat = true;
		this.attenuationType = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		
		if(this.bomber == null || this.bomber.isDead || this.bomber.health <= 0) {
			this.stop();
		} else {
			this.xPosF = (float)bomber.posX;
			this.yPosF = (float)bomber.posY;
			this.zPosF = (float)bomber.posZ;
			
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			float f = 0;
			float iVolume = 150;
			if(player != null) {
				f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
				volume = (f / iVolume) * -2 + 2;
			} else {
				volume = iVolume;
			}
		}
		
		if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) {
			stop();
		}
	}
	
	public void stop() {
		this.donePlaying = true;
		this.repeat = false;
		
		globalSoundList.remove(this);
	}
	
	public void setPitch(float f) {
		this.pitch = f;
	}
	
	public void setVolume(float f) {
		this.volume = f;
	}
	
	public void setDone(boolean b) {
		this.donePlaying = b;
	}
}
