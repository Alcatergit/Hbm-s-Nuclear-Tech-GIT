package com.hbm.items.weapon;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import org.lwjgl.input.Mouse;

import com.hbm.config.GeneralConfig;
import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import static com.hbm.items.ItemEnums.EnumAmmo;
import com.hbm.handler.HbmKeybinds;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.interfaces.IItemHUD;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.GunAnimationPacket;
import com.hbm.packet.GunButtonPacket;
import com.hbm.packet.GunFXPacket;
import com.hbm.packet.GunFXPacket.FXType;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.render.misc.RenderScreenOverlay.Crosshair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunBase extends Item implements IHoldableWeapon, IItemHUD {

	public GunConfiguration mainConfig;
	public GunConfiguration altConfig;
	public GunConfiguration[] modeConfigs;
	public static Field hurtResistantTime;

	public GunConfiguration getCurrentConfig(ItemStack stack) {
		if(modeConfigs != null && modeConfigs.length > 1) {
			int m = getFireMode(stack);
			if(m >= 0 && m < modeConfigs.length) return modeConfigs[m];
		}
		return mainConfig;
	}

	public static int getFireMode(ItemStack stack) {
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("fireMode"))
			return -1;
		return readNBT(stack, "fireMode");
	}

	public static void setFireMode(ItemStack stack, int mode) {
		writeNBT(stack, "fireMode", mode);
	}

	@SideOnly(Side.CLIENT)
	public static boolean m1;// = false;
	@SideOnly(Side.CLIENT)
	public static boolean m2;// = false;
	public static boolean oldClickRight;
	public static boolean oldClickLeft;

	public ItemGunBase(GunConfiguration config, String s) {
		mainConfig = config;
		this.setMaxStackSize(1);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	public ItemGunBase(GunConfiguration config, GunConfiguration alt, String s) {
		this(config, s);
		altConfig = alt;
	}

	public ItemGunBase setModeConfigs(GunConfiguration... configs) {
		this.modeConfigs = configs;
		return this;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if(modeConfigs != null && modeConfigs.length > 1) {
			if(!worldIn.isRemote && getMag(stack) == 0 && !getIsReloading(stack)) {
				int m = getFireMode(stack);
				if(m < 0) m = 0;
				int next = (m + 1) % modeConfigs.length;
				setFireMode(stack, next);
				if(getMagType(stack) < 0 || getMagType(stack) >= modeConfigs[next].config.size()) {
					setMagType(stack, 0);
				}
				return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
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
			} else if(world.isRemote) {
				if(getIsReloading(stack)) {
					int gh = getGunHand(stack);
					if(gh == 0) stopReloadSound(EnumHand.MAIN_HAND);
					else if(gh == 1) stopReloadSound(EnumHand.OFF_HAND);
				}
			}
		}
		if(!world.isRemote) {
			if(getGunId(stack).isEmpty()) {
				writeNBTString(stack, "gunId", UUID.randomUUID().toString());
			} else if(hand != null && entity instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) entity;
				ItemStack other = p.getHeldItem(hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
				int gh = hand == EnumHand.MAIN_HAND ? 0 : 1;
				if(!other.isEmpty() && other.getItem() instanceof ItemGunBase && getGunId(stack).equals(getGunId(other))) {
					if(getGunHand(stack) != gh) {
						writeNBTString(stack, "gunId", UUID.randomUUID().toString());
					}
				}
				setGunHand(stack, gh);
			} else if(hand == null && entity instanceof EntityPlayer) {
				EntityPlayer p = (EntityPlayer) entity;
				if(getIsReloading(stack)) {
					setIsReloading(stack, false);
					resetReloadCycle(stack);
					if(mainConfig.reloadSound != null) {
						int gh = getGunHand(stack);
						EnumHand h = (gh == 1) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
						GunAnimationPacket stop = new GunAnimationPacket(mainConfig.reloadSound, h);
						for(EntityPlayerMP mp : p.getServer().getPlayerList().getPlayers()) {
							if(mp != p && mp.dimension == p.dimension && mp.getDistanceSq(p.posX, p.posY, p.posZ) < 4096D) {
								PacketDispatcher.wrapper.sendTo(stop, mp);
							}
						}
					}
				}
				String thisId = getGunId(stack);
				for(int i = 0; i < p.inventory.getSizeInventory(); i++) {
					ItemStack other = p.inventory.getStackInSlot(i);
					if(other != stack && !other.isEmpty() && other.getItem() instanceof ItemGunBase && thisId.equals(getGunId(other))) {
						writeNBTString(stack, "gunId", UUID.randomUUID().toString());
						break;
					}
				}
				setGunHand(stack, -1);
			}
		}
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		return true;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return oldStack.getItem() != newStack.getItem();
	}

	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, EnumHand hand) {

		boolean clickLeft = Mouse.isButtonDown(0);
		boolean clickRight = Mouse.isButtonDown(1);
		boolean left = m1;
		boolean right = m2;
		
		if(hand != null) {
			if(left && right) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m1 = false;
				m2 = false;
			}
			
			if((m1 || getIsMouseDown(stack)) && !clickLeft) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 0, hand));
				m1 = false;
				endActionClient(stack, world, entity, true, hand);
			}
			
			if((m2 || getIsAltDown(stack)) && !clickRight) {
				PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(false, (byte) 1, hand));
				m2 = false;
				endActionClient(stack, world, entity, false, hand);
			}
			
			if(mainConfig.reloadType != GunConfiguration.RELOAD_NONE || (altConfig != null && altConfig.reloadType != 0)) {
				boolean reloadDown = HbmKeybinds.reloadKey.isKeyDown();
				boolean prevReload = hand == EnumHand.OFF_HAND ? prevReloadDownOff : prevReloadDownMain;
				if(reloadDown && !prevReload && (getMag(stack) < getCurrentConfig(stack).ammoCap || (mainConfig.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0))) {
					setAimingId(hand, null);
					PacketDispatcher.wrapper.sendToServer(new GunButtonPacket(true, (byte) 2, hand));
				}
				if(hand == EnumHand.OFF_HAND) prevReloadDownOff = reloadDown; else prevReloadDownMain = reloadDown;
			}

			boolean aimDown = HbmKeybinds.aimKey.isKeyDown();
			if(aimDown && !getPrevAimDown(hand)) {
				setAimingId(hand, getIsAiming(hand) ? null : getGunId(stack));
			}
			setPrevAimDown(hand, aimDown);
		}
		oldClickLeft = m1;
		oldClickRight = m2;
	}

	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		if(getDelay(stack) > 0 && hand != null)
			setDelay(stack, getDelay(stack) - 1);
		
		if(getIsMouseDown(stack) && hand == null) {
			setIsMouseDown(stack, false);
		}
		
		if(getIsAltDown(stack) && hand == null) {
			setIsAltDown(stack, false);
		}
		if(GeneralConfig.enableGuns && mainConfig.firingMode == GunConfiguration.FIRE_AUTO && getIsMouseDown(stack) && tryShoot(stack, world, player, hand != null)) {

			fire(stack, world, player, hand);
			setDelay(stack, mainConfig.rateOfFire);
			// setMag(stack, getMag(stack) - 1);
		}

		if(getIsReloading(stack) && hand != null) {
			reload2(stack, world, player, hand);
		}
	}

	// tries to shoot, bullet checks are done here
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {

		if(main && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {

			if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				BulletConfiguration belt = getBeltType(player, stack, main);
				return getBeltSize(player, belt.ammo, belt.ammoMeta) > 0;

			} else {
				return getMag(stack) > 0;
			}
		}

		if(!main && altConfig != null && getDelay(stack) == 0 && !getIsReloading(stack) && getItemWear(stack) < mainConfig.durability) {

			if(altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				BulletConfiguration belt = getBeltType(player, stack, main);
				return getBeltSize(player, belt.ammo, belt.ammoMeta) > 0;
				
			} else {
				return getMag(stack) > 0;
			}
		}

		return false;
	}

	public boolean hasAmmo(ItemStack stack, EntityPlayer player, boolean main) {
		
		if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE || !main) {
			BulletConfiguration belt = getBeltType(player, stack, main);
			return getBeltSize(player, belt.ammo, belt.ammoMeta) > 0;
			
		} else {
			return getMag(stack) > 0;
		}
	}
	
	// called every time the gun shoots, overridden to change bullet
	// entity/special additions
	private void fire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		BulletConfiguration config = null;
		
		if(mainConfig.reloadType == GunConfiguration.RELOAD_NONE) {
			config = getBeltCfg(player, stack, true);
		} else {
			config = BulletConfigSyncingUtil.pullConfig(getCurrentConfig(stack).config.get(getMagType(stack)));
		}
		
		int bullets = getBullets(world, player, hand, config);
		
		for(int k = 0; k < getCurrentConfig(stack).roundsPerCycle; k++) {
			
			if(!hasAmmo(stack, player, true))
				break;
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config), hand);
			}
			
			useUpAmmo(player, stack, true);
			player.inventoryContainer.detectAndSendChanges();
			
			int wear = (int) Math.ceil(config.wear / (1F + EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack)));
			setItemWear(stack, getItemWear(stack) + wear);
		}
		
		if(mainConfig.firingSound != null) {
			world.playSound(player, player.posX, player.posY, player.posZ, mainConfig.firingSound, SoundCategory.PLAYERS, 1.0F, mainConfig.firingPitch);
			if(player instanceof EntityPlayerMP)
				PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(mainConfig.firingSound, 1.0F, mainConfig.firingPitch, hand), (EntityPlayerMP) player);
		}

		if(player.getDisplayName().toString().equals("Vic4Games")) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("type", "justTilt");
			nbt.setInteger("time", mainConfig.rateOfFire + 1);
			PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, player.posX, player.posY, player.posZ), (EntityPlayerMP) player);
		}
	}
	
	protected int getBullets(World world, EntityPlayer player, EnumHand hand, BulletConfiguration config){
		int bullets = config.bulletsMin;
		if(config.bulletsMax > config.bulletsMin)
			bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
		return bullets;
	}

	// unlike fire(), being called does not automatically imply success, some
	// things may still have to be handled before spawning the projectile
	protected void altFire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		if(altConfig == null)
			return;

		BulletConfiguration config = getBeltCfg(player, stack, false);
		
		int bullets = config.bulletsMin;
		
		for(int k = 0; k < altConfig.roundsPerCycle; k++) {

			if(!hasAmmo(stack, player, false))
				break;

			if(config.bulletsMax > config.bulletsMin)
				bullets += world.rand.nextInt(config.bulletsMax - config.bulletsMin);
			
			for(int i = 0; i < bullets; i++) {
				spawnProjectile(world, player, stack, BulletConfigSyncingUtil.getKey(config), hand);
			}
			
			useUpAmmo(player, stack, false);
			setItemWear(stack, getItemWear(stack) + config.wear);
			player.inventoryContainer.detectAndSendChanges();
		}
		
		if(altConfig.firingSound != null) {
			world.playSound(player, player.posX, player.posY, player.posZ, altConfig.firingSound, SoundCategory.PLAYERS, 1.0F, altConfig.firingPitch);
			if(player instanceof EntityPlayerMP)
				PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(altConfig.firingSound, 1.0F, altConfig.firingPitch, hand), (EntityPlayerMP) player);
		}
	}

	protected EntityBulletBase getBulletEntity(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand){
        return new EntityBulletBase(world, config, player, hand);
	}
	
	protected void spawnProjectile(World world, EntityPlayer player, ItemStack stack, int config, EnumHand hand) {
		world.spawnEntity(getBulletEntity(world, player, stack, config, hand));
		
		if(this.mainConfig.animations.containsKey(AnimType.CYCLE) && player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.CYCLE.ordinal(), hand), (EntityPlayerMP) player);
		PacketDispatcher.wrapper.sendToAllTracking(new GunFXPacket(player, hand, FXType.FIRE), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 1));
	}
	
	// called on click (server side, called by mouse packet)
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(mainConfig.firingMode == GunConfiguration.FIRE_MANUAL && getIsMouseDown(stack) && tryShoot(stack, world, player, main)) {
			fire(stack, world, player, hand);
			setDelay(stack, mainConfig.rateOfFire);
			//setMag(stack, getMag(stack) - 1);
			//useUpAmmo(player, stack, main);
		}
		if(!main && altConfig != null && tryShoot(stack, world, player, main)) {
			altFire(stack, world, player, hand);
			setDelay(stack, altConfig.rateOfFire);
			//useUpAmmo(player, stack, main);
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

		if(getMag(stack) >= getCurrentConfig(stack).ammoCap) {
			setIsReloading(stack, false);
			return;
		}

		if(getReloadCycle(stack) < 0) {

			if(getMag(stack) == 0)
				resetAmmoType(stack, world, player);

			int count = 1;

			if(mainConfig.reloadType == GunConfiguration.RELOAD_FULL) {

				count = getCurrentConfig(stack).ammoCap - getMag(stack);
			}	

			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(getCurrentConfig(stack).config.get(getMagType(stack)));
			Item ammo = cfg.ammo;

			int loadCount = 0;
			
			for(int i = 0; i < count; i++) {

				if(Library.hasInventoryItem(player.inventory, ammo) && getMag(stack) < getCurrentConfig(stack).ammoCap) {
					Library.consumeInventoryItem(player.inventory, ammo);
					setMag(stack, Math.min(getMag(stack) + cfg.ammoCount, getCurrentConfig(stack).ammoCap));
					loadCount++;
				} else {
					setIsReloading(stack, false);
					break;
				}
			}

			if(getMag(stack) >= getCurrentConfig(stack).ammoCap) {
				setIsReloading(stack, false);
			} else {
				resetReloadCycle(stack);
			}
			
			if(loadCount > 0){
				onAmmoLoad(world, player, stack, loadCount, hand);
			}

			if(loadCount > 0 && mainConfig.reloadSoundEnd && mainConfig.reloadSound != null) {
				world.playSound(player, player.posX, player.posY, player.posZ, mainConfig.reloadSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
				if(player instanceof EntityPlayerMP)
					PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(mainConfig.reloadSound, 1.0F, 1.0F, hand), (EntityPlayerMP) player);
			}

		} else {
			setReloadCycle(stack, getReloadCycle(stack) - 1);
		}

		if(stack != player.getHeldItem(hand)) {
			setReloadCycle(stack, 0);
			setIsReloading(stack, false);
		}
	}
	
	public void onAmmoLoad(World world, EntityPlayer player, ItemStack stack, int loadCount, EnumHand hand){
	}

	// server-side reload key routing: swap ammo type / reload / inspect
	public void tryStartReload(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		// sneaking + infinity = cycle ammo type (bypasses normal reload rules)
		if(player.isSneaking() && mainConfig.allowsInfinity && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0) {
			startReloadAction(stack, world, player, hand);
			return;
		}
		// mag full: do nothing, no INSPECT
		if(getMag(stack) >= getCurrentConfig(stack).ammoCap) {
			return;
		}
		// mag not full: normal check, INSPECT if no ammo
		if(canReload(stack, world, player)) {
			startReloadAction(stack, world, player, hand);
		} else if(player instanceof EntityPlayerMP) {
			PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.INSPECT.ordinal(), hand), (EntityPlayerMP) player);
		}
	}

	// initiates a reload
	public void startReloadAction(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		boolean swapped = false;

		// sneaking + infinity + mag full: swap to a different ammo type in inventory
		if(player.isSneaking() && mainConfig.allowsInfinity
				&& EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0
				&& getMag(stack) == getCurrentConfig(stack).ammoCap) {
			int currentType = getMagType(stack);
			List<Integer> configs = getCurrentConfig(stack).config;
			int newType = -1;
			for(int i = 0; i < configs.size(); i++) {
				if(i == currentType) continue;
				BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(configs.get(i));
				if(Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta)) {
					newType = i;
					break;
				}
			}
			if(newType < 0)
				return;
			setMag(stack, 0);
			setMagType(stack, newType);
			player.playSound(SoundEvents.BLOCK_PISTON_EXTEND, 1.0F, 1.0F);
			swapped = true;
		}

		if(!swapped && getMag(stack) == getCurrentConfig(stack).ammoCap)
			return;

		if(getIsReloading(stack))
			return;

		if(getMag(stack) == 0 && !swapped)
			this.resetAmmoType(stack, world, player);

		if(!mainConfig.reloadSoundEnd && mainConfig.reloadSound != null) {
			world.playSound(player, player.posX, player.posY, player.posZ, mainConfig.reloadSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
			if(player instanceof EntityPlayerMP)
				PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(mainConfig.reloadSound, 1.0F, 1.0F, hand), (EntityPlayerMP) player);
		}
		
		PacketDispatcher.wrapper.sendTo(new GunAnimationPacket(AnimType.RELOAD.ordinal(), hand), (EntityPlayerMP) player);

		setIsReloading(stack, true);
		resetReloadCycle(stack);
	}

	public boolean canReload(ItemStack stack, World world, EntityPlayer player) {

		if(getMag(stack) == 0) {

			for(Integer config : getCurrentConfig(stack).config) {

				BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);

				if(Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta)) {
					return true;
				}
			}

		} else {

			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(getCurrentConfig(stack).config.get(getMagType(stack)));
            return Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta);
		}

		return false;
	}

	// searches the player's inv for next fitting ammo type and changes the
	// gun's mag
	protected void resetAmmoType(ItemStack stack, World world, EntityPlayer player) {

		for(Integer config : getCurrentConfig(stack).config) {

			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);

			if(Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta)) {
				setMagType(stack, getCurrentConfig(stack).config.indexOf(config));
				break;
			}
		}
	}

	public static String getColor(int a, int b){
		float fraction = 100F * a/b;
		if(fraction > 75)
			return "§a";
		if(fraction > 25)
			return "§e";
		return "§c";
	}

	// item mouseover text
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		BulletConfiguration ammoCfg = BulletConfigSyncingUtil.pullConfig(getCurrentConfig(stack).config.get(getMagType(stack)));
		Item ammo = ammoCfg.ammo;
		int ammoMeta = ammoCfg.ammoMeta;

		if(getCurrentConfig(stack).ammoCap > 0){
			int mag = getMag(stack);
			list.add("Ammo: "+ getColor(mag, getCurrentConfig(stack).ammoCap) + mag + " §2/ " + getCurrentConfig(stack).ammoCap);
		}
		else{
			list.add("Ammo: §6Belt");
		}

		if(ammo == ModItems.ammo_standard) {
			list.add("Ammo Type: §e" + I18n.format(ammo.getTranslationKey() + "." + EnumAmmo.values()[ammoMeta].toString().toLowerCase() + ".name"));
		} else {
			list.add("Ammo Type: §e" + I18n.format(ammo.getTranslationKey() + ".name"));
		}

		int dura = mainConfig.durability - getItemWear(stack);

		if(dura < 0)
			dura = 0;

		list.add("Durability: " + getColor(dura, mainConfig.durability) + dura + " §2/ " + mainConfig.durability);

		// if(MainRegistry.enableDebugMode) {
		list.add("");
		list.add("Name: " + mainConfig.name);
		list.add("Manufacturer: " + mainConfig.manufacturer);
		// }

		if(!mainConfig.comment.isEmpty()) {
			list.add("");
			for(String s : mainConfig.comment)
				list.add("§6"+TextFormatting.ITALIC + s);
		}

		if(GeneralConfig.enableExtendedLogging) {
			list.add("");
			list.add("Type: " + getMagType(stack));
			list.add("Is Reloading: " + getIsReloading(stack));
			list.add("Reload Cycle: " + getReloadCycle(stack));
			list.add("RoF Cooldown: " + getDelay(stack));
		}
	}

	public static BulletConfiguration getBeltType(EntityPlayer player, ItemStack stack, boolean main) {
		if(!(stack.getItem() instanceof ItemGunBase))
			return null;
		ItemGunBase gun = (ItemGunBase)stack.getItem();
		GunConfiguration guncfg = main ? gun.mainConfig : (gun.altConfig != null ? gun.altConfig : gun.mainConfig);
		BulletConfiguration ammo = BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0));

		for(Integer config : guncfg.config) {
			
			BulletConfiguration cfg = BulletConfigSyncingUtil.pullConfig(config);
			
			if(Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta)) {
				ammo = cfg;
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
			
			if(Library.hasInventoryItem(player.inventory, cfg.ammo, cfg.ammoMeta)) {
				return cfg;
			}
		}

		return BulletConfigSyncingUtil.pullConfig(guncfg.config.get(0));
	}

	// returns ammo capacity of belt-weapons for current ammo
	public static int getBeltSize(EntityPlayer player, Item ammo, int meta) {

		int amount = 0;

		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && stack.getItem() == ammo && stack.getItemDamage() == meta)
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

		if(config.reloadType != GunConfiguration.RELOAD_NONE) {
			setMag(stack, getMag(stack) - 1);
		} else {
			BulletConfiguration belt = getBeltType(player, stack, main);
			Library.consumeInventoryItem(player.inventory, belt.ammo, belt.ammoMeta);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void onFireClient(ItemStack stack, EntityPlayer player, boolean shouldDoThirdPerson){
	}
	
	@SideOnly(Side.CLIENT)
	public void playerWorldRender(EntityPlayer player, RenderWorldLastEvent e, EnumHand hand){
	}

	/// sets reload cycle to config defult ///
	public void resetReloadCycle(ItemStack stack) {
		writeNBT(stack, "reload", getReloadDuration(stack));
	}

	public int getReloadDuration(ItemStack stack){
		return ((ItemGunBase) stack.getItem()).mainConfig.reloadDuration;
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

	protected static void writeNBTString(ItemStack stack, String key, String value) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString(key, value);
	}

	public static String readNBTString(ItemStack stack, String key) {
		if(!stack.hasTagCompound())
			return "";
		return stack.getTagCompound().getString(key);
	}

	public static String getGunId(ItemStack stack) {
		return readNBTString(stack, "gunId");
	}

	public static int getGunHand(ItemStack stack) {
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("gunHand"))
			return -1;
		return stack.getTagCompound().getInteger("gunHand");
	}

	public static void setGunHand(ItemStack stack, int hand) {
		writeNBT(stack, "gunHand", hand);
	}

	public static String aimingMainId = null;
	public static String aimingOffId = null;

	public static boolean getIsAiming(EnumHand hand) {
		return hand == EnumHand.OFF_HAND ? aimingOffId != null : aimingMainId != null;
	}

	public static void setAimingId(EnumHand hand, String gunId) {
		if(hand == EnumHand.OFF_HAND) aimingOffId = gunId;
		else aimingMainId = gunId;
	}

	public static float aimingProgressMain = 0F;
	public static float prevAimingProgressMain = 0F;
	public static float aimingProgressOff = 0F;
	public static float prevAimingProgressOff = 0F;
	private static long lastAimUpdateMain = 0L;
	private static long lastAimUpdateOff = 0L;

	public static float getAimingProgress(EnumHand hand) {
		return hand == EnumHand.OFF_HAND ? aimingProgressOff : aimingProgressMain;
	}

	public static float getPrevAimingProgress(EnumHand hand) {
		return hand == EnumHand.OFF_HAND ? prevAimingProgressOff : prevAimingProgressMain;
	}

	public static void resetAimingProgress(EnumHand hand) {
		if(hand == EnumHand.OFF_HAND) { aimingProgressOff = 0F; prevAimingProgressOff = 0F; }
		else { aimingProgressMain = 0F; prevAimingProgressMain = 0F; }
	}

	public static boolean prevReloadDownMain = false;
	public static boolean prevReloadDownOff = false;

	public static boolean prevAimDownMain = false;
	public static boolean prevAimDownOff = false;

	public static boolean getPrevAimDown(EnumHand hand) {
		return hand == EnumHand.OFF_HAND ? prevAimDownOff : prevAimDownMain;
	}

	public static void setPrevAimDown(EnumHand hand, boolean value) {
		if(hand == EnumHand.OFF_HAND) prevAimDownOff = value;
		else prevAimDownMain = value;
	}

	public static void updateAiming(EnumHand hand, boolean aiming) {
		boolean off = hand == EnumHand.OFF_HAND;
		float cur = off ? aimingProgressOff : aimingProgressMain;
		if(off) prevAimingProgressOff = cur; else prevAimingProgressMain = cur;
		long now = System.currentTimeMillis();
		long last = off ? lastAimUpdateOff : lastAimUpdateMain;
		if(last == 0L) last = now;
		float dt = Math.min((now - last) / 1000.0F, 0.1F);
		if(off) lastAimUpdateOff = now; else lastAimUpdateMain = now;
		float target = aiming ? 1F : 0F;
		float step = 5.0F * dt;
		if(cur < target) cur = Math.min(target, cur + step);
		else if(cur > target) cur = Math.max(target, cur - step);
		if(off) aimingProgressOff = cur; else aimingProgressMain = cur;
	}

	@Override
	public Crosshair getCrosshair() {
		return mainConfig.crosshair;
	}
	
	@SideOnly(Side.CLIENT)
	public static ISound reloadSoundMain = null;
	public static ISound reloadSoundOff = null;

	@SideOnly(Side.CLIENT)
	public static void playGunSound(final EntityPlayer player, SoundEvent sound, float volume, float pitch, EnumHand hand) {
		MovingSound s = new MovingSound(sound, SoundCategory.PLAYERS) {
			{
				this.repeat = false;
				this.volume = volume;
				this.pitch = pitch;
				this.xPosF = (float) player.posX;
				this.yPosF = (float) player.posY;
				this.zPosF = (float) player.posZ;
			}
			@Override
			public void update() {
				this.xPosF = (float) player.posX;
				this.yPosF = (float) player.posY;
				this.zPosF = (float) player.posZ;
			}
		};
		Minecraft.getMinecraft().getSoundHandler().playSound(s);
		if(hand == EnumHand.OFF_HAND) reloadSoundOff = s;
		else reloadSoundMain = s;
	}

	@SideOnly(Side.CLIENT)
	public static void stopReloadSound(EnumHand hand) {
		SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
		ISound s = hand == EnumHand.OFF_HAND ? reloadSoundOff : reloadSoundMain;
		if(s != null) {
			handler.stopSound(s);
			if(hand == EnumHand.OFF_HAND) reloadSoundOff = null;
			else reloadSoundMain = null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void startAnim(EntityPlayer player, ItemStack stack, int slot, AnimType type){
		GunConfiguration config = ((ItemGunBase) stack.getItem()).mainConfig;
		BusAnimation animation = config.animations.get(type);

		if(animation != null) {
			HbmAnimations.hotbar[slot] = new Animation(stack.getItem().getTranslationKey(), System.currentTimeMillis(), animation);
			HbmAnimations.hotbar[slot].stack = stack;
			HbmAnimations.hotbar[slot].gunId = getGunId(stack);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(Pre event, ElementType type, EntityPlayer player, ItemStack stack, EnumHand hand) {
		ItemGunBase gun = ((ItemGunBase) player.getHeldItem(hand).getItem());
		GunConfiguration gcfg = gun.getCurrentConfig(stack);
		if(event.getType() == ElementType.HOTBAR){
			int magType = ItemGunBase.getMagType(player.getHeldItem(hand));
			if(magType >= 0 && magType < gun.getCurrentConfig(stack).config.size()) {
			BulletConfiguration bcfg = BulletConfigSyncingUtil.pullConfig(gun.getCurrentConfig(stack).config.get(magType));
	
			ItemStack ammo = (bcfg.ammo == ModItems.ammo_standard) ? new ItemStack(bcfg.ammo, 1, bcfg.ammoMeta) : new ItemStack(bcfg.ammo);
			int count = ItemGunBase.getMag(player.getHeldItem(hand));
			int max = gcfg.ammoCap;
			boolean showammo = gcfg.showAmmo;
	
			if(gcfg.reloadType == GunConfiguration.RELOAD_NONE) {
				BulletConfiguration belt = ItemGunBase.getBeltType(player, player.getHeldItem(hand), true);
				ammo = (belt.ammo == ModItems.ammo_standard) ? new ItemStack(belt.ammo, 1, belt.ammoMeta) : new ItemStack(belt.ammo);
				count = ItemGunBase.getBeltSize(player, belt.ammo, belt.ammoMeta);
				max = -1;
			}
	
			int dura = ItemGunBase.getItemWear(player.getHeldItem(hand)) * 50 / gcfg.durability;
	
			RenderScreenOverlay.renderAmmo(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, max, dura, hand, showammo);
	
			if(gun.altConfig != null && gun.altConfig.reloadType == GunConfiguration.RELOAD_NONE) {
				ItemStack oldAmmo = ammo;
				BulletConfiguration altBelt = ItemGunBase.getBeltType(player, player.getHeldItem(hand), false);
				ammo = (altBelt.ammo == ModItems.ammo_standard) ? new ItemStack(altBelt.ammo, 1, altBelt.ammoMeta) : new ItemStack(altBelt.ammo);
	
				if(!ItemStack.areItemStacksEqual(ammo, oldAmmo)) {
					count = ItemGunBase.getBeltSize(player, altBelt.ammo, altBelt.ammoMeta);
					RenderScreenOverlay.renderAmmoAlt(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ammo, count, hand);
				}
			}
			}
		}
		if(event.getType() == ElementType.CROSSHAIRS && GeneralConfig.enableCrosshairs && !(hand == EnumHand.OFF_HAND && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IHoldableWeapon)){
		
			event.setCanceled(true);
			if(((IHoldableWeapon) player.getHeldItem(hand).getItem()).hasCustomHudElement()){
				((IHoldableWeapon) player.getHeldItem(hand).getItem()).renderHud(event.getResolution(), Minecraft.getMinecraft().ingameGUI, player.getHeldItemMainhand(), event.getPartialTicks());
			} else {
				if(!(gcfg.hasSights && player.isSneaking()))
					RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, ((IHoldableWeapon) player.getHeldItem(hand).getItem()).getCrosshair());
				else
					RenderScreenOverlay.renderCustomCrosshairs(event.getResolution(), Minecraft.getMinecraft().ingameGUI, Crosshair.NONE);
			}
		}
		
	}
}
