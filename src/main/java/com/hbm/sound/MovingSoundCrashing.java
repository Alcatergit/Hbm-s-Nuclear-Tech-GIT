package com.hbm.sound;

import com.hbm.entity.mob.EntityHunterChopper;

import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundCrashing extends MovingSoundPlayerLoop {

	public MovingSoundCrashing(SoundEvent p_i45104_1_, SoundCategory c, Entity player, EnumHbmSound type) {
		super(p_i45104_1_, c, player, type);
	}

	@Override
	public void update() {
		super.update();
		
		if(player instanceof EntityHunterChopper && !((EntityHunterChopper)player).getIsDying())
			this.stop();
	}
}
