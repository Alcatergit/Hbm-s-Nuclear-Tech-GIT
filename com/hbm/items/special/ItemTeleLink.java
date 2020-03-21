package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

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

public class ItemTeleLink extends Item {

	public ItemTeleLink(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			TileEntity te = world.getTileEntity(pos);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if (te != null && te instanceof TileEntityMachineTeleporter && world.getBlockState(pos).getBlock() == ModBlocks.machine_teleporter) {

				if (stack.getTagCompound() == null) {
					stack.setTagCompound(new NBTTagCompound());

					stack.getTagCompound().setInteger("x", x);
					stack.getTagCompound().setInteger("y", y);
					stack.getTagCompound().setInteger("z", z);

					if (world.isRemote)
						player.sendMessage(new TextComponentTranslation(
								"[TeleLink] Set teleporter exit to " + x + ", " + y + ", " + z + "."));
				} else {
					int x1 = stack.getTagCompound().getInteger("x");
					int y1 = stack.getTagCompound().getInteger("y");
					int z1 = stack.getTagCompound().getInteger("z");
					BlockPos pos1 = new BlockPos(x1, y1, z1);

					if (world.getBlockState(pos1).getBlock() == ModBlocks.machine_teleporter
							&& world.getTileEntity(pos1) != null
							&& world.getTileEntity(pos1) instanceof TileEntityMachineTeleporter) {

						((TileEntityMachineTeleporter) te).mode = true;
						((TileEntityMachineTeleporter) te).target = pos1;
						((TileEntityMachineTeleporter) te).linked = true;
						((TileEntityMachineTeleporter) world.getTileEntity(pos1)).linked = true;
						te.markDirty();
						world.getTileEntity(pos1).markDirty();

						if (world.isRemote)
							player.sendMessage(new TextComponentTranslation("[TeleLink] Teleporters have been successfully linked."));

						stack.setTagCompound(null);
					} else {
						if (world.isRemote)
							player.sendMessage(new TextComponentTranslation("[TeleLink] Warning: Exit teleporter has been destroyed while linking. Values have been reset."));
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
			tooltip.add("Teleporter Exit x: " + stack.getTagCompound().getInteger("x"));
			tooltip.add("Teleporter Exit y: " + stack.getTagCompound().getInteger("y"));
			tooltip.add("Teleporter Exit z: " + stack.getTagCompound().getInteger("z"));
		} else {
			tooltip.add("Select teleporter exit first!");
			tooltip.add("Right-click teleporter while sneaking.");
		}
	}
}
