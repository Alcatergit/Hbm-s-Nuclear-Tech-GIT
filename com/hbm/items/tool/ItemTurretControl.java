package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.bomb.TurretBase;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityTurretBase;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemTurretControl extends Item {

	public ItemTurretControl(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.weaponTab);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if (player.getHeldItem(EnumHand.MAIN_HAND).equals(stack) || player.getHeldItem(EnumHand.OFF_HAND).equals(stack)) {
				if (stack.hasTagCompound()) {
					int x = stack.getTagCompound().getInteger("xCoord");
					int y = stack.getTagCompound().getInteger("yCoord");
					int z = stack.getTagCompound().getInteger("zCoord");
					BlockPos pos = new BlockPos(x, y, z);
					
					TileEntity te = worldIn.getTileEntity(pos);

					if (te != null && te instanceof TileEntityTurretBase) {
						TileEntityTurretBase turret = (TileEntityTurretBase) te;

						if (!turret.isAI) {
							turret.rotationYaw = player.rotationYaw;
							turret.rotationPitch = player.rotationPitch;

							if (turret.rotationPitch < -60)
								turret.rotationPitch = -60;
							if (turret.rotationPitch > 30)
								turret.rotationPitch = 30;

							if (turret instanceof TileEntityTurretCheapo) {
								if (turret.rotationPitch < -30)
									turret.rotationPitch = -30;
								if (turret.rotationPitch > 15)
									turret.rotationPitch = 15;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (stack.getTagCompound() != null) {
			list.add("Linked to:");
			list.add("X: " + String.valueOf(stack.getTagCompound().getInteger("xCoord")));
			list.add("Y: " + String.valueOf(stack.getTagCompound().getInteger("yCoord")));
			list.add("Z: " + String.valueOf(stack.getTagCompound().getInteger("zCoord")));
		} else {
			list.add("Please select a turret.");
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if ((worldIn.getBlockState(pos).getBlock() instanceof TurretBase)) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getTagCompound() != null) {
				stack.getTagCompound().setInteger("xCoord", pos.getX());
				stack.getTagCompound().setInteger("yCoord", pos.getY());
				stack.getTagCompound().setInteger("zCoord", pos.getZ());
			} else {
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("xCoord", pos.getX());
				stack.getTagCompound().setInteger("yCoord", pos.getY());
				stack.getTagCompound().setInteger("zCoord", pos.getZ());
			}
			if (worldIn.isRemote) {
				player.sendMessage(new TextComponentTranslation("Turret Linked!"));
			}

			worldIn.playSound(player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		int j = this.getMaxItemUseDuration(stack) - timeLeft;
		if (entityLiving instanceof EntityPlayer) {
			ArrowLooseEvent event = new ArrowLooseEvent((EntityPlayer) entityLiving, stack, worldIn, j, false);
			MinecraftForge.EVENT_BUS.post(event);

			j = event.getCharge();
		}
		if (stack.hasTagCompound()) {
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");
			BlockPos pos = new BlockPos(x, y, z);

			if (worldIn.getBlockState(pos).getBlock() instanceof TurretBase) {

				TileEntity te = worldIn.getTileEntity(pos);

				if (te != null && te instanceof TileEntityTurretBase) {
					TileEntityTurretBase turret = (TileEntityTurretBase) te;

					if (!turret.isAI) {
						((TurretBase) worldIn.getBlockState(pos).getBlock()).executeReleaseAction(worldIn, j, entityLiving.rotationYaw, entityLiving.rotationPitch, pos);
					}
				}
			}
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ArrowNockEvent event = new ArrowNockEvent(playerIn, playerIn.getHeldItem(handIn), handIn, worldIn, false);
		MinecraftForge.EVENT_BUS.post(event);
		{
			playerIn.setActiveHand(handIn);
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		World world = player.world;
		
		if (stack.hasTagCompound()) {
			int x = stack.getTagCompound().getInteger("xCoord");
			int y = stack.getTagCompound().getInteger("yCoord");
			int z = stack.getTagCompound().getInteger("zCoord");
			BlockPos pos = new BlockPos(x, y, z);
			if (world.getBlockState(pos).getBlock() instanceof TurretBase) {

				TileEntity te = world.getTileEntity(pos);
				if (te != null && te instanceof TileEntityTurretBase) {
					TileEntityTurretBase turret = (TileEntityTurretBase) te;
					if (!turret.isAI && turret.ammo > 0) {
						if (((TurretBase) world.getBlockState(pos).getBlock()).executeHoldAction(world, stack.getMaxItemUseDuration() - count, player.rotationYaw, player.rotationPitch, pos))
							turret.ammo--;
					}
				}
			}
		}
	}
}
