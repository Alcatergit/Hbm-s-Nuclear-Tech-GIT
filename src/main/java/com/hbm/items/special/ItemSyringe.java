package com.hbm.items.special;

import java.util.List;
import java.util.Random;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmLivingProps;
import com.hbm.config.VersatileConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.armor.JetpackBase;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSyringe extends Item {

	Random rand = new Random();
	
	public ItemSyringe(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModItems.ALL_ITEMS.add(this);

	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(this == ModItems.syringe_antidote && !VersatileConfig.hasPotionSickness(player))
		{
            if (!world.isRemote)
            {
            	player.clearActivePotions();
            	VersatileConfig.applyPotionSickness(player, 5);
            	
            	player.getHeldItem(hand).shrink(1);
            	world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_empty));
				}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}
            }
		}
		if (this == ModItems.syringe_awesome) {
			if (!world.isRemote) {
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 50 * 20, 0));
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 50 * 20, 24));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 * 20, 6));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 50 * 20, 9));
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 50 * 20, 4));
				player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 4));

				player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}
			}
		}
		
		if(this == ModItems.syringe_poison)
		{
            if (!world.isRemote)
            {
            	
            	player.getHeldItem(hand).shrink(1);
            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
				}
            	
            	if(rand.nextInt(2) == 0)
            		player.attackEntityFrom(ModDamageSource.euthanizedSelf, 30);
            	else
            		player.attackEntityFrom(ModDamageSource.euthanizedSelf2, 30);
                
            	
                world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

                if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_empty));
				}
            }
		}
		
		if (this == ModItems.syringe_metal_stimpak) {
			if (!world.isRemote) {
				player.heal(5);

				player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);
				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_metal_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}
			}
		}
		
		if(this == ModItems.syringe_metal_medx)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4 * 60 * 20, 2));
            	
            	player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_metal_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}
            }
		}
		
		if(this == ModItems.syringe_metal_psycho)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2 * 60 * 20, 0));
            	player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2 * 60 * 20, 0));
            	
            	player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_metal_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}
            }
		}
		
		if(this == ModItems.syringe_metal_super)
		{
            if (!world.isRemote)
            {
            	player.heal(25);
            	player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 0));
            
            	player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (player.getHeldItem(hand).isEmpty()) {
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, new ItemStack(ModItems.syringe_metal_empty));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
					player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
				}
            }
		}
		
		if(this == ModItems.med_bag)
		{
            if (!world.isRemote)
            {
            	player.setHealth(player.getMaxHealth());
            	
        		player.removePotionEffect(MobEffects.BLINDNESS);
        		player.removePotionEffect(MobEffects.NAUSEA);
        		player.removePotionEffect(MobEffects.MINING_FATIGUE);
        		player.removePotionEffect(MobEffects.HUNGER);
        		player.removePotionEffect(MobEffects.SLOWNESS);
        		player.removePotionEffect(MobEffects.POISON);
        		player.removePotionEffect(MobEffects.WEAKNESS);
        		player.removePotionEffect(MobEffects.WITHER);
        		player.removePotionEffect(HbmPotion.radiation);
            
        		player.getHeldItem(hand).shrink(1);
        		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            }
		}
		
		if(this == ModItems.syringe_taint)
		{
            if (!world.isRemote)
            {
                player.addPotionEffect(new PotionEffect(HbmPotion.taint, 60 * 20, 0));
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
            
                player.getHeldItem(hand).shrink(1);
				world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }

        	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
        	{
        		player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
        	}

        	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty)))
        	{
        		player.dropItem(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
        	}
		}
		
		if(this == ModItems.jetpack_tank && player.inventory.armorInventory.get(2) != null && player.inventory.armorInventory.get(2).getItem() instanceof JetpackBase) {
			if (!world.isRemote) {
				ItemStack jetpack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
				JetpackBase jetItem = (JetpackBase) jetpack.getItem();

            	if(jetItem.fuel != ModForgeFluids.kerosene)
            		return super.onItemRightClick(world, player, hand);

            	int fill = JetpackBase.getFuel(jetpack) + 1000;

            	if(fill > jetItem.maxFuel)
            		fill = jetItem.maxFuel;

				if (JetpackBase.getFuel(jetpack) == fill)
					return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));

				JetpackBase.setFuel(jetpack, fill);
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.jetpackTank, SoundCategory.PLAYERS, 1.0F, 1.0F);
				ItemStack stack = player.getHeldItem(hand);
				stack.shrink(1);
				if (stack.isEmpty())
					player.setHeldItem(hand, ItemStack.EMPTY);
				else {
					ItemStack newStack = new ItemStack(stack.getItem(), stack.getCount(), stack.getMetadata());
					newStack.setTagCompound(stack.getTagCompound());
					player.setHeldItem(hand, newStack);
				}
			}
		}
		if(this == ModItems.gun_kit_1 || this == ModItems.gun_kit_2)
		{
            if (!world.isRemote)
            {
    			float repair = 0;
    			
    			if(this == ModItems.gun_kit_1) {
    				repair = 0.1F;
    		        world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.spray, SoundCategory.PLAYERS, 1.0F, 1.0F);
    			}
    			if(this == ModItems.gun_kit_2) {
    				repair = 0.5F;
    		        world.playSound(null,  player.posX,  player.posY,  player.posZ, HBMSoundHandler.repair, SoundCategory.PLAYERS, 1.0F, 1.0F);
    			}
    			
            	for(int i = 0; i < 10; i++) {
            		
            		ItemStack gun = player.inventory.mainInventory.get(i);
            		if(i == 9)
            			gun = player.getHeldItemOffhand();
            		
            		if(gun.getItem() instanceof ItemGunBase) {
            			
            			int full = ((ItemGunBase)gun.getItem()).mainConfig.durability;
            			int wear = ItemGunBase.getItemWear(gun);
            			
            			int nWear = (int) (wear - (full * repair));
            			
            			if(nWear < 0)
            				nWear = 0;
            			
            			ItemGunBase.setItemWear(gun, nWear);
            		}
            	}
            
            	player.getHeldItem(hand).shrink(1);
            }
		}
		if(this == ModItems.cbt_device)
		{
            if (!world.isRemote)
            {
                player.addPotionEffect(new PotionEffect(HbmPotion.bang, 30, 0));

            	player.getHeldItem(hand).shrink(1);
            	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.vice, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
		}
		if(this == ModItems.syringe_mkunicorn) {
			if(!world.isRemote) {
				HbmLivingProps.setContagion(player, 3 * 60 * 60 * 20);
				player.getHeldItem(hand).shrink(1);
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.vice, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
		
		return ActionResult.<ItemStack> newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if (this == ModItems.syringe_awesome) {
			return true;
		}
		return false;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if (this == ModItems.syringe_awesome) {
			return EnumRarity.UNCOMMON;
		}
		if(this == ModItems.euphemium_stopper)
    	{
    		return EnumRarity.EPIC;
    	}
		return EnumRarity.COMMON;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker) {
		World world = entity.world;
		if(this == ModItems.syringe_antidote && !VersatileConfig.hasPotionSickness(entity))
		{
            if (!entity.world.isRemote)
            {
            	entity.clearActivePotions();
            	VersatileConfig.applyPotionSickness(entity, 5);
            	stack.shrink(1);
            	world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            		}
            	}
            }
		}
		if (this == ModItems.syringe_awesome) {
			if (!entity.world.isRemote) {
				entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 50 * 20, 0));
				entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 50 * 20, 24));
				entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50 * 20, 6));
				entity.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 50 * 20, 9));
				entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 50 * 20, 4));
				entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 4));

				stack.shrink(1);
				;
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty))) {
						player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
					}
				}
			}
		}
		
		if(this == ModItems.syringe_poison)
		{
            if (!world.isRemote)
            {
            	entity.attackEntityFrom(ModDamageSource.euthanized(attacker, attacker), 30);
                
            	stack.shrink(1);
            	world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if (this == ModItems.syringe_metal_stimpak) {
			if (!entity.world.isRemote) {
				entity.heal(5);

				stack.shrink(1);
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

				if (attacker instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) attacker;
					if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty))) {
						player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
					}
				}
			}
		}
		
		if(this == ModItems.syringe_metal_medx)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4 * 60 * 20, 2));
            	
            	stack.shrink(1);
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_psycho)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2 * 60 * 20, 0));
            	entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 2 * 60 * 20, 0));
            	
            	stack.shrink(1);
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_metal_super)
		{
            if (!world.isRemote)
            {
            	entity.heal(25);
            	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 0));
            
            	stack.shrink(1);
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            	}
            }
		}
		
		if(this == ModItems.syringe_taint)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(HbmPotion.taint, 60 * 20, 0));
            	entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
            
            	stack.shrink(1);
				world.playSound(null,  entity.posX,  entity.posY,  entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);

            	if(attacker instanceof EntityPlayer)
            	{
            		EntityPlayer player = (EntityPlayer)attacker;
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            		}
            		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.bottle2_empty)))
            		{
            			player.dropItem(new ItemStack(ModItems.bottle2_empty, 1, 0), false);
            		}
            	}
            }
		}
		if(this == ModItems.euphemium_stopper)
		{
            if (!world.isRemote)
            {
            	entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 30 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 30 * 20, 9));
            	entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 30 * 20, 9));
            }
		}
		
		if(this == ModItems.syringe_mkunicorn) {
			if(!world.isRemote) {
				HbmLivingProps.setContagion(entity, 3 * 60 * 60 * 20);
				world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.syringeUse, SoundCategory.PLAYERS, 1.0F, 1.0F);
				stack.shrink(1);
			}
		}
		
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.syringe_antidote) {
			tooltip.add("Removes all potion effects");
		}
		if (this == ModItems.syringe_awesome) {
			tooltip.add("§2Every good effect for 50 seconds");
		}
		if (this == ModItems.syringe_metal_stimpak) {
			tooltip.add("§aHeals 2.5 hearts");
		}
		if(this == ModItems.syringe_metal_medx) {
			tooltip.add("§dResistance III for 4 minutes");
		}
		if(this == ModItems.syringe_metal_psycho) {
			tooltip.add("§dResistance I for 2 minutes");
			tooltip.add("§6Strength I for 2 minutes");
		}
		if(this == ModItems.syringe_metal_super) {
			tooltip.add("§aHeals 25 hearts");
			tooltip.add("§bSlowness I for 10 seconds");
		}
		if(this == ModItems.syringe_poison) {
			tooltip.add("Deadly");
		}
		if(this == ModItems.syringe_taint) {
			tooltip.add("§5Tainted I for 60 seconds");
			tooltip.add("§eNausea I for 5 seconds");
			tooltip.add("Cloud damage + taint = ghoulified effect");
		}
		if(this == ModItems.med_bag) {
			tooltip.add("§aFull heal, regardless of max health");
			tooltip.add("Removes negative effects");
		}
		if(this == ModItems.gas_mask_filter_mono) {
			tooltip.add("Repairs worn monoxide mask");
		}
		if(this == ModItems.syringe_mkunicorn) {
			tooltip.add(TextFormatting.RED + "?");
		}
	}
}
