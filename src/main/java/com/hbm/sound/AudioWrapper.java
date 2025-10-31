package com.hbm.sound;

import java.util.function.BiFunction;

public class AudioWrapper {
	
	public AudioWrapper updatePosition(float x, float y, float z) { return this; }
	
	public AudioWrapper updateVolume(float volume) { return this; }
	
	public AudioWrapper updatePitch(float pitch) { return this; }
	
	public float getVolume() { return 0F; }
	
	public float getPitch() { return 0F; }
	
	public AudioWrapper startSound() { return this; }
	
	public AudioWrapper stopSound() { return this; }

	public AudioWrapper setCustomAttentuation(BiFunction<Float,Double,Double> attentuationFunction) { return this; }

	public AudioWrapper setLooped(boolean looped) { return this; }
}