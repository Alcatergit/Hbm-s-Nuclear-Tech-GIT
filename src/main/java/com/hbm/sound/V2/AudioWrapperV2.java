package com.hbm.sound.V2;

public class AudioWrapperV2 {

	public void setKeepAlive(int keepAlive) { }
	public void keepAlive() { }

	public void updatePosition(float x, float y, float z) { }

	public void updateVolume(float volume) { }
	public void updateRange(float range) { }

	public float getVolume() { return 0F; }
	public float getRange() { return 0F; }

	public float getPitch() { return 0F; }

	public void startSound() { }

	public void stopSound() { }

	public boolean isPlaying() { return false; }
}
