package com.hbm.sound;

import com.hbm.tileentity.TileEntityLoadedBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundLoopMachine extends PositionedSound implements ITickableSound {
	boolean donePlaying = false;
	public TileEntity te;
    int targetVolume;

	public SoundLoopMachine(SoundEvent path, TileEntity te, int volume) {
		super(path, SoundCategory.BLOCKS);
		this.repeat = true;
        this.targetVolume = volume;
		this.volume = 1;
		this.pitch = 1;
		this.xPosF = te.getPos().getX();
		this.yPosF = te.getPos().getY();
		this.zPosF = te.getPos().getZ();
		this.repeatDelay = 0;
		this.te = te;
	}

    public static boolean isLoaded(TileEntity te){
        if(te instanceof TileEntityLoadedBase base) return base.isLoaded();
        return true;
    }

    public static boolean canPlay(TileEntity te){
        return !(te == null || te.isInvalid() || !te.getWorld().isBlockLoaded(te.getPos()) || !isLoaded(te));
    }

    public boolean isThisProcessing(){
        return true;
    }

	@Override
	public void update() {
		if(!canPlay(te) || !isThisProcessing() || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this)) {
            stop();
        } else if(this.volume != this.targetVolume) {
            this.volume = this.targetVolume;
        }
    }

	@Override
	public boolean isDonePlaying() {
		return this.donePlaying;
	}
	
	public void setVolume(float f) {
		volume = f;
	}
	
	public void setPitch(float f) {
		pitch = f;
	}
	
	public void stop() {
		donePlaying = true;
	}
}