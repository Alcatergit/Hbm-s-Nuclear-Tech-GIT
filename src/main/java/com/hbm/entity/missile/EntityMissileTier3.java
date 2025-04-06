package com.hbm.entity.missile;

import api.hbm.entity.IRadarDetectableNT;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.items.ModItems;
import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class EntityMissileTier3 extends EntityMissileBaseNT {

	public EntityMissileTier3(World world) { super(world); }
	public EntityMissileTier3(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 16));
		list.add(new ItemStack(ModItems.plate_titanium, 10));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier3";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER3;
	}

	@Override
	protected void spawnContrail() {
		
		Vec3 thrust = Vec3.createVectorHelper(0, 0, 0.5);
		thrust.rotateAroundY((this.rotationYaw + 90) * (float) Math.PI / 180F);
		thrust.rotateAroundX(this.rotationPitch * (float) Math.PI / 180F);
		thrust.rotateAroundY(-(this.rotationYaw + 90) * (float) Math.PI / 180F);

		this.spawnContraolWithOffset(thrust.xCoord, thrust.yCoord, thrust.zCoord);
		this.spawnContraolWithOffset(-thrust.zCoord, thrust.yCoord, thrust.xCoord);
		this.spawnContraolWithOffset(-thrust.xCoord, -thrust.zCoord, -thrust.zCoord);
		this.spawnContraolWithOffset(thrust.zCoord, -thrust.zCoord, -thrust.xCoord);
	}
	
	public static class EntityMissileBurst extends EntityMissileTier3 {
		public EntityMissileBurst(World world) { super(world); }
		public EntityMissileBurst(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact()  {
			ExplosionLarge.explode(world, posX, posY, posZ, 50.0F, true, true, true);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_burst); }
	}
	
	public static class EntityMissileInferno extends EntityMissileTier3 {
		public EntityMissileInferno(World world) { super(world); }
		public EntityMissileInferno(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			ExplosionLarge.explodeFire(world, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 50.0F, true, true, true);
			ExplosionChaos.burn(this.world, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 10);
			ExplosionChaos.flameDeath(this.world, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_inferno); }
	}

	public static class EntityMissileRain extends EntityMissileTier3 {
		public EntityMissileRain(World world) { super(world); }
		public EntityMissileRain(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onImpact() {
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, 25F, true);
			ExplosionChaos.cluster(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 100, 100);
		}
		@Override public void cluster() { this.onImpact(); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_rain); }
	}
	
	public static class EntityMissileDrill extends EntityMissileTier3 {
		public EntityMissileDrill(World world) { super(world); }
		public EntityMissileDrill(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			for(int i = 0; i < 30; i++) {
				ExplosionNT explosion = new ExplosionNT(world, this, this.posX, this.posY - i, this.posZ, 10F);
				List<ExAttrib> attribs = Arrays.asList(new ExAttrib[] {ExAttrib.ERRODE});
				explosion.addAllAttrib(attribs);
				explosion.explode(); //an explosion exploded!
			}
			ExplosionLarge.spawnParticles(world, this.posX, this.posY, this.posZ, 25);
			ExplosionLarge.spawnShrapnels(world, this.posX, this.posY, this.posZ, 12);
			ExplosionLarge.jolt(world, this.posX, this.posY, this.posZ, 10, 50, 1);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_buster_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_drill); }
	}

	public static class EntityMissileEndo extends EntityMissileTier3{
		public EntityMissileEndo(World world) { super(world); }
		public EntityMissileEndo(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override
		public void onImpact() {
			ExplosionThermo.freeze(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
			ExplosionThermo.freezer(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
		}
		@Override
		public ItemStack getDebrisRareDrop() {
			return new ItemStack(ModItems.warhead_thermo_exo);
		}

		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_endo); }
	}

	public static class EntityMissileExo extends EntityMissileTier3{
		public EntityMissileExo(World world) { super(world); }
		public EntityMissileExo(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override
		public void onImpact() {
			ExplosionThermo.scorch(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
			ExplosionThermo.setEntitiesOnFire(this.world, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
		}
		@Override
		public ItemStack getDebrisRareDrop() {
			return new ItemStack(ModItems.warhead_thermo_exo);
		}

		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_exo); }
	}
}
