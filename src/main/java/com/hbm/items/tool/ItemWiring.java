package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityPylonRedWire;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemWiring extends Item {

	public ItemWiring(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			TileEntity te = world.getTileEntity(pos);

			if (te != null && te instanceof TileEntityPylonRedWire) {
				ItemStack stack = player.getHeldItem(hand);
				if (stack.getTagCompound() == null) {
					stack.setTagCompound(new NBTTagCompound());

					stack.getTagCompound().setInteger("x", pos.getX());
					stack.getTagCompound().setInteger("y", pos.getY());
					stack.getTagCompound().setInteger("z", pos.getZ());

					if (world.isRemote)
						player.sendMessage(new TextComponentTranslation("Wire start"));
				} else {
					int x1 = stack.getTagCompound().getInteger("x");
					int y1 = stack.getTagCompound().getInteger("y");
					int z1 = stack.getTagCompound().getInteger("z");
					
					BlockPos newPos = new BlockPos(x1, y1, z1);
					
					if (world.getTileEntity(newPos) instanceof TileEntityPylonRedWire && this.isLengthValid(pos.getX(), pos.getY(), pos.getZ(), x1, y1, z1, 25)) {

						TileEntityPylonRedWire first = (TileEntityPylonRedWire) world.getTileEntity(new BlockPos(x1, y1, z1));
						TileEntityPylonRedWire second = ((TileEntityPylonRedWire) te);

						first.connected.add(second);
						second.connected.add(first);
						first.markDirty();
						second.markDirty();

						if (world.isRemote)
							player.sendMessage(new TextComponentTranslation("Wire end"));

						stack.setTagCompound(null);
					} else {
						if (world.isRemote)
							player.sendMessage(new TextComponentTranslation("Wire error"));
						stack.setTagCompound(null);
					}
				}

				player.swingArm(hand);
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getTagCompound() != null) {
			tooltip.add("Wire start x: " + stack.getTagCompound().getInteger("x"));
			tooltip.add("Wire start y: " + stack.getTagCompound().getInteger("y"));
			tooltip.add("Wire start z: " + stack.getTagCompound().getInteger("z"));
		} else {
			tooltip.add("Right-click poles to connect");
		}
	}
	
	public boolean isLengthValid(int x1, int y1, int z1, int x2, int y2, int z2, int length) {
		double l = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
		
		return l <= length;
	}
}
