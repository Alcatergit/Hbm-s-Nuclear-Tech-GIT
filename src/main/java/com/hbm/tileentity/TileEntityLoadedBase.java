package com.hbm.tileentity;

import api.hbm.energy.ILoadedTile;
import com.hbm.sound.V2.AudioWrapperV2;
import net.minecraft.tileentity.TileEntity;

public class TileEntityLoadedBase extends TileEntity implements ILoadedTile {
	
	public boolean isLoaded = false;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}

    @Override
    public void onLoad() {
        super.onLoad();
        this.isLoaded = true;
    }

	public AudioWrapperV2 createAudioLoopV2() { return null; } //Vidarin: Remember to override this if you use rebootAudio!!

	public AudioWrapperV2 rebootAudio(AudioWrapperV2 wrapper) {
		wrapper.stopSound();
		AudioWrapperV2 audio = createAudioLoopV2();
		audio.startSound();
		return audio;
	}
}
