package com.hbm.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AudioDynamic extends MovingSound {

	public float intendedVolume;
	public float maxVolume;
	public float range;
	public int keepAlive;
	public int timeSinceKA;
	public boolean shouldExpire = false;
	private boolean nonLegacy;
	public Entity parentEntity = null;

	protected AudioDynamic(SoundEvent loc, SoundCategory cat) {
		super(loc, cat);
		this.repeat = true;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.intendedVolume = 10;
		this.maxVolume = 10;
		this.volume = intendedVolume;
	}

	protected AudioDynamic(SoundEvent loc, SoundCategory cat, boolean useNewSystem, float maxVolume, float range, float intendedVolume) {
		super(loc, cat);
		this.repeat = true;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.maxVolume = maxVolume;
		this.range = range;
		this.intendedVolume = intendedVolume;
		this.nonLegacy = useNewSystem;
		this.volume = intendedVolume;
	}
	
	public void setPosition(float x, float y, float z) {
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
	}

	public void setAttenuation(ISound.AttenuationType type){
		this.attenuationType = type;
		volume = intendedVolume;
	}
	
	@Override
	public void update() {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		float f = 0;

		if (parentEntity != null && player != parentEntity) {
			this.setPosition((float) parentEntity.posX, (float) parentEntity.posY, (float) parentEntity.posZ);
		}

		if(player != null) {
			if(attenuationType == ISound.AttenuationType.LINEAR){
				/*float f3 = intendedVolume;
                float f2 = 16.0F;

                if (f3 > 1.0F)
                {
                    f2 *= f3;
                }
                f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
                volume = 1-f2/f;
                System.out.println(volume);*/
			} else {
				if (nonLegacy) {
					if (player != parentEntity) {
						f = (float) Math.sqrt(Math.pow(xPosF - player.posX, 2)
								+ Math.pow(yPosF - player.posY, 2)
								+ Math.pow(zPosF - player.posZ, 2));
						volume = func(f);
					} else {
						if (player == parentEntity) {
							this.setPosition((float) parentEntity.posX, (float) parentEntity.posY + 10, (float) parentEntity.posZ);
						}
						volume = maxVolume;
					}

					if (this.shouldExpire) {
						if (this.timeSinceKA > this.keepAlive) {
							this.stop();
						}
						this.timeSinceKA++;
					}
				} else {
					if (player != parentEntity) {
						f = (float) Math.sqrt(Math.pow(xPosF - player.posX, 2)
								+ Math.pow(yPosF - player.posY, 2)
								+ Math.pow(zPosF - player.posZ, 2));
						volume = func(f, intendedVolume);
					} else {
						if (player == parentEntity) {
							this.setPosition((float) parentEntity.posX, (float) parentEntity.posY + 10, (float) parentEntity.posZ);
						}
						volume = intendedVolume;
					}
				}
			}
		} else {
			volume = intendedVolume;
		}
	}
	
	public void start() {
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
		if(handler.sndManager.invPlayingSounds.containsKey(this)) return;
		handler.playSound(this);
	}
	
	public void stop() {
		Minecraft.getMinecraft().getSoundHandler().stopSound(this);
	}
	
	public void setVolume(float volume) {
		this.intendedVolume = volume;
		this.maxVolume = volume;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public void setKeepAlive(int keepAlive) {
		this.keepAlive = keepAlive;
		this.shouldExpire = true;
	}

	public void keepAlive() {
		this.timeSinceKA = 0;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}

	public float func(float dist) {
		return (dist / range) * -maxVolume + maxVolume;
	}

	public boolean isPlaying() {
		return Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this);
	}
}
