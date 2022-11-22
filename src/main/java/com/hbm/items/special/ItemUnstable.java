package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUnstable extends Item {

	int radius;
	int timer;

	public ItemUnstable(int radius, int timer, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.radius = radius;
		this.timer = timer;
		this.setHasSubtypes(true);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(stack.getItemDamage() != 0)
			return;

		this.setTimer(stack, this.getTimer(stack) + 1);

		if(this.getTimer(stack) == timer && !world.isRemote) {
			world.spawnEntity(EntityNukeExplosionMK4.statFac(world, radius, entity.posX, entity.posY, entity.posZ));
			EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, radius);
			entity2.posX = entity.posX;
			entity2.posY = entity.posY;
			entity2.posZ = entity.posZ;
			world.spawnEntity(entity2);
			world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.oldExplosion, SoundCategory.PLAYERS, 1.0F, 1.0F);
			entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10000);
		}
	}

	private void setTimer(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setInteger("timer", time);
	}

	private int getTimer(ItemStack stack) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		return stack.getTagCompound().getInteger("timer");
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !oldStack.isItemEqual(newStack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		switch(stack.getItemDamage()) {
		case 1:
			return "ELEMENTS";
		case 2:
			return "ARSENIC";
		case 3:
			return "VAULT";
		default:
			return ("" + I18n.format(this.getUnlocalizedName() + ".name")).trim();
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getItemDamage() != 0)
    		return;
    	tooltip.add("§4[Unstable]§r");
		tooltip.add("§cDecay Time: " + (int)timer/20 + "s - Explosion Radius: "+ radius+"m§r");
		tooltip.add("§cDecay: " + (getTimer(stack) * 100 / timer) + "%§r");
	}

}
