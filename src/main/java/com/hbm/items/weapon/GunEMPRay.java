package com.hbm.items.weapon;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.projectile.EntityDischarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;

public class GunEMPRay extends Item {

	public GunEMPRay(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		if(!(entityLiving instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer)entityLiving;
		if(player.getHeldItemMainhand() == stack && player.getHeldItemOffhand().getItem() == ModItems.gun_emp){
			player.getHeldItemOffhand().onPlayerStoppedUsing(worldIn, entityLiving, timeLeft);
		}
		int j = this.getMaxItemUseDuration(stack) - timeLeft;

		ArrowLooseEvent event = new ArrowLooseEvent(player, stack, worldIn, j, Library.hasInventoryItem(player.inventory, ModItems.gun_emp_ammo));
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.getCharge();

		boolean flag = player.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;

		if (!player.isSneaking()) {
			if (flag || Library.hasInventoryItem(player.inventory, ModItems.gun_emp_ammo)) {
				float f = j / 20.0F;
				f = (f * f + f * 2.0F) / 3.0F;

				if (j < 25.0D) {
					return;
				}

				if (j > 25.0F) {
					f = 25.0F;
				}

				EntityDischarge entityarrow = new EntityDischarge(worldIn, player, 1.0F, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);

				entityarrow.setIsCritical(true);

				stack.damageItem(1, player);
				// p_77615_2_.playSoundAtEntity(p_77615_3_, "tile.piston.out",
				// 1.0F, 0.5F);
				worldIn.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.fatmanShoot, SoundCategory.PLAYERS, 1.0F, 1F);

				if (!flag) {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_emp_ammo);
				}

				if (!worldIn.isRemote) {
					worldIn.spawnEntity(entityarrow);
				}
			}
		} else {
			if (flag || Library.hasInventoryItem(player.inventory, ModItems.gun_emp_ammo)) {

				if (j < 25.0D) {
					return;
				}
				
				if (!flag) {
					Library.consumeInventoryItem(player.inventory, ModItems.gun_emp_ammo);
				}
	    		
	    		EntityEMPBlast cloud = new EntityEMPBlast(player.world, 25);
	    		cloud.posX = player.posX;
	    		cloud.posY = player.posY + 1.0F;
	    		cloud.posZ = player.posZ;
				if (!worldIn.isRemote) {
					worldIn.spawnEntity(cloud);
				}
				
				ExplosionNukeGeneric.empBlast(player.world, (int)player.posX, (int)player.posY, (int)player.posZ, 25);
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Hold right mouse button");
		list.add("to shoot ball lightning,");
		list.add("sneak to create EMP wave!");
		list.add("");
		list.add("Ammo: Energy Cell");
		list.add("Damage: 25 - 35");
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 4, 0));
		}
		return map;
	}
}
