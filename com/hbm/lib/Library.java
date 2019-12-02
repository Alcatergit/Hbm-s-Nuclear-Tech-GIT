package com.hbm.lib;

import com.hbm.handler.HazmatRegistry;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.potion.HbmPotion;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.saveddata.RadEntitySavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class Library {
	
	public static String HbMinecraft = "192af5d7-ed0f-48d8-bd89-9d41af8524f8";
	public static String LPkukin = "937c9804-e11f-4ad2-a5b1-42e62ac73077";
	public static String Dafnik = "3af1c262-61c0-4b12-a4cb-424cc3a9c8c0";
	public static String a20 = "4729b498-a81c-42fd-8acd-20d6d9f759e0";
	public static String LordVertice = "a41df45e-13d8-4677-9398-090d3882b74f";
	public static String CodeRed_ = "912ec334-e920-4dd7-8338-4d9b2d42e0a1";
	public static String dxmaster769 = "62c168b2-d11d-4dbf-9168-c6cea3dcb20e";
	public static String Dr_Nostalgia = "e82684a7-30f1-44d2-ab37-41b342be1bbd";
	public static String Samino2 = "87c3960a-4332-46a0-a929-ef2a488d1cda";
	public static String Hoboy03new = "d7f29d9c-5103-4f6f-88e1-2632ff95973f";
	public static String Dragon59MC = "dc23a304-0f84-4e2d-b47d-84c8d3bfbcdb";
	public static String Steelcourage = "ac49720b-4a9a-4459-a26f-bee92160287a";
	public static String Drillgon = "41ebd03f-7a12-42f3-b037-0caa4d6f235b";

	public static boolean checkForHazmat(EntityPlayer player) {
		// TODO Make hazmat armors
		return false;
	}

	public static void applyRadData(Entity e, float f) {
		if (!(e instanceof EntityLivingBase))
			return;

		EntityLivingBase entity = (EntityLivingBase) e;

		if (entity.isPotionActive(HbmPotion.mutation))
			return;

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			float koeff = 5.0F;
			f *= (float) Math.pow(koeff, -HazmatRegistry.instance.getResistance(player));
		}

		RadEntitySavedData data = RadEntitySavedData.getData(entity.world);
		data.increaseRad(entity, f);
	}

	public static void applyRadDirect(Entity e, float f) {

		if (!(e instanceof EntityLivingBase))
			return;

		if (((EntityLivingBase) e).isPotionActive(HbmPotion.mutation))
			return;

		RadEntitySavedData data = RadEntitySavedData.getData(e.world);
		data.increaseRad(e, f);
	}

	public static boolean isObstructed(World world, double x, double y, double z, double a, double b, double c) {

		Vec3 vector = Vec3.createVectorHelper(a - x, b - y, c - z);
		double length = vector.lengthVector();
		Vec3 nVec = vector.normalize();
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (float i = 0; i < length; i += 0.25F)
			pos.setPos((int) Math.round(x + (nVec.xCoord * i)), (int) Math.round(y + (nVec.yCoord * i)),
					(int) Math.round(z + (nVec.zCoord * i)));
		if (world.getBlockState(pos).getBlock() != Blocks.AIR && world.getBlockState(pos).isNormalCube())
			return true;

		return false;
	}

	public static boolean checkArmor(EntityPlayer player, Item helm, Item chest, Item leg, Item shoe) {
		if (player.inventory.armorInventory.get(0).getItem() == shoe
				&& player.inventory.armorInventory.get(1).getItem() == leg
				&& player.inventory.armorInventory.get(2).getItem() == chest
				&& player.inventory.armorInventory.get(3).getItem() == helm) {
			return true;
		}

		return false;
	}

	public static String getShortNumber(long l) {
		if (l >= Math.pow(10, 18)) {
			double res = l / Math.pow(10, 18);
			res = Math.round(res * 100.0) / 100.0;
			return res + "E";
		}
		if (l >= Math.pow(10, 15)) {
			double res = l / Math.pow(10, 15);
			res = Math.round(res * 100.0) / 100.0;
			return res + "P";
		}
		if (l >= Math.pow(10, 12)) {
			double res = l / Math.pow(10, 12);
			res = Math.round(res * 100.0) / 100.0;
			return res + "T";
		}
		if (l >= Math.pow(10, 9)) {
			double res = l / Math.pow(10, 9);
			res = Math.round(res * 100.0) / 100.0;
			return res + "G";
		}
		if (l >= Math.pow(10, 6)) {
			double res = l / Math.pow(10, 6);
			res = Math.round(res * 100.0) / 100.0;
			return res + "M";
		}
		if (l >= Math.pow(10, 3)) {
			double res = l / Math.pow(10, 3);
			res = Math.round(res * 100.0) / 100.0;
			return res + "k";
		}

		return Long.toString(l);
	}

	public static long chargeTEFromItems(IItemHandlerModifiable inventory, int index, long power, long maxPower) {
		if (!(inventory.getStackInSlot(index).getItem() instanceof ItemBattery) || index > inventory.getSlots()) {
			return 0;
		}
		long dR = ((ItemBattery) inventory.getStackInSlot(index).getItem()).getDischargeRate();

		while (dR >= 1000000000000L) {
			if (power + 100000000000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000000000L;
				dR -= 1000000000000L;
				((ItemBattery) inventory.getStackInSlot(index).getItem())
						.dischargeBattery(inventory.getStackInSlot(index), 1000000000000L);
			} else
				break;
		}
		while (dR >= 1000000000) {
			if (power + 100000000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000000L;
				dR -= 1000000000L;
				((ItemBattery) inventory.getStackInSlot(index).getItem())
						.dischargeBattery(inventory.getStackInSlot(index), 1000000000);
			} else
				break;
		}
		while (dR >= 1000000) {
			if (power + 100000000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000000L;
				dR -= 1000000;
				((ItemBattery) inventory.getStackInSlot(index).getItem())
						.dischargeBattery(inventory.getStackInSlot(index), 1000000);
			} else
				break;
		}
		while (dR >= 1000) {
			if (power + 100000L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100000L;
				dR -= 1000;
				((ItemBattery) inventory.getStackInSlot(index).getItem())
						.dischargeBattery(inventory.getStackInSlot(index), 1000);
			} else
				break;
		}
		while (dR >= 1) {
			if (power + 100L <= maxPower && ItemBattery.getCharge(inventory.getStackInSlot(index)) > 0) {
				power += 100L;
				dR -= 1;
				((ItemBattery) inventory.getStackInSlot(index).getItem())
						.dischargeBattery(inventory.getStackInSlot(index), 1);
			} else
				break;
		}
		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_desh && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_desh_charged));
		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_schrabidium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_schrabidium_charged));
		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_euphemium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_euphemium_charged));
		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.dynosphere_dineutronium && ItemBattery.getCharge(inventory.getStackInSlot(index)) >= ItemBattery.getMaxChargeStatic(inventory.getStackInSlot(index)))
				inventory.setStackInSlot(index, new ItemStack(ModItems.dynosphere_dineutronium_charged));
		//TODO these tools
/*
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.elec_sword && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.elec_pickaxe && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.elec_axe && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
	
		for(int i = 0; i < 50; i++)
			if(power - 10 >= 0 && inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() == ModItems.elec_shovel && inventory.getStackInSlot(index).getItemDamage() > 0)
			{
				power -= 10;
				inventory.getStackInSlot(index).setItemDamage(inventory.getStackInSlot(index).getItemDamage() - 1);
			} else break;
		*/
		if(inventory.getStackInSlot(index).getItem() != Items.AIR && inventory.getStackInSlot(index).getItem() instanceof ItemBattery) {
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
	
	//Drillgon200: useless method but whatever
	public static ItemStack carefulCopy(ItemStack stack) {
		if(stack == null)
			return null;
		else
			return stack.copy();
	}

}
