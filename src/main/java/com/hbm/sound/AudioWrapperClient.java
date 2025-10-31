package com.hbm.sound;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.BiFunction;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {

	AudioDynamic sound;
	
	public AudioWrapperClient(SoundEvent source, SoundCategory cat) {
		if(source != null)
			sound = new AudioDynamic(source, cat);
	}
	
	public AudioWrapperClient updatePosition(float x, float y, float z) {
		if(sound != null)
			sound.setPosition(x, y, z);
		return this;
	}
	
	public AudioWrapperClient updateVolume(float volume) {
		if(sound != null)
			sound.setVolume(volume);
		return this;
	}
	
	public AudioWrapperClient updatePitch(float pitch) {
		if(sound != null)
			sound.setPitch(pitch);
		return this;
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
	
	public AudioWrapperClient startSound() {
		if(sound != null)
			sound.start();
		return this;
	}
	
	public AudioWrapperClient stopSound() {
		if(sound != null)
			sound.stop();
		return this;
	}

	public AudioWrapperClient setLooped(boolean looped) {
		sound.setLooped(looped);
		return this;
	}

	@Override
	public AudioWrapperClient setCustomAttentuation(BiFunction<Float,Double,Double> attentuationFunction) {
		sound.setCustomAttentuation(attentuationFunction);
		return this;
	}
}
