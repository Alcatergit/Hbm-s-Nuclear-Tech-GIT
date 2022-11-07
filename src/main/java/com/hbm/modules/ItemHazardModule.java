package com.hbm.modules;

import java.util.List;

import com.hbm.capability.HbmLivingProps;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.ArmorUtil;
import com.hbm.inventory.BreederRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class ItemHazardModule {
	/**
	 * Dependency injection: It's fun for boys and girls!
	 * All this interface-pattern-wish-wash only exists for three reasons:
	 * -it lets me add item hazards with ease by using self-returning setters
	 * -it's agnositc and also works with ItemBlocks or whatever implementation I want it to work
	 * -it makes the system truly centralized and I don't have to add new cases to 5 different classes when adding a new hazard
	 */

	public float radiation;
	public float digamma;
	public int fire;
	public boolean blinding;
	public int asbestos;
	public int coal;
	public boolean hydro;
	public float explosive;
	
	public float tempMod = 1F;
	
	public void setMod(float tempMod) {
		this.tempMod = tempMod;
	}
	
	public void addRadiation(float radiation) {
		this.radiation = radiation;
	}
	
	public void addDigamma(float digamma) {
		this.digamma = digamma;
	}
	
	public void addFire(int fire) {
		this.fire = fire;
	}
	
	public void addCoal(int coal) {
		this.coal = coal;
	}
	
	public void addAsbestos(int asbestos) {
		this.asbestos = asbestos;
	}
	
	public void addBlinding() {
		this.blinding = true;
	}
	
	public void addHydroReactivity() {
		this.hydro = true;
	}
	
	public void addExplosive(float bang) {
		this.explosive = bang;
	}

	public void applyEffects(EntityLivingBase entity, float mod, int slot, boolean currentItem, EnumHand hand) {
			
		boolean reacher = false;
		
		if(entity instanceof EntityPlayer && !GeneralConfig.enable528)
			reacher = Library.hasInventoryItem(((EntityPlayer) entity).inventory, ModItems.reacher);
			
		if(this.radiation * tempMod > 0) {
			float rad = this.radiation * tempMod * mod / 20F;
			
			if(reacher)
				rad = (float) Math.min(Math.sqrt(rad), rad); //to prevent radiation from going up when being <1
			
			ContaminationUtil.contaminate(entity, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}

		if(this.digamma * tempMod > 0)
			ContaminationUtil.applyDigammaData(entity, this.digamma * tempMod * mod / 20F);

		if(this.fire > 0 && !reacher)
			entity.setFire(this.fire);

		if(this.asbestos > 0) {
			if(!ArmorRegistry.hasProtection(entity, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_FINE))
				HbmLivingProps.incrementAsbestos(entity, (int) (this.asbestos * Math.min(mod, 10)));
			else
				ArmorUtil.damageGasMaskFilter(entity, (int) (this.asbestos));
		}

		if(this.coal > 0) {
			if(!ArmorRegistry.hasProtection(entity, EntityEquipmentSlot.HEAD, HazardClass.PARTICLE_COARSE))
				HbmLivingProps.incrementBlackLung(entity, (int) (this.coal * Math.min(mod, 10)));
			else
				ArmorUtil.damageGasMaskFilter(entity, (int) (this.coal));
		}

		if(this.hydro && currentItem) {

			if(!entity.world.isRemote && entity.isInWater() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem(hand);
				
				player.inventory.mainInventory.set(player.inventory.currentItem, held.getItem().getContainerItem(held));
				player.inventoryContainer.detectAndSendChanges();
				player.world.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, 2F, true, true);
			}
		}

		if(this.explosive > 0 && currentItem) {

			if(!entity.world.isRemote && entity.isBurning() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem(hand);
				
				player.inventory.mainInventory.set(player.inventory.currentItem, held.getItem().getContainerItem(held));
				player.inventoryContainer.detectAndSendChanges();
				player.world.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, this.explosive, true, true);
			}
		}

		if(this.blinding && !ArmorRegistry.hasProtection(entity, EntityEquipmentSlot.HEAD, HazardClass.LIGHT)) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 0));
		}
	}

	private String formatRads(float radiation){
		String rads = "";
		if(radiation < 1000000){
			rads = ((int)(radiation*1000))/1000+"";
		} else if(radiation < 1000000000){
			rads = ((int)(radiation/1000))/1000+"M";
		} else
			rads = ((int)(radiation/1000000))/1000+"G";
		return rads;
	}
	
	public void addInformation(ItemStack stack, List<String> list, ITooltipFlag flagIn) {
		
		if(this.radiation * tempMod > 0) {
			list.add(TextFormatting.GREEN + "[" + I18nUtil.resolveKey("trait.radioactive") + "]");
			String rad = formatRads(radiation * tempMod);
			list.add(TextFormatting.YELLOW + (rad + "RAD/s"));
			
			if(stack.getCount() > 1) {
				list.add(TextFormatting.YELLOW + "Stack: " + ((Math.floor(radiation * tempMod * 1000 * stack.getCount()) / 1000) + "RAD/s"));
			}
		}
		
		if(this.fire > 0) {
			list.add(TextFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
		}
		
		if(this.blinding) {
			list.add(TextFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey("trait.blinding") + "]");
		}
		
		if(this.asbestos > 0) {
			list.add(TextFormatting.WHITE + "[" + I18nUtil.resolveKey("trait.asbestos") + "]");
		}
		
		if(this.coal > 0) {
			list.add(TextFormatting.DARK_GRAY + "[" + I18nUtil.resolveKey("trait.coal") + "]");
		}
		
		if(this.hydro) {
			list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.hydro") + "]");
		}
		
		if(this.explosive > 0) {
			list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.explosive") + "]");
		}
		
		if(this.digamma * tempMod > 0) {
			float d = ((int) (digamma * tempMod * 10000F)) / 10F;
			list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
			list.add(TextFormatting.DARK_RED + "" + d + "mDRX/s");
		}
		
		int[] breeder = BreederRecipes.getFuelValue(stack);
		
		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[" + I18nUtil.resolveKey("trait.heat", breeder[0]) + "]", breeder[0]));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("trait.breeding", breeder[1]));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("trait.furnace", (breeder[0] * breeder[1] * 5)));
		}
	}

	public boolean onEntityItemUpdate(EntityItem item) {
		
		if(!item.world.isRemote) {
			
			if(this.hydro && item.world.getBlockState(new BlockPos((int)Math.floor(item.posX), (int)Math.floor(item.posY), (int)Math.floor(item.posZ))).getMaterial() == Material.WATER) {

				item.setDead();
				item.world.newExplosion(item, item.posX, item.posY, item.posZ, 2F, true, true);
				return true;
			}
			
			if(this.explosive > 0 && item.isBurning()) {

				item.setDead();
				item.world.newExplosion(item, item.posX, item.posY, item.posZ, this.explosive, true, true);
				return true;
			}
		}
		
		return false;
	}
}
