package com.leafia.eventbuses;

import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK3.ATEntry;
import com.hbm.interfaces.IItemHazard;
import com.hbm.inventory.OreDictManager;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.ModDamageSource;
import com.hbm.modules.ItemHazardModule;
import com.hbm.potion.HbmPotion;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet.PWRVentInletTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.optimization.LeafiaParticlePacket;
import com.leafia.dev.optimization.LeafiaParticlePacket.Sweat;
import com.leafia.passive.LeafiaPassiveServer;
import com.leafia.unsorted.IEntityCustomCollision;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fluids.FluidEvent.FluidFillingEvent;
import net.minecraftforge.fluids.FluidEvent.FluidMotionEvent;
import net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class LeafiaServerListener {
	public static class Unsorted {
		@SubscribeEvent
		public void onGetEntityCollision(GetCollisionBoxesEvent evt) {
			if (evt.getEntity() == null) return;
			List<AxisAlignedBB> list = evt.getCollisionBoxesList();
			List<Entity> list1 = evt.getWorld().getEntitiesWithinAABBExcludingEntity(evt.getEntity(), evt.getAabb().grow((double)0.25F));
			for(int i = 0; i < list1.size(); ++i) {
				Entity entity = (Entity)list1.get(i);
				if (!evt.getEntity().isRidingSameEntity(entity)) {
					if (entity instanceof IEntityCustomCollision) {
						List<AxisAlignedBB> aabbs = ((IEntityCustomCollision)entity).getCollisionBoxes(evt.getEntity());
						if (aabbs == null) continue;
						for (AxisAlignedBB aabb : aabbs) {
							if (aabb != null && aabb.intersects(aabb))
								list.add(aabb);
						}
					}
				}
			}
		}
		@SubscribeEvent
		public void onBlockNotify(NeighborNotifyEvent evt) {
			if (!evt.getWorld().isRemote) {
				//LeafiaDebug.debugPos(evt.getWorld(),evt.getPos(),3,0xFF0000,"NeighborNotifyEvent");
				for (Entry<PWRElementTE,LeafiaSet<BlockPos>> entry : PWRElementTE.listeners.entrySet()) {
					if (entry.getKey().isInvalid()) {
						PWRElementTE.listeners.remove(entry.getKey());
						continue;
					}
					if (entry.getValue().contains(evt.getPos()))
						entry.getKey().updateObstacleMappings();
				}
				for (Entry<PWRVentInletTE,LeafiaSet<BlockPos>> entry : PWRVentInletTE.listeners.entrySet()) {
					if (entry.getKey().isInvalid()) {
						PWRVentInletTE.listeners.remove(entry.getKey());
						continue;
					}
					if (entry.getValue().contains(evt.getPos()))
						entry.getKey().rebuildMap();
				}
			}
		}
		@SubscribeEvent
		public void worldInit(Load evt) {
			List<ATEntry> entries = new ArrayList<>(EntityNukeExplosionMK3.at.keySet());
			for (ATEntry entry : entries) {
				if (entry.dim == evt.getWorld().provider.getDimension())
					EntityNukeExplosionMK3.at.remove(entry);
			}
		}
	}
	public static class Fluids {
		@SubscribeEvent
		public void filled(FluidFillingEvent evt) {
			LeafiaDebug.debugLog(evt.getWorld(),"SCREW YOU! "+evt.getClass().getSimpleName());
			//LeafiaDebug.debugPos(evt.getWorld(),evt.getPos(),3,0x00CCFF,evt.getClass().getSimpleName(),evt.getFluid().getFluid().getName());
		}
		@SubscribeEvent
		public void spilled(FluidSpilledEvent evt) {
			LeafiaDebug.debugLog(evt.getWorld(),"SCREW YOU! "+evt.getClass().getSimpleName());
			//LeafiaDebug.debugPos(evt.getWorld(),evt.getPos(),3,0x00CCFF,evt.getClass().getSimpleName(),evt.getFluid().getFluid().getName());
		}
		@SubscribeEvent
		public void moved(FluidMotionEvent evt) {
			LeafiaDebug.debugLog(evt.getWorld(),"SCREW YOU! "+evt.getClass().getSimpleName());
			//LeafiaDebug.debugPos(evt.getWorld(),evt.getPos(),3,0x00CCFF,evt.getClass().getSimpleName(),evt.getFluid().getFluid().getName());
		}
	}
	public static class SharpEdges {
		public static LeafiaMap<Entity,Float> damageCache = new LeafiaMap<>();
		@SubscribeEvent
		public void onEntityHurt(LivingDamageEvent evt) {
			DamageSource src = evt.getSource();
			if (!src.equals(ModDamageSource.pointed))
				damageCache.put(evt.getEntity(),evt.getAmount());
			EntityLivingBase entity = evt.getEntityLiving();
			Random rng = entity.getRNG();
			if (src.isFireDamage()) {
				if (rng.nextInt(5+5*HbmPotion.getSkinDamage(entity)/*HbmPotion.getSkinDamage(entity)*/) == 0)
					HbmPotion.hurtSkin(entity,3);
			}
			if (src.equals(DamageSource.FALL))
				sharpDamageEntity(evt.getEntity(),evt.getAmount(),getItems(evt.getEntity()));
		}
		@SubscribeEvent
		public void onEntityHit(LivingAttackEvent evt) {
			Entity attacker = evt.getSource().getImmediateSource();
			if (attacker != null) {
				List<ItemStack> stacks = new ArrayList<>();
				for (ItemStack stack : attacker.getHeldEquipment())
					stacks.add(stack);
				sharpDamageEntity(evt.getEntity(),evt.getAmount(),stacks);
			}
		}
		@SubscribeEvent
		public void onEntityKnockback(LivingKnockBackEvent evt) {
			if (evt.getStrength() > 0) {
				if (damageCache.containsKey(evt.getEntity())) {
					float damage = damageCache.get(evt.getEntity());
					sharpDamageEntity(evt.getEntity(),damage,getItems(evt.getEntity()));
				}
			}
		}
		@SubscribeEvent
		public void onItemPickup(EntityItemPickupEvent evt) {
			sharpDamageEntity(evt.getEntity(),1,Collections.singletonList(evt.getItem().getItem()));
		}
		List<ItemStack> getItems(Entity entity) {
			List<ItemStack> stacks = new ArrayList<>();
			if (entity instanceof EntityPlayer) {
				InventoryPlayer inventory = ((EntityPlayer)entity).inventory;
				for (int i = 0; i < inventory.getSizeInventory(); i++)
					stacks.add(inventory.getStackInSlot(i));
			} else {
				for (ItemStack stack : entity.getEquipmentAndArmor())
					stacks.add(stack);
			}
			return stacks;
		}
		public void sharpDamageEntity(Entity entity,float baseDamage,List<ItemStack> stacks) {
			float modifier = 0;
			float max = 0;
			for (ItemStack stack : stacks) {
				if (!stack.isEmpty()) {
					ItemHazardModule module = null;
					if (stack.getItem() instanceof IItemHazard) {
						IItemHazard hazard = (IItemHazard)stack.getItem();
						module = hazard.getModule();
					} else {
						for (int id : OreDictionary.getOreIDs(stack)) {
							module = OreDictManager.fiaOreHazards.get(OreDictionary.getOreName(id));
							if (module != null)
								break;
						}
					}
					if (module != null) {
						modifier += module.sharp*stack.getCount();
						max = Math.max(max,module.sharp);
					}
				}
			}
			float additionalDamage = baseDamage*(modifier*(1-ItemHazardModule.sharpStackNerf)+max*ItemHazardModule.sharpStackNerf);
			if (additionalDamage > 0) {
				LeafiaPassiveServer.queueFunction(()->{
					if (entity.world == null) return;
					entity.world.playSound(null,entity.getPosition(),HBMSoundEvents.blood_splat,SoundCategory.MASTER,0.25f,entity.world.rand.nextFloat()*0.2f+0.9f);
					entity.world.playSound(null,entity.getPosition(),HBMSoundEvents.pointed,SoundCategory.MASTER,0.25f,entity.world.rand.nextFloat()*0.2f+0.9f);
					LeafiaParticlePacket.Sweat particle = new Sweat(entity,Blocks.REDSTONE_BLOCK.getDefaultState(),entity.world.rand.nextInt(4)+2);
					particle.emit(new Vec3d(entity.posX,entity.posY,entity.posZ),Vec3d.ZERO,entity.dimension);
					entity.hurtResistantTime = 0;
					entity.attackEntityFrom(ModDamageSource.pointed,additionalDamage);
				});
			}
		}
	}
}
