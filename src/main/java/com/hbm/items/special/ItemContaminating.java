package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.entity.effect.EntityFalloutUnderGround;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemContaminating extends ItemHazard {
	
	private int burntime;
	private int falloutBallRadius = 0;

	public ItemContaminating(float radiation, String s){
		super(radiation, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(float radiation, boolean fire, String s){
		super(radiation, fire, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(float radiation, boolean fire, boolean blinding, String s){
		super(radiation, fire, blinding, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem){
		if(entityItem != null && !entityItem.world.isRemote && entityItem.onGround) {
			if(falloutBallRadius > 1){
				EntityFalloutUnderGround falloutBall = new EntityFalloutUnderGround(entityItem.world);
				falloutBall.posX = entityItem.posX;
				falloutBall.posY = entityItem.posY;
				falloutBall.posZ = entityItem.posZ;
				falloutBall.setScale(falloutBallRadius);
				entityItem.world.spawnEntity(falloutBall);
			}
			entityItem.setDead();
			return true;
		}
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
		super.addInformation(stack, world, list, flagIn);
		if(falloutBallRadius > 1){
			list.add(TextFormatting.DARK_GREEN + "[Contaminating Drop]");
			list.add(TextFormatting.GREEN + " Radius: "+falloutBallRadius+"m");
		}
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
}
