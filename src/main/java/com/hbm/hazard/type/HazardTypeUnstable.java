package com.hbm.hazard.type;

import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.util.I18nUtil;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.SoundCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class HazardTypeUnstable extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase entity, float radius, ItemStack stack) {
		if(stack.getItemDamage() != 0)
    		return;
		setTimer(stack, getTimer(stack) + 1);

		if(!entity.world.isRemote && getTimer(stack) >= 72000/radius) {
			entity.world.spawnEntity(EntityNukeExplosionMK5.statFac(entity.world, (int)radius, entity.posX, entity.posY, entity.posZ));

			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(entity.world, entity.posX, entity.posY, entity.posZ, (int)radius);
			}
			entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.oldExplosion, SoundCategory.PLAYERS, 1.0F, 1.0F);
			entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10000);
			stack.setCount(0);
		}
	}

	@Override
	public void updateEntity(EntityItem itemEntity, float radius) {
		World world = itemEntity.world;

		setTimer(itemEntity.getItem(), getTimer(itemEntity.getItem()) + 1);

		if(!itemEntity.world.isRemote && getTimer(itemEntity.getItem()) >= 72000/radius) {
			itemEntity.world.spawnEntity(EntityNukeExplosionMK5.statFac(itemEntity.world, (int)radius, itemEntity.posX, itemEntity.posY, itemEntity.posZ));

			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(itemEntity.world, itemEntity.posX, itemEntity.posY, itemEntity.posZ, (int)radius);
			}
			itemEntity.world.playSound(null, itemEntity.posX, itemEntity.posY, itemEntity.posZ, HBMSoundHandler.oldExplosion, SoundCategory.PLAYERS, 1.0F, 1.0F);
			itemEntity.attackEntityFrom(ModDamageSource.nuclearBlast, 10000);
			itemEntity.setDead();
		}
	}

	private void setTimer(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setInteger("timer", time);
	}

	private int getTimer(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;

		return stack.getTagCompound().getInteger("timer");
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		if(stack.getItemDamage() != 0)
    		return;
    	list.add("§4[Unstable]§r");
		list.add(" §cDecay Time: " + (int)(72000/level)/20 + "s - Explosion Radius: "+ (int)level+"m§r");
		list.add(" §cDecay: " + (getTimer(stack) * 100 / (int)(72000/level)) + "%§r");
	}
}