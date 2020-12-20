package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCapacitor extends Item {

	public ItemCapacitor(int dura, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setMaxDamage(dura);
		this.setNoRepair();
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this == ModItems.redcoil_capacitor) {
			tooltip.add("Right-click a block to negate positive charge.");
			tooltip.add("Does not work in creative mode!");
			tooltip.add("[Needed for Schrabidium Synthesis]");
		}
		if (this == ModItems.titanium_filter) {
			tooltip.add("Can be used to remove waste");
			tooltip.add("from a watz reactor!");
			tooltip.add("[Needed for Watz Reaction]");
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this == ModItems.redcoil_capacitor) {
			ItemStack stack = player.getHeldItem(hand);
			if (!player.isSneaking()) {
				if (stack.getItemDamage() > 0) {
					stack.setItemDamage(stack.getItemDamage() - 1);
					if (!world.isRemote) {
						world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2.5F, true);
					}
					world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));

					return EnumActionResult.SUCCESS;
				}
			}
		}

		return EnumActionResult.PASS;
	}
	
}
