package com.hbm.handler;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class BossSpawnHandler {

	public static void rollTheDice(World world) {

		if(MobConfig.enableMaskman) {
			if(world.getTotalWorldTime() % MobConfig.maskmanDelay == 0) {

				if(world.rand.nextInt(MobConfig.maskmanChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {	//33% chance only if there is a player online

					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));	//choose a random player
					
					
					if(ContaminationUtil.getRads(player) >= MobConfig.maskmanMinRad && (world.getHeight((int)player.posX, (int)player.posZ) > player.posY + 3 || !MobConfig.maskmanUnderground)) {	//if the player has more than 50 RAD and is underground
						player.sendMessage(new TextComponentString("The mask man is about to claim another victim.").setStyle(new Style().setColor(TextFormatting.RED)));
						
						double spawnX = player.posX + world.rand.nextGaussian() * 20;
						double spawnZ = player.posZ + world.rand.nextGaussian() * 20;
						double spawnY = world.getHeight((int)spawnX, (int)spawnZ);

						trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, new EntityMaskMan(world));
					}
				}
			}
		}

		if(MobConfig.enableRaids) {

			if(world.getTotalWorldTime() % MobConfig.raidDelay == 0) {

				if(world.rand.nextInt(MobConfig.raidChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {

					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));
					player.sendMessage(new TextComponentString("FBI, OPEN UP!").setStyle(new Style().setColor(TextFormatting.RED)));
					
					Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackDistance, 0, 0);
					vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());

					for(int i = 0; i < MobConfig.raidAmount; i++) {

						double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian() * 5;
						double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian() * 5;
						double spawnY = world.getHeight((int)spawnX, (int)spawnZ);

						trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, new EntityFBI(world));
					}
				}
			}
		}
		
		if(MobConfig.enableRaids) {
			
			if(world.getTotalWorldTime() % MobConfig.raidDelay == 0) {
				
				if(world.rand.nextInt(MobConfig.raidChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {
					
					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));
					
					if(player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getLong("fbiMark") < world.getTotalWorldTime()) {
						player.sendMessage(new TextComponentString("FBI, OPEN UP!").setStyle(new Style().setColor(TextFormatting.RED)));
						
						Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackDistance, 0, 0);
						vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());
						
						for(int i = 0; i < MobConfig.raidAmount; i++) {
	
							double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian() * 5;
							double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian() * 5;
							double spawnY = world.getHeight((int)spawnX, (int)spawnZ);
							
							trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, new EntityFBI(world));
						}
					}
				}
			}
		}
		
		if(MobConfig.enableElementals) {

			if(world.getTotalWorldTime() % MobConfig.elementalDelay == 0) {

				if(world.rand.nextInt(MobConfig.elementalChance) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {

					EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));

					if(player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getBoolean("radMark")) {

						player.sendMessage(new TextComponentString("You hear a faint clicking...").setStyle(new Style().setColor(TextFormatting.YELLOW)));
						player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", false);

						Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackDistance, 0, 0);

						for(int i = 0; i < MobConfig.elementalAmount; i++) {

							vec.rotateAroundY((float)(Math.PI * 2) * world.rand.nextFloat());

							double spawnX = player.posX + vec.xCoord + world.rand.nextGaussian();
							double spawnZ = player.posZ + vec.zCoord + world.rand.nextGaussian();
							double spawnY = world.getHeight((int)spawnX, (int)spawnZ);

							EntityRADBeast rad = new EntityRADBeast(world);

							if(i == 0)
								rad.makeLeader();

							trySpawn(world, (float)spawnX, (float)spawnY, (float)spawnZ, rad);
						}
					}
				}
			}
		}
	}
	
	private static void trySpawn(World world, float x, float y, float z, EntityLiving e) {

		e.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
		Result canSpawn = ForgeEventFactory.canEntitySpawn(e, world, x, y, z, null);

		if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT) {
			world.spawnEntity(e);
			ForgeEventFactory.doSpecialSpawn(e, world, x, y, z, null);
			e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e)), null);
		}
	}
	
	public static void markFBI(EntityPlayer player) {
		if(!player.world.isRemote)
			player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setLong("fbiMark", player.world.getTotalWorldTime() + 20 * 60 * 20);
	}
}
