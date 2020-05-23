package com.hbm.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.capability.RadiationCapability;
import com.hbm.capability.RadiationCapability.EntityRadiationProvider;
import com.hbm.capability.RadiationCapability.IEntityRadioactive;
import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.entity.projectile.EntityChopperMine;
import com.hbm.handler.HazmatRegistry;
import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.potion.HbmPotion;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.conductor.TileEntityCable;
import com.hbm.tileentity.conductor.TileEntityCableSwitch;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineTransformer;
import com.hbm.tileentity.machine.TileEntityPylonRedWire;
import com.hbm.tileentity.machine.TileEntityWireCoated;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

public class Library {

	static Random rand = new Random();
	
	public static String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static String TacoRedneck = "5aee1e3d-3767-4987-a222-e7ce1fbdf88e";
	// Earl0fPudding
	public static String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	// anna20
	public static String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	public static String rodolphito = "c3f5e449-6d8c-4fe3-acc9-47ef50e7e7ae";
	public static String LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f";
	// twillycorn
	public static String CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1";
	public static String dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e";
	public static String Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd";
	public static String Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda";
	public static String Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f";
	public static String Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb";
	public static String SteelCourage = "ac49720b-4a9a-4459-a26f-bee92160287a";
	public static String Ducxkskiziko = "122fe98f-be19-49ca-a96b-d4dee4f0b22e";
	public static String Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b";

	public static List<String> superuser = new ArrayList<String>();

	// Drillgon200: Not like super users are used for anything, but they could
	// in the future I guess.
	public static void initSuperusers() {
		superuser.add(HbMinecraft);
		superuser.add(TacoRedneck);
		superuser.add(LPkukin);
		superuser.add(Dafnik);
		superuser.add(a20);
		superuser.add(rodolphito);
		// Drillgon200: Pretty sure he did install NEI.
		superuser.add(Ducxkskiziko);
		superuser.add(Drillgon);
	}

	public static boolean checkForAsbestos(EntityPlayer player) {

		if(checkArmor(player, ModItems.asbestos_helmet, ModItems.asbestos_plate, ModItems.asbestos_legs, ModItems.asbestos_boots)) {
			return true;
		}

		return false;
	}

	public static boolean checkForHazmat(EntityPlayer player) {
		if(checkArmor(player, ModItems.hazmat_helmet, ModItems.hazmat_plate, ModItems.hazmat_legs, ModItems.hazmat_boots) || checkArmor(player, ModItems.hazmat_helmet_red, ModItems.hazmat_plate_red, ModItems.hazmat_legs_red, ModItems.hazmat_boots_red) || checkArmor(player, ModItems.hazmat_helmet_grey, ModItems.hazmat_plate_grey, ModItems.hazmat_legs_grey, ModItems.hazmat_boots_grey) || checkArmor(player, ModItems.t45_helmet, ModItems.t45_plate, ModItems.t45_legs, ModItems.t45_boots) || checkArmor(player, ModItems.schrabidium_helmet, ModItems.schrabidium_plate, ModItems.schrabidium_legs, ModItems.schrabidium_boots) || checkForHaz2(player)) {

			return true;
		}

		if(player.isPotionActive(HbmPotion.mutation))
			return true;

		return false;
	}

	// Drillgon200: Is there a reason for this method? I don't know and I don't
	// care to find out.
	public static boolean checkForHaz2(EntityPlayer player) {

		if(checkArmor(player, ModItems.hazmat_paa_helmet, ModItems.hazmat_paa_plate, ModItems.hazmat_paa_legs, ModItems.hazmat_paa_boots) || checkArmor(player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)) {
			return true;
		}

		return false;
	}
	
	public static boolean checkForFaraday(EntityPlayer player) {
		
		NonNullList<ItemStack> armor = player.inventory.armorInventory;
		
		if(armor.get(0).isEmpty() || armor.get(1).isEmpty() || armor.get(2).isEmpty() || armor.get(3).isEmpty()) return false;
		
		if(isFaradayArmor(armor.get(0).getItem()) &&
				isFaradayArmor(armor.get(1).getItem()) &&
				isFaradayArmor(armor.get(2).getItem()) &&
				isFaradayArmor(armor.get(3).getItem()))
			return true;
		
		return false;
	}
	
	public static final String[] metals = new String[] {
			"chainmail",
			"iron",
			"silver",
			"gold",
			"platinum",
			"tin",
			"lead",
			"schrabidium",
			"euphemium",
			"steel",
			"titanium",
			"alloy",
			"copper",
			"bronze",
			"electrum",
			"t45",
			"hazmat", //also count because rubber is insulating
			"rubber"
	};
	
	public static boolean isFaradayArmor(Item item) {
		
		String name = item.getUnlocalizedName();
		
		for(String metal : metals) {
			
			if(name.toLowerCase().contains(metal))
				return true;
		}
		
		return false;
	}
	
	public static boolean checkForHeld(EntityPlayer player, Item item) {
		return player.getHeldItemMainhand().getItem() == item || player.getHeldItemOffhand().getItem() == item;
	}
	
	public static boolean checkForFiend(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt, 2) && checkForHeld(player, ModItems.shimmer_sledge);
	}
	
	public static boolean checkForFiend2(EntityPlayer player) {
		
		return checkArmorPiece(player, ModItems.jackt2, 2) && checkForHeld(player, ModItems.shimmer_axe);
	}

	public static void damageSuit(EntityPlayer player, int slot, int amount) {

		if(player.inventory.armorInventory.get(slot) == ItemStack.EMPTY)
			return;

		int j = player.inventory.armorInventory.get(slot).getItemDamage();
		player.inventory.armorInventory.get(slot).setItemDamage(j += amount);

		if(player.inventory.armorInventory.get(slot).getItemDamage() >= player.inventory.armorInventory.get(slot).getMaxDamage()) {
			System.out.println(player.inventory.armorInventory.get(slot).getMaxDamage());
			player.inventory.armorInventory.set(slot, ItemStack.EMPTY);
		}
	}

	public static void applyRadData(Entity e, float f) {
		if(!(e instanceof EntityLivingBase))
			return;

		EntityLivingBase entity = (EntityLivingBase) e;
		
		if(e instanceof EntityPlayer && ((EntityPlayer) e).isSpectator())
			return;
		
		if(entity.isPotionActive(HbmPotion.mutation))
			return;

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			float koeff = 5.0F;
			f *= (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player));
		}

		if(entity.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
			RadiationCapability.IEntityRadioactive ent = entity.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static void applyRadDirect(Entity entity, float f) {

		if(!(entity instanceof EntityLivingBase))
			return;

		if(((EntityLivingBase) entity).isPotionActive(HbmPotion.mutation))
			return;

		if(entity.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null)) {
			RadiationCapability.IEntityRadioactive ent = entity.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null);
			ent.increaseRads(f);
		}
	}

	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {
		RayTraceResult pos = world.rayTraceBlocks(new Vec3d(x, y, z), new Vec3d(a, b, c));
		return pos != null;
	}

	public static boolean checkArmor(EntityPlayer player, Item helm, Item chest, Item leg, Item shoe) {
		if(player.inventory.armorInventory.get(0).getItem() == shoe && player.inventory.armorInventory.get(1).getItem() == leg && player.inventory.armorInventory.get(2).getItem() == chest && player.inventory.armorInventory.get(3).getItem() == helm) {
			return true;
		}

		return false;
	}

	public static boolean checkArmorPiece(EntityPlayer player, Item armor, int slot) {
		if(player.inventory.armorInventory.get(slot) != null && player.inventory.armorInventory.get(slot).getItem() == armor) {
			return true;
		}

		return false;
	}

	public static boolean checkForGasMask(EntityPlayer player) {

		if(checkArmorPiece(player, ModItems.hazmat_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_red, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_helmet_grey, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.hazmat_paa_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.gas_mask_m65, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.t45_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.schrabidium_helmet, 3)) {
			return true;
		}
		if(checkArmorPiece(player, ModItems.euphemium_helmet, 3)) {
			return true;
		}

		if(player.isPotionActive(HbmPotion.mutation))
			return true;

		return false;
	}

	public static String getShortNumber(long l) {
		if(l >= Math.pow(10, 18)) {
			double res = l / Math.pow(10, 18);
			res = Math.round(res * 100.0) / 100.0;
			return res + "E";
		}
		if(l >= Math.pow(10, 15)) {
			double res = l / Math.pow(10, 15);
			res = Math.round(res * 100.0) / 100.0;
			return res + "P";
		}
		if(l >= Math.pow(10, 12)) {
			double res = l / Math.pow(10, 12);
			res = Math.round(res * 100.0) / 100.0;
			return res + "T";
		}
		if(l >= Math.pow(10, 9)) {
			double res = l / Math.pow(10, 9);
			res = Math.round(res * 100.0) / 100.0;
			return res + "G";
		}
		if(l >= Math.pow(10, 6)) {
			double res = l / Math.pow(10, 6);
			res = Math.round(res * 100.0) / 100.0;
			return res + "M";
		}
		if(l >= Math.pow(10, 3)) {
			double res = l / Math.pow(10, 3);
			res = Math.round(res * 100.0) / 100.0;
			return res + "k";
		}

		return Long.toString(l);
	}

	// Drillgon200: Just realized I copied the wrong method. God dang it.
	// It works though. Not sure why, but it works.
	public static long chargeTEFromItems(IItemHandlerModifiable inventory, int index, long power, long maxPower) {
		if(inventory.getStackInSlot(index).getItem() == ModItems.fusion_core_infinite || inventory.getStackInSlot(index).getItem() == ModItems.battery_creative) {
			return maxPower;
		}
		if(!(inventory.getStackInSlot(index).getItem() instanceof ItemBattery) || index > inventory.getSlots()) {
			return power;
		}
		long dR = ((ItemBattery) inventory.getStackInSlot(index).getItem()).getDischargeRate();

		while(dR >= 1000000000000L) {
			if(power + 100000000000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000000000L;
				dR -= 1000000000000L;
				((ItemBattery) inventory.getStackInSlot(index).getItem()).dischargeBattery(inventory.getStackInSlot(index), 1000000000000L);
			} else
				break;
		}
		while(dR >= 1000000000) {
			if(power + 100000000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000000L;
				dR -= 1000000000L;
				((ItemBattery) inventory.getStackInSlot(index).getItem()).dischargeBattery(inventory.getStackInSlot(index), 1000000000);
			} else
				break;
		}
		while(dR >= 1000000) {
			if(power + 100000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000L;
				dR -= 1000000;
				((ItemBattery) inventory.getStackInSlot(index).getItem()).dischargeBattery(inventory.getStackInSlot(index), 1000000);
			} else
				break;
		}
		while(dR >= 1000) {
			if(power + 100000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000L;
				dR -= 1000;
				((ItemBattery) inventory.getStackInSlot(index).getItem()).dischargeBattery(inventory.getStackInSlot(index), 1000);
			} else
				break;
		}
		while(dR >= 1) {
			if(power + 100L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100L;
				dR -= 1;
				((ItemBattery) inventory.getStackInSlot(index).getItem()).dischargeBattery(inventory.getStackInSlot(index), 1);
			} else
				break;
		}

		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() instanceof ItemBattery) {
			ItemBattery.updateDamage(inventory.getStackInSlot(index));
		}
		return power;
	}

	public static long chargeItemsFromTE(IItemHandlerModifiable inventory, int index, long power, long maxPower) {
		if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() instanceof ItemBattery) {

			long dR = ((ItemBattery) inventory.getStackInSlot(index).getItem()).getChargeRate();

			while(dR >= 1000000000000L) {
				if(power - 100000000000000L >= 0 && ItemBattery.getCharge(inventory.getStackInSlot(index)) < ((ItemBattery) inventory.getStackInSlot(index).getItem()).getMaxCharge()) {
					power -= 100000000000000L;
					dR -= 1000000000000L;
					((ItemBattery) inventory.getStackInSlot(index).getItem()).chargeBattery(inventory.getStackInSlot(index), 1000000000000L);
				} else
					break;
			}
			while(dR >= 1000000000) {
				if(power - 100000000000L >= 0 && ItemBattery.getCharge(inventory.getStackInSlot(index)) < ((ItemBattery) inventory.getStackInSlot(index).getItem()).getMaxCharge()) {
					power -= 100000000000L;
					dR -= 1000000000;
					((ItemBattery) inventory.getStackInSlot(index).getItem()).chargeBattery(inventory.getStackInSlot(index), 1000000000);
				} else
					break;
			}
			while(dR >= 1000000) {
				if(power - 100000000 >= 0 && ItemBattery.getCharge(inventory.getStackInSlot(index)) < ((ItemBattery) inventory.getStackInSlot(index).getItem()).getMaxCharge()) {
					power -= 100000000;
					dR -= 1000000;
					((ItemBattery) inventory.getStackInSlot(index).getItem()).chargeBattery(inventory.getStackInSlot(index), 1000000);
				} else
					break;
			}
			while(dR >= 1000) {
				if(power - 100000 >= 0 && ItemBattery.getCharge(inventory.getStackInSlot(index)) < ((ItemBattery) inventory.getStackInSlot(index).getItem()).getMaxCharge()) {
					power -= 100000;
					dR -= 1000;
					((ItemBattery) inventory.getStackInSlot(index).getItem()).chargeBattery(inventory.getStackInSlot(index), 1000);
				} else
					break;
			}
			while(dR >= 1) {
				if(power - 100 >= 0 && ItemBattery.getCharge(inventory.getStackInSlot(index)) < ((ItemBattery) inventory.getStackInSlot(index).getItem()).getMaxCharge()) {
					power -= 100;
					dR -= 1;
					((ItemBattery) inventory.getStackInSlot(index).getItem()).chargeBattery(inventory.getStackInSlot(index), 1);
				} else
					break;
			}

			if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_desh && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_desh_charged));
			if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_schrabidium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_schrabidium_charged));
			if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_euphemium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_euphemium_charged));
			if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_dineutronium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_dineutronium_charged));
		}
		
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.elec_sword && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
		
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.elec_pickaxe && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
		
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.elec_axe && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
		
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() == ModItems.elec_shovel && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
		
		if(inventory.getStackInSlot(index) != null && inventory.getStackInSlot(index).getItem() instanceof ItemBattery) {
			ItemBattery.updateDamage(inventory.getStackInSlot(index));
		}

		return power;
	}

	public static boolean isArrayEmpty(Object[] array) {
		if(array == null)
			return true;
		if(array.length == 0)
			return true;

		boolean flag = true;

		for(int i = 0; i < array.length; i++) {
			if(array[i] != null)
				flag = false;
		}

		return flag;
	}

	// Drillgon200: useless method but whatever
	public static ItemStack carefulCopy(ItemStack stack) {
		if(stack == null)
			return null;
		else
			return stack.copy();
	}
	
	public static EntityPlayer getClosestPlayerForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityPlayer entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityPlayer) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityPlayer)entityplayer1;
					}
			}
		}

		return entity;
	}

	public static EntityHunterChopper getClosestChopperForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityHunterChopper entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityHunterChopper) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityHunterChopper)entityplayer1;
					}
			}
		}

		return entity;
	}

	public static EntityChopperMine getClosestMineForSound(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityChopperMine entity = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
				Entity entityplayer1 = (Entity)world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && entityplayer1 instanceof EntityChopperMine) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entity = (EntityChopperMine)entityplayer1;
					}
			}
		}

		return entity;
	}

	public static RayTraceResult rayTrace(EntityPlayer player, double d, float f) {
		Vec3d vec3 = getPosition(f, player);
		vec3 = vec3.addVector(0D, (double) player.eyeHeight, 0D);
		Vec3d vec31 = player.getLook(f);
		Vec3d vec32 = vec3.addVector(vec31.x * d, vec31.y * d, vec31.z * d);
		return player.world.rayTraceBlocks(vec3, vec32, false, false, true);
	}

	public static Vec3d getPosition(float par1, EntityPlayer player) {
		if(par1 == 1.0F) {
			return new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
		} else {
			double d0 = player.prevPosX + (player.posX - player.prevPosX) * par1;
			double d1 = player.prevPosY + (player.posY - player.prevPosY) * par1 + (player.getEyeHeight() - player.getDefaultEyeHeight());
			double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * par1;
			return new Vec3d(d0, d1, d2);
		}
	}

	// Flut-Füll gesteuerter Energieübertragungsalgorithmus
	// Flood fill controlled energy transmission algorithm

	public static void ffgeua(MutableBlockPos pos, boolean newTact, ISource that, World worldObj) {
		Block block = worldObj.getBlockState(pos).getBlock();
		TileEntity tileentity = worldObj.getTileEntity(pos);

		// Factories
		if(block == ModBlocks.factory_titanium_conductor && worldObj.getBlockState(pos.up()).getBlock() == ModBlocks.factory_titanium_core)
		{
			tileentity = worldObj.getTileEntity(pos.up());
		}
		if(block == ModBlocks.factory_titanium_conductor && worldObj.getBlockState(pos.down()).getBlock() == ModBlocks.factory_titanium_core)
		{
			tileentity = worldObj.getTileEntity(pos.down());
		}
		if(block == ModBlocks.factory_advanced_conductor && worldObj.getBlockState(pos.up()).getBlock() == ModBlocks.factory_advanced_core)
		{
			tileentity = worldObj.getTileEntity(pos.up());
		}
		if(block == ModBlocks.factory_advanced_conductor && worldObj.getBlockState(pos.down()).getBlock() == ModBlocks.factory_advanced_core)
		{
			tileentity = worldObj.getTileEntity(pos.down());
		}
		//Derrick
		if(block == ModBlocks.dummy_port_well && worldObj.getBlockState(pos.add(1, 0, 0)).getBlock() == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(pos.add(1, 0, 0));
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlockState(pos.add(-1, 0, 0)).getBlock() == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(pos.add(-1, 0, 0));
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlockState(pos.add(0, 0, 1)).getBlock() == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(pos.add(0, 0, 1));
		}
		if(block == ModBlocks.dummy_port_well && worldObj.getBlockState(pos.add(0, 0, -1)).getBlock() == ModBlocks.machine_well)
		{
			tileentity = worldObj.getTileEntity(pos.add(0, 0, -1));
		}
		//Mining Drill
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlockState(pos.add(1, 0, 0)).getBlock() == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(pos.add(1, 0, 0));
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlockState(pos.add(-1, 0, 0)).getBlock() == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(pos.add(-1, 0, 0));
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlockState(pos.add(0, 0, 1)).getBlock() == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(pos.add(0, 0, 1));
		}
		if(block == ModBlocks.dummy_port_drill && worldObj.getBlockState(pos.add(0, 0, -1)).getBlock() == ModBlocks.machine_drill)
		{
			tileentity = worldObj.getTileEntity(pos.add(0, 0, -1));
		}
		// Assembler
		if(block == ModBlocks.dummy_port_assembler) {
			tileentity = worldObj.getTileEntity(((TileEntityDummy) worldObj.getTileEntity(pos)).target);
		}
		// Chemplant
		if(block == ModBlocks.dummy_port_chemplant) {
			tileentity = worldObj.getTileEntity(((TileEntityDummy) worldObj.getTileEntity(pos)).target);
		}
		// Refinery
		if(block == ModBlocks.dummy_port_refinery)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(pos)).target);
		}
		//Pumpjack
		if(block == ModBlocks.dummy_port_pumpjack)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(pos)).target);
		}
		//AMS Limiter
		if(block == ModBlocks.dummy_port_ams_limiter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(pos)).target);
		}
		//AMS Emitter
		if(block == ModBlocks.dummy_port_ams_emitter)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(pos)).target);
		}
		//Launchers
		if(block == ModBlocks.dummy_port_compact_launcher || block == ModBlocks.dummy_port_launch_table)
		{
			tileentity = worldObj.getTileEntity(((TileEntityDummy)worldObj.getTileEntity(pos)).target);
		}

		if(tileentity instanceof IConductor) {
			if(tileentity instanceof TileEntityCable) {
				if(Library.checkUnionList(((TileEntityCable) tileentity).uoteab, that)) {
					for(int i = 0; i < ((TileEntityCable) tileentity).uoteab.size(); i++) {
						if(((TileEntityCable) tileentity).uoteab.get(i).source == that) {
							if(((TileEntityCable) tileentity).uoteab.get(i).ticked != newTact) {
								((TileEntityCable) tileentity).uoteab.get(i).ticked = newTact;
								that.ffgeua(pos.up(), that.getTact());
								that.ffgeua(pos.down(), that.getTact());
								that.ffgeua(pos.west(), that.getTact());
								that.ffgeua(pos.east(), that.getTact());
								that.ffgeua(pos.north(), that.getTact());
								that.ffgeua(pos.south(), that.getTact());
							}
						}
					}
				} else {
					((TileEntityCable) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityWireCoated) {
				if(Library.checkUnionList(((TileEntityWireCoated) tileentity).uoteab, that)) {
					for(int i = 0; i < ((TileEntityWireCoated) tileentity).uoteab.size(); i++) {
						if(((TileEntityWireCoated) tileentity).uoteab.get(i).source == that) {
							if(((TileEntityWireCoated) tileentity).uoteab.get(i).ticked != newTact) {
								((TileEntityWireCoated) tileentity).uoteab.get(i).ticked = newTact;
								that.ffgeua(pos.up(), that.getTact());
								that.ffgeua(pos.down(), that.getTact());
								that.ffgeua(pos.west(), that.getTact());
								that.ffgeua(pos.east(), that.getTact());
								that.ffgeua(pos.north(), that.getTact());
								that.ffgeua(pos.south(), that.getTact());
							}
						}
					}
				} else {
					((TileEntityWireCoated) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
			if(tileentity instanceof TileEntityCableSwitch) {
				if(tileentity.getBlockMetadata() == 1) {
					if(Library.checkUnionList(((TileEntityCableSwitch) tileentity).uoteab, that)) {
						for(int i = 0; i < ((TileEntityCableSwitch) tileentity).uoteab.size(); i++) {
							if(((TileEntityCableSwitch) tileentity).uoteab.get(i).source == that) {
								if(((TileEntityCableSwitch) tileentity).uoteab.get(i).ticked != newTact) {
									((TileEntityCableSwitch) tileentity).uoteab.get(i).ticked = newTact;
									that.ffgeua(pos.up(), that.getTact());
									that.ffgeua(pos.down(), that.getTact());
									that.ffgeua(pos.west(), that.getTact());
									that.ffgeua(pos.east(), that.getTact());
									that.ffgeua(pos.north(), that.getTact());
									that.ffgeua(pos.south(), that.getTact());
								}
							}
						}
					} else {
						((TileEntityCableSwitch) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
					}
				} else {
					((TileEntityCableSwitch) tileentity).uoteab.clear();
				}
			}
			if(tileentity instanceof TileEntityPylonRedWire) {
				if(Library.checkUnionList(((TileEntityPylonRedWire) tileentity).uoteab, that)) {
					for(int i = 0; i < ((TileEntityPylonRedWire) tileentity).uoteab.size(); i++) {
						if(((TileEntityPylonRedWire) tileentity).uoteab.get(i).source == that) {
							if(((TileEntityPylonRedWire) tileentity).uoteab.get(i).ticked != newTact) {
								((TileEntityPylonRedWire) tileentity).uoteab.get(i).ticked = newTact;
								for(int j = 0; j < ((TileEntityPylonRedWire) tileentity).connected.size(); j++) {
									TileEntityPylonRedWire pylon = ((TileEntityPylonRedWire) tileentity).connected.get(j);
									if(pylon != null) {
										that.ffgeua(pylon.getPos().east(), that.getTact());
										that.ffgeua(pylon.getPos().west(), that.getTact());
										that.ffgeua(pylon.getPos().up(), that.getTact());
										that.ffgeua(pylon.getPos().down(), that.getTact());
										that.ffgeua(pylon.getPos().south(), that.getTact());
										that.ffgeua(pylon.getPos().north(), that.getTact());

										that.ffgeua(pylon.getPos(), that.getTact());
									}
								}
							}
						}
					}
				} else {
					((TileEntityPylonRedWire) tileentity).uoteab.add(new UnionOfTileEntitiesAndBooleans(that, newTact));
				}
			}
		}

		// TE will not be added as consumer if:
		// -TE is the source (will not send to itself)
		// -TE is already full
		// -TE is a battery set to output only
		// -TE as well as source are transformers of the same frequency
		if(tileentity instanceof IConsumer && newTact && !(tileentity instanceof TileEntityMachineBattery && ((TileEntityMachineBattery) tileentity).conducts) && tileentity != that && ((IConsumer) tileentity).getPower() < ((IConsumer) tileentity).getMaxPower() && !(tileentity instanceof TileEntityMachineTransformer && that instanceof TileEntityMachineTransformer && ((TileEntityMachineTransformer) tileentity).delay == ((TileEntityMachineTransformer) that).delay)) {
			that.getList().add((IConsumer) tileentity);
		}

		if(!newTact) {
			int size = that.getList().size();
			if(size > 0) {
				long part = that.getSPower() / size;
				for(IConsumer consume : that.getList()) {
					if(consume.getPower() < consume.getMaxPower()) {
						if(consume.getMaxPower() - consume.getPower() >= part) {
							that.setSPower(that.getSPower() - part);
							consume.setPower(consume.getPower() + part);
						} else {
							that.setSPower(that.getSPower() - (consume.getMaxPower() - consume.getPower()));
							consume.setPower(consume.getMaxPower());
						}
					}
				}
			}
			that.clearList();
		}

	}

	/**
	 * Itemstack equality method except it accounts for possible null stacks and
	 * doesn't check if empty
	 */
	public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
		if(stackA == null & stackB == null)
			return true;
		else if((stackA == null && stackB != null) || (stackA != null && stackB == null))
			return false;
		else
			return stackA.getMetadata() == stackB.getMetadata() && stackA.getItem() == stackB.getItem();
	}

	public static boolean checkCableConnectables(World world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		Block b = world.getBlockState(pos).getBlock();
		if((tileentity != null && (tileentity instanceof IConductor || tileentity instanceof IConsumer || tileentity instanceof ISource)) ||
				 b == ModBlocks.fusion_center ||
				 b == ModBlocks.factory_titanium_conductor ||
				 b == ModBlocks.factory_advanced_conductor ||
				 b == ModBlocks.watz_conductor ||
				 b == ModBlocks.fwatz_hatch ||
				 b == ModBlocks.dummy_port_cyclotron ||
				 b == ModBlocks.dummy_port_well ||
				 b == ModBlocks.dummy_port_flare ||
				 b == ModBlocks.dummy_port_drill ||
		b == ModBlocks.dummy_port_assembler || b == ModBlocks.dummy_port_chemplant ||
		 b == ModBlocks.dummy_port_refinery ||
		 b == ModBlocks.dummy_port_pumpjack ||
		 b == ModBlocks.dummy_port_turbofan ||
		 b == ModBlocks.dummy_port_ams_limiter ||
		 b == ModBlocks.dummy_port_ams_emitter ||
		 b == ModBlocks.dummy_port_ams_base ||
		 b == ModBlocks.dummy_port_radgen ||
		 b == ModBlocks.dummy_port_compact_launcher ||
		 b == ModBlocks.dummy_port_launch_table
		) {
			return true;
		}
		return false;
	}

	public static boolean checkUnionList(List<UnionOfTileEntitiesAndBooleans> list, ISource that) {

		for(UnionOfTileEntitiesAndBooleans union : list) {
			if(union.source == that) {
				return true;
			}
		}

		return false;
	}

	public static boolean hasInventoryItem(InventoryPlayer inventory, Item ammo) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack.getItem() == ammo) {
				return true;
			}
		}
		return false;
	}

	public static void consumeInventoryItem(InventoryPlayer inventory, Item ammo) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if(stack.getItem() == ammo && !stack.isEmpty()) {
				stack.shrink(1);
				inventory.setInventorySlotContents(i, stack.copy());
				return;
			}
		}
	}

	//////  //////  //////  //////  //////  ////        //////  //////  //////
	//      //  //  //        //    //      //  //      //      //      //    
	////    //////  /////     //    ////    ////        ////    //  //  //  //
	//      //  //     //     //    //      //  //      //      //  //  //  //
	//////  //  //  /////     //    //////  //  //      //////  //////  //////

	public static EntityLivingBase getClosestEntityForChopper(World world, double x, double y, double z, double radius) {
		double d4 = -1.0D;
		EntityLivingBase entityplayer = null;

		for (int i = 0; i < world.loadedEntityList.size(); ++i) {
			if (world.loadedEntityList.get(i) instanceof EntityLivingBase && !(world.loadedEntityList.get(i) instanceof EntityHunterChopper)) {
				EntityLivingBase entityplayer1 = (EntityLivingBase) world.loadedEntityList.get(i);

				if (entityplayer1.isEntityAlive() && !(entityplayer1 instanceof EntityPlayer && ((EntityPlayer)entityplayer1).capabilities.disableDamage)) {
					double d5 = entityplayer1.getDistanceSq(x, y, z);
					double d6 = radius;

					if (entityplayer1.isSneaking()) {
						d6 = radius * 0.800000011920929D;
					}

					if ((radius < 0.0D || d5 < d6 * d6) && (d4 == -1.0D || d5 < d4)) {
						d4 = d5;
						entityplayer = entityplayer1;
					}
				}
			}
		}

		return entityplayer;
	}
	
	//Drillgon200: Loot tables? I don't have time for that!
	public static void generateChestContents(Random p_76293_0_, WeightedRandomChestContentFrom1710[] p_76293_1_, ICapabilityProvider p_76293_2_, int p_76293_3_)
    {
		if(p_76293_2_.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
			IItemHandler test = p_76293_2_.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if(test instanceof IItemHandlerModifiable){
				IItemHandlerModifiable inventory = (IItemHandlerModifiable)test;
				
				for (int j = 0; j < p_76293_3_; ++j)
		        {
					WeightedRandomChestContentFrom1710 weightedrandomchestcontent = (WeightedRandomChestContentFrom1710)WeightedRandom.getRandomItem(p_76293_0_, Arrays.asList(p_76293_1_));
		            ItemStack[] stacks = weightedrandomchestcontent.generateChestContent(p_76293_0_, inventory);

		            for (ItemStack item : stacks)
		            {
		            	inventory.setStackInSlot(p_76293_0_.nextInt(inventory.getSlots()), item);
		            }
		        }
			}
		}
        
    }
	
	public static Block getRandomConcrete() {
		int i = rand.nextInt(100);

		if(i < 5)
			return ModBlocks.brick_concrete_broken;
		if(i < 20)
			return ModBlocks.brick_concrete_cracked;
		if(i < 50)
			return ModBlocks.brick_concrete_mossy;
		
		return ModBlocks.brick_concrete;
	}
	
	public static void placeDoorWithoutCheck(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge)
    {
        BlockPos blockpos2 = pos.up();
        boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
        IBlockState iblockstate = door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, Boolean.valueOf(flag2)).withProperty(BlockDoor.OPEN, Boolean.valueOf(flag2));
        worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door, false);
        worldIn.notifyNeighborsOfStateChange(blockpos2, door, false);
    }
	
	public static float remap(float num, float min1, float max1, float min2, float max2){
		return ((num - min1) / (max1 - min1)) * (max2 - min2) + min2;
	}
	
	public static boolean areItemStacksEqualIgnoreCount(ItemStack a, ItemStack b){
		if (a.isEmpty() && b.isEmpty())
        {
            return true;
        }
        else
        {
            if(!a.isEmpty() && !b.isEmpty()){

                if (a.getItem() != b.getItem())
                {
                    return false;
                }
                else if (a.getMetadata() != b.getMetadata())
                {
                    return false;
                }
                else if (a.getTagCompound() == null && b.getTagCompound() != null)
                {
                    return false;
                }
                else
                {
                    return (a.getTagCompound() == null || a.getTagCompound().equals(b.getTagCompound())) && a.areCapsCompatible(b);
                }
            }
        }
		return false;
	}
	
	/**
	 * Same as ItemStack.areItemStacksEqual, except the second one's tag only has to contain all the first one's tag, rather than being exactly equal.
	 */
	public static boolean areItemStacksCompatible(ItemStack base, ItemStack toTest, boolean shouldCompareSize){
		if (base.isEmpty() && toTest.isEmpty())
        {
            return true;
        }
        else
        {
            if(!base.isEmpty() && !toTest.isEmpty()){

            	if(shouldCompareSize && base.getCount() != toTest.getCount()){
            		return false;
            	} 
            	else if (base.getItem() != toTest.getItem())
                {
                    return false;
                }
                else if (base.getMetadata() != toTest.getMetadata() && !(base.getMetadata() == OreDictionary.WILDCARD_VALUE))
                {
                    return false;
                }
                else if (base.getTagCompound() == null && toTest.getTagCompound() != null)
                {
                    return false;
                }
                else
                {
                    return (base.getTagCompound() == null || tagContainsOther(base.getTagCompound(), toTest.getTagCompound())) && base.areCapsCompatible(toTest);
                }
            }
        }
		return false;
	}
	
	public static boolean areItemStacksCompatible(ItemStack base, ItemStack toTest){
		return areItemStacksCompatible(base, toTest, true);
	}
	
	/**
	 * Returns true if the second compound contains all the tags and values of the first one, but it can have more. This helps with intermod compatibility
	 */
	public static boolean tagContainsOther(NBTTagCompound tester, NBTTagCompound container){
		if(tester == null && container == null){
			return true;
		} if(tester == null ^ container == null){
			return false;
		} else {
			for(String s : tester.getKeySet()){
				if(!container.hasKey(s)){
					return false;
				} else {
					NBTBase nbt1 = tester.getTag(s);
					NBTBase nbt2 = container.getTag(s);
					if(nbt1 instanceof NBTTagCompound && nbt2 instanceof NBTTagCompound){
						if(!tagContainsOther((NBTTagCompound)nbt1, (NBTTagCompound) nbt2))
							return false;
					} else {
						if(!nbt1.equals(nbt2))
							return false;
					}
				}
			}
		}
		return true;
	}
	
	public static List<int[]> getBlockPosInPath(BlockPos pos, int length, Vec3 vec0) {
		List<int[]> list = new ArrayList<int[]>();
		
		for(int i = 0; i <= length; i++) {
			list.add(new int[] { (int)(pos.getX() + (vec0.xCoord * i)), pos.getY(), (int)(pos.getZ() + (vec0.zCoord * i)), i });
		}
		
		return list;
	}

	public static List<ItemStack> copyItemStackList(List<ItemStack> inputs) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		inputs.forEach(stack -> {list.add(stack.copy());});
		return list;
	}
	
	public static IEntityRadioactive getEntRadCap(Entity e){
		if(e.hasCapability(EntityRadiationProvider.ENT_RAD_CAP, null))
			return e.getCapability(EntityRadiationProvider.ENT_RAD_CAP, null);
		return EntityRadiationProvider.DUMMY;
	}
	
}
