package com.hbm.hazard.type;

import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.config.CompatibilityConfig;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.generic.BlockClean;
import com.hbm.entity.effect.EntityFalloutUnderGround;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class HazardTypeContaminating extends HazardTypeBase {

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
	}

	@Override
	public void updateEntity(EntityItem entityItem, float level) {
		if(entityItem != null && !entityItem.world.isRemote && (entityItem.onGround || entityItem.isBurning()) && CompatibilityConfig.isWarDim(entityItem.world)) {
			if(!isCleanGround(new BlockPos(entityItem.posX, entityItem.posY, entityItem.posZ), entityItem.world)){
				if(level > 1){
					EntityFalloutUnderGround falloutBall = new EntityFalloutUnderGround(entityItem.world);
					falloutBall.posX = entityItem.posX;
					falloutBall.posY = entityItem.posY+0.5F;
					falloutBall.posZ = entityItem.posZ;
					falloutBall.setScale((int)level);
					entityItem.world.spawnEntity(falloutBall);
				}
				entityItem.setDead();
			}
		}
	}

	@Override
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		list.add("§2["+I18nUtil.resolveKey("trait.contaminating")+"§2]");
		list.add(" §a"+I18nUtil.resolveKey("trait.contaminating.radius", (int)level));
	}

	public static boolean isCleanGround(BlockPos pos, World world){
		Block b = world.getBlockState(pos.down()).getBlock();
		boolean isClean = b instanceof BlockClean;
		if(isClean){
			BlockClean.getUsed(b, pos.down(), world);
		}
		return isClean;
	}
}