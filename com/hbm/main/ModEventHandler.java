package com.hbm.main;

import java.util.ArrayList;
import java.util.List;

import com.hbm.capability.RadiationCapability;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AssemblerRecipeSyncPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.RadSurveyPacket;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
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
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;

public class ModEventHandler {

	public static final ResourceLocation ENT_RAD_LOC = new ResourceLocation(RefStrings.MODID, "RADIATION");
	
	@SubscribeEvent
	public void soundRegistering(RegistryEvent.Register<SoundEvent> evt){
		HBMSoundHandler.init();
		for(SoundEvent e : HBMSoundHandler.ALL_SOUNDS){
			evt.getRegistry().register(e);
		}
	}
	
	@SubscribeEvent
	public void attachRadCap(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof EntityLivingBase)
			e.addCapability(ENT_RAD_LOC, new RadiationCapability.EntityRadiationProvider());
	}
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event){
		if(event.world != null && !event.world.isRemote && MainRegistry.enableRads) {
			int thunder = AuxSavedData.getThunder(event.world);
			
			if(thunder > 0)
				AuxSavedData.setThunder(event.world, thunder - 1);
			
			if(!event.world.loadedEntityList.isEmpty()) {

				RadiationSavedData data = RadiationSavedData.getData(event.world);
				
				if(data.worldObj == null) {
					data.worldObj = event.world;
				}
				
				for(Object o : event.world.playerEntities) {
					
					EntityPlayer player = (EntityPlayer)o;
					if(player.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
						PacketDispatcher.wrapper.sendTo(new RadSurveyPacket(player.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).getRads()), (EntityPlayerMP) player);
					else
						PacketDispatcher.wrapper.sendTo(new RadSurveyPacket(0.0F), (EntityPlayerMP) player);
				}
				
				if(event.world.getTotalWorldTime() % 20 == 0) {
					data.updateSystem();
				}
				
				List<Object> oList = new ArrayList<Object>();
				oList.addAll(event.world.loadedEntityList);
				
				for(Object e : oList) {
					if(e instanceof EntityLivingBase) {
						RadiationCapability.IEntityRadioactive entRad = null;
						if(((EntityLivingBase)e).hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)){
							entRad = ((EntityLivingBase)e).getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
						} else {
							continue;
						}
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
						
						float eRad = entRad.getRads();
						
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
								entRad.setRads(0);
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
	
	//Drillgon200: So 1.12.2's going to ignore ISpecialArmor if the damage is unblockable, huh?
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent e){
		if(e.getEntityLiving() instanceof EntityPlayer && e.getSource().isUnblockable()){
			if(Library.checkArmor((EntityPlayer)e.getEntityLiving(), ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)){
				e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event){
		if(event.getEntity().hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
			event.getEntity().getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).setRads(0.0F);;
		if(MainRegistry.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.getEntity().world);
			foeq.setPositionAndRotation(event.getEntity().posX, 500, event.getEntity().posZ, 0.0F, 0.0F);
			event.getEntity().world.spawnEntity(foeq);
		}
	}
	
	@SubscribeEvent
	public void clientJoinServer(PlayerLoggedInEvent e){
		if(e.player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new AssemblerRecipeSyncPacket(ItemAssemblyTemplate.recipes), (EntityPlayerMP) e.player);
	}
	
	//TODO should probably use these.
	
	@SubscribeEvent
	public void onItemRegister(RegistryEvent.Register<Item> evt){
	}
	@SubscribeEvent
	public void onBlockRegister(RegistryEvent.Register<Block> evt){
	}
	@SubscribeEvent
	public void onRecipeRegister(RegistryEvent.Register<IRecipe> evt){
	}
	
}
