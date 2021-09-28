package com.hbm.items.gear;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemGeigerCounter;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.ClientProxy;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorFSB extends ItemArmor {

	public static Field nextStepDistance = null;
	public static Field distanceWalkedOnStepModified = null;
	
	@SideOnly(Side.CLIENT)
	public static boolean flashlightPress;
	
	private String texture = "";
	private ResourceLocation overlay = null;
	public List<PotionEffect> effects = new ArrayList<PotionEffect>();
	public HashMap<String, Float> resistance = new HashMap<String, Float>();
	public float blastProtection = -1;
	public float damageCap = -1;
	public float damageMod = -1;
	public float damageThreshold = 0;
	public boolean fireproof = false;
	public boolean noHelmet = false;
	public boolean vats = false;
	public boolean thermal = false;
	public boolean geigerSound = false;
	public boolean customGeiger = false;
	public boolean hardLanding = false;
	public Vec3d flashlightPosition = null;
	public double gravity = 0;
	public SoundEvent step;
	public SoundEvent jump;
	public SoundEvent fall;
	
	public ArmorFSB(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String texture, String name) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.texture = texture;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public static boolean hasFSBArmor(EntityPlayer player) {
		if(player == null)
			return false;

		ItemStack plate = player.inventory.armorInventory.get(2);

		if(plate != null && plate.getItem() instanceof ArmorFSB) {

			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			boolean noHelmet = chestplate.noHelmet;
			
			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {

				ItemStack armor = player.inventory.armorInventory.get(i);

				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;

				if(((ArmorFSB)armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;

				if(!((ArmorFSB)armor.getItem()).isArmorEnabled(armor))
					return false;
			}

			return true;
		}

		return false;
    }
	
	public static boolean hasFSBArmorHelmet(EntityPlayer player){
		ItemStack plate = player.inventory.armorInventory.get(2);

		if(plate != null && plate.getItem() instanceof ArmorFSB) {
			return !((ArmorFSB)plate.getItem()).noHelmet && hasFSBArmor(player);
		}
		return false;
	}

    public static boolean hasFSBArmorIgnoreCharge(EntityPlayer player) {

		ItemStack plate = player.inventory.armorInventory.get(2);

		if(plate != null && plate.getItem() instanceof ArmorFSB) {

			ArmorFSB chestplate = (ArmorFSB)plate.getItem();
			boolean noHelmet = chestplate.noHelmet;

			for(int i = 0; i < (noHelmet ? 3 : 4); i++) {

				ItemStack armor = player.inventory.armorInventory.get(i);

				if(armor == null || !(armor.getItem() instanceof ArmorFSB))
					return false;

				if(((ArmorFSB)armor.getItem()).getArmorMaterial() != chestplate.getArmorMaterial())
					return false;
			}
			return true;
		}

		return false;
    }

	
    public static void handleAttack(LivingAttackEvent event) {

		EntityLivingBase e = event.getEntityLiving();

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;

			if(ArmorFSB.hasFSBArmor(player)) {

				ItemStack plate = player.inventory.armorInventory.get(2);

				ArmorFSB chestplate = (ArmorFSB)plate.getItem();
				
				chestplate.handleAttack(event, chestplate);
			}
		}
    }

    public void handleAttack(LivingAttackEvent event, ArmorFSB chestplate){
    	if(chestplate.damageThreshold >= event.getAmount() && !event.getSource().isUnblockable()) {
			event.setCanceled(true);
		}

		if(chestplate.fireproof && event.getSource().isFireDamage()) {
			event.getEntityLiving().extinguish();
			event.setCanceled(true);
		}
		if(chestplate.resistance.get(event.getSource().getDamageType()) != null &&
				chestplate.resistance.get(event.getSource().getDamageType()) <= 0) {
			event.setCanceled(true);
		}
    }
	
    public static void handleHurt(LivingHurtEvent event) {

		EntityLivingBase e = event.getEntityLiving();

		if(e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;

			if(ArmorFSB.hasFSBArmor(player)) {

				ArmorFSB chestplate = (ArmorFSB)player.inventory.armorInventory.get(2).getItem();

				chestplate.handleHurt(event, chestplate);
			}
		}
    }
    
    public void handleHurt(LivingHurtEvent event, ArmorFSB chestplate){
    	if(event.getAmount() < 100){
			if(!event.getSource().isUnblockable())
				event.setAmount(event.getAmount()-chestplate.damageThreshold);
			
			if(chestplate.damageMod != -1) {
				event.setAmount(event.getAmount()*chestplate.damageMod);
			}

			if(chestplate.resistance.get(event.getSource().getDamageType()) != null) {
				event.setAmount(event.getAmount()*chestplate.resistance.get(event.getSource().getDamageType()));
			}

			if(chestplate.blastProtection != -1 && event.getSource().isExplosion()) {
				event.setAmount(event.getAmount()*chestplate.blastProtection);
			}

			if(chestplate.damageCap != -1) {
				event.setAmount(Math.min(event.getAmount(), chestplate.damageCap));
			}
		}
    }
	
	public boolean isArmorEnabled(ItemStack stack) {
		return true;
	}
	
    public static void handleTick(TickEvent.PlayerTickEvent event) {

		EntityPlayer player = event.player;

		if(ArmorFSB.hasFSBArmor(player)) {

			ItemStack plate = player.inventory.armorInventory.get(2);

			ArmorFSB chestplate = (ArmorFSB) plate.getItem();

			if(!chestplate.effects.isEmpty()) {

				for(PotionEffect i : chestplate.effects) {
					player.addPotionEffect(new PotionEffect(i.getPotion(), i.getDuration(), i.getAmplifier(), i.getIsAmbient(), i.doesShowParticles()));
				}
			}

			if(!player.capabilities.isFlying && !player.isInWater())
				player.motionY -= chestplate.gravity;
			
			if(chestplate.step != null && player.world.isRemote && player.onGround && event.phase == Phase.START) {

				try {
					if(nextStepDistance == null)
						nextStepDistance = ReflectionHelper.findField(Entity.class, "nextStepDistance", "field_70150_b");
					if(distanceWalkedOnStepModified == null)
						distanceWalkedOnStepModified = ReflectionHelper.findField(Entity.class, "distanceWalkedOnStepModified", "field_82151_R");

					if(player.getEntityData().getFloat("hfr_nextStepDistance") == 0) {
						player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));
					}

	                int px = MathHelper.floor(player.posX);
	                int py = MathHelper.floor(player.posY - 0.2D);
	                int pz = MathHelper.floor(player.posZ);
	                IBlockState block = player.world.getBlockState(new BlockPos(px, py, pz));
					if(block.getMaterial() != Material.AIR && player.getEntityData().getFloat("hfr_nextStepDistance") <= distanceWalkedOnStepModified.getFloat(player)){
						player.playSound(chestplate.step, 1.0F, 1.0F);
					}

					player.getEntityData().setFloat("hfr_nextStepDistance", nextStepDistance.getFloat(player));

				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		}
    }
	
	
	public static void handleJump(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory.get(2).getItem();

			if(chestplate.jump != null)
				player.playSound(chestplate.jump, 1.0F, 1.0F);
		}
	}

	public static void handleFall(EntityPlayer player) {

		if(ArmorFSB.hasFSBArmor(player)) {

			ArmorFSB chestplate = (ArmorFSB) player.inventory.armorInventory.get(2).getItem();

			if(chestplate.hardLanding && player.fallDistance > 10) {

				// player.playSound(Block.soundTypeAnvil.func_150496_b(), 2.0F,
				// 0.5F);

				List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(3, 0, 3));

				for(Entity e : entities) {

					Vec3 vec = Vec3.createVectorHelper(player.posX - e.posX, 0, player.posZ - e.posZ);

					if(vec.lengthVector() < 3) {

						double intensity = 3 - vec.lengthVector();
						e.motionX += vec.xCoord * intensity * -2;
						e.motionY += 0.1D * intensity;
						e.motionZ += vec.zCoord * intensity * -2;

						e.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), (float) (intensity * 10));
					}
				}
				// return;
			}
			
			if(chestplate.fall != null)
				player.playSound(chestplate.fall, 1.0F, 1.0F);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateClient(ItemStack stack, ArmorFSB fsbarmor, World world, Entity entity, int slot, boolean selected){
		if(fsbarmor.flashlightPosition != null){
			if(!flashlightPress && ClientProxy.fsbFlashlight.isKeyDown()){
				PacketDispatcher.wrapper.sendToServer(new KeybindPacket(1));
			}
			flashlightPress = ClientProxy.fsbFlashlight.isKeyDown();
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
	//	System.out.println("regular update");
		if(this.armorType != EntityEquipmentSlot.CHEST || !(entity instanceof EntityPlayer))
			return;

		if(!hasFSBArmor((EntityPlayer) entity))
			return;
		ArmorFSB fsbarmor = (ArmorFSB) ((EntityPlayer) entity).inventory.armorInventory.get(2).getItem();
		
		if(world.isRemote){
			updateClient(stack, fsbarmor, world, entity, itemSlot, isSelected);
		}
		
		if(!fsbarmor.geigerSound)
			return;
		
		if(world.getTotalWorldTime() % 5 == 0) {

			int x = check((EntityPlayer)entity, world, (int)entity.posX, (int)entity.posY, (int)entity.posZ);

			if(x > 0) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1)
					list.add(0);
				if(x < 5)
					list.add(0);
				if(x < 10)
					list.add(1);
				if(x > 5 && x < 15)
					list.add(2);
				if(x > 10 && x < 20)
					list.add(3);
				if(x > 15 && x < 25)
					list.add(4);
				if(x > 20 && x < 30)
					list.add(5);
				if(x > 25)
					list.add(6);

				int r = list.get(world.rand.nextInt(list.size()));

				if(r > 0)
					world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.geigerSounds[r-1], SoundCategory.PLAYERS, 1.0F, 1.0F);
			} else if(world.rand.nextInt(50) == 0) {
				world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.geigerSounds[world.rand.nextInt(1)], SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
	}
	
	//Drillgon200: This method is literally never called in 1.12 for some unknown reason even though it absolutely looks like it should be.
	//@Override
	//public void onArmorTick(World world, EntityPlayer entity, ItemStack itemStack) {
	//	
	//}
	
	public static int check(@Nullable EntityPlayer player, World world, int x, int y, int z) {
		return ItemGeigerCounter.check(player, world, new BlockPos(x, y, z));
		/*RadiationSavedData data = RadiationSavedData.getData(world);

		int rads = (int)Math.ceil(data.getRadNumFromCoord(new BlockPos(x, y, z)));

		return rads;*/
	}
	
	//For crazier stuff not possible without hooking the event
    @SideOnly(Side.CLIENT)
	public void handleOverlay(RenderGameOverlayEvent.Pre event, EntityPlayer player) { }
	
	public ArmorFSB enableThermalSight(boolean thermal) {
		this.thermal = thermal;
		return this;
	}
	
	public ArmorFSB setHasGeigerSound(boolean geiger) {
		this.geigerSound = geiger;
		return this;
	}

	public ArmorFSB setHasCustomGeiger(boolean geiger) {
		this.customGeiger = geiger;
		return this;
	}
	
	public ArmorFSB setHasHardLanding(boolean hardLanding) {
		this.hardLanding = hardLanding;
		return this;
	}

	public ArmorFSB setGravity(double gravity) {
		this.gravity = gravity;
		return this;
	}
	
	public ArmorFSB setBlastProtection(float blastProtection) {
		this.blastProtection = blastProtection;
		return this;
	}

	public ArmorFSB setStep(SoundEvent step) {
		this.step = step;
		return this;
	}
	
	public ArmorFSB setJump(SoundEvent jump) {
		this.jump = jump;
		return this;
	}

	public ArmorFSB setFall(SoundEvent fall) {
		this.fall = fall;
		return this;
	}
	
	public ArmorFSB addEffect(PotionEffect effect) {
		effects.add(effect);
		return this;
	}
	
	public ArmorFSB addResistance(String damage, float mod) {
		resistance.put(damage, mod);
		return this;
	}
	
	public ArmorFSB setCap(float cap) {
		this.damageCap = cap;
		return this;
	}
	
	public ArmorFSB setMod(float mod) {
		this.damageMod = mod;
		return this;
	}
	
	public ArmorFSB setThreshold(float threshold) {
		this.damageThreshold = threshold;
		return this;
	}
	
	public ArmorFSB setFireproof(boolean fire) {
		this.fireproof = fire;
		return this;
	}
	
	public ArmorFSB setNoHelmet(boolean noHelmet) {
		this.noHelmet = noHelmet;
		return this;
	}
	
	public ArmorFSB enableVATS(boolean vats) {
		this.vats = vats;
		return this;
	}
	
	public ArmorFSB enableFlashlight(Vec3d pos){
		this.flashlightPosition = pos;
		return this;
	}
	
	public ArmorFSB setOverlay(String path) {
		this.overlay = new ResourceLocation(path);
		return this;
	}
	
	public ArmorFSB cloneStats(ArmorFSB original) {
		//lists aren't being modified after instantiation, so there's no need to dereference
		this.effects = original.effects;
		this.resistance = original.resistance;
		this.damageCap = original.damageCap;
		this.damageMod = original.damageMod;
		this.damageThreshold = original.damageThreshold;
		this.fireproof = original.fireproof;
		this.blastProtection = original.blastProtection;
		this.noHelmet = original.noHelmet;
		this.vats = original.vats;
		this.geigerSound = original.geigerSound;
		this.customGeiger = original.customGeiger;
		this.thermal = original.thermal;
		this.hardLanding = original.hardLanding;
		this.gravity = original.gravity;
		this.step = original.step;
		this.jump = original.jump;
		this.fall = original.fall;
		this.flashlightPosition = original.flashlightPosition;
		//overlay doesn't need to be copied because it's helmet exclusive
		return this;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return texture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(TextFormatting.GOLD + I18nUtil.resolveKey("armor.fullSetBonus"));
    	
    	if(!effects.isEmpty()) {
    		
    		for(PotionEffect effect : effects) {
	    		list.add(TextFormatting.AQUA + "  " + I18n.format(effect.getPotion().getName()));
    		}
    	}
    	
    	if(!resistance.isEmpty()) {

        	for(Entry<String, Float> struct : resistance.entrySet()) {
        		if(struct.getValue() != 0)
        			list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.damageModifier", struct.getValue(), I18n.format(struct.getKey())));
        		else
        			list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.nullDamage", I18n.format(struct.getKey())));
        	}
    	}
    	
    	if(blastProtection != -1) {
    		list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.blastProtection", blastProtection));
    	}
    	
    	if(damageCap != -1) {
    		list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.cap", damageCap));
    	}
    	
    	if(damageMod != -1) {
    		list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.modifier", damageMod));
    	}
    	
    	if(damageThreshold > 0) {
    		list.add(TextFormatting.YELLOW + "  " + I18nUtil.resolveKey("armor.threshold", damageThreshold));
    	}
    	
    	if(fireproof) {
    		list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.fireproof"));
    	}
    	
    	if(geigerSound) {
    		list.add(TextFormatting.GOLD + "  " + I18nUtil.resolveKey("armor.geigerSound"));
    	}

    	if(customGeiger) {
    		list.add(TextFormatting.GOLD + "  " + I18nUtil.resolveKey("armor.geigerHUD"));
    	}
    	
    	if(vats) {
    		list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.vats"));
    	}
    	
    	if(thermal) {
    		list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.thermal"));
    	}
    	
    	if(hardLanding) {
			list.add(TextFormatting.RED + "  " + I18nUtil.resolveKey("armor.hardLanding"));
		}
    	
    	if(gravity != 0) {
    		list.add(TextFormatting.BLUE + "  " + I18nUtil.resolveKey("armor.gravity", gravity));
    	}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		if(overlay == null)
    		return;
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
        RenderHelper.startDrawingTexturedQuads();
        RenderHelper.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        RenderHelper.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        RenderHelper.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
