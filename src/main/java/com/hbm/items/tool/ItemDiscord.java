package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDiscord extends Item {

	public ItemDiscord(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		RayTraceResult pos = Library.rayTrace(player, 100, 1);

		if(pos.typeOfHit == Type.BLOCK) {

			if(!world.isRemote) {

	            if(player.isRiding())
	            	player.dismountRidingEntity();

	            ForgeDirection dir = ForgeDirection.getOrientation(pos.sideHit.ordinal());

	            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);

	            player.setPositionAndUpdate(pos.hitVec.x + dir.offsetX, pos.hitVec.y + dir.offsetY - 1, pos.hitVec.z + dir.offsetZ);

	            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
	            player.fallDistance = 0.0F;
			}

	        for (int i = 0; i < 32; ++i)
	        	world.spawnParticle(EnumParticleTypes.PORTAL, player.posX, player.posY + player.getRNG().nextDouble() * 2.0D, player.posZ, player.getRNG().nextGaussian(), 0.0D, player.getRNG().nextGaussian());
		}

		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "I've seen the Rod of Discord and honestly");
		list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "it's not as amazing as people say.");
		list.add("");
		list.add(TextFormatting.RED + "" + TextFormatting.ITALIC + "Rod of Discord is crucial in so many boss fights.");
		list.add(TextFormatting.RED + "" + TextFormatting.ITALIC + "Imagine getting coiled by worm bosses.");
		list.add("");
		list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "Oh, you mean the Terraria item.");
		list.add(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + "Idk about that thing.");
	}
}
