package com.hbm.sound;

import com.hbm.handler.JetpackHandler;
import com.hbm.handler.JetpackHandler.JetpackInfo;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingSoundJetpack extends MovingSound {

	public EntityPlayer player;
	
	public MovingSoundJetpack(EntityPlayer player, SoundEvent soundIn, SoundCategory categoryIn) {
		super(soundIn, categoryIn);
		this.player = player;
		this.repeat = true;
	}

	@Override
	public void update() {
		this.xPosF = (float) player.posX;
		this.yPosF = (float) player.posY;
		this.zPosF = (float) player.posZ;
		JetpackInfo j = JetpackHandler.get(player);
		if(j != null){
			this.volume = (float) Math.log(j.thrust*30+1)*0.25F;
			//System.out.println(volume);
		} else {
			this.donePlaying = true;
		}
	}

	public void end() {
		this.donePlaying = true;
	}

}
