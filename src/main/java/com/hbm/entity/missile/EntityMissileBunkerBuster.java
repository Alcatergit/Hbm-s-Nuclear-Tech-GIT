package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileBunkerBuster extends EntityMissileBaseAdvanced {

	public EntityMissileBunkerBuster(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1F, 6F);
	}

	public EntityMissileBunkerBuster(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.setSize(1F, 6F);
	}

	@Override
	public void onImpact() {
		ExplosionLarge.buster(world, this.posX, this.posY, this.posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), 15, 15);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.thruster_small, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_buster_small);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER1;
	}
}
