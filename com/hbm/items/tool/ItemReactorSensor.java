package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemReactorSensor extends Item {

	public ItemReactorSensor(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		Block b = world.getBlockState(pos).getBlock();

		if(b == ModBlocks.machine_reactor_small || b == ModBlocks.dummy_block_reactor_small || b == ModBlocks.dummy_port_reactor_small) {

			if(stack.getTagCompound() == null)
				stack.setTagCompound(new NBTTagCompound());

			stack.getTagCompound().setInteger("x", pos.getX());
			stack.getTagCompound().setInteger("y", pos.getY());
			stack.getTagCompound().setInteger("z", pos.getZ());

			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 1.0F, 1.0F);

			player.swingArm(hand);
			return EnumActionResult.SUCCESS;

		}
		//TODO reactor thing
		/*if(b == ModBlocks.reactor_hatch || b == ModBlocks.reactor_ejector || b == ModBlocks.reactor_inserter) {

			int meta = world.getBlockMetadata(x, y, z);

			switch(meta) {
			case 2:
				z += 2;
				break;
			case 3:
				z -= 2;
				break;
			case 4:
				x += 2;
				break;
			case 5:
				x -= 2;
				break;
			}
		}*/
		//TODO reactor computer
		/*if(b == ModBlocks.reactor_computer) {

			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();

			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);

			world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);

			player.swingArm(hand);
			return true;
		}*/

		return EnumActionResult.PASS;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getTagCompound() != null) {
			tooltip.add("x: " + stack.getTagCompound().getInteger("x"));
			tooltip.add("y: " + stack.getTagCompound().getInteger("y"));
			tooltip.add("z: " + stack.getTagCompound().getInteger("z"));
		} else {
			tooltip.add("No reactor selected!");
		}
	}
}
