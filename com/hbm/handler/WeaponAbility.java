package com.hbm.handler;

import com.hbm.items.tool.IItemAbility;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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
			
			ContaminationUtil.applyRadData(victim, rad);
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

}