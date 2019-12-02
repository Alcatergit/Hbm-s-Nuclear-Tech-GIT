package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.RadSurveyPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadEntitySavedData;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ModEventHandler {

	@SubscribeEvent
	public void soundRegistering(RegistryEvent.Register<SoundEvent> evt){
		HBMSoundHandler.init();
		for(SoundEvent e : HBMSoundHandler.ALL_SOUNDS){
			evt.getRegistry().register(e);
		}
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event){
		if(event.world != null && !event.world.isRemote && MainRegistry.enableRads) {
int thunder = AuxSavedData.getThunder(event.world);
			
			if(thunder > 0)
				AuxSavedData.setThunder(event.world, thunder - 1);
			
			if(!event.world.loadedEntityList.isEmpty()) {

				RadiationSavedData data = RadiationSavedData.getData(event.world);
				RadEntitySavedData eData = RadEntitySavedData.getData(event.world);
				
				if(eData.worldObj == null) {
					eData.worldObj = event.world;
				}
				
				if(data.worldObj == null) {
					data.worldObj = event.world;
				}
				
				for(Object o : event.world.playerEntities) {
					
					EntityPlayer player = (EntityPlayer)o;
					PacketDispatcher.wrapper.sendTo(new RadSurveyPacket(eData.getRadFromEntity(player)), (EntityPlayerMP) player);
				}
				
				if(event.world.getTotalWorldTime() % 20 == 0) {
					data.updateSystem();
				}
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						
						//effect for radiation
						EntityLivingBase entity = (EntityLivingBase) e;

						if(event.world.getTotalWorldTime() % 20 == 0) {

							Chunk chunk = entity.world.getChunkFromBlockCoords(new BlockPos(entity.posX, entity.posY, entity.posZ));
							float rad = data.getRadNumFromCoord(chunk.x, chunk.z);
							
							if(rad > 0) {
								if(!entity.isPotionActive(HbmPotion.mutation))
									Library.applyRadData(entity, rad / 2);
							}
							
							if(entity.world.isRaining() && MainRegistry.cont > 0 && AuxSavedData.getThunder(entity.world) > 0 &&
								entity.world.canBlockSeeSky(new BlockPos(entity))) {
								
								if(!entity.isPotionActive(HbmPotion.mutation)) {
									Library.applyRadData(entity, MainRegistry.cont * 0.005F);
								}
							}
						}
						
						float eRad = eData.getRadFromEntity(entity);
						
						if(entity instanceof EntityCreeper && eRad >= 200 && entity.getHealth() > 0) {
							
							if(event.world.rand.nextInt(3) == 0 ) {
								EntityNuclearCreeper creep = new EntityNuclearCreeper(event.world);
								creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
				        		
				        		if(!entity.isDead)
				        			if(!event.world.isRemote)
				        				event.world.spawnEntity(creep);
				        		entity.setDead();
							} else {
								entity.attackEntityFrom(ModDamageSource.radiation, 100F);
							}
							continue;
		        		
			        	} else if(entity instanceof EntityCow && !(entity instanceof EntityMooshroom) && eRad >= 50) {
			        		EntityMooshroom creep = new EntityMooshroom(event.world);
			        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

			        		if(!entity.isDead)
			        			if(!event.world.isRemote)
			        				event.world.spawnEntity(creep);
			        		entity.setDead();
							continue;
			        		
			        	} else if(entity instanceof EntityVillager && eRad >= 500) {
			        		EntityZombie creep = new EntityZombie(event.world);
			        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
			        		
			        		if(!entity.isDead)
				        		if(!event.world.isRemote)
				        			event.world.spawnEntity(creep);
			        		entity.setDead();
							continue;
			        	}
						
						if(eRad < 200/* || entity instanceof EntityNuclearCreeper */|| entity instanceof EntityMooshroom || entity instanceof EntityZombie || entity instanceof EntitySkeleton)
							continue;
						
						if(eRad >= 1000) {
							if(entity.attackEntityFrom(ModDamageSource.radiation, 1000))
								eData.setRadForEntity(entity, 0);
						} else if(eRad >= 800) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10 * 20, 2));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 3 * 20, 2));
				        	if(event.world.rand.nextInt(700) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 3 * 20, 1));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 5 * 20, 3));
							
						} else if(eRad >= 600) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10 * 20, 2));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 3 * 20, 1));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 3));
							
						} else if(eRad >= 400) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 30, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, 0));
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 1));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 2));
				        	
						} else if(eRad >= 200) {
				        	if(event.world.rand.nextInt(300) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
				        	if(event.world.rand.nextInt(500) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5 * 20, 0));
				        	if(event.world.rand.nextInt(700) == 0)
				            	entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 3 * 20, 2));
						}
						
						
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event){
		RadEntitySavedData eData = RadEntitySavedData.getData(event.getEntityLiving().world);
		eData.setRadForEntity(event.getEntityLiving(), 0);
		if(MainRegistry.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.getEntity().world);
			foeq.setPositionAndRotation(event.getEntity().posX, 500, event.getEntity().posZ, 0.0F, 0.0F);
			event.getEntity().world.spawnEntity(foeq);
		}
	}
	
	//TODO should probably use these bois
	
	@SubscribeEvent
	public void onItemRegister(RegistryEvent.Register<Item> evt){
	}
	@SubscribeEvent
	public void onBlockRegister(RegistryEvent.Register<Block> evt){
	}
	
	
}
