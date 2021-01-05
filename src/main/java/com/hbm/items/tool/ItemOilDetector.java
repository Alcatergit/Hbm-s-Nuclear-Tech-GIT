package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemOilDetector extends Item {

	public ItemOilDetector(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(this.getUnlocalizedName() + ".desc1"));
		tooltip.add(I18n.format(this.getUnlocalizedName() + ".desc2"));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		boolean oil = false;
		boolean direct = false;
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;

		MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x, i, z)).getBlock() == ModBlocks.ore_oil)
				direct = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x + 5, i, z)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x - 5, i, z)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x, i, z + 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x, i, z - 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlockState(mPos.setPos(x + 10, i, z)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlockState(mPos.setPos(x - 10, i, z)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlockState(mPos.setPos(x, i, z + 10)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 10; i--)
			if(world.getBlockState(mPos.setPos(x, i, z - 10)).getBlock() == ModBlocks.ore_oil)
				oil = true;

		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x + 5, i, z + 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x - 5, i, z + 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x + 5, i, z - 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		for(int i =  y + 15; i > 5; i--)
			if(world.getBlockState(mPos.setPos(x - 5, i, z - 5)).getBlock() == ModBlocks.ore_oil)
				oil = true;
		
		if(direct)
			oil = true;
		
		if(!world.isRemote) {
			
			if(direct) {
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".bullseye").setStyle(new Style().setColor(TextFormatting.DARK_GREEN)));
			} else if(oil) {
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".detected").setStyle(new Style().setColor(TextFormatting.GOLD)));
			} else {
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".noOil").setStyle(new Style().setColor(TextFormatting.RED)));
			}
		}

    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F);
		
		player.swingArm(hand);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
