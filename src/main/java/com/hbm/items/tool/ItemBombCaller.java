package com.hbm.items.tool;

import java.util.List;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemBombCaller extends Item {

	public ItemBombCaller(String s) {
		this.setRegistryName(s);
		this.setUnlocalizedName(s);
		this.setCreativeTab(MainRegistry.consumableTab);
		this.setHasSubtypes(true);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add("Aim & click to call an airstrike!");

		switch (getTypeFromStack(stack)) {
		case CARPET:
			list.add("Type: Carpet bombing");
			break;
		case NAPALM:
			list.add("Type: Napalm");
			break;
		case POISON:
			list.add("Type: Poison gas");
			break;
		case ORANGE:
			list.add("Type: Agent orange");
			break;
		case ATOMIC:
			list.add("Type: Atomic bomb");
			break;
		case STINGER:
			list.add("Type: VT stinger rockets");
			break;
		case PIP:
			list.add("Type: PIP OH GOD");
			break;
		case CLOUD:
			list.add("Type: Cloud the cloud oh god the cloud");
			break;
		default:
			break;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer playerIn, EnumHand handIn) {
		RayTraceResult trace = Library.rayTrace(playerIn, 500, 1);
		ItemStack stack = playerIn.getHeldItem(handIn);
		boolean b = false;
		if (trace.typeOfHit != Type.MISS && !world.isRemote) {

			int x = trace.getBlockPos().getX();
			int y = trace.getBlockPos().getY();
			int z = trace.getBlockPos().getZ();

			switch (getTypeFromStack(stack)) {
			case CARPET:
				if (world.spawnEntity(EntityBomber.statFacCarpet(world, x, y, z)))
					b = true;
				break;
			case NAPALM:
				if (world.spawnEntity(EntityBomber.statFacNapalm(world, x, y, z)))
					b = true;
				break;
			case POISON:
				if (world.spawnEntity(EntityBomber.statFacChlorine(world, x, y, z)))
					b = true;
				break;
			case ORANGE:
				if (world.spawnEntity(EntityBomber.statFacOrange(world, x, y, z)))
					b = true;
				break;
			case ATOMIC:
				if (world.spawnEntity(EntityBomber.statFacABomb(world, x, y, z)))
					b = true;
				break;
			case STINGER:
				if (world.spawnEntity(EntityBomber.statFacStinger(world, x, y, z)))
					b = true;
				break;
			case PIP:
				if (world.spawnEntity(EntityBomber.statFacBoxcar(world, x, y, z)))
					b = true;
				break;
			case CLOUD:
				if (world.spawnEntity(EntityBomber.statFacPC(world, x, y, z)))
					b = true;
				break;
			default:
				break;
			}
			if (b) {
				playerIn.sendMessage(new TextComponentTranslation("Called in airstrike!"));
				if (!playerIn.capabilities.isCreativeMode)
					stack.shrink(1);
			}
			world.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 1.0F, 1.0F, true);

		}
		return new ActionResult<ItemStack>(b ? EnumActionResult.SUCCESS : EnumActionResult.FAIL, stack.copy());
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH)
			for (int i = 0; i < EnumCallerType.values().length - 4; i++) {
				ItemStack stack = new ItemStack(this, 1, 0);
				setCallerType(stack, EnumCallerType.values()[i]);
				items.add(stack);
			}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return getTypeFromStack(stack).ordinal() >= 4;
	}

	public static enum EnumCallerType {
		CARPET, NAPALM, POISON, ORANGE, ATOMIC, STINGER, PIP, CLOUD, NONE
	}

	public static EnumCallerType getTypeFromStack(ItemStack stack) {
		if (stack == null || stack.getItem() != ModItems.bomb_caller) {
			return EnumCallerType.NONE;
		}
		if (!stack.hasTagCompound()) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("callerType", EnumCallerType.CARPET.ordinal());
			stack.setTagCompound(tag);
		}
		int i = stack.getTagCompound().getInteger("callerType");
		if (i < 0 || i > EnumCallerType.values().length - 2) {
			return EnumCallerType.NONE;
		}
		return EnumCallerType.values()[i];
	}

	public static void setCallerType(ItemStack stack, EnumCallerType type) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("callerType", type.ordinal());
	}
	
	public static ItemStack getStack(EnumCallerType type){
		ItemStack stack = new ItemStack(ModItems.bomb_caller, 1, 0);
		setCallerType(stack, type);
		return stack;
	}
}
