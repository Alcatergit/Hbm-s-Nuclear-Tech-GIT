package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.tileentity.machine.TileEntityMachineSiren;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;

public class SoundLoopSiren extends SoundLoopMachine {

	public static List<SoundLoopSiren> list = new ArrayList<SoundLoopSiren>();
	public float intendedVolume;
	public SoundType type;

	public SoundLoopSiren(SoundEvent path, TileEntity te, SoundType type) {
		super(path, te);
		list.add(this);
		intendedVolume = 10.0F;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.type = type;
	}

	@Override
	public void update() {
		super.update();
		
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		float f = 0;
		
		if(player != null) {
			f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
			volume = func(f, intendedVolume);
		} else {
			volume = intendedVolume;
		}
		
		if(te instanceof TileEntityMachineSiren) {
			this.setRepeat(type.name().equals(SoundType.LOOP.name()));
		} else {
			this.donePlaying = true;
		}
	}
	
	public TileEntity getTE() {
		return te;
	}
	
	public void endSound() {
		this.donePlaying = true;
	}
	
	public String getPath() {
		return this.positionedSoundLocation.getResourceDomain() + ":" + this.positionedSoundLocation.getResourcePath();
	}
	
	public void setRepeat(boolean b) {
		this.repeat = b;
	}
	
	public void setRepeatDelay(int i) {
		this.repeatDelay = i;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}
}
