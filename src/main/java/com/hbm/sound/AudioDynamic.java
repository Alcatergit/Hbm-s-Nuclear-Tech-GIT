package com.hbm.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.BiFunction;

@SideOnly(Side.CLIENT)
public class AudioDynamic extends MovingSound {

	public float intendedVolume;
	public BiFunction<Float,Double,Double> attentuationFunction = null;

	protected AudioDynamic(SoundEvent loc, SoundCategory cat) {
		super(loc, cat);
		this.repeat = true;
		this.attenuationType = ISound.AttenuationType.NONE;
		this.intendedVolume = 10;
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
	public void setCustomAttentuation(BiFunction<Float,Double,Double> attentuationFunction) {
		this.attentuationFunction = attentuationFunction;
	}

	public void setLooped(boolean repeat) {
		this.repeat = repeat;
	}
	
	@Override
	public void update() {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		float f = 0;
		if(player != null) {
			double distance = Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
			if (attentuationFunction != null) {
				double castLiteral = attentuationFunction.apply(intendedVolume,distance);
				volume = (float)castLiteral; // java doesn't allow directly applying the value to here lol
			} else if(attenuationType == ISound.AttenuationType.LINEAR){
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
				f = (float)distance;
				volume = func(f, intendedVolume);
			}
		} else {
			volume = intendedVolume;
		}
	}
	
	public void start() {
		try {
			Minecraft.getMinecraft().getSoundHandler().playSound(this);
		} catch (IllegalArgumentException ignored) {} // seriously fuck you
	}
	
	public void stop() {
		Minecraft.getMinecraft().getSoundHandler().stopSound(this);
	}
	
	public void setVolume(float volume) {
		this.intendedVolume = volume;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public float func(float f, float v) {
		return (f / v) * -2 + 2;
	}
}
