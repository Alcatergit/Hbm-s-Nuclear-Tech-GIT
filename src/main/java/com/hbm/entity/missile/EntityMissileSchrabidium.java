package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileSchrabidium extends EntityMissileBaseAdvanced {

	public EntityMissileSchrabidium(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1F, 7F);
	}

	public EntityMissileSchrabidium(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.setSize(1F, 7F);
	}

	@Override
	public void onImpact() {
        if (!this.world.isRemote)
        {
			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.world);
			entity.posX = this.posX;
			entity.posY = this.posY;
			entity.posZ = this.posZ;
			if(!EntityNukeExplosionMK3.isJammed(this.world, entity)){
				entity.destructionRange = BombConfig.aSchrabRadius;
				entity.speed = 25;
				entity.coefficient = 1.0F;
				entity.waste = false;

				this.world.spawnEntity(entity);

				EntityCloudFleija cloud = new EntityCloudFleija(this.world, BombConfig.aSchrabRadius);
				cloud.posX = this.posX;
				cloud.posY = this.posY;
				cloud.posZ = this.posZ;
				this.world.spawnEntity(cloud);
			}
        }
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(ModItems.ducttape, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.powder_schrabidium, 1);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
