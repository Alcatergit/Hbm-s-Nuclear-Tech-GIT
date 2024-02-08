package com.hbm.handler;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;

import com.hbm.capability.HbmLivingProps;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.entity.mob.EntityDuck;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityQuackos;
import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.AdvancementManager;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class RadiationSystemNT {

	public static float minRadRate = 0.000005F;

	/**Per world radiation storage data*/
	private static Map<World, WorldRadiationData> worldMap = new HashMap<>();
	/**A tick counter so radiation only updates once every second.*/
	private static int ticks;
	
	/**
	 * Increments the radiation at the specified block position. Only increments if the current radiaion stored is less than max
	 * @param world - the world to increment radiation in
	 * @param pos - the block position to increment radiation at
	 * @param amount - the amount to increment by
	 * @param max - the maximum amount of radiation allowed before it doesn't increment
	 */
	public static void incrementRad(World world, BlockPos pos, float amount, float max){
		if(pos.getY() < 0 || pos.getY() > 255 || !world.isBlockLoaded(pos))
			return;
		RadPocket p = getPocket(world, pos);
		if(p.radiation < max){
			p.radiation += amount;
		}
		//Mark this pocket as active so it gets updated
		if(amount > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.addActivePocket(p);
		}
	}
	
	/**
	 * Subtracts amount from the current radiation at pos.
	 * @param world - the world to edit radiation in
	 * @param pos - the position to edit radiation at
	 * @param amount - the amount to subtract from current rads
	 */
	public static void decrementRad(World world, BlockPos pos, float amount){
		//If there's nothing to decrement, return
		if(pos.getY() < 0 || pos.getY() > 255 || !isSubChunkLoaded(world, pos))
			return;
		RadPocket p = getPocket(world, pos);
		p.radiation -= Math.max(amount, 0);
		if(p.radiation < 0){
			p.radiation = 0;
		}
	}
	
	/**
	 * Sets the radiation at pos to the specified amount
	 * @param world - the world to set radiation in
	 * @param pos - the position to set radiation at
	 * @param amount - the amount to set the radiation to
	 */
	public static void setRadForCoord(World world, BlockPos pos, float amount){
		RadPocket p = getPocket(world, pos);
		p.radiation = Math.max(amount, 0);
		//If the amount is greater than 0, make sure to mark it as dirty so it gets updated
		if(amount > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.addActivePocket(p);
		}
	}
	
	/**
	 * Gets the radiation at the pos
	 * @param world - the world to get raadiation in
	 * @param pos - the position to get radiation at
	 * @return - the radiation value at the specified position
	 */
	public static float getRadForCoord(World world, BlockPos pos){
		//If it's not loaded, assume there's no radiation. Makes sure to not keep a lot of chunks loaded
		if(!isSubChunkLoaded(world, pos))
			return 0;

		// If no pockets, assume no radiation
		if (getPocket(world, pos) == null)
			return 0;

		return getPocket(world, pos).radiation;
	}
	
	/**
	 * Removes all loaded radiation from a world
	 * @param world - the world from which to remove radiation
	 */
	public static void jettisonData(World world){
		WorldRadiationData data = getWorldRadData(world);
		data.data.clear();
		data.clearActivePockets();
	}
	
	/**
	 * Gets the pocket at the position (pockets explained below)
	 * @param world - the world to get the pocket from
	 * @param pos - the position the pocket should contain
	 * @return - the RadPocket at the specified position
	 */
	public static RadPocket getPocket(World world, BlockPos pos){
		return getSubChunkStorage(world, pos).getPocket(pos);
	}
	
	/**
	 * Gets the collection of RadiationPockets that have active radiation data
	 * @param world - the world to get radiation pockets from
	 * @return - collection of active rad pockets
	 */
	public static Collection<RadPocket> getActiveCollection(World world){
		return getWorldRadData(world).getActivePockets();
	}
	
	/**
	 * Gets whether the sub chunk at spefified position is loaded
	 * @param world - the world to check in
	 * @param pos - ths position to check at
	 * @return whether the specified position currently has an active sub chunk
	 */
	public static boolean isSubChunkLoaded(World world, BlockPos pos){
		//If the position is out of bounds, it isn't loaded
		if(pos.getY() > 255 || pos.getY() < 0)
			return false;
		//If the world radiation data doesn't exist, nothing is loaded
		WorldRadiationData worldRadData = worldMap.get(world);
		if(worldRadData == null){
			return false;
		}
		//If the chunk isn't loaded, neither is the sub chunk
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkPos(pos));
		if(st == null){
			return false;
		}
		//Finally, check if the chunk has a sub chunk at the specified y level
		SubChunkRadiationStorage sc = st.getForYLevel(pos.getY());
		if(sc == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the sub chunk from the specified pos. Loads it if it doesn't exist
	 * @param world - the world to get from
	 * @param pos - the position to get the sub chunk at
	 * @return the sub chunk at the specified position
	 */
	public static SubChunkRadiationStorage getSubChunkStorage(World world, BlockPos pos){
		ChunkRadiationStorage st = getChunkStorage(world, pos);
		SubChunkRadiationStorage sc = st.getForYLevel(pos.getY());
		//If the sub chunk doesn't exist, bring it into existence by rebuilding the sub chunk, then get it.
		if(sc == null){
			rebuildChunkPockets(world.getChunkFromBlockCoords(pos), pos.getY() >> 4);
		}
		sc = st.getForYLevel(pos.getY());
		return sc;
	}
	
	/**
	 * Gets the chunk at the specified pos. Loads it if it doesn't exist
	 * @param world - the world to get the chunk storage from
	 * @param pos - the position of the chunk
	 * @return the chunk radiation storage at the specified position
	 */
	public static ChunkRadiationStorage getChunkStorage(World world, BlockPos pos){
		WorldRadiationData worldRadData = getWorldRadData(world);
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkPos(pos));
		//If it doesn't currently exist, create it
		if(st == null){
			st = new ChunkRadiationStorage(worldRadData, world.getChunkFromBlockCoords(pos));
			worldRadData.data.put(new ChunkPos(pos), st);
		}
		return st;
	}
	
	/**
	 * Gets the world radiation data for the world
	 * @param world - the world to get the radiation data from
	 * @return the radiation data for the world
	 */
	private static WorldRadiationData getWorldRadData(World world){
		WorldRadiationData worldRadData = worldMap.get(world);
		//If we don't have one, make a new one
		if(worldRadData == null){
			worldRadData = new WorldRadiationData(world);
			worldMap.put(world, worldRadData);
		}
		return worldRadData;
	}

	private static void updateRadSaveData(World world) {
		RadiationSavedData data = RadiationSavedData.getData(world);

		if (data.worldObj == null) {
			data.worldObj = world;
		}

		if (GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Updated system for entity contamination processing at worldtime " + world.getTotalWorldTime());
		}

		data.updateSystem();
	}

	/**
	 * Updates entity contamination and applies effects based on current rad levels
	 * @param world
	 */
	private static void updateEntityContamination(World world, boolean updateData) {
		if(world != null && !world.isRemote && GeneralConfig.enableRads) {
			if(GeneralConfig.enableDebugMode) {
				MainRegistry.logger.info("[Debug] Starting entity contamination processing");
			}

			int thunder = AuxSavedData.getThunder(world);

			if(thunder > 0)
				AuxSavedData.setThunder(world, thunder - 1);

			if(!world.loadedEntityList.isEmpty()) {

				RadiationSavedData data = RadiationSavedData.getData(world);

				if(data.worldObj == null) {
					data.worldObj = world;
				}

				if(world.getTotalWorldTime() % 20 == 15 && updateData) { // lets not make a lag spike at tick 0 unless a chunk requires update
					updateRadSaveData(world);
				}

				List<Object> oList = new ArrayList<Object>();
				oList.addAll(world.loadedEntityList);

				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {

						// effect for radiation
						EntityLivingBase entity = (EntityLivingBase) e;

						if(entity instanceof EntityPlayer){
							EntityPlayer player = (EntityPlayer) entity;
							if(RadiationConfig.neutronActivation){
								double recievedRadiation = ContaminationUtil.getNoNeutronPlayerRads(player)*0.00004D-(0.00004D * RadiationConfig.neutronActivationThreshold); //20Rad/s threshold
								float neutronRads = ContaminationUtil.getPlayerNeutronRads(player);
								if(neutronRads > 0){
									ContaminationUtil.contaminate(player, ContaminationUtil.HazardType.NEUTRON, ContaminationUtil.ContaminationType.CREATIVE, neutronRads * 0.05F);
								}
								else{
									HbmLivingProps.setNeutron(entity, 0);
								}
								if(recievedRadiation > minRadRate){
									ContaminationUtil.neutronActivateInventory(player, (float)recievedRadiation, 1.0F);
									player.inventoryContainer.detectAndSendChanges();
								}
							}
							if(player.capabilities.isCreativeMode || player.isSpectator()){
								continue;
							}
						}

						float eRad = (float)HbmLivingProps.getRadiation(entity);

						if(eRad >= 200 && entity.getHealth() > 0 && entity instanceof EntityCreeper) {

							if(world.rand.nextInt(3) == 0) {
								EntityNuclearCreeper creep = new EntityNuclearCreeper(world);
								creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

								if(!entity.isDead)
									if(!world.isRemote)
										world.spawnEntity(creep);
								entity.setDead();
							} else {
								entity.attackEntityFrom(ModDamageSource.radiation, 100F);
							}
							continue;

						} else if(eRad >= 500 && entity instanceof EntityCow && !(entity instanceof EntityMooshroom)) {
							EntityMooshroom creep = new EntityMooshroom(world);
							creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

							if(!entity.isDead)
								if(!world.isRemote)
									world.spawnEntity(creep);
							entity.setDead();
							continue;

						} else if(eRad >= 600 && entity instanceof EntityVillager) {
							EntityVillager vil = (EntityVillager)entity;
							EntityZombieVillager creep = new EntityZombieVillager(world);
							creep.setProfession(vil.getProfession());
							creep.setForgeProfession(vil.getProfessionForge());
							creep.setChild(vil.isChild());
							creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

							if(!entity.isDead)
								if(!world.isRemote)
									world.spawnEntity(creep);
							entity.setDead();
							continue;
						} else if(eRad >= 700 && entity instanceof EntityBlaze) {
							EntityRADBeast creep = new EntityRADBeast(world);
							creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

							if(!entity.isDead)
								if(!world.isRemote)
									world.spawnEntity(creep);
							entity.setDead();
							continue;
						} else if(eRad >= 800 && entity instanceof EntityHorse) {
							EntityHorse horsie = (EntityHorse)entity;
							EntityZombieHorse zomhorsie = new EntityZombieHorse(world);
							zomhorsie.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
							zomhorsie.setGrowingAge(horsie.getGrowingAge());
							zomhorsie.setTemper(horsie.getTemper());
							zomhorsie.setHorseSaddled(horsie.isHorseSaddled());
							zomhorsie.setHorseTamed(horsie.isTame());
							zomhorsie.setOwnerUniqueId(horsie.getOwnerUniqueId());
							zomhorsie.makeMad();
							if(!entity.isDead)
								if(!world.isRemote)
									world.spawnEntity(zomhorsie);
							entity.setDead();
							continue;
						} else if(eRad >= 900 && entity.getClass().equals(EntityDuck.class)) {

							EntityQuackos quacc = new EntityQuackos(world);
							quacc.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

							if(!entity.isDead && !world.isRemote)
								world.spawnEntity(quacc);

							entity.setDead();
							continue;
						}

						if(eRad > 2500000)
							HbmLivingProps.setRadiation(entity, 2500000);

						if(eRad >= 1000) {
							entity.attackEntityFrom(ModDamageSource.radiation, 1000F);
							HbmLivingProps.setRadiation(entity, 0);

							if(entity.getHealth() > 0) {
								entity.setHealth(0);
								entity.onDeath(ModDamageSource.radiation);
							}

							if(entity instanceof EntityPlayerMP)
								AdvancementManager.grantAchievement((EntityPlayerMP) entity, AdvancementManager.achRadDeath);
						} else if(eRad >= 800) {
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10 * 20, 2));
							if(world.rand.nextInt(500) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 3 * 20, 2));
							if(world.rand.nextInt(700) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 3 * 20, 1));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 5 * 20, 3));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 5 * 20, 3));

						} else if(eRad >= 600) {
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10 * 20, 2));
							if(world.rand.nextInt(500) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 3 * 20, 1));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 3));
							if(world.rand.nextInt(400) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 6 * 20, 2));

						} else if(eRad >= 400) {
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
							if(world.rand.nextInt(500) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, 0));
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 1));
							if(world.rand.nextInt(500) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 2));
							if(world.rand.nextInt(600) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 4 * 20, 1));

						} else if(eRad >= 200) {
							if(world.rand.nextInt(300) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
							if(world.rand.nextInt(500) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 0));
							if(world.rand.nextInt(700) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 2));
							if(world.rand.nextInt(800) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 4 * 20, 0));
						} else if(eRad >= 100) {
							if(world.rand.nextInt(800) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2 * 20, 0));
							if(world.rand.nextInt(1000) == 0)
								entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1 * 20, 0));

							if(entity instanceof EntityPlayerMP)
								AdvancementManager.grantAchievement((EntityPlayerMP) entity, AdvancementManager.achRadPoison);
						}

					}
				}
			}
			if(GeneralConfig.enableDebugMode) {
				MainRegistry.logger.info("[Debug] Finished entity contamination processing");
			}
		}
	}

	/**
	 * Marks a chunk to be rebuilt. This is used when a radiation resistant block is added or removed
	 * @param world - the world to mark in
	 * @param pos - the position to mark at
	 */
	public static void markChunkForRebuild(World world, BlockPos pos){
		if(!GeneralConfig.advancedRadiation)
			return;

		//I'm using this blockpos as a sub chunk pos
		BlockPos chunkPos = new BlockPos(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
		WorldRadiationData r = getWorldRadData(world);

		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Marking chunk dirty at " + chunkPos);
		}

		//Ensures we don't run into any problems with concurrent modification
		if(r.iteratingDirty){
			r.dirtyChunks2.add(chunkPos);
		} else {
			r.dirtyChunks.add(chunkPos);
		}
	}

	/**
	 * Rebuilds stored dirty chunks
	 */
	private static void rebuildDirty() {

		for(WorldRadiationData r : worldMap.values()) {
			boolean hadDirty = false;

			//Set the iteration flag to avoid concurrent modification
			r.iteratingDirty = true;

			//For each dirty sub chunk, rebuild it
			for (BlockPos dirtyChunkPos : r.dirtyChunks) {
				if (GeneralConfig.enableDebugMode) {
					MainRegistry.logger.info("[Debug] Rebuilding chunk pockets for dirty chunk at " + dirtyChunkPos);
				}

				rebuildChunkPockets(r.world.getChunkFromChunkCoords(dirtyChunkPos.getX(), dirtyChunkPos.getZ()), dirtyChunkPos.getY());
				hadDirty = true;
			}
			r.iteratingDirty = false;
			//Clear the dirty chunks lists, and add any chunks that might have been marked while iterating to be dealt with next tick.
			r.dirtyChunks.clear();
			r.dirtyChunks.addAll(r.dirtyChunks2);
			r.dirtyChunks2.clear();

			// After chunks are updated, force save data to update as well and recalc rads for chunks
			//if (hadDirty) {
			//	if (GeneralConfig.enableDebugMode) {
			//		MainRegistry.logger.info("[Debug] Forced update of rad save data after dirty chunk rebuild");
			//	}
			//	updateRadSaveData(r.world);
			//}
		}
	}

	@SubscribeEvent
	public static void onWorldUpdate(TickEvent.WorldTickEvent e) {
		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] onWorldUpdate called for RadSys tick " + ticks);
		}

		boolean allowUpdate = (e.phase == Phase.START);

		if (allowUpdate) {
			// Make the world stinky
			RadiationWorldHandler.handleWorldDestruction(e.world);
		}

		// Make entities stinky
		updateEntityContamination(e.world, allowUpdate);
	}

	@SubscribeEvent
	public static void onUpdate(TickEvent.ServerTickEvent e){
		//If we don't do advanced radiation, don't update
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;

		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] onUpdate called for RadSys tick " + ticks);
		}

		if(e.phase == Phase.END){
			ticks ++;
			if(ticks % 20 == 17) {
				//long mil = System.nanoTime();

				//Every second, do a full system update, which will spread around radiation and all that
				updateRadiation();

				//System.out.println("rad tick took: " + (System.nanoTime()-mil));
			}
		}

		//Make sure any chunks marked as dirty by radiation resistant blocks are rebuilt instantly
		rebuildDirty();
	}
	
	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			//When a chunk is unloaded, also unload it from our radiation data if it exists
			WorldRadiationData data = getWorldRadData(e.getWorld());
			if(data.data.containsKey(e.getChunk().getPos())){
				data.data.get(e.getChunk().getPos()).unload();
				data.data.remove(e.getChunk().getPos());
			}
		}
	}
	
	@SubscribeEvent
	public static void onChunkLoad(ChunkDataEvent.Load e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			if(e.getData().hasKey("hbmRadDataNT")){
				//If this chunk had saved radiation in it, read it and add the persistent chunk data at this chunk position
				WorldRadiationData data = getWorldRadData(e.getWorld());
				ChunkRadiationStorage cData = new ChunkRadiationStorage(data, e.getChunk());
				cData.readFromNBT(e.getData().getCompoundTag("hbmRadDataNT"));
				data.data.put(e.getChunk().getPos(), cData);
			}
		}
	}
	
	@SubscribeEvent
	public static void onChunkSave(ChunkDataEvent.Save e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			WorldRadiationData data = getWorldRadData(e.getWorld());
			if(data.data.containsKey(e.getChunk().getPos())){
				//When a chunk is saved, if this chunk has radiation data, write the radiation data to the chunk's NBT
				NBTTagCompound tag = new NBTTagCompound();
				data.data.get(e.getChunk().getPos()).writeToNBT(tag);
				e.getData().setTag("hbmRadDataNT", tag);
			}
		}
	}
	
	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			//Always make sure worlds have radiation data
			worldMap.put(e.getWorld(), new WorldRadiationData(e.getWorld()));
		}
	}
	
	@SubscribeEvent
	public static void onWorldUnload(WorldEvent.Unload e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			//Remove the world data on unload
			worldMap.remove(e.getWorld());
		}
	}
	
	/**
	 * Updates the whole radiation system. This loops through every world's radiation data, and updates the value in each pocket.
	 * Pockets transfer some of their radiation to pockets they're connected to.
	 * It tries to do pretty much the same algorithm as the regular system, but in 3d with pockets.
	 */
	public static void updateRadiation() {
		long time = System.currentTimeMillis();
		//long lTime = System.nanoTime();
		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Started updating radiation");
		}

		for(WorldRadiationData w : worldMap.values()) {


			//Avoid concurrent modification
			List<RadPocket> itrActive = new ArrayList<>(w.getActivePockets());
			Iterator<RadPocket> itr = itrActive.iterator();
			while(itr.hasNext()) {
				RadPocket p = itr.next();
				BlockPos pos = p.parent.parent.getWorldPos(p.parent.yLevel);
				PlayerChunkMapEntry entry = ((WorldServer) w.world).getPlayerChunkMap().getEntry(p.parent.parent.chunk.x, p.parent.parent.chunk.z);
				if (entry == null || entry.getWatchingPlayers().isEmpty()) {
					//I shouldn't have to do this, but I ran into some issues with chunks not getting unloaded?
					//In any case, marking it for unload myself shouldn't cause any problems
					((WorldServer) w.world).getChunkProvider().queueUnload(p.parent.parent.chunk);
				}
				//Lower the radiation a bit, and mark the parent chunk as dirty so the radiation gets saved
				p.radiation *= 0.999F;
				p.radiation -= 0.05F;
				p.parent.parent.chunk.markDirty();
				if (p.radiation <= 0) {
					//If there's no more radiation and is unsealed, set it to 0 and remove
					p.radiation = 0;
					p.accumulatedRads = 0;
					itr.remove();
					p.parent.parent.chunk.markDirty();
					continue;
				}

				if (p.radiation > RadiationConfig.fogRad && w.world != null && w.world.rand.nextInt(RadiationConfig.fogCh) == 0) {
					//Fog calculation works slightly differently here to account for the 3d nature of the system
					//We just try 10 random coordinates of the sub chunk
					//If the coordinate is inside this pocket and the block at the coordinate is air,
					//use it to spawn a rad particle at that block and break
					//Also only spawn it if it's close to the ground, otherwise you get a giant fart when nukes go off.
					for (int i = 0; i < 10; i++) {
						BlockPos randPos = new BlockPos(w.world.rand.nextInt(16), w.world.rand.nextInt(16), w.world.rand.nextInt(16));
						if (p.parent.pocketsByBlock == null || p.parent.pocketsByBlock[randPos.getX() * 16 * 16 + randPos.getY() * 16 + randPos.getZ()] == p) {
							randPos = randPos.add(p.parent.parent.getWorldPos(p.parent.yLevel));
							IBlockState state = w.world.getBlockState(randPos);
							Vec3d rPos = new Vec3d(randPos.getX() + 0.5, randPos.getY() + 0.5, randPos.getZ() + 0.5);
							RayTraceResult trace = w.world.rayTraceBlocks(rPos, rPos.addVector(0, -6, 0));
							if (state.getBlock().isAir(state, w.world, randPos) && trace != null && trace.typeOfHit == Type.BLOCK) {
								PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(randPos.getX() + 0.5F, randPos.getY() + 0.5F, randPos.getZ() + 0.5F, 3), new TargetPoint(w.world.provider.getDimension(), randPos.getX(), randPos.getY(), randPos.getZ(), 100));
								break;
							}
						}
					}
				}

				//Count the number of connections to other pockets we have
				float count = 0;
				for (EnumFacing e : EnumFacing.VALUES) {
					count += p.connectionIndices[e.ordinal()].size();
				}
				float amountPer = 0.7F / count;
				if (count == 0 || p.radiation < 1) {
					//Don't update if we have no connections or our own radiation is less than 1. Prevents micro radiation bleeding.
					amountPer = 0;
				}

				if(GeneralConfig.enableDebugMode) {
					BlockPos chunkPos = new BlockPos(p.getSubChunkPos().getX() / 16, p.getSubChunkPos().getY() / 16, p.getSubChunkPos().getZ() / 16);
					MainRegistry.logger.info("[Debug] Pocket " + p.index + " has " + count + " connections to other pockets at chunk " + chunkPos);
					if (amountPer > 0) {
						MainRegistry.logger.info("[Debug] Pocket " + p.index + " will spread " + amountPer + " rads to each adjacent pocket");
					}
				}

				// TODO: This might also cause leaks from sealed pockets to unsealed
				if (p.radiation > 0 && amountPer > 0) {
					//Only update other values if this one has radiation to update with
					for (EnumFacing e : EnumFacing.VALUES) {
						//For every direction, get the block pos for the next sub chunk in that direction.
						BlockPos nPos = pos.offset(e, 16);

						//If it's not loaded or it's out of bounds, do nothhing
						if (!p.parent.parent.chunk.getWorld().isBlockLoaded(nPos) || nPos.getY() < 0 || nPos.getY() > 255)
							continue;

						if (p.connectionIndices[e.ordinal()].size() == 1 && p.connectionIndices[e.ordinal()].get(0) == -1) {
							//If the chunk in this direction isn't loaded, load it
							rebuildChunkPockets(p.parent.parent.chunk.getWorld().getChunkFromBlockCoords(nPos), nPos.getY() >> 4);
						} else {
							// Otherwise, for every pocket this chunk is connected to in this direction, add radiation to it;
							// also add those pockets to the active pockets set
							SubChunkRadiationStorage sc2 = getSubChunkStorage(p.parent.parent.chunk.getWorld(), nPos);
							for (int idx : p.connectionIndices[e.ordinal()]) {
								// Don't spread to sealed pockets
								if (!sc2.pockets[idx].isSealed()) {
									// Only accumulated rads get updated so the system doesn't interfere with itself while working
									sc2.pockets[idx].accumulatedRads += p.radiation * amountPer;
									w.addActivePocket(sc2.pockets[idx]);
								}
							}
						}
					}
				}
				if (amountPer != 0) {
					p.accumulatedRads += p.radiation * 0.3F;
				}
				//Make sure we only use around 20 ms max per tick, to help reduce lag.
				//The lag should die down by itself after a few minutes when all radioactive chunks get built.
				if (System.currentTimeMillis() - time > 20) {
					break;
				}
			}

			// Remove the ones that reached 0, and set the actual radiation values to the accumulated values
			// We don't remove sealed pockets so that dimensions with background rads can be shielded against
			List<RadPocket> itrActiveCheck = new ArrayList<>(w.getActivePockets());
			itr = itrActiveCheck.iterator();
			while(itr.hasNext()){
				RadPocket act = itr.next();
				act.radiation = act.accumulatedRads;
				act.accumulatedRads = 0;
				if(act.radiation <= 0) {
					w.removeActivePocket(act);
					itr.remove();
				}
			}
		}

		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Finished updating radiation");
		}

		//System.out.println(System.nanoTime()-lTime);
		//Should ideally never happen because of the 20 ms limit, 
		//but who knows, maybe it will, and it's nice to have debug output if it does
		if(System.currentTimeMillis()-time > 50){
			System.out.println("Rads took too long: " + (System.currentTimeMillis()-time));
		}
	}
	
	//Reduces array reallocations
	private static RadPocket[] pocketsByBlock = null;
	
	/**
	 * Divides a 16x16x16 sub chunk into pockets that are separated by radiation resistant blocks.
	 * These pockets are also linked to other pockets in neighboring chunks
	 * @param chunk - the chunk to rebuild
	 * @param yIndex - the Y index of the sub chunk to rebuild
	 */
	private static void rebuildChunkPockets(Chunk chunk, int yIndex){
		//long ms = System.currentTimeMillis();
		//long ns = System.nanoTime();
		BlockPos subChunkPos = new BlockPos(chunk.getPos().x << 4, yIndex << 4, chunk.getPos().z << 4);

		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Starting rebuild of chunk at " + new BlockPos(chunk.getPos().x, yIndex, chunk.getPos().z));
		}

		//Initialize all the necessary variables. A list of pockets for the sub chunk, the block storage for this sub chunk,
		//an array of rad pockets for fast pocket lookup by blockpos, chunk radiation storage for this position
		//And finally a new sub chunk that will be added to the chunk radiation storage when it's filled with data
		List<RadPocket> pockets = new ArrayList<>();
		ExtendedBlockStorage blocks = chunk.getBlockStorageArray()[yIndex];
		if(pocketsByBlock == null) {
			pocketsByBlock = new RadPocket[16*16*16];
		} else {
			Arrays.fill(pocketsByBlock, null);
		}
		ChunkRadiationStorage st = getChunkStorage(chunk.getWorld(), subChunkPos);
		SubChunkRadiationStorage subChunk = new SubChunkRadiationStorage(st, subChunkPos.getY(), null, null);
		
		if (blocks != null) {
			//Loop over every block in the sub chunk
			for(int x = 0; x < 16; x ++){
				for(int y = 0; y < 16; y ++){
					for(int z = 0; z < 16; z ++){
						if(pocketsByBlock[x*16*16+y*16+z] != null)
							continue;

						Block block = blocks.get(x, y, z).getBlock();

						// If it's not a radiation resistant block, and there isn't currently a pocket here,
						// do a flood fill pocket build
						if (!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).isRadResistant(chunk.getWorld(), new BlockPos(x, y, z).add(subChunkPos)))) {
							if (GeneralConfig.enableDebugMode) {
								MainRegistry.logger.info("[Debug] Block " + block + " at " + new BlockPos(x, y, z).add(subChunkPos) + " was not rad resistant; add pocket");
							}
							pockets.add(buildPocket(subChunk, chunk.getWorld(), new BlockPos(x, y, z), subChunkPos, blocks, pocketsByBlock, pockets.size()));
						}
					}
				}
			}
		} else {
			//Behold, duplicated code with 4 for loop nests because I couldn't be bothered to figure out a good way to change another method.
			RadPocket pocket = new RadPocket(subChunk, 0);
			//Absolute garbage code
			//All it's supposed to do is loop around the edges of the chunk and connect pockets in neighboring chunks to this one
			/*for(EnumFacing facing : EnumFacing.VALUES){
				for(int x = 0; x < 16; x ++){
					for(int y = 0; y < 16; y ++){
						for(int z = 0; z < 16; z ++){
							BlockPos newPos = new BlockPos(x, y, z).offset(facing);
							//If this position is on the outside of the chunk...
							if(Math.max(Math.max(newPos.getX(), newPos.getY()), newPos.getZ()) > 15 || Math.min(Math.min(newPos.getX(), newPos.getY()), newPos.getZ()) < 0){
								BlockPos outPos = newPos.add(subChunkPos);
								Block block = chunk.getWorld().getBlockState(outPos).getBlock();
								//If the block isn't radiation resistant...
								if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).isRadResistant())){
									if(!isSubChunkLoaded(chunk.getWorld(), outPos)){
										//if it's not loaded, mark it with a single -1 value. This will tell the update method that the
										//Chunk still needs to be loaded to propagate radiation into it
										if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
											pocket.connectionIndices[facing.ordinal()].add(-1);
										}
									} else {
										//If it is loaded, see if the pocket at that position is already connected to us. If not, add it as a connection.
										//Setting outPocket's connection will be handled in setForYLevel
										RadPocket outPocket = getPocket(chunk.getWorld(), outPos);
										if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
											pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
									}
								}
							}
						}
					}
				}
			}*/
			for(int x = 0; x < 16; x ++){
				for(int y = 0; y < 16; y ++){
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, 0, y), pocket, EnumFacing.DOWN);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, 15, y), pocket, EnumFacing.UP);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, y, 0), pocket, EnumFacing.NORTH);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(x, y, 15), pocket, EnumFacing.SOUTH);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(0, y, x), pocket, EnumFacing.WEST);
					doEmptyChunk(chunk, subChunkPos, new BlockPos(15, y, x), pocket, EnumFacing.EAST);
				}
			}
			pockets.add(pocket);
		}
		//If there's only one pocket, we don't need to waste memory by storing a whole 16x16x16 array, so just store null.
		subChunk.pocketsByBlock = pockets.size() == 1 ? null : pocketsByBlock;

		if (GeneralConfig.enableDebugMode) {
			if (pockets.size() == 1) {
				MainRegistry.logger.info("[Debug] There was only a single pocket for subchunk at " + new BlockPos(chunk.getPos().x, yIndex, chunk.getPos().z));
			} else {
				MainRegistry.logger.info("[Debug] There was " + pockets.size() + " pockets for subchunk at " + new BlockPos(chunk.getPos().x, yIndex, chunk.getPos().z));
			}
		}

		if(subChunk.pocketsByBlock != null)
			pocketsByBlock = null;
		subChunk.pockets = pockets.toArray(new RadPocket[pockets.size()]);

		//Finally, put the newly built sub chunk into the chunk
		st.setForYLevel(yIndex << 4, subChunk);

		if(GeneralConfig.enableDebugMode) {
			MainRegistry.logger.info("[Debug] Finished rebuild of chunk at " + new BlockPos(chunk.getPos().x, yIndex, chunk.getPos().z));
		}

		//System.out.println(System.currentTimeMillis()-ms);
		//System.out.println("b " + (System.nanoTime()-ns));
	}
	
	private static void doEmptyChunk(Chunk chunk, BlockPos subChunkPos, BlockPos pos, RadPocket pocket, EnumFacing facing){
		//long l = System.nanoTime();
		BlockPos newPos = pos.offset(facing);
		BlockPos outPos = newPos.add(subChunkPos);
		Block block = chunk.getWorld().getBlockState(outPos).getBlock();
		//If the block isn't radiation resistant...
		if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).isRadResistant(chunk.getWorld(), outPos))){
			if(!isSubChunkLoaded(chunk.getWorld(), outPos)){
				//if it's not loaded, mark it with a single -1 value. This will tell the update method that the
				//Chunk still needs to be loaded to propagate radiation into it
				if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
					pocket.connectionIndices[facing.ordinal()].add(-1);
				}
			} else {
				//If it is loaded, see if the pocket at that position is already connected to us. If not, add it as a connection.
				//Setting outPocket's connection will be handled in setForYLevel
				
				RadPocket outPocket = getPocket(chunk.getWorld(), outPos);
				if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
					pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
			}
		}
		//System.out.println(System.nanoTime()-l);
	}
	
	//To reduce a lot of reallocations
	private static Queue<BlockPos> stack = new ArrayDeque<>(1024);
	
	/**
	 * Builds a pocket using a flood fill.
	 * @param subChunk - sub chunk to build a pocket in
	 * @param world - world we're building in
	 * @param start - the block pos to flood fill from
	 * @param subChunkWorldPos - the world position of the sub chunk
	 * @param chunk - the block storage to pull blocks from
	 * @param pocketsByBlock - the array to populate with the flood fill
	 * @param index - the current pocket number
	 * @return a new rad pocket made from the flood fill data
	 */
	private static RadPocket buildPocket(SubChunkRadiationStorage subChunk, World world, BlockPos start, BlockPos subChunkWorldPos, ExtendedBlockStorage chunk, RadPocket[] pocketsByBlock, int index){
		//Create the new pocket we're going to use
		RadPocket pocket = new RadPocket(subChunk, index);

		if(GeneralConfig.enableDebugMode) {
			BlockPos chunkPos = new BlockPos(pocket.getSubChunkPos().getX() / 16, pocket.getSubChunkPos().getY() / 16, pocket.getSubChunkPos().getZ() / 16);
			MainRegistry.logger.info("[Debug] Starting build of pocket of index " + index + " for chunk at " + chunkPos + ", at local position " + start);
		}

		//Just to make sure...
		stack.clear();
		stack.add(start);
		//Do the flood fill
		while(!stack.isEmpty()){
			BlockPos pos = stack.poll();
			Block block = chunk.get(pos.getX(), pos.getY(), pos.getZ()).getBlock();
			if(pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] != null || (block instanceof IRadResistantBlock && ((IRadResistantBlock) block).isRadResistant(world, pos.add(subChunkWorldPos)))){
				//If the block is radiation resistant or we've already flood filled here, continue
				continue;
			}
			//Set the current position in the array to be this pocket
			pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] = pocket;
			//For each direction...
			for(EnumFacing facing : EnumFacing.VALUES){
				BlockPos newPos = pos.offset(facing);
				if(Math.max(Math.max(newPos.getX(), newPos.getY()), newPos.getZ()) > 15 || Math.min(Math.min(newPos.getX(), newPos.getY()), newPos.getZ()) < 0){
					//If we're outside the sub chunk bounds, try to connect to neighbor chunk pockets
					BlockPos outPos = newPos.add(subChunkWorldPos);
					//If this position is out of bounds, do nothing
					if(outPos.getY() < 0 || outPos.getY() > 255)
						continue;
					//Will also attempt to load the chunk, which will cause neighbor data to be updated correctly if it's unloaded.
					block = world.getBlockState(outPos).getBlock();
					//If the block isn't radiation resistant...
					if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).isRadResistant(world, outPos))){
						if(!isSubChunkLoaded(world, outPos)){
							//if it's not loaded, mark it with a single -1 value. This will tell the update method that the
							//Chunk still needs to be loaded to propagate radiation into it
							if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
								pocket.connectionIndices[facing.ordinal()].add(-1);
							}
						} else {
							//If it is loaded, see if the pocket at that position is already connected to us. If not, add it as a connection.
							//Setting outPocket's connection will be handled in setForYLevel
							RadPocket outPocket = getPocket(world, outPos);
							if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
								pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
						}
					}
					continue;
				}
				//Add the new position onto the stack, to be flood fill checked later
				stack.add(newPos);
			}
		}

		if(GeneralConfig.enableDebugMode) {
			BlockPos chunkPos = new BlockPos(pocket.getSubChunkPos().getX() / 16, pocket.getSubChunkPos().getY() / 16, pocket.getSubChunkPos().getZ() / 16);
			MainRegistry.logger.info("[Debug] Finished build of pocket of index " + index + " for chunk at " + chunkPos + ", at local position " + start);
		}

		return pocket;
	}
	
	/*
	 * And finally, the data structure part.
	 * The hierarchy goes like this:
	 * WorldRadiationData - Stores ChunkRadiationStorages, one per chunk. Also keeps dirty chunks that need to be rebuilt and a set of active rad pockets
	 * 		ChunkRadiationStorage - Stores an array of SubChunkRadiationStorage, one for each 16 tall section.
	 * 			SubChunkRadiationStorage - Stores and array of RadPockets as well as a larger array representing the RadPocket in each position in the sub chunk
	 * 				RadPocket - Stores the actual radiation value as well as connections to neighboring RadPockets by indices
	 */
	
	//A list of pockets completely closed off by radiation resistant blocks
	public static class RadPocket {
		public SubChunkRadiationStorage parent;
		public int index;
		public float radiation;
		//Used internally so the system doesn't interfere with itself when updating
		private float accumulatedRads = 0;
		//If an array contains -1, that means the chunk on that side hasn't been initialized, so it's an implicit connection
		@SuppressWarnings("unchecked")
		public List<Integer>[] connectionIndices = new List[EnumFacing.VALUES.length];
		
		public RadPocket(SubChunkRadiationStorage parent, int index) {
			this.parent = parent;
			this.index = index;
			//Initialize the connections in each direction
			for(int i = 0; i < EnumFacing.VALUES.length; i ++){
				connectionIndices[i] = new ArrayList<>(1);
			}
		}
		
		/**
		 * Mainly just removes itself from the active pockets list
		 * @param world - the world to remove from (unused)
		 * @param pos - the pos to remove from (also unused)
		 */
		protected void remove(World world, BlockPos pos){
			for(EnumFacing e : EnumFacing.VALUES){
				connectionIndices[e.ordinal()].clear();
			}
			parent.parent.parent.removeActivePocket(this);
		}

		/**
		 * @return the world position of the sub chunk this pocket is in
		 */
		public BlockPos getSubChunkPos() {
			return parent.parent.getWorldPos(parent.yLevel);
		}

		/**
		 * Checks if a pocket is radiation shielded against other pockets or chunks
		 * @return if pocket is sealed
		 */
		public boolean isSealed() {
			// Sealed pockets should have no connects to other chunks (-1) or other pockets
			float count = 0;
			for (EnumFacing e : EnumFacing.VALUES) {
				count += this.connectionIndices[e.ordinal()].size();
			}

			return (count == 0);
		}
	}
	
	//the smaller 16*16*16 chunk
	public static class SubChunkRadiationStorage {
		public ChunkRadiationStorage parent;
		public int yLevel;
		//If it's null, that means there's only 1 pocket, which will be most chunks, so this saves memory.
		public RadPocket[] pocketsByBlock;
		public RadPocket[] pockets;
		
		public SubChunkRadiationStorage(ChunkRadiationStorage parent, int yLevel, RadPocket[] pocketsByBlock, RadPocket[] pockets) {
			this.parent = parent;
			this.yLevel = yLevel;
			this.pocketsByBlock = pocketsByBlock;
			this.pockets = pockets;
		}
				
		/**
		 * Gets the pocket at the position
		 * @param pos - the position to get the pocket at
		 * @return the pocket at the specified position, or the first pocket if it doesn't exist
		 */
		public RadPocket getPocket(BlockPos pos){
            if(pocketsByBlock == null){
                //If pocketsByBlock is null, there's only one pocket anyway
                return pockets[0];
            } else {
                int x = pos.getX()&15;
                int y = pos.getY()&15;
                int z = pos.getZ()&15;
                RadPocket p = pocketsByBlock[x*16*16+y*16+z];
                if (p == null) {
                    if (pockets != null && pockets.length > 0 && pockets[0] != null) {
                        // If for whatever reason there isn't a pocket there, return the first pocket as a fallback if present
                        return pockets[0];
                    } else {
                        // If first pocket isn't present either, create one and warn
                        p = new RadPocket(this, 0);
                        p.radiation = 0;
                        if (pockets == null || pockets.length == 0) {
                            pockets = new RadPocket[1];
                        }
                        pockets[0] = p;
                        return p;
                    }
                } else {
                    return p;
                }
            }
        }
		
		/**
		 * Attempts to distribute radiation from another sub chunk into this one's pockets.
		 * @param other - the sub chunk to set from
		 */
		public void setRad(SubChunkRadiationStorage other){
			//Accumulate a total, and divide that evenly among our pockets
			float total = 0;
			for(RadPocket p : other.pockets) {
				// Sealed pockets should not attribute to total rad count
				if (!p.isSealed()) {
					total += p.radiation;
				}
			}

			float radPer = total / pockets.length;
			for(RadPocket p : pockets){
				p.radiation = radPer;
				if(radPer > 0) {
					//If the pocket now has radiation or is sealed, mark it as active
					p.parent.parent.parent.addActivePocket(p);
				}
			}
		}
		
		/**
		 * Remove from the world
		 * @param world - the world to remove from
		 * @param pos - the pos to remove from
		 */
		public void remove(World world, BlockPos pos){
			for(RadPocket p : pockets){
				//Call remove for each pocket
				p.remove(world, pos);
			}
			for(EnumFacing e : EnumFacing.VALUES){
				//Tries to load the chunk so it updates right.
				world.getBlockState(pos.offset(e, 16));
				if(isSubChunkLoaded(world, pos.offset(e, 16))){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, pos.offset(e, 16));
					//Clears any connections the neighboring chunk has to this sub chunk
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
				}
			}
		}
		
		/**
		 * Adds to the world
		 * @param world - the world to add to
		 * @param pos - the position to add to
		 */
		public void add(World world, BlockPos pos){
			for(EnumFacing e : EnumFacing.VALUES){
				//Tries to load the chunk so it updates right.
				world.getBlockState(pos.offset(e, 16));
				if(isSubChunkLoaded(world, pos.offset(e, 16))){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, pos.offset(e, 16));
					//Clear all the neighbor's references to this sub chunk
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
					//Sync connections to the neighbor to make it two way
					for(RadPocket p : pockets){
						List<Integer> indc = p.connectionIndices[e.ordinal()];
						for(int idx : indc){
							sc.pockets[idx].connectionIndices[e.getOpposite().ordinal()].add(p.index);
						}
					}
				}
			}
		}
	}
	
	//for a whole 16*256*16 chunk
	public static class ChunkRadiationStorage {
		//Half a megabyte is good enough isn't it? Right?
		//This is going to come back to bite me later, isn't it.
		private static ByteBuffer buf = ByteBuffer.allocate(524288);
		
		public WorldRadiationData parent;
		private Chunk chunk;
		private SubChunkRadiationStorage[] chunks = new SubChunkRadiationStorage[16];
		
		public ChunkRadiationStorage(WorldRadiationData parent, Chunk chunk) {
			this.parent = parent;
			this.chunk = chunk;
		}
		
		/**
		 * Gets the sub chunk for the specified y coordinate
		 * @param y - the y coordinate of the sub chunk
		 * @return the sub chunk at the y coordinate
		 */
		public SubChunkRadiationStorage getForYLevel(int y){
			//Bit shift it by 4 to get the index, because each one is 16 high.
			int idx = y >> 4;
			//if the index is out of range, return null, else return the chunk at the index
			if(idx < 0 || idx > chunks.length){
				return null;
			}
			return chunks[y >> 4];
		}
		
		/**
		 * Gets the world position of this chunk, using the specified y coordinate
		 * @param y - the y coordinate for the returned position
		 * @return a blockpos with x and z from the chunk and y from the parameter
		 */
		public BlockPos getWorldPos(int y){
			return new BlockPos(chunk.getPos().x << 4, y, chunk.getPos().z << 4);
		}
		
		/**
		 * Sets the sub chunk at the y level to the new sub chunk
		 * @param y - the y level to set
		 * @param sc - the new sub chunk
		 */
		public void setForYLevel(int y, SubChunkRadiationStorage sc){
			if(chunks[y >> 4] != null){
				//If there's already a sub chunk there, make sure to remove it from the world safely.
				chunks[y >> 4].remove(chunk.getWorld(), getWorldPos(y));
				//If we're not nullifying it, set the new chunk's radiation to preserve rads
				if(sc != null)
					sc.setRad(chunks[y >> 4]);
			}
			//If we're not nullifying it, add it to the world to update neighboring chunks and stuff like that
			if(sc != null){
				sc.add(chunk.getWorld(), getWorldPos(y));
			}
			chunks[y >> 4] = sc;
		}
		
		/**
		 * Removes all active pockets on unload
		 */
		public void unload(){
			for(int y = 0; y < chunks.length; y ++){
				if(chunks[y] == null)
					continue;
				for(RadPocket p : chunks[y].pockets){
					parent.removeActivePocket(p);
				}
				chunks[y] = null;
			}
		}
		
		/**
		 * Serializes the chunk data to an NBT tag
		 * @param tag - the tag to write to
		 * @return the tag written to
		 */
		public NBTTagCompound writeToNBT(NBTTagCompound tag){
			for(SubChunkRadiationStorage st : chunks){
				if(st == null){
					buf.put((byte) 0);
				} else {
					buf.put((byte) 1);
					buf.putShort((short) st.yLevel);
					//Write stored pockets
					buf.putShort((short)st.pockets.length);
					for(RadPocket p : st.pockets){
						writePocket(buf, p);
					}
					//If it's null, mark as 0, else mark as 1 and write the index of each pocket in the array sequentially
					if(st.pocketsByBlock == null){
						buf.put((byte) 0);
					} else {
						buf.put((byte) 1);
						for(RadPocket p : st.pocketsByBlock){
							buf.putShort(arrayIndex(p, st.pockets));
						}
					}
				}
			}
			//I decided to use a ByteBuffer instead of straight NBT data for this
			//because I can be more data efficient if I don't have a bunch of string tags weighing me down
			//For every little bit of data
			buf.flip();
			byte[] data = new byte[buf.limit()];
			buf.get(data);
			tag.setByteArray("chunkRadData", data);
			buf.clear();
			return tag;
		}
		
		/**
		 * Gets the index of the rad pocket in the array of pockets
		 * There's probably a helper method for this in the Arrays class or something, but this works fine too
		 * @param p - the pocket to find the index of
		 * @param pockets - the array to search in
		 * @return the index of the pocket in the array, -1 if not present
		 */
		public short arrayIndex(RadPocket p, RadPocket[] pockets){
			for(short i = 0; i < pockets.length; i ++){
				if(p == pockets[i])
					return i;
			}
			return -1;
		}
		
		/**
		 * Writes a single pocket to a ByteBuffer
		 * @param buf - the buffer to write to
		 * @param p - the pocket to write data from
		 */
		public void writePocket(ByteBuffer buf, RadPocket p){
			//Serialize index and radiation
			buf.putInt(p.index);
			buf.putFloat(p.radiation);
			//For each facing, serialize the indices in that direction
			for(EnumFacing e : EnumFacing.VALUES){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				buf.putShort((short) indc.size());
				for(int idx : indc){
					buf.putShort((short) idx);
				}
			}
		}
		
		/**
		 * Deserializes from NBT
		 * @param tag - the tag to deserialize from
		 */
		public void readFromNBT(NBTTagCompound tag){
			ByteBuffer data = ByteBuffer.wrap(tag.getByteArray("chunkRadData"));
			//For each chunk, try to deserialize it
			for(int i = 0; i < chunks.length; i ++){
				boolean subChunkExists = data.get() == 1 ? true : false;
				if(subChunkExists){
					//Y level could be implicitly defined with i, but this works too
					int yLevel = data.getShort();
					//Create the new sub chunk, don't pass any data into it because we'll set that manually
					SubChunkRadiationStorage st = new SubChunkRadiationStorage(this, yLevel, null, null);
					int pocketsLength = data.getShort();
					st.pockets = new RadPocket[pocketsLength];
					//Deserialize each pocket into the pockets array
					for(int j = 0; j < pocketsLength; j ++){
						st.pockets[j] = readPocket(data, st);
						if(st.pockets[j].radiation > 0){
							//If it has active radiation, add it to the active set to be updated
							parent.addActivePocket(st.pockets[j]);
						}
					}
					boolean perBlockDataExists = data.get() == 1 ? true : false;
					if(perBlockDataExists){
						//If the per block data exists, read indices sequentially and set each array slot to the rad pocket at that index
						st.pocketsByBlock = new RadPocket[16*16*16];
						for(int j = 0; j < 16*16*16; j ++){
							int idx = data.getShort();
							if(idx >= 0)
								st.pocketsByBlock[j] = st.pockets[idx];
						}
					}
					chunks[i] = st;
				} else {
					chunks[i] = null;
				}
			}
		}
		
		/**
		 * Reads a single pocket from a byte buffer
		 * @param buf - the buffer to read from
		 * @param parent - the sub chunk that owns the pocket getting deserialized
		 * @return the deserialized rad pocket
		 */
		public RadPocket readPocket(ByteBuffer buf, SubChunkRadiationStorage parent){
			//Read core data and create the new pocket
			int index = buf.getInt();
			RadPocket p = new RadPocket(parent, index);
			p.radiation = buf.getFloat();
			//Read each connection index list
			for(EnumFacing e : EnumFacing.VALUES){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				int size = buf.getShort();
				for(int i = 0; i < size; i ++){
					indc.add((int) buf.getShort());
				}
			}
			return p;
		}
	}
	
	//For a world's radiation data, contains a bunch of chunk data blocks
	public static class WorldRadiationData {
		public World world;

		//Keep two lists to avoid concurrent modification. If one is being iterated over, mark it dirty in the other set.
		private Set<BlockPos> dirtyChunks = new HashSet<>();
		private Set<BlockPos> dirtyChunks2 = new HashSet<>();
		private boolean iteratingDirty = false;
		
		//Active pockets are the pockets that have radiation in them and so then need to be updated
		private Set<RadPocket> activePockets = new HashSet<>();
		public Map<ChunkPos, ChunkRadiationStorage> data = new HashMap<>();
		
		public WorldRadiationData(World world) {
			this.world = world;
		}

		public Set<RadPocket> getActivePockets() {
			if(GeneralConfig.enableDebugMode) {
				MainRegistry.logger.info("[Debug] Queried active pockets for world " + world);
			}
			return this.activePockets;
		}

		public void addActivePocket(RadPocket radPocket) {
			this.activePockets.add(radPocket);
			if(GeneralConfig.enableDebugMode) {
				BlockPos chunkPos = new BlockPos(radPocket.getSubChunkPos().getX() / 16, radPocket.getSubChunkPos().getY() / 16, radPocket.getSubChunkPos().getZ() / 16);
				MainRegistry.logger.info("[Debug] Added active pocket " + radPocket.index + " (radiation: " + radPocket.radiation + ", accumulatedRads: " + radPocket.accumulatedRads + ", sealed: " + radPocket.isSealed() + ") at " + radPocket.getSubChunkPos() + " (Chunk:" + chunkPos + ") for world " + world);
			}
		}

		public void removeActivePocket(RadPocket radPocket) {
			this.activePockets.remove(radPocket);
			if(GeneralConfig.enableDebugMode) {
				BlockPos chunkPos = new BlockPos(radPocket.getSubChunkPos().getX() / 16, radPocket.getSubChunkPos().getY() / 16, radPocket.getSubChunkPos().getZ() / 16);
				MainRegistry.logger.info("[Debug] Removed active pocket " + radPocket.index + " (radiation: " + radPocket.radiation + ", accumulatedRads: " + radPocket.accumulatedRads + ", sealed: " + radPocket.isSealed() + ") at " + radPocket.getSubChunkPos() + " (Chunk:" + chunkPos + ") for world " + world);

			}
		}

		public void clearActivePockets() {
			this.activePockets.clear();
			if(GeneralConfig.enableDebugMode) {
				MainRegistry.logger.info("[Debug] Cleared active pockets for world " + world);
			}
		}
	}
}
