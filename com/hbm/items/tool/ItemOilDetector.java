package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemOilDetector extends Item {

	public ItemOilDetector(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Right click to scan for oil.");
		tooltip.add("Scanner can only detect larger deposits!");
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
		
		if(world.isRemote)
		{
			if(oil) {
				player.sendMessage(new TextComponentTranslation("Oil deposit detected!"));
				if(direct)
					player.sendMessage(new TextComponentTranslation("Oil deposit directly below!"));
			} else {
				player.sendMessage(new TextComponentTranslation("No oil detected."));
			}
		}

    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F);
		
		player.swingArm(hand);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
