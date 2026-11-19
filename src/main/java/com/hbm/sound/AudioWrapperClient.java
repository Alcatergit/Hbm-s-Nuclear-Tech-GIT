package com.hbm.sound;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {

	AudioDynamic sound;
	
	public AudioWrapperClient(SoundEvent source, SoundCategory cat) {
		if(source != null)
			sound = new AudioDynamic(source, cat);
	}

	public AudioWrapperClient(SoundEvent source, SoundCategory cat, boolean useNewSystem, float maxVolume, float range, float intendedVolume) {
		if(source != null)
			sound = new AudioDynamic(source, cat, useNewSystem, maxVolume, range, intendedVolume);
	}
	
	public void updatePosition(float x, float y, float z) {
		if(sound != null)
			sound.setPosition(x, y, z);
	}
	
	public void updateVolume(float volume) {
		if(sound != null)
			sound.setVolume(volume);
	}

	public void updateRange(float range) {
		if(sound != null)
			sound.setRange(range);
	}
	
	public void updatePitch(float pitch) {
		if(sound != null)
			sound.setPitch(pitch);
	}

	public void setKeepAlive(int keepAlive) {
		if(sound != null)
			sound.setKeepAlive(keepAlive);
	}

	@Override
	public void keepAlive() {
		if(sound != null)
			sound.keepAlive();
	}
	
	public float getVolume() {
		if(sound != null)
			return sound.getVolume();
		else
			return 1;
	}
	
	public float getPitch() {
		if(sound != null)
			return sound.getPitch();
		else
			return 1;
	}

	public float getRange() {
		if(sound != null)
			return sound.range;
		else
			return 0;
	}
	
	public void startSound() {
		if(sound != null)
			sound.start();
	}
	
	public void stopSound() {
		if(sound != null) {
			sound.stop();
			sound.setKeepAlive(0);
		}
	}

	public boolean isPlaying() {
		if(sound != null)
			return sound.isPlaying();
		return false;
	}
}
