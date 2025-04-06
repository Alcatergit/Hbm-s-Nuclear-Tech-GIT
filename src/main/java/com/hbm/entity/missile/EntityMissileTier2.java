package com.hbm.entity.missile;

import api.hbm.entity.IRadarDetectableNT;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier2 extends EntityMissileBaseNT {

	public EntityMissileTier2(World world) { super(world); }
	public EntityMissileTier2(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 10));
		list.add(new ItemStack(ModItems.plate_titanium, 6));
		list.add(new ItemStack(ModItems.thruster_medium, 1));
		
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier2";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER2;
	}

	public static class EntityMissileStrong extends EntityMissileTier2 {
		public EntityMissileStrong(World world) { super(world); }
		public EntityMissileStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact()  {
			ExplosionLarge.explode(world, posX, posY, posZ, 30.0F, true, true, true);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_strong); }
	}

	public static class EntityMissileIncendiaryStrong extends EntityMissileTier2 {
		public EntityMissileIncendiaryStrong(World world) { super(world); }
		public EntityMissileIncendiaryStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			ExplosionLarge.explodeFire(world, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 30.0F, true, true, true);
			ExplosionChaos.flameDeath(this.world, new BlockPos((int)((float)this.posX + 0.5F), (int)((float)this.posY + 0.5F), (int)((float)this.posZ + 0.5F)), 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_incendiary_strong); }
	}

	public static class EntityMissileClusterStrong extends EntityMissileTier2 {
		public EntityMissileClusterStrong(World world) { super(world); }
		public EntityMissileClusterStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onImpact() {
			ExplosionLarge.explode(world, this.posX, this.posY, this.posZ, 15F, true, false, false);
			ExplosionChaos.cluster(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 50, 100);
		}
		@Override public void cluster() { this.onImpact(); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_cluster_strong); }
	}

	public static class EntityMissileBusterStrong extends EntityMissileTier2 {
		public EntityMissileBusterStrong(World world) { super(world); }
		public EntityMissileBusterStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			ExplosionLarge.buster(world, this.posX, this.posY, this.posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), 25, 20);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_buster_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_buster_strong); }
	}

	public static class EntityMissileEMPStrong extends EntityMissileTier2 {
		public EntityMissileEMPStrong(World world) { super(world); }
		public EntityMissileEMPStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			EntityEMP emp = new EntityEMP(world);
			emp.posX = posX;
			emp.posY = posY;
			emp.posZ = posZ;
			world.spawnEntity(emp);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_emp_strong); }
	}
}
