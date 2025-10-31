package com.leafia.contents.gear;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemGasSensor extends ItemArmorMod {
	public ItemGasSensor(String s) {
		super(ArmorModHandler.extra,true,true,true,true,s);
	}
	@Override
	public void addInformation(ItemStack stack,World worldIn,List<String> list,ITooltipFlag flagIn){
		String[] lol = I18nUtil.resolveKey("item.gas_sensor.desc").split("\\$");
		for (String s : lol)
			list.add(TextFormatting.YELLOW + s);
		list.add("");
		super.addInformation(stack,worldIn,list,flagIn);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.YELLOW + "  " + stack.getDisplayName() + " ("+I18nUtil.resolveKey("item.gas_sensor.mod")+")");
	}

	@Override
	public void onUpdate(ItemStack stack,World worldIn,Entity entityIn,int itemSlot,boolean isSelected) {
		super.onUpdate(stack,worldIn,entityIn,itemSlot,isSelected);
		if (entityIn instanceof EntityLivingBase)
			modUpdate((EntityLivingBase)entityIn,null);
	}

	@Override
	public void modUpdate(EntityLivingBase entity,ItemStack armor) {

		if(entity.world.isRemote || entity.world.getTotalWorldTime() % 20 != 0) return;

		int x = (int) Math.floor(entity.posX);
		int y = (int) Math.floor(entity.posY + entity.getEyeHeight() - entity.getYOffset());
		int z = (int) Math.floor(entity.posZ);

		boolean poison = false;
		boolean explosive = false;

		for(int i = -3; i <= 3; i++) {
			for(int j = -1; j <= 1; j++) {
				for(int k = -3; k <= 3; k++) {
					Block b = entity.world.getBlockState(new BlockPos(x + i * 2, y + j * 2, z + k * 2)).getBlock();
					if(b == ModBlocks.gas_asbestos || b == ModBlocks.gas_coal || b == ModBlocks.gas_radon || b == ModBlocks.gas_monoxide || b == ModBlocks.gas_radon_dense || b == ModBlocks.chlorine_gas) {
						poison = true;
					}
					if(b == ModBlocks.gas_flammable || b == ModBlocks.gas_explosive) {
						explosive = true;
					}
				}
			}
		}

		if(explosive) {
			if (entity instanceof EntityPlayer)
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(TextFormatting.RED+""+TextFormatting.BOLD+I18nUtil.resolveKey("chat.gas_sensor.flammable")),true);
			entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.follyAquired,SoundCategory.PLAYERS,0.5F,1.0F);
		} else if(poison) {
			if (entity instanceof EntityPlayer)
				((EntityPlayer) entity).sendStatusMessage(new TextComponentString(TextFormatting.GOLD+""+TextFormatting.BOLD+I18nUtil.resolveKey("chat.gas_sensor.poison")),true);
			entity.world.playSound(null,entity.posX,entity.posY,entity.posZ,HBMSoundEvents.techBoop,SoundCategory.PLAYERS,2F,1.5F);
		}
	}
}
