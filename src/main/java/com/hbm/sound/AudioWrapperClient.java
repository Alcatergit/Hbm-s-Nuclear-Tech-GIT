package com.hbm.sound;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AudioWrapperClient extends AudioWrapper {

	AudioDynamic sound;
	
	public AudioWrapperClient(SoundEvent source, SoundCategory cat) {
		sound = new AudioDynamic(source, cat);
	}
	
	public void updatePosition(float x, float y, float z) {
		sound.setPosition(x, y, z);
	}
	
	public void updateVolume(float volume) {
		sound.setVolume(volume);
	}
	
	public void updatePitch(float pitch) {
		sound.setPitch(pitch);
	}
	
	public float getVolume() {
		return sound.getVolume();
	}
	
	public float getPitch() {
		return sound.getPitch();
	}
	
	public void startSound() {
		sound.start();
	}
	
	public void stopSound() {
		sound.stop();
	}
}
