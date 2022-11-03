package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.hbm.blocks.ModBlocks;
import com.hbm.capability.HbmLivingProps;
import com.hbm.items.ModItems;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.items.weapon.ItemGunEgon;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGeigerCounter extends Item {

	Random rand = new Random();
	
	public ItemGeigerCounter(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		if(entity instanceof EntityPlayer) {
			
			if(ArmorFSB.hasFSBArmor((EntityPlayer)entity) && ((ArmorFSB)((EntityPlayer)entity).inventory.armorInventory.get(2).getItem()).geigerSound)
				return;
		}
		
		float x = HbmLivingProps.getRadBuf((EntityLivingBase)entity);
		
		if(world.getTotalWorldTime() % 5 == 0) {
			if(x > 0) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1)
					list.add(0);
				if(x < 5)
					list.add(0);
				if(x < 10)
					list.add(1);
				if(x > 5 && x < 20)
					list.add(2);
				if(x > 15 && x < 40)
					list.add(3);
				if(x > 30 && x < 80)
					list.add(4);
				if(x > 60 && x < 160)
					list.add(5);
				if(x > 160)
					list.add(6);
			
				int r = list.get(rand.nextInt(list.size()));
				
				if(r > 0)
					world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.geigerSounds[r-1], SoundCategory.PLAYERS, 1.0F, 1.0F);
			} else if(rand.nextInt(50) == 0) {
				world.playSound(null, entity.posX, entity.posY, entity.posZ, HBMSoundHandler.geigerSounds[(rand.nextInt(1))], SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
	}
	
	static void setInt(ItemStack stack, int i, String name) {
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		stack.getTagCompound().setInteger(name, i);
	}
	
	public static int getInt(ItemStack stack, String name) {
		if(stack.hasTagCompound())
			return stack.getTagCompound().getInteger(name);
		
		return 0;
	}

	public static int check(@Nullable EntityPlayer player, World world, BlockPos pos) {
		
		RadiationSavedData data = RadiationSavedData.getData(world);
		
		int rads = (int)Math.ceil(data.getRadNumFromCoord(pos));
		
		if(player != null){
			if(ItemGunEgon.getIsFiring(player.getHeldItemMainhand())){
				rads += 100;
			}
		}
		return rads;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.getBlockState(pos).getBlock() == ModBlocks.block_red_copper) {
    		Library.consumeInventoryItem(player.inventory, ModItems.geiger_counter);
    		player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner));
    		return EnumActionResult.SUCCESS;
    	}
    	
    	return EnumActionResult.PASS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(!world.isRemote) {
	    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 1.0F, 1.0F);

	    	ContaminationUtil.printGeigerData(player);
		}
		
		return super.onItemRightClick(world, player, handIn);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !ItemStack.areItemsEqual(oldStack, newStack);
	}
}
