package com.hbm.items.weapon;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.interfaces.IHasCustomModel;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.GunFXPacket;
import com.hbm.packet.GunFXPacket.FXType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunBase extends Item implements IHoldableWeapon, IHasCustomModel {

	public GunConfiguration mainConfig;
	public GunConfiguration altConfig;

	// Drillgon200: Wait this doesn't make any sense, there's only one mouse 1 and 2. What was I thinking???
	// Drillgon200: true if mouse 1 has been pressed for the right hand
	@SideOnly(Side.CLIENT)
	public boolean m1r;// = false;
	// Drillgon200: true if mouse 2 has been pressed for the right hand
	@SideOnly(Side.CLIENT)
	public boolean m2r;// = false;
	// Drillgon200: true if mouse 1 has been pressed for the left hand
	@SideOnly(Side.CLIENT)
	public boolean m1l;// = false;
	// Drillgon200: true if mouse 2 has been pressed for the left hand
	@SideOnly(Side.CLIENT)
	public boolean m2l;// = false;

	public ItemGunBase(GunConfiguration config, String s) {
		mainConfig = config;
		this.setMaxStackSize(1);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	public ItemGunBase(GunConfiguration config, GunConfiguration alt, String s) {
		this(config, s);
		altConfig = alt;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		EnumHand hand = null;
		if(entity instanceof EntityPlayer) {
			if(((EntityPlayer) entity).getHeldItem(EnumHand.MAIN_HAND) == stack) {
				hand = EnumHand.MAIN_HAND;
			} else if(((EntityPlayer) entity).getHeldItem(EnumHand.OFF_HAND) == stack) {
				hand = EnumHand.OFF_HAND;
			}
			if(hand != null) {
				if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && world.isRemote) {
					updateClient(stack, world, (EntityPlayer) entity, itemSlot, hand);
				} else {
					updateServer(stack, world, (EntityPlayer) entity, itemSlot, hand);
				}
			}
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}

	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, EnumHand hand) {

		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = m1r;
		boolean right = m2r;
		boolean leftleft = m1l;
		boolean leftright = m2l;

		if(hand != null) {
			if(left && right && hand == EnumHand.MAIN_HAND) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m1r = false;
				m2r = false;
			}
			if(leftleft && leftright && hand == EnumHand.OFF_HAND) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m1l = false;
				m2l = false;
			}

			if(left && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, EnumHand.MAIN_HAND));
				// setIsMouseDown(stack, false);
				m1r = false;
				endActionClient(stack, world, entity, true, EnumHand.MAIN_HAND);
			}

			if(leftleft && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, EnumHand.OFF_HAND));
				m1l = false;
				endActionClient(stack, world, entity, true, EnumHand.OFF_HAND);
			}

			if(right && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, EnumHand.MAIN_HAND));
				m2r = false;
				endActionClient(stack, world, entity, false, EnumHand.MAIN_HAND);
			}

			if(leftright && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, EnumHand.OFF_HAND));
				m2l = false;
				endActionClient(stack, world, entity, false, EnumHand.OFF_HAND);
			}

			if(mainConfig.reloadType != GunConfiguration.RELOAD_NONE || (altConfig != null && altConfig.reloadType != 0)) {

				if(Keyboard.isKeyDown(Keyboard.KEY_R) && getMag(stack) < mainConfig.ammoCap) {
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2, hand));
					setIsReloading(stack, true);
					resetReloadCycle(stack);
				}
			}
		} else {

			if(left) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				m1r = false;
			}
			if(right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m2r = false;
			}
			if(leftleft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				m1l = false;
			}
			if(leftright) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m2l = false;
			}
		}
	}

	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {

		if(getDelay(stack) > 0 && hand != null)
			setDelay(stack, getDelay(stack) - 1);
		
		if(getIsMouseDown(stack) && hand == null) {
			setIsMouseDown(stack, false);
		}
		
		if(getIsAltDown(stack) && hand != null) {
			setIsAltDown(stack, false);
		}
		if(GeneralConfig.enableGuns && mainConfig.firingMode == GunConfiguration.FIRE_AUTO && getIsMouseDown(stack) && tryShoot(stack, world, player, hand != null)) {

			fire(stack, world, player, hand);
			setDelay(stack, mainConfig.rateOfFire);
			// setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack, true);
		}

		if(getIsReloading(stack) && hand != null) {
			reload2(stack, world, player, hand);
		}
	}

	// tries to shoot, bullet checks are done here
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(main && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {

			if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				return getBeltSize(player, getBeltType(player, stack, main)) > 0;

			} else {
				return getMag(stack) > 0;
			}
		}

		if(!main && altConfig != null && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {

			if(altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				return getBeltSize(player, getBeltType(player, stack, main)) > 0;
				
			} else {
				return getMag(stack) > 0;
			}
		}

		return false;
	}

	// called every time the gun shoots, overridden to change bullet
	// entity/special additions
	private void fire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		BulletConfiguration config = null;

		if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
			config = getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
		}

		int bullets = config.bulletsMin;

		for(int k = 0; k < mainConfig.roundsPerCycle; k++) {
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);

			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config), hand);
			}

			setItemWear(stack, getItemWear(stack) + config.wear);
		}
		world.playSound(null, player.posX, player.posY, player.posZ, mainConfig.firingSound, SoundCategory.PLAYERS, 1.0F, mainConfig.firingPitch);
	}

	// unlike fire(), being called does not automatically imply success, some
	// things may still have to be handled before spawning the projectile
	protected void altFire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(altConfig == null)
			return;

		BulletConfiguration config = getBeltCfg(player, stack, false);
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < altConfig.roundsPerCycle; k++) {
			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config), hand);
			}
			
			setItemWear(stack, getItemWear(stack) + config.wear);
		}
		
		world.playSound(null, player.posX, player.posY, player.posZ, altConfig.firingSound, SoundCategory.PLAYERS, 1.0F, altConfig.firingPitch);
	}

	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand) {

		EntityBulletBase bullet = new EntityBulletBase(world, config, player, hand);
		world.spawnEntity(bullet);
		
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal(), hand), (EntityPlayerMP) player);
		PacketDispatcher.wrapper.sendToAllTracking(new GunFXPacket(player, hand, FXType.FIRE), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 1));
	}

	// called on click (server side, called by mouse packet)
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(mainConfig.firingMode == GunConfiguration.FIRE_MANUAL && getIsMouseDown(stack) && tryShoot(stack, world, player, main)) {
			fire(stack, world, player, hand);
			setDelay(stack, mainConfig.rateOfFire);
			// setMag(stack, getMag(stack) - 1);
			useUpAmmo(player, stack, main);
		}
		if(!main && altConfig != null && tryShoot(stack, world, player, main)) {
			altFire(stack, world, player, hand);
			setDelay(stack, altConfig.rateOfFire);
			useUpAmmo(player, stack, main);
		}
	}

	// called on click (client side, called by update cylce)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
	}

	// called on click release (server side, called by mouse packet) for release
	// actions like charged shots
	public void endAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
	}

	// called on click release (client side, called by update cylce)
	public void endActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
	}

	// martin 2 reload algorithm
	// now with less WET and more DRY
	// compact, readable and most importantly, FUNCTIONAL
	protected void reload2(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {

		if(getMag(stack) >= mainConfig.ammoCap) {
			setIsReloading(stack, false);
			return;
		}

		if(getReloadCycle(stack) < 0) {

			if(getMag(stack) == 0)
				resetAmmoType(stack, world, player);

			int count = 1;

			if(mainConfig.reloadType == GunConfiguration.RELOAD_FULL) {

				count = mainConfig.ammoCap - getMag(stack);
			}	

			boolean hasLoaded = false;
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack)));
			Item ammo = cfg.ammo;

			for(int i = 0; i < count; i++) {

				if(Library.hasInventoryItem(player.inventory, ammo) && getMag(stack) < mainConfig.ammoCap) {
					Library.consumeInventoryItem(player.inventory, ammo);
					setMag(stack, Math.min(getMag(stack) + cfg.ammoCount, mainConfig.ammoCap));
					hasLoaded = true;
				} else {
					setIsReloading(stack, false);
					break;
				}
			}

			if(getMag(stack) >= mainConfig.ammoCap) {
				setIsReloading(stack, false);
			} else {
				resetReloadCycle(stack);
			}

			if(hasLoaded && mainConfig.reloadSoundEnd)
				world.playSound(null, player.posX, player.posY, player.posZ, mainConfig.reloadSound, SoundCategory.PLAYERS, 1.0F, 1.0F);

		} else {
			setReloadCycle(stack, getReloadCycle(stack) - 1);
		}

		if(stack != player.getHeldItem(hand)) {
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}
	}

	// initiates a reload
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {

		if(getIsReloading(stack))
			return;

		if(!mainConfig.reloadSoundEnd)
			world.playSound(null, player.posX, player.posY, player.posZ, mainConfig.reloadSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
		
		PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.RELOAD.ordinal(), hand), (EntityPlayerMP) player);

		setIsReloading(stack, true);
		resetReloadCycle(stack);
	}

	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {

		if(getMag(stack) == 0) {

			for(Integer config : mainConfig.config) {

				BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);

				if(Library.hasInventoryItem(player.inventory, cfg.ammo)) {
					return true;
				}
			}

		} else {

			Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;
			if(Library.hasInventoryItem(player.inventory, ammo))
				return true;
		}

		return false;
	}

	// searches the player's inv for next fitting ammo type and changes the
	// gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {

		for(Integer config : mainConfig.config) {

			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);

			if(Library.hasInventoryItem(player.inventory, cfg.ammo)) {
				setMagType(stack, mainConfig.config.indexOf(config));
				break;
			}
		}
	}

	// item mouseover text
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		Item ammo = BulletConfigSyncingUtil.pullConfig(mainConfig.config.get(getMagType(stack))).ammo;

		if(mainConfig.ammoCap > 0)
			list.add("Ammo: " + getMag(stack) + " / " + mainConfig.ammoCap);
		else
			list.add("Ammo: Belt");

		list.add("Ammo Type: " + I18n.format(ammo.getUnlocalizedName() + ".name"));

		int dura = mainConfig.durability - getItemWear(stack);

		if(dura < 0)
			dura = 0;

		list.add("Durability: " + dura + " / " + mainConfig.durability);

		// if(MainRegistry.enableDebugMode) {
		list.add("");
		list.add("Name: " + mainConfig.name);
		list.add("Manufacturer: " + mainConfig.manufacturer);
		// }

		if(!mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : mainConfig.comment)
				list.add(TextFormatting.ITALIC + s);
		}

		if(GeneralConfig.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + getMagType(stack));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
		}
	}

	public static Item getBeltType(EntityPlayer player, ItemStack stack, boolean main) {

		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);
		Item ammo = BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0)).ammo;

		for(Integer config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(Library.hasInventoryItem(player.inventory, cfg.ammo)) {
				ammo = cfg.ammo;
				break;
			}
		}
		
		return ammo;
	}

	public static BulletConfiguration getBeltCfg(EntityPlayer player, ItemStack stack, boolean main) {
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);
		getBeltType(player, stack, main);

		for(Integer config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(Library.hasInventoryItem(player.inventory, cfg.ammo)) {
				return cfg;
			}
		}

		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0));
	}

	// returns ammo capacity of belt-weapons for current ammo
	public static int getBeltSize(EntityPlayer player, Item ammo) {

		int amount = 0;

		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && stack.getItem() == ammo)
				amount += stack.getCount();
		}

		return amount;
	}

	// reduces ammo count for mag and belt-based weapons, should be called AFTER
	// firing
	public void useUpAmmo(EntityPlayer player, ItemStack stack, boolean main) {

		if(!main && altConfig == null)
			return;
		
		GunConfiguration config = mainConfig;
		
		if(!main)
			config = altConfig;
		
		if(config.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0)
			return;

		for(int k = 0; k < config.roundsPerCycle; k++) {
			if(config.reloadType != GunConfiguration.RELOAD_NONE) {
				setMag(stack, getMag(stack) - 1);
			} else {
				Library.consumeInventoryItem(player.inventory, getBeltType(player, stack, main));
				player.inventoryContainer.detectAndSendChanges();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void onFireClient(ItemStack stack, EntityPlayer player, boolean shouldDoThirdPerson){
	}

	/// sets reload cycle to config defult ///
	public static void resetReloadCycle(ItemStack stack) {
		writeNBT(stack, "reload", ((ItemGunBase) stack.getItem()).mainConfig.reloadDuration);
	}

	/// if reloading routine is active ///
	public static void setIsReloading(ItemStack stack, boolean b) {
		writeNBT(stack, "isReloading", b ? 1 : 0);
	}

	public static boolean getIsReloading(ItemStack stack) {
		return readNBT(stack, "isReloading") == 1;
	}

	/// if left mouse button is down ///
	public static void setIsMouseDown(ItemStack stack, boolean b) {
		writeNBT(stack, "isMouseDown", b ? 1 : 0);
	}

	public static boolean getIsMouseDown(ItemStack stack) {
		return readNBT(stack, "isMouseDown") == 1;
	}

	/// if alt mouse button is down ///
	public static void setIsAltDown(ItemStack stack, boolean b) {
		writeNBT(stack, "isAltDown", b ? 1 : 0);
	}

	public static boolean getIsAltDown(ItemStack stack) {
		return readNBT(stack, "isAltDown") == 1;
	}

	/// RoF cooldown ///
	public static void setDelay(ItemStack stack, int i) {
		writeNBT(stack, "dlay", i);
	}

	public static int getDelay(ItemStack stack) {
		return readNBT(stack, "dlay");
	}

	/// Gun wear ///
	public static void setItemWear(ItemStack stack, int i) {
		writeNBT(stack, "wear", i);
	}

	public static int getItemWear(ItemStack stack) {
		return readNBT(stack, "wear");
	}

	/// R/W cycle animation timer ///
	public static void setCycleAnim(ItemStack stack, int i) {
		writeNBT(stack, "cycle", i);
	}

	public static int getCycleAnim(ItemStack stack) {
		return readNBT(stack, "cycle");
	}

	/// R/W reload animation timer ///
	public static void setReloadCycle(ItemStack stack, int i) {
		writeNBT(stack, "reload", i);
	}

	public static int getReloadCycle(ItemStack stack) {
		return readNBT(stack, "reload");
	}

	/// magazine capacity ///
	public static void setMag(ItemStack stack, int i) {
		writeNBT(stack, "magazine", i);
	}

	public static int getMag(ItemStack stack) {
		return readNBT(stack, "magazine");
	}

	/// magazine type (int specified by index in bullet config list) ///
	public static void setMagType(ItemStack stack, int i) {
		writeNBT(stack, "magazineType", i);
	}

	public static int getMagType(ItemStack stack) {
		return readNBT(stack, "magazineType");
	}

	/// NBT utility ///
	protected static void writeNBT(ItemStack stack, String key, int value) {

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setInteger(key, value);
	}

	public static int readNBT(ItemStack stack, String key) {

		if(!stack.hasTagCompound())
			return 0;

		return stack.getTagCompound().getInteger(key);
	}

	@Override
	public Crosshair getCrosshair() {
		return mainConfig.crosshair;
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return new ModelResourceLocation(this.getRegistryName(), "inventory");
	}
}
