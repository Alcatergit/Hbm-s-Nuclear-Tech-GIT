package com.hbm.handler;

import static com.hbm.items.ItemEnums.EnumAmmo;
import java.util.Arrays;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.IItemAbility;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class WeaponAbility {
	
	public abstract void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool);
	public abstract String getName();
	@SideOnly(Side.CLIENT)
	public abstract String getFullName();
	
	public static class RadiationAbility extends WeaponAbility {
		
		float rad;
		
		public RadiationAbility(float rad) {
			this.rad = rad;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			if(victim instanceof EntityLivingBase)
				ContaminationUtil.contaminate((EntityLivingBase)victim, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}

		@Override
		public String getName() {
			return "weapon.ability.radiation";
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getFullName() {
			return I18n.format(getName()) + " (" + rad + ")";
		}
	}
	
	public static class VampireAbility extends WeaponAbility {
		
		float amount;
		
		public VampireAbility(float amount) {
			this.amount = amount;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;
				
				living.setHealth(living.getHealth() - amount);
				player.heal(amount);
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.vampire";
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getFullName() {
			return I18n.format(getName()) + " (" + amount + ")";
		}
	}
	
	public static class StunAbility extends WeaponAbility {
		
		int duration;
		
		public StunAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				
				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration * 20, 4));
				living.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration * 20, 4));
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.stun";
		}

		@Override
		@SideOnly(Side.CLIENT)
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}
	
	public static class PhosphorusAbility extends WeaponAbility {

		int duration;

		public PhosphorusAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {

			if(victim instanceof EntityLivingBase) {

				EntityLivingBase living = (EntityLivingBase) victim;

				living.addPotionEffect(new PotionEffect(HbmPotion.phosphorus, duration * 20, 4));
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.phosphorus";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}
	
	public static class ChainsawAbility extends WeaponAbility {

		int divider;

		public ChainsawAbility(int divider) {
			this.divider = divider;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {

			if(victim instanceof EntityLivingBase) {

				EntityLivingBase living = (EntityLivingBase) victim;

				if(living.getHealth() <= 0.0F) {

					WeightedRandomObject[] ammo = new WeightedRandomObject[] {
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G12.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G12_SHRAPNEL.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G12_DU.ordinal()), 3),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G20.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G20_FLECHETTE.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G20_SLUG.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.P9.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.P9_AP.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R5.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R5_DU.ordinal()), 3),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R556.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R556_PHOSPHORUS.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R556_FLECHETTE.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.R556_FLECHETTE_PHOSPHORUS.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.BMG50.ordinal()), 10),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.BMG50_INCENDIARY.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.BMG50_AP.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE_CONCUSSION.ordinal()), 3),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE_PHOSPHORUS.ordinal()), 3),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET_GLARE.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET_PHOSPHORUS.ordinal()), 5),
							new WeightedRandomObject(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET_RPC.ordinal()), 1),
							new WeightedRandomObject(ModItems.syringe_metal_stimpak, 25),
					};

					int count = Math.min((int)Math.ceil(living.getMaxHealth() / divider), 250); //safeguard to prevent funnies from bosses with obscene health

					for(int i = 0; i < count; i++) {

						living.entityDropItem(((WeightedRandomObject)WeightedRandom.getRandomItem(living.getRNG(), Arrays.asList(ammo))).asStack(), 0F);
						world.spawnEntity(new EntityXPOrb(world, living.posX, living.posY, living.posZ, 1));
					}

					if(player instanceof EntityPlayerMP) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "vanillaburst");
						data.setInteger("count", count * 4);
						data.setDouble("motion", 0.1D);
						data.setString("mode", "blockdust");
						data.setInteger("block", Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
						PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(data, living.posX, living.posY + living.height * 0.5, living.posZ), (EntityPlayerMP)player);
					}

					world.playSound(null, living.posX, living.posY + living.height * 0.5, living.posZ, HBMSoundHandler.chainsaw, SoundCategory.PLAYERS, 0.5F, 1.0F);
				}
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.chainsaw";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (1:" + divider + ")";
		}
	}

	public static class BeheaderAbility extends WeaponAbility {

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {

			if(victim instanceof EntityLivingBase && ((EntityLivingBase) victim).getHealth() <= 0.0F) {

				EntityLivingBase living = (EntityLivingBase) victim;

				if(living instanceof EntitySkeleton) {
					living.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
				} else if(living instanceof EntityWitherSkeleton){
					living.entityDropItem(ItemCell.getFullCell(ModForgeFluids.AMAT), 0.0F);
				} else if(living instanceof EntityZombie) {
					living.entityDropItem(new ItemStack(Items.SKULL, 1, 2), 0.0F);
				} else if(living instanceof EntityCreeper) {
					living.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
				} else if(living instanceof EntityPlayer) {

					ItemStack head = new ItemStack(Items.SKULL, 1, 3);
					head.setTagCompound(new NBTTagCompound());
					head.getTagCompound().setString("SkullOwner", ((EntityPlayer) living).getDisplayName().getUnformattedText());
					living.entityDropItem(head, 0.0F);
				} else {
					living.entityDropItem(new ItemStack(Items.ROTTEN_FLESH, 3, 0), 0.0F);
					living.entityDropItem(new ItemStack(Items.BONE, 2, 0), 0.0F);
				}
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.beheader";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName());
		}
	}
	
	public static class FireAbility extends WeaponAbility {
		
		int duration;
		
		public FireAbility(int duration) {
			this.duration = duration;
		}

		@Override
		public void onHit(World world, EntityPlayer player, Entity victim, IItemAbility tool) {
			
			if(victim instanceof EntityLivingBase) {
				victim.setFire(duration);
			}
		}

		@Override
		public String getName() {
			return "weapon.ability.fire";
		}

		@Override
		public String getFullName() {
			return I18n.format(getName()) + " (" + duration + ")";
		}
	}

}