package com.leafia.dev.fluids.ntmtraits;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.leafia.contents.effects.folkvangr.visual.EntityCloudFleija;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nullable;

public class NTMTraitAntischrab extends NTMTraitAntimatter {
	@Nullable
	@Override
	public Runnable onViolation(World world,BlockPos pos,FluidStack stack,Object container) {
		Runnable lol = super.onViolation(world,pos,stack,container);
		float scale = (float)Math.min(stack.amount/1000F,Math.pow(stack.amount/1000F,0.25));
		if (lol == null) {
			lol = ()->{
				world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);
				EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
				entity.posX = pos.getX()+0.5;
				entity.posY = pos.getY()+0.5;
				entity.posZ = pos.getZ()+0.5;
				if(!EntityNukeExplosionMK3.isJammed(world, entity)){
					entity.destructionRange = (int) (BombConfig.aSchrabRadius * scale);

					entity.speed = 25;
					entity.coefficient = 1.0F;
					entity.waste = false;

					world.spawnEntity(entity);

					EntityCloudFleija cloud = new EntityCloudFleija(world, (int) (BombConfig.aSchrabRadius * scale));
					cloud.posX = pos.getX()+0.5;
					cloud.posY = pos.getY()+0.5;
					cloud.posZ = pos.getZ()+0.5;
					world.spawnEntity(cloud.setAntischrab());
				}
			};
		}
		return lol;
	}
}
