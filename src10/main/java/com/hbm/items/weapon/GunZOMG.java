package com.hbm.items.weapon;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.entity.projectile.EntityRainbow;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class GunZOMG extends Item {

	Random rand = new Random();
	
	public GunZOMG(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.weaponTab);
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		{
			player.setActiveHand(handIn);
		}

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("valid", false);
			stack.getTagCompound().setBoolean("superuser", false);
		}

		if (!player.isSneaking()) {
			if (stack.getTagCompound().getBoolean("valid")) {
				if ((Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium)
						|| Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium))) {
				} else {
					if (!Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium)
							&& !Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium)) {
						stack.getTagCompound().setBoolean("valid", false);
						if (!worldIn.isRemote) {
							player.sendMessage(new TextComponentTranslation("[ZOMG] Validation lost!"));
							player.sendMessage(new TextComponentTranslation("[ZOMG] Request new validation!"));
						}
					}
				}
			} else {
				if (!worldIn.isRemote) {
					player.sendMessage(new TextComponentTranslation("[ZOMG] Gun not validated!"));
					player.sendMessage(new TextComponentTranslation("[ZOMG] Validate your gun with shift right-click."));
				}
			}
		} else {
			if (stack.getTagCompound().getBoolean("valid")) {
				if (!worldIn.isRemote) {
					player.sendMessage(new TextComponentTranslation("[ZOMG] Gun has already been validated."));
				}
			} else {
				if (Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium) || Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium)) {
					stack.getTagCompound().setBoolean("valid", true);
					if (!worldIn.isRemote) {
						player.sendMessage(new TextComponentTranslation("[ZOMG] Gun has been validated!"));
					}

					if(Library.superuser.contains(player.getUniqueID().toString())) {
						if (!worldIn.isRemote) {
							player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, gigachad!"));
						}
						stack.getTagCompound().setBoolean("superuser", true);
					} else if(Library.hasInventoryItem(player.inventory, ModItems.polaroid)) {
						if (!worldIn.isRemote) {
							player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, superuser!"));
						}
						stack.getTagCompound().setBoolean("superuser", true);
					} else {
						if (!worldIn.isRemote) {
							player.sendMessage(new TextComponentTranslation("[ZOMG] Welcome, user!"));
						}
						stack.getTagCompound().setBoolean("superuser", false);
					}
				} else {
					if (!worldIn.isRemote) {
						player.sendMessage(new TextComponentTranslation("[ZOMG] Validation failed!"));
						player.sendMessage(new TextComponentTranslation("[ZOMG] No external negative gravity well found!"));
					}
				}
			}
		}

		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase ent, int count) {
		if(!(ent instanceof EntityPlayer))
			return;
		EnumHand hand = ent.getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		if(hand == EnumHand.MAIN_HAND && ent.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.gun_zomg){
			ent.getHeldItem(EnumHand.OFF_HAND).getItem().onUsingTick(ent.getHeldItem(EnumHand.OFF_HAND), ent, count);
		}
		EntityPlayer player = (EntityPlayer) ent;
		World world = player.world;

		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("valid", false);
			stack.getTagCompound().setBoolean("superuser", false);
		}
		
		
		if (!player.isSneaking()) {
			if (stack.getTagCompound().getBoolean("valid")) {
				if ((player.capabilities.isCreativeMode || Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium)
						|| Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium)) && count % 1 == 0) {
					if (!stack.getTagCompound().getBoolean("superuser")) {
						EntityBullet entityarrow = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						EntityBullet entityarrow1 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						EntityBullet entityarrow2 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						EntityBullet entityarrow3 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						EntityBullet entityarrow4 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						EntityBullet entityarrow5 = new EntityBullet(world, player, 3.0F, 35, 45, false, "chopper", hand);
						entityarrow.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow1.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow2.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow3.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow4.setDamage(35 + rand.nextInt(45 - 35));
						entityarrow5.setDamage(35 + rand.nextInt(45 - 35));

						world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.osiprShoot, SoundCategory.PLAYERS, 1.0F, 0.6F + (rand.nextFloat() * 0.4F));

						if (!world.isRemote) {
							world.spawnEntity(entityarrow);
							world.spawnEntity(entityarrow1);
							world.spawnEntity(entityarrow2);
							world.spawnEntity(entityarrow3);
							world.spawnEntity(entityarrow4);
							world.spawnEntity(entityarrow5);
						}
					} else {
						
						EntityRainbow entityarrow = new EntityRainbow(world, player, 1F, hand);
						EntityRainbow entityarrow1 = new EntityRainbow(world, player, 1F, hand);
						EntityRainbow entityarrow2 = new EntityRainbow(world, player, 1F, hand);
						EntityRainbow entityarrow3 = new EntityRainbow(world, player, 1F, hand);
						EntityRainbow entityarrow4 = new EntityRainbow(world, player, 1F, hand);
						entityarrow.setDamage(10000 + rand.nextInt(90000));
						entityarrow1.setDamage(10000 + rand.nextInt(90000));
						entityarrow2.setDamage(10000 + rand.nextInt(90000));
						entityarrow3.setDamage(10000 + rand.nextInt(90000));
						entityarrow4.setDamage(10000 + rand.nextInt(90000));

						//world.playSoundAtEntity(player, "random.explode", 1.0F, 1.5F + (rand.nextFloat() / 4));
						world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.zomgShoot, SoundCategory.PLAYERS, 1.0F, 0.8F + (rand.nextFloat() * 0.4F));

						if (!world.isRemote) {
							world.spawnEntity(entityarrow);
							world.spawnEntity(entityarrow1);
							world.spawnEntity(entityarrow2);
							world.spawnEntity(entityarrow3);
							world.spawnEntity(entityarrow4);
						}
					}
				} else {
					if (!Library.hasInventoryItem(player.inventory, ModItems.nugget_euphemium) && !Library.hasInventoryItem(player.inventory, ModItems.ingot_euphemium)) {
						stack.getTagCompound().setBoolean("valid", false);
						if (!world.isRemote) {
							player.sendMessage(new TextComponentTranslation("[ZOMG] Validation lost!"));
							player.sendMessage(new TextComponentTranslation("[ZOMG] Request new validation!"));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(stack.getTagCompound() == null)
		{
			list.add("Gun not validated.");
		} else if(stack.getTagCompound().getBoolean("valid")) {
			if(stack.getTagCompound().getBoolean("superuser")) {
				list.add("Gun set to superuser mode.");
				list.add("Firing mode: Negative energy bursts");
			} else {
				list.add("Gun set to regular user mode.");
				list.add("Firing mode: Dark pulse spray");
			}
		} else {
			list.add("Gun not validated.");
		}
		list.add("");
		list.add("Ammo: §5None (Requires Validation)");
		list.add("Damage: 35 - 45");
		list.add("Energy Damage: 10000 - 100000");
		list.add("Energy projectiles destroy blocks.");
		list.add("");
		list.add(I18nUtil.resolveKey("trait.legendaryweap"));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> map = super.getAttributeModifiers(slot, stack);
		if(slot == EntityEquipmentSlot.MAINHAND){
			map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 6, 0));
		}
		return map;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
}
