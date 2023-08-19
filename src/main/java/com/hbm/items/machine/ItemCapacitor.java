package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCapacitor extends Item {

	private int dura;
	
	public ItemCapacitor(int dura, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.dura = dura;
		
		ModItems.ALL_ITEMS.add(this);
	}

	public static String getColor(long a, long b){
		float fraction = 100F * a/b;
		if(fraction > 75)
			return "§a";
		if(fraction > 25)
			return "§e";
		return "§c";
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this == ModItems.redcoil_capacitor) {
			tooltip.add("Right-click a block to negate positive charge.");
			tooltip.add("§b[Needed for Schrabidium Synthesis]");
			int itemDura = getDura(stack);
			tooltip.add(getColor(itemDura, dura) + itemDura + " §2/ " + dura);
		}
		if (this == ModItems.titanium_filter) {
			tooltip.add("§e[Needed for Watz Reaction]");
			int itemDura = getDura(stack);
			tooltip.add(getColor(itemDura, dura) + itemDura/20 + " §2/ " + dura/20);
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (this == ModItems.redcoil_capacitor) {
			ItemStack stack = player.getHeldItem(hand);
			if (!player.isSneaking()) {
				if (getDura(stack) < dura) {

					setDura(stack, getDura(stack) + 1);
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
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0;
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double)getDura(stack) / (double)dura;
	}
	
	public static int getDura(ItemStack stack) {

    	if(!stack.hasTagCompound())
    		return ((ItemCapacitor)stack.getItem()).dura;

    	return stack.getTagCompound().getInteger("dura");
    }

    public static void setDura(ItemStack stack, int dura) {

    	if(!stack.hasTagCompound())
    		stack.setTagCompound(new NBTTagCompound());

    	stack.getTagCompound().setInteger("dura", dura);
    }
}
