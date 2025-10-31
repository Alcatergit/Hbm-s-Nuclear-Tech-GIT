package com.hbm.entity.projectile;

import com.hbm.lib.Library;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBeamVortex extends EntityBeamBase {

	public EntityBeamVortex(World worldIn) {
		super(worldIn);
	}
	
	public EntityBeamVortex(World world, EntityPlayer player) {
		super(world, player);
	}
	
	@Override
	public void onUpdate() {
		if(world.isRemote){
			if(this.ticksExisted == 1){
				EntityPlayer player = this.world.getPlayerEntityByName(this.getDataManager().get(PLAYER_NAME));
				
			}
			return;
		}
		if(this.ticksExisted > 10)
			this.setDead();

		if(this.ticksExisted > 1)
			return;

		int range = 100;

		EntityPlayer player = world.getPlayerEntityByName(this.getDataManager().get(PLAYER_NAME));

		if(player != null) {

			RayTraceResult pos = Library.rayTrace(player, range, 1, false, true, false);

			if(pos == null)
				return;

			world.spawnParticle(EnumParticleTypes.CLOUD, pos.hitVec.x, pos.hitVec.y, pos.hitVec.z, 0, 0, 0);
			world.playSound(null, pos.hitVec.x, pos.hitVec.y, pos.hitVec.z, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, 1, 1);
		}
	}
}
