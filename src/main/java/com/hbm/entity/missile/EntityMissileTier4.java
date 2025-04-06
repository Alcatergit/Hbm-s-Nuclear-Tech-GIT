package com.hbm.entity.missile;

import api.hbm.entity.IRadarDetectableNT;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityMissileTier4 extends EntityMissileBaseNT {

	public EntityMissileTier4(World world) { super(world); }
	public EntityMissileTier4(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.plate_titanium, 16));
		list.add(new ItemStack(ModItems.plate_steel, 20));
		list.add(new ItemStack(ModItems.plate_aluminium, 12));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		return list;
	}

	@Override
	public String getTranslationKey() {
		return "radar.target.tier4";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER4;
	}

	@Override
	protected void spawnContrail() {
		
		byte rot = this.dataManager.get(this.pr3);
		
		Vec3 thrust = Vec3.createVectorHelper(0, 0, 1);
		switch(rot) {
		case 2: thrust.rotateAroundY((float) -Math.PI / 2F); break;
		case 4: thrust.rotateAroundY((float) -Math.PI); break;
		case 3: thrust.rotateAroundY((float) -Math.PI / 2F * 3F);  break;
		}
		thrust.rotateAroundY((this.rotationYaw + 90) * (float) Math.PI / 180F);
		thrust.rotateAroundX(this.rotationPitch * (float) Math.PI / 180F);
		thrust.rotateAroundY(-(this.rotationYaw + 90) * (float) Math.PI / 180F);

		this.spawnContraolWithOffset(thrust.xCoord, thrust.yCoord, thrust.zCoord);
		this.spawnContraolWithOffset(0, 0, 0);
		this.spawnContraolWithOffset(-thrust.xCoord, -thrust.zCoord, -thrust.zCoord);
	}
	
	public static class EntityMissileNuclear extends EntityMissileTier4 {
		public EntityMissileNuclear(World world) { super(world); }
		public EntityMissileNuclear(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius, posX, posY, posZ));
			EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.missileRadius);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_nuclear); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_nuclear); }
	}
	
	public static class EntityMissileMirv extends EntityMissileTier4 {
		public EntityMissileMirv(World world) { super(world); }
		public EntityMissileMirv(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius * 2, posX, posY, posZ));
			EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.missileRadius * 2);
		}
		@Override public List<ItemStack> getDebris() {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModItems.plate_titanium, 16));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			return list;
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_mirv); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_nuclear_cluster); }
	}
	
	public static class EntityMissileVolcano extends EntityMissileTier4 {
		public EntityMissileVolcano(World world) { super(world); }
		public EntityMissileVolcano(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			BlockPos pos = new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ));
			ExplosionLarge.explode(world, posX, posY, posZ, 10.0F, true, true, true);
			for(int x = -1; x <= 1; x++) for(int y = -1; y <= 1; y++) for(int z = -1; z <= 1; z++) world.setBlockState(pos, ModBlocks.volcanic_lava_block.getDefaultState());
			world.setBlockState(pos, ModBlocks.volcano_core.getDefaultState());
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_volcano); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_volcano); }
	}
	
	public static class EntityMissileDoomsday extends EntityMissileTier4 {
		public EntityMissileDoomsday(World world) { super(world); }
		public EntityMissileDoomsday(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius * 2, posX, posY, posZ).moreFallout(100));
			EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.missileRadius * 2);
		}
		@Override public List<ItemStack> getDebris() { return null; }
		@Override public ItemStack getDebrisRareDrop() { return null; }
		@Override public String getTranslationKey() { return "radar.target.doomsday"; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_doomsday); }
	}
	
	/*public static class EntityMissileDoomsdayRusted extends EntityMissileDoomsday {
		public EntityMissileDoomsdayRusted(World world) { super(world); }
		public EntityMissileDoomsdayRusted(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius, posX, posY, posZ).moreFallout(100));
			EntityNukeTorex.statFac(world, posX, posY, posZ, BombConfig.missileRadius);
		}
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_doomsday_rusted); }
	}*/

	public static class EntityMissileN2 extends EntityMissileTier4 {
		public EntityMissileN2(World world) { super(world); }
		public EntityMissileN2(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int)(BombConfig.n2Radius/12) * 5, posX, posY, posZ));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, this.posX, this.posY, this.posZ, (int)(BombConfig.n2Radius/12) * 5);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_n2); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_n2); }
	}
}
