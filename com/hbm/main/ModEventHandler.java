package com.hbm.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.capability.RadiationCapability;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.projectile.EntityBurningFOEQ;
import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.forgefluid.FFPipeNetwork;
import com.hbm.handler.MissileStruct;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.registries.DataSerializerEntry;

public class ModEventHandler {

	public static final ResourceLocation ENT_RAD_LOC = new ResourceLocation(RefStrings.MODID, "RADIATION");

	public static boolean showMessage = true;
	public static int meteorShower = 0;
	public static Random rand = new Random();

	@SubscribeEvent
	public void soundRegistering(RegistryEvent.Register<SoundEvent> evt) {

		for(SoundEvent e : HBMSoundHandler.ALL_SOUNDS) {
			evt.getRegistry().register(e);
		}
	}

	@SubscribeEvent
	public void attachRadCap(AttachCapabilitiesEvent<Entity> e) {
		if(e.getObject() instanceof EntityLivingBase)
			e.addCapability(ENT_RAD_LOC, new RadiationCapability.EntityRadiationProvider());
	}

	@SubscribeEvent
	public void worldUnload(WorldEvent.Unload e) {
		Iterator<FFPipeNetwork> itr = MainRegistry.allPipeNetworks.iterator();
		while(itr.hasNext()) {
			FFPipeNetwork net = itr.next();
			if(net.getNetworkWorld() == e.getWorld()) {
				net.destroySoft();
				itr.remove();
			}
		}
	}

	@SubscribeEvent
	public void enteringChunk(EnteringChunk evt) {
		if(evt.getEntity() instanceof IChunkLoader) {
			((IChunkLoader) evt.getEntity()).loadNeighboringChunks(evt.getNewChunkX(), evt.getNewChunkZ());
		}
	}

	@SubscribeEvent
	public void mobSpawn(LivingSpawnEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		World world = event.getWorld();

		if(entity instanceof EntityZombie) {
			if(rand.nextInt(64) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.gas_mask_m65, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.gas_mask, 1, world.rand.nextInt(100)));
			if(rand.nextInt(256) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.mask_of_infamy, 1, world.rand.nextInt(100)));

			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.pipe_lead, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.reer_graar, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.pipe_rusty, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.crowbar, 1, world.rand.nextInt(100)));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.geiger_counter, 1));
			if(rand.nextInt(128) == 0)
				entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.steel_pickaxe, 1, world.rand.nextInt(300)));
		}
		if(entity instanceof EntitySkeleton) {
			if(rand.nextInt(16) == 0) {
				entity.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ModItems.gas_mask_m65, 1, world.rand.nextInt(100)));

				if(rand.nextInt(32) == 0) {
					entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.syringe_poison));
				}
			}
		}
	}
	
	@SubscribeEvent
	public void itemCrafted(ItemCraftedEvent e){
		EntityPlayer player = e.player;
		Item item = e.crafting.getItem();

		/*if (item == ModItems.gun_mp40) {
			e.player.addStat(MainRegistry.achFreytag, 1);
		}
		if (item == ModItems.piston_selenium || item == ModItems.gun_b92) {
			e.player.addStat(MainRegistry.achSelenium, 1);
		}
		if (item == ModItems.battery_potatos) {
			e.player.addStat(MainRegistry.achPotato, 1);
		}
		if (item == ModItems.gun_revolver_pip) {
			e.player.addStat(MainRegistry.achC44, 1);
		}*/
		if (item == Item.getItemFromBlock(ModBlocks.machine_difurnace_off)) {
			AdvancementManager.grantAchievement(player, AdvancementManager.bobMetalworks);
		}
		if (item == Item.getItemFromBlock(ModBlocks.machine_assembler) && AdvancementManager.hasAdvancement(player, AdvancementManager.bobMetalworks)) {
			AdvancementManager.grantAchievement(player, AdvancementManager.bobAssembly);
		}
		if (item == Item.getItemFromBlock(ModBlocks.brick_concrete) && AdvancementManager.hasAdvancement(player, AdvancementManager.bobAssembly)) {
			AdvancementManager.grantAchievement(player, AdvancementManager.bobChemistry);
		}
		if (item == Item.getItemFromBlock(ModBlocks.machine_boiler_electric_off) && AdvancementManager.hasAdvancement(player, AdvancementManager.bobChemistry)) {
			AdvancementManager.grantAchievement(player, AdvancementManager.bobOil);
		}
		if (item == ModItems.ingot_uranium_fuel && AdvancementManager.hasAdvancement(player, AdvancementManager.bobOil)) {
			AdvancementManager.grantAchievement(player, AdvancementManager.bobNuclear);
		}
	}
	
	private static final String hash = "a4e6e2d37cc6bae3b19a925569c008d8f98b867e62ecb72398ee6fd5d7ee535a";
	
	@SubscribeEvent
	public void onClickSign(PlayerInteractEvent event) {

		BlockPos pos = event.getPos();
		World world = event.getWorld();
		
		if(!world.isRemote && world.getBlockState(pos).getBlock() == Blocks.STANDING_SIGN) {
			
			TileEntitySign sign = (TileEntitySign)world.getTileEntity(pos);
			
			String result = smoosh(sign.signText[0].getUnformattedText(), sign.signText[1].getUnformattedText(), sign.signText[2].getUnformattedText(), sign.signText[3].getUnformattedText());
			//System.out.println(result);
			
			if(result.equals(hash)) {
				world.destroyBlock(pos, false);
	            EntityItem entityitem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.bobmazon_hidden));
	            entityitem.setPickupDelay(10);
	            world.spawnEntity(entityitem);
			}
		}
		
	}
	
	private String smoosh(String s1, String s2, String s3, String s4) {
		
		Random rand = new Random();
		String s = "";

		byte[] b1 = s1.getBytes();
		byte[] b2 = s2.getBytes();
		byte[] b3 = s3.getBytes();
		byte[] b4 = s4.getBytes();
		
		if(b1.length == 0 || b2.length == 0 || b3.length == 0 || b4.length == 0)
			return "";
		
		s += s1;
		rand.setSeed(b1[0]);
		s += rand.nextInt(0xffffff);
		
		s += s2;
		rand.setSeed(rand.nextInt(0xffffff) + b2[0]);
		rand.setSeed(b2[0]);
		s += rand.nextInt(0xffffff);
		
		s += s3;
		rand.setSeed(rand.nextInt(0xffffff) + b3[0]);
		rand.setSeed(b3[0]);
		s += rand.nextInt(0xffffff);
		
		s += s4;
		rand.setSeed(rand.nextInt(0xffffff) + b4[0]);
		rand.setSeed(b4[0]);
		s += rand.nextInt(0xffffff);
		
		//System.out.println(s);
		
		return getHash(s);
	}
	
	private String getHash(String inp) {
		
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] bytes = sha256.digest(inp.getBytes());
			String str = "";
			
		    for(int b : bytes)
		      str = str + Integer.toString((b & 0xFF) + 256, 16).substring(1);
	    
		    return str;
		    
		} catch (NoSuchAlgorithmException e) { }
		
		return "";
	}

	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if(!MainRegistry.allPipeNetworks.isEmpty()) {
			Iterator<FFPipeNetwork> itr = MainRegistry.allPipeNetworks.iterator();
			while(itr.hasNext()) {
				FFPipeNetwork net = itr.next();
				if(net != null)
					net.updateTick();
				if(net.getPipes().isEmpty()) {
					net.destroySoft();
					itr.remove();
				}

			}
		}

		if(event.world != null && !event.world.isRemote && event.world.provider.isSurfaceWorld() && MainRegistry.enableMeteorStrikes) {
			if(event.world.rand.nextInt(meteorShower > 0 ? MainRegistry.meteorShowerChance : MainRegistry.meteorStrikeChance) == 0) {
				if(!event.world.playerEntities.isEmpty()) {
					EntityPlayer p = (EntityPlayer) event.world.playerEntities.get(event.world.rand.nextInt(event.world.playerEntities.size()));

					if(p != null && p.dimension == 0) {
						EntityMeteor meteor = new EntityMeteor(event.world);
						meteor.posX = p.posX + event.world.rand.nextInt(201) - 100;
						meteor.posY = 384;
						meteor.posZ = p.posZ + event.world.rand.nextInt(201) - 100;
						meteor.motionX = event.world.rand.nextDouble() - 0.5;
						meteor.motionY = -2.5;
						meteor.motionZ = event.world.rand.nextDouble() - 0.5;
						event.world.spawnEntity(meteor);
					}
				}
			}

			if(meteorShower > 0) {
				meteorShower--;
				if(meteorShower == 0 && MainRegistry.enableDebugMode)
					MainRegistry.logger.info("Ended meteor shower.");
			}

			if(event.world.rand.nextInt(MainRegistry.meteorStrikeChance * 100) == 0 && MainRegistry.enableMeteorShowers) {
				meteorShower = (int) (MainRegistry.meteorShowerDuration * 0.75 + MainRegistry.meteorShowerDuration * 0.25 * event.world.rand.nextFloat());

				if(MainRegistry.enableDebugMode)
					MainRegistry.logger.info("Started meteor shower! Duration: " + meteorShower);
			}
		}

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

					EntityPlayer player = (EntityPlayer) o;
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
						if(((EntityLivingBase) e).hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
							entRad = ((EntityLivingBase) e).getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
						} else {
							continue;
						}
						// effect for radiation
						EntityLivingBase entity = (EntityLivingBase) e;

						if(event.world.getTotalWorldTime() % 20 == 0) {

							Chunk chunk = entity.world.getChunkFromBlockCoords(new BlockPos(entity.posX, entity.posY, entity.posZ));
							float rad = data.getRadNumFromCoord(chunk.x, chunk.z);

							if(rad > 0) {
								if(!entity.isPotionActive(HbmPotion.mutation))
									Library.applyRadData(entity, rad / 2);
							}

							if(entity.world.isRaining() && MainRegistry.cont > 0 && AuxSavedData.getThunder(entity.world) > 0 && entity.world.canBlockSeeSky(new BlockPos(entity))) {

								if(!entity.isPotionActive(HbmPotion.mutation)) {
									Library.applyRadData(entity, MainRegistry.cont * 0.005F);
								}
							}
						}

						float eRad = entRad.getRads();

						if(entity instanceof EntityCreeper && eRad >= 200 && entity.getHealth() > 0) {

							if(event.world.rand.nextInt(3) == 0) {
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

						if(eRad < 200 || entity instanceof EntityNuclearCreeper || entity instanceof EntityMooshroom || entity instanceof EntityZombie || entity instanceof EntitySkeleton)
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

	// Drillgon200: So 1.12.2's going to ignore ISpecialArmor if the damage is
	// unblockable, huh?
	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent e) {
		if(e.getEntityLiving() instanceof EntityPlayer) {
			if(Library.checkArmor((EntityPlayer) e.getEntityLiving(), ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
				e.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityHurt(LivingAttackEvent event) {
		EntityLivingBase e = event.getEntityLiving();

		if(e instanceof EntityPlayer && Library.checkArmor((EntityPlayer)e, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
			e.world.playSound(null, e.posX, e.posY, e.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 5F, 1.0F + e.getRNG().nextFloat() * 0.5F);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(!event.player.world.isRemote && event.player.getUniqueID().toString().equals("c874fd4e-5841-42e4-8f77-70efd5881bc1"))
			if(event.player.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
				event.player.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).increaseRads(0.05F);
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		if(event.getEntity().hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
			event.getEntity().getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).setRads(0.0F);
		;
		if(MainRegistry.enableCataclysm) {
			EntityBurningFOEQ foeq = new EntityBurningFOEQ(event.getEntity().world);
			foeq.setPositionAndRotation(event.getEntity().posX, 500, event.getEntity().posZ, 0.0F, 0.0F);
			event.getEntity().world.spawnEntity(foeq);
		}
		if(event.getEntityLiving() instanceof EntityPlayer) {
			if(Library.checkArmor((EntityPlayer) event.getEntityLiving(), ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
				event.setCanceled(true);
				event.getEntityLiving().setHealth(event.getEntityLiving().getMaxHealth());
			}
		}
		if(event.getEntity().getUniqueID().toString().equals(Library.HbMinecraft)) {
			event.getEntity().dropItem(ModItems.book_of_, 1);
		}
		
		if(event.getEntity() instanceof EntityEnderman && event.getSource() == ModDamageSource.boxcar) {
			
			for(EntityPlayer player : event.getEntity().getEntityWorld().playerEntities) {
				if(Library.getEntRadCap(player).getRads() > 250 && player.isBurning()) {
					AdvancementManager.grantAchievement(player, AdvancementManager.bobHidden);
				}
			}
		}
	}

	@SubscribeEvent
	public void clientJoinServer(PlayerLoggedInEvent e) {
		if(e.player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new AssemblerRecipeSyncPacket(ItemAssemblyTemplate.recipes), (EntityPlayerMP) e.player);

		if(showMessage) {
			e.player.sendMessage(new TextComponentTranslation("Loaded world with Hbm's Nuclear Tech Mod " + RefStrings.VERSION + " for Minecraft 1.12.2!"));
		}
		showMessage = false;
	}

	@SubscribeEvent
	public void onDataSerializerRegister(RegistryEvent.Register<DataSerializerEntry> evt) {
		evt.getRegistry().register(new DataSerializerEntry(MissileStruct.SERIALIZER).setRegistryName(new ResourceLocation(RefStrings.MODID, "missile_struct")));
	}

	// TODO should probably use these.

	@SubscribeEvent
	public void onItemRegister(RegistryEvent.Register<Item> evt) {
	}

	@SubscribeEvent
	public void onBlockRegister(RegistryEvent.Register<Block> evt) {
	}

	@SubscribeEvent
	public void onRecipeRegister(RegistryEvent.Register<IRecipe> evt) {
	}

}
