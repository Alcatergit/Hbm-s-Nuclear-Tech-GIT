package com.hbm.items.gear;

import java.util.List;

import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
import com.hbm.main.MainRegistry;
import com.hbm.render.model.ModelT45Boots;
import com.hbm.render.model.ModelT45Chest;
import com.hbm.render.model.ModelT45Helmet;
import com.hbm.render.model.ModelT45Legs;

import api.hbm.energy.IBatteryItem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorT45 extends ItemArmor implements ISpecialArmor {

	@SideOnly(Side.CLIENT)
	private ModelT45Helmet helmet;
	@SideOnly(Side.CLIENT)
	private ModelT45Chest plate;
	@SideOnly(Side.CLIENT)
	private ModelT45Legs legs;
	@SideOnly(Side.CLIENT)
	private ModelT45Boots boots;
	
	public ArmorT45(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		if (stack.getItem() == ModItems.t45_helmet)
			return armorType == EntityEquipmentSlot.HEAD;
		if (stack.getItem() == ModItems.t45_plate)
			return armorType == EntityEquipmentSlot.CHEST;
		if (stack.getItem() == ModItems.t45_legs)
			return armorType == EntityEquipmentSlot.LEGS;
		if (stack.getItem() == ModItems.t45_boots)
			return armorType == EntityEquipmentSlot.FEET;
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (this == ModItems.t45_helmet) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.helmet == null) {
					this.helmet = new ModelT45Helmet();
				}
				return this.helmet;
			}
		}
		if (this == ModItems.t45_plate) {
			if (armorSlot == EntityEquipmentSlot.CHEST) {
				if (this.plate == null) {
					this.plate = new ModelT45Chest();
				}
				return this.plate;
			}
		}
		if (this == ModItems.t45_legs) {
			if (armorSlot == EntityEquipmentSlot.LEGS) {
				if (this.legs == null) {
					this.legs = new ModelT45Legs();
				}
				return this.legs;
			}
		}
		if (this == ModItems.t45_boots) {
			if (armorSlot == EntityEquipmentSlot.FEET) {
				if (this.boots == null) {
					this.boots = new ModelT45Boots();
				}
				return this.boots;
			}
		}
		return null;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ModItems.t45_helmet) {
			return "hbm:textures/models/T45Helmet.png";
		}
		if (stack.getItem() == ModItems.t45_plate) {
			return "hbm:textures/models/T45Chest.png";
		}
		if (stack.getItem() == ModItems.t45_legs) {
			return "hbm:textures/models/T45Legs.png";
		}
		if (stack.getItem() == ModItems.t45_boots) {
			return "hbm:textures/models/T45Boots.png";
		}
		return null;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		// return null;
				if (player instanceof EntityPlayer && ArmorUtil.checkArmor((EntityPlayer) player, ModItems.t45_helmet,
						ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots)) {
					if (source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE || source == DamageSource.FALL
							|| source == DamageSource.DROWN || source == DamageSource.CACTUS || source == DamageSource.MAGIC
							|| source.isProjectile())
						return new ArmorProperties(1, 1, MathHelper.floor(999999999));
					if (source == DamageSource.FALLING_BLOCK || source == DamageSource.ANVIL)
						return new ArmorProperties(1, 1, MathHelper.floor(10));
					if (source == DamageSource.LAVA)
						return new ArmorProperties(1, 1, MathHelper.floor(5));
					if (source.isExplosion())
						return new ArmorProperties(1, 1, MathHelper.floor(10));
				}
				return new ArmorProperties(1, 1, MathHelper.floor(15));
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (slot == 0) {
			return 3;
		}
		if (slot == 1) {
			return 8;
		}
		if (slot == 2) {
			return 6;
		}
		if (slot == 3) {
			return 3;
		}
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		if (source != DamageSource.CACTUS && source != DamageSource.DROWN && source != DamageSource.FALL)
			stack.damageItem(damage * 1, entity);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if (armor.getItem() == ModItems.t45_plate) {
			if (armor.getTagCompound() == null) {
				armor.setTagCompound(new NBTTagCompound());
				armor.getTagCompound().setInteger("charge", 0);
			}
			
			boolean b = true;
			
			if(player.inventory.hasItemStack(new ItemStack(ModItems.fusion_core_infinite))) {
				armor.getTagCompound().setInteger("charge", (int)((IBatteryItem)ModItems.fusion_core).getMaxCharge());
			}
			
			if(b)
			if (armor.getTagCompound().getInteger("charge") <= 0) {
				for (int i = 0; i < player.inventory.mainInventory.size(); i++) {
					ItemStack stack = player.inventory.getStackInSlot(i);
					if (stack != null && stack.getItem() == ModItems.fusion_core
							&& ((IBatteryItem)stack.getItem()).getCharge(stack) != 0) {
						if (armor.getTagCompound().getInteger("charge") == 0) {
							int j = (int) ((IBatteryItem)stack.getItem()).getCharge(stack);
							armor.getTagCompound().setInteger("charge", j);
							player.inventory.mainInventory.set(i, ItemStack.EMPTY);
							player.sendMessage(new TextComponentTranslation("[Power Armor recharged]"));
							break;
						}
					}
				}
			}

			if (armor.getTagCompound().getInteger("charge") > 0 && ArmorUtil.checkArmor(player, ModItems.t45_helmet,
					ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots)) {
				armor.getTagCompound().setInteger("charge", armor.getTagCompound().getInteger("charge") - 1);
			}
		}

		if (ArmorUtil.checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) && !world.isRemote) {
			//Probably don't need the null check because it's a non null list, but whatever
			if (player.inventory.armorInventory.get(2) != null
					&& player.inventory.armorInventory.get(2).getItem() == ModItems.t45_plate
					&& player.inventory.armorInventory.get(2).getTagCompound() != null
					&& player.inventory.armorInventory.get(2).getTagCompound().getInteger("charge") > 0) {
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 30, 0, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 30, 1, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 30, 2, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 30, 0, true, false));
			} else {
				player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30, 1, true, false));
				player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30, 0, true, false));
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (stack.getItem() == ModItems.t45_plate) {
			if (stack.getTagCompound() != null) {
				if (stack.getTagCompound().getInteger("charge") != 0)
					list.add("Charge: " + (stack.getTagCompound().getInteger("charge") / 2000 + 1) + "%");
				else
					list.add("Charge: " + (stack.getTagCompound().getInteger("charge") / 2000) + "%");
			}
		}
	}

}
